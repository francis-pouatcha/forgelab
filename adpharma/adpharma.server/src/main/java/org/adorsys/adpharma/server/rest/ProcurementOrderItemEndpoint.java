package org.adorsys.adpharma.server.rest;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
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

import org.adorsys.adpharma.server.jpa.Login;
import org.adorsys.adpharma.server.jpa.ProcurementOrderItem;
import org.adorsys.adpharma.server.jpa.ProcurementOrderItem_;
import org.adorsys.adpharma.server.jpa.ProcurementOrderItemSearchInput;
import org.adorsys.adpharma.server.jpa.ProcurementOrderItemSearchResult;
import org.adorsys.adpharma.server.security.SecurityUtil;

/**
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/procurementorderitems")
public class ProcurementOrderItemEndpoint
{

	@Inject
	private ProcurementOrderItemEJB ejb;

	@Inject
	private LoginMerger loginMerger;

	@Inject
	private ProcurementOrderMerger procurementOrderMerger;

	@Inject
	private ArticleMerger articleMerger;

	@Inject
	private SecurityUtil securityUtilEJB;

	@POST
	@Consumes({ "application/json", "application/xml" })
	@Produces({ "application/json", "application/xml" })
	public ProcurementOrderItem create(ProcurementOrderItem entity)
	{
		Login login = securityUtilEJB.getConnectedUser();
		entity.setCreatingUser(login);
		entity.setProductRecCreated(new Date());
		return detach(ejb.create(entity));
	}

	@DELETE
	@Path("/{id:[0-9][0-9]*}")
	public Response deleteById(@PathParam("id") Long id)
	{
		ProcurementOrderItem deleted = ejb.deleteById(id);
		if (deleted == null)
			return Response.status(Status.NOT_FOUND).build();

		return Response.ok(detach(deleted)).build();
	}

	@PUT
	@Path("/{id:[0-9][0-9]*}")
	@Produces({ "application/json", "application/xml" })
	@Consumes({ "application/json", "application/xml" })
	public ProcurementOrderItem update(ProcurementOrderItem entity)
	{
		return detach(ejb.update(entity));
	}

	@GET
	@Path("/{id:[0-9][0-9]*}")
	@Produces({ "application/json", "application/xml" })
	public Response findById(@PathParam("id") Long id)
	{
		ProcurementOrderItem found = ejb.findById(id);
		if (found == null)
			return Response.status(Status.NOT_FOUND).build();
		return Response.ok(detach(found)).build();
	}

	@GET
	@Produces({ "application/json", "application/xml" })
	public ProcurementOrderItemSearchResult listAll(@QueryParam("start") int start,
			@QueryParam("max") int max)
	{
		List<ProcurementOrderItem> resultList = ejb.listAll(start, max);
		ProcurementOrderItemSearchInput searchInput = new ProcurementOrderItemSearchInput();
		searchInput.setStart(start);
		searchInput.setMax(max);
		return new ProcurementOrderItemSearchResult((long) resultList.size(),
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
	public ProcurementOrderItemSearchResult findBy(ProcurementOrderItemSearchInput searchInput)
	{
		SingularAttribute<ProcurementOrderItem, ?>[] attributes = readSeachAttributes(searchInput);
		Long count = ejb.countBy(searchInput.getEntity(), attributes);
		List<ProcurementOrderItem> resultList = ejb.findBy(searchInput.getEntity(),
				searchInput.getStart(), searchInput.getMax(), attributes);
		return new ProcurementOrderItemSearchResult(count, detach(resultList),
				detach(searchInput));
	}

	@POST
	@Path("/countBy")
	@Consumes({ "application/json", "application/xml" })
	public Long countBy(ProcurementOrderItemSearchInput searchInput)
	{
		SingularAttribute<ProcurementOrderItem, ?>[] attributes = readSeachAttributes(searchInput);
		return ejb.countBy(searchInput.getEntity(), attributes);
	}

	@POST
	@Path("/findByLike")
	@Produces({ "application/json", "application/xml" })
	@Consumes({ "application/json", "application/xml" })
	public ProcurementOrderItemSearchResult findByLike(ProcurementOrderItemSearchInput searchInput)
	{
		SingularAttribute<ProcurementOrderItem, ?>[] attributes = readSeachAttributes(searchInput);
		Long countLike = ejb.countByLike(searchInput.getEntity(), attributes);
		List<ProcurementOrderItem> resultList = ejb.findByLike(searchInput.getEntity(),
				searchInput.getStart(), searchInput.getMax(), attributes);
		return new ProcurementOrderItemSearchResult(countLike, detach(resultList),
				detach(searchInput));
	}

	@POST
	@Path("/countByLike")
	@Consumes({ "application/json", "application/xml" })
	public Long countByLike(ProcurementOrderItemSearchInput searchInput)
	{
		SingularAttribute<ProcurementOrderItem, ?>[] attributes = readSeachAttributes(searchInput);
		return ejb.countByLike(searchInput.getEntity(), attributes);
	}

	@SuppressWarnings("unchecked")
	private SingularAttribute<ProcurementOrderItem, ?>[] readSeachAttributes(
			ProcurementOrderItemSearchInput searchInput)
			{
		List<String> fieldNames = searchInput.getFieldNames();
		List<SingularAttribute<ProcurementOrderItem, ?>> result = new ArrayList<SingularAttribute<ProcurementOrderItem, ?>>();
		for (String fieldName : fieldNames)
		{
			Field[] fields = ProcurementOrderItem_.class.getFields();
			for (Field field : fields)
			{
				if (field.getName().equals(fieldName))
				{
					try
					{
						result.add((SingularAttribute<ProcurementOrderItem, ?>) field.get(null));
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

	private static final List<String> procurementOrderFields = Arrays.asList("procurementOrderNumber", "poStatus", "agency.name", "amountBeforeTax", "amountAfterTax", "amountDiscount", "taxAmount", "netAmountToPay", "vat.rate");

	private static final List<String> articleFields = Arrays.asList("articleName", "pic", "manufacturer", "active", "qtyInStock", "sppu", "authorizedSale", "agency.name");

	private static final List<String> creatingUserFields = Arrays.asList("loginName", "email", "gender");

	private ProcurementOrderItem detach(ProcurementOrderItem entity)
	{
		if (entity == null)
			return null;

		// composed
		entity.setProcurementOrder(procurementOrderMerger.unbind(entity.getProcurementOrder(), procurementOrderFields));

		// aggregated
		entity.setArticle(articleMerger.unbind(entity.getArticle(), articleFields));

		// aggregated
		entity.setCreatingUser(loginMerger.unbind(entity.getCreatingUser(), creatingUserFields));

		return entity;
	}

	private List<ProcurementOrderItem> detach(List<ProcurementOrderItem> list)
	{
		if (list == null)
			return list;
		List<ProcurementOrderItem> result = new ArrayList<ProcurementOrderItem>();
		for (ProcurementOrderItem entity : list)
		{
			result.add(detach(entity));
		}
		return result;
	}

	private ProcurementOrderItemSearchInput detach(ProcurementOrderItemSearchInput searchInput)
	{
		searchInput.setEntity(detach(searchInput.getEntity()));
		return searchInput;
	}
}