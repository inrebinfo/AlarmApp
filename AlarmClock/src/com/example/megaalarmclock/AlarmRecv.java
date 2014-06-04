package com.example.megaalarmclock;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

import java.util.Calendar;

public class AlarmRecv extends WakefulBroadcastReceiver
{
    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;
  
    @Override
    public void onReceive(Context context, Intent intent)
    {   
    	String value1 = intent.getStringExtra("alarmid");
    	Log.i("RECV", "alarmid onreceive: "+value1);
    	
        Intent service = new Intent(context, ScheduleSrvc.class);
        service.putExtra("alarmid", value1);
         
        startWakefulService(context, service);
        
        Log.i("RECV", "starting wakeful service");
    }


    public void setAlarm(Context context, int hour, int minute, String alarmid)
    {
        alarmMgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmRecv.class);
        intent.putExtra("alarmid", alarmid);
        
        
        Log.i("RECV", "alarmid onreceive: "+intent.getStringExtra("alarmid"));
        
        int intentID = Integer.parseInt(alarmid);
        
        alarmIntent = PendingIntent.getBroadcast(context, intentID, intent, PendingIntent.FLAG_CANCEL_CURRENT);
               
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());

        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        
        Log.i("RECV", calendar.toString());
        
        Log.i("RECV", "hour = " + hour + ", minute = " + minute);
        
        alarmMgr.setInexactRepeating(AlarmManager.RTC_WAKEUP,  
                calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, alarmIntent);
        
        Log.i("RECV", "alarm is set");

    }


    public void cancelAlarm(Context context, int hour, int minute, String alarmid)
    {
    	alarmMgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmRecv.class);
        intent.putExtra("alarmid", alarmid);
        
        int intentID = Integer.parseInt(alarmid);
        
        alarmIntent = PendingIntent.getBroadcast(context, intentID, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        
        alarmMgr.cancel(alarmIntent);
    }
}
