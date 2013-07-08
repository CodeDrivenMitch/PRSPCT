package com.insidion.prspct;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class NewsItemDatasource {

	private SQLiteDatabase database;
	private DatabaseHelper dbHelper;

	private String[] allColumns = { DatabaseHelper.COL_ID,
			DatabaseHelper.COL_ITEM_ID, DatabaseHelper.COL_DATE,
			DatabaseHelper.COL_TITLE, DatabaseHelper.COL_CONTENT,
			DatabaseHelper.COL_ICON_URL, DatabaseHelper.COL_FEATURED_URL };

	public NewsItemDatasource(Context context) {
		dbHelper = new DatabaseHelper(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	public void deleteNewsItem(NewsItem newsitem) {
		long id = newsitem.getId();

		database.delete(DatabaseHelper.TABLE_NEWSITEMS, DatabaseHelper.COL_ID
				+ " = " + id, null);
	}
	
	public NewsItem updateNewsItem(NewsItem updated)
	{
		if(updated.hasDatabaseID() && updated.isValid())
		{
			//TODO Update action database
			
			return null;
		}
		else return null;
	}

	public NewsItem createNewsItem(NewsItem newsitem) {
		ContentValues values = new ContentValues();
		values.put(DatabaseHelper.COL_ITEM_ID, newsitem.getNid());
		values.put(DatabaseHelper.COL_DATE, newsitem.getDate());
		values.put(DatabaseHelper.COL_TITLE, newsitem.getTitle());
		values.put(DatabaseHelper.COL_CONTENT, newsitem.getContent());
		values.put(DatabaseHelper.COL_ICON_URL, newsitem.getIcon_URL());
		values.put(DatabaseHelper.COL_FEATURED_URL, newsitem.getFeatured_URL());

		long insertId = database.insert(DatabaseHelper.TABLE_NEWSITEMS, null,
				values);
		Cursor cursor = database.query(DatabaseHelper.TABLE_NEWSITEMS,
				allColumns, DatabaseHelper.COL_ID + " = " + insertId, null,
				null, null, null);
		cursor.moveToFirst();
		NewsItem newnewsitem = cursorToNewsItem(cursor);
		cursor.close();
		return newnewsitem;
	}

	public List<NewsItem> getAllNewsItems() {
		List<NewsItem> newsitems = new ArrayList<NewsItem>();
		Cursor cursor = database.query(DatabaseHelper.TABLE_NEWSITEMS,
				allColumns, null, null, null, null, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			NewsItem comment = cursorToNewsItem(cursor);
			newsitems.add(comment);
			cursor.moveToNext();
		}
		// Make sure to close the cursor
		cursor.close();
		return newsitems;
	}

	private NewsItem cursorToNewsItem(Cursor cursor) {
		NewsItem ni = new NewsItem();

		ni.setId(cursor.getLong(0));
		ni.setNid(cursor.getLong(1));
		ni.setDate(cursor.getLong(2));
		ni.setTitle(cursor.getString(3));
		ni.setContent(cursor.getString(4));
		ni.setIcon_URL(cursor.getString(5));
		ni.setFeatured_URL(cursor.getString(6));

		return ni;
	}
}
