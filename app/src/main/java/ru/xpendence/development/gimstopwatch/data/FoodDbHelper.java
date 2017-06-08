package ru.xpendence.development.gimstopwatch.data;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by promoscow on 26.05.17.
 * Class to work with database.
 */

public class FoodDbHelper extends SQLiteOpenHelper {

    private final String TAG = this.getClass().getSimpleName();

    /** Name of main database. */
    private static final String DATABASE_NAME = "slimizator.db";

    /**
     * Database version. Increment on change.
     */
    private static final int DATABASE_VERSION = 1;

    /**
     * Конструктор {@link FoodDbHelper}.
     * @param context Application context.
     */
    public FoodDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public FoodDbHelper(Context context,
                        String name,
                        SQLiteDatabase.CursorFactory factory,
                        int version) {
        super(context, name, factory, version);
    }

    public FoodDbHelper(Context context,
                        String name,
                        SQLiteDatabase.CursorFactory factory,
                        int version,
                        DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_GOODS_CATALOG_TABLE = "CREATE TABLE "
                + FoodDbContract.GoodsCatalog.TABLE_NAME + " ("
                + FoodDbContract.GoodsCatalog._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + FoodDbContract.GoodsCatalog.NAME + " TEXT NOT NULL, "
                + FoodDbContract.GoodsCatalog.PROTEINS + " TEXT NOT NULL, "
                + FoodDbContract.GoodsCatalog.FATS + " TEXT NOT NULL, "
                + FoodDbContract.GoodsCatalog.CARBOHYDRATES + " TEXT NOT NULL, "
                + FoodDbContract.GoodsCatalog.CALORIES + " TEXT NOT NULL, "
                + FoodDbContract.GoodsCatalog.CATEGORY + " TEXT NOT NULL);";

        String SQL_CREATE_GOODS_ARCHIVE = "CREATE TABLE "
                + FoodDbContract.GoodsArchive.TABLE_NAME + " ("
                + FoodDbContract.GoodsArchive._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + FoodDbContract.GoodsArchive.PROTEINS + " TEXT NOT NULL, "
                + FoodDbContract.GoodsArchive.FATS + " TEXT NOT NULL, "
                + FoodDbContract.GoodsArchive.CARBOHYDRATES + " TEXT NOT NULL, "
                + FoodDbContract.GoodsArchive.CALORIES + " TEXT NOT NULL, "
                + FoodDbContract.GoodsArchive.DATE + " TEXT NOT NULL);";

        db.execSQL(SQL_CREATE_GOODS_CATALOG_TABLE);
        db.execSQL(SQL_CREATE_GOODS_ARCHIVE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + FoodDbContract.GoodsArchive.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + FoodDbContract.GoodsCatalog.TABLE_NAME);
    }
}
