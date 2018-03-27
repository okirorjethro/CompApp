package com.example.navigationd;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import network.NetworkManager;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import com.mainpackage.R;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

@SuppressLint("NewApi")
public class GetImageActivity extends Fragment {
	EditText txt_photoupload;
	private String selectedImagePath;
	Button Buttongallary,btnimagesend;
	ImageView imgviewhoto;
	private static final int SELECT_PICTURE = 1;
	private static final int RESULT_OK = 0;
	
	ArrayList<String> params = new ArrayList<String>();
	String mytelephone;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle args) {
		View view = inflater
				.inflate(R.layout.photo_upload, container, false);
		
		txt_photoupload = (EditText) view.findViewById(R.id.txt_photoupload);
		
		Buttongallary = (Button) view.findViewById(R.id.Buttongallary);
		btnimagesend = (Button) view.findViewById(R.id.btnimagesend);
		
		imgviewhoto=(ImageView) view.findViewById(R.id.imgviewhoto);
		

		Buttongallary.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) { 
			
				Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select Picture"), SELECT_PICTURE);
				Log.e("", "**********************AM HERE*****************");
				

			}
		}); 
		
		return view;
	} 
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
	    if (resultCode == RESULT_OK) {
	        if (requestCode == SELECT_PICTURE) {
	            Uri selectedImageUri = data.getData();
	            selectedImagePath = getPath(selectedImageUri);	           
	           
	            System.out.println("Image Path : ***************************************************************************************************************************");
	            imgviewhoto.setImageURI(selectedImageUri); 
	            Log.e("", "*****HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH******\t\n"+selectedImagePath);
	        }
	    }
	}
	public String getPath(Uri uri) {
	    String[] projection = { MediaStore.Images.Media.DATA };
	    Cursor cursor = managedQuery(uri, projection, null, null, null);
	    int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
	    cursor.moveToFirst();
	    return cursor.getString(column_index);
	}
	private Cursor managedQuery(Uri uri, String[] projection, Object object,
			Object object2, Object object3) {
		// TODO Auto-generated method stub
		return null;
	}
}

