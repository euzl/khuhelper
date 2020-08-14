package com.dasom.khuhelper.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.dasom.khuhelper.R;

import androidx.appcompat.app.AppCompatActivity;

public class AdminActivity extends AppCompatActivity implements View.OnClickListener{

    Button firstBtn;
    Button secondBtn;
    Button thirdBtn;
    TextView logoutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        initView();
    }

    private void initView() {
        firstBtn = findViewById(R.id.btn_menu_1);
        secondBtn = findViewById(R.id.btn_menu_2);
        thirdBtn = findViewById(R.id.btn_menu_3);
        logoutBtn = findViewById(R.id.tv_logout);

        firstBtn.setOnClickListener(this);
        secondBtn.setOnClickListener(this);
        thirdBtn.setOnClickListener(this);
        logoutBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_menu_1:
                startActivity(new Intent(AdminActivity.this, OptimalPlaceActivity.class));
                break;
            case R.id.btn_menu_2:
                startActivity(new Intent(AdminActivity.this, PredictActivity.class));
                break;
            case R.id.btn_menu_3:
                startActivity(new Intent(AdminActivity.this, ManageActivity.class));
                break;
            case R.id.tv_logout:
                Toast.makeText(AdminActivity.this, "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show();
                finish();
                break;
        }

    }
}
