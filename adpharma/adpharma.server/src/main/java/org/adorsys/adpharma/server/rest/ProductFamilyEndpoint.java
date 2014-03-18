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
import org.adorsys.adpharma.server.jpa.ProductFamily;
import org.adorsys.adpharma.server.jpa.ProductFamily_;
import org.adorsys.adpharma.server.jpa.ProductFamilySearchInput;
import org.adorsys.adpharma.server.jpa.ProductFamilySearchResult;

/**
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/productfamilys")
public class ProductFamilyEndpoint
{

   @Inject
   private ProductFamilyEJB ejb;

   @Inject
   private ProductFamilyMerger productFamilyMerger;

   @POST
   @Consumes({ "application/json", "application/xml" })
   @Produces({ "application/json", "application/xml" })
   public ProductFamily create(ProductFamily entity)
   {
      return detach(ejb.create(entity));
   }

   @DELETE
   @Path("/{id:[0-9][0-9]*}")
   public Response deleteById(@PathParam("id") Long id)
   {
      ProductFamily deleted = ejb.deleteById(id);
      if (deleted == null)
         return Response.status(Status.NOT_FOUND).build();

      return Response.ok(detach(deleted)).build();
   }

   @PUT
   @Path("/{id:[0-9][0-9]*}")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public ProductFamily update(ProductFamily entity)
   {
      return detach(ejb.update(entity));
   }

   @GET
   @Path("/{id:[0-9][0-9]*}")
   @Produces({ "application/json", "application/xml" })
   public Response findById(@PathParam("id") Long id)
   {
      ProductFamily found = ejb.findById(id);
      if (found == null)
         return Response.status(Status.NOT_FOUND).build();
      return Response.ok(detach(found)).build();
   }

   @GET
   @Produces({ "application/json", "application/xml" })
   public ProductFamilySearchResult listAll(@QueryParam("start") int start,
         @QueryParam("max") int max)
   {
      List<ProductFamily> resultList = ejb.listAll(start, max);
      ProductFamilySearchInput searchInput = new ProductFamilySearchInput();
      searchInput.setStart(start);
      searchInput.setMax(max);
      return new ProductFamilySearchResult((long) resultList.size(),
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
   public ProductFamilySearchResult findBy(ProductFamilySearchInput searchInput)
   {
      SingularAttribute<ProductFamily, ?>[] attributes = readSeachAttributes(searchInput);
      Long count = ejb.countBy(searchInput.getEntity(), attributes);
      List<ProductFamily> resultList = ejb.findBy(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new ProductFamilySearchResult(count, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countBy")
   @Consumes({ "application/json", "application/xml" })
   public Long countBy(ProductFamilySearchInput searchInput)
   {
      SingularAttribute<ProductFamily, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countBy(searchInput.getEntity(), attributes);
   }

   @POST
   @Path("/findByLike")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public ProductFamilySearchResult findByLike(ProductFamilySearchInput searchInput)
   {
      SingularAttribute<ProductFamily, ?>[] attributes = readSeachAttributes(searchInput);
      Long countLike = ejb.countByLike(searchInput.getEntity(), attributes);
      List<ProductFamily> resultList = ejb.findByLike(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new ProductFamilySearchResult(countLike, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countByLike")
   @Consumes({ "application/json", "application/xml" })
   public Long countByLike(ProductFamilySearchInput searchInput)
   {
      SingularAttribute<ProductFamily, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countByLike(searchInput.getEntity(), attributes);
   }

   @SuppressWarnings("unchecked")
   private SingularAttribute<ProductFamily, ?>[] readSeachAttributes(
         ProductFamilySearchInput searchInput)
   {
      List<String> fieldNames = searchInput.getFieldNames();
      List<SingularAttribute<ProductFamily, ?>> result = new ArrayList<SingularAttribute<ProductFamily, ?>>();
      for (String fieldName : fieldNames)
      {
         Field[] fields = ProductFamily_.class.getFields();
         for (Field field : fields)
         {
            if (field.getName().equals(fieldName))
            {
               try
               {
                  result.add((SingularAttribute<ProductFamily, ?>) field.get(null));
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

   private static final List<String> parentFamillyFields = Arrays.asList("name");

   private ProductFamily detach(ProductFamily entity)
   {
      if (entity == null)
         return null;

      // aggregated
      entity.setParentFamilly(productFamilyMerger.unbind(entity.getParentFamilly(), parentFamillyFields));

      return entity;
   }

   private List<ProductFamily> detach(List<ProductFamily> list)
   {
      if (list == null)
         return list;
      List<ProductFamily> result = new ArrayList<ProductFamily>();
      for (ProductFamily entity : list)
      {
         result.add(detach(entity));
      }
      return result;
   }

   private ProductFamilySearchInput detach(ProductFamilySearchInput searchInput)
   {
      searchInput.setEntity(detach(searchInput.getEntity()));
      return searchInput;
   }
}