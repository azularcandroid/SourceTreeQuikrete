package com.quikrete.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.quikrete.R;
import com.quikrete.gsondata.storelocator.Result;

public class ItemListAdapter_Shop extends BaseAdapter {
	List<Result> itemList = new ArrayList<Result>();
	Context context;

	public ItemListAdapter_Shop(Context c) {
		context = c;
	}

	@Override
	public int getCount() {
		return itemList.size();
	}

	@Override
	public Result getItem(int position) {
		return itemList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View arg1, ViewGroup parent) {

		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View itemView = inflater.inflate(R.layout.list_view_row_store, parent,
				false);
		TextView txt_shop_name = (TextView) itemView
				.findViewById(R.id.txt_shop_name);

		txt_shop_name.setText(itemList.get(position).getName());

		TextView txt_shop_address = (TextView) itemView
				.findViewById(R.id.txt_shop_address);

		txt_shop_address.setText(itemList.get(position).getAddress());

		return itemView;
	}

	public void addItem(Result item) {
		itemList.add(item);
		notifyDataSetChanged();
	}

	public void clearItems() {
		itemList.clear();
		notifyDataSetChanged();
	}
}
