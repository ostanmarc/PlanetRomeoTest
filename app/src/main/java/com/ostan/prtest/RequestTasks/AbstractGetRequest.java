package com.ostan.prtest.RequestTasks;

import android.os.AsyncTask;
import android.util.Log;

import com.ostan.prtest.utils.CommonUtils;
import com.ostan.prtest.utils.Constants;
import com.ostan.prtest.utils.SystemLogger;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public abstract class AbstractGetRequest extends AsyncTask<String, Void, JSONObject> {

    String methodName = "";
    String querry = "";
    boolean isGetMethod = true;
    RequestListener requestListener = null;

    public AbstractGetRequest(String methodName, boolean isGetMethod, String querry, RequestListener listener) {
        this.methodName = methodName;
        this.isGetMethod = isGetMethod;
        this.requestListener = listener;
        this.querry = querry;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        this.mException = null;
    }

    @Override
    protected JSONObject doInBackground(String... params) {

        return (executeRequest(Constants.BASE_URL + methodName + getQuerry(querry)));
    }

    @Override
    protected void onPostExecute(JSONObject result) {
        super.onPostExecute(result);


        if (this.mException != null) {
            this.mException.printStackTrace();
        }

        requestListener.onSuccess(result);
        Log.i("DATA RECEIVED", result.toString());

        SystemLogger.debug("DONE " + result);

    }

    Exception mException = null;

    private JSONObject executeRequest(String urlString) {
        SystemLogger.subscribeForLog(AbstractGetRequest.class.getName());
        HttpURLConnection urlConnection = null;
        URL url = null;
        JSONObject object = null;
        InputStream inStream = null;
        try {
            url = new URL(urlString.toString());

            urlConnection = (HttpURLConnection) url.openConnection();
            String userCredentials = Constants.CLIENT_ID + ":" + Constants.CLIENT_SECRET;
            String basicAuth = "Basic " + new String(CommonUtils.getBase64(userCredentials));
            urlConnection.setRequestProperty("Authorization", basicAuth);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if (urlConnection.getResponseCode() != 200) {
                try {
                    SystemLogger.debug("REQUEST FAILED: " + ((JSONObject) (urlConnection.getContent())).toString());
                    requestListener.onRequestFailure();
                } catch (IOException e) {
                }

            }

            inStream = urlConnection.getInputStream();
            BufferedReader bReader = new BufferedReader(new InputStreamReader(inStream));
            String temp, response = "";
            while ((temp = bReader.readLine()) != null) {
                response += temp;
            }
            object = (JSONObject) new JSONTokener(response).nextValue();
        } catch (Exception e) {
            this.mException = e;
        } finally {
            if (inStream != null) {
                try {
                    inStream.close();
                } catch (IOException ignored) {
                }
            }
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return object;
    }

    protected abstract String getQuerry(String params);

    public interface RequestListener {
        public void onRequestFailure();
        public void onSuccess(JSONObject result);

    }

}
