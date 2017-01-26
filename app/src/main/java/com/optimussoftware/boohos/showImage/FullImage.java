package com.optimussoftware.boohos.showImage;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import com.optimussoftware.boohos.R;
import com.optimussoftware.boohos.viewDetail.FragPagerAdapter;
import com.optimussoftware.boohos.widget.BaseActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by guerra on 13/07/16.
 * Este Activity recibe:
 * 1. ArrayList<ImageDescription> en el intent
 * asi --> intent.putExtra("imageArray", tuArrayImageDescription);
 * 2. la posicion donde se desea ubicar el viewPager
 */
public class FullImage extends BaseActivity {

    @BindView(R.id.view_pager_full)
    ViewPager view_pager_full;

    private int position;
    private ArrayList<ImageDescription> imageArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.full_image);
        ButterKnife.bind(this);
        if (getIntent().getExtras() != null) {
            imageArray = (ArrayList<ImageDescription>)
                    getIntent().getExtras().getSerializable("imageArray");
            position = getIntent().getIntExtra("position", 0);
            viewPager();
        }
    }

    private void viewPager() {
        FragPagerAdapter pagerAdapter = new FragPagerAdapter(getSupportFragmentManager());
        if (imageArray.size() > 1) {
            for (ImageDescription image : imageArray) {
                Log.d("lary", image.getName() + " " +
                        image.getDescription() + " " +
                        image.getUrlImage());
                FragImage fragImage = FragImage.newInstance(image.getName(),
                        image.getDescription(), image.getUrlImage());
                pagerAdapter.addFragment(fragImage);
            }
            view_pager_full.setAdapter(pagerAdapter);
            view_pager_full.setCurrentItem(position);
        }
    }

}
