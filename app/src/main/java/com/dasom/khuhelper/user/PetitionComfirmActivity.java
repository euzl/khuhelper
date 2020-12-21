package com.dasom.khuhelper.user;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dasom.khuhelper.petition.Petition;
import com.dasom.khuhelper.R;
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

public class PetitionComfirmActivity extends AppCompatActivity implements View.OnClickListener {

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
        TextView titleTv = findViewById(R.id.tv_manage_title);
        titleTv.setText("민원확인");
        manageBackBtn = findViewById(R.id.btn_manage_back);
        manageNotiTv = findViewById(R.id.tv_manage_noti);
        recyclerView = findViewById(R.id.recyclerview_petitionlist);

        manageBackBtn.setOnClickListener(this);

        // data가 있을 때 / 없을 때
        if (openData()) {
            manageNotiTv.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            recyclerView.setOnClickListener(this);

            // LinearLayout 사용
            linearLayoutManager = new LinearLayoutManager(this);
            // adapter객체 생성
            petitionAdapter = new PetitionAdapter(this, petitionList);
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
//            case R.id.layout_petition_data:
//                AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.DialogStyle);
//                StringBuilder stringBuilder = new StringBuilder()
//                        .append("충전소 : " + petition.getCsName() + "\n충전소ID : " + petition.getCsId() + "\n\n"
//                                + "제목 : " + petition.getTitle() +"\n"
//                                + "내용 : " + petition.getContent());
//                if(petition.isCheck()) {
//                    builder.setTitle("처리된 민원입니다.");
//                    stringBuilder.append("\n\n답변 : " + petition.getReply());
//                } else {
//                    builder.setTitle("대기중인 민원입니다.");
//                }
//                builder.setMessage(stringBuilder)
//                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//                                // 아무 일도 일어나지 않음
//                            }
//                        })
//                        .setNegativeButton("삭제", new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//                                // 취소하면 남겨놓음
//                                if (removeData()) {
//                                    // 확인하면 리스트에서 지운다.
//                                    dataLayout.setVisibility(View.GONE);
//                                    manageNotiTv.setVisibility(View.VISIBLE);
//                                }
//                                Toast.makeText(PetitionComfirmActivity.this, "삭제되었습니다", Toast.LENGTH_SHORT).show();
//                            }
//                        });
//                builder.show();
//                break;
            // TODO: 07/09/2020 사용자용 액티비티에서 확인하고 삭제 가능하게 변경
        }
    }

    private boolean openData() {
        // TODO: 04/09/2020 petition 리스트 비동기로 불러오기
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference().child("petition");

        //Read from the database (message 아래의 child의 이벤트 수신)
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    petitionList.add(data.getValue(Petition.class));
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

    // 원래는 목록에서 지우는 기능 제공
    private boolean removeData() {
        // 지우기
        SharedPreferences sp = getSharedPreferences("shared", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove("petition").commit();
        return true;
    }
}
