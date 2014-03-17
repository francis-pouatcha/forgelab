package org.adorsys.adpharma.client.jpa.prescriptionbook;

import java.util.Collections;
import java.util.List;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

public class PrescriptionBookSearchService extends Service<PrescriptionBookSearchResult>
{

   @Inject
   private PrescriptionBookService remoteService;

   private PrescriptionBookSearchInput searchInputs;

   public PrescriptionBookSearchService setSearchInputs(PrescriptionBookSearchInput searchInputs)
   {
      this.searchInputs = searchInputs;
      return this;
   }

   @Override
   protected Task<PrescriptionBookSearchResult> createTask()
   {
      return new Task<PrescriptionBookSearchResult>()
      {
         @Override
         protected PrescriptionBookSearchResult call() throws Exception
         {
            if (searchInputs == null)
               return null;
            return remoteService.findByLike(searchInputs);
         }
      };
   }
}
