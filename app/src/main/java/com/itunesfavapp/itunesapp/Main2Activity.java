/*
* Homework 06
* Main2Activity.java
* Hozefa Haveliwala, Nikhil Nagori Group 29
* */

package com.itunesfavapp.itunesapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Main2Activity extends AppCompatActivity implements GetJSONAsyncTask.SetListInterface {

    ProgressDialog pd;
    ArrayList<TopApps> topAppsArrayList;
    TopAppsAdapter adapter;
    ListView lV1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        lV1 = (ListView) findViewById(R.id.listView1);
        if (isConnected() == true) {
            new GetJSONAsyncTask(this).execute(getResources().getString(R.string.API_URL));
        } else {
            Toast.makeText(Main2Activity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_refresh) {
            if (isConnected() == true) {
                new GetJSONAsyncTask(this).execute(getResources().getString(R.string.API_URL));
            } else {
                Toast.makeText(Main2Activity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
            }
        } else if (id == R.id.action_favorite) {
            Intent intent = new Intent(Main2Activity.this, FavoriteActivity.class);
            startActivity(intent);
        } else if (id == R.id.action_sort_increase) {
            if (topAppsArrayList != null) {
                sortAsc(topAppsArrayList);
                adapter.notifyDataSetChanged();
            }
        } else if (id == R.id.action_sort_decrease) {
            if (topAppsArrayList != null) {
                sortDsc(topAppsArrayList);
                adapter.notifyDataSetChanged();
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setProgressDialog() {
        pd = new ProgressDialog(Main2Activity.this);
        pd.setCancelable(false);
        pd.show();

    }

    @Override
    public void dismissProgressDialog() {
        pd.dismiss();
    }

    @Override
    public void setTopApps(ArrayList<TopApps> topApps) {
        if (topApps != null) {
            topAppsArrayList = new ArrayList<>();
            topAppsArrayList = topApps;
            adapter = new TopAppsAdapter(Main2Activity.this, R.layout.list_item, topAppsArrayList);
            adapter.setNotifyOnChange(true);
            lV1.setAdapter(adapter);
        } else {
            Toast.makeText(Main2Activity.this, "No Data", Toast.LENGTH_SHORT).show();
        }
    }


    private boolean isConnected() {
        ConnectivityManager cM = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cM.getActiveNetworkInfo();
        if ((networkInfo != null) && (networkInfo.isConnected() == true)) {
            return true;
        }
        return false;
    }


    private void sortAsc(ArrayList<TopApps> appArray) {
        Collections.sort(appArray, new Comparator<TopApps>() {
            @Override
            public int compare(TopApps o1, TopApps o2) {
                if (o1.amount > o2.amount)
                    return 1;
                else if (o1.amount < o2.amount)
                    return -1;
                else
                    return 0;
            }

        });
    }

    private void sortDsc(ArrayList<TopApps> appArray) {
        Collections.sort(appArray, new Comparator<TopApps>() {
            @Override
            public int compare(TopApps o1, TopApps o2) {
                if (o2.amount > o1.amount)
                    return 1;
                else if (o2.amount < o1.amount)
                    return -1;
                else
                    return 0;
            }

        });
    }

    @Override
    protected void onResume() {
      /*  if (isConnected() == true) {
            new GetJSONAsyncTask(this).execute(getResources().getString(R.string.API_URL));
        } else {
            Toast.makeText(Main2Activity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }*/
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
        super.onResume();
    }
}
