package com.example.vent.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.vent.PreferenceManager.PreferenceManager;
import com.example.vent.R;
import com.example.vent.api.LoginApi;
import com.example.vent.client.RetrofitClient;
import com.example.vent.dto.LoginDto;

import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = LoginActivity.class.getSimpleName();

    private EditText editId;
    private EditText editPw;
    private Button btnLogin;
    private Button btnSignup;
    private Context mContext;


    public static final String AUTO_LOGIN_ID = "autoLoginId";
    public static final String PREF = "pref";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mContext = this;



        initView();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String id = editId.getText().toString();
                String password = editPw.getText().toString();


                if ((id.length() == 0) || (password.length() == 0)) {
                    Toast.makeText(getApplicationContext(), "모든 정보를 입력해주세요.", Toast.LENGTH_SHORT).show();
                } else {

                    LoginApi loginApi = RetrofitClient.getInstance().create(LoginApi.class);

                    Call<LoginDto> call = loginApi.login(id, password);

                    call.enqueue(new Callback<LoginDto>() {
                        @Override
                        public void onResponse(Call<LoginDto> call, Response<LoginDto> response) {
                            if (response.isSuccessful() && response.body() != null) {
                                LoginDto loginDto = response.body();
                                if (loginDto.getRows() != null) {
                                    Log.d(TAG, Arrays.toString(loginDto.getRows().toArray()));
                                    Toast.makeText(getApplicationContext(), "로그인 성공", Toast.LENGTH_SHORT).show();
                                    setPreference(AUTO_LOGIN_ID, id);
                                    PreferenceManager.setString(mContext, "id", editId.getText().toString()); //id라는 키값으로 저장
                                    PreferenceManager.setString(mContext, "pw", editPw.getText().toString()); //pw라는 키값으로 저장
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(intent);
                                } else {
                                    Log.d(TAG, "존재하지 않는 회원입니다.");
                                    Toast.makeText(getApplicationContext(), "존재하지 않는 회원입니다.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<LoginDto> call, Throwable t) {
                            Log.d(TAG, "서버와의 연결에 실패하였습니다.");
                            Toast.makeText(getApplicationContext(), "서버와의 연결에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( LoginActivity.this, SignActivity.class );
                startActivity( intent );
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        // 자동 로그인
        if(!getPreferenceString(AUTO_LOGIN_ID).equals("")) {
            editId.setText(PreferenceManager.getString(mContext, "id"));
            editPw.setText(PreferenceManager.getString(mContext, "pw"));
        }
    }

    private void initView() {
        editId = findViewById(R.id.editId);
        editPw = findViewById(R.id.editPw);
        btnLogin = findViewById(R.id.btnLogin);
        btnSignup = findViewById(R.id.btnSign_Up);
    }

    //데이터를 내부 저장소에 저장하기
    public void setPreference(String key, String value) {
        SharedPreferences pref = getSharedPreferences(PREF, MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(key, value);
        editor.apply();
    }

    //내부 저장소에 저장된 데이터 가져오기
    public String getPreferenceString(String key) {
        SharedPreferences pref = getSharedPreferences(PREF, MODE_PRIVATE);
        return pref.getString(key, "");
    }

}