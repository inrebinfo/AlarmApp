package com.example.megaalarmclock;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class AlarmFiredActivity extends Activity
{

	
	private MediaPlayer mMediaPlayer;
	private Vibrator vVibrator;
	private Timer timer = new Timer();
	private Button snoozeBtn;
	private Button killBtn;
	private String alarmID;
	private int snoozeTime;
	private AlarmSettingsObject settingsObj;
	private boolean snoozed = false;

    final Handler h = new Handler();
    float leftVol = 0f;
    float rightVol = 0f;

	private Handler btnHandler = new Handler()
	{ 
	     public void handleMessage(Message msg)
	     {
	    	 if(snoozed == false)
	    	 {
	    		 snoozeBtn.setEnabled(true);
	    	 }
	     } 
	 }; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_alarm_fired);
		
		Intent intent = getIntent();
		
		String value1 = intent.getStringExtra("alarmid");
    	Log.i("FIRED", "alarmid fired: "+value1);
		
    	SettingsHandler settingsHandler = new SettingsHandler(this.getApplicationContext());
    	
    	settingsObj = settingsHandler.getAlarm(value1);
    	
    	snoozeTime = settingsObj.getSnooze();
    	
    	playSound();
        vibrate();
        
        settingsObj.setActive("false");
        
        settingsHandler.editAlarm(settingsObj);
	}
	
	public void snoozeBtnClicked(View view) throws IllegalArgumentException, SecurityException, IllegalStateException, IOException
	{
		stopVibrate();
		stopPlayer();
		snoozeAction();
		
		if(settingsObj.getOneTimeSnooze().equals("true"))
		{
			snoozed = true;
		}

		snoozeBtn = (Button)findViewById(R.id.snoozeBtn);
		snoozeBtn.setEnabled(false);
		
		Log.i("FIRED", "snooze btn clicked");
	}
	
	public void killBtnClicked(View view) throws IllegalArgumentException, SecurityException, IllegalStateException, IOException
	{
		stopVibrate();
		stopPlayer();

		killBtn = (Button)findViewById(R.id.killBtn);
		killBtn.setEnabled(false);
		snoozeBtn = (Button)findViewById(R.id.snoozeBtn);
		snoozeBtn.setEnabled(false);
		
		timer.cancel(); 
		
		Log.i("FIRED", "kill btn clicked");
		
		finish();
	}
	
	public void stopVibrate()
	{
		vVibrator.cancel();
	}
	
	public void stopPlayer()
	{
		mMediaPlayer.stop();
	}
	
	private void playSound()
    {
    	mMediaPlayer = new MediaPlayer();	
    	mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
    	
    	try
    	{
    		Uri soundUri;
    		
    		if(!settingsObj.getURL().equals("null"))
    		{
    			try
    			{
    				soundUri = Uri.parse(settingsObj.getURL());
    			}
    			catch(Exception ex)
    			{
    				Log.i("FIRED", ex.getMessage());
        			soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
    			}
    		}
    		else
    		{
    			soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
    		}
    		//soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
    		Log.i("FIRED", "uri = "+soundUri.toString());
    		
		    mMediaPlayer.setDataSource(this, soundUri);
		    final AudioManager audioManager = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);
	
		    
		    if (audioManager.getStreamVolume(AudioManager.STREAM_ALARM) != 0) {
		        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_ALARM);
		        mMediaPlayer.setLooping(true);
		        mMediaPlayer.prepare();
		        mMediaPlayer.start();
		        
		    	
		        
	            Runnable increaseVol = new Runnable(){
	                public void run(){
	                	mMediaPlayer.setVolume(leftVol, rightVol);
	                    if(leftVol < 2.0f){
	                        leftVol += .05f;
	                        rightVol += .05f;
	                        h.postDelayed(this, 1000);
	                    }
	                }
	            };


	            h.post(increaseVol);
		    }
    	}
    	catch(Exception ex)
    	{
    		Log.i("FIRED", ex.getMessage());
    	}
    }
	    
    private void vibrate()
    {
    	long[] pattern = {1000, 250, 1000, 250};
    	vVibrator = (Vibrator)getSystemService(this.VIBRATOR_SERVICE);
    	vVibrator.vibrate(pattern, 0);	
    }
    
    public synchronized void snoozeAction()
    {
		timer.cancel(); //this will cancel the current task. if there is no active task, nothing happens
		timer = new Timer();
		
		TimerTask action = new TimerTask()
		{
			public void run()
			{
				playSound();
				vibrate();
				            	
				btnHandler.obtainMessage(1).sendToTarget();
			}
		};
		
		timer.schedule(action, snoozeTime*60000);
    }
}
