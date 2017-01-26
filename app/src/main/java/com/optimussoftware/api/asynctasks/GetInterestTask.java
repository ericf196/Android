package com.optimussoftware.api.asynctasks;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import com.optimussoftware.api.InterestResource;
import com.optimussoftware.api.UserInterestResource;
import com.optimussoftware.api.core.Factory;
import com.optimussoftware.api.response.commons.Meta;
import com.optimussoftware.api.response.interest.InterestList;
import com.optimussoftware.api.response.userinterest.UserInterestList;
import com.optimussoftware.db.Interest;
import com.optimussoftware.db.UserInterest;
import com.optimussoftware.boohos.UserInterestActivity;
import com.optimussoftware.boohos.data.Constants;
import com.optimussoftware.boohos.data.DBController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Response;

/**
 * Created by william.castillo@optimus-software.com on 04/07/16.
 */
public class GetInterestTask extends AsyncTask<UserInterestActivity, Void, Boolean> {
    private final String TAG = GetInterestTask.class.getSimpleName();
    private List<Interest> interests;
    private List<UserInterest> userInterests;
    private UserInterestActivity activity;
    private boolean syncronized;

    private Object sync = new Object();

    private void init(UserInterestActivity activity) {

        this.activity = activity;
        syncronized = false;
        interests = new ArrayList<>();
        userInterests = new ArrayList<>();
        activity.setVisibleProgressBar(View.VISIBLE);
    }

    private void getInterestFromDB() {
        DBController controller = DBController.getControler();
        interests = controller.getInterestList(activity);
        activity.setInterests(interests);
    }

    private boolean getInterests() throws IOException {
        InterestResource interestResource = new InterestResource();
        Response<InterestList> response;
        Meta meta;
        int page = 1;
        do {
            response = interestResource.listSync(page);
            if (response.isSuccessful()) {
                meta = response.body().getMeta();
                for (com.optimussoftware.api.response.interest.Item item : response.body().getItems())
                    interests.add(Factory.getInterestFromResponse(item));
            } else
                return false;
            page++;
        } while (!meta.isLast());
        Log.d(TAG, "Interests: " + interests.size());
        return true;
    }

    private boolean getUserInterest() throws IOException {
        UserInterestResource userInterestResource = new UserInterestResource();
        Response<UserInterestList> response;
        Meta meta;
        int page = 1;
        String user_id = activity.getUserID();
        do {
            response = userInterestResource.listSync(page, user_id);
            if (response.isSuccessful()) {
                meta = response.body().getMeta();
                for (com.optimussoftware.api.response.userinterest.Item item : response.body().getItems())
                    userInterests.add(Factory.getUserInterestFromResponse(item));
            } else
                return false;
            page++;
        } while (!meta.isLast());
        Log.d(TAG, "Users interest: " + userInterests.size());
        return true;
    }

    private boolean syncInterest() {
        try {
            boolean a = getInterests();
           // boolean b = getUserInterest();
            if (a) {
                DBController.getControler().sincronizeInterestData(activity, interests);
                activity.getPreferenceManager().setDate(Constants.LASTED_SYNC_INTERST, new Date());
            }
            return a;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }


    @Override
    protected Boolean doInBackground(UserInterestActivity... activities) {
        activity = activities[0];
        if (activity != null) {
            synchronized (sync) {
                init(activity);
                boolean sw = false;
                sw = syncInterest();
                if (sw) {
                }
                return sw;
            }
        }
        return false;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        if (result) {
            getInterestFromDB();
            activity.populateGrid();
            activity.setVisibleProgressBar(View.GONE);
        } else
            activity.finish();
    }

    @Override
    protected void onCancelled() {
        if (!syncronized)
            activity.finish();
    }
}
