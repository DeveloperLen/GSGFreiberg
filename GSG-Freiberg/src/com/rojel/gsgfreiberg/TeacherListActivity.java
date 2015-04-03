package com.rojel.gsgfreiberg;

import android.app.*;
import android.os.*;
import android.widget.*;
import android.widget.AdapterView.*;
import android.view.*;
import java.util.*;
import android.content.*;

public class TeacherListActivity extends Activity implements OnItemClickListener
{

	@Override
	public void onItemClick(AdapterView<?> p1, View p2, int index, long p4)
	{
		System.out.println("clicker reached");
		String[] teacherList = getResources().getStringArray(R.array.teacherChooserSpinner);
		ArrayList<String> teacherListA = new ArrayList<String>(Arrays.asList(teacherList));
		String teacherName = teacherListA.get(index);
		Intent screenIntent = new Intent(this, TeacherScreenActivity.class);
		screenIntent.putExtra("teacherName", teacherName);
		startActivity(screenIntent);
		finish();
	}


	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		Util.chooseRightTheme(this);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.teacherlist);
		ListView teacherLv = (ListView) findViewById(R.id.teacherlistListView);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.teacherChooserSpinner, android.R.layout.simple_list_item_1);
		teacherLv.setAdapter(adapter);
		teacherLv.setOnItemClickListener(this);
	}
	
}
