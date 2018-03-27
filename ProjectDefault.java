package com.example.navigationd;

import com.mainpackage.R;
import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

@SuppressLint("NewApi")
public class ProjectDefault extends Fragment {


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle args) {
		View view = inflater
				.inflate(R.layout.project_default, container, false);

		return view;
	}

}