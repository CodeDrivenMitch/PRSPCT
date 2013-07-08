package com.insidion.prspct;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Toast;

public class SingleNewsItemActivity extends Activity {

	private NewsItemDatasource DAO;
	private NewsItem newsItem;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_single_news_item);
		
		DAO = new NewsItemDatasource(this);
		DAO.open();
		
		Intent i = getIntent();
		long itemID = i.getExtras().getLong("item_id", -1);
		
		if(itemID != -1)
		{
			this.newsItem = DAO.getSingleNewsItem(itemID);
			
			Toast.makeText(this, "Item id = " + itemID + " with title " + newsItem.getTitle(), Toast.LENGTH_LONG).show();
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.single_news_item, menu);
		return true;
	}

}
