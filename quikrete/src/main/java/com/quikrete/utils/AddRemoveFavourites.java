package com.quikrete.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;

import com.google.gson.Gson;
import com.quikrete.data.FavResult;
import com.sharedpreference.SharedPreferenceHelper;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by NabeelRangari on 8/21/17.
 */

public class AddRemoveFavourites {

    public static void onClickAddToFav(final String id, final TextView txt_star, final Context context) {

        final boolean value = Utils.checkAddToFav(id, context);

        new AsyncTask<String, String, FavResult>() {
            ProgressDialog pdialog;

            @Override
            protected void onPreExecute() {
                // TODO Auto-generated method stub
                String message[] = {"Adding to Favorites...please wait",
                        "Removing from Favorites...please wait"};
                if (value) {
                    pdialog = ScreenUtils.returnProgDialogObj(context,
                            message[1], false);
                } else
                    pdialog = ScreenUtils.returnProgDialogObj(context,
                            message[0], false);
                pdialog.show();

                super.onPreExecute();
            }

            @Override
            protected FavResult doInBackground(String... params) {
                // TODO Auto-generated method stub
                try {

                    List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
                    nameValuePair.add(new BasicNameValuePair(
                            WebServiceUtil.METHOD_12_1_ADD_TO_FAVOURITE_ID,
                            params[0]));

                    String url = "";
                    if (value) {
                        url = WebServiceUtil.URL
                                + WebServiceUtil.METHOD_13_1_ADD_TO_UNFAVOURITE;
                    } else {
                        url = WebServiceUtil.URL
                                + WebServiceUtil.METHOD_12_1_ADD_TO_FAVOURITE;
                    }

                    String result = WebServiceUtil.getPostResponce(
                            nameValuePair, url);

                    Gson gson = new Gson();
                    Reader reader = new InputStreamReader(
                            WebServiceUtil.convertStringToInputstream(result));
                    FavResult response = gson.fromJson(reader, FavResult.class);

                    return response;
                } catch (Exception e) {
                    // TODO: handle exception
                }
                return null;
            }

            @Override
            protected void onPostExecute(FavResult result) {
                // TODO Auto-generated method stub
                try {
                    pdialog.dismiss();
                } catch (Exception e) {
                    // TODO: handle exception
                }
                if (result != null) {

                    try {
                        String message = result.getMessage();
                        if (message != null && message.trim().equals("success")) {
                            if (value) {
                                SharedPreferenceHelper.savePreferences(id, "0",
                                        context);
                                ScreenUtils.showToast(context,
                                        "Item removed from Favourites");
                                ScreenUtils.changeStarColor(id, txt_star, context);

                            } else {
                                SharedPreferenceHelper.savePreferences(id, "1",
                                        context);
                                ScreenUtils.showToast(context,
                                        "Item added to Favourites");
                                ScreenUtils.changeStarColor(id, txt_star, context);
                            }
                        } else {
                            ScreenUtils.showToast(context,
                                    WebServiceUtil.FAILURE_MESSAGE);
                        }
                    } catch (Exception e) {
                        // TODO: handle exception
                    }
                }
                super.onPostExecute(result);
            }
        }.execute(new String[]{id});

    }
}
