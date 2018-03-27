package com.example.navigationd;

import java.util.ArrayList;
import java.util.List;

import network.NetworkManager;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import adapters.View_Material_Report_Adapter;
import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.mainpackage.R;

@SuppressLint("NewApi")
public class View_Material_Report extends Fragment {
	ArrayList<String> material = new ArrayList<String>();
	ArrayList<String> remaining = new ArrayList<String>();
	ArrayList<String> comment = new ArrayList<String>();
	ArrayList<String> date = new ArrayList<String>();
	ArrayList<String> id = new ArrayList<String>();
	ArrayList<String> params = new ArrayList<String>();
	ListView listview;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle args) {
		View view = inflater.inflate(R.layout.viewappointments, container,
				false);
		listview = (ListView) view.findViewById(R.id.listViewappointment);
		
		Toast.makeText(getActivity(), "project id is "+Data.ProjectManager(getActivity()), Toast.LENGTH_LONG).show();
		new Appointments().execute();

		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				// start full view
				Intent start = new Intent(getActivity(),
						View__Full_Material_Report.class);
				start.putExtra("id", id.get(position));
				startActivity(start);

			}
		});
		return view;
	}

	public class Appointments extends AsyncTask<Void, Integer, Integer> {
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

		}

		@Override
		protected Integer doInBackground(Void... arg0) {
			// TODO Auto-generated method stub
			final String url2 = Data.server + "view_material_report.php";
			List<NameValuePair> param = new ArrayList<NameValuePair>();
			param.add(new BasicNameValuePair("project", Data
					.ProjectManager(getActivity())));
			Log.e("project ", Data.ProjectManager(getActivity()));
			String json = NetworkManager.jsonarray(url2, param);
			Log.e("mat report ", json);

			JSONArray jarray;
			try {
				jarray = new JSONArray(json);
				int x = 0;
				for (x = 0; x < jarray.length(); x++) {
					JSONObject obj = jarray.getJSONObject(x);
					material.add(obj.getString("material"));
					remaining.add(obj.getString("remaining"));
					comment.add(obj.getString("comments"));
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
			View_Material_Report_Adapter adapter = new View_Material_Report_Adapter(
					getActivity(), material, remaining, comment, date, id);
			listview.setAdapter(adapter);

		}
	}

	public String myTelephone() {
		String result = null;
		DBhelper help = new DBhelper(getActivity());
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

}