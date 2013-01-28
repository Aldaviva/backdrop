package com.aldaviva.backdrop.data.entity;

public enum SubscriptionType {

	GROUP, PHOTOSTREAM, EXPLORE, COMMONS, RECENT;

	public interface Names {
		static final String GROUP = "group";
		static final String PHOTOSTREAM = "photostream";
		static final String EXPLORE = "explore";
		static final String COMMONS = "commons";
		static final String RECENT = "recent";
	}

}
