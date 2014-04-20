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

import org.adorsys.adpharma.server.jpa.ArticleLotTransferManager;
import org.adorsys.adpharma.server.jpa.WareHouseArticleLot;
import org.adorsys.adpharma.server.jpa.WareHouseArticleLot_;
import org.adorsys.adpharma.server.jpa.WareHouseArticleLotSearchInput;
import org.adorsys.adpharma.server.jpa.WareHouseArticleLotSearchResult;

/**
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/warehousearticlelots")
public class WareHouseArticleLotEndpoint
{

   @Inject
   private WareHouseArticleLotEJB ejb;

   @Inject
   private WareHouseMerger wareHouseMerger;

   @Inject
   private ArticleLotMerger articleLotMerger;

   @POST
   @Consumes({ "application/json", "application/xml" })
   @Produces({ "application/json", "application/xml" })
   public WareHouseArticleLot create(WareHouseArticleLot entity)
   {
      return detach(ejb.create(entity));
   }
   
   @POST
   @Path("/processTransfer")
   @Consumes({ "application/json", "application/xml" })
   @Produces({ "application/json", "application/xml" })
   public WareHouseArticleLot processTransfer(ArticleLotTransferManager entity)
   {
      return detach(ejb.processTransFer(entity));
   }

   @DELETE
   @Path("/{id:[0-9][0-9]*}")
   public Response deleteById(@PathParam("id") Long id)
   {
      WareHouseArticleLot deleted = ejb.deleteById(id);
      if (deleted == null)
         return Response.status(Status.NOT_FOUND).build();

      return Response.ok(detach(deleted)).build();
   }

   @PUT
   @Path("/{id:[0-9][0-9]*}")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public WareHouseArticleLot update(WareHouseArticleLot entity)
   {
      return detach(ejb.update(entity));
   }

   @GET
   @Path("/{id:[0-9][0-9]*}")
   @Produces({ "application/json", "application/xml" })
   public Response findById(@PathParam("id") Long id)
   {
      WareHouseArticleLot found = ejb.findById(id);
      if (found == null)
         return Response.status(Status.NOT_FOUND).build();
      return Response.ok(detach(found)).build();
   }

   @GET
   @Produces({ "application/json", "application/xml" })
   public WareHouseArticleLotSearchResult listAll(@QueryParam("start") int start,
         @QueryParam("max") int max)
   {
      List<WareHouseArticleLot> resultList = ejb.listAll(start, max);
      WareHouseArticleLotSearchInput searchInput = new WareHouseArticleLotSearchInput();
      searchInput.setStart(start);
      searchInput.setMax(max);
      return new WareHouseArticleLotSearchResult((long) resultList.size(),
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
   public WareHouseArticleLotSearchResult findBy(WareHouseArticleLotSearchInput searchInput)
   {
      SingularAttribute<WareHouseArticleLot, ?>[] attributes = readSeachAttributes(searchInput);
      Long count = ejb.countBy(searchInput.getEntity(), attributes);
      List<WareHouseArticleLot> resultList = ejb.findBy(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new WareHouseArticleLotSearchResult(count, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countBy")
   @Consumes({ "application/json", "application/xml" })
   public Long countBy(WareHouseArticleLotSearchInput searchInput)
   {
      SingularAttribute<WareHouseArticleLot, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countBy(searchInput.getEntity(), attributes);
   }

   @POST
   @Path("/findByLike")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public WareHouseArticleLotSearchResult findByLike(WareHouseArticleLotSearchInput searchInput)
   {
      SingularAttribute<WareHouseArticleLot, ?>[] attributes = readSeachAttributes(searchInput);
      Long countLike = ejb.countByLike(searchInput.getEntity(), attributes);
      List<WareHouseArticleLot> resultList = ejb.findByLike(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new WareHouseArticleLotSearchResult(countLike, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countByLike")
   @Consumes({ "application/json", "application/xml" })
   public Long countByLike(WareHouseArticleLotSearchInput searchInput)
   {
      SingularAttribute<WareHouseArticleLot, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countByLike(searchInput.getEntity(), attributes);
   }

   @SuppressWarnings("unchecked")
   private SingularAttribute<WareHouseArticleLot, ?>[] readSeachAttributes(
         WareHouseArticleLotSearchInput searchInput)
   {
      List<String> fieldNames = searchInput.getFieldNames();
      List<SingularAttribute<WareHouseArticleLot, ?>> result = new ArrayList<SingularAttribute<WareHouseArticleLot, ?>>();
      for (String fieldName : fieldNames)
      {
         Field[] fields = WareHouseArticleLot_.class.getFields();
         for (Field field : fields)
         {
            if (field.getName().equals(fieldName))
            {
               try
               {
                  result.add((SingularAttribute<WareHouseArticleLot, ?>) field.get(null));
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

   private static final List<String> wareHouseFields = Arrays.asList("name");

   private static final List<String> articleLotFields = Arrays.asList("internalPic");

   private WareHouseArticleLot detach(WareHouseArticleLot entity)
   {
      if (entity == null)
         return null;

      // aggregated
      entity.setWareHouse(wareHouseMerger.unbind(entity.getWareHouse(), wareHouseFields));

      // aggregated
      entity.setArticleLot(articleLotMerger.unbind(entity.getArticleLot(), articleLotFields));

      return entity;
   }

   private List<WareHouseArticleLot> detach(List<WareHouseArticleLot> list)
   {
      if (list == null)
         return list;
      List<WareHouseArticleLot> result = new ArrayList<WareHouseArticleLot>();
      for (WareHouseArticleLot entity : list)
      {
         result.add(detach(entity));
      }
      return result;
   }

   private WareHouseArticleLotSearchInput detach(WareHouseArticleLotSearchInput searchInput)
   {
      searchInput.setEntity(detach(searchInput.getEntity()));
      return searchInput;
   }
}