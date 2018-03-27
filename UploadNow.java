package com.example.navigationd;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.util.EntityUtils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.content.CursorLoader;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.mainpackage.R;

public class UploadNow extends Activity {

	final static int REQUEST_CODE = 1;
	final static String[] BUTTON_LABEL = { "Select Image", "Upload Image" };

	// CHANGE THIS TO YOUR URL
	final static String UPLOAD_SERVER_URI = Data.server + "upload.php";

	ProgressDialog progressDialog;
	ScrollView scrollView;
	LinearLayout linearLayout;
	ImageView imageView;
	TextView imageLocationTextView;
	Button selectImgBtn;
	Button uploadBtn;
	EditText desc;

	String imagePath;
	String imageName;
	String describe;

	long imageSize = 0; // kb

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.upload);
		imageView = (ImageView) findViewById(R.id.imageView1);
		selectImgBtn = (Button) findViewById(R.id.buttonbrowse);
		uploadBtn = (Button) findViewById(R.id.buttonupload);
		desc = (EditText) findViewById(R.id.editText1);
		imageLocationTextView = (TextView) findViewById(R.id.textViewpath);

		selectImgBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				// SELECT IMAGE

				Intent intent = new Intent();
				intent.setType("image/*");
				intent.setAction(Intent.ACTION_GET_CONTENT);
				startActivityForResult(
						Intent.createChooser(intent, "Select Image"),
						REQUEST_CODE);

			}
		});

		uploadBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				// UPLOAD IMAGE

				if (imagePath == null) {
					// IF NO IMAGE SELECTED DO NOTHING
					Toast.makeText(getApplicationContext(),
							"No image selected", Toast.LENGTH_SHORT).show();
					return;
				}

				progressDialog = createDialog();
				progressDialog.show();
				describe = desc.getText().toString();

				// EXECUTED ASYNCTASK TO UPLOAD IMAGE
				new ImageUploader().execute(describe);

			}
		});
	}

	private ProgressDialog createDialog() {
		ProgressDialog progressDialog = new ProgressDialog(this);
		progressDialog.setMessage("Please wait.. Uploading File");
		progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		progressDialog.setCancelable(false);

		return progressDialog;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {

			Uri selectedImageUri = data.getData();
			String realPath;
			// SDK < API11
			if (Build.VERSION.SDK_INT < 11)
				realPath = getRealPathFromURI_BelowAPI11(this, data.getData());

			// SDK >= 11 && SDK < 19
			else if (Build.VERSION.SDK_INT < 19)
				realPath = getRealPathFromURI_API11to18(this, data.getData());

			// SDK > 19 (Android 4.4)
			else
				realPath = getRealPathFromURI_API19(this, data.getData());

			// GET IMAGE PATH
			imagePath = getRealPathFromURI_API19(getApplicationContext(),
					selectedImageUri);

			// IMAGE NAME
			imageName = imagePath.substring(imagePath.lastIndexOf("/"));

			imageSize = getFileSize(imagePath);
			imageLocationTextView.setText(realPath);

			// DECODE TO BITMAP
			Bitmap bitmap = BitmapFactory.decodeFile(imagePath);

			// DISPLAY IMAGE
			imageView.setImageBitmap(bitmap);
			imageLocationTextView.setText("File path :" + imagePath);

		}
	}

	private String getPath(Uri uri) {
		Log.e("uri received", uri.toString());
		String[] projection = { MediaStore.Images.Media.DATA };
		Cursor cursor = managedQuery(uri, projection, null, null, null);
		// CursorLoader cursor=new CursorLoader(this, uri, projection, null,
		// null, null);
		int column_index = cursor
				.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		cursor.moveToFirst();
		Log.e("image path", cursor.getString(column_index));
		return cursor.getString(column_index);
	}

	private long getFileSize(String imagePath) {

		long length = 0;

		try {

			File file = new File(imagePath);
			length = file.length();
			length = length / 1024;

		} catch (Exception e) {

			e.printStackTrace();
		}

		return length;
	}

	/**
	 * This class is responsible for uploading data
	 * 
	 * @author lauro
	 * 
	 */
	private class ImageUploader extends AsyncTask<String, Integer, Boolean>
			implements UploadProgressListener {

		@SuppressWarnings("deprecation")
		@Override
		protected Boolean doInBackground(String... params) {

			try {

				InputStream inputStream = new FileInputStream(new File(
						imagePath));

				// *** CONVERT INPUTSTREAM TO BYTE ARRAY

				byte[] data = this.convertToByteArray(inputStream);

				HttpClient httpClient = new DefaultHttpClient();
				httpClient.getParams().setParameter(
						CoreProtocolPNames.USER_AGENT,
						System.getProperty("http.agent"));

				HttpPost httpPost = new HttpPost(UPLOAD_SERVER_URI);

				// STRING DATA
				StringBody dataString = new StringBody(params[0]);
				StringBody project = new StringBody(
						Data.ForemanProject(UploadNow.this));

				// FILE DATA OR IMAGE DATA
				InputStreamBody inputStreamBody = new InputStreamBody(
						new ByteArrayInputStream(data), imageName);

				// MultipartEntity multipartEntity = new MultipartEntity();
				CustomMultiPartEntity multipartEntity = new CustomMultiPartEntity();

				// SET UPLOAD LISTENER
				multipartEntity.setUploadProgressListener(this);

				// *** ADD THE FILE
				multipartEntity.addPart("images", inputStreamBody);

				// *** ADD STRING DATA
				multipartEntity.addPart("description", dataString);
				multipartEntity.addPart("project", project);

				httpPost.setEntity(multipartEntity);

				// EXECUTE HTTPPOST
				HttpResponse httpResponse = httpClient.execute(httpPost);

				// THE RESPONSE FROM SERVER
				String stringResponse = EntityUtils.toString(httpResponse
						.getEntity());

				// DISPLAY RESPONSE OF THE SERVER
				Log.d("data from server", stringResponse);

			} catch (FileNotFoundException e1) {
				e1.printStackTrace();

				return false;

			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

				return false;

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

				return false;
			}

			return true;
		}

		/**
    * 
    */
		@Override
		public void transferred(long num) {

			// COMPUTE DATA UPLOADED BY PERCENT

			long dataUploaded = ((num / 1024) * 100) / imageSize;

			// PUBLISH PROGRESS

			this.publishProgress((int) dataUploaded);

		}

		/**
		 * Convert the InputStream to byte[]
		 * 
		 * @param inputStream
		 * @return
		 * @throws IOException
		 */
		private byte[] convertToByteArray(InputStream inputStream)
				throws IOException {

			ByteArrayOutputStream bos = new ByteArrayOutputStream();

			int next = inputStream.read();

			while (next > -1) {
				bos.write(next);
				next = inputStream.read();
			}

			bos.flush();

			return bos.toByteArray();
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			super.onProgressUpdate(values);

			// UPDATE THE PROGRESS DIALOG

			progressDialog.setProgress(values[0]);

		}

		@Override
		protected void onPostExecute(Boolean uploaded) {
			// TODO Auto-generated method stub
			super.onPostExecute(uploaded);

			if (uploaded) {

				// UPLOADING DATA SUCCESS

				progressDialog.dismiss();
				Toast.makeText(UploadNow.this, "File Uploaded",
						Toast.LENGTH_SHORT).show();

			} else {

				// UPLOADING DATA FAILED

				progressDialog.setMessage("Uploading Failed");
				progressDialog.setCancelable(true);

			}

		}

	}

	@SuppressLint("NewApi")
	public static String getRealPathFromURI_API19(Context context, Uri uri) {
		String filePath = "";
		String wholeID = DocumentsContract.getDocumentId(uri);

		// Split at colon, use second item in the array
		String id = wholeID.split(":")[1];

		String[] column = { MediaStore.Images.Media.DATA };

		// where id is equal to
		String sel = MediaStore.Images.Media._ID + "=?";

		Cursor cursor = context.getContentResolver().query(
				MediaStore.Images.Media.EXTERNAL_CONTENT_URI, column, sel,
				new String[] { id }, null);

		int columnIndex = cursor.getColumnIndex(column[0]);

		if (cursor.moveToFirst()) {
			filePath = cursor.getString(columnIndex);
		}
		cursor.close();
		return filePath;
	}

	@SuppressLint("NewApi")
	public static String getRealPathFromURI_API11to18(Context context,
			Uri contentUri) {
		String[] proj = { MediaStore.Images.Media.DATA };
		String result = null;

		CursorLoader cursorLoader = new CursorLoader(context, contentUri, proj,
				null, null, null);
		Cursor cursor = cursorLoader.loadInBackground();

		if (cursor != null) {
			int column_index = cursor
					.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			cursor.moveToFirst();
			result = cursor.getString(column_index);
		}
		return result;
	}

	public static String getRealPathFromURI_BelowAPI11(Context context,
			Uri contentUri) {
		String[] proj = { MediaStore.Images.Media.DATA };
		Cursor cursor = context.getContentResolver().query(contentUri, proj,
				null, null, null);
		int column_index = cursor
				.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		cursor.moveToFirst();
		return cursor.getString(column_index);
	}

}
