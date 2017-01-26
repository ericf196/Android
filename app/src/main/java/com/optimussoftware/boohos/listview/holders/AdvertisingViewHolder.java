package com.optimussoftware.boohos.listview.holders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.optimussoftware.db.Advertising;
import com.optimussoftware.boohos.R;
import com.optimussoftware.boohos.data.Constants;
import com.optimussoftware.boohos.listview.model.AdvertisingContent;
import com.optimussoftware.boohos.util.Commons;
import com.optimussoftware.boohos.widget.OptimusTextView;
import com.optimussoftware.image.Factory;
import com.optimussoftware.image.SizeImage;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Perez on 6/7/2016.
 */
public class AdvertisingViewHolder extends RecyclerView.ViewHolder {

    //controls
    public
    @BindView(R.id.content_advertising)
    LinearLayout content_advertising;
    public
    @BindView(R.id.content_image_advertising)
    LinearLayout content_image_advertising;
    public
    @BindView(R.id.image_advertising)
    ImageView image_advertising;
    public
    @BindView(R.id.name_advertising)
    OptimusTextView name_advertising;
    public
    @BindView(R.id.description_advertising)
    OptimusTextView description_advertising;

    private Context context;

    public AdvertisingViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
        this.context = view.getContext();
    }

    public void setView(Advertising advertisingItem) {
        SizeImage sizeImage = new SizeImage(Commons.getWidthDisplay(context), Commons.getHeightDisplay(context) / 3);
        Factory.setImage(image_advertising, context, AdvertisingContent.pathViewDefault(context, advertisingItem), sizeImage);
        name_advertising.setText(advertisingItem.getName());
        description_advertising.setText(advertisingItem.getDescription());
    }

    //advertising by campaign
    public void setView(com.optimussoftware.api.response.advertising.AdvertisingByCampaign.Item advertisingItem) {
        SizeImage sizeImage = new SizeImage(Commons.getWidthDisplay(context), Commons.getHeightDisplay(context) / 3);
        //Factory.setImage(image_advertising, context, AdvertisingContent.pathViewDefault(context, advertisingItem), sizeImage);
        Factory.setImage(image_advertising, context, Constants.URL_WEB + advertisingItem.getImages().get(0).getFile(), sizeImage);
        name_advertising.setText(advertisingItem.getName());
        description_advertising.setText(advertisingItem.getDescription());
    }

}
