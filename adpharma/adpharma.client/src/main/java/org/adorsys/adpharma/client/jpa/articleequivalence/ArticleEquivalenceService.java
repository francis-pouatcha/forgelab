package org.adorsys.adpharma.client.jpa.articleequivalence;

import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.adorsys.javafx.crud.extensions.address.ServerAddress;
import org.adorsys.javafx.crud.extensions.login.ClientCookieFilter;

public class ArticleEquivalenceService {
	private String media = MediaType.APPLICATION_JSON;
	private static final String FIND_BY = "findBy";
	private static final String FIND_BY_LIKE_PATH = "findByLike";

	@Inject
	private ClientCookieFilter clientCookieFilter;
	@Inject
	private ServerAddress serverAddress;
	Client client = ClientBuilder.newClient();

	private WebTarget target() {
		return client.target(serverAddress + "/rest/articleequivalences")
				.register(clientCookieFilter);
	}

	public ArticleEquivalence create(ArticleEquivalence entity) {
		Entity<ArticleEquivalence> eCopy = Entity.entity(entity, media);
		return target().request(media).post(eCopy, ArticleEquivalence.class);
	}

	// @DELETE
	// @Path("/{id:[0-9][0-9]*}")
	public ArticleEquivalence deleteById(Long id) {// @PathParam("id")
													// TODO encode id
		return target().path("" + id).request(media)
				.delete(ArticleEquivalence.class);
	}

	// @PUT
	// @Path("/{id:[0-9][0-9]*}")
	// @Consumes("application/xml")
	public ArticleEquivalence update(ArticleEquivalence entity) {
		Entity<ArticleEquivalence> ent = Entity.entity(entity, media);
		return target().path("" + entity.getId()).request(media)
				.put(ent, ArticleEquivalence.class);
	}

	// @GET
	// @Path("/{id:[0-9][0-9]*}")
	// @Produces("application/xml")
	public ArticleEquivalence findById(Long id) {// @PathParam("id")
		return target().path("" + id).request(media)
				.get(ArticleEquivalence.class);
	}

	// @GET
	// @Produces("application/xml")
	public ArticleEquivalenceSearchResult listAll() {
		return target().request(media)
				.get(ArticleEquivalenceSearchResult.class);
	}

	// @GET
	// @Produces("application/xml")
	public ArticleEquivalenceSearchResult listAll(int start, int max) {
		return target().queryParam("start", start).queryParam("max", max)
				.request(media).get(ArticleEquivalenceSearchResult.class);
	}

	// @GET
	// @Path("/count")
	public Long count() {
		return target().path("count").request().get(Long.class);
	}

	// @POST
	// @Produces("application/xml")
	// @Consumes("application/xml")
	public ArticleEquivalenceSearchResult findBy(
			ArticleEquivalenceSearchInput searchInput) {
		Entity<ArticleEquivalenceSearchInput> searchInputEntity = Entity
				.entity(searchInput, media);
		return target().path(FIND_BY).request(media)
				.post(searchInputEntity, ArticleEquivalenceSearchResult.class);
	}

	// @POST
	// @Path("/countBy")
	// @Consumes("application/xml")
	public Long countBy(ArticleEquivalenceSearchInput searchInput) {
		Entity<ArticleEquivalenceSearchInput> searchInputEntity = Entity
				.entity(searchInput, media);
		return target().path("countBy").request()
				.post(searchInputEntity, Long.class);
	}

	// @POST
	// @Path("/findByLike"
	// @Produces("application/xml")
	// @Consumes("application/xml")
	public ArticleEquivalenceSearchResult findByLike(
			ArticleEquivalenceSearchInput searchInput) {
		Entity<ArticleEquivalenceSearchInput> searchInputEntity = Entity
				.entity(searchInput, media);
		return target().path(FIND_BY_LIKE_PATH).request(media)
				.post(searchInputEntity, ArticleEquivalenceSearchResult.class);
	}

	// @POST
	// @Path("/countByLike"
	// @Consumes("application/xml")
	public Long countByLike(ArticleEquivalenceSearchInput searchInput) {
		Entity<ArticleEquivalenceSearchInput> searchInputEntity = Entity
				.entity(searchInput, media);
		return target().path("countByLike").request()
				.post(searchInputEntity, Long.class);
	}
}
