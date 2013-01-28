package com.aldaviva.backdrop.remoting.impl;

import com.aldaviva.backdrop.data.entity.Photo;
import com.aldaviva.backdrop.data.entity.Subscription;
import com.aldaviva.backdrop.remoting.marshal.PhotolistResponseEnvelope;
import com.aldaviva.backdrop.remoting.marshal.PhotolistResponseEnvelope.PhotoEnvelope;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.joda.time.DateTime;

public class ExploreClient extends FlickrClient {

	private static final String URI_TEMPLATE = "http://api.flickr.com/services/rest/?method={method}&api_key={apiKey}&extras=owner_name&per_page=1&format=rest";
	private static final Character SIZE_CODE = 'b';

	Random random = new Random();

	@Override
	public Photo getRandomPhoto(final Subscription subscription) {
		final int count = getCount(subscription);

		final Map<String, String> uriVariables = getUriVariables();

		final int page = random.nextInt(count) + 1;
		uriVariables.put("page", String.valueOf(page));

		final PhotolistResponseEnvelope response = rest.getForObject(URI_TEMPLATE, PhotolistResponseEnvelope.class, uriVariables);

		final PhotoEnvelope responsePhoto = response.getPhoto();

		final Photo resultPhoto = new Photo();
		resultPhoto.setTitle(responsePhoto.getTitle());
		resultPhoto.setAuthorName(responsePhoto.getOwnername());
		resultPhoto.setLastUsed(new DateTime());
		resultPhoto.setSubscriptionName(subscription.getName());
		resultPhoto.setPageUrl(String.format("http://www.flickr.com/photos/%s/%s", responsePhoto.getOwner(), responsePhoto.getId()));
		resultPhoto.setPhotoUrl(String.format("http://farm%d.staticflickr.com/%d/%d_%s_%s.jpg", responsePhoto.getFarm(), responsePhoto.getServer(), responsePhoto.getId(), responsePhoto.getSecret(), SIZE_CODE));

		return resultPhoto;
	}

	@Override
	public int getCount(final Subscription subscription) {
		final Map<String, String> uriVariables = getUriVariables();
		uriVariables.put("page", "1");

		final PhotolistResponseEnvelope response = rest.getForObject(URI_TEMPLATE, PhotolistResponseEnvelope.class, uriVariables);

		return response.getCount();
	}

	private Map<String, String> getUriVariables() {
		final Map<String, String> uriVariables = new HashMap<String, String>();
		uriVariables.put("method", "flickr.interestingness.getList");
		uriVariables.put("apiKey", "e3a48ab90a77acdac9267848b95956ff");
		return uriVariables;
	}
}
