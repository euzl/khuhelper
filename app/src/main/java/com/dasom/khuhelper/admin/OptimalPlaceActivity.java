package com.dasom.khuhelper.admin;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.dasom.khuhelper.R;

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

    ArrayList<AnalyzePlace> analyzePlaces = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_optimalplace);

        initView();
        setAnalyzePlaces();
        markRecommendPlace();
    }

    private void initView() {
        backBtn = findViewById(R.id.btn_optimal_back);
        backBtn.setOnClickListener(this);

        mapViewContainer = findViewById(R.id.map_view);
        mapView = new MapView(this);
        mapViewContainer.addView(mapView);
    }

    private void markRecommendPlace() {
        MapPOIItem mapPOIItem;
        Log.d("Mark Recommend Place", "마커표시시작 총 개수 - " + analyzePlaces.size());
        for (int i=0; i<1000; i += 15) {
            mapPOIItem = new MapPOIItem();
            mapPOIItem.setItemName(analyzePlaces.get(i).getKey());
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
        }
    }
}
