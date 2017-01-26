package com.optimussoftware.boohos.listview;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import com.optimussoftware.boohos.R;

/**
 * Created by guerra on 19/07/16.
 * footer to list and grid
 */
public class Footer {

    private Context context;
    private View view;
    LinearLayout linear_no_more;
    LinearLayout linear_loading;

    public Footer(Context context) {
        this.context = context;
        init();
    }

    private void init() {
        view = View.inflate(context, R.layout.footer_item, null);
        linear_no_more = (LinearLayout) view.findViewById(R.id.linear_no_more);
        linear_loading = (LinearLayout) view.findViewById(R.id.linear_loading);
    }

    public View getView() {
        return view;
    }

    public void setStatusFooter(boolean lodaing) {
        if (lodaing) {
            linear_no_more.setVisibility(View.GONE);
            linear_loading.setVisibility(View.VISIBLE);
        } else {
            linear_no_more.setVisibility(View.VISIBLE);
            linear_loading.setVisibility(View.GONE);
        }
    }
}
