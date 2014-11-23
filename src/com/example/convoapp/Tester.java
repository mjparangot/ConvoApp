package com.example.convoapp;

import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.List;

import android.util.Log;

import com.example.convoapp.GoogleController.GoogleResult;
import com.example.convoapp.GoogleTrends.Trend;

public class Tester {
	private static Tester instance = null;
	public static Tester getInstance(){
		if(instance == null) 
			return instance = new Tester();
		else
			return instance;
	}
	private static final int MAXWORDS = 20;
	private static final int MAXTERMS = 5;
	private String buildText = "";
	private List<String> termslist = new LinkedList<String>();
	private List<GoogleResult> bestresults = new LinkedList<GoogleResult>();
	
	public void newText(String text){
		buildText += text;
		int numwords = buildText.length() - buildText.replace(" ", "").length() ;
		Log.e("DEBUG", text + " " + numwords);

		if(numwords > MAXWORDS){
			process(buildText);
			buildText = "";
		}
	}
	
	private void process(String text){
		Log.e("DEBUG", "PROCESSING: " + text);
		
	    // add back in
	    //
	    String[] terms = null;
		try {
			terms = Skyttle.getTerms(text);
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    //String[] terms = new String[]{"set%20lunch","lunch%20for%20people","reputation","dollars","RMB",
//	    		"way%20of%20living","bedtime" ,"career","influence","interpersonal%20circle",
//	    		"friends","set%20of%20funds","vegetables%20dishes","fruit","food%20cost" };

		if(terms == null) return;
		
	    for(String s : terms){
	    	termslist.add(s);
	    }
	    
	    Log.e("DEBUG", "# TERMS:" + termslist.size()); 
	    
	    //System.out.println(termslist.size());
	    
		if(termslist.size() < MAXTERMS){
			return;
		}

		//convert to array for convenience
		terms = termslist.toArray(new String[0]);
		termslist.clear();
		
	    Trend[] best = GoogleTrends.getInstance().getBestSearch(terms);

	    if(best == null) return;
	    
	    //System.out.println("BEST SEARCH QUERY: \n  " + best[0].search + "\n  " + best[1].search + "\n  " + best[2].search);
	    
		List<GoogleResult> results = null;
		try {
			results = GoogleController.getInstance().Search(best[0].search, best[0].score);
			results.addAll(GoogleController.getInstance().Search(best[1].search, best[1].score));
			results.addAll(GoogleController.getInstance().Search(best[2].search, best[2].score));
		} catch (Exception e) {
			e.printStackTrace();
		}

		//for(GoogleResult r : results){
			//System.out.println("\n\n\n" + r.title.replace("<b>", "").replace("</b>", ""));
			//System.out.println(r.url);
		//}
		if(results == null) return;
		bestresults = results;
	}
	
	public String[] getBest(){
		if(bestresults.size() > 2){
			String[] retstr = new String[]{bestresults.get(0).title, bestresults.get(0).url,
					bestresults.get(1).title, bestresults.get(1).url,
					bestresults.get(2).title, bestresults.get(2).url};
			bestresults.clear();
			return retstr;
		}else{
			return null;
		}
	}
	/*
	public static void main(String[] args) {
		String text2 = "First set of funds:The first set of funds is used for living expenses. Its a simple way of living and you can onlybe assigned to less than twenty dollars a day.A daily breakfast of vermicelli, an egg and a cup of milk.For lunch just have a simple set lunch, a snack and a fruit.For dinner go to your kitchen and cook your own meals that consist of two vegetables dishes and a glass of milk before bedtime.For one month the food cost is probably $500-$600. When you are young, the body will not have too many problems for a few years with this way of living. Second set of funds:To make friends, expand your interpersonal circle. This will make you well off. Your phone bills can be budgeted at 100 RMB. You can buy your friends 2 lunches a month, each at $150.Who should you buy lunch for? Always remember to buy lunch for people who are more knowledgeable than you, richer than you or people who has helped you in your career. Make sure you do that every month. After one year, your circle of friends should have generated tremendous value for you. Your reputation, influence, added value will be clearly recognized. Youll also enhance your image of being good and generous.";

		//text2 = "feafe feafe";
		Tester.getInstance().newText(text2);
		
		String[] best = Tester.getInstance().getBest();
	}*/
}
