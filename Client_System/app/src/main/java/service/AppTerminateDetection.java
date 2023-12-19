package service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import utils.SharedPreferencesManager;

public class AppTerminateDetection extends Service {


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        Log.i("Service", "App Terminated");

        SharedPreferencesManager.removeAccessToken(getApplicationContext());

        stopSelf();
    }
}
