package org.adorsys.adpharma.client.jpa.supplierinvoiceitem;

import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.adorsys.javafx.crud.extensions.address.ServerAddress;
import org.adorsys.javafx.crud.extensions.login.ClientCookieFilter;

public class SupplierInvoiceItemService {
	private String media = MediaType.APPLICATION_JSON;
	private static final String FIND_BY = "findBy";
	private static final String FIND_BY_LIKE_PATH = "findByLike";

	@Inject
	private ClientCookieFilter clientCookieFilter;
	Client client = ClientBuilder.newClient();
	@Inject
	private ServerAddress serverAddress;

	private WebTarget target() {
		return client.target(serverAddress + "/rest/supplierinvoiceitems")
				.register(clientCookieFilter);
	}

	public SupplierInvoiceItem create(SupplierInvoiceItem entity) {
		Entity<SupplierInvoiceItem> eCopy = Entity.entity(entity, media);
		return target().request(media).post(eCopy, SupplierInvoiceItem.class);
	}

	// @DELETE
	// @Path("/{id:[0-9][0-9]*}")
	public SupplierInvoiceItem deleteById(Long id) {// @PathParam("id")
													// TODO encode id
		return target().path("" + id).request(media)
				.delete(SupplierInvoiceItem.class);
	}

	// @PUT
	// @Path("/{id:[0-9][0-9]*}")
	// @Consumes("application/xml")
	public SupplierInvoiceItem update(SupplierInvoiceItem entity) {
		Entity<SupplierInvoiceItem> ent = Entity.entity(entity, media);
		return target().path("" + entity.getId()).request(media)
				.put(ent, SupplierInvoiceItem.class);
	}

	// @GET
	// @Path("/{id:[0-9][0-9]*}")
	// @Produces("application/xml")
	public SupplierInvoiceItem findById(Long id) {// @PathParam("id")
		return target().path("" + id).request(media)
				.get(SupplierInvoiceItem.class);
	}

	// @GET
	// @Produces("application/xml")
	public SupplierInvoiceItemSearchResult listAll() {
		return target().request(media).get(
				SupplierInvoiceItemSearchResult.class);
	}

	// @GET
	// @Produces("application/xml")
	public SupplierInvoiceItemSearchResult listAll(int start, int max) {
		return target().queryParam("start", start).queryParam("max", max)
				.request(media).get(SupplierInvoiceItemSearchResult.class);
	}

	// @GET
	// @Path("/count")
	public Long count() {
		return target().path("count").request().get(Long.class);
	}

	// @POST
	// @Produces("application/xml")
	// @Consumes("application/xml")
	public SupplierInvoiceItemSearchResult findBy(
			SupplierInvoiceItemSearchInput searchInput) {
		Entity<SupplierInvoiceItemSearchInput> searchInputEntity = Entity
				.entity(searchInput, media);
		return target().path(FIND_BY).request(media)
				.post(searchInputEntity, SupplierInvoiceItemSearchResult.class);
	}

	// @POST
	// @Path("/countBy")
	// @Consumes("application/xml")
	public Long countBy(SupplierInvoiceItemSearchInput searchInput) {
		Entity<SupplierInvoiceItemSearchInput> searchInputEntity = Entity
				.entity(searchInput, media);
		return target().path("countBy").request()
				.post(searchInputEntity, Long.class);
	}

	// @POST
	// @Path("/findByLike"
	// @Produces("application/xml")
	// @Consumes("application/xml")
	public SupplierInvoiceItemSearchResult findByLike(
			SupplierInvoiceItemSearchInput searchInput) {
		Entity<SupplierInvoiceItemSearchInput> searchInputEntity = Entity
				.entity(searchInput, media);
		return target().path(FIND_BY_LIKE_PATH).request(media)
				.post(searchInputEntity, SupplierInvoiceItemSearchResult.class);
	}

	// @POST
	// @Path("/countByLike"
	// @Consumes("application/xml")
	public Long countByLike(SupplierInvoiceItemSearchInput searchInput) {
		Entity<SupplierInvoiceItemSearchInput> searchInputEntity = Entity
				.entity(searchInput, media);
		return target().path("countByLike").request()
				.post(searchInputEntity, Long.class);
	}
}
