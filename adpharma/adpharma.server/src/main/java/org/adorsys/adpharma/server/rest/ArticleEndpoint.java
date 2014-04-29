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
import org.adorsys.adpharma.server.jpa.Article;
import org.adorsys.adpharma.server.jpa.Article_;
import org.adorsys.adpharma.server.jpa.ArticleSearchInput;
import org.adorsys.adpharma.server.jpa.ArticleSearchResult;

/**
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/articles")
public class ArticleEndpoint
{

   @Inject
   private ArticleEJB ejb;

   @Inject
   private ProductFamilyMerger productFamilyMerger;

   @Inject
   private AgencyMerger agencyMerger;

   @Inject
   private SalesMarginMerger salesMarginMerger;

   @Inject
   private VATMerger vATMerger;

   @Inject
   private PackagingModeMerger packagingModeMerger;

   @Inject
   private SectionMerger sectionMerger;

   @Inject
   private ClearanceConfigMerger clearanceConfigMerger;

   @POST
   @Consumes({ "application/json", "application/xml" })
   @Produces({ "application/json", "application/xml" })
   public Article create(Article entity)
   {
      return detach(ejb.create(entity));
   }

   @DELETE
   @Path("/{id:[0-9][0-9]*}")
   public Response deleteById(@PathParam("id") Long id)
   {
      Article deleted = ejb.deleteById(id);
      if (deleted == null)
         return Response.status(Status.NOT_FOUND).build();

      return Response.ok(detach(deleted)).build();
   }

   @PUT
   @Path("/{id:[0-9][0-9]*}")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public Article update(Article entity)
   {
      return detach(ejb.update(entity));
   }

   @GET
   @Path("/{id:[0-9][0-9]*}")
   @Produces({ "application/json", "application/xml" })
   public Response findById(@PathParam("id") Long id)
   {
      Article found = ejb.findById(id);
      if (found == null)
         return Response.status(Status.NOT_FOUND).build();
      return Response.ok(detach(found)).build();
   }

   @GET
   @Produces({ "application/json", "application/xml" })
   public ArticleSearchResult listAll(@QueryParam("start") int start,
         @QueryParam("max") int max)
   {
      List<Article> resultList = ejb.listAll(start, max);
      ArticleSearchInput searchInput = new ArticleSearchInput();
      searchInput.setStart(start);
      searchInput.setMax(max);
      return new ArticleSearchResult((long) resultList.size(),
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
   public ArticleSearchResult findBy(ArticleSearchInput searchInput)
   {
      SingularAttribute<Article, ?>[] attributes = readSeachAttributes(searchInput);
      Long count = ejb.countBy(searchInput.getEntity(), attributes);
      List<Article> resultList = ejb.findBy(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new ArticleSearchResult(count, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countBy")
   @Consumes({ "application/json", "application/xml" })
   public Long countBy(ArticleSearchInput searchInput)
   {
      SingularAttribute<Article, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countBy(searchInput.getEntity(), attributes);
   }

   @POST
   @Path("/findByLike")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public ArticleSearchResult findByLike(ArticleSearchInput searchInput)
   {
      SingularAttribute<Article, ?>[] attributes = readSeachAttributes(searchInput);
      Long countLike = ejb.countByLike(searchInput.getEntity(), attributes);
      List<Article> resultList = ejb.findByLike(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new ArticleSearchResult(countLike, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countByLike")
   @Consumes({ "application/json", "application/xml" })
   public Long countByLike(ArticleSearchInput searchInput)
   {
      SingularAttribute<Article, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countByLike(searchInput.getEntity(), attributes);
   }

   @SuppressWarnings("unchecked")
   private SingularAttribute<Article, ?>[] readSeachAttributes(
         ArticleSearchInput searchInput)
   {
      List<String> fieldNames = searchInput.getFieldNames();
      List<SingularAttribute<Article, ?>> result = new ArrayList<SingularAttribute<Article, ?>>();
      for (String fieldName : fieldNames)
      {
         Field[] fields = Article_.class.getFields();
         for (Field field : fields)
         {
            if (field.getName().equals(fieldName))
            {
               try
               {
                  result.add((SingularAttribute<Article, ?>) field.get(null));
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

   private static final List<String> sectionFields = Arrays.asList("sectionCode", "name", "position", "agency.name");

   private static final List<String> familyFields = Arrays.asList("name");

   private static final List<String> defaultSalesMarginFields = Arrays.asList("name", "rate", "active");

   private static final List<String> packagingModeFields = Arrays.asList("name");

   private static final List<String> agencyFields = Arrays.asList("agencyNumber", "name", "active", "name", "name", "phone", "fax");

   private static final List<String> clearanceConfigFields = Arrays.asList("startDate", "endDate", "discountRate", "clearanceState", "active");

   private static final List<String> vatFields = Arrays.asList("name", "rate", "active");

   private Article detach(Article entity)
   {
      if (entity == null)
         return null;

      // aggregated
      entity.setSection(sectionMerger.unbind(entity.getSection(), sectionFields));

      // aggregated
      entity.setFamily(productFamilyMerger.unbind(entity.getFamily(), familyFields));

      // aggregated
      entity.setDefaultSalesMargin(salesMarginMerger.unbind(entity.getDefaultSalesMargin(), defaultSalesMarginFields));

      // aggregated
      entity.setPackagingMode(packagingModeMerger.unbind(entity.getPackagingMode(), packagingModeFields));

      // aggregated
      entity.setAgency(agencyMerger.unbind(entity.getAgency(), agencyFields));

      // aggregated
      entity.setClearanceConfig(clearanceConfigMerger.unbind(entity.getClearanceConfig(), clearanceConfigFields));

      // aggregated
      entity.setVat(vATMerger.unbind(entity.getVat(), vatFields));

      return entity;
   }

   private List<Article> detach(List<Article> list)
   {
      if (list == null)
         return list;
      List<Article> result = new ArrayList<Article>();
      for (Article entity : list)
      {
         result.add(detach(entity));
      }
      return result;
   }

   private ArticleSearchInput detach(ArticleSearchInput searchInput)
   {
      searchInput.setEntity(detach(searchInput.getEntity()));
      return searchInput;
   }
}