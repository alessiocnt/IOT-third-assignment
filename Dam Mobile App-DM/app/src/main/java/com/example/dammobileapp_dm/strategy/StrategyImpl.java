package com.example.dammobileapp_dm.strategy;

import android.app.Activity;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

public class StrategyImpl implements Strategy {

    private Activity activity;
    private TextView textState;
    private Switch switchManual;
    private TextView textLevel;
    private Button btnGapDecrease;
    private TextView textGap;
    private Button btnGapIncrease;

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
    public void test(String contentAsString) {
        activity.runOnUiThread(() -> textState.setText(contentAsString));
    }
}
