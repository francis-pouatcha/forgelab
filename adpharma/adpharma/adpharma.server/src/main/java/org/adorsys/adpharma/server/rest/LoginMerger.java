package org.adorsys.adpharma.server.rest;

import java.util.HashSet;
import java.util.Set;
import java.util.List;

import javax.inject.Inject;
import org.adorsys.adpharma.server.jpa.Login;
import org.adorsys.adpharma.server.repo.LoginRepository;

public class LoginMerger
{

   @Inject
   private LoginRepository repository;

   public Login bindComposed(Login entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return entity;
      return repository.save(entity);
   }

   public Login bindAggregated(Login entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return null;
      return repository.findBy(entity.getId());
   }

   public void bindComposed(final Set<Login> entities)
   {
      if (entities == null)
         return;
      HashSet<Login> oldCol = new HashSet<Login>(entities);
      entities.clear();
      for (Login entity : oldCol)
      {
         entity = bindComposed(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public void bindAggregated(final Set<Login> entities)
   {
      if (entities == null)
         return;
      HashSet<Login> oldCol = new HashSet<Login>(entities);
      entities.clear();
      for (Login entity : oldCol)
      {
         entity = bindAggregated(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public Login unbind(final Login entity, List<String> fieldList)
   {
      if (entity == null)
         return null;
      Login newEntity = new Login();
      newEntity.setId(entity.getId());
      newEntity.setVersion(entity.getVersion());
      MergerUtils.copyFields(entity, newEntity, fieldList);
      return newEntity;
   }

   public Set<Login> unbind(final Set<Login> entities, List<String> fieldList)
   {
      if (entities == null)
         return null;
      return new HashSet<Login>();
      //       HashSet<Login> cache = new HashSet<Login>(entities);
      //       entities.clear();
      //       for (Login entity : cache) {
      //  		entities.add(unbind(entity, fieldList));
      //       }
      //      return entities;
   }
}
