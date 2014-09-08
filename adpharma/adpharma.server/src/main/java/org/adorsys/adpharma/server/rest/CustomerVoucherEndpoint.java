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

import org.adorsys.adpharma.server.jpa.CustomerVoucher;
import org.adorsys.adpharma.server.jpa.CustomerVoucherSearchInput;
import org.adorsys.adpharma.server.jpa.CustomerVoucherSearchResult;
import org.adorsys.adpharma.server.jpa.CustomerVoucher_;
import org.adorsys.adpharma.server.jpa.SalesOrder;

/**
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/customervouchers")
public class CustomerVoucherEndpoint
{

	@Inject
	private CustomerVoucherEJB ejb;

	@Inject
	private CustomerMerger customerMerger;

	@Inject
	private LoginMerger loginMerger;

	@Inject
	private CustomerInvoiceMerger customerInvoiceMerger;

	@Inject
	private AgencyMerger agencyMerger;

	@POST
	@Consumes({ "application/json", "application/xml" })
	@Produces({ "application/json", "application/xml" })
	public CustomerVoucher create(CustomerVoucher entity)
	{
		return detach(ejb.create(entity));
	}

	@DELETE
	@Path("/{id:[0-9][0-9]*}")
	public Response deleteById(@PathParam("id") Long id)
	{
		CustomerVoucher deleted = ejb.deleteById(id);
		if (deleted == null)
			return Response.status(Status.NOT_FOUND).build();

		return Response.ok(detach(deleted)).build();
	}

	@PUT
	@Path("/{id:[0-9][0-9]*}")
	@Produces({ "application/json", "application/xml" })
	@Consumes({ "application/json", "application/xml" })
	public CustomerVoucher update(CustomerVoucher entity)
	{
		return detach(ejb.update(entity));
	}

	@GET
	@Path("/{id:[0-9][0-9]*}")
	@Produces({ "application/json", "application/xml" })
	public Response findById(@PathParam("id") Long id)
	{
		CustomerVoucher found = ejb.findById(id);
		if (found == null)
			return Response.status(Status.NOT_FOUND).build();
		return Response.ok(detach(found)).build();
	}

	@POST
	@Path("/findBySalesOrder")
	@Produces({ "application/json", "application/xml" })
	public CustomerVoucher findBySalesOrder(SalesOrder salesOrder )
	{
		return detach(ejb.findBySalesOrder(salesOrder));
	}

	@POST
	@Path("/findByPaiementId")
	@Produces({ "application/json", "application/xml" })
	public CustomerVoucherSearchResult findByPaiementId(Long PaiementId )
	{
		List<CustomerVoucher> resultList = ejb.findByPaiementId(PaiementId);
		return new CustomerVoucherSearchResult((long) resultList.size(),
				detach(resultList), detach(new CustomerVoucherSearchInput()));
	}


	@GET
	@Produces({ "application/json", "application/xml" })
	public CustomerVoucherSearchResult listAll(@QueryParam("start") int start,
			@QueryParam("max") int max)
	{
		List<CustomerVoucher> resultList = ejb.listAll(start, max);
		CustomerVoucherSearchInput searchInput = new CustomerVoucherSearchInput();
		searchInput.setStart(start);
		searchInput.setMax(max);
		return new CustomerVoucherSearchResult((long) resultList.size(),
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
	public CustomerVoucherSearchResult findBy(CustomerVoucherSearchInput searchInput)
	{
		SingularAttribute<CustomerVoucher, Object>[] attributes = readSeachAttributes(searchInput);
		Long count = ejb.countBy(searchInput.getEntity(), attributes);
		List<CustomerVoucher> resultList = ejb.findBy(searchInput.getEntity(),
				searchInput.getStart(), searchInput.getMax(), attributes);
		return new CustomerVoucherSearchResult(count, detach(resultList),
				detach(searchInput));
	}

	@POST
	@Path("/countBy")
	@Consumes({ "application/json", "application/xml" })
	public Long countBy(CustomerVoucherSearchInput searchInput)
	{
		SingularAttribute<CustomerVoucher, ?>[] attributes = readSeachAttributes(searchInput);
		return ejb.countBy(searchInput.getEntity(), attributes);
	}

	@POST
	@Path("/findByLike")
	@Produces({ "application/json", "application/xml" })
	@Consumes({ "application/json", "application/xml" })
	public CustomerVoucherSearchResult findByLike(CustomerVoucherSearchInput searchInput)
	{
		SingularAttribute<CustomerVoucher, Object>[] attributes = readSeachAttributes(searchInput);
		Long countLike = ejb.countByLike(searchInput.getEntity(), attributes);
		List<CustomerVoucher> resultList = ejb.findByLike(searchInput.getEntity(),
				searchInput.getStart(), searchInput.getMax(), attributes);
		return new CustomerVoucherSearchResult(countLike, detach(resultList),
				detach(searchInput));
	}

	@POST
	@Path("/countByLike")
	@Consumes({ "application/json", "application/xml" })
	public Long countByLike(CustomerVoucherSearchInput searchInput)
	{
		SingularAttribute<CustomerVoucher, ?>[] attributes = readSeachAttributes(searchInput);
		return ejb.countByLike(searchInput.getEntity(), attributes);
	}

	@SuppressWarnings("unchecked")
	private SingularAttribute<CustomerVoucher, Object>[] readSeachAttributes(
			CustomerVoucherSearchInput searchInput)
			{
		List<String> fieldNames = searchInput.getFieldNames();
		List<SingularAttribute<CustomerVoucher, Object>> result = new ArrayList<SingularAttribute<CustomerVoucher, Object>>();
		for (String fieldName : fieldNames)
		{
			Field[] fields = CustomerVoucher_.class.getFields();
			for (Field field : fields)
			{
				if (field.getName().equals(fieldName))
				{
					try
					{
						result.add((SingularAttribute<CustomerVoucher, Object>) field.get(null));
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

	private static final List<String> customerInvoiceFields = Arrays.asList("invoiceType", "invoiceNumber", "creationDate", "customer.fullName", "insurance.customer.fullName", "insurance.insurer.fullName", "creatingUser.fullName", "agency.name", "salesOrder.soNumber", "settled", "amountBeforeTax", "taxAmount", "amountDiscount", "amountAfterTax", "netToPay", "customerRestTopay", "insurranceRestTopay", "cashed", "advancePayment", "totalRestToPay");

	private static final List<String> customerFields = Arrays.asList("fullName", "birthDate", "landLinePhone", "mobile", "fax", "email", "creditAuthorized", "discountAuthorized");

	private static final List<String> agencyFields = Arrays.asList("agencyNumber", "name", "active", "name", "name", "phone", "fax");

	private static final List<String> recordingUserFields = Arrays.asList("loginName", "email", "gender");

	private CustomerVoucher detach(CustomerVoucher entity)
	{
		if (entity == null)
			return null;

		// aggregated
		entity.setCustomerInvoice(customerInvoiceMerger.unbind(entity.getCustomerInvoice(), customerInvoiceFields));

		// aggregated
		entity.setCustomer(customerMerger.unbind(entity.getCustomer(), customerFields));

		// aggregated
		entity.setAgency(agencyMerger.unbind(entity.getAgency(), agencyFields));

		// aggregated
		entity.setRecordingUser(loginMerger.unbind(entity.getRecordingUser(), recordingUserFields));

		return entity;
	}

	private List<CustomerVoucher> detach(List<CustomerVoucher> list)
	{
		if (list == null)
			return list;
		List<CustomerVoucher> result = new ArrayList<CustomerVoucher>();
		for (CustomerVoucher entity : list)
		{
			result.add(detach(entity));
		}
		return result;
	}

	private CustomerVoucherSearchInput detach(CustomerVoucherSearchInput searchInput)
	{
		searchInput.setEntity(detach(searchInput.getEntity()));
		return searchInput;
	}
}