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
		classChooserSpinner.setSelection(0);
		
	}
	
	public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
		String chosen = parent.getItemAtPosition(pos).toString();
		if (chosen.equalsIgnoreCase("@string/class5")) {
			filter = "5";
		}
		if (chosen.equalsIgnoreCase("@string/class6")) {
			filter = "6";
			System.out.println("6");
		}
		if (chosen.equalsIgnoreCase("@string/class7")) {
			filter = "7";
		}
		if (chosen.equalsIgnoreCase("@string/class8")) {
			filter = "8";
		}
		if (chosen.equalsIgnoreCase("@string/class9")) {
			filter = "9";
		}
		if (chosen.equalsIgnoreCase("@string/class10")) {
			filter = "10";
		}
		if (chosen.equalsIgnoreCase("@string/class11")) {
			filter = "11";
		}
		if (chosen.equalsIgnoreCase("@string/class12")) {
			filter = "12";
		}
	}
	
	public void onClick(View view) {
		Intent data = new Intent();
		data.putExtra("class", filter);
		setResult(RESULT_OK);
		finish();
	}
}
