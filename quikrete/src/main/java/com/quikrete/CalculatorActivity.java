package com.quikrete;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
import android.webkit.WebView;
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

import com.google.gson.Gson;

import com.journeyapps.barcodescanner.CaptureActivity;
import com.quikrete.data.FavData;
import com.quikrete.data.FavResult;
import com.quikrete.data.GetSocialMedia;
import com.quikrete.gsondata.calc.Alphabetical;
import com.quikrete.gsondata.calc.Calculator;
import com.quikrete.gsondata.calc.CalculatorDetail;
import com.quikrete.gsondata.calc.Option;
import com.quikrete.gsondata.calc.Related;
import com.quikrete.gsondata.calc.Result;
import com.quikrete.gsondata.calc.Video;
import com.quikrete.utils.AccordionView;
import com.quikrete.utils.ScreenUtils;
import com.quikrete.utils.Utils;
import com.quikrete.utils.WebServiceUtil;
import com.sharedpreference.SharedPreferenceHelper;

public class CalculatorActivity extends Activity {
    LinearLayout ll_header_down, ll_header_up, ll_shadow_layer, ll_techinfo;
    ImageView img_header_menu_cross, img_header_menu_cross_1;
    RelativeLayout rel_bg_home;

    LinearLayout linear_header_searchmenu, linear_header_hammenu;
    TextView txt_header_barcode, txt_header_mappoint, txt_header_search,txt_header_favorites,
            txt_header_menu;

    TextView txt_star, txt_send, txt_add_to_fav;
    TextView txt_label, txt_info, txt_calc_qty, txt_changetoll_arrow;

    TextView txt_calc_title;
    String id, title;

    Context context;

    CharSequence[] array_label = new CharSequence[0];
    CharSequence[] array_input_title = new CharSequence[0];

    LinearLayout ll_parent;
    EditText edt_value;

    LinearLayout ll_favourites, ll_prd_search, ll_prj_search, ll_how_to_videos,
            ll_calculators, ll_where_to_buy, ll_call_support_header,ll_social_media;
    EditText edt_keyword;
    Button btn_search;

    CheckBox chk_1, chk_2, chk_3, chk_4;
    TextView txt_chk_1, txt_chk_2, txt_chk_3, txt_chk_4;
    LinearLayout ll_chk_1, ll_chk_2, ll_chk_3, ll_chk_4;

    AccordionView accordion_view_1;
    LinearLayout accrdn_ll_1, accrdn_ll_2, accrdn_ll_3, accrdn_ll_4;

    Dialog connectionDialog;
    String instructions = "", notes = "";
    WebView tv_notes;
    TextView tv_notes_label;
    String values = "", option = "";
    boolean fromFav = false;

    String socialMessage="";
    String responseBody="";
    GetSocialMedia response;

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
        setContentView(R.layout.activity_calculator);

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
            m.appendReplacement(sb, "<font color=\"#f8ac40\">" + m.group(1) + "</font>");
        }
        m.appendTail(sb);


        Log.e("Value--","SocialMessage--"+socialMessage);

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
        txt_send = (TextView) findViewById(R.id.txt_send);
        txt_add_to_fav = (TextView) findViewById(R.id.txt_add_to_fav);

        txt_calc_qty = (TextView) findViewById(R.id.txt_calc_qty);
        txt_changetoll_arrow = (TextView) findViewById(R.id.txt_changetoll_arrow);
        txt_label = (TextView) findViewById(R.id.txt_label);
        txt_info = (TextView) findViewById(R.id.txt_info);
        setTypeFace();

        id = getIntent().getExtras().getString("id");
        title = getIntent().getExtras().getString("title");

        Log.e("Calculator", "id---" + id);

        txt_calc_title = (TextView) findViewById(R.id.txt_calc_title);
        txt_calc_title.setText(title);

        ll_parent = (LinearLayout) findViewById(R.id.ll_parent);
        edt_value = (EditText) findViewById(R.id.edt_value);
        tv_notes = (WebView) findViewById(R.id.tv_notes);
        tv_notes_label = (TextView) findViewById(R.id.tv_notes_label);

        if (Utils.isNetworkAvailable(context)) {

            new AsyncDownloadDetails().execute(id);


        } else
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

        edt_value.setOnEditorActionListener(new OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId,
                                          KeyEvent event) {
                // TODO Auto-generated method stub
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    // do here your stuff f
                    getResult();
                    return true;
                }
                return false;
            }
        });

        ll_techinfo = (LinearLayout) findViewById(R.id.ll_techinfo);
        ll_techinfo.setVisibility(View.GONE);

        txt_info.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                showDialog(instructions);
            }
        });
    }

    private void findViews() {
        llProducts = (LinearLayout)findViewById( R.id.llProducts );
        llVideos = (LinearLayout)findViewById( R.id.llVideos );
        llCalculator = (LinearLayout)findViewById( R.id.llCalculator );
        llProjects = (LinearLayout)findViewById( R.id.llProjects );
        llBuy = (LinearLayout)findViewById( R.id.llBuy );
    }


    private void showDialog(String instructions) {

        connectionDialog = new Dialog(this, R.style.CustomAlertDialog);
        // connectionDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        connectionDialog.setContentView(R.layout.alert_dialog_instructions);

        // WindowManager.LayoutParams wmlp = connectionDialog.getWindow()
        // .getAttributes();

        // wmlp.gravity = Gravity.TOP;

        TextView tv_close = (TextView) connectionDialog
                .findViewById(R.id.tv_close);
        WebView tv_instructions = (WebView) connectionDialog
                .findViewById(R.id.tv_instructions);

        tv_instructions.setBackgroundColor(Color.TRANSPARENT);
        tv_close.setTypeface(ScreenUtils.returnTypeFace(this));

        // String textTitleStyling = "<head><style>body{margin:0;padding:0; "
        // + "text-align:justify;color:#FFFFFF;}</style></head>";
        //
        // String titleWithStyle = textTitleStyling + "<body><h1>" +
        // instructions
        // + "</h1></body>";
        Log.e("newlog", instructions);
        // tv_instructions.loadDataWithBaseURL(null, titleWithStyle,
        // "text/html",
        // "utf-8", null);

        tv_instructions.loadDataWithBaseURL(null,
                "<html><body><style>body{color:#FFFFFF;}</style>"
                        + instructions + "</body></html>", "text/html",
                "utf-8", null);
        tv_close.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (connectionDialog != null) {
                    if (connectionDialog.isShowing()) {
                        connectionDialog.dismiss();
                    }
                }
            }
        });

        connectionDialog.show();
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

    private LinearLayout returnLayout(final Video info) {

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

    private LinearLayout returnLayoutCalculator(final Calculator info) {

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
        txt_send.setTypeface(ScreenUtils.returnTypeFace(this));
        txt_changetoll_arrow.setTypeface(ScreenUtils.returnTypeFace(this));
        txt_info.setTypeface(ScreenUtils.returnTypeFace(this));
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

//                ll_social_media.setVisibility(View.VISIBLE);
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
                ll_shadow_layer.setAlpha(0.9f);

                ll_header_up.setBackgroundResource(R.drawable.trial_header_border);

                changeColorGradually(android.R.color.transparent,
                        android.R.color.black);
                break;

            case R.id.txt_header_menu:

              //  ll_social_media.setVisibility(View.VISIBLE);

                img_header_menu_cross.setVisibility(View.VISIBLE);
                linear_header_searchmenu.setVisibility(View.GONE);
                linear_header_hammenu.setVisibility(View.INVISIBLE);

                ll_header_down.setVisibility(View.VISIBLE);
                ll_header_down.startAnimation(slide);
                // ll_header_down.setForeground(getResources().getColor(R.color.YOUR_COLOR_NAME));
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
                LayoutParams params = new LayoutParams(
                        LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                params.setMargins(0, height, 0, 0);
                img_header_menu_cross.setLayoutParams(params);
            }
        });

        edt_value.clearFocus();

        edt_value.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    Utils.hideKeyboard(context, v);
                }
            }
        });
        llCalculator.setBackgroundResource(R.drawable.selectedbackground);
        llVideos.setBackgroundResource(R.drawable.background);
        llProducts.setBackgroundResource(R.drawable.background);
        llProjects.setBackgroundResource(R.drawable.background);
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

    public void onClickShowDropdown(View view) {

        ScreenUtils.showMltpleCHoiceDlg(array_label, this, txt_label, "state",
                txt_calc_qty, array_input_title);
    }

    public void onClickGetResult(View view) {
        getResult();
    }

    private void getResult() {

        values = edt_value.getText().toString().trim();
        option = txt_calc_qty.getText().toString();

        if (option.length() > 0 && !option.equals("Choose an Item")) {
            if (values.length() > 0) {
                new AsyncDownloadResult().execute(id, values,
                        option);
            } else
                ScreenUtils.showToast(context, "Enter a valid value");
        } else
            ScreenUtils.showToast(context, "No values to choose from");

        ScreenUtils.dismissKeyboard(edt_value, context);

    }

    class AsyncDownloadDetails extends
            AsyncTask<String, String, CalculatorDetail> {

        ProgressDialog pdialog;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            pdialog = ScreenUtils.returnProgDialogObj(context,
                    "Loading options...please wait", false);
            pdialog.show();
            super.onPreExecute();
        }

        @Override
        protected CalculatorDetail doInBackground(String... params) {
            // TODO Auto-generated method stub
            try {
                List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
                nameValuePair
                        .add(new BasicNameValuePair(
                                WebServiceUtil.METHOD_6_1_1_1_GET_CALCULATOR_DETAILS_ID,
                                params[0]));

                String url = WebServiceUtil.URL
                        + WebServiceUtil.METHOD_6_1_1_GET_CALCULATOR_DETAILS;

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

                Log.e("newlog", url);
                Log.e("newlog", nameValuePair.toString());

                Gson gson = new Gson();
                Reader reader = new InputStreamReader(
                        WebServiceUtil.convertStringToInputstream(result));
                CalculatorDetail response = gson.fromJson(reader,
                        CalculatorDetail.class);
                instructions = response.getInstructions();
                notes = response.getNotes();
                return response;
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(CalculatorDetail result) {
            // TODO Auto-generated method stub
            try {
                pdialog.dismiss();
            } catch (Exception e) {
                // TODO: handle exception
            }
            super.onPostExecute(result);
            if (result != null) {

                try {
                    Log.e("newlog", "notes---" + notes);

                    Log.e("CalculatorDetail", "result---" + result.toString());

                    if (notes != null && notes.equals("null")) {
                        tv_notes_label.setVisibility(View.GONE);
                        notes = "";
                    }
                    if (notes == null) {
                        tv_notes_label.setVisibility(View.GONE);
                        notes = "";
                    }

                    tv_notes.setBackgroundColor(Color.TRANSPARENT);

                    tv_notes.loadDataWithBaseURL(null,
                            "<html><body><style>body{color:#FFFFFF;}</style>"
                                    + notes + "</body></html>", "text/html",
                            "utf-8", null);

                    List<Option> reultantBanks = result.getOptions();
                    updateUi(reultantBanks);
                    updateAccordians(result.getRelated());

                    fromFav = getIntent().getBooleanExtra("fromFav", false);
                    if (fromFav) {
                        FavData favData = SharedPreferenceHelper.getFavorite(context, id);
                        if (favData != null) {

                            edt_value.setText(favData.getValue());
                            txt_calc_qty.setText(favData.getOption());

                            new AsyncDownloadResult().execute(favData.getCalc_id(), favData.getValue(),
                                    favData.getOption());
                        }
                    }
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

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
                e.printStackTrace();
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
                e.printStackTrace();
                accordion_view_1.setSectionVisibility(1);
            }
            try {
                for (Video obj : related.getVideos()) {
                    accrdn_ll_3.addView(returnLayout(obj));
                }
                int size = related.getVideos().size();
                if (size == 0)
                    accordion_view_1.setSectionVisibility(2);
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
                accordion_view_1.setSectionVisibility(2);
            }
            try {
                for (Calculator obj : related.getCalculators()) {
                    accrdn_ll_4.addView(returnLayoutCalculator(obj));
                }
                int size = related.getCalculators().size();
                if (size == 0)
                    accordion_view_1.setSectionVisibility(3);
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
                accordion_view_1.setSectionVisibility(3);
            }

        }

        private void updateUi(List<Option> result) throws Exception {
            // TODO Auto-generated method stub

            Option opt = new Option();
            opt.setLabel("Choose an Item");
            opt.setInputTitle("");
            result.add(0, opt);

            int size = result.size();
            array_label = new CharSequence[size];
            array_input_title = new CharSequence[size];

            for (int i = 0; i < size; i++) {
                if (i == 0) {
                    txt_calc_qty.setText(result.get(i).getLabel());
                    txt_label.setText(result.get(i).getInputTitle());
                }
                array_label[i] = result.get(i).getLabel();
                array_input_title[i] = result.get(i).getInputTitle();
            }

        }

    }

    class AsyncDownloadResult extends AsyncTask<String, String, List<Result>> {

        ProgressDialog pdialog;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            pdialog = ScreenUtils.returnProgDialogObj(context,
                    "Loading result...please wait", false);
            pdialog.show();
            super.onPreExecute();
        }

        @Override
        protected List<Result> doInBackground(String... params) {
            // TODO Auto-generated method stub
            try {
                List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();

                nameValuePair.add(new BasicNameValuePair(
                        WebServiceUtil.METHOD_7_1_GET_CALCULATOR_RESULT_ID,
                        params[0]));
                nameValuePair.add(new BasicNameValuePair(
                        WebServiceUtil.METHOD_7_1_GET_CALCULATOR_RESULT_VALUE,
                        params[1]));
                nameValuePair.add(new BasicNameValuePair(
                        WebServiceUtil.METHOD_7_1_GET_CALCULATOR_RESULT_OPTION,
                        params[2]));

                String url = WebServiceUtil.URL
                        + WebServiceUtil.METHOD_7_1_GET_CALCULATOR_RESULT;

                Log.e("newlog", url);
                Log.e("newlog", nameValuePair.toString());

                String result = WebServiceUtil.getPostResponce(nameValuePair,
                        url);
                Gson gson = new Gson();
                Reader reader = new InputStreamReader(
                        WebServiceUtil.convertStringToInputstream(result));
                CalculatorDetail response = gson.fromJson(reader,
                        CalculatorDetail.class);
                List<Result> reultantBanks = response.getResult();

                return reultantBanks;
            } catch (Exception e) {
                // TODO: handle exception
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<Result> result) {
            // TODO Auto-generated method stub
            try {
                ll_parent.removeAllViews();
                pdialog.dismiss();
            } catch (Exception e) {
                // TODO: handle exception
            }
            super.onPostExecute(result);
            if (result != null) {
                Log.e("Calculator", "result--- " + result.toString());
                ll_techinfo.setVisibility(View.VISIBLE);
                try {
                    updateUi(result);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            } else
                ll_techinfo.setVisibility(View.GONE);
        }

        private void updateUi(List<Result> result) throws Exception {
            // TODO Auto-generated method stub

            for (Result obj : result) {
                try {
                    ll_parent.addView(returnLayout(obj));
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

    LinearLayout returnLayout(Result info) throws Exception {

        final LinearLayout lytContainer = (LinearLayout) View.inflate(context,
                R.layout.layout_calc, null);

        TextView txt_key = (TextView) lytContainer.findViewById(R.id.txt_key);
        TextView txt_value = (TextView) lytContainer
                .findViewById(R.id.txt_value);

        txt_key.setText(info.getKey());
        txt_value.setText(info.getValue());

        lytContainer.setTag(info);
        return lytContainer;

    }

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

                                FavData favData = new FavData(id, values, option);
                                Log.e("Calculator", "favData---" + favData.toString());
                                SharedPreferenceHelper.removeFavorite(context, favData);

                            } else {
                                SharedPreferenceHelper.savePreferences(id, "1",
                                        context);
                                ScreenUtils.showToast(context,
                                        "Item added to Favourites");
                                ScreenUtils.changeStarColor(id, txt_star,
                                        txt_add_to_fav, context);

                                FavData favData = new FavData(id, values, option);
                                Log.e("Calculator", "favData---" + favData.toString());
                                SharedPreferenceHelper.addFavorite(context, favData);
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
        }.execute(id);

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
        Intent intent = new Intent(context, VideoListActivity.class);
        intent.putExtra("related", "product");
        intent.putExtra("related_for", id);
        startActivity(intent);
    }

    public void onClickSendEmail(View view) {

        try {
            int count = ll_parent.getChildCount();
            LinearLayout lytContainer = null;
            StringBuffer links = new StringBuffer();
            for (int i = 0; i < count; i++) {

                lytContainer = (LinearLayout) ll_parent.getChildAt(i);
                // do something with your child element
                Result info = (Result) lytContainer.getTag();

                links.append(info.getKey() + " : " + info.getValue()).append(
                        "\n\n");
            }

			/*
             * Product Name : Polymeric Jointing Sands Size of the slab : 2 Sq.
			 * Ft. Type : For 1/4 inch Thick Joints
			 * 
			 * Concrete mix required are as follows : Number of 50# Bags : 2
			 */

            String preTextSubject = "Calculation Results for ";
            String preTextMessage = "Hi,\n\n\nI would like to share the Calculation Results for \n\n"
                    + "Product Name : "
                    + getTitleOfProduct()
                    + "\n"
                    + "Size of the slab : "
                    + getValueOfProduct()
                    + "\n"
                    + "Type : "
                    + getOptionSelected()
                    + "\n\n"
                    + "Concrete mix required are as follows : " + links;

            String signature = "--Sent From Quikrete App";
            if (links.length() > 0) {
                Utils.sendEmail(context, preTextSubject + getTitleOfProduct(),
                        preTextMessage + "\n\n\n\n" + signature);
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

    }

    private String getTitleOfProduct() {
        // TODO Auto-generated method stub
        return txt_calc_title.getText().toString();
    }

    private String getValueOfProduct() {
        // TODO Auto-generated method stub
        return edt_value.getText().toString();
    }

    private String getOptionSelected() {
        return txt_calc_qty.getText().toString();
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

        // onBackPressed();
        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

}
