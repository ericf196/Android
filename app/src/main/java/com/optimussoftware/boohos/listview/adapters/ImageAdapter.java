package com.optimussoftware.boohos.listview.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.optimussoftware.boohos.R;
import com.optimussoftware.boohos.listview.holders.ImageViewHolder;

import java.util.ArrayList;

/**
 * Created by guerra on 13/07/16.
 */
public class ImageAdapter extends RecyclerView.Adapter<ImageViewHolder>
        implements View.OnClickListener {

    private View.OnClickListener listener;
    private ArrayList<String> arrayImages;

    public ImageAdapter(ArrayList<String> arrayImages) {
        this.arrayImages = arrayImages;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.image_item, parent, false);
        v.setOnClickListener(this);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        holder.setImage(arrayImages.get(position));
    }

    @Override
    public int getItemCount() {
        return arrayImages.size();
    }

    public void addArrayImage(ArrayList<String> arrayNew) {
        arrayImages.addAll(arrayNew);
        notifyDataSetChanged();
    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        if (listener != null)
            listener.onClick(view);
    }

}
