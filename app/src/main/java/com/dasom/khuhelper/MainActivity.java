package com.dasom.khuhelper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

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
        // TODO: 09/07/2020 지도api 연결 & 충전소 위치 띄우기 
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
