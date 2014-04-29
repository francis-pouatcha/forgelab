package org.adorsys.adpharma.client.jpa.insurrance;

import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.adorsys.javafx.crud.extensions.address.ServerAddress;
import org.adorsys.javafx.crud.extensions.login.ClientCookieFilter;

public class InsurranceService {
	private String media = MediaType.APPLICATION_JSON;
	private static final String FIND_BY = "findBy";
	private static final String FIND_BY_LIKE_PATH = "findByLike";

	@Inject
	private ClientCookieFilter clientCookieFilter;
	@Inject
	private ServerAddress serverAddress;
	Client client = ClientBuilder.newClient();

	private WebTarget target() {
		return client.target(serverAddress + "/rest/insurrances").register(
				clientCookieFilter);
	}

	public Insurrance create(Insurrance entity) {
		Entity<Insurrance> eCopy = Entity.entity(entity, media);
		return target().request(media).post(eCopy, Insurrance.class);
	}

	// @DELETE
	// @Path("/{id:[0-9][0-9]*}")
	public Insurrance deleteById(Long id) {// @PathParam("id")
											// TODO encode id
		return target().path("" + id).request(media).delete(Insurrance.class);
	}

	// @PUT
	// @Path("/{id:[0-9][0-9]*}")
	// @Consumes("application/xml")
	public Insurrance update(Insurrance entity) {
		Entity<Insurrance> ent = Entity.entity(entity, media);
		return target().path("" + entity.getId()).request(media)
				.put(ent, Insurrance.class);
	}

	// @GET
	// @Path("/{id:[0-9][0-9]*}")
	// @Produces("application/xml")
	public Insurrance findById(Long id) {// @PathParam("id")
		return target().path("" + id).request(media).get(Insurrance.class);
	}

	// @GET
	// @Produces("application/xml")
	public InsurranceSearchResult listAll() {
		return target().request(media).get(InsurranceSearchResult.class);
	}

	// @GET
	// @Produces("application/xml")
	public InsurranceSearchResult listAll(int start, int max) {
		return target().queryParam("start", start).queryParam("max", max)
				.request(media).get(InsurranceSearchResult.class);
	}

	// @GET
	// @Path("/count")
	public Long count() {
		return target().path("count").request().get(Long.class);
	}

	// @POST
	// @Produces("application/xml")
	// @Consumes("application/xml")
	public InsurranceSearchResult findBy(InsurranceSearchInput searchInput) {
		Entity<InsurranceSearchInput> searchInputEntity = Entity.entity(
				searchInput, media);
		return target().path(FIND_BY).request(media)
				.post(searchInputEntity, InsurranceSearchResult.class);
	}

	// @POST
	// @Path("/countBy")
	// @Consumes("application/xml")
	public Long countBy(InsurranceSearchInput searchInput) {
		Entity<InsurranceSearchInput> searchInputEntity = Entity.entity(
				searchInput, media);
		return target().path("countBy").request()
				.post(searchInputEntity, Long.class);
	}

	// @POST
	// @Path("/findByLike"
	// @Produces("application/xml")
	// @Consumes("application/xml")
	public InsurranceSearchResult findByLike(InsurranceSearchInput searchInput) {
		Entity<InsurranceSearchInput> searchInputEntity = Entity.entity(
				searchInput, media);
		return target().path(FIND_BY_LIKE_PATH).request(media)
				.post(searchInputEntity, InsurranceSearchResult.class);
	}

	// @POST
	// @Path("/countByLike"
	// @Consumes("application/xml")
	public Long countByLike(InsurranceSearchInput searchInput) {
		Entity<InsurranceSearchInput> searchInputEntity = Entity.entity(
				searchInput, media);
		return target().path("countByLike").request()
				.post(searchInputEntity, Long.class);
	}
}
