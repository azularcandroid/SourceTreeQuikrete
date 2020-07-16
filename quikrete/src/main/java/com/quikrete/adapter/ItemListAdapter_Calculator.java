package com.quikrete.adapter;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.quikrete.R;
import com.quikrete.gsondata.searchresult.Calculator;
import com.quikrete.utils.ScreenUtils;

public class ItemListAdapter_Calculator extends BaseAdapter {
	List<Calculator> itemList = new ArrayList<Calculator>();
	Context context;
	int layout;

	public ItemListAdapter_Calculator(Context c, int layout) {
		context = c;
		this.layout = layout;
	}

	@Override
	public int getCount() {
		return itemList.size();
	}

	@Override
	public Calculator getItem(int position) {
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
			String context = itemList.get(position).getTitle();
			txt_prd_name.setText(context.replaceAll("&amp;", "&"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

//		ImageView img_prd_cat = (ImageView) itemView
//				.findViewById(R.id.img_prd_cat);
//
//		UrlImageViewHelper.setUrlDrawable(img_prd_cat, itemList.get(position)
//				.getImage(), R.drawable.ic_launcher,
//				new UrlImageViewCallback() {
//					@Override
//					public void onLoaded(ImageView imageView,
//							Bitmap loadedBitmap, String url,
//							boolean loadedFromCache) {
//						if (!loadedFromCache) {
//							ScaleAnimation scale = new ScaleAnimation(0, 1, 0,
//									1, ScaleAnimation.RELATIVE_TO_SELF, .5f,
//									ScaleAnimation.RELATIVE_TO_SELF, .5f);
//							scale.setDuration(300);
//							scale.setInterpolator(new OvershootInterpolator());
//							imageView.startAnimation(scale);
//						}
//					}
//				});
		
		TextView txt_star = (TextView) itemView.findViewById(R.id.txt_star);
		txt_star.setTypeface(ScreenUtils.returnTypeFace(context));
		
		ScreenUtils.changeStarColor(itemList.get(position).getId(), txt_star, context);

		return itemView;
	}

	public void addItem(Calculator item) {
		itemList.add(item);
		notifyDataSetChanged();
	}

	public void clearItems() {
		itemList.clear();
		notifyDataSetChanged();
	}
}
