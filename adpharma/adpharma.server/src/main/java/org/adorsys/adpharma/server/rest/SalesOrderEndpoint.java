package org.adorsys.adpharma.server.rest;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

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

import org.adorsys.adpharma.server.jpa.Agency;
import org.adorsys.adpharma.server.jpa.Customer;
import org.adorsys.adpharma.server.jpa.CustomerInvoice;
import org.adorsys.adpharma.server.jpa.Delivery;
import org.adorsys.adpharma.server.jpa.DocumentProcessingState;
import org.adorsys.adpharma.server.jpa.Login;
import org.adorsys.adpharma.server.jpa.SalesOrder;
import org.adorsys.adpharma.server.jpa.SalesOrderItem;
import org.adorsys.adpharma.server.jpa.SalesOrderSearchInput;
import org.adorsys.adpharma.server.jpa.SalesOrderSearchResult;
import org.adorsys.adpharma.server.jpa.SalesOrderType;
import org.adorsys.adpharma.server.jpa.SalesOrder_;
import org.adorsys.adpharma.server.security.SecurityUtil;

/**
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/salesorders")
public class SalesOrderEndpoint
{

	@Inject
	private SalesOrderEJB ejb;

	@Inject
	private CustomerMerger customerMerger;

	@Inject
	private LoginMerger loginMerger;

	@Inject
	private CashDrawerMerger cashDrawerMerger;

	@Inject
	private VATMerger vATMerger;

	@Inject
	private InsurranceMerger insurranceMerger;

	@Inject
	private SalesOrderItemMerger salesOrderItemMerger;

	@Inject
	private AgencyMerger agencyMerger;


	@Inject
	CustomerEJB customerEJB ;

	@Inject
	SecurityUtil securityUtilEJB;

	@Inject 
	private CustomerInvoiceEJB customerInvoiceEJB;

	@POST
	@Consumes({ "application/json", "application/xml" })
	@Produces({ "application/json", "application/xml" })
	public SalesOrder create(SalesOrder entity)
	{
		List<Customer> listAll = customerEJB.listAll(0, 2) ;
		if(!listAll.isEmpty())
			entity.setCustomer(listAll.iterator().next());
		Login user = securityUtilEJB.getConnectedUser();
		entity.setAgency(user.getAgency());
		entity.setSalesOrderType(SalesOrderType.CASH_SALE);
		entity.setSalesAgent(user);
		entity.setSalesOrderStatus(DocumentProcessingState.ONGOING);
		return detach(ejb.create(entity));
	}

	@DELETE
	@Path("/{id:[0-9][0-9]*}")
	public Response deleteById(@PathParam("id") Long id)
	{
		SalesOrder deleted = ejb.deleteById(id);
		if (deleted == null)
			return Response.status(Status.NOT_FOUND).build();

		return Response.ok(detach(deleted)).build();
	}

	@PUT
	@Path("/{id:[0-9][0-9]*}")
	@Produces({ "application/json", "application/xml" })
	@Consumes({ "application/json", "application/xml" })
	public SalesOrder update(SalesOrder entity)
	{
		return detach(ejb.update(entity));
	}

	@PUT
	@Path("/saveAndClose")
	@Produces({ "application/json", "application/xml" })
	@Consumes({ "application/json", "application/xml" })
	public SalesOrder saveAndClose(SalesOrder salesOrder) {
		if(salesOrder.getSalesOrderStatus()==DocumentProcessingState.CLOSED)
			return salesOrder;
		
		SalesOrder closedOrder = ejb.saveAndClose(salesOrder);

		return detach(closedOrder);
	}

	@PUT
	@Path("/close")
	@Produces({ "application/json", "application/xml" })
	@Consumes({ "application/json", "application/xml" })
	public SalesOrder close(SalesOrder entity)
	{
		if(DocumentProcessingState.CLOSED.equals(entity.getSalesOrderStatus()))
			return entity;
		Login login = securityUtilEJB.getConnectedUser();
		Agency agency = login.getAgency();
		Date creationDate = new Date();

		//	   generate customer invoice
		CustomerInvoice ci = new CustomerInvoice();
		ci.setAgency(agency);
		ci.setCustomer(entity.getCustomer());
		ci.setInsurance(entity.getInsurance());
		ci.setCreatingUser(login);
		ci.setCreationDate(creationDate);
		ci.setSalesOrder(entity);
		CustomerInvoice customerInvoice = customerInvoiceEJB.create(ci);
		Set<SalesOrderItem> salesOrderItems = entity.getSalesOrderItems();
		for (SalesOrderItem salesOrderItem : salesOrderItems) {

			//		generate sale order item

			//		generate customer invoice item

			//		   generate stok mouvements for each sale order item
			//			StockMovement sm = new StockMovement();
			//			sm.setAgency(agency);
			//			sm.setMovementType(StockMovementType.IN);
			//			sm.setArticle(salesOrderItem.getArticle());
			//			sm.setCreatingUser(login);
			//			sm.setCreationDate(creationDate);
			//			sm.setInitialQty(BigDecimal.ZERO);
			//			sm.setMovedQty(salesOrderItem.getDeliveredQty());
			//			sm.setFinalQty(salesOrderItem.getStockQuantity());
			//			sm.setMovementOrigin(StockMovementTerminal.WAREHOUSE);
			//			sm.setMovementDestination(StockMovementTerminal.CUSTOMER);
			//			sm.setOriginatedDocNumber(entity.getSoNumber());
			//			//			sm.setTotalDiscount(entity.get);// TODO remove field
			//			sm.setTotalPurchasingPrice(deliveryItem.getTotalPurchasePrice());
			//			if(deliveryItem.getSalesPricePU()!=null && deliveryItem.getStockQuantity()!=null)
			//				sm.setTotalSalesPrice(deliveryItem.getSalesPricePU().multiply(deliveryItem.getStockQuantity()));
			//			sm = stockMovementEJB.create(sm);
		}


		return detach(ejb.update(entity));
	}

	@GET
	@Path("/{id:[0-9][0-9]*}")
	@Produces({ "application/json", "application/xml" })
	public Response findById(@PathParam("id") Long id)
	{
		SalesOrder found = ejb.findById(id);
		if (found == null)
			return Response.status(Status.NOT_FOUND).build();
		return Response.ok(detach(found)).build();
	}

	@GET
	@Produces({ "application/json", "application/xml" })
	public SalesOrderSearchResult listAll(@QueryParam("start") int start,
			@QueryParam("max") int max)
	{
		List<SalesOrder> resultList = ejb.listAll(start, max);
		SalesOrderSearchInput searchInput = new SalesOrderSearchInput();
		searchInput.setStart(start);
		searchInput.setMax(max);
		return new SalesOrderSearchResult((long) resultList.size(),
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
	public SalesOrderSearchResult findBy(SalesOrderSearchInput searchInput)
	{
		SingularAttribute<SalesOrder, ?>[] attributes = readSeachAttributes(searchInput);
		Long count = ejb.countBy(searchInput.getEntity(), attributes);
		List<SalesOrder> resultList = ejb.findBy(searchInput.getEntity(),
				searchInput.getStart(), searchInput.getMax(), attributes);
		return new SalesOrderSearchResult(count, detach(resultList),
				detach(searchInput));
	}

	@POST
	@Path("/countBy")
	@Consumes({ "application/json", "application/xml" })
	public Long countBy(SalesOrderSearchInput searchInput)
	{
		SingularAttribute<SalesOrder, ?>[] attributes = readSeachAttributes(searchInput);
		return ejb.countBy(searchInput.getEntity(), attributes);
	}

	@POST
	@Path("/findByLike")
	@Produces({ "application/json", "application/xml" })
	@Consumes({ "application/json", "application/xml" })
	public SalesOrderSearchResult findByLike(SalesOrderSearchInput searchInput)
	{
		SingularAttribute<SalesOrder, ?>[] attributes = readSeachAttributes(searchInput);
		Long countLike = ejb.countByLike(searchInput.getEntity(), attributes);
		List<SalesOrder> resultList = ejb.findByLike(searchInput.getEntity(),
				searchInput.getStart(), searchInput.getMax(), attributes);
		return new SalesOrderSearchResult(countLike, detach(resultList),
				detach(searchInput));
	}

	@POST
	@Path("/countByLike")
	@Consumes({ "application/json", "application/xml" })
	public Long countByLike(SalesOrderSearchInput searchInput)
	{
		SingularAttribute<SalesOrder, ?>[] attributes = readSeachAttributes(searchInput);
		return ejb.countByLike(searchInput.getEntity(), attributes);
	}

	@SuppressWarnings("unchecked")
	private SingularAttribute<SalesOrder, ?>[] readSeachAttributes(
			SalesOrderSearchInput searchInput)
			{
		List<String> fieldNames = searchInput.getFieldNames();
		List<SingularAttribute<SalesOrder, ?>> result = new ArrayList<SingularAttribute<SalesOrder, ?>>();
		for (String fieldName : fieldNames)
		{
			Field[] fields = SalesOrder_.class.getFields();
			for (Field field : fields)
			{
				if (field.getName().equals(fieldName))
				{
					try
					{
						result.add((SingularAttribute<SalesOrder, ?>) field.get(null));
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

	private static final List<String> cashDrawerFields = Arrays.asList("cashDrawerNumber", "agency.name", "openingDate", "closingDate", "initialAmount", "totalCashIn", "totalCashOut", "totalCash", "totalCheck", "totalCreditCard", "totalCompanyVoucher", "totalClientVoucher", "opened");

	private static final List<String> customerFields = Arrays.asList("fullName", "birthDate", "landLinePhone", "mobile", "fax", "email", "creditAuthorized", "discountAuthorized");

	private static final List<String> insuranceFields = Arrays.asList("beginDate", "endDate", "customer.fullName", "insurer.fullName", "coverageRate");

	private static final List<String> vatFields = Arrays.asList("name", "rate", "active");

	private static final List<String> salesAgentFields = Arrays.asList("loginName", "email", "gender");

	private static final List<String> agencyFields = Arrays.asList("agencyNumber", "name", "active", "name", "name", "phone", "fax");

	private static final List<String> salesOrderItemsFields = Arrays.asList("orderedQty", "returnedQty", "deliveredQty", "salesPricePU", "totalSalePrice", "internalPic", "article.articleName");

	private SalesOrder detach(SalesOrder entity)
	{
		if (entity == null)
			return null;

		// aggregated
		entity.setCashDrawer(cashDrawerMerger.unbind(entity.getCashDrawer(), cashDrawerFields));

		// aggregated
		entity.setCustomer(customerMerger.unbind(entity.getCustomer(), customerFields));

		// aggregated
		entity.setInsurance(insurranceMerger.unbind(entity.getInsurance(), insuranceFields));

		// aggregated
		entity.setVat(vATMerger.unbind(entity.getVat(), vatFields));

		// aggregated
		entity.setSalesAgent(loginMerger.unbind(entity.getSalesAgent(), salesAgentFields));

		// aggregated
		entity.setAgency(agencyMerger.unbind(entity.getAgency(), agencyFields));

		// composed collections
		entity.setSalesOrderItems(salesOrderItemMerger.unbind(entity.getSalesOrderItems(), salesOrderItemsFields));

		return entity;
	}

	private List<SalesOrder> detach(List<SalesOrder> list)
	{
		if (list == null)
			return list;
		List<SalesOrder> result = new ArrayList<SalesOrder>();
		for (SalesOrder entity : list)
		{
			result.add(detach(entity));
		}
		return result;
	}

	private SalesOrderSearchInput detach(SalesOrderSearchInput searchInput)
	{
		searchInput.setEntity(detach(searchInput.getEntity()));
		return searchInput;
	}
}