package org.adorsys.adpharma.client.access;

import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.adorsys.javafx.crud.extensions.address.ServerAddress;
import org.adorsys.javafx.crud.extensions.login.ClientCookieFilter;

public class PermsService {
	private String media = MediaType.APPLICATION_JSON;

	@Inject
	private ClientCookieFilter clientCookieFilter;
	Client client = ClientBuilder.newClient();
	@Inject
	private ServerAddress serverAddress;

	private WebTarget target() {
		return client.target(serverAddress + "/rest/perms").register(
				clientCookieFilter);
	}

	public String loadDCs(String loginName) {
		Entity<String> eCopy = Entity.entity(loginName, media);
		return target().request(media).post(eCopy, String.class);
	}
}
