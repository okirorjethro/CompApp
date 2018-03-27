package com.example.navigationd;

import java.util.ArrayList;
import java.util.List;

import network.NetworkManager;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.navigationd.ViewAppointments.Appointments;
import com.mainpackage.R;

import adapters.PostAdapter;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

@SuppressLint("NewApi")
public class ViewOperationalCost extends Activity {
	ArrayList<String> name = new ArrayList<String>();
	ArrayList<String> date = new ArrayList<String>();
	ArrayList<String> id = new ArrayList<String>();
	ListView dList;
	TextView header;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.viewappointments);
		dList = (ListView) findViewById(R.id.listViewappointment);
		Intent i = getIntent();
		String project = i.getStringExtra("project");
		new Projects().execute(project);
	}

	public class Projects extends AsyncTask<String, Integer, Integer> {
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}

		@Override
		protected Integer doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			final String url2 = Data.server + "operationalcost.php";
			List<NameValuePair> param = new ArrayList<NameValuePair>();
			param.add(new BasicNameValuePair("project", arg0[0]));
			String json = NetworkManager.jsonarray(url2, param);
			Log.e("appointments", json);
			JSONArray jarray;
			try {
				jarray = new JSONArray(json);
				int x = 0;
				for (x = 0; x < jarray.length(); x++) {
					JSONObject obj = jarray.getJSONObject(x);
					name.add(obj.getString("employee"));
					date.add(obj.getString("pay_roll"));
					id.add(obj.getString("work_description"));
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
			PostAdapter adapter = new PostAdapter(ViewOperationalCost.this,
					name, date, id);
			dList.setAdapter(adapter);

		}
	}

}