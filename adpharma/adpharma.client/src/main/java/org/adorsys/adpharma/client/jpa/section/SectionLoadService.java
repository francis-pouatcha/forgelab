package org.adorsys.adpharma.client.jpa.section;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

public class SectionLoadService extends Service<Section>
{

   @Inject
   private SectionService remoteService;

   private Long id;

   public SectionLoadService setId(Long id)
   {
      this.id = id;
      return this;
   }

   @Override
   protected Task<Section> createTask()
   {
      return new Task<Section>()
      {
         @Override
         protected Section call() throws Exception
         {
            if (id == null)
               return null;
            return remoteService.findById(id);
         }
      };
   }
}
