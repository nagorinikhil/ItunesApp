/*
* Homework 06
* TopAppsAdapter.java
* Hozefa Haveliwala, Nikhil Nagori Group 29
* */

package com.itunesfavapp.itunesapp;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.itunesfavapp.itunesapp.sharedpreferences.SharedPreferences;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class TopAppsAdapter extends ArrayAdapter<TopApps> {
    Context c;
    List<TopApps> topAppsList;
    ArrayList<TopApps> favAppArrayList;
    int res;
    AlertDialog aD = null;
    ViewHolder holder = null;
    SharedPreferences sharedPreference;

    public TopAppsAdapter(Context context, int resource, List<TopApps> topAppsList) {
        super(context, resource, topAppsList);
        this.c = context;
        this.topAppsList = topAppsList;
        this.res = resource;
        sharedPreference = new SharedPreferences(context);
        favAppArrayList = new ArrayList<>();

    }

    private class ViewHolder {
        TextView textViewAppName;
        ImageView favoriteImg;
        ImageView noFavoriteImg;
        ImageView appImg;
    }

    @Override
    public int getCount() {
        return topAppsList.size();
    }

    @Override
    public TopApps getItem(int position) {
        return topAppsList.get(position);
    }


    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        favAppArrayList = sharedPreference.getFavorites();
        holder = null;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(res, parent, false);
            holder = new ViewHolder();
            holder.textViewAppName = (TextView) convertView.findViewById(R.id.textViewApp);
            holder.favoriteImg = (ImageView) convertView.findViewById(R.id.imageView2);
            // holder.noFavoriteImg = (ImageView) convertView.findViewById(R.id.imageView3);
            holder.appImg = (ImageView) convertView.findViewById(R.id.imageView);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        TopApps tA = (TopApps) getItem(position);
        holder.textViewAppName.setText(tA.toString());
        Picasso.with(c)
                .load(tA.getImageUrl())
                .resize(50, 50)
                .into(holder.appImg);
        holder.favoriteImg.setImageResource(R.drawable.whitestar);
        holder.favoriteImg.setTag("white");

        if (favAppArrayList.contains(topAppsList.get(position)) == true) {
            //changeStar(true);
            holder.favoriteImg.setImageResource(R.drawable.blackstar);
            holder.favoriteImg.setTag("black");
            //Log.d("app", topAppsList.get(position).toString());
            // notifyDataSetChanged();
        }

        holder.favoriteImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getTag().equals("white")) {
                    createDialog(c.getResources().getString(R.string.Dialog_Title), c.getResources().getString(R.string.Add_Favorite_Msg), true, topAppsList.get(position), v);
                } else {
                    createDialog(c.getResources().getString(R.string.Dialog_Title1), c.getResources().getString(R.string.Remove_Favorite_Msg), false, topAppsList.get(position), v);
                }
            }
        });

        return convertView;
    }

    private void createDialog(String title, String msg, final boolean isFavorite, final TopApps app, final View v) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(c);

        builder.setTitle(title)
                .setMessage(msg)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ImageView iV = (ImageView) v;
                        if (isFavorite == true) {
                            setFavorite(app);
                            iV.setImageResource(R.drawable.blackstar);
                            iV.setTag("black");
                            notifyDataSetChanged();
                        } else {
                            removeFavorite(app);
                            iV.setImageResource(R.drawable.whitestar);
                            iV.setTag("while");
                            notifyDataSetChanged();

                        }
                        //changeStar(isFavorite);
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

    private void setFavorite(TopApps app) {
        sharedPreference.addFavorite(app);
    }

    private void removeFavorite(TopApps app) {
        sharedPreference.removeFavorite(app);
    }
}
