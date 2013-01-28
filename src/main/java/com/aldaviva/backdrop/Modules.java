package com.aldaviva.backdrop;

import com.aldaviva.backdrop.data.OpenHelper;
import com.aldaviva.backdrop.data.dao.PhotoDao;
import com.aldaviva.backdrop.data.dao.SubscriptionDao;
import com.aldaviva.backdrop.data.dao.impl.PhotoDaoImpl;
import com.aldaviva.backdrop.data.dao.impl.SubscriptionDaoImpl;
import com.aldaviva.backdrop.data.entity.SubscriptionType;
import com.aldaviva.backdrop.remoting.RemoteClient;
import com.aldaviva.backdrop.remoting.impl.CommonsClient;
import com.aldaviva.backdrop.remoting.impl.ExploreClient;
import com.aldaviva.backdrop.remoting.impl.GroupClient;
import com.aldaviva.backdrop.remoting.impl.PhotostreamClient;
import com.aldaviva.backdrop.remoting.impl.RecentClient;
import com.aldaviva.backdrop.service.ImageDownloadService;
import com.aldaviva.backdrop.service.SchedulingService;
import com.aldaviva.backdrop.service.SubscriptionService;
import com.aldaviva.backdrop.service.UpdateService;
import com.aldaviva.backdrop.service.WallpaperManagerService;
import com.aldaviva.backdrop.service.impl.ImageDownloadServiceImpl;
import com.aldaviva.backdrop.service.impl.SchedulingServiceImpl;
import com.aldaviva.backdrop.service.impl.SubscriptionServiceImpl;
import com.aldaviva.backdrop.service.impl.UpdateServiceImpl;
import com.aldaviva.backdrop.service.impl.WallpaperManagerServiceImpl;

import android.app.Application;
import android.app.WallpaperManager;
import android.content.Context;

import java.util.List;

import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.xml.SimpleXmlHttpMessageConverter;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import roboguice.inject.SharedPreferencesName;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Scopes;
import com.google.inject.Singleton;
import com.google.inject.name.Names;
import com.j256.ormlite.android.AndroidConnectionSource;
import com.j256.ormlite.support.ConnectionSource;

public class Modules extends AbstractModule {

	@Override
	protected void configure() {
		bindConstant().annotatedWith(SharedPreferencesName.class).to("com.aldaviva.backdrop.preferences");

		bind(WallpaperManagerService.class).to(WallpaperManagerServiceImpl.class);
		bind(ImageDownloadService.class).to(ImageDownloadServiceImpl.class);
		bind(SchedulingService.class).to(SchedulingServiceImpl.class);
		bind(UpdateService.class).to(UpdateServiceImpl.class);
		bind(SubscriptionService.class).to(SubscriptionServiceImpl.class);

		bind(RemoteClient.class).annotatedWith(Names.named(SubscriptionType.Names.PHOTOSTREAM)).to(PhotostreamClient.class);
		bind(RemoteClient.class).annotatedWith(Names.named(SubscriptionType.Names.COMMONS)).to(CommonsClient.class);
		bind(RemoteClient.class).annotatedWith(Names.named(SubscriptionType.Names.EXPLORE)).to(ExploreClient.class);
		bind(RemoteClient.class).annotatedWith(Names.named(SubscriptionType.Names.GROUP)).to(GroupClient.class);
		bind(RemoteClient.class).annotatedWith(Names.named(SubscriptionType.Names.RECENT)).to(RecentClient.class);

		bind(SubscriptionDao.class).to(SubscriptionDaoImpl.class).in(Scopes.SINGLETON);
		bind(PhotoDao.class).to(PhotoDaoImpl.class).in(Scopes.SINGLETON);
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

	@Provides
	@Singleton
	protected ConnectionSource connectionSourceProvider(final Application context) {
		return new AndroidConnectionSource(new OpenHelper(context));
	}

}
