package com.optimussoftware.boohos.notification;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.github.clans.fab.FloatingActionButton;
import com.mikepenz.community_material_typeface_library.CommunityMaterial;
import com.optimussoftware.db.NotificationAdvertising;
import com.optimussoftware.boohos.R;
import com.optimussoftware.boohos.data.DBController;
import com.optimussoftware.boohos.notification.adapters.AlarmManagerViewAdapter;
import com.optimussoftware.boohos.widget.BaseActivity;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by leonardojpr on 11/3/16.
 */

public class AlarmMangerActivity extends BaseActivity {

    private static final String TAG = AlarmMangerActivity.class.getSimpleName();

    @BindView(R.id.toolbar)
    Toolbar toolbar;
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
        initToolbar(CommunityMaterial.Icon.cmd_arrow_left, R.string.app_name, R.string.notifications_setting);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        floatingActionButton.setVisibility(View.GONE);

        List<NotificationAdvertising> notificationAdvertisingListDB = DBController.getControler().getNotificationAdvertisingList(getBaseContext());
        Log.i(TAG, notificationAdvertisingListDB.size()+ " db");
        for (int i = 0; i < notificationAdvertisingListDB.size(); i++) {
            if (notificationAdvertisingListDB.get(i).getDate().compareTo(new Date()) > 0) {
                map.put(notificationAdvertisingListDB.get(i).getAdvertising_id(), notificationAdvertisingListDB.get(i));
            }
        }

        notificationAdvertisingList = new ArrayList<>();
        for (Map.Entry<String, NotificationAdvertising> m : map.entrySet()) {
            notificationAdvertisingList.add(m.getValue());
        }

        Log.i(TAG, notificationAdvertisingList.size()+ "");

        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);
        alarmManagerViewAdapter = new AlarmManagerViewAdapter(this, notificationAdvertisingList, AlarmManagerViewAdapter.ALARMANAGER);
        recyclerView.setAdapter(alarmManagerViewAdapter);
    }

}
