package com.example.dammobileapp_dm.strategy;

import android.app.Activity;
import android.util.Log;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.dammobileapp_dm.utils.BluetoothChannel;

import org.apache.commons.lang3.StringUtils;

public class StrategyImpl implements Strategy {

    private final Activity activity;
    private final BluetoothChannel btChannel;
    private final TextView textState;
    private final Switch switchManual;
    private final TextView textLevel;
    private final Button btnGapDecrease;
    private final TextView textGap;
    private final Button btnGapIncrease;

    private final RequestQueue queue;
    private final String url = "http://192.168.1.48:8000";

    private String mode = "auto";
    private String state = "normal";
    private String waterLevel = "300";
    private String gap = "0";

    public StrategyImpl(final Activity activity, final BluetoothChannel btChannel, final TextView textState, final Switch switchManual, final TextView textLevel, final Button btnGapDecrease, final TextView textGap, final Button btnGapIncrease){
        this.activity = activity;
        this.btChannel = btChannel;
        this.textState = textState;
        this.switchManual = switchManual;
        this.textLevel = textLevel;
        this.btnGapDecrease = btnGapDecrease;
        this.textGap = textGap;
        this.btnGapIncrease = btnGapIncrease;
        this.queue = Volley.newRequestQueue(activity);
    }

    @Override
    public void setGap(String gap) {
        this.gap = gap;
        if (mode.equals("auto")) {
            activity.runOnUiThread(() -> textGap.setText("Dam gap: " + gap));
        }
    }

    @Override
    public void setWaterLevel(String waterLevel) {
        this.waterLevel = waterLevel;
        activity.runOnUiThread(() -> textLevel.setText("Water level: " + waterLevel));
    }

    @Override
    public void setState(String state) {
        this.state = state;
        activity.runOnUiThread(() -> textState.setText("State: " + StringUtils.capitalize(state)));
        if (state.equals("alarm")) {
            activity.runOnUiThread(() -> switchManual.setEnabled(true));
        } else {
            activity.runOnUiThread(() -> switchManual.setEnabled(false));
        }
    }

    @Override
    public void setMode(String mode) {
        if (this.mode.equals(mode)){
            return;
        }
        this.mode = mode;
        if (mode.equals("auto")) {
            activity.runOnUiThread(() -> {
                //switchManual.setChecked(false);
                //switchManual.setEnabled(false);

                btnGapDecrease.setEnabled(false);
                btnGapIncrease.setEnabled(false);
            });
        } else {
            activity.runOnUiThread(() -> {
                //switchManual.setChecked(true);

                switchManual.setEnabled(true);
                btnGapDecrease.setEnabled(true);
                btnGapIncrease.setEnabled(true);
            });
        }
    }

    private void sendMode() {
        // send via http to backend
        String url = this.url + "/setvalues?mode=" + mode;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> Log.i("Response", response), error -> {
            Log.e("Error", "Error!");
        });
        queue.add(stringRequest);
        // send via bluetooth to arduino
        String message = "mode:" + mode;
        btChannel.sendMessage(message);
    }

    private void sendGap() {
        // send via http to backend
        String url = this.url + "/setvalues?gap=" + gap;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> Log.i("Response", response), error -> {
            Log.e("Error", "Error!");
        });
        queue.add(stringRequest);
        // send via bluetooth to arduino
        String message = "gap:" + gap;
        btChannel.sendMessage(message);
    }

    @Override
    public void increaseGap() {
        int gapInt = Integer.parseInt(this.gap);
        if (gapInt < 100){
            gapInt += 5;
        }
        this.gap = "" + gapInt;
        sendGap();
        activity.runOnUiThread(() -> textGap.setText("Dam gap: " + gap));
    }

    @Override
    public void decreaseGap() {
        int gapInt = Integer.parseInt(this.gap);
        if (gapInt > 0){
            gapInt -= 5;
        }
        this.gap = "" + gapInt;
        sendGap();
        activity.runOnUiThread(() -> textGap.setText("Dam gap: " + gap));
    }

    @Override
    public void changeMode() {
        if (mode.equals("auto")){
            setMode("manual");
        } else {
            setMode("auto");
        }
        sendMode();
    }

    @Override
    public void test(String contentAsString) {
        activity.runOnUiThread(() -> textState.setText(contentAsString));
    }
}
