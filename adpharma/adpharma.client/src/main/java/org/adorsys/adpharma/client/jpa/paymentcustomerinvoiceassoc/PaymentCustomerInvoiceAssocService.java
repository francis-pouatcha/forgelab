package org.adorsys.adpharma.client.jpa.paymentcustomerinvoiceassoc;

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

public class PaymentCustomerInvoiceAssocService
{
   private WebTarget target;
   private String media = MediaType.APPLICATION_JSON;
   private static final String FIND_BY = "findBy";
   private static final String FIND_BY_LIKE_PATH = "findByLike";

   @Inject
   private ClientCookieFilter clientCookieFilter;

   public PaymentCustomerInvoiceAssocService()
   {
      Client client = ClientBuilder.newClient();
      String serverAddress = System.getProperty("server.address");
      if (serverAddress == null)
         throw new IllegalStateException("Set system property server address before calling this service. Like: http://localhost:8080/<ContextRoot>");
      this.target = client.target(serverAddress + "/rest/paymentcustomerinvoiceassocs");
   }

   @PostConstruct
   protected void postConstruct()
   {
      this.target.register(clientCookieFilter);
   }

   public PaymentCustomerInvoiceAssoc create(PaymentCustomerInvoiceAssoc entity)
   {
      Entity<PaymentCustomerInvoiceAssoc> eCopy = Entity.entity(entity, media);
      return target.request(media).post(eCopy, PaymentCustomerInvoiceAssoc.class);
   }

   // @DELETE
   // @Path("/{id:[0-9][0-9]*}")
   public PaymentCustomerInvoiceAssoc deleteById(Long id)
   {//@PathParam("id")
      // TODO encode id
      return target.path("" + id).request(media).delete(PaymentCustomerInvoiceAssoc.class);
   }

   // @PUT
   // @Path("/{id:[0-9][0-9]*}")
   // @Consumes("application/xml")
   public PaymentCustomerInvoiceAssoc update(PaymentCustomerInvoiceAssoc entity)
   {
      Entity<PaymentCustomerInvoiceAssoc> ent = Entity.entity(entity, media);
      return target.path("" + entity.getId())
            .request(media).put(ent, PaymentCustomerInvoiceAssoc.class);
   }

   // @GET
   // @Path("/{id:[0-9][0-9]*}")
   // @Produces("application/xml")
   public PaymentCustomerInvoiceAssoc findById(Long id)
   {// @PathParam("id") 
      return target.path("" + id).request(media).get(PaymentCustomerInvoiceAssoc.class);
   }

   // @GET
   // @Produces("application/xml")
   public PaymentCustomerInvoiceAssocSearchResult listAll()
   {
      return target.request(media).get(PaymentCustomerInvoiceAssocSearchResult.class);
   }

   // @GET
   // @Produces("application/xml")
   public PaymentCustomerInvoiceAssocSearchResult listAll(int start, int max)
   {
      return target.queryParam("start", start).queryParam("max", max)
            .request(media).get(PaymentCustomerInvoiceAssocSearchResult.class);
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
   public PaymentCustomerInvoiceAssocSearchResult findBy(PaymentCustomerInvoiceAssocSearchInput searchInput)
   {
      Entity<PaymentCustomerInvoiceAssocSearchInput> searchInputEntity = Entity.entity(
            searchInput, media);
      return target.path(FIND_BY).request(media).post(
            searchInputEntity, PaymentCustomerInvoiceAssocSearchResult.class);
   }

   //	@POST
   //	@Path("/countBy")
   //	@Consumes("application/xml")
   public Long countBy(PaymentCustomerInvoiceAssocSearchInput searchInput)
   {
      Entity<PaymentCustomerInvoiceAssocSearchInput> searchInputEntity = Entity.entity(
            searchInput, media);
      return target.path("countBy").request()
            .post(searchInputEntity, Long.class);
   }

   // @POST
   // @Path("/findByLike"
   // @Produces("application/xml")
   // @Consumes("application/xml")
   public PaymentCustomerInvoiceAssocSearchResult findByLike(PaymentCustomerInvoiceAssocSearchInput searchInput)
   {
      Entity<PaymentCustomerInvoiceAssocSearchInput> searchInputEntity = Entity.entity(
            searchInput, media);
      return target.path(FIND_BY_LIKE_PATH).request(media).post(
            searchInputEntity, PaymentCustomerInvoiceAssocSearchResult.class);
   }

   // @POST
   // @Path("/countByLike"
   // @Consumes("application/xml")
   public Long countByLike(PaymentCustomerInvoiceAssocSearchInput searchInput)
   {
      Entity<PaymentCustomerInvoiceAssocSearchInput> searchInputEntity = Entity.entity(
            searchInput, media);
      return target.path("countByLike").request()
            .post(searchInputEntity, Long.class);
   }
}
