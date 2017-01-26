package com.optimussoftware.boohos.listview.adapters;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.optimussoftware.api.ReviewResource;
import com.optimussoftware.api.core.Factory;
import com.optimussoftware.api.response.review.NewReview.Item;
import com.optimussoftware.db.Review;
import com.optimussoftware.boohos.R;
import com.optimussoftware.boohos.data.DBController;
import com.optimussoftware.boohos.listview.holders.ReviewViewHolder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by guerra on 16/07/16.
 * Items review
 */
public class ReviewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_ITEM = 1;
    private static final int TYPE_FOOTER = 2;

    private List<Item> itemReview;

    private Context context;
    private ReviewResource reviewResource;
    private Callback<Void> cb;

    private boolean loading = true;

    public ReviewAdapter(List<Item> itemReview) {
        this.itemReview = itemReview;
        reviewResource = new ReviewResource();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        if (viewType == TYPE_FOOTER) {
            View view = LayoutInflater.from(context).inflate(R.layout.footer_item, parent, false);
            return new FooterViewHolder(view);
        } else if (viewType == TYPE_ITEM) {
            View view = LayoutInflater.from(context).inflate(R.layout.review_item, parent, false);
            //view.setOnClickListener(this);
            return new ReviewViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final int pos = holder.getAdapterPosition();
        if (holder instanceof FooterViewHolder) {
            FooterViewHolder footerViewHolder = (FooterViewHolder) holder;
            footerViewHolder.setStatusFooter(getLoading());
        } else if (holder instanceof ReviewViewHolder) {
            ReviewViewHolder reviewViewHolder = (ReviewViewHolder) holder;
            reviewViewHolder.icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context, R.string.edit, Toast.LENGTH_SHORT).show();
                    //todo edit
                    // delete(pos);
                }
            });
            reviewViewHolder.setReview(Factory.getReviewFromResponse(itemReview.get(position)));
        }
    }

    @Override
    public int getItemCount() {
        return itemReview.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (isPositionFooter(position)) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }

    private boolean isPositionFooter(int position) {
        return position == itemReview.size();
    }

    public void add(List<Item> itemReviewNew) {
        itemReview.addAll(itemReview.size(), itemReviewNew);
        notifyDataSetChanged();
    }

    public void delete(final int position) {
        //todo editar no borrar
        final Review review = new Review();
//        review.set_id(itemReview.get(position).getUid());
//        review.set_etag(itemReview.get(position).getEtag());
        review.set_id(itemReview.get(position).getId());
        cb = new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.d("lary", "delete review onResponse--> ");
                DBController.getControler().removeReview(context, review);
                itemReview.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(0, itemReview.size());
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("lary", "delete review onFailure--> " + t.getMessage());
            }
        };
        reviewResource.remove(review, cb);
    }

    public void setLoading(boolean loading) {
        this.loading = loading;
    }

    public boolean getLoading() {
        return loading;
    }
}
