package com.quikrete;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.gson.Gson;
import com.journeyapps.barcodescanner.CaptureActivity;
import com.quikrete.data.GetSocialMedia;
import com.quikrete.utils.PermissionsUtils;
import com.quikrete.utils.ScreenUtils;
import com.quikrete.utils.Utils;
import com.quikrete.utils.WebServiceUtil;
import com.sharedpreference.SharedPreferenceHelper;

import org.apache.http.NameValuePair;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.CALL_PHONE;
import static android.Manifest.permission.CAMERA;

public class MainActivity extends Activity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private static final int PERMISSION_REQUEST_CAMERA = 2;
    private static final int PERMISSION_REQUEST_LOCATION = 1;
    private static final int PERMISSION_REQUEST_CALL = 4;
    private static final int REQUEST_LOCATION = 4;
    LinearLayout ll_header_down, ll_header_up;
    // ll_shadow_layer;
    ImageView img_header_menu_cross, img_header_menu_cross_1, close;

    /**
     * All widgets for the header tool bar
     */
    LinearLayout linear_header_searchmenu, linear_header_hammenu, ll_social_media;
    TextView txt_header_barcode, txt_header_mappoint, txt_header_search, txt_header_favorites,
            txt_header_menu, txtsocialMedia;
    EditText edt_keyword;
    Button btn_search;
    CheckBox chk_1, chk_2, chk_3, chk_4;
    TextView txt_chk_1, txt_chk_2, txt_chk_3, txt_chk_4;
    LinearLayout ll_chk_1, ll_chk_2, ll_chk_3, ll_chk_4;

    Context context;

    RelativeLayout ll_search_products, ll_search_projects, ll_how_videos,
            ll_whre_buy;
    LinearLayout ll_qty_calc, ll_quick_share, ll_call_support;

    LinearLayout ll_favourites, ll_prd_search, ll_prj_search, ll_how_to_videos,
            ll_calculators, ll_where_to_buy, ll_call_support_header,
            ll_app_sweep_takes_header;

    boolean cameraAccepted = false, locationAccepted = false, callAccepted = false, isPermissionGranted = true;

    String responseBody = "";
    GetSocialMedia response;

    Dialog myDialog;


    //Location New---

    GoogleApiClient mGoogleApiClient;
    LocationRequest mLocationRequest;
    PendingResult<LocationSettingsResult> result;

    private LinearLayout ll_instagram;
    boolean isClicked;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.e("onCreate", "MainActivity");

        context = this;
        ScreenUtils.fullScreen(this);
        setContentView(R.layout.activity_main);


        View headerView = findViewById(R.id.header1);

        initHeaderWidgets(headerView);
        getSocialMedia();
        myDialog = new Dialog(this);

        ll_instagram = (LinearLayout) findViewById(R.id.ll_instagram);
        requestPermissionLocation();


        txt_header_barcode.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(getApplicationContext(),
                        CaptureActivity.class);
                intent.setAction("com.google.zxing.client.android.SCAN");
                // this stops saving ur barcode in barcode scanner app's history
                intent.putExtra("SCAN_MODE", "SCAN_MODE");
                intent.putExtra("SCAN_FORMATS",
                        "CODE_39,CODE_93,CODE_128,DATA_MATRIX,ITF,CODABAR,EAN_13,EAN_8,UPC_A,QR_CODE");
                intent.putExtra("SAVE_HISTORY", false);
                startActivityForResult(intent, 0);
            }
        });

        txt_header_favorites.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),
                        FavResultListActivity.class);
                startActivity(intent);
            }
        });

//        txtsocialMedia.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(context, "Social Media", Toast.LENGTH_LONG).show();
//            }
//        });

        txt_header_search.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                slideDown(txt_header_search);
                txt_header_search.setTextColor(getResources().getColor(
                        R.color.home_icons_active));
                txt_header_menu.setTextColor(getResources().getColor(
                        R.color.home_icons_inactive));
            }
        });
        ll_instagram.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                ll_instagram.setBackgroundResource(R.drawable.homepageselectedborder);
                ll_quick_share.setBackgroundResource(R.drawable.home_page_border);
                ll_call_support.setBackgroundResource(R.drawable.home_page_border);
                showPopUp(v);

                txt_header_search.setTextColor(getResources().getColor(
                        R.color.home_icons_inactive));
                txt_header_menu.setTextColor(getResources().getColor(
                        R.color.home_icons_active));
            }
        });


        img_header_menu_cross_1.setOnClickListener(
                new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        slideUp();
                    }
                });

        ll_header_up = (LinearLayout) headerView
                .findViewById(R.id.ll_header_up);

        // ll_shadow_layer = (LinearLayout) findViewById(R.id.ll_shadow_layer);

        setTypeFace();

        ll_search_products = (RelativeLayout) findViewById(R.id.ll_search_products);
        ll_qty_calc = (LinearLayout) findViewById(R.id.ll_qty_calc);
        ll_search_projects = (RelativeLayout) findViewById(R.id.ll_search_projects);
        ll_how_videos = (RelativeLayout) findViewById(R.id.ll_how_videos);
        ll_whre_buy = (RelativeLayout) findViewById(R.id.ll_whre_buy);
        ll_quick_share = (LinearLayout) findViewById(R.id.ll_quick_share);
        ll_call_support = (LinearLayout) findViewById(R.id.ll_call_support);

        // /int i=1000;

        // for (int i = 0; i < 7; i++)
        {

            //ll_quick_share.startAnimation(returnFadeAnimation(3500)); //
            //ll_call_support.startAnimation(returnFadeAnimation(3500)); //
            //ll_search_projects.startAnimation(returnAnimation(1000));
            //ll_whre_buy.startAnimation(returnAnimation(1500));
            //ll_search_products.startAnimation(returnAnimation(2000));
            //ll_how_videos.startAnimation(returnAnimation(2500));
            //ll_qty_calc.startAnimation(returnFadeAnimation(3500));//

        }

        try {
            Log.e("newlog", Utils.isWithinRange(Utils.getTodaysDateObj(), context)
                    + "---");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        Utils.getTracker("Dashboard", getApplication());
    }


    Animation returnAnimation(long milSec) {
        Animation animationFalling = AnimationUtils.loadAnimation(this,
                R.anim.fall);
        animationFalling.setDuration(milSec);
        return animationFalling;
    }

    Animation returnFadeAnimation(long milSec) {
        Animation animationFalling = AnimationUtils.loadAnimation(this,
                R.anim.fade_in);
        animationFalling.setDuration(milSec);
        return animationFalling;
    }


    private void initHeaderWidgets(View headerView) {
        // TODO Auto-generated method stub
        ll_header_down = (LinearLayout) headerView
                .findViewById(R.id.ll_header_down);


        ll_social_media = (LinearLayout) headerView.findViewById(R.id.ll_social_media);

        txt_header_barcode = (TextView) headerView
                .findViewById(R.id.txt_header_barcode);
        txt_header_favorites = (TextView) headerView
                .findViewById(R.id.txt_header_favorites);
        txt_header_mappoint = (TextView) headerView
                .findViewById(R.id.txt_header_mappoint);
        txt_header_search = (TextView) headerView
                .findViewById(R.id.txt_header_search);
        txt_header_menu = (TextView) headerView
                .findViewById(R.id.txt_header_menu);
        img_header_menu_cross = (ImageView) headerView
                .findViewById(R.id.img_header_menu_cross);

        img_header_menu_cross_1 = (ImageView) headerView
                .findViewById(R.id.img_header_menu_cross_1);

        linear_header_searchmenu = (LinearLayout) headerView
                .findViewById(R.id.linear_header_searchmenu);
        linear_header_hammenu = (LinearLayout) headerView
                .findViewById(R.id.linear_header_hammenu);

        ll_favourites = (LinearLayout) headerView
                .findViewById(R.id.ll_favourites);
        ll_prd_search = (LinearLayout) headerView
                .findViewById(R.id.ll_prd_search);
        ll_prj_search = (LinearLayout) headerView
                .findViewById(R.id.ll_prj_search);
        ll_how_to_videos = (LinearLayout) headerView
                .findViewById(R.id.ll_how_to_videos);
        ll_calculators = (LinearLayout) headerView
                .findViewById(R.id.ll_calculators);
        ll_where_to_buy = (LinearLayout) headerView
                .findViewById(R.id.ll_where_to_buy);
        ll_call_support_header = (LinearLayout) headerView
                .findViewById(R.id.ll_call_support_header);
        ll_app_sweep_takes_header = (LinearLayout) headerView
                .findViewById(R.id.ll_app_sweep_takes_header);

        edt_keyword = (EditText) headerView.findViewById(R.id.edt_keyword);
        btn_search = (Button) headerView.findViewById(R.id.btn_search);
        chk_1 = (CheckBox) headerView.findViewById(R.id.chk_1);
        chk_2 = (CheckBox) headerView.findViewById(R.id.chk_2);
        chk_3 = (CheckBox) headerView.findViewById(R.id.chk_3);
        chk_4 = (CheckBox) headerView.findViewById(R.id.chk_4);

        txt_chk_1 = (TextView) headerView.findViewById(R.id.txt_chk_1);
        txt_chk_2 = (TextView) headerView.findViewById(R.id.txt_chk_2);
        txt_chk_3 = (TextView) headerView.findViewById(R.id.txt_chk_3);
        txt_chk_4 = (TextView) headerView.findViewById(R.id.txt_chk_4);
        ll_chk_1 = (LinearLayout) headerView.findViewById(R.id.ll_chk_1);
        ll_chk_2 = (LinearLayout) headerView.findViewById(R.id.ll_chk_2);
        ll_chk_3 = (LinearLayout) headerView.findViewById(R.id.ll_chk_3);
        ll_chk_4 = (LinearLayout) headerView.findViewById(R.id.ll_chk_4);


        chk_1.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (chk_1.isChecked()) {
                    ll_chk_1.setBackgroundResource(R.drawable.grey_border);
                } else {
                    ll_chk_1.setBackgroundResource(R.drawable.grey_border_grey_bg);
                }
            }
        });
        chk_2.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (chk_2.isChecked()) {
                    ll_chk_2.setBackgroundResource(R.drawable.grey_border);
                } else {
                    ll_chk_2.setBackgroundResource(R.drawable.grey_border_grey_bg);
                }
            }
        });
        chk_3.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (chk_3.isChecked()) {
                    ll_chk_3.setBackgroundResource(R.drawable.grey_border);
                } else {
                    ll_chk_3.setBackgroundResource(R.drawable.grey_border_grey_bg);
                }
            }
        });
        chk_4.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (chk_4.isChecked()) {
                    ll_chk_4.setBackgroundResource(R.drawable.grey_border);
                } else {
                    ll_chk_4.setBackgroundResource(R.drawable.grey_border_grey_bg);
                }
            }
        });

        edt_keyword.setOnEditorActionListener(new OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId,
                                          KeyEvent event) {
                // TODO Auto-generated method stub
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    // do here your stuff f

                    String keyword = edt_keyword.getText().toString().trim();
                    if (keyword.length() > 0) {

                        Intent intent = new Intent(context,
                                SearchResultListActivity.class);
                        intent.putExtra("keyword", keyword);

                        if (chk_1.isChecked() || chk_2.isChecked()
                                || chk_3.isChecked() || chk_4.isChecked()) {
                            // TODO Auto-generated method stub
                            StringBuffer sbf = new StringBuffer();
                            if (chk_1.isChecked())
                                sbf.append(
                                        txt_chk_1.getText().toString()
                                                .replaceAll("s", "")
                                                .toLowerCase()).append(",");
                            if (chk_2.isChecked())
                                sbf.append(
                                        txt_chk_2.getText().toString()
                                                .replaceAll("s", "")
                                                .toLowerCase()).append(",");
                            if (chk_3.isChecked())
                                sbf.append(
                                        txt_chk_3.getText().toString()
                                                .replaceAll("s", "")
                                                .toLowerCase()).append(",");
                            if (chk_4.isChecked())
                                sbf.append(txt_chk_4.getText().toString()
                                        .replaceAll("s", "").toLowerCase());

                            intent.putExtra("type", sbf.toString());

                        } else {
                            StringBuffer sbf = new StringBuffer();
                            sbf.append(
                                    txt_chk_1.getText().toString()
                                            .replaceAll("s", "").toLowerCase())
                                    .append(",");
                            sbf.append(
                                    txt_chk_2.getText().toString()
                                            .replaceAll("s", "").toLowerCase())
                                    .append(",");
                            sbf.append(
                                    txt_chk_3.getText().toString()
                                            .replaceAll("s", "").toLowerCase())
                                    .append(",");
                            sbf.append(txt_chk_4.getText().toString()
                                    .replaceAll("s", "").toLowerCase());

                            intent.putExtra("type", sbf.toString());

                        }

                        startActivity(intent);
                    } else
                        ScreenUtils.showToast(context, "Enter a valid keyword");

                    return true;
                }
                return false;
            }
        });
        btn_search.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                searchResult();

            }

            private void searchResult() {
                String keyword = edt_keyword.getText().toString().trim();
                if (keyword.length() > 0) {

                    Intent intent = new Intent(context,
                            SearchResultListActivity.class);
                    intent.putExtra("keyword", keyword);
                    if (chk_1.isChecked() || chk_2.isChecked()
                            || chk_3.isChecked() || chk_4.isChecked()) {

                        intent.putExtra("type",
                                returnCheckedText(chk_1, chk_2, chk_3, chk_4));

                    } else {
                        StringBuffer sbf = new StringBuffer();
                        sbf.append(
                                txt_chk_1.getText().toString()
                                        .replaceAll("s", "").toLowerCase())
                                .append(",");
                        sbf.append(
                                txt_chk_2.getText().toString()
                                        .replaceAll("s", "").toLowerCase())
                                .append(",");
                        sbf.append(
                                txt_chk_3.getText().toString()
                                        .replaceAll("s", "").toLowerCase())
                                .append(",");
                        sbf.append(txt_chk_4.getText().toString()
                                .replaceAll("s", "").toLowerCase());

                        intent.putExtra("type", sbf.toString());

                    }
                    startActivity(intent);
                } else
                    ScreenUtils.showToast(context, "Enter a valid keyword");
            }

            private String returnCheckedText(CheckBox chk_1, CheckBox chk_2,
                                             CheckBox chk_3, CheckBox chk_4) {
                // TODO Auto-generated method stub
                StringBuffer sbf = new StringBuffer();
                if (chk_1.isChecked())
                    sbf.append(
                            txt_chk_1.getText().toString().replaceAll("s", "")
                                    .toLowerCase()).append(",");
                if (chk_2.isChecked())
                    sbf.append(
                            txt_chk_2.getText().toString().replaceAll("s", "")
                                    .toLowerCase()).append(",");
                if (chk_3.isChecked())
                    sbf.append(
                            txt_chk_3.getText().toString().replaceAll("s", "")
                                    .toLowerCase()).append(",");
                if (chk_4.isChecked())
                    sbf.append(txt_chk_4.getText().toString()
                            .replaceAll("s", "").toLowerCase());
                return sbf.toString();
            }
        });

        ll_favourites.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                startActivity(new Intent(context, FavResultListActivity.class));

            }
        });

        ll_prd_search.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                startActivity(new Intent(context,
                        ProductCatgryListActivity.class));
            }
        });
        ll_prj_search.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                startActivity(new Intent(context,
                        ProjectCatgryListActivity.class));

            }
        });
        ll_how_to_videos.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                startActivity(new Intent(context, VideoListActivity.class));

            }
        });
        ll_calculators.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                startActivity(new Intent(context, CalculatorListActivity.class));

            }
        });
        ll_where_to_buy.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (locationAccepted) {
                    startActivity(new Intent(context, WhereToBuyActivity.class));
                } else {
                    askForPermission(android.Manifest.permission.ACCESS_FINE_LOCATION);
                }


            }
        });
        ll_call_support_header.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Utils.callThisNumber("1-800-282-5828", context);

            }
        });
        ll_app_sweep_takes_header.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(context, AppSweepTakesActivity.class);
                intent.putExtra("main", "1");
                startActivity(intent);
                finish();
            }
        });

        try {
            if (Utils.isWithinRange(Utils.getTodaysDateObj(), context)) {

                String regStatus = SharedPreferenceHelper.getPreference(
                        context,
                        SharedPreferenceHelper.APP_SWEEPSTAKES_REGISTERED);
                if ((regStatus != null && regStatus.equals("1"))) {

                    ll_app_sweep_takes_header.setVisibility(View.GONE);
                } else {
                    ll_app_sweep_takes_header.setVisibility(View.VISIBLE);
                }
            } else
                ll_app_sweep_takes_header.setVisibility(View.GONE);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

    }

    private void setTypeFace() {
        // TODO Auto-generated method stub
        txt_header_barcode.setTypeface(ScreenUtils.returnTypeFace(this));
        txt_header_favorites.setTypeface(ScreenUtils.returnTypeFace(this));
        txt_header_mappoint.setTypeface(ScreenUtils.returnTypeFace(this));
        txt_header_search.setTypeface(ScreenUtils.returnTypeFace(this));
        txt_header_menu.setTypeface(ScreenUtils.returnTypeFace(this));
    }

    void slideUp() {

        txt_header_search.setTextColor(getResources().getColor(
                R.color.home_icons_inactive));
        txt_header_menu.setTextColor(getResources().getColor(
                R.color.home_icons_inactive));

        Animation slideUp = AnimationUtils.loadAnimation(this, R.anim.slide_up);
        slideUp.setAnimationListener(new AnimationListener() {
            @Override
            public void onAnimationEnd(Animation animation) {
                ll_header_down.setVisibility(View.GONE);
                ll_header_up
                        .setBackgroundResource(R.drawable.trial_header_border_slide_up);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

            @Override
            public void onAnimationStart(Animation animation) {

            }
        });
        ll_header_down.startAnimation(slideUp);
        // ll_header_down.setForeground(getResources().getColor(R.color.YOUR_COLOR_NAME));
        // ll_shadow_layer.setAlpha(0.0f);

        changeColorGradually(android.R.color.black, android.R.color.transparent);
    }

    void slideDown(TextView txt_header_type) {

        Animation slide = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.slide_down);
        switch (txt_header_type.getId()) {
            case R.id.txt_header_search:

                img_header_menu_cross.setVisibility(View.GONE);
                linear_header_searchmenu.setVisibility(View.VISIBLE);
                linear_header_hammenu.setVisibility(View.INVISIBLE);

                ll_header_down.setVisibility(View.VISIBLE);
                ll_header_down.startAnimation(slide);
                // ll_header_down.setForeground(getResources().getColor(R.color.YOUR_COLOR_NAME));
                // ll_shadow_layer.setAlpha(0.9f);

                ll_header_up.setBackgroundResource(R.drawable.trial_header_border);

                changeColorGradually(android.R.color.transparent,
                        android.R.color.white);
                break;

            case R.id.ll_instagram:


            default:
                break;
        }

    }

    public void showPopUp(View view) {


//        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        LayoutInflater layoutInflater = getLayoutInflater();
//
//        //this is custom dialog
//        //custom_popup_dialog contains textview only
//        View customView = layoutInflater.inflate(R.layout.instapopup, null);
//        // reference the textview of custom_popup_dialog
//        txtsocialMedia = (TextView) view.findViewById(R.id.txtsocialMedia);
//
//        ll_social_media = (LinearLayout) view.findViewById(R.id.ll_social_media);
//        close = (ImageView) view.findViewById(R.id.close);
//
//        builder.setView(customView);
//        builder.create();
//        builder.show();

        myDialog.setContentView(R.layout.instapopup);
        close = (ImageView) myDialog.findViewById(R.id.close);
        txtsocialMedia = (TextView) myDialog.findViewById(R.id.txtsocialMedia);
        ll_social_media = (LinearLayout) myDialog.findViewById(R.id.ll_social_media);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
        myDialog.show();

    }

    public void slideDown() {
        Animation slide = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.slide_down);
        img_header_menu_cross.setVisibility(View.VISIBLE);
        linear_header_searchmenu.setVisibility(View.GONE);
        linear_header_hammenu.setVisibility(View.INVISIBLE);

        ll_header_down.setVisibility(View.VISIBLE);
        ll_header_down.startAnimation(slide);
        // ll_header_down.setForeground(getResources().getColor(R.color.YOUR_COLOR_NAME));
        // ll_shadow_layer.setAlpha(0.9f);

        ll_header_up.setBackgroundResource(R.drawable.trial_header_border);

        changeColorGradually(android.R.color.transparent,
                android.R.color.white);

    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();

        ll_header_up.post(new Runnable() {
            public void run() {
                int height = ll_header_up.getHeight();

                LayoutParams params = new LayoutParams(
                        LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                params.setMargins(0, height, 0, 0);
                img_header_menu_cross.setLayoutParams(params);
            }
        });

    }

    void changeColorGradually(int from, int to) {

        Integer colorFrom = getResources().getColor(from);
        Integer colorTo = getResources().getColor(to);
        ValueAnimator colorAnimation = ValueAnimator.ofObject(
                new ArgbEvaluator(), colorFrom, colorTo);
        colorAnimation.addUpdateListener(new AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                // ll_shadow_layer.setBackgroundColor((Integer) animator
                // .getAnimatedValue());
            }

        });
        colorAnimation.start();
    }

    public void onClickSearchProducts(View view) {

        startActivity(new Intent(MainActivity.this,
                ProductCatgryListActivity.class));

        Utils.getTracker("Search Products", getApplication());
    }

    public void onClickSearchProjects(View view) {
        startActivity(new Intent(MainActivity.this,
                ProjectCatgryListActivity.class));

        Utils.getTracker("Search Projects", getApplication());
    }

    public void onClickSearchVideos(View view) {
        startActivity(new Intent(MainActivity.this, VideoListActivity.class));

        Utils.getTracker("Search Videos", getApplication());
    }

    public void onClickCallNumber(View view) {

        if (callAccepted) {
            ll_quick_share.setBackgroundResource(R.drawable.homepageselectedborder);
            ll_instagram.setBackgroundResource(R.drawable.home_page_border);
            ll_call_support.setBackgroundResource(R.drawable.home_page_border);
            Utils.callThisNumber("1-800-282-5828", this);

            Utils.getTracker("Place Calls", getApplication());
        } else {
            requestPermissionCALL();
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void onClickQuickSnap(View view) {
        Log.e("QuikShare", "Quikshare");
        if (cameraAccepted) {

            ll_quick_share.setBackgroundResource(R.drawable.homepageselectedborder);
            ll_instagram.setBackgroundResource(R.drawable.home_page_border);
            ll_call_support.setBackgroundResource(R.drawable.home_page_border);
            startActivity(new Intent(MainActivity.this, ProjectShareActivity.class));
        } else {
            requestPermission();
        }
        Utils.getTracker("Facebook Upload", getApplication());
    }

    public void onClickWhereToBuy(View view) {


        startActivity(new Intent(MainActivity.this, WhereToBuyActivity.class));

        Utils.getTracker("Where to Buy", getApplication());
    }

    public void onClickCalculator(View view) {

        ll_qty_calc.setBackgroundResource(R.drawable.homepageselectedborder);
        ll_instagram.setBackgroundResource(R.drawable.home_page_border);
        ll_call_support.setBackgroundResource(R.drawable.home_page_border);
        ll_instagram.setBackgroundResource(R.drawable.home_page_border);
        startActivity(new Intent(MainActivity.this,
                CalculatorListActivity.class));

        Utils.getTracker("Calculators", getApplication());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        if (!(resultCode < 0)) {
            switch (requestCode) {
                case REQUEST_LOCATION:
                    switch (resultCode) {
                        case Activity.RESULT_OK: {

                            String contents = data.getStringExtra("SCAN_RESULT");
                            Log.d("newlog", "contents: " + contents);
                            Intent intent = new Intent(context, ProductDetailActivity.class);
                            intent.putExtra("type", "2");
                            intent.putExtra("cat_id", contents);
                            startActivity(intent);
                            // All required changes were successfully made
                            Toast.makeText(MainActivity.this, "Location enabled by user!", Toast.LENGTH_LONG).show();
                            break;
                        }
                        case Activity.RESULT_CANCELED: {
                            // The user was asked to change settings, but chose not to
                            Toast.makeText(MainActivity.this, "Location not enabled, user cancelled.", Toast.LENGTH_LONG).show();
                            break;
                        }
                        default: {
                            break;
                        }
                    }
            }

        } else {
            Toast.makeText(context, "No product found for this barcode", Toast.LENGTH_LONG).show();
        }
    }

    // void startAnim(){
    // TranslateAnimation translation;
    // translation = new TranslateAnimation(0f, 0F, 0f, getDisplayHeight());
    // translation.setStartOffset(500);
    // translation.setDuration(2000);
    // translation.setFillAfter(true);
    // translation.setInterpolator(new BounceInterpolator());
    // }

    public void onClickHomeMenu(View view) {

        //	onBackPressed();
        Log.e("onClickHomeMenu", "onBackPressed");
    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        //GoogleAnalytics.getInstance(MainActivity.this).reportActivityStart(this);
    }

    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
        //GoogleAnalytics.getInstance(MainActivity.this).reportActivityStop(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (ActivityCompat.checkSelfPermission(this, permissions[0]) == PackageManager.PERMISSION_GRANTED) {
            switch (requestCode) {
                case PERMISSION_REQUEST_CAMERA:
                    if (grantResults.length > 0) {

                        cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;

                        if (cameraAccepted) {
                            Intent intent = new Intent(MainActivity.this, ProjectShareActivity.class);
                            startActivity(intent);
                        }
                        // Snackbar.make(view, "Permission Granted, Now you can access location data and camera.", Snackbar.LENGTH_LONG).show();
                        else {

                            Toast.makeText(context, "Permission denied!!", Toast.LENGTH_LONG).show();

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                if (shouldShowRequestPermissionRationale(CAMERA)) {
                                    showMessageOK("Enable permission for camera to access",
                                            new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                        requestPermissions(new String[]{CAMERA},
                                                                PERMISSION_REQUEST_CAMERA);
                                                    }
                                                }
                                            });
                                    return;
                                }
                            }

                        }
                    }


                    break;

                case PERMISSION_REQUEST_LOCATION:
                    if (grantResults.length > 0) {

                        locationAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;

                        if (locationAccepted) {
                        }
                        // Snackbar.make(view, "Permission Granted, Now you can access location data and camera.", Snackbar.LENGTH_LONG).show();
                        else {

                            Toast.makeText(context, "Permission denied!!", Toast.LENGTH_LONG).show();

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                if (shouldShowRequestPermissionRationale(ACCESS_FINE_LOCATION)) {
                                    showMessageOK("Enable permission for location to access",
                                            new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                        requestPermissions(new String[]{ACCESS_FINE_LOCATION},
                                                                PERMISSION_REQUEST_LOCATION);
                                                    }
                                                }
                                            });
                                    return;
                                }
                            }

                        }
                    }
                    break;

                case PERMISSION_REQUEST_CALL: {

                    if (grantResults.length > 0) {

                        callAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;

                        if (callAccepted) {
                            Utils.callThisNumber("1-800-282-5828", this);

                            Utils.getTracker("Place Calls", getApplication());
                        }
                        // Snackbar.make(view, "Permission Granted, Now you can access location data and camera.", Snackbar.LENGTH_LONG).show();
                        else {

                            Toast.makeText(context, "Permission denied!!", Toast.LENGTH_LONG).show();

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                if (shouldShowRequestPermissionRationale(CAMERA)) {
                                    showMessageOK("Enable permission for call to access",
                                            new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                        requestPermissions(new String[]{CALL_PHONE},
                                                                PERMISSION_REQUEST_CALL);
                                                    }
                                                }
                                            });
                                }
                            }

                        }
                    }
                }


            }
            //Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            locationAccepted = false;

        }

    }
//    @Override
//    public void onRequestPermissionsResult(int requestCode,
//                                           @NonNull String[] permissions,
//                                           @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        switch (requestCode) {
//            case PermissionsUtils.PERMISSION_ALL: {
//
//                if (grantResults.length > 0) {
//
//                    List<Integer> indexesOfPermissionsNeededToShow = new ArrayList<>();
//
//                    for(int i = 0; i < permissions.length; ++i) {
//                        if(ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[i])) {
//                            indexesOfPermissionsNeededToShow.add(i);
//                        }
//                    }
//
//                    int size = indexesOfPermissionsNeededToShow.size();
//                    if(size != 0) {
//                        int i = 0;
//                        boolean isPermissionGranted = true;
//
//                        while(i < size && isPermissionGranted) {
//                            isPermissionGranted = grantResults[indexesOfPermissionsNeededToShow.get(i)]
//                                    == PackageManager.PERMISSION_GRANTED;
//                            i++;
//                        }
//
//                        if(!isPermissionGranted) {
//
//                            showMessageOK("Enable permission for call to access",
//                                    new DialogInterface.OnClickListener() {
//                                        @Override
//                                        public void onClick(DialogInterface dialog, int which) {
//                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                                                requestPermissions(new String[]{CALL_PHONE},
//                                                        PERMISSION_REQUEST_CALL);
//                                            }
//                                        }
//                                    });
//                            return;
//                        }
//                    }
//                }
//
//
//            }
//
//
//        }
//    }

    private void showMessageOK(String message, DialogInterface.OnClickListener okListener) {
        new androidx.appcompat.app.AlertDialog.Builder(MainActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .create()
                .show();
    }


    private void requestPermission() {

        ActivityCompat.requestPermissions(this, new String[]{CAMERA}, PERMISSION_REQUEST_CAMERA);

    }


    private void requestPermissionLocation() {

        ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_LOCATION);

    }

    private void requestPermissionCALL() {

        ActivityCompat.requestPermissions(this, new String[]{CALL_PHONE}, PERMISSION_REQUEST_CALL);

    }


    private void checkPermissions() {
        PermissionsUtils.askPermissions(this);
    }


    @SuppressLint("StaticFieldLeak")
    public void getSocialMedia() {
        try {

            new AsyncTask<Void, Void, GetSocialMedia>() {

                @Override
                protected void onPreExecute() {


                }

                @Override
                protected GetSocialMedia doInBackground(Void... params) {
                    // TODO Auto-generated method stub
                    try {
                        List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();

                        String url = WebServiceUtil.URL
                                + WebServiceUtil.METHOD_SOCIAL_MEDIA;

                        String result = WebServiceUtil.getPostResponce(nameValuePair,
                                url);

                        Log.e("Pavestone Result--", "Response " + result);

                        Gson gson = new Gson();


                        java.io.Reader reader = new InputStreamReader(
                                WebServiceUtil.convertStringToInputstream(result));


                        response = gson.fromJson(reader,
                                GetSocialMedia.class);
                        responseBody = response.toString();

                        responseBody = String.valueOf(gson.fromJson(reader, GetSocialMedia.class));

                        Log.e("Pavestone--", "Response " + responseBody);


                        return response;
                    } catch (Exception e) {
                        // TODO: handle exception
                    }

                    return null;
                }

                @Override
                protected void onPostExecute(GetSocialMedia result) {

                    try {

                        if (result != null) {

                            String value = response.getMessage();
                            //  txtsocialMedia.setText(value);

                            Pattern hashtagPattern = Pattern.compile("(#[A-Za-z0-9_-]+)");
                            Pattern urlPattern = Patterns.WEB_URL;

                            StringBuffer sb = new StringBuffer(value.length());
                            Matcher o = hashtagPattern.matcher(value);

                            while (o.find()) {
                                o.appendReplacement(sb, "<font color=\"#f8ac40\">" + o.group(1) + "</font>");
                            }
                            o.appendTail(sb);


                            Matcher m = urlPattern.matcher(sb.toString());
                            sb = new StringBuffer(sb.length());

                            while (m.find()) {
                                m.appendReplacement(sb, "<font color=\"#f8ac40\">" + m.group(1) + "</f ont>");
                            }
                            m.appendTail(sb);

                            txtsocialMedia.setText(Html.fromHtml(sb.toString()));

                            Log.e("JSON RESPONSE ", "Value--" + value);


//                            EpaLog.e(TAG, "appLists" + appLists.toString());
                            // ll_social_media.setVisibility(View.VISIBLE);

                            String txtValue = txtsocialMedia.getText().toString();

                            SharedPreferenceHelper.savePreferences(SharedPreferenceHelper.SOCIALMESSAGE, txtValue, context);


                        } else {
                            Toast.makeText(context, "No result from social Media", Toast.LENGTH_LONG).show();
                        }

                    } catch (Exception e) {
                        // TODO: handle exception
                        // Toast.makeText(context,"No result from social Media",Toast.LENGTH_LONG).show();

                    }

                }

                ;

            }.execute();


        } catch (Exception e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        }

    }


    @Override
    public void onConnected(Bundle bundle) {

        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(30 * 1000);
        mLocationRequest.setFastestInterval(5 * 1000);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);
        builder.setAlwaysShow(true);

        result = LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, builder.build());

        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                //final LocationSettingsStates state = result.getLocationSettingsStates();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        // All location settings are satisfied. The client can initialize location
                        // requests here.
                        //...
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        // Location settings are not satisfied. But could be fixed by showing the user
                        // a dialog.
                        try {
                            // Show the dialog by calling startResolutionForResult(),
                            // and check the result in onActivityResult().
                            status.startResolutionForResult(
                                    MainActivity.this,
                                    REQUEST_LOCATION);
                        } catch (IntentSender.SendIntentException e) {
                            // Ignore the error.
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        // Location settings are not satisfied. However, we have no way to fix the
                        // settings so we won't show the dialog.
                        //...
                        break;
                }
            }
        });

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }


    private void askForPermission(String permission) {
        if (ContextCompat.checkSelfPermission(MainActivity.this, permission) != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, permission)) {

                //This is called if user has denied the permission before
                //In this case I am just asking the permission again
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{permission}, PERMISSION_REQUEST_LOCATION);

            } else {

                ActivityCompat.requestPermissions(MainActivity.this, new String[]{permission}, PERMISSION_REQUEST_LOCATION);
            }
        } else {
            Toast.makeText(this, "" + permission + " is already granted.", Toast.LENGTH_SHORT).show();
        }
    }


}

