package com.rojel.gsgfreiberg;
import android.content.*;
import android.graphics.drawable.*;
import android.widget.*;
import android.os.*;

public class Util
{
	public static void chooseRightTheme(Context c){
		//this.setTheme(R.style.Theme_GSGFreiberg_Light);
		if(GSGFreibergActivity.theme_type.equalsIgnoreCase("holo_dark")){
			c.setTheme(R.style.Theme_GSGFreiberg);
		}else{
			c.setTheme(R.style.Theme_GSGFreiberg_Light);
		}
	}
	public static void chooseRightThemeDialog(Context c){
		//this.setTheme(R.style.Theme_GSGFreiberg_Light);
		if(GSGFreibergActivity.theme_type.equalsIgnoreCase("holo_dark")){
			c.setTheme(R.style.Theme_GSGFreiberg_Dialog);
		}else{
			c.setTheme(R.style.Theme_GSGFreiberg_Dialog_Light);
		}
	}
	public static void chooseRightThemeDialogInformation(InformationActivity c){
		//this.setTheme(R.style.Theme_GSGFreiberg_Light);	
		ImageButton callDuerer = (ImageButton) c.findViewById(R.id.informationCallDuerer);
		ImageButton callAlbertinum = (ImageButton) c.findViewById(R.id.informationCallAlbertinum);
		ImageButton mailDuerer = (ImageButton) c.findViewById(R.id.informationMailDuerer);
		ImageButton mailAlbertinum = (ImageButton) c.findViewById(R.id.informationMailAlbertinum);
		ImageButton webDuerer = (ImageButton) c.findViewById(R.id.informationWebDuerer);	
		ImageButton webAlbertinum = (ImageButton) c.findViewById(R.id.informationWebAlbertinum);
		if(GSGFreibergActivity.theme_type.equalsIgnoreCase("holo_dark")){
			c.setTheme(R.style.Theme_GSGFreiberg_Dialog);
		}else{
			c.setTheme(R.style.Theme_GSGFreiberg_Dialog_Light);
			callDuerer.setImageResource(R.drawable.ic_action_call_dark);
		}
	}
	
	public static boolean isEqualsBelowKitkat(){
		return Build.VERSION.SDK_INT <= 19;
	}
	
	public static boolean isEqualsHigherLollipop(){
		return Build.VERSION.SDK_INT >= 21;
	}
	
}
