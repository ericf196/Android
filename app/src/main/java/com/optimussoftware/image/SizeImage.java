package com.optimussoftware.image;

import android.content.Context;

import com.optimussoftware.boohos.util.Commons;

/**
 * Created by william.castillo@optimus-software.com on 25/06/16.
 */
public class SizeImage {

   public final int width;
   public final int height;

   public SizeImage(int width, int height){
      this.width = width;
      this.height = height;
   }

   public SizeImage(Context context, int width, int height){
      this.width = Commons.dpToPx(context, width);
      this.height = Commons.dpToPx(context, height);
   }
}
