package com.example.navigationd;

import java.util.ArrayList;
import java.util.Arrays;

import adapters.DrawerAdapter;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.mainpackage.R;

public class ProcurementHome extends Activity {
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

		menu = new String[] { "Prepare Load Instruction",
				"Prepares Delivery Note", "Create Appointment","View Appointment",
				"View Material Requests","View Fleet Conditions", "Logout" };
		dLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		dList = (ListView) findViewById(R.id.left_drawer);

		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, menu);

		text.addAll(Arrays.asList(menu));
		image.add(R.drawable.projectsreport);
		image.add(R.drawable.poles);
		image.add(R.drawable.appointments);
		image.add(R.drawable.appointments);
		image.add(R.drawable.loadinstruction);
		image.add(R.drawable.poles);
		image.add(R.drawable.logout);
		
		final FragmentManager fragmentManager = getFragmentManager();
		// ////////////dfault
		fragmentManager.beginTransaction()
				.replace(R.id.content_frame, new MaterialProject()).commit();
		// ///////////////////

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
					Fragment detail = new Loadinstruction();
					fragmentManager.beginTransaction()
							.replace(R.id.content_frame, detail).commit();
					dLayout.closeDrawers();
				} else if (position == 1) {
					fragmentManager.beginTransaction()
							.replace(R.id.content_frame, new Delivery())
							.commit();
					dLayout.closeDrawers();
					
				} else if (position == 4) {
					fragmentManager.beginTransaction()
							.replace(R.id.content_frame, new MaterialProject())
							.commit();
					dLayout.closeDrawers();
				}
				else if (position == 5) {
					Fragment detail = new CreateProject();
					fragmentManager.beginTransaction()
							.replace(R.id.content_frame, new FleetConditions())
							.commit();
					dLayout.closeDrawers();
				}
				else if (position == 2) {
					Fragment detail = new CreateAppointment();
					fragmentManager.beginTransaction()
							.replace(R.id.content_frame, detail).commit();
					dLayout.closeDrawers();
					
				} else if (position == 3) {
					Fragment detail = new ViewAppointments();
					fragmentManager.beginTransaction()
							.replace(R.id.content_frame, detail).commit();
					dLayout.closeDrawers();
				}

				else if (position == 6) {
					DBhelper h = new DBhelper(getApplicationContext());
					SQLiteDatabase db = h.getWritableDatabase();
					db.execSQL("delete  from users");
					ProcurementHome.this.finish();
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

}
