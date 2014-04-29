package org.adorsys.adpharma.client.jpa.productdetailconfig;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.adorsys.javafx.crud.extensions.address.ServerAddress;
import org.adorsys.javafx.crud.extensions.login.ClientCookieFilter;

public class ProductDetailConfigService {
	private String media = MediaType.APPLICATION_JSON;
	private static final String FIND_BY = "findBy";
	private static final String FIND_BY_LIKE_PATH = "findByLike";

	@Inject
	private ClientCookieFilter clientCookieFilter;
	@Inject
	private ServerAddress serverAddress;
	Client client = ClientBuilder.newClient();

	private WebTarget target() {
		return client.target(serverAddress + "/rest/productdetailconfigs")
				.register(clientCookieFilter);
	}

	public ProductDetailConfig create(ProductDetailConfig entity) {
		Entity<ProductDetailConfig> eCopy = Entity.entity(entity, media);
		return target().request(media).post(eCopy, ProductDetailConfig.class);
	}

	// @DELETE
	// @Path("/{id:[0-9][0-9]*}")
	public ProductDetailConfig deleteById(Long id) {// @PathParam("id")
													// TODO encode id
		return target().path("" + id).request(media)
				.delete(ProductDetailConfig.class);
	}

	// @PUT
	// @Path("/{id:[0-9][0-9]*}")
	// @Consumes("application/xml")
	public ProductDetailConfig update(ProductDetailConfig entity) {
		Entity<ProductDetailConfig> ent = Entity.entity(entity, media);
		return target().path("" + entity.getId()).request(media)
				.put(ent, ProductDetailConfig.class);
	}

	// @GET
	// @Path("/{id:[0-9][0-9]*}")
	// @Produces("application/xml")
	public ProductDetailConfig findById(Long id) {// @PathParam("id")
		return target().path("" + id).request(media)
				.get(ProductDetailConfig.class);
	}

	// @GET
	// @Produces("application/xml")
	public ProductDetailConfigSearchResult listAll() {
		return target().request(media).get(
				ProductDetailConfigSearchResult.class);
	}

	// @GET
	// @Produces("application/xml")
	public ProductDetailConfigSearchResult listAll(int start, int max) {
		return target().queryParam("start", start).queryParam("max", max)
				.request(media).get(ProductDetailConfigSearchResult.class);
	}

	// @GET
	// @Path("/findByOriginAndTargetNameLike")
	// @Produces({ "application/json", "application/xml" })
	public List<ProductDetailConfig> findByOriginAndTargetNameLike(
			String sourceName, String targetName) {
		return target().path("findByOriginAndTargetNameLike")
				.queryParam("source", sourceName)
				.queryParam("target", targetName).request(media)
				.get(List.class);
	}

	// @GET
	// @Path("/count")
	public Long count() {
		return target().path("count").request().get(Long.class);
	}

	// @POST
	// @Produces("application/xml")
	// @Consumes("application/xml")
	public ProductDetailConfigSearchResult findBy(
			ProductDetailConfigSearchInput searchInput) {
		Entity<ProductDetailConfigSearchInput> searchInputEntity = Entity
				.entity(searchInput, media);
		return target().path(FIND_BY).request(media)
				.post(searchInputEntity, ProductDetailConfigSearchResult.class);
	}

	// @POST
	// @Path("/countBy")
	// @Consumes("application/xml")
	public Long countBy(ProductDetailConfigSearchInput searchInput) {
		Entity<ProductDetailConfigSearchInput> searchInputEntity = Entity
				.entity(searchInput, media);
		return target().path("countBy").request()
				.post(searchInputEntity, Long.class);
	}

	// @POST
	// @Path("/findByLike"
	// @Produces("application/xml")
	// @Consumes("application/xml")
	public ProductDetailConfigSearchResult findByLike(
			ProductDetailConfigSearchInput searchInput) {
		Entity<ProductDetailConfigSearchInput> searchInputEntity = Entity
				.entity(searchInput, media);
		return target().path(FIND_BY_LIKE_PATH).request(media)
				.post(searchInputEntity, ProductDetailConfigSearchResult.class);
	}

	// @POST
	// @Path("/countByLike"
	// @Consumes("application/xml")
	public Long countByLike(ProductDetailConfigSearchInput searchInput) {
		Entity<ProductDetailConfigSearchInput> searchInputEntity = Entity
				.entity(searchInput, media);
		return target().path("countByLike").request()
				.post(searchInputEntity, Long.class);
	}
}
