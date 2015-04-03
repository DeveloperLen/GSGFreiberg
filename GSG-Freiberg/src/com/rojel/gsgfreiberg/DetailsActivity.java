package com.rojel.gsgfreiberg;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class DetailsActivity extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		Util.chooseRightThemeDialog(this);
		
		super.onCreate(savedInstanceState);
		
		setResult(RESULT_OK);
		
		setContentView(R.layout.details);
		
		Lesson lesson = (Lesson) getIntent().getSerializableExtra("lesson");
		
		((TextView) findViewById(R.id.date)).setText(lesson.date);
		((TextView) findViewById(R.id.day)).setText(lesson.day);
		((TextView) findViewById(R.id.classname)).setText(lesson.classname);
		((TextView) findViewById(R.id.teacher)).setText(lesson.teacher);
		((TextView) findViewById(R.id.subject)).setText(lesson.subject);
		((TextView) findViewById(R.id.lesson)).setText(lesson.lesson);
		((TextView) findViewById(R.id.newSubject)).setText(lesson.newSubject);
		((TextView) findViewById(R.id.newTeacher)).setText(lesson.newTeacher);
		((TextView) findViewById(R.id.room)).setText(lesson.room);
		((TextView) findViewById(R.id.instead)).setText(lesson.instead);
	}
}
