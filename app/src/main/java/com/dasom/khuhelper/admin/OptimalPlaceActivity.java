package com.dasom.khuhelper.admin;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dasom.khuhelper.R;

import net.daum.mf.map.api.CalloutBalloonAdapter;
import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;

import androidx.appcompat.app.AppCompatActivity;

public class OptimalPlaceActivity extends AppCompatActivity implements View.OnClickListener {

    ViewGroup mapViewContainer;
    MapView mapView;

    ImageView backBtn;

    LinearLayout all;
    LinearLayout red;
    LinearLayout orange;
    LinearLayout yellow;
    LinearLayout green;
    LinearLayout sky;

    ArrayList<AnalyzePlace> analyzePlaces = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_optimalplace);

        initView();
        setAnalyzePlaces();
        markRecommendPlace(0, 426, 10);
        markRecommendPlace(426,5000,13);
        markRecommendPlace(5000, 29339, 100);
    }

    private void initView() {
        backBtn = findViewById(R.id.btn_optimal_back);
        backBtn.setOnClickListener(this);

        all = findViewById(R.id.layout_all);
        red = findViewById(R.id.layout_red);
        orange = findViewById(R.id.layout_orange);
        yellow = findViewById(R.id.layout_yellow);
        green = findViewById(R.id.layout_green);
        sky = findViewById(R.id.layout_sky);
        all.setOnClickListener(this);
        red.setOnClickListener(this);
        orange.setOnClickListener(this);
        yellow.setOnClickListener(this);
        green.setOnClickListener(this);
        sky.setOnClickListener(this);

        mapViewContainer = findViewById(R.id.map_view);
        mapView = new MapView(this);
        mapViewContainer.addView(mapView);
        mapView.setCalloutBalloonAdapter(new OptimalPlaceActivity.CustomCalloutBalloonAdapter());
        mapView.setMapCenterPointAndZoomLevel(MapPoint.mapPointWithGeoCoord(37.518469, 126.988232), 5, false);
    }

    private void markRecommendPlace(int first, int last, int term) {
        MapPOIItem mapPOIItem;
        Log.d("Mark Recommend Place", "마커표시시작 총 개수 - " + analyzePlaces.size());
        for (int i=first; i<last; i += term) {
            mapPOIItem = new MapPOIItem();
            mapPOIItem.setItemName(analyzePlaces.get(i).getFinalPoint()+"");
            mapPOIItem.setMapPoint(MapPoint.mapPointWithGeoCoord(analyzePlaces.get(i).getLat(), analyzePlaces.get(i).getLng()));
            mapPOIItem.setMarkerType(MapPOIItem.MarkerType.CustomImage); // 마커타입을 커스텀 마커로 지정.
            mapPOIItem.setCustomImageResourceId(getMarkerColorId(analyzePlaces.get(i).getFinalPoint())); // 마커 이미지.
            mapPOIItem.setCustomImageAutoscale(false); // hdpi, xhdpi 등 안드로이드 플랫폼의 스케일을 사용할 경우 지도 라이브러리의 스케일 기능을 꺼줌.
            mapPOIItem.setCustomImageAnchor(0.5f, 0.5f); // 마커 이미지중 기준이 되는 위치(앵커포인트) 지정 - 마커 이미지 좌측 상단 기준 x(0.0f ~ 1.0f), y(0.0f ~ 1.0f) 값.
            mapView.addPOIItem(mapPOIItem);
        }
    }

    private int getMarkerColorId(double finalPoint) {
        if (finalPoint >= 50) {
            return R.drawable.ic_recommend_red;
        } else if (finalPoint >= 40) {
            return R.drawable.ic_recommend_orange;
        } else if (finalPoint >= 30) {
            return R.drawable.ic_recommend_yellow;
        } else if (finalPoint >= 20) {
            return R.drawable.ic_recommend_green;
        } else {
            return R.drawable.ic_recommned_sky;
        }
    }

    private void setAnalyzePlaces() {
        String json = getJonString();
        jsonParsing(json);
    }

    private String getJonString() {
        String json = "";
        AssetManager assetManager = getAssets();
        try {
            InputStream is = assetManager.open("0814.json");
            int fileSize = is.available();

            byte[] buffer = new byte[fileSize];
            is.read(buffer);
            is.close();

            json = new String(buffer, "UTF-8");
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }

        return json;
    }

    private void jsonParsing(String json)
    {
        try{
            AnalyzePlace analyzePlace;

            JSONObject jsonObject = new JSONObject(json);
            JSONObject finalPointObject = jsonObject.getJSONObject("Final_Point");
            final Iterator<String> keys = finalPointObject.keys();

            JSONObject lngObject = jsonObject.getJSONObject("Centroid_x");
            JSONObject latObject = jsonObject.getJSONObject("Centroid_y");

            while(keys.hasNext()) {
                analyzePlace = new AnalyzePlace();
                String key = keys.next();
                analyzePlace.setId(Integer.parseInt(key));
                analyzePlace.setKey(key);
                analyzePlace.setFinalPoint(finalPointObject.getDouble(key));
                analyzePlace.setLat(latObject.getDouble(key));
                analyzePlace.setLng(lngObject.getDouble(key));
                analyzePlaces.add(analyzePlace);
            }
        }catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btn_optimal_back:
                finish();
                break;
            case R.id.layout_all:
                mapView.removeAllPOIItems();
                markRecommendPlace(0, 5000, 10);
                markRecommendPlace(5000, 29339, 100);
                break;
            case R.id.layout_red:
                mapView.removeAllPOIItems();
                markRecommendPlace(0,426,5);
                break;
            case R.id.layout_orange:
                mapView.removeAllPOIItems();
                markRecommendPlace(426, 3768, 50);
                break;
            case R.id.layout_yellow:
                mapView.removeAllPOIItems();
                markRecommendPlace(3768, 11483, 50);
                break;
            case R.id.layout_green:
                mapView.removeAllPOIItems();
                markRecommendPlace(11483, 20285, 50);
                break;
            case R.id.layout_sky:
                mapView.removeAllPOIItems();
                markRecommendPlace(20285, 29339, 50);
                break;
        }
    }

    class CustomCalloutBalloonAdapter implements CalloutBalloonAdapter {
        private final View mCalloutBalloon;

        TextView nameTv;

        public CustomCalloutBalloonAdapter() {
            mCalloutBalloon = getLayoutInflater().inflate(R.layout.khuhelper_callout_ballon, null);
        }

        @Override
        public View getCalloutBalloon(MapPOIItem poiItem) {
            nameTv = mCalloutBalloon.findViewById(R.id.tv_name);
            nameTv.setText("Final Point : " + poiItem.getItemName());

            return mCalloutBalloon;
        }

        @Override
        public View getPressedCalloutBalloon(MapPOIItem poiItem) {
            return null;
        }
    }
}
