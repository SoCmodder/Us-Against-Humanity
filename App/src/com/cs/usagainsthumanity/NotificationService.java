package com.cs.usagainsthumanity;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created with IntelliJ IDEA.
 * User: Stewart
 * Date: 4/22/13
 * Time: 5:23 PM
 * To change this template use File | Settings | File Templates.
 */
public class NotificationService extends Service {

    private Timer timer;

    private TimerTask checkNotification = new TimerTask() {
        @Override
        public void run() {
            Log.i("Service", "Timer task doing work");
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void onCreate(){
        super.onCreate();
        Log.i("Service", "Creating Task");

        timer = new Timer("Check Timer");
        timer.schedule(checkNotification, 1000L, 60 * 1000L);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();

        timer.cancel();
        timer = null;
    }
}
