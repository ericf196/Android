package com.optimussoftware.boohos.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by guerra on 01/11/16.
 * Check connection state
 */

public class ConnectionReceiver extends BroadcastReceiver {

    private String TAG = "ConnectionReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Commons.isConnected(context)) {
            Log.d(TAG, "Connection OnLine");
            ConnectionUtil.setOnLine();
        } else {
            ConnectionUtil.setOffLine();
            Log.e(TAG, "Connection OffLine");
        }
    }

}
