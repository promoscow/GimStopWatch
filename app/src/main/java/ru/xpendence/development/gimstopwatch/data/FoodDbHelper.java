package ru.xpendence.development.gimstopwatch.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.util.Log;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.IOException;
import java.io.InputStream;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import ru.xpendence.development.gimstopwatch.MainActivity;
import ru.xpendence.development.gimstopwatch.foodstuffs.Good;
import ru.xpendence.development.gimstopwatch.foodstuffs.GoodInDayRation;
import ru.xpendence.development.gimstopwatch.foodstuffs.GoodsArchiveObject;
import ru.xpendence.development.gimstopwatch.util.ChartsGraphicsFactory;

/**
 * Created by promoscow on 26.05.17.
 * Class to work with database.
 */

public class FoodDbHelper extends SQLiteOpenHelper {

    private final String TAG = this.getClass().getSimpleName();

    Context context;

    /**
     * Name of main database.
     */
    private static final String DATABASE_NAME = "slimizator.db";

    /**
     * Database version. Increment on change.
     */
    private static final int DATABASE_VERSION = 1;

    /**
     * Конструктор {@link FoodDbHelper}.
     *
     * @param context Application context.
     */
    public FoodDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

//    public FoodDbHelper(Context context,
//                        String name,
//                        SQLiteDatabase.CursorFactory factory,
//                        int version) {
//        super(context, name, factory, version);
//    }
//
//    public FoodDbHelper(Context context,
//                        String name,
//                        SQLiteDatabase.CursorFactory factory,
//                        int version,
//                        DatabaseErrorHandler errorHandler) {
//        super(context, name, factory, version, errorHandler);
//    }

    /**
     * If database not exists, executing this method.
     * 1. Creating default databases - GoodsCatalog & GoodsArchiveObject.
     * 2. GoodsCatalog filling from calories.xls.
     * 3. calories.xls deleting
     *
     * @param db - main database.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_GOODS_CATALOG_TABLE = "CREATE TABLE "
                + FoodDbContract.GoodsCatalog.TABLE_NAME + " ("
                + FoodDbContract.GoodsCatalog._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + FoodDbContract.GoodsCatalog.NAME + " TEXT NOT NULL, "
                + FoodDbContract.GoodsCatalog.PROTEINS + " REAL NOT NULL, "
                + FoodDbContract.GoodsCatalog.FATS + " REAL NOT NULL, "
                + FoodDbContract.GoodsCatalog.CARBOHYDRATES + " REAL NOT NULL, "
                + FoodDbContract.GoodsCatalog.CALORIES + " INTEGER NOT NULL, "
                + FoodDbContract.GoodsCatalog.CATEGORY + " TEXT NOT NULL);";

        String SQL_CREATE_GOODS_ARCHIVE = "CREATE TABLE "
                + FoodDbContract.GoodsArchive.TABLE_NAME + " ("
                + FoodDbContract.GoodsArchive._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + FoodDbContract.GoodsArchive.PROTEINS + " DOUBLE NOT NULL, "
                + FoodDbContract.GoodsArchive.FATS + " DOUBLE NOT NULL, "
                + FoodDbContract.GoodsArchive.CARBOHYDRATES + " DOUBLE NOT NULL, "
                + FoodDbContract.GoodsArchive.CALORIES + " INTEGER NOT NULL, "
                + FoodDbContract.GoodsArchive.DATE + " TEXT NOT NULL);";

        String SQL_CREATE_TEMPORARY_STORAGE = "CREATE TABLE "
                + FoodDbContract.GoodsTemporaryStorage.TABLE_NAME + " ("
                + FoodDbContract.GoodsTemporaryStorage._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + FoodDbContract.GoodsTemporaryStorage.NAME + " TEXT NOT NULL, "
                + FoodDbContract.GoodsTemporaryStorage.PROTEINS + " DOUBLE NOT NULL, "
                + FoodDbContract.GoodsTemporaryStorage.FATS + " DOUBLE NOT NULL, "
                + FoodDbContract.GoodsTemporaryStorage.CARBOHYDRATES + " DOUBLE NOT NULL, "
                + FoodDbContract.GoodsTemporaryStorage.CALORIES + " INTEGER NOT NULL, "
                + FoodDbContract.GoodsTemporaryStorage.CATEGORY + " TEXT NOT NULL, "
                + FoodDbContract.GoodsTemporaryStorage.AMOUNT + " INTEGER NOT NULL, "
                + FoodDbContract.GoodsTemporaryStorage.DATE + " TEXT NOT NULL);";

        db.execSQL(SQL_CREATE_GOODS_CATALOG_TABLE);
        db.execSQL(SQL_CREATE_GOODS_ARCHIVE);
        db.execSQL(SQL_CREATE_TEMPORARY_STORAGE);

        FoodDbHelper.ExcelParser parser = new FoodDbHelper.ExcelParser(db);
        try {
            parser.fill(context.getAssets().open("calories.xls"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + FoodDbContract.GoodsArchive.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + FoodDbContract.GoodsCatalog.TABLE_NAME);
    }

    /**
     * This inner class filling database at first initialization.
     */
    private class ExcelParser {
        SQLiteDatabase db;

        ExcelParser(SQLiteDatabase db) {
            this.db = db;
        }

        /**
         * This method fills database from xls file.
         */
        public void fill(InputStream iStream) {

            InputStream inputStream;
            HSSFWorkbook workBook = null;
            try {
                inputStream = iStream;
                workBook = new HSSFWorkbook(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (workBook != null) {
                Sheet sheet = workBook.getSheetAt(0);
                for (Row row : sheet) {
                    Iterator<Cell> cells = row.iterator();
                    while (cells.hasNext()) {
                        Cell cell = cells.next();
                        if (cell.toString().equals("Name")) break;

                        String name = cell.getStringCellValue();
                        double proteins = cells.next().getNumericCellValue();
                        double fats = cells.next().getNumericCellValue();
                        double carbohydrates = cells.next().getNumericCellValue();
                        int calories = (int) cells.next().getNumericCellValue();
                        String category = cells.next().getStringCellValue();

                        Log.d(TAG, String.format("%s, %s, %s, %s, %d, %s",
                                name,
                                String.valueOf(proteins),
                                String.valueOf(fats),
                                String.valueOf(carbohydrates),
                                calories,
                                category));

                        ContentValues contentValues = new ContentValues();
                        contentValues.put(FoodDbContract.GoodsCatalog.NAME, name);
                        contentValues.put(FoodDbContract.GoodsCatalog.PROTEINS, proteins);
                        contentValues.put(FoodDbContract.GoodsCatalog.FATS, fats);
                        contentValues.put(FoodDbContract.GoodsCatalog.CARBOHYDRATES, carbohydrates);
                        contentValues.put(FoodDbContract.GoodsCatalog.CALORIES, calories);
                        contentValues.put(FoodDbContract.GoodsCatalog.CATEGORY, category);

                        db.insert(FoodDbContract.GoodsCatalog.TABLE_NAME, null, contentValues);
                    }
                }
            }
        }
    }

    /**
     * Service class to work with database.
     */
    public static class GoodsObjectsInit {

        /**
         * This method filling goods list from database
         *
         * @param database - main database with all tables.
         * @return HashMap with all goods catalog.
         */
        public static Map<String, Good> fillGoods(SQLiteDatabase database) {
            Map<String, Good> map = new HashMap<>();

            NumberFormat numberFormat = new DecimalFormat("0.00");
            numberFormat.setRoundingMode(RoundingMode.DOWN);

            Cursor cursor = database.query(FoodDbContract.GoodsCatalog.TABLE_NAME,
                    null, null, null, null, null, null, null);
            if (cursor.moveToFirst()) {
                int nameIndex = cursor.getColumnIndex(FoodDbContract.GoodsCatalog.NAME);
                int proteinsIndex = cursor.getColumnIndex(FoodDbContract.GoodsCatalog.PROTEINS);
                int fatsIndex = cursor.getColumnIndex(FoodDbContract.GoodsCatalog.FATS);
                int carbohydratesIndex = cursor.getColumnIndex(FoodDbContract.GoodsCatalog.CARBOHYDRATES);
                int caloriesIndex = cursor.getColumnIndex(FoodDbContract.GoodsCatalog.CALORIES);
                int categoryIndex = cursor.getColumnIndex(FoodDbContract.GoodsCatalog.CATEGORY);
                do {
                    String name = cursor.getString(nameIndex);
                    double proteins = Double.parseDouble(String.valueOf(cursor.getDouble(proteinsIndex))
                            .replace(",", "."));
                    double fats = Double.parseDouble(String.valueOf(cursor.getDouble(fatsIndex))
                            .replace(",", "."));
                    double carbohydrates = Double.parseDouble(String.valueOf(cursor.getDouble(carbohydratesIndex))
                            .replace(",", "."));
                    int calories = (int) cursor.getDouble(caloriesIndex);
                    String category = cursor.getString(categoryIndex);

                    Log.d("FROM_DATABASE", String.format("%s, %s, %s, %s, %d, %s",
                            name,
                            String.valueOf(proteins),
                            String.valueOf(fats),
                            String.valueOf(carbohydrates),
                            calories,
                            category));

                    Good good = new Good(name, proteins, fats, carbohydrates, calories, category);
                    System.out.println(good.toString());
                    map.put(good.getName(), good);
                } while (cursor.moveToNext());
            }
            cursor.close();
            return map;
        }

        /**
         * This method fills archiveRations & archiveCharts
         *
         * @param database - main database for application.
         * @return main TreeMap with archives.
         * Also creating archiveCharts.
         */
        public static Map<String, GoodsArchiveObject> fillArchiveGoods(SQLiteDatabase database) {
            Map<String, GoodsArchiveObject> map = new TreeMap<>();
            Cursor cursor = database.query(FoodDbContract.GoodsArchive.TABLE_NAME,
                    null, null, null, null, null, null);
            if (cursor.moveToFirst()) {
                int proteinsIndex = cursor.getColumnIndex(FoodDbContract.GoodsArchive.PROTEINS);
                int fatsIndex = cursor.getColumnIndex(FoodDbContract.GoodsArchive.FATS);
                int carbohydratesIndex = cursor.getColumnIndex(FoodDbContract.GoodsArchive.CARBOHYDRATES);
                int caloriesIndex = cursor.getColumnIndex(FoodDbContract.GoodsArchive.CALORIES);
                int dateIndex = cursor.getColumnIndex(FoodDbContract.GoodsArchive.DATE);

                do {
                    double proteinsTemp = Double.parseDouble(String.valueOf(cursor.getDouble(proteinsIndex))
                            .replace(",", "."));
                    double fatsTemp = Double.parseDouble(String.valueOf(cursor.getDouble(fatsIndex))
                            .replace(",", "."));
                    double carbohydratesTemp = Double.parseDouble(String.valueOf(cursor.getDouble(carbohydratesIndex))
                            .replace(",", "."));
                    int calories = (int) cursor.getDouble(caloriesIndex);
                    String date = cursor.getString(dateIndex);

                    NumberFormat numberFormat = new DecimalFormat("0.00");
                    numberFormat.setRoundingMode(RoundingMode.DOWN);

                    double proteins =
                            Double.parseDouble(String.valueOf(numberFormat.format(proteinsTemp))
                                    .replace(",", "."));
                    double fats =
                            Double.parseDouble(String.valueOf(numberFormat.format(fatsTemp))
                                    .replace(",", "."));
                    double carbohydrates =
                            Double.parseDouble(String.valueOf(numberFormat.format(carbohydratesTemp))
                                    .replace(",", "."));

                    Log.d("FROM_ARCHIVE_DB", String.format("%s, %s, %s, %d, %s",
                            String.valueOf(proteins),
                            String.valueOf(fats),
                            String.valueOf(carbohydrates),
                            calories,
                            date));

                    GoodsArchiveObject goodsArchiveObject = new GoodsArchiveObject(proteins, fats,
                            carbohydrates, calories, date);
                    System.out.println(goodsArchiveObject.toString());
                    map.put(date, goodsArchiveObject);
                } while (cursor.moveToNext());
            }
            for (String s : map.keySet()) {
                Log.e("FROM_DATABASE", map.get(s).toString());
            }
            cursor.close();
            return map;
        }

        // TODO: 11.06.17 Сделать 11 июня!!!
        /** Наполняет dailyGoods из goodsTemporaryStorage (базы данных). */
        public static ArrayList<GoodInDayRation> fillDailyGoodsFromDbStorage(SQLiteDatabase database) {
            ArrayList<GoodInDayRation> mList = new ArrayList<>();
            Cursor cursor = database.query(FoodDbContract.GoodsTemporaryStorage.TABLE_NAME,
                    null, null, null, null, null, null);
            if (cursor.moveToFirst()) {
                int nameIndex = cursor.getColumnIndex(FoodDbContract.GoodsTemporaryStorage.NAME);
                int proteinsIndex = cursor.getColumnIndex(FoodDbContract.GoodsTemporaryStorage.PROTEINS);
                int fatsIndex = cursor.getColumnIndex(FoodDbContract.GoodsTemporaryStorage.FATS);
                int carbohydratesIndex = cursor.getColumnIndex(FoodDbContract.GoodsTemporaryStorage.CARBOHYDRATES);
                int caloriesIndex = cursor.getColumnIndex(FoodDbContract.GoodsTemporaryStorage.CALORIES);
                int category = cursor.getColumnIndex(FoodDbContract.GoodsTemporaryStorage.CATEGORY);
                int amount = cursor.getColumnIndex(FoodDbContract.GoodsTemporaryStorage.AMOUNT);
                int date = cursor.getColumnIndex(FoodDbContract.GoodsTemporaryStorage.DATE);
            }
            cursor.close();
            return mList;
        }
    }

    /**
     * This method adds archive package to database
     *
     * @param date    - date which will be used as date of archive.
     * @param temp    - list which contains all daily meals. Will be used to learn nutrients & calories.
     * @param context - simple context.
     */
    public static void writeArchiveToDb(String date, ArrayList<GoodInDayRation> temp, Context context) {

        double proteins = 0;
        double fats = 0;
        double carbohydrates = 0;
        int calories = 0;

        for (GoodInDayRation good : temp) {
            proteins += good.getProteins();
            fats += good.getFats();
            carbohydrates += good.getCarbohydrates();
            calories += good.getCalories();
        }

        Log.e("TO_ARCHIVE_DB", String.format("%s, %s, %s, %d, %s",
                String.valueOf(proteins),
                String.valueOf(fats),
                String.valueOf(carbohydrates),
                calories,
                date));

        FoodDbHelper foodDbHelper = new FoodDbHelper(context);
        SQLiteDatabase database = foodDbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(FoodDbContract.GoodsArchive.PROTEINS, proteins);
        contentValues.put(FoodDbContract.GoodsArchive.FATS, fats);
        contentValues.put(FoodDbContract.GoodsArchive.CARBOHYDRATES, carbohydrates);
        contentValues.put(FoodDbContract.GoodsArchive.CALORIES, calories);
        contentValues.put(FoodDbContract.GoodsArchive.DATE, date);

        database.insert(FoodDbContract.GoodsArchive.TABLE_NAME, null, contentValues);
    }

    public Bitmap createBitmap(GoodsArchiveObject goodsArchiveObject) {
        return new ChartsGraphicsFactory(context).createChartImage(goodsArchiveObject);
    }
}
