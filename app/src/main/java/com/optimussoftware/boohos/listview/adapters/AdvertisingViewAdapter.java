package com.optimussoftware.boohos.listview.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.optimussoftware.db.Advertising;
import com.optimussoftware.boohos.R;
import com.optimussoftware.boohos.listview.holders.AdvertisingViewHolder;
import com.optimussoftware.boohos.util.Commons;
import com.optimussoftware.boohos.viewDetail.ViewDetailMaterial;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by guerra on 02/08/16.
 * Similar Offer
 */

public class AdvertisingViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Advertising> advertisingList;
    private int width = 0;
    private Context context;

    public AdvertisingViewAdapter(Context context) {
        this.advertisingList = new ArrayList<>();
        this.context = context;
        width = Commons.getWidthDisplay(context) / 3;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.advertising_item, parent, false);
        return new AdvertisingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        AdvertisingViewHolder myHol = (AdvertisingViewHolder) holder;
        int marginDp = Commons.dpToPx(context, 5);
        int sizeView = (Commons.getWidthDisplay(context) / 3) - (marginDp * 2);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(sizeView, sizeView);
        myHol.content_image_advertising.setLayoutParams(layoutParams);
        myHol.content_advertising.setPadding(marginDp, 0, marginDp, 0);

        /*RelativeLayout.LayoutParams linearLayout = new RelativeLayout.LayoutParams(width, width);
        linearLayout.setMargins(1, 0, 1, 0);
        myHol.content_image_advertising.setLayoutParams(linearLayout);*/
        myHol.setView(advertisingList.get(position));
        myHol.content_advertising.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ViewDetailMaterial.class);
                intent.putExtra("item", advertisingList.get(position));
                context.startActivity(intent);
                if (context instanceof ViewDetailMaterial) {
                    ((ViewDetailMaterial) context).finish();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return advertisingList.size();
    }

    public void addAvertingList(List<Advertising> mAdvertisingList) {
        advertisingList.addAll(getItemCount(), mAdvertisingList);
        notifyDataSetChanged();
    }

    public void clearAvertingList() {
        advertisingList.clear();
        notifyDataSetChanged();
    }
}
