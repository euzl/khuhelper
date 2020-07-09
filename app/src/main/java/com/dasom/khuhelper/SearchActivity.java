package com.dasom.khuhelper;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        initView();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_search_back:
                finish();
                break;
            case R.id.btn_search:
                // TODO: 09/07/2020 '검색'버튼 눌렀을 때 액션 (검색내용에 맞는 결과 띄워주기)
                break;
        }
    }

    private void initView() {
        ImageView userLoginBtn = findViewById(R.id.btn_search_back);
        EditText searchEdt = findViewById(R.id.edt_ev_search);
        Button adminLoginBtn = findViewById(R.id.btn_search);

        searchEdt.requestFocus();

        userLoginBtn.setOnClickListener(this);
        adminLoginBtn.setOnClickListener(this);

        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }
}
