package com.example.navigationd;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
import android.content.Intent;
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
public class MaterialRequisition extends Fragment {
	EditText employee, date, material, quantity, percost, extended;
	Button click;
	ArrayList<String> params = new ArrayList<String>();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle args) {
		View view = inflater.inflate(R.layout.material, container, false);
		employee = (EditText) view.findViewById(R.id.editText1);
		date = (EditText) view.findViewById(R.id.editText2);
		material = (EditText) view.findViewById(R.id.EditText01);
		quantity = (EditText) view.findViewById(R.id.EditText02);
		percost = (EditText) view.findViewById(R.id.EditText03);
		extended = (EditText) view.findViewById(R.id.EditText04);

		click = (Button) view.findViewById(R.id.button1);

		click.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				params.add(employee.getText().toString());
				params.add(date.getText().toString());
				params.add(material.getText().toString());
				params.add(quantity.getText().toString());
				params.add(percost.getText().toString());
				params.add(extended.getText().toString());
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
			int result = 0;
			ArrayList<String> ar = arg0[0];
			final String url2 = Data.server + "material_insert.php";
			DefaultHttpClient client = new DefaultHttpClient();
			HttpPost post = new HttpPost(url2);
			List<NameValuePair> param = new ArrayList<NameValuePair>();
			param.add(new BasicNameValuePair("employee", Data.myTelephone(getActivity())));
			param.add(new BasicNameValuePair("date", ar.get(1)));
			param.add(new BasicNameValuePair("material", ar.get(2)));
			param.add(new BasicNameValuePair("quantity", ar.get(3)));
			param.add(new BasicNameValuePair("unit", ar.get(4)));
			param.add(new BasicNameValuePair("extended", ar.get(5)));
			param.add(new BasicNameValuePair("project", Data.ProjectManager(getActivity())));
			UrlEncodedFormEntity uefa;
			try {
				uefa = new UrlEncodedFormEntity(param);
				post.setEntity(uefa);
				HttpResponse response;
				try {
					response = client.execute(post);
					HttpEntity entity = response.getEntity();
					String data = EntityUtils.toString(entity);
					Log.e("password change", data);
					JSONObject obj = new JSONObject(data);
					result = obj.getInt("success");

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					result = 0;
				}
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
				alert.setMessage("material Posted");
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