package ru.xpendence.development.gimstopwatch.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class CheckDateService extends Service {
    private static boolean isDateChanged = false;

    public CheckDateService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
