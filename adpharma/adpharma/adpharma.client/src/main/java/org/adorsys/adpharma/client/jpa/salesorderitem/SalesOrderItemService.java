package org.adorsys.adpharma.client.jpa.salesorderitem;

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

public class SalesOrderItemService
{
   private WebTarget target;
   private String media = MediaType.APPLICATION_JSON;
   private static final String FIND_BY = "findBy";
   private static final String FIND_BY_LIKE_PATH = "findByLike";

   @Inject
   private ClientCookieFilter clientCookieFilter;

   public SalesOrderItemService()
   {
      Client client = ClientBuilder.newClient();
      String serverAddress = System.getProperty("server.address");
      if (serverAddress == null)
         throw new IllegalStateException("Set system property server address before calling this service. Like: http://localhost:8080/<ContextRoot>");
      this.target = client.target(serverAddress + "/rest/salesorderitems");
   }

   @PostConstruct
   protected void postConstruct()
   {
      this.target.register(clientCookieFilter);
   }

   public SalesOrderItem create(SalesOrderItem entity)
   {
      Entity<SalesOrderItem> eCopy = Entity.entity(entity, media);
      return target.request(media).post(eCopy, SalesOrderItem.class);
   }

   // @DELETE
   // @Path("/{id:[0-9][0-9]*}")
   public SalesOrderItem deleteById(Long id)
   {//@PathParam("id")
      // TODO encode id
      return target.path("" + id).request(media).delete(SalesOrderItem.class);
   }

   // @PUT
   // @Path("/{id:[0-9][0-9]*}")
   // @Consumes("application/xml")
   public SalesOrderItem update(SalesOrderItem entity)
   {
      Entity<SalesOrderItem> ent = Entity.entity(entity, media);
      return target.path("" + entity.getId())
            .request(media).put(ent, SalesOrderItem.class);
   }

   // @GET
   // @Path("/{id:[0-9][0-9]*}")
   // @Produces("application/xml")
   public SalesOrderItem findById(Long id)
   {// @PathParam("id") 
      return target.path("" + id).request(media).get(SalesOrderItem.class);
   }

   // @GET
   // @Produces("application/xml")
   public SalesOrderItemSearchResult listAll()
   {
      return target.request(media).get(SalesOrderItemSearchResult.class);
   }

   // @GET
   // @Produces("application/xml")
   public SalesOrderItemSearchResult listAll(int start, int max)
   {
      return target.queryParam("start", start).queryParam("max", max)
            .request(media).get(SalesOrderItemSearchResult.class);
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
   public SalesOrderItemSearchResult findBy(SalesOrderItemSearchInput searchInput)
   {
      Entity<SalesOrderItemSearchInput> searchInputEntity = Entity.entity(
            searchInput, media);
      return target.path(FIND_BY).request(media).post(
            searchInputEntity, SalesOrderItemSearchResult.class);
   }

   //	@POST
   //	@Path("/countBy")
   //	@Consumes("application/xml")
   public Long countBy(SalesOrderItemSearchInput searchInput)
   {
      Entity<SalesOrderItemSearchInput> searchInputEntity = Entity.entity(
            searchInput, media);
      return target.path("countBy").request()
            .post(searchInputEntity, Long.class);
   }

   // @POST
   // @Path("/findByLike"
   // @Produces("application/xml")
   // @Consumes("application/xml")
   public SalesOrderItemSearchResult findByLike(SalesOrderItemSearchInput searchInput)
   {
      Entity<SalesOrderItemSearchInput> searchInputEntity = Entity.entity(
            searchInput, media);
      return target.path(FIND_BY_LIKE_PATH).request(media).post(
            searchInputEntity, SalesOrderItemSearchResult.class);
   }

   // @POST
   // @Path("/countByLike"
   // @Consumes("application/xml")
   public Long countByLike(SalesOrderItemSearchInput searchInput)
   {
      Entity<SalesOrderItemSearchInput> searchInputEntity = Entity.entity(
            searchInput, media);
      return target.path("countByLike").request()
            .post(searchInputEntity, Long.class);
   }
}
