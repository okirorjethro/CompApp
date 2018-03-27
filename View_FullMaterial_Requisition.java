package com.example.navigationd;

import java.util.ArrayList;
import java.util.List;

import network.NetworkManager;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mainpackage.R;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class View_FullMaterial_Requisition extends Activity {
	TextView  tvprojectmanname,tvrequisitiondate, tvmaterialrequis,tvmaterialquantity,  tvunitprice, tvextendedprice;
	ArrayList<String> items = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_material_requisition);
		
		tvprojectmanname = (TextView) findViewById(R.id.tvprojectmanname);
		tvrequisitiondate = (TextView) findViewById(R.id.tvrequisitiondate);
		 tvmaterialrequis = (TextView) findViewById(R.id.tvmaterialrequis);
		 tvmaterialquantity = (TextView) findViewById(R.id.tvmaterialquantity);
		 tvunitprice = (TextView) findViewById(R.id.tvunitprice);
		 tvextendedprice = (TextView) findViewById(R.id.tvextendedprice);
		
		Intent i = getIntent();
		String id = i.getStringExtra("id");
		new FullDetails().execute(id);
	}

	public class FullDetails extends AsyncTask<String, Integer, Integer> {
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

		}

		@Override
		protected Integer doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			final String url2 = Data.server + "view_material_requisition.php";
			List<NameValuePair> param = new ArrayList<NameValuePair>();
			param.add(new BasicNameValuePair("id", arg0[0]));
			String json = NetworkManager.jsonarray(url2, param);
			Log.e("material requisition", json);

			JSONArray jarray;
			try {
				jarray = new JSONArray(json);
				int x = 0;
				for (x = 0; x < jarray.length(); x++) {
					JSONObject obj = jarray.getJSONObject(x);
					items.add(obj.getString("employee"));
					items.add(obj.getString("date"));
					items.add(obj.getString("material"));
					items.add(obj.getString("quantity"));
					items.add(obj.getString("per_unit_cost"));
					items.add(obj.getString("extended_cost"));
					

				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return null;
		}

		// Show Dialog Box with Progress bar
		@Override
		protected void onPostExecute(Integer result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			tvprojectmanname.setText(items.get(0));
			tvrequisitiondate.setText(items.get(1));
			tvmaterialrequis.setText(items.get(2));
			tvmaterialquantity.setText(items.get(3));
			tvunitprice.setText(items.get(4));
			tvextendedprice.setText(items.get(4));
			

		}
	}

}
