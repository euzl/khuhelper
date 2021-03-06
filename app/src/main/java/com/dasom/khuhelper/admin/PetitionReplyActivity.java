package com.dasom.khuhelper.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dasom.khuhelper.R;
import com.dasom.khuhelper.petition.Petition;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;

public class PetitionReplyActivity extends AppCompatActivity implements View.OnClickListener {

    Button replySubmitBtn;
    ImageView backBtn;
    TextView userTv;
    TextView placeTv;
    TextView titleTv;
    TextView contentTv;
    EditText replyEdt;

    Petition petition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_petitionreply);

        // 데이터 수신
        Intent intent = getIntent();
        petition = (Petition)intent.getSerializableExtra("petition");

        initView();
    }

    private void initView() {
        TextView petitionTv = findViewById(R.id.tv_petition);
        replyEdt = findViewById(R.id.edt_reply);

        if (petition.isCheck()) {
            petitionTv.setText("처리된 민원 확인");
            replyEdt.setVisibility(View.GONE);
            // TODO: 07/09/2020 응답 작성한 것도 표시되도록
        } else {
            petitionTv.setText("처리되지 않은 민원 확인");
            // 응답기능
            replyEdt.setImeOptions(EditorInfo.IME_ACTION_DONE);
            replyEdt.setOnEditorActionListener(new TextView.OnEditorActionListener()
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
        replySubmitBtn = findViewById(R.id.btn_reply_apply);
        backBtn = findViewById(R.id.btn_search_back);
        userTv = findViewById(R.id.tv_user);
        placeTv = findViewById(R.id.tv_place);
        titleTv = findViewById(R.id.tv_title);
        contentTv = findViewById(R.id.tv_content);

        replySubmitBtn.setText("민원확인");
        replySubmitBtn.setOnClickListener(this);
        backBtn.setOnClickListener(this);

        userTv.setText("작성자 : " + petition.getUsername() + " (" + petition.getUseremail() + ")");
        placeTv.setText("충전소 : " + petition.getCsName() + " (" + petition.getCsId() + ")");
        titleTv.setText("제목 : " + petition.getTitle());
        contentTv.setText("내용 : " + petition.getContent());
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btn_reply_apply:
                submitPetition();
                break;
            case R.id.btn_search_back:
                finish();
                break;
        }
    }

    private void submitPetition() {
        // TODO: 14/07/2020 DB에 관련 내용들이 저장되도록 구현
        petition.setCheck(true);
        petition.setReply(replyEdt.getText().toString());
        saveFirebase();

        Toast.makeText(this.getApplicationContext(),"민원이 처리되었습니다.", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void saveFirebase() {
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/petition/" + petition.getKey(), petition);
        database.updateChildren(childUpdates);
    }
}