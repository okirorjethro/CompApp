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

import adapters.DrawerAdapter;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Build;
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

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class RegEmployees extends Fragment {
	private ProgressDialog prgDialog;
	// Progress Dialog type (0 - for Horizontal progress bar)
	public static final int progress_bar_type = 0;
	private EditText fullname, telephone, pin, email;
	private Spinner spin;
	private Button signup;
	ArrayList<String> params = new ArrayList<String>();

	@SuppressLint("NewApi")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle args) {
		View view = inflater.inflate(R.layout.register, container, false);
		fullname = (EditText) view.findViewById(R.id.editTextfullname);
		telephone = (EditText) view.findViewById(R.id.editTexttelephone);
		pin = (EditText) view.findViewById(R.id.editTextpin);
		email = (EditText) view.findViewById(R.id.editTextemail);
		signup = (Button) view.findViewById(R.id.buttonregister);
		spin = (Spinner) view.findViewById(R.id.spinner1);
		ArrayList<String> roles = new ArrayList<String>();
		roles.add("ceo");
		roles.add("it");
		roles.add("project");
		roles.add("procurement");
		roles.add("foreman");
		roles.add("supervisor");
		ArrayList<Integer> image = new ArrayList<Integer>();
		image.add(R.drawable.home);
		image.add(R.drawable.homepagr);
		image.add(R.drawable.team);
		image.add(R.drawable.loadinstruction);
		image.add(R.drawable.transformer);
		image.add(R.drawable.foreman);

		DrawerAdapter d=new DrawerAdapter(getActivity(), roles, image);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_spinner_item, roles);
		spin.setAdapter(d);

		signup.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				params.add(fullname.getText().toString());
				params.add(telephone.getText().toString());
				params.add(pin.getText().toString());
				params.add(spin.getSelectedItem().toString());
				params.add(email.getText().toString());
				try {
					new OnlineTast().execute(params);
				} catch (Exception e) {

				}

			}
		});
		return view;
	}

	public class OnlineTast extends
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
			params = arg0[0];
			String fullname, telephone, pin, email;
			fullname = params.get(0);
			telephone = params.get(1);
			pin = params.get(2);
			email = params.get(4);
			final String url2 = Data.server + "register.php";
			DefaultHttpClient client = new DefaultHttpClient();
			HttpPost post = new HttpPost(url2);
			List<NameValuePair> param = new ArrayList<NameValuePair>();
			param.add(new BasicNameValuePair("fullname", fullname));
			param.add(new BasicNameValuePair("telephone", telephone));
			param.add(new BasicNameValuePair("pin", pin));
			param.add(new BasicNameValuePair("role", params.get(3)));
			param.add(new BasicNameValuePair("email", email));
			UrlEncodedFormEntity uefa;
			try {
				uefa = new UrlEncodedFormEntity(param);
				post.setEntity(uefa);
				HttpResponse response;
				try {
					response = client.execute(post);
					HttpEntity entity = response.getEntity();
					String data = EntityUtils.toString(entity);
					Log.e("tenant registration feedback", data);
					JSONObject obj = new JSONObject(data);
					int done = obj.getInt("success");
					result = done;
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
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
				alert.setMessage("success");
				alert.setTitle("Success");
				alert.show();
			} else if (result == 0) {
				AlertDialog.Builder alert = new AlertDialog.Builder(
						getActivity());
			
				alert.setMessage("success");
				alert.setTitle("Success");
				alert.show();

			} else if (result == 2) {
				// this account already exists
				// try claiming it
				// sen
			}
		}

	}

}