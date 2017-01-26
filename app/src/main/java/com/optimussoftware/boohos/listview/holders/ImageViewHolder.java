package com.optimussoftware.boohos.listview.holders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.optimussoftware.boohos.R;
import com.optimussoftware.image.Factory;
import com.optimussoftware.image.SizeImage;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by guerra on 13/07/16.
 */
public class ImageViewHolder extends RecyclerView.ViewHolder {

    //controls
    @BindView(R.id.image) ImageView image;

    //class
    private Context context;

    public ImageViewHolder(View v) {
        super(v);
        ButterKnife.bind(this, v);
        this.context = v.getContext();
    }

    public void setImage(String url) {
        SizeImage sizeImage = new SizeImage(200,
                200);
        Factory.setImage(image, context, url, sizeImage);
    }
}
