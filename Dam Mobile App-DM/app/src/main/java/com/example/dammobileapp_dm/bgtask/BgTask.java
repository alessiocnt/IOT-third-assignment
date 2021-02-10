package com.example.dammobileapp_dm.bgtask;

import android.widget.TextView;

import com.example.dammobileapp_dm.netutils.Http;
import com.example.dammobileapp_dm.strategy.Strategy;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Locale;

public class BgTask implements Runnable{

    private Strategy strategy;
    private final String url = "https://192.168.1.106:8000/test?water=13";

    public BgTask(final Strategy strategy){
        this.strategy = strategy;
    }

    @Override
    public void run() {
        while(true){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            /** TODO  in attesa di messaggi ->
             * lo passo a srategy che agisce di conseguenza
             * sarà poi stategy ad invocare la runOnUiThread() e a modificare
             * la view.
             **/
            Http.get(url, response -> {
                if(response.code() == HttpURLConnection.HTTP_OK){
                    try {
                        strategy.test(response.contentAsString());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });


        }
    }
}
