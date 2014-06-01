package com.example.megaalarmclock;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootRecv extends BroadcastReceiver
{
    AlarmRecv alarm = new AlarmRecv();
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED"))
        {
            //alarm.setAlarm(context);
        }
    }
}
