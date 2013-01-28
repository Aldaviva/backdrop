package com.aldaviva.backdrop.remoting.impl;

public class ExploreClient extends FlickrPhotoListClient {

	@Override
	public String getFlickrMethod() {
		return "flickr.interestingness.getList";
	}
}
