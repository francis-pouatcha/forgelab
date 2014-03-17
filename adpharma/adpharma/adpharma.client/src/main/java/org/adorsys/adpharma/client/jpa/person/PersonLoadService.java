package org.adorsys.adpharma.client.jpa.person;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

public class PersonLoadService extends Service<Person>
{

   @Inject
   private PersonService remoteService;

   private Long id;

   public PersonLoadService setId(Long id)
   {
      this.id = id;
      return this;
   }

   @Override
   protected Task<Person> createTask()
   {
      return new Task<Person>()
      {
         @Override
         protected Person call() throws Exception
         {
            if (id == null)
               return null;
            return remoteService.findById(id);
         }
      };
   }
}
