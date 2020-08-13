package com.dasom.khuhelper.admin;

import android.content.res.AssetManager;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dasom.khuhelper.R;

import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapPolyline;
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

    TextView testTv;

    ArrayList<AnalyzePlace> analyzePlaces = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_optimalplace);

        initView();
        setAnalyzePlaces();
        setPolyLine();
    }

    private void initView() {
        testTv = findViewById(R.id.tv_parsingtest);

        mapViewContainer = findViewById(R.id.map_view);
        mapView = new MapView(this);
        mapViewContainer.addView(mapView);
    }

    private void setPolyLine() {
        for (AnalyzePlace ap : analyzePlaces) {
            MapPolyline polyline = new MapPolyline();
            polyline.setTag(ap.getId());
            polyline.setLineColor(Color.argb(128, 255, 51, 0)); // Polyline 컬러 지정.

            double[][] points = ap.getPoints();
            for (int i=0; i<5; i++){
                polyline.addPoint(MapPoint.mapPointWithGeoCoord(points[i][1], points[i][0]));
            }
            mapView.addPolyline(polyline);
        }
    }

    private void setAnalyzePlaces() {
        String json = getJonString();
        jsonParsing(json);
//        testTv.setText(analyzePlaces.get(0).getId() + "\n" + analyzePlaces.get(0).getFinalPoint());
    }

    private String getJonString() {
        String json = "";
        AssetManager assetManager = getAssets();
        try {
            InputStream is = assetManager.open("0811.json");
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
            JSONObject jsonObject = new JSONObject(json);

            JSONArray featureArray = jsonObject.getJSONArray("features");

            for(int i=0; i<100; i++)
//            for(int i=0; i<featureArray.length(); i++)
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

                // sub json 3층
                JSONArray doubleArray = geometryObject.getJSONArray("coordinates");
                JSONArray coordinateArray = doubleArray.getJSONArray(0);
                double[][] coords = new double[5][];
                for (int j = 0; j < 5; j++) {
                    JSONArray xyArray = coordinateArray.getJSONArray(j);
                    coords[j] = new double[2];
                    for (int k = 0; k < 2; k++) {
                        coords[j][k] = xyArray.getDouble(k);
                    }
                }
                analyzePlace.setPoints(coords);

                analyzePlaces.add(analyzePlace);
            }
        }catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
