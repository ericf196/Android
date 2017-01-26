package com.optimussoftware.boohos.listview.holders;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;

import com.afollestad.materialdialogs.MaterialDialog;
import com.optimussoftware.db.Advertising;
import com.optimussoftware.db.AdvertisingInterest;
import com.optimussoftware.db.Location;
import com.optimussoftware.boohos.R;
import com.optimussoftware.boohos.data.Constants;
import com.optimussoftware.boohos.data.DBController;
import com.optimussoftware.boohos.listview.model.AdvertisingContent;
import com.optimussoftware.boohos.util.Commons;
import com.optimussoftware.boohos.widget.AdvertisingActionButtonView;
import com.optimussoftware.boohos.widget.OptimusDate;
import com.optimussoftware.boohos.widget.OptimusTextView;
import com.optimussoftware.image.Factory;
import com.optimussoftware.image.SizeImage;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Perez on 6/7/2016.
 */
public class OffertViewHolders extends RecyclerView.ViewHolder {

    private static final String TAG = OffertViewHolders.class.getSimpleName();

    public LinearLayout linearLayout_padre;
    private OptimusTextView name, category, description, date_advertising, nameLocation;
    public ImageView photo;
    private CircleImageView logo;
    private RatingBar rating_indicator;
    public LinearLayout linearLayout;
    public AdvertisingActionButtonView actionButtonView;
    public Advertising advertisingItem;
    private Context context;

    public OffertViewHolders(View view) {
        super(view);
        this.context = view.getContext();
        linearLayout_padre = (LinearLayout) view.findViewById(R.id.linearlayout_padre);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(Commons.getWidthDisplay(context), Commons.getWidthDisplay(context));
        name = (OptimusTextView) view.findViewById(R.id.name_advertising);
        description = (OptimusTextView) view.findViewById(R.id.description_advertising);
        category = (OptimusTextView) view.findViewById(R.id.name_category);
        date_advertising = (OptimusTextView) view.findViewById(R.id.date_advertising);
        nameLocation = (OptimusTextView) view.findViewById(R.id.text_name_location);
        photo = (ImageView) view.findViewById(R.id.image_advertising);
        photo.setLayoutParams(layoutParams);
        logo = (CircleImageView) view.findViewById(R.id.logo_location);
        rating_indicator = (RatingBar) view.findViewById(R.id.rating_indicator);
        LayerDrawable stars = (LayerDrawable) rating_indicator.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
        stars.getDrawable(1).setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
        //stars.getDrawable(0).setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_OVER);
        actionButtonView = (AdvertisingActionButtonView) view.findViewById(R.id.advertising_action_button);
        actionButtonView.setPaddingIcon(5);
        linearLayout = (LinearLayout) view.findViewById(R.id.linea_background);

    }

    public void setView(Advertising advertisingItem) {
        Location location = DBController.getControler().getLocation(context, advertisingItem.getLocation_id());
        if (location != null) {
            nameLocation.setText(location.getName());
            Factory.setImageWithBlur(logo, context, Constants.URL_WEB + location.getImage());
        }
        List<AdvertisingInterest> interestList = DBController.getControler().getAdvertisingInterestList(context, advertisingItem.get_id());
        this.advertisingItem = advertisingItem;
        name.setText(advertisingItem.getName());
        String interest = "";
        if (!interestList.isEmpty()) {
            if (interestList.size() > 1)
                interest = DBController.getControler().getInterest(context, interestList.get(0).getInterest_id()).getName() + "...";
            else {
                if (DBController.getControler().getInterest(context, interestList.get(0).getInterest_id()).getName() != null) {
                    interest = DBController.getControler().getInterest(context, interestList.get(0).getInterest_id()).getName();
                }
            }
        }

        category.setText(interest);
        final List<String> finalInterest = new ArrayList<>();
        for (int i = 0; i < interestList.size(); i++) {
            finalInterest.add(DBController.getControler().getInterest(context, interestList.get(i).getInterest_id()).getName());
        }

        category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialDialog.Builder builder = new MaterialDialog.Builder(context)
                        .title(R.string.interest_title)
                        .items(finalInterest)
                        .positiveText(R.string.interest_agree);

                MaterialDialog dialog = builder.build();
                dialog.show();
            }
        });
        description.setText(advertisingItem.getDescription());
        rating_indicator.setRating(advertisingItem.getAcum_votes());
        SizeImage sizeImage = new SizeImage(Commons.getWidthDisplay(context), Commons.getWidthDisplay(context));
        Factory.setImage(photo, context, AdvertisingContent.pathViewDefault(context, advertisingItem), sizeImage);
        if (advertisingItem.getCreated() != null)
            date_advertising.setText(getDate(context, advertisingItem.getCreated()));


        // Log.d(TAG, "url image --> "  + campaignContent.getLocations().get(random).getLogo().getFilename());
    }

    private static String getDate(Context c, Date date) {
        long dateReview = date.getTime();
        long dateNow = new DateTime().getMillis();
        return OptimusDate.timeAgo(c, dateReview, dateNow);
    }

    @Override
    public String toString() {
        return super.toString() + " '" + name.getText() + "'";
    }

}
