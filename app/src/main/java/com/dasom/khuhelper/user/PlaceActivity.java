package com.dasom.khuhelper.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.dasom.khuhelper.R;
import com.dasom.khuhelper.user.map.ChargingStation;

import androidx.appcompat.app.AppCompatActivity;

public class PlaceActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView searchBackBtn;
    TextView nameTv;
    TextView operTimeTv;
    TextView batteryTypeTv;
    TextView addressTv;
    TextView extraTv;
    Button petitionApplyBtn;

    ChargingStation chargingStation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);

        // 데이터 수신
        Intent intent = getIntent();
        chargingStation = (ChargingStation)intent.getSerializableExtra("chargingStation");

        initView();
    }

    private void initView() {
        searchBackBtn = findViewById(R.id.btn_search_back);
        nameTv = findViewById(R.id.tv_name);
        operTimeTv = findViewById(R.id.tv_oper_time);
        batteryTypeTv = findViewById(R.id.tv_battery_type);
        addressTv = findViewById(R.id.tv_address);
        extraTv = findViewById(R.id.tv_extra_info);
        petitionApplyBtn = findViewById(R.id.btn_petition_apply);

        searchBackBtn.setOnClickListener(this);
        petitionApplyBtn.setOnClickListener(this);

        setInformation();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btn_petition_apply:
                Intent intent = new Intent(PlaceActivity.this, PetitionApplyActivity.class);
                intent.putExtra("chargingStation", chargingStation);
                startActivity(intent);
                break;
            case R.id.btn_search_back:
                finish();
                break;
        }
    }

    private void setInformation() {
        nameTv.setText(chargingStation.getStatNm());
        operTimeTv.setText(chargingStation.getUseTime());
        switch (chargingStation.getChgerType()) {
            case 01:
                batteryTypeTv.setText("DC 차데모");
                break;
            case 03:
                batteryTypeTv.setText("DC 차데모 + AC 3상");
                break;
            case 06:
                batteryTypeTv.setText("DC 차데모 + AD 3상 + DC 콤보");
                break;
        }

        String stat = "충전기상태 : ";
        switch (chargingStation.getStat()) {
            case 1:
                stat += "통신이상";
                break;
            case 2:
                stat += "충전대기";
                break;
            case 3:
                stat += "충전중";
                break;
            case 4:
                stat += "운영중지";
                break;
            case 5:
                stat += "점검중";
                break;
        }
        extraTv.setText(stat);

        addressTv.setText(chargingStation.getAddrDoro());
    }
}
