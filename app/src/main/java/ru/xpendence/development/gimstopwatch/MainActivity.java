package ru.xpendence.development.gimstopwatch;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.Map;

import ru.xpendence.development.gimstopwatch.data.FoodDbHelper;
import ru.xpendence.development.gimstopwatch.foodstuffs.FoodStuffsData;
import ru.xpendence.development.gimstopwatch.foodstuffs.Good;
import ru.xpendence.development.gimstopwatch.util.PersonalData;

public class MainActivity extends AppCompatActivity {

    private final String TAG = this.getClass().getSimpleName();
    public static Bitmap transparentBitmap;

    private SharedPreferences mSettings;

    static {
        transparentBitmap = Bitmap.createBitmap(125, 525, Bitmap.Config.ARGB_8888);
//        for (int i = 0; i < 125; i++) {
//            transparentBitmap.setPixel(i, 524, Color.DKGRAY);
//        }
//        for (int i = 0; i < 125; i++) {
//            transparentBitmap.setPixel(i, 0, Color.DKGRAY);
//        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SQLiteDatabase database = FoodDbHelper.getInstance(this).getWritableDatabase();
        String s = this.getClass().getPackage().getName();
        Log.e(TAG, s);
        /** Filling goods list from database. */
        Map<String, Good> goods = FoodDbHelper.GoodsObjectsInit.fillGoods(database);

        assert goods != null;
        Log.d(TAG, String.valueOf(goods.size()));
        FoodStuffsData.goods = goods;

        for (String good : goods.keySet()) FoodStuffsData.goodsList.add(good);
        System.out.println(FoodStuffsData.goodsList.size());

        FoodStuffsData.dailyGoods = FoodDbHelper.GoodsObjectsInit.fillDailyGoodsFromDbStorage(database, this);
        FoodStuffsData.fillDailyGoodsAfterWakeUp();
        Log.d(TAG, "dailyGoods.updated");

//        /** Filling archive database from script. */
//        new FillArchiveScript(getBaseContext(),
//                FoodStuffsData.dailyGoods).fillArchiveWithDefaultData(this);
//        Log.d(TAG, "default.data.updated");

        /** Reading archive goods from database. */
        FoodStuffsData.archiveStrings = FoodDbHelper.GoodsObjectsInit.fillArchiveGoods(database);
        Log.e("dailyGoods.after.script", FoodStuffsData.dailyGoods.toString());

        FoodStuffsData.checkDate(this);

        Intent intent = new Intent(MainActivity.this, FirstLaunchActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    // TODO: 13.06.17 Данные удаляются раньше, чем создаётся столбик.
    @Override
    public void onResume() {
        super.onResume();

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
