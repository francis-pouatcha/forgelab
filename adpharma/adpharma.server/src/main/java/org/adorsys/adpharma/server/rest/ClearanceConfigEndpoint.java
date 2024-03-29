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
import org.adorsys.adpharma.server.jpa.ClearanceConfig;
import org.adorsys.adpharma.server.jpa.ClearanceConfig_;
import org.adorsys.adpharma.server.jpa.ClearanceConfigSearchInput;
import org.adorsys.adpharma.server.jpa.ClearanceConfigSearchResult;

/**
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/clearanceconfigs")
public class ClearanceConfigEndpoint
{

   @Inject
   private ClearanceConfigEJB ejb;

   @POST
   @Consumes({ "application/json", "application/xml" })
   @Produces({ "application/json", "application/xml" })
   public ClearanceConfig create(ClearanceConfig entity)
   {
      return detach(ejb.create(entity));
   }

   @DELETE
   @Path("/{id:[0-9][0-9]*}")
   public Response deleteById(@PathParam("id") Long id)
   {
      ClearanceConfig deleted = ejb.deleteById(id);
      if (deleted == null)
         return Response.status(Status.NOT_FOUND).build();

      return Response.ok(detach(deleted)).build();
   }

   @PUT
   @Path("/{id:[0-9][0-9]*}")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public ClearanceConfig update(ClearanceConfig entity)
   {
      return detach(ejb.update(entity));
   }

   @GET
   @Path("/{id:[0-9][0-9]*}")
   @Produces({ "application/json", "application/xml" })
   public Response findById(@PathParam("id") Long id)
   {
      ClearanceConfig found = ejb.findById(id);
      if (found == null)
         return Response.status(Status.NOT_FOUND).build();
      return Response.ok(detach(found)).build();
   }

   @GET
   @Produces({ "application/json", "application/xml" })
   public ClearanceConfigSearchResult listAll(@QueryParam("start") int start,
         @QueryParam("max") int max)
   {
      List<ClearanceConfig> resultList = ejb.listAll(start, max);
      ClearanceConfigSearchInput searchInput = new ClearanceConfigSearchInput();
      searchInput.setStart(start);
      searchInput.setMax(max);
      return new ClearanceConfigSearchResult((long) resultList.size(),
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
   public ClearanceConfigSearchResult findBy(ClearanceConfigSearchInput searchInput)
   {
      SingularAttribute<ClearanceConfig, ?>[] attributes = readSeachAttributes(searchInput);
      Long count = ejb.countBy(searchInput.getEntity(), attributes);
      List<ClearanceConfig> resultList = ejb.findBy(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new ClearanceConfigSearchResult(count, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countBy")
   @Consumes({ "application/json", "application/xml" })
   public Long countBy(ClearanceConfigSearchInput searchInput)
   {
      SingularAttribute<ClearanceConfig, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countBy(searchInput.getEntity(), attributes);
   }

   @POST
   @Path("/findByLike")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public ClearanceConfigSearchResult findByLike(ClearanceConfigSearchInput searchInput)
   {
      SingularAttribute<ClearanceConfig, ?>[] attributes = readSeachAttributes(searchInput);
      Long countLike = ejb.countByLike(searchInput.getEntity(), attributes);
      List<ClearanceConfig> resultList = ejb.findByLike(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new ClearanceConfigSearchResult(countLike, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countByLike")
   @Consumes({ "application/json", "application/xml" })
   public Long countByLike(ClearanceConfigSearchInput searchInput)
   {
      SingularAttribute<ClearanceConfig, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countByLike(searchInput.getEntity(), attributes);
   }

   @SuppressWarnings("unchecked")
   private SingularAttribute<ClearanceConfig, ?>[] readSeachAttributes(
         ClearanceConfigSearchInput searchInput)
   {
      List<String> fieldNames = searchInput.getFieldNames();
      List<SingularAttribute<ClearanceConfig, ?>> result = new ArrayList<SingularAttribute<ClearanceConfig, ?>>();
      for (String fieldName : fieldNames)
      {
         Field[] fields = ClearanceConfig_.class.getFields();
         for (Field field : fields)
         {
            if (field.getName().equals(fieldName))
            {
               try
               {
                  result.add((SingularAttribute<ClearanceConfig, ?>) field.get(null));
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

   private ClearanceConfig detach(ClearanceConfig entity)
   {
      if (entity == null)
         return null;

      return entity;
   }

   private List<ClearanceConfig> detach(List<ClearanceConfig> list)
   {
      if (list == null)
         return list;
      List<ClearanceConfig> result = new ArrayList<ClearanceConfig>();
      for (ClearanceConfig entity : list)
      {
         result.add(detach(entity));
      }
      return result;
   }

   private ClearanceConfigSearchInput detach(ClearanceConfigSearchInput searchInput)
   {
      searchInput.setEntity(detach(searchInput.getEntity()));
      return searchInput;
   }
}