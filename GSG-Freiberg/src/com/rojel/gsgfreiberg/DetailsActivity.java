package com.rojel.gsgfreiberg;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class DetailsActivity extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.details);
		
		Lesson lesson = (Lesson) getIntent().getSerializableExtra("lesson");
		
		((TextView) findViewById(R.id.date)).append(lesson.date);
		((TextView) findViewById(R.id.day)).append(lesson.day);
		((TextView) findViewById(R.id.classname)).append(lesson.classname);
		((TextView) findViewById(R.id.teacher)).append(lesson.teacher);
		((TextView) findViewById(R.id.subject)).append(lesson.subject);
		((TextView) findViewById(R.id.lesson)).append(lesson.lesson);
		((TextView) findViewById(R.id.newSubject)).append(lesson.newSubject);
		((TextView) findViewById(R.id.newTeacher)).append(lesson.newTeacher);
		((TextView) findViewById(R.id.room)).append(lesson.room);
		((TextView) findViewById(R.id.instead)).append(lesson.instead);
	}
}
