package com.example.megaalarmclock;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		ListView listview = (ListView)findViewById(R.id.lwAlarmItems);
		
		//http://wptrafficanalyzer.in/blog/enabling-multi-selection-mode-in-listview-by-adding-togglebutton-using-custom-layout-in-android/
		
		SettingsHandler settingsHandler = new SettingsHandler(this.getApplicationContext());
		
		List<AlarmSettingsObject> list = settingsHandler.getAlarms(); 
		List<String> idList = new ArrayList<String>();
		

		String[] arr = new String[list.size()];
		
		int i = 0;
		for(AlarmSettingsObject obj : list)
		{
			idList.add(obj.getID());
			arr[i] = obj.getID();
			Log.i("MAIN", obj.getID());
			i++;
		}
		
		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this.getApplicationContext(), android.R.layout.simple_list_item_1, idList);

		listview.setAdapter(arrayAdapter); 
	}
	
	public void addNewAlarm(View view) throws IllegalArgumentException, SecurityException, IllegalStateException, IOException
	{
		Intent intent = new Intent(this, NewAlarmActivity.class);
    	startActivity(intent);
	}
}
