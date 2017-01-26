package com.optimussoftware.boohos.listview.holders;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.optimussoftware.boohos.R;
import com.optimussoftware.boohos.data.Constants;
import com.optimussoftware.boohos.listview.adapters.AdvertisingAdapter;
import com.optimussoftware.boohos.listview.model.AdvertisingByInterest;
import com.optimussoftware.boohos.widget.OptimusTextView;
import com.optimussoftware.image.Factory;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by leonardojpr on 8/09/16.
 */
public class ByInterestsViewHolder extends RecyclerView.ViewHolder {

    private Context context;
    private CircleImageView img_customer;
    private OptimusTextView category, interest;
    private RecyclerView recyclerView;
    public LinearLayout linea_background;

    public ByInterestsViewHolder(View itemView) {
        super(itemView);
        context = itemView.getContext();
        img_customer = (CircleImageView) itemView.findViewById(R.id.img_customer);
        category = (OptimusTextView) itemView.findViewById(R.id.label_category);
        recyclerView = (RecyclerView) itemView.findViewById(R.id.advertising_list);
        interest = (OptimusTextView) itemView.findViewById(R.id.label_interest);
        linea_background = (LinearLayout) itemView.findViewById(R.id.linea_background);
    }

    public void setView(AdvertisingByInterest advertisingByInterest) {
        category.setText(advertisingByInterest.getInterest().getName());
        Factory.setImageWithBlur(img_customer, context, Constants.URL_WEB + advertisingByInterest.getInterest().getImage());
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        if (!advertisingByInterest.getAdvertisingList().isEmpty())
        recyclerView.setAdapter(new AdvertisingAdapter(context, advertisingByInterest.getAdvertisingList()));
        interest.setText(advertisingByInterest.getInterest().getDescription());
    }

}
