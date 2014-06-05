package org.adorsys.adpharma.client.jpa.supplier;

import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.adorsys.javafx.crud.extensions.address.ServerAddress;
import org.adorsys.javafx.crud.extensions.login.ClientCookieFilter;

public class SupplierService {
	private String media = MediaType.APPLICATION_JSON;
	private static final String FIND_BY = "findBy";
	private static final String FIND_BY_LIKE_PATH = "findByLike";

	@Inject
	private ClientCookieFilter clientCookieFilter;
	Client client = ClientBuilder.newClient();
	@Inject
	private ServerAddress serverAddress;

	private WebTarget target() {
		return client.target(serverAddress + "/rest/suppliers").register(
				clientCookieFilter);
	}

	public Supplier create(Supplier entity) {
		Entity<Supplier> eCopy = Entity.entity(entity, media);
		return target().request(media).post(eCopy, Supplier.class);
	}

	// @DELETE
	// @Path("/{id:[0-9][0-9]*}")
	public Supplier deleteById(Long id) {// @PathParam("id")
											// TODO encode id
		return target().path("" + id).request(media).delete(Supplier.class);
	}

	// @PUT
	// @Path("/{id:[0-9][0-9]*}")
	// @Consumes("application/xml")
	public Supplier update(Supplier entity) {
		Entity<Supplier> ent = Entity.entity(entity, media);
		return target().path("" + entity.getId()).request(media)
				.put(ent, Supplier.class);
	}

	// @GET
	// @Path("/{id:[0-9][0-9]*}")
	// @Produces("application/xml")
	public Supplier findById(Long id) {// @PathParam("id")
		return target().path("" + id).request(media).get(Supplier.class);
	}

	// @GET
	// @Produces("application/xml")
	public SupplierSearchResult listAll() {
		return target().request(media).get(SupplierSearchResult.class);
	}

	// @GET
	// @Produces("application/xml")
	public SupplierSearchResult listAll(int start, int max) {
		return target().queryParam("start", start).queryParam("max", max)
				.request(media).get(SupplierSearchResult.class);
	}

	// @GET
	// @Path("/count")
	public Long count() {
		return target().path("count").request().get(Long.class);
	}

	// @POST
	// @Produces("application/xml")
	// @Consumes("application/xml")
	public SupplierSearchResult findBy(SupplierSearchInput searchInput) {
		Entity<SupplierSearchInput> searchInputEntity = Entity.entity(
				searchInput, media);
		return target().path(FIND_BY).request(media)
				.post(searchInputEntity, SupplierSearchResult.class);
	}

	// @POST
	// @Path("/countBy")
	// @Consumes("application/xml")
	public Long countBy(SupplierSearchInput searchInput) {
		Entity<SupplierSearchInput> searchInputEntity = Entity.entity(
				searchInput, media);
		return target().path("countBy").request()
				.post(searchInputEntity, Long.class);
	}

	// @POST
	// @Path("/findByLike"
	// @Produces("application/xml")
	// @Consumes("application/xml")
	public SupplierSearchResult findByLike(SupplierSearchInput searchInput) {
		Entity<SupplierSearchInput> searchInputEntity = Entity.entity(
				searchInput, media);
		return target().path(FIND_BY_LIKE_PATH).request(media)
				.post(searchInputEntity, SupplierSearchResult.class);
	}

	// @POST
	// @Path("/countByLike"
	// @Consumes("application/xml")
	public Long countByLike(SupplierSearchInput searchInput) {
		Entity<SupplierSearchInput> searchInputEntity = Entity.entity(
				searchInput, media);
		return target().path("countByLike").request()
				.post(searchInputEntity, Long.class);
	}
}
