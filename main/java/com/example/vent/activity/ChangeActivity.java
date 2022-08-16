package com.example.vent.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.vent.R;
import com.example.vent.Sensor;
import com.example.vent.api.SensorApi;
import com.example.vent.client.RetrofitClient;
import com.example.vent.dto.SensorDto;
import com.example.vent.element.SensorRow;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangeActivity extends AppCompatActivity {

    private static final String TAG = ChangeActivity.class.getSimpleName();

    private String pid = "";
    private static int FanNumber = 0 ;
    private String uid;
    private String pcid = "test";
    private static ArrayList<String> list1 = new ArrayList<String>();


    private Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change);

        initView();
        uid = getPreferenceString(LoginActivity.AUTO_LOGIN_ID);


        updateSensorData();

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent01 = new Intent( ChangeActivity.this, MainActivity.class );
                intent01.putExtra("fan01", 0);
                startActivity( intent01 );
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent01 = new Intent( ChangeActivity.this, MainActivity.class );
                intent01.putExtra("fan01", 1);
                startActivity( intent01 );
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent01 = new Intent( ChangeActivity.this, MainActivity.class );
                intent01.putExtra("fan01", 2);
                startActivity( intent01 );
            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent01 = new Intent( ChangeActivity.this, MainActivity.class );
                intent01.putExtra("fan01", 3);
                startActivity( intent01 );
            }
        });
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent01 = new Intent( ChangeActivity.this, MainActivity.class );
                intent01.putExtra("fan01", 4);
                startActivity( intent01 );
            }
        });
        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent01 = new Intent( ChangeActivity.this, MainActivity.class );
                intent01.putExtra("fan01", 5);
                startActivity( intent01 );
            }
        });
        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent01 = new Intent( ChangeActivity.this, MainActivity.class );
                intent01.putExtra("fan01", 6);
                startActivity( intent01 );
            }
        });
        btn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent01 = new Intent( ChangeActivity.this, MainActivity.class );
                intent01.putExtra("fan01", 7);
                startActivity( intent01 );
            }
        });
        btn9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent01 = new Intent( ChangeActivity.this, MainActivity.class );
                intent01.putExtra("fan01", 8);
                startActivity( intent01 );
            }
        });
        btn10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent01 = new Intent( ChangeActivity.this, MainActivity.class );
                intent01.putExtra("fan01", 9);
                startActivity( intent01 );
            }
        });
    }

    private void updateSensorData() {

        SensorApi sensorApi = RetrofitClient.getInstance().create(SensorApi.class);

        Call<SensorDto> call = sensorApi.getSensor(uid);

        call.enqueue(new Callback<SensorDto>() {
            @Override
            public void onResponse(Call<SensorDto> call, Response<SensorDto> response) {
                if (response.isSuccessful() && response.body() != null) {
                    SensorDto sensorDto = response.body();
                    if (sensorDto.getRows() != null) {

                        try {
                            pid = sensorDto.getRows().get(0).getPid();
                            pcid = sensorDto.getRows().get(0).getUser_id();
                            btn1.setText("FAN" + pid);
                        } catch (Exception e) {
                            pid = sensorDto.getRows().get(FanNumber).getPid();
                            pcid = sensorDto.getRows().get(FanNumber).getUser_id();
                            btn1.setVisibility(View.GONE);
                        }
                        try {
                            pid = sensorDto.getRows().get(1).getPid();
                            pcid = sensorDto.getRows().get(1).getUser_id();
                            btn2.setText("FAN" + pid);
                        } catch (Exception e) {
                            pid = sensorDto.getRows().get(FanNumber).getPid();
                            pcid = sensorDto.getRows().get(FanNumber).getUser_id();
                            btn2.setVisibility(View.GONE);
                        }
                        try {
                            pid = sensorDto.getRows().get(2).getPid();
                            pcid = sensorDto.getRows().get(2).getUser_id();
                            btn3.setText("FAN" + pid);
                        } catch (Exception e) {
                            pid = sensorDto.getRows().get(FanNumber).getPid();
                            pcid = sensorDto.getRows().get(FanNumber).getUser_id();
                            btn3.setVisibility(View.GONE);
                        }
                        try {
                            pid = sensorDto.getRows().get(3).getPid();
                            pcid = sensorDto.getRows().get(3).getUser_id();
                            btn4.setText("FAN" + pid);
                        } catch (Exception e) {
                            pid = sensorDto.getRows().get(FanNumber).getPid();
                            pcid = sensorDto.getRows().get(FanNumber).getUser_id();
                            btn4.setVisibility(View.GONE);
                        }
                        try {
                            pid = sensorDto.getRows().get(4).getPid();
                            pcid = sensorDto.getRows().get(4).getUser_id();
                            btn5.setText("FAN" + pid);
                        } catch (Exception e) {
                            pid = sensorDto.getRows().get(FanNumber).getPid();
                            pcid = sensorDto.getRows().get(FanNumber).getUser_id();
                            btn5.setVisibility(View.GONE);
                        }
                        try {
                            pid = sensorDto.getRows().get(5).getPid();
                            pcid = sensorDto.getRows().get(5).getUser_id();
                            btn6.setText("FAN" + pid);
                        } catch (Exception e) {
                            pid = sensorDto.getRows().get(FanNumber).getPid();
                            pcid = sensorDto.getRows().get(FanNumber).getUser_id();
                            btn6.setVisibility(View.GONE);
                        }
                        try {
                            pid = sensorDto.getRows().get(6).getPid();
                            pcid = sensorDto.getRows().get(6).getUser_id();
                            btn7.setText("FAN" + pid);
                        } catch (Exception e) {
                            pid = sensorDto.getRows().get(FanNumber).getPid();
                            pcid = sensorDto.getRows().get(FanNumber).getUser_id();
                            btn7.setVisibility(View.GONE);
                        }
                        try {
                            pid = sensorDto.getRows().get(7).getPid();
                            pcid = sensorDto.getRows().get(7).getUser_id();
                            btn8.setText("FAN" + pid);
                        } catch (Exception e) {
                            pid = sensorDto.getRows().get(FanNumber).getPid();
                            pcid = sensorDto.getRows().get(FanNumber).getUser_id();
                            btn8.setVisibility(View.GONE);
                        }try {
                            pid = sensorDto.getRows().get(8).getPid();
                            pcid = sensorDto.getRows().get(8).getUser_id();
                            btn9.setText("FAN" + pid);
                        } catch (Exception e) {
                            pid = sensorDto.getRows().get(FanNumber).getPid();
                            pcid = sensorDto.getRows().get(FanNumber).getUser_id();
                            btn9.setVisibility(View.GONE);
                        }
                        try {
                            pid = sensorDto.getRows().get(9).getPid();
                            pcid = sensorDto.getRows().get(9).getUser_id();
                            btn10.setText("FAN" + pid);
                        } catch (Exception e) {
                            pid = sensorDto.getRows().get(FanNumber).getPid();
                            pcid = sensorDto.getRows().get(FanNumber).getUser_id();
                            btn10.setVisibility(View.GONE);
                        }




                }
            }

        }

            @Override
            public void onFailure(Call<SensorDto> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "서버와의 연결에 실패하였습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initView() {
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btn4 = findViewById(R.id.btn4);
        btn5 = findViewById(R.id.btn5);
        btn6 = findViewById(R.id.btn6);
        btn7 = findViewById(R.id.btn7);
        btn8 = findViewById(R.id.btn8);
        btn9 = findViewById(R.id.btn9);
        btn10 = findViewById(R.id.btn10);


    }

    // 내부 저장소에 저장된 데이터 가져오기
    public String getPreferenceString(String key) {
        SharedPreferences pref = getSharedPreferences(LoginActivity.PREF, MODE_PRIVATE);
        return pref.getString(key, "");
    }

    // 내부 저장소에 저장된 데이터 삭제하기
    public void removePreferenceString(String key) {
        SharedPreferences pref = getSharedPreferences(LoginActivity.PREF, MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.remove(key);
        editor.apply();
    }

    //                                if (btn1.toString() == "") {
//                                    btn1.setText(pid.toString());
//                                }else if (btn2.toString() == "") {
//                                    btn2.setText(pid.toString());
//                                }else if (btn3.toString() == "") {
//                                    btn3.setText(pid.toString());
//                                }else if (btn4.toString() == "") {
//                                    btn4.setText(pid.toString());
//                                }else if (btn5.toString() == "") {
//                                    btn5.setText(pid.toString());
//                                }else if (btn6.toString() == "") {
//                                    btn6.setText(pid.toString());
//                                }else if (btn7.toString() == "") {
//                                    btn7.setText(pid.toString());
//                                }else if (btn8.toString() == "") {
//                                    btn8.setText(pid.toString());
//                                }else if (btn9.toString() == "") {
//                                    btn9.setText(pid.toString());
//                                }else if (btn10.toString() == "") {
//                                    btn10.setText(pid.toString());
//                                }
}
