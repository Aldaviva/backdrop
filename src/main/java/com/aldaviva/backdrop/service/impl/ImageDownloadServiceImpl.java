package com.aldaviva.backdrop.service.impl;

import com.aldaviva.backdrop.service.ImageDownloadService;

import android.content.Context;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;

import org.springframework.util.support.Base64;
import org.springframework.web.client.RestOperations;

import roboguice.util.Ln;

import com.google.inject.Inject;

public class ImageDownloadServiceImpl implements ImageDownloadService {

	@Inject private RestOperations rest;
	@Inject private Context context;

	@Override
	public File downloadImage(final URI uri) {
		final byte[] imageBytes = retrieveBytes(uri);
		final String filename = getFilename(uri);
		saveFile(filename, imageBytes);
		return context.getFileStreamPath(filename);
	}

	private byte[] retrieveBytes(final URI uri) {
		return rest.getForObject(uri, byte[].class);
	}

	protected String getFilename(final URI uri){
		try {
			return Base64.encodeBytes(uri.toString().getBytes(), Base64.URL_SAFE) + ".jpg";
		} catch (final IOException e) {
			return null;
		}
//		return new String(md5.digest(uri.toASCIIString().getBytes()))+".jpg";
	}

	private void saveFile(final String filename, final byte[] data){
		try {
			final FileOutputStream fileOutputStream = context.openFileOutput(filename, Context.MODE_PRIVATE);
			fileOutputStream.write(data);
			fileOutputStream.flush();
			fileOutputStream.close();
		} catch (final FileNotFoundException e) {
			Ln.e(e, "Could not open file for saving image");
		} catch (final IOException e) {
			Ln.e(e, "Could not write image to file");
		}
	}

}
