package org.adorsys.adpharma.client.jpa.section;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;
import org.adorsys.adpharma.client.jpa.section.Section;

public class SectionRemoveService extends Service<Section>
{

   @Inject
   private SectionService remoteService;

   private Section entity;

   public SectionRemoveService setEntity(Section entity)
   {
      this.entity = entity;
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
            return remoteService.deleteById(entity.getId());
         }
      };
   }
}
