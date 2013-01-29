package com.aldaviva.backdrop.service.impl;

import com.aldaviva.backdrop.data.PreferencesKeys;
import com.aldaviva.backdrop.service.SchedulingService;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.PendingIntent.CanceledException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.SystemClock;

import roboguice.util.Ln;

import com.google.inject.Inject;

public class SchedulingServiceImpl implements SchedulingService {

	private static final String INTENT_ACTION = "com.aldaviva.backdrop.action.update";
	private static final long DEFAULT_INTERVAL = 15*1000;
//	private static final long DEFAULT_INTERVAL = AlarmManager.INTERVAL_HOUR;

	@Inject private SharedPreferences preferences;
	@Inject private AlarmManager alarmManager;
	@Inject private Context context;

	private PendingIntent broadcastIntent;
	private Intent immediateUpdateIntent;

	@Inject
	protected void init(){
		immediateUpdateIntent = new Intent(INTENT_ACTION);
		broadcastIntent = PendingIntent.getBroadcast(context, 0, immediateUpdateIntent, PendingIntent.FLAG_UPDATE_CURRENT);
	}

	@Override
	public boolean isScheduleEnabled() {
		return preferences.getBoolean(PreferencesKeys.IS_SCHEDULED_UPDATE_ENABLED, false);
	}

	@Override
	public void setScheduleEnabled(final boolean isScheduleEnabled){
		preferences.edit()
			.putBoolean(PreferencesKeys.IS_SCHEDULED_UPDATE_ENABLED, isScheduleEnabled)
		.commit();

		if(isScheduleEnabled){
			schedule(getInterval());
		} else {
			unschedule();
		}
	}

	@Override
	public long getInterval() {
		return preferences.getLong(PreferencesKeys.UPDATE_INTERVAL, DEFAULT_INTERVAL);
	}

	@Override
	public void setInterval(final long interval) {
		//TODO make sure only AlarmManager imprecise intervals are accepted
		preferences.edit()
			.putLong(PreferencesKeys.UPDATE_INTERVAL, interval)
		.commit();

		schedule(interval);
	}

	@Override
	public void updateImmediately(){
		try {
			//No idea if this works.
			Ln.i("Updating wallpaper immediately.");
			broadcastIntent.send();
		} catch (final CanceledException e) {
			Ln.e(e, "Intent was canceled.");
		}
	}

	private void schedule(final long interval){
		alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime() + interval, interval, broadcastIntent);
		Ln.i("Scheduled wallpaper updates for every %d minutes.", Math.round(interval/1000.0/60.0));
	}

	private void unschedule(){
		alarmManager.cancel(broadcastIntent);
		Ln.i("Disabled scheduled wallpaper updates.");
	}

}
