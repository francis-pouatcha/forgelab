package org.adorsys.adpharma.client.jpa.payment;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.adorsys.adpharma.client.jpa.customerinvoice.CustomerInvoice;
import org.adorsys.javafx.crud.extensions.login.ClientCookieFilter;

public class PaymentService
{
   private WebTarget target;
   private String media = MediaType.APPLICATION_JSON;
   private static final String FIND_BY = "findBy";
   private static final String FIND_BY_LIKE_PATH = "findByLike";

   @Inject
   private ClientCookieFilter clientCookieFilter;
   Client client = ClientBuilder.newClient();
   String serverAddress = System.getProperty("server.address");

   public PaymentService()
   {
      if (serverAddress == null)
         throw new IllegalStateException("Set system property server address before calling this service. Like: http://localhost:8080/<ContextRoot>");
   }

   @PostConstruct
   protected void postConstruct()
   {
   }

   private WebTarget target(){
	   return client.target(serverAddress + "/rest/payments").register(clientCookieFilter);
   }
   public Payment create(Payment entity)
   {
      Entity<Payment> eCopy = Entity.entity(entity, media);
      return target().request(media).post(eCopy, Payment.class);
   }

   // @DELETE
   // @Path("/{id:[0-9][0-9]*}")
   public Payment deleteById(Long id)
   {//@PathParam("id")
      // TODO encode id
      return target().path("" + id).request(media).delete(Payment.class);
   }

   // @PUT
   // @Path("/{id:[0-9][0-9]*}")
   // @Consumes("application/xml")
   public Payment update(Payment entity)
   {
      Entity<Payment> ent = Entity.entity(entity, media);
      return target().path("" + entity.getId())
            .request(media).put(ent, Payment.class);
   }
   
  

   // @GET
   // @Path("/{id:[0-9][0-9]*}")
   // @Produces("application/xml")
   public Payment findById(Long id)
   {// @PathParam("id") 
      return target().path("" + id).request(media).get(Payment.class);
   }

   // @GET
   // @Produces("application/xml")
   public PaymentSearchResult listAll()
   {
      return target().request(media).get(PaymentSearchResult.class);
   }

   // @GET
   // @Produces("application/xml")
   public PaymentSearchResult listAll(int start, int max)
   {
      return target().queryParam("start", start).queryParam("max", max)
            .request(media).get(PaymentSearchResult.class);
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
   public PaymentSearchResult findBy(PaymentSearchInput searchInput)
   {
      Entity<PaymentSearchInput> searchInputEntity = Entity.entity(
            searchInput, media);
      return target().path(FIND_BY).request(media).post(
            searchInputEntity, PaymentSearchResult.class);
   }

   //	@POST
   //	@Path("/countBy")
   //	@Consumes("application/xml")
   public Long countBy(PaymentSearchInput searchInput)
   {
      Entity<PaymentSearchInput> searchInputEntity = Entity.entity(
            searchInput, media);
      return target().path("countBy").request()
            .post(searchInputEntity, Long.class);
   }

   // @POST
   // @Path("/findByLike"
   // @Produces("application/xml")
   // @Consumes("application/xml")
   public PaymentSearchResult findByLike(PaymentSearchInput searchInput)
   {
      Entity<PaymentSearchInput> searchInputEntity = Entity.entity(
            searchInput, media);
      return target().path(FIND_BY_LIKE_PATH).request(media).post(
            searchInputEntity, PaymentSearchResult.class);
   }

   // @POST
   // @Path("/countByLike"
   // @Consumes("application/xml")
   public Long countByLike(PaymentSearchInput searchInput)
   {
      Entity<PaymentSearchInput> searchInputEntity = Entity.entity(
            searchInput, media);
      return target().path("countByLike").request()
            .post(searchInputEntity, Long.class);
   }
   
//   @PUT
// @Path("/customerPayment/{id:[0-9][0-9]*}")
//   @Produces({ "application/json", "application/xml" })
//   @Consumes({ "application/json", "application/xml" })
//   public Payment customerPayment(Long id , List<CustomerInvoice> customerInvoices)
//   {
//      Entity<List<CustomerInvoice>> ent = Entity.entity(customerInvoices, media);
//      return target().path("customerPayment/"+id)
//            .request(media).put(ent, Payment.class);
//   }   
}
