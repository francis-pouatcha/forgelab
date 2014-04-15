package org.adorsys.adpharma.server.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adpharma.server.jpa.ProductDetailConfig;
import org.adorsys.adpharma.server.jpa.ProductDetailConfig_;
import org.adorsys.adpharma.server.repo.ProductDetailConfigRepository;

@Stateless
public class ProductDetailConfigEJB
{

	@Inject
	private ProductDetailConfigRepository repository;

	@Inject
	private ArticleMerger articleMerger;

	@SuppressWarnings("unchecked")
	public ProductDetailConfig create(ProductDetailConfig entity)
	{
		if(entity.getSource().equals(entity.getTarget())) throw new IllegalStateException("Source article must be different of target Article ");
		List<ProductDetailConfig> found = findBy(entity, 0, -1, new SingularAttribute[]{ProductDetailConfig_.source,ProductDetailConfig_.target});
		if(!found.isEmpty()) throw new IllegalStateException("this configuration already exist ");
		return repository.save(attach(entity));
	}

	public List<ProductDetailConfig>  findByOriginAndTArgetNameLike(String sourceName,String targetName){
		sourceName =sourceName+"%";
		targetName=targetName+"%";
		return repository.findByOriginAndTArgetNameLike(sourceName, targetName);

	}

	public ProductDetailConfig deleteById(Long id)
	{
		ProductDetailConfig entity = repository.findBy(id);
		if (entity != null)
		{
			repository.remove(entity);
		}
		return entity;
	}

	public ProductDetailConfig update(ProductDetailConfig entity)
	{
		return repository.save(attach(entity));
	}

	public ProductDetailConfig findById(Long id)
	{
		return repository.findBy(id);
	}

	public List<ProductDetailConfig> listAll(int start, int max)
	{
		return repository.findAll(start, max);
	}

	public Long count()
	{
		return repository.count();
	}

	public List<ProductDetailConfig> findBy(ProductDetailConfig entity, int start, int max, SingularAttribute<ProductDetailConfig, ?>[] attributes)
	{
		return repository.findBy(entity, start, max, attributes);
	}

	public Long countBy(ProductDetailConfig entity, SingularAttribute<ProductDetailConfig, ?>[] attributes)
	{
		return repository.count(entity, attributes);
	}

	public List<ProductDetailConfig> findByLike(ProductDetailConfig entity, int start, int max, SingularAttribute<ProductDetailConfig, ?>[] attributes)
	{
		return repository.findByLike(entity, start, max, attributes);
	}

	public Long countByLike(ProductDetailConfig entity, SingularAttribute<ProductDetailConfig, ?>[] attributes)
	{
		return repository.countLike(entity, attributes);
	}

	private ProductDetailConfig attach(ProductDetailConfig entity)
	{
		if (entity == null)
			return null;

		// aggregated
		entity.setSource(articleMerger.bindAggregated(entity.getSource()));

		// aggregated
		entity.setTarget(articleMerger.bindAggregated(entity.getTarget()));

		return entity;
	}
}
