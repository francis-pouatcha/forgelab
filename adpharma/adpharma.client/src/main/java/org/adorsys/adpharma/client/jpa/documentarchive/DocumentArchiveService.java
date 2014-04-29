package org.adorsys.adpharma.client.jpa.documentarchive;

import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.adorsys.javafx.crud.extensions.address.ServerAddress;
import org.adorsys.javafx.crud.extensions.login.ClientCookieFilter;

public class DocumentArchiveService {
	private String media = MediaType.APPLICATION_JSON;
	private static final String FIND_BY = "findBy";
	private static final String FIND_BY_LIKE_PATH = "findByLike";

	@Inject
	private ClientCookieFilter clientCookieFilter;
	@Inject
	private ServerAddress serverAddress;
	Client client = ClientBuilder.newClient();

	private WebTarget target() {
		return client.target(serverAddress + "/rest/documentarchives")
				.register(clientCookieFilter);
	}

	public DocumentArchive create(DocumentArchive entity) {
		Entity<DocumentArchive> eCopy = Entity.entity(entity, media);
		return target().request(media).post(eCopy, DocumentArchive.class);
	}

	// @DELETE
	// @Path("/{id:[0-9][0-9]*}")
	public DocumentArchive deleteById(Long id) {// @PathParam("id")
												// TODO encode id
		return target().path("" + id).request(media)
				.delete(DocumentArchive.class);
	}

	// @PUT
	// @Path("/{id:[0-9][0-9]*}")
	// @Consumes("application/xml")
	public DocumentArchive update(DocumentArchive entity) {
		Entity<DocumentArchive> ent = Entity.entity(entity, media);
		return target().path("" + entity.getId()).request(media)
				.put(ent, DocumentArchive.class);
	}

	// @GET
	// @Path("/{id:[0-9][0-9]*}")
	// @Produces("application/xml")
	public DocumentArchive findById(Long id) {// @PathParam("id")
		return target().path("" + id).request(media).get(DocumentArchive.class);
	}

	// @GET
	// @Produces("application/xml")
	public DocumentArchiveSearchResult listAll() {
		return target().request(media).get(DocumentArchiveSearchResult.class);
	}

	// @GET
	// @Produces("application/xml")
	public DocumentArchiveSearchResult listAll(int start, int max) {
		return target().queryParam("start", start).queryParam("max", max)
				.request(media).get(DocumentArchiveSearchResult.class);
	}

	// @GET
	// @Path("/count")
	public Long count() {
		return target().path("count").request().get(Long.class);
	}

	// @POST
	// @Produces("application/xml")
	// @Consumes("application/xml")
	public DocumentArchiveSearchResult findBy(
			DocumentArchiveSearchInput searchInput) {
		Entity<DocumentArchiveSearchInput> searchInputEntity = Entity.entity(
				searchInput, media);
		return target().path(FIND_BY).request(media)
				.post(searchInputEntity, DocumentArchiveSearchResult.class);
	}

	// @POST
	// @Path("/countBy")
	// @Consumes("application/xml")
	public Long countBy(DocumentArchiveSearchInput searchInput) {
		Entity<DocumentArchiveSearchInput> searchInputEntity = Entity.entity(
				searchInput, media);
		return target().path("countBy").request()
				.post(searchInputEntity, Long.class);
	}

	// @POST
	// @Path("/findByLike"
	// @Produces("application/xml")
	// @Consumes("application/xml")
	public DocumentArchiveSearchResult findByLike(
			DocumentArchiveSearchInput searchInput) {
		Entity<DocumentArchiveSearchInput> searchInputEntity = Entity.entity(
				searchInput, media);
		return target().path(FIND_BY_LIKE_PATH).request(media)
				.post(searchInputEntity, DocumentArchiveSearchResult.class);
	}

	// @POST
	// @Path("/countByLike"
	// @Consumes("application/xml")
	public Long countByLike(DocumentArchiveSearchInput searchInput) {
		Entity<DocumentArchiveSearchInput> searchInputEntity = Entity.entity(
				searchInput, media);
		return target().path("countByLike").request()
				.post(searchInputEntity, Long.class);
	}
}
