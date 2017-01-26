package com.optimussoftware.boohos.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.optimussoftware.db.Advertising;
import com.optimussoftware.db.Campaign;
import com.optimussoftware.boohos.data.DBController;

import java.util.Date;
import java.util.List;

/**
 * Created by leonardojpr on 10/29/16.
 */

public class ClearDataUtil extends Service {

    static final String TAG = ClearDataUtil.class.getSimpleName();
    public static final String UPDATEDATA = ClearDataUtil.class.getSimpleName() + "updatedata";
    private LocalBroadcastManager broadcaster;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        broadcaster = LocalBroadcastManager.getInstance(getBaseContext());
        campaignEnded();
        return START_STICKY;
    }

    public List<Advertising> getAdvertisingListByCampaignList(String campaign_id) {
        return DBController.getControler().getAdvertisingListByCampaign(getBaseContext(), campaign_id);
    }

    public List<Campaign> getCampaignList() {
        return DBController.getControler().getCampaignList(getBaseContext());
    }

    public void campaignEnded() {

        Date date = new Date();
        Log.i(TAG, "Date --> " + date.toString());
        Log.i(TAG, "campaign --> " + getCampaignList().size());
        for (int i = 0; i < getCampaignList().size(); i++) {
            List<Advertising> advertisingList = getAdvertisingListByCampaignList(getCampaignList().get(i).get_id());
            Log.i(TAG, "Date --> " + getCampaignList().get(i).getFinish_date());
            if (getCampaignList().get(i).getFinish_date().compareTo(date) > 0) {
                for (int j = 0; j < advertisingList.size(); j++) {
                    DBController.getControler().removeAdvertising(getBaseContext(), advertisingList.get(j));
                    Log.i(TAG, "delete advertising --> " + advertisingList.get(j).getName());
                }

                Log.i(TAG, "detele campaign --> " + getCampaignList().get(i).getName());
                DBController.getControler().removeCampaign(getBaseContext(), getCampaignList().get(i));
            }

        }
        Intent intent = new Intent(UPDATEDATA);
        broadcaster.sendBroadcast(intent);
        stopSelf();
    }
}
