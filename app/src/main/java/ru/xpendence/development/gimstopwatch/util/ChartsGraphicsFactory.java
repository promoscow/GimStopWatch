package ru.xpendence.development.gimstopwatch.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import ru.xpendence.development.gimstopwatch.foodstuffs.GoodInDayRation;

/**
 * Created by promoscow on 04.06.17.
 * Class creating daily bitmap for archive.
 */

public class ChartsGraphicsFactory {
    private final String TAG = this.getClass().getSimpleName();

    Context context;
    private final String IMAGE_PATH = "charts";

    public ChartsGraphicsFactory(Context baseContext) {
        context = baseContext;
    }

    // TODO: 06.06.17 Разобраться, почему в параметрах dailyGoods.
    /**
     * Creates bitmap & saving in in Assets
     * @param dailyGoods - - all meals today
     */
    public Bitmap createChartImage(ArrayList<GoodInDayRation> dailyGoods) {
        Log.e(TAG, "enter.bitmap");

//        String baseFolder = Environment.getExternalStorageDirectory().getAbsolutePath();
//        File file = new File(baseFolder + "/a111.png");
        Bitmap bitmap = createNewBitmap(dailyGoods);
//        Log.e(TAG, String.valueOf(bitmap));

//        String filePath = context.getFilesDir().getAbsolutePath() + "/" + IMAGE_PATH + "/" + "asdfg.png";
//        FileOutputStream fileOutputStream;
//        try {
//            fileOutputStream = new FileOutputStream(file);
////            fileOutputStream = new FileOutputStream(context.getAssets() + "/test.png");
//            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
//            fileOutputStream.flush();
//            fileOutputStream.close();
//            Log.e(TAG, "bitmap written");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        return bitmap;
    }

    private Bitmap createNewBitmap(ArrayList<GoodInDayRation> dailyGoods) {
        // TODO: 04.06.17 Проверка на null;
        Bitmap bitmap = Bitmap.createBitmap(175, 525, Bitmap.Config.ARGB_8888);

        if (estimateHeight(dailyGoods) > 0) {
            /**
             * Поскольку Bitmap рисуется сверху, а нам надо снизу,
             * то charHeight — это пустота над рисунком.
             * Начинаем с неё и заканчиваем 525. Это и будет высота столбика.
             */
            int chartHeight = 525 - estimateHeight(dailyGoods);
            int chartWidth = 125;
            int chartIndent = 25;

            int[] nutrientsHeights = estimateProteinsHeight(dailyGoods, chartHeight);
            int proteinsHeight = nutrientsHeights[0];
            int fatsHeight = nutrientsHeights[1];
            int carbohydratesHeight = 525;
            Log.e(TAG, String.format("height %d, prot %d, fat %d",
                    chartHeight, proteinsHeight, fatsHeight));

            bitmap = drawNutrients(bitmap,
                    chartHeight,
                    proteinsHeight + chartHeight,
                    chartWidth,
                    chartIndent,
                    NutrientsHelper.getBLUE());

//            bitmap = drawNutrients(bitmap,
//                    proteinsHeight,
//                    fatsHeight,
//                    chartWidth,
//                    chartIndent,
//                    NutrientsHelper.getYELLOW());
//
//            bitmap = drawNutrients(bitmap,
//                    fatsHeight,
//                    carbohydratesHeight,
//                    chartWidth,
//                    chartIndent,
//                    NutrientsHelper.getGREEN());

            for (int i = 0; i < 175; i++) {
                bitmap.setPixel(i, 524, Color.DKGRAY);
            }
        }
        return bitmap;
    }

    /**
     * Drawing nutrients one by one.
     * @param bitmap - target bitmap
     * @param yStart — Y top
     * @param yFinish - Y bottom
     * @param xFinish - X bottom
     * @param xStart - X top
     * @param color - color of nutrient
     * @return bitmap with drawn nutrient
     */
    private Bitmap drawNutrients(Bitmap bitmap,
                                 int yStart,
                                 int yFinish,
                                 int xFinish,
                                 int xStart,
                                 int color) {
        System.out.println(yStart);
        System.out.println(yFinish);
        for (int y = yStart; y < yFinish; y++) {
            for (int x = xStart; x < xFinish; x++) {
                bitmap.setPixel(x, y, color);
            }
        }
        return bitmap;
    }

    /**
     * Estimates nutrients heights.
     * @param dailyGoods - all meals today
     * @param chartHeight - total height of nutrients
     * @return proteins finish Y, fats finish Y
     * carbohydrates finish Y = 525
     */
    private int[] estimateProteinsHeight(ArrayList<GoodInDayRation> dailyGoods, int chartHeight) {
        double proteins = 0;
        double fats = 0;
        double carbons = 0;
        double summary;

        for (GoodInDayRation dailyGood : dailyGoods) {
            proteins += dailyGood.getProteins();
            fats += dailyGood.getFats();
            carbons += dailyGood.getCarbohydrates();
        }
        summary = proteins + fats + carbons;

        Log.e(TAG, String.format("proteins %d, fats %d, carbons %d",
                (int) proteins, (int) fats, (int) carbons));

        /**
         * Опять же, обратный порядок умножения, как в estimateHeight().
         * Правильно: нутриент / сумма нутриентов * высота.
         */
        return new int[] {
                (int) (chartHeight * (proteins / summary)),
                (int) (chartHeight * (fats / summary))
        };
    }

    /**
     * Estimates total nutrients height.
     * @return total nutrients height.
     * @param dailyGoods
     */
    private int estimateHeight(ArrayList<GoodInDayRation> dailyGoods) {
        int summary = 0;

        for (GoodInDayRation good : dailyGoods) summary += good.getCalories();

        /**
         * Изменил порядок, чтобы не морочаться с приведением к int.
         * Правильно было бы: калории за день / цель за день * высота.
         */
        Log.e(TAG, String.valueOf("summary: " + summary));
        Log.e(TAG, String.valueOf((summary <  PersonalData.getGoalCalories())
                ? 525 * summary / PersonalData.getGoalCalories() : 525));
        return (summary <  PersonalData.getGoalCalories())
                ? 525 * summary / PersonalData.getGoalCalories() : 525;
    }
}
