package com.aldaviva.backdrop;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

	private BackdropService backdropService;

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		backdropService = new BackdropServiceImpl(getApplicationContext());

		final Button button1 = (Button) findViewById(R.id.button1);
		final Button button2 = (Button) findViewById(R.id.button2);

		button1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(final View v) {
				backdropService.setBackgroundToLego();
			}
		});

		button2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(final View v) {
				backdropService.setBackgroundToBoris();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(final Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
