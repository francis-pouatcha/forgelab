package org.adorsys.adpharma.client.jpa.article;

import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.adorsys.javafx.crud.extensions.address.ServerAddress;
import org.adorsys.javafx.crud.extensions.login.ClientCookieFilter;

public class ArticleService {
	private String media = MediaType.APPLICATION_JSON;
	private static final String FIND_BY = "findBy";
	private static final String FIND_BY_LIKE_PATH = "findByLike";

	@Inject
	private ClientCookieFilter clientCookieFilter;
	@Inject
	private ServerAddress serverAddress;

	Client client = ClientBuilder.newClient();

	private WebTarget target() {
		return client.target(serverAddress + "/rest/articles").register(
				clientCookieFilter);
	}

	public Article create(Article entity) {
		Entity<Article> eCopy = Entity.entity(entity, media);
		return target().request(media).post(eCopy, Article.class);
	}

	// @DELETE
	// @Path("/{id:[0-9][0-9]*}")
	public Article deleteById(Long id) {// @PathParam("id")
										// TODO encode id
		return target().path("" + id).request(media).delete(Article.class);
	}

	// @PUT
	// @Path("/{id:[0-9][0-9]*}")
	// @Consumes("application/xml")
	public Article update(Article entity) {
		Entity<Article> ent = Entity.entity(entity, media);
		return target().path("" + entity.getId()).request(media)
				.put(ent, Article.class);
	}

	// @GET
	// @Path("/{id:[0-9][0-9]*}")
	// @Produces("application/xml")
	public Article findById(Long id) {// @PathParam("id")
		return target().path("" + id).request(media).get(Article.class);
	}

	// @GET
	// @Produces("application/xml")
	public ArticleSearchResult listAll() {
		return target().request(media).get(ArticleSearchResult.class);
	}

	// @GET
	// @Produces("application/xml")
	public ArticleSearchResult listAll(int start, int max) {
		return target().queryParam("start", start).queryParam("max", max)
				.request(media).get(ArticleSearchResult.class);
	}

	// @GET
	// @Path("/count")
	public Long count() {
		return target().path("count").request().get(Long.class);
	}

	// @POST
	// @Produces("application/xml")
	// @Consumes("application/xml")
	public ArticleSearchResult findBy(ArticleSearchInput searchInput) {
		Entity<ArticleSearchInput> searchInputEntity = Entity.entity(
				searchInput, media);
		return target().path(FIND_BY).request(media)
				.post(searchInputEntity, ArticleSearchResult.class);
	}

	// @POST
	// @Path("/countBy")
	// @Consumes("application/xml")
	public Long countBy(ArticleSearchInput searchInput) {
		Entity<ArticleSearchInput> searchInputEntity = Entity.entity(
				searchInput, media);
		return target().path("countBy").request()
				.post(searchInputEntity, Long.class);
	}

	// @POST
	// @Path("/findByLike"
	// @Produces("application/xml")
	// @Consumes("application/xml")
	public ArticleSearchResult findByLike(ArticleSearchInput searchInput) {
		Entity<ArticleSearchInput> searchInputEntity = Entity.entity(
				searchInput, media);
		return target().path(FIND_BY_LIKE_PATH).request(media)
				.post(searchInputEntity, ArticleSearchResult.class);
	}

	// @POST
	// @Path("/countByLike"
	// @Consumes("application/xml")
	public Long countByLike(ArticleSearchInput searchInput) {
		Entity<ArticleSearchInput> searchInputEntity = Entity.entity(
				searchInput, media);
		return target().path("countByLike").request()
				.post(searchInputEntity, Long.class);
	}
}
