package org.adorsys.adpharma.client.jpa.currency;

import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.adorsys.javafx.crud.extensions.address.ServerAddress;
import org.adorsys.javafx.crud.extensions.login.ClientCookieFilter;

public class CurrencyService {
	private String media = MediaType.APPLICATION_JSON;
	private static final String FIND_BY = "findBy";
	private static final String FIND_BY_LIKE_PATH = "findByLike";

	@Inject
	private ClientCookieFilter clientCookieFilter;
	@Inject
	private ServerAddress serverAddress;
	Client client = ClientBuilder.newClient();

	private WebTarget target() {
		return client.target(serverAddress + "/rest/currencys").register(
				clientCookieFilter);
	}

	public Currency create(Currency entity) {
		Entity<Currency> eCopy = Entity.entity(entity, media);
		return target().request(media).post(eCopy, Currency.class);
	}

	// @DELETE
	// @Path("/{id:[0-9][0-9]*}")
	public Currency deleteById(Long id) {// @PathParam("id")
											// TODO encode id
		return target().path("" + id).request(media).delete(Currency.class);
	}

	// @PUT
	// @Path("/{id:[0-9][0-9]*}")
	// @Consumes("application/xml")
	public Currency update(Currency entity) {
		Entity<Currency> ent = Entity.entity(entity, media);
		return target().path("" + entity.getId()).request(media)
				.put(ent, Currency.class);
	}

	// @GET
	// @Path("/{id:[0-9][0-9]*}")
	// @Produces("application/xml")
	public Currency findById(Long id) {// @PathParam("id")
		return target().path("" + id).request(media).get(Currency.class);
	}

	// @GET
	// @Produces("application/xml")
	public CurrencySearchResult listAll() {
		return target().request(media).get(CurrencySearchResult.class);
	}

	// @GET
	// @Produces("application/xml")
	public CurrencySearchResult listAll(int start, int max) {
		return target().queryParam("start", start).queryParam("max", max)
				.request(media).get(CurrencySearchResult.class);
	}

	// @GET
	// @Path("/count")
	public Long count() {
		return target().path("count").request().get(Long.class);
	}

	// @POST
	// @Produces("application/xml")
	// @Consumes("application/xml")
	public CurrencySearchResult findBy(CurrencySearchInput searchInput) {
		Entity<CurrencySearchInput> searchInputEntity = Entity.entity(
				searchInput, media);
		return target().path(FIND_BY).request(media)
				.post(searchInputEntity, CurrencySearchResult.class);
	}

	// @POST
	// @Path("/countBy")
	// @Consumes("application/xml")
	public Long countBy(CurrencySearchInput searchInput) {
		Entity<CurrencySearchInput> searchInputEntity = Entity.entity(
				searchInput, media);
		return target().path("countBy").request()
				.post(searchInputEntity, Long.class);
	}

	// @POST
	// @Path("/findByLike"
	// @Produces("application/xml")
	// @Consumes("application/xml")
	public CurrencySearchResult findByLike(CurrencySearchInput searchInput) {
		Entity<CurrencySearchInput> searchInputEntity = Entity.entity(
				searchInput, media);
		return target().path(FIND_BY_LIKE_PATH).request(media)
				.post(searchInputEntity, CurrencySearchResult.class);
	}

	// @POST
	// @Path("/countByLike"
	// @Consumes("application/xml")
	public Long countByLike(CurrencySearchInput searchInput) {
		Entity<CurrencySearchInput> searchInputEntity = Entity.entity(
				searchInput, media);
		return target().path("countByLike").request()
				.post(searchInputEntity, Long.class);
	}
}
