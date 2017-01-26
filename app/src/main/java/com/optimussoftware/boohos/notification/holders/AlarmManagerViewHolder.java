package com.optimussoftware.boohos.notification.holders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.mikepenz.iconics.view.IconicsImageView;
import com.optimussoftware.db.Advertising;
import com.optimussoftware.db.NotificationAdvertising;
import com.optimussoftware.boohos.R;
import com.optimussoftware.boohos.listview.model.AdvertisingContent;
import com.optimussoftware.boohos.util.Commons;
import com.optimussoftware.boohos.widget.OptimusDate;
import com.optimussoftware.boohos.widget.OptimusTextView;
import com.optimussoftware.image.Factory;
import com.optimussoftware.image.SizeImage;

import org.joda.time.DateTime;

import java.util.Date;

/**
 * Created by leonardojpr on 11/3/16.
 */

public class AlarmManagerViewHolder extends RecyclerView.ViewHolder {

    private Context context;
    public LinearLayout content;
    public ImageView image_adv;
    private OptimusTextView name_adv, description_adv, date_notify;
    public IconicsImageView remove;
    private View view;

    public AlarmManagerViewHolder(View itemView) {
        super(itemView);
        context = itemView.getContext();
        content = (LinearLayout) itemView.findViewById(R.id.content);
        image_adv = (ImageView) itemView.findViewById(R.id.image_advertising);
        name_adv = (OptimusTextView) itemView.findViewById(R.id.name_advertising);
        description_adv = (OptimusTextView) itemView.findViewById(R.id.description_advertising);
        date_notify = (OptimusTextView) itemView.findViewById(R.id.date_notificacion);
        remove = (IconicsImageView) itemView.findViewById(R.id.remove_notification);
        view = (View) itemView.findViewById(R.id.view_notification);
    }

    public void setViewAlarmManager(Advertising advertising) {
        SizeImage sizeImage = new SizeImage(Commons.getWidthDisplay(context)/4, Commons.getWidthDisplay(context)/4);
        Factory.setImage(image_adv, context, AdvertisingContent.pathViewDefault(context, advertising), sizeImage);
        name_adv.setText(advertising.getName());
        description_adv.setText(advertising.getDescription());
        date_notify.setVisibility(View.VISIBLE);
    }

    public void setViewAreaNotification(Advertising advertising, NotificationAdvertising notificationAdvertising) {
        SizeImage sizeImage = new SizeImage(Commons.pxToDp(context, 100), Commons.pxToDp(context, 100));
        Factory.setImage(image_adv, context, AdvertisingContent.pathViewDefault(context, advertising), sizeImage);
        name_adv.setText(advertising.getName());
        description_adv.setText(advertising.getDescription());
        remove.setVisibility(View.GONE);
        view.setVisibility(View.GONE);
        date_notify.setVisibility(View.VISIBLE);
        date_notify.setText(getDate(context, notificationAdvertising.getDate()));
        if (!notificationAdvertising.getViewed()) {
            content.setBackgroundColor(context.getResources().getColor(R.color.no_viewed));
        } else {
            content.setBackgroundColor(context.getResources().getColor(R.color.white));
        }
    }

    private static String getDate(Context c, Date date) {
        long dateReview = date.getTime();
        long dateNow = new DateTime().getMillis();
        return OptimusDate.timeAgo(c, dateReview, dateNow);
    }
}
