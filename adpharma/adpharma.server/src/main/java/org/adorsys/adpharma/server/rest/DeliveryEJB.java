package org.adorsys.adpharma.server.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;
import org.adorsys.adpharma.server.jpa.Delivery;
import org.adorsys.adpharma.server.repo.DeliveryRepository;
import java.util.Set;
import org.adorsys.adpharma.server.jpa.DeliveryItem;

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
