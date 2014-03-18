package org.adorsys.adpharma.client.jpa.login;

import java.util.ResourceBundle;

import javax.inject.Inject;

import org.adorsys.javaext.description.Description;
import org.adorsys.javafx.crud.extensions.DomainComponentController;
import org.adorsys.javafx.crud.extensions.DomainComponentRegistration;
import org.adorsys.javafx.crud.extensions.cdiextention.Eager;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.adpharma.client.jpa.login.Login;

/**
 * Mark this component eager to enable registration of this component with the component list.
 * 
 * @author francis
 *
 */
@Eager
public class LoginRegistration extends DomainComponentRegistration
{

   @Inject
   @Bundle(Login.class)
   private ResourceBundle resourceBundle;

   @Override
   protected Class<?> getComponentClass()
   {
      return Login.class;
   }

   @Override
   protected Class<? extends DomainComponentController> getControllerClass()
   {
      return LoginController.class;
   }

   @Override
   protected String getComponentI18nName()
   {
      Description annotation = Login.class.getAnnotation(Description.class);
      if (annotation != null)
         return resourceBundle.getString(annotation.value() + ".title");
      return Login.class.getName();
   }
}
