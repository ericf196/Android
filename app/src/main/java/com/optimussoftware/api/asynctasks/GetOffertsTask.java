package com.optimussoftware.api.asynctasks;

import android.os.AsyncTask;
import android.util.Log;

import com.optimussoftware.api.core.Factory;
import com.optimussoftware.api.response.newAdvertising.AdverstingsList;
import com.optimussoftware.boohos.data.DBController;
import com.optimussoftware.boohos.listview.model.AdvertisingByInterest;
import com.optimussoftware.boohos.main.MainActivity;
import com.optimussoftware.db.Advertising;
import com.optimussoftware.db.AdvertisingInterest;
import com.optimussoftware.db.Interest;
import com.optimussoftware.db.Location;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by leonardojpr on 10/1/16.
 */
public class GetOffertsTask extends AsyncTask<String, Void, Boolean> {

    private static final String TAG = GetOffertsTask.class.getSimpleName();

    //todo  Campaign --> False || Advertising --> true

    private MainActivity mainActivity;
    private AdverstingsList adverstingsList;
    private List<AdvertisingByInterest> advertisingByInterestList;

    private Object sync = new Object();

    public GetOffertsTask(MainActivity mainActivity, AdverstingsList adverstingsList) {
        this.mainActivity = mainActivity;
        this.adverstingsList = adverstingsList;
        advertisingByInterestList = new ArrayList<>();
    }

    private boolean sortByInterests() throws IOException {

        for (com.optimussoftware.api.response.newAdvertising.Item item : adverstingsList.getItems()) {
            Log.d(TAG, "sortByInterests" + item.getInterestList().get(0).getName());
            Location location = Factory.getLocationSource(item.getSource());
            if (DBController.getControler().getLocation(mainActivity, location.get_id()) == null) {
                getLocation(location);
            }

            Log.i(TAG, "item.getInterestList().size() --> " + item.getInterestList().size());
            for (int i = 0; i < item.getInterestList().size(); i++) {
                AdvertisingInterest advertisingInterest = new AdvertisingInterest();
                advertisingInterest.setAdvertising_id(item.getId());
                advertisingInterest.setInterest_id(item.getInterestList().get(i).getId());
                if (DBController.getControler().checkAdvertisingInterestList(mainActivity, item.getInterestList().get(i).getId(), item.getId()).isEmpty())
                    DBController.getControler().createAdvertisingInterest(mainActivity, advertisingInterest);

                boolean exist = false;
                if (advertisingByInterestList.isEmpty()) {

                    Interest interest = new Interest();
                    interest.set_id(item.getInterestList().get(i).getId());
                    interest.setName(item.getInterestList().get(i).getName());
                    interest.setImage(item.getInterestList().get(i).getLogo());
                    saveAdvertisingByInterest(item, interest);
                } else {
                    int position = 0;
                    for (int j = 0; j < advertisingByInterestList.size(); j++) {

                        if (advertisingByInterestList.get(j).getInterest().get_id().equals(item.getInterestList().get(i).getId())) {
                            exist = true;
                            position = j;
                        }
                    }

                    if (!exist) {
                        Interest interest = new Interest();
                        interest.set_id(item.getInterestList().get(i).getId());
                        interest.setName(item.getInterestList().get(i).getName());
                        interest.setImage(item.getInterestList().get(i).getLogo());
                        saveAdvertisingByInterest(item, interest);
                    } else {
                        addAdvertisingByInterest(position, Factory.getAdvertisingFromResponse(item));
                    }
                }
            }
        }

        return true;
    }

    private void saveAdvertisingByInterest(com.optimussoftware.api.response.newAdvertising.Item advertising, Interest interest) {
        List<Advertising> a = new ArrayList<>();
        AdvertisingByInterest advertisingByInterest = new AdvertisingByInterest(interest, a);

        advertisingByInterest.getAdvertisingList().add(Factory.getAdvertisingFromResponse(advertising));

        advertisingByInterestList.add(advertisingByInterest);
        DBController.getControler().createInterest(mainActivity, interest);
    }

    private void addAdvertisingByInterest(int position, Advertising advertising) {
        Log.d(TAG," addAdvertisingByInterest : " + position + " " + advertising.getName());
        boolean exist = false;
        for (int i = 0; i < advertisingByInterestList.get(position).getAdvertisingList().size(); i++) {
            if (advertisingByInterestList.get(position).getAdvertisingList().get(i).get_id().equals(advertising.get_id())) {
                exist = true;
            }
        }
        if (!exist) {
            Log.d(TAG," add : " );
            advertisingByInterestList.get(position).getAdvertisingList().add(advertising);

        }
    }

    private boolean getLocation(Location location) throws IOException {
       /* LocationResource locationResource = new LocationResource();
        Response<Item> response;
        response = locationResource.listSync(location_id);
        if (response.isSuccessful()) {
            Log.i(TAG, "locations --> " + response.body().getName());
            Location obj = new Location();
            obj.set_id(response.body().getId());
            obj.set_etag(response.body().getEtag());
            obj.setCreated(Commons.parseDate(response.body().getCreated()));
            obj.setUpdated(Commons.parseDate(response.body().getUpdated()));
            obj.setName(response.body().getName());
            obj.setImage(response.body().getLogo().getFilename());

            return true;
        } */
        DBController.getControler().createLocation(mainActivity, location);

        return true;
    }

    @Override
    protected Boolean doInBackground(String... data) {

        synchronized (sync) {
            try {
                boolean sw = false;
                sw = sortByInterests();
                if (sw) {
                }
                return sw;
            } catch (Exception e) {
                Log.i(TAG, "doInBackground exception err -->" + e.toString());
            }

        }
        return false;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        if (aBoolean) {
            Log.i(TAG, "advertisingList size -->" + adverstingsList.getItems().size());
            Log.i(TAG, "advertisingByInterest size -->" + advertisingByInterestList.size());
            mainActivity.onlastOffertListener.lastOffertListener(mainActivity.advertisingContent.getItems());
            mainActivity.onByCategoryListener.byCategoryListener(advertisingByInterestList);
        } else {
            Log.i(TAG, "Err onPostExecute");
        }
    }
}
