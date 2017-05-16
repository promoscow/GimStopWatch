package ru.xpendence.development.gimstopwatch;

import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.widget.ScrollView;
import android.widget.Toast;

public class StopWatchActivity extends AppCompatActivity {
    ScrollView myScroll;
    public static int resolutionX;
    public static int resolutionY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stop_watch_scroll_layout);

        getResolutions();
        Log.d("calcDP", String.valueOf(calcDP(100)));
        defineDensity();

        View view = findViewById(R.id.main_fragment_view);

        myScroll = (ScrollView) findViewById(R.id.stop_watch_scroll);
        myScroll.post(new Runnable(){
            public void run(){
                myScroll.scrollBy(0, (int) calcDP(101));
            }
        });
    }

    private int pixelsY(int i) {
        Log.d("pixelsY", String.valueOf(resolutionY / 320 * i));
        return resolutionY / 320 * i;
    }

    private int pixelsX(int i) {
        return resolutionX / 320 * i;
    }

    private void getResolutions() {
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);

        resolutionX = metrics.widthPixels;
        resolutionY = metrics.heightPixels;

        Log.d("resX", String.valueOf(resolutionX));
        Log.d("resY", String.valueOf(resolutionY));
    }

    public float calcDP(int i) {
        Resources r = getResources();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, i, r.getDisplayMetrics());
    }

    public void defineDensity() {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        float dpHeight = displayMetrics.heightPixels / displayMetrics.density;
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        Log.d("density", String.valueOf(displayMetrics.density));
        Log.d("heightPixels", String.valueOf(displayMetrics.heightPixels));
        Log.d("widthPixels", String.valueOf(displayMetrics.widthPixels));
        Log.d("dpHeight", String.valueOf(dpHeight));
        Log.d("dpWidth", String.valueOf(dpWidth));
    }
}
