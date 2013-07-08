package com.insidion.prspct;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;

import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

public class NewsActivity extends ListActivity implements OnClickListener {

	private NewsItemDatasource DAO;
	private Button button;

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
		
		DAO = new NewsItemDatasource(this);
		DAO.open();
		
		//this.refreshArrayAdapter();

		button = (Button) findViewById(R.id.bCreateNI);
		button.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.news, menu);
		return true;
	}

	public void onJSONLoadComplete(String JSONString) {

	}

	@Override
	public void onClick(View arg0) {
		if (arg0.getId() == R.id.bCreateNI) {
			this.startActivity(new Intent(NewsActivity.this, NewNewsItemActivity.class));
		}
	}
	
	private void refreshArrayAdapter()
	{
		List<NewsItem> values = DAO.getAllNewsItems();

		ArrayAdapter<NewsItem> adapter = new ArrayAdapter<NewsItem>(this,
				R.layout.newsitemlist, values);

		setListAdapter(adapter);
	}
}
