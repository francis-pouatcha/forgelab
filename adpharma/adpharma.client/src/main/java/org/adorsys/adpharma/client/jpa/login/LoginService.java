package org.adorsys.adpharma.client.jpa.login;

import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.adorsys.javafx.crud.extensions.address.ServerAddress;
import org.adorsys.javafx.crud.extensions.login.ClientCookieFilter;

public class LoginService {
	private String media = MediaType.APPLICATION_JSON;
	private static final String FIND_BY = "findBy";
	private static final String FIND_BY_LIKE_PATH = "findByLike";

	Client client = ClientBuilder.newClient();
	@Inject
	private ServerAddress serverAddress;
	@Inject
	private ClientCookieFilter clientCookieFilter;

	private WebTarget target() {
		return client.target(serverAddress + "/rest/logins").register(
				clientCookieFilter);
	}

	public Login create(Login entity) {
		Entity<Login> eCopy = Entity.entity(entity, media);
		return target().request(media).post(eCopy, Login.class);
	}

	// @DELETE
	// @Path("/{id:[0-9][0-9]*}")
	public Login deleteById(Long id) {// @PathParam("id")
										// TODO encode id
		return target().path("" + id).request(media).delete(Login.class);
	}

	// @PUT
	// @Path("/{id:[0-9][0-9]*}")
	// @Consumes("application/xml")
	public Login update(Login entity) {
		Entity<Login> ent = Entity.entity(entity, media);
		return target().path("" + entity.getId()).request(media)
				.put(ent, Login.class);
	}

	// @GET
	// @Path("/{id:[0-9][0-9]*}")
	// @Produces("application/xml")
	public Login findById(Long id) {// @PathParam("id")
		return target().path("" + id).request(media).get(Login.class);
	}

	// @GET
	// @Produces("application/xml")
	public LoginSearchResult listAll() {
		return target().request(media).get(LoginSearchResult.class);
	}

	// @GET
	// @Produces("application/xml")
	public LoginSearchResult listAll(int start, int max) {
		return target().queryParam("start", start).queryParam("max", max)
				.request(media).get(LoginSearchResult.class);
	}

	// @GET
	// @Path("/count")
	public Long count() {
		return target().path("count").request().get(Long.class);
	}

	// @POST
	// @Produces("application/xml")
	// @Consumes("application/xml")
	public LoginSearchResult findBy(LoginSearchInput searchInput) {
		Entity<LoginSearchInput> searchInputEntity = Entity.entity(searchInput,
				media);
		return target().path(FIND_BY).request(media)
				.post(searchInputEntity, LoginSearchResult.class);
	}

	// @POST
	// @Path("/countBy")
	// @Consumes("application/xml")
	public Long countBy(LoginSearchInput searchInput) {
		Entity<LoginSearchInput> searchInputEntity = Entity.entity(searchInput,
				media);
		return target().path("countBy").request()
				.post(searchInputEntity, Long.class);
	}

	// @POST
	// @Path("/findByLike"
	// @Produces("application/xml")
	// @Consumes("application/xml")
	public LoginSearchResult findByLike(LoginSearchInput searchInput) {
		Entity<LoginSearchInput> searchInputEntity = Entity.entity(searchInput,
				media);
		return target().path(FIND_BY_LIKE_PATH).request(media)
				.post(searchInputEntity, LoginSearchResult.class);
	}

	// @POST
	// @Path("/countByLike"
	// @Consumes("application/xml")
	public Long countByLike(LoginSearchInput searchInput) {
		Entity<LoginSearchInput> searchInputEntity = Entity.entity(searchInput,
				media);
		return target().path("countByLike").request()
				.post(searchInputEntity, Long.class);
	}
}
