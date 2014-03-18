package org.adorsys.adpharma.client.jpa.login;

import java.util.Collections;
import java.util.List;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

public class LoginSearchService extends Service<LoginSearchResult>
{

   @Inject
   private LoginService remoteService;

   private LoginSearchInput searchInputs;

   public LoginSearchService setSearchInputs(LoginSearchInput searchInputs)
   {
      this.searchInputs = searchInputs;
      return this;
   }

   @Override
   protected Task<LoginSearchResult> createTask()
   {
      return new Task<LoginSearchResult>()
      {
         @Override
         protected LoginSearchResult call() throws Exception
         {
            if (searchInputs == null)
               return null;
            return remoteService.findByLike(searchInputs);
         }
      };
   }
}
