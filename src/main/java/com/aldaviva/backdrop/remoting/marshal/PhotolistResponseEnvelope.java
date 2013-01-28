package com.aldaviva.backdrop.remoting.marshal;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;

@Root(name="rsp", strict=false)
public class PhotolistResponseEnvelope {

	@Attribute(name="total") @Path("photos") private int count;

	@Element @Path("photos") private PhotoEnvelope photo;

	@Root(name="photo", strict=false)
	public static class PhotoEnvelope {

		@Attribute private long id;
		@Attribute private int farm;
		@Attribute private int server;
		@Attribute private String secret;
		@Attribute private String title;
		@Attribute(required=false) private String owner;
		@Attribute(required=false) private String ownername;

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

		public String getOwner() {
			return owner;
		}

		public void setOwner(final String owner) {
			this.owner = owner;
		}

		public String getOwnername() {
			return ownername;
		}

		public void setOwnername(final String ownername) {
			this.ownername = ownername;
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
