package org.adorsys.adpharma.client.jpa.person;

import java.util.ResourceBundle;

import javax.inject.Inject;

import org.adorsys.javaext.description.Description;
import org.adorsys.javafx.crud.extensions.DomainComponentController;
import org.adorsys.javafx.crud.extensions.DomainComponentRegistration;
import org.adorsys.javafx.crud.extensions.cdiextention.Eager;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.adpharma.client.jpa.person.Person;

/**
 * Mark this component eager to enable registration of this component with the component list.
 * 
 * @author francis
 *
 */
@Eager
public class PersonRegistration extends DomainComponentRegistration
{

   @Inject
   @Bundle(Person.class)
   private ResourceBundle resourceBundle;

   @Override
   protected Class<?> getComponentClass()
   {
      return Person.class;
   }

   @Override
   protected Class<? extends DomainComponentController> getControllerClass()
   {
      return PersonController.class;
   }

   @Override
   protected String getComponentI18nName()
   {
      Description annotation = Person.class.getAnnotation(Description.class);
      if (annotation != null)
         return resourceBundle.getString(annotation.value() + ".title");
      return Person.class.getName();
   }
}
