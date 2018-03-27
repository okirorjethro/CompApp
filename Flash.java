package com.example.navigationd;



import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class Flash extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		DBhelper help = new DBhelper(this);
		SQLiteDatabase db=help.getWritableDatabase();
	String project = "create table if not exists project(project TEXT primary key)";
		db.execSQL(project);
        Toast.makeText(this, role(), Toast.LENGTH_LONG).show();
		if (isVisitor()) {
			Intent intent = new Intent(this, Login.class);
			startActivity(intent);
			Flash.this.finish();

		} else if (!isVisitor()) {

			if (role().equals("it")) {
				Intent intent = new Intent(getApplicationContext(),
						MainActivity.class);
				startActivity(intent);
				Flash.this.finish();

			}

			if (role().equalsIgnoreCase("ceo")) {
				Toast.makeText(this, isVisitor() + "CEO IS HERE",
						Toast.LENGTH_LONG).show();
				Intent intent = new Intent(getApplicationContext(),
						CeoHome.class);
				startActivity(intent);
				Flash.this.finish();

			} else if (role().equals("project")) {
				Intent intent = new Intent(getApplicationContext(),
						ProjectManager.class);
				startActivity(intent);
				Flash.this.finish();

			} else if (role().equals("procurement")) {
				Intent intent = new Intent(getApplicationContext(),
						ProcurementHome.class);
				startActivity(intent);
				Flash.this.finish();

			} else if (role().equals("foreman")) {
				Intent intent = new Intent(getApplicationContext(),
						Foreman.class);
				startActivity(intent);
				Flash.this.finish();

			}
		}
	}

	public boolean isVisitor() {
		boolean result = false;
		DBhelper help = new DBhelper(this);
		SQLiteDatabase db = help.getReadableDatabase();
		Cursor cur = db.rawQuery("select * from users", null);
		int row = cur.getCount();
		Log.e("records", "" + row);
		if (row > 0) {
			result = false;
		}

		else if (row == 0) {
			result = true;
		}

		cur.close();
		db.close();
		return result;
	}

	public String role() {
		String result = null;
		DBhelper help = new DBhelper(this);
		SQLiteDatabase db = help.getReadableDatabase();
		Cursor cur = db.rawQuery("select * from users", null);
		int row = cur.getCount();
		Log.e("records", "" + row);
		if (row > 0) {
			while (cur.moveToNext()) {
				result = cur.getString(3);
			}

		}
		db.close();
		cur.close();
		return result;
	}

	public String mynumber() {
		String result = null;
		DBhelper help = new DBhelper(this);
		SQLiteDatabase db = help.getReadableDatabase();
		Cursor cur = db.rawQuery("select * from users", null);
		int row = cur.getCount();
		Log.e("records", "" + row);
		if (row > 0) {
			while (cur.moveToNext()) {
				result = cur.getString(1);
			}
		}
		return result;
	}

}
