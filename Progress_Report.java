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
import android.webkit.WebView.FindListener;
import android.widget.Button;
import android.widget.EditText;

@SuppressLint("NewApi")
public class Progress_Report extends Fragment {
	EditText report;
	Button button;
	ArrayList<String> params = new ArrayList<String>();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle args) {
		View view = inflater
				.inflate(R.layout.progress_report, container, false);

		report = (EditText) view.findViewById(R.id.editTextfullreport);
		button = (Button) view.findViewById(R.id.buttonpost);
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				params.add(report.getText().toString());
				new Upload().execute(params);

			}
		});
		return view;
	}

	public class Upload extends
			AsyncTask<ArrayList<String>, Integer, Integer> {
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

		}

		@Override
		protected Integer doInBackground(ArrayList<String>... arg0) {
			// TODO Auto-generated method stub
			int result = 0;
			ArrayList<String> ar = arg0[0];
			final String url2 = Data.server + "report.php";
			DefaultHttpClient client = new DefaultHttpClient();
			HttpPost post = new HttpPost(url2);
			List<NameValuePair> param = new ArrayList<NameValuePair>();
			param.add(new BasicNameValuePair("report", ar.get(0)));
			param.add(new BasicNameValuePair("project", Data
					.ProjectManager(getActivity())));
			JSONObject obj = NetworkManager.object(url2, param);
			result = NetworkManager.result(obj);
			return result;

		}

		// Show Dialog Box with Progress bar
		@Override
		protected void onPostExecute(Integer result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			if (result == 1) {
				AlertDialog.Builder alert = new AlertDialog.Builder(
						getActivity());
				alert.setMessage("report successfully processed");
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