package com.aldaviva.backdrop.service.impl;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import android.content.Context;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;

import org.jukito.JukitoModule;
import org.jukito.JukitoRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.web.client.RestOperations;

import com.google.inject.Inject;

@RunWith(JukitoRunner.class)
public class ImageDownloadServiceTest {

	public static class TestModule extends JukitoModule {

		@Override
		protected void configureTest() {
			final Context mockContext = mock(Context.class);
			bind(Context.class).toInstance(mockContext);

			bind(ImageDownloadServiceImpl.class);

			final RestOperations mockRestOperations = mock(RestOperations.class);
			bind(RestOperations.class).toInstance(mockRestOperations);
		}
	}

	@Inject private Context context;
	@Inject private ImageDownloadServiceImpl imageDownloadService;
	@Inject private RestOperations rest;

	@Test
	public void testFilenameEncoding() throws URISyntaxException{
		final String actual = imageDownloadService.getFilename("http://www.aldaviva.com/headlights.png");
		final String expected = "aHR0cDovL3d3dy5hbGRhdml2YS5jb20vaGVhZGxpZ2h0cy5wbmc=.jpg";

		assertEquals(expected, actual);
	}

	@Test
	public void testDownloadImage() throws URISyntaxException, IOException{
		final FileOutputStream mockFileOutputStream = mock(FileOutputStream.class);
		final String uri = "http://www.aldaviva.com/headlights.png";
		final byte[] fakeImageData = new byte[]{1, 2, 3};
		final File fakeFile = new File("/data/data/com.aldaviva.backdrop/files/aHR0cDovL3d3dy5hbGRhdml2YS5jb20vaGVhZGxpZ2h0cy5wbmc=.jpg");

		when(rest.getForObject(isA(String.class), (Class<byte[]>) any()))
			.thenReturn(fakeImageData);

		when(context.openFileOutput(anyString(), anyInt()))
			.thenReturn(mockFileOutputStream);

		when(context.getFileStreamPath(anyString()))
			.thenReturn(fakeFile);

		imageDownloadService.downloadImage(uri);

		verify(rest).getForObject(uri, byte[].class);
		verify(context).openFileOutput("aHR0cDovL3d3dy5hbGRhdml2YS5jb20vaGVhZGxpZ2h0cy5wbmc=.jpg", 0);
		verify(mockFileOutputStream).write(fakeImageData);
		verify(mockFileOutputStream).flush();
		verify(mockFileOutputStream).close();
		verify(context).getFileStreamPath("aHR0cDovL3d3dy5hbGRhdml2YS5jb20vaGVhZGxpZ2h0cy5wbmc=.jpg");
	}


}
