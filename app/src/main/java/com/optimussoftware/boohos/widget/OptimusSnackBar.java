package com.optimussoftware.boohos.widget;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.androidadvance.topsnackbar.TSnackbar;
import com.optimussoftware.boohos.R;

/**
 * Created by guerra on 14/09/16.
 * OptimusSnackBar
 */
public class OptimusSnackBar {

    public static int LENGTH_SHORT = TSnackbar.LENGTH_SHORT;
    public static int LENGTH_LONG = TSnackbar.LENGTH_LONG;
    public static int LENGTH_INDEFINITE = TSnackbar.LENGTH_INDEFINITE;

    public static TSnackbar snackBarTop(Context c, View v, String mjs, int duration) {
        return snackBarTop(c, v, mjs, duration, false);
    }

    public static TSnackbar snackBarTop(Context c, View v, String mjs, int duration, boolean alert) {
        TSnackbar snackbar = TSnackbar.make(v, mjs, duration);
        View sbView = snackbar.getView();
        TextView text = (TextView) sbView.findViewById(com.androidadvance.topsnackbar.R.id.snackbar_text);
        Button button = (Button) sbView.findViewById(com.androidadvance.topsnackbar.R.id.snackbar_action);

        if (alert) {
            sbView.setBackgroundColor(ContextCompat.getColor(c, R.color.colorPrimaryDark));
        }
        text.setTypeface(FontCache.getTypeface(c, c.getString(R.string.font_path_medium)));
        text.setTextColor(ContextCompat.getColor(c, R.color.white));
        button.setTypeface(FontCache.getTypeface(c, c.getString(R.string.font_path_bold)));
        button.setTextColor(ContextCompat.getColor(c, R.color.white));

        return snackbar;
    }

    public static Snackbar snackBarBotton(Context c, View v, String mjs, int duration) {
        return snackBarBotton(c, v, mjs, duration, false);
    }

    public static Snackbar snackBarBotton(Context c, View v, String mjs, int duration, boolean alert) {
        Snackbar snackbar = Snackbar.make(v, mjs, duration);
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        Button button = (Button) sbView.findViewById(android.support.design.R.id.snackbar_action);

        if (alert) {
            sbView.setBackgroundColor(ContextCompat.getColor(c, R.color.colorPrimaryDark));
        }
        textView.setTypeface(FontCache.getTypeface(c, c.getString(R.string.font_path_light)));
        textView.setTextColor(ContextCompat.getColor(c, R.color.white));
        button.setTypeface(FontCache.getTypeface(c, c.getString(R.string.font_path_bold)));
        button.setTextColor(ContextCompat.getColor(c, R.color.white));
        return snackbar;
    }

}
