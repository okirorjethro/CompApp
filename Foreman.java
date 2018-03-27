package com.example.navigationd;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import network.NetworkManager;
import adapters.DrawerAdapter;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

import com.mainpackage.R;

public class Foreman extends Activity {
	ArrayList<String> text = new ArrayList<String>();
	ArrayList<Integer> image = new ArrayList<Integer>();
	private CharSequence mTitle;
	String[] menu;
	DrawerLayout dLayout;
	ListView dList;
	ArrayAdapter<String> adapter;
	String mynumber, myproject_id;
	private ActionBarDrawerToggle mDrawerToggle;

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		final FragmentManager fragmentManager = getFragmentManager();
		final android.app.ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setDisplayUseLogoEnabled(true);
		actionBar.setIcon(R.drawable.abc_ab_share_pack_holo_dark);
		actionBar.setHomeButtonEnabled(true);
		actionBar.setDisplayShowHomeEnabled(true);
		mynumber = Data.myTelephone(this);
		new MyProject().execute(mynumber);
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		myproject_id = Data.ForemanProject(this);
		final Fragment timesheet = new TimeSheet();
		final Fragment attendance = new Attendency();
		final Fragment materialreport = new MaterialReportForeman();

		menu = new String[] { "Prepares Time Sheet",
				"Attendance Lists", "Material report","Create Appointment","View Appointments","Take photo FieldActivity", "Logout" };

		dLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		dList = (ListView) findViewById(R.id.left_drawer);

		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, menu);
		text.addAll(Arrays.asList(menu));
		image.add(R.drawable.load);
		image.add(R.drawable.allocate);
		image.add(R.drawable.pole);
		image.add(R.drawable.appointments);
		image.add(R.drawable.appointments);
		image.add(R.drawable.transformer);
		image.add(R.drawable.logout);
		
		/////////////////default home
		fragmentManager.beginTransaction()
		.replace(R.id.content_frame, materialreport)
		.commit();
		/////////////
	
		DrawerAdapter d = new DrawerAdapter(this, text, image);
		dList.setAdapter(d);
		dList.setSelector(android.R.color.holo_blue_dark);

		dList.setOnItemClickListener(new OnItemClickListener() {

			@TargetApi(Build.VERSION_CODES.HONEYCOMB)
			@SuppressLint("NewApi")
			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int position,
					long id) {

				if (position == 6) {
					DBhelper h = new DBhelper(getApplicationContext());
					SQLiteDatabase db = h.getWritableDatabase();
					db.execSQL("delete  from users");
					db.execSQL("delete  from project");
					db.execSQL("delete  from foreman");
					Foreman.this.finish();
				}

				else if (position == 0) {
					fragmentManager.beginTransaction()
							.replace(R.id.content_frame, timesheet).commit();
					actionBar.setTitle(menu[position]);
				}
				
				else if (position == 3) {
					Fragment detail = new CreateAppointment();
					fragmentManager.beginTransaction()
							.replace(R.id.content_frame, detail).commit();
					dLayout.closeDrawers();
				} 
				else if (position == 4) {
					Fragment detail = new ViewAppointments();
					fragmentManager.beginTransaction()
							.replace(R.id.content_frame, detail).commit();
					dLayout.closeDrawers();
				}
				else if (position == 5) {
					Intent image=new Intent(getApplicationContext(),UploadNow.class);
					startActivity(image);
				}

				
				

				else if (position == 2) {
					fragmentManager.beginTransaction()
							.replace(R.id.content_frame, materialreport)
							.commit();
					actionBar.setTitle(menu[position]);
				}
				else if (position == 1) {
					fragmentManager.beginTransaction()
							.replace(R.id.content_frame, attendance)
							.commit();
					actionBar.setTitle(menu[position]);
				}

				else {
					dLayout.closeDrawers();
					Bundle args = new Bundle();
					args.putString("Menu", menu[position]);
					Fragment detail = new DetailFragment();
					detail.setArguments(args);

					fragmentManager.beginTransaction()
							.replace(R.id.content_frame, detail).commit();
					actionBar.setTitle(menu[position]);
				}
				dLayout.closeDrawers();

			}

		});
		mDrawerToggle = new ActionBarDrawerToggle(this, /*													 */
		dLayout, /* DrawerLayout object */
		R.drawable.ic_launcher, /* nav drawer image to replace 'Up' caret */
		R.string.app_name, /*
							 * "open drawer" description for accessibility
							 */
		R.string.menu /*
					 * "close drawer" description for accessibility
					 */
		) {
			@Override
			public void onDrawerClosed(View drawerView) {
				super.onDrawerClosed(drawerView);
				//actionBar.setTitle(R.string.app_name);
				actionBar.setDisplayHomeAsUpEnabled(false);

			}

			@Override
			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
				//actionBar.setTitle("opened");
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
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		} else if (id == android.R.id.home) {
			mDrawerToggle.onOptionsItemSelected(item);
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	public class MyProject extends AsyncTask<String, Integer, Integer> {
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

		}

		@Override
		protected Integer doInBackground(String... arg0) {
			int result = 0;
			List<NameValuePair> param = new ArrayList<NameValuePair>();
			param.add(new BasicNameValuePair("foreman", arg0[0]));
			JSONObject obj = NetworkManager.object(Data.server
					+ "myproject.php", param);
			result = NetworkManager.result(obj);
			return result;
		}

		// Show Dialog Box with Progress bar
		@Override
		protected void onPostExecute(Integer result) {
			myproject_id = String.valueOf(result);
			// insert into database
			if (!Data.isRecord("select * from foreman", Foreman.this)) {
				insertforemanproject(myproject_id);
			} else {
				DBhelper help = new DBhelper(getApplicationContext());
				SQLiteDatabase db = help.getWritableDatabase();
				db.execSQL("update foreman set project='" + myproject_id + "'");
			}

		}
	}

	public void insertforemanproject(String project) {
		DBhelper help = new DBhelper(getApplicationContext());
		SQLiteDatabase db = help.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("project", project);
		db.insert("foreman", null, values);
	}

	public String myTelephone() {
		String result = null;
		DBhelper help = new DBhelper(Foreman.this);
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
