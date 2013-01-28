package com.aldaviva.backdrop.remoting.impl;

import com.aldaviva.backdrop.data.entity.Photo;
import com.aldaviva.backdrop.data.entity.Subscription;
import com.aldaviva.backdrop.remoting.marshal.PhotolistResponseEnvelope;
import com.aldaviva.backdrop.remoting.marshal.PhotolistResponseEnvelope.PhotoEnvelope;

import java.util.Random;

import org.joda.time.DateTime;
import org.springframework.web.util.UriComponentsBuilder;

import roboguice.util.Ln;

public abstract class FlickrPhotoListClient extends FlickrClient {

//	private static final Builder URI_TEMPLATE = Uri.parse("http://api.flickr.com/services/rest/?extras=owner_name&per_page=1&format=rest").buildUpon();
	private static final Character SIZE_CODE = 'b';

	private final Random random = new Random();
	private final UriComponentsBuilder uriTemplate;

	public FlickrPhotoListClient() {
		super();
		uriTemplate = UriComponentsBuilder.newInstance()
			.scheme("http")
			.host("api.flickr.com")
			.port(80)
			.path("/services/rest/")
			.queryParam("format", "rest")
			.queryParam("api_key", "e3a48ab90a77acdac9267848b95956ff")
			.queryParam("per_page", "1")
			.queryParam("extras", "owner_name");
	}

	@Override
	public Photo getRandomPhoto(final Subscription subscription) {
		final UriComponentsBuilder uriBuilder = getUriBuilder(subscription);

		final int pageCount = getCount(subscription);
		Ln.i("%s has %d photos", subscription.getName(), pageCount);
		final int page = random.nextInt(pageCount) + 1;
		uriBuilder.queryParam("page", String.valueOf(page));

//		final String uri = uriBuilder.build().toString();
//		Ln.i("GET "+uri);
		final PhotolistResponseEnvelope response = rest.getForObject(uriBuilder.build().toUri(), PhotolistResponseEnvelope.class);

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
		final UriComponentsBuilder uriBuilder = getUriBuilder(subscription);
		uriBuilder.queryParam("page", "1");

//		final String uri = uriBuilder.build().toString();
//		Ln.i("GET "+uri);
		final PhotolistResponseEnvelope response = rest.getForObject(uriBuilder.build().toUri(), PhotolistResponseEnvelope.class);

		return response.getCount();
	}

	protected UriComponentsBuilder getUriBuilder(final Subscription subscription) {
		return uriTemplate
				.queryParam("method", getFlickrMethod());

	}

	public abstract String getFlickrMethod();

}