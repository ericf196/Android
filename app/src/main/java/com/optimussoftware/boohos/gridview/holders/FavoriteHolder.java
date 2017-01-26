package com.optimussoftware.boohos.gridview.holders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.optimussoftware.db.Advertising;
import com.optimussoftware.boohos.R;
import com.optimussoftware.boohos.listview.model.AdvertisingContent;
import com.optimussoftware.boohos.util.Commons;
import com.optimussoftware.boohos.widget.OptimusTextView;
import com.optimussoftware.image.Factory;
import com.optimussoftware.image.SizeImage;


import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by guerra on 20/10/16.
 * Favorite Item
 */

public class FavoriteHolder extends RecyclerView.ViewHolder {

    private Context context;
    private int sizeView;

    @BindView(R.id.content_advertising)
    public LinearLayout content_advertising;
    @BindView(R.id.content_image_advertising)
    public LinearLayout content_image_advertising;
    @BindView(R.id.image_advertising)
    public ImageView image_advertising;
    @BindView(R.id.name_advertising)
    OptimusTextView name_advertising;
    @BindView(R.id.description_advertising)
    OptimusTextView description_advertising;
    @BindView(R.id.icon_advertising)
    ImageView icon_advertising;


    public FavoriteHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.context = itemView.getContext();
        sizeView = Commons.getWidthDisplay(context) / 3;
    }

    public void setView(Advertising advertising) {
        SizeImage sizeImage = new SizeImage(sizeView, sizeView);
        try {
            Factory.setImage(image_advertising, context,
                    AdvertisingContent.pathViewDefault(context, advertising),
                    sizeImage);
        } catch (Exception e) {
            Log.d("FavoriteHolder", "setView Exception" + e);
        }
        Log.e("Favorite", "Holder.getName()" + advertising.getName());
        name_advertising.setText(advertising.getName());
        description_advertising.setText(advertising.getDescription());
        icon_advertising.setVisibility(View.VISIBLE);
    }

}
