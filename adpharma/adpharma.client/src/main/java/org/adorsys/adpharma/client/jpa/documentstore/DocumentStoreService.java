package org.adorsys.adpharma.client.jpa.documentstore;

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

public class DocumentStoreService
{
   private WebTarget target;
   private String media = MediaType.APPLICATION_JSON;
   private static final String FIND_BY = "findBy";
   private static final String FIND_BY_LIKE_PATH = "findByLike";

   @Inject
   private ClientCookieFilter clientCookieFilter;

   public DocumentStoreService()
   {
      Client client = ClientBuilder.newClient();
      String serverAddress = System.getProperty("server.address");
      if (serverAddress == null)
         throw new IllegalStateException("Set system property server address before calling this service. Like: http://localhost:8080/<ContextRoot>");
      this.target = client.target(serverAddress + "/rest/documentstores");
   }

   @PostConstruct
   protected void postConstruct()
   {
      this.target.register(clientCookieFilter);
   }

   public DocumentStore create(DocumentStore entity)
   {
      Entity<DocumentStore> eCopy = Entity.entity(entity, media);
      return target.request(media).post(eCopy, DocumentStore.class);
   }

   // @DELETE
   // @Path("/{id:[0-9][0-9]*}")
   public DocumentStore deleteById(Long id)
   {//@PathParam("id")
      // TODO encode id
      return target.path("" + id).request(media).delete(DocumentStore.class);
   }

   // @PUT
   // @Path("/{id:[0-9][0-9]*}")
   // @Consumes("application/xml")
   public DocumentStore update(DocumentStore entity)
   {
      Entity<DocumentStore> ent = Entity.entity(entity, media);
      return target.path("" + entity.getId())
            .request(media).put(ent, DocumentStore.class);
   }

   // @GET
   // @Path("/{id:[0-9][0-9]*}")
   // @Produces("application/xml")
   public DocumentStore findById(Long id)
   {// @PathParam("id") 
      return target.path("" + id).request(media).get(DocumentStore.class);
   }

   // @GET
   // @Produces("application/xml")
   public DocumentStoreSearchResult listAll()
   {
      return target.request(media).get(DocumentStoreSearchResult.class);
   }

   // @GET
   // @Produces("application/xml")
   public DocumentStoreSearchResult listAll(int start, int max)
   {
      return target.queryParam("start", start).queryParam("max", max)
            .request(media).get(DocumentStoreSearchResult.class);
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
   public DocumentStoreSearchResult findBy(DocumentStoreSearchInput searchInput)
   {
      Entity<DocumentStoreSearchInput> searchInputEntity = Entity.entity(
            searchInput, media);
      return target.path(FIND_BY).request(media).post(
            searchInputEntity, DocumentStoreSearchResult.class);
   }

   //	@POST
   //	@Path("/countBy")
   //	@Consumes("application/xml")
   public Long countBy(DocumentStoreSearchInput searchInput)
   {
      Entity<DocumentStoreSearchInput> searchInputEntity = Entity.entity(
            searchInput, media);
      return target.path("countBy").request()
            .post(searchInputEntity, Long.class);
   }

   // @POST
   // @Path("/findByLike"
   // @Produces("application/xml")
   // @Consumes("application/xml")
   public DocumentStoreSearchResult findByLike(DocumentStoreSearchInput searchInput)
   {
      Entity<DocumentStoreSearchInput> searchInputEntity = Entity.entity(
            searchInput, media);
      return target.path(FIND_BY_LIKE_PATH).request(media).post(
            searchInputEntity, DocumentStoreSearchResult.class);
   }

   // @POST
   // @Path("/countByLike"
   // @Consumes("application/xml")
   public Long countByLike(DocumentStoreSearchInput searchInput)
   {
      Entity<DocumentStoreSearchInput> searchInputEntity = Entity.entity(
            searchInput, media);
      return target.path("countByLike").request()
            .post(searchInputEntity, Long.class);
   }
}
