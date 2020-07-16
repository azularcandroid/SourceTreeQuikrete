package com.quikrete.adapter;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.koushikdutta.urlimageviewhelper.UrlImageViewCallback;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;
import com.quikrete.R;
import com.quikrete.data.FavResult;
import com.quikrete.gsondata.videocat.Result;
import com.quikrete.utils.AddRemoveFavourites;
import com.quikrete.utils.ScreenUtils;
import com.quikrete.utils.Utils;
import com.quikrete.utils.WebServiceUtil;
import com.sharedpreference.SharedPreferenceHelper;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import static android.R.attr.id;

public class ItemListAdapter_Video_Poplrty extends BaseAdapter {
    List<Result> itemList = new ArrayList<Result>();
    Context context;
    int layout;
    String id = "";

    public ItemListAdapter_Video_Poplrty(Context c, int layout) {
        context = c;
        this.layout = layout;
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

    // R.layout.list_view_row
    @SuppressLint("ViewHolder")
    @Override
    public View getView(final int position, View arg1, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View itemView = inflater.inflate(layout, parent, false);
        TextView txt_prd_name = (TextView) itemView
                .findViewById(R.id.txt_prd_name);
        try {
            String context = itemList.get(position).getTitle();
            txt_prd_name.setText(context.replaceAll("&amp;", "&"));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        ImageView img_prd_cat = (ImageView) itemView
                .findViewById(R.id.img_prd_cat);
        String imageName = itemList.get(position).getImage().replaceAll("\\s","");
        if (imageName != null) {
            imageName = imageName.replaceAll("\\/default", "\\/mqdefault");
            UrlImageViewHelper.setUrlDrawable(img_prd_cat,
                    imageName,
                    R.drawable.banner, new UrlImageViewCallback() {
                        @Override
                        public void onLoaded(ImageView imageView,
                                             Bitmap loadedBitmap, String url,
                                             boolean loadedFromCache) {
                            if (!loadedFromCache) {
                                ScaleAnimation scale = new ScaleAnimation(0, 1,
                                        0, 1, ScaleAnimation.RELATIVE_TO_SELF,
                                        .5f, ScaleAnimation.RELATIVE_TO_SELF,
                                        .5f);
                                scale.setDuration(300);
                                scale.setInterpolator(new OvershootInterpolator());
                                imageView.startAnimation(scale);
                            }
                        }
                    });
        }

        final TextView txt_star = (TextView) itemView.findViewById(R.id.txt_star);
        txt_star.setTypeface(ScreenUtils.returnTypeFace(context));

        ScreenUtils.changeStarColor(itemList.get(position).getId(), txt_star,
                context);

        txt_star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                id = itemList.get(position).getId();
                AddRemoveFavourites.onClickAddToFav(id, txt_star, context);
                Log.e("AddRemoveFavourites","Video");
            }
        });

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
