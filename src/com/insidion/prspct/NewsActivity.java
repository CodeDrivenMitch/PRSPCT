package com.insidion.prspct;

import java.net.URL;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

public class NewsActivity extends Activity {

	/*
	 * Return button in title bar
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This is called when the Home (Up) button is pressed
			// in the Action Bar.
			Intent parentActivityIntent = new Intent(this, MenuActivity.class);
			parentActivityIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
					| Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(parentActivityIntent);
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_news);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		setLoadingBar();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.news, menu);
		return true;
	}
	private void setLoadingBar() {
		
	}
	
	public void onJSONLoadComplete(String JSONString) {
		
	}

}

class getJSONString extends AsyncTask<URL, Void, String> {
	
	
	private NewsActivity act;
	private URL JSONURL;
	
 	getJSONString(NewsActivity act, URL url) {
		this.act = act;
		this.JSONURL = url;
	}

	

	@Override
	protected String doInBackground(URL... params) {
		// TODO Auto-generated method stub
		String JSON = "bla";
		return JSON;
	}



	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		this.act.onJSONLoadComplete(result);
	}

	

}
