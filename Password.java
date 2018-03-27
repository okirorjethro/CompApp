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

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class Password extends Activity {
	private ProgressDialog prgDialog;
	// Progress Dialog type (0 - for Horizontal progress bar)
	public static final int progress_bar_type = 0;
	EditText old, npass, confirm;
	Button change;
	ArrayList<String> params = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.password);
		old = (EditText) findViewById(R.id.editTextoldpassword);
		npass = (EditText) findViewById(R.id.editTextnewpin);
		confirm = (EditText) findViewById(R.id.editTextconfirm);
		change = (Button) findViewById(R.id.buttonchange);
		change.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				params.add(old.getText().toString());
				params.add(npass.getText().toString());
				new ChangeTast().execute(params);

			}
		});

	}

	public class ChangeTast extends
			AsyncTask<ArrayList<String>, Integer, Integer> {
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			showDialog(progress_bar_type);
		}

		@Override
		protected Integer doInBackground(ArrayList<String>... arg0) {
			// TODO Auto-generated method stub
			int result = 0;
			params = arg0[0];
			String newpin, pin;
			newpin = params.get(1);
			pin = params.get(0);

			final String url2 = Data.server + "changepassword.php";
			DefaultHttpClient client = new DefaultHttpClient();
			HttpPost post = new HttpPost(url2);
			List<NameValuePair> param = new ArrayList<NameValuePair>();
			param.add(new BasicNameValuePair("telephone", mynumber()));
			param.add(new BasicNameValuePair("oldpin", pin));
			param.add(new BasicNameValuePair("newpin", newpin));
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
			dismissDialog(progress_bar_type);
			if (result == 1) {
				AlertDialog.Builder alert = new AlertDialog.Builder(
						getApplicationContext());
				Log.e("system role", role());
				alert.setMessage("Password Changed");
				alert.setTitle("Success");
				alert.setPositiveButton("Okay",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface arg0, int arg1) {
								if (role().equals("landlord")) {
									Intent intent = new Intent(
											getApplicationContext(),
											MainActivity.class);
									startActivity(intent);
									Password.this.finish();
								}

								else if (role().equals("tenant")) {

									Intent intent = new Intent(
											getApplicationContext(),
											MainActivity.class);
									startActivity(intent);
									Password.this.finish();

								}

							}
						});
				alert.show();
			} else if (result == 0) {
				AlertDialog.Builder alert = new AlertDialog.Builder(
						getApplicationContext());
				alert.setMessage("Failed");
				alert.setTitle("try again");
				alert.show();
				Intent intent = new Intent(
						getApplicationContext(),
						MainActivity.class);
				startActivity(intent);
				Password.this.finish();
			}
		}

	}

	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case progress_bar_type:
			prgDialog = new ProgressDialog(this);
			prgDialog.setTitle("Changing Password");
			prgDialog.setMessage("Please wait");
			prgDialog.setIndeterminate(false);
			prgDialog.setMax(100);
			prgDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			prgDialog.setCancelable(true);
			prgDialog.show();
			return prgDialog;
		default:
			return null;
		}

	}

	public String mynumber() {
		String result = null;
		DBhelper help = new DBhelper(this);
		SQLiteDatabase db = help.getReadableDatabase();
		Cursor cur = db.rawQuery("select * from users", null);
		int row = cur.getCount();
		if (row > 0) {
			while (cur.moveToNext()) {
				result = cur.getString(1);
			}
		}
		return result;
	}

	public String role() {
		String result = null;
		DBhelper help = new DBhelper(this);
		SQLiteDatabase db = help.getReadableDatabase();
		Cursor cur = db.rawQuery("select * from users", null);
		int row = cur.getCount();

		if (row > 0) {
			while (cur.moveToNext()) {
				result = cur.getString(3);
				Log.e("role", result);
			}
		}
		cur.close();
		db.close();

		return result;
	}

}
