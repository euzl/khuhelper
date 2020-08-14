package com.dasom.khuhelper.user;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.dasom.khuhelper.R;

import androidx.appcompat.app.AppCompatActivity;

public class PetitionActivity extends AppCompatActivity implements View.OnClickListener {

    Button petitionSubmitBtn;
    ImageView petitionBackBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_petition);

        initView();
    }

    private void initView() {
        petitionSubmitBtn = findViewById(R.id.btn_petition_submit);
        petitionBackBtn = findViewById(R.id.btn_petition_back);
        petitionSubmitBtn.setOnClickListener(this);
        petitionBackBtn.setOnClickListener(this);

        // TODO: 14/07/2020 충전소 이름이 표시되도록 
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btn_petition_submit:
                // TODO: 14/07/2020 DB에 관련 내용들이 저장되도록 구현
                Toast.makeText(this.getApplicationContext(),R.string.cp_submit_success, Toast.LENGTH_SHORT).show();
                finish();
                break;
            case R.id.btn_petition_back:
                finish();
                break;
        }
    }
}
