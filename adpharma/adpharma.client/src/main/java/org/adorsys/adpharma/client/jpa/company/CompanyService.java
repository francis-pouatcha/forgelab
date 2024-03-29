package org.adorsys.adpharma.client.jpa.company;

import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.adorsys.javafx.crud.extensions.address.ServerAddress;
import org.adorsys.javafx.crud.extensions.login.ClientCookieFilter;

public class CompanyService {
	private String media = MediaType.APPLICATION_JSON;
	private static final String FIND_BY = "findBy";
	private static final String FIND_BY_LIKE_PATH = "findByLike";

	@Inject
	private ClientCookieFilter clientCookieFilter;
	Client client = ClientBuilder.newClient();
	@Inject
	private ServerAddress serverAddress;

	public WebTarget target() {
		return client.target(serverAddress + "/rest/companys").register(
				clientCookieFilter);
	}

	public Company create(Company entity) {
		Entity<Company> eCopy = Entity.entity(entity, media);
		return target().request(media).post(eCopy, Company.class);
	}

	// @DELETE
	// @Path("/{id:[0-9][0-9]*}")
	public Company deleteById(Long id) {// @PathParam("id")
										// TODO encode id
		return target().path("" + id).request(media).delete(Company.class);
	}

	// @PUT
	// @Path("/{id:[0-9][0-9]*}")
	// @Consumes("application/xml")
	public Company update(Company entity) {
		Entity<Company> ent = Entity.entity(entity, media);
		return target().path("" + entity.getId()).request(media)
				.put(ent, Company.class);
	}

	// @GET
	// @Path("/{id:[0-9][0-9]*}")
	// @Produces("application/xml")
	public Company findById(Long id) {// @PathParam("id")
		return target().path("" + id).request(media).get(Company.class);
	}

	// @GET
	// @Produces("application/xml")
	public CompanySearchResult listAll() {
		return target().request(media).get(CompanySearchResult.class);
	}

	// @GET
	// @Produces("application/xml")
	public CompanySearchResult listAll(int start, int max) {
		return target().queryParam("start", start).queryParam("max", max)
				.request(media).get(CompanySearchResult.class);
	}

	// @GET
	// @Path("/count")
	public Long count() {
		return target().path("count").request().get(Long.class);
	}

	// @POST
	// @Produces("application/xml")
	// @Consumes("application/xml")
	public CompanySearchResult findBy(CompanySearchInput searchInput) {
		Entity<CompanySearchInput> searchInputEntity = Entity.entity(
				searchInput, media);
		return target().path(FIND_BY).request(media)
				.post(searchInputEntity, CompanySearchResult.class);
	}

	// @POST
	// @Path("/countBy")
	// @Consumes("application/xml")
	public Long countBy(CompanySearchInput searchInput) {
		Entity<CompanySearchInput> searchInputEntity = Entity.entity(
				searchInput, media);
		return target().path("countBy").request()
				.post(searchInputEntity, Long.class);
	}

	// @POST
	// @Path("/findByLike"
	// @Produces("application/xml")
	// @Consumes("application/xml")
	public CompanySearchResult findByLike(CompanySearchInput searchInput) {
		Entity<CompanySearchInput> searchInputEntity = Entity.entity(
				searchInput, media);
		return target().path(FIND_BY_LIKE_PATH).request(media)
				.post(searchInputEntity, CompanySearchResult.class);
	}

	// @POST
	// @Path("/countByLike"
	// @Consumes("application/xml")
	public Long countByLike(CompanySearchInput searchInput) {
		Entity<CompanySearchInput> searchInputEntity = Entity.entity(
				searchInput, media);
		return target().path("countByLike").request()
				.post(searchInputEntity, Long.class);
	}
}
