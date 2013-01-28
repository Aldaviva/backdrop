package com.aldaviva.backdrop.data.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Subscription {

	public static final String _id = "id";
	public static final String _name = "name";
	public static final String _type = "type";
	public static final String _isEnabled = "isEnabled";
	public static final String _flickrId = "flickrId";

	@Id @Column(name=_id)
	private Long id;

	@Column(name=_name)
	private String name;

	@Column(name=_type)
	private SubscriptionType type;

	@Column(name=_isEnabled)
	private boolean isEnabled;

	@Column(name=_flickrId)
	private String flickrId;

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public SubscriptionType getType() {
		return type;
	}

	public void setType(final SubscriptionType type) {
		this.type = type;
	}

	public boolean isEnabled() {
		return isEnabled;
	}

	public void setEnabled(final boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	public String getFlickrId() {
		return flickrId;
	}

	public void setFlickrId(final String flickrId) {
		this.flickrId = flickrId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((flickrId == null) ? 0 : flickrId.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		final Subscription other = (Subscription) obj;
		if (flickrId == null) {
			if (other.flickrId != null)
				return false;
		} else if (!flickrId.equals(other.flickrId))
			return false;
		if (type != other.type)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return String.format("Subscription [id=%s, name=%s, type=%s, isEnabled=%s, flickrId=%s]", id, name, type, isEnabled, flickrId);
	}


}
