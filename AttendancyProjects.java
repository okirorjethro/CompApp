package com.example.navigationd;

import java.util.ArrayList;
import java.util.List;

import network.NetworkManager;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mainpackage.R;

import adapters.PostAdapter;
import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

@SuppressLint("NewApi")
public class AttendancyProjects extends Fragment {
	ArrayList<String> name = new ArrayList<String>();
	ArrayList<String> date = new ArrayList<String>();
	ArrayList<String> id = new ArrayList<String>();
	ListView dList;
	TextView header;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle args) {
		View view = inflater.inflate(R.layout.main, container, false);
		header=(TextView)view.findViewById(R.id.textViewmainheader);
		header.setText("Choose a project below ");
		dList = (ListView) view.findViewById(R.id.listView1);
		new Projects().execute();
		dList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				Intent i = new Intent(getActivity(), FullAttendacyList.class);
				
				i.putExtra("project", id.get(position));
				startActivity(i);

			}
		});
		return view;
	}

	public class Projects extends AsyncTask<Void, Integer, Integer> {
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}

		@Override
		protected Integer doInBackground(Void... arg0) {
			// TODO Auto-generated method stub
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
					name.add(obj.getString("name"));
					date.add(obj.getString("date"));
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
			PostAdapter adapter = new PostAdapter(getActivity(), name, date, id);
			dList.setAdapter(adapter);

		}
	}

}