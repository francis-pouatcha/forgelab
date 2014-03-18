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
import org.adorsys.adpharma.server.jpa.RoleNamePermissionNameAssoc;
import org.adorsys.adpharma.server.jpa.RoleNamePermissionNameAssoc_;
import org.adorsys.adpharma.server.jpa.RoleNamePermissionNameAssocSearchInput;
import org.adorsys.adpharma.server.jpa.RoleNamePermissionNameAssocSearchResult;

/**
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/rolenamepermissionnameassocs")
public class RoleNamePermissionNameAssocEndpoint
{

   @Inject
   private RoleNamePermissionNameAssocEJB ejb;

   @Inject
   private RoleNameMerger roleNameMerger;

   @Inject
   private PermissionNameMerger permissionNameMerger;

   @POST
   @Consumes({ "application/json", "application/xml" })
   @Produces({ "application/json", "application/xml" })
   public RoleNamePermissionNameAssoc create(RoleNamePermissionNameAssoc entity)
   {
      return detach(ejb.create(entity));
   }

   @DELETE
   @Path("/{id:[0-9][0-9]*}")
   public Response deleteById(@PathParam("id") Long id)
   {
      RoleNamePermissionNameAssoc deleted = ejb.deleteById(id);
      if (deleted == null)
         return Response.status(Status.NOT_FOUND).build();

      return Response.ok(detach(deleted)).build();
   }

   @PUT
   @Path("/{id:[0-9][0-9]*}")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public RoleNamePermissionNameAssoc update(RoleNamePermissionNameAssoc entity)
   {
      return detach(ejb.update(entity));
   }

   @GET
   @Path("/{id:[0-9][0-9]*}")
   @Produces({ "application/json", "application/xml" })
   public Response findById(@PathParam("id") Long id)
   {
      RoleNamePermissionNameAssoc found = ejb.findById(id);
      if (found == null)
         return Response.status(Status.NOT_FOUND).build();
      return Response.ok(detach(found)).build();
   }

   @GET
   @Produces({ "application/json", "application/xml" })
   public RoleNamePermissionNameAssocSearchResult listAll(@QueryParam("start") int start,
         @QueryParam("max") int max)
   {
      List<RoleNamePermissionNameAssoc> resultList = ejb.listAll(start, max);
      RoleNamePermissionNameAssocSearchInput searchInput = new RoleNamePermissionNameAssocSearchInput();
      searchInput.setStart(start);
      searchInput.setMax(max);
      return new RoleNamePermissionNameAssocSearchResult((long) resultList.size(),
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
   public RoleNamePermissionNameAssocSearchResult findBy(RoleNamePermissionNameAssocSearchInput searchInput)
   {
      SingularAttribute<RoleNamePermissionNameAssoc, ?>[] attributes = readSeachAttributes(searchInput);
      Long count = ejb.countBy(searchInput.getEntity(), attributes);
      List<RoleNamePermissionNameAssoc> resultList = ejb.findBy(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new RoleNamePermissionNameAssocSearchResult(count, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countBy")
   @Consumes({ "application/json", "application/xml" })
   public Long countBy(RoleNamePermissionNameAssocSearchInput searchInput)
   {
      SingularAttribute<RoleNamePermissionNameAssoc, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countBy(searchInput.getEntity(), attributes);
   }

   @POST
   @Path("/findByLike")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public RoleNamePermissionNameAssocSearchResult findByLike(RoleNamePermissionNameAssocSearchInput searchInput)
   {
      SingularAttribute<RoleNamePermissionNameAssoc, ?>[] attributes = readSeachAttributes(searchInput);
      Long countLike = ejb.countByLike(searchInput.getEntity(), attributes);
      List<RoleNamePermissionNameAssoc> resultList = ejb.findByLike(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new RoleNamePermissionNameAssocSearchResult(countLike, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countByLike")
   @Consumes({ "application/json", "application/xml" })
   public Long countByLike(RoleNamePermissionNameAssocSearchInput searchInput)
   {
      SingularAttribute<RoleNamePermissionNameAssoc, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countByLike(searchInput.getEntity(), attributes);
   }

   @SuppressWarnings("unchecked")
   private SingularAttribute<RoleNamePermissionNameAssoc, ?>[] readSeachAttributes(
         RoleNamePermissionNameAssocSearchInput searchInput)
   {
      List<String> fieldNames = searchInput.getFieldNames();
      List<SingularAttribute<RoleNamePermissionNameAssoc, ?>> result = new ArrayList<SingularAttribute<RoleNamePermissionNameAssoc, ?>>();
      for (String fieldName : fieldNames)
      {
         Field[] fields = RoleNamePermissionNameAssoc_.class.getFields();
         for (Field field : fields)
         {
            if (field.getName().equals(fieldName))
            {
               try
               {
                  result.add((SingularAttribute<RoleNamePermissionNameAssoc, ?>) field.get(null));
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

   private static final List<String> s_permissions_t_source_sourceFields = Arrays.asList("name");

   private static final List<String> s_permissions_t_source_targetFields = Arrays.asList("name", "action");

   private RoleNamePermissionNameAssoc detach(RoleNamePermissionNameAssoc entity)
   {
      if (entity == null)
         return null;

      // aggregated
      entity.setSource(roleNameMerger.unbind(entity.getSource(), s_permissions_t_source_sourceFields));

      // aggregated
      entity.setTarget(permissionNameMerger.unbind(entity.getTarget(), s_permissions_t_source_targetFields));

      return entity;
   }

   private List<RoleNamePermissionNameAssoc> detach(List<RoleNamePermissionNameAssoc> list)
   {
      if (list == null)
         return list;
      List<RoleNamePermissionNameAssoc> result = new ArrayList<RoleNamePermissionNameAssoc>();
      for (RoleNamePermissionNameAssoc entity : list)
      {
         result.add(detach(entity));
      }
      return result;
   }

   private RoleNamePermissionNameAssocSearchInput detach(RoleNamePermissionNameAssocSearchInput searchInput)
   {
      searchInput.setEntity(detach(searchInput.getEntity()));
      return searchInput;
   }
}