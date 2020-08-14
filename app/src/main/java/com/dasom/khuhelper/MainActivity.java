package com.dasom.khuhelper;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.dasom.khuhelper.admin.AdminActivity;
import com.dasom.khuhelper.admin.OptimalPlaceActivity;
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
                break;
            case R.id.btn_admin_login:
                startActivity(new Intent(MainActivity.this, AdminActivity.class));
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