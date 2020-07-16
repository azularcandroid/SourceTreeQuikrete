package com.quikrete;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.util.Patterns;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.gson.Gson;
import com.journeyapps.barcodescanner.CaptureActivity;
import com.quikrete.data.FavResult;
import com.quikrete.gsondata.videocat.Alphabetical;
import com.quikrete.gsondata.videocat.Calculator;
import com.quikrete.gsondata.videocat.Related;
import com.quikrete.gsondata.videocat.Video;
import com.quikrete.gsondata.videocat.VideoDetailsData;
import com.quikrete.utils.AccordionView;
import com.quikrete.utils.ScreenUtils;
import com.quikrete.utils.SegmentedGroup;
import com.quikrete.utils.Utils;
import com.quikrete.utils.WebServiceUtil;
import com.quikrete.youtubeapi.DeveloperKey;
import com.quikrete.youtubeapi.YouTubeFailureRecoveryActivity;
import com.sharedpreference.SharedPreferenceHelper;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class VideoDetailActivity extends YouTubeFailureRecoveryActivity
        implements View.OnClickListener, YouTubePlayer.OnFullscreenListener {
    LinearLayout ll_header_down, ll_header_up, ll_shadow_layer;
    ImageView img_header_menu_cross, img_header_menu_cross_1;
    RelativeLayout rel_bg_home;

    TextView txt_header_barcode, txt_header_mappoint, txt_header_search,
            txt_header_menu, txt_header_favorites;

    LinearLayout linear_header_searchmenu, linear_header_hammenu;
    TextView txt_star, txt_download, txt_send;

    LinearLayout ll_overview, ll_techinfo;
    LinearLayout ll1, ll2, ll_parent, ll3, ll4, ll5;
    LinearLayout ll_main_header;

    private YouTubePlayerView playerView;
    private YouTubePlayer player;
    private boolean fullscreen;

    Context context;

    int radioGroup;
    private static final int RECOVERY_DIALOG_REQUEST = 1;

    public static String title = "", youtubeUrl = "", stepByStep = "",
            toolsAndMaterials = "";

    TextView txt_prd_title;
    WebView txt_step_by_step;
    WebView txt_tools_materls;
    TextView txt_add_to_fav;

    String id = "";

    LinearLayout ll_favourites, ll_prd_search, ll_prj_search, ll_how_to_videos,
            ll_calculators, ll_where_to_buy, ll_call_support_header;
    EditText edt_keyword;
    Button btn_search;
    CheckBox chk_1, chk_2, chk_3, chk_4;
    TextView txt_chk_1, txt_chk_2, txt_chk_3, txt_chk_4;
    LinearLayout ll_chk_1, ll_chk_2, ll_chk_3, ll_chk_4;

    AccordionView accordion_view_1;
    LinearLayout accrdn_ll_1, accrdn_ll_2, accrdn_ll_4, accrdn_ll_3;

    private static final String TAG = "VideoDetailActvity";


    String socialMessage = "";
    private LinearLayout llProducts;
    private LinearLayout llVideos;
    private LinearLayout llCalculator;
    private LinearLayout llProjects;
    private LinearLayout llBuy;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = this;
        ScreenUtils.fullScreen(this);
        setContentView(R.layout.activity_video_detail);

        View headerView = findViewById(R.id.header2);

        initHeaderWidgets(headerView);
        findViews();


        socialMessage = SharedPreferenceHelper.getPreference(
                context,
                SharedPreferenceHelper.SOCIALMESSAGE);

        Pattern hashtagPattern = Pattern.compile("(#[A-Za-z0-9_-]+)");
        Pattern urlPattern = Patterns.WEB_URL;

        StringBuffer sb = new StringBuffer(socialMessage.length());
        Matcher o = hashtagPattern.matcher(socialMessage);

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


        Log.e("Value--,", "SocialMessage-- " + socialMessage);
        llProducts.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                llProducts.setBackgroundResource(R.drawable.selectedbackground);
                llVideos.setBackgroundResource(R.drawable.background);
                llCalculator.setBackgroundResource(R.drawable.background);
                llProjects.setBackgroundResource(R.drawable.background);
                llBuy.setBackgroundResource(R.drawable.background);


                startActivity(new Intent(context, ProductCatgryListActivity.class));
            }
        });
        llCalculator.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                llCalculator.setBackgroundResource(R.drawable.selectedbackground);
                llVideos.setBackgroundResource(R.drawable.background);
                llProducts.setBackgroundResource(R.drawable.background);
                llProjects.setBackgroundResource(R.drawable.background);
                llBuy.setBackgroundResource(R.drawable.background);
                startActivity(new Intent(context,
                        CalculatorListActivity.class));
            }
        });
        llVideos.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                llVideos.setBackgroundResource(R.drawable.selectedbackground);
                llProducts.setBackgroundResource(R.drawable.background);
                llCalculator.setBackgroundResource(R.drawable.background);
                llProjects.setBackgroundResource(R.drawable.background);
                llBuy.setBackgroundResource(R.drawable.background);
                startActivity(new Intent(context, VideoListActivity.class));
            }
        });
        llProjects.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                llProjects.setBackgroundResource(R.drawable.selectedbackground);
                llVideos.setBackgroundResource(R.drawable.background);
                llCalculator.setBackgroundResource(R.drawable.background);
                llProducts.setBackgroundResource(R.drawable.background);
                llBuy.setBackgroundResource(R.drawable.background);

                startActivity(new Intent(context,
                        ProjectCatgryListActivity.class));
            }
        });
        llBuy.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                llBuy.setBackgroundResource(R.drawable.selectedbackground);
                llVideos.setBackgroundResource(R.drawable.background);
                llCalculator.setBackgroundResource(R.drawable.background);
                llProjects.setBackgroundResource(R.drawable.background);
                llProducts.setBackgroundResource(R.drawable.background);

                startActivity(new Intent(context,
                        WhereToBuyActivity.class));
            }
        });

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
        txt_header_menu.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                slideDown(txt_header_menu);

                txt_header_search.setTextColor(getResources().getColor(
                        R.color.home_icons_inactive));
                txt_header_menu.setTextColor(getResources().getColor(
                        R.color.home_icons_active));
            }
        });

        img_header_menu_cross.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                slideUp();
            }
        });
        img_header_menu_cross_1.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                slideUp();
            }
        });

        ll_header_up = (LinearLayout) headerView
                .findViewById(R.id.ll_header_up);

        rel_bg_home = (RelativeLayout) findViewById(R.id.rel_bg_home);
        ll_shadow_layer = (LinearLayout) findViewById(R.id.ll_shadow_layer);
        // ll_shadow_layer.setAlpha(0.0f);
        txt_star = (TextView) findViewById(R.id.txt_star);
        txt_download = (TextView) findViewById(R.id.txt_download);
        txt_send = (TextView) findViewById(R.id.txt_send);
        ll_overview = (LinearLayout) findViewById(R.id.ll_overview);
        ll_techinfo = (LinearLayout) findViewById(R.id.ll_techinfo);
        ll1 = (LinearLayout) findViewById(R.id.ll1);
        ll2 = (LinearLayout) findViewById(R.id.ll2);
        ll_main_header = (LinearLayout) findViewById(R.id.ll_main_header);
        ll_parent = (LinearLayout) findViewById(R.id.ll_parent);
        ll3 = (LinearLayout) findViewById(R.id.ll3);
        ll4 = (LinearLayout) findViewById(R.id.ll4);
        //	ll5 = (LinearLayout) findViewById(R.id.ll5);

        setTypeFace();

        SegmentedGroup segmented = (SegmentedGroup) findViewById(R.id.segmented2);
        segmented.setTintColor(Color.parseColor("#FFECECEC"),
                Color.parseColor("#FF4D4D4D"));

        segmented.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub
                switch (checkedId) {
                    case R.id.button21:
                        radioGroup = 0;
                        ll_overview.setVisibility(View.VISIBLE);
                        ll_techinfo.setVisibility(View.GONE);
                        break;
                    case R.id.button22:
                        radioGroup = 1;
                        ll_overview.setVisibility(View.GONE);
                        ll_techinfo.setVisibility(View.VISIBLE);
                        break;

                    default:
                        break;
                }

            }
        });

        txt_prd_title = (TextView) findViewById(R.id.txt_prd_title);
        txt_step_by_step = (WebView) findViewById(R.id.web_step_by_step);
        txt_tools_materls = (WebView) findViewById(R.id.web_tools);
        txt_add_to_fav = (TextView) findViewById(R.id.txt_add_to_fav);

        id = getIntent().getExtras().getString("id");

        if (Utils.isNetworkAvailable(context))
            new AsyncLoadVideoDetails().execute(new String[]{id});
        else
            ScreenUtils.showToast(context,
                    getResources().getString(R.string.no_internet_message));

        ScreenUtils.changeStarColor(id, txt_star, txt_add_to_fav, context);

        accordion_view_1 = (AccordionView) findViewById(R.id.accordion_view_1);

        try {
            accrdn_ll_1 = (LinearLayout) findViewById(R.id.accrdn_ll_1);
            accrdn_ll_2 = (LinearLayout) findViewById(R.id.accrdn_ll_2);
            accrdn_ll_3 = (LinearLayout) findViewById(R.id.accrdn_ll_3);
            accrdn_ll_4 = (LinearLayout) findViewById(R.id.accrdn_ll_4);
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    private void initYouTubePlugins() {
        // TODO Auto-generated method stub
        playerView = findViewById(R.id.player);
        playerView.initialize(DeveloperKey.DEVELOPER_KEY, this);
    }

    private void findViews() {
        llProducts = (LinearLayout) findViewById(R.id.llProducts);
        llVideos = (LinearLayout) findViewById(R.id.llVideos);
        llCalculator = (LinearLayout) findViewById(R.id.llCalculator);
        llProjects = (LinearLayout) findViewById(R.id.llProjects);
        llBuy = (LinearLayout) findViewById(R.id.llBuy);
    }


    private void initHeaderWidgets(View headerView) {
        // TODO Auto-generated method stub
        ll_header_down = (LinearLayout) headerView
                .findViewById(R.id.ll_header_down);

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
                startActivity(new Intent(context, WhereToBuyActivity.class));

            }
        });
        ll_call_support_header.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Utils.callThisNumber("1-800-282-5828", context);

            }
        });

        LinearLayout ll_app_sweep_takes_header = (LinearLayout) headerView
                .findViewById(R.id.ll_app_sweep_takes_header);
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

        txt_star.setTypeface(ScreenUtils.returnTypeFace(this));
        txt_download.setTypeface(ScreenUtils.returnTypeFace(this));
        txt_send.setTypeface(ScreenUtils.returnTypeFace(this));
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
        ll_shadow_layer.setVisibility(View.GONE);
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
                ll_shadow_layer.setVisibility(View.VISIBLE);
                ll_shadow_layer.setAlpha(0.9f);

                ll_header_up.setBackgroundResource(R.drawable.trial_header_border);

                changeColorGradually(android.R.color.transparent,
                        android.R.color.black);
                break;

            case R.id.txt_header_menu:


                img_header_menu_cross.setVisibility(View.VISIBLE);
                linear_header_searchmenu.setVisibility(View.INVISIBLE);
                linear_header_hammenu.setVisibility(View.VISIBLE);

                ll_header_down.setVisibility(View.VISIBLE);
                ll_header_down.startAnimation(slide);
                // ll_header_down.setForeground(getResources().getColor(R.color.YOUR_COLOR_NAME));
                ll_shadow_layer.setVisibility(View.VISIBLE);
                ll_shadow_layer.setAlpha(0.9f);

                ll_header_up.setBackgroundResource(R.drawable.trial_header_border);

                changeColorGradually(android.R.color.transparent,
                        android.R.color.black);
                break;

            default:
                break;
        }

    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();

        ll_header_up.post(new Runnable() {
            public void run() {
                int height = ll_header_up.getHeight();
                Log.e("newlog", height + "---");
                LayoutParams params = new LayoutParams(
                        LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                params.setMargins(0, height, 0, 0);
                img_header_menu_cross.setLayoutParams(params);
            }
        });
        llVideos.setBackgroundResource(R.drawable.selectedbackground);
        llProjects.setBackgroundResource(R.drawable.background);
        llCalculator.setBackgroundResource(R.drawable.background);
        llProducts.setBackgroundResource(R.drawable.background);
        llBuy.setBackgroundResource(R.drawable.background);

    }

    void changeColorGradually(int from, int to) {

        Integer colorFrom = getResources().getColor(from);
        Integer colorTo = getResources().getColor(to);
        ValueAnimator colorAnimation = ValueAnimator.ofObject(
                new ArgbEvaluator(), colorFrom, colorTo);
        colorAnimation.addUpdateListener(new AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                ll_shadow_layer.setBackgroundColor((Integer) animator
                        .getAnimatedValue());
            }

        });
        colorAnimation.start();
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                        YouTubePlayer player, boolean wasRestored) {
        this.player = player;
        // Specify that we want to handle fullscreen behavior ourselves.
        player.addFullscreenControlFlag(YouTubePlayer.FULLSCREEN_FLAG_CUSTOM_LAYOUT);
        player.setOnFullscreenListener(this);

        if (!wasRestored) {
            player.cueVideo(youtubeUrl);
            //
            // m4UviyTKH40
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                        YouTubeInitializationResult errorReason) {
        if (errorReason.isUserRecoverableError()) {
            errorReason.getErrorDialog(this, RECOVERY_DIALOG_REQUEST).show();
        } else {
            @SuppressLint({"StringFormatInvalid", "LocalSuppress"})
            String errorMessage = String.format(
                    getString(R.string.error_player), errorReason.toString());
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected YouTubePlayer.Provider getYouTubePlayerProvider() {
        return playerView;
    }

    @Override
    public void onClick(View v) {
        player.setFullscreen(!fullscreen);
    }

    private void doLayout() {
        LinearLayout.LayoutParams playerParams = (LinearLayout.LayoutParams) playerView
                .getLayoutParams();
        if (fullscreen) {
            // When in fullscreen, the visibility of all other views than the
            // player should be set to
            // GONE and the player should be laid out across the whole screen.
            playerParams.width = LayoutParams.MATCH_PARENT;
            playerParams.height = LayoutParams.MATCH_PARENT;

            ll_overview.setVisibility(View.GONE);
            ll_techinfo.setVisibility(View.GONE);
            ll1.setVisibility(View.GONE);
            ll2.setVisibility(View.GONE);
            ll_main_header.setVisibility(View.GONE);

        } else {
            // This layout is up to you - this is just a simple example
            // (vertically stacked boxes in
            // portrait, horizontally stacked in landscape).

            if (radioGroup == 0) {
                ll_overview.setVisibility(View.VISIBLE);
                ll_techinfo.setVisibility(View.GONE);
            } else {
                ll_overview.setVisibility(View.GONE);
                ll_techinfo.setVisibility(View.VISIBLE);
            }

            ll1.setVisibility(View.VISIBLE);
            ll2.setVisibility(View.VISIBLE);
            ll_main_header.setVisibility(View.VISIBLE);
        }

        try {
            setTextDetails();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void landscapeCondition() {
        LinearLayout.LayoutParams playerParams = (LinearLayout.LayoutParams) playerView
                .getLayoutParams();
        playerParams.width = LayoutParams.MATCH_PARENT;
        playerParams.height = LayoutParams.MATCH_PARENT;

        ll_overview.setVisibility(View.GONE);
        ll_techinfo.setVisibility(View.GONE);
        ll1.setVisibility(View.GONE);
        ll2.setVisibility(View.GONE);
        ll_main_header.setVisibility(View.GONE);
    }

    private void portraitCondition() {

        LinearLayout.LayoutParams playerParams = (LinearLayout.LayoutParams) playerView
                .getLayoutParams();
        playerParams.width = LayoutParams.MATCH_PARENT;
        playerParams.height = (int) ScreenUtils.returnPixel(context, 180);
        playerParams.setMargins((int) ScreenUtils.returnPixel(context, 10),
                (int) ScreenUtils.returnPixel(context, 10),
                (int) ScreenUtils.returnPixel(context, 10),
                (int) ScreenUtils.returnPixel(context, 10));

        if (radioGroup == 0) {
            ll_overview.setVisibility(View.VISIBLE);
            ll_techinfo.setVisibility(View.GONE);
        } else {
            ll_overview.setVisibility(View.GONE);
            ll_techinfo.setVisibility(View.VISIBLE);
        }

        ll1.setVisibility(View.VISIBLE);
        ll2.setVisibility(View.VISIBLE);
        ll_main_header.setVisibility(View.VISIBLE);
    }

    @Override
    public void onFullscreen(boolean isFullscreen) {
        fullscreen = isFullscreen;
        Log.e("newlog", "fullscreen---" + fullscreen);
        if (fullscreen) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        }
        doLayout();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        doLayout();
        Log.e("newlog", "onConfigurationChanged---" + newConfig.orientation);
        switch (newConfig.orientation) {
            case Configuration.ORIENTATION_LANDSCAPE:
                fullscreen = true;
                landscapeCondition();
                setmarginsToParentLayout(new float[]{0, 0, 0, 0});

                // changeWeight(ll3, 0);
                // changeWeight(ll4, 1);
                // changeWeight(ll5, 0);
                break;
            case Configuration.ORIENTATION_PORTRAIT:

                fullscreen = false;
                portraitCondition();
                setmarginsToParentLayout(new float[]{returnPixels(8),
                        returnPixels(8), returnPixels(8), returnPixels(8)});

                // changeWeight(ll3, 0.13f);
                // changeWeight(ll4, 0.86f);
                // changeWeight(ll5, 0.01f);
                break;

            default:
                break;
        }
    }

    void setmarginsToParentLayout(final float param[]) {

        new Handler().post(new Runnable() {
            @Override
            public void run() {

                LinearLayout.LayoutParams playerParams = (LinearLayout.LayoutParams) ll4
                        .getLayoutParams();
                playerParams.leftMargin = (int) param[0];
                playerParams.topMargin = (int) param[1];
                playerParams.rightMargin = (int) param[2];
                playerParams.bottomMargin = (int) param[3];
            }
        });

    }

    void changeWeight(final LinearLayout ll, final float weight) {
        new Handler().post(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub

                LinearLayout.LayoutParams playerParams = (LinearLayout.LayoutParams) ll
                        .getLayoutParams();
                playerParams.weight = weight;
            }
        });

    }

    private float returnPixels(int dpVal) {
        Resources r = getResources();
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, r.getDisplayMetrics());
        Log.e("newlog", dpVal + "---" + px + "---px");
        return px;
    }

    class AsyncLoadVideoDetails extends
            AsyncTask<String, String, VideoDetailsData> {

        ProgressDialog pdialog;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            pdialog = ScreenUtils.returnProgDialogObj(context,
                    "Loading details...please wait", false);
            pdialog.show();
            super.onPreExecute();
        }

        @Override
        protected VideoDetailsData doInBackground(String... params) {
            // TODO Auto-generated method stub
            try {

                List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
                nameValuePair.add(new BasicNameValuePair(
                        WebServiceUtil.METHOD_3_2_GET_VIDEO_LIST_DETAILS_ID,
                        params[0]));

                String url = WebServiceUtil.URL
                        + WebServiceUtil.METHOD_3_1_GET_VIDEO_LIST_DETAILS;

                String result = WebServiceUtil.getPostResponce(nameValuePair,
                        url);

                String temp1 = "\"product\":{\"popularity\":[],\"alphabetical\":[]}";
                String replacer1 = "\"product\":[\"No Result\"]";

                result = result.replaceAll(Pattern.quote(replacer1), temp1);

                String temp2 = "\"project\":{\"popularity\":[],\"alphabetical\":[]}";
                String replacer2 = "\"project\":[\"No Result\"]";

                result = result.replaceAll(Pattern.quote(replacer2), temp2);

                String replacer = "\"No Result\"";
                result = result.replaceAll(replacer, "");
                // Utils.saveInSdcard(result);

                Gson gson = new Gson();
                Reader reader = new InputStreamReader(
                        WebServiceUtil.convertStringToInputstream(result));
                VideoDetailsData response = gson.fromJson(reader,
                        VideoDetailsData.class);

                return response;

            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(VideoDetailsData result) {
            // TODO Auto-generated method stub
            try {
                pdialog.dismiss();
            } catch (Exception e) {
                // TODO: handle exception
            }
            if (result != null) {

                String replacement = "https:\\/\\/www.youtube.com\\/watch\\?v=";
                String replacement1 = "https:\\/\\/www.youtube.com\\/watch\\?time_continue=13&v=";
                try {
                    title = result.getTitle();
                    if (!result.getYoutubeUrl().contains("time_continue")) {
                        youtubeUrl = result.getYoutubeUrl().replaceAll(replacement,
                                "");
                    } else {
                        youtubeUrl = result.getYoutubeUrl().replaceAll(replacement1,
                                "").trim();
                    }
                    stepByStep = result.getStepByStep();
                    toolsAndMaterials = result.getToolsAndMaterials();

                    Log.e(TAG, "Tools&Materials--" + result.getToolsAndMaterials());

                    txt_prd_title.setText(Html.fromHtml(title));
                    txt_step_by_step.loadDataWithBaseURL(null, "<html><body>"
                                    + stepByStep + "</body></html>", "text/html", "utf-8",
                            null);
                    txt_tools_materls.loadDataWithBaseURL(null, "<html><body>"
                                    + toolsAndMaterials + "</body></html>", "text/html", "utf-8",
                            null);
                    initYouTubePlugins();
                    updateAccordians(result.getRelated());

                } catch (Exception e) {
                    // TODO: handle exception
                }

            }

            super.onPostExecute(result);
        }
    }

    private void updateAccordians(Related related) {
        // TODO Auto-generated method stub
        try {
            for (Alphabetical obj : related.getProduct().getAlphabetical()) {
                accrdn_ll_1.addView(returnLayoutProduct(obj));
            }
            int size = related.getProduct().getAlphabetical().size();
            if (size == 0)
                accordion_view_1.setSectionVisibility(0);
        } catch (Exception e) {
            // TODO: handle exception
            int size = related.getProduct().getAlphabetical().size();
            if (size == 0)
                accordion_view_1.setSectionVisibility(0);
        }
        try {
            for (Alphabetical obj : related.getProject().getAlphabetical()) {
                accrdn_ll_2.addView(returnLayoutProject(obj));
            }
            int size = related.getProject().getAlphabetical().size();
            if (size == 0)
                accordion_view_1.setSectionVisibility(1);
        } catch (Exception e) {
            // TODO: handle exception
            int size = related.getProject().getAlphabetical().size();
            if (size == 0)
                accordion_view_1.setSectionVisibility(1);
        }
        try {
            for (com.quikrete.gsondata.videocat.Video obj : related.getVideo()) {
                accrdn_ll_4.addView(returnLayoutForVideo(obj));
            }
            int size = related.getVideo().size();
            if (size == 0)
                accordion_view_1.setSectionVisibility(2);
        } catch (Exception e) {
            // TODO: handle exception
            int size = related.getVideo().size();
            if (size == 0)
                accordion_view_1.setSectionVisibility(2);
        }
        try {
            for (com.quikrete.gsondata.videocat.Calculator obj : related
                    .getCalculator()) {
                accrdn_ll_3.addView(returnLayout(obj));
            }
            int size = related.getCalculator().size();
            if (size == 0)
                accordion_view_1.setSectionVisibility(3);
        } catch (Exception e) {
            // TODO: handle exception
            int size = related.getCalculator().size();
            if (size == 0)
                accordion_view_1.setSectionVisibility(3);
        }

    }

    private LinearLayout returnLayoutProduct(final Alphabetical info) {

        final LinearLayout lytContainer = (LinearLayout) View.inflate(context,
                R.layout.layout_accordian_rows, null);

        TextView txt_value = (TextView) lytContainer
                .findViewById(R.id.txt_accrdn_content);

        txt_value.setText(info.getTitle() + "");

        lytContainer.setTag(info);

        lytContainer.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(context, ProductDetailActivity.class);
                intent.putExtra("id", info.getId());
                intent.putExtra("type", "0");
                startActivity(intent);
            }
        });
        return lytContainer;

    }

    private LinearLayout returnLayoutProject(final Alphabetical info) {

        final LinearLayout lytContainer = (LinearLayout) View.inflate(context,
                R.layout.layout_accordian_rows, null);

        TextView txt_value = (TextView) lytContainer
                .findViewById(R.id.txt_accrdn_content);

        txt_value.setText(info.getTitle() + "");

        lytContainer.setTag(info);

        lytContainer.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(context, ProjectDetailActivity.class);
                intent.putExtra("id", info.getId());
                intent.putExtra("type", "1");
                startActivity(intent);
            }
        });
        return lytContainer;

    }

    private LinearLayout returnLayout(final Calculator info) {

        final LinearLayout lytContainer = (LinearLayout) View.inflate(context,
                R.layout.layout_accordian_rows, null);

        TextView txt_value = (TextView) lytContainer
                .findViewById(R.id.txt_accrdn_content);

        txt_value.setText(info.getTitle() + "");

        lytContainer.setTag(info);

        lytContainer.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(context, CalculatorActivity.class);
                intent.putExtra("id", info.getId());
                intent.putExtra("title", info.getTitle());
                startActivity(intent);
            }
        });

        return lytContainer;

    }

    private LinearLayout returnLayoutForVideo(final Video info) {

        final LinearLayout lytContainer = (LinearLayout) View.inflate(context,
                R.layout.layout_accordian_rows, null);

        TextView txt_value = (TextView) lytContainer
                .findViewById(R.id.txt_accrdn_content);

        txt_value.setText(info.getTitle() + "");

        lytContainer.setTag(info);

        lytContainer.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(context, VideoDetailActivity.class);
                intent.putExtra("id", info.getId());
                startActivity(intent);
            }
        });

        return lytContainer;

    }

    void setTextDetails() throws Exception {
        txt_prd_title.setText(Html.fromHtml(title));
        txt_step_by_step.loadDataWithBaseURL(null, "<html><body>"
                        + stepByStep + "</body></html>", "text/html", "utf-8",
                null);

        //txt_tools_materls.setText(Html.fromHtml(Html.fromHtml(toolsAndMaterials).toString()));
        txt_tools_materls.loadDataWithBaseURL(null, "<html><body>"
                        + toolsAndMaterials + "</body></html>", "text/html", "utf-8",
                null);
    }

    @SuppressLint("StaticFieldLeak")
    public void onClickAddToFav(View view) {

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
                                ScreenUtils.changeStarColor(id, txt_star,
                                        txt_add_to_fav, context);
                            } else {
                                SharedPreferenceHelper.savePreferences(id, "1",
                                        context);
                                ScreenUtils.showToast(context,
                                        "Item added to Favourites");
                                ScreenUtils.changeStarColor(id, txt_star,
                                        txt_add_to_fav, context);
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

    public void onClickRelated1(View view) {

        Intent intent = new Intent(context, ProductListActivity.class);
        intent.putExtra("related", "product");
        intent.putExtra("related_for", id);
        intent.putExtra("type", "0");
        startActivity(intent);
    }

    public void onClickRelated2(View view) {

        Intent intent = new Intent(context, ProjectListActivity.class);
        intent.putExtra("related", "project");
        intent.putExtra("related_for", id);
        intent.putExtra("type", "1");
        startActivity(intent);
    }

    public void onClickRelated3(View view) {

        Intent intent = new Intent(context, CalculatorListActivity.class);
        intent.putExtra("related", "product");
        intent.putExtra("related_for", id);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                String contents = data.getStringExtra("SCAN_RESULT");
                Log.d("newlog", "contents: " + contents);
                Intent intent = new Intent(context, ProductDetailActivity.class);
                intent.putExtra("type", "2");
                intent.putExtra("cat_id", contents);
                startActivity(intent);
            } else if (resultCode == RESULT_CANCELED) {
                // Handle cancel
                Log.d("newlog", "RESULT_CANCELED");
            }
        }
    }

    public void onClickHomeMenu(View view) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
