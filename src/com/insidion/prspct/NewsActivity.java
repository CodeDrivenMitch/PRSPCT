package com.insidion.prspct;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
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

class getJSONString extends AsyncTask<URI, Void, String> {
	
	
	private NewsActivity act;
	private URI JSONURL;
	
 	getJSONString(NewsActivity act, URI url) {
		this.act = act;
		this.JSONURL = url;
	}

	

	@Override
	protected String doInBackground(URI... params) {
		BufferedReader in = null;
		String JSON =  null;
		try {
			HttpClient client = new DefaultHttpClient();
			HttpGet request = new HttpGet();
			request.setURI(this.JSONURL);
			HttpResponse response = client.execute(request);
			in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			StringBuffer sb = new StringBuffer("");
			String l = "";
			String nl = System.getProperty("line.separator");
			while ((l = in.readLine()) != null) {
				sb.append(l + nl);
			}
			in.close();
			JSON = sb.toString();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(in != null) {
				try {
					in.close();
				} catch (IOException e){
					e.printStackTrace();
				}
			}
		}
		return JSON;
	}



	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		this.act.onJSONLoadComplete(result);
	}

	

}
