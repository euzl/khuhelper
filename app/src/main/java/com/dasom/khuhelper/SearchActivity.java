package com.dasom.khuhelper;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_search_back:
                startActivity(new Intent(SearchActivity.this, MainActivity.class));
                break;
            case R.id.btn_search:
                // TODO: 09/07/2020 '검색'버튼 눌렀을 때 액션 (검색내용에 맞는 결과 띄워주기)
                break;
        }
    }

    private void initView() {
        Button userLoginBtn = findViewById(R.id.btn_user_login);
        Button adminLoginBtn = findViewById(R.id.btn_admin_login);

        userLoginBtn.setOnClickListener(this);
        adminLoginBtn.setOnClickListener(this);
    }
}
