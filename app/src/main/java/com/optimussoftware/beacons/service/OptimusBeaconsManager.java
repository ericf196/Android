package com.optimussoftware.beacons.service;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Nearable;
import com.estimote.sdk.Region;
import com.estimote.sdk.eddystone.Eddystone;
import com.optimussoftware.api.LastOfferts;

import com.optimussoftware.api.response.newAdvertising.AdverstingsList;
import com.optimussoftware.beacons.Configuration;
import com.optimussoftware.beacons.utils.BeaconRegister;
import com.optimussoftware.boohos.R;
import com.optimussoftware.boohos.data.Constants;
import com.optimussoftware.boohos.data.DBController;
import com.optimussoftware.boohos.data.PersonalInfo;
import com.optimussoftware.boohos.util.PreferenceManager;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by william.castillo@optimus-software.com on 27/07/16.
 */
public class OptimusBeaconsManager {

    private static String TAG = OptimusService.class.getSimpleName();
    public static String ACTION = OptimusService.class.getSimpleName();
    private static final int NOTIFICATION_ID = 123;
    private BeaconManager beaconManager;
    private NotificationManager notificationManager;
    public static final String EXTRAS_BEACON = "extrasBeacon";
    private static final UUID ESTIMOTE_PROXIMITY_UUID = UUID.fromString("B9407F30-F5F8-466E-AFF9-25556B57FE6D");
    private static final Region ALL_ESTIMOTE_BEACONS = new Region("regionId", ESTIMOTE_PROXIMITY_UUID, null, null);
    private String user_id;
    private Context context;
    private ArrayList<BeaconRegister> beaconsDetected = null;
    private Configuration configuration = null;
    private Service service = null;

    public void Create(NotificationManager notificationMngr, Service service) {
        try {
            context = service.getApplicationContext();
            this.service = service;
            configuration = new Configuration(context);
            beaconsDetected = new ArrayList<BeaconRegister>();
            notificationManager = notificationMngr;
            PreferenceManager preferenceManager = new PreferenceManager(context);
            PersonalInfo info = new PersonalInfo(preferenceManager);
            user_id = info.getUuid();
            Log.d("USER ID", user_id);
            beaconManager = new BeaconManager(context);
            beaconManager.setBackgroundScanPeriod(TimeUnit.SECONDS.toMillis(3), 0);
            beaconManager.setMonitoringListener(monitoringListener);
            beaconManager.setEddystoneListener(eddystoneListener);
            beaconManager.setNearableListener(nearableListener);
            beaconManager.connect(serviceReadyCallback);
        } catch (Exception e) {
            Log.e("service fails", e.toString());
        }
    }

    private BeaconManager.MonitoringListener monitoringListener = new BeaconManager.MonitoringListener() {
        @Override
        public void onEnteredRegion(Region region, List<Beacon> beacons) {
            try {
                for (Beacon b : beacons) {
                    if (control(b.getProximityUUID().toString(), b.getMacAddress().toStandardString())) {
                        Log.d("-beacon", b.toString());
                        getLastedDealsFromBeacon(b);
                        test();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onExitedRegion(Region region) {

        }
    };

    private BeaconManager.EddystoneListener eddystoneListener = new BeaconManager.EddystoneListener() {
        @Override
        public void onEddystonesFound(List<Eddystone> list) {
            if (list.size() > 0) {
                for (Eddystone eddystone : list) {
                    Log.d(TAG, eddystone.toString());
                    if (eddystone.isUid()) {
                        //TODO
                        //postNotificationIntent("Estimote testing", "I have lost my estimote !!!", intent);
                    }
                }
            }
        }
    };

    private BeaconManager.NearableListener nearableListener = new BeaconManager.NearableListener() {
        @Override
        public void onNearablesDiscovered(List<Nearable> nearables) {
            try {
                for (Nearable nearable : nearables) {
                    getAdvertisingFromNearable(nearable);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    private BeaconManager.ServiceReadyCallback serviceReadyCallback = new BeaconManager.ServiceReadyCallback() {
        @Override
        public void onServiceReady() {
            try {
                beaconManager.startEddystoneScanning();
                beaconManager.startNearableDiscovery();
                beaconManager.startTelemetryDiscovery();
                beaconManager.startLocationDiscovery();
                beaconManager.startMonitoring(ALL_ESTIMOTE_BEACONS);
            } catch (Exception e) {
                Log.e(TAG, e.toString());
            }
        }
    };

    // Pops a notification in the task bar
    private void postNotificationIntent(String title, String msg, Intent intent) {
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivities(
                context, 0, new Intent[]{intent},
                PendingIntent.FLAG_UPDATE_CURRENT);
        Notification notification = new Notification.Builder(context)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle(title)
                .setContentText(msg)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setOngoing(true)
                .build();
        notification.defaults |= Notification.DEFAULT_SOUND;
        notification.defaults |= Notification.DEFAULT_LIGHTS;
        if (service != null)
            service.startForeground(NOTIFICATION_ID, notification);
        else
            notificationManager.notify(NOTIFICATION_ID, notification);
    }

    // Stop beacons monitoring, and closes the service
    public void stop() {
        try {
            beaconManager.stopMonitoring(ALL_ESTIMOTE_BEACONS);
            beaconManager.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean control(String uuid, String mac) {
        for (BeaconRegister b : beaconsDetected) {
            Log.d("this", b.toString());
            if (b.exist(uuid))
                return b.check(configuration);
        }
        beaconsDetected.add(new BeaconRegister(uuid, mac, Constants.TYPE_DEVICE_BEACON));
        return true;
    }

    public boolean control(String namespace, String instance, String mac, int type) {
        for (BeaconRegister b : beaconsDetected) {
            if (b.exist(namespace, instance))
                return b.check(configuration);
        }
        beaconsDetected.add(new BeaconRegister(namespace, instance, mac, type));
        return false;
    }

    private void getLastedDealsFromBeacon(Beacon beacon)  {
        LastOfferts offerts = new LastOfferts();
        offerts.getOfferts(beacon.getProximityUUID().toString(), callbackOffert);

        /*offerts.getOfferts(user_id, Constants.TYPE_DEVICE_BEACON,
                beacon.getMacAddress().toString(),
                beacon.getProximityUUID().toString(),"all",
                callbackOffert);*/
    }

    private void getAdvertisingFromNearable(Nearable nearable) {
        //TODO
        /*Advertising advertising = new Advertising(context);
		advertising.setCallback(new RestCallback() {
			@Override
			public void success(Object obj) {
				sendResponse(context, _intent, obj);
			}
		});
		advertising.getOfertByNearable(nearable.identifier);*/
    }

    private void sendResponse(AdverstingsList adverstingsList) {
        //TODO
        //TODO
        //TODO
        //TODO
        int size = adverstingsList.getItems().size();
        if (size > 0) {
            String ids = DBController.getControler().storeOffertList(context, adverstingsList);
            if (isAppIsInBackground(context)) {
                Intent intent = new Intent();
                intent.putExtra("_items", ids);
                //todo leo revisar
                /*String description = size > 1 ?
                        context.getString(R.string.new_offerts) : context.getString(R.string.new_offert);*/
                String description ="des";
                postNotificationIntent(context.getString(R.string.app_name), description, intent);
            } else {
                Intent intentBroadcast = new Intent();
                intentBroadcast.setAction(ACTION);
                intentBroadcast.putExtra("_items", ids);
                service.sendBroadcast(intentBroadcast);
            }
        }
        //TODO
        //TODO
        //TODO
        //TODO
    }

    private boolean isAppIsInBackground(Context context) {
        boolean isInBackground = true;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
            List<ActivityManager.RunningAppProcessInfo> runningProcesses = am.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo processInfo : runningProcesses) {
                if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    for (String activeProcess : processInfo.pkgList) {
                        if (activeProcess.equals(context.getPackageName())) {
                            isInBackground = false;
                        }
                    }
                }
            }
        } else {
            List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
            ComponentName componentInfo = taskInfo.get(0).topActivity;
            if (componentInfo.getPackageName().equals(context.getPackageName())) {
                isInBackground = false;
            }
        }
        return isInBackground;
    }

    private Callback<AdverstingsList> callbackOffert = new Callback<AdverstingsList>() {
        @Override
        public void onResponse(Call<AdverstingsList> call, Response<AdverstingsList> response) {
            sendResponse(response.body());
        }

        @Override
        public void onFailure(Call<AdverstingsList> call, Throwable t) {
            Log.d("Herme: ErrorService", t.toString());
        }
    };

    private void test() {
        try {
            LastOfferts lastOfferts = new LastOfferts();
            lastOfferts.getOfferts("29407f30-f5f8-466e-aff9-25556b57fe6d",  callbackOffert);
            /*lastOfferts.getOfferts("775627cf-031e-4fde-93e9-a84b14b6a83f", Constants.TYPE_DEVICE_BEACON,
                    "D2:02:6B:63:FC:C1", "b9407f30-f5f8-466e-aff9-25556b57fe6d", "all", callbackOffert);*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
