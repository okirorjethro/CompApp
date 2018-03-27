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
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

@SuppressLint("NewApi")
public class MaterialsView extends Activity {

	ArrayList<String> name = new ArrayList<String>();
	ArrayList<String> quantity = new ArrayList<String>();
	ArrayList<String> cost_per_unit = new ArrayList<String>();
	ArrayList<String> id = new ArrayList<String>();

	ListView dList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		dList = (ListView) findViewById(R.id.listView1);

		Intent i = getIntent();
		String project = i.getStringExtra("project");
		new ProjectReport().execute(project);

		dList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				Intent i = new Intent(getApplicationContext(), FullMaterialsView.class);
				i.putExtra("id", id.get(position));
				startActivity(i);

			}
		});
	}

	public class ProjectReport extends AsyncTask<String, Integer, Integer> {
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}

		@Override
		protected Integer doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			final String url2 = Data.server + "materialsview.php";
			List<NameValuePair> param = new ArrayList<NameValuePair>();
			param.add(new BasicNameValuePair("project", arg0[0]));
			Log.e("project", arg0[0]);
			String json = NetworkManager.jsonarray(url2, param);
			Log.e("appointments", json);
			JSONArray jarray;
			try {
				jarray = new JSONArray(json);
				int x = 0;
				for (x = 0; x < jarray.length(); x++) {
					JSONObject obj = jarray.getJSONObject(x);
					name.add(obj.getString("material"));
					quantity.add(obj.getString("quantity"));
					cost_per_unit.add(obj.getString("per_unit_cost"));
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

			View_Time_Sheet_Adapter adapter = new View_Time_Sheet_Adapter(
					MaterialsView.this, name, quantity, cost_per_unit, id);

			dList.setAdapter(adapter);

		}
	}

}