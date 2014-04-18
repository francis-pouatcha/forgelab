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
import org.adorsys.adpharma.server.jpa.SalesOrderItem;
import org.adorsys.adpharma.server.jpa.SalesOrderItem_;
import org.adorsys.adpharma.server.jpa.SalesOrderItemSearchInput;
import org.adorsys.adpharma.server.jpa.SalesOrderItemSearchResult;

/**
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/salesorderitems")
public class SalesOrderItemEndpoint
{

   @Inject
   private SalesOrderItemEJB ejb;

   @Inject
   private ArticleMerger articleMerger;

   @Inject
   private VATMerger vATMerger;

   @Inject
   private SalesOrderMerger salesOrderMerger;

   @POST
   @Consumes({ "application/json", "application/xml" })
   @Produces({ "application/json", "application/xml" })
   public SalesOrderItem create(SalesOrderItem entity)
   {
      return detach(ejb.create(entity));
   }

   @DELETE
   @Path("/{id:[0-9][0-9]*}")
   public Response deleteById(@PathParam("id") Long id)
   {
      SalesOrderItem deleted = ejb.deleteById(id);
      if (deleted == null)
         return Response.status(Status.NOT_FOUND).build();

      return Response.ok(detach(deleted)).build();
   }

   @PUT
   @Path("/{id:[0-9][0-9]*}")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public SalesOrderItem update(SalesOrderItem entity)
   {
      return detach(ejb.update(entity));
   }

   @GET
   @Path("/{id:[0-9][0-9]*}")
   @Produces({ "application/json", "application/xml" })
   public Response findById(@PathParam("id") Long id)
   {
      SalesOrderItem found = ejb.findById(id);
      if (found == null)
         return Response.status(Status.NOT_FOUND).build();
      return Response.ok(detach(found)).build();
   }

   @GET
   @Produces({ "application/json", "application/xml" })
   public SalesOrderItemSearchResult listAll(@QueryParam("start") int start,
         @QueryParam("max") int max)
   {
      List<SalesOrderItem> resultList = ejb.listAll(start, max);
      SalesOrderItemSearchInput searchInput = new SalesOrderItemSearchInput();
      searchInput.setStart(start);
      searchInput.setMax(max);
      return new SalesOrderItemSearchResult((long) resultList.size(),
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
   public SalesOrderItemSearchResult findBy(SalesOrderItemSearchInput searchInput)
   {
      SingularAttribute<SalesOrderItem, ?>[] attributes = readSeachAttributes(searchInput);
      Long count = ejb.countBy(searchInput.getEntity(), attributes);
      List<SalesOrderItem> resultList = ejb.findBy(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new SalesOrderItemSearchResult(count, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countBy")
   @Consumes({ "application/json", "application/xml" })
   public Long countBy(SalesOrderItemSearchInput searchInput)
   {
      SingularAttribute<SalesOrderItem, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countBy(searchInput.getEntity(), attributes);
   }

   @POST
   @Path("/findByLike")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public SalesOrderItemSearchResult findByLike(SalesOrderItemSearchInput searchInput)
   {
      SingularAttribute<SalesOrderItem, ?>[] attributes = readSeachAttributes(searchInput);
      Long countLike = ejb.countByLike(searchInput.getEntity(), attributes);
      List<SalesOrderItem> resultList = ejb.findByLike(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new SalesOrderItemSearchResult(countLike, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countByLike")
   @Consumes({ "application/json", "application/xml" })
   public Long countByLike(SalesOrderItemSearchInput searchInput)
   {
      SingularAttribute<SalesOrderItem, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countByLike(searchInput.getEntity(), attributes);
   }

   @SuppressWarnings("unchecked")
   private SingularAttribute<SalesOrderItem, ?>[] readSeachAttributes(
         SalesOrderItemSearchInput searchInput)
   {
      List<String> fieldNames = searchInput.getFieldNames();
      List<SingularAttribute<SalesOrderItem, ?>> result = new ArrayList<SingularAttribute<SalesOrderItem, ?>>();
      for (String fieldName : fieldNames)
      {
         Field[] fields = SalesOrderItem_.class.getFields();
         for (Field field : fields)
         {
            if (field.getName().equals(fieldName))
            {
               try
               {
                  result.add((SingularAttribute<SalesOrderItem, ?>) field.get(null));
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

   private static final List<String> salesOrderFields = Arrays.asList("cashDrawer.cashDrawerNumber", "soNumber", "customer.fullName", "insurance.customer.fullName", "insurance.insurer.fullName", "vat.rate", "salesAgent.fullName", "agency.name", "salesOrderStatus", "cashed", "amountBeforeTax", "amountVAT", "amountDiscount", "totalReturnAmount", "amountAfterTax", "salesOrderType");

   private static final List<String> articleFields = Arrays.asList("articleName", "pic", "manufacturer", "active", "qtyInStock", "sppu", "authorizedSale", "agency.name", "vat.rate");

   private static final List<String> vatFields = Arrays.asList("name", "rate", "active");

   private SalesOrderItem detach(SalesOrderItem entity)
   {
      if (entity == null)
         return null;

      // composed
      entity.setSalesOrder(salesOrderMerger.unbind(entity.getSalesOrder(), salesOrderFields));

      // aggregated
      entity.setArticle(articleMerger.unbind(entity.getArticle(), articleFields));

      // aggregated
      entity.setVat(vATMerger.unbind(entity.getVat(), vatFields));

      return entity;
   }

   private List<SalesOrderItem> detach(List<SalesOrderItem> list)
   {
      if (list == null)
         return list;
      List<SalesOrderItem> result = new ArrayList<SalesOrderItem>();
      for (SalesOrderItem entity : list)
      {
         result.add(detach(entity));
      }
      return result;
   }

   private SalesOrderItemSearchInput detach(SalesOrderItemSearchInput searchInput)
   {
      searchInput.setEntity(detach(searchInput.getEntity()));
      return searchInput;
   }
}