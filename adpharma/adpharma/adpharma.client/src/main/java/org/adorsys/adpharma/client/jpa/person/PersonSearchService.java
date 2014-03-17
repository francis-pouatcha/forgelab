package org.adorsys.adpharma.client.jpa.person;

import java.util.Collections;
import java.util.List;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

public class PersonSearchService extends Service<PersonSearchResult>
{

   @Inject
   private PersonService remoteService;

   private PersonSearchInput searchInputs;

   public PersonSearchService setSearchInputs(PersonSearchInput searchInputs)
   {
      this.searchInputs = searchInputs;
      return this;
   }

   @Override
   protected Task<PersonSearchResult> createTask()
   {
      return new Task<PersonSearchResult>()
      {
         @Override
         protected PersonSearchResult call() throws Exception
         {
            if (searchInputs == null)
               return null;
            return remoteService.findByLike(searchInputs);
         }
      };
   }
}
