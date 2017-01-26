package com.odoo.widgets.slider;

import com.odoo.widgets.slider.SliderPagerAdapter.SliderBuilderListener;

import java.util.HashMap;

public class SliderItem {

    private String content = null;
    private String title = null;
    private String subtitle = null;
    private int image = 0;
    private boolean last = false;
    private SliderBuilderListener mSliderBuilderListener = null;
    private HashMap<String, Object> extras = new HashMap<>();

    public SliderItem(String title, String subtitle, String content, int image,
                      SliderBuilderListener listener) {
        super();
        this.content = content;
        this.title = title;
        this.subtitle = subtitle;
        this.image = image;
        mSliderBuilderListener = listener;
    }

    public SliderItem(int image, String title, String content, SliderBuilderListener listener) {
        super();
        this.content = content;
        this.title = title;
        this.image = image;
        mSliderBuilderListener = listener;
    }

    public SliderItem putExtra(String key, Object value) {
        extras.put(key, value);
        return this;
    }

    public SliderItem setExtras(HashMap<String, Object> extras) {
        this.extras = extras;
        return this;
    }

    public HashMap<String, Object> getExtras() {
        return extras;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String mContent) {
        this.content = mContent;
    }

    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setTitle(String mTitle) {
        this.title = mTitle;
    }

    public void setSubtitle(String mSubitle) {
        this.subtitle = mSubitle;
    }

    public int getImagePath() {
        return image;
    }

    public void setImagePath(int image) {
        this.image = image;
    }

    public void makeLast(){
        last = true;
    }

    public boolean isLast(){
        return last;
    }

    public void setSliderCustomViewListener(SliderBuilderListener listener) {
        mSliderBuilderListener = listener;
    }

    public SliderBuilderListener getSliderCustomViewListener() {
        return mSliderBuilderListener;
    }
}
