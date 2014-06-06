package org.adorsys.adpharma.client.jpa.rolename;

import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.adorsys.javafx.crud.extensions.address.ServerAddress;
import org.adorsys.javafx.crud.extensions.login.ClientCookieFilter;

public class RoleNameService {
	private String media = MediaType.APPLICATION_JSON;
	private static final String FIND_BY = "findBy";
	private static final String FIND_BY_LIKE_PATH = "findByLike";

	@Inject
	private ClientCookieFilter clientCookieFilter;
	@Inject
	private ServerAddress serverAddress;
	Client client = ClientBuilder.newClient();

	private WebTarget target() {
		return client.target(serverAddress + "/rest/rolenames").register(
				clientCookieFilter);
	}

	public RoleName create(RoleName entity) {
		Entity<RoleName> eCopy = Entity.entity(entity, media);
		return target().request(media).post(eCopy, RoleName.class);
	}

	// @DELETE
	// @Path("/{id:[0-9][0-9]*}")
	public RoleName deleteById(Long id) {// @PathParam("id")
											// TODO encode id
		return target().path("" + id).request(media).delete(RoleName.class);
	}

	// @PUT
	// @Path("/{id:[0-9][0-9]*}")
	// @Consumes("application/xml")
	public RoleName update(RoleName entity) {
		Entity<RoleName> ent = Entity.entity(entity, media);
		return target().path("" + entity.getId()).request(media)
				.put(ent, RoleName.class);
	}

	// @GET
	// @Path("/{id:[0-9][0-9]*}")
	// @Produces("application/xml")
	public RoleName findById(Long id) {// @PathParam("id")
		return target().path("" + id).request(media).get(RoleName.class);
	}

	// @GET
	// @Produces("application/xml")
	public RoleNameSearchResult listAll() {
		return target().request(media).get(RoleNameSearchResult.class);
	}

	// @GET
	// @Produces("application/xml")
	public RoleNameSearchResult listAll(int start, int max) {
		return target().queryParam("start", start).queryParam("max", max)
				.request(media).get(RoleNameSearchResult.class);
	}

	// @GET
	// @Path("/count")
	public Long count() {
		return target().path("count").request().get(Long.class);
	}

	// @POST
	// @Produces("application/xml")
	// @Consumes("application/xml")
	public RoleNameSearchResult findBy(RoleNameSearchInput searchInput) {
		Entity<RoleNameSearchInput> searchInputEntity = Entity.entity(
				searchInput, media);
		return target().path(FIND_BY).request(media)
				.post(searchInputEntity, RoleNameSearchResult.class);
	}

	// @POST
	// @Path("/countBy")
	// @Consumes("application/xml")
	public Long countBy(RoleNameSearchInput searchInput) {
		Entity<RoleNameSearchInput> searchInputEntity = Entity.entity(
				searchInput, media);
		return target().path("countBy").request()
				.post(searchInputEntity, Long.class);
	}

	// @POST
	// @Path("/findByLike"
	// @Produces("application/xml")
	// @Consumes("application/xml")
	public RoleNameSearchResult findByLike(RoleNameSearchInput searchInput) {
		Entity<RoleNameSearchInput> searchInputEntity = Entity.entity(
				searchInput, media);
		return target().path(FIND_BY_LIKE_PATH).request(media)
				.post(searchInputEntity, RoleNameSearchResult.class);
	}

	// @POST
	// @Path("/countByLike"
	// @Consumes("application/xml")
	public Long countByLike(RoleNameSearchInput searchInput) {
		Entity<RoleNameSearchInput> searchInputEntity = Entity.entity(
				searchInput, media);
		return target().path("countByLike").request()
				.post(searchInputEntity, Long.class);
	}
}
