package com.aldaviva.backdrop.data;

import com.aldaviva.backdrop.data.entity.Photo;
import com.aldaviva.backdrop.data.entity.Subscription;
import com.aldaviva.backdrop.data.entity.SubscriptionType;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;

import roboguice.util.Ln;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.Dao.CreateOrUpdateStatus;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class OpenHelper extends OrmLiteSqliteOpenHelper {

	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "data.db";

	public OpenHelper(final Application context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(final SQLiteDatabase database, final ConnectionSource connectionSource) {
		try {
			TableUtils.createTableIfNotExists(connectionSource, Photo.class);
			TableUtils.createTableIfNotExists(connectionSource, Subscription.class);

			createDefaultSubscriptions(connectionSource);

		} catch (final SQLException e) {
			Ln.e(e, "Unable to create and populate tables.");
			e.printStackTrace();
		}
	}

	private void createDefaultSubscriptions(final ConnectionSource connectionSource) throws SQLException {
		final Dao<Subscription, String> subscriptionDao = DaoManager.createDao(connectionSource, Subscription.class);

		final Subscription remizova = new Subscription();
		remizova.setEnabled(true);
		remizova.setFlickrId("50241002@N04");
		remizova.setId(0L);
		remizova.setName("Remizova");
		remizova.setType(SubscriptionType.PHOTOSTREAM);

		final CreateOrUpdateStatus createOrUpdate = subscriptionDao.createOrUpdate(remizova);
		if(createOrUpdate.isCreated()){
			Ln.i("Added subscription to remizova's photostream");
		} else if(createOrUpdate.isUpdated()){
			Ln.i("Updated subscription to remizova's photostream");
		} else {
			Ln.w("Neither created nor updated subscription to remizova's photostream!");
		}

	}

	@Override
	public void onUpgrade(final SQLiteDatabase database, final ConnectionSource connectionSource, final int oldVersion, final int newVersion) {
		//TODO run some migrations
	}


}
