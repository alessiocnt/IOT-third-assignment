package com.example.dammobileapp_dm.bgtask;

import android.content.Context;
import android.os.AsyncTask;
import com.example.dammobileapp_dm.strategy.Strategy;
import com.example.dammobileapp_dm.utils.Config;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class BgTask extends AsyncTask {

    private Strategy strategy;
    private Context context;

    public BgTask(final Strategy strategy, final Context context){
        this.strategy = strategy;
        this.context = context;
    }

    @Override
    public Object doInBackground(Object[] objects) {
        while(true) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            /** In attesa di messaggi ->
             * Srategy che agisce di conseguenza sarà poi stategy ad invocare la runOnUiThread() e a modificare la view.
             **/
            RequestQueue queue = Volley.newRequestQueue(this.context);
            getGap(queue);
            getLevel(queue);
            getState(queue);
            getMode(queue);
            // test(queue);
        }
    }

    private void test(RequestQueue queue) {
        String url = Config.URL + "/test";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> strategy.test(response), error -> {
            strategy.test("Error");
        });
        queue.add(stringRequest);
    }

    private void getGap(RequestQueue queue) {
        String url = Config.URL + "/gap";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> strategy.setGap(response), error -> {
            strategy.test("Error");
        });
        queue.add(stringRequest);
    }

    private void getLevel(RequestQueue queue) {
        String url = Config.URL + "/level";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> strategy.setWaterLevel(response), error -> {
            strategy.test("Error!");
        });
        queue.add(stringRequest);
    }

    private void getState(RequestQueue queue) {
        String url = Config.URL + "/state";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> strategy.setState(response), error -> {
            strategy.test("Error!");
        });
        queue.add(stringRequest);
    }

    private void getMode(RequestQueue queue) {
        String url = Config.URL + "/mode";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> strategy.setMode(response), error -> {
            strategy.test("Error!");
        });
        queue.add(stringRequest);
    }
}
