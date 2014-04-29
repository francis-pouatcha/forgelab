package org.adorsys.adpharma.client.jpa.customervoucher;

import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.adorsys.javafx.crud.extensions.address.ServerAddress;
import org.adorsys.javafx.crud.extensions.login.ClientCookieFilter;

public class CustomerVoucherService {
	private String media = MediaType.APPLICATION_JSON;
	private static final String FIND_BY = "findBy";
	private static final String FIND_BY_LIKE_PATH = "findByLike";

	@Inject
	private ClientCookieFilter clientCookieFilter;
	@Inject
	private ServerAddress serverAddress;

	Client client = ClientBuilder.newClient();

	private WebTarget target() {
		return client.target(serverAddress + "/rest/customervouchers")
				.register(clientCookieFilter);
	}

	public CustomerVoucher create(CustomerVoucher entity) {
		Entity<CustomerVoucher> eCopy = Entity.entity(entity, media);
		return target().request(media).post(eCopy, CustomerVoucher.class);
	}

	// @DELETE
	// @Path("/{id:[0-9][0-9]*}")
	public CustomerVoucher deleteById(Long id) {// @PathParam("id")
												// TODO encode id
		return target().path("" + id).request(media)
				.delete(CustomerVoucher.class);
	}

	// @PUT
	// @Path("/{id:[0-9][0-9]*}")
	// @Consumes("application/xml")
	public CustomerVoucher update(CustomerVoucher entity) {
		Entity<CustomerVoucher> ent = Entity.entity(entity, media);
		return target().path("" + entity.getId()).request(media)
				.put(ent, CustomerVoucher.class);
	}

	// @GET
	// @Path("/{id:[0-9][0-9]*}")
	// @Produces("application/xml")
	public CustomerVoucher findById(Long id) {// @PathParam("id")
		return target().path("" + id).request(media).get(CustomerVoucher.class);
	}

	// @GET
	// @Produces("application/xml")
	public CustomerVoucherSearchResult listAll() {
		return target().request(media).get(CustomerVoucherSearchResult.class);
	}

	// @GET
	// @Produces("application/xml")
	public CustomerVoucherSearchResult listAll(int start, int max) {
		return target().queryParam("start", start).queryParam("max", max)
				.request(media).get(CustomerVoucherSearchResult.class);
	}

	// @GET
	// @Path("/count")
	public Long count() {
		return target().path("count").request().get(Long.class);
	}

	// @POST
	// @Produces("application/xml")
	// @Consumes("application/xml")
	public CustomerVoucherSearchResult findBy(
			CustomerVoucherSearchInput searchInput) {
		Entity<CustomerVoucherSearchInput> searchInputEntity = Entity.entity(
				searchInput, media);
		return target().path(FIND_BY).request(media)
				.post(searchInputEntity, CustomerVoucherSearchResult.class);
	}

	// @POST
	// @Path("/countBy")
	// @Consumes("application/xml")
	public Long countBy(CustomerVoucherSearchInput searchInput) {
		Entity<CustomerVoucherSearchInput> searchInputEntity = Entity.entity(
				searchInput, media);
		return target().path("countBy").request()
				.post(searchInputEntity, Long.class);
	}

	// @POST
	// @Path("/findByLike"
	// @Produces("application/xml")
	// @Consumes("application/xml")
	public CustomerVoucherSearchResult findByLike(
			CustomerVoucherSearchInput searchInput) {
		Entity<CustomerVoucherSearchInput> searchInputEntity = Entity.entity(
				searchInput, media);
		return target().path(FIND_BY_LIKE_PATH).request(media)
				.post(searchInputEntity, CustomerVoucherSearchResult.class);
	}

	// @POST
	// @Path("/countByLike"
	// @Consumes("application/xml")
	public Long countByLike(CustomerVoucherSearchInput searchInput) {
		Entity<CustomerVoucherSearchInput> searchInputEntity = Entity.entity(
				searchInput, media);
		return target().path("countByLike").request()
				.post(searchInputEntity, Long.class);
	}
}
