package org.adorsys.adpharma.client.jpa.hospital;

import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.adorsys.javafx.crud.extensions.address.ServerAddress;
import org.adorsys.javafx.crud.extensions.login.ClientCookieFilter;

public class HospitalService
{
   private String media = MediaType.APPLICATION_JSON;
   private static final String FIND_BY = "findBy";
   private static final String FIND_BY_LIKE_PATH = "findByLike";

   @Inject
   private ClientCookieFilter clientCookieFilter;
   Client client = ClientBuilder.newClient();
	@Inject
	private ServerAddress serverAddress;
	
	private WebTarget target()
   {
      return client.target(serverAddress + "/rest/hospitals").register(clientCookieFilter);
   }

   public Hospital create(Hospital entity)
   {
      Entity<Hospital> eCopy = Entity.entity(entity, media);
      return target().request(media).post(eCopy, Hospital.class);
   }

   // @DELETE
   // @Path("/{id:[0-9][0-9]*}")
   public Hospital deleteById(Long id)
   {//@PathParam("id")
      // TODO encode id
      return target().path("" + id).request(media).delete(Hospital.class);
   }

   // @PUT
   // @Path("/{id:[0-9][0-9]*}")
   // @Consumes("application/xml")
   public Hospital update(Hospital entity)
   {
      Entity<Hospital> ent = Entity.entity(entity, media);
      return target().path("" + entity.getId())
            .request(media).put(ent, Hospital.class);
   }

   // @GET
   // @Path("/{id:[0-9][0-9]*}")
   // @Produces("application/xml")
   public Hospital findById(Long id)
   {// @PathParam("id") 
      return target().path("" + id).request(media).get(Hospital.class);
   }

   // @GET
   // @Produces("application/xml")
   public HospitalSearchResult listAll()
   {
      return target().request(media).get(HospitalSearchResult.class);
   }

   // @GET
   // @Produces("application/xml")
   public HospitalSearchResult listAll(int start, int max)
   {
      return target().queryParam("start", start).queryParam("max", max)
            .request(media).get(HospitalSearchResult.class);
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
   public HospitalSearchResult findBy(HospitalSearchInput searchInput)
   {
      Entity<HospitalSearchInput> searchInputEntity = Entity.entity(
            searchInput, media);
      return target().path(FIND_BY).request(media).post(
            searchInputEntity, HospitalSearchResult.class);
   }

   //	@POST
   //	@Path("/countBy")
   //	@Consumes("application/xml")
   public Long countBy(HospitalSearchInput searchInput)
   {
      Entity<HospitalSearchInput> searchInputEntity = Entity.entity(
            searchInput, media);
      return target().path("countBy").request()
            .post(searchInputEntity, Long.class);
   }

   // @POST
   // @Path("/findByLike"
   // @Produces("application/xml")
   // @Consumes("application/xml")
   public HospitalSearchResult findByLike(HospitalSearchInput searchInput)
   {
      Entity<HospitalSearchInput> searchInputEntity = Entity.entity(
            searchInput, media);
      return target().path(FIND_BY_LIKE_PATH).request(media).post(
            searchInputEntity, HospitalSearchResult.class);
   }

   // @POST
   // @Path("/countByLike"
   // @Consumes("application/xml")
   public Long countByLike(HospitalSearchInput searchInput)
   {
      Entity<HospitalSearchInput> searchInputEntity = Entity.entity(
            searchInput, media);
      return target().path("countByLike").request()
            .post(searchInputEntity, Long.class);
   }
}
