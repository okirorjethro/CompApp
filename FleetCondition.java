package com.example.navigationd;

import java.util.ArrayList;
import java.util.List;

import network.NetworkManager;

import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import com.example.navigationd.MaterialRequisition.UploadMaterials;
import com.mainpackage.R;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

@SuppressLint("NewApi")
public class FleetCondition extends Fragment {
EditText name,car,condition,fuel;
Button click;
ArrayList<String> params = new ArrayList<String>();


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle args) {
		View view = inflater
				.inflate(R.layout.fragment_fleet_condition, container, false);
		
		name = (EditText) view.findViewById(R.id.editText1);
		car = (EditText) view.findViewById(R.id.EditText01);
		condition = (EditText) view.findViewById(R.id.EditText02);
		fuel = (EditText) view.findViewById(R.id.EditText03);
		click = (Button) view.findViewById(R.id.button1);

		click.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				params.add(name.getText().toString());
				params.add(car.getText().toString());
				params.add(condition.getText().toString());
				params.add(fuel.getText().toString());
				
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
	final String url2 = Data.server + "fleet_insert.php";
	List<NameValuePair> param = new ArrayList<NameValuePair>();
	param.add(new BasicNameValuePair("employee", ar.get(0)));
	param.add(new BasicNameValuePair("car", ar.get(1)));
	param.add(new BasicNameValuePair("condition", ar.get(2)));
	param.add(new BasicNameValuePair("fuel", ar.get(3)));
	JSONObject obj=NetworkManager.object(url2, param);
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
		alert.setMessage("fleet Recorded");
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

}