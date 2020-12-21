package com.dasom.khuhelper.admin;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dasom.khuhelper.R;
import com.dasom.khuhelper.petition.Petition;
import com.dasom.khuhelper.petition.PetitionAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;
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

        linearLayoutManager = new LinearLayoutManager(this);
        petitionAdapter = new PetitionAdapter(this, petitionList);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // data가 있을 때(확인 안했을 경우만) / 없을 때
        if (openData()) {
            manageNotiTv.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            recyclerView.setOnClickListener(this);
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

    private boolean openData() {
        // TODO: 04/09/2020 petition 리스트 비동기로 불러오기
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference().child("petition");

        //Read from the database (message 아래의 child의 이벤트 수신)
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                petitionList.clear();

                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    Petition petition = data.getValue(Petition.class);
                    petition.setKey(data.getKey());
                    petitionList.add(petition);
                }

                // LinearLayout 사용
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setAdapter(petitionAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return true;
    }
}
