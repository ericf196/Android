package com.optimussoftware.boohos.share;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;

import com.optimussoftware.boohos.util.SaveFile;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;

/**
 * Created by guerra on 21/06/16.
 * Save
 */
public class Save {

    private String TAG = "Save";

    private Context context;
    private OnSaveMedia onSaveMedia;

    public Save(Context context) {
        this.context = context;
    }

    public void saveImageFromUrl(String urlImage) {
        dowloadImage(urlImage);
    }

    public void saveImageFromView(View view) {
        getBitmapFromView(view);
    }

    private void getBitmapFromView(View view) {
        Log.d(TAG, "onPrepareLoad");
        if (onSaveMedia != null) {
            onSaveMedia.onSaveStart();
        }
        try {
            //Define a bitmap with the same size as the view
            Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
            //Bind a canvas to it
            Canvas canvas = new Canvas(returnedBitmap);
            //Get the view's background
            Drawable bgDrawable = view.getBackground();
            if (bgDrawable != null)
                //has background drawable, then draw it on the canvas
                bgDrawable.draw(canvas);
            else
                //does not have background drawable, then draw white background on the canvas
                canvas.drawColor(Color.WHITE);
            // draw the view on the canvas
            view.draw(canvas);
            //return the bitmap
            if (onSaveMedia != null) {
                File file = SaveFile.saveImage(returnedBitmap);
                if(file!=null){
                    onSaveMedia.onSaveEnd(file);
                }else {
                    onSaveMedia.onSaveError();
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "onBitmapFailed");
            if (onSaveMedia != null) {
                onSaveMedia.onSaveError();
            }
        }


    }

    private void dowloadImage(String url) {
        Picasso.with(context)
                .load(url)
                .into(new Target() {
                    @Override
                    public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
                        Log.d(TAG, "onBitmapLoaded");
                        if (onSaveMedia != null) {
                            onSaveMedia.onSaveEnd(SaveFile.saveImage(bitmap));
                        }
                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {
                        Log.d(TAG, "onPrepareLoad");
                        if (onSaveMedia != null) {
                            onSaveMedia.onSaveStart();
                        }
                    }

                    @Override
                    public void onBitmapFailed(Drawable errorDrawable) {
                        Log.e(TAG, "onBitmapFailed");
                        if (onSaveMedia != null) {
                            onSaveMedia.onSaveError();
                        }
                    }
                });
    }


    /**
     * Interface
     **/
    public void setOnSaveMedia(OnSaveMedia onSaveMedia) {
        this.onSaveMedia = onSaveMedia;
    }

    public interface OnSaveMedia {

        void onSaveStart();

        void onSaveEnd(File file);

        void onSaveError();

    }

    /*private File saveImageFromUrl(String urlImage) {
        String nameFolder = "/Android/data/com.herme-app.images/";
        String nameFile = "imagen_";
        Bitmap bitmap = urlImageToBitmap(urlImage);
        File sdCardDirectory = Environment.getExternalStorageDirectory();
        File dir = new File(sdCardDirectory.getAbsolutePath() + nameFolder);
        dir.mkdirs();
        String nameFileNew = nameFile + getCurrentDateAndTime() + ".jpg";
        File image = new File(dir, nameFileNew);
        FileOutputStream outStream;
        try {
            outStream = new FileOutputStream(image);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
            outStream.flush();
            outStream.close();
        } catch (IOException e) {
            Log.e(TAG, "IOException 1 " + e);
            e.printStackTrace();
        }
        File image2 = new File(sdCardDirectory.getAbsolutePath() + nameFolder + nameFileNew);
        Log.d(TAG, "Tamaño archivo " + image2.length());
        return image2;
    }


    private Bitmap urlImageToBitmap(String urlImage) {
        Bitmap mIcon1 = null;
        URL url_value = null;
        try {
            url_value = new URL(urlImage);
        } catch (MalformedURLException e) {
            Log.e(TAG, "MalformedURLException " + e);
        }
        if (url_value != null) {
            try {
                mIcon1 = BitmapFactory.decodeStream(url_value.openConnection().getInputStream());
            } catch (IOException e) {
                Log.e(TAG, "IOException 2 " + e);
            }
        }
        return mIcon1;
    }*/

}