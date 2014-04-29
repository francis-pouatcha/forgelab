package org.adorsys.adpharma.client.jpa.warehouse;

import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.adorsys.javafx.crud.extensions.address.ServerAddress;
import org.adorsys.javafx.crud.extensions.login.ClientCookieFilter;

public class WareHouseService {
	private String media = MediaType.APPLICATION_JSON;
	private static final String FIND_BY = "findBy";
	private static final String FIND_BY_LIKE_PATH = "findByLike";

	@Inject
	private ClientCookieFilter clientCookieFilter;
	Client client = ClientBuilder.newClient();
	@Inject
	private ServerAddress serverAddress;

	private WebTarget target() {
		return client.target(serverAddress + "/rest/warehouses").register(
				clientCookieFilter);
	}

	public WareHouse create(WareHouse entity) {
		Entity<WareHouse> eCopy = Entity.entity(entity, media);
		return target().request(media).post(eCopy, WareHouse.class);
	}

	// @DELETE
	// @Path("/{id:[0-9][0-9]*}")
	public WareHouse deleteById(Long id) {// @PathParam("id")
											// TODO encode id
		return target().path("" + id).request(media).delete(WareHouse.class);
	}

	// @PUT
	// @Path("/{id:[0-9][0-9]*}")
	// @Consumes("application/xml")
	public WareHouse update(WareHouse entity) {
		Entity<WareHouse> ent = Entity.entity(entity, media);
		return target().path("" + entity.getId()).request(media)
				.put(ent, WareHouse.class);
	}

	// @GET
	// @Path("/{id:[0-9][0-9]*}")
	// @Produces("application/xml")
	public WareHouse findById(Long id) {// @PathParam("id")
		return target().path("" + id).request(media).get(WareHouse.class);
	}

	// @GET
	// @Produces("application/xml")
	public WareHouseSearchResult listAll() {
		return target().request(media).get(WareHouseSearchResult.class);
	}

	// @GET
	// @Produces("application/xml")
	public WareHouseSearchResult listAll(int start, int max) {
		return target().queryParam("start", start).queryParam("max", max)
				.request(media).get(WareHouseSearchResult.class);
	}

	// @GET
	// @Path("/count")
	public Long count() {
		return target().path("count").request().get(Long.class);
	}

	// @POST
	// @Produces("application/xml")
	// @Consumes("application/xml")
	public WareHouseSearchResult findBy(WareHouseSearchInput searchInput) {
		Entity<WareHouseSearchInput> searchInputEntity = Entity.entity(
				searchInput, media);
		return target().path(FIND_BY).request(media)
				.post(searchInputEntity, WareHouseSearchResult.class);
	}

	// @POST
	// @Path("/countBy")
	// @Consumes("application/xml")
	public Long countBy(WareHouseSearchInput searchInput) {
		Entity<WareHouseSearchInput> searchInputEntity = Entity.entity(
				searchInput, media);
		return target().path("countBy").request()
				.post(searchInputEntity, Long.class);
	}

	// @POST
	// @Path("/findByLike"
	// @Produces("application/xml")
	// @Consumes("application/xml")
	public WareHouseSearchResult findByLike(WareHouseSearchInput searchInput) {
		Entity<WareHouseSearchInput> searchInputEntity = Entity.entity(
				searchInput, media);
		return target().path(FIND_BY_LIKE_PATH).request(media)
				.post(searchInputEntity, WareHouseSearchResult.class);
	}

	// @POST
	// @Path("/countByLike"
	// @Consumes("application/xml")
	public Long countByLike(WareHouseSearchInput searchInput) {
		Entity<WareHouseSearchInput> searchInputEntity = Entity.entity(
				searchInput, media);
		return target().path("countByLike").request()
				.post(searchInputEntity, Long.class);
	}
}
