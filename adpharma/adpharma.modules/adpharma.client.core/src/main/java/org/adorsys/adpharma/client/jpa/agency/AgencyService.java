package org.adorsys.adpharma.client.jpa.agency;

import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.adorsys.javafx.crud.extensions.address.ServerAddress;
import org.adorsys.javafx.crud.extensions.login.ClientCookieFilter;

public class AgencyService {
	private String media = MediaType.APPLICATION_JSON;
	private static final String FIND_BY = "findBy";
	private static final String FIND_BY_LIKE_PATH = "findByLike";

	@Inject
	private ClientCookieFilter clientCookieFilter;
	@Inject
	private ServerAddress serverAddress;

	Client client = ClientBuilder.newClient();
	public AgencyService() {

	}
	private WebTarget target(){
		return client.target(serverAddress + "/rest/agencys").register(clientCookieFilter);
	}

	public Agency create(Agency entity) {
		Entity<Agency> eCopy = Entity.entity(entity, media);
		return target().request(media).post(eCopy, Agency.class);
	}

	// @DELETE
	// @Path("/{id:[0-9][0-9]*}")
	public Agency deleteById(Long id) {// @PathParam("id")
										// TODO encode id
		return target().path("" + id).request(media).delete(Agency.class);
	}

	// @PUT
	// @Path("/{id:[0-9][0-9]*}")
	// @Consumes("application/xml")
	public Agency update(Agency entity) {
		Entity<Agency> ent = Entity.entity(entity, media);
		return target().path("" + entity.getId()).request(media)
				.put(ent, Agency.class);
	}

	// @GET
	// @Path("/{id:[0-9][0-9]*}")
	// @Produces("application/xml")
	public Agency findById(Long id) {// @PathParam("id")
		return target().path("" + id).request(media).get(Agency.class);
	}

	// @GET
	// @Produces("application/xml")
	public AgencySearchResult listAll() {
		return target().request(media).get(AgencySearchResult.class);
	}

	// @GET
	// @Produces("application/xml")
	public AgencySearchResult listAll(int start, int max) {
		return target().queryParam("start", start).queryParam("max", max)
				.request(media).get(AgencySearchResult.class);
	}

	// @GET
	// @Path("/count")
	public Long count() {
		return target().path("count").request().get(Long.class);
	}

	// @POST
	// @Produces("application/xml")
	// @Consumes("application/xml")
	public AgencySearchResult findBy(AgencySearchInput searchInput) {
		Entity<AgencySearchInput> searchInputEntity = Entity.entity(
				searchInput, media);
		return target().path(FIND_BY).request(media)
				.post(searchInputEntity, AgencySearchResult.class);
	}

	// @POST
	// @Path("/countBy")
	// @Consumes("application/xml")
	public Long countBy(AgencySearchInput searchInput) {
		Entity<AgencySearchInput> searchInputEntity = Entity.entity(
				searchInput, media);
		return target().path("countBy").request()
				.post(searchInputEntity, Long.class);
	}

	// @POST
	// @Path("/findByLike"
	// @Produces("application/xml")
	// @Consumes("application/xml")
	public AgencySearchResult findByLike(AgencySearchInput searchInput) {
		Entity<AgencySearchInput> searchInputEntity = Entity.entity(
				searchInput, media);
		return target().path(FIND_BY_LIKE_PATH).request(media)
				.post(searchInputEntity, AgencySearchResult.class);
	}

	// @POST
	// @Path("/countByLike"
	// @Consumes("application/xml")
	public Long countByLike(AgencySearchInput searchInput) {
		Entity<AgencySearchInput> searchInputEntity = Entity.entity(
				searchInput, media);
		return target().path("countByLike").request()
				.post(searchInputEntity, Long.class);
	}
}
