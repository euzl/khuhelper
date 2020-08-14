package com.dasom.khuhelper.admin;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.dasom.khuhelper.R;

import androidx.appcompat.app.AppCompatActivity;

public class PredictActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView predictBackBtn;
    TextView predictNotiTv;
    EditText numberEdt;
    Button checkBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_predict);

        initView();
    }

    private void initView() {
        predictBackBtn = findViewById(R.id.btn_predict_back);
        predictNotiTv = findViewById(R.id.tv_predict_noti);
        numberEdt = findViewById(R.id.edt_number);
        checkBtn = findViewById(R.id.btn_check);

        predictBackBtn.setOnClickListener(this);
        checkBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_predict_back:
                finish();
                break;
            case R.id.btn_check:
                // 어쩔까
                break;
        }
    }
}
