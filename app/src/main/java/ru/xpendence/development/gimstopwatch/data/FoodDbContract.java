package ru.xpendence.development.gimstopwatch.data;

import android.provider.BaseColumns;

/**
 * Created by promoscow on 26.05.17.
 */

public class FoodDbContract {

    private FoodDbContract() {
    }

    public static final class GoodsCatalog implements BaseColumns {
        public final static String TABLE_NAME = "goods";

        public final static String _ID = BaseColumns._ID;
        public final static String NAME = "name";
        public final static String PROTEINS = "proteins";
        public final static String FATS = "fats";
        public final static String CARBOHYDRATES = "carbohydrates";
        public final static String CALORIES = "calories";
        public final static String CATEGORY = "category";
    }

    public static final class GoodsArchive implements BaseColumns {
        public final static String TABLE_NAME = "archiveGoods";

        public final static String _ID = BaseColumns._ID;
        public final static String PROTEINS = "proteins";
        public final static String FATS = "fats";
        public final static String CARBOHYDRATES = "carbohydrates";
        public final static String CALORIES = "calories";
        public final static String DATE = "date";
    }

    /**
     * Алгоритм работы с этой БД:
     * При добавлении продукта — запись из объекта сюда.
     * При смене времени — очистка БД.
     * onResume(); — запись в объекты из БД.
     */
    public static final class GoodsTemporaryStorage implements BaseColumns {
        public final static String TABLE_NAME = "goodsTemporaryStorage";

        public final static String _ID = BaseColumns._ID;
        public final static String NAME = "name";
        public final static String PROTEINS = "proteins";
        public final static String FATS = "fats";
        public final static String CARBOHYDRATES = "carbohydrates";
        public final static String CALORIES = "calories";
        public final static String CATEGORY = "category";
        public final static String AMOUNT = "amount";
        public final static String DATE = "date";
    }
}
