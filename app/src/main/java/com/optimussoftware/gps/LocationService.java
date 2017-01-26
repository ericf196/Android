package com.optimussoftware.gps;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import com.loopj.android.http.RequestParams;
import com.optimussoftware.boohos.util.PreferenceManager;


public class LocationService extends Service implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private static final String TAG = "LocationService";
    private boolean currentlyProcessingLocation = false;
    private GoogleApiClient googleApiClient;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (!currentlyProcessingLocation) {
            currentlyProcessingLocation = true;
            startTracking();
        }
        return START_NOT_STICKY;
    }

    private void startTracking() {
        Log.d(TAG, "startTracking");
        //if (GooglePlayServicesUtil.isGooglePlayServicesAvailable(this) == ConnectionResult.SUCCESS) {
        if (GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this) == ConnectionResult.SUCCESS) {
            googleApiClient = new GoogleApiClient.Builder(this)
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .build();

            if (!googleApiClient.isConnected() || !googleApiClient.isConnecting()) {
                googleApiClient.connect();
            }
        } else {
            Log.e(TAG, "unable to connect to google play services.");
        }
    }

    protected void sendLocation(Location location) {
        Log.d("lary", "sendLocation");
        PreferenceManager pManager = new PreferenceManager(this.getApplicationContext());
        boolean firstTimeGettingPosition = pManager.getBoolean("firstTimeGettingPosition", true);
        float totalDistanceInMeters = pManager.getFloat("totalDistanceInMeters", 0f);

        if (firstTimeGettingPosition) {
            pManager.setBoolean("firstTimeGettingPosition", false);
        } else {
            Location previousLocation = new Location("");
            previousLocation.setLatitude(pManager.getFloat("previousLatitude", 0f));
            previousLocation.setLongitude(pManager.getFloat("previousLongitude", 0f));

            float distance = location.distanceTo(previousLocation);
            totalDistanceInMeters += distance;
            pManager.putFloat("totalDistanceInMeters", totalDistanceInMeters);
        }

        pManager.putFloat("previousLatitude", (float) location.getLatitude());
        pManager.putFloat("previousLongitude", (float) location.getLongitude());

        final RequestParams requestParams = new RequestParams();
        requestParams.put("latitude", Double.toString(location.getLatitude()));
        requestParams.put("longitude", Double.toString(location.getLongitude()));

        Double speedInMilesPerHour = location.getSpeed() * 2.2369;
        requestParams.put("speed", Integer.toString(speedInMilesPerHour.intValue()));
        requestParams.put("locationmethod", location.getProvider());

        if (totalDistanceInMeters > 0) {
            requestParams.put("distance", String.format("%.1f", totalDistanceInMeters / 1609)); // in miles,
        } else {
            requestParams.put("distance", "0.0"); // in miles
        }

        requestParams.put("username", pManager.getString("userName", ""));
        requestParams.put("phonenumber", pManager.getString("appID", "")); // uuid
        requestParams.put("sessionid", pManager.getString("sessionID", "")); // uuid

        Double accuracyInFeet = location.getAccuracy() * 3.28;
        requestParams.put("accuracy", Integer.toString(accuracyInFeet.intValue()));

        Double altitudeInFeet = location.getAltitude() * 3.28;
        requestParams.put("extrainfo", Integer.toString(altitudeInFeet.intValue()));

        requestParams.put("eventtype", "android");

        Float direction = location.getBearing();
        requestParams.put("direction", Integer.toString(direction.intValue()));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {
            Log.e(TAG, "position: " + location.getLatitude() + ", " + location.getLongitude() + " accuracy: " + location.getAccuracy());
            Log.e("lary", "position: " + location.getLatitude() + ", " + location.getLongitude() + " accuracy: " + location.getAccuracy());
            // we have our desired accuracy of 500 meters so lets quit this service,
            // onDestroy will be called and stop our location uodates
            if (location.getAccuracy() < 500.0f) {
                stopLocationUpdates();
                sendLocation(location);
            }
        }
    }

    private void stopLocationUpdates() {
        if (googleApiClient != null && googleApiClient.isConnected()) {
            googleApiClient.disconnect();
        }
    }

    /**
     * Called by Location Services when the request to connect the
     * client finishes successfully. At this point, you can
     * request the current location or start periodic updates
     */

    @Override
    public void onConnected(Bundle bundle) {
        Log.d(TAG, "onConnected");

        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setInterval(1000); // milliseconds
        locationRequest.setFastestInterval(1000); // the fastest rate in milliseconds at which your app can handle location updates
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(
                googleApiClient, locationRequest, this);
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.e(TAG, "onConnectionFailed");
        stopLocationUpdates();
        stopSelf();
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.e(TAG, "GoogleApiClient connection has been suspend");
    }
}
