package com.example.convoapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

public class TopicListActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		// Remove title bar
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                WindowManager.LayoutParams.FLAG_FULLSCREEN); 
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_topic_list);
	}
}
