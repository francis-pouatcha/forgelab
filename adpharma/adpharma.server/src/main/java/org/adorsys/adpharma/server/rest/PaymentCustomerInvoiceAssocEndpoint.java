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
import org.adorsys.adpharma.server.jpa.PaymentCustomerInvoiceAssoc;
import org.adorsys.adpharma.server.jpa.PaymentCustomerInvoiceAssoc_;
import org.adorsys.adpharma.server.jpa.PaymentCustomerInvoiceAssocSearchInput;
import org.adorsys.adpharma.server.jpa.PaymentCustomerInvoiceAssocSearchResult;

/**
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/paymentcustomerinvoiceassocs")
public class PaymentCustomerInvoiceAssocEndpoint
{

   @Inject
   private PaymentCustomerInvoiceAssocEJB ejb;

   @Inject
   private CustomerInvoiceMerger customerInvoiceMerger;

   @Inject
   private PaymentMerger paymentMerger;

   @POST
   @Consumes({ "application/json", "application/xml" })
   @Produces({ "application/json", "application/xml" })
   public PaymentCustomerInvoiceAssoc create(PaymentCustomerInvoiceAssoc entity)
   {
      return detach(ejb.create(entity));
   }

   @DELETE
   @Path("/{id:[0-9][0-9]*}")
   public Response deleteById(@PathParam("id") Long id)
   {
      PaymentCustomerInvoiceAssoc deleted = ejb.deleteById(id);
      if (deleted == null)
         return Response.status(Status.NOT_FOUND).build();

      return Response.ok(detach(deleted)).build();
   }

   @PUT
   @Path("/{id:[0-9][0-9]*}")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public PaymentCustomerInvoiceAssoc update(PaymentCustomerInvoiceAssoc entity)
   {
      return detach(ejb.update(entity));
   }

   @GET
   @Path("/{id:[0-9][0-9]*}")
   @Produces({ "application/json", "application/xml" })
   public Response findById(@PathParam("id") Long id)
   {
      PaymentCustomerInvoiceAssoc found = ejb.findById(id);
      if (found == null)
         return Response.status(Status.NOT_FOUND).build();
      return Response.ok(detach(found)).build();
   }

   @GET
   @Produces({ "application/json", "application/xml" })
   public PaymentCustomerInvoiceAssocSearchResult listAll(@QueryParam("start") int start,
         @QueryParam("max") int max)
   {
      List<PaymentCustomerInvoiceAssoc> resultList = ejb.listAll(start, max);
      PaymentCustomerInvoiceAssocSearchInput searchInput = new PaymentCustomerInvoiceAssocSearchInput();
      searchInput.setStart(start);
      searchInput.setMax(max);
      return new PaymentCustomerInvoiceAssocSearchResult((long) resultList.size(),
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
   public PaymentCustomerInvoiceAssocSearchResult findBy(PaymentCustomerInvoiceAssocSearchInput searchInput)
   {
      SingularAttribute<PaymentCustomerInvoiceAssoc, ?>[] attributes = readSeachAttributes(searchInput);
      Long count = ejb.countBy(searchInput.getEntity(), attributes);
      List<PaymentCustomerInvoiceAssoc> resultList = ejb.findBy(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new PaymentCustomerInvoiceAssocSearchResult(count, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countBy")
   @Consumes({ "application/json", "application/xml" })
   public Long countBy(PaymentCustomerInvoiceAssocSearchInput searchInput)
   {
      SingularAttribute<PaymentCustomerInvoiceAssoc, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countBy(searchInput.getEntity(), attributes);
   }

   @POST
   @Path("/findByLike")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public PaymentCustomerInvoiceAssocSearchResult findByLike(PaymentCustomerInvoiceAssocSearchInput searchInput)
   {
      SingularAttribute<PaymentCustomerInvoiceAssoc, ?>[] attributes = readSeachAttributes(searchInput);
      Long countLike = ejb.countByLike(searchInput.getEntity(), attributes);
      List<PaymentCustomerInvoiceAssoc> resultList = ejb.findByLike(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new PaymentCustomerInvoiceAssocSearchResult(countLike, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countByLike")
   @Consumes({ "application/json", "application/xml" })
   public Long countByLike(PaymentCustomerInvoiceAssocSearchInput searchInput)
   {
      SingularAttribute<PaymentCustomerInvoiceAssoc, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countByLike(searchInput.getEntity(), attributes);
   }

   @SuppressWarnings("unchecked")
   private SingularAttribute<PaymentCustomerInvoiceAssoc, ?>[] readSeachAttributes(
         PaymentCustomerInvoiceAssocSearchInput searchInput)
   {
      List<String> fieldNames = searchInput.getFieldNames();
      List<SingularAttribute<PaymentCustomerInvoiceAssoc, ?>> result = new ArrayList<SingularAttribute<PaymentCustomerInvoiceAssoc, ?>>();
      for (String fieldName : fieldNames)
      {
         Field[] fields = PaymentCustomerInvoiceAssoc_.class.getFields();
         for (Field field : fields)
         {
            if (field.getName().equals(fieldName))
            {
               try
               {
                  result.add((SingularAttribute<PaymentCustomerInvoiceAssoc, ?>) field.get(null));
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

   private static final List<String> s_invoices_t_payments_sourceFields = Arrays.asList("paymentNumber", "paymentDate", "recordDate", "amount", "receivedAmount", "difference", "agency.name", "cashier.fullName", "cashDrawer.cashDrawerNumber", "paymentMode", "paymentReceiptPrinted", "paidBy.fullName");

   private static final List<String> s_invoices_t_payments_targetFields = Arrays.asList("invoiceType", "invoiceNumber", "creationDate", "customer.fullName", "insurance.customer.fullName", "insurance.insurer.fullName", "creatingUser.fullName", "agency.name", "salesOrder.soNumber", "settled", "amountBeforeTax", "amountVAT", "amountDiscount", "amountAfterTax", "netToPay", "customerRestTopay", "insurranceRestTopay", "cashed", "advancePayment", "totalRestToPay");

   private PaymentCustomerInvoiceAssoc detach(PaymentCustomerInvoiceAssoc entity)
   {
      if (entity == null)
         return null;

      // aggregated
      entity.setSource(paymentMerger.unbind(entity.getSource(), s_invoices_t_payments_sourceFields));

      // aggregated
      entity.setTarget(customerInvoiceMerger.unbind(entity.getTarget(), s_invoices_t_payments_targetFields));

      return entity;
   }

   private List<PaymentCustomerInvoiceAssoc> detach(List<PaymentCustomerInvoiceAssoc> list)
   {
      if (list == null)
         return list;
      List<PaymentCustomerInvoiceAssoc> result = new ArrayList<PaymentCustomerInvoiceAssoc>();
      for (PaymentCustomerInvoiceAssoc entity : list)
      {
         result.add(detach(entity));
      }
      return result;
   }

   private PaymentCustomerInvoiceAssocSearchInput detach(PaymentCustomerInvoiceAssocSearchInput searchInput)
   {
      searchInput.setEntity(detach(searchInput.getEntity()));
      return searchInput;
   }
}