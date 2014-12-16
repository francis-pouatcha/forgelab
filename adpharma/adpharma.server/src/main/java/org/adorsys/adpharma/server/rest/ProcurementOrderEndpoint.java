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

import org.adorsys.adpharma.server.jpa.ProcurementOrder;
import org.adorsys.adpharma.server.jpa.ProcurementOrderAdvancedSearchData;
import org.adorsys.adpharma.server.jpa.ProcurementOrderPreparationData;
import org.adorsys.adpharma.server.jpa.ProcurementOrder_;
import org.adorsys.adpharma.server.jpa.ProcurementOrderSearchInput;
import org.adorsys.adpharma.server.jpa.ProcurementOrderSearchResult;

/**
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/procurementorders")
public class ProcurementOrderEndpoint
{

	@Inject
	private ProcurementOrderEJB ejb;

	@Inject
	private LoginMerger loginMerger;

	@Inject
	private ProcurementOrderItemMerger procurementOrderItemMerger;

	@Inject
	private SupplierMerger supplierMerger;

	@Inject
	private VATMerger vATMerger;

	@Inject
	private AgencyMerger agencyMerger;

	@POST
	@Consumes({ "application/json", "application/xml" })
	@Produces({ "application/json", "application/xml" })
	public ProcurementOrder create(ProcurementOrder entity)
	{
		return detach(ejb.create(entity));
	}

	@POST
	@Path("/proccessPreparation")
	@Consumes({ "application/json", "application/xml" })
	@Produces({ "application/json", "application/xml" })
	public ProcurementOrder proccessPreparation(ProcurementOrderPreparationData entity)
	{
		return detach(ejb.proccessPreparation(entity));
	}
	
	@POST
	@Path("/sendOrderToPhmlServer")
	@Consumes({ "application/json", "application/xml" })
	@Produces({ "application/json", "application/xml" })
	public ProcurementOrder sendOrderToPhmlServer(ProcurementOrder entity)
	{
		return detach(ejb.sendOrderToPhmlServer(entity));
	}
	
	@POST
	@Path("/retrievedPreparationOrder")
	@Consumes({ "application/json", "application/xml" })
	@Produces({ "application/json", "application/xml" })
	public ProcurementOrder retrievedPreparationOrder(ProcurementOrder entity)
	{
		return detach(ejb.retrievedPreparationOrder(entity));
	}

	@DELETE
	@Path("/{id:[0-9][0-9]*}")
	public Response deleteById(@PathParam("id") Long id)
	{
		ProcurementOrder deleted = ejb.deleteById(id);
		if (deleted == null)
			return Response.status(Status.NOT_FOUND).build();

		return Response.ok(detach(deleted)).build();
	}

	@PUT
	@Path("/{id:[0-9][0-9]*}")
	@Produces({ "application/json", "application/xml" })
	@Consumes({ "application/json", "application/xml" })
	public ProcurementOrder update(ProcurementOrder entity)
	{
		return detach(ejb.update(entity));
	}

	@GET
	@Path("/{id:[0-9][0-9]*}")
	@Produces({ "application/json", "application/xml" })
	public Response findById(@PathParam("id") Long id)
	{
		ProcurementOrder found = ejb.findById(id);
		if (found == null)
			return Response.status(Status.NOT_FOUND).build();
		return Response.ok(detach(found)).build();
	}

	@GET
	@Produces({ "application/json", "application/xml" })
	public ProcurementOrderSearchResult listAll(@QueryParam("start") int start,
			@QueryParam("max") int max)
	{
		List<ProcurementOrder> resultList = ejb.listAll(start, max);
		ProcurementOrderSearchInput searchInput = new ProcurementOrderSearchInput();
		searchInput.setStart(start);
		searchInput.setMax(max);
		return new ProcurementOrderSearchResult((long) resultList.size(),
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
	public ProcurementOrderSearchResult findBy(ProcurementOrderSearchInput searchInput)
	{
		SingularAttribute<ProcurementOrder, Object>[] attributes = readSeachAttributes(searchInput);
		Long count = ejb.countBy(searchInput.getEntity(), attributes);
		List<ProcurementOrder> resultList = ejb.findBy(searchInput.getEntity(),
				searchInput.getStart(), searchInput.getMax(), attributes);
		return new ProcurementOrderSearchResult(count, detach(resultList),
				detach(searchInput));
	}

	@POST
	@Path("/countBy")
	@Consumes({ "application/json", "application/xml" })
	public Long countBy(ProcurementOrderSearchInput searchInput)
	{
		SingularAttribute<ProcurementOrder, ?>[] attributes = readSeachAttributes(searchInput);
		return ejb.countBy(searchInput.getEntity(), attributes);
	}

	@POST
	@Path("/findByLike")
	@Produces({ "application/json", "application/xml" })
	@Consumes({ "application/json", "application/xml" })
	public ProcurementOrderSearchResult findByLike(ProcurementOrderSearchInput searchInput)
	{
		SingularAttribute<ProcurementOrder, Object>[] attributes = readSeachAttributes(searchInput);
		Long countLike = ejb.countByLike(searchInput.getEntity(), attributes);
		List<ProcurementOrder> resultList = ejb.findByLike(searchInput.getEntity(),
				searchInput.getStart(), searchInput.getMax(), attributes);
		return new ProcurementOrderSearchResult(countLike, detach(resultList),
				detach(searchInput));
	}
	
	
	// Advanced search method
	@POST
	@Path("/advancedSearch")
	@Produces({ "application/json", "application/xml" })
	@Consumes({ "application/json", "application/xml" })
	public ProcurementOrderSearchResult advancedSearch(ProcurementOrderAdvancedSearchData data) {
		List<ProcurementOrder> search = ejb.avancedSearch(data);
		return new ProcurementOrderSearchResult(Long.valueOf(1), detach(search), detach(new ProcurementOrderSearchInput()));
	}

	@POST
	@Path("/countByLike")
	@Consumes({ "application/json", "application/xml" })
	public Long countByLike(ProcurementOrderSearchInput searchInput)
	{
		SingularAttribute<ProcurementOrder, ?>[] attributes = readSeachAttributes(searchInput);
		return ejb.countByLike(searchInput.getEntity(), attributes);
	}

	@SuppressWarnings("unchecked")
	private SingularAttribute<ProcurementOrder, Object>[] readSeachAttributes(
			ProcurementOrderSearchInput searchInput)
			{
		List<String> fieldNames = searchInput.getFieldNames();
		List<SingularAttribute<ProcurementOrder, ?>> result = new ArrayList<SingularAttribute<ProcurementOrder, ?>>();
		for (String fieldName : fieldNames)
		{
			Field[] fields = ProcurementOrder_.class.getFields();
			for (Field field : fields)
			{
				if (field.getName().equals(fieldName))
				{
					try
					{
						result.add((SingularAttribute<ProcurementOrder, ?>) field.get(null));
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

	private static final List<String> creatingUserFields = Arrays.asList("loginName", "email", "gender");

	private static final List<String> supplierFields = Arrays.asList("name", "fax", "email");

	private static final List<String> agencyFields = Arrays.asList("agencyNumber", "name", "active", "name", "name", "phone", "fax");

	private static final List<String> vatFields = Arrays.asList("name", "rate", "active");

	private static final List<String> procurementOrderItemsFields = Arrays.asList("mainPic", "secondaryPic", "articleName", "article.articleName", "expirationDate", "qtyOrdered", "availableQty", "freeQuantity", "stockQuantity", "salesPricePU", "purchasePricePU", "totalPurchasePrice", "valid");

	public ProcurementOrder detach(ProcurementOrder entity)
	{
		if (entity == null)
			return null;

		// aggregated
		entity.setCreatingUser(loginMerger.unbind(entity.getCreatingUser(), creatingUserFields));

		// aggregated
		entity.setSupplier(supplierMerger.unbind(entity.getSupplier(), supplierFields));

		// aggregated
		entity.setAgency(agencyMerger.unbind(entity.getAgency(), agencyFields));

		// aggregated
		entity.setVat(vATMerger.unbind(entity.getVat(), vatFields));

		// composed collections
		entity.setProcurementOrderItems(procurementOrderItemMerger.unbind(entity.getProcurementOrderItems(), procurementOrderItemsFields));

		return entity;
	}

	private List<ProcurementOrder> detach(List<ProcurementOrder> list)
	{
		if (list == null)
			return list;
		List<ProcurementOrder> result = new ArrayList<ProcurementOrder>();
		for (ProcurementOrder entity : list)
		{
			result.add(detach(entity));
		}
		return result;
	}

	private ProcurementOrderSearchInput detach(ProcurementOrderSearchInput searchInput)
	{
		searchInput.setEntity(detach(searchInput.getEntity()));
		return searchInput;
	}
	
}