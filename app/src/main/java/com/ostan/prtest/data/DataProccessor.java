package com.ostan.prtest.data;

import android.graphics.drawable.Drawable;

import com.ostan.prtest.RequestTasks.ImageDownloadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by marco on 23/01/2016.
 */
public class DataProccessor {
    private static final String DATA_KEY = "data";
    private static final String ASSESTS_KEY = "assets";
    private static final String PREVIEW_KEY = "preview";
    private static final String CATEGORIES_KEY = "categories";
    private static final String ASPECT_KEY = "aspect";
    private static final String IMAGE_ID_KEY = "id";


    static List<ImageDataObject> dataList = null;

    public static void importReceivedData(List<Map> src, Notifyable notifyable) {
        dataList = proccessImport(src);
        downLoadImages(notifyable);

    }

    private static List<ImageDataObject> proccessImport(List<Map> src) {
        List<ImageDataObject> result = new ArrayList<>();
        for (Map iterator : src) {
            result.add(new ImageDataObject(getImageId(iterator),
                    getImageAspect(iterator),
                    getPreviewImageView(iterator),
                    getImageCategories(iterator)));
        }
        return result;
    }

    private static void downLoadImages(Notifyable notifyable){
        for(ImageDataObject iterator : dataList){
            new ImageDownloadTask(iterator, notifyable).execute();
        }
    }


    private static List<ImageDataObject.ImageCategory> getImageCategories(Map imageDataMapObject){
        ArrayList<HashMap<String, String>> src =  (ArrayList<HashMap<String, String>>) imageDataMapObject.get(CATEGORIES_KEY);
        List<ImageDataObject.ImageCategory> result = new ArrayList<>();
        for(HashMap iterator : src) {
            result.add(ImageDataObject.ImageCategory.createInstance(iterator));
        }
        return result;
    }

    private static ImageDataObject.SingleImageItem getPreviewImageView(Map imageDataMapObject){
        Map assets = (HashMap<String, HashMap>) imageDataMapObject.get(ASSESTS_KEY);
        Map previewAsset = (Map)assets.get(PREVIEW_KEY);
        return ImageDataObject.SingleImageItem.getInstanceFromJSON(previewAsset);

    }

    private static double getImageAspect (Map imageDataMapObject){
        return (Double)imageDataMapObject.get(ASPECT_KEY);
    }

    private static String getImageId (Map imageDataMapObject){
        return (String)imageDataMapObject.get(IMAGE_ID_KEY);
    }

    public interface Presentable{
        public Drawable getImageDrawable();
        public void setImageDrawable(Drawable src);
        public String getImageUrl();
        public List<ImageDataObject.ImageCategory> getCategories();
    }

    public interface Notifyable {
        public void newPresentableArrived(Presentable newbie);
        public void newSearchStarted();
    }

}
