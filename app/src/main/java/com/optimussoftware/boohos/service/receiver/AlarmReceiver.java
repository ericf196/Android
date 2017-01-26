package com.optimussoftware.boohos.service.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.optimussoftware.boohos.service.ClearDataUtil;

/**
 * Created by leonardojpr on 10/29/16.
 * AlarmReceiver
 */

public class AlarmReceiver extends BroadcastReceiver {

    private static final String TAG = AlarmReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        initClearData(context);
        Log.i(TAG,"init service ClearDataUtil");
        // Toast.makeText(context, "Iniciar borrar datos", Toast.LENGTH_LONG).show();
    }

    private void initClearData(Context context) {
        Intent i = new Intent(context, ClearDataUtil.class);
        context.startService(i);
    }


}
