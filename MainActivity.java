package com.example.navigationd;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends Activity {
	ArrayList<String> text = new ArrayList<String>();
	ArrayList<Integer> image = new ArrayList<Integer>();
	private CharSequence mTitle;
	String[] menu;
	DrawerLayout dLayout;
	ListView dList;
	ArrayAdapter<String> adapter;
	private ActionBarDrawerToggle mDrawerToggle;

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		final android.app.ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setDisplayUseLogoEnabled(true);
		actionBar.setIcon(R.drawable.abc_ab_share_pack_holo_dark);
		actionBar.setHomeButtonEnabled(true);
		actionBar.setDisplayShowHomeEnabled(true);
		
		

		menu = new String[] { "Register", "Logout" };
		dLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		dList = (ListView) findViewById(R.id.left_drawer);

		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, menu);
		text.addAll(Arrays.asList(menu));
		image.add(R.drawable.ic_launcher);
		image.add(R.drawable.ic_launcher);
		DrawerAdapter d = new DrawerAdapter(this, text, image);

		dList.setAdapter(d);
		dList.setSelector(android.R.color.holo_blue_dark);
		
		final FragmentManager fragmentManager = getFragmentManager();
		fragmentManager.beginTransaction()
		.replace(R.id.content_frame, new Default()).commit();
		

		dList.setOnItemClickListener(new OnItemClickListener() {

			@TargetApi(Build.VERSION_CODES.HONEYCOMB)
			@SuppressLint("NewApi")
			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int position,
					long id) {
				
				if (position == 0) {
					dLayout.closeDrawers();
					Fragment detail = new RegEmployees();
					fragmentManager.beginTransaction()
							.replace(R.id.content_frame, detail).commit();
				} else if (position == 1) {
					DBhelper h = new DBhelper(getApplicationContext());
					SQLiteDatabase db = h.getWritableDatabase();
					db.execSQL("delete  from users");
					MainActivity.this.finish();
				} else {
					dLayout.closeDrawers();
					Bundle args = new Bundle();
					args.putString("Menu", menu[position]);
					Fragment detail = new DetailFragment();
					detail.setArguments(args);

					fragmentManager.beginTransaction()
							.replace(R.id.content_frame, detail).commit();
					actionBar.setTitle(menu[position]);
				}

			}

		});
		mDrawerToggle = new ActionBarDrawerToggle(MainActivity.this, /*													 */
		dLayout, /* DrawerLayout object */
		R.drawable.ic_launcher, /* nav drawer image to replace 'Up' caret */
		R.string.app_name,

		R.string.menu

		) {

			@Override
			public void onDrawerClosed(View drawerView) {
				super.onDrawerClosed(drawerView);
				actionBar.setTitle(R.string.app_name);
				actionBar.setDisplayHomeAsUpEnabled(false);

			}

			@Override
			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
				actionBar.setTitle("opened");
				actionBar.setDisplayHomeAsUpEnabled(true);

			}
		};

		dLayout.setDrawerListener(mDrawerToggle);
		// see if the drawer is open or closed
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		} else if (id == android.R.id.home) {
			mDrawerToggle.onOptionsItemSelected(item);
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	public class Sendchat extends AsyncTask<ArrayList<String>, Void, String> {
		@Override
		protected String doInBackground(ArrayList<String>... arg0) {
			// TODO Auto-generated method stub
			String result = null;
			final String url2 = Data.server + "sendchat.php";
			DefaultHttpClient client = new DefaultHttpClient();
			HttpPost post = new HttpPost(url2);
			List<NameValuePair> param = new ArrayList<NameValuePair>();
			param.add(new BasicNameValuePair("fullname", "role"));
			param.add(new BasicNameValuePair("sender", "my number"));
			param.add(new BasicNameValuePair("receiver", "employee"));
			param.add(new BasicNameValuePair("message", arg0[0].get(0)));
			param.add(new BasicNameValuePair("appointment_date", ""));
			UrlEncodedFormEntity uefa;
			try {
				uefa = new UrlEncodedFormEntity(param);
				post.setEntity(uefa);
				HttpResponse response;
				response = client.execute(post);
				HttpEntity entity = response.getEntity();
				String data = EntityUtils.toString(entity);
				Log.e("message sent", data);
				JSONObject ob = new JSONObject(data);
				int responsecode = ob.getInt("success");
				if (responsecode == 1) {
					result = "success";
				} else {
					result = "failed";
				}

			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			// json parse and put this on list
			if (result.equals("success")) {

				AlertDialog.Builder alert = new AlertDialog.Builder(
						MainActivity.this);
				alert.setMessage("Complain Sent please wait for the reply");
				alert.setTitle("Success");
				alert.show();
			} else {
				// eror
			}

		}

	}

}