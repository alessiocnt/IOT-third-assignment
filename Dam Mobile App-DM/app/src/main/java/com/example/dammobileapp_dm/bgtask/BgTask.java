package com.example.dammobileapp_dm.bgtask;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;

import com.example.dammobileapp_dm.netutils.Http;
import com.example.dammobileapp_dm.strategy.Strategy;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Locale;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class BgTask extends AsyncTask {

    private Strategy strategy;
    private Context context;
    private final String url = "http://192.168.1.106:8000";  // http://192.168.1.157:8000/test?water=1  https://192.168.1.106:8000/test?water=13

    public BgTask(final Strategy strategy, final Context context){
        this.strategy = strategy;
        this.context = context;
    }

    @Override
    public Object doInBackground(Object[] objects) {
        while(true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            /** TODO  in attesa di messaggi ->
             * lo passo a srategy che agisce di conseguenza
             * sarà poi stategy ad invocare la runOnUiThread() e a modificare
             * la view.
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
        String url = "http://192.168.1.106:8000/test?water=1";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> strategy.test(response), error -> {
            strategy.test("Error");
        });
        queue.add(stringRequest);
    }

    private void getGap(RequestQueue queue) {
        String url = this.url + "/gap";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> strategy.setGap(response), error -> {
            strategy.test("Error");
        });
        queue.add(stringRequest);
    }

    private void getLevel(RequestQueue queue) {
        String url = this.url + "/level";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> strategy.setWaterLevel(response), error -> {
            strategy.test("Error!");
        });
        queue.add(stringRequest);
    }

    private void getState(RequestQueue queue) {
        String url = this.url + "/state";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> strategy.setState(response), error -> {
            strategy.test("Error!");
        });
        queue.add(stringRequest);
    }

    private void getMode(RequestQueue queue) {
        String url = this.url + "/mode";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> strategy.setMode(response), error -> {
            strategy.test("Error!");
        });
        queue.add(stringRequest);
    }
}
