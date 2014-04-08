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
import org.adorsys.adpharma.server.jpa.DocumentStore;
import org.adorsys.adpharma.server.jpa.DocumentStore_;
import org.adorsys.adpharma.server.jpa.DocumentStoreSearchInput;
import org.adorsys.adpharma.server.jpa.DocumentStoreSearchResult;

/**
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/documentstores")
public class DocumentStoreEndpoint
{

   @Inject
   private DocumentStoreEJB ejb;

   @Inject
   private LoginMerger loginMerger;

   @POST
   @Consumes({ "application/json", "application/xml" })
   @Produces({ "application/json", "application/xml" })
   public DocumentStore create(DocumentStore entity)
   {
      return detach(ejb.create(entity));
   }

   @DELETE
   @Path("/{id:[0-9][0-9]*}")
   public Response deleteById(@PathParam("id") Long id)
   {
      DocumentStore deleted = ejb.deleteById(id);
      if (deleted == null)
         return Response.status(Status.NOT_FOUND).build();

      return Response.ok(detach(deleted)).build();
   }

   @PUT
   @Path("/{id:[0-9][0-9]*}")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public DocumentStore update(DocumentStore entity)
   {
      return detach(ejb.update(entity));
   }

   @GET
   @Path("/{id:[0-9][0-9]*}")
   @Produces({ "application/json", "application/xml" })
   public Response findById(@PathParam("id") Long id)
   {
      DocumentStore found = ejb.findById(id);
      if (found == null)
         return Response.status(Status.NOT_FOUND).build();
      return Response.ok(detach(found)).build();
   }

   @GET
   @Produces({ "application/json", "application/xml" })
   public DocumentStoreSearchResult listAll(@QueryParam("start") int start,
         @QueryParam("max") int max)
   {
      List<DocumentStore> resultList = ejb.listAll(start, max);
      DocumentStoreSearchInput searchInput = new DocumentStoreSearchInput();
      searchInput.setStart(start);
      searchInput.setMax(max);
      return new DocumentStoreSearchResult((long) resultList.size(),
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
   public DocumentStoreSearchResult findBy(DocumentStoreSearchInput searchInput)
   {
      SingularAttribute<DocumentStore, ?>[] attributes = readSeachAttributes(searchInput);
      Long count = ejb.countBy(searchInput.getEntity(), attributes);
      List<DocumentStore> resultList = ejb.findBy(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new DocumentStoreSearchResult(count, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countBy")
   @Consumes({ "application/json", "application/xml" })
   public Long countBy(DocumentStoreSearchInput searchInput)
   {
      SingularAttribute<DocumentStore, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countBy(searchInput.getEntity(), attributes);
   }

   @POST
   @Path("/findByLike")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public DocumentStoreSearchResult findByLike(DocumentStoreSearchInput searchInput)
   {
      SingularAttribute<DocumentStore, ?>[] attributes = readSeachAttributes(searchInput);
      Long countLike = ejb.countByLike(searchInput.getEntity(), attributes);
      List<DocumentStore> resultList = ejb.findByLike(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new DocumentStoreSearchResult(countLike, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countByLike")
   @Consumes({ "application/json", "application/xml" })
   public Long countByLike(DocumentStoreSearchInput searchInput)
   {
      SingularAttribute<DocumentStore, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countByLike(searchInput.getEntity(), attributes);
   }

   @SuppressWarnings("unchecked")
   private SingularAttribute<DocumentStore, ?>[] readSeachAttributes(
         DocumentStoreSearchInput searchInput)
   {
      List<String> fieldNames = searchInput.getFieldNames();
      List<SingularAttribute<DocumentStore, ?>> result = new ArrayList<SingularAttribute<DocumentStore, ?>>();
      for (String fieldName : fieldNames)
      {
         Field[] fields = DocumentStore_.class.getFields();
         for (Field field : fields)
         {
            if (field.getName().equals(fieldName))
            {
               try
               {
                  result.add((SingularAttribute<DocumentStore, ?>) field.get(null));
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

   private static final List<String> recordingUserFields = Arrays.asList("loginName", "email", "gender");

   private DocumentStore detach(DocumentStore entity)
   {
      if (entity == null)
         return null;

      // aggregated
      entity.setRecordingUser(loginMerger.unbind(entity.getRecordingUser(), recordingUserFields));

      return entity;
   }

   private List<DocumentStore> detach(List<DocumentStore> list)
   {
      if (list == null)
         return list;
      List<DocumentStore> result = new ArrayList<DocumentStore>();
      for (DocumentStore entity : list)
      {
         result.add(detach(entity));
      }
      return result;
   }

   private DocumentStoreSearchInput detach(DocumentStoreSearchInput searchInput)
   {
      searchInput.setEntity(detach(searchInput.getEntity()));
      return searchInput;
   }
}