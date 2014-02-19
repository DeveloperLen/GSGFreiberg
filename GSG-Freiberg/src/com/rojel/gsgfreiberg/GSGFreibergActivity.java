package com.rojel.gsgfreiberg;

import android.app.*;
import android.content.*;
import android.os.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import java.util.*;
import org.jsoup.nodes.*;

public class GSGFreibergActivity extends Activity implements OnClickListener {
	public static final int FILTER_REQUEST = 1;
	public static final int DETAIL_REQUEST = 2048;
	public static final int CLASSCHOOSER_REQUEST = 454864948;
	public static View tableView;
	
	public Schedule schedule;
	public ArrayList<Lesson> displayed;
	public static String filter;
	
	public Menu menu;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		GSGSave.load(this);
		
		updateSchedule();
		
		updateList(schedule.getByClass(""));
		filter = "";
	}
	
	protected void onStop() {
		super.onStop();
		
		GSGSave.save(this);
	}
	
	protected void onDestroy() {
		super.onDestroy();
		
		GSGSave.save(this);
	}
	
	public void updateList(ArrayList<Lesson> lessons) {
		setContentView(R.layout.schedule);
		TableLayout table = (TableLayout) findViewById(R.id.table);
		
		if (lessons.size() == 0) {
			Toast.makeText(this, R.string.nocancels, Toast.LENGTH_LONG).show();
		} else {
			
			for (Lesson cancel : lessons) {
				TableRow row = new TableRow(this);
				row.setOnClickListener(this);
				
				TextView date = (TextView) this.getLayoutInflater().inflate(R.layout.scheduleitemtemplate, null);
				date.setText(cancel.date);
				date.setOnClickListener(this);
				
				TextView classname = (TextView) this.getLayoutInflater().inflate(R.layout.scheduleitemtemplate, null);
				classname.setText(cancel.classname);
				classname.setOnClickListener(this);
				
				TextView lesson = (TextView) this.getLayoutInflater().inflate(R.layout.scheduleitemtemplate, null);
				lesson.setText(cancel.lesson);
				lesson.setOnClickListener(this);
				
				//Button details = (Button) this.getLayoutInflater().inflate(R.layout.detailsbuttontemplate, null);
				//details.setText(R.string.details);
				//details.setOnClickListener(this);
				//details.setVisibility(1);
				
				row.addView(date);
				row.addView(classname);
				row.addView(lesson);
				//row.addView(details);
				
				table.addView(row);
			}
		}
		displayed = lessons;
	}
	
	public void onClick(View v) {
		/*if(v instanceof Button) {
			int index;
			
			TableRow viewRow = (TableRow) v.getParent();
			TableLayout rowTable = (TableLayout) viewRow.getParent();
			
			index = rowTable.indexOfChild(viewRow);
			Lesson clickedLesson = displayed.get(index);
			Intent intent = new Intent(this, DetailsActivity.class);
			intent.putExtra("lesson", clickedLesson);
			startActivity(intent);
		} else */if (v instanceof TextView) {
			int index;
			
			TableRow viewRow = (TableRow) v.getParent();
			TableLayout rowTable = (TableLayout) viewRow.getParent();
			
			tableView = viewRow;
			viewRow.setBackgroundColor(0x33FFFFFF);
			
			index = rowTable.indexOfChild(viewRow);
			Lesson clickedLesson = displayed.get(index);
			
			Intent intent = new Intent(this, DetailsActivity.class);
			intent.putExtra("lesson", clickedLesson);
			startActivityForResult(intent, DETAIL_REQUEST);
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == DETAIL_REQUEST) {
			if (resultCode == RESULT_OK) {
				tableView.setBackgroundColor(0x00000000);
			}
		}
		if (requestCode == CLASSCHOOSER_REQUEST) {
			if (resultCode == RESULT_OK) {
				filter = data.getStringExtra("class");
				GSGSave.lastFilter = filter;
				updateList(schedule.getByClass(filter));
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		this.menu = menu;
		if (!GSGSave.lastFilter.equalsIgnoreCase("")) {
			this.menu.findItem(R.id.lastfiltered).setTitle(getString(R.string.lastfiltered) + " " + GSGSave.lastFilter);
		} else {
			this.menu.findItem(R.id.lastfiltered).setVisible(false);
		}
		
		return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.update:
				updateSchedule();
				updateList(schedule.getByClass(filter));
				System.out.println(filter);
				return true;
			case R.id.filterbyclass:
				Intent intent = new Intent(this, ClassChooser.class);
				startActivityForResult(intent, CLASSCHOOSER_REQUEST);
				return true;
				/*case R.id.class5:
					filter = "5";
					GSGSave.lastFilter = filter;
					updateList(schedule.getByClass(filter));
					this.menu.findItem(R.id.lastfiltered).setTitle(getString(R.string.lastfiltered) + " " + GSGSave.lastFilter);
					this.menu.findItem(R.id.lastfiltered).setVisible(true);
					return true;
				case R.id.class6:
					filter = "6";
					updateList(schedule.getByClass(filter));
					GSGSave.lastFilter = filter;
					this.menu.findItem(R.id.lastfiltered).setTitle(getString(R.string.lastfiltered) + " " + GSGSave.lastFilter);
					this.menu.findItem(R.id.lastfiltered).setVisible(true);
					return true;
				case R.id.class7:
					filter = "7";
					updateList(schedule.getByClass(filter));
					GSGSave.lastFilter = filter;
					this.menu.findItem(R.id.lastfiltered).setTitle(getString(R.string.lastfiltered) + " " + GSGSave.lastFilter);
					this.menu.findItem(R.id.lastfiltered).setVisible(true);
					return true;
				case R.id.class8:
					filter = "8";
					updateList(schedule.getByClass(filter));
					GSGSave.lastFilter = filter;
					this.menu.findItem(R.id.lastfiltered).setTitle(getString(R.string.lastfiltered) + " " + GSGSave.lastFilter);
					this.menu.findItem(R.id.lastfiltered).setVisible(true);
					return true;
				case R.id.class9:
					filter = "9";
					updateList(schedule.getByClass(filter));
					GSGSave.lastFilter = filter;
					this.menu.findItem(R.id.lastfiltered).setTitle(getString(R.string.lastfiltered) + " " + GSGSave.lastFilter);
					this.menu.findItem(R.id.lastfiltered).setVisible(true);
					return true;
				case R.id.class10:
					filter = "10";
					updateList(schedule.getByClass(filter));
					GSGSave.lastFilter = filter;
					this.menu.findItem(R.id.lastfiltered).setTitle(getString(R.string.lastfiltered) + " " + GSGSave.lastFilter);
					this.menu.findItem(R.id.lastfiltered).setVisible(true);
					return true;
				case R.id.class11:
					filter = "11";
					updateList(schedule.getByClass(filter));
					GSGSave.lastFilter = filter;
					this.menu.findItem(R.id.lastfiltered).setTitle(getString(R.string.lastfiltered) + " " + GSGSave.lastFilter);
					this.menu.findItem(R.id.lastfiltered).setVisible(true);
					return true;
				case R.id.class12:
					filter = "12";
					updateList(schedule.getByClass(filter));
					GSGSave.lastFilter = filter;
					this.menu.findItem(R.id.lastfiltered).setTitle(getString(R.string.lastfiltered) + " " + GSGSave.lastFilter);
					this.menu.findItem(R.id.lastfiltered).setVisible(true);
					return true;
				case R.id.lastfiltered:
					updateList(schedule.getByClass(GSGSave.lastFilter));
					filter = GSGSave.lastFilter;
					
					return true;*/
			case R.id.disablefilter:
				updateList(schedule.getByClass(""));
				filter = "";
				GSGSave.lastFilter = filter;
				this.menu.findItem(R.id.lastfiltered).setVisible(true);
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}
	
	/*public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode == FILTER_REQUEST) {
			if(resultCode == RESULT_OK) {
				System.out.println("Got the following text from filter activity: " + data.getStringExtra("classname"));
				updateList(schedule.getByClass(data.getStringExtra("classname")));
				filter = data.getStringExtra("classname");
				GSGSave.lastFilter = filter;
				this.menu.findItem(R.id.lastfiltered).setTitle(getString(R.string.lastfiltered) + " " + GSGSave.lastFilter);
			} else if(resultCode == RESULT_CANCELED) {
				System.out.println("Aborted filter activity.");
			}
		}
	}*/
	
	public void updateSchedule() {
		Document page = HTMLHandler.downloadPage(getString(R.string.pageurl));
		
		if (page == null) {
			System.out.println("Download failed.");
			
			Toast.makeText(this, R.string.connectionproblem, Toast.LENGTH_LONG).show();
			
			page = GSGSave.page;
		} else {
			GSGSave.page = page;
		}
		
		this.schedule = HTMLHandler.parse(page);
	}
}
