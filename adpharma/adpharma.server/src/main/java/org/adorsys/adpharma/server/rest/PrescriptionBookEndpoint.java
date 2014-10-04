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

import org.adorsys.adpharma.server.jpa.PeriodicalPrescriptionBookDataSearchInput;
import org.adorsys.adpharma.server.jpa.PeriodicalPrescriptionBookSearchResult;
import org.adorsys.adpharma.server.jpa.PrescriptionBook;
import org.adorsys.adpharma.server.jpa.PrescriptionBook_;
import org.adorsys.adpharma.server.jpa.PrescriptionBookSearchInput;
import org.adorsys.adpharma.server.jpa.PrescriptionBookSearchResult;

/**
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/prescriptionbooks")
public class PrescriptionBookEndpoint
{

   @Inject
   private PrescriptionBookEJB ejb;

   @Inject
   private LoginMerger loginMerger;

   @Inject
   private SalesOrderMerger salesOrderMerger;

   @Inject
   private HospitalMerger hospitalMerger;

   @Inject
   private PrescriberMerger prescriberMerger;

   @Inject
   private AgencyMerger agencyMerger;

   @POST
   @Consumes({ "application/json", "application/xml" })
   @Produces({ "application/json", "application/xml" })
   public PrescriptionBook create(PrescriptionBook entity)
   {
      return detach(ejb.create(entity));
   }

   @DELETE
   @Path("/{id:[0-9][0-9]*}")
   public Response deleteById(@PathParam("id") Long id)
   {
      PrescriptionBook deleted = ejb.deleteById(id);
      if (deleted == null)
         return Response.status(Status.NOT_FOUND).build();

      return Response.ok(detach(deleted)).build();
   }

   @PUT
   @Path("/{id:[0-9][0-9]*}")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public PrescriptionBook update(PrescriptionBook entity)
   {
      return detach(ejb.update(entity));
   }

   @GET
   @Path("/{id:[0-9][0-9]*}")
   @Produces({ "application/json", "application/xml" })
   public Response findById(@PathParam("id") Long id)
   {
      PrescriptionBook found = ejb.findById(id);
      if (found == null)
         return Response.status(Status.NOT_FOUND).build();
      return Response.ok(detach(found)).build();
   }

   @GET
   @Produces({ "application/json", "application/xml" })
   public PrescriptionBookSearchResult listAll(@QueryParam("start") int start,
         @QueryParam("max") int max)
   {
      List<PrescriptionBook> resultList = ejb.listAll(start, max);
      PrescriptionBookSearchInput searchInput = new PrescriptionBookSearchInput();
      searchInput.setStart(start);
      searchInput.setMax(max);
      return new PrescriptionBookSearchResult((long) resultList.size(),
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
   public PrescriptionBookSearchResult findBy(PrescriptionBookSearchInput searchInput)
   {
      SingularAttribute<PrescriptionBook, ?>[] attributes = readSeachAttributes(searchInput);
      Long count = ejb.countBy(searchInput.getEntity(), attributes);
      List<PrescriptionBook> resultList = ejb.findBy(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new PrescriptionBookSearchResult(count, detach(resultList),
            detach(searchInput));
   }
   
   @POST
   @Path("/periodicalPrescriptionBook")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public PrescriptionBookSearchResult periodicalPrescriptionBook(PeriodicalPrescriptionBookDataSearchInput searchInput)
   {
	   List<PrescriptionBook> resultList = ejb.periodicalPrescriptionBook(searchInput);
	   return new PrescriptionBookSearchResult(Long.valueOf(resultList.size()) , detach(resultList), detach(new PrescriptionBookSearchInput())); 
	   
   }
   
   

   @POST
   @Path("/countBy")
   @Consumes({ "application/json", "application/xml" })
   public Long countBy(PrescriptionBookSearchInput searchInput)
   {
      SingularAttribute<PrescriptionBook, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countBy(searchInput.getEntity(), attributes);
   }

   @POST
   @Path("/findByLike")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public PrescriptionBookSearchResult findByLike(PrescriptionBookSearchInput searchInput)
   {
      SingularAttribute<PrescriptionBook, ?>[] attributes = readSeachAttributes(searchInput);
      Long countLike = ejb.countByLike(searchInput.getEntity(), attributes);
      List<PrescriptionBook> resultList = ejb.findByLike(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new PrescriptionBookSearchResult(countLike, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countByLike")
   @Consumes({ "application/json", "application/xml" })
   public Long countByLike(PrescriptionBookSearchInput searchInput)
   {
      SingularAttribute<PrescriptionBook, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countByLike(searchInput.getEntity(), attributes);
   }

   @SuppressWarnings("unchecked")
   private SingularAttribute<PrescriptionBook, ?>[] readSeachAttributes(
         PrescriptionBookSearchInput searchInput)
   {
      List<String> fieldNames = searchInput.getFieldNames();
      List<SingularAttribute<PrescriptionBook, ?>> result = new ArrayList<SingularAttribute<PrescriptionBook, ?>>();
      for (String fieldName : fieldNames)
      {
         Field[] fields = PrescriptionBook_.class.getFields();
         for (Field field : fields)
         {
            if (field.getName().equals(fieldName))
            {
               try
               {
                  result.add((SingularAttribute<PrescriptionBook, ?>) field.get(null));
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

   private static final List<String> prescriberFields = Arrays.asList("name", "phone", "street", "city");

   private static final List<String> hospitalFields = Arrays.asList("name", "phone", "street", "city");

   private static final List<String> agencyFields = Arrays.asList("agencyNumber", "name", "active", "name", "name", "phone", "fax");

   private static final List<String> recordingAgentFields = Arrays.asList("loginName", "email", "gender");

   private static final List<String> salesOrderFields = Arrays.asList("cashDrawer.cashDrawerNumber", "soNumber", "customer.fullName", "insurance.customer.fullName", "insurance.insurer.fullName", "vat.rate", "salesAgent.fullName", "agency.name", "salesOrderStatus", "cashed", "amountBeforeTax", "amountVAT", "amountDiscount", "totalReturnAmount", "amountAfterTax", "salesOrderType");

   private PrescriptionBook detach(PrescriptionBook entity)
   {
      if (entity == null)
         return null;

      // aggregated
      entity.setPrescriber(prescriberMerger.unbind(entity.getPrescriber(), prescriberFields));

      // aggregated
      entity.setHospital(hospitalMerger.unbind(entity.getHospital(), hospitalFields));

      // aggregated
      entity.setAgency(agencyMerger.unbind(entity.getAgency(), agencyFields));

      // aggregated
      entity.setRecordingAgent(loginMerger.unbind(entity.getRecordingAgent(), recordingAgentFields));

      // aggregated
      entity.setSalesOrder(salesOrderMerger.unbind(entity.getSalesOrder(), salesOrderFields));

      return entity;
   }

   private List<PrescriptionBook> detach(List<PrescriptionBook> list)
   {
      if (list == null)
         return list;
      List<PrescriptionBook> result = new ArrayList<PrescriptionBook>();
      for (PrescriptionBook entity : list)
      {
         result.add(detach(entity));
      }
      return result;
   }

   private PrescriptionBookSearchInput detach(PrescriptionBookSearchInput searchInput)
   {
      searchInput.setEntity(detach(searchInput.getEntity()));
      return searchInput;
   }
}