package com.optimussoftware.boohos.notification;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.github.clans.fab.FloatingActionButton;
import com.mikepenz.community_material_typeface_library.CommunityMaterial;
import com.mikepenz.iconics.view.IconicsImageView;
import com.optimussoftware.db.NotificationAdvertising;
import com.optimussoftware.boohos.R;
import com.optimussoftware.boohos.data.Constants;
import com.optimussoftware.boohos.data.DBController;
import com.optimussoftware.boohos.notification.adapters.AlarmManagerViewAdapter;
import com.optimussoftware.boohos.widget.BaseActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by leonardojpr on 11/4/16.
 */

public class NotificationActivity extends BaseActivity {

    private static final String TAG = NotificationActivity.class.getSimpleName();

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.notify)
    public IconicsImageView notify;
    @BindView(R.id.content_notify)
    RelativeLayout content_notify;
    @BindView(R.id.option_notification)
    FloatingActionButton floatingActionButton;
    @BindView(R.id.recycler_alarmmanager)
    RecyclerView recyclerView;

    final LinearLayoutManager mLayoutManager = new LinearLayoutManager(this,
            LinearLayoutManager.VERTICAL, false);

    private AlarmManagerViewAdapter alarmManagerViewAdapter;

    private Map<String, NotificationAdvertising> map = new HashMap();

    List<NotificationAdvertising> notificationAdvertisingList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarmmanager);
        ButterKnife.bind(this);
        startBase(toolbar);
        initToolbar(CommunityMaterial.Icon.cmd_arrow_left, R.string.app_name, R.string.notifications);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(Constants.NOTIFICATION_VIEWED);
                finish();
            }
        });
        notificationAdvertisingList = new ArrayList<>();
        List<NotificationAdvertising> notificationAdvertisingListDB = DBController.getControler().getNotificationAdvertisingList(getBaseContext());
        Log.i(TAG, notificationAdvertisingListDB.size() + " db");
        for (int i = 0; i < notificationAdvertisingListDB.size(); i++) {
            if (notificationAdvertisingListDB.get(i).getDate().compareTo(new Date()) < 0) {
                notificationAdvertisingList.add(notificationAdvertisingListDB.get(i));
            }
        }

        Log.i(TAG, notificationAdvertisingList.size() + "");
        sortByDate(notificationAdvertisingList);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);
        alarmManagerViewAdapter = new AlarmManagerViewAdapter(this, notificationAdvertisingList, AlarmManagerViewAdapter.NOTIFICATION);
        recyclerView.setAdapter(alarmManagerViewAdapter);
        notify.setVisibility(View.VISIBLE);
        notify.setIcon(CommunityMaterial.Icon.cmd_settings);
        content_notify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), AlarmMangerActivity.class);
                startActivity(intent);
            }
        });
        floatingActionButton.setVisibility(View.GONE);
    }

    public void sortByDate(List<NotificationAdvertising> advertising) {
        Collections.sort(advertising, new Comparator<NotificationAdvertising>() {
            @Override
            public int compare(NotificationAdvertising o1, NotificationAdvertising o2) {
                return o2.getDate().compareTo(o1.getDate());
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == Constants.NOTIFICATION_VIEWED) {
            Log.i(TAG, "yes");
            List<NotificationAdvertising> notificationAdvertisingListDB = DBController.getControler().getNotificationAdvertisingList(getBaseContext());
            notificationAdvertisingList = new ArrayList<>();
            Log.i(TAG, notificationAdvertisingListDB.size() + " db");
            for (int i = 0; i < notificationAdvertisingListDB.size(); i++) {
                if (notificationAdvertisingListDB.get(i).getDate().compareTo(new Date()) < 0) {
                    notificationAdvertisingList.add(notificationAdvertisingListDB.get(i));
                }
            }
            sortByDate(notificationAdvertisingList);
            alarmManagerViewAdapter = new AlarmManagerViewAdapter(this, notificationAdvertisingList, AlarmManagerViewAdapter.NOTIFICATION);
            recyclerView.setAdapter(alarmManagerViewAdapter);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(Constants.NOTIFICATION_VIEWED);
    }
}

