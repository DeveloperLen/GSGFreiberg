package com.rojel.gsgfreiberg;

import org.jsoup.nodes.Document;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;

public class GSGFreibergActivity extends Activity implements OnClickListener {
    public Schedule schedule;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.schedule);
        TableLayout table = (TableLayout) findViewById(R.id.table);
        
        Document page = HTMLHandler.downloadPage(getString(R.string.page_url));
        this.schedule = HTMLHandler.parse(page);
        
        for(int i = 0; i < schedule.size(); i++) {
	        TextView date = new TextView(this);
	        date.setPadding(0, 0, 5, 10);
	        date.setText(schedule.get(i).date);
	        
	        TextView classname = new TextView(this);
	        classname.setPadding(0, 0, 5, 10);
	        classname.setText(schedule.get(i).classname);
	        
	        TextView lesson = new TextView(this);
	        lesson.setPadding(0, 0, 5, 10);
	        lesson.setText(schedule.get(i).lesson);
	        
	        TextView newSubject = new TextView(this);
	        newSubject.setPadding(0, 0, 5, 10);
	        newSubject.setText(schedule.get(i).newSubject);
	        
	        TextView newTeacher = new TextView(this);
	        newTeacher.setPadding(0, 0, 5, 10);
	        newTeacher.setText(schedule.get(i).newTeacher);
	        
	        TextView room = new TextView(this);
	        room.setPadding(0, 0, 5, 10);
	        room.setText(schedule.get(i).room);
	        
	        Button more = new Button(this);
	        more.setWidth(80);
	        more.setPadding(0, 0, 0, 10);
	        more.setText("Details");
	        more.setOnClickListener(this);
	        
	        TableRow row = new TableRow(this);
	        
	        row.addView(date);
	        row.addView(classname);
	        row.addView(lesson);
	        row.addView(newSubject);
	        row.addView(newTeacher);
	        row.addView(room);
	        row.addView(more);
	        
	        table.addView(row);
        }
    }

	public void onClick(View v) {
		if(v instanceof Button) {
			int index;
			
			TableRow viewRow = (TableRow) v.getParent();
			TableLayout rowTable = (TableLayout) viewRow.getParent();
			
			index = rowTable.indexOfChild(viewRow);
			Lesson clickedLesson = schedule.get(index);
			Intent intent = new Intent(this, DetailsActivity.class);
			intent.putExtra("lesson", clickedLesson);
			startActivity(intent);
		} else if(v instanceof TextView) {
			
		}
	}
}