package org.adorsys.adpharma.client.jpa.debtstatementcustomerinvoiceassoc;

import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.adorsys.javafx.crud.extensions.address.ServerAddress;
import org.adorsys.javafx.crud.extensions.login.ClientCookieFilter;

public class DebtStatementCustomerInvoiceAssocService {
	private String media = MediaType.APPLICATION_JSON;
	private static final String FIND_BY = "findBy";
	private static final String FIND_BY_LIKE_PATH = "findByLike";

	@Inject
	private ClientCookieFilter clientCookieFilter;
	@Inject
	private ServerAddress serverAddress;
	Client client = ClientBuilder.newClient();

	private WebTarget target() {
		return client.target(
				serverAddress + "/rest/debtstatementcustomerinvoiceassocs")
				.register(clientCookieFilter);
	}

	public DebtStatementCustomerInvoiceAssoc create(
			DebtStatementCustomerInvoiceAssoc entity) {
		Entity<DebtStatementCustomerInvoiceAssoc> eCopy = Entity.entity(entity,
				media);
		return target().request(media).post(eCopy,
				DebtStatementCustomerInvoiceAssoc.class);
	}

	// @DELETE
	// @Path("/{id:[0-9][0-9]*}")
	public DebtStatementCustomerInvoiceAssoc deleteById(Long id) {// @PathParam("id")
																	// TODO
																	// encode id
		return target().path("" + id).request(media)
				.delete(DebtStatementCustomerInvoiceAssoc.class);
	}

	// @PUT
	// @Path("/{id:[0-9][0-9]*}")
	// @Consumes("application/xml")
	public DebtStatementCustomerInvoiceAssoc update(
			DebtStatementCustomerInvoiceAssoc entity) {
		Entity<DebtStatementCustomerInvoiceAssoc> ent = Entity.entity(entity,
				media);
		return target().path("" + entity.getId()).request(media)
				.put(ent, DebtStatementCustomerInvoiceAssoc.class);
	}

	// @GET
	// @Path("/{id:[0-9][0-9]*}")
	// @Produces("application/xml")
	public DebtStatementCustomerInvoiceAssoc findById(Long id) {// @PathParam("id")
		return target().path("" + id).request(media)
				.get(DebtStatementCustomerInvoiceAssoc.class);
	}

	// @GET
	// @Produces("application/xml")
	public DebtStatementCustomerInvoiceAssocSearchResult listAll() {
		return target().request(media).get(
				DebtStatementCustomerInvoiceAssocSearchResult.class);
	}

	// @GET
	// @Produces("application/xml")
	public DebtStatementCustomerInvoiceAssocSearchResult listAll(int start,
			int max) {
		return target().queryParam("start", start).queryParam("max", max)
				.request(media)
				.get(DebtStatementCustomerInvoiceAssocSearchResult.class);
	}

	// @GET
	// @Path("/count")
	public Long count() {
		return target().path("count").request().get(Long.class);
	}

	// @POST
	// @Produces("application/xml")
	// @Consumes("application/xml")
	public DebtStatementCustomerInvoiceAssocSearchResult findBy(
			DebtStatementCustomerInvoiceAssocSearchInput searchInput) {
		Entity<DebtStatementCustomerInvoiceAssocSearchInput> searchInputEntity = Entity
				.entity(searchInput, media);
		return target()
				.path(FIND_BY)
				.request(media)
				.post(searchInputEntity,
						DebtStatementCustomerInvoiceAssocSearchResult.class);
	}

	// @POST
	// @Path("/countBy")
	// @Consumes("application/xml")
	public Long countBy(DebtStatementCustomerInvoiceAssocSearchInput searchInput) {
		Entity<DebtStatementCustomerInvoiceAssocSearchInput> searchInputEntity = Entity
				.entity(searchInput, media);
		return target().path("countBy").request()
				.post(searchInputEntity, Long.class);
	}

	// @POST
	// @Path("/findByLike"
	// @Produces("application/xml")
	// @Consumes("application/xml")
	public DebtStatementCustomerInvoiceAssocSearchResult findByLike(
			DebtStatementCustomerInvoiceAssocSearchInput searchInput) {
		Entity<DebtStatementCustomerInvoiceAssocSearchInput> searchInputEntity = Entity
				.entity(searchInput, media);
		return target()
				.path(FIND_BY_LIKE_PATH)
				.request(media)
				.post(searchInputEntity,
						DebtStatementCustomerInvoiceAssocSearchResult.class);
	}

	// @POST
	// @Path("/countByLike"
	// @Consumes("application/xml")
	public Long countByLike(
			DebtStatementCustomerInvoiceAssocSearchInput searchInput) {
		Entity<DebtStatementCustomerInvoiceAssocSearchInput> searchInputEntity = Entity
				.entity(searchInput, media);
		return target().path("countByLike").request()
				.post(searchInputEntity, Long.class);
	}
}
