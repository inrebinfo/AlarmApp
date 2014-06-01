package com.example.megaalarmclock;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Timer;
import java.util.TimerTask;

import android.R.bool;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.os.Build;

public class AlarmFiredActivity extends ActionBarActivity {


    public MediaPlayer mMediaPlayer;
	public Vibrator vVibrator;
	public Timer timer = new Timer();
	public Button snoozeBtn;
	public Button killBtn;
	public String alarmID;
	private Handler btnHandler = new Handler()
	{ 
	     public void handleMessage(Message msg)
	     {
	    	 snoozeBtn.setEnabled(true);
	     } 
	 }; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_alarm_fired);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}

		Intent intent = getIntent();
		
		String value1 = intent.getStringExtra("alarmid");
    	Log.i("FIRED", "alarmid fired: "+value1);
		
    	playSound();
        vibrate();
        
		
	}
	
	public void snoozeBtnClicked(View view) throws IllegalArgumentException, SecurityException, IllegalStateException, IOException
	{
		stopVibrate();
		stopPlayer();
		snoozeAction();

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
		
		Log.i("FIRED", "kill btn clicked");
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
	    	Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
		    mMediaPlayer.setDataSource(this, soundUri);
		    final AudioManager audioManager = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);
	
		    if (audioManager.getStreamVolume(AudioManager.STREAM_ALARM) != 0) {
		        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_ALARM);
		        mMediaPlayer.setLooping(true);
		        mMediaPlayer.prepare();
		        mMediaPlayer.start();
		    }
    	}
    	catch(Exception ex)
    	{
    		Log.i("RECV", ex.getMessage());
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

        timer.schedule(action, 10000);
    }
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.alarm_fired, menu);
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
			View rootView = inflater.inflate(R.layout.fragment_alarm_fired,
					container, false);
			return rootView;
		}
	}

}
