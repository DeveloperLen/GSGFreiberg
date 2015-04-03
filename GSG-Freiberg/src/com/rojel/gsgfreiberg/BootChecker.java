package com.rojel.gsgfreiberg;

import android.content.*;

public class BootChecker extends BroadcastReceiver
{

	@Override
	public void onReceive(Context c, Intent intent){
		if(intent.getAction().equals("android.intent.action.BOOT_COMPLETED")){
			
		}
	}
}
