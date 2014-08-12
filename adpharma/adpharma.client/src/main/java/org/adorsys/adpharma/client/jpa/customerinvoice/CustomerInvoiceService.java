package org.adorsys.adpharma.client.jpa.customerinvoice;

import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.adorsys.adpharma.client.jpa.debtstatement.DebtStatement;
import org.adorsys.adpharma.client.jpa.salesorder.SalesStatisticsDataSearchResult;
import org.adorsys.adpharma.client.jpa.salesorder.SalesStattisticsDataSearchInput;
import org.adorsys.javafx.crud.extensions.address.ServerAddress;
import org.adorsys.javafx.crud.extensions.login.ClientCookieFilter;

public class CustomerInvoiceService {
	private String media = MediaType.APPLICATION_JSON;
	private static final String FIND_BY = "findBy";
	private static final String FIND_BY_LIKE_PATH = "findByLike";

	@Inject
	private ClientCookieFilter clientCookieFilter;
	@Inject
	private ServerAddress serverAddress;
	Client client = ClientBuilder.newClient();

	private WebTarget target() {
		return client.target(serverAddress + "/rest/customerinvoices")
				.register(clientCookieFilter);
	}

	public CustomerInvoice create(CustomerInvoice entity) {
		Entity<CustomerInvoice> eCopy = Entity.entity(entity, media);
		return target().request(media).post(eCopy, CustomerInvoice.class);
	}

	// @DELETE
	// @Path("/{id:[0-9][0-9]*}")
	public CustomerInvoice deleteById(Long id) {// @PathParam("id")
		// TODO encode id
		return target().path("" + id).request(media)
				.delete(CustomerInvoice.class);
	}


	//	@GET
	//	@Path("/findByAgencyAndDateBetween")
	//	@Produces({ "application/json", "application/xml" })
	public CustomerInvoiceSearchResult findByAgencyAndDateBetween(InvoiceByAgencyPrintInput searchInput)
	{
		Entity<InvoiceByAgencyPrintInput> searchInputEntity = Entity.entity(
				searchInput, media);
		return target().path("/findByAgencyAndDateBetween").request(media)
				.post(searchInputEntity, CustomerInvoiceSearchResult.class);
	}
	//	@GET
	//	@Path("/findByAgencyAndDateBetween")
	//	@Produces({ "application/json", "application/xml" })
	public CustomerInvoiceSearchResult customerInvicePerDayAndPerAgency(InvoiceByAgencyPrintInput searchInput)
	{
		Entity<InvoiceByAgencyPrintInput> searchInputEntity = Entity.entity(
				searchInput, media);
		return target().path("/customerInvicePerDayAndPerAgency").request(media)
				.post(searchInputEntity, CustomerInvoiceSearchResult.class);
	}

	// @PUT
	// @Path("/{id:[0-9][0-9]*}")
	// @Consumes("application/xml")
	public CustomerInvoice update(CustomerInvoice entity) {
		Entity<CustomerInvoice> ent = Entity.entity(entity, media);
		return target().path("" + entity.getId()).request(media)
				.put(ent, CustomerInvoice.class);
	}



	// @GET
	// @Path("/{id:[0-9][0-9]*}")
	// @Produces("application/xml")
	public CustomerInvoice findById(Long id) {// @PathParam("id")
		return target().path("" + id).request(media).get(CustomerInvoice.class);
	}

	// @GET
	// @Produces("application/xml")
	public CustomerInvoiceSearchResult listAll() {
		return target().request(media).get(CustomerInvoiceSearchResult.class);
	}

	// @GET
	// @Produces("application/xml")
	public CustomerInvoiceSearchResult listAll(int start, int max) {
		return target().queryParam("start", start).queryParam("max", max)
				.request(media).get(CustomerInvoiceSearchResult.class);
	}

	// @GET
	// @Path("/count")
	public Long count() {
		return target().path("count").request().get(Long.class);
	}

	// @POST
	// @Produces("application/xml")
	// @Consumes("application/xml")
	public CustomerInvoiceSearchResult findBy(
			CustomerInvoiceSearchInput searchInput) {
		Entity<CustomerInvoiceSearchInput> searchInputEntity = Entity.entity(
				searchInput, media);
		return target().path(FIND_BY).request(media)
				.post(searchInputEntity, CustomerInvoiceSearchResult.class);
	}

	// @POST
	// @Path("/countBy")
	// @Consumes("application/xml")
	public Long countBy(CustomerInvoiceSearchInput searchInput) {
		Entity<CustomerInvoiceSearchInput> searchInputEntity = Entity.entity(
				searchInput, media);
		return target().path("countBy").request()
				.post(searchInputEntity, Long.class);
	}

	// @POST
	// @Path("/findByLike"
	// @Produces("application/xml")
	// @Consumes("application/xml")
	public CustomerInvoiceSearchResult findByLike(
			CustomerInvoiceSearchInput searchInput) {
		Entity<CustomerInvoiceSearchInput> searchInputEntity = Entity.entity(
				searchInput, media);
		return target().path(FIND_BY_LIKE_PATH).request(media)
				.post(searchInputEntity, CustomerInvoiceSearchResult.class);
	}

	// @POST
	// @Path("/countByLike"
	// @Consumes("application/xml")
	public Long countByLike(CustomerInvoiceSearchInput searchInput) {
		Entity<CustomerInvoiceSearchInput> searchInputEntity = Entity.entity(
				searchInput, media);
		return target().path("countByLike").request()
				.post(searchInputEntity, Long.class);
	}

	// @POST
	// @Path("/findCustomerInvoiceBySource"
	// @Produces("application/xml")
	// @Consumes("application/xml")
	public CustomerInvoiceSearchResult findCustomerInvoiceBySource(
			DebtStatement source) {
		Entity<DebtStatement> sourceEntity = Entity.entity(
				source, media);
		return target().path("findCustomerInvoiceBySource").request(media)
				.post(sourceEntity, CustomerInvoiceSearchResult.class);
	}
}
