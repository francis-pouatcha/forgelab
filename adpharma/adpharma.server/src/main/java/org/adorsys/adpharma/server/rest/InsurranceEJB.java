package org.adorsys.adpharma.server.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;
import org.adorsys.adpharma.server.jpa.Insurrance;
import org.adorsys.adpharma.server.repo.InsurranceRepository;

@Stateless
public class InsurranceEJB
{

	@Inject
	private InsurranceRepository repository;

	@Inject
	private CustomerMerger customerMerger;

	public Insurrance create(Insurrance entity)
	{
		Insurrance attach = attach(entity);
		List<Insurrance> byCustomerAndInsurer = repository.findByCustomerAndInsurer(attach.getCustomer(), attach.getInsurer());
		if(!byCustomerAndInsurer.isEmpty())
			return byCustomerAndInsurer.iterator().next();
		return repository.save(attach);
	}

	public Insurrance deleteById(Long id)
	{
		Insurrance entity = repository.findBy(id);
		if (entity != null)
		{
			repository.remove(entity);
		}
		return entity;
	}

	public Insurrance update(Insurrance entity)
	{
		return repository.save(attach(entity));
	}

	public Insurrance findById(Long id)
	{
		return repository.findBy(id);
	}

	public List<Insurrance> listAll(int start, int max)
	{
		return repository.findAll(start, max);
	}

	public Long count()
	{
		return repository.count();
	}

	public List<Insurrance> findBy(Insurrance entity, int start, int max, SingularAttribute<Insurrance, ?>[] attributes)
	{
		return repository.findBy(entity, start, max, attributes);
	}

	public Long countBy(Insurrance entity, SingularAttribute<Insurrance, ?>[] attributes)
	{
		return repository.count(entity, attributes);
	}

	public List<Insurrance> findByLike(Insurrance entity, int start, int max, SingularAttribute<Insurrance, ?>[] attributes)
	{
		return repository.findByLike(entity, start, max, attributes);
	}

	public Long countByLike(Insurrance entity, SingularAttribute<Insurrance, ?>[] attributes)
	{
		return repository.countLike(entity, attributes);
	}

	private Insurrance attach(Insurrance entity)
	{
		if (entity == null)
			return null;

		// aggregated
		entity.setCustomer(customerMerger.bindAggregated(entity.getCustomer()));

		// aggregated
		entity.setInsurer(customerMerger.bindAggregated(entity.getInsurer()));

		return entity;
	}
}
