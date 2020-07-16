package com.quikrete;


import android.Manifest;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

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
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

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
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import com.journeyapps.barcodescanner.CaptureActivity;
import com.quikrete.adapter.ItemListAdapter_Shop;
import com.quikrete.gsondata.storelocator.Result;
import com.quikrete.gsondata.storelocator.StoreLocatorResult;
import com.quikrete.utils.MyLocation;
import com.quikrete.utils.ScreenUtils;
import com.quikrete.utils.Utils;
import com.quikrete.utils.WebServiceUtil;
import com.sharedpreference.SharedPreferenceHelper;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class WhereToBuyActivity extends FragmentActivity
		implements GoogleApiClient.ConnectionCallbacks, LocationListener, GoogleApiClient.OnConnectionFailedListener, OnMapReadyCallback {

	private MapFragment mMapFragment;
	private GoogleMap mMap;

	float zoomLevel = 17;

	Context context;

	LatLng CENTER = new LatLng(38.979609, -102.388633);

	// LatLng[] arrayLatLong = { new LatLng(46.576722, -119.615196),
	// new LatLng(46.273808, -109.331993),
	// new LatLng(30.999882, -83.931601),
	// new LatLng(38.085845, -110.650352),
	// new LatLng(42.959356, -93.160117) };

	ArrayList<Result> arrayLatLong = new ArrayList<Result>();

	ItemListAdapter_Shop adapter;
	ListView listViewShops;

	ArrayList<Marker> markerArray = new ArrayList<Marker>();

	ArrayList<Marker> userLocationmarker = new ArrayList<Marker>();

	LinearLayout ll_header_down, ll_header_up, ll_shadow_layer;
	ImageView img_header_menu_cross, img_header_menu_cross_1;

	/** All widgets for the header tool bar */
	LinearLayout linear_header_searchmenu, linear_header_hammenu;
	TextView txt_header_barcode, txt_header_mappoint, txt_header_search,
			txt_header_menu,txt_header_favorites;

	ProgressDialog progressDialg;

	LinearLayout ll_favourites, ll_prd_search, ll_prj_search, ll_how_to_videos,
			ll_calculators, ll_where_to_buy, ll_call_support_header;
	EditText edt_keyword;
	Button btn_search;
	CheckBox chk_1, chk_2, chk_3, chk_4;
	TextView txt_chk_1, txt_chk_2, txt_chk_3, txt_chk_4;
	LinearLayout ll_chk_1, ll_chk_2, ll_chk_3, ll_chk_4;

	EditText edt_zipcode;
	Button btn_search_zipcode;
	ImageView img_gps_fix;

	Marker marker;
	LatLng currentLatLong = new LatLng(26.019787, -80.233574);

	LocationRequest mLocationRequest;
	GoogleApiClient mGoogleApiClient;
	PendingResult<LocationSettingsResult> result;

	public static final int REQUEST_LOCATION = 1;
	private static final int PERMISSION_REQUEST_CAMERA = 2;
	private static final int PERMISSION_REQUEST_LOCATION = 1;
	boolean locationAccepted = false, cameraAccepted = false;

	private LocationManager locationManager;
	private static final long MIN_TIME = 400;
	private static final float MIN_DISTANCE = 1000;

	MarkerOptions markerOptions;
	LatLng latLng;

	public static final String TAG="Where To Buy Map";

	public Location mCurrentLocation;
	protected String mLastUpdateTime;
	boolean userLocationStatus = false;

	boolean isUserLocationStatus = false;
	boolean gotUserLoc = false;

	public static final int LOCATION_UPDATE_MIN_DISTANCE = 10;
	public static final int LOCATION_UPDATE_MIN_TIME = 5000;

	LocationListener locationListener;

	String socialMessage="";

	private LinearLayout llProducts;
	private LinearLayout llVideos;
	private LinearLayout llCalculator;
	private LinearLayout llProjects;
	private LinearLayout llBuy;


	@Override
	public void onLocationChanged(Location location) {
		LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
		CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 10);
		mMap.animateCamera(cameraUpdate);
		locationManager.removeUpdates(this);
	}




	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		context = this;
		ScreenUtils.fullScreen(this);

		setContentView(R.layout.activity_where_to_buy);


		SupportMapFragment mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map));
		mMap.getMapAsync(this);
		//((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMapAsync(this);



		//((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMapAsync(this);

		requestPermissionLocation();

		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

		if (ActivityCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
			// TODO: Consider calling
			//    ActivityCompat#requestPermissions
			// here to request the missing permissions, and then overriding
			//   public void onRequestPermissionsResult(int requestCode, String[] permissions,
			//                                          int[] grantResults)
			// to handle the case where the user grants the permission. See the documentation
			// for ActivityCompat#requestPermissions for more details.
			return;
		}
		locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME, MIN_DISTANCE, this); //You can also use LocationManager.GPS_PROVIDER and LocationManager.PASSIVE_PROVIDER


		//initMapComponenets();

		initListView();
		findViews();

		View headerView = findViewById(R.id.header1);

		initHeaderWidgets(headerView);

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

		Log.e("Value--,", "SocialMessage-- " + socialMessage);
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

		ll_shadow_layer = (LinearLayout) findViewById(R.id.ll_shadow_layer);

		setTypeFace();

		edt_zipcode = (EditText) findViewById(R.id.edt_zipcode);

		ScreenUtils.dismissKeyboard(edt_zipcode, context);
		btn_search_zipcode = (Button) findViewById(R.id.btn_search_zipcode);
		img_gps_fix = (ImageView) findViewById(R.id.img_gps_fix);

		btn_search_zipcode.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String content = edt_zipcode.getText().toString().trim();
				if (content.length() == 0) {
					ScreenUtils.showToast(context,
							"Please enter a valid Zipcode");
				} else {
					new AsyncLoadStoreLocatorByZipCode().execute(content);
				}
			}
		});
		img_gps_fix.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				getLocationFromNetworkOrGPS(context);
			}
		});

		progressDialg = new ProgressDialog(this);
		progressDialg.setMessage("Fetching location...please wait");
		progressDialg.show();
		getLocationFromNetworkOrGPS(this);

		final Handler handler  = new Handler();
		final Runnable runnable = new Runnable() {
			@Override
			public void run() {
				if (progressDialg.isShowing()) {
					progressDialg.dismiss();
				}
			}
		};

		progressDialg.setOnDismissListener(new DialogInterface.OnDismissListener() {
			@Override
			public void onDismiss(DialogInterface dialog) {
				handler.removeCallbacks(runnable);
			}
		});

		handler.postDelayed(runnable, 20000);

		edt_zipcode.setOnEditorActionListener(new OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_DONE) {

					String content = edt_zipcode.getText().toString().trim();
					if (content.length() == 0) {
						ScreenUtils.showToast(context,
								"Please enter a valid Zipcode");
					} else {
						new AsyncLoadStoreLocatorByZipCode().execute(content);
					}

                    /*return true;*/
				}
				return false;
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
				linear_header_hammenu.setVisibility(View.GONE);

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
				linear_header_hammenu.setVisibility(View.VISIBLE);

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

	private void initListView() {
		// TODO Auto-generated method stub
		listViewShops = (ListView) findViewById(R.id.list_shop);
		adapter = new ItemListAdapter_Shop(this);

		listViewShops.setAdapter(adapter);

		listViewShops.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
									long arg3) {
				// TODO Auto-generated method stub
				// LatLng posn = markerArray.get(arg2).getPosition();
				Result obj = adapter.getItem(arg2);
				mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
						new LatLng(Double.parseDouble(obj.getLat()), Double
								.parseDouble(obj.getLon())), 17));

			}
		});
	}

	private void plotPoints(List<Result> arrayLatLong2) {
		// TODO Auto-generated method stub
		for (Result objLatLng : arrayLatLong2) {

			markerArray.add(mMap.addMarker(new MarkerOptions().position(
					new LatLng(Double.parseDouble(objLatLng.getLat()), Double
							.parseDouble(objLatLng.getLon()))).title(
					objLatLng.getName() + "@" + objLatLng.getAddress())));

		}

	}

	private void updateLocationUI() {
		if (mCurrentLocation != null) {
			Log.e(TAG, "Lat : " + mCurrentLocation.getLatitude() + "Lng : " + mCurrentLocation.getLongitude() + "Time : " + mLastUpdateTime);

			// Utils.LATITUDE = mCurrentLocation.getLatitude() + "";
			// Utils.LONGITUDE = mCurrentLocation.getLongitude() + "";
			Log.e("Utils", "Lat : " + Utils.LATITUDE + "Lng : " + Utils.LONGITUDE + "Time : " + mLastUpdateTime);

			if (!isUserLocationStatus) {
				if (Utils.isNetworkAvailable(context)) {
					if (Utils.LATITUDE != null && Utils.LONGITUDE != null && Utils.LATITUDE.length() > 0 && Utils.LONGITUDE.length() > 0) {
						String content = edt_zipcode.getText().toString().trim();
						if (content.length() == 0) {
							ScreenUtils.showToast(context,
									"Please enter a valid Zipcode");
						} else {
							new AsyncLoadStoreLocatorByZipCode().execute(content);
						}
						isUserLocationStatus = true;
					}
				}

			}
		}
	}

	private void initMapComponenets() {
		// TODO Auto-generated method stub
		SupportMapFragment mapFragment=((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map));
		mapFragment.getMapAsync(this);

//		mMapFragment = ((MapFragment) getFragmentManager()
//				.findFragmentById(R.id.map));
//
//
	//	mMap = mMapFragment.getMap();


		try {
			mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(CENTER, 3));

		} catch (Exception e) {
			// TODO: handle exception

			new GeocoderTask().execute(edt_zipcode.getText().toString());

			e.printStackTrace();
		}

		mMap.animateCamera(CameraUpdateFactory.newCameraPosition(getCamPosn(
				38.979609, -102.388633, 0, 3)));

		mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

			// Use default InfoWindow frame
			@Override
			public View getInfoWindow(Marker arg0) {
				return null;
			}

			// Defines the contents of the InfoWindow
			@Override
			public View getInfoContents(Marker arg0) {

				View v = getLayoutInflater().inflate(
						R.layout.info_window_layout, null);
				TextView tvLat = null;
				try {
					String title = arg0.getTitle();

					tvLat = (TextView) v.findViewById(R.id.tv_lat);


					// Getting view from the layout file info_window_layout


					// Getting reference to the TextView to set latitude


					// Getting reference to the TextView to set longitude
					TextView tvLng = (TextView) v.findViewById(R.id.tv_lng);

					TextView txt_marker = (TextView) v
							.findViewById(R.id.txt_marker);
					txt_marker.setTypeface(ScreenUtils.returnTypeFace(context));
					// Setting the latitude

					String shopName = title.split("\\@")[0];
					String shopAddress = title.split("\\@")[1];

					tvLat.setText(shopName);
					// Setting the longitude
					tvLng.setText(shopAddress);

					//Utils.getTracker(getSelectedName(arg2), getApplication());
				} catch (Exception e) {
					// TODO: handle exception
					if (tvLat != null)
						tvLat.setText("My Location");
				}

				// Returning the view containing InfoWindow contents
				return v;

			}
		});

//		mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
//
//			@Override
//			public void onInfoWindowClick(Marker arg0) {
//				// TODO Auto-generated method stub
//				LatLng postion = arg0.getPosition();
//				Intent intent = new Intent(Intent.ACTION_VIEW, Uri
//						.parse("google.navigation:q=" + postion.latitude + ","
//								+ postion.longitude));
//				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//				startActivity(intent);
//			}
//		});

	}

	CameraPosition getCamPosn(double lat, double lon, float tilt, float zoom) {
		return new CameraPosition.Builder().target(new LatLng(lat, lon))
				.tilt(tilt).zoom(zoom).bearing(0).build();
	}

	@Override
	protected void onResumeFragments() {
		super.onResumeFragments();
		SupportMapFragment mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map));
		mMap.getMapAsync(this);
	//	((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMapAsync(this);

//		mMap = mMapFragment.getMap();

		llProducts.setBackgroundResource(R.drawable.background);
		llVideos.setBackgroundResource(R.drawable.background);
		llCalculator.setBackgroundResource(R.drawable.background);
		llProjects.setBackgroundResource(R.drawable.background);
		llBuy.setBackgroundResource(R.drawable.selectedbackground);
	}

	//9167016725
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

	}

//	@Override
//	public void onLocationChanged(Location location) {
//		LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
//		CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 10);
//		mMap.animateCamera(cameraUpdate);
//		locationManager.removeUpdates(this);
//	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) { }

	@Override
	public void onProviderEnabled(String provider) { }

	@Override
	public void onProviderDisabled(String provider) { }

	@Override
	public void onMapReady(GoogleMap googleMap) {
		mMap = googleMap;
		setUpMap();
	}

	private void setUpMap() {

		mMap.getUiSettings().setZoomControlsEnabled(true);
		mMap.getUiSettings().setCompassEnabled(true);
		mMap.getUiSettings().setAllGesturesEnabled(true);

		try {
			mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(CENTER, 3));

		} catch (Exception e) {
			// TODO: handle exception

			new GeocoderTask().execute(edt_zipcode.getText().toString());

			e.printStackTrace();
		}

		mMap.animateCamera(CameraUpdateFactory.newCameraPosition(getCamPosn(
				38.979609, -102.388633, 0, 3)));

		mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

			// Use default InfoWindow frame
			@Override
			public View getInfoWindow(Marker arg0) {
				return null;
			}

			// Defines the contents of the InfoWindow
			@Override
			public View getInfoContents(Marker arg0) {

				View v = getLayoutInflater().inflate(
						R.layout.info_window_layout, null);
				TextView tvLat = null;
				try {
					String title = arg0.getTitle();

					tvLat = (TextView) v.findViewById(R.id.tv_lat);


					// Getting view from the layout file info_window_layout


					// Getting reference to the TextView to set latitude


					// Getting reference to the TextView to set longitude
					TextView tvLng = (TextView) v.findViewById(R.id.tv_lng);

					TextView txt_marker = (TextView) v
							.findViewById(R.id.txt_marker);
					txt_marker.setTypeface(ScreenUtils.returnTypeFace(context));
					// Setting the latitude

					String shopName = title.split("\\@")[0];
					String shopAddress = title.split("\\@")[1];

					tvLat.setText(shopName);
					// Setting the longitude
					tvLng.setText(shopAddress);

					//Utils.getTracker(getSelectedName(arg2), getApplication());
				} catch (Exception e) {
					// TODO: handle exception
					if (tvLat != null)
						tvLat.setText("My Location");
				}

				// Returning the view containing InfoWindow contents
				return v;

			}
		});

	}


	class AsyncLoadStoreLocator extends AsyncTask<String, String, List<Result>> {

		ProgressDialog pdialog;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			pdialog = ScreenUtils.returnProgDialogObj(context,
					"Loading Nearest Stores...please wait", false);
			pdialog.show();
			super.onPreExecute();
		}

		@Override
		protected List<Result> doInBackground(String... params) {
			// TODO Auto-generated method stub
			try {
				// 33.78158,-84.416319
				List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
				nameValuePair.add(new BasicNameValuePair(
						WebServiceUtil.METHOD_10_1_GET_STORE_DETAILS_LAT,
						params[0]));
				nameValuePair.add(new BasicNameValuePair(
						WebServiceUtil.METHOD_10_1_GET_STORE_DETAILS_LON,
						params[1]));

//				 nameValuePair.add(new BasicNameValuePair(
//				 WebServiceUtil.METHOD_10_1_GET_STORE_DETAILS_LAT,
//				 "33.78158"));
//				 nameValuePair.add(new BasicNameValuePair(
//				 WebServiceUtil.METHOD_10_1_GET_STORE_DETAILS_LON,
//				 "-84.416319"));

				String url = WebServiceUtil.URL
						+ WebServiceUtil.METHOD_10_1_GET_STORE_DETAILS;
				Log.e("TAG","Store  Url--- "+url);
				String result = WebServiceUtil.getPostResponce(nameValuePair,
						url);
				Log.e("TAG","Store  Result--- "+result);

				Gson gson = new Gson();
				Reader reader = new InputStreamReader(
						WebServiceUtil.convertStringToInputstream(result));
				StoreLocatorResult response = gson.fromJson(reader,
						StoreLocatorResult.class);

				List<Result> reultantBanks = response.getResult();
				return reultantBanks;

			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}

			return null;
		}

		@Override
		protected void onPostExecute(List<Result> reultantBanks) {
			// TODO Auto-generated method stub
			try {
				pdialog.dismiss();
				//progressDialg.dismiss();
			} catch (Exception e) {
				// TODO: handle exception
			}
			try {
				processData(reultantBanks);


			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				new GeocoderTask().execute(edt_zipcode.getText().toString());
			}

			super.onPostExecute(reultantBanks);
		}

		private void processData(final List<Result> reultantBanks) throws Exception {
			// TODO Auto-generated method stub
			if (reultantBanks != null) {

				if (reultantBanks.size() > 0) {
					adapter.clearItems();
					mMap.clear();
				}
				mMap.addMarker(new MarkerOptions()
						.position(currentLatLong)
						.title("My Location ")
						.icon(BitmapDescriptorFactory
								.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));

				Thread t = new Thread() {
					int counter = 0;

					@Override
					public void run() {
						try {
							while (!isInterrupted()) {
								Thread.sleep(5000);
								runOnUiThread(new Runnable() {
									@Override
									public void run() {



//										Log.i("getNotificationCount", "getNotificationCount " + Utils.NOTIFICATION_COUNT);
//										nearby.setBadgeCount(Utils.NOTIFICATION_COUNT);

										mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLong, 10f));

										for (Result obj : reultantBanks) {
											adapter.addItem(obj);
										}
										plotPoints(reultantBanks);

									}
								});

								// counter used to terminate this thread after 15 sec
								if (counter > 3) {
									this.interrupt();
									Log.e("MainActivity", "Stopped thread after 15 sec");
								} else {
									Log.e("MainActivity", "Waiting till 15 sec...");
								}
								counter++;
							}
						} catch (InterruptedException e) {
						}
					}
				};
				t.start();




			}
		}

	}

	class AsyncLoadStoreLocatorByZipCode extends
			AsyncTask<String, String, List<Result>> {

		ProgressDialog pdialog;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub

			pdialog = ScreenUtils.returnProgDialogObj(context,
					"Loading Stores in "
							+ edt_zipcode.getText().toString().trim()
							+ " ...please wait", false);
			pdialog.show();
			super.onPreExecute();
		}

		@Override
		protected List<Result> doInBackground(String... params) {
			// TODO Auto-generated method stub
			try {
				// 33.78158,-84.416319
				List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
				nameValuePair.add(new BasicNameValuePair(
						WebServiceUtil.METHOD_16_1_STORE_FROM_ZIP_ZIPCODE,
						params[0]));

				String url = WebServiceUtil.URL
						+ WebServiceUtil.METHOD_16_1_STORE_FROM_ZIP;
				Log.e("TAG","Store By zip Url--- "+url);
				String result = WebServiceUtil.getPostResponce(nameValuePair,
						url);
				Log.e("TAG","Store By zip Result--- "+result);

				Gson gson = new Gson();
				Reader reader = new InputStreamReader(
						WebServiceUtil.convertStringToInputstream(result));
				StoreLocatorResult response = gson.fromJson(reader,
						StoreLocatorResult.class);


				List<Result> reultantBanks = response.getResult();



				return reultantBanks;

			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}

			return null;
		}

		@Override
		protected void onPostExecute(List<Result> reultantBanks) {
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
				new GeocoderTask().execute(edt_zipcode.getText().toString());
				Log.e(TAG,"Current Location from GeoTask!!");
				progressDialg.dismiss();
			}

			super.onPostExecute(reultantBanks);
		}

		private void processData(List<Result> reultantBanks) throws Exception {
			// TODO Auto-generated method stub
			if (reultantBanks != null) {

				if (reultantBanks.size() > 0) {
					adapter.clearItems();
					mMap.clear();
				}
				mMap.addMarker(new MarkerOptions()
						.position(currentLatLong)
						.title("My Location")
						.icon(BitmapDescriptorFactory
								.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));




				Result firstResult = reultantBanks.get(0);
				double lat = Double.parseDouble(firstResult.getLat());
				double lon = Double.parseDouble(firstResult.getLon());
				//mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));

				Log.e(TAG,"Current Latitude-- "+lat);
				Log.e(TAG,"Current Longitude-- "+lon);

				mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lon), 10f));
				for (Result obj : reultantBanks) {
					adapter.addItem(obj);
				}

				plotPoints(reultantBanks);
				adapter.notifyDataSetChanged();

			}
		}

	}

	private void getLocationFromNetworkOrGPS(Context context) {

		try {

			MyLocation myLocation = new MyLocation();
			myLocation.getLocation(context, locationResult);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public MyLocation.LocationResult locationResult = new MyLocation.LocationResult() {
		@Override
		public void gotLocation(final Location location) {
			// Got the location!

			//  Log.e("newlog", location + "  %%final location%");
			if (location != null) {
				try {
					// "33.7860975", "-84.3907286"
					currentLatLong = new LatLng(location.getLatitude(),
							location.getLongitude());

					//currentLatLong = new LatLng( 26.019787 ,-80.233574);

					Log.e(TAG,"Current Latlong **** "+String.valueOf(currentLatLong));

					runOnUiThread(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							try {
								userLocationmarker
										.add(mMap.addMarker(new MarkerOptions()
												.position(
														new LatLng(
																location.getLatitude(),
																location.getLongitude()))
												.title("My Location ")
												.icon(BitmapDescriptorFactory
														.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW))));

								new AsyncLoadStoreLocator()
										.execute(String.valueOf(currentLatLong));



							} catch (Exception e) {
								// TODO: handle exception
							}
						}
					});

				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
					runOnUiThread(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							Toast.makeText(context,
									"Failed to fetch location",
									Toast.LENGTH_LONG).show();
						}
					});

				}

			} else {
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						Toast.makeText(context,
								"Failed to fetch location", Toast.LENGTH_LONG)
								.show();
					}
				});
			}
		}
	};
	@Override
	public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		if(ActivityCompat.checkSelfPermission(this, permissions[0]) == PackageManager.PERMISSION_GRANTED){
			switch (requestCode) {

				case PERMISSION_REQUEST_LOCATION:
					if (grantResults.length > 0) {

						locationAccepted=grantResults[0]==PackageManager.PERMISSION_GRANTED;

						if (locationAccepted) {
						}
						// Snackbar.make(view, "Permission Granted, Now you can access location data and camera.", Snackbar.LENGTH_LONG).show();
						else {

							Toast.makeText(context,"Permission denied!!",Toast.LENGTH_LONG).show();

							if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
								if (shouldShowRequestPermissionRationale(ACCESS_FINE_LOCATION)) {
									showMessageOKCancel("Enable permission for location to access",
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



			}
			//Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();
		}
		else{
			Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
			locationAccepted=false;
			finish();

		}

	}

	private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
		new androidx.appcompat.app.AlertDialog.Builder(WhereToBuyActivity.this)
				.setMessage(message)
				.setPositiveButton("OK", okListener)
				.setNegativeButton("Cancel", null)
				.create()
				.show();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);

		switch (requestCode) {
			case RESULT_OK:
				String contents = data.getStringExtra("SCAN_RESULT");
				Log.d("newlog", "contents: " + contents);
				Intent intent = new Intent(context, ProductDetailActivity.class);
				intent.putExtra("type", "2");
				intent.putExtra("cat_id", contents);
				startActivity(intent);
				break;
			case RESULT_CANCELED:
				Log.d("newlog", "RESULT_CANCELED");
				break;
			case REQUEST_LOCATION:
				switch (resultCode) {
					case Activity.RESULT_OK: {
						// All required changes were successfully made
						Toast.makeText(WhereToBuyActivity.this, "Location enabled by user!", Toast.LENGTH_LONG).show();
						break;
					}
					case Activity.RESULT_CANCELED: {
						// The user was asked to change settings, but chose not to
						Toast.makeText(WhereToBuyActivity.this, "Location not enabled, user cancelled.", Toast.LENGTH_LONG).show();
						break;
					}
					default: {
						break;
					}
				}

				break;

		}
	}


	public void onClickHomeMenu(View view) {
		Intent intent = new Intent(context, MainActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
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
									WhereToBuyActivity.this,
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

	private void requestPermissionLocation() {

		ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_LOCATION);

	}



	private class GeocoderTask extends AsyncTask<String, Void, List<Address>>{

		@Override
		protected List<Address> doInBackground(String... locationName) {
			// Creating an instance of Geocoder class
			Geocoder geocoder = new Geocoder(getBaseContext());
			List<Address> addresses = null;

			try {
				// Getting a maximum of 3 Address that matches the input text
				addresses = geocoder.getFromLocationName(locationName[0], 3);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return addresses;
		}

		@Override
		protected void onPostExecute(List<Address> addresses) {

			if(addresses==null || addresses.size()==0){
				Toast.makeText(getBaseContext(), "No Location found", Toast.LENGTH_SHORT).show();
			}

			mMap.clear();
			// Clears all the existing markers on the map
			// Adding Markers on Google Map for each matching address
			for(int i=0;i<addresses.size();i++){

				Address address = (Address) addresses.get(i);

				// Creating an instance of GeoPoint, to display in Google Map
				latLng = new LatLng(address.getLatitude(), address.getLongitude());

				String addressText = String.format("%s, %s",
						address.getMaxAddressLineIndex() > 0 ? address.getAddressLine(0) : "",
						address.getCountryName());

				markerOptions = new MarkerOptions();
				markerOptions.position(latLng);
				markerOptions.title(addressText);

				mMap.addMarker(markerOptions);

				// Locate the first location
				if(i==0)
					mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
			}
		}
	}

	@Override
	protected void onPause() {
		progressDialg.dismiss();;
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		progressDialg.dismiss();;
		super.onDestroy();
	}
}
