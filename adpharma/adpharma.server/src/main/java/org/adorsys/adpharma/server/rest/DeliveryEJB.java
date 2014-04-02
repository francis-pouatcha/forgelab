package org.adorsys.adpharma.server.rest;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adpharma.server.events.DocumentClosedDoneEvent;
import org.adorsys.adpharma.server.jpa.Article;
import org.adorsys.adpharma.server.jpa.Delivery;
import org.adorsys.adpharma.server.jpa.DocumentProcessingState;
import org.adorsys.adpharma.server.jpa.Login;
import org.adorsys.adpharma.server.repo.DeliveryRepository;
import org.adorsys.adpharma.server.security.SecurityUtil;

import java.util.Set;

import org.adorsys.adpharma.server.jpa.DeliveryItem;
import org.apache.commons.lang3.RandomStringUtils;

@Stateless
public class DeliveryEJB
{

   @Inject
   private DeliveryRepository repository;

   @Inject
   private LoginMerger loginMerger;

   @Inject
   private SupplierMerger supplierMerger;

   @Inject
   private VATMerger vATMerger;

   @Inject
   private CurrencyMerger currencyMerger;

   @Inject
   private DeliveryItemMerger deliveryItemMerger;

   @Inject
   private AgencyMerger agencyMerger;


	@Inject
	private DeliveryItemEJB deliveryItemEJB;

	@Inject
	private ArticleEJB articleEJB;

	@EJB
	private SecurityUtil securityUtil;
  
	@Inject
	@DocumentClosedDoneEvent
	private Event<Delivery> deliveryClosedDoneEvent;
   
   public Delivery create(Delivery entity)
   {
      return repository.save(attach(entity));
   }

   public Delivery deleteById(Long id)
   {
      Delivery entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public Delivery update(Delivery entity)
   {
      return repository.save(attach(entity));
   }
   	
	public Delivery saveAndClose(Delivery delivery) {
		Login creatingUser = securityUtil.getConnectedUser();
		Date creationDate = new Date();
		Set<DeliveryItem> deliveryItems = delivery.getDeliveryItems();
		for (DeliveryItem deliveryItem : deliveryItems) {
			String internalPic = new SimpleDateFormat("DDMMYYHH").format(creationDate) + RandomStringUtils.randomNumeric(5);
			deliveryItem.setInternalPic(internalPic);
			deliveryItem.setCreatingUser(creatingUser);
			deliveryItem = deliveryItemEJB.update(deliveryItem);
			Article article = deliveryItem.getArticle();
			article.handleStockEntry(deliveryItem);
			articleEJB.update(article);
		}
		delivery.setDeliveryProcessingState(DocumentProcessingState.CLOSED);
		Delivery closedDelivery = update(delivery);
		deliveryClosedDoneEvent.fire(closedDelivery);
		return closedDelivery;
	}
   

   public Delivery findById(Long id)
   {
      return repository.findBy(id);
   }

   public List<Delivery> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<Delivery> findBy(Delivery entity, int start, int max, SingularAttribute<Delivery, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(Delivery entity, SingularAttribute<Delivery, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<Delivery> findByLike(Delivery entity, int start, int max, SingularAttribute<Delivery, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(Delivery entity, SingularAttribute<Delivery, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private Delivery attach(Delivery entity)
   {
      if (entity == null)
         return null;

      // aggregated
      entity.setCreatingUser(loginMerger.bindAggregated(entity.getCreatingUser()));

      // aggregated
      entity.setSupplier(supplierMerger.bindAggregated(entity.getSupplier()));

      // aggregated
      entity.setVat(vATMerger.bindAggregated(entity.getVat()));

      // aggregated
      entity.setCurrency(currencyMerger.bindAggregated(entity.getCurrency()));

      // aggregated
      entity.setReceivingAgency(agencyMerger.bindAggregated(entity.getReceivingAgency()));

      // composed collections
      Set<DeliveryItem> deliveryItems = entity.getDeliveryItems();
      for (DeliveryItem deliveryItem : deliveryItems)
      {
         deliveryItem.setDelivery(entity);
      }

      return entity;
   }
}
