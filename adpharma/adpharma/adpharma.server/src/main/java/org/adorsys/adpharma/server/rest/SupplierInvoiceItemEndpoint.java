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
import org.adorsys.adpharma.server.jpa.SupplierInvoiceItem;
import org.adorsys.adpharma.server.jpa.SupplierInvoiceItem_;
import org.adorsys.adpharma.server.jpa.SupplierInvoiceItemSearchInput;
import org.adorsys.adpharma.server.jpa.SupplierInvoiceItemSearchResult;

/**
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/supplierinvoiceitems")
public class SupplierInvoiceItemEndpoint
{

   @Inject
   private SupplierInvoiceItemEJB ejb;

   @Inject
   private SupplierInvoiceMerger supplierInvoiceMerger;

   @Inject
   private ArticleMerger articleMerger;

   @POST
   @Consumes({ "application/json", "application/xml" })
   @Produces({ "application/json", "application/xml" })
   public SupplierInvoiceItem create(SupplierInvoiceItem entity)
   {
      return detach(ejb.create(entity));
   }

   @DELETE
   @Path("/{id:[0-9][0-9]*}")
   public Response deleteById(@PathParam("id") Long id)
   {
      SupplierInvoiceItem deleted = ejb.deleteById(id);
      if (deleted == null)
         return Response.status(Status.NOT_FOUND).build();

      return Response.ok(detach(deleted)).build();
   }

   @PUT
   @Path("/{id:[0-9][0-9]*}")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public SupplierInvoiceItem update(SupplierInvoiceItem entity)
   {
      return detach(ejb.update(entity));
   }

   @GET
   @Path("/{id:[0-9][0-9]*}")
   @Produces({ "application/json", "application/xml" })
   public Response findById(@PathParam("id") Long id)
   {
      SupplierInvoiceItem found = ejb.findById(id);
      if (found == null)
         return Response.status(Status.NOT_FOUND).build();
      return Response.ok(detach(found)).build();
   }

   @GET
   @Produces({ "application/json", "application/xml" })
   public SupplierInvoiceItemSearchResult listAll(@QueryParam("start") int start,
         @QueryParam("max") int max)
   {
      List<SupplierInvoiceItem> resultList = ejb.listAll(start, max);
      SupplierInvoiceItemSearchInput searchInput = new SupplierInvoiceItemSearchInput();
      searchInput.setStart(start);
      searchInput.setMax(max);
      return new SupplierInvoiceItemSearchResult((long) resultList.size(),
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
   public SupplierInvoiceItemSearchResult findBy(SupplierInvoiceItemSearchInput searchInput)
   {
      SingularAttribute<SupplierInvoiceItem, ?>[] attributes = readSeachAttributes(searchInput);
      Long count = ejb.countBy(searchInput.getEntity(), attributes);
      List<SupplierInvoiceItem> resultList = ejb.findBy(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new SupplierInvoiceItemSearchResult(count, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countBy")
   @Consumes({ "application/json", "application/xml" })
   public Long countBy(SupplierInvoiceItemSearchInput searchInput)
   {
      SingularAttribute<SupplierInvoiceItem, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countBy(searchInput.getEntity(), attributes);
   }

   @POST
   @Path("/findByLike")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public SupplierInvoiceItemSearchResult findByLike(SupplierInvoiceItemSearchInput searchInput)
   {
      SingularAttribute<SupplierInvoiceItem, ?>[] attributes = readSeachAttributes(searchInput);
      Long countLike = ejb.countByLike(searchInput.getEntity(), attributes);
      List<SupplierInvoiceItem> resultList = ejb.findByLike(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new SupplierInvoiceItemSearchResult(countLike, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countByLike")
   @Consumes({ "application/json", "application/xml" })
   public Long countByLike(SupplierInvoiceItemSearchInput searchInput)
   {
      SingularAttribute<SupplierInvoiceItem, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countByLike(searchInput.getEntity(), attributes);
   }

   @SuppressWarnings("unchecked")
   private SingularAttribute<SupplierInvoiceItem, ?>[] readSeachAttributes(
         SupplierInvoiceItemSearchInput searchInput)
   {
      List<String> fieldNames = searchInput.getFieldNames();
      List<SingularAttribute<SupplierInvoiceItem, ?>> result = new ArrayList<SingularAttribute<SupplierInvoiceItem, ?>>();
      for (String fieldName : fieldNames)
      {
         Field[] fields = SupplierInvoiceItem_.class.getFields();
         for (Field field : fields)
         {
            if (field.getName().equals(fieldName))
            {
               try
               {
                  result.add((SingularAttribute<SupplierInvoiceItem, ?>) field.get(null));
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

   private static final List<String> invoiceFields = Arrays.asList("invoiceType", "invoiceNumber", "creationDate", "supplier.name", "creatingUser.fullName", "agency.name", "delivery.deliveryNumber", "settled", "amountBeforeTax", "amountVAT", "amountDiscount", "amountAfterTax", "netToPay", "advancePayment", "totalRestToPay");

   private static final List<String> articleFields = Arrays.asList("articleName", "pic", "manufacturer", "active", "qtyInStock", "sppu", "authorizedSale", "agency.name");

   private SupplierInvoiceItem detach(SupplierInvoiceItem entity)
   {
      if (entity == null)
         return null;

      // composed
      entity.setInvoice(supplierInvoiceMerger.unbind(entity.getInvoice(), invoiceFields));

      // aggregated
      entity.setArticle(articleMerger.unbind(entity.getArticle(), articleFields));

      return entity;
   }

   private List<SupplierInvoiceItem> detach(List<SupplierInvoiceItem> list)
   {
      if (list == null)
         return list;
      List<SupplierInvoiceItem> result = new ArrayList<SupplierInvoiceItem>();
      for (SupplierInvoiceItem entity : list)
      {
         result.add(detach(entity));
      }
      return result;
   }

   private SupplierInvoiceItemSearchInput detach(SupplierInvoiceItemSearchInput searchInput)
   {
      searchInput.setEntity(detach(searchInput.getEntity()));
      return searchInput;
   }
}