package ru.xpendence.development.gimstopwatch;

import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import ru.xpendence.development.gimstopwatch.fragments.FragmentAccount;
import ru.xpendence.development.gimstopwatch.fragments.FragmentBelowAccount;
import ru.xpendence.development.gimstopwatch.fragments.FragmentBelowTimer;
import ru.xpendence.development.gimstopwatch.fragments.FragmentFillDayRate;
import ru.xpendence.development.gimstopwatch.fragments.FragmentBelowFillDayRate;
import ru.xpendence.development.gimstopwatch.fragments.FragmentBelowNutrients;
import ru.xpendence.development.gimstopwatch.fragments.FragmentNutrientsRatio;
import ru.xpendence.development.gimstopwatch.fragments.FragmentTimer;

/**
 * Main Activity for all frames.
 */

public class AppActivity extends AppCompatActivity {

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

    View accountUnderline;
    View fillUnderline;
    View nutrientsUnderline;
    View timerUnderline;
    View settingsUnderline;

    ViewGroup defaultViewGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        final float scale = getResources().getDisplayMetrics().density;

        getResolutions();
        defineDensity();

        defaultViewGroup = (FrameLayout) findViewById(R.id.main_fragment_view);

        accountUnderline = findViewById(R.id.account_underline);
        fillUnderline = findViewById(R.id.fill_day_rate_underline);
        nutrientsUnderline = findViewById(R.id.nutrients_ratio_underline);
        timerUnderline = findViewById(R.id.timer_underline);
        settingsUnderline = findViewById(R.id.settings_underline);

        accountUnderline.setAlpha(1);
        fillUnderline.setAlpha(0);
        nutrientsUnderline.setAlpha(0);
        timerUnderline.setAlpha(0);
        settingsUnderline.setAlpha(0);

        Log.d("calcDP", String.valueOf(calcDP(100)));
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

        TextView headingText = (TextView) findViewById(R.id.heading);
        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
        ViewGroup viewGroup = (FrameLayout) findViewById(R.id.main_fragment_view);

        switch (view.getId()) {
            case R.id.timer:
                Log.d("onClick", "timer");
                setUnderlineAlpha(timerUnderline);
                headingText.setText("Таймер отдыха");
                showFragmentTop(FragmentTimer.newInstance());
                showFragmentBottom(FragmentBelowTimer.newInstance());
                break;
            case R.id.fill_day_rate:
                Log.d("onClick", "fillDayRate");
                setUnderlineAlpha(fillUnderline);
                headingText.setText("Дневная норма калорий");
                showFragmentTop(FragmentFillDayRate.newInstance());
                showFragmentBottom(FragmentBelowFillDayRate.newInstance());
                break;
            case R.id.nutrients_ratio:
                setUnderlineAlpha(nutrientsUnderline);
                headingText.setText("Соотношение нутриентов");

                showFragmentTop(FragmentNutrientsRatio.newInstance());
                showFragmentBottom(FragmentBelowNutrients.newInstance());
                break;
            case R.id.account:
                setUnderlineAlpha(accountUnderline);
                headingText.setText("Данные пользователя");

                Fragment fragment = FragmentAccount.newInstance();
                viewGroup.setLayoutParams(new LinearLayout.LayoutParams(displayMetrics.widthPixels,
                        (int) (displayMetrics.heightPixels - (123 * displayMetrics.density))));
                showFragmentTop(fragment);
                showFragmentBottom(FragmentBelowAccount.newInstance());
                break;
            case R.id.settings:
                setUnderlineAlpha(settingsUnderline);
                headingText.setText("Настройки");
                break;
        }
        Log.d("onClick", "okay");
    }

    private void setUnderlineAlpha(View view) {
        accountUnderline.setAlpha(0);
        fillUnderline.setAlpha(0);
        nutrientsUnderline.setAlpha(0);
        timerUnderline.setAlpha(0);
        settingsUnderline.setAlpha(0);

        view.setAlpha(1);
    }

    void showFragmentTop(Fragment fragment) {

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_fragment_view, fragment)
                .commit();
    }

    void showFragmentBottom(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_below_main, fragment)
                .commit();
    }
}
