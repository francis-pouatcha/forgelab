package org.adorsys.adpharma.client.jpa.disbursement;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.adorsys.javafx.crud.extensions.login.ClientCookieFilter;

public class DisbursementService {
	 private WebTarget target;
	   private String media = MediaType.APPLICATION_JSON;
	   private static final String FIND_BY = "findBy";
	   private static final String FIND_BY_LIKE_PATH = "findByLike";

	   @Inject
	   private ClientCookieFilter clientCookieFilter;

	   public DisbursementService()
	   {
	      Client client = ClientBuilder.newClient();
	      String serverAddress = System.getProperty("server.address");
	      if (serverAddress == null)
	         throw new IllegalStateException("Set system property server address before calling this service. Like: http://localhost:8080/<ContextRoot>");
	      this.target = client.target(serverAddress + "/rest/disbursements");
	   }

	   @PostConstruct
	   protected void postConstruct()
	   {
	      this.target.register(clientCookieFilter);
	   }

	   public Disbursement create(Disbursement entity)
	   {
	      Entity<Disbursement> eCopy = Entity.entity(entity, media);
	      return target.request(media).post(eCopy, Disbursement.class);
	   }

	   // @DELETE
	   // @Path("/{id:[0-9][0-9]*}")
	   public Disbursement deleteById(Long id)
	   {//@PathParam("id")
	      // TODO encode id
	      return target.path("" + id).request(media).delete(Disbursement.class);
	   }

	   // @PUT
	   // @Path("/{id:[0-9][0-9]*}")
	   // @Consumes("application/xml")
	   public Disbursement update(Disbursement entity)
	   {
	      Entity<Disbursement> ent = Entity.entity(entity, media);
	      return target.path("" + entity.getId())
	            .request(media).put(ent, Disbursement.class);
	   }
	   
	// @PUT
	   // @Path("/{id:[0-9][0-9]*}")
	   // @Consumes("application/xml")
	   public Disbursement processCashOut(Disbursement entity)
	   {
	      Entity<Disbursement> ent = Entity.entity(entity, media);
	      return target.path("/processDisbursements")
	            .request(media).put(ent, Disbursement.class);
	   }
	   
	   
	   // @GET
	   // @Path("/{id:[0-9][0-9]*}")
	   // @Produces("application/xml")
	   public Disbursement findById(Long id)
	   {// @PathParam("id") 
	      return target.path("" + id).request(media).get(Disbursement.class);
	   }

	   // @GET
	   // @Produces("application/xml")
	   public DisbursementSearchResult listAll()
	   {
	      return target.request(media).get(DisbursementSearchResult.class);
	   }

	   // @GET
	   // @Produces("application/xml")
	   public DisbursementSearchResult listAll(int start, int max)
	   {
	      return target.queryParam("start", start).queryParam("max", max)
	            .request(media).get(DisbursementSearchResult.class);
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
	   public DisbursementSearchResult findBy(DisbursementSearchInput searchInput)
	   {
	      Entity<DisbursementSearchInput> searchInputEntity = Entity.entity(
	            searchInput, media);
	      return target.path(FIND_BY).request(media).post(
	            searchInputEntity, DisbursementSearchResult.class);
	   }

	   //	@POST
	   //	@Path("/countBy")
	   //	@Consumes("application/xml")
	   public Long countBy(DisbursementSearchInput searchInput)
	   {
	      Entity<DisbursementSearchInput> searchInputEntity = Entity.entity(
	            searchInput, media);
	      return target.path("countBy").request()
	            .post(searchInputEntity, Long.class);
	   }

	   // @POST
	   // @Path("/findByLike"
	   // @Produces("application/xml")
	   // @Consumes("application/xml")
	   public DisbursementSearchResult findByLike(DisbursementSearchInput searchInput)
	   {
	      Entity<DisbursementSearchInput> searchInputEntity = Entity.entity(
	            searchInput, media);
	      return target.path(FIND_BY_LIKE_PATH).request(media).post(
	            searchInputEntity, DisbursementSearchResult.class);
	   }

	   // @POST
	   // @Path("/countByLike"
	   // @Consumes("application/xml")
	   public Long countByLike(DisbursementSearchInput searchInput)
	   {
	      Entity<DisbursementSearchInput> searchInputEntity = Entity.entity(
	            searchInput, media);
	      return target.path("countByLike").request()
	            .post(searchInputEntity, Long.class);
	   }
}
