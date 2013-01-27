package com.aldaviva.backdrop.ui.activity;

import com.aldaviva.backdrop.R;
import com.aldaviva.backdrop.service.BackdropService;
import com.aldaviva.backdrop.service.ImageDownloadService;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;
import roboguice.util.Ln;
import roboguice.util.SafeAsyncTask;

import com.google.inject.Inject;

public class MainActivity extends RoboActivity {

	@InjectView(R.id.button1) private Button button1;
	@InjectView(R.id.button2) private Button button2;
	@InjectView(R.id.button3) private Button button3;

	@Inject private BackdropService backdropService;
	@Inject private ImageDownloadService imageDownloadService;

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

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

		button3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(final View arg0) {
				try {
					setBackgroundToUri(new URI("http://farm9.staticflickr.com/8508/8400010419_859d3e67b8_b.jpg"));
				} catch (final URISyntaxException e) {
				}
			}
		});
	}

	public void setBackgroundToUri(final URI uri){
		new SafeAsyncTask<Void>() {

			@Override
			public Void call() throws Exception {
				Ln.i("Downloading image...");
				final File imageFile = imageDownloadService.downloadImage(uri);
				Ln.i("Applying wallpaper...");
				backdropService.setBackgroundToFile(imageFile);
				Ln.i("Wallpaper applied");
				return null;
			}

			@Override
			protected void onThrowable(final Throwable t) throws RuntimeException {
				Ln.e(t, "Unable to set wallpaper!");
			}
		}.execute();
	}

	@Override
	public boolean onCreateOptionsMenu(final Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
