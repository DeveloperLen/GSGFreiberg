package com.rojel.gsgfreiberg;

import android.app.*;
import android.content.*;
import android.net.*;
import android.os.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import java.net.*;

public class InformationActivity extends Activity implements OnClickListener
{
	final Context context = this;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		setContentView(R.layout.information);
		
		ImageButton callDuerer = (ImageButton) findViewById(R.id.informationCallDuerer);
		ImageButton callAlbertinum = (ImageButton) findViewById(R.id.informationCallAlbertinum);
		ImageButton mailDuerer = (ImageButton) findViewById(R.id.informationMailDuerer);
		ImageButton mailAlbertinum = (ImageButton) findViewById(R.id.informationMailAlbertinum);
		ImageButton webDuerer = (ImageButton) findViewById(R.id.informationWebDuerer);	
		ImageButton webAlbertinum = (ImageButton) findViewById(R.id.informationWebAlbertinum);
		
		callDuerer.setOnClickListener(this);
		callAlbertinum.setOnClickListener(this);
		mailDuerer.setOnClickListener(this);
		mailAlbertinum.setOnClickListener(this);
		webDuerer.setOnClickListener(this);
		webAlbertinum.setOnClickListener(this);
		
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public void onClick(View view){
		switch(view.getId()){
			case R.id.informationCallDuerer:
				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setTitle("Anrufbestätigung");
				builder.setMessage("Möchtest du wirklich das Sekretäriat des Haus Dürer anrufen? (Bitte beachte, dass dadurch Gebühren anfallen können!)");
				builder.setPositiveButton("Ja",new DialogInterface.OnClickListener(){
					public void onClick(DialogInterface dialog,int id) {
						try {
							Intent callIntent = new Intent(Intent.ACTION_CALL);
							callIntent.setData(Uri.parse("tel:+49373123081"));
							startActivity(callIntent);
						} catch (ActivityNotFoundException activityException) {
							activityException.printStackTrace();
						}
					}
				});
				builder.setNegativeButton("Nein", new DialogInterface.OnClickListener(){
					public void onClick(DialogInterface dialog,int id){
						dialog.cancel();
					}
				});
				AlertDialog dialog = builder.create();
				dialog.show();
				break;
			case R.id.informationCallAlbertinum:
				AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
				builder2.setTitle("Anrufbestätigung");
				builder2.setMessage("Möchtest du wirklich das Sekretäriat des Haus Albertinum anrufen? (Bitte beachte, dass dadurch Gebühren anfallen können!)");
				builder2.setPositiveButton("Ja",new DialogInterface.OnClickListener(){
						public void onClick(DialogInterface dialog,int id) {
							try {
								Intent callIntent = new Intent(Intent.ACTION_CALL);
								callIntent.setData(Uri.parse("tel:+49373123081"));
								startActivity(callIntent);
							} catch (ActivityNotFoundException activityException) {
								activityException.printStackTrace();
							}
						}
					});
				builder2.setNegativeButton("Nein", new DialogInterface.OnClickListener(){
						public void onClick(DialogInterface dialog,int id){
							dialog.cancel();
						}
					});
				AlertDialog dialog2 = builder2.create();
				dialog2.show();
				break;
			case R.id.informationMailDuerer:
				try {
					String uriText =
						"mailto:gym.fg.scholl@gmx.de" + 
						"?subject=" + URLEncoder.encode("") + 
						"&body=" + URLEncoder.encode("");

					Uri uri = Uri.parse(uriText);

					Intent sendIntent = new Intent(Intent.ACTION_SENDTO);
					sendIntent.setData(uri);
					startActivity(Intent.createChooser(sendIntent, "Email senden via...")); 
				} catch (ActivityNotFoundException activityException) {
					activityException.printStackTrace();
				}
				break;
			case R.id.informationMailAlbertinum:
				try {
					String uriText =
						"mailto:gym.fg.scholl@gmx.de" + 
						"?subject=" + URLEncoder.encode("") + 
						"&body=" + URLEncoder.encode("");

					Uri uri = Uri.parse(uriText);

					Intent sendIntent = new Intent(Intent.ACTION_SENDTO);
					sendIntent.setData(uri);
					startActivity(Intent.createChooser(sendIntent, "Email senden via...")); 
				} catch (ActivityNotFoundException activityException) {
					activityException.printStackTrace();
				}
				break;
			case R.id.informationWebDuerer:
				String url = "http://www.gsg-freiberg.de";
				Intent i = new Intent(Intent.ACTION_VIEW);
				i.setData(Uri.parse(url));
				startActivity(i);
				break;
			case R.id.informationWebAlbertinum:
				String url2 = "http://www.gsg-freiberg.de";
				Intent i2 = new Intent(Intent.ACTION_VIEW);
				i2.setData(Uri.parse(url2));
				startActivity(i2);
				break;
		}
		
	}
	
}
