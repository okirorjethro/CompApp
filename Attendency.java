package com.example.navigationd;

import java.util.ArrayList;
import java.util.List;

import network.NetworkManager;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import com.mainpackage.R;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

@SuppressLint("NewApi")
public class Attendency extends Fragment {
	EditText name;
	Button click;
	ArrayList<String> params = new ArrayList<String>();
	String mytelephone;
	Spinner status;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle args) {
		View view = inflater.inflate(R.layout.attendancy, container, false);
		mytelephone = Data.myTelephone(getActivity());
		name = (EditText) view.findViewById(R.id.editTextattendancyname);
		status = (Spinner) view.findViewById(R.id.spinner1);
		ArrayList<String> state = new ArrayList<String>();
		state.add("Present");
		state.add("Absent");
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_spinner_dropdown_item, state);
		status.setAdapter(adapter);
		click = (Button) view.findViewById(R.id.buttonreportattendance);
		click.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				params.add(name.getText().toString());
				params.add(status.getSelectedItem().toString());
				new Upload().execute(params);

			}
		});
		return view;
	}

	public class Upload extends AsyncTask<ArrayList<String>, Integer, Integer> {
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

		}
		@Override
		protected Integer doInBackground(ArrayList<String>... arg0) {
			// TODO Auto-generated method stub
			ArrayList<String> ar = arg0[0];
			final String url2 = Data.server + "attendancy.php";
			List<NameValuePair> param = new ArrayList<NameValuePair>();
			param.add(new BasicNameValuePair("name", ar.get(0)));
			param.add(new BasicNameValuePair("status", ar.get(1)));
			param.add(new BasicNameValuePair("project", Data
					.ForemanProject(getActivity())));
			JSONObject obj = NetworkManager.object(url2, param);
			Log.e("Attendance List", obj.toString());
			return NetworkManager.result(obj);
		}

		// Show Dialog Box with Progress bar
		@Override
		protected void onPostExecute(Integer result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			if (result == 1) {
				AlertDialog.Builder alert = new AlertDialog.Builder(
						getActivity());
				alert.setMessage("Success");
				alert.setTitle("Success");
				alert.setPositiveButton("Okay",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface arg0, int arg1) {

							}
						});
				alert.show();
			} else if (result == 0) {
				AlertDialog.Builder alert = new AlertDialog.Builder(
						getActivity());
				alert.setMessage("Failed");
				alert.setTitle("try again");
				alert.show();

			}

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