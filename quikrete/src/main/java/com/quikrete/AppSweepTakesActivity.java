package com.quikrete;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings.PluginState;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.gson.Gson;
import com.quikrete.gsondata.appsweepstakes.AppSweepstakesData;
import com.quikrete.utils.MyLocation;
import com.quikrete.utils.MyLocation.LocationResult;
import com.quikrete.utils.ScreenUtils;
import com.quikrete.utils.Utils;
import com.quikrete.utils.WebServiceUtil;
import com.sharedpreference.SharedPreferenceHelper;

public class AppSweepTakesActivity extends Activity {
    WebView webView;

    String reg = "fake://registered";
    String cancel = "fake://thanks";

    Context context;

    String latitude = "", longitude = "";

    @SuppressLint("StaticFieldLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // ScreenUtils.fullScreen(this);
        context = this;
        setContentView(R.layout.activity_app_sweep_takes);

        webView = (WebView) findViewById(R.id.webView1);
        webView.requestFocus(View.FOCUS_DOWN);

        webView.getSettings().setDomStorageEnabled(true);

        final String regStatus = SharedPreferenceHelper.getPreference(context,
                SharedPreferenceHelper.APP_SWEEPSTAKES_REGISTERED);
        final String noThanksStatus = SharedPreferenceHelper.getPreference(context,
                SharedPreferenceHelper.APP_SWEEPSTAKES_NO_THANKS);

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {

            String fromMain = bundle.getString("main");
            if (fromMain != null && fromMain.equals("1")) {
                if ((regStatus != null && regStatus.equals("1"))) {

                    try {

                        startActivity(new Intent(this, MainActivity.class));
                        finish();
                        Log.e("AppSweep", "1");

                    } catch (Exception e) {
                        // TODO: handle exception
                    }

                } else loadWebUrl();

            } else {
                try {

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

                                        if ((regStatus != null && regStatus.equals("1"))
                                                || (noThanksStatus != null && noThanksStatus
                                                .equals("1"))) {

                                            try {

                                                loadWebUrl();
                                                Log.e("AppSweep", "2");

                                            } catch (Exception e) {
                                                // TODO: handle exception
                                            }

                                        } else loadWebUrl();
                                    } else {
                                        startActivity(new Intent(context, MainActivity.class));
                                        finish();
                                        Log.e("AppSweep", "3");
                                    }
                                } else {
                                    startActivity(new Intent(context, MainActivity.class));
                                    finish();
                                    Log.e("AppSweep", "4");
                                }

                            } catch (Exception e) {
                                // TODO: handle exception
                                startActivity(new Intent(context, MainActivity.class));
                                finish();
                                Log.e("AppSweep", "5");
                            }

                        }

                        ;

                    }.execute();


                } catch (Exception e2) {
                    // TODO Auto-generated catch block
                    e2.printStackTrace();
                }

            }
        } else {

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

                            if (Utils.isWithinRange(Utils.getTodaysDateObj(), context))  {

                                if ((regStatus != null && regStatus.equals("1"))
                                        || (noThanksStatus != null && noThanksStatus
                                        .equals("1"))) {

                                    try {
                                        loadWebUrl();
                                       // finish();
                                        Log.e("AppSweep", "6");

                                    } catch (Exception e) {
                                        // TODO: handle exception
                                    }

                                } else loadWebUrl();
                            } else {
                               // loadWebUrl();
                                startActivity(new Intent(context, MainActivity.class));
                                finish();
                                Log.e("AppSweep", "7");
                            }
                        } else {
                            startActivity(new Intent(context, MainActivity.class));
                            finish();
                            Log.e("AppSweep", "8");
                        }

                    } catch (Exception e) {
                        // TODO: handle exception
                        startActivity(new Intent(context, MainActivity.class));
                        finish();
                        Log.e("AppSweep", "9");
                    }


                }

                ;
            }.execute();


        }

        try {

            MyLocation myLocation = new MyLocation();
            myLocation.getLocation(context, locationResult);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private class Callback extends WebViewClient {

        @Override
        public void onPageFinished(WebView view, String url) {
            // TODO Auto-generated method stub


            webView.loadUrl("javascript:var x = document.getElementById('geo-city').value='"
                    + latitude + "_" + longitude + "';");

//			webView.loadUrl("javascript:document.getElementById('geo-city').value='"
//					+ latitude + "_" + longitude + "';");

            //document.getElementById('myfield').value = 'aaa';

            //  webView.loadUrl("javascript:document.getElementById('geo-city').style.visibility = 'hidden';");

            super.onPageFinished(view, url);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            if (url != null && url.contains(reg)) {

                SharedPreferenceHelper.savePreferences(
                        SharedPreferenceHelper.APP_SWEEPSTAKES_REGISTERED, "1",
                        context);
                startActivity(new Intent(context, MainActivity.class));
                finish();
            }
            if (url != null && url.contains(cancel)) {

                SharedPreferenceHelper.savePreferences(
                        SharedPreferenceHelper.APP_SWEEPSTAKES_NO_THANKS, "1",
                        context);
                startActivity(new Intent(context, MainActivity.class));
                finish();
                Log.e("AppSweep", "10");
            }

            return (false);
        }

    }

    public void onClickHome(View view) {

        startActivity(new Intent(this, MainActivity.class));
        finish();
        Log.e("AppSweep", "11");
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setPluginState(PluginState.ON);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.requestFocus();
        webView.setWebViewClient(new Callback());


        super.onResume();
    }

    private void loadWebUrl() {
        // TODO Auto-generated method stub
        String pdf = SharedPreferenceHelper.getPreference(context, SharedPreferenceHelper.APP_SWEEPSTAKES_URL);

        if (Utils.isNetworkAvailable(context))
            webView.loadUrl(pdf);
        else {
            ScreenUtils
                    .showToast(
                            context,
                            "You need Internet Connectivity to take part in the 'World of Concrete Sweepstakes'");
        }
    }

    public LocationResult locationResult = new LocationResult() {
        @Override
        public void gotLocation(final Location location) {
            // Got the location!

            Log.e("newlog", location + "  %%final location%");

            if (location != null) {

                latitude = location.getLatitude() + "";
                longitude = location.getLongitude() + "";

                SharedPreferenceHelper.savePreferences(SharedPreferenceHelper.LATITUDE, latitude, context);
                SharedPreferenceHelper.savePreferences(SharedPreferenceHelper.LONGITUDE, longitude, context);

                Log.e("Latitude-- "+latitude,"Longitude "+longitude);

            } else {
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        Log.e("Latitude-- "+latitude,"Longitude "+longitude);

                    }
                });
            }
        }
    };

}
