package org.adorsys.adpharma.server.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;
import org.adorsys.adpharma.server.jpa.Supplier;
import org.adorsys.adpharma.server.repo.SupplierRepository;

@Stateless
public class SupplierEJB
{

   @Inject
   private SupplierRepository repository;

   @Inject
   private ClearanceConfigMerger clearanceConfigMerger;

   @Inject
   private SalesMarginMerger salesMarginMerger;

   @Inject
   private PackagingModeMerger packagingModeMerger;

   @Inject
   private AgencyMerger agencyMerger;

   public Supplier create(Supplier entity)
   {
      return repository.save(attach(entity));
   }

   public Supplier deleteById(Long id)
   {
      Supplier entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public Supplier update(Supplier entity)
   {
      return repository.save(attach(entity));
   }

   public Supplier findById(Long id)
   {
      return repository.findBy(id);
   }

   public List<Supplier> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<Supplier> findBy(Supplier entity, int start, int max, SingularAttribute<Supplier, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(Supplier entity, SingularAttribute<Supplier, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<Supplier> findByLike(Supplier entity, int start, int max, SingularAttribute<Supplier, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(Supplier entity, SingularAttribute<Supplier, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private Supplier attach(Supplier entity)
   {
      if (entity == null)
         return null;

      // aggregated
      entity.setDefaultSalesMargin(salesMarginMerger.bindAggregated(entity.getDefaultSalesMargin()));

      // aggregated
      entity.setPackagingMode(packagingModeMerger.bindAggregated(entity.getPackagingMode()));

      // aggregated
      entity.setAgency(agencyMerger.bindAggregated(entity.getAgency()));

      // aggregated
      entity.setClearanceConfig(clearanceConfigMerger.bindAggregated(entity.getClearanceConfig()));

      return entity;
   }
}
