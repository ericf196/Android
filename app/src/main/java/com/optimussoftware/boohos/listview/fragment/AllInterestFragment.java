package com.optimussoftware.boohos.listview.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.optimussoftware.boohos.listview.holders.OffertViewHolders;
import com.optimussoftware.db.Advertising;
import com.optimussoftware.boohos.main.MainActivity;
import com.optimussoftware.boohos.R;
import com.optimussoftware.boohos.data.DBController;
import com.optimussoftware.boohos.listview.adapters.OffertViewAdapter;
import com.optimussoftware.boohos.service.ClearDataUtil;
import com.optimussoftware.boohos.widget.EndlessRecyclerViewScrollListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by leonardojpr on 2/09/16.
 */
public class AllInterestFragment extends Fragment implements OffertViewAdapter.OnListAdapterInteractionListener,
        MainActivity.OnlastOffertListener {

    private static final String TAG = AllInterestFragment.class.getSimpleName();
    private static final String INDEX = "index";
    private int index = 0;
    private OffertViewAdapter offertViewAdapter;
    private boolean isRefresh = false;
    RecyclerView recyclerView;
    RelativeLayout relativeLayout;

    private MaterialDialog dialogLoading;

    public BroadcastReceiver receiverUpdateData;

    List<Advertising> advertisingListByDB;
    List<Advertising> advertisingListByBeacons;
    // List<Advertising> advertisingListFinal;
    Map<String, Advertising> checkAdvertising = new HashMap<String, Advertising>();

    int by = 0;

    int totalItemByBeacons = 0;
    int totalItemByDB = 0;
    int start = 0;
    int end = 0;
    int maxIterator = 9;

    final LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity(),
            LinearLayoutManager.VERTICAL, false);


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.all_interest_fragment, container, false);
        return rootView;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.index = (getArguments() != null) ? getArguments().getInt(INDEX)
                : -1;
        ((MainActivity) getContext()).setOnlastOffertListener(this);
        receiverUpdateData();
        dialogLoading = dialogLoading();
        advertisingListByBeacons = new ArrayList<>();
        advertisingListByDB = new ArrayList<>();
        advertisingListByDB = DBController.getControler().getAdvertisingList(getContext());
        advertisingListByBeacons = advertisingListByDB;
        relativeLayout = (RelativeLayout) getView().findViewById(R.id.relative);
        Log.d(TAG, "advertisingListByDB: " + advertisingListByDB.size());
        sortByDate(advertisingListByDB);
        recyclerView = (RecyclerView) getView().findViewById(R.id.offerts_list);
        initRecyclerView(advertisingListByDB);
    }

    @Override
    public void onStart() {
        super.onStart();
        LocalBroadcastManager.getInstance(getContext()).registerReceiver((receiverUpdateData), new IntentFilter(String.valueOf(ClearDataUtil.UPDATEDATA)));
    }

    public void receiverUpdateData() {
        receiverUpdateData = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.i(TAG, "refresh view ");
                advertisingListByBeacons = new ArrayList<>();
                advertisingListByDB = new ArrayList<>();
                advertisingListByDB = DBController.getControler().getAdvertisingList(getContext());
                advertisingListByBeacons = advertisingListByDB;

                sortByDate(advertisingListByDB);
                initRecyclerView(advertisingListByDB);
            }
        };
    }


    private void checkServerAndDB() {
        start = 0;
        end = 0;
        advertisingListByDB = DBController.getControler().getAdvertisingList(getContext());
        if (!advertisingListByDB.isEmpty() || !advertisingListByBeacons.isEmpty()) {
            sortByDate(advertisingListByDB);

            for (Advertising a : advertisingListByDB) {
                checkAdvertising.put(a.get_id(), a);
            }

            for (Advertising b : advertisingListByBeacons) {
                checkAdvertising.put(b.get_id(), b);
            }


            for (int j = 0; j < advertisingListByDB.size(); j++) {
                int position = 0;
                boolean exist = false;
                for (int i = 0; i < advertisingListByBeacons.size(); i++) {

                    position = j;
                    if (advertisingListByBeacons.get(i).get_id().equals(advertisingListByDB.get(j).get_id())) {
                        exist = true;

                    }
                }
                if (!exist) {
                    Log.i(TAG, "position" + position);
                    advertisingListByBeacons.add(advertisingListByDB.get(position));
                }
            }

        }

       /* advertisingListFinal = new ArrayList<>();
        for (Map.Entry<String, Advertising> a : checkAdvertising.entrySet()) {
            advertisingListFinal.add(a.getValue());
        }

        showAdvertising();*/

        initRecyclerView(advertisingListByBeacons);
    }

   /* private void showAdvertising() {

        if (advertisingListFinal.size() < 9) {
            end = advertisingListFinal.size();
        } else {
            end = 9;
        }
    }*/

    public void sortByDate(List<Advertising> advertising) {
        Collections.sort(advertising, new Comparator<Advertising>() {
            @Override
            public int compare(Advertising o1, Advertising o2) {
                return o2.getCreated().compareTo(o1.getCreated());
            }
        });
    }

    @Override
    public void onListAdapterInteraction(Advertising item, OffertViewHolders holder) {

        Log.i(TAG, item.get_id() + " " + item.getName());
        ((MainActivity) getContext()).initDetailView(item, holder);
    }


    public void initRecyclerView(final List<Advertising> advertisings) {

        Log.d(TAG, "Tamaño advertisings.size() " + advertisings.size());

        recyclerView.setLayoutManager(mLayoutManager);
        offertViewAdapter = new OffertViewAdapter(getContext(), advertisings, this);
        recyclerView.setAdapter(offertViewAdapter);

        recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(mLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                if (advertisingListByBeacons.size() > maxIterator)
                    recorrerArray(start, end);
            }
        });


    }

    private void recorrerArray(int initiate, int finish) {
        Log.d(TAG, "Ini --> " + initiate + " fin --> " + finish);
        List<Advertising> walk = new ArrayList<>(advertisingListByBeacons.subList(initiate, finish));
        offertViewAdapter.add(walk);
        Log.d(TAG, "advertising --> " + advertisingListByBeacons.size() + " fin --> " + finish);

        if (advertisingListByBeacons.size() < finish) {
            end = advertisingListByBeacons.size();
        } else {
            start = finish;
            end = finish + 10;
        }

    }


    @Override
    public void lastOffertListener(List<Advertising> advertisings) {
        advertisingListByBeacons.clear();
        advertisingListByBeacons = advertisings;
        checkServerAndDB();
    }

    private MaterialDialog dialogLoading() {
        return new MaterialDialog.Builder(getActivity())
                .theme(Theme.LIGHT)
                .content(R.string.please_wait)
                .autoDismiss(false)
                .progress(true, 0)
                .build();
    }

    private void setLoading(boolean loading) {
        if (loading) {
            dialogLoading.show();
        } else {
            dialogLoading.dismiss();
        }
    }

    private void iniBeacons() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                recyclerView.setVisibility(View.VISIBLE);
                setLoading(false);
                checkServerAndDB();
            }
        }, 2000);
    }
}
