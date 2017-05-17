package ru.xpendence.development.gimstopwatch;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import ru.xpendence.development.gimstopwatch.fragments.Timer;
import ru.xpendence.development.gimstopwatch.fragments.Timer2;

public class StopWatchActivity extends AppCompatActivity {
    ScrollView myScroll;
    LinearLayout linearLayout;
    Timer timer;
    Timer2 timer2;

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    public static int resolutionX;
    public static int resolutionY;
    public static float dpHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stop_watch_scroll_layout);

        final float scale = getResources().getDisplayMetrics().density;

        getResolutions();
        Log.d("calcDP", String.valueOf(calcDP(100)));
        defineDensity();

        timer = new Timer();
        timer2 = new Timer2();



//        View view = findViewById(R.id.main_fragment_view);

        linearLayout = (LinearLayout) findViewById(R.id.main_linear_container);
        linearLayout.setLayoutParams(new FrameLayout.LayoutParams(resolutionX, resolutionY + (101 * (int) scale)));
        Log.d("linearLayoutX", String.valueOf(resolutionX));
        Log.d("linearLayoutY", String.valueOf(resolutionY + (101 * (int) scale)));
        myScroll = (ScrollView) findViewById(R.id.stop_watch_scroll);
        Log.d("myScroll", String.valueOf(resolutionY + 101 * scale));
        myScroll.post(new Runnable() {
            public void run() {
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
        dpHeight = displayMetrics.heightPixels / displayMetrics.density;
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        Log.d("density", String.valueOf(displayMetrics.density));
        Log.d("heightPixels", String.valueOf(displayMetrics.heightPixels));
        Log.d("widthPixels", String.valueOf(displayMetrics.widthPixels));
        Log.d("dpHeight", String.valueOf(dpHeight));
        Log.d("dpWidth", String.valueOf(dpWidth));
    }

    public void onClickMenu(View view) {
        fragmentManager = getFragmentManager();
        fragmentTransaction = getFragmentManager().beginTransaction();
        Log.d("onClick", String.valueOf(fragmentTransaction));

        switch (view.getId()) {
            case R.id.timer1 :
                Log.d("onClick", "timer");
                fragmentTransaction.replace(R.id.main_fragment_view, timer);
                break;
            case R.id.timer2 :
                Log.d("onClick", "timer2");
                fragmentTransaction.replace(R.id.main_fragment_view, timer2);
                break;
        }
        fragmentTransaction.addToBackStack("1");
        fragmentTransaction.commit();
        Log.d("onClick", "commited");
    }
}
