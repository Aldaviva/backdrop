package com.aldaviva.backdrop.service.impl;

import com.aldaviva.backdrop.service.UpdateService;

import android.content.Context;
import android.content.Intent;

import roboguice.receiver.RoboBroadcastReceiver;
import roboguice.util.Ln;

import com.google.inject.Inject;

public class AlarmReceiver extends RoboBroadcastReceiver {

	@Inject private UpdateService updateService;

	@Override
	protected void handleReceive(final Context context, final Intent intent) {
		Ln.i("Received broadcast intent to perform a wallpaper update, triggering update service...");
		final Intent startServiceIntent = new Intent(context, updateService.getClass());
		context.startService(startServiceIntent);
	}

}
