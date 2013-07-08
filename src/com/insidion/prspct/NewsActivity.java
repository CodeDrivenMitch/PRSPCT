package com.insidion.prspct;

import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

public class NewsActivity extends ListActivity implements OnClickListener {

	private NewsItemDatasource DAO;
	private Button button;

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
				long did = nia.getItem(position - 1).getId();
				Intent theintent = new Intent(NewsActivity.this,
						SingleNewsItemActivity.class);
				theintent.putExtra("item_id", did);
				startActivity(theintent);
			}
		});

		this.refreshArrayAdapter();

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
			this.startActivity(new Intent(NewsActivity.this,
					NewNewsItemActivity.class));
		}
	}

	private void refreshArrayAdapter() {
		List<NewsItem> values = DAO.getAllNewsItems();

		this.nia = new NewsItemAdapter(this, R.layout.newsitem_listview_row,
				values);

		View header = (View) getLayoutInflater().inflate(
				R.layout.newsitem_listview_header, null);
		listview.addHeaderView(header);

		listview.setAdapter(this.nia);

	}
}
