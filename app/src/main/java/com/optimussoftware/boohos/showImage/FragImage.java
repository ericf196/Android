package com.optimussoftware.boohos.showImage;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.alexvasilkov.gestures.views.GestureFrameLayout;
import com.optimussoftware.boohos.R;
import com.optimussoftware.boohos.util.Commons;
import com.optimussoftware.boohos.widget.OptimusTextView;
import com.optimussoftware.image.SizeImage;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by guerra on 14/07/16.
 * Este fragment muestra una imagen con su nombre y descripcion
 */

public class FragImage extends Fragment{

    @BindView(R.id.gestures_view) GestureFrameLayout gestures_view;
    @BindView(R.id.image) ImageView image;
    @BindView(R.id.text_title) OptimusTextView text_title;
    @BindView(R.id.text_description) OptimusTextView text_description;

    private String name;
    private String description;
    private String urlImage;

    public static FragImage newInstance(String name, String description, String urlImage) {
        FragImage fragImage = new FragImage();
        Bundle bundle = new Bundle();
        bundle.putString("name", name);
        bundle.putString("description", description);
        bundle.putString("urlImage", urlImage);
        fragImage.setArguments(bundle);
        fragImage.setRetainInstance(true);
        return fragImage;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.name = getArguments().getString("name");
            this.description = getArguments().getString("description");
            this.urlImage = getArguments().getString("urlImage");
        } else {
            Log.e("lary", "getArguments()==null");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_image, container, false);
        ButterKnife.bind(this, view);
        text_title.setText(name);
        text_description.setText(description);
        SizeImage sizeImage = new SizeImage(Commons.getWidthDisplay(getContext()),
                Commons.getHeightDisplay(getContext()));
        //Factory.setImage(image, getContext(), urlImage, sizeImage);


        Picasso.with(getContext())
                .load(urlImage)
                .networkPolicy(NetworkPolicy.OFFLINE)
                .resize(sizeImage.width, sizeImage.height)
                .centerInside()
                .into(image);

        return view;
    }


}
