package org.adorsys.adpharma.client.jpa.hospital;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

public class HospitalSearchService extends Service<HospitalSearchResult>
{

   @Inject
   private HospitalService remoteService;

   private HospitalSearchInput searchInputs;

   public HospitalSearchService setSearchInputs(HospitalSearchInput searchInputs)
   {
      this.searchInputs = searchInputs;
      return this;
   }

   @Override
   protected Task<HospitalSearchResult> createTask()
   {
      return new Task<HospitalSearchResult>()
      {
         @Override
         protected HospitalSearchResult call() throws Exception
         {
            if (searchInputs == null)
               return null;
            return remoteService.findByLike(searchInputs);
         }
      };
   }
}
