package com.example.dammobileapp_dm.utils;

public interface ExtendedRunnable extends Runnable {
    void write(byte[] bytes);
    void cancel();
}