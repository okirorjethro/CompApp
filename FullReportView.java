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
import android.widget.Toast;

public class FullReportView extends Activity {
	TextView scroll;
	ArrayList<String> items = new ArrayList<String>();
	StringBuilder sb = new StringBuilder();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fullreportview);
		scroll = (TextView) findViewById(R.id.textView1);
		Intent i = getIntent();
		String id = i.getStringExtra("id");
		Toast.makeText(getApplicationContext(), id, Toast.LENGTH_LONG).show();
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
			final String url2 = Data.server + "full_report_view.php";
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
					items.add(obj.getString("report"));

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
			sb.append("project report submitted  on " + items.get(1));
			sb.append("\n");
			sb.append(items.get(0));
			scroll.setText(sb.toString());

		}
	}

}
