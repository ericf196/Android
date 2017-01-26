package com.optimussoftware.boohos.listview.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.optimussoftware.db.Advertising;
import com.optimussoftware.db.AdvertisingInterest;
import com.optimussoftware.db.Interest;
import com.optimussoftware.boohos.main.MainActivity;
import com.optimussoftware.boohos.R;
import com.optimussoftware.boohos.data.DBController;
import com.optimussoftware.boohos.listview.adapters.ByCategoryViewAdapter;
import com.optimussoftware.boohos.listview.model.AdvertisingByInterest;
import com.optimussoftware.boohos.service.ClearDataUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by leonardojpr on 2/09/16.
 */
public class ByInterestFragment extends Fragment implements MainActivity.OnByCategoryListener {

    private static final String TAG = ByInterestFragment.class.getSimpleName();

    private static final String INDEX = "index";
    private List<AdvertisingByInterest> advertisingByInterestList;
    private int index;
    ByCategoryViewAdapter byCategoryViewAdapter;
    RecyclerView recyclerView;

    public BroadcastReceiver receiverUpdateData;

    public static ByInterestFragment newInstance(int index) {

        // Instantiate a new fragment
        ByInterestFragment fragment = new ByInterestFragment();

        // Save the parameters
        Bundle bundle = new Bundle();
        bundle.putInt(INDEX, index);
        fragment.setArguments(bundle);
        fragment.setRetainInstance(true);

        return fragment;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        this.index = (getArguments() != null) ? getArguments().getInt(INDEX)
                : -1;

        ((MainActivity) getContext()).setOnByCategoryListener(this);
        receiverUpdateData();
        advertisingByInterestList = new ArrayList<>();
        recyclerView = (RecyclerView) getView().findViewById(R.id.by_category_list);
        boolean a = sortByInterest();
        if (a) {
           initRecyclerView(advertisingByInterestList);
        } else {
            initRecyclerView(advertisingByInterestList);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.by_category_fragment, container, false);

        return rootView;
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
                advertisingByInterestList = new ArrayList<>();
                boolean a = sortByInterest();
                if (a) {
                    initRecyclerView(advertisingByInterestList);
                } else {
                    initRecyclerView(advertisingByInterestList);
                }
            }
        };
    }

    private boolean sortByInterest() {
        List<Advertising> advertisingList = DBController.getControler().getAdvertisingList(getContext());
        if (!advertisingList.isEmpty()) {
            for (int i = 0; i < advertisingList.size(); i++) {
                sortByInterestFromDB(advertisingList.get(i));
            }
            return true;
        }
        return false;
    }

    private void sortByInterestFromDB(Advertising advertising) {

        List<AdvertisingInterest> advertisingInterestList;

        advertisingInterestList = DBController.getControler().getAdvertisingInterestList(getContext(), advertising.get_id());
        List<Advertising> a = new ArrayList<>();

        for (int i = 0; i < advertisingInterestList.size(); i++) {
            if (advertisingByInterestList.isEmpty()) {
                saveAdvertisingByInterest(advertising, DBController.getControler().getInterest(getContext(), advertisingInterestList.get(i).getInterest_id()));
            } else {
                int position = 0;
                boolean exist = false;
                for (int j = 0; j < advertisingByInterestList.size(); j++) {
                    if (advertisingByInterestList.get(j).getInterest().get_id().equals(advertisingInterestList.get(i).getInterest_id())) {
                        exist = true;
                        position = j;
                    } else {
                        addAdvertisingByInterest(j, advertising);
                    }
                }

                if (!exist) {
                    saveAdvertisingByInterest(advertising, DBController.getControler().getInterest(getContext(), advertisingInterestList.get(i).getInterest_id()));
                } else {
                    addAdvertisingByInterest(position, advertising);

                }
            }
        }
    }

    private void saveAdvertisingByInterest(Advertising advertising, Interest interest) {
        List<Advertising> a = new ArrayList<>();
        AdvertisingByInterest advertisingByInterest = new AdvertisingByInterest(interest, a);
        advertisingByInterest.getAdvertisingList().add((advertising));
        advertisingByInterestList.add(advertisingByInterest);
    }

    private void addAdvertisingByInterest(int position, Advertising advertising) {
        boolean exist = false;
        for (int i = 0; i < advertisingByInterestList.get(position).getAdvertisingList().size(); i++) {
            if (advertisingByInterestList.get(position).getAdvertisingList().get(i).get_id().equals(advertising.get_id())) {
                exist = true;
            }
        }
        if (!exist) {
            advertisingByInterestList.get(position).getAdvertisingList().add(advertising);
        }
    }

    public void initRecyclerView(List<AdvertisingByInterest> advertisingByCategories) {


        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        byCategoryViewAdapter = new ByCategoryViewAdapter(advertisingByCategories);
        recyclerView.setAdapter(byCategoryViewAdapter);

    }

    @Override
    public void byCategoryListener(List<AdvertisingByInterest> advertisingByCategories) {
        byCategoryViewAdapter.updateList(advertisingByCategories);
    }
}
