package com.aldaviva.backdrop.service.impl;

import static org.mockito.Mockito.*;

import android.app.WallpaperManager;
import android.graphics.Bitmap;

import org.jukito.JukitoModule;
import org.jukito.JukitoRunner;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;

@RunWith(JukitoRunner.class)
public class BackdropServiceTest {

	public static class TestModule extends JukitoModule {

		@Override
		protected void configureTest() {
			bind(WallpaperManager.class).toInstance(mock(WallpaperManager.class));
		}
	}

	@Inject private WallpaperManager wallpaperManager;

	@Test
	@Ignore
	public void testDimensionsCalculation(){
		final Bitmap mockSourceBitmap = mock(Bitmap.class);
		when(mockSourceBitmap.getWidth())
			.thenReturn(720);
		when(mockSourceBitmap.getHeight())
			.thenReturn(1280);

		when(wallpaperManager.getDesiredMinimumWidth())
			.thenReturn(1196);
		when(wallpaperManager.getDesiredMinimumWidth())
			.thenReturn(1340);
	}
}
