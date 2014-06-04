package com.example.megaalarmclock;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.*;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		ListView listview = (ListView)findViewById(R.id.lwAlarmItems);
				
		SettingsHandler settingsHandler = new SettingsHandler(this.getApplicationContext());
		
		List<AlarmSettingsObject> list = settingsHandler.getAlarms(); 
		List<String> idList = new ArrayList<String>();
		
		for(AlarmSettingsObject obj : list)
		{
			idList.add(obj.getHour()+":"+obj.getMinute()+"; "+obj.getActive()+"; "+obj.getID());
		}
		
		//T O D O : 
		//alarm aktivieren/deaktivieren im edit
		
		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this.getApplicationContext(), R.layout.list_item, idList);

		listview.setAdapter(arrayAdapter);
		
		listview.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                    long id) {
            	String entry = (String) parent.getItemAtPosition(position);
                Intent intent = new Intent(MainActivity.this, EditAlarmActivity.class);
                String message = entry;
                String[] parts = message.split(";");
                intent.putExtra("alarmid", parts[2].trim());
                startActivity(intent);
            }
        });
	}
	
	@Override
	protected void onResume() {

	   super.onResume();
	   this.onCreate(null);
	}
	
	public void addNewAlarm(View view) throws IllegalArgumentException, SecurityException, IllegalStateException, IOException
	{
		Intent intent = new Intent(this, NewAlarmActivity.class);
    	startActivity(intent);
	}
}
