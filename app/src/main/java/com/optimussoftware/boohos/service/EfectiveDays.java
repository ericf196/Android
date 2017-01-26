package com.optimussoftware.boohos.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.RemoteViews;

import com.optimussoftware.boohos.account.NotificationUtil;
import com.optimussoftware.db.Advertising;
import com.optimussoftware.db.NotificationAdvertising;
import com.optimussoftware.boohos.R;
import com.optimussoftware.boohos.data.Constants;
import com.optimussoftware.boohos.data.DBController;
import com.optimussoftware.boohos.notification.NotificationActivity;
import com.optimussoftware.boohos.viewDetail.ViewDetailMaterial;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by leonardojpr on 11/1/16.
 */

public class EfectiveDays extends Service {

    private static final String TAG = EfectiveDays.class.getSimpleName();
    public static final String EFECTIVEDAYS = EfectiveDays.class.getSimpleName();

    private Intent intent;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        this.intent = intent;
        EventBus.getDefault().post("");
        Advertising advertising = (Advertising) intent.getSerializableExtra("item");
        NotificationAdvertising notificationAdvertising = (NotificationAdvertising) intent.getSerializableExtra("notification");
        notificationAdvertising.setDelete(false);
        DBController.getControler().createAdvertisingNotification(getBaseContext(), notificationAdvertising);
        Log.i(TAG, "advertising " + advertising.getName());
        Log.i(TAG, "notification " + notificationAdvertising.get_id());
        createNotify(advertising, notificationAdvertising);
        stopSelf();
        return START_NOT_STICKY;
    }

    public void createNotification(Advertising advertising) {
        // Prepare intent which is triggered if the
        // notification is selected
        Intent intent = new Intent(getBaseContext(), ViewDetailMaterial.class);
        intent.putExtra("item", advertising);
        PendingIntent pIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent, 0);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        // Build notification
        // Actions are just fake
        Notification noti = new Notification.Builder(this)
                .setContentTitle(advertising.getName())
                .setContentText(advertising.getDescription())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setSound(alarmSound)
                .setContentIntent(pIntent)
                .build();
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        // hide the notification after its selected
        noti.flags |= Notification.FLAG_AUTO_CANCEL;
        notificationManager.notify(0, noti);

    }

    public void createNotify(Advertising advertising, NotificationAdvertising notificationAdvertising) {
        // Default stuff; making and showing notification
        final Context context = getApplicationContext();
        Intent intent = new Intent(getBaseContext(), NotificationActivity.class);
        intent.putExtra("from", EFECTIVEDAYS);
        intent.putExtra("item", advertising);
        intent.putExtra("notification", notificationAdvertising);

        PendingIntent pIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent, 0);

        NotificationUtil notificationUtil = new NotificationUtil(context);
        Notification notification = notificationUtil.notificationBuilder(pIntent,
                advertising.getName(), advertising.getDescription(), true);

        final int notifId = 237;
        notification.flags |= Notification.FLAG_AUTO_CANCEL;

        final NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(notifId, notification);

        // Get RemoteView and id's needed
        final RemoteViews contentView = notification.contentView;
        final int iconId = android.R.id.icon;

        // Uncomment for BigPictureStyle, Requires API 16!
        //final RemoteViews bigContentView = notification.bigContentView;
        //final int bigIconId = getResources().getIdentifier("android:id/big_picture", null, null);

        // Use Picasso with RemoteViews to load image into a notification
        Picasso.with(getApplicationContext())
                .load(getLogoLocation(advertising))
                .resize(100, 100)
                .into(contentView, iconId, notifId, notification);

        // Uncomment for BigPictureStyle
        //Picasso.with(getApplicationContext()).load("http://i.stack.imgur.com/CE5lz.png").into(bigContentView, iconId, notifId, notification);
        //Picasso.with(getApplicationContext()).load("http://i.stack.imgur.com/CE5lz.png").into(bigContentView, bigIconId, notifId, notification);
    }

    private String getLogoLocation(Advertising advertising) {
        return Constants.URL_WEB + DBController.getControler().getLocation(getBaseContext(), advertising.getLocation_id()).getImage();
    }

}
