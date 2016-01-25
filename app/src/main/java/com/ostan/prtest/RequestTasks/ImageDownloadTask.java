package com.ostan.prtest.RequestTasks;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;

import com.ostan.prtest.data.DataProccessor;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by marco on 24/01/2016.
 */
public class ImageDownloadTask extends AsyncTask<String, Void, Drawable> {


    DataProccessor.Presentable presentable = null;
    DataProccessor.Notifyable notifyable = null;

    public static Drawable drawableFromUrl(String url) throws IOException {
        Bitmap x;

        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.connect();
        InputStream input = connection.getInputStream();

        x = BitmapFactory.decodeStream(input);
        return new BitmapDrawable(x);
    }

    public ImageDownloadTask(DataProccessor.Presentable presentable, DataProccessor.Notifyable notifyable) {
        this.presentable = presentable;
        this.notifyable = notifyable;
    }

    @Override
    protected void onPostExecute(Drawable drawable) {
        super.onPostExecute(drawable);
        if(drawable != null){
            presentable.setImageDrawable(drawable);
            notifyable.newPresentableArrived(presentable);
        }

    }

    @Override
    protected Drawable doInBackground(String... params) {
    Drawable result  = null;
        try {
            Log.i("LOG","Downloading image for: " + presentable.getImageUrl());
            result = drawableFromUrl(presentable.getImageUrl());
        } catch (IOException e) {
            e.printStackTrace();
        }


        return result;
    }
}
