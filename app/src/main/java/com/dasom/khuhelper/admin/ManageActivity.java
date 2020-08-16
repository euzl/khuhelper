package com.dasom.khuhelper.admin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dasom.khuhelper.Petition;
import com.dasom.khuhelper.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class ManageActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView manageBackBtn;
    TextView manageNotiTv;

    Petition petition;

    ConstraintLayout dataLayout;
    TextView previewTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage);

        initView();
    }

    private void initView() {
        manageBackBtn = findViewById(R.id.btn_manage_back);
        manageNotiTv = findViewById(R.id.tv_manage_noti);
        dataLayout = findViewById(R.id.layout_petition_data);
        previewTv = findViewById(R.id.tv_petition_preview);

        manageBackBtn.setOnClickListener(this);

        // data가 있을 때(확인 안했을 경우만) / 없을 때
        if (openData()) {
            manageNotiTv.setVisibility(View.GONE);
            dataLayout.setVisibility(View.VISIBLE);
            previewTv.setText(petition.getUsername() + " : " + petition.getTitle() + "\n\n(" + petition.getCsName() + ")");
            dataLayout.setOnClickListener(this);

            if (petition.isCheck()) {
                previewTv.setTextColor(getApplicationContext().getResources().getColor(R.color.blue));
            }
        } else {
            dataLayout.setVisibility(View.GONE);
            manageNotiTv.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_manage_back:
                finish();
                break;
            case R.id.layout_petition_data:
                Intent intent = new Intent(ManageActivity.this, ReplyActivity.class);
                intent.putExtra("petition", petition);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setCheck();
    }

    private boolean openData() {
        SharedPreferences sp = getSharedPreferences("shared", MODE_PRIVATE);
        String strPetition = sp.getString("petition", "");
        if (strPetition == "") {
            return false;
        }
        Gson gson = new GsonBuilder().create();
        petition = gson.fromJson(strPetition, Petition.class);
        return true;
    }

    private void setCheck() {
        if (openData()) {
            if (petition.isCheck()) {
                previewTv.setTextColor(getApplicationContext().getResources().getColor(R.color.blue));
            }
        }
    }
}
