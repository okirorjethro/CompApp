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

import adapters.Fleet_Adapter;
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
public class ViewFleetCondition extends Activity {
	ArrayList<String> employee = new ArrayList<String>();
	ArrayList<String> car = new ArrayList<String>();
	ArrayList<String> fleet_condition = new ArrayList<String>();
	ArrayList<String> fuel= new ArrayList<String>();
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
			final String url2 = Data.server + "view_fleet_conditions.php";
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
					
					employee.add(obj.getString("employee"));
					car.add(obj.getString("car"));
					fleet_condition.add(obj.getString("fleet_condition"));
					fuel.add(obj.getString("fuel"));
					id.add(obj.getString("id"));
					
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
			Fleet_Adapter adapter = new Fleet_Adapter(ViewFleetCondition.this,
					employee, car, id, fleet_condition, fuel);
			dList.setAdapter(adapter);

		}
	}

}