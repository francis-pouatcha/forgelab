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
import org.adorsys.adpharma.server.jpa.DocumentArchive;
import org.adorsys.adpharma.server.jpa.DocumentArchive_;
import org.adorsys.adpharma.server.jpa.DocumentArchiveSearchInput;
import org.adorsys.adpharma.server.jpa.DocumentArchiveSearchResult;

/**
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/documentarchives")
public class DocumentArchiveEndpoint
{

   @Inject
   private DocumentArchiveEJB ejb;

   @Inject
   private LoginMerger loginMerger;

   @POST
   @Consumes({ "application/json", "application/xml" })
   @Produces({ "application/json", "application/xml" })
   public DocumentArchive create(DocumentArchive entity)
   {
      return detach(ejb.create(entity));
   }

   @DELETE
   @Path("/{id:[0-9][0-9]*}")
   public Response deleteById(@PathParam("id") Long id)
   {
      DocumentArchive deleted = ejb.deleteById(id);
      if (deleted == null)
         return Response.status(Status.NOT_FOUND).build();

      return Response.ok(detach(deleted)).build();
   }

   @PUT
   @Path("/{id:[0-9][0-9]*}")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public DocumentArchive update(DocumentArchive entity)
   {
      return detach(ejb.update(entity));
   }

   @GET
   @Path("/{id:[0-9][0-9]*}")
   @Produces({ "application/json", "application/xml" })
   public Response findById(@PathParam("id") Long id)
   {
      DocumentArchive found = ejb.findById(id);
      if (found == null)
         return Response.status(Status.NOT_FOUND).build();
      return Response.ok(detach(found)).build();
   }

   @GET
   @Produces({ "application/json", "application/xml" })
   public DocumentArchiveSearchResult listAll(@QueryParam("start") int start,
         @QueryParam("max") int max)
   {
      List<DocumentArchive> resultList = ejb.listAll(start, max);
      DocumentArchiveSearchInput searchInput = new DocumentArchiveSearchInput();
      searchInput.setStart(start);
      searchInput.setMax(max);
      return new DocumentArchiveSearchResult((long) resultList.size(),
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
   public DocumentArchiveSearchResult findBy(DocumentArchiveSearchInput searchInput)
   {
      SingularAttribute<DocumentArchive, ?>[] attributes = readSeachAttributes(searchInput);
      Long count = ejb.countBy(searchInput.getEntity(), attributes);
      List<DocumentArchive> resultList = ejb.findBy(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new DocumentArchiveSearchResult(count, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countBy")
   @Consumes({ "application/json", "application/xml" })
   public Long countBy(DocumentArchiveSearchInput searchInput)
   {
      SingularAttribute<DocumentArchive, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countBy(searchInput.getEntity(), attributes);
   }

   @POST
   @Path("/findByLike")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public DocumentArchiveSearchResult findByLike(DocumentArchiveSearchInput searchInput)
   {
      SingularAttribute<DocumentArchive, ?>[] attributes = readSeachAttributes(searchInput);
      Long countLike = ejb.countByLike(searchInput.getEntity(), attributes);
      List<DocumentArchive> resultList = ejb.findByLike(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new DocumentArchiveSearchResult(countLike, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countByLike")
   @Consumes({ "application/json", "application/xml" })
   public Long countByLike(DocumentArchiveSearchInput searchInput)
   {
      SingularAttribute<DocumentArchive, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countByLike(searchInput.getEntity(), attributes);
   }

   @SuppressWarnings("unchecked")
   private SingularAttribute<DocumentArchive, ?>[] readSeachAttributes(
         DocumentArchiveSearchInput searchInput)
   {
      List<String> fieldNames = searchInput.getFieldNames();
      List<SingularAttribute<DocumentArchive, ?>> result = new ArrayList<SingularAttribute<DocumentArchive, ?>>();
      for (String fieldName : fieldNames)
      {
         Field[] fields = DocumentArchive_.class.getFields();
         for (Field field : fields)
         {
            if (field.getName().equals(fieldName))
            {
               try
               {
                  result.add((SingularAttribute<DocumentArchive, ?>) field.get(null));
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

   private DocumentArchive detach(DocumentArchive entity)
   {
      if (entity == null)
         return null;

      // aggregated
      entity.setRecordingUser(loginMerger.unbind(entity.getRecordingUser(), recordingUserFields));

      return entity;
   }

   private List<DocumentArchive> detach(List<DocumentArchive> list)
   {
      if (list == null)
         return list;
      List<DocumentArchive> result = new ArrayList<DocumentArchive>();
      for (DocumentArchive entity : list)
      {
         result.add(detach(entity));
      }
      return result;
   }

   private DocumentArchiveSearchInput detach(DocumentArchiveSearchInput searchInput)
   {
      searchInput.setEntity(detach(searchInput.getEntity()));
      return searchInput;
   }
}