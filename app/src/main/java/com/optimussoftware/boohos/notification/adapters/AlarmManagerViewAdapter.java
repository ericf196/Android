package com.optimussoftware.boohos.notification.adapters;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.optimussoftware.boohos.widget.BaseActivity;
import com.optimussoftware.db.Advertising;
import com.optimussoftware.db.NotificationAdvertising;
import com.optimussoftware.boohos.R;
import com.optimussoftware.boohos.data.Constants;
import com.optimussoftware.boohos.data.DBController;
import com.optimussoftware.boohos.notification.NotificationActivity;
import com.optimussoftware.boohos.notification.holders.AlarmManagerViewHolder;
import com.optimussoftware.boohos.service.receiver.AlarmEfectiveDays;
import com.optimussoftware.boohos.viewDetail.ViewDetailMaterial;

import java.util.List;

/**
 * Created by leonardojpr on 11/3/16.
 */

public class AlarmManagerViewAdapter extends RecyclerView.Adapter<AlarmManagerViewHolder> {

    private static final String TAG = AlarmManagerViewAdapter.class.getSimpleName();

    public static final int ALARMANAGER = 1;
    public static final int NOTIFICATION = 2;

    Context context;
    int where = 0;
    List<NotificationAdvertising> notificationAdvertisingList;

    public AlarmManagerViewAdapter(Context context, List<NotificationAdvertising> notificationAdvertisingList, int where) {
        this.context = context;
        this.notificationAdvertisingList = notificationAdvertisingList;
        this.where = where;
    }

    @Override
    public AlarmManagerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_alarmmanager, parent, false);
        return new AlarmManagerViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(final AlarmManagerViewHolder holder, final int position) {
        final Advertising advertising = DBController.getControler().getAdvertising(context, notificationAdvertisingList.get(position).getAdvertising_id());
        if (where == 1) {
            holder.setViewAlarmManager(advertising);
            holder.remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cancelNotify(advertising);
                    removeAt(position);
                }
            });
        } else {
            Log.i(TAG, "viewed --> " + notificationAdvertisingList.get(position).getViewed() );
            holder.setViewAreaNotification(advertising, notificationAdvertisingList.get(position));
            holder.content.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ViewDetailMaterial.class);
                    intent.putExtra("item", advertising);
                    intent.putExtra("notification", notificationAdvertisingList.get(position));
                    intent.putExtra("from", ALARMANAGER);
                    //((NotificationActivity)context).startActivityForResult(intent, Constants.NOTIFICATION_VIEWED);

                    Pair<View, String> p = new Pair<View, String>(holder.image_adv,
                            context.getString(R.string.transition_name_advertising));
                    ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                            (BaseActivity) context, p);

                    ActivityCompat.startActivityForResult((NotificationActivity) context, intent,Constants.NOTIFICATION_VIEWED, options.toBundle());

                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return notificationAdvertisingList.size();

    }

    public void cancelNotify(Advertising advertising) {
        List<NotificationAdvertising> notificationAdvertisingListDB = DBController.getControler().getNotificationAdvertisingList(context, advertising.get_id());
        Log.i(TAG, notificationAdvertisingListDB.size() + "");
        for (int i = 0; i < notificationAdvertisingListDB.size(); i++) {
            if (notificationAdvertisingListDB.get(i).getDelete()) {
                Intent alarmIntent = new Intent(context, AlarmEfectiveDays.class);
                alarmIntent.putExtra("item", advertising);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                        Integer.parseInt(notificationAdvertisingListDB.get(i).get_id()),
                        alarmIntent, PendingIntent.FLAG_ONE_SHOT);

                AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

                manager.cancel(pendingIntent);
                DBController.getControler().removeNotificationAdvertising(context, notificationAdvertisingListDB.get(i));
            }
        }

    }

    public void update(List<NotificationAdvertising> list) {
        notificationAdvertisingList.clear();
        notificationAdvertisingList.addAll(list);
        notifyDataSetChanged();
    }

    public void removeAt(int position) {
        notificationAdvertisingList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(0, notificationAdvertisingList.size());
    }
}
