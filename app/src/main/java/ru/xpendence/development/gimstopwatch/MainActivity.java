package ru.xpendence.development.gimstopwatch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ru.xpendence.development.gimstopwatch.util.PersonalData;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PersonalData data = new PersonalData();

        Intent intent = new Intent(MainActivity.this, AppActivity.class);
        startActivity(intent);
    }

}
