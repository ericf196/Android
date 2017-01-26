package com.optimussoftware.boohos.service.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.optimussoftware.boohos.service.EfectiveDays;
import com.optimussoftware.db.Advertising;
import com.optimussoftware.db.NotificationAdvertising;

/**
 * Created by leonardojpr on 11/2/16.
 */

public class AlarmEfectiveDays extends BroadcastReceiver {

    private static final String TAG = AlarmEfectiveDays.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "init service EfectiveDays");
        iniEfectiveDays(context, intent);
    }

    private void iniEfectiveDays(Context context, Intent intent) {
        Intent i = new Intent(context, EfectiveDays.class);

        if (intent.getSerializableExtra("item") != null && intent.getSerializableExtra("notification") != null) {
        Advertising advertising = (Advertising) intent.getSerializableExtra("item");
        NotificationAdvertising notificationAdvertising = (NotificationAdvertising) intent.getSerializableExtra("notification");

            Log.i(TAG, "advertising " + advertising.getName());
            Log.i(TAG, "notification " + notificationAdvertising.get_id());
            i.putExtra("item", advertising);
            i.putExtra("notification", notificationAdvertising);
            context.startService(i);
        }
    }
}
