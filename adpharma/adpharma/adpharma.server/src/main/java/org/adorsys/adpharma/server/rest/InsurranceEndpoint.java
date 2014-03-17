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
import org.adorsys.adpharma.server.jpa.Insurrance;
import org.adorsys.adpharma.server.jpa.Insurrance_;
import org.adorsys.adpharma.server.jpa.InsurranceSearchInput;
import org.adorsys.adpharma.server.jpa.InsurranceSearchResult;

/**
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/insurrances")
public class InsurranceEndpoint
{

   @Inject
   private InsurranceEJB ejb;

   @Inject
   private CustomerMerger customerMerger;

   @POST
   @Consumes({ "application/json", "application/xml" })
   @Produces({ "application/json", "application/xml" })
   public Insurrance create(Insurrance entity)
   {
      return detach(ejb.create(entity));
   }

   @DELETE
   @Path("/{id:[0-9][0-9]*}")
   public Response deleteById(@PathParam("id") Long id)
   {
      Insurrance deleted = ejb.deleteById(id);
      if (deleted == null)
         return Response.status(Status.NOT_FOUND).build();

      return Response.ok(detach(deleted)).build();
   }

   @PUT
   @Path("/{id:[0-9][0-9]*}")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public Insurrance update(Insurrance entity)
   {
      return detach(ejb.update(entity));
   }

   @GET
   @Path("/{id:[0-9][0-9]*}")
   @Produces({ "application/json", "application/xml" })
   public Response findById(@PathParam("id") Long id)
   {
      Insurrance found = ejb.findById(id);
      if (found == null)
         return Response.status(Status.NOT_FOUND).build();
      return Response.ok(detach(found)).build();
   }

   @GET
   @Produces({ "application/json", "application/xml" })
   public InsurranceSearchResult listAll(@QueryParam("start") int start,
         @QueryParam("max") int max)
   {
      List<Insurrance> resultList = ejb.listAll(start, max);
      InsurranceSearchInput searchInput = new InsurranceSearchInput();
      searchInput.setStart(start);
      searchInput.setMax(max);
      return new InsurranceSearchResult((long) resultList.size(),
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
   public InsurranceSearchResult findBy(InsurranceSearchInput searchInput)
   {
      SingularAttribute<Insurrance, ?>[] attributes = readSeachAttributes(searchInput);
      Long count = ejb.countBy(searchInput.getEntity(), attributes);
      List<Insurrance> resultList = ejb.findBy(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new InsurranceSearchResult(count, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countBy")
   @Consumes({ "application/json", "application/xml" })
   public Long countBy(InsurranceSearchInput searchInput)
   {
      SingularAttribute<Insurrance, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countBy(searchInput.getEntity(), attributes);
   }

   @POST
   @Path("/findByLike")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public InsurranceSearchResult findByLike(InsurranceSearchInput searchInput)
   {
      SingularAttribute<Insurrance, ?>[] attributes = readSeachAttributes(searchInput);
      Long countLike = ejb.countByLike(searchInput.getEntity(), attributes);
      List<Insurrance> resultList = ejb.findByLike(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new InsurranceSearchResult(countLike, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countByLike")
   @Consumes({ "application/json", "application/xml" })
   public Long countByLike(InsurranceSearchInput searchInput)
   {
      SingularAttribute<Insurrance, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countByLike(searchInput.getEntity(), attributes);
   }

   @SuppressWarnings("unchecked")
   private SingularAttribute<Insurrance, ?>[] readSeachAttributes(
         InsurranceSearchInput searchInput)
   {
      List<String> fieldNames = searchInput.getFieldNames();
      List<SingularAttribute<Insurrance, ?>> result = new ArrayList<SingularAttribute<Insurrance, ?>>();
      for (String fieldName : fieldNames)
      {
         Field[] fields = Insurrance_.class.getFields();
         for (Field field : fields)
         {
            if (field.getName().equals(fieldName))
            {
               try
               {
                  result.add((SingularAttribute<Insurrance, ?>) field.get(null));
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

   private static final List<String> customerFields = Arrays.asList("fullName", "birthDate", "landLinePhone", "mobile", "fax", "email", "creditAuthorized", "discountAuthorized");

   private static final List<String> insurerFields = Arrays.asList("fullName", "birthDate", "landLinePhone", "mobile", "fax", "email", "creditAuthorized", "discountAuthorized");

   private Insurrance detach(Insurrance entity)
   {
      if (entity == null)
         return null;

      // aggregated
      entity.setCustomer(customerMerger.unbind(entity.getCustomer(), customerFields));

      // aggregated
      entity.setInsurer(customerMerger.unbind(entity.getInsurer(), insurerFields));

      return entity;
   }

   private List<Insurrance> detach(List<Insurrance> list)
   {
      if (list == null)
         return list;
      List<Insurrance> result = new ArrayList<Insurrance>();
      for (Insurrance entity : list)
      {
         result.add(detach(entity));
      }
      return result;
   }

   private InsurranceSearchInput detach(InsurranceSearchInput searchInput)
   {
      searchInput.setEntity(detach(searchInput.getEntity()));
      return searchInput;
   }
}