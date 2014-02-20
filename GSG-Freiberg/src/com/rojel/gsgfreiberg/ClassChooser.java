package com.rojel.gsgfreiberg;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class ClassChooser extends Activity implements OnClickListener, OnItemSelectedListener {
	
	@Override
	public void onNothingSelected(AdapterView<?> p1) {
		filter = "5";
	}
	
	public String filter = "";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.class_chooser);
		
		Button chooseButton = (Button) findViewById(R.id.chooseButton);
		chooseButton.setOnClickListener(this);
		
		Spinner classChooserSpinner = (Spinner) findViewById(R.id.classChooser);
		classChooserSpinner.setOnItemSelectedListener(this);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.classChosserSpinner, android.R.layout.simple_spinner_dropdown_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		classChooserSpinner.setAdapter(adapter);
		classChooserSpinner.setSelection(GSGFreibergActivity.filter.equalsIgnoreCase("")? 0 : (Integer.parseInt(GSGFreibergActivity.filter)-5));
		
	}
	
	public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {		
		filter = (pos + 5) + "";
	}
	
	public void onClick(View view) {
		Intent data = new Intent();
		data.putExtra("class", filter);
		setResult(RESULT_OK, data);
		finish();
	}
}
