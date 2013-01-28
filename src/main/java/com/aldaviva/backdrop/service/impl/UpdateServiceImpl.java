package com.aldaviva.backdrop.service.impl;

import com.aldaviva.backdrop.data.entity.Photo;
import com.aldaviva.backdrop.service.ImageDownloadService;
import com.aldaviva.backdrop.service.SubscriptionService;
import com.aldaviva.backdrop.service.UpdateService;
import com.aldaviva.backdrop.service.WallpaperManagerService;

import java.io.File;

import roboguice.util.Ln;

import com.google.inject.Inject;

public class UpdateServiceImpl implements UpdateService {

	@Inject private SubscriptionService subscriptionService;
	@Inject private ImageDownloadService imageDownloadService;
	@Inject private WallpaperManagerService wallpaperManagerService;

	@Override
	public void updateWallpaper() {
		final Photo photo = subscriptionService.getRandomPhoto();
		Ln.i("Picked photo: %s", photo);
		final File image = imageDownloadService.downloadImage(photo.getPhotoUrl());
		Ln.i("Image downloaded");
		wallpaperManagerService.setBackgroundToFile(image);
		Ln.i("Wallpaper applied");
	}

}
