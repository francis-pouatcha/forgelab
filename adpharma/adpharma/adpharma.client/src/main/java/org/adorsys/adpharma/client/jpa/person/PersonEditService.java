package org.adorsys.adpharma.client.jpa.person;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;
import org.adorsys.adpharma.client.jpa.person.Person;

public class PersonEditService extends Service<Person>
{

   @Inject
   private PersonService remoteService;

   private Person entity;

   public PersonEditService setPerson(Person entity)
   {
      this.entity = entity;
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
            if (entity == null)
               return null;
            return remoteService.update(entity);
         }
      };
   }
}
