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
import org.adorsys.adpharma.server.jpa.LoginRoleNameAssoc;
import org.adorsys.adpharma.server.jpa.LoginRoleNameAssoc_;
import org.adorsys.adpharma.server.jpa.LoginRoleNameAssocSearchInput;
import org.adorsys.adpharma.server.jpa.LoginRoleNameAssocSearchResult;

/**
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/loginrolenameassocs")
public class LoginRoleNameAssocEndpoint
{

   @Inject
   private LoginRoleNameAssocEJB ejb;

   @Inject
   private LoginMerger loginMerger;

   @Inject
   private RoleNameMerger roleNameMerger;

   @POST
   @Consumes({ "application/json", "application/xml" })
   @Produces({ "application/json", "application/xml" })
   public LoginRoleNameAssoc create(LoginRoleNameAssoc entity)
   {
      return detach(ejb.create(entity));
   }

   @DELETE
   @Path("/{id:[0-9][0-9]*}")
   public Response deleteById(@PathParam("id") Long id)
   {
      LoginRoleNameAssoc deleted = ejb.deleteById(id);
      if (deleted == null)
         return Response.status(Status.NOT_FOUND).build();

      return Response.ok(detach(deleted)).build();
   }

   @PUT
   @Path("/{id:[0-9][0-9]*}")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public LoginRoleNameAssoc update(LoginRoleNameAssoc entity)
   {
      return detach(ejb.update(entity));
   }

   @GET
   @Path("/{id:[0-9][0-9]*}")
   @Produces({ "application/json", "application/xml" })
   public Response findById(@PathParam("id") Long id)
   {
      LoginRoleNameAssoc found = ejb.findById(id);
      if (found == null)
         return Response.status(Status.NOT_FOUND).build();
      return Response.ok(detach(found)).build();
   }

   @GET
   @Produces({ "application/json", "application/xml" })
   public LoginRoleNameAssocSearchResult listAll(@QueryParam("start") int start,
         @QueryParam("max") int max)
   {
      List<LoginRoleNameAssoc> resultList = ejb.listAll(start, max);
      LoginRoleNameAssocSearchInput searchInput = new LoginRoleNameAssocSearchInput();
      searchInput.setStart(start);
      searchInput.setMax(max);
      return new LoginRoleNameAssocSearchResult((long) resultList.size(),
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
   public LoginRoleNameAssocSearchResult findBy(LoginRoleNameAssocSearchInput searchInput)
   {
      SingularAttribute<LoginRoleNameAssoc, ?>[] attributes = readSeachAttributes(searchInput);
      Long count = ejb.countBy(searchInput.getEntity(), attributes);
      List<LoginRoleNameAssoc> resultList = ejb.findBy(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new LoginRoleNameAssocSearchResult(count, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countBy")
   @Consumes({ "application/json", "application/xml" })
   public Long countBy(LoginRoleNameAssocSearchInput searchInput)
   {
      SingularAttribute<LoginRoleNameAssoc, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countBy(searchInput.getEntity(), attributes);
   }

   @POST
   @Path("/findByLike")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public LoginRoleNameAssocSearchResult findByLike(LoginRoleNameAssocSearchInput searchInput)
   {
      SingularAttribute<LoginRoleNameAssoc, ?>[] attributes = readSeachAttributes(searchInput);
      Long countLike = ejb.countByLike(searchInput.getEntity(), attributes);
      List<LoginRoleNameAssoc> resultList = ejb.findByLike(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new LoginRoleNameAssocSearchResult(countLike, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countByLike")
   @Consumes({ "application/json", "application/xml" })
   public Long countByLike(LoginRoleNameAssocSearchInput searchInput)
   {
      SingularAttribute<LoginRoleNameAssoc, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countByLike(searchInput.getEntity(), attributes);
   }

   @SuppressWarnings("unchecked")
   private SingularAttribute<LoginRoleNameAssoc, ?>[] readSeachAttributes(
         LoginRoleNameAssocSearchInput searchInput)
   {
      List<String> fieldNames = searchInput.getFieldNames();
      List<SingularAttribute<LoginRoleNameAssoc, ?>> result = new ArrayList<SingularAttribute<LoginRoleNameAssoc, ?>>();
      for (String fieldName : fieldNames)
      {
         Field[] fields = LoginRoleNameAssoc_.class.getFields();
         for (Field field : fields)
         {
            if (field.getName().equals(fieldName))
            {
               try
               {
                  result.add((SingularAttribute<LoginRoleNameAssoc, ?>) field.get(null));
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

   private static final List<String> s_roleNames_t_source_sourceFields = Arrays.asList("loginName", "email");

   private static final List<String> s_roleNames_t_source_targetFields = Arrays.asList("name");

   private LoginRoleNameAssoc detach(LoginRoleNameAssoc entity)
   {
      if (entity == null)
         return null;

      // aggregated
      entity.setSource(loginMerger.unbind(entity.getSource(), s_roleNames_t_source_sourceFields));

      // aggregated
      entity.setTarget(roleNameMerger.unbind(entity.getTarget(), s_roleNames_t_source_targetFields));

      return entity;
   }

   private List<LoginRoleNameAssoc> detach(List<LoginRoleNameAssoc> list)
   {
      if (list == null)
         return list;
      List<LoginRoleNameAssoc> result = new ArrayList<LoginRoleNameAssoc>();
      for (LoginRoleNameAssoc entity : list)
      {
         result.add(detach(entity));
      }
      return result;
   }

   private LoginRoleNameAssocSearchInput detach(LoginRoleNameAssocSearchInput searchInput)
   {
      searchInput.setEntity(detach(searchInput.getEntity()));
      return searchInput;
   }
}