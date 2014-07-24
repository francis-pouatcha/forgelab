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

import org.adorsys.adpharma.server.jpa.CashDrawer;
import org.adorsys.adpharma.server.jpa.CashDrawerSearchInput;
import org.adorsys.adpharma.server.jpa.CashDrawerSearchResult;
import org.adorsys.adpharma.server.jpa.CashDrawer_;
import org.adorsys.adpharma.server.utils.AdTimeFrameBasedSearchInput;

/**
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/cashdrawers")
public class CashDrawerEndpoint
{

	@Inject
	private CashDrawerEJB ejb;

	@Inject
	private LoginMerger loginMerger;

	@Inject
	private AgencyMerger agencyMerger;


	@POST
	@Consumes({ "application/json", "application/xml" })
	@Produces({ "application/json", "application/xml" })
	public CashDrawer create(CashDrawer entity)
	{
		return detach(ejb.create(entity));
	}

	@DELETE
	@Path("/{id:[0-9][0-9]*}")
	public Response deleteById(@PathParam("id") Long id)
	{
		CashDrawer deleted = ejb.deleteById(id);
		if (deleted == null)
			return Response.status(Status.NOT_FOUND).build();

		return Response.ok(detach(deleted)).build();
	}

	@PUT
	@Path("/{id:[0-9][0-9]*}")
	@Produces({ "application/json", "application/xml" })
	@Consumes({ "application/json", "application/xml" })
	public CashDrawer update(CashDrawer entity)
	{
		return detach(ejb.update(entity));
	}
	
	@PUT
	@Path("/close")
	@Produces({ "application/json", "application/xml" })
	@Consumes({ "application/json", "application/xml" })
	public CashDrawer close(CashDrawer entity)
	{
		return detach(ejb.close(entity));
	}

	@GET
	@Path("/{id:[0-9][0-9]*}")
	@Produces({ "application/json", "application/xml" })
	public Response findById(@PathParam("id") Long id)
	{
		CashDrawer found = ejb.findById(id);
		if (found == null)
			return Response.status(Status.NOT_FOUND).build();
		return Response.ok(detach(found)).build();
	}

	@GET
	@Produces({ "application/json", "application/xml" })
	public CashDrawerSearchResult listAll(@QueryParam("start") int start,
			@QueryParam("max") int max)
	{
		List<CashDrawer> resultList = ejb.listAll(start, max);
		CashDrawerSearchInput searchInput = new CashDrawerSearchInput();
		searchInput.setStart(start);
		searchInput.setMax(max);
		return new CashDrawerSearchResult((long) resultList.size(),
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
	public CashDrawerSearchResult findBy(CashDrawerSearchInput searchInput)
	{
		SingularAttribute<CashDrawer, ?>[] attributes = readSeachAttributes(searchInput);
		Long count = ejb.countBy(searchInput.getEntity(), attributes);
		List<CashDrawer> resultList = ejb.findBy(searchInput.getEntity(),
				searchInput.getStart(), searchInput.getMax(), attributes);
		return new CashDrawerSearchResult(count, detach(resultList),
				detach(searchInput));
	}

	@POST
	@Path("/countBy")
	@Consumes({ "application/json", "application/xml" })
	public Long countBy(CashDrawerSearchInput searchInput)
	{
		SingularAttribute<CashDrawer, ?>[] attributes = readSeachAttributes(searchInput);
		return ejb.countBy(searchInput.getEntity(), attributes);
	}

	@POST
	@Path("/findByLike")
	@Produces({ "application/json", "application/xml" })
	@Consumes({ "application/json", "application/xml" })
	public CashDrawerSearchResult findByLike(CashDrawerSearchInput searchInput)
	{
		SingularAttribute<CashDrawer, ?>[] attributes = readSeachAttributes(searchInput);
		Long countLike = ejb.countByLike(searchInput.getEntity(), attributes);
		List<CashDrawer> resultList = ejb.findByLike(searchInput.getEntity(),
				searchInput.getStart(), searchInput.getMax(), attributes);
		return new CashDrawerSearchResult(countLike, detach(resultList),
				detach(searchInput));
	}

	@POST
	@Path("/countByLike")
	@Consumes({ "application/json", "application/xml" })
	public Long countByLike(CashDrawerSearchInput searchInput)
	{
		SingularAttribute<CashDrawer, ?>[] attributes = readSeachAttributes(searchInput);
		return ejb.countByLike(searchInput.getEntity(), attributes);
	}

	@SuppressWarnings("unchecked")
	private SingularAttribute<CashDrawer, ?>[] readSeachAttributes(
			CashDrawerSearchInput searchInput)
			{
		List<String> fieldNames = searchInput.getFieldNames();
		List<SingularAttribute<CashDrawer, ?>> result = new ArrayList<SingularAttribute<CashDrawer, ?>>();
		for (String fieldName : fieldNames)
		{
			Field[] fields = CashDrawer_.class.getFields();
			for (Field field : fields)
			{
				if (field.getName().equals(fieldName))
				{
					try
					{
						result.add((SingularAttribute<CashDrawer, ?>) field.get(null));
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

	private static final List<String> closedByFields = Arrays.asList("loginName", "email", "gender");

	private static final List<String> agencyFields = Arrays.asList("agencyNumber", "name", "active", "name", "name", "phone", "fax");

	private CashDrawer detach(CashDrawer entity)
	{
		if (entity == null)
			return null;

		// aggregated
		entity.setCashier(loginMerger.unbind(entity.getCashier(), cashierFields));

		// aggregated
		entity.setClosedBy(loginMerger.unbind(entity.getClosedBy(), closedByFields));

		// aggregated
		entity.setAgency(agencyMerger.unbind(entity.getAgency(), agencyFields));

		return entity;
	}

	private List<CashDrawer> detach(List<CashDrawer> list)
	{
		if (list == null)
			return list;
		List<CashDrawer> result = new ArrayList<CashDrawer>();
		for (CashDrawer entity : list)
		{
			result.add(detach(entity));
		}
		return result;
	}

	private CashDrawerSearchInput detach(CashDrawerSearchInput searchInput)
	{
		searchInput.setEntity(detach(searchInput.getEntity()));
		return searchInput;
	}
	
	/**
	 * Returns the opened cash drawer of a cashier.
	 * 
	 * @param start
	 * @param max
	 * @return
	 */
	@GET
	@Path("/myOpenDrawers")
	@Produces({ "application/json", "application/xml" })
	public CashDrawerSearchResult myOpenDrawers(){
		
		List<CashDrawer> resultList = ejb.myOpenDrawers();
		return new CashDrawerSearchResult(new Long(resultList.size()), detach(resultList),
				new CashDrawerSearchInput());
	}
	
	@GET
	@Path("/agencyDrawers")
	@Produces({ "application/json", "application/xml" })
	public CashDrawerSearchResult agencyDrawers(){
		List<CashDrawer> resultList = ejb.agencyDrawers();
		return new CashDrawerSearchResult(new Long(resultList.size()), detach(resultList),
				new CashDrawerSearchInput());
	}
	
//	@PUT
//	@Path("/close/{id:[0-9][0-9]*}")
//	@Produces({ "application/json", "application/xml" })
//	@Consumes({ "application/json", "application/xml" })
//	public CashDrawer close(CashDrawer entity)
//	{
//		return detach(ejb.close(entity));
//	}

	@POST
	@Path("/findByClosingDateBetween")
	@Produces({ "application/json", "application/xml" })
	@Consumes({ "application/json", "application/xml" })
	public CashDrawerSearchResult findByClosingDateBetween(AdTimeFrameBasedSearchInput searchInput)
	{
		Long count = ejb.countByOpeningDateBetween(searchInput.getTimeFrame().getStartTime(), searchInput.getTimeFrame().getEndTime());
		List<CashDrawer> resultList = ejb.findByOpeningDateBetween(searchInput.getTimeFrame().getStartTime(), 
				searchInput.getTimeFrame().getEndTime(), searchInput.getStart(), searchInput.getMax());
		return new CashDrawerSearchResult(count, detach(resultList),null);
	}
}