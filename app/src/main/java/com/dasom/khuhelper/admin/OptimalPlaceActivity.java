package com.dasom.khuhelper.admin;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.ViewGroup;

import com.dasom.khuhelper.R;

import net.daum.mf.map.api.MapView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;

public class OptimalPlaceActivity extends AppCompatActivity {

    ViewGroup mapViewContainer;
    MapView mapView;

    ArrayList<AnalyzePlace> analyzePlaces;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_optimalplace);

//        initView();
        setAnalyzePlaces();
    }

    private void initView() {
        mapViewContainer = findViewById(R.id.map_view);
        mapView = new MapView(this);
        mapViewContainer.addView(mapView);
    }

    private void setAnalyzePlaces() {
        String json = getJonString();
        jsonParsing(json);
    }

    private String getJonString() {
        String json = "";
        AssetManager assetManager = getAssets();
        try {
            InputStream is = assetManager.open("0811modi.json");
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
        double[][] points = new double[5][2]; // 좌표등록

        try{
            JSONObject jsonObject = new JSONObject(json);

            JSONArray featureArray = jsonObject.getJSONArray("Features");

//            for(int i=0; i<10; i++)
            for(int i=0; i<featureArray.length(); i++)
            {
                JSONObject featureObject = featureArray.getJSONObject(i);

                AnalyzePlace analyzePlace = new AnalyzePlace();

                analyzePlace.setId(featureObject.getInt("id"));

                // sub json
                String properties = featureObject.getString("properties");
                JSONObject propertyObject = new JSONObject(properties);

                analyzePlace.setFinalPoint(propertyObject.getDouble("Final_Point"));

                // sub json
                String geometry = featureObject.getString("geometry");
                JSONObject geometryObject = new JSONObject(geometry);

                // sub json 2층
                JSONArray coordinateArray = geometryObject.getJSONArray("coordinates");
                JSONArray subArray = coordinateArray.getJSONArray(0);

                for (int j = 0; j < 5; j++) {
                    JSONArray subsubArray = subArray.getJSONArray(j);
                    for (int k = 0; k < 2; k++) {
                        points[j][k] = subsubArray.getDouble(k);
                    }
                }
                analyzePlace.setPoints(points);

                analyzePlaces.add(analyzePlace);
            }
        }catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
