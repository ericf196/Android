package com.optimussoftware.beacons.reciver;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.estimote.sdk.connection.internal.EstimoteService;

/**
 *
 * Created by Created by william.castillo@optimus-software.com on 15/05/16.
 */
public class BluetoothReceiver extends BroadcastReceiver {
    private Intent estimoteServiceIntent;

    // Method called when bluetooth is turned on or off.
    @Override
    public void onReceive(Context context, Intent intent) {
        final String action = intent.getAction();
        if (action.equals(BluetoothAdapter.ACTION_STATE_CHANGED)) {
            final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE,
                    BluetoothAdapter.ERROR);
            switch (state) {
                case BluetoothAdapter.STATE_TURNING_OFF:
                    if (estimoteServiceIntent != null) {
                        context.stopService(estimoteServiceIntent);
                        estimoteServiceIntent = null;
                    }
                    break;
                case BluetoothAdapter.STATE_ON:
                    if (estimoteServiceIntent == null) {
                        estimoteServiceIntent = new Intent(context,
                                EstimoteService.class);
                        context.startService(estimoteServiceIntent);
                    }
                    break;
            }
        }
    }
}
