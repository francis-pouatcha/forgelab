package org.adorsys.adpharma.server.rest;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adpharma.server.jpa.DeliveryItemArticleEvent;
import org.adorsys.adpharma.server.jpa.DeliveryItemArticleLotEvent;
import org.adorsys.adpharma.server.repo.DeliveryItemArticleEventRepository;

@Stateless
public class DeliveryItemArticleEventEJB
{

	@Inject
	private DeliveryItemArticleEventRepository repository;

	public DeliveryItemArticleEvent create(DeliveryItemArticleEvent entity)
	{
		return repository.save(entity);
	}

	public DeliveryItemArticleEvent deleteById(Long id)
	{
		DeliveryItemArticleEvent entity = repository.findBy(id);
		if (entity != null)
		{
			repository.remove(entity);
		}
		return entity;
	}


	public DeliveryItemArticleEvent update(DeliveryItemArticleEvent entity)
	{
		DeliveryItemArticleEvent save = repository.save(entity);
		return save;
	}

	public DeliveryItemArticleEvent findById(Long id)
	{
		return repository.findBy(id);
	}

	public List<DeliveryItemArticleEvent> listAll(int start, int max)
	{
		return repository.findAll(start, max);
	}

	public Long count()
	{
		return repository.count();
	}

	public List<DeliveryItemArticleEvent> loadOldestEvent(int qty, Date date){
		return repository.loadOldestEvent(date).orderAsc("created").maxResults(qty).getResultList();
	}


}
