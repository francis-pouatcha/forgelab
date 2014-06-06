package org.adorsys.adpharma.client.jpa.employer;

import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.adorsys.javafx.crud.extensions.address.ServerAddress;
import org.adorsys.javafx.crud.extensions.login.ClientCookieFilter;

public class EmployerService {
	private String media = MediaType.APPLICATION_JSON;
	private static final String FIND_BY = "findBy";
	private static final String FIND_BY_LIKE_PATH = "findByLike";

	@Inject
	private ClientCookieFilter clientCookieFilter;
	@Inject
	private ServerAddress serverAddress;
	Client client = ClientBuilder.newClient();

	private WebTarget target() {
		return client.target(serverAddress + "/rest/employers").register(
				clientCookieFilter);
	}

	public Employer create(Employer entity) {
		Entity<Employer> eCopy = Entity.entity(entity, media);
		return target().request(media).post(eCopy, Employer.class);
	}

	// @DELETE
	// @Path("/{id:[0-9][0-9]*}")
	public Employer deleteById(Long id) {// @PathParam("id")
											// TODO encode id
		return target().path("" + id).request(media).delete(Employer.class);
	}

	// @PUT
	// @Path("/{id:[0-9][0-9]*}")
	// @Consumes("application/xml")
	public Employer update(Employer entity) {
		Entity<Employer> ent = Entity.entity(entity, media);
		return target().path("" + entity.getId()).request(media)
				.put(ent, Employer.class);
	}

	// @GET
	// @Path("/{id:[0-9][0-9]*}")
	// @Produces("application/xml")
	public Employer findById(Long id) {// @PathParam("id")
		return target().path("" + id).request(media).get(Employer.class);
	}

	// @GET
	// @Produces("application/xml")
	public EmployerSearchResult listAll() {
		return target().request(media).get(EmployerSearchResult.class);
	}

	// @GET
	// @Produces("application/xml")
	public EmployerSearchResult listAll(int start, int max) {
		return target().queryParam("start", start).queryParam("max", max)
				.request(media).get(EmployerSearchResult.class);
	}

	// @GET
	// @Path("/count")
	public Long count() {
		return target().path("count").request().get(Long.class);
	}

	// @POST
	// @Produces("application/xml")
	// @Consumes("application/xml")
	public EmployerSearchResult findBy(EmployerSearchInput searchInput) {
		Entity<EmployerSearchInput> searchInputEntity = Entity.entity(
				searchInput, media);
		return target().path(FIND_BY).request(media)
				.post(searchInputEntity, EmployerSearchResult.class);
	}

	// @POST
	// @Path("/countBy")
	// @Consumes("application/xml")
	public Long countBy(EmployerSearchInput searchInput) {
		Entity<EmployerSearchInput> searchInputEntity = Entity.entity(
				searchInput, media);
		return target().path("countBy").request()
				.post(searchInputEntity, Long.class);
	}

	// @POST
	// @Path("/findByLike"
	// @Produces("application/xml")
	// @Consumes("application/xml")
	public EmployerSearchResult findByLike(EmployerSearchInput searchInput) {
		Entity<EmployerSearchInput> searchInputEntity = Entity.entity(
				searchInput, media);
		return target().path(FIND_BY_LIKE_PATH).request(media)
				.post(searchInputEntity, EmployerSearchResult.class);
	}

	// @POST
	// @Path("/countByLike"
	// @Consumes("application/xml")
	public Long countByLike(EmployerSearchInput searchInput) {
		Entity<EmployerSearchInput> searchInputEntity = Entity.entity(
				searchInput, media);
		return target().path("countByLike").request()
				.post(searchInputEntity, Long.class);
	}
}
