package xyz.lego.trainee;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class LocalService extends Service {
    private static final String TAG = "LocalService";

    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "onBind");
        return new LocalBinder();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "onStartCommand " + intent + " " + flags + " " + startId);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy");
    }

    public void whoami() {
        Log.i(TAG, "Hey, I'm LocalService");
    }

    public class LocalBinder extends Binder {
        LocalService getService(){
            return LocalService.this;
        }
    }
}