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
import org.adorsys.adpharma.server.jpa.Customer;
import org.adorsys.adpharma.server.jpa.Customer_;
import org.adorsys.adpharma.server.jpa.CustomerSearchInput;
import org.adorsys.adpharma.server.jpa.CustomerSearchResult;

/**
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/customers")
public class CustomerEndpoint
{

   @Inject
   private CustomerEJB ejb;

   @Inject
   private EmployerMerger employerMerger;

   @Inject
   private CustomerCategoryMerger customerCategoryMerger;

   @POST
   @Consumes({ "application/json", "application/xml" })
   @Produces({ "application/json", "application/xml" })
   public Customer create(Customer entity)
   {
      return detach(ejb.create(entity));
   }

   @DELETE
   @Path("/{id:[0-9][0-9]*}")
   public Response deleteById(@PathParam("id") Long id)
   {
      Customer deleted = ejb.deleteById(id);
      if (deleted == null)
         return Response.status(Status.NOT_FOUND).build();

      return Response.ok(detach(deleted)).build();
   }

   @PUT
   @Path("/{id:[0-9][0-9]*}")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public Customer update(Customer entity)
   {
      return detach(ejb.update(entity));
   }

   @GET
   @Path("/{id:[0-9][0-9]*}")
   @Produces({ "application/json", "application/xml" })
   public Response findById(@PathParam("id") Long id)
   {
      Customer found = ejb.findById(id);
      if (found == null)
         return Response.status(Status.NOT_FOUND).build();
      return Response.ok(detach(found)).build();
   }

   @GET
   @Produces({ "application/json", "application/xml" })
   public CustomerSearchResult listAll(@QueryParam("start") int start,
         @QueryParam("max") int max)
   {
      List<Customer> resultList = ejb.listAll(start, max);
      CustomerSearchInput searchInput = new CustomerSearchInput();
      searchInput.setStart(start);
      searchInput.setMax(max);
      return new CustomerSearchResult((long) resultList.size(),
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
   public CustomerSearchResult findBy(CustomerSearchInput searchInput)
   {
      SingularAttribute<Customer, ?>[] attributes = readSeachAttributes(searchInput);
      Long count = ejb.countBy(searchInput.getEntity(), attributes);
      List<Customer> resultList = ejb.findBy(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new CustomerSearchResult(count, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countBy")
   @Consumes({ "application/json", "application/xml" })
   public Long countBy(CustomerSearchInput searchInput)
   {
      SingularAttribute<Customer, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countBy(searchInput.getEntity(), attributes);
   }

   @POST
   @Path("/findByLike")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public CustomerSearchResult findByLike(CustomerSearchInput searchInput)
   {
      SingularAttribute<Customer, ?>[] attributes = readSeachAttributes(searchInput);
      Long countLike = ejb.countByLike(searchInput.getEntity(), attributes);
      List<Customer> resultList = ejb.findByLike(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new CustomerSearchResult(countLike, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countByLike")
   @Consumes({ "application/json", "application/xml" })
   public Long countByLike(CustomerSearchInput searchInput)
   {
      SingularAttribute<Customer, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countByLike(searchInput.getEntity(), attributes);
   }

   @SuppressWarnings("unchecked")
   private SingularAttribute<Customer, ?>[] readSeachAttributes(
         CustomerSearchInput searchInput)
   {
      List<String> fieldNames = searchInput.getFieldNames();
      List<SingularAttribute<Customer, ?>> result = new ArrayList<SingularAttribute<Customer, ?>>();
      for (String fieldName : fieldNames)
      {
         Field[] fields = Customer_.class.getFields();
         for (Field field : fields)
         {
            if (field.getName().equals(fieldName))
            {
               try
               {
                  result.add((SingularAttribute<Customer, ?>) field.get(null));
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

   private static final List<String> employerFields = Arrays.asList("name", "phone");

   private static final List<String> customerCategoryFields = Arrays.asList("name", "discountRate");

   private Customer detach(Customer entity)
   {
      if (entity == null)
         return null;

      // aggregated
      entity.setEmployer(employerMerger.unbind(entity.getEmployer(), employerFields));

      // aggregated
      entity.setCustomerCategory(customerCategoryMerger.unbind(entity.getCustomerCategory(), customerCategoryFields));

      return entity;
   }

   private List<Customer> detach(List<Customer> list)
   {
      if (list == null)
         return list;
      List<Customer> result = new ArrayList<Customer>();
      for (Customer entity : list)
      {
         result.add(detach(entity));
      }
      return result;
   }

   private CustomerSearchInput detach(CustomerSearchInput searchInput)
   {
      searchInput.setEntity(detach(searchInput.getEntity()));
      return searchInput;
   }
}