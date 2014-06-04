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
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class EditAlarmActivity extends Activity {

	private String toneURL;
	
	private String alarmID;
	private AlarmSettingsObject obj;
	private SettingsHandler settingsHandler;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_alarm);
		
		Intent intent = getIntent();
		String alarmid = intent.getStringExtra("alarmid");
		alarmID = alarmid;
		
		settingsHandler = new SettingsHandler(getApplicationContext());
		
		obj = settingsHandler.getAlarm(alarmid);
		
		TimePicker time = (TimePicker)findViewById(R.id.alarmTimePickerEdit);
		EditText snooze = (EditText)findViewById(R.id.tbSnoozeTimeEdit);
		CheckBox onetime = (CheckBox)findViewById(R.id.cbOneTimeSnoozeEdit);
		TextView sound = (TextView)findViewById(R.id.lblAlarmToneEdit);
		CheckBox active = (CheckBox)findViewById(R.id.cbIsActive);
		
		time.setCurrentHour(obj.getHour());
		time.setCurrentMinute(obj.getMinute());
		
		snooze.setText(String.valueOf(obj.getSnooze()));
		
		if(obj.getOneTimeSnooze().equalsIgnoreCase("true"))
		{
			onetime.setChecked(true);
		}
		else
		{
			onetime.setChecked(false);
		}
		
		if(obj.getActive().equalsIgnoreCase("true"))
		{
			active.setChecked(true);
		}
		else
		{
			active.setChecked(false);
		}
		
		Log.i("EDIT", "isActive = "+obj.getActive());
		Log.i("EDIT", "onetime = "+obj.getOneTimeSnooze());
	  	
	  	Uri soundUri;
		
		if(obj.getURL() != "null")
		{
			try
			{
				soundUri = Uri.parse(obj.getURL());
			}
			catch(Exception ex)
			{
				Log.i("EDIT", ex.getMessage());
    			soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
			}
		}
		else
		{
			soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
		}
		
		String[] projection = {MediaStore.MediaColumns.DATA};
		String path = "";
		ContentResolver cr = getApplicationContext().getContentResolver();
		Cursor metaCursor = cr.query(soundUri, projection, null, null, null);
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
	  	
	  	sound.setText(path);
	}
	
	public void editAlarmTime(View view) throws IllegalArgumentException, SecurityException, IllegalStateException, IOException
	{
		TimePicker time = (TimePicker)findViewById(R.id.alarmTimePickerEdit);
    	EditText inputSnoozeTime = (EditText)findViewById(R.id.tbSnoozeTimeEdit);
    	CheckBox inputOneTimeSnooze = (CheckBox)findViewById(R.id.cbOneTimeSnoozeEdit);
    	CheckBox inputIsActive = (CheckBox)findViewById(R.id.cbIsActive);
    	
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
    		Log.i("EDIT", "snoozetime parsed");
    	}
    	else
    	{
    		iSnooze = 5;
    		Log.i("EDIT", "snoozetime not parsed");
    	}
    	
    	int iHour = time.getCurrentHour();
    	int iMinute = time.getCurrentMinute();
    	
    	String sHour = time.getCurrentHour().toString(); 
    	String sMinute = time.getCurrentMinute().toString();
    	  	
    	//write snoozetime to file
        AlarmSettingsObject settings = new AlarmSettingsObject();
        settings.setSnooze(iSnooze);
        
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
        
       settingsHandler.editAlarm(settings);
       

       Toast.makeText(getApplicationContext(), "Alarm changed...", Toast.LENGTH_LONG).show();
       finish();
        
	}
	
	public void deleteAlarmTime(View view) throws IllegalArgumentException, SecurityException, IllegalStateException, IOException
	{
		settingsHandler.delAlarm(alarmID);
		
		TimePicker time = (TimePicker)findViewById(R.id.alarmTimePickerEdit);
		
		int iHour = time.getCurrentHour();
    	int iMinute = time.getCurrentMinute();
    	    	
		AlarmRecv recv = new AlarmRecv();
		recv.cancelAlarm(this, iHour, iMinute, alarmID);
		
		Toast.makeText(getApplicationContext(), "Alarm deleted...", Toast.LENGTH_LONG).show();
		
		finish();
	}	
	
	public void selectToneEdit(View view) throws IllegalArgumentException, SecurityException, IllegalStateException, IOException
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
				Log.i("EDIT", "tone: "+toneURL);
			}
			else
			{
				this.toneURL = null;
				Log.i("EDIT", "tone: "+toneURL);
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
			    
			TextView chosenSound = (TextView)findViewById(R.id.lblAlarmToneEdit);
			    
			chosenSound.setText(path);
			            
		}
	}
}
