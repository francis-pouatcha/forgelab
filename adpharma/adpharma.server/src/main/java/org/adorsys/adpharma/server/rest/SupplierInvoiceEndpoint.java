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
import org.adorsys.adpharma.server.jpa.SupplierInvoice;
import org.adorsys.adpharma.server.jpa.SupplierInvoice_;
import org.adorsys.adpharma.server.jpa.SupplierInvoiceSearchInput;
import org.adorsys.adpharma.server.jpa.SupplierInvoiceSearchResult;

/**
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/supplierinvoices")
public class SupplierInvoiceEndpoint
{

   @Inject
   private SupplierInvoiceEJB ejb;

   @Inject
   private LoginMerger loginMerger;

   @Inject
   private SupplierMerger supplierMerger;

   @Inject
   private DeliveryMerger deliveryMerger;

   @Inject
   private SupplierInvoiceItemMerger supplierInvoiceItemMerger;

   @Inject
   private AgencyMerger agencyMerger;

   @POST
   @Consumes({ "application/json", "application/xml" })
   @Produces({ "application/json", "application/xml" })
   public SupplierInvoice create(SupplierInvoice entity)
   {
      return detach(ejb.create(entity));
   }

   @DELETE
   @Path("/{id:[0-9][0-9]*}")
   public Response deleteById(@PathParam("id") Long id)
   {
      SupplierInvoice deleted = ejb.deleteById(id);
      if (deleted == null)
         return Response.status(Status.NOT_FOUND).build();

      return Response.ok(detach(deleted)).build();
   }

   @PUT
   @Path("/{id:[0-9][0-9]*}")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public SupplierInvoice update(SupplierInvoice entity)
   {
      return detach(ejb.update(entity));
   }

   @GET
   @Path("/{id:[0-9][0-9]*}")
   @Produces({ "application/json", "application/xml" })
   public Response findById(@PathParam("id") Long id)
   {
      SupplierInvoice found = ejb.findById(id);
      if (found == null)
         return Response.status(Status.NOT_FOUND).build();
      return Response.ok(detach(found)).build();
   }

   @GET
   @Produces({ "application/json", "application/xml" })
   public SupplierInvoiceSearchResult listAll(@QueryParam("start") int start,
         @QueryParam("max") int max)
   {
      List<SupplierInvoice> resultList = ejb.listAll(start, max);
      SupplierInvoiceSearchInput searchInput = new SupplierInvoiceSearchInput();
      searchInput.setStart(start);
      searchInput.setMax(max);
      return new SupplierInvoiceSearchResult((long) resultList.size(),
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
   public SupplierInvoiceSearchResult findBy(SupplierInvoiceSearchInput searchInput)
   {
      SingularAttribute<SupplierInvoice, ?>[] attributes = readSeachAttributes(searchInput);
      Long count = ejb.countBy(searchInput.getEntity(), attributes);
      List<SupplierInvoice> resultList = ejb.findBy(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new SupplierInvoiceSearchResult(count, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countBy")
   @Consumes({ "application/json", "application/xml" })
   public Long countBy(SupplierInvoiceSearchInput searchInput)
   {
      SingularAttribute<SupplierInvoice, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countBy(searchInput.getEntity(), attributes);
   }

   @POST
   @Path("/findByLike")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public SupplierInvoiceSearchResult findByLike(SupplierInvoiceSearchInput searchInput)
   {
      SingularAttribute<SupplierInvoice, ?>[] attributes = readSeachAttributes(searchInput);
      Long countLike = ejb.countByLike(searchInput.getEntity(), attributes);
      List<SupplierInvoice> resultList = ejb.findByLike(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new SupplierInvoiceSearchResult(countLike, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countByLike")
   @Consumes({ "application/json", "application/xml" })
   public Long countByLike(SupplierInvoiceSearchInput searchInput)
   {
      SingularAttribute<SupplierInvoice, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countByLike(searchInput.getEntity(), attributes);
   }

   @SuppressWarnings("unchecked")
   private SingularAttribute<SupplierInvoice, ?>[] readSeachAttributes(
         SupplierInvoiceSearchInput searchInput)
   {
      List<String> fieldNames = searchInput.getFieldNames();
      List<SingularAttribute<SupplierInvoice, ?>> result = new ArrayList<SingularAttribute<SupplierInvoice, ?>>();
      for (String fieldName : fieldNames)
      {
         Field[] fields = SupplierInvoice_.class.getFields();
         for (Field field : fields)
         {
            if (field.getName().equals(fieldName))
            {
               try
               {
                  result.add((SingularAttribute<SupplierInvoice, ?>) field.get(null));
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

   private static final List<String> supplierFields = Arrays.asList("name", "fax", "email");

   private static final List<String> creatingUserFields = Arrays.asList("loginName", "email", "gender");

   private static final List<String> agencyFields = Arrays.asList("agencyNumber", "name", "active", "name", "name", "phone", "fax");

   private static final List<String> deliveryFields = Arrays.asList("deliveryNumber", "deliverySlipNumber", "dateOnDeliverySlip", "amountBeforeTax", "amountAfterTax", "amountDiscount", "netAmountToPay", "vat.rate", "receivingAgency.name");

   private static final List<String> invoiceItemsFields = Arrays.asList("internalPic", "article.articleName", "deliveryQty", "purchasePricePU", "salesPricePU", "amountReturn", "totalPurchasePrice");

   private SupplierInvoice detach(SupplierInvoice entity)
   {
      if (entity == null)
         return null;

      // aggregated
      entity.setSupplier(supplierMerger.unbind(entity.getSupplier(), supplierFields));

      // aggregated
      entity.setCreatingUser(loginMerger.unbind(entity.getCreatingUser(), creatingUserFields));

      // aggregated
      entity.setAgency(agencyMerger.unbind(entity.getAgency(), agencyFields));

      // aggregated
      entity.setDelivery(deliveryMerger.unbind(entity.getDelivery(), deliveryFields));

      // composed collections
      entity.setInvoiceItems(supplierInvoiceItemMerger.unbind(entity.getInvoiceItems(), invoiceItemsFields));

      return entity;
   }

   private List<SupplierInvoice> detach(List<SupplierInvoice> list)
   {
      if (list == null)
         return list;
      List<SupplierInvoice> result = new ArrayList<SupplierInvoice>();
      for (SupplierInvoice entity : list)
      {
         result.add(detach(entity));
      }
      return result;
   }

   private SupplierInvoiceSearchInput detach(SupplierInvoiceSearchInput searchInput)
   {
      searchInput.setEntity(detach(searchInput.getEntity()));
      return searchInput;
   }
}