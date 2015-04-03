package com.rojel.gsgfreiberg;
import android.content.*;
import android.os.*;
import android.preference.*;
import android.view.*;
import android.webkit.*;
import android.support.v4.view.*;

public class SettingsActivity extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener
{

	
	WebView wv;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		Util.chooseRightTheme(this);
		super.onCreate(savedInstanceState);
		//Util.chooseRightTheme(this);
		//this.setTheme(R.style.Theme_GSGFreiberg);
		addPreferencesFromResource(R.xml.preferences);
		PreferenceManager.getDefaultSharedPreferences(this).registerOnSharedPreferenceChangeListener(this);
		
		//Util.setTranscluentNav(this);
		//Util.setTranscluentStatus(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// TODO: Implement this method
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// TODO: Implement this method
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onSharedPreferenceChanged(SharedPreferences p1, String key)
	{
		if (key.equalsIgnoreCase("pref_key_theme")){
			Util.chooseRightTheme(this);
		}
	}
}
