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
import org.adorsys.adpharma.server.jpa.ProductDetailConfig;
import org.adorsys.adpharma.server.jpa.ProductDetailConfig_;
import org.adorsys.adpharma.server.jpa.ProductDetailConfigSearchInput;
import org.adorsys.adpharma.server.jpa.ProductDetailConfigSearchResult;

/**
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/productdetailconfigs")
public class ProductDetailConfigEndpoint
{

	@Inject
	private ProductDetailConfigEJB ejb;

	@Inject
	private ArticleMerger articleMerger;

	@POST
	@Consumes({ "application/json", "application/xml" })
	@Produces({ "application/json", "application/xml" })
	public ProductDetailConfig create(ProductDetailConfig entity)
	{
		return detach(ejb.create(entity));
	}

	@DELETE
	@Path("/{id:[0-9][0-9]*}")
	public Response deleteById(@PathParam("id") Long id)
	{
		ProductDetailConfig deleted = ejb.deleteById(id);
		if (deleted == null)
			return Response.status(Status.NOT_FOUND).build();

		return Response.ok(detach(deleted)).build();
	}

	@PUT
	@Path("/{id:[0-9][0-9]*}")
	@Produces({ "application/json", "application/xml" })
	@Consumes({ "application/json", "application/xml" })
	public ProductDetailConfig update(ProductDetailConfig entity)
	{
		return detach(ejb.update(entity));
	}

	@GET
	@Path("/{id:[0-9][0-9]*}")
	@Produces({ "application/json", "application/xml" })
	public Response findById(@PathParam("id") Long id)
	{
		ProductDetailConfig found = ejb.findById(id);
		if (found == null)
			return Response.status(Status.NOT_FOUND).build();
		return Response.ok(detach(found)).build();
	}

	@GET
	@Path("/findByOriginAndTargetNameLike")
	@Produces({ "application/json", "application/xml" })
	public Response findByOriginAndTargetNameLike(@QueryParam("source") String source,@QueryParam("target") String target)
	{
		List<ProductDetailConfig> productDetailConfig = ejb.findByOriginAndTArgetNameLike(source, target);
		return Response.ok(productDetailConfig).build();
	}

	@GET
	@Produces({ "application/json", "application/xml" })
	public ProductDetailConfigSearchResult listAll(@QueryParam("start") int start,
			@QueryParam("max") int max)
	{
		List<ProductDetailConfig> resultList = ejb.listAll(start, max);
		ProductDetailConfigSearchInput searchInput = new ProductDetailConfigSearchInput();
		searchInput.setStart(start);
		searchInput.setMax(max);
		return new ProductDetailConfigSearchResult((long) resultList.size(),
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
	public ProductDetailConfigSearchResult findBy(ProductDetailConfigSearchInput searchInput)
	{
		SingularAttribute<ProductDetailConfig, ?>[] attributes = readSeachAttributes(searchInput);
		Long count = ejb.countBy(searchInput.getEntity(), attributes);
		List<ProductDetailConfig> resultList = ejb.findBy(searchInput.getEntity(),
				searchInput.getStart(), searchInput.getMax(), attributes);
		return new ProductDetailConfigSearchResult(count, detach(resultList),
				detach(searchInput));
	}

	@POST
	@Path("/countBy")
	@Consumes({ "application/json", "application/xml" })
	public Long countBy(ProductDetailConfigSearchInput searchInput)
	{
		SingularAttribute<ProductDetailConfig, ?>[] attributes = readSeachAttributes(searchInput);
		return ejb.countBy(searchInput.getEntity(), attributes);
	}

	@POST
	@Path("/findByLike")
	@Produces({ "application/json", "application/xml" })
	@Consumes({ "application/json", "application/xml" })
	public ProductDetailConfigSearchResult findByLike(ProductDetailConfigSearchInput searchInput)
	{
		SingularAttribute<ProductDetailConfig, ?>[] attributes = readSeachAttributes(searchInput);
		Long countLike = ejb.countByLike(searchInput.getEntity(), attributes);
		List<ProductDetailConfig> resultList = ejb.findByLike(searchInput.getEntity(),
				searchInput.getStart(), searchInput.getMax(), attributes);
		return new ProductDetailConfigSearchResult(countLike, detach(resultList),
				detach(searchInput));
	}

	@POST
	@Path("/countByLike")
	@Consumes({ "application/json", "application/xml" })
	public Long countByLike(ProductDetailConfigSearchInput searchInput)
	{
		SingularAttribute<ProductDetailConfig, ?>[] attributes = readSeachAttributes(searchInput);
		return ejb.countByLike(searchInput.getEntity(), attributes);
	}

	@SuppressWarnings("unchecked")
	private SingularAttribute<ProductDetailConfig, ?>[] readSeachAttributes(
			ProductDetailConfigSearchInput searchInput)
			{
		List<String> fieldNames = searchInput.getFieldNames();
		List<SingularAttribute<ProductDetailConfig, ?>> result = new ArrayList<SingularAttribute<ProductDetailConfig, ?>>();
		for (String fieldName : fieldNames)
		{
			Field[] fields = ProductDetailConfig_.class.getFields();
			for (Field field : fields)
			{
				if (field.getName().equals(fieldName))
				{
					try
					{
						result.add((SingularAttribute<ProductDetailConfig, ?>) field.get(null));
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

	private static final List<String> sourceFields = Arrays.asList("articleName", "pic", "manufacturer", "active", "qtyInStock", "sppu", "authorizedSale", "agency.name");

	private static final List<String> targetFields = Arrays.asList("articleName", "pic", "manufacturer", "active", "qtyInStock", "sppu", "authorizedSale", "agency.name");

	private ProductDetailConfig detach(ProductDetailConfig entity)
	{
		if (entity == null)
			return null;

		// aggregated
		entity.setSource(articleMerger.unbind(entity.getSource(), sourceFields));

		// aggregated
		entity.setTarget(articleMerger.unbind(entity.getTarget(), targetFields));

		return entity;
	}

	private List<ProductDetailConfig> detach(List<ProductDetailConfig> list)
	{
		if (list == null)
			return list;
		List<ProductDetailConfig> result = new ArrayList<ProductDetailConfig>();
		for (ProductDetailConfig entity : list)
		{
			result.add(detach(entity));
		}
		return result;
	}

	private ProductDetailConfigSearchInput detach(ProductDetailConfigSearchInput searchInput)
	{
		searchInput.setEntity(detach(searchInput.getEntity()));
		return searchInput;
	}
}