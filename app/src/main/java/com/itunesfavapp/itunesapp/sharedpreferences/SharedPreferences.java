/*
* Homework 06
* SharedPreferences.java
* Hozefa Haveliwala, Nikhil Nagori Group 29
* */

package com.itunesfavapp.itunesapp.sharedpreferences;

import android.content.Context;

import com.itunesfavapp.itunesapp.TopApps;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;

public class SharedPreferences {
    private static final String USER_PREFS = "TOP_APPS";
    private android.content.SharedPreferences.Editor editor = null;
    private android.content.SharedPreferences preferences = null;
    private Context c;

    public SharedPreferences(Context context) {
        c = context;
        preferences = context.getApplicationContext().getSharedPreferences(
                USER_PREFS, Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    public void addFavorite( TopApps app) {
        List<TopApps> favorites = getFavorites();
        if (favorites == null)
            favorites = new ArrayList<TopApps>();
        favorites.add(app);
        saveFavorites(favorites);
    }

    public void removeFavorite( TopApps app) {
        ArrayList<TopApps> favorites = getFavorites();
        if (favorites != null) {
            favorites.remove(app);
            saveFavorites(favorites);
        }
    }

    public void saveFavorites(List<TopApps> favorites) {
        Gson gson = new Gson();
        String jsonFavorites = gson.toJson(favorites);
        editor.putString(USER_PREFS, jsonFavorites);
        editor.commit();
    }

    public ArrayList<TopApps> getFavorites() {

        List<TopApps> favorites;
        if (preferences.contains(USER_PREFS)) {
            String jsonFavorites = preferences.getString(USER_PREFS, null);
            Gson gson = new Gson();
            TopApps[] favoriteItems = gson.fromJson(jsonFavorites,
                    TopApps[].class);
            favorites = Arrays.asList(favoriteItems);
            favorites = new ArrayList<TopApps>(favorites);
            return (ArrayList<TopApps>) favorites;
        } else
            return null;
    }
}
