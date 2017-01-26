package com.optimussoftware.boohos.listview.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.optimussoftware.boohos.R;
import com.optimussoftware.boohos.data.DBController;
import com.optimussoftware.boohos.listview.holders.ByInterestsViewHolder;
import com.optimussoftware.boohos.listview.model.AdvertisingByInterest;

import java.util.List;

/**
 * Created by leonardojpr on 8/09/16.
 */
public class ByCategoryViewAdapter extends RecyclerView.Adapter<ByInterestsViewHolder> {

    private static final String TAG = ByCategoryViewAdapter.class.getSimpleName();

    Context context;
    DBController dbController;
    List<AdvertisingByInterest> advertisingByInterest;

    public ByCategoryViewAdapter(List<AdvertisingByInterest> advertisingByInterest) {
        this.advertisingByInterest = advertisingByInterest;
//        Log.d(TAG," size : " + advertisingByInterest.get(0).getAdvertisingList().size());
        dbController = DBController.getControler();
    }

    @Override
    public ByInterestsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.by_category_item, parent, false);
        return new ByInterestsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ByInterestsViewHolder holder, int position) {

        holder.setView(advertisingByInterest.get(position));

    }

    public void updateList(List<AdvertisingByInterest> advertisingsList) {
        advertisingByInterest.clear();
        advertisingByInterest.addAll(advertisingsList);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return advertisingByInterest.size();
    }
}
