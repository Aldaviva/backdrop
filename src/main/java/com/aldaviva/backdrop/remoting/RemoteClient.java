package com.aldaviva.backdrop.remoting;

import com.aldaviva.backdrop.data.entity.Photo;
import com.aldaviva.backdrop.data.entity.Subscription;

public interface RemoteClient {

	Photo getRandomPhoto(Subscription subscription);

}
