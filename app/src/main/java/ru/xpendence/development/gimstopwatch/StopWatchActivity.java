package ru.xpendence.development.gimstopwatch;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import ru.xpendence.development.gimstopwatch.fragments.FillDayRate;
import ru.xpendence.development.gimstopwatch.fragments.Timer;

public class StopWatchActivity extends AppCompatActivity {

    /**
     * Static sizes of display.
     */
    public static int resolutionX;
    public static int resolutionY;
    public static float dpHeight;
    public static float dpWidth;

    /**
     * All layouts in this activity.
     */
    LinearLayout mainLinearContainer;
    ScrollView stopWatchScroll;
    LinearLayout mainLinearLayout;
    HorizontalScrollView horizontalMenu;
    LinearLayout horizontalMenuLayout;
    LinearLayout timerMainFrame;
    ConstraintLayout upArrowLayout;
    ConstraintLayout mainFragmentLayout;
    ScrollView fragmentBelowMain;
    LinearLayout fragmentBelowNameLinearLayout;

    /**
     * All fragments on this activity & its utils.
     */
    Timer timer;
    FillDayRate fillDayRate;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stop_watch_scroll_layout);

        final float scale = getResources().getDisplayMetrics().density;

        getResolutions();
        defineDensity();

        timer = new Timer();
        fillDayRate = new FillDayRate();

        mainLinearContainer = (LinearLayout) findViewById(R.id.main_linear_container);
        stopWatchScroll = (ScrollView) findViewById(R.id.stop_watch_scroll);
        mainLinearLayout = (LinearLayout) findViewById(R.id.main_linear_layout);
        horizontalMenu = (HorizontalScrollView) findViewById(R.id.horizontal_menu);
        horizontalMenuLayout = (LinearLayout) findViewById(R.id.horizontal_menu_layout);
        timerMainFrame = (LinearLayout) findViewById(R.id.timer_main_frame);
        upArrowLayout = (ConstraintLayout) findViewById(R.id.up_arrow_layout);
        mainFragmentLayout = (ConstraintLayout) findViewById(R.id.main_fragment_layout);
        fragmentBelowNameLinearLayout = (LinearLayout) findViewById(R.id.fragment_below_main_linearlayout);

        Log.d("mainLinearContainer1", String.valueOf(mainLinearContainer.getLayoutParams().height));

        mainLinearContainer.setLayoutParams(new FrameLayout.LayoutParams(resolutionX, resolutionY + (int) (101 * scale)));
        stopWatchScroll.setLayoutParams(new LinearLayout.LayoutParams(resolutionX, resolutionY + (int) (101 * scale)));
        mainLinearLayout.setLayoutParams(new FrameLayout.LayoutParams(resolutionX, resolutionY + (int) (101 * scale)));
        horizontalMenu.setLayoutParams(new LinearLayout.LayoutParams(resolutionX, (int) (101 * scale)));
        timerMainFrame.setLayoutParams(new LinearLayout.LayoutParams(resolutionX, (int) (resolutionY * 0.6)));
        fragmentBelowNameLinearLayout.setLayoutParams(new LinearLayout.LayoutParams(resolutionX, (int) (resolutionY * 0.4)));

        Log.d("mainLinearContainer", String.valueOf(mainLinearContainer.getLayoutParams().height));

        Log.d("mainLinearContainer", String.format("%d, %d", mainLinearContainer.getWidth(), mainLinearContainer.getHeight()));
        Log.d("stopWatchScroll", String.format("%d, %d", stopWatchScroll.getWidth(), stopWatchScroll.getHeight()));
        Log.d("mainLinearLayout", String.format("%d, %d", mainLinearLayout.getWidth(), mainLinearLayout.getHeight()));
        Log.d("horizontalMenu", String.format("%d, %d", horizontalMenu.getWidth(), horizontalMenu.getHeight()));
        Log.d("timerMainFrame", String.format("%d, %d", timerMainFrame.getWidth(), timerMainFrame.getHeight()));
        Log.d("fragmentBelowNameLL", String.format("%d, %d", fragmentBelowNameLinearLayout.getWidth(), fragmentBelowNameLinearLayout.getHeight()));

        Log.d("calcDP", String.valueOf(calcDP(100)));

//        FragmentTransaction fragmentTransaction1;
//        fragmentTransaction1 = getFragmentManager().beginTransaction();
//        fragmentTransaction1.replace(R.id.fragment_below_main, fillDayRate);
//        fragmentTransaction1.commit();

//        mainLinearContainer.getLayoutParams().height = 100;
//        Log.d("mainLinearContainer", String.valueOf(mainLinearContainer.getLayoutParams().height));

        stopWatchScroll.post(new Runnable() {
            public void run() {
                stopWatchScroll.scrollBy(0, (int) calcDP(101));
            }
        });
    }

    private void getResolutions() {
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);

        resolutionX = metrics.widthPixels;
        resolutionY = metrics.heightPixels;

        Log.d("resolutionX", String.valueOf(resolutionX));
        Log.d("resolutionY", String.valueOf(resolutionY));
    }

    public float calcDP(int i) {
        Resources r = getResources();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, i, r.getDisplayMetrics());
    }

    public void defineDensity() {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        dpHeight = displayMetrics.heightPixels / displayMetrics.density;
        dpWidth = displayMetrics.widthPixels / displayMetrics.density;
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
            case R.id.timer:
                Log.d("onClick", "timer");
                fragmentTransaction.replace(R.id.main_fragment_view, timer);
                break;
            case R.id.fill_day_rate:
                Log.d("onClick", "fillDayRate");
                fragmentTransaction.replace(R.id.main_fragment_view, fillDayRate);
                break;
        }
        fragmentTransaction.addToBackStack("1");
        fragmentTransaction.commit();
        Log.d("onClick", "commited");
    }
}
