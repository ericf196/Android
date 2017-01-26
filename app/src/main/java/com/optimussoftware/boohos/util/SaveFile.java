package com.optimussoftware.boohos.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

import com.optimussoftware.boohos.data.Constants;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by guerra on 09/12/16.
 * SaveFile
 */

public class SaveFile {

    private static String TAG = "SaveFile";
    private static final String TYPE_JPG = ".jpg";

    private Context context;

    public SaveFile(Context context) {
        this.context = context;
    }

    public static File saveImage(Bitmap bitmap) {
        String folderName = Constants.DIRECTORY_BASE + Constants.DIRECTORY_MEDIA_IMAGES + "/";
        String fileName = Constants.IMAGE_NAME_BASE + Commons.getCurrentDateAndTime() + TYPE_JPG;

        File path = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + folderName);
        File image = new File(path, fileName);

        try {
            if (!path.exists()) {
                path.mkdirs();
            }
            FileOutputStream outStream = new FileOutputStream(image);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
            outStream.flush();
            outStream.close();
            return image;
        } catch (IOException e) {
            Log.e(TAG, "IOException 1 " + e);
            e.printStackTrace();
            return null;
        }
    }

}
