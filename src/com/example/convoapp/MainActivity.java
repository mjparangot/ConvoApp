package com.example.convoapp;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity implements RecognitionListener {

	protected static final int RESULT_SPEECH = 1;
	private StringBuilder conversation;
	private SpeechRecognizer speech;
	private Intent intent;
	private boolean stopPressed=false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Remove title bar
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_main);
		
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
}
