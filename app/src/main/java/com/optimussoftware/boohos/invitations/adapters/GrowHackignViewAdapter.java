package com.optimussoftware.boohos.invitations.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mikepenz.community_material_typeface_library.CommunityMaterial;
import com.optimussoftware.boohos.R;
import com.optimussoftware.boohos.invitations.GrowHackingActivity;
import com.optimussoftware.boohos.invitations.GrowHackingModel;
import com.optimussoftware.boohos.invitations.viewholders.GrowHackinViewHolder;
import com.optimussoftware.boohos.listview.adapters.AdvertisingAdapter;

import java.util.ArrayList;

/**
 * Created by leonardojpr on 12/7/16.
 */

public class GrowHackignViewAdapter extends RecyclerView.Adapter<GrowHackinViewHolder> {

    private static final String TAG = GrowHackignViewAdapter.class.getSimpleName();

    private ArrayList<GrowHackingModel> mValue;
    private int from = 0;

    public GrowHackignViewAdapter(ArrayList<GrowHackingModel> mValue, int from) {
        this.mValue = mValue;
        this.from = from;
    }

    public GrowHackignViewAdapter(ArrayList<GrowHackingModel> mValue) {
        this.mValue = mValue;
    }

    @Override
    public GrowHackinViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_grow_hacking, parent, false);

        return new GrowHackinViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(final GrowHackinViewHolder holder, final int position) {
        final boolean[] isSelect  = {false};
        if (position == 0) {
            holder.showMessage(true);
            holder.setData(mValue.get(position), from);
        } else {
            holder.showMessage(false);
            holder.setData(mValue.get(position), from);
        }
    }

    @Override
    public int getItemCount() {
        return mValue.size();
    }

}
