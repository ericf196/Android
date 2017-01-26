package com.optimussoftware.boohos.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.optimussoftware.api.FavoriteResource;
import com.optimussoftware.api.core.Factory;
import com.optimussoftware.api.response.favorites.advertisingFavorite.AdvertisingFavoriteList;
import com.optimussoftware.boohos.widget.EndlessRecyclerViewScrollListener;
import com.optimussoftware.db.Advertising;
import com.optimussoftware.db.Favorites;
import com.optimussoftware.boohos.R;
import com.optimussoftware.boohos.data.DBController;
import com.optimussoftware.boohos.data.PersonalInfo;
import com.optimussoftware.boohos.gridview.adapters.FavoriteAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by guerra on 20/10/16.
 * Fragment with advertising favorites
 */

public class FavoriteFragment extends Fragment implements Callback<AdvertisingFavoriteList> {
    private SwipeRefreshLayout swipeFavorites;

    private FavoriteResource favoriteResource;
    private PersonalInfo personalInfo;
    private FavoriteAdapter favoriteAdapter;
    private DBController controller;
    private String TAG = "FavoriteFragment";

    private int page = 1;
    private float total = 0.0f;
    private float maxResults = 0.0f;
    private boolean canGetNext = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        personalInfo = new PersonalInfo(getActivity());
        favoriteResource = new FavoriteResource();
        controller = DBController.getControler();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = LayoutInflater.from(getActivity()).inflate(R.layout.wihs_fragment, container, false);
        initRecycler(fragmentView);
        return fragmentView;
    }

    private void initRecycler(View view) {
        RecyclerView recyclerFavorites = (RecyclerView) view.findViewById(R.id.recycler_favorites);
        swipeFavorites = (SwipeRefreshLayout) view.findViewById(R.id.swipe_favorites);
        favoriteAdapter = new FavoriteAdapter();
        GridLayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 3, GridLayoutManager.VERTICAL, false);
        recyclerFavorites.setLayoutManager(mLayoutManager);
        recyclerFavorites.setAdapter(favoriteAdapter);
        recyclerFavorites.addOnScrollListener(new EndlessRecyclerViewScrollListener(mLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                if (page <= Math.ceil((total / maxResults))) {
                    if (canGetNext) {
                        canGetNext = false;
                        Log.d("ReviewActivity", "next " + page);
                        getAdvertisingFavorite(page, false);
                        //reviewResource.list(page, advertising_id, cb1);
                    }
                }
                Log.d("ReviewActivity", "onLoadMore next " + page);
            }
        });
        swipeFavorites.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getAdvertisingFavorite(1, false);
            }
        });
        setFavorites();
    }

    private void setFavorites() {
        Log.d(TAG, "setFavorites");
        //todo
        getAdvertisingFavorite(1, true);
        //todo
    }

    private void getAdvertisingFavorite(int page, boolean refresh) {
        swipeFavorites.setRefreshing(refresh);
        favoriteResource.getAdvertisingFavorite(page, personalInfo.getUuid(), this);
    }

    @Override
    public void onResponse(Call<AdvertisingFavoriteList> call, Response<AdvertisingFavoriteList> response) {
        Log.d(TAG, "onResponse");
        swipeFavorites.setRefreshing(false);
        List<Favorites> favorites = new ArrayList<>();
        List<Advertising> adverstingsLists = new ArrayList<>();
        for (int i = 0; i < response.body().getItems().size(); i++) {
            favorites.add(Factory.getFavoriteFromResponse(response.body().getItems().get(i)));
            adverstingsLists.add(Factory.getAdvertisingFavoriteFromResponse(response.body().getItems().get(i).getAdvertisingId()));
            controller.createFavorite(getActivity(), favorites.get(i));
            //controller.createAdvertising(getActivity(), adverstingsLists.get(i));
            //controller.createImagenFromAdvertising(getActivity(), response.body().getItems().get(i).getAdvertisingId());
        }
        favoriteAdapter.clearAdvertisingFavorites();
        favoriteAdapter.addAdvertisingFavorites(adverstingsLists);

        page = response.body().getMeta().getPage() + 1;
        total = response.body().getMeta().getTotal();
        maxResults = response.body().getMeta().getMaxResults();
    }

    @Override
    public void onFailure(Call<AdvertisingFavoriteList> call, Throwable t) {
        Log.e(TAG, "onFailure");
        swipeFavorites.setRefreshing(false);
        t.printStackTrace();
    }

    public void reinit() {
        Log.d(TAG, "reinit");
        //todo
        getAdvertisingFavorite(1, true);
        //todo
    }
}
