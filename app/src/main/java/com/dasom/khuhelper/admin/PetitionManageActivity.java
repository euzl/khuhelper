package com.dasom.khuhelper.admin;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dasom.khuhelper.R;
import com.dasom.khuhelper.petition.Petition;
import com.dasom.khuhelper.petition.PetitionAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class PetitionManageActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView manageBackBtn;
    TextView manageNotiTv;

    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    PetitionAdapter petitionAdapter;

    ArrayList<Petition> petitionList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_petitionlist);

        initView();
    }

    private void initView() {
        manageBackBtn = findViewById(R.id.btn_manage_back);
        manageNotiTv = findViewById(R.id.tv_manage_noti);
        recyclerView = findViewById(R.id.recyclerview_petitionlist);

        manageBackBtn.setOnClickListener(this);

        // TODO: 04/09/2020 비동기로 리스트 다 가져온 다음에 실행하기
        // data가 있을 때(확인 안했을 경우만) / 없을 때
        if (openData()) {
            manageNotiTv.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            recyclerView.setOnClickListener(this);

            // LinearLayout 사용
            linearLayoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(linearLayoutManager);

            // adapter객체 생성해서 리사이클러뷰에 적용
            petitionAdapter = new PetitionAdapter(this, petitionList);
            recyclerView.setAdapter(petitionAdapter);


//            // 체크했으면 글씨 파란색으로 바꾸는 부분
//            if (petition.isCheck()) {
//                previewTv.setTextColor(getApplicationContext().getResources().getColor(R.color.blue));
//            }
        } else {
            recyclerView.setVisibility(View.GONE);
            manageNotiTv.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_manage_back:
                finish();
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
//        setCheck();
    }

    private boolean openData() {
        // TODO: 04/09/2020 petition 리스트 비동기로 불러오기
        SharedPreferences sp = getSharedPreferences("shared", MODE_PRIVATE);
        String strPetition = sp.getString("petition", "");
        if (strPetition == "") {
            return false;
        }
        Gson gson = new GsonBuilder().create();
        Petition petition = gson.fromJson(strPetition, Petition.class);
        petitionList.add(petition); // 일단 얘라도 넣자
        petitionList.add(new Petition("dd","d","d","d","d","d"));
        return true;
    }

    // 확인했으면 색 변하는 것
//    private void setCheck() {
//        if (openData()) {
//            if (petition.isCheck()) {
//                previewTv.setTextColor(getApplicationContext().getResources().getColor(R.color.blue));
//            }
//        }
//    }

}
