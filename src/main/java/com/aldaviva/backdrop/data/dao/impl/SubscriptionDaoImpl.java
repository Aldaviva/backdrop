package com.aldaviva.backdrop.data.dao.impl;

import com.aldaviva.backdrop.data.dao.SubscriptionDao;
import com.aldaviva.backdrop.data.entity.Subscription;

import java.sql.SQLException;

import com.google.inject.Inject;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

public class SubscriptionDaoImpl extends BaseDaoImpl<Subscription, Long> implements SubscriptionDao {

	@Inject
	protected SubscriptionDaoImpl(final ConnectionSource connectionSource) throws SQLException {
		super(connectionSource, Subscription.class);
	}



}
