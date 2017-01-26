package com.optimussoftware.boohos.account;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.mikepenz.community_material_typeface_library.CommunityMaterial;
import com.optimussoftware.boohos.share.Share;
import com.optimussoftware.boohos.share.ShareNative;
import com.optimussoftware.boohos.widget.OptimusTextView;
import com.optimussoftware.gps.GpsTrackerAlarmReceiver;
import com.optimussoftware.boohos.R;
import com.optimussoftware.boohos.util.PreferenceManager;
import com.optimussoftware.boohos.widget.BaseActivity;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by guerra on 03/08/16.
 */
public class PreferencesActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    public Toolbar toolbar;
    @BindView(R.id.switch_bluetooth)
    public SwitchCompat switch_bluetooth;
    @BindView(R.id.spinner_recurrence)
    public MaterialBetterSpinner spinner_recurrence;
    @BindView(R.id.switch_notification)
    public SwitchCompat switch_notification;
    @BindView(R.id.spinner_notification_tone)
    public MaterialBetterSpinner spinner_notification_tone;
    @BindView(R.id.spinner_notification_light)
    public MaterialBetterSpinner spinner_notification_light;
    @BindView(R.id.switch_notification_vibrate)
    public SwitchCompat switch_notification_vibrate;
    @BindView(R.id.text_invite_friend)
    public OptimusTextView text_invite_friend;
    @BindView(R.id.text_invite_facebook_friend)
    public OptimusTextView text_invite_facebook_friend;

    public static int REQUEST_BLUETOOTH = 1;
    public static int REQUEST_TONE = 2;
    private NotificationUtil notificationUtil;

    //GPS
    private PreferenceManager preferenceManager;
    private boolean currentlyTracking;
    private int check = 0;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_BLUETOOTH) {
            if (resultCode == RESULT_OK) {
                //Bluetooth On
                Toast.makeText(PreferencesActivity.this, "Bluetooth On", Toast.LENGTH_SHORT).show();
            } else {
                //Bluetooth Off
                Toast.makeText(PreferencesActivity.this, "Bluetooth Off", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == REQUEST_TONE) {
            if (resultCode == RESULT_OK) {
                Uri uriTone = data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);
                if (uriTone != null) {
                    notificationUtil.setUriSound(uriTone);
                    spinner_notification_tone.setText(notificationUtil.getNameSound());
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);
        ButterKnife.bind(this);
        startBase(toolbar);
        initToolbar(CommunityMaterial.Icon.cmd_arrow_left, R.string.main_menu_option_preference);
        init();
        setBluetooth();
        setRecurrenceTime();
        setNotification();
        setInvitations();
    }

    private void init() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void setBluetooth() {
        final BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null) {
            Toast.makeText(PreferencesActivity.this, "Buetooth NULL", Toast.LENGTH_LONG).show();
        } else {
            switch_bluetooth.setChecked(bluetoothAdapter.isEnabled());
        }
        switch_bluetooth.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (bluetoothAdapter != null) {
                        Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                        startActivityForResult(intent, REQUEST_BLUETOOTH);
                    }
                } else {
                    if (bluetoothAdapter != null) {
                        bluetoothAdapter.disable();
                    }
                }
            }
        });
    }

    private void setRecurrenceTime() {
        preferenceManager = new PreferenceManager(PreferencesActivity.this);
        currentlyTracking = preferenceManager.getBoolean("currentlyTracking", false);

        boolean firstTimeLoadindApp = getPreferenceManager().getBoolean("firstTimeLoadindApp", true);
        if (firstTimeLoadindApp) {
            getPreferenceManager().setBoolean("firstTimeLoadindApp", false);
            getPreferenceManager().putString("appID", UUID.randomUUID().toString());
        }

        /*if (!currentlyTracking) {
            statusRecurrence(false);
            switch_recurrence.setChecked(false);
        }

        switch_recurrence.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (isCheckPlayServices()) {
                    if (b) {
                        statusRecurrence(true);
                        startAlarmManager();
                        currentlyTracking = true;
                        getPreferenceManager().setBoolean("currentlyTracking", true);
                        getPreferenceManager().putFloat("totalDistanceInMeters", 0.0f);
                        getPreferenceManager().setBoolean("firstTimeGettingPosition", true);
                        getPreferenceManager().putString("sessionID", UUID.randomUUID().toString());
                    } else {
                        statusRecurrence(false);
                        cancelAlarmManager();
                        currentlyTracking = false;
                        preferenceManager.setBoolean("currentlyTracking", false);
                        getPreferenceManager().putString("sessionID", "");
                    }
                } else {
                    statusRecurrence(false);
                }
            }
        });*/

        String[] dataset = getResources().getStringArray(R.array.recurrence_frequency);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, dataset);

        AdapterView.OnItemSelectedListener onItemSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                check++;
                if (check > 1) {
                    saveInterval(i);
                    if (isCheckPlayServices()) {
                        startAlarmManager();
                        currentlyTracking = true;
                        getPreferenceManager().setBoolean("currentlyTracking", currentlyTracking);
                        getPreferenceManager().putFloat("totalDistanceInMeters", 0.0f);
                        getPreferenceManager().setBoolean("firstTimeGettingPosition", true);
                        getPreferenceManager().putString("sessionID", UUID.randomUUID().toString());
                    }
                } else {
                    if (isCheckPlayServices()) {
                        cancelAlarmManager();
                        currentlyTracking = false;
                        preferenceManager.setBoolean("currentlyTracking", currentlyTracking);
                        getPreferenceManager().putString("sessionID", "");
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        };
        spinner_recurrence.setOnItemSelectedListener(onItemSelectedListener);
        spinner_recurrence.setAdapter(adapter);
        spinner_recurrence.setSelection(getPosition(preferenceManager.getInt("intervalInMinutes", 0)));
    }

    private void saveInterval(int pos) {
        /*if (currentlyTracking) {
            showToast("user_needs_to_restart_tracking");
        }*/
        switch (pos) {
            case 0:
                preferenceManager.putInt("intervalInMinutes", 1);//1 min
                break;
            case 1:
                preferenceManager.putInt("intervalInMinutes", 5);//5 min
                break;
            case 2:
                preferenceManager.putInt("intervalInMinutes", 10);//10 min
                break;
            case 3:
                preferenceManager.putInt("intervalInMinutes", 15);//15 min
                break;
            case 4:
                preferenceManager.putInt("intervalInMinutes", 20);//20 min
                break;
            case 5:
                preferenceManager.putInt("intervalInMinutes", 30);//30 min
                break;
            case 6:
                preferenceManager.putInt("intervalInMinutes", 45);//45 min
                break;
            case 7:
                preferenceManager.putInt("intervalInMinutes", 60);//1 hours
                break;
            case 8:
                preferenceManager.putInt("intervalInMinutes", 120);//2 hours
                break;
            case 9:
                preferenceManager.putInt("intervalInMinutes", 180);//3 hours
                break;
            case 10:
                preferenceManager.putInt("intervalInMinutes", 720);//12 hours
                break;
            case 11:
                preferenceManager.putInt("intervalInMinutes", 1440);//24 hours
                break;
        }

    }

    private int getPosition(int min) {
        if (min == 1) {
            return 0;
        } else if (min == 5) {
            return 1;
        } else if (min == 10) {
            return 2;
        } else if (min == 15) {
            return 3;
        } else if (min == 20) {
            return 4;
        } else if (min == 30) {
            return 5;
        } else if (min == 45) {
            return 6;
        } else if (min == 60) {
            return 7;
        } else if (min == 120) {
            return 8;
        } else if (min == 180) {
            return 9;
        } else if (min == 720) {
            return 10;
        } else if (min == 1440) {
            return 11;
        }
        return 0;
    }

    private void startAlarmManager() {
        Context context = getBaseContext();
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent gpsTrackerIntent = new Intent(context, GpsTrackerAlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, gpsTrackerIntent, 0);
        int intervalInMinutes = preferenceManager.getInt("intervalInMinutes", 1);

        alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime(),
                intervalInMinutes * 60000, // 60000 = 1 minute
                pendingIntent);
    }

    private void cancelAlarmManager() {
        Context context = getBaseContext();
        Intent gpsTrackerIntent = new Intent(context, GpsTrackerAlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, gpsTrackerIntent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
    }

    private boolean isCheckPlayServices() {
        GoogleApiAvailability googleAPI = GoogleApiAvailability.getInstance();
        int result = googleAPI.isGooglePlayServicesAvailable(this);
        if (result != ConnectionResult.SUCCESS) {
            showToast("unable to connect to google play services.");
            return false;
        }
        return true;
    }

    private void setNotification() {
        notificationUtil = new NotificationUtil(PreferencesActivity.this);
        intiStatusNotifications();
        initTone();
        initLight();
        initVibrate();
    }

    private void intiStatusNotifications() {
        statusNotifications(notificationUtil.getActiveNotifications());
        switch_notification.setChecked(notificationUtil.getActiveNotifications());
        switch_notification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                statusNotifications(b);
            }
        });
    }

    private void statusNotifications(boolean activate) {
        notificationUtil.setActiveNotifications(activate);
        spinner_notification_tone.setEnabled(activate);
        spinner_notification_tone.setClickable(activate);
        spinner_notification_light.setEnabled(activate);
        spinner_notification_light.setClickable(activate);
        switch_notification_vibrate.setEnabled(activate);
        switch_notification_vibrate.setClickable(activate);
    }

    private void initTone() {
        List<String> dataset = new LinkedList<>(Arrays.asList(getString(R.string.notification_tone)));
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, dataset);
        spinner_notification_tone.setAdapter(adapter);
        spinner_notification_tone.setText(notificationUtil.getNameSound());
        spinner_notification_tone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
                intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, getString(R.string.notification_tone));
                intent.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_SILENT, false);
                intent.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_DEFAULT, true);
                intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_NOTIFICATION);
                intent.putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI, notificationUtil.getUriSound());
                startActivityForResult(intent, REQUEST_TONE);
            }
        });
    }

    private void initLight() {
        String[] colorsArray = getResources().getStringArray(R.array.colors_notification);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, colorsArray);
        spinner_notification_light.setText(colorsArray[notificationUtil.getLightColor()]);
        spinner_notification_light.setAdapter(adapter);

        //todo set color selected
        //notificationUtil.setLightColor(positionSelected);
    }

    private void initVibrate() {
        switch_notification_vibrate.setChecked(notificationUtil.getVibrateLong() != NotificationUtil.VIBRATE_OFF);
        switch_notification_vibrate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    notificationUtil.setVibrateLong(NotificationUtil.VIBRATE_SHORT);
                } else {
                    notificationUtil.setVibrateLong(NotificationUtil.VIBRATE_OFF);
                }
            }
        });
    }

    private void setInvitations() {
        text_invite_friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareNative shareNative = new ShareNative(PreferencesActivity.this);
                shareNative.shareContent(getString(R.string.invitation_title),
                        getString(R.string.invitation_title) + "\n \n" +
                        String.format(getString(R.string.invitation_message), getString(R.string.invitation_deep_link))+ "\n \n" +
                        "¡¡Otra parte del mensaje!!",
                        shareNative.TYPE_TEXT_PLAIN);
            }
        });
        text_invite_facebook_friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Uri deepLink = buildDeepLink(Uri.parse("https://boohos.com/welcome"), 1, false);
                ShareNative shareNative = new ShareNative(PreferencesActivity.this);
                shareNative.shareContent(getString(R.string.invitation_title),
                        String.format(getString(R.string.invitation_message), deepLink.toString()),
                        shareNative.TYPE_TEXT_PLAIN);
            }
        });
    }

    public Uri buildDeepLink(@NonNull Uri deepLink, int minVersion, boolean isAd) {
        // Get the unique appcode for this app.
        String appCode = getString(R.string.app_code_unique);

        // Get this app's package name.
        String packageName = getApplicationContext().getPackageName();

        // Build the link with all required parameters
        Uri.Builder builder = new Uri.Builder()
                .scheme("https")
                .authority(appCode + ".app.goo.gl")
                .path("/")
                .appendQueryParameter("link", deepLink.toString())
                .appendQueryParameter("apn", packageName);

        // If the deep link is used in an advertisement, this value must be set to 1.
        if (isAd) {
            builder.appendQueryParameter("ad", "1");
        }

        // Minimum version is optional.
        if (minVersion > 0) {
            builder.appendQueryParameter("amv", Integer.toString(minVersion));
        }

        // Return the completed deep link.
        return builder.build();
    }

}
