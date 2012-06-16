package com.rojel.gsgfreiberg;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.ArrayList;

import org.jsoup.nodes.Document;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;

public class GSGFreibergActivity extends Activity implements OnClickListener {
    public static final int FILTER_REQUEST = 1;
	
	public Schedule schedule;
	public ArrayList<Lesson> displayed;
	public String filter;
	public Menu menu;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        updateSchedule();
        
        updateList(schedule.getByClass(""));
        filter = "";
    }
	
	public void updateList(ArrayList<Lesson> lessons) {
        setContentView(R.layout.schedule);
        TableLayout table = (TableLayout) findViewById(R.id.table);
        
		for(Lesson cancel : lessons) {
	        TableRow row = new TableRow(this);

	        TextView date = (TextView) this.getLayoutInflater().inflate(R.layout.scheduleitemtemplate, null);
	        date.setText(cancel.date);
	        
	        TextView classname = (TextView) this.getLayoutInflater().inflate(R.layout.scheduleitemtemplate, null);
	        classname.setText(cancel.classname);
	        
	        TextView lesson = (TextView) this.getLayoutInflater().inflate(R.layout.scheduleitemtemplate, null);
	        lesson.setText(cancel.lesson);
	        
	        Button details = (Button) this.getLayoutInflater().inflate(R.layout.detailsbuttontemplate, null);
	        details.setText(R.string.details);
	        details.setOnClickListener(this);
	        
	        row.addView(date);
	        row.addView(classname);
	        row.addView(lesson);
	        row.addView(details);
	        
	        table.addView(row);
        }
		
		displayed = lessons;
	}

	public void onClick(View v) {
		if(v instanceof Button) {
			int index;
			
			TableRow viewRow = (TableRow) v.getParent();
			TableLayout rowTable = (TableLayout) viewRow.getParent();
			
			index = rowTable.indexOfChild(viewRow);
			Lesson clickedLesson = displayed.get(index);
			Intent intent = new Intent(this, DetailsActivity.class);
			intent.putExtra("lesson", clickedLesson);
			startActivity(intent);
		} else if(v instanceof TextView) {
			
		}
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		this.menu = menu;
		
		try {
			this.menu.findItem(R.id.lastfiltered).setTitle(getString(R.string.lastfiltered) + " " + loadLastFilter());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
			case R.id.update:
				updateSchedule();
				updateList(schedule.getByClass(filter));
				System.out.println(filter);
				
				return true;
			case R.id.filterbyclass:
				System.out.println("Pressed filter button");
				startActivityForResult(new Intent(this, FilterActivity.class), FILTER_REQUEST);
				
				return true;
			case R.id.lastfiltered:
				try {
					updateList(schedule.getByClass(loadLastFilter()));
					filter = loadLastFilter();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
				
				return true;
			case R.id.disablefilter:
				updateList(schedule.getByClass(""));
				
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}
	
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode == FILTER_REQUEST) {
			if(resultCode == RESULT_OK) {
				System.out.println("Got the following text from filter activity: " + data.getStringExtra("classname"));
				updateList(schedule.getByClass(data.getStringExtra("classname")));
				filter = data.getStringExtra("classname");
				try {
					saveLastFilter(filter);
					this.menu.findItem(R.id.lastfiltered).setTitle(getString(R.string.lastfiltered) + " " + loadLastFilter());
				} catch (IOException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			} else if(resultCode == RESULT_CANCELED) {
				System.out.println("Aborted filter activity.");
			}
		}
	}
	
	public void saveLastFilter(String filter) throws IOException {
		FileOutputStream fos = openFileOutput(getString(R.string.optionsfile), Context.MODE_PRIVATE);
		ObjectOutputStream os = new ObjectOutputStream(fos);
		os.writeObject(filter);
		os.close();
	}
	
	public String loadLastFilter() throws IOException, ClassNotFoundException {
		FileInputStream fis = openFileInput(getString(R.string.optionsfile));
		ObjectInputStream is = new ObjectInputStream(fis);
		String filter = (String) is.readObject();
		is.close();
		
		if(filter == null)
			filter = "";
		
		return filter;
	}
	
	public void updateSchedule() {
		Document page = HTMLHandler.downloadPage(getString(R.string.page_url));
        
        if(page == null) {
        	System.out.println("Download failed.");
        	
        	Toast.makeText(this, R.string.connectionProblem, Toast.LENGTH_LONG).show();
        	
        	try {
				page = HTMLHandler.load(this);
			} catch (StreamCorruptedException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
        } else {
        	try {
				HTMLHandler.save(this, page);
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
        
        this.schedule = HTMLHandler.parse(page);
	}
}