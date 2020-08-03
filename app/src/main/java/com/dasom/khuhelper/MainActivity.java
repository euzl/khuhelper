package com.dasom.khuhelper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    LinearLayout searchBtn;
    ViewGroup mapViewContainer;
    MapView mapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        parseChargingStation(); // 충전소 api 파싱

        initView();
        markChargingStation(); // 마커 표시
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btn_search_ev_place:
                startActivity(new Intent(MainActivity.this, SearchActivity.class));
                break;
        }
    }

    private void initView() {
        // xml
        searchBtn = findViewById(R.id.btn_search_ev_place);
        mapViewContainer = (ViewGroup) findViewById(R.id.map_view);

        searchBtn.setOnClickListener(this);

        // map view
        mapView = new MapView(this);
        mapViewContainer.addView(mapView);
    }

    private void parseChargingStation() {
        // 충전소 파서 실행 (AsyncTask)
        ChargingStationParser chargingStationParser = new ChargingStationParser();
        chargingStationParser.execute();
    }

    private void markChargingStation() {
        // 마커표시 테스트
//        MapPOIItem mapPOIItem = new MapPOIItem();
//        mapPOIItem.setItemName("서울시청");
//        mapPOIItem.setTag(0);
//        mapPOIItem.setMapPoint(MapPoint.mapPointWithGeoCoord(37.5666805, 126.9784147));
//        mapPOIItem.setMarkerType(MapPOIItem.MarkerType.CustomImage); // 마커타입을 커스텀 마커로 지정.
//        mapPOIItem.setCustomImageResourceId(R.drawable.ic_ev_place); // 마커 이미지.
//        mapPOIItem.setCustomImageAutoscale(false); // hdpi, xhdpi 등 안드로이드 플랫폼의 스케일을 사용할 경우 지도 라이브러리의 스케일 기능을 꺼줌.
//        mapPOIItem.setCustomImageAnchor(0.5f, 1.0f); // 마커 이미지중 기준이 되는 위치(앵커포인트) 지정 - 마커 이미지 좌측 상단 기준 x(0.0f ~ 1.0f), y(0.0f ~ 1.0f) 값.
//        mapView.addPOIItem(mapPOIItem);
    }
}
