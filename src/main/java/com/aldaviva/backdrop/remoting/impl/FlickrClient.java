package com.aldaviva.backdrop.remoting.impl;

import com.aldaviva.backdrop.data.entity.Subscription;
import com.aldaviva.backdrop.remoting.RemoteClient;

import org.springframework.web.client.RestOperations;

import com.google.inject.Inject;

public abstract class FlickrClient implements RemoteClient {

	@Inject protected RestOperations rest;

	public abstract int getCount(final Subscription subscription);

}
