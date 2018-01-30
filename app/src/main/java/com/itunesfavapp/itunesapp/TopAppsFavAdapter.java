/*
* Homework 06
* TopsAppsFavAdapter.java
* Hozefa Haveliwala, Nikhil Nagori Group 29
* */

package com.itunesfavapp.itunesapp;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.itunesfavapp.itunesapp.sharedpreferences.SharedPreferences;
import com.squareup.picasso.Picasso;

import java.util.List;


public class TopAppsFavAdapter extends ArrayAdapter<TopApps> {
    Context c;
    List<TopApps> topAppsList;
    int res;
    ImageView img, imgWhite;

    AlertDialog aD = null;
    SharedPreferences sharedPreference;

    public TopAppsFavAdapter(Context context, int resource, List<TopApps> topAppsList) {
        super(context, resource, topAppsList);
        this.c = context;
        this.topAppsList = topAppsList;
        this.res = resource;
        sharedPreference = new SharedPreferences(context);
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(res, parent, false);
        }
        TextView textViewColorName = (TextView) convertView.findViewById(R.id.textViewApp);
        img = (ImageView) convertView.findViewById((R.id.imageView));
        imgWhite = (ImageView) convertView.findViewById(R.id.imageView2);
        imgWhite.setImageResource(R.drawable.blackstar);
        imgWhite.setTag("black");


        Picasso.with(c)
                .load(topAppsList.get(position).getImageUrl())
                .resize(50, 50)
                .into(img);

        textViewColorName.setText(topAppsList.get(position).toString());

        imgWhite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDialog(c.getResources().getString(R.string.Dialog_Title1), c.getResources().getString(R.string.Remove_Favorite_Msg), false, topAppsList.get(position));
            }
        });
        return convertView;
    }

    private void createDialog(String title, String msg, final boolean isFavorite, final TopApps app) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(c);

        builder.setTitle(title)
                .setMessage(msg)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (isFavorite == false) {
                            removeFavorite(app);
                            topAppsList.remove(app);
                            notifyDataSetChanged();
                        }
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        aD.dismiss();
                    }
                });
        aD = builder.create();
        aD.show();

    }

    private void removeFavorite(TopApps app) {
        sharedPreference.removeFavorite(app);
    }
}
