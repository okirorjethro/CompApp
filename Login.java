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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mainpackage.R;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
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
import android.widget.TextView;
import android.widget.Toast;

public class Login extends Activity {
	private EditText tel, pin;
	private Button login, sign;
	private TextView res;
	private ProgressDialog prgDialog;
	// Progress Dialog type (0 - for Horizontal progress bar)
	public static final int progress_bar_type = 0;
	ArrayList<String> params = new ArrayList<String>();
	ArrayList<String> input = new ArrayList<String>();
	String mynumber = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		tel = (EditText) findViewById(R.id.editText1);
		pin = (EditText) findViewById(R.id.editText2);
		login = (Button) findViewById(R.id.buttonlogin);
		login.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				// online login is here
				mynumber = tel.getText().toString();
				params.add(tel.getText().toString());
				params.add(pin.getText().toString());
				new LoginTask().execute(params);
				/*
				 * 
				 * if (isValidUser(tel.getText().toString(), pin.getText()
				 * .toString())) { try { Intent intent = new
				 * Intent(getApplicationContext(), MainActivity.class);
				 * startActivity(intent); } catch (Exception e) {
				 * Toast.makeText(getApplicationContext(), "login failed",
				 * Toast.LENGTH_LONG).show(); }
				 * 
				 * } else { res.setTextColor(Color.RED);
				 * res.setText("wrong username and pasword combination"); }
				 */
			}
		});
	}

	// login is online
	// after login keep data local
	// so there is no hustle next time
	// keep logged

	public boolean isValidUser(String telephone, String pin) {
		boolean result = false;
		try {
			DBhelper help = new DBhelper(this);
			SQLiteDatabase db = help.getReadableDatabase();
			Cursor cur = db.rawQuery("select * from users where telephone='"
					+ telephone + "' and pin='" + pin + "'", null);
			int row = cur.getCount();
			Log.e("records", "" + row);
			if (row > 0) {
				result = true;
			}

			else if (row == 0) {
				result = true;
			}
		} catch (Exception er) {
			res.setText(er.toString());
		}

		return result;
	}

	public class LoginTask extends
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
			String telephone, pin;
			telephone = params.get(0);
			pin = params.get(1);

			final String url2 = Data.server + "login.php";
			DefaultHttpClient client = new DefaultHttpClient();
			HttpPost post = new HttpPost(url2);
			List<NameValuePair> param = new ArrayList<NameValuePair>();
			param.add(new BasicNameValuePair("telephone", telephone));
			param.add(new BasicNameValuePair("pin", pin));
			UrlEncodedFormEntity uefa;
			try {
				uefa = new UrlEncodedFormEntity(param);

				post.setEntity(uefa);
				HttpResponse response;
				try {
					response = client.execute(post);
					HttpEntity entity = response.getEntity();
					String data = EntityUtils.toString(entity);
					Log.e("feed back from server booking", data);
					JSONArray jarray = new JSONArray(data);
					int x = 0;
					for (x = 0; x < jarray.length(); x++) {
						JSONObject obj = jarray.getJSONObject(x);
						// keep track of other details
						input.add(obj.getString("fullname"));
						input.add(obj.getString("telephone"));
						input.add(obj.getString("pin"));
						input.add(obj.getString("role"));
						result = 1;
					}

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
				if (isVisitor()) {

					LocalRegistryreg(input.get(0), input.get(1), input.get(2),
							input.get(3));
					// now here we direct accordingly
					
					if (input.get(3).equals("it")) {
						Intent intent = new Intent(getApplicationContext(),
								MainActivity.class);
						startActivity(intent);
						Login.this.finish();
					}
					else if (input.get(3).equals("ceo")) {
						Intent intent = new Intent(getApplicationContext(),
								CeoHome.class);
						startActivity(intent);
						Login.this.finish();

					} else if (input.get(3).equals("project")) {
						Intent intent = new Intent(getApplicationContext(),
								ProjectManager.class);
						startActivity(intent);
						Login.this.finish();

					} else if (input.get(3).equals("procurement")) {
						Intent intent = new Intent(getApplicationContext(),
								ProcurementHome.class);
						startActivity(intent);
						Login.this.finish();

					} else if (input.get(3).equals("foreman")) {
						Intent intent = new Intent(getApplicationContext(),
								Foreman.class);
						startActivity(intent);
						Login.this.finish();

					}
				} else {
					Intent intent = new Intent(getApplicationContext(),
							MainActivity.class);
					startActivity(intent);
				}

			} else if (result == 0) {
				Toast.makeText(getApplicationContext(), "Login Failed",
						Toast.LENGTH_LONG).show();

			}

		}
	}

	public void LocalRegistryreg(String fullname, String telephone, String pin,
			String role) {
		int result = 0;

		try {
			DBhelper help = new DBhelper(getApplicationContext());
			SQLiteDatabase db = help.getWritableDatabase();
			ContentValues values = new ContentValues();
			values.put("fullname", fullname);
			values.put("telephone", telephone);
			values.put("pin", pin);
			values.put("role", role);
			db.insert("users", null, values);
			result = 1;

		} catch (Exception er) {

		}

	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case progress_bar_type:
			prgDialog = new ProgressDialog(this);
			prgDialog.setTitle("Login");
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

	public boolean isVisitor() {
		boolean result = false;
		DBhelper help = new DBhelper(this);
		SQLiteDatabase db = help.getReadableDatabase();
		Cursor cur = db.rawQuery("select * from users", null);
		int row = cur.getCount();
		Log.e("records", "" + row);
		if (row > 0) {
			result = false;
		}

		else if (row == 0) {
			result = true;
		}
		db.close();
		cur.close();
		return result;
	}

}
