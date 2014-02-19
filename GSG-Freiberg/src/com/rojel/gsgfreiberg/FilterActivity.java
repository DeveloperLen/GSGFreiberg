package com.rojel.gsgfreiberg;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class FilterActivity extends Activity implements OnClickListener {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.filter);
		
		Button ok = (Button) findViewById(R.id.okay);
		Button cancel = (Button) findViewById(R.id.cancel);
		
		ok.setOnClickListener(this);
		cancel.setOnClickListener(this);
	}
	
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.okay:
				Intent data = new Intent();
				data.putExtra("classname", ((EditText) findViewById(R.id.classInput)).getText().toString());
				setResult(RESULT_OK, data);
				finish();
			case R.id.cancel:
				setResult(RESULT_CANCELED);
				finish();
		}
	}
}
