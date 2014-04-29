package org.adorsys.adpharma.client.jpa.cashdrawer;

import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.adorsys.adpharma.client.utils.AdTimeFrameBasedSearchInput;
import org.adorsys.javafx.crud.extensions.address.ServerAddress;
import org.adorsys.javafx.crud.extensions.login.ClientCookieFilter;

public class CashDrawerService {
	private String media = MediaType.APPLICATION_JSON;
	private static final String FIND_BY = "findBy";
	private static final String FIND_BY_LIKE_PATH = "findByLike";
	private static final String CLOSE_PATH = "close";

	@Inject
	private ClientCookieFilter clientCookieFilter;

	Client client = ClientBuilder.newClient();
	@Inject
	private ServerAddress serverAddress;

	private WebTarget target() {
		return client.target(serverAddress + "/rest/cashdrawers").register(
				clientCookieFilter);
	}

	public CashDrawer create(CashDrawer entity) {
		Entity<CashDrawer> eCopy = Entity.entity(entity, media);
		return target().request(media).post(eCopy, CashDrawer.class);
	}

	// @DELETE
	// @Path("/{id:[0-9][0-9]*}")
	public CashDrawer deleteById(Long id) {// @PathParam("id")
											// TODO encode id
		return target().path("" + id).request(media).delete(CashDrawer.class);
	}

	// @PUT
	// @Path("/{id:[0-9][0-9]*}")
	// @Consumes("application/xml")
	public CashDrawer update(CashDrawer entity) {
		Entity<CashDrawer> ent = Entity.entity(entity, media);
		return target().path("" + entity.getId()).request(media)
				.put(ent, CashDrawer.class);
	}

	// @PUT
	// @Path("/close/{id:[0-9][0-9]*}")
	// @Produces({ "application/json", "application/xml" })
	// @Consumes({ "application/json", "application/xml" })
	public CashDrawer close(CashDrawer entity) {
		Entity<CashDrawer> ent = Entity.entity(entity, media);
		return target().path(CLOSE_PATH + "/" + entity.getId()).request(media)
				.put(ent, CashDrawer.class);
	}

	// @GET
	// @Path("/{id:[0-9][0-9]*}")
	// @Produces("application/xml")
	public CashDrawer findById(Long id) {// @PathParam("id")
		return target().path("" + id).request(media).get(CashDrawer.class);
	}

	// @GET
	// @Path("/myOpenDrawers")
	// @Produces({ "application/json", "application/xml" })
	public CashDrawerSearchResult myOpenDrawers() {
		return target().path("/myOpenDrawers").request(media)
				.get(CashDrawerSearchResult.class);
	}

	// @GET
	// @Path("/agencyDrawers")
	// @Produces({ "application/json", "application/xml" })
	public CashDrawerSearchResult agencyDrawers() {
		return target().path("/agencyDrawers").request(media)
				.get(CashDrawerSearchResult.class);
	}

	// @GET
	// @Produces("application/xml")
	public CashDrawerSearchResult listAll() {
		return target().request(media).get(CashDrawerSearchResult.class);
	}

	// @GET
	// @Produces("application/xml")
	public CashDrawerSearchResult listAll(int start, int max) {
		return target().queryParam("start", start).queryParam("max", max)
				.request(media).get(CashDrawerSearchResult.class);
	}

	// @GET
	// @Path("/count")
	public Long count() {
		return target().path("count").request().get(Long.class);
	}

	// @POST
	// @Produces("application/xml")
	// @Consumes("application/xml")
	public CashDrawerSearchResult findBy(CashDrawerSearchInput searchInput) {
		Entity<CashDrawerSearchInput> searchInputEntity = Entity.entity(
				searchInput, media);
		return target().path(FIND_BY).request(media)
				.post(searchInputEntity, CashDrawerSearchResult.class);
	}

	// @POST
	// @Path("/countBy")
	// @Consumes("application/xml")
	public Long countBy(CashDrawerSearchInput searchInput) {
		Entity<CashDrawerSearchInput> searchInputEntity = Entity.entity(
				searchInput, media);
		return target().path("countBy").request()
				.post(searchInputEntity, Long.class);
	}

	// @POST
	// @Path("/findByLike"
	// @Produces("application/xml")
	// @Consumes("application/xml")
	public CashDrawerSearchResult findByLike(CashDrawerSearchInput searchInput) {
		Entity<CashDrawerSearchInput> searchInputEntity = Entity.entity(
				searchInput, media);
		return target().path(FIND_BY_LIKE_PATH).request(media)
				.post(searchInputEntity, CashDrawerSearchResult.class);
	}

	// @POST
	// @Path("/countByLike"
	// @Consumes("application/xml")
	public Long countByLike(CashDrawerSearchInput searchInput) {
		Entity<CashDrawerSearchInput> searchInputEntity = Entity.entity(
				searchInput, media);
		return target().path("countByLike").request()
				.post(searchInputEntity, Long.class);
	}


	// @POST
	// @Path("/findByClosingDateBetween"
	// @Produces("application/xml")
	// @Consumes("application/xml")
	public CashDrawerSearchResult findByClosingDateBetween(AdTimeFrameBasedSearchInput searchInput) {
		Entity<AdTimeFrameBasedSearchInput> searchInputEntity = Entity.entity(
				searchInput, media);
		return target().path("findByClosingDateBetween").request(media)
				.post(searchInputEntity, CashDrawerSearchResult.class);
	}
}
