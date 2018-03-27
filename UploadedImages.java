package com.example.navigationd;

import java.util.ArrayList;
import java.util.List;

import network.NetworkManager;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import adapters.ImageAdapter;
import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.mainpackage.R;

@SuppressLint("NewApi")
public class UploadedImages extends Fragment {
	ArrayList<String> photo = new ArrayList<String>();
	ArrayList<String> description = new ArrayList<String>();
	ArrayList<String> id = new ArrayList<String>();
	GridView dList;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle args) {
		View view = inflater.inflate(R.layout.imagesview, container, false);
		dList = (GridView) view.findViewById(R.id.gridView1);
		new Projects().execute();
		return view;
	}

	public class Projects extends AsyncTask<Void, Integer, Integer> {
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}

		@Override
		protected Integer doInBackground(Void... arg0) {
			// TODO Auto-generated method stub
			final String url2 = Data.server + "images.php";
			List<NameValuePair> param = new ArrayList<NameValuePair>();
			String json = NetworkManager.jsonarray(url2, param);
			Log.e("images information", json);
			JSONArray jarray;
			try {
				jarray = new JSONArray(json);
				int x = 0;
				for (x = 0; x < jarray.length(); x++) {
					JSONObject obj = jarray.getJSONObject(x);
					photo.add(obj.getString("photo"));
					description.add(obj.getString("description"));
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
		protected void onPostExecute(Integer result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			ImageAdapter adapter = new ImageAdapter(getActivity(), photo,
					description, id);
			dList.setAdapter(adapter);

		}
	}

}