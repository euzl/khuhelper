package com.dasom.khuhelper.admin;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dasom.khuhelper.Petition;
import com.dasom.khuhelper.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import androidx.appcompat.app.AlertDialog;
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
        if (openData() || petition.isCheck() == false) {
            manageNotiTv.setVisibility(View.GONE);
            dataLayout.setVisibility(View.VISIBLE);
            previewTv.setText(petition.getUsername() + " : " + petition.getTitle() + "\n\n(" + petition.getCsName() + ")");
            dataLayout.setOnClickListener(this);
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
                AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.DialogStyle);
                builder.setMessage("이름 : " + petition.getUsername() + " (" + petition.getUseremail() + ")\n"
                        + "충전소 : " + petition.getCsName() + "\n충전소ID : " + petition.getCsId() + "\n\n"
                        + "제목 : " + petition.getTitle() +"\n"
                        + "내용 : " + petition.getContent())
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                if (removeData()) {
                                    // 확인하면 리스트에서 지운다.
                                    dataLayout.setVisibility(View.GONE);
                                    manageNotiTv.setVisibility(View.VISIBLE);
                                }
                                Toast.makeText(ManageActivity.this, "민원이 처리되었습니다.", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("다음에 확인", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // 취소하면 남겨놓음
                            }
                        });
                builder.show();
                break;
        }
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

    private boolean removeData() {
        // 지우기
//        SharedPreferences sp = getSharedPreferences("shared", MODE_PRIVATE);
//        SharedPreferences.Editor editor = sp.edit();
//        editor.remove("petition").commit();

        petition.setCheck(true);
        saveData();
        return true;
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
