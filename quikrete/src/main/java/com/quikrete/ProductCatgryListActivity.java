package com.quikrete;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.google.gson.Gson;

import com.journeyapps.barcodescanner.CaptureActivity;
import com.quikrete.adapter.ItemListAdapter_Project_Cat_Home;
import com.quikrete.adapter.ItemListAdapter_Project_Cat_Pro;
import com.quikrete.gsondata.productcat.ProductCategoryData;
import com.quikrete.gsondata.productcat.Result;
import com.quikrete.gsondata.projectcat.Homeowner;
import com.quikrete.gsondata.projectcat.Pro;
import com.quikrete.utils.ScreenUtils;
import com.quikrete.utils.SegmentedGroup;
import com.quikrete.utils.Utils;
import com.quikrete.utils.WebServiceUtil;
import com.sharedpreference.SharedPreferenceHelper;

public class ProductCatgryListActivity extends Activity {
	LinearLayout ll_header_down, ll_header_up, ll_shadow_layer;
	ImageView img_header_menu_cross, img_header_menu_cross_1;
	RelativeLayout rel_bg_home;

	TextView txt_header_barcode, txt_header_mappoint, txt_header_search,
			txt_header_menu,txt_header_favorites;

	/** All widgets for the header tool bar */
	LinearLayout linear_header_searchmenu, linear_header_hammenu;
	SegmentedGroup segmented;

	/** List view elements */
	private ListView mListView;
	private ItemListAdapter_Project_Cat_Home mAdapterHomeOwner;

	private ItemListAdapter_Project_Cat_Pro mAdapterForPro;

	Context context;

	int radioGroup = 0;

	LinearLayout ll_favourites, ll_prd_search, ll_prj_search, ll_how_to_videos,
			ll_calculators, ll_where_to_buy, ll_call_support_header;
	EditText edt_keyword;
	Button btn_search;
	CheckBox chk_1, chk_2, chk_3, chk_4;
	TextView txt_chk_1, txt_chk_2, txt_chk_3, txt_chk_4;
	LinearLayout ll_chk_1, ll_chk_2, ll_chk_3, ll_chk_4;


	String socialMessage="";

	private LinearLayout llProducts;
	private LinearLayout llVideos;
	private LinearLayout llCalculator;
	private LinearLayout llProjects;
	private LinearLayout llBuy;



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		ScreenUtils.fullScreen(this);
		setContentView(R.layout.activity_project_cat_list);

		context = this;

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

		setTypeFace();

		segmented = (SegmentedGroup) findViewById(R.id.segmented2);
		segmented.setTintColor(Color.parseColor("#FFECECEC"),
				Color.parseColor("#FF4D4D4D"));

		segmented.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				switch (checkedId) {
				case R.id.button21:
					radioGroup = 0;
					mListView.setAdapter(mAdapterHomeOwner);
					break;
				case R.id.button22:
					radioGroup = 1;
					mListView.setAdapter(mAdapterForPro);
					break;

				default:
					break;
				}
			}
		});

		listViewInit();

		if (Utils.isNetworkAvailable(context))
			new AsyncLoadProductCategory().execute();
		else
			ScreenUtils.showToast(context,
					getResources().getString(R.string.no_internet_message));

	}

	private void listViewInit() {
		// TODO Auto-generated method stub
		mAdapterHomeOwner = new ItemListAdapter_Project_Cat_Home(this,
				R.layout.list_view_cat_row);
		mListView = (ListView) findViewById(R.id.xListView);
		mListView.setAdapter(mAdapterHomeOwner);

		mAdapterForPro = new ItemListAdapter_Project_Cat_Pro(this,
				R.layout.list_view_cat_row);

		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(context, ProjectListActivity.class);
				intent.putExtra("cat_id", getCatId(arg2));
				intent.putExtra("type", getType(arg2));
				startActivity(intent);
				//
				// Log.e("newlog", getCatId(arg2));
				// Log.e("newlog", getType(arg2));
				Utils.getTracker(getCatName(arg2), getApplication());
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


	private String getCatId(int pos) {

		switch (radioGroup) {
		case 0:
			return mAdapterHomeOwner.getItem(pos).getId();
		case 1:

			return mAdapterForPro.getItem(pos).getId();

		default:
			break;
		}
		return null;
	}
	private String getCatName(int pos) {

		switch (radioGroup) {
		case 0:
			return mAdapterHomeOwner.getItem(pos).getName();
		case 1:

			return mAdapterForPro.getItem(pos).getName();

		default:
			break;
		}
		return null;
	}

	private String getType(int pos) {

		switch (radioGroup) {
		case 0:

			return "homeowner";
		case 1:

			return "pro";

		default:
			break;
		}
		return null;
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
				Log.e("newlog", height + "---");
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

	class AsyncLoadProductCategory extends AsyncTask<String, String, Result> {

		ProgressDialog pdialog;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			pdialog = ScreenUtils.returnProgDialogObj(context,
					"Loading Product Categories...please wait", false);
			pdialog.show();
			super.onPreExecute();
		}

		@Override
		protected Result doInBackground(String... params) {
			// TODO Auto-generated method stub
			try {

				InputStream source = WebServiceUtil.retrieveStream(
						WebServiceUtil.URL
								+ WebServiceUtil.METHOD_1_GET_PRODUCT_CAT_LIST,
						context);
				Gson gson = new Gson();
				Reader reader = new InputStreamReader(source);
				ProductCategoryData response = gson.fromJson(reader,
						ProductCategoryData.class);
				Result reultantBanks = response.getResult();

				return reultantBanks;

			} catch (Exception e) {
				// TODO: handle exception
			}

			return null;
		}

		@Override
		protected void onPostExecute(Result reultantBanks) {
			// TODO Auto-generated method stub
			try {
				pdialog.dismiss();
			} catch (Exception e) {
				// TODO: handle exception
			}
			try {
				processData(reultantBanks);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			super.onPostExecute(reultantBanks);
		}

		private void processData(Result reultantBanks) throws Exception {
			// TODO Auto-generated method stub
			if (reultantBanks != null) {

				for (Homeowner home : reultantBanks.getHomeowner())
					mAdapterHomeOwner.addItem(home);
				mAdapterHomeOwner.notifyDataSetChanged();

				for (Pro pro : reultantBanks.getPro())
					mAdapterForPro.addItem(pro);
				mAdapterForPro.notifyDataSetChanged();

			}
		}

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
