package org.adorsys.adpharma.client.jpa.section;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

public class SectionEditService extends Service<Section>
{

   @Inject
   private SectionService remoteService;

   private Section entity;

   public SectionEditService setSection(Section entity)
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
            if (entity == null)
               return null;
            return remoteService.update(entity);
         }
      };
   }
}
