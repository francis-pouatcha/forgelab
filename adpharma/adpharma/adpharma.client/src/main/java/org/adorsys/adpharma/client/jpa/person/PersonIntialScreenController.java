package org.adorsys.adpharma.client.jpa.person;

import org.adorsys.javafx.crud.extensions.InitialScreenController;

public class PersonIntialScreenController extends InitialScreenController<Person>
{
   @Override
   public Person newEntity()
   {
      return new Person();
   }
}
