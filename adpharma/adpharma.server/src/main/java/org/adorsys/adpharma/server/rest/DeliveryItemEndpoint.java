package org.adorsys.adpharma.server.rest;

import java.lang.reflect.Field;
import java.security.SecurityPermission;
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

import org.adorsys.adpharma.server.jpa.DeliveryItem;
import org.adorsys.adpharma.server.jpa.DeliveryItem_;
import org.adorsys.adpharma.server.jpa.DeliveryItemSearchInput;
import org.adorsys.adpharma.server.jpa.DeliveryItemSearchResult;
import org.adorsys.adpharma.server.jpa.Login;
import org.adorsys.adpharma.server.security.SecurityUtil;

/**
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/deliveryitems")
public class DeliveryItemEndpoint
{

	@Inject
	private DeliveryItemEJB ejb;

	@Inject
	private LoginMerger loginMerger;

	@Inject
	private DeliveryMerger deliveryMerger;

	@Inject
	private ArticleMerger articleMerger;

	@Inject
	private SecurityUtil securityUtilEJB;

	@POST
	@Consumes({ "application/json", "application/xml" })
	@Produces({ "application/json", "application/xml" })
	public DeliveryItem create(DeliveryItem entity)
	{
		Login login = securityUtilEJB.getConnectedUser();
		entity.setCreatingUser(login);
		entity.calculateAmount();
		return detach(ejb.create(entity));
	}

	@DELETE
	@Path("/{id:[0-9][0-9]*}")
	public Response deleteById(@PathParam("id") Long id)
	{
		DeliveryItem deleted = ejb.deleteById(id);
		if (deleted == null)
			return Response.status(Status.NOT_FOUND).build();

		return Response.ok(detach(deleted)).build();
	}

	@PUT
	@Path("/{id:[0-9][0-9]*}")
	@Produces({ "application/json", "application/xml" })
	@Consumes({ "application/json", "application/xml" })
	public DeliveryItem update(DeliveryItem entity)
	{
		entity.calculateAmount();
		return detach(ejb.update(entity));
	}

	@GET
	@Path("/{id:[0-9][0-9]*}")
	@Produces({ "application/json", "application/xml" })
	public Response findById(@PathParam("id") Long id)
	{
		DeliveryItem found = ejb.findById(id);
		if (found == null)
			return Response.status(Status.NOT_FOUND).build();
		return Response.ok(detach(found)).build();
	}

	@GET
	@Produces({ "application/json", "application/xml" })
	public DeliveryItemSearchResult listAll(@QueryParam("start") int start,
			@QueryParam("max") int max)
	{
		List<DeliveryItem> resultList = ejb.listAll(start, max);
		DeliveryItemSearchInput searchInput = new DeliveryItemSearchInput();
		searchInput.setStart(start);
		searchInput.setMax(max);
		return new DeliveryItemSearchResult((long) resultList.size(),
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
	public DeliveryItemSearchResult findBy(DeliveryItemSearchInput searchInput)
	{
		SingularAttribute<DeliveryItem, ?>[] attributes = readSeachAttributes(searchInput);
		Long count = ejb.countBy(searchInput.getEntity(), attributes);
		List<DeliveryItem> resultList = ejb.findBy(searchInput.getEntity(),
				searchInput.getStart(), searchInput.getMax(), attributes);
		return new DeliveryItemSearchResult(count, detach(resultList),
				detach(searchInput));
	}

	@POST
	@Path("/countBy")
	@Consumes({ "application/json", "application/xml" })
	public Long countBy(DeliveryItemSearchInput searchInput)
	{
		SingularAttribute<DeliveryItem, ?>[] attributes = readSeachAttributes(searchInput);
		return ejb.countBy(searchInput.getEntity(), attributes);
	}

	@POST
	@Path("/findByLike")
	@Produces({ "application/json", "application/xml" })
	@Consumes({ "application/json", "application/xml" })
	public DeliveryItemSearchResult findByLike(DeliveryItemSearchInput searchInput)
	{
		SingularAttribute<DeliveryItem, ?>[] attributes = readSeachAttributes(searchInput);
		Long countLike = ejb.countByLike(searchInput.getEntity(), attributes);
		List<DeliveryItem> resultList = ejb.findByLike(searchInput.getEntity(),
				searchInput.getStart(), searchInput.getMax(), attributes);
		return new DeliveryItemSearchResult(countLike, detach(resultList),
				detach(searchInput));
	}

	@POST
	@Path("/countByLike")
	@Consumes({ "application/json", "application/xml" })
	public Long countByLike(DeliveryItemSearchInput searchInput)
	{
		SingularAttribute<DeliveryItem, ?>[] attributes = readSeachAttributes(searchInput);
		return ejb.countByLike(searchInput.getEntity(), attributes);
	}

	@SuppressWarnings("unchecked")
	private SingularAttribute<DeliveryItem, ?>[] readSeachAttributes(
			DeliveryItemSearchInput searchInput)
			{
		List<String> fieldNames = searchInput.getFieldNames();
		List<SingularAttribute<DeliveryItem, ?>> result = new ArrayList<SingularAttribute<DeliveryItem, ?>>();
		for (String fieldName : fieldNames)
		{
			Field[] fields = DeliveryItem_.class.getFields();
			for (Field field : fields)
			{
				if (field.getName().equals(fieldName))
				{
					try
					{
						result.add((SingularAttribute<DeliveryItem, ?>) field.get(null));
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

	private static final List<String> deliveryFields = Arrays.asList("deliveryNumber", "deliverySlipNumber", "dateOnDeliverySlip", "amountBeforeTax", "amountAfterTax", "amountDiscount", "netAmountToPay", "vat.rate", "receivingAgency.name");

	private static final List<String> articleFields = Arrays.asList("articleName", "pic", "manufacturer", "active", "qtyInStock", "sppu", "authorizedSale", "agency.name");

	private static final List<String> creatingUserFields = Arrays.asList("loginName", "email", "gender");

	private DeliveryItem detach(DeliveryItem entity)
	{
		if (entity == null)
			return null;

		// composed
		entity.setDelivery(deliveryMerger.unbind(entity.getDelivery(), deliveryFields));

		// aggregated
		entity.setArticle(articleMerger.unbind(entity.getArticle(), articleFields));

		// aggregated
		entity.setCreatingUser(loginMerger.unbind(entity.getCreatingUser(), creatingUserFields));

		return entity;
	}

	private List<DeliveryItem> detach(List<DeliveryItem> list)
	{
		if (list == null)
			return list;
		List<DeliveryItem> result = new ArrayList<DeliveryItem>();
		for (DeliveryItem entity : list)
		{
			result.add(detach(entity));
		}
		return result;
	}

	private DeliveryItemSearchInput detach(DeliveryItemSearchInput searchInput)
	{
		searchInput.setEntity(detach(searchInput.getEntity()));
		return searchInput;
	}
}