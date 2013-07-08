package com.insidion.prspct;

import java.util.Date;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NewNewsItemActivity extends Activity implements OnClickListener {

	NewsItemDatasource DAO;
	
	Button save;
	
	EditText content, title;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_news_item);
		
		DAO = new NewsItemDatasource(this);
		DAO.open();
		
		content = (EditText) findViewById(R.id.etContentNewsItem);
		title = (EditText) findViewById(R.id.etTitleNewsItem);
		save = (Button) findViewById(R.id.bCreateNI);
		save.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.new_news_item, menu);
		return true;
	}

	@Override
	public void onClick(View arg0) {
		if(arg0.getId() == R.id.bCreateNI) saveNewsItem();
	}
	
	private void saveNewsItem()
	{
		NewsItem item = new NewsItem();
		
		item.setContent(content.getText().toString());
		item.setTitle(title.getText().toString());
		
		item.setDate((new Date()).getTime());
		
		item.setNid(1);
		
		NewsItem result = DAO.createNewsItem(item);
		
		if(result.hasDatabaseID()) {
			Toast.makeText(getApplicationContext(), "Writing to database succesful!", Toast.LENGTH_LONG).show(); 
		} else {
			Toast.makeText(getApplicationContext(), "Writing to database FAILED", Toast.LENGTH_LONG).show(); 
		}
	}

}
