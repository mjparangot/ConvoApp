package com.example.convoapp;

import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.os.StrictMode;

public class GoogleController {
	private static GoogleController instance = null;
	private static final int NUM_RESULTS = 1; 
	private static String charset = "UTF-8";

	public static GoogleController getInstance(){
		if(instance == null) 
			return instance = new GoogleController();
		else
			return instance;
	}
	
	public List<GoogleResult> Search(String query, double score) throws Exception{
	    List<GoogleResult> listResults = new ArrayList<GoogleResult>();
	    
	    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
	    StrictMode.setThreadPolicy(policy);
	    
	    //ACTUALLY DOESNT USE NUM_RESULTS (only returns 1)..its an appathon wtv
	    for(int i = 0; i < NUM_RESULTS; i++) {
	    	String address = "http://ajax.googleapis.com/ajax/services/search/news?v=1.0&start=" + i * 4 + "&q=";

	    	URL url = new URL(address + URLEncoder.encode(query, charset));
	    	Reader reader = new InputStreamReader(url.openStream(), charset);
  
	    	String responsestr = GoogleTrends.given(reader);
			JSONObject json = new JSONObject(responsestr);
			JSONObject rd = json.getJSONObject("responseData");
			JSONArray results = rd.getJSONArray("results");
			JSONObject result = results.getJSONObject(0);
			String url2 = result.getString("unescapedUrl");
			String title = result.getString("title");
			
			listResults.add(new GoogleResult(url2, title, score));
	    }
	    return listResults;
	}
	
	public class GoogleResult{
		public final double score;
		public final String url, title;
		public GoogleResult(String url, String title, double score){
			this.url = url;
			this.title = title.replace("<b>", "").replace("</b>", "");
			this.score = score;
		}
	}
}
