package org.adorsys.adpharma.client.jpa.permissionname;

import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.adorsys.javafx.crud.extensions.address.ServerAddress;
import org.adorsys.javafx.crud.extensions.login.ClientCookieFilter;

public class PermissionNameService {
	private String media = MediaType.APPLICATION_JSON;
	private static final String FIND_BY = "findBy";
	private static final String FIND_BY_LIKE_PATH = "findByLike";

	@Inject
	private ClientCookieFilter clientCookieFilter;
	@Inject
	private ServerAddress serverAddress;
	Client client = ClientBuilder.newClient();

	private WebTarget target() {
		return client.target(serverAddress + "/rest/permissionnames").register(
				clientCookieFilter);
	}

	public PermissionName create(PermissionName entity) {
		Entity<PermissionName> eCopy = Entity.entity(entity, media);
		return target().request(media).post(eCopy, PermissionName.class);
	}

	// @DELETE
	// @Path("/{id:[0-9][0-9]*}")
	public PermissionName deleteById(Long id) {// @PathParam("id")
												// TODO encode id
		return target().path("" + id).request(media).delete(PermissionName.class);
	}

	// @PUT
	// @Path("/{id:[0-9][0-9]*}")
	// @Consumes("application/xml")
	public PermissionName update(PermissionName entity) {
		Entity<PermissionName> ent = Entity.entity(entity, media);
		return target().path("" + entity.getId()).request(media)
				.put(ent, PermissionName.class);
	}

	// @GET
	// @Path("/{id:[0-9][0-9]*}")
	// @Produces("application/xml")
	public PermissionName findById(Long id) {// @PathParam("id")
		return target().path("" + id).request(media).get(PermissionName.class);
	}

	// @GET
	// @Produces("application/xml")
	public PermissionNameSearchResult listAll() {
		return target().request(media).get(PermissionNameSearchResult.class);
	}

	// @GET
	// @Produces("application/xml")
	public PermissionNameSearchResult listAll(int start, int max) {
		return target().queryParam("start", start).queryParam("max", max)
				.request(media).get(PermissionNameSearchResult.class);
	}

	// @GET
	// @Path("/count")
	public Long count() {
		return target().path("count").request().get(Long.class);
	}

	// @POST
	// @Produces("application/xml")
	// @Consumes("application/xml")
	public PermissionNameSearchResult findBy(
			PermissionNameSearchInput searchInput) {
		Entity<PermissionNameSearchInput> searchInputEntity = Entity.entity(
				searchInput, media);
		return target().path(FIND_BY).request(media)
				.post(searchInputEntity, PermissionNameSearchResult.class);
	}

	// @POST
	// @Path("/countBy")
	// @Consumes("application/xml")
	public Long countBy(PermissionNameSearchInput searchInput) {
		Entity<PermissionNameSearchInput> searchInputEntity = Entity.entity(
				searchInput, media);
		return target().path("countBy").request()
				.post(searchInputEntity, Long.class);
	}

	// @POST
	// @Path("/findByLike"
	// @Produces("application/xml")
	// @Consumes("application/xml")
	public PermissionNameSearchResult findByLike(
			PermissionNameSearchInput searchInput) {
		Entity<PermissionNameSearchInput> searchInputEntity = Entity.entity(
				searchInput, media);
		return target().path(FIND_BY_LIKE_PATH).request(media)
				.post(searchInputEntity, PermissionNameSearchResult.class);
	}

	// @POST
	// @Path("/countByLike"
	// @Consumes("application/xml")
	public Long countByLike(PermissionNameSearchInput searchInput) {
		Entity<PermissionNameSearchInput> searchInputEntity = Entity.entity(
				searchInput, media);
		return target().path("countByLike").request()
				.post(searchInputEntity, Long.class);
	}
}
