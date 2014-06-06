package org.adorsys.adpharma.client.jpa.stockmovement;

import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.adorsys.javafx.crud.extensions.address.ServerAddress;
import org.adorsys.javafx.crud.extensions.login.ClientCookieFilter;

public class StockMovementService {
	private String media = MediaType.APPLICATION_JSON;
	private static final String FIND_BY = "findBy";
	private static final String FIND_BY_LIKE_PATH = "findByLike";

	@Inject
	private ClientCookieFilter clientCookieFilter;
	@Inject
	private ServerAddress serverAddress;
	Client client = ClientBuilder.newClient();

	private WebTarget target() {
		return client.target(serverAddress + "/rest/stockmovements").register(
				clientCookieFilter);
	}

	public StockMovement create(StockMovement entity) {
		Entity<StockMovement> eCopy = Entity.entity(entity, media);
		return target().request(media).post(eCopy, StockMovement.class);
	}

	// @DELETE
	// @Path("/{id:[0-9][0-9]*}")
	public StockMovement deleteById(Long id) {// @PathParam("id")
												// TODO encode id
		return target().path("" + id).request(media)
				.delete(StockMovement.class);
	}

	// @PUT
	// @Path("/{id:[0-9][0-9]*}")
	// @Consumes("application/xml")
	public StockMovement update(StockMovement entity) {
		Entity<StockMovement> ent = Entity.entity(entity, media);
		return target().path("" + entity.getId()).request(media)
				.put(ent, StockMovement.class);
	}

	// @GET
	// @Path("/{id:[0-9][0-9]*}")
	// @Produces("application/xml")
	public StockMovement findById(Long id) {// @PathParam("id")
		return target().path("" + id).request(media).get(StockMovement.class);
	}

	// @GET
	// @Produces("application/xml")
	public StockMovementSearchResult listAll() {
		return target().request(media).get(StockMovementSearchResult.class);
	}

	// @GET
	// @Produces("application/xml")
	public StockMovementSearchResult listAll(int start, int max) {
		return target().queryParam("start", start).queryParam("max", max)
				.request(media).get(StockMovementSearchResult.class);
	}

	// @GET
	// @Path("/count")
	public Long count() {
		return target().path("count").request().get(Long.class);
	}

	// @POST
	// @Produces("application/xml")
	// @Consumes("application/xml")
	public StockMovementSearchResult findBy(StockMovementSearchInput searchInput) {
		Entity<StockMovementSearchInput> searchInputEntity = Entity.entity(
				searchInput, media);
		return target().path(FIND_BY).request(media)
				.post(searchInputEntity, StockMovementSearchResult.class);
	}

	// @POST
	// @Path("/countBy")
	// @Consumes("application/xml")
	public Long countBy(StockMovementSearchInput searchInput) {
		Entity<StockMovementSearchInput> searchInputEntity = Entity.entity(
				searchInput, media);
		return target().path("countBy").request()
				.post(searchInputEntity, Long.class);
	}

	// @POST
	// @Path("/findByLike"
	// @Produces("application/xml")
	// @Consumes("application/xml")
	public StockMovementSearchResult findByLike(
			StockMovementSearchInput searchInput) {
		Entity<StockMovementSearchInput> searchInputEntity = Entity.entity(
				searchInput, media);
		return target().path(FIND_BY_LIKE_PATH).request(media)
				.post(searchInputEntity, StockMovementSearchResult.class);
	}

	// @POST
	// @Path("/countByLike"
	// @Consumes("application/xml")
	public Long countByLike(StockMovementSearchInput searchInput) {
		Entity<StockMovementSearchInput> searchInputEntity = Entity.entity(
				searchInput, media);
		return target().path("countByLike").request()
				.post(searchInputEntity, Long.class);
	}
}
