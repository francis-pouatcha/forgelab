package org.adorsys.adph.server.rest;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
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
import org.adorsys.adph.server.jpa.ProcurementOrder;
import org.adorsys.adph.server.jpa.ProcurementOrder_;
import org.adorsys.adph.server.jpa.ProcurementOrderSearchInput;
import org.adorsys.adph.server.repo.ProcurementOrderRepository;

/**
 * 
 */
@Stateless
@Path("/procurementorders")
public class ProcurementOrderEndpoint
{

   @Inject
   private ProcurementOrderRepository repository;

   @POST
   @Consumes("application/json")
   @Produces("application/xml")
   public ProcurementOrder create(ProcurementOrder entity)
   {
      /*
       * This avoids setting ids from outside. If not create and save will be very
       * similar, and an entity could be mistakenly overriden by just 
       * changing it's id. 
       */
      entity.setId(null);
      repository.save(entity);
      return entity;
   }

   @DELETE
   @Path("/{id:[0-9][0-9]*}")
   public Response deleteById(@PathParam("id") Long id)
   {
      ProcurementOrder entity = repository.findBy(id);
      if (entity == null)
      {
         return Response.status(Status.NOT_FOUND).build();
      }
      /*
       * TODO test if entity listener will be invoked upon deleting through
       * direct jpql queries.
       * repository.remove(entity);
       */
      repository.removeJPQL(entity.getId());
      return Response.ok(entity).build();
   }

   @PUT
   @Path("/{id:[0-9][0-9]*}")
   @Produces("application/xml")
   @Consumes("application/json")
   public ProcurementOrder update(ProcurementOrder entity)
   {
      ProcurementOrder saved = repository.save(entity);
      return saved;
   }

   @GET
   @Path("/{id:[0-9][0-9]*}")
   @Produces("application/json")
   public Response findById(@PathParam("id") Long id)
   {
      ProcurementOrder entity = repository.findBy(id);

      if (entity == null)
      {
         return Response.status(Status.NOT_FOUND).build();
      }
      return Response.ok(entity).build();
   }

   @GET
   @Produces("application/json")
   public List<ProcurementOrder> listAll(@QueryParam("start") int start,
         @QueryParam("max") int max)
   {
      return repository.findAll(start, max);
   }

   @GET
   @Path("/count")
   public Long count()
   {
      return repository.count();
   }

   @POST
   @Path("/findBy")
   @Produces("application/json")
   @Consumes("application/json")
   public List<ProcurementOrder> findBy(ProcurementOrderSearchInput searchInput)
   {
      SingularAttribute<ProcurementOrder, ?>[] attributes = readSeachAttributes(searchInput);
      return repository.findBy(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
   }

   @POST
   @Path("/countBy")
   @Consumes("application/json")
   public Long countBy(ProcurementOrderSearchInput searchInput)
   {
      SingularAttribute<ProcurementOrder, ?>[] attributes = readSeachAttributes(searchInput);
      return repository.count(searchInput.getEntity(), attributes);
   }

   @POST
   @Path("/findByLike")
   @Produces("application/json")
   @Consumes("application/json")
   public List<ProcurementOrder> findByLike(ProcurementOrderSearchInput searchInput)
   {
      SingularAttribute<ProcurementOrder, ?>[] attributes = readSeachAttributes(searchInput);
      return repository.findByLike(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
   }

   @POST
   @Path("/countByLike")
   @Consumes("application/json")
   public Long countByLike(ProcurementOrderSearchInput searchInput)
   {
      SingularAttribute<ProcurementOrder, ?>[] attributes = readSeachAttributes(searchInput);
      return repository.countLike(searchInput.getEntity(), attributes);
   }

   @SuppressWarnings("unchecked")
   private SingularAttribute<ProcurementOrder, ?>[] readSeachAttributes(
         ProcurementOrderSearchInput searchInput)
   {
      List<String> fieldNames = searchInput.getFieldNames();
      List<SingularAttribute<ProcurementOrder, ?>> result = new ArrayList<SingularAttribute<ProcurementOrder, ?>>();
      for (String fieldName : fieldNames)
      {
         Field[] fields = ProcurementOrder_.class.getFields();
         for (Field field : fields)
         {
            if (field.getName().equals(fieldName))
            {
               try
               {
                  result.add((SingularAttribute<ProcurementOrder, ?>) field.get(null));
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
}