package com.aldaviva.backdrop.remoting.impl;

import com.aldaviva.backdrop.data.entity.Subscription;

import org.springframework.web.util.UriComponentsBuilder;


public class GroupClient extends FlickrPhotoListClient {

	@Override
	public String getFlickrMethod() {
		return "flickr.groups.pools.getPhotos";
	}

	@Override
	protected UriComponentsBuilder getUriBuilder(final Subscription subscription) {
		return super.getUriBuilder(subscription)
				.queryParam("group_id", subscription.getFlickrId());
	}
}
