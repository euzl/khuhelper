package com.dasom.khuhelper.admin;

import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.dasom.khuhelper.R;

import androidx.appcompat.app.AppCompatActivity;

public class PredictActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView predictBackBtn;
    TextView predictNotiTv;
    TextView resultTv;
    TextView unitTv;
    EditText numberEdt;
    Button checkBtn;

    private int carNumber; // 자동차 대수
    private int usage; // 사용량

    boolean isResult = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_predict);

        initView();
    }

    private void initView() {
        predictBackBtn = findViewById(R.id.btn_predict_back);
        predictNotiTv = findViewById(R.id.tv_predict_noti);
        resultTv = findViewById(R.id.tv_result);
        unitTv = findViewById(R.id.tv_result_unit);
        numberEdt = findViewById(R.id.edt_number);
        checkBtn = findViewById(R.id.btn_check);

        unitTv.setVisibility(View.GONE);
        predictBackBtn.setOnClickListener(this);
        checkBtn.setOnClickListener(this);

        // 키보드 올리기
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_predict_back:
                finish();
                break;
            case R.id.btn_check:
                if(!isResult) {
                    showResult();
                } else {
                    finish();
                }
                break;
        }
    }

    /**
     * 동찬 결과 보여주는 함수
     */
    private void showResult() {
        // 키보드 내리기
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);

        numberEdt.setVisibility(View.GONE);

        StringBuilder notiMessage = new StringBuilder()
                .append("분석결과 자동차가 ")
                .append(carNumber)
                .append("대일 때\n예상 전기량은 다음과 같습니다.");
        predictNotiTv.setText(notiMessage);

        resultTv.setText(usage);
        unitTv.setVisibility(View.VISIBLE);

        isResult = true;
    }

    public void finish() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
        super.finish();
    }
}
