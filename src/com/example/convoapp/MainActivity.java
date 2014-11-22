package com.example.convoapp;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.content.Intent;
public class MainActivity extends Activity {
	
	// Spinner
	ProgressWheel pw;

	// Stores last three topics found
    ArrayList<String> topicList = new ArrayList<String>();
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		// Remove title bar
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                WindowManager.LayoutParams.FLAG_FULLSCREEN); 
		
		// Set view to activity_main
		setContentView(R.layout.activity_main);
		
		// Init spinner
		pw = (ProgressWheel) findViewById(R.id.pw_spinner);
		
		
		final Button button = (Button) findViewById(R.id.button1);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	
            	Intent intent = new Intent(getApplicationContext(), TopicListActivity.class);
            	startActivity(intent);
            	
                if (pw.isSpinning()) {
                	pw.stopSpinning();
            		button.setText("Start");
                }
                else {
                	pw.spin();
                	button.setText("Stop");
                }
                
            }
        });
	}
	
	
}
