package com.example.vent.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.graphics.fonts.Font;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vent.R;
import com.example.vent.Sensor;
import com.example.vent.api.CommandApi;
import com.example.vent.api.SensorApi;
import com.example.vent.client.RetrofitClient;
import com.example.vent.dto.SensorDto;
import com.example.vent.element.SensorRow;

import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private static final String ALFACMD_POWER_OFF = "ALFACMD1-0";
    private static final String ALFACMD_POWER_ON = "ALFACMD1-1";
    private static final String ALFACMD_FAN = "ALFACMD2-";
    private static final String ALFACMD_MANUAL = "ALFACMD3-0";
    private static final String ALFACMD_AUTO = "ALFACMD3-1";
    private static final String ALFACMD_DAMPER_CLOSE = "ALFACMD4-0";
    private static final String ALFACMD_DAMPER_OPEN = "ALFACMD4-1";
    private static int FanNumber = 0 ;

    private String uid;

    private TextView inTempVal;
    private TextView rhVal;
    private TextView dustVal;
    private TextView co2Val;
    private TextView vocVal;
    private TextView outTempVal;
    private TextView modeVal;
    private TextView fanVal;
    private TextView damperVal;
    private TextView fname;
    private Timer timer;


    private long backKeyPressedTime = 0;

    private String pid = "";

    private int standardSize_X, standardSize_Y;
    private float density;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        uid = getPreferenceString(LoginActivity.AUTO_LOGIN_ID);
    }

    private void updateSensorData() {

        Intent gintent = getIntent();

        try {
            int chnumber = gintent.getExtras().getInt("fan01");
            FanNumber = chnumber;
        }catch (Exception e) {
            FanNumber = 0;
        }


        SensorApi sensorApi = RetrofitClient.getInstance().create(SensorApi.class);

        Call<SensorDto> call = sensorApi.getSensor(uid);

        call.enqueue(new Callback<SensorDto>() {
            @Override
            public void onResponse(Call<SensorDto> call, Response<SensorDto> response) {
                if (response.isSuccessful() && response.body() != null) {
                    SensorDto sensorDto = response.body();
                    if (sensorDto.getRows() != null) {
                        Log.d(TAG, Arrays.toString(sensorDto.getRows().toArray(new SensorRow[FanNumber])));


                        pid = sensorDto.getRows().get(FanNumber).getPid();
                        Log.d(TAG, pid.toString());
                        String pdata = sensorDto.getRows().get(FanNumber).getPdata();
                        String airVolume = pdata.substring(4, 5);
                        String indoorTemp = pdata.substring(5, 8);
                        String humidity = pdata.substring(8, 11);
                        String outdoorTemp = pdata.substring(11, 14);
                        String dust = pdata.substring(14, 18);
                        String co2 = pdata.substring(18, 22);
                        String voc = pdata.substring(22, 25);
                        String mode = pdata.substring(25, 26);
                        String damper = pdata.substring(26, 27);
                        String fanStatus = pdata.substring(27, 28);
                        Log.d(TAG, pdata.toString());
                        String mname = "FAN"+pid.toString();
                        fname.setText(mname);

                        Sensor sensor = new Sensor(mode, indoorTemp, outdoorTemp, voc, co2, dust, humidity, airVolume, damper);
                        Log.d(TAG, sensor.toString());

                        inTempVal.setText(String.valueOf(Integer.parseInt(sensor.getIndoorTemp(), 16) / 10.0));
                        rhVal.setText(String.valueOf(Integer.parseInt(sensor.getHumidity(), 16) / 10.0));
                        dustVal.setText(String.valueOf(Integer.parseInt(sensor.getDust(), 16) / 10.0));
                        outTempVal.setText(String.valueOf(Integer.parseInt(sensor.getOutdoorTemp(), 16) / 10.0));
                        co2Val.setText(String.valueOf(Integer.parseInt(sensor.getCo2(), 16)));
                        vocVal.setText(String.valueOf(Integer.parseInt(sensor.getVoc(), 16)));
                        fanVal.setText(sensor.getAirVolume());

                        if (sensor.getMode().equals("0"))
                            modeVal.setText("??????");
                        else
                            modeVal.setText("??????");

                        if (sensor.getDamper().equals("0"))
                            damperVal.setText("CLOSE");
                        else
                            damperVal.setText("OPEN");

                    } else {
                        Log.d(TAG, "???????????? ???????????? ????????????.");
                    }
                }
            }

            @Override
            public void onFailure(Call<SensorDto> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "???????????? ????????? ?????????????????????.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                updateSensorData();
            }
        };

        // 1??? ????????? ?????? ????????? ????????????
        timer.schedule(timerTask, 0, 1000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        timer.cancel();
    }

    private void initView() {
        inTempVal = findViewById(R.id.inTempVal);
        rhVal = findViewById(R.id.rhVal);
        dustVal = findViewById(R.id.dustVal);
        co2Val = findViewById(R.id.co2Val);
        vocVal = findViewById(R.id.vocVal);
        outTempVal = findViewById(R.id.outTempVal);
        modeVal = findViewById(R.id.modeVal);
        fanVal = findViewById(R.id.fanVal);
        damperVal = findViewById(R.id.damperVal);
        fname = findViewById(R.id.fanId);
        Button btnPowerOn = findViewById(R.id.btnPowerOn);
        btnPowerOn.setOnClickListener(this);
        Button btnPowerOff = findViewById(R.id.btnPowerOff);
        btnPowerOff.setOnClickListener(this);
        Button btnDamperOpen = findViewById(R.id.btnDamperOpen);
        btnDamperOpen.setOnClickListener(this);
        Button btnDamperClose = findViewById(R.id.btnDamperClose);
        btnDamperClose.setOnClickListener(this);
        Button btnAuto = findViewById(R.id.btnAuto);
        btnAuto.setOnClickListener(this);
        Button btnManual = findViewById(R.id.btnManual);
        btnManual.setOnClickListener(this);
        Button btnFanUp = findViewById(R.id.btnFanUp);
        btnFanUp.setOnClickListener(this);
        Button btnFanDown = findViewById(R.id.btnFanDown);
        btnFanDown.setOnClickListener(this);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_option, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        TextView FanId = findViewById(R.id.fanId);

        switch (item.getItemId()) {
            case R.id.menu1:
                Intent wifiIntent = new Intent(getApplicationContext(), WiFiActivity.class);
                startActivity(wifiIntent);
                break;

            case  R.id.menu2:
                Intent changeIntent = new Intent(getApplicationContext(), ChangeActivity.class);
                startActivity(changeIntent);
                break;

            case R.id.menu3:
                removePreferenceString(LoginActivity.AUTO_LOGIN_ID);
                Intent loginIntent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(loginIntent);
                finish();
                break;



        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View view) {

        if (!pid.equals("")) {
            CommandApi commandApi = RetrofitClient.getInstance().create(CommandApi.class);
            Call<String> call;
            int airVolume = Integer.parseInt(fanVal.getText().toString());

            switch (view.getId()) {
                case R.id.btnPowerOn:

                    call = commandApi.command(pid, ALFACMD_POWER_ON);
                    call.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {

                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {

                        }
                    });
                    WaitDialog();

                    break;

                case R.id.btnPowerOff:

                    call = commandApi.command(pid, ALFACMD_POWER_OFF);
                    call.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {

                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {

                        }
                    });
                    WaitDialog();

                    break;

                case R.id.btnFanDown:

                    if (airVolume > 0) airVolume--;

                    call = commandApi.command(pid, ALFACMD_FAN + airVolume);
                    call.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {

                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {

                        }
                    });
                    WaitDialog();

                    break;


                case R.id.btnFanUp:

                    if (airVolume < 5) airVolume++;

                    call = commandApi.command(pid, ALFACMD_FAN + airVolume);
                    call.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {

                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {

                        }
                    });
                    WaitDialog();

                    break;

                case R.id.btnAuto:

                    call = commandApi.command(pid, ALFACMD_AUTO);
                    call.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {

                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {

                        }
                    });
                    WaitDialog();

                    break;

                case R.id.btnManual:

                    call = commandApi.command(pid, ALFACMD_MANUAL);
                    call.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {

                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {

                        }
                    });
                    WaitDialog();

                    break;

                case R.id.btnDamperClose:

                    call = commandApi.command(pid, ALFACMD_DAMPER_CLOSE);
                    call.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {

                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {

                        }
                    });
                    WaitDialog();

                    break;

                case R.id.btnDamperOpen:

                    call = commandApi.command(pid, ALFACMD_DAMPER_OPEN);
                    call.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {

                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {

                        }
                    });
                    WaitDialog();

                    break;
            }
        }
    }

    @Override
    public void onBackPressed() {
        // ?????? ???????????? ????????? ????????? ???????????? ???????????? ?????? ??????
        // super.onBackPressed();

        // ??????????????? ???????????? ????????? ????????? ????????? 2?????? ?????? ??????????????? ?????? ???
        // ??????????????? ???????????? ????????? ????????? ????????? 2?????? ???????????? Toast Show
        // 2000 milliseconds = 2 seconds
        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis();
            Toast.makeText(getApplicationContext(), "\'??????\' ????????? ?????? ??? ???????????? ???????????????.", Toast.LENGTH_SHORT).show();
            return;
        }
        // ??????????????? ???????????? ????????? ????????? ????????? 2?????? ?????? ??????????????? ?????? ???
        // ??????????????? ???????????? ????????? ????????? ????????? 2?????? ????????? ???????????? ??????
        // ?????? ????????? Toast ??????
        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            moveTaskToBack(true); // ???????????? ?????????????????? ??????
            finishAndRemoveTask(); // ???????????? ?????? + ????????? ??????????????? ?????????
            android.os.Process.killProcess(android.os.Process.myPid()); // ??? ???????????? ??????
        }

    }


    // ?????? ???????????? ????????? ????????? ????????????
    public String getPreferenceString(String key) {
        SharedPreferences pref = getSharedPreferences(LoginActivity.PREF, MODE_PRIVATE);
        return pref.getString(key, "");
    }

    // ?????? ???????????? ????????? ????????? ????????????
    public void removePreferenceString(String key) {
        SharedPreferences pref = getSharedPreferences(LoginActivity.PREF, MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.remove(key);
        editor.apply();
    }

    private void WaitDialog() {
        showDialog(1); // ???????????? ??????

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                // 5?????? ????????? ??????????????? ??????
                TimerTask task = new TimerTask(){
                    @Override
                    public void run() {
                        removeDialog(1);

                    }
                };

                Timer timer = new Timer();
                timer.schedule(task, 5000);
            }
        });
        thread.start();

    }


    @Override
    protected Dialog onCreateDialog(int id) {

        ProgressDialog dialog = new ProgressDialog(this); // ??????????????? ????????? ????????????
        dialog.setMessage("????????? ??????????????????.");
        dialog.setButton(ProgressDialog.BUTTON_NEGATIVE, "??????",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }
        );

        return dialog;
    }

}