package org.adorsys.adpharma.server.rest;

import java.util.HashSet;
import java.util.Set;
import java.util.List;

import javax.inject.Inject;
import org.adorsys.adpharma.server.jpa.Section;
import org.adorsys.adpharma.server.repo.SectionRepository;

public class SectionMerger
{

   @Inject
   private SectionRepository repository;

   public Section bindComposed(Section entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return entity;
      return repository.save(entity);
   }

   public Section bindAggregated(Section entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return null;
      return repository.findBy(entity.getId());
   }

   public void bindComposed(final Set<Section> entities)
   {
      if (entities == null)
         return;
      HashSet<Section> oldCol = new HashSet<Section>(entities);
      entities.clear();
      for (Section entity : oldCol)
      {
         entity = bindComposed(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public void bindAggregated(final Set<Section> entities)
   {
      if (entities == null)
         return;
      HashSet<Section> oldCol = new HashSet<Section>(entities);
      entities.clear();
      for (Section entity : oldCol)
      {
         entity = bindAggregated(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public Section unbind(final Section entity, List<String> fieldList)
   {
      if (entity == null)
         return null;
      Section newEntity = new Section();
      newEntity.setId(entity.getId());
      newEntity.setVersion(entity.getVersion());
      MergerUtils.copyFields(entity, newEntity, fieldList);
      return entity;
   }

   public Set<Section> unbind(final Set<Section> entities, List<String> fieldList)
   {
      if (entities == null)
         return null;
      return new HashSet<Section>();
      //       HashSet<Section> cache = new HashSet<Section>(entities);
      //       entities.clear();
      //       for (Section entity : cache) {
      //  		entities.add(unbind(entity, fieldList));
      //       }
      //      return entities;
   }
}
