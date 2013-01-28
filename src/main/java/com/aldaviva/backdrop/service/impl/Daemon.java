package com.aldaviva.backdrop.service.impl;

import android.content.Intent;

import roboguice.service.RoboIntentService;

public class Daemon extends RoboIntentService {

	public Daemon(final String name) {
		super(name);
	}

	@Override
	protected void onHandleIntent(final Intent arg0) {
	}

}
