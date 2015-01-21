package org.adorsys.adpharma.server.rest;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adpharma.server.jpa.DeliveryItemArticleLotEvent;
import org.adorsys.adpharma.server.repo.DeliveryItemArticleLotEventRepository;

@Stateless
public class DeliveryItemArticleLotEventEJB
{

	@Inject
	private DeliveryItemArticleLotEventRepository repository;

	public DeliveryItemArticleLotEvent create(DeliveryItemArticleLotEvent entity)
	{
		return repository.save(entity);
	}

	public DeliveryItemArticleLotEvent deleteById(Long id)
	{
		DeliveryItemArticleLotEvent entity = repository.findBy(id);
		if (entity != null)
		{
			repository.remove(entity);
		}
		return entity;
	}


	public DeliveryItemArticleLotEvent update(DeliveryItemArticleLotEvent entity)
	{
		DeliveryItemArticleLotEvent save = repository.save(entity);
		return save;
	}

	public DeliveryItemArticleLotEvent findById(Long id)
	{
		return repository.findBy(id);
	}

	public List<DeliveryItemArticleLotEvent> listAll(int start, int max)
	{
		return repository.findAll(start, max);
	}

	public Long count()
	{
		return repository.count();
	}

	public List<DeliveryItemArticleLotEvent> loadOldestEvent(int qty, Date date){
		return repository.loadOldestEvent(date).orderAsc("created").maxResults(qty).getResultList();
	}


}
