package com.optimussoftware.boohos.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import com.optimussoftware.boohos.R;

/**
 * Created by guerra on 01/09/16.
 */
public class CustomFontUtils {

//    public static final String ANDROID_SCHEMA = "http://schemas.android.com/apk/res/android";

    public static void applyCustomFont(TextView customFontTextView, Context context, AttributeSet attrs) {
        TypedArray attributeArray = context.obtainStyledAttributes(
                attrs,
                R.styleable.OptimusTextView);

        int textStyle = attributeArray.getInt(R.styleable.OptimusTextView_textStyle, 2);

        Typeface customFont = selectTypeface(context, textStyle);
        customFontTextView.setTypeface(customFont);

        attributeArray.recycle();
    }

    private static Typeface selectTypeface(Context context, int textStyle) {
        switch (textStyle) {

            case 0: // font_style_thin
                return FontCache.getTypeface(context, context.getString(R.string.font_path_thin));

             case 1: // font_style_light
             return FontCache.getTypeface(context, context.getString(R.string.font_path_light));

            /** font default
            case 2: // font_style_regular
                return FontCache.getTypeface(context, context.getString(R.string.font_path_regular));*/

            case 3: // font_style_medium
                return FontCache.getTypeface(context, context.getString(R.string.font_path_medium));

            case 4: // font_style_bold
                return FontCache.getTypeface(context, context.getString(R.string.font_path_bold));

            case 5: // font_style_black
                return FontCache.getTypeface(context, context.getString(R.string.font_path_black));

            case 6: // font_style_thin_italic
                return FontCache.getTypeface(context, context.getString(R.string.font_path_thin_italic));

            case 7: // font_style_light_italic
                return FontCache.getTypeface(context, context.getString(R.string.font_path_light_italic));

            case 8: // font_style_regular_italic
                return FontCache.getTypeface(context, context.getString(R.string.font_path_regular_italic));

            case 9: // font_style_medium_italic
                return FontCache.getTypeface(context, context.getString(R.string.font_path_medium_italic));

            case 10: // font_style_bold_italic
                return FontCache.getTypeface(context, context.getString(R.string.font_path_bold_italic));

            case 11: // font_style_black_italic
                return FontCache.getTypeface(context, context.getString(R.string.font_path_black_italic));

            case 2: // font_style_light
            default:
                return FontCache.getTypeface(context, context.getString(R.string.font_path_regular));
        }
    }
}