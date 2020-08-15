package com.dasom.khuhelper.user;

import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.dasom.khuhelper.R;

import androidx.appcompat.app.AppCompatActivity;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener{

    ImageView userLoginBtn;
    EditText searchEdt;
    Button adminLoginBtn;
    TextView resultTv;

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
        userLoginBtn = findViewById(R.id.btn_search_back);
        searchEdt = findViewById(R.id.edt_ev_search);
        adminLoginBtn = findViewById(R.id.btn_search);
        resultTv = findViewById(R.id.tv_result);

        searchEdt.requestFocus();

        userLoginBtn.setOnClickListener(this);
        adminLoginBtn.setOnClickListener(this);

        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    public void finish() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
        super.finish();
    }
}
