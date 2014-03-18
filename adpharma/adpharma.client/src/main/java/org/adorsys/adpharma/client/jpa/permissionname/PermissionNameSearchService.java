package org.adorsys.adpharma.client.jpa.permissionname;

import java.util.Collections;
import java.util.List;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

public class PermissionNameSearchService extends Service<PermissionNameSearchResult>
{

   @Inject
   private PermissionNameService remoteService;

   private PermissionNameSearchInput searchInputs;

   public PermissionNameSearchService setSearchInputs(PermissionNameSearchInput searchInputs)
   {
      this.searchInputs = searchInputs;
      return this;
   }

   @Override
   protected Task<PermissionNameSearchResult> createTask()
   {
      return new Task<PermissionNameSearchResult>()
      {
         @Override
         protected PermissionNameSearchResult call() throws Exception
         {
            if (searchInputs == null)
               return null;
            return remoteService.findByLike(searchInputs);
         }
      };
   }
}
