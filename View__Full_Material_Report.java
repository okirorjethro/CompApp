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

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class View__Full_Material_Report extends Activity {
	TextView material, remains, comment, date;
	ArrayList<String> items = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.viewmaterialreport);
		material = (TextView) findViewById(R.id.textViewmaterial);
		remains = (TextView) findViewById(R.id.textViewremainingnow);
		comment = (TextView) findViewById(R.id.textViewcommentsnow);
		date = (TextView) findViewById(R.id.textViewdate);
		Intent i = getIntent();
		String id = i.getStringExtra("id");
		new FullDetails().execute(id);
	}

	public class FullDetails extends AsyncTask<String, Integer, Integer> {
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

		}

		@Override
		protected Integer doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			final String url2 = Data.server + "view_full_material_report.php";
			List<NameValuePair> param = new ArrayList<NameValuePair>();
			param.add(new BasicNameValuePair("id", arg0[0]));
			String json = NetworkManager.jsonarray(url2, param);
			Log.e("appointments", json);

			JSONArray jarray;
			try {
				jarray = new JSONArray(json);
				int x = 0;
				for (x = 0; x < jarray.length(); x++) {
					JSONObject obj = jarray.getJSONObject(x);
					items.add(obj.getString("material"));
					items.add(obj.getString("remaining"));
					items.add(obj.getString("comments"));
					items.add(obj.getString("date"));

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
			material.setText(items.get(0));
			remains.setText(items.get(1));
			comment.setText(items.get(2));
			date.setText(items.get(3));

		}
	}

}
