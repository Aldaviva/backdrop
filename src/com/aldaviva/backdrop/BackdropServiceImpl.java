package com.aldaviva.backdrop;

import java.io.IOException;

import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;

public class BackdropServiceImpl implements BackdropService {

	private final WallpaperManager wallpaperManager;
	private final Context context;

	public BackdropServiceImpl(final Context context) {
		this.context = context;
		wallpaperManager = WallpaperManager.getInstance(context);
//		final int minWidth = wallpaperManager.getDesiredMinimumWidth();
//		final int minHeight = wallpaperManager.getDesiredMinimumHeight();
//		Toast.makeText(context, "Desired size: " + minWidth + " x " + minHeight, Toast.LENGTH_LONG).show();
	}

	@Override
	public void setBackgroundToBoris() {
		setBackgroundToDrawable(R.drawable.boris);
	}

	@Override
	public void setBackgroundToLego() {
		setBackgroundToDrawable(R.drawable.lego);
	}

	private void setBackgroundToDrawable(final int drawableId) {
		final Bitmap sourceBitmap = BitmapFactory.decodeResource(context.getResources(), drawableId);

		setBackgroundToBitmap(sourceBitmap);
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
			e.printStackTrace();
		}
	}

}
