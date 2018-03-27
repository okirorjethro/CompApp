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

import adapters.AppointmentAdapter;
import android.annotation.SuppressLint;
import android.app.Fragment;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

@SuppressLint("NewApi")
public class ViewAppointments extends Fragment {
	ArrayList<String> appointment = new ArrayList<String>();
	ArrayList<String> sender = new ArrayList<String>();
	ArrayList<String> date = new ArrayList<String>();
	ArrayList<String> time = new ArrayList<String>();
	ArrayList<String> id = new ArrayList<String>();
	ArrayList<String> params = new ArrayList<String>();
	ListView listview;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle args) {
		View view = inflater.inflate(R.layout.viewappointments,
				container, false);
		listview = (ListView) view.findViewById(R.id.listViewappointment);
		new Appointments().execute();
		return view;
	}

	public class Appointments extends
			AsyncTask<Void, Integer, Integer> {
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

		}

		@Override
		protected Integer doInBackground(Void... arg0) {
			// TODO Auto-generated method stub
			final String url2 = Data.server + "view_appointment.php";
			List<NameValuePair> param = new ArrayList<NameValuePair>();
			param.add(new BasicNameValuePair("receiver", myTelephone()));
			String json = NetworkManager.jsonarray(url2, param);
			Log.e("appointments", json);

			JSONArray jarray;
			try {
				jarray = new JSONArray(json);
				int x = 0;
				for (x = 0; x < jarray.length(); x++) {
					JSONObject obj = jarray.getJSONObject(x);
					appointment.add(obj.getString("message"));
					sender.add(obj.getString("sender"));
					date.add(obj.getString("date"));
					time.add(obj.getString("time"));
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
			AppointmentAdapter adapter = new AppointmentAdapter(getActivity(),
					appointment, sender, date, time, id);
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