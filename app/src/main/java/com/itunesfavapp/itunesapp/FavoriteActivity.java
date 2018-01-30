/*
* Homework 06
* FavoriteActivity.java
* Hozefa Haveliwala, Nikhil Nagori Group 29
* */

package com.itunesfavapp.itunesapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.itunesfavapp.itunesapp.sharedpreferences.SharedPreferences;

import java.util.ArrayList;

public class FavoriteActivity extends AppCompatActivity {
    ListView lV1;
    SharedPreferences sharedPreference;
    TopAppsFavAdapter adapter;
    ArrayList<TopApps> favAppArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        sharedPreference = new SharedPreferences(this);
        favAppArrayList = new ArrayList<>();
        favAppArrayList = sharedPreference.getFavorites();
        lV1 = (ListView) findViewById(R.id.listView2);

        if (favAppArrayList != null) {
            adapter = new TopAppsFavAdapter(this, R.layout.list_item, favAppArrayList);
            lV1.setAdapter(adapter);
            adapter.setNotifyOnChange(true);
        }

    }
}
