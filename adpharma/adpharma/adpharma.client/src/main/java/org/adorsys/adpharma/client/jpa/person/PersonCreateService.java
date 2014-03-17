package org.adorsys.adpharma.client.jpa.person;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;
import javax.inject.Singleton;
import org.adorsys.adpharma.client.jpa.person.Person;

@Singleton
public class PersonCreateService extends Service<Person>
{

   private Person model;

   @Inject
   private PersonService remoteService;

   public PersonCreateService setModel(Person model)
   {
      this.model = model;
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
            if (model == null)
               return null;
            return remoteService.create(model);
         }
      };
   }
}
