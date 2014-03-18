package org.adorsys.adpharma.client.jpa.salesorder;

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

public class SalesOrderService
{
   private WebTarget target;
   private String media = MediaType.APPLICATION_JSON;
   private static final String FIND_BY = "findBy";
   private static final String FIND_BY_LIKE_PATH = "findByLike";

   @Inject
   private ClientCookieFilter clientCookieFilter;

   public SalesOrderService()
   {
      Client client = ClientBuilder.newClient();
      String serverAddress = System.getProperty("server.address");
      if (serverAddress == null)
         throw new IllegalStateException("Set system property server address before calling this service. Like: http://localhost:8080/<ContextRoot>");
      this.target = client.target(serverAddress + "/rest/salesorders");
   }

   @PostConstruct
   protected void postConstruct()
   {
      this.target.register(clientCookieFilter);
   }

   public SalesOrder create(SalesOrder entity)
   {
      Entity<SalesOrder> eCopy = Entity.entity(entity, media);
      return target.request(media).post(eCopy, SalesOrder.class);
   }

   // @DELETE
   // @Path("/{id:[0-9][0-9]*}")
   public SalesOrder deleteById(Long id)
   {//@PathParam("id")
      // TODO encode id
      return target.path("" + id).request(media).delete(SalesOrder.class);
   }

   // @PUT
   // @Path("/{id:[0-9][0-9]*}")
   // @Consumes("application/xml")
   public SalesOrder update(SalesOrder entity)
   {
      Entity<SalesOrder> ent = Entity.entity(entity, media);
      return target.path("" + entity.getId())
            .request(media).put(ent, SalesOrder.class);
   }

   // @GET
   // @Path("/{id:[0-9][0-9]*}")
   // @Produces("application/xml")
   public SalesOrder findById(Long id)
   {// @PathParam("id") 
      return target.path("" + id).request(media).get(SalesOrder.class);
   }

   // @GET
   // @Produces("application/xml")
   public SalesOrderSearchResult listAll()
   {
      return target.request(media).get(SalesOrderSearchResult.class);
   }

   // @GET
   // @Produces("application/xml")
   public SalesOrderSearchResult listAll(int start, int max)
   {
      return target.queryParam("start", start).queryParam("max", max)
            .request(media).get(SalesOrderSearchResult.class);
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
   public SalesOrderSearchResult findBy(SalesOrderSearchInput searchInput)
   {
      Entity<SalesOrderSearchInput> searchInputEntity = Entity.entity(
            searchInput, media);
      return target.path(FIND_BY).request(media).post(
            searchInputEntity, SalesOrderSearchResult.class);
   }

   //	@POST
   //	@Path("/countBy")
   //	@Consumes("application/xml")
   public Long countBy(SalesOrderSearchInput searchInput)
   {
      Entity<SalesOrderSearchInput> searchInputEntity = Entity.entity(
            searchInput, media);
      return target.path("countBy").request()
            .post(searchInputEntity, Long.class);
   }

   // @POST
   // @Path("/findByLike"
   // @Produces("application/xml")
   // @Consumes("application/xml")
   public SalesOrderSearchResult findByLike(SalesOrderSearchInput searchInput)
   {
      Entity<SalesOrderSearchInput> searchInputEntity = Entity.entity(
            searchInput, media);
      return target.path(FIND_BY_LIKE_PATH).request(media).post(
            searchInputEntity, SalesOrderSearchResult.class);
   }

   // @POST
   // @Path("/countByLike"
   // @Consumes("application/xml")
   public Long countByLike(SalesOrderSearchInput searchInput)
   {
      Entity<SalesOrderSearchInput> searchInputEntity = Entity.entity(
            searchInput, media);
      return target.path("countByLike").request()
            .post(searchInputEntity, Long.class);
   }
}