package com.example.navigationd;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBhelper extends SQLiteOpenHelper {
	private static final int DATABASE_VERSION = 1;
	private static String DB_NAME = "tenant";

	public DBhelper(Context context) {
		super(context, DB_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String user = "create table if not exists users(fullname TEXT not null ,telephone TEXT primary key,pin TEXT not null,role TEXT not null)";
		String foreman = "create table if not exists foreman(project TEXT primary key)";
		String project = "create table if not exists project(project TEXT primary key)";
		db.execSQL(user);
		db.execSQL(foreman);
		db.execSQL(project);
	
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
		db.execSQL("drop table if exists users");
		db.execSQL("drop table if exists foreman");
		db.execSQL("drop table if exists project");

	}

	public void createToDo() {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("column name", "value");
		long todo_id = db.insert("table name", null, values);
	}

	public void getTodo() {
		SQLiteDatabase db = this.getReadableDatabase();

		String selectQuery = "SELECT  * FROM " + "table" + " WHERE " + "column"
				+ " = " + "value";
		Cursor c = db.rawQuery(selectQuery, null);

		if (c != null)
			c.moveToFirst();
		c.getInt(c.getColumnIndex("column"));
	}

	public void getAllToDosByTag() {
		List<String> todos = new ArrayList<String>();

		String selectQuery = "SELECT  * FROM  table";

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (c.moveToFirst()) {
			do {
				// add here
			} while (c.moveToNext());
		}

	}

	public int updateBook() {

		// 1. get reference to writable DB
		SQLiteDatabase db = this.getWritableDatabase();

		// 2. create ContentValues to add key "column"/value
		ContentValues values = new ContentValues();
		values.put("title", ""); // get title
		values.put("author", ""); // get author

		// 3. updating row
		int i = db.update("", // table
				values, // column/value
				"primary column" + " = ?", // selections
				new String[] { String.valueOf("value") }); // selection args

		// 4. close
		db.close();

		return i;

	}

	public void deleteBook() {

		// 1. get reference to writable DB
		SQLiteDatabase db = this.getWritableDatabase();

		// 2. delete
		db.delete("", // table name
				"column" + " = ?", // selections
				new String[] { String.valueOf("id") }); // selections args
	}

}
