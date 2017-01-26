package com.optimussoftware.beacons;

import android.content.Context;

import com.optimussoftware.boohos.data.Constants;
import com.optimussoftware.boohos.util.PreferenceManager;

/**
 *
 * Created by Created by william.castillo@optimus-software.com on 16/05/16.
 */
public class Configuration {
    private PreferenceManager mPreference;
    private int seconds = -1;

    @SuppressWarnings("FieldCanBeLocal")
    private Context context;
    public Configuration(Context context){
        this.context = context;
        mPreference = new PreferenceManager(context);
    }
    public void setSeconds(int seconds){
        this.seconds = seconds;
    }
    public int getSeconds(){
        if(seconds == -1)
            seconds = mPreference.getInt(Constants.BEACONS_TIME_DELAY, 3600);
        return seconds;
    }
}
