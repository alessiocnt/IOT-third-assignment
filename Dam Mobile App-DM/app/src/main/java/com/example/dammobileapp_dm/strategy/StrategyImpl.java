package com.example.dammobileapp_dm.strategy;

import android.app.Activity;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

public class StrategyImpl implements Strategy {

    private final Activity activity;
    private final TextView textState;
    private final Switch switchManual;
    private final TextView textLevel;
    private final Button btnGapDecrease;
    private final TextView textGap;
    private final Button btnGapIncrease;

    private String mode = "auto";
    private String state = "normal";
    private String waterLevel = "300";
    private String gap = "0";

    public StrategyImpl(final Activity activity, final TextView textState, final Switch switchManual, final TextView textLevel, final Button btnGapDecrease, final TextView textGap, final Button btnGapIncrease){
        this.activity = activity;
        this.textState = textState;
        this.switchManual = switchManual;
        this.textLevel = textLevel;
        this.btnGapDecrease = btnGapDecrease;
        this.textGap = textGap;
        this.btnGapIncrease = btnGapIncrease;
    }

    @Override
    public void setGap(String gap) {
        this.gap = gap;
        if (mode.equals("auto")) {
            activity.runOnUiThread(() -> textGap.setText(gap));
        }
    }

    @Override
    public void setWaterLevel(String waterLevel) {
        this.waterLevel = waterLevel;
        activity.runOnUiThread(() -> textLevel.setText(waterLevel));
    }

    @Override
    public void setState(String state) {
        this.state = state;
        activity.runOnUiThread(() -> textState.setText(state));
        if (state.equals("alarm")) {
            switchManual.setEnabled(true);
            btnGapDecrease.setActivated(true);
            btnGapIncrease.setActivated(true);
        }
    }

    @Override
    public void setMode(String mode) {
        this.mode = mode;
        if (mode.equals("auto")) {
            activity.runOnUiThread(() -> {
                switchManual.setChecked(false);
                switchManual.setEnabled(false);
                btnGapDecrease.setEnabled(false);
                btnGapIncrease.setEnabled(false);
            });
        } else {
            activity.runOnUiThread(() -> {
                switchManual.setChecked(true);
                switchManual.setEnabled(true);
                btnGapDecrease.setActivated(true);
                btnGapIncrease.setActivated(true);
            });
        }
    }

    @Override
    public void increaseGap() {
        int gapInt = Integer.parseInt(this.gap);
        if (gapInt < 100){
            gapInt += 5;
        }
        this.gap = "" + gapInt;
        activity.runOnUiThread(() -> textGap.setText(gap));
    }

    @Override
    public void decreaseGap() {
        int gapInt = Integer.parseInt(this.gap);
        if (gapInt > 0){
            gapInt -= 5;
        }
        this.gap = "" + gapInt;
        activity.runOnUiThread(() -> textGap.setText(gap));
    }

    @Override
    public void changeMode() {
        if (mode.equals("auto")){
            mode = "manual";
            activity.runOnUiThread(() -> switchManual.setChecked(true));
        } else {
            mode = "auto";
            activity.runOnUiThread(() -> switchManual.setChecked(false));
        }
    }

    @Override
    public void test(String contentAsString) {
        activity.runOnUiThread(() -> textState.setText(contentAsString));
    }
}
