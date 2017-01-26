package com.optimussoftware.beacons.service;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;


public class OptimusService extends Service {

	private OptimusBeaconsManager optimusBeaconsManager;

	@Override
	public void onCreate(){
	}
	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		try {
			optimusBeaconsManager = new OptimusBeaconsManager();
			optimusBeaconsManager.Create((NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE), this);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return START_NOT_STICKY;
	}

}
