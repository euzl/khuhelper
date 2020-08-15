package com.dasom.khuhelper.user;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dasom.khuhelper.Petition;
import com.dasom.khuhelper.R;
import com.dasom.khuhelper.user.map.ChargingStation;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import androidx.appcompat.app.AppCompatActivity;

public class PetitionActivity extends AppCompatActivity implements View.OnClickListener {

    TextView nameTv;
    Button petitionSubmitBtn;
    ImageView petitionBackBtn;
    EditText usernameEdt;
    EditText useremailEdt;
    EditText titleEdt;
    EditText contentEdt;

    ChargingStation chargingStation;
    Petition petition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_petition);

        // 데이터 수신
        Intent intent = getIntent();
        chargingStation = (ChargingStation)intent.getSerializableExtra("chargingStation");

        initView();
    }

    private void initView() {
        nameTv = findViewById(R.id.tv_petition_name);
        usernameEdt = findViewById(R.id.edt_petition_user_name);
        useremailEdt = findViewById(R.id.edt_petition_user_email);
        titleEdt = findViewById(R.id.edt_petition_title);
        contentEdt = findViewById(R.id.edt_petition_content);

        petitionSubmitBtn = findViewById(R.id.btn_petition_submit);
        petitionBackBtn = findViewById(R.id.btn_petition_back);
        petitionSubmitBtn.setOnClickListener(this);
        petitionBackBtn.setOnClickListener(this);

        nameTv.setText(chargingStation.getStatNm());

        contentEdt.setImeOptions(EditorInfo.IME_ACTION_DONE);
        contentEdt.setOnEditorActionListener(new TextView.OnEditorActionListener()
        {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
            {
                if(actionId == EditorInfo.IME_ACTION_DONE)
                {
                    submitPetition();
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btn_petition_submit:
                submitPetition();
                break;
            case R.id.btn_petition_back:
                finish();
                break;
        }
    }

    private void submitPetition() {
        // TODO: 14/07/2020 DB에 관련 내용들이 저장되도록 구현
        petition = new Petition(usernameEdt.getText().toString(), useremailEdt.getText().toString(),
                titleEdt.getText().toString(), contentEdt.getText().toString(),
                chargingStation.getStatId(), chargingStation.getStatNm());
        saveData();

        Toast.makeText(this.getApplicationContext(),R.string.cp_submit_success, Toast.LENGTH_SHORT).show();
        finish();
    }

    private void saveData() {
        // Gson 인스턴스 생성
        Gson gson = new GsonBuilder().create();
        // JSON 으로 변환
        String strPetition = gson.toJson(petition, Petition.class);

        // sharedpreference에 저장
        SharedPreferences sp = getSharedPreferences("shared", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("petition", strPetition);
        editor.commit();
    }
}
