package org.adorsys.adpharma.client.jpa.salesorder;

import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.adorsys.adpharma.client.jpa.customer.Customer;
import org.adorsys.javafx.crud.extensions.address.ServerAddress;
import org.adorsys.javafx.crud.extensions.login.ClientCookieFilter;

public class SalesOrderService {
	// private WebTarget target;
	private String media = MediaType.APPLICATION_JSON;
	private static final String FIND_BY = "findBy";
	private static final String FIND_BY_LIKE_PATH = "findByLike";
	private static final String SAVE_AND_CLOSE_PATH = "saveAndClose/";
	private static final String CANCEL_PATH = "cancel/";

	@Inject
	private ClientCookieFilter clientCookieFilter;

	Client client = ClientBuilder.newClient();
	@Inject
	private ServerAddress serverAddress;

	private WebTarget target() {
		return client.target(serverAddress + "/rest/salesorders").register(
				clientCookieFilter);
	}

	public SalesOrder create(SalesOrder entity) {
		Entity<SalesOrder> eCopy = Entity.entity(entity, media);
		return target().request(media).post(eCopy, SalesOrder.class);
	}


	//	@GET
	//	@Path("/isManagedLot")
	//	@Produces({ "application/json", "application/xml" })
	public Boolean isManagedLot()
	{
		return target().path("isManagedLot").request(media).get(Boolean.class);

	}

	// @DELETE
	// @Path("/{id:[0-9][0-9]*}")
	public SalesOrder deleteById(Long id) {// @PathParam("id")
		// TODO encode id
		return target().path("" + id).request(media).delete(SalesOrder.class);
	}

	// @PUT
	// @Path("/{id:[0-9][0-9]*}")
	// @Consumes("application/xml")
	public SalesOrder update(SalesOrder entity) {
		Entity<SalesOrder> ent = Entity.entity(entity, media);
		return target().path("" + entity.getId()).request(media)
				.put(ent, SalesOrder.class);
	}

	// @PUT
	// @Path("/{id:[0-9][0-9]*}")
	// @Consumes("application/xml")
	public SalesOrder changeCustomer(Long salesId ,Customer customer) {
		Entity<Customer> ent = Entity.entity(customer, media);
		return target().path("changeCustomer/" + salesId).request(media)
				.put(ent, SalesOrder.class);
	}

	// @PUT
	// @Path("/saveAndClose/{id:[0-9][0-9]*}")
	// @Consumes("application/xml")
	public SalesOrder saveAndClose(SalesOrder entity) {
		Entity<SalesOrder> ent = Entity.entity(entity, media);
		return target().path(SAVE_AND_CLOSE_PATH + entity.getId())
				.request(media).put(ent, SalesOrder.class);
	}

	// @GET
	// @Path("/{id:[0-9][0-9]*}")
	// @Produces("application/xml")
	public SalesOrder findById(Long id) {// @PathParam("id")
		return target().path("" + id).request(media).get(SalesOrder.class);
	}

	// @GET
	// @Produces("application/xml")
	public SalesOrderSearchResult listAll() {
		return target().request(media).get(SalesOrderSearchResult.class);
	}

	// @GET
	// @Produces("application/xml")
	public SalesOrderSearchResult listAll(int start, int max) {
		return target().queryParam("start", start).queryParam("max", max)
				.request(media).get(SalesOrderSearchResult.class);
	}

	// @GET
	// @Path("/count")
	public Long count() {
		return target().path("count").request().get(Long.class);
	}

	// @POST
	// @Produces("application/xml")
	// @Consumes("application/xml")
	public SalesOrderSearchResult findBy(SalesOrderSearchInput searchInput) {
		Entity<SalesOrderSearchInput> searchInputEntity = Entity.entity(
				searchInput, media);
		return target().path(FIND_BY).request(media)
				.post(searchInputEntity, SalesOrderSearchResult.class);
	}

	// @POST
	// @Path("/countBy")
	// @Consumes("application/xml")
	public Long countBy(SalesOrderSearchInput searchInput) {
		Entity<SalesOrderSearchInput> searchInputEntity = Entity.entity(
				searchInput, media);
		return target().path("countBy").request()
				.post(searchInputEntity, Long.class);
	}

	// @POST
	// @Path("/findByLike"
	// @Produces("application/xml")
	// @Consumes("application/xml")
	public SalesOrderSearchResult findByLike(SalesOrderSearchInput searchInput) {
		Entity<SalesOrderSearchInput> searchInputEntity = Entity.entity(
				searchInput, media);
		return target().path(FIND_BY_LIKE_PATH).request(media)
				.post(searchInputEntity, SalesOrderSearchResult.class);
	}

	// @POST
	// @Path("/countByLike"
	// @Consumes("application/xml")
	public Long countByLike(SalesOrderSearchInput searchInput) {
		Entity<SalesOrderSearchInput> searchInputEntity = Entity.entity(
				searchInput, media);
		return target().path("countByLike").request()
				.post(searchInputEntity, Long.class);
	}

	// @PUT
	// @Path("/cancel/{id:[0-9][0-9]*}")
	// @Consumes("application/xml")
	public SalesOrder cancel(SalesOrder entity) {
		Entity<SalesOrder> ent = Entity.entity(entity, media);
		return target().path(CANCEL_PATH + entity.getId()).request(media)
				.put(ent, SalesOrder.class);
	}

	// @PUT
	// @Path("/processReturn")
	// @Produces({ "application/json", "application/xml" })
	// @Consumes({ "application/json", "application/xml" })
	public SalesOrder processReturn(SalesOrder entity) {
		Entity<SalesOrder> ent = Entity.entity(entity, media);
		return target().path("processReturn").request(media)
				.put(ent, SalesOrder.class);
	}

}
