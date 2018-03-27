package com.example.navigationd;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import network.NetworkManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.mainpackage.R;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

@SuppressLint("NewApi")
public class OperationCost extends Fragment {
	EditText employee, payrol, descript;
	Button send;
	ArrayList<String> params = new ArrayList<String>();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle args) {
		View view = inflater
				.inflate(R.layout.fragment_operation_cost, container, false);
		
		employee = (EditText) view.findViewById(R.id.editText1);
		payrol = (EditText) view.findViewById(R.id.EditText01);
		descript = (EditText) view.findViewById(R.id.EditText02);
		send = (Button) view.findViewById(R.id.button1);
		send.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				params.add(employee.getText().toString());
				params.add(payrol.getText().toString());
				params.add(descript.getText().toString());
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
	final String url2 = Data.server + "operational_insert.php";
	DefaultHttpClient client = new DefaultHttpClient();
	HttpPost post = new HttpPost(url2);
	List<NameValuePair> param = new ArrayList<NameValuePair>();
	param.add(new BasicNameValuePair("employee", ar.get(0)));
	param.add(new BasicNameValuePair("payrole", ar.get(1)));
	param.add(new BasicNameValuePair("description", ar.get(2)));
	param.add(new BasicNameValuePair("project", Data
			.ForemanProject(getActivity())));
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
		alert.setMessage("Operational Cost Recorded");
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