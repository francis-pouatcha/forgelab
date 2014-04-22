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
import org.adorsys.adpharma.server.jpa.PaymentItem;
import org.adorsys.adpharma.server.jpa.PaymentItem_;
import org.adorsys.adpharma.server.jpa.PaymentItemSearchInput;
import org.adorsys.adpharma.server.jpa.PaymentItemSearchResult;

/**
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/paymentitems")
public class PaymentItemEndpoint
{

   @Inject
   private PaymentItemEJB ejb;

   @Inject
   private PaymentMerger paymentMerger;

   @Inject
   private CustomerMerger customerMerger;

   @POST
   @Consumes({ "application/json", "application/xml" })
   @Produces({ "application/json", "application/xml" })
   public PaymentItem create(PaymentItem entity)
   {
      return detach(ejb.create(entity));
   }

   @DELETE
   @Path("/{id:[0-9][0-9]*}")
   public Response deleteById(@PathParam("id") Long id)
   {
      PaymentItem deleted = ejb.deleteById(id);
      if (deleted == null)
         return Response.status(Status.NOT_FOUND).build();

      return Response.ok(detach(deleted)).build();
   }

   @PUT
   @Path("/{id:[0-9][0-9]*}")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public PaymentItem update(PaymentItem entity)
   {
      return detach(ejb.update(entity));
   }

   @GET
   @Path("/{id:[0-9][0-9]*}")
   @Produces({ "application/json", "application/xml" })
   public Response findById(@PathParam("id") Long id)
   {
      PaymentItem found = ejb.findById(id);
      if (found == null)
         return Response.status(Status.NOT_FOUND).build();
      return Response.ok(detach(found)).build();
   }

   @GET
   @Produces({ "application/json", "application/xml" })
   public PaymentItemSearchResult listAll(@QueryParam("start") int start,
         @QueryParam("max") int max)
   {
      List<PaymentItem> resultList = ejb.listAll(start, max);
      PaymentItemSearchInput searchInput = new PaymentItemSearchInput();
      searchInput.setStart(start);
      searchInput.setMax(max);
      return new PaymentItemSearchResult((long) resultList.size(),
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
   public PaymentItemSearchResult findBy(PaymentItemSearchInput searchInput)
   {
      SingularAttribute<PaymentItem, ?>[] attributes = readSeachAttributes(searchInput);
      Long count = ejb.countBy(searchInput.getEntity(), attributes);
      List<PaymentItem> resultList = ejb.findBy(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new PaymentItemSearchResult(count, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countBy")
   @Consumes({ "application/json", "application/xml" })
   public Long countBy(PaymentItemSearchInput searchInput)
   {
      SingularAttribute<PaymentItem, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countBy(searchInput.getEntity(), attributes);
   }

   @POST
   @Path("/findByLike")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public PaymentItemSearchResult findByLike(PaymentItemSearchInput searchInput)
   {
      SingularAttribute<PaymentItem, ?>[] attributes = readSeachAttributes(searchInput);
      Long countLike = ejb.countByLike(searchInput.getEntity(), attributes);
      List<PaymentItem> resultList = ejb.findByLike(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new PaymentItemSearchResult(countLike, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countByLike")
   @Consumes({ "application/json", "application/xml" })
   public Long countByLike(PaymentItemSearchInput searchInput)
   {
      SingularAttribute<PaymentItem, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countByLike(searchInput.getEntity(), attributes);
   }

   @SuppressWarnings("unchecked")
   private SingularAttribute<PaymentItem, ?>[] readSeachAttributes(
         PaymentItemSearchInput searchInput)
   {
      List<String> fieldNames = searchInput.getFieldNames();
      List<SingularAttribute<PaymentItem, ?>> result = new ArrayList<SingularAttribute<PaymentItem, ?>>();
      for (String fieldName : fieldNames)
      {
         Field[] fields = PaymentItem_.class.getFields();
         for (Field field : fields)
         {
            if (field.getName().equals(fieldName))
            {
               try
               {
                  result.add((SingularAttribute<PaymentItem, ?>) field.get(null));
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

   private static final List<String> paymentFields = Arrays.asList("paymentNumber", "paymentDate", "recordDate", "amount", "receivedAmount", "difference", "agency.name", "cashier.fullName", "cashDrawer.cashDrawerNumber", "paymentMode", "paymentReceiptPrinted", "paidBy.fullName");

   private static final List<String> paidByFields = Arrays.asList("fullName", "birthDate", "landLinePhone", "mobile", "fax", "email", "creditAuthorized", "discountAuthorized");

   private PaymentItem detach(PaymentItem entity)
   {
      if (entity == null)
         return null;

      // composed
      entity.setPayment(paymentMerger.unbind(entity.getPayment(), paymentFields));

      // aggregated
      entity.setPaidBy(customerMerger.unbind(entity.getPaidBy(), paidByFields));

      return entity;
   }

   private List<PaymentItem> detach(List<PaymentItem> list)
   {
      if (list == null)
         return list;
      List<PaymentItem> result = new ArrayList<PaymentItem>();
      for (PaymentItem entity : list)
      {
         result.add(detach(entity));
      }
      return result;
   }

   private PaymentItemSearchInput detach(PaymentItemSearchInput searchInput)
   {
      searchInput.setEntity(detach(searchInput.getEntity()));
      return searchInput;
   }
}