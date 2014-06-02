package com.example.megaalarmclock;

import java.io.IOException;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

public class NewAlarmActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_alarm);
	}
	
	public void saveAlarmTime(View view) throws IllegalArgumentException, SecurityException, IllegalStateException, IOException
    {
    	
    	TimePicker time = (TimePicker)findViewById(R.id.alarmTimePicker);
    	EditText inputSnoozeTime = (EditText)findViewById(R.id.tbSnoozeTime);
    	CheckBox inputOneTimeSnooze = (CheckBox)findViewById(R.id.cbOneTimeSnooze);
    	
    	boolean oneTimeSnooze = false;
    	
    	if (inputOneTimeSnooze.isChecked())
    	{
    		oneTimeSnooze = true;
        }
    	else
    	{
    		oneTimeSnooze = false;
    	}
    	    	
    	String snoozeTime = inputSnoozeTime.getText().toString();
    	
    	int iSnooze = 0;
    	
    	if(snoozeTime != "")
    	{
    		iSnooze = Integer.parseInt(snoozeTime);
    	}
    	else
    	{
    		iSnooze = 5;
    	}
    	
    	int iHour = time.getCurrentHour();
    	int iMinute = time.getCurrentMinute();
    	
    	String sHour = time.getCurrentHour().toString(); 
    	String sMinute = time.getCurrentMinute().toString();
    	
    	String Message = "Wecker auf " + sHour + " Uhr und " + sMinute + " Minuten gestellt.";
    	
    	//write snoozetime to file
        AlarmSettingsObject settings = new AlarmSettingsObject();
        settings.setSnooze(iSnooze);
        
        AlarmKeyGenerator idGen = new AlarmKeyGenerator();
        String alarmID = idGen.Generate(8);
        settings.setID(alarmID);
        
        settings.setOneTimeSnooze(oneTimeSnooze);
        
        Log.i("NEW", "settingsobject snooze: "+String.valueOf(settings.getSnooze()));
        Log.i("NEW", "settingsobject id: "+settings.getID());
        
        String alarmSetting = settings.getID()+";"+settings.getSnooze()+";"+settings.getOneTimeSnooze()+";"+settings.getURL()+";";
       
        SettingsHandler settingsHandler = new SettingsHandler(this.getApplicationContext());
        
        settingsHandler.addAlarm(alarmSetting);
      

        AlarmRecv alarm = new AlarmRecv();
        alarm.setAlarm(this, iHour, iMinute, alarmID);        
        
        //daten generieren (identifier, snooze, url)
        //ins file schreiben
        //identifier an schedulesrvc bringen (vielleicht das ganze erst in alarmrecv generieren)
        //wenn alarmfiredactivity hat identifier, lädt snooze und url in sich rein und speichert in variablen
        
        
        
        
        
        Toast.makeText(getApplicationContext(), Message, Toast.LENGTH_LONG).show();
        Toast.makeText(getApplicationContext(), "snooze time: "+snoozeTime, Toast.LENGTH_LONG).show();
        
    	/*
    	Builder alert = new AlertDialog.Builder(this); //ein builder
    	alert.setTitle("Weckzeit"); //titel
    	alert.setMessage(Message); //nachricht
    	
    	alert.setPositiveButton("OK", new DialogInterface.OnClickListener() { 
            @Override
            public void onClick(DialogInterface dialog,
                    int which) {
                dialog.dismiss();
            }
        });
    	alert.show();
    		    
    	//positivebutton, gibt auch negativebutton, macht neuen onclicklistener 
    	// dadurch bei onclick alles mögliche machbar, hier nur dismiss
    	alert.setPositiveButton("OK", new DialogInterface.OnClickListener() { 
            @Override
            public void onClick(DialogInterface dialog,
                    int which) {
                dialog.dismiss();
            }
        });
    	alert.show();    
    	*/
    	
    }
}
