package com.aldaviva.backdrop.service.impl;

import com.aldaviva.backdrop.data.PreferencesKeys;
import com.aldaviva.backdrop.service.SchedulingService;

import android.content.SharedPreferences;

import com.google.inject.Inject;

public class SchedulingServiceImpl implements SchedulingService {

	@Inject private SharedPreferences preferences;

	@Override
	public boolean isScheduleEnabled() {
		return preferences.getBoolean(PreferencesKeys.IS_SCHEDULED_UPDATE_ENABLED, false);
	}

	public void setScheduleEnabled(final boolean isScheduleEnabled){
		preferences.edit()
			.putBoolean(PreferencesKeys.IS_SCHEDULED_UPDATE_ENABLED, isScheduleEnabled)
		.commit();

		//TODO tell AlarmManager about the new schedule
	}

	@Override
	public long getInterval() {
		return preferences.getLong(PreferencesKeys.UPDATE_INTERVAL, 0);
	}

	@Override
	public void setInterval(final long interval) {
		//TODO make sure only AlarmManager imprecise intervals are accepted
		preferences.edit()
			.putLong(PreferencesKeys.UPDATE_INTERVAL, interval)
		.commit();

		//TODO tell AlarmManager about the new schedule
		//TODO cancel old schedules
	}

}
