package com.optimussoftware.boohos.widget;

import android.content.Context;
import android.graphics.Typeface;

import java.util.HashMap;

/**
 * Created by guerra on 01/09/16.
 */
public class FontCache{
    private static HashMap<String, Typeface> fontCache = new HashMap<>();

    public static Typeface getTypeface(Context context, String font_path) {
        Typeface typeface = fontCache.get(font_path);

        if (typeface == null) {
            try {
                typeface = Typeface.createFromAsset(context.getAssets(), font_path);
            } catch (Exception e) {
                return null;
            }

            fontCache.put(font_path, typeface);
        }

        return typeface;
    }
}