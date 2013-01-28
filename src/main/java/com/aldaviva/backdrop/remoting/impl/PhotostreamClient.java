package com.aldaviva.backdrop.remoting.impl;

import com.aldaviva.backdrop.data.entity.Subscription;

import org.springframework.web.util.UriComponentsBuilder;

public class PhotostreamClient extends FlickrPhotoListClient {

	@Override
	public String getFlickrMethod() {
		return "flickr.people.getPublicPhotos";
	}

	@Override
	protected UriComponentsBuilder getUriBuilder(final Subscription subscription) {
		return super.getUriBuilder(subscription)
				.queryParam("user_id", subscription.getFlickrId());
	}

}
