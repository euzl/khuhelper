package com.dasom.khuhelper.admin;

import android.content.res.AssetManager;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dasom.khuhelper.R;
import com.dasom.khuhelper.user.ChargingStation;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapPolyline;
import net.daum.mf.map.api.MapView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;

import androidx.appcompat.app.AppCompatActivity;

public class OptimalPlaceActivity extends AppCompatActivity {

    ViewGroup mapViewContainer;
    MapView mapView;

    TextView testTv;

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
        testTv = findViewById(R.id.tv_parsingtest);

        mapViewContainer = findViewById(R.id.map_view);
        mapView = new MapView(this);
        mapViewContainer.addView(mapView);
    }

    private void markRecommendPlace() {
        MapPOIItem mapPOIItem;
        Log.d("Mark Recommend Place", "마커표시시작");
        int cnt = 1;
        for (AnalyzePlace ap : analyzePlaces) {
            if (cnt == 1000) break;
            mapPOIItem = new MapPOIItem();
            mapPOIItem.setItemName(ap.getKey());
//            mapPOIItem.setTag(ap.getId()); // 일단 생략..
            mapPOIItem.setMapPoint(MapPoint.mapPointWithGeoCoord(ap.getLat(), ap.getLng()));
            mapPOIItem.setMarkerType(MapPOIItem.MarkerType.CustomImage); // 마커타입을 커스텀 마커로 지정.
            mapPOIItem.setCustomImageResourceId(R.drawable.ic_recommend_place); // 마커 이미지.
            mapPOIItem.setCustomImageAutoscale(false); // hdpi, xhdpi 등 안드로이드 플랫폼의 스케일을 사용할 경우 지도 라이브러리의 스케일 기능을 꺼줌.
            mapPOIItem.setCustomImageAnchor(0.5f, 0.5f); // 마커 이미지중 기준이 되는 위치(앵커포인트) 지정 - 마커 이미지 좌측 상단 기준 x(0.0f ~ 1.0f), y(0.0f ~ 1.0f) 값.
            mapView.addPOIItem(mapPOIItem);
            cnt++;
            Log.d("marker", cnt + "개 찍는중");
        }
        // TODO: 05/08/2020 마커를 클릭하면 에러남 ㅠ
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

}
