package com.example.droussos2867.gotoclass;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;

/**
 * Created by sempiternalsearch on 11/14/17.
 */
//This class just takes the profile image and creates a rounded bit image
public class ImageTask extends AsyncTask<String, Void, Bitmap> {
    private ImageView bmImage;
    private Resources res;
    private RoundedBitmapDrawable dr;
    private String urldisplay;
    private InputStream in;
    private Bitmap icon;

    public ImageTask(ImageView bmImage, Resources res) {
        this.res = res;
        this.bmImage = bmImage;
    }

    //grabs the image from url
    protected Bitmap doInBackground(String... urls) {
        urldisplay = urls[0];
        icon = null;
        try {
            in = new java.net.URL(urldisplay).openStream();
            icon = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return icon;
    }

    //Created a rounded bitmaps from image grabbed by doInBackground()
    //and set it to imageView sent into ImageTask
    protected void onPostExecute(Bitmap result) {
        dr = RoundedBitmapDrawableFactory.create(res, result);
        dr.setCornerRadius(360);
        bmImage.setImageDrawable(dr);
    }
}