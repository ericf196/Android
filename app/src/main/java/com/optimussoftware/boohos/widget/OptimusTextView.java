package com.optimussoftware.boohos.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Widget for all TextView's on hermeApp
 * Created by william.castillo@optimus-software.com on 18/06/16.
 */
public class OptimusTextView extends TextView {

    public OptimusTextView(Context context) {
        super(context);
        CustomFontUtils.applyCustomFont(this, context, null);
    }

    public OptimusTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        CustomFontUtils.applyCustomFont(this, context, attrs);
    }

    public OptimusTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        CustomFontUtils.applyCustomFont(this, context, attrs);
    }

}
