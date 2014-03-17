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
import org.adorsys.adpharma.server.jpa.CustomerCategory;
import org.adorsys.adpharma.server.jpa.CustomerCategory_;
import org.adorsys.adpharma.server.jpa.CustomerCategorySearchInput;
import org.adorsys.adpharma.server.jpa.CustomerCategorySearchResult;

/**
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/customercategorys")
public class CustomerCategoryEndpoint
{

   @Inject
   private CustomerCategoryEJB ejb;

   @POST
   @Consumes({ "application/json", "application/xml" })
   @Produces({ "application/json", "application/xml" })
   public CustomerCategory create(CustomerCategory entity)
   {
      return detach(ejb.create(entity));
   }

   @DELETE
   @Path("/{id:[0-9][0-9]*}")
   public Response deleteById(@PathParam("id") Long id)
   {
      CustomerCategory deleted = ejb.deleteById(id);
      if (deleted == null)
         return Response.status(Status.NOT_FOUND).build();

      return Response.ok(detach(deleted)).build();
   }

   @PUT
   @Path("/{id:[0-9][0-9]*}")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public CustomerCategory update(CustomerCategory entity)
   {
      return detach(ejb.update(entity));
   }

   @GET
   @Path("/{id:[0-9][0-9]*}")
   @Produces({ "application/json", "application/xml" })
   public Response findById(@PathParam("id") Long id)
   {
      CustomerCategory found = ejb.findById(id);
      if (found == null)
         return Response.status(Status.NOT_FOUND).build();
      return Response.ok(detach(found)).build();
   }

   @GET
   @Produces({ "application/json", "application/xml" })
   public CustomerCategorySearchResult listAll(@QueryParam("start") int start,
         @QueryParam("max") int max)
   {
      List<CustomerCategory> resultList = ejb.listAll(start, max);
      CustomerCategorySearchInput searchInput = new CustomerCategorySearchInput();
      searchInput.setStart(start);
      searchInput.setMax(max);
      return new CustomerCategorySearchResult((long) resultList.size(),
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
   public CustomerCategorySearchResult findBy(CustomerCategorySearchInput searchInput)
   {
      SingularAttribute<CustomerCategory, ?>[] attributes = readSeachAttributes(searchInput);
      Long count = ejb.countBy(searchInput.getEntity(), attributes);
      List<CustomerCategory> resultList = ejb.findBy(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new CustomerCategorySearchResult(count, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countBy")
   @Consumes({ "application/json", "application/xml" })
   public Long countBy(CustomerCategorySearchInput searchInput)
   {
      SingularAttribute<CustomerCategory, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countBy(searchInput.getEntity(), attributes);
   }

   @POST
   @Path("/findByLike")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public CustomerCategorySearchResult findByLike(CustomerCategorySearchInput searchInput)
   {
      SingularAttribute<CustomerCategory, ?>[] attributes = readSeachAttributes(searchInput);
      Long countLike = ejb.countByLike(searchInput.getEntity(), attributes);
      List<CustomerCategory> resultList = ejb.findByLike(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new CustomerCategorySearchResult(countLike, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countByLike")
   @Consumes({ "application/json", "application/xml" })
   public Long countByLike(CustomerCategorySearchInput searchInput)
   {
      SingularAttribute<CustomerCategory, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countByLike(searchInput.getEntity(), attributes);
   }

   @SuppressWarnings("unchecked")
   private SingularAttribute<CustomerCategory, ?>[] readSeachAttributes(
         CustomerCategorySearchInput searchInput)
   {
      List<String> fieldNames = searchInput.getFieldNames();
      List<SingularAttribute<CustomerCategory, ?>> result = new ArrayList<SingularAttribute<CustomerCategory, ?>>();
      for (String fieldName : fieldNames)
      {
         Field[] fields = CustomerCategory_.class.getFields();
         for (Field field : fields)
         {
            if (field.getName().equals(fieldName))
            {
               try
               {
                  result.add((SingularAttribute<CustomerCategory, ?>) field.get(null));
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

   private CustomerCategory detach(CustomerCategory entity)
   {
      if (entity == null)
         return null;

      return entity;
   }

   private List<CustomerCategory> detach(List<CustomerCategory> list)
   {
      if (list == null)
         return list;
      List<CustomerCategory> result = new ArrayList<CustomerCategory>();
      for (CustomerCategory entity : list)
      {
         result.add(detach(entity));
      }
      return result;
   }

   private CustomerCategorySearchInput detach(CustomerCategorySearchInput searchInput)
   {
      searchInput.setEntity(detach(searchInput.getEntity()));
      return searchInput;
   }
}