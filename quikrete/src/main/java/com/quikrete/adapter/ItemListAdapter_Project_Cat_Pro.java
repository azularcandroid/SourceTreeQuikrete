package com.quikrete.adapter;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.koushikdutta.urlimageviewhelper.UrlImageViewCallback;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;
import com.quikrete.R;
import com.quikrete.gsondata.projectcat.Pro;

public class ItemListAdapter_Project_Cat_Pro extends BaseAdapter {
	List<Pro> itemList = new ArrayList<Pro>();
	Context context;
	int layout;

	public ItemListAdapter_Project_Cat_Pro(Context c, int layout) {
		context = c;
		this.layout = layout;
	}

	@Override
	public int getCount() {
		return itemList.size();
	}

	@Override
	public Pro getItem(int position) {
		return itemList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	// R.layout.list_view_row
	@SuppressLint("ViewHolder")
	@Override
	public View getView(int position, View arg1, ViewGroup parent) {

		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View itemView = inflater.inflate(layout, parent, false);
		TextView txt_prd_name = (TextView) itemView
				.findViewById(R.id.txt_prd_name);
		try {
			String context = itemList.get(position).getName();
			txt_prd_name.setText(context.replaceAll("&amp;","&"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		ImageView img_prd_cat = (ImageView) itemView
				.findViewById(R.id.img_prd_cat);
		
		UrlImageViewHelper.setUrlDrawable(img_prd_cat, itemList.get(position).getImage(), R.drawable.banner, new UrlImageViewCallback() {
             @Override
             public void onLoaded(ImageView imageView, Bitmap loadedBitmap, String url, boolean loadedFromCache) {
                 if (!loadedFromCache) {
                     ScaleAnimation scale = new ScaleAnimation(0, 1, 0, 1, ScaleAnimation.RELATIVE_TO_SELF, .5f, ScaleAnimation.RELATIVE_TO_SELF, .5f);
                     scale.setDuration(300);
                     scale.setInterpolator(new OvershootInterpolator());
                     imageView.startAnimation(scale);
                 }
             }
         });

		return itemView;
	}

	public void addItem(Pro item) {
		itemList.add(item);
		notifyDataSetChanged();
	}
	
	public void addList(){
		
		
	}

	public void clearItems() {
		itemList.clear();
		notifyDataSetChanged();
	}
}
