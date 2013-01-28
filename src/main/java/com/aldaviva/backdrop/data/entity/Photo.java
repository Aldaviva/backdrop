package com.aldaviva.backdrop.data.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.joda.time.DateTime;

@Entity
public class Photo {

	public static final String _id = "id";
	public static final String _title = "title";
	public static final String _authorName = "authorName";
	public static final String _photoUrl = "photoUrl";
	public static final String _pageUrl = "pageUrl";
	public static final String _subscriptionName = "subscriptionName";
	public static final String _lastUsed = "lastUsed";

	@Id @Column(name=_id)
	private Long id;

	@Column(name=_title)
	private String title;

	@Column(name=_authorName)
	private String authorName;

	@Column(name=_photoUrl)
	private String photoUrl;

	@Column(name=_pageUrl)
	private String pageUrl;

	@Column(name=_subscriptionName)
	private String subscriptionName;

	@Column(name=_lastUsed)
	private DateTime lastUsed;

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(final String authorName) {
		this.authorName = authorName;
	}

	public String getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(final String photoUrl) {
		this.photoUrl = photoUrl;
	}

	public String getPageUrl() {
		return pageUrl;
	}

	public void setPageUrl(final String pageUrl) {
		this.pageUrl = pageUrl;
	}

	public String getSubscriptionName() {
		return subscriptionName;
	}

	public void setSubscriptionName(final String subscriptionName) {
		this.subscriptionName = subscriptionName;
	}

	public DateTime getLastUsed() {
		return lastUsed;
	}

	public void setLastUsed(final DateTime lastUsed) {
		this.lastUsed = lastUsed;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((authorName == null) ? 0 : authorName.hashCode());
		result = prime * result + ((pageUrl == null) ? 0 : pageUrl.hashCode());
		result = prime * result + ((photoUrl == null) ? 0 : photoUrl.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Photo other = (Photo) obj;
		if (authorName == null) {
			if (other.authorName != null)
				return false;
		} else if (!authorName.equals(other.authorName))
			return false;
		if (pageUrl == null) {
			if (other.pageUrl != null)
				return false;
		} else if (!pageUrl.equals(other.pageUrl))
			return false;
		if (photoUrl == null) {
			if (other.photoUrl != null)
				return false;
		} else if (!photoUrl.equals(other.photoUrl))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return String.format("Photo [id=%s, title=%s, authorName=%s, photoUrl=%s, pageUrl=%s, subscriptionName=%s, lastUsed=%s]", id, title, authorName,
				photoUrl, pageUrl, subscriptionName, lastUsed);
	}


}
