package com.example.megaalarmclock;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.content.Context;
import android.util.Log;

public class SettingsHandler
{
	private String _filename = "settings.csv";
	private Context _context;
	
	public SettingsHandler(Context context)
	{
		_context = context;
	}
	
	public void addAlarm(String alarm)
	{    	
		try
		{
	    	BufferedWriter bw = new BufferedWriter(new FileWriter(new File(_context.getFilesDir()+"/"+_filename), true));
		    bw.write(alarm);
		    bw.newLine();
		    bw.close();
		}
		catch(Exception ex) { }
	}
	
	public void delAlarm(String alarmid)
	{
		List<AlarmSettingsObject> objList = new ArrayList<AlarmSettingsObject>();
		
		//create list of all entries in our settings file
		try
        {
	        InputStream inputStream = new FileInputStream(_context.getFilesDir()+"/"+_filename);
	        BufferedReader r = new BufferedReader(new InputStreamReader(inputStream));
	        StringBuilder total = new StringBuilder();
	        String line;
	        while ((line = r.readLine()) != null)
	        {
	        	String[] parts = line.split(";");
	        	AlarmSettingsObject tempObj = new AlarmSettingsObject();
	        	tempObj.setID(parts[0]);
	        	tempObj.setSnooze(Integer.valueOf(parts[1]));
	        	if(parts[2] == "true")
	        	{
	        		tempObj.setOneTimeSnooze(true);
	        	}
	        	else
	        	{
	        		tempObj.setOneTimeSnooze(false);
	        	}
	        	tempObj.setURL(parts[3]);
	            //total.append(line);
	        	Log.i("SET", tempObj.toString());
	        	
	        	objList.add(tempObj);
	        }
	        
	        r.close();
        }
        catch(Exception ex){ }
		
		//search for the setting line we want to delete
		Iterator<AlarmSettingsObject> it = objList.iterator();
		while (it.hasNext())
		{
			AlarmSettingsObject obj = it.next();
			Log.i("ITER", obj.getID());
			if(obj.getID().equals(alarmid))
			{
				it.remove();
				Log.i("SETTINGS", "found and deleted "+alarmid);
			}
		}

		
		File file = new File(_context.getFilesDir()+"/"+_filename);
		 
		if(file.delete())
		{
			Log.i("SETTINGS", "file deleted");
		}
		else
		{
			Log.i("SETTINGS", "file not deleted");
		}
		
		//now write the list back to the file
		try
		{
			BufferedWriter bw = new BufferedWriter(new FileWriter(new File(_context.getFilesDir()+"/"+_filename), true));
			
			for(AlarmSettingsObject obj : objList)
			{
				String alarmSetting = obj.getID()+";"+obj.getSnooze()+";"+obj.getOneTimeSnooze()+";"+obj.getURL()+";";
				bw.write(alarmSetting);
			    bw.newLine();
			}
			Log.i("SETTINGS", "file new written");
		    bw.close();
		}
		catch(Exception ex) { }
	}
	
	public AlarmSettingsObject getAlarm(String alarmid)
	{
		AlarmSettingsObject alarmObj = new AlarmSettingsObject();
		
		return alarmObj;
	}
}
