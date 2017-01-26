/**
 * Odoo, Open Source Management Solution
 * Copyright (C) 2012-today Odoo SA (<http:www.odoo.com>)
 * <p/>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version
 * <p/>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details
 * <p/>
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http:www.gnu.org/licenses/>
 * <p/>
 * Created on 13/2/15 4:16 PM
 */
package com.optimussoftware.boohos.config;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mikepenz.iconics.view.IconicsButton;
import com.optimussoftware.boohos.util.Commons;
import com.optimussoftware.boohos.util.OControls;
import com.odoo.widgets.slider.SliderItem;
import com.odoo.widgets.slider.SliderPagerAdapter;
import com.optimussoftware.boohos.R;
import com.optimussoftware.boohos.data.Constants;

import java.util.ArrayList;
import java.util.List;

public class IntroSliderItems implements SliderPagerAdapter.SliderBuilderListener {

    public static final String TAG = IntroSliderItems.class.getSimpleName();
    private Activity __parent = null;

    public IntroSliderItems(Activity __parent) {
        this.__parent = __parent;
    }

    public List<SliderItem> getItems() {
        List<SliderItem> items = new ArrayList<>();

        SliderItem item = new SliderItem(R.drawable.ic_img_slider1, __parent.getString(R.string.title_slider1), __parent.getString(R.string.content_slider1),
                this);
        items.add(item);

        item = new SliderItem(R.drawable.ic_img_slider2, __parent.getString(R.string.title_slider2), __parent.getString(R.string.content_slider2),
                this);
        items.add(item);

        item = new SliderItem(R.drawable.ic_img_slider3,__parent.getString(R.string.title_slider3), __parent.getString(R.string.content_slider3),
                this);
        items.add(item);

        item = new SliderItem(R.drawable.ic_img_slider4, __parent.getString(R.string.title_slider4), __parent.getString(R.string.content_slider4), this);
        item.makeLast();
        items.add(item);

        return items;
    }

    @Override
    public View getCustomView(final Context context, SliderItem item, final ViewGroup parent) {
        View view;
        if (!item.isLast())
            view = LayoutInflater.from(context).inflate(R.layout.base_intro_slider_view, parent, false);
        else {
            view = LayoutInflater.from(context).inflate(R.layout.last_intro_slider_view, parent, false);
            IconicsButton endButton = (IconicsButton) view.findViewById(R.id.finishSlider);
            Commons.setTypeFace(endButton);
            endButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    __parent.setResult(Constants.CODE_START_LOGIN);
                    __parent.finish();
                }
            });
        }
        OControls.setImage(view, R.id.slider_image, item.getImagePath());
        OControls.setText(view, R.id.big_title, item.getTitle());
        OControls.setText(view, R.id.sub_title, item.getSubtitle());
        OControls.setText(view, R.id.description, item.getContent());
        return view;
    }
}
