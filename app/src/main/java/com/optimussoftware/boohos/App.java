package com.optimussoftware.boohos;

import android.app.Application;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.estimote.sdk.EstimoteSDK;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.karumi.dexter.Dexter;
import com.optimussoftware.boohos.util.PreferenceManager;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;

import io.fabric.sdk.android.Fabric;


@SuppressWarnings("unused")
public class App extends Application {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    /*private static final String TWITTER_KEY = "NOR940cWbUBLule9WBxzprW6T";
    private static final String TWITTER_SECRET = "xOa4sxcEAUtHh7LJ4j0gAVTJz2i9a1Y3hzMwKNv0SqLqWOWZIw";*/
    private static final String TWITTER_KEY = "5W5TBycyu7bYjDOcJiY51j5fI";
    private static final String TWITTER_SECRET = "pxJLHooqEgFN3Ct7bMvozPz7K80j4VgBN80OlyHMXDXAvZw7xk";

    private static String APPLICATION_ID;
    private static String APPLICATION_NAME;
    public String TAG = null;
    //    private static final String REGULAR_FONT_PATH = "fonts/NotoSans/NotoSans-Regular.ttf";
    public static Typeface optimusFontDefault;
    private static PreferenceManager preferenceManager;

    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(getBaseContext());
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig), new Crashlytics());
        App.APPLICATION_ID = getPackageName();
        App.APPLICATION_NAME = getPackageManager().getApplicationLabel(getApplicationInfo()).toString();
        String app_id_estimote = getResources().getString(R.string.estimote_AppID);
        String app_token_estimote = getResources().getString(R.string.estimote_AppToken);
        EstimoteSDK.initialize(this, app_id_estimote, app_token_estimote);
        EstimoteSDK.enableDebugLogging(true);
        Dexter.initialize(this);
        preferenceManager = new PreferenceManager(this);
        initTypeface();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(getBaseContext());
    }

    /**
     * Checks for network availability
     *
     * @return true, if network available
     */
    public boolean inNetwork() {
        boolean isConnected = false;
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo nInfo = manager.getActiveNetworkInfo();
        if (nInfo != null && nInfo.isConnectedOrConnecting()) {
            isConnected = true;
        }
        return isConnected;
    }

    /**
     * Checks for installed application
     *
     * @param appPackage
     * @return true, if application installed on device
     */
    public boolean appInstalled(String appPackage) {
        boolean mInstalled = false;
        try {
            PackageManager mPackage = getPackageManager();
            mPackage.getPackageInfo(appPackage, PackageManager.GET_ACTIVITIES);
            mInstalled = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mInstalled;
    }

    private void initTypeface() {
        String REGULAR_FONT_PATH = getBaseContext().getString(R.string.font_path_regular);
        optimusFontDefault = Typeface.createFromAsset(getAssets(), REGULAR_FONT_PATH);
    }

    public boolean bluetoothSuport(String appPackage) {
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            // Device does not support Bluetooth
        }
        return mBluetoothAdapter == null;
    }

    public static String getTokenBoohos() {
        Log.d("tokenBoohos", "GET TOKEN_BOOHOS " + preferenceManager.getString("tokenBoohos", ""));
        return preferenceManager.getString("tokenBoohos", "");
    }

    public static void setTokenBoohos(String tokenBoohos) {
        Log.d("tokenBoohos", "PUT TOKEN_BOOHOS " + tokenBoohos);
        preferenceManager.putString("tokenBoohos", tokenBoohos);
    }
}