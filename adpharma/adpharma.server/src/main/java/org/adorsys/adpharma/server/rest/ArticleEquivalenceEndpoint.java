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
import org.adorsys.adpharma.server.jpa.ArticleEquivalence;
import org.adorsys.adpharma.server.jpa.ArticleEquivalence_;
import org.adorsys.adpharma.server.jpa.ArticleEquivalenceSearchInput;
import org.adorsys.adpharma.server.jpa.ArticleEquivalenceSearchResult;

/**
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/articleequivalences")
public class ArticleEquivalenceEndpoint
{

   @Inject
   private ArticleEquivalenceEJB ejb;

   @Inject
   private ArticleMerger articleMerger;

   @POST
   @Consumes({ "application/json", "application/xml" })
   @Produces({ "application/json", "application/xml" })
   public ArticleEquivalence create(ArticleEquivalence entity)
   {
      return detach(ejb.create(entity));
   }

   @DELETE
   @Path("/{id:[0-9][0-9]*}")
   public Response deleteById(@PathParam("id") Long id)
   {
      ArticleEquivalence deleted = ejb.deleteById(id);
      if (deleted == null)
         return Response.status(Status.NOT_FOUND).build();

      return Response.ok(detach(deleted)).build();
   }

   @PUT
   @Path("/{id:[0-9][0-9]*}")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public ArticleEquivalence update(ArticleEquivalence entity)
   {
      return detach(ejb.update(entity));
   }

   @GET
   @Path("/{id:[0-9][0-9]*}")
   @Produces({ "application/json", "application/xml" })
   public Response findById(@PathParam("id") Long id)
   {
      ArticleEquivalence found = ejb.findById(id);
      if (found == null)
         return Response.status(Status.NOT_FOUND).build();
      return Response.ok(detach(found)).build();
   }

   @GET
   @Produces({ "application/json", "application/xml" })
   public ArticleEquivalenceSearchResult listAll(@QueryParam("start") int start,
         @QueryParam("max") int max)
   {
      List<ArticleEquivalence> resultList = ejb.listAll(start, max);
      ArticleEquivalenceSearchInput searchInput = new ArticleEquivalenceSearchInput();
      searchInput.setStart(start);
      searchInput.setMax(max);
      return new ArticleEquivalenceSearchResult((long) resultList.size(),
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
   public ArticleEquivalenceSearchResult findBy(ArticleEquivalenceSearchInput searchInput)
   {
      SingularAttribute<ArticleEquivalence, ?>[] attributes = readSeachAttributes(searchInput);
      Long count = ejb.countBy(searchInput.getEntity(), attributes);
      List<ArticleEquivalence> resultList = ejb.findBy(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new ArticleEquivalenceSearchResult(count, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countBy")
   @Consumes({ "application/json", "application/xml" })
   public Long countBy(ArticleEquivalenceSearchInput searchInput)
   {
      SingularAttribute<ArticleEquivalence, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countBy(searchInput.getEntity(), attributes);
   }

   @POST
   @Path("/findByLike")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public ArticleEquivalenceSearchResult findByLike(ArticleEquivalenceSearchInput searchInput)
   {
      SingularAttribute<ArticleEquivalence, ?>[] attributes = readSeachAttributes(searchInput);
      Long countLike = ejb.countByLike(searchInput.getEntity(), attributes);
      List<ArticleEquivalence> resultList = ejb.findByLike(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new ArticleEquivalenceSearchResult(countLike, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countByLike")
   @Consumes({ "application/json", "application/xml" })
   public Long countByLike(ArticleEquivalenceSearchInput searchInput)
   {
      SingularAttribute<ArticleEquivalence, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countByLike(searchInput.getEntity(), attributes);
   }

   @SuppressWarnings("unchecked")
   private SingularAttribute<ArticleEquivalence, ?>[] readSeachAttributes(
         ArticleEquivalenceSearchInput searchInput)
   {
      List<String> fieldNames = searchInput.getFieldNames();
      List<SingularAttribute<ArticleEquivalence, ?>> result = new ArrayList<SingularAttribute<ArticleEquivalence, ?>>();
      for (String fieldName : fieldNames)
      {
         Field[] fields = ArticleEquivalence_.class.getFields();
         for (Field field : fields)
         {
            if (field.getName().equals(fieldName))
            {
               try
               {
                  result.add((SingularAttribute<ArticleEquivalence, ?>) field.get(null));
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

   private static final List<String> mainArticleFields = Arrays.asList("articleName", "pic", "manufacturer", "active", "qtyInStock", "sppu", "authorizedSale", "agency.name");

   private static final List<String> equivalentArticleFields = Arrays.asList("articleName", "pic", "manufacturer", "active", "qtyInStock", "sppu", "authorizedSale", "agency.name");

   private ArticleEquivalence detach(ArticleEquivalence entity)
   {
      if (entity == null)
         return null;

      // aggregated
      entity.setMainArticle(articleMerger.unbind(entity.getMainArticle(), mainArticleFields));

      // aggregated
      entity.setEquivalentArticle(articleMerger.unbind(entity.getEquivalentArticle(), equivalentArticleFields));

      return entity;
   }

   private List<ArticleEquivalence> detach(List<ArticleEquivalence> list)
   {
      if (list == null)
         return list;
      List<ArticleEquivalence> result = new ArrayList<ArticleEquivalence>();
      for (ArticleEquivalence entity : list)
      {
         result.add(detach(entity));
      }
      return result;
   }

   private ArticleEquivalenceSearchInput detach(ArticleEquivalenceSearchInput searchInput)
   {
      searchInput.setEntity(detach(searchInput.getEntity()));
      return searchInput;
   }
}