package com.aldaviva.backdrop.service.impl;

import com.aldaviva.backdrop.data.entity.Photo;
import com.aldaviva.backdrop.service.ImageDownloadService;
import com.aldaviva.backdrop.service.SubscriptionService;
import com.aldaviva.backdrop.service.UpdateService;
import com.aldaviva.backdrop.service.WallpaperManagerService;

import android.content.Intent;

import java.io.File;

import roboguice.service.RoboIntentService;
import roboguice.util.Ln;

import com.google.inject.Inject;

public class UpdateServiceImpl extends RoboIntentService implements UpdateService {

	@Inject private SubscriptionService subscriptionService;
	@Inject private ImageDownloadService imageDownloadService;
	@Inject private WallpaperManagerService wallpaperManagerService;

	public UpdateServiceImpl() {
		super("UpdateService");
	}

	@Override
	public void updateWallpaper() {
		Ln.i("Updating wallpaper...");
		final Photo photo = subscriptionService.getRandomPhoto();
		Ln.i("Picked photo: %s", photo.getPhotoUrl());
		final File image = imageDownloadService.downloadImage(photo.getPhotoUrl());
		Ln.i("Image downloaded");
		wallpaperManagerService.setBackgroundToFile(image);
		Ln.i("Wallpaper applied");
	}

	@Override
	protected void onHandleIntent(final Intent arg0) {
		updateWallpaper();
	}

}
