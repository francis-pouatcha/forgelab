package org.adorsys.adpharma.client.jpa.supplierinvoice;

import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.adorsys.adpharma.client.jpa.delivery.DeliveryStatisticsDataSearchResult;
import org.adorsys.adpharma.client.jpa.delivery.DeliveryStattisticsDataSearchInput;
import org.adorsys.adpharma.client.jpa.salesorder.SalesStatisticsDataSearchResult;
import org.adorsys.adpharma.client.jpa.salesorder.SalesStattisticsDataSearchInput;
import org.adorsys.javafx.crud.extensions.address.ServerAddress;
import org.adorsys.javafx.crud.extensions.login.ClientCookieFilter;

public class SupplierInvoiceService {
	private String media = MediaType.APPLICATION_JSON;
	private static final String FIND_BY = "findBy";
	private static final String FIND_BY_LIKE_PATH = "findByLike";

	@Inject
	private ClientCookieFilter clientCookieFilter;
	@Inject
	private ServerAddress serverAddress;
	Client client = ClientBuilder.newClient();

	private WebTarget target() {
		return client.target(serverAddress + "/rest/supplierinvoices")
				.register(clientCookieFilter);
	}

	public SupplierInvoice create(SupplierInvoice entity) {
		Entity<SupplierInvoice> eCopy = Entity.entity(entity, media);
		return target().request(media).post(eCopy, SupplierInvoice.class);
	}

	// @DELETE
	// @Path("/{id:[0-9][0-9]*}")
	public SupplierInvoice deleteById(Long id) {// @PathParam("id")
		// TODO encode id
		return target().path("" + id).request(media)
				.delete(SupplierInvoice.class);
	}

	// @PUT
	// @Path("/{id:[0-9][0-9]*}")
	// @Consumes("application/xml")
	public SupplierInvoice update(SupplierInvoice entity) {
		Entity<SupplierInvoice> ent = Entity.entity(entity, media);
		return target().path("" + entity.getId()).request(media)
				.put(ent, SupplierInvoice.class);
	}

	// @GET
	// @Path("/{id:[0-9][0-9]*}")
	// @Produces("application/xml")
	public SupplierInvoice findById(Long id) {// @PathParam("id")
		return target().path("" + id).request(media).get(SupplierInvoice.class);
	}

	// @GET
	// @Produces("application/xml")
	public SupplierInvoiceSearchResult listAll() {
		return target().request(media).get(SupplierInvoiceSearchResult.class);
	}

	// @GET
	// @Produces("application/xml")
	public SupplierInvoiceSearchResult listAll(int start, int max) {
		return target().queryParam("start", start).queryParam("max", max)
				.request(media).get(SupplierInvoiceSearchResult.class);
	}

	// @GET
	// @Path("/count")
	public Long count() {
		return target().path("count").request().get(Long.class);
	}

	// @POST
	// @Produces("application/xml")
	// @Consumes("application/xml")
	public SupplierInvoiceSearchResult findBy(
			SupplierInvoiceSearchInput searchInput) {
		Entity<SupplierInvoiceSearchInput> searchInputEntity = Entity.entity(
				searchInput, media);
		return target().path(FIND_BY).request(media)
				.post(searchInputEntity, SupplierInvoiceSearchResult.class);
	}

	// @POST
	// @Path("/countBy")
	// @Consumes("application/xml")
	public Long countBy(SupplierInvoiceSearchInput searchInput) {
		Entity<SupplierInvoiceSearchInput> searchInputEntity = Entity.entity(
				searchInput, media);
		return target().path("countBy").request()
				.post(searchInputEntity, Long.class);
	}

	// @POST
	// @Path("/findByLike"
	// @Produces("application/xml")
	// @Consumes("application/xml")
	public SupplierInvoiceSearchResult findByLike(
			SupplierInvoiceSearchInput searchInput) {
		Entity<SupplierInvoiceSearchInput> searchInputEntity = Entity.entity(
				searchInput, media);
		return target().path(FIND_BY_LIKE_PATH).request(media)
				.post(searchInputEntity, SupplierInvoiceSearchResult.class);
	}

	// @POST
	// @Path("/countByLike"
	// @Consumes("application/xml")
	public Long countByLike(SupplierInvoiceSearchInput searchInput) {
		Entity<SupplierInvoiceSearchInput> searchInputEntity = Entity.entity(
				searchInput, media);
		return target().path("countByLike").request()
				.post(searchInputEntity, Long.class);
	}

	//	@GET
	//	@Path("/findSalesStatistics")
	//	@Produces({ "application/json", "application/xml" })
	@SuppressWarnings("unchecked")
	public DeliveryStatisticsDataSearchResult findSalesStatistics(
			DeliveryStattisticsDataSearchInput entity) {
		Entity<DeliveryStattisticsDataSearchInput> ent = Entity.entity(entity, media);
		return target().path("findDeliveryStatistics").request(media)
				.post(ent, DeliveryStatisticsDataSearchResult.class);
	}


}
