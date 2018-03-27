package com.example.navigationd;

import java.util.ArrayList;
import java.util.List;

import network.NetworkManager;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import adapters.Load_Instruction_Adapter;
import adapters.PostAdapter;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.mainpackage.R;

@SuppressLint("NewApi")
public class View_Load_Instruction extends Activity {
	ArrayList<String> Material_Desc = new ArrayList<String>();
	ArrayList<String> Instruction = new ArrayList<String>();
	ArrayList<String> sender = new ArrayList<String>();
	ArrayList<String> reciever = new ArrayList<String>();

	ArrayList<String> id = new ArrayList<String>();
	ListView dList;
	TextView header;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		header = (TextView) findViewById(R.id.textViewmainheader);
		header.setText("Choose a project below");
		dList = (ListView) findViewById(R.id.listView1);

		Intent i = getIntent();
		String project = i.getStringExtra("project");
		new Projects().execute(project);
		dList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				Intent i = new Intent(getApplicationContext(),
						ViewLoadInstruction.class);
				i.putExtra("project", id.get(position));
				startActivity(i);

			}
		});
	}

	public class Projects extends AsyncTask<String, Integer, Void> {
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}

		@Override
		protected Void doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			final String url2 = Data.server + "view_load_instruction.php";
			List<NameValuePair> param = new ArrayList<NameValuePair>();

			param.add(new BasicNameValuePair("project", arg0[0]));
			String json = NetworkManager.jsonarray(url2, param);
			Log.e("View Load Instruction Error", json);
			JSONArray jarray;
			try {
				jarray = new JSONArray(json);
				int x = 0;
				for (x = 0; x < jarray.length(); x++) {
					JSONObject obj = jarray.getJSONObject(x);

					Material_Desc.add(obj.getString("Material_Desc"));
					Instruction.add(obj.getString("Instruction"));
					sender.add(obj.getString("sender"));
					reciever.add(obj.getString("reciever"));
					id.add(obj.getString("id"));
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return null;
		}

		// Show Dialog Box with Progress bar
		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			PostAdapter adapter=new PostAdapter(View_Load_Instruction.this, Material_Desc, Instruction, id);
			dList.setAdapter(adapter);

		}
	}

}