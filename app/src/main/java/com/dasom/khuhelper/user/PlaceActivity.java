package com.dasom.khuhelper.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.dasom.khuhelper.R;

import androidx.appcompat.app.AppCompatActivity;

public class PlaceActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);

        initView();
    }

    private void initView() {
        Button petitionApplyBtn = findViewById(R.id.btn_petition_apply);
        ImageView searchBackBtn = findViewById(R.id.btn_search_back);
        petitionApplyBtn.setOnClickListener(this);
        searchBackBtn.setOnClickListener(this);

        // TODO: 14/07/2020 전기충전소에 대한 정보 받아와서 표시해주기
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btn_petition_apply:
                // TODO: 14/07/2020 아마도 그냥 액티비티를 시작하는 게 아니라, 현재 충전소의 이름(또는 아이디)를 같이 넘겨준다.
                startActivity(new Intent(PlaceActivity.this, PetitionActivity.class));
                break;
            case R.id.btn_search_back:
                finish();
                break;
        }
    }
}
