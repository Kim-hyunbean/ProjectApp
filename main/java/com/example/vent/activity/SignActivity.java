package com.example.vent.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.vent.R;
import com.example.vent.api.SignApi;
import com.example.vent.client.RetrofitClient;
import com.example.vent.dto.SignDto;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.SneakyThrows;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.HTTP;

public class SignActivity extends AppCompatActivity {

    private EditText signName, signID, signPW, signPw2 ,signAddress, signCallNum1,
            signCallNum2, signCallNum3,signCompany;
    private Button signbtn;
    private long mNow;
    private Date mDate;
    private SimpleDateFormat mFormat = new SimpleDateFormat("yyyyMMdd");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);

        signName = findViewById(R.id.signName);
        signID = findViewById(R.id.signID);
        signPW = findViewById(R.id.signPW);
        signPw2 = findViewById(R.id.signPW2);
        signbtn = findViewById(R.id.signbtn);
        signAddress = findViewById(R.id.signAddress);
        signCallNum1 = findViewById(R.id.signCallNum1);
        signCallNum2 = findViewById(R.id.signCallNum2);
        signCallNum3 = findViewById(R.id.signCallNum3);
        signCompany = findViewById(R.id.signCompany);

        signbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (signPW.getText().toString().equals(signPw2.getText().toString())) {

                    attemptJoin();
                }else{

                    signPW.setError("??????????????? ????????????");
                }
            }
        });
    }
    private void attemptJoin() {

        String ID = signID.getText().toString();
        String Password = signPW.getText().toString();
        String Name = signName.getText().toString();
        String Address = signAddress.getText().toString();
        String CallNum = signCallNum1.toString()+"-"+signCallNum2.toString()+"-"+signCallNum3.toString();
        String Company = signCompany.getText().toString();

        signID.setError(null);
        signPW.setError(null);
        signName.setError(null);
        signAddress.setError(null);
        signCallNum1.setError(null);
        signCompany.setError(null);

        boolean cancel = false;
        View focusView = null;

        // ????????? ????????? ??????
        if (ID.isEmpty()) {

            signID.setError("???????????? ??????????????????.");
            focusView = signID;
            cancel = true;
        }

        // ???????????? ????????? ??????
        if (Password.isEmpty()) {
            signPW.setError("??????????????? ??????????????????.");
            focusView = signPW;
            cancel = true;
        }

        // ?????? ????????? ??????
        if (Name.isEmpty()) {
            signName.setError("????????? ??????????????????.");
            focusView = signName;
            cancel = true;
        }

        // ?????? ????????? ??????
        if (Address.isEmpty()) {
            signAddress.setError("????????? ??????????????????.");
            focusView = signAddress;
            cancel = true;
        }

        // ???????????? ????????? ??????
        if (CallNum.length()<5) {
            signCallNum1.setError("??????????????? ??????????????????.");
            focusView = signCallNum1;
            cancel = true;
        }

        // ????????? ????????? ??????
        if (Company.isEmpty()) {
            signCompany.setError("???????????? ??????????????????.");
            focusView = signCompany;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            startJoin();
        }
    }

    @SneakyThrows
    private void startJoin() {



        String ID = (signID.getText().toString());
        String Password = signPW.getText().toString();
        String Name = signName.getText().toString();
        String Address = signAddress.getText().toString();
        String CallNum = signCallNum1.getText().toString()+"-"+signCallNum2.getText().toString()+"-"+signCallNum3.getText().toString();
        String Company = signCompany.getText().toString();
        String gubun = getTime();


        SignApi signApi = RetrofitClient.getInstance().create(SignApi.class);

        String contentText1 = java.net.URLEncoder.encode(new String(
                ID.getBytes("euc-kr")
        ));
        String contentText2 = java.net.URLEncoder.encode(new String(
                Password.getBytes("euc-kr")
        ));
        String contentText3 = java.net.URLEncoder.encode(new String(
                Name.getBytes("euc-kr")
        ));
        String contentText4 = java.net.URLEncoder.encode(new String(
                Address.getBytes("euc-kr")
        ));
        String contentText5 = java.net.URLEncoder.encode(new String(
                Company.getBytes("euc-kr")
        ));

//        "http://183.109.219.60/ALFASYSTEM/memb_post.aspx",
        Call<SignDto> call = signApi.userSign("http://183.109.219.60/ALFASYSTEM/memb_post.aspx",
                contentText1,contentText2,contentText3,"C","ON",contentText4,CallNum,contentText5,gubun);

//        Call<SignDto> call = signApi.userSign(
//                ID,Password,Name,"C","ON",Address,CallNum,Company,gubun);
        call.enqueue(new Callback<SignDto>() {
            @Override
            public void onResponse(Call<SignDto> call, Response<SignDto> response) {

                SignDto signDto = response.body();

                Toast.makeText(getApplicationContext(), "??????????????? ?????????????????????.", Toast.LENGTH_LONG).show();
                Intent intent = new Intent( getApplicationContext(), LoginActivity.class );
                startActivity( intent );
            }

            @Override
            public void onFailure(Call<SignDto> call, Throwable t) {
                Toast.makeText(SignActivity.this, "?????? ??????", Toast.LENGTH_SHORT).show();
                Log.e("???????????? ?????? ??????",t.getMessage());
                t.printStackTrace();
            }
        });
    }

    private String getTime(){
        mNow = System.currentTimeMillis();
        mDate = new Date(mNow);
        return mFormat.format(mDate);
    }
}

