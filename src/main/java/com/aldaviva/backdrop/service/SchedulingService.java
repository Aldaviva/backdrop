package com.aldaviva.backdrop.service;

/**
 * Keep track of the user's preferences regarding scheduled updates.
 */
public interface SchedulingService {

	boolean isScheduleEnabled();

	void setScheduleEnabled(boolean isScheduleEnabled);

	long getInterval();

	void setInterval(long interval);

	void updateImmediately();

}