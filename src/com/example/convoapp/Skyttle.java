package com.example.convoapp;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.json.JSONArray;
import org.json.JSONObject;

import android.os.StrictMode;
import android.util.Log;

public class Skyttle {
	public static String[] getTerms(String text) throws UnsupportedEncodingException{
		Log.e("DEBUG", "Getting terms:" + text);
		
		
		String url = "https://sentinelprojects-skyttle20.p.mashape.com/";
	    String lang = "en";
	    String keywords = "1";
	    String sentiment = "1";
	    String annotate = "0";

	    String params =
	      "text=" + URLEncoder.encode(text, "UTF-8") +
	      "&lang=" + URLEncoder.encode(lang, "UTF-8") +
	      "&keywords=" + URLEncoder.encode(keywords, "UTF-8") +
	      "&sentiment=" + URLEncoder.encode(sentiment, "UTF-8") +
	      "&annotate=" + URLEncoder.encode(annotate, "UTF-8");

	    String response = makeCall(url, params);
	    
	    //System.out.println("Response: " + response);
	    
	    try {
			JSONObject json = new JSONObject(response);
			JSONArray docs = json.getJSONArray("docs");
			JSONObject doc = docs.getJSONObject(0);
			JSONArray termsarr = doc.getJSONArray("terms");
			String[] terms = new String[termsarr.length()];
			for(int i = 0 ; i < terms.length; i++){
				terms[i] = termsarr.getJSONObject(i).getString("term");
			}
			return terms;
	    }
	    catch (Exception e) {
	    	
	    }
		return null;
	}
  
	public static String makeCall(String targetURL, String urlParameters){
		URL url;
		
	    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
	    StrictMode.setThreadPolicy(policy);

		
		HttpURLConnection connection = null;
		try {
		    // create connection
			url = new URL(targetURL);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type",
			          "application/x-www-form-urlencoded");
			connection.setRequestProperty("X-Mashape-Authorization",
				""); // Authorization string for second parameter
	        connection.setUseCaches(false);
	        connection.setDoInput(true);
	        connection.setDoOutput(true);
	        
	        // send request
	        DataOutputStream wr = new DataOutputStream (
	                    connection.getOutputStream ());
	        wr.writeBytes(urlParameters);
	        wr.flush();
	        wr.close();
	
	        // get Response
	        InputStream is = connection.getInputStream();
	        BufferedReader rd = new BufferedReader(
	                    new InputStreamReader(is));
	        String line;
	        StringBuffer response = new StringBuffer();
	        while((line = rd.readLine()) != null) {
	          response.append(line);
	          response.append('\r');
	        }
	        rd.close();
	        return response.toString();
	
	      } catch (Exception e) {
	
	        e.printStackTrace();
	        return null;
	
	      } finally {
	
	        if(connection != null) {
	          connection.disconnect();
        }
      }
    }

}
