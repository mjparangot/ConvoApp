package com.example.convoapp;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.convoapp.SlidingUpPanelLayout.PanelSlideListener;

public class MainActivity extends Activity implements RecognitionListener {
	
	private ProgressWheel pw;

    private ListView mainListView;  
    private ArrayAdapter<String> listAdapter;
	
	// Stores last three topics found
    ArrayList<String> topicList = new ArrayList<String>();

	protected static final int RESULT_SPEECH = 1;
	private StringBuilder conversation;
	private SpeechRecognizer speech;
	private Intent intent;
	private boolean stopPressed=false;
	
	private SlidingUpPanelLayout mLayout;
	
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
		
		// Roboto font
		//Typeface myTypeface = Typeface.createFromAsset(getAssets(), "fonts/RobotoTTF/Roboto-Medium.ttf");
	    
		final TextView topText = (TextView)findViewById(R.id.top_text);
		final TextView slideText = (TextView)findViewById(R.id.slide_text);
		final TextView infoText = (TextView)findViewById(R.id.info_text);
		
	    //topText.setTypeface(myTypeface);
	    //slideText.setTypeface(myTypeface);
	    
	    
	    // Find the ListView resource.     
	    mainListView = (ListView) findViewById(R.id.mainListView);
	    
	    // Create ArrayAdapter using the planet list.  
	    listAdapter = new ArrayAdapter<String>(this, R.layout.row, topicList);  
	      
	    // Add more planets. If you passed a String[] instead of a List<String>   
	    // into the ArrayAdapter constructor, you must not add more items.   
	    // Otherwise an exception will occur.  
	      
	    // Set the ArrayAdapter as the ListView's adapter.  
	    mainListView.setAdapter( listAdapter );  
	
	    // Sliding layout
	    mLayout = (SlidingUpPanelLayout) findViewById(R.id.sliding_layout);
        mLayout.setPanelSlideListener(new PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {
            	
            }

            @Override
            public void onPanelExpanded(View panel) {
            	slideText.setText("Click to close panel");
            }

            @Override
            public void onPanelCollapsed(View panel) {
            	slideText.setText("Slide up to view matches");
            }

            @Override
            public void onPanelAnchored(View panel) {
            	
            }

            @Override
            public void onPanelHidden(View panel) {

            }
        });
	    
	    // Spinner animation
        pw.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	
                if (pw.isSpinning()) {
                	pw.stopSpinning();
                	topText.setText("START");
                	infoText.setVisibility(View.VISIBLE);
                	speech.stopListening();
                }
                else {
                	pw.spin();
                	topText.setText("STOP");
                	infoText.setVisibility(View.INVISIBLE);
                    speech.startListening(intent);
                }
                
            }
        });
		
		//init things for speech to text
		conversation = new StringBuilder();
		speech = SpeechRecognizer.createSpeechRecognizer(this);
	    speech.setRecognitionListener(this);
	    intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
	    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, "en");
	    intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,
	        this.getPackageName());
        
	}	


	@Override
	public void onReadyForSpeech(Bundle params) {
		// TODO Auto-generated method stub
	}


	@Override
	public void onBeginningOfSpeech() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onRmsChanged(float rmsdB) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onBufferReceived(byte[] buffer) {
		// TODO Auto-generated method stub
	}


	@Override
	public void onEndOfSpeech() {
		// TODO Auto-generated method stub
		Tester.getInstance().getBest();
	}


	@Override
	public void onError(int error) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onResults(Bundle results) {
		// TODO Auto-generated method stub
		ArrayList<String> matches = results.getStringArrayList(
		        SpeechRecognizer.RESULTS_RECOGNITION);
		conversation.append(matches.get(0)+" ");
		Tester.getInstance().newText(matches.get(0));
		showDialog();
		//Tester.getInstance().newText("feafe fea feafe fea feaf feaf feaf fe afe");

		if(!stopPressed)
			speech.startListening(intent);
	}


	@Override
	public void onPartialResults(Bundle partialResults) {
		// TODO Auto-generated method stub
	}


	@Override
	public void onEvent(int eventType, Bundle params) {
		// TODO Auto-generated method stub
	}
	
	public void showDialog(){
		final String[] items = Tester.getInstance().getBest();
		if(items == null) {
			Log.e("NULL", "GETBEST");
			return;
		}
		
		listAdapter.clear();
		listAdapter.add(items[0]);
		listAdapter.add(items[2]);
		listAdapter.add(items[4]);
		
		mainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent browserIntent = new Intent();
	    	
		    	switch(position) {
		    	case 0:
		    		browserIntent= new Intent(Intent.ACTION_VIEW, Uri.parse(items[1]));
		    		break;
		    	case 1:
		    		browserIntent= new Intent(Intent.ACTION_VIEW, Uri.parse(items[3]));
		    		break;
		    	case 2:
		    		browserIntent= new Intent(Intent.ACTION_VIEW, Uri.parse(items[5]));
		    		break;
		    	}
		    	startActivity(browserIntent);
			}
		});
		
		Toast toast = Toast.makeText(getApplicationContext(), "You have new topics!", Toast.LENGTH_LONG);
		toast.show();
		
		/*
		String[] names = {items[0], items[2], items[4]};
		final String[] links = {items[1], items[3], items[5]};
		
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Relevant information has been found!");
		builder.setItems(names, new DialogInterface.OnClickListener() {
		    public void onClick(DialogInterface dialog, int item) {
		    	Intent browserIntent = new Intent();
		    	
		    	switch(item){
		    	case 0:
		    		browserIntent= new Intent(Intent.ACTION_VIEW, Uri.parse(links[0]));
		    		break;
		    	case 1:
		    		browserIntent= new Intent(Intent.ACTION_VIEW, Uri.parse(links[1]));
		    		break;
		    	case 2:
		    		browserIntent= new Intent(Intent.ACTION_VIEW, Uri.parse(links[2]));
		    		break;
		    	}
		    	startActivity(browserIntent);
		    }
		});
		AlertDialog alert = builder.create();
		alert.getWindow().clearFlags(LayoutParams.FLAG_DIM_BEHIND);
		WindowManager.LayoutParams wmlp = alert.getWindow().getAttributes();
		wmlp.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
		wmlp.x = 100;   //x position
		wmlp.y = 100;   //y position
		
		alert.show();		*/
	}
}
