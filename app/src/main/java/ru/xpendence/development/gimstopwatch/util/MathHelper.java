package ru.xpendence.development.gimstopwatch.util;

import android.app.Activity;
import android.util.DisplayMetrics;

/**
 * Created by promoscow on 13.05.17.
 * All mathematical calculations are here.
 */

public class MathHelper {
    public static boolean isFirst = true;
    private static int[] colors = {
            0xFFFFFFFF, 0xFFF24AB9, 0xFF4E5CD1, 0xFFF6DE42, 0xFF973F8B, 0xFF97E17F, 0xFFF25750, 0xFF457333
    };

    private static int i = 1;

    public static int getI() {
        return i;
    }

    public static boolean isStoped = true;

    public static float[] defineDensity(Activity activity) {

        DisplayMetrics displayMetrics = activity.getResources().getDisplayMetrics();
        float dpHeight = displayMetrics.heightPixels / displayMetrics.density;
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        return new float[] {displayMetrics.density,
                displayMetrics.heightPixels,
                displayMetrics.widthPixels,
                dpHeight,
                dpWidth};
    }

    public static float getAngle(float angle) {
        return isStoped ? angle : ++angle;
    }

    public static int setTimelineColor(float angle) {
        if (i == colors.length) i = 1;
        return colors[i];
    }

    public static int setI() {
        isFirst = false;
        return i++;
    }

    public static int setBgI() {
        return (i == 1 && !isFirst) ? colors[colors.length - 1] : colors[i - 1];
    }
}
