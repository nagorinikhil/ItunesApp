/*
* Homework 06
* GetJSONAsyncTask.java
* Hozefa Haveliwala, Nikhil Nagori Group 29
* */

package com.itunesfavapp.itunesapp;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;


public class GetJSONAsyncTask extends AsyncTask<String, Void, ArrayList<TopApps>>{

    SetListInterface listInterface;

    public GetJSONAsyncTask(SetListInterface listInterface) {
        this.listInterface = listInterface;
    }

    @Override
    protected void onPreExecute() {
        listInterface.setProgressDialog();
        super.onPreExecute();
    }

    @Override
    protected ArrayList<TopApps> doInBackground(String... params) {

        try {
            ArrayList<TopApps> arrayList_topApps;
            URL url = new URL(params[0]);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sB = new StringBuilder();
            String line = "";
            while ((line = reader.readLine()) != null) {
                sB.append(line);
            }
            String json = sB.toString();
            JsonParser jp = new JsonParser();
            arrayList_topApps = jp.parse(json);
            return arrayList_topApps;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<TopApps> result) {
        if(result != null){
            Log.d("Result",result.toString());

        }
        listInterface.setTopApps(result);
        listInterface.dismissProgressDialog();
        super.onPostExecute(result);
    }

    static public interface SetListInterface{
        public void setProgressDialog();
        public void dismissProgressDialog();
        public void setTopApps(ArrayList<TopApps> topApps);
    }
}
