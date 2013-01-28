package com.aldaviva.backdrop.remoting.impl;

import com.aldaviva.backdrop.data.entity.Photo;
import com.aldaviva.backdrop.data.entity.Subscription;

public class ExploreClient extends FlickrClient {

	@Override
	public Photo getRandomPhoto(Subscription subscription) {
		return null;
	}

	@Override
	public int getCount(Subscription subscription) {
		return 0;
	}

}
