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
public class Loadinstruction extends Fragment {
	EditText loadmaterials, instruction, txtSendername,txtReceivername;
	Button buttonload;
	ArrayList<String> params = new ArrayList<String>();
	String mytelephone;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle args) {
		View view = inflater
				.inflate(R.layout.activity_loadinstruct,container, false);
		
		loadmaterials = (EditText) view.findViewById(R.id.loadmaterials);
		instruction = (EditText) view
				.findViewById(R.id.instruction);
		txtSendername = (EditText) view.findViewById(R.id.txtSendername);
		txtReceivername= (EditText) view.findViewById(R.id.txtReceivername);
		buttonload = (Button) view.findViewById(R.id.buttonload);

		buttonload.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				params.add(loadmaterials.getText().toString());
				params.add(instruction.getText().toString());
				params.add(txtSendername.getText().toString());
				params.add(txtReceivername.getText().toString());
				
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
			final String url2 = Data.server + "load_instruction.php";
			List<NameValuePair> param = new ArrayList<NameValuePair>();
			param.add(new BasicNameValuePair("Material_Desc", ar.get(0)));
			param.add(new BasicNameValuePair("Instruction", ar.get(1)));
			param.add(new BasicNameValuePair("sender", ar.get(2)));
			param.add(new BasicNameValuePair("reciever", ar.get(3)));
			
			JSONObject obj = NetworkManager.object(url2, param);
			Log.e("Load Instruction", obj.toString());
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
				alert.setMessage("Load instruction performed");
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