package com.optimussoftware.boohos.listview.model;

import com.optimussoftware.db.Advertising;
import com.optimussoftware.db.Interest;

import java.util.List;

/**
 * Created by leonardojpr on 8/09/16.
 */
public class AdvertisingByInterest {

    private static final String TAG = AdvertisingByInterest.class.getSimpleName();

    private Interest interest;
    private List<Advertising> advertisingList;

    public AdvertisingByInterest(Interest interest, List<Advertising> advertisingList) {
        this.interest = interest;
        this.advertisingList = advertisingList;
    }

    public Interest getInterest() {
        return interest;
    }

    public void setInterest(Interest interest) {
        this.interest = interest;
    }

    public List<Advertising> getAdvertisingList() {
        return advertisingList;
    }

    public void setAdvertisingList(List<Advertising> advertisingList) {
        this.advertisingList = advertisingList;
    }
}
