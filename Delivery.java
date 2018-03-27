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
import android.widget.Button;
import android.widget.EditText;

@SuppressLint("NewApi")
public class Delivery extends Fragment {
	EditText edtmaterialname, edtdelivery_desc, edtdelquality,edtdelsendername,edtdelrecievername;
	Button buttondelivernote;
	ArrayList<String> params = new ArrayList<String>();
	String mytelephone;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle args) {
		View view = inflater
				.inflate(R.layout.delivery_note, container, false);
		
		edtmaterialname = (EditText) view.findViewById(R.id.edtmaterialname);
		edtdelivery_desc= (EditText) view.findViewById(R.id.edtdelivery_desc);
		edtdelquality = (EditText) view.findViewById(R.id.edtdelquality);
		edtdelsendername= (EditText) view.findViewById(R.id.edtdelsendername);
		edtdelrecievername= (EditText) view.findViewById(R.id.edtdelrecievername);
		
		buttondelivernote = (Button) view.findViewById(R.id.buttondelivernote);

		buttondelivernote.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				params.add(edtmaterialname.getText().toString());
				params.add(edtdelivery_desc.getText().toString());
				params.add(edtdelquality.getText().toString());
				params.add(edtdelsendername.getText().toString());
				params.add(edtdelrecievername.getText().toString());
				
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
			final String url2 = Data.server + "deliverynote.php";
			List<NameValuePair> param = new ArrayList<NameValuePair>();
			param.add(new BasicNameValuePair("material_name", ar.get(0)));
			param.add(new BasicNameValuePair("description", ar.get(1)));
			param.add(new BasicNameValuePair("delivery_quantity", ar.get(2)));
			param.add(new BasicNameValuePair("delivery_sender", ar.get(3)));
			param.add(new BasicNameValuePair("delivery_receiver", ar.get(4)));
			
			JSONObject obj = NetworkManager.object(url2, param);
			Log.e("Delivery note", obj.toString());
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
				alert.setMessage("Materials Delivered");
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