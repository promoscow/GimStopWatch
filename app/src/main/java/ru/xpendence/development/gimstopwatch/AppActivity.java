package ru.xpendence.development.gimstopwatch;

import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import ru.xpendence.development.gimstopwatch.foodstuffs.FoodStuffsData;
import ru.xpendence.development.gimstopwatch.foodstuffs.Good;
import ru.xpendence.development.gimstopwatch.fragments.FragmentAccount;
import ru.xpendence.development.gimstopwatch.fragments.FragmentBelowAccount;
import ru.xpendence.development.gimstopwatch.fragments.FragmentBelowTimer;
import ru.xpendence.development.gimstopwatch.fragments.FragmentFillDayRate;
import ru.xpendence.development.gimstopwatch.fragments.FragmentBelowFillDayRate;
import ru.xpendence.development.gimstopwatch.fragments.FragmentBelowNutrients;
import ru.xpendence.development.gimstopwatch.fragments.FragmentNutrientsRatio;
import ru.xpendence.development.gimstopwatch.fragments.FragmentTimer;

import static ru.xpendence.development.gimstopwatch.foodstuffs.FoodStuffsData.goodsList;

/**
 * Main Activity for all frames.
 */

public class AppActivity extends AppCompatActivity {

    private final String TAG = this.getClass().getSimpleName();

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
                setUnderlineAlpha(timerUnderline);
                headingText.setText(R.string.recovery_timer);
                showFragmentTop(FragmentTimer.newInstance());
                showFragmentBottom(FragmentBelowTimer.newInstance());
                break;
            case R.id.fill_day_rate:
                setUnderlineAlpha(fillUnderline);
                headingText.setText(R.string.cal_daily_norm);
                showFragmentTop(FragmentFillDayRate.newInstance());
                showFragmentBottom(FragmentBelowFillDayRate.newInstance());
                break;
            case R.id.nutrients_ratio:
                setUnderlineAlpha(nutrientsUnderline);
                headingText.setText(R.string.nutrients_ratio);

                showFragmentTop(FragmentNutrientsRatio.newInstance());
                showFragmentBottom(FragmentBelowNutrients.newInstance());
                break;
            case R.id.account:
                setUnderlineAlpha(accountUnderline);
                headingText.setText(R.string.user_data);

                Fragment fragment = FragmentAccount.newInstance();
                viewGroup.setLayoutParams(new LinearLayout.LayoutParams(displayMetrics.widthPixels,
                        (int) (displayMetrics.heightPixels - (123 * displayMetrics.density))));
                showFragmentTop(fragment);
                showFragmentBottom(FragmentBelowAccount.newInstance());
                break;
            case R.id.settings:
                setUnderlineAlpha(settingsUnderline);
                headingText.setText(R.string.settings);
                break;
        }
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

    LayoutInflater layoutInflater;
    View addFoodView;
    AlertDialog.Builder alertDialogBuilder;
    AlertDialog alertDialog;
    AutoCompleteTextView autoCompleteTextView;

    /** Обработчик поиска продукта */
    public void onClickAddFoodButton(View view) {
        layoutInflater = LayoutInflater.from(view.getContext());
        addFoodView = layoutInflater.inflate(R.layout.add_food_layout, null);
        alertDialogBuilder = new AlertDialog.Builder(view.getContext());
        alertDialogBuilder.setView(addFoodView);
        alertDialog = alertDialogBuilder.create();

//        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);



        TableLayout nutrientsTable = (TableLayout) addFoodView.findViewById(R.id.nutrients_table);
        nutrientsTable.setVisibility(View.GONE);

        TextView in100 = (TextView) addFoodView.findViewById(R.id.in_100_text);
        in100.setVisibility(View.GONE);

        RelativeLayout addGood = (RelativeLayout) addFoodView.findViewById(R.id.add_good_layout);
        addGood.setVisibility(View.GONE);

//        final EditText addFoodEditText = (EditText) addFoodView.findViewById(R.id.add_food_name_text);
//        addFoodEditText.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                if (s != null && s.length() != 0) {
//                    try {
//                        TextView textView = (TextView) addFoodView.findViewById(R.id.testing_listener);
//                        textView.setText(s);
//                    } catch (NullPointerException e) {
//                        e.printStackTrace();
//                    }
//                    Log.d(TAG, String.valueOf(s));
//                }
//            }
//        });

//        ArrayAdapter arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, COUNTRIES);

        autoCompleteTextView = (AutoCompleteTextView) addFoodView.findViewById(R.id.add_food_name_text);
        Log.i(TAG, String.valueOf(autoCompleteTextView));
        autoCompleteTextView.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line,
                goodsList));

        autoCompleteTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            TextView textView = (TextView) addFoodView.findViewById(R.id.good_name);
            @Override
            public void afterTextChanged(Editable s) {
                if (s != null && s.length() != 0) {
                    String goodName = String.valueOf(s);
                    if (goodsList.contains(goodName)) {
                        addGoodsToRation(goodName);
                        textView.setText(s);
                    } else textView.setText("");
                }
            }
        });

//        autoCompleteTextView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String s = autoCompleteTextView.getText().toString();
//                System.out.println(s);
//                TextView textView = (TextView) addFoodView.findViewById(R.id.testing_listener);
//                textView.setText(s);
//            }
//        });

//        autoCompleteTextView.setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                if (event.getAction() == KeyEvent.ACTION_DOWN &&
//                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
//                    String s = autoCompleteTextView.getText().toString();
//                    TextView textView = (TextView) addFoodView.findViewById(R.id.testing_listener);
//                    textView.setText(s);
//                    return true;
//                }
//                return false;
//            }
//        });

        alertDialog.show();
        alertDialog.setCanceledOnTouchOutside(true);
    }

    /** Информация о выбранном продукте и добавление его в список съеденных */
    private void addGoodsToRation(String goodName) {

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(autoCompleteTextView.getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);

        Good  good = FoodStuffsData.goods.get(goodName);

        TableLayout nutrientsTable = (TableLayout) addFoodView.findViewById(R.id.nutrients_table);
        nutrientsTable.setVisibility(View.VISIBLE);

        TextView in100 = (TextView) addFoodView.findViewById(R.id.in_100_text);
        in100.setVisibility(View.VISIBLE);

        RelativeLayout addGood = (RelativeLayout) addFoodView.findViewById(R.id.add_good_layout);
        addGood.setVisibility(View.VISIBLE);

        EditText addGoodEditText = (EditText) addFoodView.findViewById(R.id.add_good_edit_text);
        addGoodEditText.requestFocus();

        TextView proteinsTableText = (TextView) addFoodView.findViewById(R.id.good_proteins);
        proteinsTableText.setText(R.string.how_much_proteins_text);

        TextView proteinsTableValue = (TextView) addFoodView.findViewById(R.id.good_proteins_values);
        String proteinsValue = String.valueOf(good.getProteins()) + " " + getString(R.string.grams);
        proteinsTableValue.setText(proteinsValue);

        TextView fatsTableText = (TextView) addFoodView.findViewById(R.id.good_fat);
        fatsTableText.setText(R.string.how_much_fats_text);

        TextView fatsTableValue = (TextView) addFoodView.findViewById(R.id.good_fat_values);
        String fatsValue = String.valueOf(good.getFats()) + " " + getString(R.string.grams);
        fatsTableValue.setText(fatsValue);

        TextView carbonsTableText = (TextView) addFoodView.findViewById(R.id.good_carbohydrates);
        carbonsTableText.setText(R.string.how_much_carbohydrates_text);

        TextView carbonsTableValue = (TextView) addFoodView.findViewById(R.id.good_carbohydrates_values);
        String carbonsValue = String.valueOf(good.getCarbohydrates()) + " " + getString(R.string.grams);
        carbonsTableValue.setText(carbonsValue);

        TextView caloriesTableText = (TextView) addFoodView.findViewById(R.id.good_calories);
        caloriesTableText.setText(R.string.how_much_calories_text);

        TextView caloriesTableValue = (TextView) addFoodView.findViewById(R.id.good_calories_values);
        String caloriesValue = String.valueOf(good.getCalories()) + " " + getString(R.string.calories);
        caloriesTableValue.setText(caloriesValue);
    }

    /** Переход на фрагмент, вместо @FragmentBelowFillDayRate выводим фрагмент
     * со списком съеденных продуктов и возможностью их удалять/редактировать.
     * @param view
     */
    public void onClickEditFoodButton(View view) {
    }

    /** Переход на @FragmentFillDayRate */
    public void onClickInfoFoodButton(View view) {
        setUnderlineAlpha(fillUnderline);
        TextView headingText = (TextView) findViewById(R.id.heading);
        headingText.setText(R.string.cal_daily_norm);
        showFragmentTop(FragmentFillDayRate.newInstance());
        showFragmentBottom(FragmentBelowFillDayRate.newInstance());
    }

    public void onClickEditAccountButton(View view) {
    }

    public void onClickInfoAccountButton(View view) {
    }
}
