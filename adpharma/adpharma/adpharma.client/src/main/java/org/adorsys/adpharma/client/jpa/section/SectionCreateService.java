package org.adorsys.adpharma.client.jpa.section;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;
import javax.inject.Singleton;
import org.adorsys.adpharma.client.jpa.section.Section;

@Singleton
public class SectionCreateService extends Service<Section>
{

   private Section model;

   @Inject
   private SectionService remoteService;

   public SectionCreateService setModel(Section model)
   {
      this.model = model;
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
            if (model == null)
               return null;
            return remoteService.create(model);
         }
      };
   }
}
