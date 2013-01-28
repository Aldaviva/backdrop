package com.aldaviva.backdrop.service.impl;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import com.aldaviva.backdrop.Modules;
import com.aldaviva.backdrop.data.entity.Subscription;
import com.aldaviva.backdrop.remoting.impl.PhotostreamClient;
import com.aldaviva.backdrop.remoting.marshal.PhotostreamResponseEnvelope;
import com.aldaviva.backdrop.remoting.marshal.PhotostreamResponseEnvelope.PhotoEnvelope;

import java.io.StringWriter;

import org.jukito.JukitoModule;
import org.jukito.JukitoRunner;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import com.google.inject.Inject;

@RunWith(JukitoRunner.class)
public class PhotostreamClientTest {

	public static class TestModule extends JukitoModule {

		@Override
		protected void configureTest() {
			install(new Modules());
		}
	}

	@Inject private PhotostreamClient client;

	@Test @Ignore
	public void testGetCount(){
		final Subscription sub = new Subscription();
		sub.setFlickrId("50241002@N04"); //remizova
		final int actual = client.getCount(sub);
		final int expected = 2066;
		assertEquals(expected, actual);
	}

	@Test
	public void testDeserialize() throws Exception{
		final Serializer serializer = new Persister();
		final PhotostreamResponseEnvelope actual = serializer.read(PhotostreamResponseEnvelope.class, "<?xml version=\"1.0\" encoding=\"utf-8\" ?>"+
				"<rsp stat=\"ok\">"+
				"  <photos page=\"1\" pages=\"2066\" perpage=\"1\" total=\"2066\">"+
				"    <photo id=\"8420431125\" owner=\"50241002@N04\" secret=\"be18df0ca9\" server=\"8517\" farm=\"9\" title=\"Lincoln\" ispublic=\"1\" isfriend=\"0\" isfamily=\"0\" />"+
				"  </photos>"+
				"</rsp>");
		final PhotostreamResponseEnvelope expected = new PhotostreamResponseEnvelope();
		expected.setCount(2066);
		final PhotoEnvelope expectedPhoto = new PhotoEnvelope();
		expectedPhoto.setFarm(9);
		expectedPhoto.setId(8420431125L);
		expectedPhoto.setSecret("be18df0ca9");
		expectedPhoto.setServer(8517);
		expectedPhoto.setTitle("Lincoln");
		expected.setPhoto(expectedPhoto);
		assertEquals(expected.getCount(), actual.getCount());
		assertThat(actual.getPhoto(), samePropertyValuesAs(expectedPhoto));
	}

	@Test
	public void testSerialize() throws Exception{
		final Serializer serializer = new Persister();

		final PhotostreamResponseEnvelope env = new PhotostreamResponseEnvelope();
		env.setCount(2066);
		final PhotoEnvelope expectedPhoto = new PhotoEnvelope();
		expectedPhoto.setFarm(9);
		expectedPhoto.setId(8420431125L);
		expectedPhoto.setSecret("be18df0ca9");
		expectedPhoto.setServer(8517);
		expectedPhoto.setTitle("Lincoln");
		env.setPhoto(expectedPhoto);
		final StringWriter writer = new StringWriter();
		serializer.write(env, writer);
		System.out.println(writer.toString());
	}
}
