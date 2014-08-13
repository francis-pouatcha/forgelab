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

import org.adorsys.adpharma.server.jpa.ArticleLot;
import org.adorsys.adpharma.server.jpa.ArticleLotDetailsManager;
import org.adorsys.adpharma.server.jpa.ArticleLotMovedToTrashData;
import org.adorsys.adpharma.server.jpa.ArticleLotSearchInput;
import org.adorsys.adpharma.server.jpa.ArticleLotSearchResult;
import org.adorsys.adpharma.server.jpa.ArticleLotTransferManager;
import org.adorsys.adpharma.server.jpa.ArticleLot_;
import org.adorsys.adpharma.server.jpa.ArticleSearchInput;
import org.adorsys.adpharma.server.jpa.WareHouseArticleLot;

/**
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/articlelots")
public class ArticleLotEndpoint
{

   @Inject
   private ArticleLotEJB ejb;

   @Inject
   private AgencyMerger agencyMerger;

   @Inject
   private ArticleMerger articleMerger;

   @Inject
   private VATMerger vATMerger;

   @POST
   @Consumes({ "application/json", "application/xml" })
   @Produces({ "application/json", "application/xml" })
   public ArticleLot create(ArticleLot entity)
   {
      return detach(ejb.create(entity));
   }

   @DELETE
   @Path("/{id:[0-9][0-9]*}")
   public Response deleteById(@PathParam("id") Long id)
   {
      ArticleLot deleted = ejb.deleteById(id);
      if (deleted == null)
         return Response.status(Status.NOT_FOUND).build();

      return Response.ok(detach(deleted)).build();
   }
   
   
   @POST
   @Path("/processTransfer")
   @Consumes({ "application/json", "application/xml" })
   @Produces({ "application/json", "application/xml" })
   public ArticleLot processTransfer(ArticleLotTransferManager entity)
   {
      return detach(ejb.processTransFer(entity));
   }

   @PUT
   @Path("/{id:[0-9][0-9]*}")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public ArticleLot update(ArticleLot entity)
   {
      return detach(ejb.update(entity));
   }
   

   @PUT
   @Path("movedToTrash")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public ArticleLot movedToTrash(ArticleLotMovedToTrashData data)
   {
      return detach(ejb.movetoTrash(data));
   }
   
   @PUT
   @Path("/processDetails")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public ArticleLot processDetails(ArticleLotDetailsManager lotDetailsManager)
   {
      return detach(ejb.processDetails(lotDetailsManager));
   }

   @GET
   @Path("/{id:[0-9][0-9]*}")
   @Produces({ "application/json", "application/xml" })
   public Response findById(@PathParam("id") Long id)
   {
      ArticleLot found = ejb.findById(id);
      if (found == null)
         return Response.status(Status.NOT_FOUND).build();
      return Response.ok(detach(found)).build();
   }

   @GET
   @Produces({ "application/json", "application/xml" })
   public ArticleLotSearchResult listAll(@QueryParam("start") int start,
         @QueryParam("max") int max)
   {
      List<ArticleLot> resultList = ejb.listAll(start, max);
      ArticleLotSearchInput searchInput = new ArticleLotSearchInput();
      searchInput.setStart(start);
      searchInput.setMax(max);
      return new ArticleLotSearchResult((long) resultList.size(),
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
   public ArticleLotSearchResult findBy(ArticleLotSearchInput searchInput)
   {
      SingularAttribute<ArticleLot, Object>[] attributes = readSeachAttributes(searchInput);
      Long count = ejb.countBy(searchInput.getEntity(), attributes);
      List<ArticleLot> resultList = ejb.findBy(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new ArticleLotSearchResult(count, detach(resultList),
            detach(searchInput));
   }
   
   
   @POST
   @Path("/findArticleLotByInternalPicWhitRealPrice")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public ArticleLotSearchResult findArticleLotByInternalPicWhitRealPrice(ArticleLotSearchInput searchInput)
   {
      SingularAttribute<ArticleLot, Object>[] attributes = readSeachAttributes(searchInput);
      List<ArticleLot> resultList = ejb.findArticleLotByInternalPicWhitRealPrice(searchInput);
      return new ArticleLotSearchResult(Long.valueOf(1), detach(resultList), detach(searchInput));
   }
   
   @POST
   @Path("/articleLotByArticleOrderByCreationDate")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public ArticleLotSearchResult articleLotByArticleOrderByCreationDate(ArticleLotSearchInput searchInput)
   {
      SingularAttribute<ArticleLot, Object>[] attributes = readSeachAttributes(searchInput);
      List<ArticleLot> resultList = ejb.articleLotByArticleOrderByCreationDate(searchInput.getEntity().getArticle());
      return new ArticleLotSearchResult(Long.valueOf(1), detach(resultList), detach(searchInput));
   }
   
   @POST
   @Path("/stockValue")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public ArticleLotSearchResult stockValue(ArticleSearchInput searchInput)
   {
      List<ArticleLot> resultList = ejb.stockValue(searchInput);
      return new ArticleLotSearchResult(Long.MIN_VALUE, detach(resultList),
            detach(new ArticleLotSearchInput()));
   }

   @POST
   @Path("/countBy")
   @Consumes({ "application/json", "application/xml" })
   public Long countBy(ArticleLotSearchInput searchInput)
   {
      SingularAttribute<ArticleLot, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countBy(searchInput.getEntity(), attributes);
   }

   @POST
   @Path("/findByLike")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public ArticleLotSearchResult findByLike(ArticleLotSearchInput searchInput)
   {
      SingularAttribute<ArticleLot, Object>[] attributes = readSeachAttributes(searchInput);
      Long countLike = ejb.countByLike(searchInput.getEntity(), attributes);
      List<ArticleLot> resultList = ejb.findByLike(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new ArticleLotSearchResult(countLike, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countByLike")
   @Consumes({ "application/json", "application/xml" })
   public Long countByLike(ArticleLotSearchInput searchInput)
   {
      SingularAttribute<ArticleLot, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countByLike(searchInput.getEntity(), attributes);
   }

   @SuppressWarnings("unchecked")
   private SingularAttribute<ArticleLot, Object>[] readSeachAttributes(
         ArticleLotSearchInput searchInput)
   {
      List<String> fieldNames = searchInput.getFieldNames();
      List<SingularAttribute<ArticleLot, ?>> result = new ArrayList<SingularAttribute<ArticleLot, ?>>();
      for (String fieldName : fieldNames)
      {
         Field[] fields = ArticleLot_.class.getFields();
         for (Field field : fields)
         {
            if (field.getName().equals(fieldName))
            {
               try
               {
                  result.add((SingularAttribute<ArticleLot, ?>) field.get(null));
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

   private static final List<String> agencyFields = Arrays.asList("agencyNumber", "name", "active", "name", "name", "phone", "fax");

   private static final List<String> articleFields = Arrays.asList("articleName", "pic", "manufacturer", "active", "qtyInStock", "sppu", "authorizedSale", "agency.name", "clearanceConfig","section","section.wareHouse","vat","vat.rate");

   private static final List<String> vatFields = Arrays.asList("name", "rate", "active");

   private ArticleLot detach(ArticleLot entity)
   {
      if (entity == null)
         return null;

      // aggregated
      entity.setAgency(agencyMerger.unbind(entity.getAgency(), agencyFields));

      // aggregated
      entity.setArticle(articleMerger.unbind(entity.getArticle(), articleFields));

      // aggregated
      entity.setVat(vATMerger.unbind(entity.getVat(), vatFields));

      return entity;
   }

   private List<ArticleLot> detach(List<ArticleLot> list)
   {
      if (list == null)
         return list;
      List<ArticleLot> result = new ArrayList<ArticleLot>();
      for (ArticleLot entity : list)
      {
         result.add(detach(entity));
      }
      return result;
   }

   private ArticleLotSearchInput detach(ArticleLotSearchInput searchInput)
   {
      searchInput.setEntity(detach(searchInput.getEntity()));
      return searchInput;
   }

}