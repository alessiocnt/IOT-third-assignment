package com.example.dammobileapp_dm;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;

import com.example.dammobileapp_dm.bgtask.BgTask;
import com.example.dammobileapp_dm.strategy.Strategy;
import com.example.dammobileapp_dm.strategy.StrategyImpl;
import com.example.dammobileapp_dm.utils.BluetoothChannel;
import com.example.dammobileapp_dm.utils.BluetoothDeviceNotFound;
import com.example.dammobileapp_dm.utils.BluetoothUtils;
import com.example.dammobileapp_dm.utils.RealBluetoothChannel;
import com.example.dammobileapp_dm.utils.ConnectToBluetoothServerTask;
import com.example.dammobileapp_dm.utils.ConnectionTask;
import com.example.dammobileapp_dm.utils.C;

import org.json.JSONException;
import org.w3c.dom.Text;

import java.util.Objects;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private TextView textState;
    private Switch switchManual;
    private TextView textLevel;
    private Button btnGapDecrease;
    private TextView textGap;
    private Button btnGapIncrease;
    private Activity myActivity;
    private Strategy strategy;
    private BgTask bgTask;

    private BluetoothChannel btChannel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.myActivity = this;
        initUI();
        setupBluetooth();
        setupWifi();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        btChannel.close();
    }

    private void initUI() {
        this.textState = findViewById(R.id.textState);
        this.switchManual = findViewById(R.id.switchManual);
        this.textLevel = findViewById(R.id.textLevel);
        this.btnGapDecrease = findViewById(R.id.btnGapDecrease);
        this.textGap = findViewById(R.id.textGap);
        this.btnGapIncrease = findViewById(R.id.btnGapIncrease);

        btnGapDecrease.setEnabled(false);
        btnGapIncrease.setEnabled(false);
        switchManual.setEnabled(false);

        btnGapDecrease.setOnClickListener(v -> {
            strategy.decreaseGap();
        });

        btnGapIncrease.setOnClickListener(v -> {
            strategy.increaseGap();
        });

        switchManual.setOnClickListener (v -> {
            strategy.changeMode();
        });
    }

    private void setupWifi() {
        final ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
         NetworkInfo activeNetwork;
        do {
            activeNetwork = Objects.requireNonNull(cm).getActiveNetworkInfo();
        } while (!activeNetwork.isConnectedOrConnecting());
    }

    private void setupBluetooth() {
        final BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();
        if(btAdapter != null && !btAdapter.isEnabled()){
            startActivityForResult(new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE), C.bluetooth.ENABLE_BT_REQUEST);
        }
        try {
            connectToBTServer();
        } catch (BluetoothDeviceNotFound bluetoothDeviceNotFound) {
            bluetoothDeviceNotFound.printStackTrace();
        }
    }

    private void connectToBTServer() throws BluetoothDeviceNotFound {
        final BluetoothDevice serverDevice = BluetoothUtils.getPairedDeviceByName(C.bluetooth.BT_DEVICE_ACTING_AS_SERVER_NAME);
        final UUID uuid = BluetoothUtils.getEmbeddedDeviceDefaultUuid();

        new ConnectToBluetoothServerTask(serverDevice, uuid, new ConnectionTask.EventListener() {
            @Override
            public void onConnectionActive(final BluetoothChannel channel) {
                btChannel = channel;
                strategy = new StrategyImpl(myActivity, btChannel, textState, switchManual, textLevel, btnGapDecrease, textGap, btnGapIncrease);
                bgTask = new BgTask(strategy, myActivity);
                bgTask.execute();
                btChannel.registerListener(new RealBluetoothChannel.Listener() {
                    @Override
                    public void onMessageReceived(String receivedMessage) {
                        Log.i("Ok", "New msg recived.");
                    }
                    @Override
                    public void onMessageSent(String sentMessage) {
                        Log.i("Ok", "New msg sent.");
                    }
                });
            }

            @Override
            public void onConnectionCanceled() {
                Log.i("Connection", "Connection terminated.");
            }
        }).execute();
    }
}