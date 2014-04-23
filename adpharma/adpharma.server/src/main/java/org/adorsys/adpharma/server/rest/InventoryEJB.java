package org.adorsys.adpharma.server.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;
import org.adorsys.adpharma.server.jpa.Inventory;
import org.adorsys.adpharma.server.repo.InventoryRepository;
import java.util.Set;
import org.adorsys.adpharma.server.jpa.InventoryItem;

@Stateless
public class InventoryEJB
{

   @Inject
   private InventoryRepository repository;

   @Inject
   private InventoryItemMerger inventoryItemMerger;

   @Inject
   private LoginMerger loginMerger;

   @Inject
   private AgencyMerger agencyMerger;

   public Inventory create(Inventory entity)
   {
      return repository.save(attach(entity));
   }

   public Inventory deleteById(Long id)
   {
      Inventory entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public Inventory update(Inventory entity)
   {
      return repository.save(attach(entity));
   }

   public Inventory findById(Long id)
   {
      return repository.findBy(id);
   }

   public List<Inventory> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<Inventory> findBy(Inventory entity, int start, int max, SingularAttribute<Inventory, ?>[] attributes)
   {
	   Inventory inventory = attach(entity);
      return repository.findBy(inventory, start, max, attributes);
   }

   public Long countBy(Inventory entity, SingularAttribute<Inventory, ?>[] attributes)
   {
	   Inventory inventory = attach(entity);
      return repository.count(inventory, attributes);
   }

   public List<Inventory> findByLike(Inventory entity, int start, int max, SingularAttribute<Inventory, ?>[] attributes)
   {
	   Inventory inventory = attach(entity);
      return repository.findByLike(inventory, start, max, attributes);
   }

   public Long countByLike(Inventory entity, SingularAttribute<Inventory, ?>[] attributes)
   {
	   Inventory inventory = attach(entity);
      return repository.countLike(inventory, attributes);
   }

   private Inventory attach(Inventory entity)
   {
      if (entity == null)
         return null;

      // aggregated
      entity.setRecordingUser(loginMerger.bindAggregated(entity.getRecordingUser()));

      // aggregated
      entity.setAgency(agencyMerger.bindAggregated(entity.getAgency()));

      // composed collections
      Set<InventoryItem> inventoryItems = entity.getInventoryItems();
      for (InventoryItem inventoryItem : inventoryItems)
      {
         inventoryItem.setInventory(entity);
      }

      return entity;
   }
}
