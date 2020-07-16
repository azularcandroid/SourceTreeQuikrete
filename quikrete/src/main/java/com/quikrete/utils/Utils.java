package com.quikrete.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.TimeZone;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.share.Sharer;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.quikrete.GoogleAnalyticsApp;
import com.quikrete.GoogleAnalyticsApp.TrackerName;
import com.sharedpreference.SharedPreferenceHelper;

public class Utils {
	//

	// public static final String startDate = "2015-02-02 08:00:00";
	// public static final String endDate = "2015-02-06 23:59:00";

	public static String LATITUDE="";
	public static String LONGITUDE="";

	@SuppressLint("MissingPermission")
	public static void callThisNumber(String phoneNumber, Context ctx) {

		Intent callIntent = new Intent(Intent.ACTION_CALL);
		callIntent.setData(Uri.parse("tel:" + phoneNumber));
		ctx.startActivity(callIntent);
	}

	public static boolean isNetworkAvailable(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager
				.getActiveNetworkInfo();

		return activeNetworkInfo != null;
	}

	public static String decodeString(String stingvalue)
			throws UnsupportedEncodingException {

		return URLDecoder.decode(stingvalue, "UTF-8");
	}

	public static void sendEmail(Context context, String subject, String body) {
		Intent i = new Intent(Intent.ACTION_SEND);
		i.setType("message/rfc822");
		// i.putExtra(Intent.EXTRA_EMAIL, recipients);
		i.putExtra(Intent.EXTRA_SUBJECT, subject);
		i.putExtra(Intent.EXTRA_TEXT, body);
		try {
			context.startActivity(Intent.createChooser(i, "Send mail..."));
		} catch (android.content.ActivityNotFoundException ex) {
			Toast.makeText(context, "There are no email clients installed.",
					Toast.LENGTH_SHORT).show();
		}
	}

	public static boolean checkAddToFav(String key, Context ctx) {
		// TODO Auto-generated method stub
		try {
			String value = SharedPreferenceHelper.getPreference(ctx, key);
			if (value == null || value.equals("") || value.equals("0"))
				return false;
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}

	}

	public static String getFavIds(Context ctx) {
		SharedPreferences sharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(ctx);
		Map<String, ?> keys = sharedPreferences.getAll();
		StringBuffer buff = new StringBuffer();
		for (Map.Entry<String, ?> entry : keys.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue().toString();
			if (value != null && value.equals("1")) {
				buff.append(key).append(",");
			}
		}
		return buff.toString();
	}

	public static void saveInSdcard(String content) {
		try {
			File myFile = new File("/sdcard/mysdfile.txt");
			myFile.createNewFile();
			FileOutputStream fOut = new FileOutputStream(myFile);
			OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
			myOutWriter.append(content);
			myOutWriter.close();
			fOut.close();

		} catch (Exception e) {

		}
	}

	public static boolean isWithinRange(Date testDate, Context context)
			throws Exception {

		try {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			// DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date startDateObj = df
					.parse(SharedPreferenceHelper.getPreference(context,
							SharedPreferenceHelper.APP_SWEEPSTAKES_START_DATE));
			Date endDateObj = df.parse(SharedPreferenceHelper.getPreference(
					context, SharedPreferenceHelper.APP_SWEEPSTAKES_END_DATE));

			return !(testDate.before(startDateObj) || testDate
					.after(endDateObj));

		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}

	public static Date getTodaysDateObj() throws Exception {
		Date date = new Date();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		// Use Madrid's time zone to format the date in
		df.setTimeZone(TimeZone.getTimeZone("America/Los_Angeles"));

		date = df.parse(df.format(date));

		return date;
	}

	public static void getTracker(String screenName, Application context) {
		Tracker t = ((GoogleAnalyticsApp) context)
				.getTracker(TrackerName.APP_TRACKER);
		t.setScreenName(screenName);
		t.send(new HitBuilders.AppViewBuilder().build());

	}

	public static void hideKeyboard(Context context, View view) {
		InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
		inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
	}

	public static void shareImageOnFacebook(Bitmap image, Activity activity, ShareDialog shareDialog) {

		SharePhoto photo = new SharePhoto.Builder()
				.setBitmap(image)
				.build();

		SharePhotoContent content = new SharePhotoContent.Builder()
				.addPhoto(photo)
				.build();

		ShareDialog.show(activity, content);
	}

	public static ShareDialog initFacebook(Context context, Activity activity) {
		FacebookSdk.sdkInitialize(context);
		CallbackManager callbackManager = CallbackManager.Factory.create();
		ShareDialog shareDialog = new ShareDialog(activity);
		shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {

			@Override
			public void onSuccess(Sharer.Result result) {

			}

			@Override
			public void onCancel() {

			}

			@Override
			public void onError(FacebookException e) {

			}
		});
		return new ShareDialog(activity);
	}



}
