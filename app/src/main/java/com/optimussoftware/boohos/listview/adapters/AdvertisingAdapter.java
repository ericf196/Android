package com.optimussoftware.boohos.listview.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.optimussoftware.boohos.widget.BaseActivity;
import com.optimussoftware.db.Advertising;
import com.optimussoftware.boohos.R;
import com.optimussoftware.boohos.data.DBController;
import com.optimussoftware.boohos.listview.model.AdvertisingContent;
import com.optimussoftware.boohos.util.Commons;
import com.optimussoftware.boohos.viewDetail.ViewDetailMaterial;
import com.optimussoftware.image.Factory;
import com.optimussoftware.image.SizeImage;

import java.util.List;

/**
 * Created by leonardojpr on 8/09/16.
 */
public class AdvertisingAdapter extends RecyclerView.Adapter<AdvertisingAdapter.ViewHolder> {

    private Context context;
    private List<Advertising> mValues;
    private int width = 0;
    DBController dbController;

    public AdvertisingAdapter(Context context, List<Advertising> mValues) {
        this.context = context;
        this.mValues = mValues;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.advertising_category_item, parent, false);
        return new ViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.setView(mValues.get(position));
        holder.photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                starReviewActivity(context, mValues.get(position), holder);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView photo;
        View view, view2;

        public ViewHolder(View itemView) {
            super(itemView);
            int marginDp = Commons.dpToPx(context, 5);
            int sizeView = (Commons.getWidthDisplay(context) / 3) - (marginDp * 2);
            photo = (ImageView) itemView.findViewById(R.id.img_advertising);
            view = (View) itemView.findViewById(R.id.view_advertising);
            view2 = (View) itemView.findViewById(R.id.view2_advertising);
            RelativeLayout.LayoutParams linearLayout = new RelativeLayout.LayoutParams(sizeView, sizeView);
            linearLayout.setMargins(marginDp, 0, marginDp, 0);
            photo.setLayoutParams(linearLayout);
            view.setLayoutParams(linearLayout);
            view2.setLayoutParams(linearLayout);
        }

        public void setView(Advertising advertisingItem) {
            SizeImage sizeImage = new SizeImage(Commons.getWidthDisplay(context) / 3, Commons.getWidthDisplay(context) / 3);
            Factory.setImage(photo, context, AdvertisingContent.pathViewDefault(context, advertisingItem), sizeImage);
        }
    }

    private void starReviewActivity(Context context, Advertising advertising,  ViewHolder holder) {
        Intent intent = new Intent(context, ViewDetailMaterial.class);
        intent.putExtra("item", advertising);
        Pair<View, String> p = new Pair<View, String>(holder.photo,
                context.getString(R.string.transition_name_advertising));
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                (BaseActivity) context, p);
        ActivityCompat.startActivity((BaseActivity) context, intent, options.toBundle());
    }

}
