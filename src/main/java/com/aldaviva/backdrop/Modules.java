package com.aldaviva.backdrop;

import com.aldaviva.backdrop.service.BackdropService;
import com.aldaviva.backdrop.service.ImageDownloadService;
import com.aldaviva.backdrop.service.impl.BackdropServiceImpl;
import com.aldaviva.backdrop.service.impl.ImageDownloadServiceImpl;

import android.app.WallpaperManager;
import android.content.Context;

import java.util.List;

import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.xml.SimpleXmlHttpMessageConverter;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Scopes;

public class Modules extends AbstractModule {

	@Override
	protected void configure() {
		bind(BackdropService.class).to(BackdropServiceImpl.class).in(Scopes.SINGLETON);
		bind(ImageDownloadService.class).to(ImageDownloadServiceImpl.class).in(Scopes.SINGLETON);
	}

	@Provides
	protected RestOperations restOperations(){
		final RestTemplate restTemplate = new RestTemplate();
		final List<HttpMessageConverter<?>> messageConverters = restTemplate.getMessageConverters();
		messageConverters.add(new ByteArrayHttpMessageConverter());
		messageConverters.add(new SimpleXmlHttpMessageConverter());
		return restTemplate;
	}

	@Provides
	protected WallpaperManager wallpaperManager(final Context context){
		return WallpaperManager.getInstance(context);
	}

}
