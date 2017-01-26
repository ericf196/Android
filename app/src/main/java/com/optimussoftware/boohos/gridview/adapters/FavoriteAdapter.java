package com.optimussoftware.boohos.gridview.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.optimussoftware.api.core.Factory;
import com.optimussoftware.boohos.listview.adapters.FooterViewHolder;
import com.optimussoftware.boohos.listview.holders.ReviewViewHolder;
import com.optimussoftware.boohos.widget.BaseActivity;
import com.optimussoftware.db.Advertising;
import com.optimussoftware.boohos.main.MainActivity;
import com.optimussoftware.boohos.R;
import com.optimussoftware.boohos.gridview.holders.FavoriteHolder;
import com.optimussoftware.boohos.util.Commons;
import com.optimussoftware.boohos.viewDetail.ViewDetailMaterial;
import com.optimussoftware.db.Favorites;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by guerra on 20/10/16.
 * FavoriteAdapter
 */

public class FavoriteAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Advertising> itemList;
    private Context context;

    public FavoriteAdapter() {
        this.itemList = new ArrayList<>();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.advertising_item, parent, false);
        return new FavoriteHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final int pos = holder.getAdapterPosition();

        Advertising advertising = itemList.get(position);
        FavoriteHolder favoriteHolder = (FavoriteHolder) holder;
        int marginDp = Commons.dpToPx(context, 5);
        int marginTop = Commons.dpToPx(context, 10);
        int sizeView = (Commons.getWidthDisplay(context) / 3) - (marginDp * 2);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                sizeView);
        favoriteHolder.content_image_advertising.setLayoutParams(lp);
        favoriteHolder.content_image_advertising.setPadding(marginDp, marginTop, marginDp, 0);
        setClick(favoriteHolder, advertising);
        favoriteHolder.setView(advertising);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public void clearAdvertisingFavorites() {
        itemList.clear();
        notifyDataSetChanged();
    }

    public void addAdvertisingFavorites(List<Advertising> adverstingsLists) {
        itemList.addAll(adverstingsLists);
        notifyDataSetChanged();
    }

    private void setClick(final FavoriteHolder favoriteHolder, final Advertising advertising) {
        favoriteHolder.content_advertising.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                starReviewActivity(context, advertising, favoriteHolder);
            }
        });
    }

    private void starReviewActivity(Context context, Advertising advertising,
                                    FavoriteHolder favoriteHolder) {
        ((MainActivity) context).closeMenu();
        Intent intent = new Intent(context, ViewDetailMaterial.class);
        intent.putExtra("item", advertising);
        Pair<View, String> p = new Pair<View, String>(favoriteHolder.content_image_advertising,
                context.getString(R.string.transition_name_advertising));
        /*Pair<LinearLayout, String> p = Pair.create(favoriteHolder.content_image_advertising,
                context.getString(R.string.transition_name_advertising));*/
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                (BaseActivity) context, p);

        ActivityCompat.startActivity((BaseActivity) context, intent, options.toBundle());
    }
}
