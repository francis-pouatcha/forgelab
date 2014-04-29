package org.adorsys.adpharma.client.jpa.articlelot;

import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.adorsys.javafx.crud.extensions.address.ServerAddress;
import org.adorsys.javafx.crud.extensions.login.ClientCookieFilter;

public class ArticleLotService
{
   private String media = MediaType.APPLICATION_JSON;
   private static final String FIND_BY = "findBy";
   private static final String FIND_BY_LIKE_PATH = "findByLike";

   @Inject
   private ClientCookieFilter clientCookieFilter;

	@Inject
	private ServerAddress serverAddress;
	Client client = ClientBuilder.newClient();
   
   private WebTarget target()
   {
	   return client.target(serverAddress + "/rest/articlelots").register(clientCookieFilter);
   }

   public ArticleLot create(ArticleLot entity)
   {
      Entity<ArticleLot> eCopy = Entity.entity(entity, media);
      return target().request(media).post(eCopy, ArticleLot.class);
   }

   // @DELETE
   // @Path("/{id:[0-9][0-9]*}")
   public ArticleLot deleteById(Long id)
   {//@PathParam("id")
      // TODO encode id
      return target().path("" + id).request(media).delete(ArticleLot.class);
   }

   // @PUT
   // @Path("/{id:[0-9][0-9]*}")
   // @Consumes("application/xml")
   public ArticleLot update(ArticleLot entity)
   {
      Entity<ArticleLot> ent = Entity.entity(entity, media);
      return target().path("" + entity.getId())
            .request(media).put(ent, ArticleLot.class);
   }

   // @GET
   // @Path("/{id:[0-9][0-9]*}")
   // @Produces("application/xml")
   public ArticleLot findById(Long id)
   {// @PathParam("id") 
      return target().path("" + id).request(media).get(ArticleLot.class);
   }

   // @GET
   // @Produces("application/xml")
   public ArticleLotSearchResult listAll()
   {
      return target().request(media).get(ArticleLotSearchResult.class);
   }

   // @GET
   // @Produces("application/xml")
   public ArticleLotSearchResult listAll(int start, int max)
   {
      return target().queryParam("start", start).queryParam("max", max)
            .request(media).get(ArticleLotSearchResult.class);
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
   public ArticleLotSearchResult findBy(ArticleLotSearchInput searchInput)
   {
      Entity<ArticleLotSearchInput> searchInputEntity = Entity.entity(
            searchInput, media);
      return target().path(FIND_BY).request(media).post(
            searchInputEntity, ArticleLotSearchResult.class);
   }
   
   //	@POST
   //	@Path("/processDetails")
   //	@Consumes("application/xml")
   public ArticleLot processDetails(ArticleLotDetailsManager lotDetailsManager)
   {
      Entity<ArticleLotDetailsManager> articleLotDetailsManagerEntity = Entity.entity(
            lotDetailsManager, media);
      return target().path("processDetails").request()
            .post(articleLotDetailsManagerEntity, ArticleLot.class);
   }


   //	@POST
   //	@Path("/countBy")
   //	@Consumes("application/xml")
   public Long countBy(ArticleLotSearchInput searchInput)
   {
      Entity<ArticleLotSearchInput> searchInputEntity = Entity.entity(
            searchInput, media);
      return target().path("countBy").request()
            .post(searchInputEntity, Long.class);
   }

   // @POST
   // @Path("/findByLike"
   // @Produces("application/xml")
   // @Consumes("application/xml")
   public ArticleLotSearchResult findByLike(ArticleLotSearchInput searchInput)
   {
      Entity<ArticleLotSearchInput> searchInputEntity = Entity.entity(
            searchInput, media);
      return target().path(FIND_BY_LIKE_PATH).request(media).post(
            searchInputEntity, ArticleLotSearchResult.class);
   }

   // @POST
   // @Path("/countByLike"
   // @Consumes("application/xml")
   public Long countByLike(ArticleLotSearchInput searchInput)
   {
      Entity<ArticleLotSearchInput> searchInputEntity = Entity.entity(
            searchInput, media);
      return target().path("countByLike").request()
            .post(searchInputEntity, Long.class);
   }

   // @POST
   // @Path("/findLots"
   // @Produces("application/xml")
   // @Consumes("application/xml")
   public ArticleLotSearchResult findLots(ArticleLotSearchInput searchInput)
   {
      Entity<ArticleLotSearchInput> searchInputEntity = Entity.entity(
            searchInput, media);
      return target().path("/findLots").request(media).post(
            searchInputEntity, ArticleLotSearchResult.class);
   }
}
