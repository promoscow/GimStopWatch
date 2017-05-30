package ru.xpendence.development.gimstopwatch.util;

import android.content.Context;
import android.graphics.Typeface;

/**
 * Created by promoscow on 30.05.17.
 */

public class CommonSettings {
    public static Typeface robotoCondLight;

    public static Typeface getRobotoCondLight() {
        return robotoCondLight;
    }

    public static void setRobotoCondLight(Typeface robotoCondLight) {
        CommonSettings.robotoCondLight = robotoCondLight;
    }

    private static volatile CommonSettings instance;

    public CommonSettings() {
    }

    public static CommonSettings getInstance(Context activity) {
        CommonSettings localInstance = instance;
        if (localInstance == null) {
            synchronized (CommonSettings.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new CommonSettings();
                }
            }
            setRobotoCondLight(Typeface.createFromAsset(activity.getAssets(), "fonts/roboto_cl.ttf"));
        }
        return localInstance;
    }
}
