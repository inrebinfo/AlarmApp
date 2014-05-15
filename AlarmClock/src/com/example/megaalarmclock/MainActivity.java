package com.example.megaalarmclock;

import java.io.IOException;
import java.util.Calendar;

import android.R.string;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TimePicker;
import android.widget.Toast;
import android.os.Build;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;

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

    public MediaPlayer mMediaPlayer;
    	
    public void saveAlarmTime(View view) throws IllegalArgumentException, SecurityException, IllegalStateException, IOException
    {
    	//Toast.makeText(this, "Hello World", Toast.LENGTH_SHORT).show();
    	/*AlertDialog dialog = new AlertDialog.Builder(this).create();
    	dialog.setCancelable(false);
    	dialog.setMessage("Test");
    	dialog.setButton("OK", new DialogInterface.OnClickListener() {
    	    @Override
    	    public void onClick(DialogInterface dialog, int which) {
    	        dialog.dismiss();                    
    	    }
    	});
    	dialog.show();*/
    	
    	TimePicker time = (TimePicker)findViewById(R.id.alarmTimePicker);
    	
    	int iHour = time.getCurrentHour();
    	int iMinute = time.getCurrentMinute();
    	
    	String sHour = time.getCurrentHour().toString(); 
    	String sMinute = time.getCurrentMinute().toString();
    	
    	String Message = "Wecker auf " + sHour + " Uhr und " + sMinute + " Minuten gestellt.";
    	
    	Builder alert = new AlertDialog.Builder(this); //ein builder
    	alert.setTitle("Weckzeit"); //titel
    	alert.setMessage(Message); //nachricht
    	
    	Calendar c = Calendar.getInstance(); 
    	int curMinute = c.get(Calendar.MINUTE);
    	int curHour = c.get(Calendar.HOUR_OF_DAY);

	    mMediaPlayer = new MediaPlayer();
	    
    	if(curHour == iHour && curMinute == iMinute)
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
    	
    	//positivebutton, gibt auch negativebutton, macht neuen onclicklistener 
    	// dadurch bei onclick alles mögliche machbar, hier nur dismiss
    	alert.setPositiveButton("OK", new DialogInterface.OnClickListener() { 
            @Override
            public void onClick(DialogInterface dialog,
                    int which) {
                dialog.dismiss();
                mMediaPlayer.stop();
            }
        });
    	alert.show();    
    	
    }
    

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
