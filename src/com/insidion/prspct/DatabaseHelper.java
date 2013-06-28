package com.insidion.prspct;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

	public static final String TABLE_NEWSITEMS = "newsitems";
	public static final String COL_ID = "_id";
	public static final String COL_ITEM_ID = "iId";
	public static final String COL_DATE = "iDate";
	public static final String COL_TITLE = "iTitle";
	public static final String COL_CONTENT = "iContent";
	public static final String COL_ICON_URL = "iIconURL";
	public static final String COL_FEATURED_URL = "iFeaturedURL";

	private static final String DATABASE_NAME = "PRSPCT.db";
	private static final int DATABASE_VERSION = 1;

	private static final String DATABASE_CREATE = "create table "
			+ TABLE_NEWSITEMS + " ( " + COL_ID
			+ " integer primary key autoincrement, " + COL_ITEM_ID
			+ " integer not null, " + COL_DATE + " integer not null, "
			+ COL_TITLE + " text not null, " + COL_CONTENT + " text not null, "
			+ COL_ICON_URL + " text, " + COL_FEATURED_URL + " text);";

	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int old_version, int new_version) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NEWSITEMS);
		onCreate(db);
	}

}
