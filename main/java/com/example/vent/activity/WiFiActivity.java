package com.example.vent.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vent.R;
import com.example.vent.api.WifiApi;
import com.example.vent.client.RetrofitClient;
import com.example.vent.dto.WifiDto;
import com.pedro.library.AutoPermissions;
import com.pedro.library.AutoPermissionsListener;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class WiFiActivity extends AppCompatActivity implements AutoPermissionsListener {

    private static final String TAG = WiFiActivity.class.getSimpleName();

    private WifiManager wifiManager;
    private IntentFilter intentFilter;

    private RecyclerView rvWifiList;
    private Button btnWifiScan;
    private RecyclerView.Adapter mAdapter;

    private LinearLayout TextLayout;

    BroadcastReceiver wifiScanReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context c, Intent intent) {   // wifiManager.startScan(); 시  발동되는 메소드 ( 예제에서는 버튼을 누르면 startScan()을 했음. )

            boolean success = intent.getBooleanExtra(WifiManager.EXTRA_RESULTS_UPDATED, false); //스캔 성공 여부 값 반환
            if (success) {
                scanSuccess();
            } else {
                scanFailure();
            }
        }
    };

    // WiFi 검색 성공
    private void scanSuccess() {
        List<ScanResult> results = wifiManager.getScanResults();
        Log.d(TAG, "WiFi 검색 성공");
        mAdapter = new MyAdapter(results);
        rvWifiList.setAdapter(mAdapter);
    }

    // WiFi 검색 실패
    private void scanFailure() {
        Log.d(TAG, "WiFi 검색 실패");
//        Toast.makeText(getApplicationContext(), "WiFi 검색 실패", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wi_fi);

        initView();

        AutoPermissions.Companion.loadAllPermissions(this, 1);

        wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        intentFilter = new IntentFilter();
        intentFilter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
        intentFilter.addAction(ConnectivityManager.EXTRA_NO_CONNECTIVITY);
        intentFilter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);

        getApplicationContext().registerReceiver(wifiScanReceiver, intentFilter);

        TextLayout = findViewById(R.id.TextLayout);

        btnWifiScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getWiFiSSID().contains("ALFA")) {
                    boolean success = wifiManager.startScan();
                    TextLayout.setVisibility(View.GONE);
                    if (!success)
                        Log.d(TAG, "환기팬 검색에 실패하였습니다.");
//                        Toast.makeText(getApplicationContext(), "환기팬 검색에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "ALFA 환기장치를 먼저 연결해주세요.", Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
//                    startActivity(intent);
                }
            }
        });
    }

    private void initView() {
        rvWifiList = findViewById(R.id.rvWifiList);
        rvWifiList.setLayoutManager(new LinearLayoutManager(this));
        btnWifiScan = findViewById(R.id.btnWifiScan);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        AutoPermissions.Companion.parsePermissions(this, requestCode, permissions, this);
    }

    @Override
    public void onDenied(int i, String[] strings) {

    }

    @Override
    public void onGranted(int i, String[] strings) {

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private String getWiFiSSID() {
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        return wifiInfo.getSSID();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

        private List<ScanResult> items;
        private Context context;

        public MyAdapter(List<ScanResult> items) {
            this.items = items;
        }

        class MyViewHolder extends RecyclerView.ViewHolder {

            public TextView tvWifiName;

            public MyViewHolder(View itemView) {
                super(itemView);
                tvWifiName = itemView.findViewById(R.id.tvWifiName);
                tvWifiName.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Dialog dialog = new Dialog(context);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.enter_pw_dialog);

                        EditText message = dialog.findViewById(R.id.message);
                        TextView title = dialog.findViewById(R.id.title);
                        Button okButton = dialog.findViewById(R.id.okButton);
                        Button cancelButton = dialog.findViewById(R.id.cancelButton);

                        String ssid = tvWifiName.getText().toString();
                        title.setText(ssid);

                        dialog.show();

                        okButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                WifiDto wifiDto = new WifiDto(ssid, message.getText().toString());

                                WifiApi wifiApi = RetrofitClient.getNewInstance("http://192.168.4.100:80").create(WifiApi.class);

                                Call<String> call = wifiApi.sendWifiInfo(wifiDto);

                                call.enqueue(new Callback<String>() {
                                    @Override
                                    public void onResponse(Call<String> call, Response<String> response) {
                                        if (response.isSuccessful() && response.body() != null) {
                                            Toast.makeText(getApplicationContext(), "WIFI 설정 성공", Toast.LENGTH_SHORT).show();

                                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                            startActivity(intent);
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<String> call, Throwable t) {
                                        Log.d(TAG, t.getMessage());
                                        Toast.makeText(getApplicationContext(), "서버와의 연결에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                                    }
                                });
//                                WifiConfiguration wifiConfiguration = new WifiConfiguration();
//                                wifiConfiguration.SSID = String.format("\"%s\"", ssid);
//                                wifiConfiguration.preSharedKey = String.format("\"%s\"", message.getText().toString());
//                                wifiConfiguration.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
//                                wifiManager.disconnect();
//                                wifiManager.enableNetwork(wifiManager.addNetwork(wifiConfiguration), true);
//                                if (wifiManager.getConnectionInfo().getSSID().equals("\"" + title + "\"")) {
//                                    Toast.makeText(getApplicationContext(), "일치합니다.", Toast.LENGTH_SHORT).show();
//                                }
                                dialog.dismiss();
                            }
                        });

                        cancelButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                    }
                });
            }

            public void setItem(ScanResult item) {
                tvWifiName.setText(item.SSID);
            }
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            context = parent.getContext();
            View itemView = LayoutInflater.from(context).inflate(R.layout.recyclerview_item, parent, false);
            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            ScanResult scanResult = items.get(position);
            if (scanResult.frequency <= 3000) { // WIFI 2.4GHz
                holder.setItem(items.get(position));
            }
        }

        @Override
        public int getItemCount() {
            return items.size();
        }
    }
}