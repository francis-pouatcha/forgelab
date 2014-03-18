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
import org.adorsys.adpharma.server.jpa.Delivery;
import org.adorsys.adpharma.server.jpa.Delivery_;
import org.adorsys.adpharma.server.jpa.DeliverySearchInput;
import org.adorsys.adpharma.server.jpa.DeliverySearchResult;

/**
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/deliverys")
public class DeliveryEndpoint
{

   @Inject
   private DeliveryEJB ejb;

   @Inject
   private LoginMerger loginMerger;

   @Inject
   private SupplierMerger supplierMerger;

   @Inject
   private VATMerger vATMerger;

   @Inject
   private CurrencyMerger currencyMerger;

   @Inject
   private DeliveryItemMerger deliveryItemMerger;

   @Inject
   private AgencyMerger agencyMerger;

   @POST
   @Consumes({ "application/json", "application/xml" })
   @Produces({ "application/json", "application/xml" })
   public Delivery create(Delivery entity)
   {
      return detach(ejb.create(entity));
   }

   @DELETE
   @Path("/{id:[0-9][0-9]*}")
   public Response deleteById(@PathParam("id") Long id)
   {
      Delivery deleted = ejb.deleteById(id);
      if (deleted == null)
         return Response.status(Status.NOT_FOUND).build();

      return Response.ok(detach(deleted)).build();
   }

   @PUT
   @Path("/{id:[0-9][0-9]*}")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public Delivery update(Delivery entity)
   {
      return detach(ejb.update(entity));
   }

   @GET
   @Path("/{id:[0-9][0-9]*}")
   @Produces({ "application/json", "application/xml" })
   public Response findById(@PathParam("id") Long id)
   {
      Delivery found = ejb.findById(id);
      if (found == null)
         return Response.status(Status.NOT_FOUND).build();
      return Response.ok(detach(found)).build();
   }

   @GET
   @Produces({ "application/json", "application/xml" })
   public DeliverySearchResult listAll(@QueryParam("start") int start,
         @QueryParam("max") int max)
   {
      List<Delivery> resultList = ejb.listAll(start, max);
      DeliverySearchInput searchInput = new DeliverySearchInput();
      searchInput.setStart(start);
      searchInput.setMax(max);
      return new DeliverySearchResult((long) resultList.size(),
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
   public DeliverySearchResult findBy(DeliverySearchInput searchInput)
   {
      SingularAttribute<Delivery, ?>[] attributes = readSeachAttributes(searchInput);
      Long count = ejb.countBy(searchInput.getEntity(), attributes);
      List<Delivery> resultList = ejb.findBy(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new DeliverySearchResult(count, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countBy")
   @Consumes({ "application/json", "application/xml" })
   public Long countBy(DeliverySearchInput searchInput)
   {
      SingularAttribute<Delivery, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countBy(searchInput.getEntity(), attributes);
   }

   @POST
   @Path("/findByLike")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public DeliverySearchResult findByLike(DeliverySearchInput searchInput)
   {
      SingularAttribute<Delivery, ?>[] attributes = readSeachAttributes(searchInput);
      Long countLike = ejb.countByLike(searchInput.getEntity(), attributes);
      List<Delivery> resultList = ejb.findByLike(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new DeliverySearchResult(countLike, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countByLike")
   @Consumes({ "application/json", "application/xml" })
   public Long countByLike(DeliverySearchInput searchInput)
   {
      SingularAttribute<Delivery, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countByLike(searchInput.getEntity(), attributes);
   }

   @SuppressWarnings("unchecked")
   private SingularAttribute<Delivery, ?>[] readSeachAttributes(
         DeliverySearchInput searchInput)
   {
      List<String> fieldNames = searchInput.getFieldNames();
      List<SingularAttribute<Delivery, ?>> result = new ArrayList<SingularAttribute<Delivery, ?>>();
      for (String fieldName : fieldNames)
      {
         Field[] fields = Delivery_.class.getFields();
         for (Field field : fields)
         {
            if (field.getName().equals(fieldName))
            {
               try
               {
                  result.add((SingularAttribute<Delivery, ?>) field.get(null));
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

   private static final List<String> creatingUserFields = Arrays.asList("loginName", "email", "gender");

   private static final List<String> supplierFields = Arrays.asList("name", "fax", "email");

   private static final List<String> vatFields = Arrays.asList("name", "rate", "active");

   private static final List<String> currencyFields = Arrays.asList("name");

   private static final List<String> receivingAgencyFields = Arrays.asList("agencyNumber", "name", "active", "name", "name", "phone", "fax");

   private static final List<String> deliveryItemsFields = Arrays.asList("internalPic", "mainPic", "secondaryPic", "articleName", "article.articleName", "expirationDate", "qtyOrdered", "freeQuantity", "stockQuantity", "salesPricePU", "purchasePricePU", "totalPurchasePrice");

   private Delivery detach(Delivery entity)
   {
      if (entity == null)
         return null;

      // aggregated
      entity.setCreatingUser(loginMerger.unbind(entity.getCreatingUser(), creatingUserFields));

      // aggregated
      entity.setSupplier(supplierMerger.unbind(entity.getSupplier(), supplierFields));

      // aggregated
      entity.setVat(vATMerger.unbind(entity.getVat(), vatFields));

      // aggregated
      entity.setCurrency(currencyMerger.unbind(entity.getCurrency(), currencyFields));

      // aggregated
      entity.setReceivingAgency(agencyMerger.unbind(entity.getReceivingAgency(), receivingAgencyFields));

      // composed collections
      entity.setDeliveryItems(deliveryItemMerger.unbind(entity.getDeliveryItems(), deliveryItemsFields));

      return entity;
   }

   private List<Delivery> detach(List<Delivery> list)
   {
      if (list == null)
         return list;
      List<Delivery> result = new ArrayList<Delivery>();
      for (Delivery entity : list)
      {
         result.add(detach(entity));
      }
      return result;
   }

   private DeliverySearchInput detach(DeliverySearchInput searchInput)
   {
      searchInput.setEntity(detach(searchInput.getEntity()));
      return searchInput;
   }
}