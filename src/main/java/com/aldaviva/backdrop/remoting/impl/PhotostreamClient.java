package com.aldaviva.backdrop.remoting.impl;

import com.aldaviva.backdrop.data.entity.Photo;
import com.aldaviva.backdrop.data.entity.Subscription;
import com.aldaviva.backdrop.remoting.marshal.PhotostreamResponseEnvelope;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import roboguice.util.Ln;

public class PhotostreamClient extends FlickrClient {

	private static final String SIZE_CODE = "b";
	private static final String URI_TEMPLATE = "http://api.flickr.com/services/rest/?method={method}&api_key={apiKey}&user_id={userId}&per_page=1&page={page}&format=rest";

	private final Random random = new Random();

	@Override
	public Photo getRandomPhoto(final Subscription subscription) {
		final int count = getCount(subscription);

		final Map<String, String> uriVariables = getUriVariables(subscription);
		final int page = random.nextInt(count);
		uriVariables.put("page", String.valueOf(page));

		final PhotostreamResponseEnvelope response = rest.getForObject(URI_TEMPLATE, PhotostreamResponseEnvelope.class, uriVariables);

		final com.aldaviva.backdrop.remoting.marshal.PhotostreamResponseEnvelope.PhotoEnvelope responsePhoto = response.getPhoto();

		final Photo resultPhoto = new Photo();
		resultPhoto.setTitle(responsePhoto.getTitle());
		resultPhoto.setSubscriptionName(subscription.getName());
		resultPhoto.setPageUrl(String.format("http://www.flickr.com/photos/%s/%s", subscription.getFlickrId(), responsePhoto.getId()));
		resultPhoto.setPhotoUrl(String.format("http://farm%d.staticflickr.com/%d/%d_%s_" + SIZE_CODE + ".jpg", responsePhoto.getFarm(), responsePhoto.getServer(), responsePhoto.getId(), responsePhoto.getSecret()));

		return resultPhoto;
	}

	private Map<String, String> getUriVariables(final Subscription subscription) {
		final Map<String, String> uriVariables = new HashMap<String, String>();
		uriVariables.put("method", "flickr.people.getPublicPhotos");
		uriVariables.put("userId", subscription.getFlickrId());
		uriVariables.put("apiKey", "e3a48ab90a77acdac9267848b95956ff");
		return uriVariables;
	}

	@Override
	public int getCount(final Subscription subscription){
		final Map<String, String> uriVariables = getUriVariables(subscription);
		uriVariables.put("page", "0");

		final PhotostreamResponseEnvelope response = rest.getForObject(URI_TEMPLATE, PhotostreamResponseEnvelope.class, uriVariables);

		final int count = response.getCount();
		Ln.i("%s has %d photos", subscription.getName(), count);

		return count;
	}

}
