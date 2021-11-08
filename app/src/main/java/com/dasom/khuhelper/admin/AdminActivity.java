package com.dasom.khuhelper.admin;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.dasom.khuhelper.R;

import androidx.appcompat.app.AlertDialog;
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
        firstBtn = findViewById(R.id.firstBtn);
        secondBtn = findViewById(R.id.secondBtn);
        thirdBtn = findViewById(R.id.thirdBtn);
        logoutBtn = findViewById(R.id.logoutBtn);

        firstBtn.setOnClickListener(this);
        secondBtn.setOnClickListener(this);
        thirdBtn.setOnClickListener(this);
        logoutBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.firstBtn:
                startActivity(new Intent(AdminActivity.this, OptimalPlaceActivity.class));
                break;
            case R.id.secondBtn:
                startActivity(new Intent(AdminActivity.this, PredictActivity.class));
                break;
            case R.id.thirdBtn:
                startActivity(new Intent(AdminActivity.this, PetitionManageActivity.class));
                break;
            case R.id.logoutBtn:
                AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.DialogStyle);
                builder.setMessage("로그아웃 하시겠습니까?")
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                finish();
                                Toast.makeText(AdminActivity.this, "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // 취소하면 남겨놓음
                            }
                        });
                builder.show();
                break;
        }

    }
}
