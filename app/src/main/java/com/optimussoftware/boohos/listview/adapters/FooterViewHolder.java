package com.optimussoftware.boohos.listview.adapters;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.optimussoftware.boohos.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by guerra on 19/07/16.
 * View to footer
 */
public class FooterViewHolder extends RecyclerView.ViewHolder{
    //controls
    @BindView(R.id.linear_no_more)
    LinearLayout linear_no_more;
    @BindView(R.id.linear_loading)
    LinearLayout linear_loading;

    public FooterViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        linear_no_more.setVisibility(View.GONE);
        linear_loading.setVisibility(View.GONE);
    }

    public void setStatusFooter(boolean lodaing) {
        if (lodaing) {
            linear_no_more.setVisibility(View.GONE);
            linear_loading.setVisibility(View.VISIBLE);
        } else {
            linear_no_more.setVisibility(View.VISIBLE);
            linear_loading.setVisibility(View.GONE);
        }
    }
}
