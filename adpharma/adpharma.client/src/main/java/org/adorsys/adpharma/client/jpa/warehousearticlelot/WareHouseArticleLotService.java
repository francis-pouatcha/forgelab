package org.adorsys.adpharma.client.jpa.warehousearticlelot;

import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.adorsys.adpharma.client.jpa.articlelot.ArticleLotTransferManager;
import org.adorsys.javafx.crud.extensions.address.ServerAddress;
import org.adorsys.javafx.crud.extensions.login.ClientCookieFilter;

public class WareHouseArticleLotService {
	private String media = MediaType.APPLICATION_JSON;
	private static final String FIND_BY = "findBy";
	private static final String FIND_BY_LIKE_PATH = "findByLike";

	@Inject
	private ClientCookieFilter clientCookieFilter;
	Client client = ClientBuilder.newClient();
	@Inject
	private ServerAddress serverAddress;

	private WebTarget target() {
		return client.target(serverAddress + "/rest/warehousearticlelots")
				.register(clientCookieFilter);
	}

	public WareHouseArticleLot create(WareHouseArticleLot entity) {
		Entity<WareHouseArticleLot> eCopy = Entity.entity(entity, media);
		return target().request(media).post(eCopy, WareHouseArticleLot.class);
	}

	// @DELETE
	// @Path("/{id:[0-9][0-9]*}")
	public WareHouseArticleLot deleteById(Long id) {// @PathParam("id")
													// TODO encode id
		return target().path("" + id).request(media)
				.delete(WareHouseArticleLot.class);
	}

	// @PUT
	// @Path("/{id:[0-9][0-9]*}")
	// @Consumes("application/xml")
	public WareHouseArticleLot update(WareHouseArticleLot entity) {
		Entity<WareHouseArticleLot> ent = Entity.entity(entity, media);
		return target().path("" + entity.getId()).request(media)
				.put(ent, WareHouseArticleLot.class);
	}

	// @GET
	// @Path("/{id:[0-9][0-9]*}")
	// @Produces("application/xml")
	public WareHouseArticleLot findById(Long id) {// @PathParam("id")
		return target().path("" + id).request(media)
				.get(WareHouseArticleLot.class);
	}

	// @GET
	// @Produces("application/xml")
	public WareHouseArticleLotSearchResult listAll() {
		return target().request(media).get(
				WareHouseArticleLotSearchResult.class);
	}

	// @GET
	// @Produces("application/xml")
	public WareHouseArticleLotSearchResult listAll(int start, int max) {
		return target().queryParam("start", start).queryParam("max", max)
				.request(media).get(WareHouseArticleLotSearchResult.class);
	}

	// @GET
	// @Path("/count")
	public Long count() {
		return target().path("count").request().get(Long.class);
	}

	// @POST
	// @Produces("application/xml")
	// @Consumes("application/xml")
	public WareHouseArticleLotSearchResult findBy(
			WareHouseArticleLotSearchInput searchInput) {
		Entity<WareHouseArticleLotSearchInput> searchInputEntity = Entity
				.entity(searchInput, media);
		return target().path(FIND_BY).request(media)
				.post(searchInputEntity, WareHouseArticleLotSearchResult.class);
	}

	// @POST
	// @Path("/countBy")
	// @Consumes("application/xml")
	public Long countBy(WareHouseArticleLotSearchInput searchInput) {
		Entity<WareHouseArticleLotSearchInput> searchInputEntity = Entity
				.entity(searchInput, media);
		return target().path("countBy").request()
				.post(searchInputEntity, Long.class);
	}


	// @POST
	// @Path("/processDestocking")
	// @Consumes("application/xml")
	public WareHouseArticleLot processDestocking(
			ArticleLotTransferManager transferManager) {
		Entity<ArticleLotTransferManager> entity = Entity.entity(
				transferManager, media);
		return target().path("processDestocking").request()
				.post(entity, WareHouseArticleLot.class);
	}

	// @POST
	// @Path("/findByLike"
	// @Produces("application/xml")
	// @Consumes("application/xml")
	public WareHouseArticleLotSearchResult findByLike(
			WareHouseArticleLotSearchInput searchInput) {
		Entity<WareHouseArticleLotSearchInput> searchInputEntity = Entity
				.entity(searchInput, media);
		return target().path(FIND_BY_LIKE_PATH).request(media)
				.post(searchInputEntity, WareHouseArticleLotSearchResult.class);
	}

	// @POST
	// @Path("/countByLike"
	// @Consumes("application/xml")
	public Long countByLike(WareHouseArticleLotSearchInput searchInput) {
		Entity<WareHouseArticleLotSearchInput> searchInputEntity = Entity
				.entity(searchInput, media);
		return target().path("countByLike").request()
				.post(searchInputEntity, Long.class);
	}
}
