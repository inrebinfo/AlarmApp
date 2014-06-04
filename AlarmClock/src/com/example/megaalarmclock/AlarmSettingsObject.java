package com.example.megaalarmclock;

import android.util.Log;

public class AlarmSettingsObject
{
	private String _identifier;
	private int _snooze;
	private String _onetimesnooze;
	private String _songURL;
	private String _isActive;
	private int _hour;
	private int _minute;
	
	//id;active;hour;minute;snooze;onetimesnooze;url;
	
	public int getSnooze()
	{
		return _snooze;
	}
	
	public void setSnooze(int value)
	{
		_snooze = value;
	}
	
	public String getURL()
	{
		return _songURL;
	}
	
	public void setURL(String value)
	{
		_songURL = value;
	}
	
	public String getID()
	{
		return _identifier;
	}
	
	public void setID(String value)
	{
		_identifier = value;
	}
	
	public String getOneTimeSnooze()
	{
		return _onetimesnooze;
	}
	
	public void setOneTimeSnooze(String value)
	{
		_onetimesnooze = value;
	}
	
	public String getActive()
	{
		return _isActive;
	}
	
	public void setActive(String value)
	{
		_isActive = value;	
	}
	
	public int getHour()
	{
		return _hour;
	}
	
	public void setHour(int value)
	{
		_hour = value;
	}
	
	public int getMinute()
	{
		return _minute;
	}
	
	public void setMinute(int value)
	{
		_minute = value;
	}
}
