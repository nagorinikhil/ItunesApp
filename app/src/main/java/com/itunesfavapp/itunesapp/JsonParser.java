/*
* Homework 06
* JsonParser.java
* Hozefa Haveliwala, Nikhil Nagori Group 29
* */

package com.itunesfavapp.itunesapp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonParser {
    ArrayList<TopApps> arrayList_topApps;

    public ArrayList<TopApps> parse(String json){

        JSONObject jObject = null;
        try {
            arrayList_topApps = new ArrayList<TopApps>();
            jObject = new JSONObject(json);
            jObject = jObject.getJSONObject("feed");
            JSONArray jArray = jObject.getJSONArray("entry");
            for (int i = 0; i < jArray.length(); i++){
                TopApps topApps = new TopApps();

                JSONObject app = jArray.getJSONObject(i);
                JSONObject appName = app.getJSONObject("im:name");
                topApps.setName(appName.getString("label"));

                JSONArray appImage = app.getJSONArray("im:image");
                JSONObject imageObject = appImage.getJSONObject(0);
                topApps.setImageUrl(imageObject.getString("label"));

                JSONObject appPrice = app.getJSONObject("im:price");
                topApps.setAmount(Float.parseFloat(appPrice.getString("label").substring(1)));
                JSONObject appPriceAttribute  = appPrice.getJSONObject("attributes");
                topApps.setCurrency(appPriceAttribute.getString("currency"));

                arrayList_topApps.add(topApps);
            }
            return arrayList_topApps;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  null;
    }
}
