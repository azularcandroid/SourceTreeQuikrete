package com.quikrete;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.text.Html;
import android.util.Log;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.view.animation.OvershootInterpolator;
import android.view.animation.ScaleAnimation;
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

import com.google.gson.Gson;

import com.journeyapps.barcodescanner.CaptureActivity;
import com.koushikdutta.urlimageviewhelper.UrlImageViewCallback;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;
import com.quikrete.data.FavResult;
import com.quikrete.gsondata.productcat.list.Alphabetical;
import com.quikrete.gsondata.productcat.list.ProductDetail;
import com.quikrete.gsondata.productcat.list.TechInfo;
import com.quikrete.utils.AccordionView;
import com.quikrete.utils.ScreenUtils;
import com.quikrete.utils.SegmentedGroup;
import com.quikrete.utils.Utils;
import com.quikrete.utils.WebServiceUtil;
import com.sharedpreference.SharedPreferenceHelper;

public class ProductDetailActivity extends Activity {
	LinearLayout ll_header_down, ll_header_up, ll_shadow_layer;
	ImageView img_header_menu_cross, img_header_menu_cross_1;
	RelativeLayout rel_bg_home;

	TextView txt_header_barcode, txt_header_mappoint, txt_header_search,
			txt_header_menu,txt_header_favorites;

	LinearLayout linear_header_searchmenu, linear_header_hammenu;
	TextView txt_star, txt_download, txt_send;

	LinearLayout ll_overview, ll_techinfo;

	String cat_id, id, type;

	Context context;

	String detail_id, detail_title, detail_image, detail_content;
	List<TechInfo> listTechInfo = new ArrayList<TechInfo>();

	/** widgets */
	TextView txt_prd_title;
	ImageView img_prd_image;
	WebView txt_prd_content;
	LinearLayout ll_techinfo_parent;

	TextView txt_add_to_fav;
	// txt_related_title;

	LinearLayout ll_favourites, ll_prd_search, ll_prj_search, ll_how_to_videos,
			ll_calculators, ll_where_to_buy, ll_call_support_header;
	EditText edt_keyword;
	Button btn_search;
	CheckBox chk_1, chk_2, chk_3, chk_4;
	TextView txt_chk_1, txt_chk_2, txt_chk_3, txt_chk_4;
	LinearLayout ll_chk_1, ll_chk_2, ll_chk_3, ll_chk_4;

	AccordionView accordion_view_1;
	LinearLayout accrdn_ll_4, accrdn_ll_1, accrdn_ll_2, accrdn_ll_3;


	String socialMessage="";

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
		setContentView(R.layout.activity_product_detail);

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


		Log.e("Value--,","SocialMessage-- "+socialMessage);

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

		setTypeFace();

		SegmentedGroup segmented = (SegmentedGroup) findViewById(R.id.segmented2);
		segmented.setTintColor(Color.parseColor("#FFECECEC"),
				Color.parseColor("#FF4D4D4D"));

		accordion_view_1 = (AccordionView) findViewById(R.id.accordion_view_1);
		segmented.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				switch (checkedId) {
				case R.id.button21:

					ll_overview.setVisibility(View.VISIBLE);
					ll_techinfo.setVisibility(View.GONE);
					accordion_view_1.setVisibility(View.VISIBLE);
					break;
				case R.id.button22:

					ll_overview.setVisibility(View.GONE);
					ll_techinfo.setVisibility(View.VISIBLE);

					accordion_view_1.setVisibility(View.GONE);
					break;

				default:
					break;
				}

			}
		});

		cat_id = getIntent().getExtras().getString("cat_id");
		id = getIntent().getExtras().getString("id");
		type = getIntent().getExtras().getString("type");

		initWidgets();

		if (Utils.isNetworkAvailable(context))
			new AsyncDownloadDetails()
					.execute(new String[] { cat_id, id, type });
		else
			ScreenUtils.showToast(context,
					getResources().getString(R.string.no_internet_message));

		ScreenUtils.changeStarColor(id, txt_star, txt_add_to_fav, context);

		// Log.e("newlog", "type---" + type);
		// Log.e("newlog", "txt_related_title----" + txt_related_title);
		/*
		 * if (type != null && type.equals("0"))
		 * txt_related_title.setText("Related Projects"); if (type != null &&
		 * type.equals("1")) txt_related_title.setText("Related Products");
		 */

		try {
			accrdn_ll_4 = (LinearLayout) findViewById(R.id.accrdn_ll_4);
			accrdn_ll_1 = (LinearLayout) findViewById(R.id.accrdn_ll_1);
			accrdn_ll_2 = (LinearLayout) findViewById(R.id.accrdn_ll_2);
			accrdn_ll_3 = (LinearLayout) findViewById(R.id.accrdn_ll_3);

		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	LinearLayout returnLayout(final Alphabetical info) throws Exception {

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

	LinearLayout returnLayoutProducts(final Alphabetical info) throws Exception {

		final LinearLayout lytContainer = (LinearLayout) View.inflate(context,
				R.layout.layout_accordian_rows, null);

		TextView txt_value = (TextView) lytContainer
				.findViewById(R.id.txt_accrdn_content);

		txt_value.setText(info.getTitle() + "");

		lytContainer.setTag(info);

		lytContainer.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.e("newlog", info.toString());
				// TODO Auto-generated method stub
				Intent intent = new Intent(context, ProductDetailActivity.class);
				intent.putExtra("id", info.getId());
				intent.putExtra("type", "0");
				startActivity(intent);
			}
		});
		return lytContainer;

	}

	LinearLayout returnLayout(
			final com.quikrete.gsondata.productcat.list.Video info)
			throws Exception {

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

	LinearLayout returnLayout(
			final com.quikrete.gsondata.productcat.list.Calculator info)
			throws Exception {

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

	private void findViews() {
		llProducts = (LinearLayout)findViewById( R.id.llProducts );
		llVideos = (LinearLayout)findViewById( R.id.llVideos );
		llCalculator = (LinearLayout)findViewById( R.id.llCalculator );
		llProjects = (LinearLayout)findViewById( R.id.llProjects );
		llBuy = (LinearLayout)findViewById( R.id.llBuy );
	}

	private void initWidgets() {
		// TODO Auto-generated method stub
		txt_prd_title = (TextView) findViewById(R.id.txt_prd_title);
		txt_prd_content = (WebView) findViewById(R.id.txt_prd_content);
		img_prd_image = (ImageView) findViewById(R.id.img_prd_image);
		ll_techinfo_parent = (LinearLayout) findViewById(R.id.ll_techinfo_parent);
		txt_add_to_fav = (TextView) findViewById(R.id.txt_add_to_fav);
		// txt_related_title = (TextView) findViewById(R.id.txt_related_title);
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
		llProducts.setBackgroundResource(R.drawable.selectedbackground);
		llVideos.setBackgroundResource(R.drawable.background);
		llCalculator.setBackgroundResource(R.drawable.background);
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

	class AsyncDownloadDetails extends AsyncTask<String, String, String> {

		ProgressDialog pdialog;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			pdialog = ScreenUtils.returnProgDialogObj(context,
					"Please wait...loading details", false);
			pdialog.show();
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			String url = "";
			try {
				List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();

				if (params[2] != null && params[2].equals("0")) {
					url = WebServiceUtil.URL
							+ WebServiceUtil.METHOD_5_1_GET_PRODUCT_DETAILS;
					nameValuePair.add(new BasicNameValuePair(
							WebServiceUtil.METHOD_5_1_1_GET_PRODUCT_LIST_CAT,
							params[0]));
					nameValuePair.add(new BasicNameValuePair(
							WebServiceUtil.METHOD_5_1_2_GET_PRODUCT_LIST_ID,
							params[1]));
				} else if (params[2] != null && params[2].equals("1")) {
					url = WebServiceUtil.URL
							+ WebServiceUtil.METHOD_8_1_GET_PRODUCT_DETAILS;
					nameValuePair.add(new BasicNameValuePair(
							WebServiceUtil.METHOD_5_1_1_GET_PRODUCT_LIST_CAT,
							params[0]));
					nameValuePair.add(new BasicNameValuePair(
							WebServiceUtil.METHOD_5_1_2_GET_PRODUCT_LIST_ID,
							params[1]));
				} else if (params[2] != null && params[2].equals("2")) {
					url = WebServiceUtil.URL
							+ WebServiceUtil.METHOD_9_1_GET_PRODUCT_BARCODE;
					nameValuePair.add(new BasicNameValuePair(
							WebServiceUtil.METHOD_9_1_1_GET_PRODUCT_BARCODE,
							params[0]));
				}

				Log.e("newlog", nameValuePair.toString());
				Log.e("newlog", url);
				String result = WebServiceUtil.getPostResponce(nameValuePair,
						url);
				return result;
			} catch (Exception e) {
				// TODO: handle exception
				return null;
			}

		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			try {
				pdialog.dismiss();
			} catch (Exception e) {
				// TODO: handle exception
			}

			if (result != null) {

				String temp1 = "\"product\":{\"popularity\":[],\"alphabetical\":[]}";
				String replacer1 = "\"product\":[\"No Result\"]";

				String temp2 = "\"project\":{\"popularity\":[],\"alphabetical\":[]}";
				String replacer2 = "\"project\":[\"No Result\"]";

				result = result.replaceAll(Pattern.quote(replacer2), temp2);
				result = result.replaceAll(Pattern.quote(replacer1), temp1);

				String replacer = "\"No Result\"";
				result = result.replaceAll(replacer, "");

				try {
					Gson gson = new Gson();
					Reader reader = new InputStreamReader(
							WebServiceUtil.convertStringToInputstream(result));
					ProductDetail response = gson.fromJson(reader,
							ProductDetail.class);

					detail_id = response.getId();
					detail_title = response.getTitle();
					detail_image = response.getImage();
					detail_content = response.getContent();

					listTechInfo.addAll(response.getTechInfo());

					updateUi(detail_id, detail_title, detail_image,
							detail_content, listTechInfo);

					updateAccordians(response.getRelated());

				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}

			}
		}

		private void updateAccordians(
				com.quikrete.gsondata.productcat.list.Related related) {
			// TODO Auto-generated method stub
			
			try {
				for (Alphabetical obj : related.getProduct().getAlphabetical()) {
					accrdn_ll_4.addView(returnLayoutProducts(obj));
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
					accrdn_ll_1.addView(returnLayout(obj));
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
				for (com.quikrete.gsondata.productcat.list.Video obj : related
						.getVideo()) {
					accrdn_ll_2.addView(returnLayout(obj));
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
				for (com.quikrete.gsondata.productcat.list.Calculator obj : related
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

		private void updateUi(String detail_id, String detail_title,
				String detail_image, String detail_content,
				List<TechInfo> listTechInfo) {
			// TODO Auto-generated method stub

			txt_prd_title.setText(detail_title);
			// txt_prd_content.setText(Html.fromHtml("<![CDATA["+detail_content+"]]>"));
			// txt_prd_content.setText(Html.fromHtml(detail_content));

			// String content =
			// "body\\{font-family:open sans; font-size:12px;\\}";
			// String content1 =
			// "body\\{font-family:open sans; font-size:14px;\\}";
			// detail_content = detail_content.replaceAll(content, "");
			// detail_content = detail_content.replaceAll(content1, "");
			// txt_prd_content.setHtmlFromString(detail_content, true);
			txt_prd_content.loadDataWithBaseURL(null, "<html><body>"
					+ detail_content + "</body></html>", "text/html", "utf-8",
					null);

			if (detail_image != null && !detail_image.equals("null"))
				UrlImageViewHelper.setUrlDrawable(img_prd_image, detail_image,
						R.drawable.banner, new UrlImageViewCallback() {
							@Override
							public void onLoaded(ImageView imageView,
									Bitmap loadedBitmap, String url,
									boolean loadedFromCache) {
								if (!loadedFromCache) {
									ScaleAnimation scale = new ScaleAnimation(
											0, 1, 0, 1,
											ScaleAnimation.RELATIVE_TO_SELF,
											.5f,
											ScaleAnimation.RELATIVE_TO_SELF,
											.5f);
									scale.setDuration(300);
									scale.setInterpolator(new OvershootInterpolator());
									imageView.startAnimation(scale);
								}
							}
						});

			for (TechInfo obj : listTechInfo) {
				try {
					ll_techinfo_parent.addView(returnLayoutTech(obj));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}

		LinearLayout returnLayoutTech(TechInfo info) throws Exception {

			final LinearLayout lytContainer = (LinearLayout) View.inflate(
					context, R.layout.layout_tech_info, null);

			TextView txt_tech_data = (TextView) lytContainer
					.findViewById(R.id.txt_tech_data);
			CheckBox check_tech_data = (CheckBox) lytContainer
					.findViewById(R.id.check_tech_data);

			txt_tech_data.setText(info.getTitle());

			lytContainer.setTag(info);

			return lytContainer;

		}

	}

	public void onClickStartDownload(View view) {

		try {
			int count = ll_techinfo_parent.getChildCount();
			LinearLayout lytContainer = null;
			for (int i = 0; i < count; i++) {

				lytContainer = (LinearLayout) ll_techinfo_parent.getChildAt(i);
				// do something with your child element
				TechInfo info = (TechInfo) lytContainer.getTag();
				CheckBox check_tech_data = (CheckBox) lytContainer
						.findViewById(R.id.check_tech_data);
				if (check_tech_data.isChecked()) {

					new DownloadFile(info.getTitle()).execute(new String[] {
							info.getTitle(), info.getFile() });

				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	public void onClickSendEmail(View view) {

		try {
			int count = ll_techinfo_parent.getChildCount();
			LinearLayout lytContainer = null;
			StringBuffer links = new StringBuffer();
			for (int i = 0; i < count; i++) {

				lytContainer = (LinearLayout) ll_techinfo_parent.getChildAt(i);
				// do something with your child element
				TechInfo info = (TechInfo) lytContainer.getTag();
				CheckBox check_tech_data = (CheckBox) lytContainer
						.findViewById(R.id.check_tech_data);
				if (check_tech_data.isChecked()) {
					links.append(info.getTitle() + " : " + info.getFile())
							.append("\n\n");
				}
			}

			String preTextSubject = "Technical Information for ";
			String preTextMessage = "Hi,\n\n\nI would like to share the Technical Information for "
					+ getTitleOfProduct()
					+ ". Click following link(s) to view:\n\n\n" + links;

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

	private class DownloadFile extends AsyncTask<String, Void, String> {

		public DownloadFile(String title) {
			// TODO Auto-generated constructor stub
			Toast.makeText(context, "Downloading " + title, Toast.LENGTH_LONG)
					.show();
		}

		@Override
		protected String doInBackground(String... params) {
			String filename = params[0] + ".pdf";// "somefile.pdf";

			HttpURLConnection c;
			try {
				URL url = new URL(params[1]);
				c = (HttpURLConnection) url.openConnection();
				c.setRequestMethod("GET");
				c.setDoOutput(true);
				c.connect();
			} catch (IOException e1) {
				return e1.getMessage();
			}

			File myFilesDir = new File(Environment
					.getExternalStorageDirectory().getAbsolutePath()
					+ "/Download");

			File file = new File(myFilesDir, filename);

			if (file.exists()) {
				file.delete();
			}

			if ((myFilesDir.mkdirs() || myFilesDir.isDirectory())) {
				try {
					InputStream is = c.getInputStream();
					FileOutputStream fos = new FileOutputStream(myFilesDir
							+ "/" + filename);

					byte[] buffer = new byte[1024];
					int len1 = 0;
					while ((len1 = is.read(buffer)) != -1) {
						fos.write(buffer, 0, len1);
					}
					fos.close();
					is.close();

				} catch (Exception e) {
					return e.getMessage();
				}

			} else {
				return "Unable to create folder";
			}
			return filename;

		}

		@Override
		protected void onPostExecute(String result) {
			Toast.makeText(context, result, Toast.LENGTH_LONG).show();
			super.onPostExecute(result);
		}
	}

	public void onClickAddToFav(View view) {

		final boolean value = Utils.checkAddToFav(id, context);

		new AsyncTask<String, String, FavResult>() {
			ProgressDialog pdialog;

			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				String message[] = { "Adding to Favorites...please wait",
						"Removing from Favorites...please wait" };
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
										"Item removed from Favorites");
								ScreenUtils.changeStarColor(id, txt_star,
										txt_add_to_fav, context);
							} else {
								SharedPreferenceHelper.savePreferences(id, "1",
										context);
								ScreenUtils.showToast(context,
										"Item added to Favorites");
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
		}.execute(new String[] { id });

	}

	// public void onClickRelated1(View view) {
	//
	// if (type != null && type.equals("0")) {
	//
	// Intent intent = new Intent(context, ProjectListActivity.class);
	// intent.putExtra("related", "project");
	// intent.putExtra("related_for", id);
	// intent.putExtra("type", "1");
	// startActivity(intent);
	// } else if (type != null && type.equals("1")) {
	// Intent intent = new Intent(context, ProductListActivity.class);
	// intent.putExtra("related", "product");
	// intent.putExtra("related_for", id);
	// intent.putExtra("type", "0");
	// startActivity(intent);
	// }
	// }
	//
	// public void onClickRelated2(View view) {
	//
	// Intent intent = new Intent(context, VideoListActivity.class);
	// intent.putExtra("related", "video");
	// intent.putExtra("related_for", id);
	// startActivity(intent);
	// }
	//
	// public void onClickRelated3(View view) {
	// Intent intent = new Intent(context, CalculatorListActivity.class);
	// intent.putExtra("related", "calculator");
	// intent.putExtra("related_for", id);
	// startActivity(intent);
	// }

	private String getTitleOfProduct() {

		return txt_prd_title.getText().toString();
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

	// private class AsyncLoadProjects
	// extends
	// AsyncTask<String, String, com.quikrete.gsondata.projectcat.list.Result> {
	//
	// @Override
	// protected com.quikrete.gsondata.projectcat.list.Result doInBackground(
	// String... params) {
	// // TODO Auto-generated method stub
	//
	// try {
	// String url = "";
	// List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
	// nameValuePair.add(new BasicNameValuePair(
	// WebServiceUtil.METHOD_15_1_GET_RELATED_RELATED,
	// params[0]));
	// nameValuePair.add(new BasicNameValuePair(
	// WebServiceUtil.METHOD_15_1_GET_RELATED_FOR, "1064"));
	// url = WebServiceUtil.URL
	// + WebServiceUtil.METHOD_15_1_GET_RELATED;
	//
	// String result = WebServiceUtil.getPostResponce(nameValuePair,
	// url);
	//
	// Gson gson = new Gson();
	// Reader reader = new InputStreamReader(
	// WebServiceUtil.convertStringToInputstream(result));
	// ProjectListData response = gson.fromJson(reader,
	// ProjectListData.class);
	// com.quikrete.gsondata.projectcat.list.Result reultantBanks = response
	// .getResult();
	//
	// return reultantBanks;
	//
	// } catch (Exception e) {
	// // TODO: handle exception
	// return null;
	// }
	//
	// }
	//
	// @Override
	// protected void onPostExecute(
	// com.quikrete.gsondata.projectcat.list.Result result) {
	// // TODO Auto-generated method stub
	// try {
	// if (result != null) {
	// for (com.quikrete.gsondata.projectcat.list.Popularity obj : result
	// .getPopularity()) {
	// accrdn_ll_1.addView(returnLayout(obj.getTitle()));
	// }
	// }
	// } catch (Exception e) {
	// // TODO: handle exception
	// }
	//
	// super.onPostExecute(result);
	// }
	// }
	//
	// private class AsyncLoadVideos
	// extends
	// AsyncTask<String, String, List<com.quikrete.gsondata.videocat.Result>> {
	//
	// @Override
	// protected List<com.quikrete.gsondata.videocat.Result> doInBackground(
	// String... params) {
	// // TODO Auto-generated method stub
	//
	// // TODO Auto-generated method stub
	// try {
	// String url = "";
	// List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
	//
	// nameValuePair.add(new BasicNameValuePair(
	// WebServiceUtil.METHOD_15_1_GET_RELATED_RELATED,
	// params[0]));
	// nameValuePair.add(new BasicNameValuePair(
	// WebServiceUtil.METHOD_15_1_GET_RELATED_FOR, "1064"));
	// url = WebServiceUtil.URL
	// + WebServiceUtil.METHOD_15_1_GET_RELATED;
	//
	// String result = WebServiceUtil.getPostResponce(nameValuePair,
	// url);
	//
	// Gson gson = new Gson();
	// Reader reader = new InputStreamReader(
	// WebServiceUtil.convertStringToInputstream(result));
	// VideoCategoryData response = gson.fromJson(reader,
	// VideoCategoryData.class);
	// List<Result> reultantBanks = response.getResult();
	//
	// return reultantBanks;
	//
	// } catch (Exception e) {
	// // TODO: handle exception
	// }
	//
	// return null;
	// }
	//
	// @Override
	// protected void onPostExecute(
	// List<com.quikrete.gsondata.videocat.Result> result) {
	// // TODO Auto-generated method stub
	// try {
	// if (result != null) {
	// for (com.quikrete.gsondata.videocat.Result home : result) {
	//
	// accrdn_ll_2.addView(returnLayout(home.getTitle()));
	// }
	// }
	// } catch (Exception e) {
	// // TODO: handle exception
	// }
	//
	// super.onPostExecute(result);
	// }
	// }
	//
	// private class AsyncLoadCalci extends
	// AsyncTask<String, String, List<com.quikrete.gsondata.calc.Result>> {
	//
	// @Override
	// protected List<com.quikrete.gsondata.calc.Result> doInBackground(
	// String... params) {
	// // TODO Auto-generated method stub
	//
	// // TODO Auto-generated method stub
	// try {
	// String url = "";
	// List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
	//
	// nameValuePair.add(new BasicNameValuePair(
	// WebServiceUtil.METHOD_15_1_GET_RELATED_RELATED,
	// params[0]));
	// nameValuePair.add(new BasicNameValuePair(
	// WebServiceUtil.METHOD_15_1_GET_RELATED_FOR, "1064"));
	// url = WebServiceUtil.URL
	// + WebServiceUtil.METHOD_15_1_GET_RELATED;
	//
	// String result = WebServiceUtil.getPostResponce(nameValuePair,
	// url);
	//
	// Gson gson = new Gson();
	// Reader reader = new InputStreamReader(
	// WebServiceUtil.convertStringToInputstream(result));
	// CalculatorList response = gson.fromJson(reader,
	// CalculatorList.class);
	// List<com.quikrete.gsondata.calc.Result> reultantBanks = response
	// .getResult();
	//
	// return reultantBanks;
	//
	// } catch (Exception e) {
	// // TODO: handle exception
	// }
	//
	// return null;
	// }
	//
	// @Override
	// protected void onPostExecute(
	// List<com.quikrete.gsondata.calc.Result> result) {
	// // TODO Auto-generated method stub
	// try {
	// if (result != null) {
	// for (com.quikrete.gsondata.calc.Result home : result) {
	//
	// accrdn_ll_3.addView(returnLayout(home.getTitle()));
	// }
	// }
	// } catch (Exception e) {
	// // TODO: handle exception
	// }
	//
	// super.onPostExecute(result);
	// }
	// }

	public void onClickHomeMenu(View view) {
		Intent intent = new Intent(context, MainActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
	}

}
