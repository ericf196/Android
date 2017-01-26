package com.optimussoftware.boohos.gridview.holders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import com.mikepenz.iconics.view.IconicsButton;
import com.mikepenz.iconics.view.IconicsImageView;
import com.optimussoftware.db.Interest;
import com.optimussoftware.boohos.R;
import com.optimussoftware.boohos.UserInterestActivity;
import com.optimussoftware.boohos.data.Constants;
import com.optimussoftware.boohos.widget.OptimusTextView;
import com.optimussoftware.image.Factory;

public class CategoryInterestViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener{

    private String TAG = CategoryInterestViewHolders.class.getSimpleName();
    private OptimusTextView nameCategory;
    private IconicsImageView iconCategory;
    public ImageView photoCategory;
    private Context context;
    private IconicsButton select;
    private View shadow_select;
    private UserInterestActivity activity;
    private Interest interest;

    public CategoryInterestViewHolders(View itemView, UserInterestActivity activity) {
        super(itemView);
        this.activity = activity;
        context = itemView.getContext();
        itemView.setOnClickListener(this);
        nameCategory = (OptimusTextView) itemView.findViewById(R.id.name_category);
        iconCategory = (IconicsImageView) itemView.findViewById(R.id.icon_category);
        photoCategory = (ImageView) itemView.findViewById(R.id.photo_category);
        select = (IconicsButton) itemView.findViewById(R.id.select_icon);
        shadow_select = (View) itemView.findViewById(R.id.shadow_view);
    }

    public void setView(Interest interest){
        this.interest = interest;
        nameCategory.setText(interest.getName());
        Log.i(TAG, "image interest -->" + Constants.URL_WEB + interest.getImage());
        Factory.setImageWithBlur(photoCategory, context, Constants.URL_WEB + interest.getImage());
    }

    public void getIconVisible(boolean valor) {
        if(valor) {
            select.setVisibility(View.VISIBLE);
            shadow_select.setVisibility(View.VISIBLE);
            nameCategory.setTextColor(context.getResources().getColor(R.color.colorPrimary));
        } else {
            select.setVisibility(View.GONE);
            shadow_select.setVisibility(View.GONE);
            nameCategory.setTextColor(context.getResources().getColor(R.color.colorEnabled));

        }
    }

    @Override
    public void onClick(View view) {
        // activity.changeView(interest);
    }
}