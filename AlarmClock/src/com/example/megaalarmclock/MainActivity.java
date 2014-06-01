package com.example.megaalarmclock;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Calendar;

import org.xmlpull.v1.XmlSerializer;

import android.R.string;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;
import android.os.Build;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
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
        
        Log.i("MAIN", "settingsobject snooze: "+String.valueOf(settings.getSnooze()));
        Log.i("MAIN", "settingsobject id: "+settings.getID());
        
        String alarmSetting = settings.getID()+";"+settings.getSnooze()+";"+settings.getOneTimeSnooze()+";"+settings.getURL()+";";
       
        SettingsHandler settingsHandler = new SettingsHandler(this.getApplicationContext());
        
        settingsHandler.addAlarm(alarmSetting);
        settingsHandler.delAlarm("zZNFuwVp");
      

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
    
    /*@Override
    public void onDestroy()
    {
        super.onDestroy();
        //File file = new File(this.getFilesDir()+"/settings.csv");
        //boolean deleted = file.delete();
    }*/


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }

}
