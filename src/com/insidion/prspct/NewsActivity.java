package com.insidion.prspct;

import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
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
	}

	private void refreshArrayAdapter() {
		List<NewsItem> values = DAO.getAllNewsItems();

		this.nia = new NewsItemAdapter(this, R.layout.newsitem_listview_row,
				values);
		listview.setAdapter(this.nia);

	}
}
