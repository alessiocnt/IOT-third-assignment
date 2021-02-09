package com.example.dammobileapp_dm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import org.json.JSONException;
import org.w3c.dom.Text;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private TextView textState;
    private Switch switchManual;
    private TextView textLevel;
    private Button btnGapDecrease;
    private TextView textGap;
    private Button btnGapIncrease;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupBluetooth();
        setupWifi();
        initUI();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private void initUI() {
        this.textState = findViewById(R.id.textState);
        this.switchManual = findViewById(R.id.switchManual);
        this.textLevel = findViewById(R.id.textLevel);
        this.btnGapDecrease = findViewById(R.id.btnGapDecrease);
        this.textGap = findViewById(R.id.textGap);
        this.btnGapIncrease = findViewById(R.id.btnGapIncrease);
    }

    private void setupBluetooth() {

    }

    private void setupWifi() {

    }
}