package ru.xpendence.development.gimstopwatch.util;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.DisplayMetrics;

import ru.xpendence.development.gimstopwatch.R;

/**
 * Created by promoscow on 13.05.17.
 * All mathematical calculations are here.
 */

public class MathHelper {
    public static boolean isFirst = true;
    private static int[] colors = {
            0xFF455A64, 0xFFF24AB9, 0xFF4E5CD1, 0xFFF6DE42, 0xFF973F8B, 0xFF97E17F, 0xFFF25750, 0xFF457333
    };

    private static int i = 1;
    public static boolean isStoped = true;
    public static int bgColor = 0xffd8e2e7;

    public static int getI() {
        return i;
    }

    /**
     * Defines all parameters of device.
     * @param activity - display of device with all its parameters.
     * @return array with main parameters of display —
     * density, width & height in pixels, width & height in dp.
     */
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

    public static int getAbsoluteSize(int fragmentHeight, int fragmentWidth) {
        return fragmentHeight > fragmentWidth
                ? fragmentWidth : fragmentHeight;
    }

    public static int getIndent(int height) {
        return (int) (height * 0.2);
    }

    public static Bitmap getBitmap(Bitmap playButton, Bitmap pauseButton) {
        return isStoped ? playButton : pauseButton;
    }
}
