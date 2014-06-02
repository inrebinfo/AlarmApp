package com.example.megaalarmclock;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Vibrator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ScheduleSrvc extends IntentService
{
	public ScheduleSrvc()
	{
        super("ScheduleSrvc");
    }
	
    public static final int NOTIFICATION_ID = 1;
    private NotificationManager mNotificationManager;
    NotificationCompat.Builder builder;
    
    public int snoozeTime;

    @Override
    protected void onHandleIntent(Intent intent)
    {
    	
    	String alarmid = intent.getStringExtra("alarmid");
    	
    	Log.i("SRVC", "alarmid: "+alarmid);
    	Log.i("SRVC", "intent fired");
    	
    	Intent intent2 = new Intent(this, AlarmFiredActivity.class);
    	intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    	intent2.setAction(Intent.ACTION_MAIN);
    	intent2.addCategory(Intent.CATEGORY_LAUNCHER);
    	intent2.putExtra("alarmid", alarmid);
    	startActivity(intent2);
    
        sendNotification("STEH AUF!");
        
        AlarmRecv.completeWakefulIntent(intent);
    }
    
    private void sendNotification(String msg)
    {
        mNotificationManager = (NotificationManager)this.getSystemService(Context.NOTIFICATION_SERVICE);
    
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, new Intent(this, NewAlarmActivity.class), 0);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
        .setSmallIcon(R.drawable.ic_launcher)
        .setContentTitle("MegaAlarmClock")
        .setStyle(new NotificationCompat.BigTextStyle()
        .bigText(msg))
        .setContentText(msg)
        .setAutoCancel(true);

        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
        
        Log.i("SRVC", "notification posted");

    }
}
