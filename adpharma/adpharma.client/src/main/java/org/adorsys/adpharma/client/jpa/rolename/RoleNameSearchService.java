package org.adorsys.adpharma.client.jpa.rolename;

import java.util.Collections;
import java.util.List;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

public class RoleNameSearchService extends Service<RoleNameSearchResult>
{

   @Inject
   private RoleNameService remoteService;

   private RoleNameSearchInput searchInputs;

   public RoleNameSearchService setSearchInputs(RoleNameSearchInput searchInputs)
   {
      this.searchInputs = searchInputs;
      return this;
   }

   @Override
   protected Task<RoleNameSearchResult> createTask()
   {
      return new Task<RoleNameSearchResult>()
      {
         @Override
         protected RoleNameSearchResult call() throws Exception
         {
            if (searchInputs == null)
               return null;
            return remoteService.findByLike(searchInputs);
         }
      };
   }
}
