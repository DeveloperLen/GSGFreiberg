package com.rojel.gsgfreiberg;

import org.jsoup.nodes.Document;
import android.app.Activity;
import android.os.Bundle;
import android.widget.*;

public class GSGFreibergActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.schedule);
        TableLayout table = (TableLayout) findViewById(R.id.table);
        
        Document page = HTMLHandler.downloadPage(getString(R.string.page_url));
        Schedule schedule = HTMLHandler.parse(page);
        
        for(int i = 0; i < schedule.size(); i++) {
	        TextView date = new TextView(this);
	        date.setPadding(0, 0, 10, 10);
	        date.setText(schedule.get(i).date);
	        
	        TextView classname = new TextView(this);
	        classname.setPadding(0, 0, 10, 10);
	        classname.setText(schedule.get(i).classname);
	        
	        TextView lesson = new TextView(this);
	        lesson.setPadding(0, 0, 10, 10);
	        lesson.setText(schedule.get(i).lesson);
	        
	        TextView newSubject = new TextView(this);
	        newSubject.setPadding(0, 0, 10, 10);
	        newSubject.setText(schedule.get(i).newSubject);
	        
	        TextView newTeacher = new TextView(this);
	        newTeacher.setPadding(0, 0, 10, 10);
	        newTeacher.setText(schedule.get(i).newTeacher);
	        
	        TextView room = new TextView(this);
	        room.setPadding(0, 0, 10, 10);
	        room.setText(schedule.get(i).room);
	        
	        TableRow row = new TableRow(this);
	        
	        row.addView(date);
	        row.addView(classname);
	        row.addView(lesson);
	        row.addView(newSubject);
	        row.addView(newTeacher);
	        row.addView(room);
	        
	        table.addView(row);
        }
    }
}