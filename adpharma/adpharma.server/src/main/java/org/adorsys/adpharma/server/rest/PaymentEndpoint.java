package org.adorsys.adpharma.server.rest;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.adorsys.adpharma.server.jpa.Payment;
import org.adorsys.adpharma.server.jpa.PaymentSearchInput;
import org.adorsys.adpharma.server.jpa.PaymentSearchResult;
import org.adorsys.adpharma.server.jpa.Payment_;

/**
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/payments")
public class PaymentEndpoint
{

   @Inject
   private PaymentEJB ejb;

   @Inject
   private LoginMerger loginMerger;

   @Inject
   private CustomerMerger customerMerger;

   @Inject
   private CashDrawerMerger cashDrawerMerger;

   @Inject
   private PaymentItemMerger paymentItemMerger;

   @Inject
   private PaymentCustomerInvoiceAssocMerger paymentCustomerInvoiceAssocMerger;

   @Inject
   private AgencyMerger agencyMerger;

   @POST
   @Consumes({ "application/json", "application/xml" })
   @Produces({ "application/json", "application/xml" })
   public Payment create(Payment entity)
   {
      return detach(ejb.create(entity));
   }

   @DELETE
   @Path("/{id:[0-9][0-9]*}")
   public Response deleteById(@PathParam("id") Long id)
   {
      Payment deleted = ejb.deleteById(id);
      if (deleted == null)
         return Response.status(Status.NOT_FOUND).build();

      return Response.ok(detach(deleted)).build();
   }

   @PUT
   @Path("/{id:[0-9][0-9]*}")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public Payment update(Payment entity)
   {
      return detach(ejb.update(entity));
   }

   @GET
   @Path("/{id:[0-9][0-9]*}")
   @Produces({ "application/json", "application/xml" })
   public Response findById(@PathParam("id") Long id)
   {
      Payment found = ejb.findById(id);
      if (found == null)
         return Response.status(Status.NOT_FOUND).build();
      return Response.ok(detach(found)).build();
   }

   @GET
   @Produces({ "application/json", "application/xml" })
   public PaymentSearchResult listAll(@QueryParam("start") int start,
         @QueryParam("max") int max)
   {
      List<Payment> resultList = ejb.listAll(start, max);
      PaymentSearchInput searchInput = new PaymentSearchInput();
      searchInput.setStart(start);
      searchInput.setMax(max);
      return new PaymentSearchResult((long) resultList.size(),
            detach(resultList), detach(searchInput));
   }

   @GET
   @Path("/count")
   public Long count()
   {
      return ejb.count();
   }

   @POST
   @Path("/findBy")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public PaymentSearchResult findBy(PaymentSearchInput searchInput)
   {
      SingularAttribute<Payment, ?>[] attributes = readSeachAttributes(searchInput);
      Long count = ejb.countBy(searchInput.getEntity(), attributes);
      List<Payment> resultList = ejb.findBy(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new PaymentSearchResult(count, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countBy")
   @Consumes({ "application/json", "application/xml" })
   public Long countBy(PaymentSearchInput searchInput)
   {
      SingularAttribute<Payment, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countBy(searchInput.getEntity(), attributes);
   }

   @POST
   @Path("/findByLike")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public PaymentSearchResult findByLike(PaymentSearchInput searchInput)
   {
      SingularAttribute<Payment, ?>[] attributes = readSeachAttributes(searchInput);
      Long countLike = ejb.countByLike(searchInput.getEntity(), attributes);
      List<Payment> resultList = ejb.findByLike(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new PaymentSearchResult(countLike, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countByLike")
   @Consumes({ "application/json", "application/xml" })
   public Long countByLike(PaymentSearchInput searchInput)
   {
      SingularAttribute<Payment, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countByLike(searchInput.getEntity(), attributes);
   }

   @SuppressWarnings("unchecked")
   private SingularAttribute<Payment, ?>[] readSeachAttributes(
         PaymentSearchInput searchInput)
   {
      List<String> fieldNames = searchInput.getFieldNames();
      List<SingularAttribute<Payment, ?>> result = new ArrayList<SingularAttribute<Payment, ?>>();
      for (String fieldName : fieldNames)
      {
         Field[] fields = Payment_.class.getFields();
         for (Field field : fields)
         {
            if (field.getName().equals(fieldName))
            {
               try
               {
                  result.add((SingularAttribute<Payment, ?>) field.get(null));
               }
               catch (IllegalArgumentException e)
               {
                  throw new IllegalStateException(e);
               }
               catch (IllegalAccessException e)
               {
                  throw new IllegalStateException(e);
               }
            }
         }
      }
      return result.toArray(new SingularAttribute[result.size()]);
   }

   private static final List<String> emptyList = Collections.emptyList();

   private static final List<String> agencyFields = Arrays.asList("agencyNumber", "name", "active", "name", "name", "phone", "fax");

   private static final List<String> cashierFields = Arrays.asList("loginName", "email", "gender");

   private static final List<String> cashDrawerFields = Arrays.asList("cashDrawerNumber", "agency.name", "openingDate", "closingDate", "initialAmount", "totalCashIn", "totalCashOut", "totalCash", "totalCheck", "totalCreditCard", "totalCompanyVoucher", "totalClientVoucher", "opened");

   private static final List<String> paidByFields = Arrays.asList("fullName", "birthDate", "landLinePhone", "mobile", "fax", "email", "creditAuthorized", "discountAuthorized");

   private static final List<String> paymentItemsFields = Arrays.asList("paymentMode", "documentNumber", "amount", "receivedAmount", "paidBy.fullName");

   private static final List<String> invoicesFields = emptyList;

   private Payment detach(Payment entity)
   {
      if (entity == null)
         return null;

      // aggregated
      entity.setAgency(agencyMerger.unbind(entity.getAgency(), agencyFields));

      // aggregated
      entity.setCashier(loginMerger.unbind(entity.getCashier(), cashierFields));

      // aggregated
      entity.setCashDrawer(cashDrawerMerger.unbind(entity.getCashDrawer(), cashDrawerFields));

      // aggregated
      entity.setPaidBy(customerMerger.unbind(entity.getPaidBy(), paidByFields));

      // composed collections
      entity.setPaymentItems(paymentItemMerger.unbind(entity.getPaymentItems(), paymentItemsFields));

      // aggregated collection
      entity.setInvoices(paymentCustomerInvoiceAssocMerger.unbind(entity.getInvoices(), invoicesFields));

      return entity;
   }

   private List<Payment> detach(List<Payment> list)
   {
      if (list == null)
         return list;
      List<Payment> result = new ArrayList<Payment>();
      for (Payment entity : list)
      {
         result.add(detach(entity));
      }
      return result;
   }

   private PaymentSearchInput detach(PaymentSearchInput searchInput)
   {
      searchInput.setEntity(detach(searchInput.getEntity()));
      return searchInput;
   }
}