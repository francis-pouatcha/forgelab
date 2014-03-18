package org.adorsys.adpharma.server.rest;

import java.util.HashSet;
import java.util.Set;
import java.util.List;

import javax.inject.Inject;
import org.adorsys.adpharma.server.jpa.LoginRoleNameAssoc;
import org.adorsys.adpharma.server.repo.LoginRoleNameAssocRepository;

public class LoginRoleNameAssocMerger
{

   @Inject
   private LoginRoleNameAssocRepository repository;

   public LoginRoleNameAssoc bindComposed(LoginRoleNameAssoc entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return entity;
      return repository.save(entity);
   }

   public LoginRoleNameAssoc bindAggregated(LoginRoleNameAssoc entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return null;
      return repository.findBy(entity.getId());
   }

   public void bindComposed(final Set<LoginRoleNameAssoc> entities)
   {
      if (entities == null)
         return;
      HashSet<LoginRoleNameAssoc> oldCol = new HashSet<LoginRoleNameAssoc>(entities);
      entities.clear();
      for (LoginRoleNameAssoc entity : oldCol)
      {
         entity = bindComposed(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public void bindAggregated(final Set<LoginRoleNameAssoc> entities)
   {
      if (entities == null)
         return;
      HashSet<LoginRoleNameAssoc> oldCol = new HashSet<LoginRoleNameAssoc>(entities);
      entities.clear();
      for (LoginRoleNameAssoc entity : oldCol)
      {
         entity = bindAggregated(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public LoginRoleNameAssoc unbind(final LoginRoleNameAssoc entity, List<String> fieldList)
   {
      if (entity == null)
         return null;
      LoginRoleNameAssoc newEntity = new LoginRoleNameAssoc();
      newEntity.setId(entity.getId());
      newEntity.setVersion(entity.getVersion());
      MergerUtils.copyFields(entity, newEntity, fieldList);
      return newEntity;
   }

   public Set<LoginRoleNameAssoc> unbind(final Set<LoginRoleNameAssoc> entities, List<String> fieldList)
   {
      if (entities == null)
         return null;
      return new HashSet<LoginRoleNameAssoc>();
      //       HashSet<LoginRoleNameAssoc> cache = new HashSet<LoginRoleNameAssoc>(entities);
      //       entities.clear();
      //       for (LoginRoleNameAssoc entity : cache) {
      //  		entities.add(unbind(entity, fieldList));
      //       }
      //      return entities;
   }
}
