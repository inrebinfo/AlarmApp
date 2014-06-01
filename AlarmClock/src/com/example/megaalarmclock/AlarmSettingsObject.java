package com.example.megaalarmclock;

public class AlarmSettingsObject
{
	private String _identifier;
	private int _snooze;
	private boolean _onetimesnooze;
	private String _songURL;
	
	
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
	
	public boolean getOneTimeSnooze()
	{
		return _onetimesnooze;
	}
	
	public void setOneTimeSnooze(boolean value)
	{
		_onetimesnooze = value;
	}
}
