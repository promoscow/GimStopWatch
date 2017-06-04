package ru.xpendence.development.gimstopwatch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;
import java.util.Map;

import ru.xpendence.development.gimstopwatch.foodstuffs.ExcellParser;
import ru.xpendence.development.gimstopwatch.foodstuffs.FoodStuffsData;
import ru.xpendence.development.gimstopwatch.foodstuffs.Good;
import ru.xpendence.development.gimstopwatch.util.FillArchiveScrypt;
import ru.xpendence.development.gimstopwatch.util.PersonalData;

public class MainActivity extends AppCompatActivity {

    private final String TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PersonalData data = new PersonalData();

        ExcellParser parser = new ExcellParser();
        String s = this.getClass().getPackage().getName();
        Log.e(TAG, s);
        Map<String, Good> goods = null;
        try {
            goods = parser.fill(getAssets().open("calories.xls"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert goods != null;
        Log.d(TAG, String.valueOf(goods.size()));
        FoodStuffsData.goods = goods;

        for (String good : goods.keySet()) FoodStuffsData.goodsList.add(good);
        System.out.println(FoodStuffsData.goodsList.size());

        FillArchiveScrypt.fillArchiveWithDefaultData();

        Intent intent = new Intent(MainActivity.this, FirstLaunchActivity.class);
        startActivity(intent);
    }

}
