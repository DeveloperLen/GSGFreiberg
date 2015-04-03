package com.rojel.gsgfreiberg;

import android.app.*;
import android.os.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;

public class TeacherScreenActivity extends Activity implements OnClickListener
{

	public static String teachername ="";
	
	@Override
	public void onClick(View v)
	{
		//System.out.println("clicks reach");
		TempStore.changedTeacher = true;
		TempStore.sortTeacher = extractTeacherShort(teachername);
		System.out.println(TempStore.sortTeacher);
		finish();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		Util.chooseRightTheme(this);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.teacher_screen);
		teachername = getIntent().getStringExtra("teacherName");
		setTitle(teachername);
		Button sortButton = (Button) findViewById(R.id.teacherSort);
		sortButton.setOnClickListener(this);
	}
	
	public String extractTeacherShort(String teachername){
		int ind = teachername.indexOf("(") + 1;
		String tname = teachername.substring(ind, ind + 1);
		System.out.println(tname);
		return tname;
	}
}
