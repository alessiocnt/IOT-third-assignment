package com.example.dammobileapp_dm.strategy;

public interface Strategy {
    void test(String contentAsString);
    void setGap(String gap);
    void setWaterLevel(String waterLevel);
    void setMode(String mode);
    void setState(String state);
    void decreaseGap();
    void increaseGap();
    void changeMode();
}

