package com.dasom.khuhelper;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

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
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
                break;
            case R.id.btn_admin_login:
                // TODO: 09/07/2020 admin login 구현하고 연결
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
