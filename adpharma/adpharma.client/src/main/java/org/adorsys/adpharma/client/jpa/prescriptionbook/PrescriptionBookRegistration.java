package org.adorsys.adpharma.client.jpa.prescriptionbook;

import java.util.ResourceBundle;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javaext.description.Description;
import org.adorsys.javafx.crud.extensions.DomainComponentController;
import org.adorsys.javafx.crud.extensions.DomainComponentRegistration;
import org.adorsys.javafx.crud.extensions.cdiextention.Eager;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.adpharma.client.jpa.prescriptionbook.PrescriptionBook;

/**
 * Mark this component eager to enable registration of this component with the component list.
 * 
 * @author francis
 *
 */
@Eager
@Singleton
public class PrescriptionBookRegistration extends DomainComponentRegistration
{

   @Inject
   @Bundle(PrescriptionBook.class)
   private ResourceBundle resourceBundle;

   @Override
   protected Class<?> getComponentClass()
   {
      return PrescriptionBook.class;
   }

   @Override
   protected Class<? extends DomainComponentController> getControllerClass()
   {
      return PrescriptionBookController.class;
   }

   @Override
   protected String getComponentI18nName()
   {
      Description annotation = PrescriptionBook.class.getAnnotation(Description.class);
      if (annotation != null)
         return resourceBundle.getString(annotation.value() + ".title");
      return PrescriptionBook.class.getName();
   }

   @Override
   public String getComponentPermission()
   {
      return "org.adorsys.adpharma.server.jpa.PrescriptionBook";
   }

}
