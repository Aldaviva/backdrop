package com.aldaviva.backdrop.remoting.marshal;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;

@Root(name="rsp", strict=false)
public class PhotostreamResponseEnvelope {

	@Attribute(name="total") @Path("photos") private int count;

	@Element @Path("photos") private PhotoEnvelope photo;

	@Root(name="photo", strict=false)
	public static class PhotoEnvelope {

		@Attribute private long id;
		@Attribute private int farm;
		@Attribute private int server;
		@Attribute private String secret;
		@Attribute private String title;

		public long getId() {
			return id;
		}

		public void setId(final long id) {
			this.id = id;
		}

		public int getFarm() {
			return farm;
		}

		public void setFarm(final int farm) {
			this.farm = farm;
		}

		public int getServer() {
			return server;
		}

		public void setServer(final int server) {
			this.server = server;
		}

		public String getSecret() {
			return secret;
		}

		public void setSecret(final String secret) {
			this.secret = secret;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(final String title) {
			this.title = title;
		}
	}

	public int getCount() {
		return count;
	}

	public void setCount(final int count) {
		this.count = count;
	}

	public PhotoEnvelope getPhoto() {
		return photo;
	}

	public void setPhoto(final PhotoEnvelope photo) {
		this.photo = photo;
	}

}
