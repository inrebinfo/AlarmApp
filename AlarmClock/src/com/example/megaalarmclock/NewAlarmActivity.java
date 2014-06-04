package com.example.megaalarmclock;

import java.io.IOException;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class NewAlarmActivity extends Activity {

	private String toneURL;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_alarm);
	}
	
	public void selectTone(View view) throws IllegalArgumentException, SecurityException, IllegalStateException, IOException
	{
		Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
		intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_NOTIFICATION);
		intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, "Select Tone");
		intent.putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI, (Uri) null);
		this.startActivityForResult(intent, 5);
	}
	
	@Override
	protected void onActivityResult(final int requestCode, final int resultCode, final Intent intent)
	{
		if (resultCode == Activity.RESULT_OK && requestCode == 5)
		{
			Uri uri = intent.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);
			
			if (uri != null)
			{
				this.toneURL = uri.toString();
				Log.i("NEW", "tone: "+toneURL);
			}
			else
			{
				this.toneURL = null;
				Log.i("NEW", "tone: "+toneURL);
			}
			          
			  
			String[] projection = {MediaStore.MediaColumns.DATA};
			String path = "";
			ContentResolver cr = getApplicationContext().getContentResolver();
			Cursor metaCursor = cr.query(uri, projection, null, null, null);
			if (metaCursor != null)
			{
				try
				{
					if (metaCursor.moveToFirst())
					{
						path = metaCursor.getString(0);
					}
				}
				finally
				{
					metaCursor.close();
				}
			}
			    
			TextView chosenSound = (TextView)findViewById(R.id.lblAlarmTone);
			    
			chosenSound.setText(path);
			            
		}
	}
	
	public void saveAlarmTime(View view) throws IllegalArgumentException, SecurityException, IllegalStateException, IOException
    {
    	
    	TimePicker time = (TimePicker)findViewById(R.id.alarmTimePicker);
    	EditText inputSnoozeTime = (EditText)findViewById(R.id.tbSnoozeTime);
    	CheckBox inputOneTimeSnooze = (CheckBox)findViewById(R.id.cbOneTimeSnooze);
    	
    	String oneTimeSnooze = "false";
    	
    	if (inputOneTimeSnooze.isChecked())
    	{
    		oneTimeSnooze = "true";
        }
    	else
    	{
    		oneTimeSnooze = "false";
    	}
    	    	
    	String snoozeTime = inputSnoozeTime.getText().toString();
    	
    	int iSnooze = 0;
    	
    	if(snoozeTime.length() != 0)
    	{
    		iSnooze = Integer.parseInt(snoozeTime);
    		Log.i("NEW", "snoozetime parsed");
    	}
    	else
    	{
    		iSnooze = 5;
    		Log.i("NEW", "snoozetime not parsed");
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
        String alarmID = idGen.Generate();
        settings.setID(alarmID);

        settings.setHour(iHour);
        settings.setMinute(iMinute);
        
        settings.setOneTimeSnooze(oneTimeSnooze);
        
        settings.setURL(toneURL);
        
        settings.setActive("true");
        
        //id;active;hour;minute;snooze;onetimesnooze;url;
        
        
        String alarmSetting =	settings.getID()+";"+
	        					settings.getActive()+";"+
	        					settings.getHour()+";"+
	        					settings.getMinute()+";"+
	        					settings.getSnooze()+";"+
	        					settings.getOneTimeSnooze()+";"+
	        					settings.getURL()+";";
        
       
        SettingsHandler settingsHandler = new SettingsHandler(this.getApplicationContext());
        
        settingsHandler.addAlarm(alarmSetting);
      

        AlarmRecv alarm = new AlarmRecv();
        alarm.setAlarm(this, iHour, iMinute, alarmID);        

        
        
        Toast.makeText(getApplicationContext(), Message, Toast.LENGTH_LONG).show();
        
        finish();
        
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
