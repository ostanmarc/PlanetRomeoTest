package com.ostan.prtest.utils;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by marco on 06/01/2016.
 */
public class SystemLogger {

    private static final String LOG_PREFIX = "SYS_LOG_";

    private static String getCallerClassName(){
        StackTraceElement[] stElements = new Exception().getStackTrace();
        return stElements[2].getClassName();
    }

    public static void debug(String src){
        if(isSubscribed(getCallerClassName())) {
            Log.i(LOG_PREFIX+getCallerClassName(), src);
        }
        else {
            Log.i(LOG_PREFIX, "NON SUBSCRIBED LOG ATTEMPT: " + getCallerClassName());
        }

    }

    private static List<String> loggedClasses = new ArrayList<>();
    public static void subscribeForLog(String cls) {
        if(!loggedClasses.contains(cls)) {

            loggedClasses.add(cls);
        }

    }
    public static void unsubscribeForLog(String cls) {
        loggedClasses.remove(cls);
    }

    private static boolean isSubscribed(String cls){
        for(String iterator: loggedClasses) {
            if (cls.contains(iterator)) {
                return true;
            }
        }
        return false;
//        return loggedClasses.contains(cls);
    }

}
