package com.quikrete;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.google.gson.Gson;
import com.quikrete.gsondata.appsweepstakes.AppSweepstakesData;
import com.quikrete.utils.Utils;
import com.quikrete.utils.WebServiceUtil;
import com.sharedpreference.SharedPreferenceHelper;

import org.apache.http.NameValuePair;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;

public class SplashActivity extends Activity {
    final long SPLASH_TIME_OUT = 2000;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_splash);
        context = this;
        //     ImageView ic_logo = (ImageView) findViewById(R.id.ic_logo);

        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @SuppressLint("StaticFieldLeak")
            @Override
            public void run() {
                new AsyncTask<Void, Void, AppSweepstakesData>() {

                    @Override
                    protected void onPreExecute() {

                    }

                    @Override
                    protected AppSweepstakesData doInBackground(Void... params) {
                        // TODO Auto-generated method stub
                        try {
                            List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();

                            String url = WebServiceUtil.URL
                                    + WebServiceUtil.METHOD_17_1_APP_SWEEP;

                            String result = WebServiceUtil.getPostResponce(nameValuePair,
                                    url);
                            Gson gson = new Gson();
                            Reader reader = new InputStreamReader(
                                    WebServiceUtil.convertStringToInputstream(result));
                            AppSweepstakesData response = gson.fromJson(reader,
                                    AppSweepstakesData.class);


                            return response;
                        } catch (Exception e) {
                            // TODO: handle exception
                        }

                        return null;
                    }

                    @Override
                    protected void onPostExecute(AppSweepstakesData result) {

                        try {

                            if (result != null) {

                                SharedPreferenceHelper.savePreferences(SharedPreferenceHelper.APP_SWEEPSTAKES_START_DATE, result.getFrom(), context);
                                SharedPreferenceHelper.savePreferences(SharedPreferenceHelper.APP_SWEEPSTAKES_END_DATE, result.getTo(), context);
                                SharedPreferenceHelper.savePreferences(SharedPreferenceHelper.APP_SWEEPSTAKES_URL, result.getPageUrl(), context);

                                if (Utils.isWithinRange(Utils.getTodaysDateObj(), context)) {
                                    Intent i = new Intent(SplashActivity.this, AppSweepTakesActivity.class);
                                    startActivity(i);
                                    finish();
                                    Log.e("SplashActivity", "1");

                                } else {
                                    startActivity(new Intent(context, AppSweepTakesActivity.class));
                                    finish();
                                    Log.e("SplashActivity", "2");
                                }
                            } else {
                                startActivity(new Intent(context, AppSweepTakesActivity.class));
                                finish();
                                Log.e("SplashActivity", "3");
                            }

                        } catch (Exception e) {
                            // TODO: handle exception
                            startActivity(new Intent(context, AppSweepTakesActivity.class));
                            finish();
                            Log.e("SplashActivity", "4");
                        }
                    }
                }.execute();
            }
        }, SPLASH_TIME_OUT);

    }

}

