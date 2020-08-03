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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        LinearLayout searchBtn = findViewById(R.id.btn_search_ev_place);
        searchBtn.setOnClickListener(this);
        // TODO: 09/07/2020 충전소 위치 띄우기

        MapView mapView = new MapView(this);

        ViewGroup mapViewContainer = (ViewGroup) findViewById(R.id.map_view);
        mapViewContainer.addView(mapView);

        // 마커표시 테스트
        MapPOIItem mapPOIItem = new MapPOIItem();
        mapPOIItem.setItemName("서울시청");
        mapPOIItem.setTag(0);
        mapPOIItem.setMapPoint(MapPoint.mapPointWithGeoCoord(37.5666805, 126.9784147));
        mapPOIItem.setMarkerType(MapPOIItem.MarkerType.CustomImage); // 마커타입을 커스텀 마커로 지정.
        mapPOIItem.setCustomImageResourceId(R.drawable.ic_ev_place); // 마커 이미지.
        mapPOIItem.setCustomImageAutoscale(false); // hdpi, xhdpi 등 안드로이드 플랫폼의 스케일을 사용할 경우 지도 라이브러리의 스케일 기능을 꺼줌.
        mapPOIItem.setCustomImageAnchor(0.5f, 1.0f); // 마커 이미지중 기준이 되는 위치(앵커포인트) 지정 - 마커 이미지 좌측 상단 기준 x(0.0f ~ 1.0f), y(0.0f ~ 1.0f) 값.
        mapView.addPOIItem(mapPOIItem);

        MapPOIItem mapPOIItem2 = new MapPOIItem();
        mapPOIItem2.setItemName("덕수궁");
        mapPOIItem2.setTag(1);
        mapPOIItem2.setMapPoint(MapPoint.mapPointWithGeoCoord(37.565984, 126.975570));
        mapPOIItem2.setMarkerType(MapPOIItem.MarkerType.CustomImage); // 마커타입을 커스텀 마커로 지정.
        mapPOIItem2.setCustomImageResourceId(R.drawable.ic_ev_place); // 마커 이미지.
        mapPOIItem2.setCustomImageAutoscale(false); // hdpi, xhdpi 등 안드로이드 플랫폼의 스케일을 사용할 경우 지도 라이브러리의 스케일 기능을 꺼줌.
        mapPOIItem2.setCustomImageAnchor(0.5f, 1.0f); // 마커 이미지중 기준이 되는 위치(앵커포인트) 지정 - 마커 이미지 좌측 상단 기준 x(0.0f ~ 1.0f), y(0.0f ~ 1.0f) 값.
        mapView.addPOIItem(mapPOIItem2);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btn_search_ev_place:
                startActivity(new Intent(MainActivity.this, SearchActivity.class));
                break;
        }
    }
}
