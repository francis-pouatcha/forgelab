package org.adorsys.adpharma.client.jpa.section;

import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.adorsys.javafx.crud.extensions.address.ServerAddress;
import org.adorsys.javafx.crud.extensions.login.ClientCookieFilter;

public class SectionService {
	private String media = MediaType.APPLICATION_JSON;
	private static final String FIND_BY = "findBy";
	private static final String FIND_BY_LIKE_PATH = "findByLike";

	@Inject
	private ClientCookieFilter clientCookieFilter;
	@Inject
	private ServerAddress serverAddress;
	Client client = ClientBuilder.newClient();

	private WebTarget target() {
		WebTarget target = client.target(serverAddress + "/rest/sections");
		target.register(clientCookieFilter);
		return target;
	}

	public Section create(Section entity) {
		Entity<Section> eCopy = Entity.entity(entity, media);
		return target().request(media).post(eCopy, Section.class);
	}

	// @DELETE
	// @Path("/{id:[0-9][0-9]*}")
	public Section deleteById(Long id) {// @PathParam("id")
										// TODO encode id
		return target().path("" + id).request(media).delete(Section.class);
	}

	// @PUT
	// @Path("/{id:[0-9][0-9]*}")
	// @Consumes("application/xml")
	public Section update(Section entity) {
		Entity<Section> ent = Entity.entity(entity, media);
		return target().path("" + entity.getId()).request(media)
				.put(ent, Section.class);
	}

	// @GET
	// @Path("/{id:[0-9][0-9]*}")
	// @Produces("application/xml")
	public Section findById(Long id) {// @PathParam("id")
		return target().path("" + id).request(media).get(Section.class);
	}

	// @GET
	// @Produces("application/xml")
	public SectionSearchResult listAll() {
		return target().request(media).get(SectionSearchResult.class);
	}

	// @GET
	// @Produces("application/xml")
	public SectionSearchResult listAll(int start, int max) {
		return target().queryParam("start", start).queryParam("max", max)
				.request(media).get(SectionSearchResult.class);
	}

	// @GET
	// @Path("/count")
	public Long count() {
		return target().path("count").request().get(Long.class);
	}

	// @POST
	// @Produces("application/xml")
	// @Consumes("application/xml")
	public SectionSearchResult findBy(SectionSearchInput searchInput) {
		Entity<SectionSearchInput> searchInputEntity = Entity.entity(
				searchInput, media);
		return target().path(FIND_BY).request(media)
				.post(searchInputEntity, SectionSearchResult.class);
	}

	// @POST
	// @Path("/countBy")
	// @Consumes("application/xml")
	public Long countBy(SectionSearchInput searchInput) {
		Entity<SectionSearchInput> searchInputEntity = Entity.entity(
				searchInput, media);
		return target().path("countBy").request()
				.post(searchInputEntity, Long.class);
	}

	// @POST
	// @Path("/findByLike"
	// @Produces("application/xml")
	// @Consumes("application/xml")
	public SectionSearchResult findByLike(SectionSearchInput searchInput) {
		Entity<SectionSearchInput> searchInputEntity = Entity.entity(
				searchInput, media);
		return target().path(FIND_BY_LIKE_PATH).request(media)
				.post(searchInputEntity, SectionSearchResult.class);
	}

	// @POST
	// @Path("/countByLike"
	// @Consumes("application/xml")
	public Long countByLike(SectionSearchInput searchInput) {
		Entity<SectionSearchInput> searchInputEntity = Entity.entity(
				searchInput, media);
		return target().path("countByLike").request()
				.post(searchInputEntity, Long.class);
	}
}
