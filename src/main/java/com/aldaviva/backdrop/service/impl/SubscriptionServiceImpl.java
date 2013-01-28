package com.aldaviva.backdrop.service.impl;

import com.aldaviva.backdrop.data.dao.SubscriptionDao;
import com.aldaviva.backdrop.data.entity.Photo;
import com.aldaviva.backdrop.data.entity.Subscription;
import com.aldaviva.backdrop.data.entity.SubscriptionType;
import com.aldaviva.backdrop.remoting.RemoteClient;
import com.aldaviva.backdrop.service.SubscriptionService;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.j256.ormlite.stmt.QueryBuilder;

public class SubscriptionServiceImpl implements SubscriptionService {

	@Inject private SubscriptionDao subscriptionDao;

	@Inject @Named(SubscriptionType.Names.PHOTOSTREAM)
	private RemoteClient photostreamClient;

	@Inject @Named(SubscriptionType.Names.COMMONS)
	private RemoteClient commonsClient;

	@Inject @Named(SubscriptionType.Names.EXPLORE)
	private RemoteClient exploreClient;

	@Inject @Named(SubscriptionType.Names.GROUP)
	private RemoteClient groupClient;

	@Inject @Named(SubscriptionType.Names.RECENT)
	private RemoteClient recentClient;

	private final Random random = new Random();
	private final Map<SubscriptionType, RemoteClient> clientMapping = new HashMap<SubscriptionType, RemoteClient>();

	@Inject
	protected void initClientMapping(){
		clientMapping.put(SubscriptionType.PHOTOSTREAM, photostreamClient);
		clientMapping.put(SubscriptionType.COMMONS, commonsClient);
		clientMapping.put(SubscriptionType.EXPLORE, exploreClient);
		clientMapping.put(SubscriptionType.GROUP, groupClient);
		clientMapping.put(SubscriptionType.RECENT, recentClient);
	}

	@Override
	public Subscription getRandomSubscription(){
		try {
			final long count = subscriptionDao.countOf();
			final long randomOffset = (long) (random.nextDouble() * count);

			final QueryBuilder<Subscription, Long> query = subscriptionDao.queryBuilder()
				.limit(1L)
				.offset(randomOffset);

			return query.queryForFirst();

		} catch (final SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Photo getRandomPhoto() {
		return getRandomPhoto(getRandomSubscription());
	}

	@Override
	public Photo getRandomPhoto(final Subscription subscription) {
		final RemoteClient client = clientMapping.get(subscription.getType());
		return client.getRandomPhoto(subscription);
	}

}
