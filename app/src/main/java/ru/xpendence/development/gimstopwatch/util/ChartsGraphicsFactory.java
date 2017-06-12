package ru.xpendence.development.gimstopwatch.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;

import ru.xpendence.development.gimstopwatch.foodstuffs.GoodsArchiveObject;

/**
 * Created by promoscow on 04.06.17.
 * Class creating daily bitmap for archive.
 */

public class ChartsGraphicsFactory {
    private final String TAG = this.getClass().getSimpleName();

    private Context context;

    public ChartsGraphicsFactory(Context baseContext) {
        context = baseContext;
    }

    /**
     * Creates bitmap & saving in in Assets
     *
     * @param dailyGoods - all meals today
     */
    public Bitmap createChartImage(GoodsArchiveObject dailyGoods) {
        Log.e(TAG, "enter.bitmap");

        return createNewBitmap(dailyGoods);
    }

    /**
     * Full cycle creating bitmap.
     *
     * @param goodsArchiveObject - data to use for bitmap creating
     * @return ready bitmap
     */
    private Bitmap createNewBitmap(GoodsArchiveObject goodsArchiveObject) {
        // TODO: 04.06.17 Проверка на null;
        Bitmap bitmap = Bitmap.createBitmap(125, 525, Bitmap.Config.ARGB_8888);

        if (estimateHeight(goodsArchiveObject) > 0) {
            /**
             * Поскольку Bitmap рисуется сверху, а нам надо снизу,
             * то charHeight — это пустота над рисунком.
             * Начинаем с неё и заканчиваем 525. Это и будет высота столбика.
             */
            int chartHeight = 525 - estimateHeight(goodsArchiveObject);
            int chartWidth = 75;
            int chartIndent = 25;

            int summary = (int) (goodsArchiveObject.getProteins() + goodsArchiveObject.getFats()
                    + goodsArchiveObject.getCarbohydrates());
            int proteinsHeight = (int) ((525 - chartHeight) * (goodsArchiveObject.getProteins() / summary));
            int fatsHeight = (int) ((525 - chartHeight) * (goodsArchiveObject.getFats() / summary));
            Log.e(TAG, String.format("height %d, prot %d, fat %d",
                    chartHeight, proteinsHeight, fatsHeight));

            bitmap = drawNutrients(bitmap,
                    chartHeight,
                    proteinsHeight,
                    fatsHeight,
                    chartIndent,
                    chartWidth,
                    NutrientsHelper.getBLUE(),
                    NutrientsHelper.getYELLOW(),
                    NutrientsHelper.getGREEN());
//            for (int y = 0; y < 525; y += 50) {
//                for (int x = 0; x < 175; x++) {
//                    bitmap.setPixel(x, y, Color.DKGRAY);
//                }
//            }
            for (int i = 0; i < 125; i++) {
                bitmap.setPixel(i, 524, Color.DKGRAY);
            }
            for (int i = 0; i < 125; i++) {
                bitmap.setPixel(i, 0, Color.DKGRAY);
            }
        }
        return bitmap;
    }

    /**
     * Draws all parts of chart.
     *
     * @param bitmap         — main bitmap
     * @param chartHeight    — height of empty place above chart
     * @param proteinsHeight - height of only proteins part
     * @param fatsHeight     - height of only fats part
     * @param chartIndent    - indent (left edge) of X axis
     * @param chartWidth     - right edge of X axis
     * @param blue           - proteins color
     * @param yellow         - fats color
     * @param green          - carbohydrates color
     * @return bitmap fully ready
     */
    private Bitmap drawNutrients(Bitmap bitmap,
                                 int chartHeight,
                                 int proteinsHeight,
                                 int fatsHeight,
                                 int chartIndent,
                                 int chartWidth,
                                 int blue,
                                 int yellow,
                                 int green) {
        for (int y = chartHeight; y < chartHeight + proteinsHeight; y++) {
            for (int x = chartIndent; x < chartWidth + chartIndent; x++) {
                bitmap.setPixel(x, y, blue);
            }
        }

        for (int y = chartHeight + proteinsHeight; y < chartHeight + proteinsHeight + fatsHeight; y++) {
            for (int x = chartIndent; x < chartIndent + chartWidth; x++) {
                bitmap.setPixel(x, y, yellow);
            }
        }

        for (int y = chartHeight + proteinsHeight + fatsHeight; y < 524; y++) {
            for (int x = chartIndent; x < chartIndent + chartWidth; x++) {
                bitmap.setPixel(x, y, green);
            }
        }
        return bitmap;
    }

//    /**
//     * Estimates nutrients heights.
//     *
//     * @param dailyGoods  - all meals today
//     * @param chartHeight - total height of nutrients
//     * @return proteins finish Y, fats finish Y
//     * carbohydrates finish Y = 525
//     */
//    private int[] estimateProteinsHeight(ArrayList<GoodInDayRation> dailyGoods, int chartHeight) {
//        double proteins = 0;
//        double fats = 0;
//        double carbons = 0;
//        double summary;
//
//        for (GoodInDayRation dailyGood : dailyGoods) {
//            proteins += dailyGood.getProteins();
//            fats += dailyGood.getFats();
//            carbons += dailyGood.getCarbohydrates();
//        }
//        summary = proteins + fats + carbons;
//
//        Log.e(TAG, String.format("proteins %d, fats %d, carbons %d, summary %d",
//                (int) proteins, (int) fats, (int) carbons, (int) summary));
//
//        /**
//         * Опять же, обратный порядок умножения, как в estimateHeight().
//         * Правильно: нутриент / сумма нутриентов * высота.
//         */
//        return new int[]{
//                (int) ((525 - chartHeight) * (proteins / summary)),
//                (int) ((525 - chartHeight) * (fats / summary))
//        };
//    }

    /**
     * Estimates total nutrients height.
     *
     * @param dailyGoods - String from database archive in object implementation.
     * @return — total nutrients height in pixels.
     */
    private int estimateHeight(GoodsArchiveObject dailyGoods) {
        int summary = dailyGoods.getCalories();

        /**
         * Изменил порядок, чтобы не морочаться с приведением к int.
         * Правильно было бы: калории за день / цель за день * высота.
         */
        Log.e(TAG, String.valueOf("summary: " + summary));
        Log.e(TAG, String.valueOf((summary < PersonalData.getGoalCalories())
                ? 525 * summary / PersonalData.getGoalCalories() : 525));
        return (summary < PersonalData.getGoalCalories())
                ? 525 * summary / PersonalData.getGoalCalories() : 525;
    }

    public Bitmap createBlankImage(GoodsArchiveObject object) {
        Bitmap transparentBitmap = Bitmap.createBitmap(125, 525, Bitmap.Config.ARGB_8888);
        for (int i = 0; i < 125; i++) {
            transparentBitmap.setPixel(i, 524, Color.DKGRAY);
        }
        for (int i = 0; i < 125; i++) {
            transparentBitmap.setPixel(i, 0, Color.DKGRAY);
        }
        return transparentBitmap;
    }
}
