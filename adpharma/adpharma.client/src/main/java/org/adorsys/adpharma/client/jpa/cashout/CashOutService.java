package org.adorsys.adpharma.client.jpa.cashout;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.adorsys.javafx.crud.extensions.login.ClientCookieFilter;

public class CashOutService {
	 private WebTarget target;
	   private String media = MediaType.APPLICATION_JSON;
	   private static final String FIND_BY = "findBy";
	   private static final String FIND_BY_LIKE_PATH = "findByLike";

	   @Inject
	   private ClientCookieFilter clientCookieFilter;

	   public CashOutService()
	   {
	      Client client = ClientBuilder.newClient();
	      String serverAddress = System.getProperty("server.address");
	      if (serverAddress == null)
	         throw new IllegalStateException("Set system property server address before calling this service. Like: http://localhost:8080/<ContextRoot>");
	      this.target = client.target(serverAddress + "/rest/cashout");
	   }

	   @PostConstruct
	   protected void postConstruct()
	   {
	      this.target.register(clientCookieFilter);
	   }

	   public CashOut create(CashOut entity)
	   {
	      Entity<CashOut> eCopy = Entity.entity(entity, media);
	      return target.request(media).post(eCopy, CashOut.class);
	   }

	   // @DELETE
	   // @Path("/{id:[0-9][0-9]*}")
	   public CashOut deleteById(Long id)
	   {//@PathParam("id")
	      // TODO encode id
	      return target.path("" + id).request(media).delete(CashOut.class);
	   }

	   // @PUT
	   // @Path("/{id:[0-9][0-9]*}")
	   // @Consumes("application/xml")
	   public CashOut update(CashOut entity)
	   {
	      Entity<CashOut> ent = Entity.entity(entity, media);
	      return target.path("" + entity.getId())
	            .request(media).put(ent, CashOut.class);
	   }
	   
	// @PUT
	   // @Path("/{id:[0-9][0-9]*}")
	   // @Consumes("application/xml")
	   public CashOut processCashOut(CashOut entity)
	   {
	      Entity<CashOut> ent = Entity.entity(entity, media);
	      return target.path("/processCashOut")
	            .request(media).put(ent, CashOut.class);
	   }
	   
	   
	   // @GET
	   // @Path("/{id:[0-9][0-9]*}")
	   // @Produces("application/xml")
	   public CashOut findById(Long id)
	   {// @PathParam("id") 
	      return target.path("" + id).request(media).get(CashOut.class);
	   }

	   // @GET
	   // @Produces("application/xml")
	   public CashOutSearchResult listAll()
	   {
	      return target.request(media).get(CashOutSearchResult.class);
	   }

	   // @GET
	   // @Produces("application/xml")
	   public CashOutSearchResult listAll(int start, int max)
	   {
	      return target.queryParam("start", start).queryParam("max", max)
	            .request(media).get(CashOutSearchResult.class);
	   }

	   //	@GET
	   //	@Path("/count")
	   public Long count()
	   {
	      return target.path("count").request().get(Long.class);
	   }

	   // @POST
	   // @Produces("application/xml")
	   // @Consumes("application/xml")
	   public CashOutSearchResult findBy(CashOutSearchInput searchInput)
	   {
	      Entity<CashOutSearchInput> searchInputEntity = Entity.entity(
	            searchInput, media);
	      return target.path(FIND_BY).request(media).post(
	            searchInputEntity, CashOutSearchResult.class);
	   }

	   //	@POST
	   //	@Path("/countBy")
	   //	@Consumes("application/xml")
	   public Long countBy(CashOutSearchInput searchInput)
	   {
	      Entity<CashOutSearchInput> searchInputEntity = Entity.entity(
	            searchInput, media);
	      return target.path("countBy").request()
	            .post(searchInputEntity, Long.class);
	   }

	   // @POST
	   // @Path("/findByLike"
	   // @Produces("application/xml")
	   // @Consumes("application/xml")
	   public CashOutSearchResult findByLike(CashOutSearchInput searchInput)
	   {
	      Entity<CashOutSearchInput> searchInputEntity = Entity.entity(
	            searchInput, media);
	      return target.path(FIND_BY_LIKE_PATH).request(media).post(
	            searchInputEntity, CashOutSearchResult.class);
	   }

	   // @POST
	   // @Path("/countByLike"
	   // @Consumes("application/xml")
	   public Long countByLike(CashOutSearchInput searchInput)
	   {
	      Entity<CashOutSearchInput> searchInputEntity = Entity.entity(
	            searchInput, media);
	      return target.path("countByLike").request()
	            .post(searchInputEntity, Long.class);
	   }
}
