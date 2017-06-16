package ru.xpendence.development.gimstopwatch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ru.xpendence.development.gimstopwatch.foodstuffs.FoodStuffsData;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();

        /** Проверка, не настало ли завтра */
        FoodStuffsData.checkDate(this);
    }
}
