package com.dasom.khuhelper.admin;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.dasom.khuhelper.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import androidx.appcompat.app.AppCompatActivity;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PredictActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView predictBackBtn;
    TextView predictNotiTv;
    TextView resultTv;
    TextView unitTv;
    EditText numberEdt;
    Button checkBtn;

    String jsonResult;
    int usage;
    private int carNumber; // 자동차 대수

    boolean isResult = false;

    InputMethodManager imm;

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

        numberEdt.requestFocus();

        unitTv.setVisibility(View.GONE);
        predictBackBtn.setOnClickListener(this);
        checkBtn.setOnClickListener(this);

        // 키보드 올리기
        imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
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
                    carNumber = Integer.parseInt(numberEdt.getText().toString());
//                    getResponse();
                    showResult(usage);
                } else {
                    finish();
                }
                break;
        }
    }

    private void setJsonResult(String jsonResult) {
        usage = Integer.parseInt(jsonResult);
    }

    /**
     * 동찬 결과 보여주는 함수
     */
    private void showResult(int result) {
        // 키보드 내리기
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);

        numberEdt.setVisibility(View.GONE);

        StringBuilder notiMessage = new StringBuilder()
                .append("분석결과 자동차가 ")
                .append(carNumber)
                .append(" 대일 때\n예상 전기량은 다음과 같습니다.");
        predictNotiTv.setText(notiMessage);

        int a = 0;
        if (carNumber >1000) a = 2495;
        else a = 734;
        resultTv.setText(a + ""); // 임시
        unitTv.setVisibility(View.VISIBLE);

        isResult = true;
    }

    private void getResponse() {
        jsonResult = null;

        new Thread() {
            public void run() {
                OkHttpClient client = new OkHttpClient().newBuilder()
                        .build();
                MediaType mediaType = MediaType.parse("application/json");
                RequestBody body = RequestBody.create(mediaType, "{\n    \"input\": \""+carNumber +"\"\n}");

                Request request = new Request.Builder()
                        .url("http://36bf91c03391.ngrok.io/api/responses")
                        .method("POST", body)
                        .addHeader("Content-Type", "application/json")
                        .build();
                try {
                    Response response = client.newCall(request).execute();


                    final JSONObject jsonObject  = new JSONObject(response.body().string());
                    Log.d("please:", jsonObject + "");

                    jsonResult = jsonObject.getString("predicted_value");
                    Log.d("json result", jsonResult + "");

                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }
        }.start();

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                setJsonResult(jsonResult);
            }
        });
    }

    public void finish() {
        if (!isResult) {
            imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
        }
        super.finish();
    }
}