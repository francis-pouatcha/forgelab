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
import org.adorsys.adpharma.server.jpa.InventoryItem;
import org.adorsys.adpharma.server.jpa.InventoryItem_;
import org.adorsys.adpharma.server.jpa.InventoryItemSearchInput;
import org.adorsys.adpharma.server.jpa.InventoryItemSearchResult;

/**
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/inventoryitems")
public class InventoryItemEndpoint
{

   @Inject
   private InventoryItemEJB ejb;

   @Inject
   private LoginMerger loginMerger;

   @Inject
   private InventoryMerger inventoryMerger;

   @Inject
   private ArticleMerger articleMerger;

   @POST
   @Consumes({ "application/json", "application/xml" })
   @Produces({ "application/json", "application/xml" })
   public InventoryItem create(InventoryItem entity)
   {
      return detach(ejb.create(entity));
   }

   @DELETE
   @Path("/{id:[0-9][0-9]*}")
   public Response deleteById(@PathParam("id") Long id)
   {
      InventoryItem deleted = ejb.deleteById(id);
      if (deleted == null)
         return Response.status(Status.NOT_FOUND).build();

      return Response.ok(detach(deleted)).build();
   }

   @PUT
   @Path("/{id:[0-9][0-9]*}")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public InventoryItem update(InventoryItem entity)
   {
      return detach(ejb.update(entity));
   }

   @GET
   @Path("/{id:[0-9][0-9]*}")
   @Produces({ "application/json", "application/xml" })
   public Response findById(@PathParam("id") Long id)
   {
      InventoryItem found = ejb.findById(id);
      if (found == null)
         return Response.status(Status.NOT_FOUND).build();
      return Response.ok(detach(found)).build();
   }

   @GET
   @Produces({ "application/json", "application/xml" })
   public InventoryItemSearchResult listAll(@QueryParam("start") int start,
         @QueryParam("max") int max)
   {
      List<InventoryItem> resultList = ejb.listAll(start, max);
      InventoryItemSearchInput searchInput = new InventoryItemSearchInput();
      searchInput.setStart(start);
      searchInput.setMax(max);
      return new InventoryItemSearchResult((long) resultList.size(),
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
   public InventoryItemSearchResult findBy(InventoryItemSearchInput searchInput)
   {
      SingularAttribute<InventoryItem, ?>[] attributes = readSeachAttributes(searchInput);
      Long count = ejb.countBy(searchInput.getEntity(), attributes);
      List<InventoryItem> resultList = ejb.findBy(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new InventoryItemSearchResult(count, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countBy")
   @Consumes({ "application/json", "application/xml" })
   public Long countBy(InventoryItemSearchInput searchInput)
   {
      SingularAttribute<InventoryItem, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countBy(searchInput.getEntity(), attributes);
   }

   @POST
   @Path("/findByLike")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public InventoryItemSearchResult findByLike(InventoryItemSearchInput searchInput)
   {
      SingularAttribute<InventoryItem, ?>[] attributes = readSeachAttributes(searchInput);
      Long countLike = ejb.countByLike(searchInput.getEntity(), attributes);
      List<InventoryItem> resultList = ejb.findByLike(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new InventoryItemSearchResult(countLike, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countByLike")
   @Consumes({ "application/json", "application/xml" })
   public Long countByLike(InventoryItemSearchInput searchInput)
   {
      SingularAttribute<InventoryItem, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countByLike(searchInput.getEntity(), attributes);
   }

   @SuppressWarnings("unchecked")
   private SingularAttribute<InventoryItem, ?>[] readSeachAttributes(
         InventoryItemSearchInput searchInput)
   {
      List<String> fieldNames = searchInput.getFieldNames();
      List<SingularAttribute<InventoryItem, ?>> result = new ArrayList<SingularAttribute<InventoryItem, ?>>();
      for (String fieldName : fieldNames)
      {
         Field[] fields = InventoryItem_.class.getFields();
         for (Field field : fields)
         {
            if (field.getName().equals(fieldName))
            {
               try
               {
                  result.add((SingularAttribute<InventoryItem, ?>) field.get(null));
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

   private static final List<String> inventoryFields = Arrays.asList("inventoryNumber", "gapSaleAmount", "gapPurchaseAmount", "inventoryStatus", "agency.name");

   private static final List<String> recordingUserFields = Arrays.asList("loginName", "email", "gender");

   private static final List<String> articleFields = Arrays.asList("articleName", "pic", "manufacturer", "active", "qtyInStock", "sppu", "authorizedSale", "agency.name");

   private InventoryItem detach(InventoryItem entity)
   {
      if (entity == null)
         return null;

      // composed
      entity.setInventory(inventoryMerger.unbind(entity.getInventory(), inventoryFields));

      // aggregated
      entity.setRecordingUser(loginMerger.unbind(entity.getRecordingUser(), recordingUserFields));

      // aggregated
      entity.setArticle(articleMerger.unbind(entity.getArticle(), articleFields));

      return entity;
   }

   private List<InventoryItem> detach(List<InventoryItem> list)
   {
      if (list == null)
         return list;
      List<InventoryItem> result = new ArrayList<InventoryItem>();
      for (InventoryItem entity : list)
      {
         result.add(detach(entity));
      }
      return result;
   }

   private InventoryItemSearchInput detach(InventoryItemSearchInput searchInput)
   {
      searchInput.setEntity(detach(searchInput.getEntity()));
      return searchInput;
   }
}