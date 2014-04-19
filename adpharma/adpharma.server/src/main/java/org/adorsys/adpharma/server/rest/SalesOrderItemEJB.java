package org.adorsys.adpharma.server.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adpharma.server.events.DocumentCreatedEvent;
import org.adorsys.adpharma.server.events.DocumentDeletedEvent;
import org.adorsys.adpharma.server.events.DocumentProcessedEvent;
import org.adorsys.adpharma.server.jpa.SalesOrderItem;
import org.adorsys.adpharma.server.repo.SalesOrderItemRepository;

@Stateless
public class SalesOrderItemEJB
{

   @Inject
   private SalesOrderItemRepository repository;

   @Inject
   private ArticleMerger articleMerger;

   @Inject
   private VATMerger vATMerger;

   @Inject
   private SalesOrderMerger salesOrderMerger;

	@Inject
	@DocumentProcessedEvent
	private Event<SalesOrderItem> salesOrderItemProcessedEvent;
   
	@Inject
	@DocumentDeletedEvent
	private Event<SalesOrderItem> salesOrderItemDeletedEvent;

	@Inject
	@DocumentCreatedEvent
	private Event<SalesOrderItem> salesOrderItemCreatedEvent;

	public SalesOrderItem create(SalesOrderItem entity)
    {
		entity.updateTotalSalesPrice();
      entity = repository.save(attach(entity));
      salesOrderItemCreatedEvent.fire(entity);
      return entity;
   }

   public SalesOrderItem deleteById(Long id)
   {
      SalesOrderItem entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
         salesOrderItemDeletedEvent.fire(entity);
      }
      return entity;
   }

   public SalesOrderItem update(SalesOrderItem entity)
   {
	  entity.updateTotalSalesPrice();
      entity = repository.save(attach(entity));
      salesOrderItemProcessedEvent.fire(entity);
      return entity;
   }

   public SalesOrderItem findById(Long id)
   {
      return repository.findBy(id);
   }

   public List<SalesOrderItem> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<SalesOrderItem> findBy(SalesOrderItem entity, int start, int max, SingularAttribute<SalesOrderItem, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(SalesOrderItem entity, SingularAttribute<SalesOrderItem, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<SalesOrderItem> findByLike(SalesOrderItem entity, int start, int max, SingularAttribute<SalesOrderItem, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(SalesOrderItem entity, SingularAttribute<SalesOrderItem, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private SalesOrderItem attach(SalesOrderItem entity)
   {
      if (entity == null)
         return null;

      // composed

      // aggregated
      entity.setArticle(articleMerger.bindAggregated(entity.getArticle()));

      // aggregated
      entity.setVat(vATMerger.bindAggregated(entity.getVat()));

      return entity;
   }
}
