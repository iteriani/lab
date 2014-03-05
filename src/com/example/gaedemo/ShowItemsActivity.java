package com.example.gaedemo;

import java.util.List;
import java.util.Vector;

import org.json.JSONArray;
import org.json.JSONObject;

import HTTP.RequestReceiver;
import HTTP.RequestTask;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ShowItemsActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// create showitems layout first.
		// Click on layout, New->Android XML File.
		// ResourceType should be Layout.
		// Add a EditText field in it showitems.xml
		// setContentView(R.layout.showitems);
		
		// Now create a method which get query your Google App Engine via HTTP GET.
		// Make sure that method gets the data via a new thread
		// or use async task.

		setContentView(R.layout.showitems);
		getData();
		// Update the TextView with itemsData.
		
	}
	
	private void receiveData(String s){
		try {
			JSONArray obj = new JSONArray(s);
			List<String> names = new Vector<String>();
			for(int i = 0; i < obj.length(); i++){
				JSONObject item = obj.getJSONObject(i);
				names.add(item.getString("name"));
			}
			ListView lists = (ListView) findViewById(R.id.list);
			lists.setAdapter(new ArrayAdapter<String>(this,
					R.layout.showitems, names));
	
		}catch(Exception e){
			Log.d("JSON parsing error", "Show Items Activity");
		}
	}
	
	private void getData()
	{
		final ShowItemsActivity current = this;
		new RequestTask(new RequestReceiver(){

			@Override
			public void receiveTask(String s) {
				current.receiveData(s);
				
			}
		}).execute(MainActivity.ITEM_URI);
	}
	
}
