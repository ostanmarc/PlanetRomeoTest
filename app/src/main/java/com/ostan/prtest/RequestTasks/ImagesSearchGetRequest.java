package com.ostan.prtest.RequestTasks;

import com.ostan.prtest.utils.CommonUtils;

/**
 * Created by marco on 23/01/2016.
 */
public class ImagesSearchGetRequest extends AbstractGetRequest {
    public ImagesSearchGetRequest(String methodName, boolean isGetMethod, String querry, RequestListener listener) {
        super(methodName, isGetMethod, querry, listener);
    }

    @Override
    protected String getQuerry(String querry) {
        return "?query="+CommonUtils.urlEncode(querry)+"&safe=true&image_type=photo&orientation=horizontal&page=1&per_page=10&view=full";
    }
}
