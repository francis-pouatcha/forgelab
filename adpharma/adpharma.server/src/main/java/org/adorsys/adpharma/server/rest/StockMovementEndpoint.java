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
import org.adorsys.adpharma.server.jpa.SalesOrderItemSearchInput;
import org.adorsys.adpharma.server.jpa.SalesOrderItemSearchResult;
import org.adorsys.adpharma.server.jpa.StockMovement;
import org.adorsys.adpharma.server.jpa.StockMovement_;
import org.adorsys.adpharma.server.jpa.StockMovementSearchInput;
import org.adorsys.adpharma.server.jpa.StockMovementSearchResult;
import org.adorsys.adpharma.server.utils.PeriodicalDataSearchInput;

/**
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/stockmovements")
public class StockMovementEndpoint
{

   @Inject
   private StockMovementEJB ejb;

   @Inject
   private LoginMerger loginMerger;

   @Inject
   private ArticleMerger articleMerger;

   @Inject
   private AgencyMerger agencyMerger;

   @POST
   @Path("/periodicalMovement")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public StockMovementSearchResult periodicalMovement(PeriodicalDataSearchInput data)
   {
	   List<StockMovement> periodicalMvt = ejb.periodicalStockMovement(data);
	   periodicalMvt = detach(periodicalMvt);
	   StockMovementSearchInput searchInput = new StockMovementSearchInput();
      return new StockMovementSearchResult(Long.valueOf(1), periodicalMvt, searchInput);
   }
   
   
   @POST
   @Consumes({ "application/json", "application/xml" })
   @Produces({ "application/json", "application/xml" })
   public StockMovement create(StockMovement entity)
   {
      return detach(ejb.create(entity));
   }

   @DELETE
   @Path("/{id:[0-9][0-9]*}")
   public Response deleteById(@PathParam("id") Long id)
   {
      StockMovement deleted = ejb.deleteById(id);
      if (deleted == null)
         return Response.status(Status.NOT_FOUND).build();

      return Response.ok(detach(deleted)).build();
   }

   @PUT
   @Path("/{id:[0-9][0-9]*}")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public StockMovement update(StockMovement entity)
   {
      return detach(ejb.update(entity));
   }

   @GET
   @Path("/{id:[0-9][0-9]*}")
   @Produces({ "application/json", "application/xml" })
   public Response findById(@PathParam("id") Long id)
   {
      StockMovement found = ejb.findById(id);
      if (found == null)
         return Response.status(Status.NOT_FOUND).build();
      return Response.ok(detach(found)).build();
   }

   @GET
   @Produces({ "application/json", "application/xml" })
   public StockMovementSearchResult listAll(@QueryParam("start") int start,
         @QueryParam("max") int max)
   {
      List<StockMovement> resultList = ejb.listAll(start, max);
      StockMovementSearchInput searchInput = new StockMovementSearchInput();
      searchInput.setStart(start);
      searchInput.setMax(max);
      return new StockMovementSearchResult((long) resultList.size(),
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
   public StockMovementSearchResult findBy(StockMovementSearchInput searchInput)
   {
      SingularAttribute<StockMovement, Object>[] attributes = readSeachAttributes(searchInput);
      Long count = ejb.countBy(searchInput.getEntity(), attributes);
      List<StockMovement> resultList = ejb.findBy(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new StockMovementSearchResult(count, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countBy")
   @Consumes({ "application/json", "application/xml" })
   public Long countBy(StockMovementSearchInput searchInput)
   {
      SingularAttribute<StockMovement, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countBy(searchInput.getEntity(), attributes);
   }

   @POST
   @Path("/findByLike")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public StockMovementSearchResult findByLike(StockMovementSearchInput searchInput)
   {
      SingularAttribute<StockMovement, Object>[] attributes = readSeachAttributes(searchInput);
      Long countLike = ejb.countByLike(searchInput.getEntity(), attributes);
      List<StockMovement> resultList = ejb.findByLike(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new StockMovementSearchResult(countLike, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countByLike")
   @Consumes({ "application/json", "application/xml" })
   public Long countByLike(StockMovementSearchInput searchInput)
   {
      SingularAttribute<StockMovement, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countByLike(searchInput.getEntity(), attributes);
   }
   @SuppressWarnings("unchecked")
   private SingularAttribute<StockMovement, Object>[] readSeachAttributes(
         StockMovementSearchInput searchInput)
   {
      List<String> fieldNames = searchInput.getFieldNames();
      List<SingularAttribute<StockMovement, Object>> result = new ArrayList<SingularAttribute<StockMovement, Object>>();
      for (String fieldName : fieldNames)
      {
         Field[] fields = StockMovement_.class.getFields();
         for (Field field : fields)
         {
            if (field.getName().equals(fieldName))
            {
               try
               {
                  result.add((SingularAttribute<StockMovement, Object>) field.get(null));
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

   private static final List<String> articleFields = Arrays.asList("articleName", "pic", "manufacturer", "active", "qtyInStock", "sppu", "authorizedSale", "agency.name");

   private static final List<String> agencyFields = Arrays.asList("agencyNumber", "name", "active", "name", "name", "phone", "fax");

   private StockMovement detach(StockMovement entity)
   {
      if (entity == null)
         return null;

      // aggregated
      entity.setCreatingUser(loginMerger.unbind(entity.getCreatingUser(), creatingUserFields));

      // aggregated
      entity.setArticle(articleMerger.unbind(entity.getArticle(), articleFields));

      // aggregated
      entity.setAgency(agencyMerger.unbind(entity.getAgency(), agencyFields));

      return entity;
   }

   private List<StockMovement> detach(List<StockMovement> list)
   {
      if (list == null)
         return list;
      List<StockMovement> result = new ArrayList<StockMovement>();
      for (StockMovement entity : list)
      {
         result.add(detach(entity));
      }
      return result;
   }

   private StockMovementSearchInput detach(StockMovementSearchInput searchInput)
   {
      searchInput.setEntity(detach(searchInput.getEntity()));
      return searchInput;
   }
}