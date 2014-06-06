package org.adorsys.adpharma.client.jpa.rolenamepermissionnameassoc;

import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.adorsys.javafx.crud.extensions.address.ServerAddress;
import org.adorsys.javafx.crud.extensions.login.ClientCookieFilter;

public class RoleNamePermissionNameAssocService {
	private String media = MediaType.APPLICATION_JSON;
	private static final String FIND_BY = "findBy";
	private static final String FIND_BY_LIKE_PATH = "findByLike";

	@Inject
	private ClientCookieFilter clientCookieFilter;
	@Inject
	private ServerAddress serverAddress;
	Client client = ClientBuilder.newClient();

	private WebTarget target() {
		return client.target(
				serverAddress + "/rest/rolenamepermissionnameassocs").register(
				clientCookieFilter);
	}

	public RoleNamePermissionNameAssoc create(RoleNamePermissionNameAssoc entity) {
		Entity<RoleNamePermissionNameAssoc> eCopy = Entity
				.entity(entity, media);
		return target().request(media).post(eCopy,
				RoleNamePermissionNameAssoc.class);
	}

	// @DELETE
	// @Path("/{id:[0-9][0-9]*}")
	public RoleNamePermissionNameAssoc deleteById(Long id) {// @PathParam("id")
															// TODO encode id
		return target().path("" + id).request(media)
				.delete(RoleNamePermissionNameAssoc.class);
	}

	// @PUT
	// @Path("/{id:[0-9][0-9]*}")
	// @Consumes("application/xml")
	public RoleNamePermissionNameAssoc update(RoleNamePermissionNameAssoc entity) {
		Entity<RoleNamePermissionNameAssoc> ent = Entity.entity(entity, media);
		return target().path("" + entity.getId()).request(media)
				.put(ent, RoleNamePermissionNameAssoc.class);
	}

	// @GET
	// @Path("/{id:[0-9][0-9]*}")
	// @Produces("application/xml")
	public RoleNamePermissionNameAssoc findById(Long id) {// @PathParam("id")
		return target().path("" + id).request(media)
				.get(RoleNamePermissionNameAssoc.class);
	}

	// @GET
	// @Produces("application/xml")
	public RoleNamePermissionNameAssocSearchResult listAll() {
		return target().request(media).get(
				RoleNamePermissionNameAssocSearchResult.class);
	}

	// @GET
	// @Produces("application/xml")
	public RoleNamePermissionNameAssocSearchResult listAll(int start, int max) {
		return target().queryParam("start", start).queryParam("max", max)
				.request(media)
				.get(RoleNamePermissionNameAssocSearchResult.class);
	}

	// @GET
	// @Path("/count")
	public Long count() {
		return target().path("count").request().get(Long.class);
	}

	// @POST
	// @Produces("application/xml")
	// @Consumes("application/xml")
	public RoleNamePermissionNameAssocSearchResult findBy(
			RoleNamePermissionNameAssocSearchInput searchInput) {
		Entity<RoleNamePermissionNameAssocSearchInput> searchInputEntity = Entity
				.entity(searchInput, media);
		return target()
				.path(FIND_BY)
				.request(media)
				.post(searchInputEntity,
						RoleNamePermissionNameAssocSearchResult.class);
	}

	// @POST
	// @Path("/countBy")
	// @Consumes("application/xml")
	public Long countBy(RoleNamePermissionNameAssocSearchInput searchInput) {
		Entity<RoleNamePermissionNameAssocSearchInput> searchInputEntity = Entity
				.entity(searchInput, media);
		return target().path("countBy").request()
				.post(searchInputEntity, Long.class);
	}

	// @POST
	// @Path("/findByLike"
	// @Produces("application/xml")
	// @Consumes("application/xml")
	public RoleNamePermissionNameAssocSearchResult findByLike(
			RoleNamePermissionNameAssocSearchInput searchInput) {
		Entity<RoleNamePermissionNameAssocSearchInput> searchInputEntity = Entity
				.entity(searchInput, media);
		return target()
				.path(FIND_BY_LIKE_PATH)
				.request(media)
				.post(searchInputEntity,
						RoleNamePermissionNameAssocSearchResult.class);
	}

	// @POST
	// @Path("/countByLike"
	// @Consumes("application/xml")
	public Long countByLike(RoleNamePermissionNameAssocSearchInput searchInput) {
		Entity<RoleNamePermissionNameAssocSearchInput> searchInputEntity = Entity
				.entity(searchInput, media);
		return target().path("countByLike").request()
				.post(searchInputEntity, Long.class);
	}
}
