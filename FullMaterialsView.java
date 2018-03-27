package com.example.navigationd;

import java.util.ArrayList;
import java.util.List;

import network.NetworkManager;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mainpackage.R;

import adapters.View_Time_Sheet_Adapter;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class FullMaterialsView extends Activity {
	TextView date, material, quantity, unitcost, extendTextView;
	ArrayList<String> items = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fullmaterialsview);
		date = (TextView) findViewById(R.id.textView1);
		material = (TextView) findViewById(R.id.textView2);
		quantity = (TextView) findViewById(R.id.textView3);
		unitcost = (TextView) findViewById(R.id.textView4);
		extendTextView = (TextView) findViewById(R.id.textView5);

		Intent i = getIntent();
		String id = i.getStringExtra("id");
		new Upload().execute(id);
	}

	public class Upload extends AsyncTask<String, Integer, Integer> {
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}

		@Override
		protected Integer doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			final String url2 = Data.server + "fullmaterialsview.php";
			List<NameValuePair> param = new ArrayList<NameValuePair>();
			param.add(new BasicNameValuePair("id", arg0[0]));
			Log.e("project", arg0[0]);
			String json = NetworkManager.jsonarray(url2, param);
			Log.e("appointments", json);
			JSONArray jarray;
			try {
				jarray = new JSONArray(json);
				int x = 0;
				for (x = 0; x < jarray.length(); x++) {
					JSONObject obj = jarray.getJSONObject(x);
					items.add(obj.getString("date"));
					items.add(obj.getString("material"));
					items.add(obj.getString("quantity"));
					items.add(obj.getString("per_unit_cost"));
					items.add(obj.getString("extended_cost"));
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return null;
		}

		// Show Dialog Box with Progress bar
		@Override
		protected void onPostExecute(Integer result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			date.setText(items.get(0));
			material.setText(items.get(1));
			quantity.setText(items.get(2));
			unitcost.setText(items.get(3));
			extendTextView.setText(items.get(4));

		}
	}

}
