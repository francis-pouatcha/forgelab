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
import org.adorsys.adpharma.server.jpa.Disbursement;
import org.adorsys.adpharma.server.jpa.Disbursement_;
import org.adorsys.adpharma.server.jpa.DisbursementSearchInput;
import org.adorsys.adpharma.server.jpa.DisbursementSearchResult;

/**
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/disbursements")
public class DisbursementEndpoint
{

   @Inject
   private DisbursementEJB ejb;

   @Inject
   private LoginMerger loginMerger;

   @Inject
   private CashDrawerMerger cashDrawerMerger;

   @Inject
   private AgencyMerger agencyMerger;

   @POST
   @Consumes({ "application/json", "application/xml" })
   @Produces({ "application/json", "application/xml" })
   public Disbursement create(Disbursement entity)
   {
      return detach(ejb.create(entity));
   }

   @DELETE
   @Path("/{id:[0-9][0-9]*}")
   public Response deleteById(@PathParam("id") Long id)
   {
      Disbursement deleted = ejb.deleteById(id);
      if (deleted == null)
         return Response.status(Status.NOT_FOUND).build();

      return Response.ok(detach(deleted)).build();
   }

   @PUT
   @Path("/{id:[0-9][0-9]*}")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public Disbursement update(Disbursement entity)
   {
      return detach(ejb.update(entity));
   }

   @GET
   @Path("/{id:[0-9][0-9]*}")
   @Produces({ "application/json", "application/xml" })
   public Response findById(@PathParam("id") Long id)
   {
      Disbursement found = ejb.findById(id);
      if (found == null)
         return Response.status(Status.NOT_FOUND).build();
      return Response.ok(detach(found)).build();
   }

   @GET
   @Produces({ "application/json", "application/xml" })
   public DisbursementSearchResult listAll(@QueryParam("start") int start,
         @QueryParam("max") int max)
   {
      List<Disbursement> resultList = ejb.listAll(start, max);
      DisbursementSearchInput searchInput = new DisbursementSearchInput();
      searchInput.setStart(start);
      searchInput.setMax(max);
      return new DisbursementSearchResult((long) resultList.size(),
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
   public DisbursementSearchResult findBy(DisbursementSearchInput searchInput)
   {
      SingularAttribute<Disbursement, ?>[] attributes = readSeachAttributes(searchInput);
      Long count = ejb.countBy(searchInput.getEntity(), attributes);
      List<Disbursement> resultList = ejb.findBy(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new DisbursementSearchResult(count, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countBy")
   @Consumes({ "application/json", "application/xml" })
   public Long countBy(DisbursementSearchInput searchInput)
   {
      SingularAttribute<Disbursement, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countBy(searchInput.getEntity(), attributes);
   }

   @POST
   @Path("/findByLike")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public DisbursementSearchResult findByLike(DisbursementSearchInput searchInput)
   {
      SingularAttribute<Disbursement, ?>[] attributes = readSeachAttributes(searchInput);
      Long countLike = ejb.countByLike(searchInput.getEntity(), attributes);
      List<Disbursement> resultList = ejb.findByLike(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new DisbursementSearchResult(countLike, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countByLike")
   @Consumes({ "application/json", "application/xml" })
   public Long countByLike(DisbursementSearchInput searchInput)
   {
      SingularAttribute<Disbursement, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countByLike(searchInput.getEntity(), attributes);
   }

   @SuppressWarnings("unchecked")
   private SingularAttribute<Disbursement, ?>[] readSeachAttributes(
         DisbursementSearchInput searchInput)
   {
      List<String> fieldNames = searchInput.getFieldNames();
      List<SingularAttribute<Disbursement, ?>> result = new ArrayList<SingularAttribute<Disbursement, ?>>();
      for (String fieldName : fieldNames)
      {
         Field[] fields = Disbursement_.class.getFields();
         for (Field field : fields)
         {
            if (field.getName().equals(fieldName))
            {
               try
               {
                  result.add((SingularAttribute<Disbursement, ?>) field.get(null));
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

   private static final List<String> cashierFields = Arrays.asList("loginName", "email", "gender");

   private static final List<String> cashDrawerFields = Arrays.asList("cashDrawerNumber", "agency.name", "openingDate", "closingDate", "initialAmount", "totalCashIn", "totalCashOut", "totalCash", "totalCheck", "totalCreditCard", "totalCompanyVoucher", "totalClientVoucher", "opened");

   private static final List<String> agencyFields = Arrays.asList("agencyNumber", "name", "active", "name", "name", "phone", "fax");

   private Disbursement detach(Disbursement entity)
   {
      if (entity == null)
         return null;

      // aggregated
      entity.setCashier(loginMerger.unbind(entity.getCashier(), cashierFields));

      // aggregated
      entity.setCashDrawer(cashDrawerMerger.unbind(entity.getCashDrawer(), cashDrawerFields));

      // aggregated
      entity.setAgency(agencyMerger.unbind(entity.getAgency(), agencyFields));

      return entity;
   }

   private List<Disbursement> detach(List<Disbursement> list)
   {
      if (list == null)
         return list;
      List<Disbursement> result = new ArrayList<Disbursement>();
      for (Disbursement entity : list)
      {
         result.add(detach(entity));
      }
      return result;
   }

   private DisbursementSearchInput detach(DisbursementSearchInput searchInput)
   {
      searchInput.setEntity(detach(searchInput.getEntity()));
      return searchInput;
   }
}