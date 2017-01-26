package com.optimussoftware.boohos.listview.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.optimussoftware.db.Advertising;
import com.optimussoftware.boohos.main.MainActivity;
import com.optimussoftware.boohos.R;
import com.optimussoftware.boohos.data.DBController;
import com.optimussoftware.boohos.listview.holders.OffertViewHolders;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Perez on 7/7/2016.
 */
public class OffertViewAdapter extends RecyclerView.Adapter<OffertViewHolders> {

    static final String TAG = OffertViewAdapter.class.getSimpleName();

    private List<Advertising> mValues;
    private OnListAdapterInteractionListener mListener;
    private Context context;
    private DBController dbController;
    private int lastPosition = -1;

    public OffertViewAdapter(Context context, List<Advertising> mValues, OnListAdapterInteractionListener mListener) {
        this.context = context;
        this.mValues = mValues;
        this.mListener = mListener;
        dbController = DBController.getControler();
        EventBus.getDefault().register(this);
    }

    @Override
    public OffertViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.offert_item, parent, false);
        return new OffertViewHolders(layoutView);
    }

    @Override
    public void onBindViewHolder(final OffertViewHolders holder, final int position) {
        holder.setView(mValues.get(position));
        setAnimation(holder.linearLayout_padre, position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onListAdapterInteraction(mValues.get(position), holder);
            }
        });
        holder.actionButtonView.initIconButton(mValues.get(position), ((MainActivity) context).getPersonalInfo());
        holder.actionButtonView.getActionListener(mValues.get(position), ((MainActivity) context).getPersonalInfo(), false);
        holder.actionButtonView.getActionShare(((MainActivity) context).share, mValues.get(position), ((MainActivity) context).shareButtonUtil);
        holder.linearLayout.setVisibility(View.VISIBLE);

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public interface OnListAdapterInteractionListener {
        void onListAdapterInteraction(Advertising item, OffertViewHolders holder);
    }

    public void updateItem(int position) {
        notifyItemChanged(position);
    }

    public void updateList(List<Advertising> advertisingsList) {
        mValues.clear();
        mValues.addAll(advertisingsList);
        notifyDataSetChanged();
    }

    public void removeItem(Advertising advertising) {
        for (Advertising a : mValues) {
            if (a.get_id().equals(advertising.get_id())) {
                Log.i(TAG, "remover ---> " + mValues.indexOf(a));
                removeAt(mValues.indexOf(a));
            }
        }


    }

    public void removeAt(int position) {
        mValues.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(0, mValues.size());
    }

    public void removeAll() {
        notifyItemRangeRemoved(0, mValues.size());
    }

    public void addItem(List<Advertising> nuevos, boolean alfinal) {
        if (alfinal) {
            int ultAct = mValues.size();
            mValues.addAll(nuevos);
            //notifyItemRangeInserted(ultAct, nuevos.size()+ultAct);
            notifyDataSetChanged();
        } else {
            List<Advertising> nd = new ArrayList<>();
            nd.addAll(nuevos);
            nd.addAll(mValues);
            mValues = nd;
            int initPos = 0;
            notifyItemRangeInserted(initPos, nuevos.size());
            //notifyDataSetChanged();
        }
    }

    public void add(List<Advertising> itemAdvertisingNew) {
        mValues.addAll(mValues.size(), itemAdvertisingNew);
        notifyDataSetChanged();
    }

    public void refresh() {
        notifyAll();
    }

    @Subscribe
    public void onEvent(String advertising_id) {
        Log.i(TAG, "EventBus -->" + advertising_id);
        for (int i = 0; i < mValues.size(); i++) {
            if (mValues.get(i).get_id().equals(advertising_id)) {
                notifyItemChanged(i);
            }
        }
    }

    public List<Advertising> getmValues() {
        return mValues;
    }

    private void setAnimation(View viewToAnimate, int position) {
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.slide_up_in);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    @Override
    public void onViewDetachedFromWindow(OffertViewHolders holder) {
        super.onViewDetachedFromWindow(holder);
        holder.linearLayout_padre.clearAnimation();
    }

}
