package com.rojel.gsgfreiberg;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class ClassChooser extends Activity{
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.class_chooser);
		
		Button chooseButton = (Button)findViewById(R.id.chooseButton);
		chooseButton.setOnClickListener((OnClickListener) this);
		
		Spinner classChooserSpinner = (Spinner) findViewById(R.id.classChooser);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.classChosserSpinner, android.R.layout.simple_spinner_dropdown_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		classChooserSpinner.setAdapter(adapter);
		classChooserSpinner.setSelection(0);
		
	}
	
	public void onItemSelected(AdapterView<?> parent, View view, int pos, long id){
		String chosen = parent.getItemAtPosition(pos).toString();
		if(chosen.equalsIgnoreCase("@string/class5")){
			GSGFreibergActivity.filter = "5";
		}
		if(chosen.equalsIgnoreCase("@string/class6")){
			GSGFreibergActivity.filter = "6";
		}
		if(chosen.equalsIgnoreCase("@string/class7")){
			GSGFreibergActivity.filter = "7";
		}
		if(chosen.equalsIgnoreCase("@string/class8")){
			GSGFreibergActivity.filter = "8";
		}
		if(chosen.equalsIgnoreCase("@string/class9")){
			GSGFreibergActivity.filter = "9";
		}
		if(chosen.equalsIgnoreCase("@string/class10")){
			GSGFreibergActivity.filter = "10";
		}
		if(chosen.equalsIgnoreCase("@string/class11")){
			GSGFreibergActivity.filter = "11";
		}
		if(chosen.equalsIgnoreCase("@string/class12")){
			GSGFreibergActivity.filter = "12";
		}
	}
	
	public void onClick(View view){
		setResult(RESULT_OK);
		finish();
		
	}
}
