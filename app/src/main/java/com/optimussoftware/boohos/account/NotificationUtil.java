package com.optimussoftware.boohos.account;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.optimussoftware.db.Advertising;
import com.optimussoftware.db.Campaign;
import com.optimussoftware.db.NotificationAdvertising;
import com.optimussoftware.boohos.main.MainActivity;
import com.optimussoftware.boohos.R;
import com.optimussoftware.boohos.data.DBController;
import com.optimussoftware.boohos.service.receiver.AlarmEfectiveDays;
import com.optimussoftware.boohos.util.PreferenceManager;
import com.optimussoftware.boohos.widget.OptimusDate;

import org.joda.time.DateTime;
import org.joda.time.Period;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by guerra on 07/08/16.
 * Control notification
 */
public class NotificationUtil {

    private String TAG = "NotificationUtil";
    public PreferenceManager manager;
    private Context context;
    private boolean checkActiveNotifications = false;
    private boolean activeNotifications = true;
    private int smallIcon;

    public static int VIBRATE_OFF = 0, VIBRATE_SHORT = 1, VIBRATE_MEDIUM = 2, VIBRATE_LARGE = 3;
    public static int LIGHT_OFF = 0, LIGHT_BLUE = 1, LIGHT_CYAN = 2, LIGHT_GREEN = 3, LIGHT_MAGENTA = 4,
            LIGHT_RED = 5, LIGHT_WHITE = 6, LIGHT_YELLOW = 7;

    private String CHECK_ACTIVE_NOTIFICATION = "checkActiveNotification";
    private String ACTIVE_NOTIFICATION = "activeNotification";
    private String SOUND_NOTIFICATION = "uriSound";
    private String VIBRATE_NOTIFICATION = "vibrate";
    private String LIGHT_NOTIFICATION = "light";

    private Uri uriSound;
    private int vibrateLong;
    private int lightColor;

    public NotificationUtil(Context context) {
        this.context = context;
        manager = new PreferenceManager(context);
        smallIcon = R.drawable.ic_notification;
    }

    public void createNotification(String title, String content) {
        if (getActiveNotifications()) {

            Intent intent = new Intent(context, MainActivity.class);
            PendingIntent pIntent = PendingIntent.getActivity(context, (int) System.currentTimeMillis(), intent, 0);
            Notification n = notificationBuilder(pIntent, title, content, true);
            NotificationManager notificationManager =
                    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

            notificationManager.notify(0, n);
        } else {
            Log.i(TAG, "Notifications disables");
            //Toast.makeText(context, "Notificaciones Inactivas", Toast.LENGTH_SHORT).show();
        }
    }

    public Notification notificationBuilder(PendingIntent pIntent, String title, String content, boolean autoCancel) {
        //Notification with preferences user
        return new Notification.Builder(context)
                .setContentTitle(title)
                .setContentText(content)
                .setSmallIcon(smallIcon)
                .setContentIntent(pIntent)
                .setAutoCancel(autoCancel)
                .setSound(getUriSound())
                .setLights(lightColor(), 1000, 1000)
                .setVibrate(vibrate()).build();
    }


    public boolean getCheckActiveNotifications() {
        checkActiveNotifications = manager.getBoolean(CHECK_ACTIVE_NOTIFICATION, false);
        return checkActiveNotifications;
    }

    public void setCheckActiveNotifications(boolean checkActiveNotifications) {
        manager.setBoolean(CHECK_ACTIVE_NOTIFICATION, checkActiveNotifications);
        this.checkActiveNotifications = checkActiveNotifications;
    }

    /**
     * Importante verificar si estan activas las notificaciones antes de lanzar el push
     **/
    public boolean getActiveNotifications() {
        activeNotifications = manager.getBoolean(ACTIVE_NOTIFICATION, true);
        return activeNotifications;
    }

    public void setActiveNotifications(boolean activeNotifications) {
        manager.setBoolean(ACTIVE_NOTIFICATION, activeNotifications);
        this.activeNotifications = activeNotifications;
    }

    public Uri getUriSound() {
        uriSound = Uri.parse(manager.getString(SOUND_NOTIFICATION,
                RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION).toString()));
        return uriSound;
    }

    public void setUriSound(Uri uriSound) {
        manager.putString(SOUND_NOTIFICATION, uriSound.toString());
        this.uriSound = uriSound;
    }

    public String getNameSound() {
        Ringtone ringtone = RingtoneManager.getRingtone(context, getUriSound());
        return ringtone.getTitle(context);
    }

    public int getVibrateLong() {
        vibrateLong = manager.getInt(VIBRATE_NOTIFICATION, VIBRATE_SHORT);
        return vibrateLong;
    }

    public void setVibrateLong(int vibrateLong) {
        manager.putInt(VIBRATE_NOTIFICATION, vibrateLong);
        this.vibrateLong = vibrateLong;
    }

    private long[] vibrate() {
        if (getVibrateLong() == VIBRATE_OFF) {
            return new long[]{0};
        } else if (getVibrateLong() == VIBRATE_MEDIUM) {
            return new long[]{300, 300, 300, 300};
        } else if (getVibrateLong() == VIBRATE_LARGE) {
            return new long[]{1000, 1000, 1000, 1000};
        }
        return new long[]{100, 100, 100, 100};//VIBRATE_SHORT default
    }

    public int getLightColor() {
        lightColor = manager.getInt(LIGHT_NOTIFICATION, LIGHT_BLUE);
        return lightColor;
    }

    public void setLightColor(int lightColor) {
        manager.putInt(LIGHT_NOTIFICATION, lightColor);
        this.lightColor = lightColor;
    }

    private int lightColor() {
        if (getLightColor() == LIGHT_OFF) {
            return Color.TRANSPARENT;
        } else if (getLightColor() == LIGHT_CYAN) {
            return Color.CYAN;
        } else if (getLightColor() == LIGHT_GREEN) {
            return Color.GREEN;
        } else if (getLightColor() == LIGHT_MAGENTA) {
            return Color.MAGENTA;
        } else if (getLightColor() == LIGHT_RED) {
            return Color.RED;
        } else if (getLightColor() == LIGHT_WHITE) {
            return Color.WHITE;
        } else if (getLightColor() == LIGHT_YELLOW) {
            return Color.YELLOW;
        }
        return Color.BLUE;//default
    }

    public void showDialogCheckActiveNotification(final Advertising advertising) {

        if (!getCheckActiveNotifications() && getActiveNotifications()) {
            MaterialDialog dialog = new MaterialDialog.Builder(context)
                    .theme(Theme.LIGHT)
                    //.title(R.string.wan_recive_notifications)
                    .content(R.string.wan_recive_notifications)
                    .checkBoxPrompt(context.getString(R.string.do_not_ask_again),
                            false,
                            new CompoundButton.OnCheckedChangeListener() {
                                @Override
                                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                    setCheckActiveNotifications(b);                    //.title(R.string.about)
                                }
                            })
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            setActiveNotifications(true);
                            startEfectiveDay(advertising);
                        }
                    })
                    .onNegative(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            setActiveNotifications(false);
                        }
                    })
                    .negativeText(android.R.string.cancel)
                    .positiveText(android.R.string.ok)
                    .build();
            dialog.show();
        }
    }

    public void startEfectiveDay(Advertising advertising) {

        Campaign campaign = DBController.getControler().getCampaign(context, advertising.getCampaign_id());
        Date date = new Date();
        int action = date.compareTo(campaign.getStart_date());
        if (action == 1) {
            alarmEfectiveDay(campaign, new Date(), advertising);
            Log.i(TAG, "in progress " + campaign.getEfectiveDays());
        } else {
            Log.i(TAG, "before ");
            alarmABeforeStartingTheCampaign(campaign, advertising);
        }
        ((MainActivity) context).notify.setVisibility(View.VISIBLE);
    }

    public void alarmABeforeStartingTheCampaign(Campaign campaign, Advertising advertising) {

        Intent alarmIntent = new Intent(context, AlarmEfectiveDays.class);
        final int _id = (int) System.currentTimeMillis();
        NotificationAdvertising notificationAdvertising = new NotificationAdvertising();
        notificationAdvertising.set_id(String.valueOf(_id));
        notificationAdvertising.setAdvertising_id(advertising.get_id());
        notificationAdvertising.setCampaign_id(advertising.getCampaign_id());
        notificationAdvertising.setDate(campaign.getStart_date());
        notificationAdvertising.setDelete(true);
        notificationAdvertising.setViewed(false);
        DBController.getControler().createAdvertisingNotification(context, notificationAdvertising);
        alarmIntent.putExtra("item", advertising);
        alarmIntent.putExtra("notification", notificationAdvertising);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, _id, alarmIntent, PendingIntent.FLAG_ONE_SHOT);

        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        //int interval = 86400;

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());

        manager.setExact(AlarmManager.RTC_WAKEUP, campaign.getStart_date().getTime() - 60000, pendingIntent);

        alarmEfectiveDay(campaign, campaign.getStart_date(), advertising);

    }

    public void alarmEfectiveDay(Campaign campaign, Date date, Advertising advertising) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date.getTime());

        String[] days = campaign.getEfectiveDays().split("~");

        Period period = OptimusDate.diferenceBetewnDateInDay(new DateTime(), new DateTime(campaign.getFinish_date()));

        Log.i(TAG, "Days " + campaign.getEfectiveDays());
        Log.i(TAG, "date now " + new Date().toString() + " finish campaign -->  " + campaign.getFinish_date());
        Log.i(TAG, "diferencia dias " + period.getDays() + " ");

        for (int i = 0; i < period.getDays(); i++) {
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            for (int j = 0; j < days.length; j++) {
                if ((int) calendar.get(Calendar.DAY_OF_WEEK) == getDay(days[j])) {
                    final int _id = (int) System.currentTimeMillis();
                    Intent alarmIntent = new Intent(context, AlarmEfectiveDays.class);

                    NotificationAdvertising notificationAdvertising = new NotificationAdvertising();
                    notificationAdvertising.set_id(String.valueOf(_id));
                    notificationAdvertising.setAdvertising_id(advertising.get_id());
                    notificationAdvertising.setCampaign_id(advertising.getCampaign_id());
                    notificationAdvertising.setDate(calendar.getTime());
                    notificationAdvertising.setDelete(true);
                    notificationAdvertising.setViewed(false);
                    DBController.getControler().createAdvertisingNotification(context, notificationAdvertising);
                    alarmIntent.putExtra("item", advertising);
                    alarmIntent.putExtra("notification", notificationAdvertising);

                    PendingIntent pendingIntent = PendingIntent.getBroadcast(context, _id, alarmIntent, PendingIntent.FLAG_ONE_SHOT);

                    AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

                    manager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);


                    Log.i(TAG, calendar.getTime() + " days Create Alarm, ID --> " + _id);
                }
            }
        }
    }

    public int getDay(String day) {
        int d = 0;
        switch (day) {
            case "sun":
                d = 1;
                break;
            case "mon":
                d = 2;
                break;
            case "tue":
                d = 3;
                break;
            case "wed":
                d = 4;
                break;
            case "thu":
                d = 5;
                break;
            case "fri":
                d = 6;
                break;
            case "sat":
                d = 7;
                break;
        }
        return d;
    }

}
