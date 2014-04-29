package org.adorsys.adpharma.client.jpa.customercategory;

import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.adorsys.javafx.crud.extensions.address.ServerAddress;
import org.adorsys.javafx.crud.extensions.login.ClientCookieFilter;

public class CustomerCategoryService {
	private String media = MediaType.APPLICATION_JSON;
	private static final String FIND_BY = "findBy";
	private static final String FIND_BY_LIKE_PATH = "findByLike";

	@Inject
	private ClientCookieFilter clientCookieFilter;
	@Inject
	private ServerAddress serverAddress;

	Client client = ClientBuilder.newClient();

	private WebTarget target() {
		return client.target(serverAddress + "/rest/customercategorys")
				.register(clientCookieFilter);
	}

	public CustomerCategory create(CustomerCategory entity) {
		Entity<CustomerCategory> eCopy = Entity.entity(entity, media);
		return target().request(media).post(eCopy, CustomerCategory.class);
	}

	// @DELETE
	// @Path("/{id:[0-9][0-9]*}")
	public CustomerCategory deleteById(Long id) {// @PathParam("id")
													// TODO encode id
		return target().path("" + id).request(media)
				.delete(CustomerCategory.class);
	}

	// @PUT
	// @Path("/{id:[0-9][0-9]*}")
	// @Consumes("application/xml")
	public CustomerCategory update(CustomerCategory entity) {
		Entity<CustomerCategory> ent = Entity.entity(entity, media);
		return target().path("" + entity.getId()).request(media)
				.put(ent, CustomerCategory.class);
	}

	// @GET
	// @Path("/{id:[0-9][0-9]*}")
	// @Produces("application/xml")
	public CustomerCategory findById(Long id) {// @PathParam("id")
		return target().path("" + id).request(media)
				.get(CustomerCategory.class);
	}

	// @GET
	// @Produces("application/xml")
	public CustomerCategorySearchResult listAll() {
		return target().request(media).get(CustomerCategorySearchResult.class);
	}

	// @GET
	// @Produces("application/xml")
	public CustomerCategorySearchResult listAll(int start, int max) {
		return target().queryParam("start", start).queryParam("max", max)
				.request(media).get(CustomerCategorySearchResult.class);
	}

	// @GET
	// @Path("/count")
	public Long count() {
		return target().path("count").request().get(Long.class);
	}

	// @POST
	// @Produces("application/xml")
	// @Consumes("application/xml")
	public CustomerCategorySearchResult findBy(
			CustomerCategorySearchInput searchInput) {
		Entity<CustomerCategorySearchInput> searchInputEntity = Entity.entity(
				searchInput, media);
		return target().path(FIND_BY).request(media)
				.post(searchInputEntity, CustomerCategorySearchResult.class);
	}

	// @POST
	// @Path("/countBy")
	// @Consumes("application/xml")
	public Long countBy(CustomerCategorySearchInput searchInput) {
		Entity<CustomerCategorySearchInput> searchInputEntity = Entity.entity(
				searchInput, media);
		return target().path("countBy").request()
				.post(searchInputEntity, Long.class);
	}

	// @POST
	// @Path("/findByLike"
	// @Produces("application/xml")
	// @Consumes("application/xml")
	public CustomerCategorySearchResult findByLike(
			CustomerCategorySearchInput searchInput) {
		Entity<CustomerCategorySearchInput> searchInputEntity = Entity.entity(
				searchInput, media);
		return target().path(FIND_BY_LIKE_PATH).request(media)
				.post(searchInputEntity, CustomerCategorySearchResult.class);
	}

	// @POST
	// @Path("/countByLike"
	// @Consumes("application/xml")
	public Long countByLike(CustomerCategorySearchInput searchInput) {
		Entity<CustomerCategorySearchInput> searchInputEntity = Entity.entity(
				searchInput, media);
		return target().path("countByLike").request()
				.post(searchInputEntity, Long.class);
	}
}
