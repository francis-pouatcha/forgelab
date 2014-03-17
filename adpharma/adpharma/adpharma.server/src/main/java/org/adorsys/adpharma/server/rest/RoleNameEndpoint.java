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
import org.adorsys.adpharma.server.jpa.RoleName;
import org.adorsys.adpharma.server.jpa.RoleName_;
import org.adorsys.adpharma.server.jpa.RoleNameSearchInput;
import org.adorsys.adpharma.server.jpa.RoleNameSearchResult;

/**
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/rolenames")
public class RoleNameEndpoint
{

   @Inject
   private RoleNameEJB ejb;

   @Inject
   private RoleNamePermissionNameAssocMerger roleNamePermissionNameAssocMerger;

   @POST
   @Consumes({ "application/json", "application/xml" })
   @Produces({ "application/json", "application/xml" })
   public RoleName create(RoleName entity)
   {
      return detach(ejb.create(entity));
   }

   @DELETE
   @Path("/{id:[0-9][0-9]*}")
   public Response deleteById(@PathParam("id") Long id)
   {
      RoleName deleted = ejb.deleteById(id);
      if (deleted == null)
         return Response.status(Status.NOT_FOUND).build();

      return Response.ok(detach(deleted)).build();
   }

   @PUT
   @Path("/{id:[0-9][0-9]*}")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public RoleName update(RoleName entity)
   {
      return detach(ejb.update(entity));
   }

   @GET
   @Path("/{id:[0-9][0-9]*}")
   @Produces({ "application/json", "application/xml" })
   public Response findById(@PathParam("id") Long id)
   {
      RoleName found = ejb.findById(id);
      if (found == null)
         return Response.status(Status.NOT_FOUND).build();
      return Response.ok(detach(found)).build();
   }

   @GET
   @Produces({ "application/json", "application/xml" })
   public RoleNameSearchResult listAll(@QueryParam("start") int start,
         @QueryParam("max") int max)
   {
      List<RoleName> resultList = ejb.listAll(start, max);
      RoleNameSearchInput searchInput = new RoleNameSearchInput();
      searchInput.setStart(start);
      searchInput.setMax(max);
      return new RoleNameSearchResult((long) resultList.size(),
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
   public RoleNameSearchResult findBy(RoleNameSearchInput searchInput)
   {
      SingularAttribute<RoleName, ?>[] attributes = readSeachAttributes(searchInput);
      Long count = ejb.countBy(searchInput.getEntity(), attributes);
      List<RoleName> resultList = ejb.findBy(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new RoleNameSearchResult(count, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countBy")
   @Consumes({ "application/json", "application/xml" })
   public Long countBy(RoleNameSearchInput searchInput)
   {
      SingularAttribute<RoleName, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countBy(searchInput.getEntity(), attributes);
   }

   @POST
   @Path("/findByLike")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public RoleNameSearchResult findByLike(RoleNameSearchInput searchInput)
   {
      SingularAttribute<RoleName, ?>[] attributes = readSeachAttributes(searchInput);
      Long countLike = ejb.countByLike(searchInput.getEntity(), attributes);
      List<RoleName> resultList = ejb.findByLike(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new RoleNameSearchResult(countLike, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countByLike")
   @Consumes({ "application/json", "application/xml" })
   public Long countByLike(RoleNameSearchInput searchInput)
   {
      SingularAttribute<RoleName, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countByLike(searchInput.getEntity(), attributes);
   }

   @SuppressWarnings("unchecked")
   private SingularAttribute<RoleName, ?>[] readSeachAttributes(
         RoleNameSearchInput searchInput)
   {
      List<String> fieldNames = searchInput.getFieldNames();
      List<SingularAttribute<RoleName, ?>> result = new ArrayList<SingularAttribute<RoleName, ?>>();
      for (String fieldName : fieldNames)
      {
         Field[] fields = RoleName_.class.getFields();
         for (Field field : fields)
         {
            if (field.getName().equals(fieldName))
            {
               try
               {
                  result.add((SingularAttribute<RoleName, ?>) field.get(null));
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

   private static final List<String> permissionsFields = emptyList;

   private RoleName detach(RoleName entity)
   {
      if (entity == null)
         return null;

      // aggregated collection
      entity.setPermissions(roleNamePermissionNameAssocMerger.unbind(entity.getPermissions(), permissionsFields));

      return entity;
   }

   private List<RoleName> detach(List<RoleName> list)
   {
      if (list == null)
         return list;
      List<RoleName> result = new ArrayList<RoleName>();
      for (RoleName entity : list)
      {
         result.add(detach(entity));
      }
      return result;
   }

   private RoleNameSearchInput detach(RoleNameSearchInput searchInput)
   {
      searchInput.setEntity(detach(searchInput.getEntity()));
      return searchInput;
   }
}