package com.dasom.khuhelper.user;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dasom.khuhelper.R;
import com.dasom.khuhelper.user.map.ChargingStation;
import com.dasom.khuhelper.user.map.ChargingStationParser;
import com.dasom.khuhelper.user.map.ChargingStationParserCallBack;

import net.daum.mf.map.api.CalloutBalloonAdapter;
import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import java.util.ArrayList;

public class UserActivity extends AppCompatActivity implements View.OnClickListener {

    ViewGroup mapViewContainer;
    MapView mapView;
    EditText searchEdt;
    ImageView backBtn;
    ImageButton petitionBtn;

    boolean isSearchMode = false;

    InputMethodManager imm;

    ChargingStationParser chargingStationParser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        initView();
        parseChargingStation(); // 충전소 api 파싱
    }

    class CustomCalloutBalloonAdapter implements CalloutBalloonAdapter {
        private final View mCalloutBalloon;

        TextView nameTv;

        public CustomCalloutBalloonAdapter() {
            mCalloutBalloon = getLayoutInflater().inflate(R.layout.khuhelper_callout_ballon, null);
        }

        @Override
        public View getCalloutBalloon(MapPOIItem poiItem) {
            Intent intent = new Intent(UserActivity.this, PlaceActivity.class);
            intent.putExtra("chargingStation", chargingStationParser.findStationByTag(poiItem.getTag()));
            startActivity(intent);

            nameTv = mCalloutBalloon.findViewById(R.id.tv_name);
            nameTv.setText(poiItem.getItemName());

            return mCalloutBalloon;
        }

        @Override
        public View getPressedCalloutBalloon(MapPOIItem poiItem) {
            return null;
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btn_user_back:
                onBackButton();
                break;
            case R.id.btn_petition:
                startActivity(new Intent(UserActivity.this, PetitionComfirmActivity.class));
        }
    }

    private void initView() {
        // xml
        mapViewContainer = findViewById(R.id.map_view);
        searchEdt = findViewById(R.id.edt_search);
        backBtn = findViewById(R.id.btn_user_back);
        petitionBtn = findViewById(R.id.btn_petition);

        backBtn.setOnClickListener(this);
        petitionBtn.setOnClickListener(this);

        // map view
        mapView = new MapView(this);
        mapViewContainer.addView(mapView);
        mapView.setCalloutBalloonAdapter(new CustomCalloutBalloonAdapter());
        mapView.setMapCenterPointAndZoomLevel(MapPoint.mapPointWithGeoCoord(37.518469, 126.988232), 5, false);

        searchEdt.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchEdt.setOnEditorActionListener(new TextView.OnEditorActionListener()
        {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
            {
                if(actionId == EditorInfo.IME_ACTION_DONE)
                {
                    searchPlace(searchEdt.getText().toString());
                    isSearchMode = true;
                    return true;
                }
                return false;
            }
        });
    }

    private void onBackButton() {
        if (isSearchMode) {
            searchEdt.setHint(R.string.search_ev_place);
            isSearchMode = false;
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.DialogStyle);
            builder.setMessage("로그아웃 하시겠습니까?")
                    .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            finish();
                            Toast.makeText(UserActivity.this, "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // 취소하면 남겨놓음
                        }
                    });
            builder.show();
        }
    }

    /**
     * name이 해당되는 충전소 마커로 표시
     *
     * @param name
     */
    private void searchPlace(String name) {
        imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);

        mapView.removeAllPOIItems();
        markChargingStation(chargingStationParser.getStationByName(name));
        StringBuilder builder = new StringBuilder()
                .append(name)
                .append("(와)과 관련된 충전소가 표시되었습니다.");
        Toast.makeText(UserActivity.this, builder, Toast.LENGTH_SHORT).show();
        mapView.fitMapViewAreaToShowAllPOIItems();
    }

    private void parseChargingStation() {
        // 충전소 파서 실행 (AsyncTask)
        chargingStationParser = new ChargingStationParser(new ChargingStationParserCallBack() {
            @Override
            public void onSuccess(ArrayList<ChargingStation> chargingStations) {
                markChargingStation(chargingStations);
            }
        });
        chargingStationParser.execute();
    }

    private void markChargingStation(ArrayList<ChargingStation> chargingStations) {
        MapPOIItem mapPOIItem;
        Log.d("Mark Carging Station", "마커표시시작");
        int tag=0;
        for (ChargingStation cs : chargingStations) {
            mapPOIItem = new MapPOIItem();
            mapPOIItem.setItemName(cs.getStatNm());
            mapPOIItem.setTag(tag++);
            mapPOIItem.setMapPoint(MapPoint.mapPointWithGeoCoord(cs.getLat(), cs.getLng()));
            mapPOIItem.setMarkerType(MapPOIItem.MarkerType.CustomImage); // 마커타입을 커스텀 마커로 지정.
            mapPOIItem.setCustomImageResourceId(R.drawable.ic_ev_place); // 마커 이미지.
            mapPOIItem.setCustomImageAutoscale(false); // hdpi, xhdpi 등 안드로이드 플랫폼의 스케일을 사용할 경우 지도 라이브러리의 스케일 기능을 꺼줌.
            mapPOIItem.setCustomImageAnchor(0.5f, 1.0f); // 마커 이미지중 기준이 되는 위치(앵커포인트) 지정 - 마커 이미지 좌측 상단 기준 x(0.0f ~ 1.0f), y(0.0f ~ 1.0f) 값.
            mapView.addPOIItem(mapPOIItem);
        }
    }
}
