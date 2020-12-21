package com.dasom.khuhelper.user;

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

import com.dasom.khuhelper.petition.Petition;
import com.dasom.khuhelper.R;
import com.dasom.khuhelper.user.map.ChargingStation;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.appcompat.app.AppCompatActivity;

public class PetitionApplyActivity extends AppCompatActivity implements View.OnClickListener {

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
        setContentView(R.layout.activity_petitionapply);

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
        // TODO: 04/09/2020 테스트끝나면 기존 saveData() 삭제 
        petition = new Petition(usernameEdt.getText().toString(), useremailEdt.getText().toString(),
                titleEdt.getText().toString(), contentEdt.getText().toString(),
                chargingStation.getStatId(), chargingStation.getStatNm());
        saveFirebase();

        Toast.makeText(this.getApplicationContext(),R.string.cp_submit_success, Toast.LENGTH_SHORT).show();
        finish();
    }

    private void saveFirebase() {
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        database.child("petition").push().setValue(petition);
    }
}
