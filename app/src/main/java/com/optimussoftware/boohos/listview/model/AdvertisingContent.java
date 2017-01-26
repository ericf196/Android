package com.optimussoftware.boohos.listview.model;

import android.content.Context;
import android.util.Log;

import com.optimussoftware.db.Advertising;
import com.optimussoftware.db.AdvertisingImage;
import com.optimussoftware.boohos.data.Constants;
import com.optimussoftware.boohos.data.DBController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class AdvertisingContent {

    private static final String TAG = AdvertisingContent.class.getSimpleName();

    private Context context;
    private List<Advertising> items = new ArrayList<>();
    private Map<String, Advertising> itemsMap = new HashMap<>();
    private Map<String, String> campaignMap = new HashMap<>();

    public AdvertisingContent(Context context) {
        this.context = context;
    }

    public AdvertisingContent(Context context, String ids) {
        this.context = context;
        String[] id = ids.split("~");
        for (int i = 0; i < id.length; i++) {
            Advertising advertising = DBController.getControler().getAdvertising(context, id[i]);
            if (advertising != null) {
                items.add(advertising);
                itemsMap.put(advertising.get_id(), advertising);
                campaignMap.put("campaign_id", advertising.getCampaign_id());
            }
        }
    }

    private void addItem(Advertising item) {
        items.add(item);
        itemsMap.put(item.get_id(), item);
    }

    public List<Advertising> getItems() {
        return items;
    }

    public static String pathViewDefault(Context context, Advertising advertising) {
        List<AdvertisingImage> images = DBController.getControler().getAdvertisingImageList(context, advertising.get_id());
        Log.i(TAG, "pathViewDefault array probando" + images.size());
        if (images.size() > 0) {
            return Constants.URL_WEB + DBController.getControler().getImage(context, images.get(0).getImage_id()).getFile();
        }
        return "";
    }
}
