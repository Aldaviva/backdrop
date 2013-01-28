package com.aldaviva.backdrop.service;

import com.aldaviva.backdrop.data.entity.Photo;
import com.aldaviva.backdrop.data.entity.Subscription;

public interface SubscriptionService {

	Photo getRandomPhoto();

	Photo getRandomPhoto(Subscription subscription);

	Subscription getRandomSubscription();

}
