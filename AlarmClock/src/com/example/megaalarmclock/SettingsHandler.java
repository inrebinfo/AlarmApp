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
		//id;active;hour;minute;snooze;onetimesnooze;url;
		
		try
        {
	        InputStream inputStream = new FileInputStream(_context.getFilesDir()+"/"+_filename);
	        BufferedReader r = new BufferedReader(new InputStreamReader(inputStream));
	        String line;
	        while ((line = r.readLine()) != null)
	        {
	        	String[] parts = line.split(";");
	        	AlarmSettingsObject tempObj = new AlarmSettingsObject();
	        	
	        	tempObj.setID(parts[0]);
	       
	        	tempObj.setActive(parts[1]);
	        	
	        	tempObj.setHour(Integer.valueOf(parts[2]));
	        	tempObj.setMinute(Integer.valueOf(parts[3]));
	        	tempObj.setSnooze(Integer.valueOf(parts[4]));
	        	
	        	tempObj.setOneTimeSnooze(parts[5]);
	        		
	        	tempObj.setURL(parts[6]);
	        	
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
				String alarmSetting =	obj.getID()+";"+
										obj.getActive()+";"+
										obj.getHour()+";"+
										obj.getMinute()+";"+
				    					obj.getSnooze()+";"+
				    					obj.getOneTimeSnooze()+";"+
				    					obj.getURL()+";";
								
				bw.write(alarmSetting);
			    bw.newLine();
			}
			Log.i("SETTINGS", "file new written");
		    bw.close();
		}
		catch(Exception ex) { }
	}
	
	public void editAlarm(AlarmSettingsObject obj)
	{
		List<AlarmSettingsObject> objList = new ArrayList<AlarmSettingsObject>();
		
		//create list of all entries in our settings file
		//id;active;hour;minute;snooze;onetimesnooze;url;
		
		try
        {
	        InputStream inputStream = new FileInputStream(_context.getFilesDir()+"/"+_filename);
	        BufferedReader r = new BufferedReader(new InputStreamReader(inputStream));
	        String line;
	        while ((line = r.readLine()) != null)
	        {
	        	String[] parts = line.split(";");
	        	AlarmSettingsObject tempObj = new AlarmSettingsObject();
	        	
	        	tempObj.setID(parts[0]);
	       
	        	tempObj.setActive(parts[1]);
	        	
	        	tempObj.setHour(Integer.valueOf(parts[2]));
	        	tempObj.setMinute(Integer.valueOf(parts[3]));
	        	tempObj.setSnooze(Integer.valueOf(parts[4]));
	        	
	        	tempObj.setOneTimeSnooze(parts[5]);
	        		
	        	tempObj.setURL(parts[6]);
	        	
	        	objList.add(tempObj);
	        }
	        
	        r.close();
        }
        catch(Exception ex){ }
		
		//search for the setting line we want to delete
		Iterator<AlarmSettingsObject> it = objList.iterator();
		while (it.hasNext())
		{
			AlarmSettingsObject newObj = it.next();
			if(newObj.getID().equals(obj.getID()))
			{
				newObj.setActive(obj.getActive());
				newObj.setSnooze(obj.getSnooze());
				newObj.setURL(obj.getURL());
				newObj.setOneTimeSnooze(obj.getOneTimeSnooze());
				newObj.setHour(obj.getHour());
				newObj.setMinute(obj.getMinute());
				Log.i("SETTINGS", "found line to edit");
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
			
			for(AlarmSettingsObject obj2 : objList)
			{
				String alarmSetting =	obj2.getID()+";"+
										obj2.getActive()+";"+
										obj2.getHour()+";"+
										obj2.getMinute()+";"+
				    					obj2.getSnooze()+";"+
				    					obj2.getOneTimeSnooze()+";"+
				    					obj2.getURL()+";";
								
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
		
		List<AlarmSettingsObject> objList = new ArrayList<AlarmSettingsObject>();
		
		//create list of all entries in our settings file
		try
        {
	        InputStream inputStream = new FileInputStream(_context.getFilesDir()+"/"+_filename);
	        BufferedReader r = new BufferedReader(new InputStreamReader(inputStream));
	        String line;
	        while ((line = r.readLine()) != null)
	        {
	        	String[] parts = line.split(";");
	        	AlarmSettingsObject tempObj = new AlarmSettingsObject();
	        	
	        	tempObj.setID(parts[0]);
	 	       
	        	tempObj.setActive(parts[1]);
	        	
	        	tempObj.setHour(Integer.valueOf(parts[2]));
	        	tempObj.setMinute(Integer.valueOf(parts[3]));
	        	tempObj.setSnooze(Integer.valueOf(parts[4]));
	        	
	        	tempObj.setOneTimeSnooze(parts[5]);
	        		
	        	tempObj.setURL(parts[6]);
	        	
	        	objList.add(tempObj);
	        }
	        
	        r.close();
        }
        catch(Exception ex){ }
		
		//search for the setting line we want
		Iterator<AlarmSettingsObject> it = objList.iterator();
		while (it.hasNext())
		{
			AlarmSettingsObject obj = it.next();
			if(obj.getID().equals(alarmid))
			{
				alarmObj = obj;
				Log.i("SETTINGS", "isActive = "+alarmObj.getActive());
				Log.i("SETTINGS", "onetime = "+alarmObj.getOneTimeSnooze());
			}
		}
		
		return alarmObj;
	}
	
	public List<AlarmSettingsObject> getAlarms()
	{		
		List<AlarmSettingsObject> objList = new ArrayList<AlarmSettingsObject>();
		
		//create list of all entries in our settings file
		try
        {
	        InputStream inputStream = new FileInputStream(_context.getFilesDir()+"/"+_filename);
	        BufferedReader r = new BufferedReader(new InputStreamReader(inputStream));
	        String line;
	        while ((line = r.readLine()) != null)
	        {
	        	String[] parts = line.split(";");
	        	AlarmSettingsObject tempObj = new AlarmSettingsObject();
	        	
	        	tempObj.setID(parts[0]);
	 	       
	        	tempObj.setActive(parts[1]);
	        	
	        	tempObj.setHour(Integer.valueOf(parts[2]));
	        	tempObj.setMinute(Integer.valueOf(parts[3]));
	        	tempObj.setSnooze(Integer.valueOf(parts[4]));
	        	
	        	tempObj.setOneTimeSnooze(parts[5]);
	        		
	        	tempObj.setURL(parts[6]);
	        	
	        	objList.add(tempObj);
	        }
	        
	        r.close();
        }
        catch(Exception ex){ }

		return objList;
	}
}
