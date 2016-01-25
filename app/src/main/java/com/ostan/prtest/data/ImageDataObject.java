package com.ostan.prtest.data;

import android.graphics.drawable.Drawable;
import android.util.Log;

import java.util.List;
import java.util.Map;

/**
 * Created by marco on 23/01/2016.
 */
public class ImageDataObject implements DataProccessor.Presentable {
    String id = "";
    double aspect = 1;
    SingleImageItem previewImage = null;
    List<ImageCategory> categories = null;
    Drawable imageDrawable = null;


    public ImageDataObject(String id, double aspect, SingleImageItem previewImage, List<ImageCategory> categories) {
        this.id = id;
        this.aspect = aspect;
        this.previewImage = previewImage;
        this.categories = categories;
    }

    @Override
    public Drawable getImageDrawable() {
        return imageDrawable;
    }

    @Override
    public void setImageDrawable(Drawable src) {
        imageDrawable = src;
    }

    @Override
    public String getImageUrl() {
        return previewImage.imageUrl;
    }

    @Override
    public List<ImageCategory> getCategories() {
        return categories;
    }


    public static class ImageCategory {
        String id;
        String name;
        private static final String KEY_ID = "id";
        private static final String KEY_NAME = "name";

        public static ImageCategory createInstance(Map<String, String> src) {
            return new ImageCategory(src.get(KEY_ID), src.get(KEY_NAME));
        }

        private ImageCategory(String id, String name) {
            this.id = id;
            this.name = name;
            Log.i("LOG","CATEGORY: "+name + " ID: "+id);
        }

        public String getName() {
            return name;
        }
    }

    public static class SingleImageItem {
        int height = -1;
        int width = -1;
        String imageUrl = "";

        private final static String HEIGHT_KEY = "height";
        private final static String WIDTH_KEY = "width";
        private final static String URL_KEY = "url";

        private SingleImageItem(int height, int width, String imageUrl) {
            this.height = height;
            this.width = width;
            this.imageUrl = imageUrl;
        }

        public static SingleImageItem getInstanceFromJSON(Map src){
            SingleImageItem res = new SingleImageItem((int)src.get(HEIGHT_KEY),(int)src.get(WIDTH_KEY),(String)src.get(URL_KEY));
            return res;
        }
    }



}
