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
public class CreateProject extends Fragment {
	EditText name;
	Spinner spin, foreman;
	Button click;
	ArrayList<String> params = new ArrayList<String>();

	ArrayList<String> usersfullnames = new ArrayList<String>();
	ArrayList<String> userstelephone = new ArrayList<String>();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle args) {
		View view = inflater.inflate(R.layout.createproject, container, false);

		name = (EditText) view.findViewById(R.id.editTextprojectname);
		spin = (Spinner) view.findViewById(R.id.spinnermanagers);
		foreman = (Spinner) view.findViewById(R.id.spinnerforeman);

		click = (Button) view.findViewById(R.id.buttoncreate);
		new FillManagers().execute();

		click.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				int index = spin.getSelectedItemPosition();
				int index2 = foreman.getSelectedItemPosition();
				params.add(name.getText().toString());
				params.add(userstelephone.get(index));
				params.add(userstelephone.get(index));
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
			final String url2 = Data.server + "create_project.php";
			List<NameValuePair> param = new ArrayList<NameValuePair>();
			param.add(new BasicNameValuePair("name", ar.get(0)));
			param.add(new BasicNameValuePair("manager", ar.get(1)));
			param.add(new BasicNameValuePair("foreman", ar.get(2)));
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
				alert.setMessage("Project Created");
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

			else if (result == 2) {
				AlertDialog.Builder alert = new AlertDialog.Builder(
						getActivity());
				alert.setMessage("manager already has another project");
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
			ArrayList<String> result = new ArrayList<String>();
			result = NetworkManager.Managers(Data.server + "allusers.php");
			usersfullnames = NetworkManager.ManagersFullnames(Data.server
					+ "allusers.php");
			userstelephone = result;
			return result;
		}

		// Show Dialog Box with Progress bar
		@Override
		protected void onPostExecute(ArrayList<String> result) {

			ArrayAdapter<String> adapter = new ArrayAdapter<String>(
					getActivity(), android.R.layout.simple_spinner_item,
					usersfullnames);
			spin.setAdapter(adapter);
			foreman.setAdapter(adapter);
		}
	}
}