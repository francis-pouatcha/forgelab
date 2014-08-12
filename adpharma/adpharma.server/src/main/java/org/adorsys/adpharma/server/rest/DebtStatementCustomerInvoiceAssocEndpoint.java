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

import org.adorsys.adpharma.server.jpa.CustomerInvoice;
import org.adorsys.adpharma.server.jpa.CustomerInvoiceSearchInput;
import org.adorsys.adpharma.server.jpa.CustomerInvoiceSearchResult;
import org.adorsys.adpharma.server.jpa.DebtStatement;
import org.adorsys.adpharma.server.jpa.DebtStatementCustomerInvoiceAssoc;
import org.adorsys.adpharma.server.jpa.DebtStatementCustomerInvoiceAssoc_;
import org.adorsys.adpharma.server.jpa.DebtStatementCustomerInvoiceAssocSearchInput;
import org.adorsys.adpharma.server.jpa.DebtStatementCustomerInvoiceAssocSearchResult;

/**
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/debtstatementcustomerinvoiceassocs")
public class DebtStatementCustomerInvoiceAssocEndpoint
{

   @Inject
   private DebtStatementCustomerInvoiceAssocEJB ejb;

   @Inject
   private CustomerInvoiceMerger customerInvoiceMerger;

   @Inject
   private DebtStatementMerger debtStatementMerger;

   @POST
   @Consumes({ "application/json", "application/xml" })
   @Produces({ "application/json", "application/xml" })
   public DebtStatementCustomerInvoiceAssoc create(DebtStatementCustomerInvoiceAssoc entity)
   {
      return detach(ejb.create(entity));
   }

   @DELETE
   @Path("/{id:[0-9][0-9]*}")
   public Response deleteById(@PathParam("id") Long id)
   {
      DebtStatementCustomerInvoiceAssoc deleted = ejb.deleteById(id);
      if (deleted == null)
         return Response.status(Status.NOT_FOUND).build();

      return Response.ok(detach(deleted)).build();
   }

   @PUT
   @Path("/{id:[0-9][0-9]*}")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public DebtStatementCustomerInvoiceAssoc update(DebtStatementCustomerInvoiceAssoc entity)
   {
      return detach(ejb.update(entity));
   }

   @GET
   @Path("/{id:[0-9][0-9]*}")
   @Produces({ "application/json", "application/xml" })
   public Response findById(@PathParam("id") Long id)
   {
      DebtStatementCustomerInvoiceAssoc found = ejb.findById(id);
      if (found == null)
         return Response.status(Status.NOT_FOUND).build();
      return Response.ok(detach(found)).build();
   }

   @GET
   @Produces({ "application/json", "application/xml" })
   public DebtStatementCustomerInvoiceAssocSearchResult listAll(@QueryParam("start") int start,
         @QueryParam("max") int max)
   {
      List<DebtStatementCustomerInvoiceAssoc> resultList = ejb.listAll(start, max);
      DebtStatementCustomerInvoiceAssocSearchInput searchInput = new DebtStatementCustomerInvoiceAssocSearchInput();
      searchInput.setStart(start);
      searchInput.setMax(max);
      return new DebtStatementCustomerInvoiceAssocSearchResult((long) resultList.size(),
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
   public DebtStatementCustomerInvoiceAssocSearchResult findBy(DebtStatementCustomerInvoiceAssocSearchInput searchInput)
   {
      SingularAttribute<DebtStatementCustomerInvoiceAssoc, ?>[] attributes = readSeachAttributes(searchInput);
      Long count = ejb.countBy(searchInput.getEntity(), attributes);
      List<DebtStatementCustomerInvoiceAssoc> resultList = ejb.findBy(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new DebtStatementCustomerInvoiceAssocSearchResult(count, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countBy")
   @Consumes({ "application/json", "application/xml" })
   public Long countBy(DebtStatementCustomerInvoiceAssocSearchInput searchInput)
   {
      SingularAttribute<DebtStatementCustomerInvoiceAssoc, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countBy(searchInput.getEntity(), attributes);
   }

   @POST
   @Path("/findByLike")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public DebtStatementCustomerInvoiceAssocSearchResult findByLike(DebtStatementCustomerInvoiceAssocSearchInput searchInput)
   {
      SingularAttribute<DebtStatementCustomerInvoiceAssoc, ?>[] attributes = readSeachAttributes(searchInput);
      Long countLike = ejb.countByLike(searchInput.getEntity(), attributes);
      List<DebtStatementCustomerInvoiceAssoc> resultList = ejb.findByLike(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      List<DebtStatementCustomerInvoiceAssoc> detach = detach(resultList);
      return new DebtStatementCustomerInvoiceAssocSearchResult(countLike,detach ,
            detach(searchInput));
   }

   @POST
   @Path("/countByLike")
   @Consumes({ "application/json", "application/xml" })
   public Long countByLike(DebtStatementCustomerInvoiceAssocSearchInput searchInput)
   {
      SingularAttribute<DebtStatementCustomerInvoiceAssoc, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countByLike(searchInput.getEntity(), attributes);
   }

   @SuppressWarnings("unchecked")
   private SingularAttribute<DebtStatementCustomerInvoiceAssoc, ?>[] readSeachAttributes(
         DebtStatementCustomerInvoiceAssocSearchInput searchInput)
   {
      List<String> fieldNames = searchInput.getFieldNames();
      List<SingularAttribute<DebtStatementCustomerInvoiceAssoc, ?>> result = new ArrayList<SingularAttribute<DebtStatementCustomerInvoiceAssoc, ?>>();
      for (String fieldName : fieldNames)
      {
         Field[] fields = DebtStatementCustomerInvoiceAssoc_.class.getFields();
         for (Field field : fields)
         {
            if (field.getName().equals(fieldName))
            {
               try
               {
                  result.add((SingularAttribute<DebtStatementCustomerInvoiceAssoc, ?>) field.get(null));
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

   private static final List<String> s_invoices_t_source_sourceFields = Arrays.asList("statementNumber", "insurrance.fullName", "agency.name", "paymentDate", "initialAmount", "advancePayment", "restAmount", "settled", "amountFromVouchers", "canceled", "useVoucher");

   private static final List<String> s_invoices_t_source_targetFields = Arrays.asList("invoiceType","patientMatricle", "invoiceNumber", "creationDate", "customer.fullName", "customer.serialNumber", "insurance.beginDate", "insurance.endDate", "insurance.insurer.fullName","insurance.customer.serialNumber", "insurance.customer.fullName", "creatingUser.fullName","creatingUser.loginName", "agency", "salesOrder", "settled", "amountBeforeTax", "taxAmount", "amountDiscount", "amountAfterTax", "netToPay", "customerRestTopay", "insurranceRestTopay", "cashed", "advancePayment", "totalRestToPay");

   private DebtStatementCustomerInvoiceAssoc detach(DebtStatementCustomerInvoiceAssoc entity)
   {
      if (entity == null)
         return null;

      // aggregated
      entity.setSource(debtStatementMerger.unbind(entity.getSource(), s_invoices_t_source_sourceFields));

      // aggregated
      entity.setTarget(customerInvoiceMerger.unbind(entity.getTarget(), s_invoices_t_source_targetFields));

      return entity;
   }

   private List<DebtStatementCustomerInvoiceAssoc> detach(List<DebtStatementCustomerInvoiceAssoc> list)
   {
      if (list == null)
         return list;
      List<DebtStatementCustomerInvoiceAssoc> result = new ArrayList<DebtStatementCustomerInvoiceAssoc>();
      for (DebtStatementCustomerInvoiceAssoc entity : list)
      {
         result.add(detach(entity));
      }
      return result;
   }

   private DebtStatementCustomerInvoiceAssocSearchInput detach(DebtStatementCustomerInvoiceAssocSearchInput searchInput)
   {
      searchInput.setEntity(detach(searchInput.getEntity()));
      return searchInput;
   }
}