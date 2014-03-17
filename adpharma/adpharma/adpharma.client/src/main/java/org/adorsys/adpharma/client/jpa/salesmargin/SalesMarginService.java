package org.adorsys.adpharma.client.jpa.salesmargin;

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

public class SalesMarginService
{
   private WebTarget target;
   private String media = MediaType.APPLICATION_JSON;
   private static final String FIND_BY = "findBy";
   private static final String FIND_BY_LIKE_PATH = "findByLike";

   @Inject
   private ClientCookieFilter clientCookieFilter;

   public SalesMarginService()
   {
      Client client = ClientBuilder.newClient();
      String serverAddress = System.getProperty("server.address");
      if (serverAddress == null)
         throw new IllegalStateException("Set system property server address before calling this service. Like: http://localhost:8080/<ContextRoot>");
      this.target = client.target(serverAddress + "/rest/salesmargins");
   }

   @PostConstruct
   protected void postConstruct()
   {
      this.target.register(clientCookieFilter);
   }

   public SalesMargin create(SalesMargin entity)
   {
      Entity<SalesMargin> eCopy = Entity.entity(entity, media);
      return target.request(media).post(eCopy, SalesMargin.class);
   }

   // @DELETE
   // @Path("/{id:[0-9][0-9]*}")
   public SalesMargin deleteById(Long id)
   {//@PathParam("id")
      // TODO encode id
      return target.path("" + id).request(media).delete(SalesMargin.class);
   }

   // @PUT
   // @Path("/{id:[0-9][0-9]*}")
   // @Consumes("application/xml")
   public SalesMargin update(SalesMargin entity)
   {
      Entity<SalesMargin> ent = Entity.entity(entity, media);
      return target.path("" + entity.getId())
            .request(media).put(ent, SalesMargin.class);
   }

   // @GET
   // @Path("/{id:[0-9][0-9]*}")
   // @Produces("application/xml")
   public SalesMargin findById(Long id)
   {// @PathParam("id") 
      return target.path("" + id).request(media).get(SalesMargin.class);
   }

   // @GET
   // @Produces("application/xml")
   public SalesMarginSearchResult listAll()
   {
      return target.request(media).get(SalesMarginSearchResult.class);
   }

   // @GET
   // @Produces("application/xml")
   public SalesMarginSearchResult listAll(int start, int max)
   {
      return target.queryParam("start", start).queryParam("max", max)
            .request(media).get(SalesMarginSearchResult.class);
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
   public SalesMarginSearchResult findBy(SalesMarginSearchInput searchInput)
   {
      Entity<SalesMarginSearchInput> searchInputEntity = Entity.entity(
            searchInput, media);
      return target.path(FIND_BY).request(media).post(
            searchInputEntity, SalesMarginSearchResult.class);
   }

   //	@POST
   //	@Path("/countBy")
   //	@Consumes("application/xml")
   public Long countBy(SalesMarginSearchInput searchInput)
   {
      Entity<SalesMarginSearchInput> searchInputEntity = Entity.entity(
            searchInput, media);
      return target.path("countBy").request()
            .post(searchInputEntity, Long.class);
   }

   // @POST
   // @Path("/findByLike"
   // @Produces("application/xml")
   // @Consumes("application/xml")
   public SalesMarginSearchResult findByLike(SalesMarginSearchInput searchInput)
   {
      Entity<SalesMarginSearchInput> searchInputEntity = Entity.entity(
            searchInput, media);
      return target.path(FIND_BY_LIKE_PATH).request(media).post(
            searchInputEntity, SalesMarginSearchResult.class);
   }

   // @POST
   // @Path("/countByLike"
   // @Consumes("application/xml")
   public Long countByLike(SalesMarginSearchInput searchInput)
   {
      Entity<SalesMarginSearchInput> searchInputEntity = Entity.entity(
            searchInput, media);
      return target.path("countByLike").request()
            .post(searchInputEntity, Long.class);
   }
}
