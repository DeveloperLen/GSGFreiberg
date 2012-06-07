package com.rojel.gsgfreiberg;

import org.jsoup.nodes.Document;
import android.app.Activity;
import android.os.Bundle;
import android.widget.*;

public class GSGFreibergActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.schedule);
        TableLayout table = (TableLayout) findViewById(R.id.table);
        
        Document page = HTMLHandler.downloadPage(getString(R.string.page_url));
        Schedule schedule = HTMLHandler.parse(page);
        
        for(int i = 0; i < schedule.size(); i++) {
	        TextView cancel = new TextView(this);
	        cancel.setText(schedule.get(i).date + "-" + schedule.get(i).classname + "-" + schedule.get(i).lesson);
	        
	        TableRow row = new TableRow(this);
	        row.addView(cancel);
	        
	        table.addView(row);
        }
    }
}