package com.optimussoftware.boohos.viewDetail;


import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.LinearLayout;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import com.mikepenz.community_material_typeface_library.CommunityMaterial;
import com.optimussoftware.api.LocationResource;
import com.optimussoftware.db.Location;
import com.optimussoftware.boohos.R;
import com.optimussoftware.boohos.widget.BaseActivity;
import com.optimussoftware.boohos.widget.OptimusTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by guerra on 01/08/16.
 */
public class LocationActivity extends BaseActivity
        implements OnMapReadyCallback {

    @BindView(R.id.toolbar)
    public Toolbar toolbar;
    @BindView(R.id.text_name)
    public OptimusTextView text_name;
    @BindView(R.id.linear_web)
    public LinearLayout linear_web;
    @BindView(R.id.text_web)
    public OptimusTextView text_web;
    @BindView(R.id.linear_address)
    public LinearLayout linear_address;
    @BindView(R.id.text_address)
    public OptimusTextView text_address;
    @BindView(R.id.linear_map)
    public LinearLayout linear_map;
    @BindView(R.id.linear_phone)
    public LinearLayout linear_phone;
    @BindView(R.id.text_phone)
    public OptimusTextView text_phone;

    private String id_location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        ButterKnife.bind(this);
        startBase(toolbar);
        initToolbar(CommunityMaterial.Icon.cmd_arrow_left);
        init();
        id_location = getIntent().getStringExtra("uid_location");
        setView();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng ubication = new LatLng(10.5, 66.91);
        googleMap.addMarker(new MarkerOptions().position(ubication).title("Marker Location"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ubication, 15));
    }

    private void init() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void setView() {
        LocationResource locationResource = new LocationResource();
        locationResource.get(id_location, new Callback<Location>() {
            @Override
            public void onResponse(Call<Location> call, Response<Location> response) {
                setData(response.body());
                setClick(response.body());
            }

            @Override
            public void onFailure(Call<Location> call, Throwable t) {

            }
        });

    }

    private void setData(Location location) {
        text_name.setText(location.getName());
        text_web.setText(location.getHome_page());
        text_address.setText(location.getCountry() + ", " +
                location.getState() + " " + location.getCity() + ".");
        text_phone.setText(location.getTelephone());
    }

    private void setClick(final Location location) {
        linear_web.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(location.getHome_page()));
                startActivity(intent);
            }
        });
        linear_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startAnimation(linear_map);
                /*String map = "http://maps.google.com/maps?daddr=" +
                        location.getCity() + "+" +
                        location.getState() + "+" +
                        location.getCountry();
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(map));
                startActivity(intent);*/
            }
        });
        linear_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + location.getTelephone()));
                startActivity(intent);
            }
        });
    }


    private void startAnimation(View view) {
        if (view.getHeight() == 0) {
            animation(view, 0, 500).start();
        } else {
            animation(view, 500, 0).start();
        }
    }

    private AnimatorSet animation(final View view, int heightIni, int heightNew) {
        ValueAnimator slideAnimator = ValueAnimator
                .ofInt(heightIni, heightNew)
                .setDuration(600);
        slideAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Integer value = (Integer) animation.getAnimatedValue();
                view.getLayoutParams().height = value.intValue();
                view.requestLayout();
            }
        });
        AnimatorSet set = new AnimatorSet();
        set.play(slideAnimator);
        set.setInterpolator(new AccelerateDecelerateInterpolator());
        return set;
    }

}
