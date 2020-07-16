package com.quikrete.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.quikrete.R;

public class ScreenUtils {

	/**
	 * fullScreen method hides the status and notification bar, making the
	 * application appear in full screen mode
	 */
	public static void fullScreen(Activity activity) {

		activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
		activity.getWindow().setFlags(
				WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
	}

	/**
	 * snippet used all over the app(where ever necessary) to use external fonts
	 * - fontawesome
	 */
	public static Typeface returnTypeFace(Context ctx) {
		return Typeface.createFromAsset(ctx.getAssets(),
				"fonts/fontawesome-webfont.ttf");
	}

	public static Typeface returnTypeFaceHelv(Context ctx) {
		return Typeface.createFromAsset(ctx.getAssets(),
				"fonts/helveticaneue-webfont.ttf");
	}

	/**
	 * snippet to disable auto pop up of soft keypad in screens where there is
	 * an input field
	 */
	public static void disableAutoPopUp(EditText editTextField, Context ctx) {
		InputMethodManager imm = (InputMethodManager) ctx
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(editTextField.getWindowToken(), 0);
	}

	/** programatically set padding values to views */
	public static int[] getPaddingVal(EditText value) {
		int val[] = new int[4];
		val[0] = value.getPaddingLeft();
		val[1] = value.getPaddingTop();
		val[2] = value.getPaddingRight();
		val[3] = value.getPaddingBottom();
		return val;
	}

	/** snippet to get the background color of the view */
	public static int getBgColor(View view) {
		int color = Color.TRANSPARENT;
		Drawable background = view.getBackground();
		if (background instanceof ColorDrawable)
			color = ((ColorDrawable) background).getColor();

		// Log.e("newlog", color+"");
		return color;
	}

	/**
	 * snippet used all over the app(where ever necessary) to use external fonts
	 * - helveticaneue
	 */
	public static Typeface returnHelvetica(Context context) {
		return Typeface.createFromAsset(context.getAssets(),
				"fonts/helveticaneue-webfont.ttf");
	}

	/**
	 * snippet used all over the app(where ever necessary) to use external fonts
	 * - helvetica-neue-bold
	 */
	public static Typeface returnHelveticaBold(Context context) {
		return Typeface.createFromAsset(context.getAssets(),
				"fonts/helvetica-neue-bold.ttf");
	}

	public static void showMltpleCHoiceDlg(CharSequence[] array, Context ctx,
			final TextView text_label, String title, final TextView text_input_type,
			CharSequence[] array_label) {
		
		final CharSequence[] items_label = array_label;
		final CharSequence[] items = array;

		AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
		//builder.setTitle("Select an Item");

		builder.setItems(items, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				text_label.setText(items_label[which]);
				text_input_type.setText(items[which]);
			}
		});

		builder.show();
	}

	public static ProgressDialog returnProgDialogObj(Context context,
			String message, boolean flag) {

		ProgressDialog pdiaolog = new ProgressDialog(context);
		pdiaolog.setMessage(message);
		pdiaolog.setCancelable(flag);

		return pdiaolog;
	}

	public static void showToast(Context context, String message) {
		Toast.makeText(context, message, Toast.LENGTH_LONG).show();
	}

	public static float returnPixel(Context context, int dp) {
		Resources r = context.getResources();
		float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
				r.getDisplayMetrics());
		return px;
	}

	public static void changeStarColor(String id, TextView txt_star,
			TextView txt_add_to_fav, Context context) {
		if (Utils.checkAddToFav(id, context)) {
			txt_star.setTextColor(context.getResources().getColor(
					R.color.yellow_color));
			txt_add_to_fav.setText("Add to Favorites");
		} else {
			txt_star.setTextColor(context.getResources().getColor(
					R.color.star_unlike));
			txt_add_to_fav.setText("Add to Favorites");
		}
	}

	public static void changeStarColor(String id, TextView txt_star,
			Context context) {
		if (id != null) {
			if (Utils.checkAddToFav(id, context)) {
				txt_star.setTextColor(context.getResources().getColor(
						R.color.yellow_color));
			} else {
				txt_star.setTextColor(context.getResources().getColor(
						R.color.star_unlike));
			}
		}

	}

	public static Bitmap shrinkmethod(String file, int width, int height) {
		BitmapFactory.Options bitopt = new BitmapFactory.Options();
		bitopt.inJustDecodeBounds = true;
		Bitmap bit = BitmapFactory.decodeFile(file, bitopt);

		int h = (int) Math.ceil(bitopt.outHeight / (float) height);
		int w = (int) Math.ceil(bitopt.outWidth / (float) width);

		if (h > 1 || w > 1) {
			if (h > w) {
				bitopt.inSampleSize = h;

			} else {
				bitopt.inSampleSize = w;
			}
		}
		bitopt.inJustDecodeBounds = false;
		bit = BitmapFactory.decodeFile(file, bitopt);

		return bit;

	}

	public static void dismissKeyboard(EditText myEditText, Context context) {

		InputMethodManager imm = (InputMethodManager) context
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(myEditText.getWindowToken(), 0);
	}

	public static int HEADER_INIT_Y = 0;
	public static int HEADER_HEIGHT = 0;

	public static int CONTENT_INIT_Y = 0;

}
