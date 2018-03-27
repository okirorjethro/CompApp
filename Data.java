package com.example.navigationd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import network.NetworkManager;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class Data extends Activity {
	Context context = Data.this;
	// public static String server = "http://tenants.6te.net/tenant/";
	//public static String server = "http://192.168.164.1/jethro/";
	public static String server = "http://10.0.2.2/jethro/";
	
	//public static String server = "http://192.168.1.4/jethro/";
	
	//public static String server = "http://192.168.159.1/jethro/";

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

	public static String myTelephone(Context context) {
		String result = null;
		DBhelper help = new DBhelper(context);
		SQLiteDatabase db = help.getReadableDatabase();
		Cursor cur = db.rawQuery("select * from users", null);
		int row = cur.getCount();
		Log.e("records", "" + row);
		if (row > 0) {
			while (cur.moveToNext()) {
				result = cur.getString(1);
			}

		}
		db.close();
		cur.close();
		return result;
	}

	public static String ForemanProject(Context context) {
		String result = null;
		DBhelper help = new DBhelper(context);
		SQLiteDatabase db = help.getReadableDatabase();
		Cursor cur = db.rawQuery("select * from foreman", null);
		int row = cur.getCount();
		Log.e("records", "" + row);
		if (row > 0) {
			while (cur.moveToNext()) {
				result = cur.getString(0);
			}

		}
		db.close();
		cur.close();
		return result;
	}

	public static String ProjectManager(Context context) {
		String result = null;
		DBhelper help = new DBhelper(context);
		SQLiteDatabase db = help.getReadableDatabase();
		Cursor cur = db.rawQuery("select * from project", null);
		int row = cur.getCount();
		Log.e("records", "" + row);
		if (row > 0) {
			while (cur.moveToNext()) {
				result = cur.getString(0);
			}

		}
		db.close();
		cur.close();
		return result;
	}

	public static boolean isRecord(String sql, Context context) {
		boolean result = false;
		DBhelper help = new DBhelper(context);
		SQLiteDatabase db = help.getReadableDatabase();
		Cursor cur = db.rawQuery(sql, null);
		int row = cur.getCount();
		Log.e("records", "" + row);
		if (row > 0) {
			result = true;
		}

		else if (row == 0) {
			result = false;
		}

		cur.close();
		db.close();
		return result;
	}

	public static ArrayList<String> allprojects() {
		ArrayList<String> result = new ArrayList<String>();
		final String url2 = Data.server + "projects.php";
		List<NameValuePair> param = new ArrayList<NameValuePair>();
		String json = NetworkManager.jsonarray(url2, param);
		Log.e("appointments", json);
		JSONArray jarray;
		try {
			jarray = new JSONArray(json);
			int x = 0;
			for (x = 0; x < jarray.length(); x++) {
				JSONObject obj = jarray.getJSONObject(x);
				result.add(obj.getString("name"));

			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	public static ArrayList<String> allprojectsids() {
		ArrayList<String> result = new ArrayList<String>();
		final String url2 = Data.server + "projects.php";
		List<NameValuePair> param = new ArrayList<NameValuePair>();
		String json = NetworkManager.jsonarray(url2, param);
		Log.e("appointments", json);
		JSONArray jarray;
		try {
			jarray = new JSONArray(json);
			int x = 0;
			for (x = 0; x < jarray.length(); x++) {
				JSONObject obj = jarray.getJSONObject(x);
				result.add(obj.getString("id"));

			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	public static ArrayList<String> allprojectdates() {
		ArrayList<String> result = new ArrayList<String>();
		final String url2 = Data.server + "projects.php";
		List<NameValuePair> param = new ArrayList<NameValuePair>();
		String json = NetworkManager.jsonarray(url2, param);
		Log.e("appointments", json);
		JSONArray jarray;
		try {
			jarray = new JSONArray(json);
			int x = 0;
			for (x = 0; x < jarray.length(); x++) {
				JSONObject obj = jarray.getJSONObject(x);
				result.add(obj.getString("date"));

			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
 
	
	

}
