package com.aldaviva.backdrop.service;

/**
 * 1. Pick which wallpaper to use with the SubscriptionService.
 * 2. Download it with the ImageDownloadService.
 * 3. Apply it with the WallpaperManagerService
 */
public interface UpdateService {

	void updateWallpaper();

}
