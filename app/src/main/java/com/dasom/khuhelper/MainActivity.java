package com.dasom.khuhelper;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.dasom.khuhelper.admin.AdminActivity;
import com.dasom.khuhelper.user.UserActivity;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_user_login:
                startActivity(new Intent(MainActivity.this, UserActivity.class));
                Toast.makeText(MainActivity.this, "사용자로 로그인되었습니다.", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_admin_login:
                startActivity(new Intent(MainActivity.this, AdminActivity.class));
                Toast.makeText(MainActivity.this, "관리자로 로그인되었습니다.", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void initView() {
        Button userLoginBtn = findViewById(R.id.btn_user_login);
        TextView adminLoginBtn = findViewById(R.id.btn_admin_login);

        userLoginBtn.setOnClickListener(this);
        adminLoginBtn.setOnClickListener(this);
    }
}