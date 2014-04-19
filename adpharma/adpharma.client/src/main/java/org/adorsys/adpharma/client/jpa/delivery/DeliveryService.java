package org.adorsys.adpharma.client.jpa.delivery;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

import org.adorsys.javafx.crud.extensions.login.ClientCookieFilter;

public class DeliveryService
{
	private String media = MediaType.APPLICATION_JSON;
	private static final String FIND_BY = "findBy";
	private static final String FIND_BY_LIKE_PATH = "findByLike";

	@Inject
	private ClientCookieFilter clientCookieFilter;

	Client client = ClientBuilder.newClient();
	String serverAddress = System.getProperty("server.address");
	public DeliveryService()
	{
		if (serverAddress == null)
			throw new IllegalStateException("Set system property server address before calling this service. Like: http://localhost:8080/<ContextRoot>");
	}
	private WebTarget target(){
		return client.target(serverAddress + "/rest/deliverys").register(clientCookieFilter);
	}
	@PostConstruct
	protected void postConstruct()
	{
	}

	public Delivery create(Delivery entity)
	{
		Entity<Delivery> eCopy = Entity.entity(entity, media);
		return target().request(media).post(eCopy, Delivery.class);
	}

	// @DELETE
	// @Path("/{id:[0-9][0-9]*}")
	public Delivery deleteById(Long id)
	{//@PathParam("id")
		// TODO encode id
		return target().path("" + id).request(media).delete(Delivery.class);
	}

	// @PUT
	// @Path("/{id:[0-9][0-9]*}")
	// @Consumes("application/xml")
	public Delivery update(Delivery entity)
	{
		Entity<Delivery> ent = Entity.entity(entity, media);
		return target().path("" + entity.getId())
				.request(media).put(ent, Delivery.class);
	}

	//	@PUT
	//	@Path("/saveAndClose")
	//	@Produces({ "application/json", "application/xml" })
	//	@Consumes({ "application/json", "application/xml" })
	public Delivery saveAndClose(Delivery entity) {
		Entity<Delivery> ent = Entity.entity(entity, media);
		return target().path("saveAndClose")
				.request(media).put(ent, Delivery.class);
	}

	// @GET
	// @Path("/{id:[0-9][0-9]*}")
	// @Produces("application/xml")
	public Delivery findById(Long id)
	{// @PathParam("id") 
		return target().path("" + id).request(media).get(Delivery.class);
	}

	// @GET
	// @Produces("application/xml")
	public DeliverySearchResult listAll()
	{
		return target().request(media).get(DeliverySearchResult.class);
	}

	// @GET
	// @Produces("application/xml")
	public DeliverySearchResult listAll(int start, int max)
	{
		return target().queryParam("start", start).queryParam("max", max)
				.request(media).get(DeliverySearchResult.class);
	}

	//	@GET
	//	@Path("/count")
	public Long count()
	{
		return target().path("count").request().get(Long.class);
	}

	// @POST
	// @Produces("application/xml")
	// @Consumes("application/xml")
	public DeliverySearchResult findBy(DeliverySearchInput searchInput)
	{
		Entity<DeliverySearchInput> searchInputEntity = Entity.entity(
				searchInput, media);
		return target().path(FIND_BY).request(media).post(
				searchInputEntity, DeliverySearchResult.class);
	}

	//	@POST
	//	@Path("/countBy")
	//	@Consumes("application/xml")
	public Long countBy(DeliverySearchInput searchInput)
	{
		Entity<DeliverySearchInput> searchInputEntity = Entity.entity(
				searchInput, media);
		return target().path("countBy").request()
				.post(searchInputEntity, Long.class);
	}

	// @POST
	// @Path("/findByLike"
	// @Produces("application/xml")
	// @Consumes("application/xml")
	public DeliverySearchResult findByLike(DeliverySearchInput searchInput)
	{
		Entity<DeliverySearchInput> searchInputEntity = Entity.entity(
				searchInput, media);
		return target().path(FIND_BY_LIKE_PATH).request(media).post(
				searchInputEntity, DeliverySearchResult.class);
	}

	// @POST
	// @Path("/countByLike"
	// @Consumes("application/xml")
	public Long countByLike(DeliverySearchInput searchInput)
	{
		Entity<DeliverySearchInput> searchInputEntity = Entity.entity(
				searchInput, media);
		return target().path("countByLike").request()
				.post(searchInputEntity, Long.class);
	}
}
