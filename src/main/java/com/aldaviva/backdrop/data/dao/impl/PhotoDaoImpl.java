package com.aldaviva.backdrop.data.dao.impl;

import com.aldaviva.backdrop.data.dao.PhotoDao;
import com.aldaviva.backdrop.data.entity.Photo;

import java.sql.SQLException;

import com.google.inject.Inject;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

public class PhotoDaoImpl extends BaseDaoImpl<Photo, Long> implements PhotoDao {

	@Inject
	public PhotoDaoImpl(final ConnectionSource connectionSource) throws SQLException {
		super(connectionSource, Photo.class);
	}

}
