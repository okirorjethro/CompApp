package com.example.navigationd;

import java.util.ArrayList;
import java.util.Calendar;
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
import android.widget.TimePicker;

@SuppressLint("NewApi")
public class CreateAppointment extends Fragment {
	EditText name,date;
	Spinner spin;
	Button click;
	String datetime;
	TimePicker tp;
	int year,month,day;
	ArrayList<String> params = new ArrayList<String>();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle args) {
		View view = inflater.inflate(R.layout.fragment_create_appointment, container, false);
		name = (EditText) view.findViewById(R.id.editTextappointment);
		date= (EditText) view.findViewById(R.id.editTextdate);
		spin = (Spinner) view.findViewById(R.id.spinner1);
         tp=(TimePicker)view.findViewById(R.id.timePicker1);
		click = (Button) view.findViewById(R.id.buttoncreateappointment);
		new FillManagers().execute();
		
		Calendar cal=Calendar.getInstance();
		year=cal.get(Calendar.YEAR);
		month=cal.get(Calendar.MONTH);
		day=cal.get(Calendar.DAY_OF_MONTH);

		click.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				params.add(name.getText().toString());
				params.add(myTelephone());
				params.add(spin.getSelectedItem().toString());
				params.add(date.getText().toString());
				params.add(tp.getCurrentHour().toString()+":"+tp.getCurrentMinute().toString());
				new UploadMaterials().execute(params);

			}
		});
		return view;
	}

	public class UploadMaterials extends
			AsyncTask<ArrayList<String>, Integer, Integer> {
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

		}
		@Override
		protected Integer doInBackground(ArrayList<String>... arg0) {
			// TODO Auto-generated method stub
			ArrayList<String> ar = arg0[0];
			final String url2 = Data.server + "create_appointment.php";
			List<NameValuePair> param = new ArrayList<NameValuePair>();
			param.add(new BasicNameValuePair("name", ar.get(0)));
			param.add(new BasicNameValuePair("sender", ar.get(1)));
			param.add(new BasicNameValuePair("receiver", ar.get(2)));
			param.add(new BasicNameValuePair("date", ar.get(3)));
			param.add(new BasicNameValuePair("time", ar.get(4)));
			JSONObject obj = NetworkManager.object(url2, param);
			Log.e("create report", obj.toString());
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
				alert.setMessage("Appointment Created");
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

	public class FillManagers extends
			AsyncTask<Void, Integer, ArrayList<String>> {
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

		}

		@Override
		protected ArrayList<String> doInBackground(Void... arg0) {
			// TODO Auto-generated method stub
			ArrayList<String>result=new ArrayList<String>();
			result= NetworkManager.Managers(Data.server+"allusers.php");
			return result;
		}

		// Show Dialog Box with Progress bar
		@Override
		protected void onPostExecute(ArrayList<String> result) {

			ArrayAdapter<String> adapter = new ArrayAdapter<String>(
					getActivity(), android.R.layout.simple_spinner_item, result);
			spin.setAdapter(adapter);
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