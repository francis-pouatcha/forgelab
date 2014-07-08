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

import org.adorsys.adpharma.server.jpa.CustomerInvoiceItem;
import org.adorsys.adpharma.server.jpa.CustomerInvoiceItem_;
import org.adorsys.adpharma.server.jpa.CustomerInvoiceItemSearchInput;
import org.adorsys.adpharma.server.jpa.CustomerInvoiceItemSearchResult;
import org.adorsys.adpharma.server.jpa.SalesStatisticsDataSearchInput;
import org.adorsys.adpharma.server.jpa.SalesStatisticsDataSearchResult;

/**
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/customerinvoiceitems")
public class CustomerInvoiceItemEndpoint
{

	@Inject
	private CustomerInvoiceItemEJB ejb;

	@Inject
	private CustomerInvoiceMerger customerInvoiceMerger;

	@Inject
	private ArticleMerger articleMerger;

	@POST
	@Consumes({ "application/json", "application/xml" })
	@Produces({ "application/json", "application/xml" })
	public CustomerInvoiceItem create(CustomerInvoiceItem entity)
	{
		return detach(ejb.create(entity));
	}

	@POST
	@Path("/findSalesStatistics")
	@Produces({ "application/json", "application/xml" })
	public SalesStatisticsDataSearchResult findSalesStatistics(SalesStatisticsDataSearchInput chartDataSearchInput)
	{
		return ejb.findSalesStatistics(chartDataSearchInput);
	}


	@DELETE
	@Path("/{id:[0-9][0-9]*}")
	public Response deleteById(@PathParam("id") Long id)
	{
		CustomerInvoiceItem deleted = ejb.deleteById(id);
		if (deleted == null)
			return Response.status(Status.NOT_FOUND).build();

		return Response.ok(detach(deleted)).build();
	}

	@PUT
	@Path("/{id:[0-9][0-9]*}")
	@Produces({ "application/json", "application/xml" })
	@Consumes({ "application/json", "application/xml" })
	public CustomerInvoiceItem update(CustomerInvoiceItem entity)
	{
		return detach(ejb.update(entity));
	}

	@GET
	@Path("/{id:[0-9][0-9]*}")
	@Produces({ "application/json", "application/xml" })
	public Response findById(@PathParam("id") Long id)
	{
		CustomerInvoiceItem found = ejb.findById(id);
		if (found == null)
			return Response.status(Status.NOT_FOUND).build();
		return Response.ok(detach(found)).build();
	}

	@GET
	@Produces({ "application/json", "application/xml" })
	public CustomerInvoiceItemSearchResult listAll(@QueryParam("start") int start,
			@QueryParam("max") int max)
	{
		List<CustomerInvoiceItem> resultList = ejb.listAll(start, max);
		CustomerInvoiceItemSearchInput searchInput = new CustomerInvoiceItemSearchInput();
		searchInput.setStart(start);
		searchInput.setMax(max);
		return new CustomerInvoiceItemSearchResult((long) resultList.size(),
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
	public CustomerInvoiceItemSearchResult findBy(CustomerInvoiceItemSearchInput searchInput)
	{
		SingularAttribute<CustomerInvoiceItem, ?>[] attributes = readSeachAttributes(searchInput);
		Long count = ejb.countBy(searchInput.getEntity(), attributes);
		List<CustomerInvoiceItem> resultList = ejb.findBy(searchInput.getEntity(),
				searchInput.getStart(), searchInput.getMax(), attributes);
		return new CustomerInvoiceItemSearchResult(count, detach(resultList),
				detach(searchInput));
	}

	@POST
	@Path("/countBy")
	@Consumes({ "application/json", "application/xml" })
	public Long countBy(CustomerInvoiceItemSearchInput searchInput)
	{
		SingularAttribute<CustomerInvoiceItem, ?>[] attributes = readSeachAttributes(searchInput);
		return ejb.countBy(searchInput.getEntity(), attributes);
	}

	@POST
	@Path("/findByLike")
	@Produces({ "application/json", "application/xml" })
	@Consumes({ "application/json", "application/xml" })
	public CustomerInvoiceItemSearchResult findByLike(CustomerInvoiceItemSearchInput searchInput)
	{
		SingularAttribute<CustomerInvoiceItem, ?>[] attributes = readSeachAttributes(searchInput);
		Long countLike = ejb.countByLike(searchInput.getEntity(), attributes);
		List<CustomerInvoiceItem> resultList = ejb.findByLike(searchInput.getEntity(),
				searchInput.getStart(), searchInput.getMax(), attributes);
		return new CustomerInvoiceItemSearchResult(countLike, detach(resultList),
				detach(searchInput));
	}

	@POST
	@Path("/countByLike")
	@Consumes({ "application/json", "application/xml" })
	public Long countByLike(CustomerInvoiceItemSearchInput searchInput)
	{
		SingularAttribute<CustomerInvoiceItem, ?>[] attributes = readSeachAttributes(searchInput);
		return ejb.countByLike(searchInput.getEntity(), attributes);
	}

	@SuppressWarnings("unchecked")
	private SingularAttribute<CustomerInvoiceItem, ?>[] readSeachAttributes(
			CustomerInvoiceItemSearchInput searchInput)
			{
		List<String> fieldNames = searchInput.getFieldNames();
		List<SingularAttribute<CustomerInvoiceItem, ?>> result = new ArrayList<SingularAttribute<CustomerInvoiceItem, ?>>();
		for (String fieldName : fieldNames)
		{
			Field[] fields = CustomerInvoiceItem_.class.getFields();
			for (Field field : fields)
			{
				if (field.getName().equals(fieldName))
				{
					try
					{
						result.add((SingularAttribute<CustomerInvoiceItem, ?>) field.get(null));
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

	private static final List<String> invoiceFields = Arrays.asList("invoiceType", "invoiceNumber", "creationDate", "customer.fullName", "insurance.customer.fullName", "insurance.insurer.fullName", "creatingUser.fullName", "agency.name", "salesOrder.soNumber", "settled", "amountBeforeTax", "taxAmount", "amountDiscount", "amountAfterTax", "netToPay", "customerRestTopay", "insurranceRestTopay", "cashed", "advancePayment", "totalRestToPay");

	private static final List<String> articleFields = Arrays.asList("articleName", "pic", "manufacturer", "active", "qtyInStock", "sppu", "authorizedSale", "agency.name");

	private CustomerInvoiceItem detach(CustomerInvoiceItem entity)
	{
		if (entity == null)
			return null;

		// composed
		entity.setInvoice(customerInvoiceMerger.unbind(entity.getInvoice(), invoiceFields));

		// aggregated
		entity.setArticle(articleMerger.unbind(entity.getArticle(), articleFields));

		return entity;
	}

	private List<CustomerInvoiceItem> detach(List<CustomerInvoiceItem> list)
	{
		if (list == null)
			return list;
		List<CustomerInvoiceItem> result = new ArrayList<CustomerInvoiceItem>();
		for (CustomerInvoiceItem entity : list)
		{
			result.add(detach(entity));
		}
		return result;
	}

	private CustomerInvoiceItemSearchInput detach(CustomerInvoiceItemSearchInput searchInput)
	{
		searchInput.setEntity(detach(searchInput.getEntity()));
		return searchInput;
	}
}