package com.sharedpreference;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.gson.Gson;
import com.quikrete.data.FavData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SharedPreferenceHelper {

    public static String TEMP_DUP_FILE_NAME = "temp_file_name";

    public static String APP_SWEEPSTAKES_REGISTERED = "registered";
    public static String APP_SWEEPSTAKES_NO_THANKS = "no_thanks";

    public static String APP_SWEEPSTAKES_START_DATE = "start_date";
    public static String APP_SWEEPSTAKES_END_DATE = "end_date";
    public static String APP_SWEEPSTAKES_URL = "app_url";

    public static String CALC_FAV_DATA_1 = "calc_fav_data_1";
    public static String CALC_FAV_DATA_2 = "calc_fav_data_2";

    public static final String PREFS_NAME = "PRODUCT_APP";
    public static final String FAVORITES = "Product_Favorite";
    public static final String LATITUDE = "";
    public static final String LONGITUDE = "";
    public static final String SOCIALMESSAGE = "";

    public static void savePreferences(String key, String value, Context ctx) {

        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(ctx);
        Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getPreference(Context ctx, String key) {

        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(ctx);

        if (key.equals(APP_SWEEPSTAKES_URL))
            return sharedPreferences.getString(key, "http://quikrete.staging.wpengine.com/sweepstakes/");
        return sharedPreferences.getString(key, "");
    }

    // This four methods are used for maintaining favorites.
    public static void saveFavorites(Context context, List<FavData> favorites) {
        SharedPreferences settings;
        Editor editor;

        settings = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);
        editor = settings.edit();

        Gson gson = new Gson();
        String jsonFavorites = gson.toJson(favorites);

        editor.putString(FAVORITES, jsonFavorites);

        editor.commit();
    }

    public static void addFavorite(Context context, FavData product) {
        List<FavData> favorites = getFavorites(context);
        if (favorites == null)
            favorites = new ArrayList<FavData>();
        favorites.add(product);
        saveFavorites(context, favorites);
    }

    public static void removeFavorite(Context context, FavData product) {
        ArrayList<FavData> favorites = getFavorites(context);
        if (favorites != null) {
            favorites.remove(product);
            saveFavorites(context, favorites);
        }
    }

    public static FavData getFavorite(Context context, String calcId) {
        SharedPreferences settings;
        FavData favorites = null;

        settings = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);

        if (settings.contains(FAVORITES)) {
            String jsonFavorites = settings.getString(FAVORITES, null);
            Gson gson = new Gson();
            FavData[] favoriteItems = gson.fromJson(jsonFavorites,
                    FavData[].class);

            for (FavData favData : favoriteItems) {
                if (calcId.equals(favData.getCalc_id())) {
                    favorites = favData;
                    Log.e("getFavorite", "match found---"+favorites);

                } else {
                    Log.e("getFavorite", "No match found---");
                }
            }

        } else
            return null;

        return favorites;
    }

    public static ArrayList<FavData> getFavorites(Context context) {
        SharedPreferences settings;
        List<FavData> favorites;

        settings = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);

        if (settings.contains(FAVORITES)) {
            String jsonFavorites = settings.getString(FAVORITES, null);
            Gson gson = new Gson();
            FavData[] favoriteItems = gson.fromJson(jsonFavorites,
                    FavData[].class);

            favorites = Arrays.asList(favoriteItems);
            favorites = new ArrayList<FavData>(favorites);
        } else
            return null;

        return (ArrayList<FavData>) favorites;
    }

}
