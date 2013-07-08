package com.insidion.prspct;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class SingleNewsItemActivity extends Activity {

	private NewsItemDatasource DAO;
	private NewsItem newsItem;
	
	private TextView title, date, content;
	
	//TODO: Insert imageviews to layout
	private ImageView icon, featured;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_single_news_item);
		
		title = (TextView) findViewById(R.id.tvTitle);
		content = (TextView) findViewById(R.id.tvContent);
		
		DAO = new NewsItemDatasource(this);
		DAO.open();
		
		Intent i = getIntent();
		long itemID = i.getExtras().getLong("item_id", -1);
		
		if(itemID != -1)
		{
			this.newsItem = DAO.getSingleNewsItem(itemID);
			title.setText("" + this.newsItem.getTitle());
			content.setText("" + this.newsItem.getContent());
			
		}
		else {
			Toast.makeText(this, "This newsitem was not found! Something went wrong, please try again!", Toast.LENGTH_LONG).show();
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.single_news_item, menu);
		return true;
	}

}
