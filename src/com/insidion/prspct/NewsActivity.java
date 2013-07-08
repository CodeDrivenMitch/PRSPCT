package com.insidion.prspct;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;
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
import org.json.JSONObject;

import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class NewsActivity extends ListActivity {

	private NewsItemDatasource DAO;

	private ListView listview;
	private NewsItemAdapter nia;

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

		this.listview = (ListView) findViewById(android.R.id.list);

		listview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				long did = nia.getItem(position).getId();
				Intent theintent = new Intent(NewsActivity.this,
						SingleNewsItemActivity.class);
				theintent.putExtra("item_id", did);
				startActivity(theintent);
			}
		});

	}

	@Override
	protected void onResume() {
		super.onResume();

		this.refreshArrayAdapter();

		NewsItemRetriever nir = new NewsItemRetriever();
		nir.execute();

	}

	private void refreshArrayAdapter() {
		List<NewsItem> values = DAO.getAllNewsItems();

		this.nia = new NewsItemAdapter(this, R.layout.newsitem_listview_row,
				values);
		listview.setAdapter(this.nia);

	}

	class NewsItemRetriever extends AsyncTask<Void, Void, JSONArray> {

		@Override
		protected JSONArray doInBackground(Void... params) {
			JSONArray array = null;
			try {
				array = new JSONObject(readNewsFeed()).getJSONArray("item");
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			return array;
		}

		@Override
		protected void onPostExecute(JSONArray result) {
			super.onPostExecute(result);
			
			if(result == null) return;
			
			NewsItem ni = new NewsItem();
			
			for(int i = 0; i < result.length(); i++)
			{
				try {
					Log.d("DBG_JSON", ""+result.get(i).toString());
				
					JSONObject Jasonobject = result.getJSONObject(i);
					ni.setContent(Jasonobject.getString("content"));
					ni.setTitle(Jasonobject.getString("title"));
					ni.setNid(Jasonobject.getLong("n_id"));	
					
					//TODO : parse date
					
					ni.setDate((new Date()).getTime());
					
				} catch (JSONException e) {
					
				}
			}
			DAO.createNewsItem(ni);
			refreshArrayAdapter();
		}

		public String readNewsFeed() {
			StringBuilder builder = new StringBuilder();
			HttpClient client = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet("http://insidion.com/prspct/news.php");
			try {
				HttpResponse response = client.execute(httpGet);
				StatusLine statusLine = response.getStatusLine();
				int statusCode = statusLine.getStatusCode();
				if (statusCode == 200) {
					HttpEntity entity = response.getEntity();
					InputStream content = entity.getContent();
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(content));
					String line;
					while ((line = reader.readLine()) != null) {
						builder.append(line);
					}
				} else {
					Log.e(NewsActivity.class.toString(),
							"Failed to download file");
				}
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return builder.toString();
		}
	}
}
