package org.adorsys.adpharma.client.jpa.paymentitem;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.adorsys.javafx.crud.extensions.address.ServerAddress;
import org.adorsys.javafx.crud.extensions.login.ClientCookieFilter;

public class PaymentItemService {
	private String media = MediaType.APPLICATION_JSON;
	private static final String FIND_BY = "findBy";
	private static final String FIND_BY_LIKE_PATH = "findByLike";

	@Inject
	private ClientCookieFilter clientCookieFilter;
	@Inject
	private ServerAddress serverAddress;
	Client client = ClientBuilder.newClient();

	private WebTarget target() {
		return client.target(serverAddress + "/rest/paymentitems").register(clientCookieFilter);
	}

	public PaymentItem create(PaymentItem entity) {
		Entity<PaymentItem> eCopy = Entity.entity(entity, media);
		return target().request(media).post(eCopy, PaymentItem.class);
	}

	// @DELETE
	// @Path("/{id:[0-9][0-9]*}")
	public PaymentItem deleteById(Long id) {// @PathParam("id")
											// TODO encode id
		return target().path("" + id).request(media).delete(PaymentItem.class);
	}

	// @PUT
	// @Path("/{id:[0-9][0-9]*}")
	// @Consumes("application/xml")
	public PaymentItem update(PaymentItem entity) {
		Entity<PaymentItem> ent = Entity.entity(entity, media);
		return target().path("" + entity.getId()).request(media)
				.put(ent, PaymentItem.class);
	}

	// @GET
	// @Path("/{id:[0-9][0-9]*}")
	// @Produces("application/xml")
	public PaymentItem findById(Long id) {// @PathParam("id")
		return target().path("" + id).request(media).get(PaymentItem.class);
	}

	// @GET
	// @Produces("application/xml")
	public PaymentItemSearchResult listAll() {
		return target().request(media).get(PaymentItemSearchResult.class);
	}

	// @GET
	// @Produces("application/xml")
	public PaymentItemSearchResult listAll(int start, int max) {
		return target().queryParam("start", start).queryParam("max", max)
				.request(media).get(PaymentItemSearchResult.class);
	}

	// @GET
	// @Path("/count")
	public Long count() {
		return target().path("count").request().get(Long.class);
	}

	// @POST
	// @Produces("application/xml")
	// @Consumes("application/xml")
	public PaymentItemSearchResult findBy(PaymentItemSearchInput searchInput) {
		Entity<PaymentItemSearchInput> searchInputEntity = Entity.entity(
				searchInput, media);
		return target().path(FIND_BY).request(media)
				.post(searchInputEntity, PaymentItemSearchResult.class);
	}

	// @POST
	// @Path("/countBy")
	// @Consumes("application/xml")
	public Long countBy(PaymentItemSearchInput searchInput) {
		Entity<PaymentItemSearchInput> searchInputEntity = Entity.entity(
				searchInput, media);
		return target().path("countBy").request()
				.post(searchInputEntity, Long.class);
	}

	// @POST
	// @Path("/findByLike"
	// @Produces("application/xml")
	// @Consumes("application/xml")
	public PaymentItemSearchResult findByLike(PaymentItemSearchInput searchInput) {
		Entity<PaymentItemSearchInput> searchInputEntity = Entity.entity(
				searchInput, media);
		return target().path(FIND_BY_LIKE_PATH).request(media)
				.post(searchInputEntity, PaymentItemSearchResult.class);
	}

	// @POST
	// @Path("/countByLike"
	// @Consumes("application/xml")
	public Long countByLike(PaymentItemSearchInput searchInput) {
		Entity<PaymentItemSearchInput> searchInputEntity = Entity.entity(
				searchInput, media);
		return target().path("countByLike").request()
				.post(searchInputEntity, Long.class);
	}
}
