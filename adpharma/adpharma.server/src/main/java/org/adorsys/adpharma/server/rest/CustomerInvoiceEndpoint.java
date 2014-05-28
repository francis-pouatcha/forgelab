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
import org.adorsys.adpharma.server.jpa.CustomerInvoice_;
import org.adorsys.adpharma.server.jpa.CustomerInvoiceSearchInput;
import org.adorsys.adpharma.server.jpa.CustomerInvoiceSearchResult;
import org.adorsys.adpharma.server.jpa.InvoiceByAgencyPrintInput;
import org.adorsys.adpharma.server.jpa.SalesStatisticsDataSearchInput;
import org.adorsys.adpharma.server.jpa.SalesStatisticsDataSearchResult;

/**
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/customerinvoices")
public class CustomerInvoiceEndpoint
{

	@Inject
	private CustomerInvoiceEJB ejb;

	@Inject
	private CustomerMerger customerMerger;

	@Inject
	private LoginMerger loginMerger;

	@Inject
	private CustomerInvoiceItemMerger customerInvoiceItemMerger;

	@Inject
	private SalesOrderMerger salesOrderMerger;

	@Inject
	private InsurranceMerger insurranceMerger;

	@Inject
	private PaymentCustomerInvoiceAssocMerger paymentCustomerInvoiceAssocMerger;

	@Inject
	private AgencyMerger agencyMerger;

	@POST
	@Consumes({ "application/json", "application/xml" })
	@Produces({ "application/json", "application/xml" })
	public CustomerInvoice create(CustomerInvoice entity)
	{
		return detach(ejb.create(entity));
	}

	@DELETE
	@Path("/{id:[0-9][0-9]*}")
	public Response deleteById(@PathParam("id") Long id)
	{
		CustomerInvoice deleted = ejb.deleteById(id);
		if (deleted == null)
			return Response.status(Status.NOT_FOUND).build();

		return Response.ok(detach(deleted)).build();
	}

	@PUT
	@Path("/{id:[0-9][0-9]*}")
	@Produces({ "application/json", "application/xml" })
	@Consumes({ "application/json", "application/xml" })
	public CustomerInvoice update(CustomerInvoice entity)
	{
		return detach(ejb.update(entity));
	}

	@GET
	@Path("/{id:[0-9][0-9]*}")
	@Produces({ "application/json", "application/xml" })
	public Response findById(@PathParam("id") Long id)
	{
		CustomerInvoice found = ejb.findById(id);
		if (found == null)
			return Response.status(Status.NOT_FOUND).build();
		return Response.ok(detach(found)).build();
	}

	@POST
	@Path("/findSalesStatistics")
	@Produces({ "application/json", "application/xml" })
	public SalesStatisticsDataSearchResult findSalesStatistics(SalesStatisticsDataSearchInput chartDataSearchInput)
	{
		return ejb.findSalesStatistics(chartDataSearchInput);
	}




	@GET
	@Produces({ "application/json", "application/xml" })
	public CustomerInvoiceSearchResult listAll(@QueryParam("start") int start,
			@QueryParam("max") int max)
	{
		List<CustomerInvoice> resultList = ejb.listAll(start, max);
		CustomerInvoiceSearchInput searchInput = new CustomerInvoiceSearchInput();
		searchInput.setStart(start);
		searchInput.setMax(max);
		return new CustomerInvoiceSearchResult((long) resultList.size(),
				detach(resultList), detach(searchInput));
	}
	
	@POST
	@Path("/findByAgencyAndDateBetween")
	@Produces({ "application/json", "application/xml" })
	public CustomerInvoiceSearchResult findByAgencyAndDateBetween(InvoiceByAgencyPrintInput searchInput)
	{
		List<CustomerInvoice> resultList = ejb.findByAgencyAndDateBetween(searchInput);
		CustomerInvoiceSearchInput input = new CustomerInvoiceSearchInput();
		return new CustomerInvoiceSearchResult((long) resultList.size(),
				detach(resultList), detach(input));
	}
	
	@POST
	@Path("/customerInvicePerDayAndPerAgency")
	@Produces({ "application/json", "application/xml" })
	public CustomerInvoiceSearchResult customerInvicePerDayAndPerAgency(InvoiceByAgencyPrintInput searchInput)
	{
		List<CustomerInvoice> resultList = ejb.customerInvicePerDayAndPerAgency(searchInput);
		CustomerInvoiceSearchInput input = new CustomerInvoiceSearchInput();
		return new CustomerInvoiceSearchResult((long) resultList.size(),
				detach(resultList), detach(input));
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
	public CustomerInvoiceSearchResult findBy(CustomerInvoiceSearchInput searchInput)
	{
		SingularAttribute<CustomerInvoice, Object>[] attributes = readSeachAttributes(searchInput);
		Long count = ejb.countBy(searchInput.getEntity(), attributes);
		List<CustomerInvoice> resultList = ejb.findBy(searchInput.getEntity(),
				searchInput.getStart(), searchInput.getMax(), attributes);
		return new CustomerInvoiceSearchResult(count, detach(resultList),
				detach(searchInput));
	}

	@POST
	@Path("/countBy")
	@Consumes({ "application/json", "application/xml" })
	public Long countBy(CustomerInvoiceSearchInput searchInput)
	{
		SingularAttribute<CustomerInvoice, ?>[] attributes = readSeachAttributes(searchInput);
		return ejb.countBy(searchInput.getEntity(), attributes);
	}

	@POST
	@Path("/findByLike")
	@Produces({ "application/json", "application/xml" })
	@Consumes({ "application/json", "application/xml" })
	public CustomerInvoiceSearchResult findByLike(CustomerInvoiceSearchInput searchInput)
	{
		SingularAttribute<CustomerInvoice, Object>[] attributes = readSeachAttributes(searchInput);
		Long countLike = ejb.countByLike(searchInput.getEntity(), attributes);
		List<CustomerInvoice> resultList = ejb.findByLike(searchInput.getEntity(),
				searchInput.getStart(), searchInput.getMax(), attributes);
		return new CustomerInvoiceSearchResult(countLike, detach(resultList),
				detach(searchInput));
	}

	@POST
	@Path("/countByLike")
	@Consumes({ "application/json", "application/xml" })
	public Long countByLike(CustomerInvoiceSearchInput searchInput)
	{
		SingularAttribute<CustomerInvoice, ?>[] attributes = readSeachAttributes(searchInput);
		return ejb.countByLike(searchInput.getEntity(), attributes);
	}

	@SuppressWarnings("unchecked")
	private SingularAttribute<CustomerInvoice, Object>[] readSeachAttributes(
			CustomerInvoiceSearchInput searchInput)
			{
		List<String> fieldNames = searchInput.getFieldNames();
		List<SingularAttribute<CustomerInvoice, ?>> result = new ArrayList<SingularAttribute<CustomerInvoice, ?>>();
		for (String fieldName : fieldNames)
		{
			Field[] fields = CustomerInvoice_.class.getFields();
			for (Field field : fields)
			{
				if (field.getName().equals(fieldName))
				{
					try
					{
						result.add((SingularAttribute<CustomerInvoice, ?>) field.get(null));
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

	private static final List<String> customerFields = Arrays.asList("fullName", "birthDate", "landLinePhone", "mobile", "fax", "email", "creditAuthorized", "discountAuthorized");

	private static final List<String> insuranceFields = Arrays.asList("beginDate", "endDate", "customer.fullName", "insurer.fullName", "coverageRate");

	private static final List<String> creatingUserFields = Arrays.asList("loginName", "email", "gender");

	private static final List<String> agencyFields = Arrays.asList("agencyNumber", "name", "active", "name", "name", "phone", "fax");

	private static final List<String> salesOrderFields = Arrays.asList("cashDrawer.cashDrawerNumber", "soNumber", "customer.fullName", "insurance.customer.fullName", "insurance.insurer.fullName", "vat.rate", "salesAgent.fullName", "agency.name", "salesOrderStatus", "cashed", "amountBeforeTax", "amountVAT", "amountDiscount", "totalReturnAmount", "amountAfterTax", "salesOrderType");

	private static final List<String> invoiceItemsFields = Arrays.asList("internalPic", "article.articleName", "purchasedQty", "salesPricePU", "totalSalesPrice");

	private static final List<String> paymentsFields = emptyList;

	private CustomerInvoice detach(CustomerInvoice entity)
	{
		if (entity == null)
			return null;

		// aggregated
		entity.setCustomer(customerMerger.unbind(entity.getCustomer(), customerFields));

		// aggregated
		entity.setInsurance(insurranceMerger.unbind(entity.getInsurance(), insuranceFields));

		// aggregated
		entity.setCreatingUser(loginMerger.unbind(entity.getCreatingUser(), creatingUserFields));

		// aggregated
		entity.setAgency(agencyMerger.unbind(entity.getAgency(), agencyFields));

		// aggregated
		entity.setSalesOrder(salesOrderMerger.unbind(entity.getSalesOrder(), salesOrderFields));

		// composed collections
		entity.setInvoiceItems(customerInvoiceItemMerger.unbind(entity.getInvoiceItems(), invoiceItemsFields));

		// aggregated collection
		entity.setPayments(paymentCustomerInvoiceAssocMerger.unbind(entity.getPayments(), paymentsFields));

		return entity;
	}

	private List<CustomerInvoice> detach(List<CustomerInvoice> list)
	{
		if (list == null)
			return list;
		List<CustomerInvoice> result = new ArrayList<CustomerInvoice>();
		for (CustomerInvoice entity : list)
		{
			result.add(detach(entity));
		}
		return result;
	}

	private CustomerInvoiceSearchInput detach(CustomerInvoiceSearchInput searchInput)
	{
		searchInput.setEntity(detach(searchInput.getEntity()));
		return searchInput;
	}
}