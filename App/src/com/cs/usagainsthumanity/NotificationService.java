package com.cs.usagainsthumanity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;
import com.cs.usagainsthumanity.Adapters.GameArrayAdapter;
import com.cs.usagainsthumanity.Objects.Game;
import com.savagelook.android.UrlJsonAsyncTask;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Stewart
 * Date: 4/22/13
 * Time: 5:23 PM
 * To change this template use File | Settings | File Templates.
 */
public class NotificationService extends Service {

    private Timer timer;
    private NotificationManager mgr = null;
    private static final int NOTIFY_ID = 1337455;
    private Random random = null;
    private boolean flag = false;
    private SharedPreferences mPreferences;

    private TimerTask checkNotification = new TimerTask() {
        @Override
        public void run() {
            Log.i("Service", "Timer task doing work");
            DefaultHttpClient httpclient = new DefaultHttpClient();
            HttpGet request = new HttpGet("http://r06sjbkcc.device.mst.edu:3000/api/v1/notifications");
            request.setHeader("Authorization", "Token token=\"" + mPreferences.getString("AuthToken", "") + "\"");
            Log.d("String", "Request: " + request.getAllHeaders()[0].toString());
            HttpResponse response = null;
            try {
                response = httpclient.execute(request);
                JSONObject jsonObject = new JSONObject(convertStreamToString(response.getEntity().getContent()));
                JSONObject data = jsonObject.getJSONObject("data");
                JSONArray jsonNotificatons = data.getJSONArray("notifications");
                int length = jsonNotificatons.length();
                for (int i = 0; i < length; i++) {
                    Notification note = buildNotification(jsonNotificatons.getJSONObject(i));
                    mgr.notify(random.nextInt(), note);
                }


            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (JSONException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            httpclient.getConnectionManager().shutdown();
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
        mPreferences = getSharedPreferences("CurrentUser", MODE_PRIVATE);
        int minutes = mPreferences.getInt("Check", 20);
        random = new Random();
        mgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        timer = new Timer("Check Timer");
        timer.schedule(checkNotification, 1000L, minutes * 60 * 1000L);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();

        timer.cancel();
        timer = null;
    }



    private Notification buildNotification(JSONObject jsonObject) {
        try {
            Intent intent;
            int id = random.nextInt();
            if(jsonObject.getInt("status") < 5){
                intent = new Intent(NotificationService.this, GameActivity.class);
                intent.putExtra("gameID", jsonObject.getInt("game_id"));
            }else{
                intent = new Intent(NotificationService.this, ViewGameInfoActivity.class);
                intent.putExtra("gameID", jsonObject.getInt("game_id"));
            }
            PendingIntent pendingIntent = PendingIntent.getActivity(NotificationService.this, id, intent,
                    Intent.FLAG_ACTIVITY_CLEAR_TASK);
            String message;
            switch (jsonObject.getInt("status")){
                case 1:
                    message = "Game has started";
                    break;
                case 2:
                    message = "Next round";
                    break;
                case 3:
                    message = "You won the last round";
                    break;
                case 4:
                    message = "All cards submitted";
                    break;
                case 5:
                    message = "Game over";
                    break;
                default:
                    message = "Error";
                    break;

            }
            Notification.Builder noteBuilder = new Notification.Builder(NotificationService.this)
                    .setContentTitle(message)
                    .setContentText(jsonObject.getString("message"))
                    .setContentIntent(pendingIntent).setSmallIcon(R.drawable.ic_stat_note).setNumber(-1);
            Notification note = noteBuilder.getNotification();
            note.flags |= Notification.FLAG_AUTO_CANCEL;


            return note;


        } catch (JSONException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return null;
    }

    private static String convertStreamToString(InputStream is) {
		/*
		 * To convert the InputStream to String we use the BufferedReader.readLine()
		 * method. We iterate until the BufferedReader return null which means
		 * there's no more data to read. Each line will appended to a StringBuilder
		 * and returned as String.
		 */
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}
