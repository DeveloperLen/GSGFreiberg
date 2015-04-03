package com.rojel.gsgfreiberg;

import android.app.*;
import android.content.*;
import android.os.*;
import android.preference.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import java.util.*;
import org.jsoup.*;
import org.jsoup.nodes.*;
import android.content.pm.*;
import android.support.v7.widget.*;
import android.graphics.*;

public class GSGFreibergActivity extends Activity implements OnClickListener
{
	
	public static final int FILTER_REQUEST = 1;
	public static final int DETAIL_REQUEST = 2048;
	public static final int CLASSCHOOSER_REQUEST = 454864948;
	public static final int TEACHERCHOOSER_REQUEST = 7238598;
	public static String KEY_PREF_AUTO_UPDATE = "pref_key_auto_update";
	public static String KEY_PREF_AUTO_SORT_CLASS = "pref_key_auto_sort_class";
	public static String KEY_PREF_CLASS = "pref_key_class";
	public static String KEY_PREF_THEME = "pref_key_theme";
	public static String theme_type;
	public static View tableView;
	public static Notification pNot;
	public static NotificationManager pNotManager;
	public static Notification.Builder pNotBuilder;
	
	public static Schedule schedule;
	public static ArrayList<Lesson> displayed;
	public static boolean isTypeTeacher;
	public static boolean isFirstStarted;
	public static String filter = "";
	public static String filterTeacher;
	public static Document docPage;
	public static Activity ct;
	public static boolean changedTeacher = false;
	public static String sortTeacher = "";
	public static View mDecorView;
	
	public Menu menu;
	
	protected void onCreate(Bundle savedInstanceState){
		//load plan
		ct = this;
		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
		//sharedPref.registerOnSharedPreferenceChangeListener(this);
		boolean autoUpdate = sharedPref.getBoolean(KEY_PREF_AUTO_UPDATE,true);
		boolean autoSortClass = sharedPref.getBoolean(KEY_PREF_AUTO_SORT_CLASS, false);
		String sortClass = sharedPref.getString(KEY_PREF_CLASS, "5");
		String themeType = sharedPref.getString(KEY_PREF_THEME, "holo_dark");
		this.setTitle(R.string.gsgActivityTitle);
		theme_type = themeType;
		
		Util.chooseRightTheme(this);
		this.setTitleColor(Color.WHITE);
		
		
		this.requestWindowFeature(Window.FEATURE_PROGRESS);
		super.onCreate(savedInstanceState);
		//Util.setTranscluent(this);
		mDecorView = getWindow().getDecorView();
		
		if(autoUpdate == true){
			System.out.println("download");
			try{
		 		GSGSave.load(this);
				updateSchedule();
				System.out.println("downloaded");
			}catch (Exception e){
				Toast.makeText(this,R.string.nointernet, Toast.LENGTH_LONG).show();
			}
		}else{
			try{
				System.out.println("no download");
				GSGSave.load(this);
				docPage = GSGSave.page;
				this.schedule = HTMLHandler.parse(docPage);
				//updateList(schedule.getByClass(""));
			}catch (Exception e){
				//Toast.makeText(this,"doc is null", Toast.LENGTH_LONG).show();
			}
		}
		if(autoSortClass){
			updateList(schedule.getByClass(sortClass));
		}else{
			updateList(schedule.getByClass(""));
		}
		//showFilterNoti();

		System.out.println("Auto sorting class? - " + autoSortClass);
		
		ListView listV = (ListView) findViewById(R.id.scheduleentriesListView1);
	}

	/**
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
		if (hasFocus) {
			mDecorView.setSystemUiVisibility(
                //View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);}
	}*/
	
	@Override
	protected void onStart()
	{
		super.onStart();
		//showFilterNoti();
		if(TempStore.changedTeacher == true){
			updateList(schedule.getByTeacher(TempStore.sortTeacher));
			TempStore.changedTeacher = false;
		}
	}
	
	public void showFilterNoti(){

		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
		
		boolean autoSortClass = sharedPref.getBoolean(KEY_PREF_AUTO_SORT_CLASS, false);
		String sortClass = sharedPref.getString(KEY_PREF_CLASS, "9");
		
		// create Notification
		Intent resultIntent = new Intent(this, GSGFreibergActivity.class);
		TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        // Adds the back stack for the Intent (but not the Intent itself)
		stackBuilder.addParentStack(GSGFreibergActivity.class);
        // Adds the Intent that starts the Activity to the top of the stack
		stackBuilder.addNextIntent(resultIntent);
		PendingIntent resultPendingIntent =
			stackBuilder.getPendingIntent(
            0,
			PendingIntent.FLAG_UPDATE_CURRENT
        );
		Notification.Builder notBuilder = new Notification.Builder(this);
		notBuilder.setSmallIcon(R.drawable.ic_action_sort_by_size);
		notBuilder.setContentTitle("GSGFreiberg");
		notBuilder.setContentText("Status: Ungefiltert");
		notBuilder.setContentInfo(displayed.size() + " Einträge");
		//notBuilder.setContentIntent(resultPendingIntent);
		notBuilder.setOngoing(true);
		Notification not = notBuilder.build();
		NotificationManager notManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		notManager.notify(1,not);
		pNot = not;
		pNotManager = notManager;
		pNotBuilder = notBuilder;
		filter = "";
		filterTeacher = "";

		if(autoSortClass == true){
			updateList(schedule.getByClass(sortClass+""));
			//notBuilder.setContentText("Status: Gefiltert");
			//not = notBuilder.build();
			//notManager.notify(1,not);
		}
	}
	
	protected void onStop() {
		super.onStop();
		GSGSave.save(this);
		//pNotManager.cancel(1);
	}
	
	protected void onDestroy() {
		super.onDestroy();
		//pNotManager.cancel(1);
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
				
				row.addView(date);
				row.addView(classname);
				row.addView(lesson);
				
				table.addView(row);
			}
		}
		displayed = lessons;
		//if (pNot != null){
			//pNotBuilder.setContentInfo(displayed.size() + " Einträge");
			//if (filter == ""){
				//pNotBuilder.setContentText("Status: Ungefiltert");
			//}else{
			//	pNotBuilder.setContentText("Status: Gefiltert");
			//}
			//pNotManager.cancel(1);
		    //pNotManager.notify(1,pNotBuilder.build());
		//}
	}
	
	public void onClick(View v) {
		if (v instanceof TextView) {
			int index;
			
			TableRow viewRow = (TableRow) v.getParent();
			TableLayout rowTable = (TableLayout) viewRow.getParent();
			
			tableView = viewRow;
			if(theme_type.equalsIgnoreCase("holo_dark")){
				viewRow.setBackgroundColor(0x33FFFFFF);
			}else{
				viewRow.setBackgroundColor(0x33000000);
			}
			
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
				this.menu.findItem(R.id.lastfiltered).setTitle(getString(R.string.lastfiltered) + " " + GSGSave.lastFilter);
				this.menu.findItem(R.id.lastfiltered).setVisible(true);
				updateList(schedule.getByClass(filter));
			}
		}
		if (requestCode == TEACHERCHOOSER_REQUEST){
			if(resultCode == RESULT_OK){
				filterTeacher = data.getStringExtra("teacher");
				GSGSave.lastFilterTeacher = filterTeacher;
				this.menu.findItem(R.id.lastfilteredteacher).setTitle(getString(R.string.lastfilteredteacher) + " " + GSGSave.lastFilterTeacher);
				this.menu.findItem(R.id.lastfilteredteacher).setVisible(true);
				updateList(schedule.getByTeacher(filterTeacher));
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
		}else{
			this.menu.findItem(R.id.lastfiltered).setVisible(false);
		}
		if(!(GSGSave.lastFilterTeacher.equalsIgnoreCase(""))){
			this.menu.findItem(R.id.lastfilteredteacher).setTitle(getString(R.string.lastfilteredteacher) + " " + GSGSave.lastFilterTeacher);
		}else{
			this.menu.findItem(R.id.lastfilteredteacher).setVisible(false);
		}
		return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.update:
				updateSchedule();
				System.out.println(filter);
				return true;
			case R.id.lastfiltered:
				updateList(schedule.getByClass(GSGSave.lastFilter));
				//pNotBuilder.setContentText("Status: Gefiltert");
				//pNotManager.notify(1, pNotBuilder.build());
				return true;
			case R.id.lastfilteredteacher:
				updateList(schedule.getByTeacher(GSGSave.lastFilterTeacher));
				//pNotBuilder.setContentText("Status: Gefiltert");
				//pNotManager.notify(1, pNotBuilder.build());
				return true;
			case R.id.filterbyclass:
				Intent intent = new Intent(this, ClassChooser.class);
				startActivityForResult(intent, CLASSCHOOSER_REQUEST);
				return true;
			case R.id.filterbyteacher:
				Intent intentTeacher = new Intent(this, TeacherChooser.class);
				startActivityForResult(intentTeacher, TEACHERCHOOSER_REQUEST);
				return true;
			case R.id.disablefilter:
				updateList(schedule.getByClass(""));
				filter = "";
				return true;
			case R.id.informationschool:
				Intent info = new Intent(this, InformationActivity.class);
				startActivity(info);				
				return true;
			case R.id.informationteacher:
				Intent teacher = new Intent(this, TeacherListActivity.class);
				startActivity(teacher);				
				return true;
			case R.id.settings:
				Intent settings = new Intent(this, SettingsActivity.class);
				startActivity(settings);
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}
	
	public void updateSchedule() {
		final ProgressDialog prog = ProgressDialog.show(this, "Vertretungsplan", "Lädt...", true);
        prog.setCancelable(false);
		Document page = HTMLHandler.downloadPage(getString(R.string.pageurl));
		if (page == null) {
			System.out.println("Download failed.");
			
			Toast.makeText(this, R.string.connectionproblem, Toast.LENGTH_LONG).show();
			
			if(GSGSave.page != null){
				page = GSGSave.page;
				this.schedule = HTMLHandler.parse(page);
			}else{
				this.schedule = new Schedule();
				//this.displayed = new ArrayList<Lesson>();
			}
		} else {
			GSGSave.page = page;
		
			this.schedule = HTMLHandler.parse(page);
			Toast.makeText(this, "Erfolgreich aktualisiert!", Toast.LENGTH_LONG).show();
		}
		prog.dismiss();
	}
	
	
}
