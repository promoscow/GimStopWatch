package ru.xpendence.development.gimstopwatch;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import ru.xpendence.development.gimstopwatch.data.FoodDbHelper;
import ru.xpendence.development.gimstopwatch.foodstuffs.FoodStuffsData;
import ru.xpendence.development.gimstopwatch.foodstuffs.Good;
import ru.xpendence.development.gimstopwatch.foodstuffs.GoodInDayRation;
import ru.xpendence.development.gimstopwatch.fragments.FragmentAccount;
import ru.xpendence.development.gimstopwatch.fragments.FragmentBelowAccount;
import ru.xpendence.development.gimstopwatch.fragments.FragmentBelowTimer;
import ru.xpendence.development.gimstopwatch.fragments.FragmentCharts;
import ru.xpendence.development.gimstopwatch.fragments.FragmentFillDayRate;
import ru.xpendence.development.gimstopwatch.fragments.FragmentBelowFillDayRate;
import ru.xpendence.development.gimstopwatch.fragments.FragmentBelowNutrients;
import ru.xpendence.development.gimstopwatch.fragments.FragmentFoodsInfo;
import ru.xpendence.development.gimstopwatch.fragments.FragmentNutrientsRatio;
import ru.xpendence.development.gimstopwatch.fragments.FragmentSettings;
import ru.xpendence.development.gimstopwatch.util.CommonSettings;
import ru.xpendence.development.gimstopwatch.util.PersonalData;

import static ru.xpendence.development.gimstopwatch.foodstuffs.FoodStuffsData.archiveRations;
import static ru.xpendence.development.gimstopwatch.foodstuffs.FoodStuffsData.count;
import static ru.xpendence.development.gimstopwatch.foodstuffs.FoodStuffsData.dailyGoods;
import static ru.xpendence.development.gimstopwatch.foodstuffs.FoodStuffsData.goodsList;
import static ru.xpendence.development.gimstopwatch.foodstuffs.FoodStuffsData.updateSummaries;

/**
 * Main Activity for all frames.
 */

public class AppActivity extends AppCompatActivity {

    private final String TAG = this.getClass().getSimpleName();

    public static final String APP_PREFERENCES = "appSettings";

    /** User preferences */
    public static final String USER_NAME = "userName";
    public static final String USER_AGE = "userAge";
    public static final String USER_WEIGHT = "userWeight";
    public static final String GOAL_CALORIES = "goalCalories";

    private SharedPreferences mSettings;

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
//    View accountUnderline;
//    View fillUnderline;
//    View nutrientsUnderline;
//    View chartsUnderline;
//    View settingsUnderline;
    ViewGroup defaultViewGroup;

    /**
     * Menu buttons.
     */
    ImageView account;
    ImageView fillDayRate;
    ImageView nutrientsRatio;
    ImageView charts;
    ImageView settings;

    /**
     * TextViews
     */
    TextView accountUnderline;
    TextView fillDayRateUnderline;
    TextView nutrientsRatioUnderline;
    TextView chartsUnderline;
    TextView settingsUnderline;

    /**
     * Alert dialogs.
     */
    LayoutInflater layoutInflater;
    AlertDialog.Builder alertDialogBuilder;
    AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        /** Проверка, не настало ли завтра */
        FoodStuffsData.checkDate(this);

        /** Создание файла настроек */
        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);

        final float scale = getResources().getDisplayMetrics().density;

        getResolutions();
        defineDensity();

        defaultViewGroup = (FrameLayout) findViewById(R.id.main_fragment_view);

//        accountUnderline = findViewById(R.id.account_underline);
//        fillUnderline = findViewById(R.id.fill_day_rate_underline);
//        nutrientsUnderline = findViewById(R.id.nutrients_ratio_underline);
//        chartsUnderline = findViewById(R.id.timer_underline);
//        settingsUnderline = findViewById(R.id.settings_underline);
//
//        accountUnderline.setAlpha(1);
//        fillUnderline.setAlpha(1);
//        nutrientsUnderline.setAlpha(1);
//        chartsUnderline.setAlpha(1);
//        settingsUnderline.setAlpha(1);

        /**
         * Initialization & setting up menu buttons & its captions.
         */
        account = (ImageView) findViewById(R.id.account);
        fillDayRate = (ImageView) findViewById(R.id.fill_day_rate);
        nutrientsRatio = (ImageView) findViewById(R.id.nutrients_ratio);
        charts = (ImageView) findViewById(R.id.charts);
        settings = (ImageView) findViewById(R.id.settings);

        accountUnderline = (TextView) findViewById(R.id.account_underline);
        fillDayRateUnderline = (TextView) findViewById(R.id.fill_day_rate_underline);
        nutrientsRatioUnderline = (TextView) findViewById(R.id.nutrients_ratio_underline);
        chartsUnderline = (TextView) findViewById(R.id.charts_underline);
        settingsUnderline = (TextView) findViewById(R.id.settings_underline);

        accountUnderline.setTypeface(CommonSettings.getRobotoCondLight());
        fillDayRateUnderline.setTypeface(CommonSettings.getRobotoCondLight());
        nutrientsRatioUnderline.setTypeface(CommonSettings.getRobotoCondLight());
        chartsUnderline.setTypeface(CommonSettings.getRobotoCondLight());
        settingsUnderline.setTypeface(CommonSettings.getRobotoCondLight());

//        DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, Locale.US);
//        String s = df.format(new Date());
//        Log.d("DateFormat", s);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.US);
        String s1 = dateFormat.format(new Date());
        Log.d("SimpleDateFormat", s1);

        setIconsColorAsDefault();
        setUnderlinesAsDefault();

        Log.d("calcDP", String.valueOf(calcDP(100)));
    }

    /**
     * Underlines has been changed as accent color
     */
    private void setUnderlinesAsDefault() {
        accountUnderline.setTextColor(getResources().getColor(R.color.primary_dark_custom));
        fillDayRateUnderline.setTextColor(getResources().getColor(R.color.primary_dark_custom));
        nutrientsRatioUnderline.setTextColor(getResources().getColor(R.color.primary_dark_custom));
        chartsUnderline.setTextColor(getResources().getColor(R.color.primary_dark_custom));
        settingsUnderline.setTextColor(getResources().getColor(R.color.primary_dark_custom));
    }

    /**
     * Setting up menu icons color as default
     */
    private void setIconsColorAsDefault() {
        account.setImageResource(R.drawable.account);
        fillDayRate.setImageResource(R.drawable.format_line_weight);
        nutrientsRatio.setImageResource(R.drawable.chart_donut);
        charts.setImageResource(R.drawable.chart_bar_stacked);
        settings.setImageResource(R.drawable.settings);
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

    /**
     * Define all display parameters
     */
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

    /**
     * Обработчик меню. Основные фрагменты запускаются отсюда.
     */
    public void onClickMenu(View view) {

        new AppActivity();
        TextView headingText = (TextView) findViewById(R.id.heading);
        CommonSettings.getInstance(this);
        headingText.setTypeface(CommonSettings.getRobotoCondLight());
        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
        ViewGroup viewGroup = (FrameLayout) findViewById(R.id.main_fragment_view);

        switch (view.getId()) {
            case R.id.charts:
                /** Проверка, не настало ли завтра */
                FoodStuffsData.checkDate(this);

                headingText.setText(R.string.charts_text);
                setIconsColorAsDefault();
                setUnderlinesAsDefault();
                charts.setImageResource(R.drawable.chart_bar_stacked_accent);
                chartsUnderline.setTextColor(getResources().getColor(R.color.accent_custom));
                Fragment fragmentCharts = FragmentCharts.newInstance();
                viewGroup.setLayoutParams(new LinearLayout.LayoutParams(displayMetrics.widthPixels,
                        (int) (displayMetrics.heightPixels - (123 * displayMetrics.density))));
                showFragmentTop(fragmentCharts);
                showFragmentBottom(FragmentBelowTimer.newInstance());
                break;
            case R.id.fill_day_rate:
                /** Проверка, не настало ли завтра */
                FoodStuffsData.checkDate(this);

                headingText.setText(R.string.cal_daily_norm);
                setIconsColorAsDefault();
                setUnderlinesAsDefault();
                fillDayRate.setImageResource(R.drawable.format_line_weight_accent);
                fillDayRateUnderline.setTextColor(getResources().getColor(R.color.accent_custom));
                Fragment fragmentFill = FragmentFillDayRate.newInstance();
                viewGroup.setLayoutParams(new LinearLayout.LayoutParams(displayMetrics.widthPixels,
                        (int) (250 * displayMetrics.density)));
                showFragmentTop(fragmentFill);
                showFragmentBottom(FragmentBelowFillDayRate.newInstance());
                break;
            case R.id.nutrients_ratio:
                /** Проверка, не настало ли завтра */
                FoodStuffsData.checkDate(this);

                setIconsColorAsDefault();
                setUnderlinesAsDefault();
                nutrientsRatio.setImageResource(R.drawable.chart_donut_accent);
                nutrientsRatioUnderline.setTextColor(getResources().getColor(R.color.accent_custom));
                headingText.setText(R.string.nutrients_ratio);
                Fragment fragmentNutrients = FragmentNutrientsRatio.newInstance();
                viewGroup.setLayoutParams(new LinearLayout.LayoutParams(displayMetrics.widthPixels,
                        (int) (250 * displayMetrics.density)));
                showFragmentTop(fragmentNutrients);
                showFragmentBottom(FragmentBelowNutrients.newInstance());
                break;
            case R.id.account:
                /** Проверка, не настало ли завтра */
                FoodStuffsData.checkDate(this);

                setIconsColorAsDefault();
                setUnderlinesAsDefault();
                account.setImageResource(R.drawable.account_accent);
                accountUnderline.setTextColor(getResources().getColor(R.color.accent_custom));
                headingText.setText(R.string.user_data);
                Fragment fragmentAccount = FragmentAccount.newInstance();
                viewGroup.setLayoutParams(new LinearLayout.LayoutParams(displayMetrics.widthPixels,
                        (int) (displayMetrics.heightPixels - (123 * displayMetrics.density))));
                showFragmentTop(fragmentAccount);
                showFragmentBottom(FragmentBelowAccount.newInstance());
                break;
            case R.id.settings:
                /** Проверка, не настало ли завтра */
                FoodStuffsData.checkDate(this);

                setIconsColorAsDefault();
                setUnderlinesAsDefault();
                settings.setImageResource(R.drawable.settings_accent);
                settingsUnderline.setTextColor(getResources().getColor(R.color.accent_custom));
                headingText.setText(R.string.settings);
                Fragment fragmentSettings = FragmentSettings.newInstance();
                viewGroup.setLayoutParams(new LinearLayout.LayoutParams(displayMetrics.widthPixels,
                        (int) (displayMetrics.heightPixels - (123 * displayMetrics.density))));
                showFragmentTop(fragmentSettings);
                showFragmentBottom(FragmentBelowAccount.newInstance());
                break;
            case R.id.info_food_button:
                /** Проверка, не настало ли завтра */
                FoodStuffsData.checkDate(this);

                headingText.setText(R.string.your_ration_today);

                Fragment fragmentInfo = FragmentFoodsInfo.newInstance();
                viewGroup.setLayoutParams(new LinearLayout.LayoutParams(displayMetrics.widthPixels,
                        (int) (displayMetrics.heightPixels - (123 * displayMetrics.density))));
                Log.e("displayMetrics!!!", String.valueOf(displayMetrics.heightPixels));
                showFragmentTop(fragmentInfo);
                showFragmentBottom(FragmentBelowAccount.newInstance());
                break;
        }
    }

    private void setUnderlineAlpha(View view) {
//        accountUnderline.setAlpha(0);
//        fillUnderline.setAlpha(0);
//        nutrientsUnderline.setAlpha(0);
//        chartsUnderline.setAlpha(0);
//        settingsUnderline.setAlpha(0);
//
//        view.setAlpha(1);
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

    View addFoodView;
    AutoCompleteTextView autoCompleteTextView;
    /**
     * Обработчик поиска продукта
     */
    public void onClickAddFoodButton(View view) {
        /** Проверка, не настало ли завтра */
        FoodStuffsData.checkDate(this);

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
        in100.setTypeface(CommonSettings.getRobotoCondLight());
        in100.setVisibility(View.GONE);

        TextView goodNameText = (TextView) addFoodView.findViewById(R.id.good_name);
        goodNameText.setTypeface(CommonSettings.getRobotoCondLight());
        goodNameText.setVisibility(View.GONE);

        TextView addGoodInGramsText = (TextView) addFoodView.findViewById(R.id.add_good_in_grams);
        addGoodInGramsText.setTypeface(CommonSettings.getRobotoCondLight());
        addGoodInGramsText.setVisibility(View.GONE);

        LinearLayout addGood = (LinearLayout) addFoodView.findViewById(R.id.add_good_layout);
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
        autoCompleteTextView.setTypeface(CommonSettings.getRobotoCondLight());
        Log.i(TAG, String.valueOf(autoCompleteTextView));
        autoCompleteTextView.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line,
                goodsList));
        autoCompleteTextView.setTextColor(0xFF212121);

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
                        autoCompleteTextView.setTextColor(Color.DKGRAY);
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

    /**
     * Информация о выбранном продукте и добавление его в список съеденных
     */
    private void addGoodsToRation(String goodName) {

        /** Проверка, не настало ли завтра */
        FoodStuffsData.checkDate(this);

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(autoCompleteTextView.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        final Good good = FoodStuffsData.goods.get(goodName);

        TableLayout nutrientsTable = (TableLayout) addFoodView.findViewById(R.id.nutrients_table);
        nutrientsTable.setVisibility(View.VISIBLE);

        TextView goodNameText = (TextView) addFoodView.findViewById(R.id.good_name);
        goodNameText.setTypeface(CommonSettings.getRobotoCondLight());
        goodNameText.setVisibility(View.VISIBLE);

        TextView in100 = (TextView) addFoodView.findViewById(R.id.in_100_text);
        in100.setTypeface(CommonSettings.getRobotoCondLight());
        in100.setVisibility(View.VISIBLE);

        TextView addGoodInGramsText = (TextView) addFoodView.findViewById(R.id.add_good_in_grams);
        addGoodInGramsText.setTypeface(CommonSettings.getRobotoCondLight());
        addGoodInGramsText.setVisibility(View.VISIBLE);

        LinearLayout addGood = (LinearLayout) addFoodView.findViewById(R.id.add_good_layout);
        addGood.setVisibility(View.VISIBLE);

        final EditText addGoodEditText = (EditText) addFoodView.findViewById(R.id.add_good_edit_text);
        addGoodEditText.requestFocus();
        addGoodEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    int amount = Integer.parseInt(addGoodEditText.getText().toString());

                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd", Locale.US);
                    String s1 = dateFormat.format(new Date());
                    Log.d("SimpleDateFormat", s1);

                    GoodInDayRation newPortion = new GoodInDayRation();
                    newPortion.setName(good.getName());
                    newPortion.setProteins(good.getProteins(), amount);
                    newPortion.setFats(good.getFats(), amount);
                    newPortion.setCarbohydrates(good.getCarbohydrates(), amount);
                    newPortion.setCalories(good.getCalories(), amount);
                    newPortion.setCategory(good.getCategory());
                    newPortion.setAmount(amount);
                    newPortion.setDate(new Date());
                    System.out.println(newPortion.toString());

                    /** Adding to today meals list. */
                    dailyGoods.add(newPortion);
                    Log.d(TAG, String.valueOf(newPortion));
                    Log.d("portions", String.valueOf(dailyGoods.size()));
                    FoodDbHelper.writePortionToStorage(getBaseContext(), newPortion);

//                    archiveRations.put(s1, dailyGoods);
//                    archiveCharts.put(s1, new ChartsGraphicsFactory(getBaseContext()).createChartImage(dailyGoods));
                    Log.e(s1, String.valueOf(archiveRations.get(s1)));

//                    for (String archiveRation : archiveRations.keySet()) {
//                        for (int i = 0; i < archiveRations.size() - 1; i++) {
//                            Log.d("archive", archiveRations.get(archiveRation).get(i).toString());
//                        }
//                    }

                    updateSummaries(count++);

                    showFragmentTop(FragmentAccount.newInstance());
                    showFragmentBottom(FragmentBelowAccount.newInstance());

//                    TextView headingText = (TextView) findViewById(R.id.heading);
//                    setUnderlineAlpha(fillUnderline);
//                    headingText.setText(R.string.cal_daily_norm);
//                    setIconsColorAsDefault();
//                    fillDayRate.setImageResource(R.drawable.format_line_weight_accent);
//                    setUnderlinesAsDefault();
//                    fillDayRateUnderline.setTextColor(getResources().getColor(R.color.accent_custom));
//                    runFillDayRateFragment();

//                    Display display = getWindowManager().getDefaultDisplay();
//                    DisplayMetrics metricsB = new DisplayMetrics();
//                    display.getMetrics(metricsB);
//                    ViewGroup viewGroup = (FrameLayout) findViewById(R.id.main_fragment_view);
//
//                    Fragment fragment = FragmentAccount.newInstance();
//                    viewGroup.setLayoutParams(new LinearLayout.LayoutParams(metricsB.widthPixels,
//                            (int) (metricsB.heightPixels - (123 * metricsB.density))));
//                    showFragmentTop(fragment);
//                    showFragmentBottom(FragmentBelowAccount.newInstance());


//                    DisplayMetrics displayMetrics1 = getResources().getDisplayMetrics();
//                    Log.d(TAG, String.valueOf(displayMetrics1.heightPixels));
//                    ViewGroup viewGroup1 = (FrameLayout) findViewById(R.id.main_fragment_view);
//                    ViewGroup viewGroup2 = (FrameLayout) findViewById(R.id.fragment_below_main);
//                    Fragment fragment1 = FragmentAccount.newInstance();

//                    viewGroup2.setLayoutParams(new LinearLayout.LayoutParams(displayMetrics1.widthPixels,
//                            0));
//                    viewGroup1.setLayoutParams(new LinearLayout.LayoutParams(displayMetrics1.widthPixels,
//                            (int) (displayMetrics1.heightPixels - (123 * displayMetrics1.density))));
//
//                    viewGroup2.setLayoutParams(new LinearLayout.LayoutParams(displayMetrics1.widthPixels,
//                            0));
//                    showFragmentTop(fragment1);
//                    showFragmentBottom(FragmentBelowAccount.newInstance());

                    alertDialog.cancel();
                    Toast.makeText(getBaseContext(),
                            "Блюдо (" + newPortion.getName() + " , " + newPortion.getAmount() + " гр.) успешно добавлено.",
                            Toast.LENGTH_SHORT).show();
                    return true;
                }
                return false;
            }
        });

        TextView proteinsTableText = (TextView) addFoodView.findViewById(R.id.good_proteins);
        proteinsTableText.setTypeface(CommonSettings.getRobotoCondLight());
        proteinsTableText.setText(R.string.how_much_proteins_text);

        TextView proteinsTableValue = (TextView) addFoodView.findViewById(R.id.good_proteins_values);
        proteinsTableValue.setTypeface(CommonSettings.getRobotoCondLight());
        String proteinsValue = String.valueOf(good.getProteins()) + " " + getString(R.string.grams);
        proteinsTableValue.setText(proteinsValue);

        TextView fatsTableText = (TextView) addFoodView.findViewById(R.id.good_fat);
        fatsTableText.setTypeface(CommonSettings.getRobotoCondLight());
        fatsTableText.setText(R.string.how_much_fats_text);

        TextView fatsTableValue = (TextView) addFoodView.findViewById(R.id.good_fat_values);
        fatsTableValue.setTypeface(CommonSettings.getRobotoCondLight());
        String fatsValue = String.valueOf(good.getFats()) + " " + getString(R.string.grams);
        fatsTableValue.setText(fatsValue);

        TextView carbonsTableText = (TextView) addFoodView.findViewById(R.id.good_carbohydrates);
        carbonsTableText.setTypeface(CommonSettings.getRobotoCondLight());
        carbonsTableText.setText(R.string.how_much_carbohydrates_text);

        TextView carbonsTableValue = (TextView) addFoodView.findViewById(R.id.good_carbohydrates_values);
        carbonsTableValue.setTypeface(CommonSettings.getRobotoCondLight());
        String carbonsValue = String.valueOf(good.getCarbohydrates()) + " " + getString(R.string.grams);
        carbonsTableValue.setText(carbonsValue);

        TextView caloriesTableText = (TextView) addFoodView.findViewById(R.id.good_calories);
        caloriesTableText.setTypeface(CommonSettings.getRobotoCondLight());
        caloriesTableText.setText(R.string.how_much_calories_text);

        TextView caloriesTableValue = (TextView) addFoodView.findViewById(R.id.good_calories_values);
        caloriesTableValue.setTypeface(CommonSettings.getRobotoCondLight());
        String caloriesValue = String.valueOf(good.getCalories()) + " " + getString(R.string.calories);
        caloriesTableValue.setText(caloriesValue);
    }

    /**
     * Переход на фрагмент, вместо @FragmentBelowFillDayRate выводим фрагмент
     * со списком съеденных продуктов и возможностью их удалять/редактировать.
     *
     * @param view — обычный View (кнопка, в данном случае).
     */
    public void onClickEditFoodButton(View view) {
    }

    public void onClickEditAccountButton(View view) {
    }

    public void onClickInfoAccountButton(View view) {
    }

    View changeNameView;
    TextView enterName;
    EditText enterNameEdit;
    /**
     * Обработчик клика по имени
     */
    public void onClickChangeName(View view) {
        layoutInflater = LayoutInflater.from(view.getContext());
        changeNameView = layoutInflater.inflate(R.layout.set_name_layout, null);
        alertDialogBuilder = new AlertDialog.Builder(view.getContext());
        alertDialogBuilder.setView(changeNameView);
        alertDialog = alertDialogBuilder.create();

        enterName = (TextView) changeNameView.findViewById(R.id.enter_name);
        enterName.setTypeface(CommonSettings.getRobotoCondLight());

        enterNameEdit = (EditText) changeNameView.findViewById(R.id.enter_name_edit);

        enterNameEdit.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {

                    Log.e(TAG, "enter.nameChange");
                    String result = enterNameEdit.getText().toString();
                    if (result.matches("^[a-zA-Zа-яА-Я -]{3,30}$")) {
                        PersonalData.setName(result);
                        Log.e(TAG, PersonalData.getName());

                        showFragmentTop(FragmentSettings.newInstance());
                        showFragmentBottom(FragmentBelowAccount.newInstance());
                    }
                    alertDialog.cancel();
                    return true;
                }
                return false;
            }
        });

        alertDialog.show();
        alertDialog.setCanceledOnTouchOutside(true);
    }

    View changeAgeView;

    public void onClickChangeAge(View view) {
        layoutInflater = LayoutInflater.from(view.getContext());
        changeAgeView = layoutInflater.inflate(R.layout.set_age_layout, null);
        alertDialogBuilder = new AlertDialog.Builder(view.getContext());
        alertDialogBuilder.setView(changeAgeView);
        alertDialog = alertDialogBuilder.create();

        alertDialog.show();
        alertDialog.setCanceledOnTouchOutside(true);
    }

    public void onClickChangeWeight(View view) {
    }

    @Override
    protected void onPause() {
        super.onPause();

        /** Сохранение настроек */
        SharedPreferences.Editor editor = mSettings.edit();
        editor.putString(USER_NAME, PersonalData.getName());
        Log.e("SAVING_NAME", PersonalData.getName());
        editor.putInt(USER_AGE, PersonalData.getAge());
        editor.putFloat(USER_WEIGHT, (float) PersonalData.getWeight());
        editor.putInt(GOAL_CALORIES, PersonalData.getGoalCalories());
        editor.apply();
    }

    @Override
    public void onResume() {
        super.onResume();

        /** Загрузка сохранённых настроек */
        if (mSettings.contains(USER_NAME)) {
            PersonalData.setName(mSettings.getString(USER_NAME, "Новый пользователь"));
            Log.e(TAG, PersonalData.getName());
        }
        if (mSettings.contains(USER_AGE)) {
            PersonalData.setAge(mSettings.getInt(USER_AGE, 25));
            Log.e(TAG, String.valueOf(PersonalData.getAge()));
        }
        if (mSettings.contains(USER_WEIGHT)) {
            PersonalData.setWeight((double) mSettings.getFloat(USER_WEIGHT, 70));
            Log.e(TAG, String.valueOf(PersonalData.getWeight()));
        }
        if (mSettings.contains(GOAL_CALORIES)) {
            PersonalData.setGoalCalories(mSettings.getInt(GOAL_CALORIES, 2500));
            Log.e(TAG, String.valueOf(PersonalData.getGoalCalories()));
        }

        /** Проверка, не настало ли завтра */
        FoodStuffsData.checkDate(this);

        if (FoodStuffsData.dailyGoods == null || FoodStuffsData.dailyGoods.size() == 0) {
            Log.e(TAG, "dailyGoods == null");
            SQLiteDatabase database = FoodDbHelper.getInstance(this).getWritableDatabase();
            FoodDbHelper.GoodsObjectsInit.fillDailyGoodsFromDbStorage(database, this);

            for (int i = 0; i < FoodStuffsData.dailyGoods.size(); i++) {
                FoodStuffsData.updateSummaries(i);
            }
        }
    }
}
