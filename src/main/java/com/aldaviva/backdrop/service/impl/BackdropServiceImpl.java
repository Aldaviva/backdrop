package com.aldaviva.backdrop.service.impl;

import com.aldaviva.backdrop.R;
import com.aldaviva.backdrop.service.BackdropService;

import android.app.WallpaperManager;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;

import java.io.File;
import java.io.IOException;

import roboguice.util.Ln;

import com.google.inject.Inject;

public class BackdropServiceImpl implements BackdropService {

	@Inject private WallpaperManager wallpaperManager;
	@Inject private Resources resources;
	@Inject private Context context;

	@Override
	public void setBackgroundToBoris() {
		setBackgroundToDrawable(R.drawable.boris);
	}

	@Override
	public void setBackgroundToLego() {
		setBackgroundToDrawable(R.drawable.lego);
	}

	private void setBackgroundToDrawable(final int drawableId) {
		final Bitmap sourceBitmap = BitmapFactory.decodeResource(resources, drawableId);

		setBackgroundToBitmap(sourceBitmap);
	}

	@Override
	public void setBackgroundToFile(final File file){
		setBackgroundToBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));
	}

	private void setBackgroundToBitmap(final Bitmap sourceBitmap) {
		final int sourceWidth = sourceBitmap.getWidth();
		final int sourceHeight = sourceBitmap.getHeight();
		final int letterboxedWidth = wallpaperManager.getDesiredMinimumWidth();
		final int letterboxedHeight = wallpaperManager.getDesiredMinimumHeight();

		final float resizeRatio = (float) letterboxedHeight / sourceHeight;
		final int resizedWidth = (int) Math.ceil(sourceWidth * resizeRatio);

		final Bitmap letterboxedBitmap = Bitmap.createBitmap(letterboxedWidth, letterboxedHeight, Bitmap.Config.ARGB_8888);

		final Canvas canvas = new Canvas(letterboxedBitmap);
		canvas.drawRGB(0, 0, 0);

		final Matrix transformations = new Matrix();
		transformations.postScale(resizeRatio, resizeRatio);
		transformations.postTranslate(Math.round(((float) letterboxedWidth - resizedWidth)/2), 0);
		canvas.drawBitmap(sourceBitmap, transformations, null);


		try {
			wallpaperManager.setBitmap(letterboxedBitmap);
		} catch (final IOException e) {
			Ln.e(e, "WallpaperManager rejected bitmap");
		}
	}

}
