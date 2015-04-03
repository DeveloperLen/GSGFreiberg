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
import android.widget.*;

public class TeacherChooser extends Activity implements OnClickListener{

	public String filterTeacher = "";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Util.chooseRightThemeDialog(this);
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.teacher_chooser);
		Button teacherButton = (Button) findViewById(R.id.teacherChooserButton);
		teacherButton.setOnClickListener(this);
		
	}
	public void onClick(View view) {
		EditText teacherEditText = (EditText) findViewById(R.id.teacherChooserEditText);
		filterTeacher = teacherEditText.getText().toString();
		Intent data = new Intent();
		data.putExtra("teacher", filterTeacher);
		setResult(RESULT_OK, data);
		finish();
	}
}
