package com.example.navigationd;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import network.NetworkManager;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import adapters.DrawerAdapter;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.ContentValues;
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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.mainpackage.R;

public class ProjectManager extends Activity {
	ArrayList<String> text = new ArrayList<String>();
	ArrayList<Integer> image = new ArrayList<Integer>();
	private CharSequence mTitle;
	String[] menu;
	DrawerLayout dLayout;
	ListView dList;
	ArrayAdapter<String> adapter;
	private ActionBarDrawerToggle mDrawerToggle;
	String mynumber, myproject_id;

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

		Fragment detail = new RegEmployees();

		final Fragment major = new ProjectDefault();
		final Fragment material = new MaterialRequisition();
		final Fragment op = new OperationCost();
		final Fragment fleet = new FleetCondition();
		final Fragment progreport = new Progress_Report();
		final Fragment appointment = new CreateAppointment();
		final Fragment viewmati = new View_Material_Report();
		final Fragment report = new Progress_Report();
		final Fragment attend = new AttendancyProjects();
		//final Fragment sheet = new Time_Sheet_Fragment();

		fragmentManager.beginTransaction().replace(R.id.content_frame, major)
				.commit();

		menu = new String[] { "Material Requisition",  "Operation cost",
				"Fleet Condition", "Progress Report", "Create Appointment","View Appointment",
				"View Material Report","View Workers Attendance", "Logout" };
		
		dLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		dList = (ListView) findViewById(R.id.left_drawer);

		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, menu);
		//////////////////////////////////////////////////////////////////
		image.add(R.drawable.loadinstruction);
		image.add(R.drawable.costs);
		image.add(R.drawable.poles);
		image.add(R.drawable.projectsreport);
		image.add(R.drawable.appointments);
		image.add(R.drawable.appointments);
		image.add(R.drawable.pole);
		image.add(R.drawable.delivery);
		image.add(R.drawable.foreman);
		image.add(R.drawable.projects);
		image.add(R.drawable.logout);
		image.add(R.drawable.logout);
		
		text.addAll(Arrays.asList(menu));
		//////////////////////////////////////////////////////
		DrawerAdapter d = new DrawerAdapter(this, text, image);
		dList.setAdapter(d);
		dList.setSelector(android.R.color.holo_blue_dark);

		dList.setOnItemClickListener(new OnItemClickListener() {

			@TargetApi(Build.VERSION_CODES.HONEYCOMB)
			@SuppressLint("NewApi")
			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int position,
					long id) {

				if (position == 0) {
					fragmentManager.beginTransaction()
							.replace(R.id.content_frame, material).commit();
				} else if (position == 1) {
					fragmentManager.beginTransaction()
							.replace(R.id.content_frame, op).commit();
				} else if (position == 2) {
					fragmentManager.beginTransaction()
							.replace(R.id.content_frame, fleet).commit();
				} else if (position == 3) {
					fragmentManager.beginTransaction()
							.replace(R.id.content_frame, report).commit();
				}

				else if (position == 4) {
					Fragment detail = new CreateAppointment();
					fragmentManager.beginTransaction()
							.replace(R.id.content_frame, appointment).commit();
					dLayout.closeDrawers();

				}
				else if (position == 5) {
					Fragment detail = new ViewAppointments();
					fragmentManager.beginTransaction()
							.replace(R.id.content_frame, detail).commit();
					dLayout.closeDrawers();
					
				}else if (position == 6) {
					// profile
					Fragment detail = new MaterialReportForeman();
					fragmentManager.beginTransaction()
							.replace(R.id.content_frame, new View_Material_Report()).commit();
					dLayout.closeDrawers();
				}
//				
				 else if (position == 7) {
						fragmentManager.beginTransaction()
								.replace(R.id.content_frame,attend ).commit();
					}
				
				 else if (position == 7) {
						// profile
						Fragment detail = new CreateAppointment();
						fragmentManager.beginTransaction()
								.replace(R.id.content_frame, new AttendancyProjects()).commit();
						dLayout.closeDrawers();
					}

				else if (position == 8) {
					DBhelper h = new DBhelper(getApplicationContext());
					SQLiteDatabase db = h.getWritableDatabase();
					db.execSQL("delete  from users");
					db.execSQL("delete  from project");
					db.execSQL("delete  from foreman");
					ProjectManager.this.finish();
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
			param.add(new BasicNameValuePair("manager", arg0[0]));
			JSONObject obj = NetworkManager.object(Data.server
					+ "projectmanager.php", param);
			result = NetworkManager.result(obj);
			return result;
		}

		// Show Dialog Box with Progress bar
		@Override
		protected void onPostExecute(Integer result) {
			myproject_id = String.valueOf(result);
			// insert into database
			if (!Data.isRecord("select * from project", ProjectManager.this)) {
				insertforemanproject(myproject_id);
			} else {
				DBhelper help = new DBhelper(getApplicationContext());
				SQLiteDatabase db = help.getWritableDatabase();
				db.execSQL("update project set project='" + myproject_id + "'");
			}
		}
	}

	public void insertforemanproject(String project) {
		DBhelper help = new DBhelper(getApplicationContext());
		SQLiteDatabase db = help.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("project", project);
		db.insert("project", null, values);
	}

	public String myTelephone() {
		String result = null;
		DBhelper help = new DBhelper(ProjectManager.this);
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
