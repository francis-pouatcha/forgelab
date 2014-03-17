package org.adorsys.adpharma.client.jpa.supplier;

import java.util.ResourceBundle;

import javax.inject.Inject;

import org.adorsys.javaext.description.Description;
import org.adorsys.javafx.crud.extensions.DomainComponentController;
import org.adorsys.javafx.crud.extensions.DomainComponentRegistration;
import org.adorsys.javafx.crud.extensions.cdiextention.Eager;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.adpharma.client.jpa.supplier.Supplier;

/**
 * Mark this component eager to enable registration of this component with the component list.
 * 
 * @author francis
 *
 */
@Eager
public class SupplierRegistration extends DomainComponentRegistration
{

   @Inject
   @Bundle(Supplier.class)
   private ResourceBundle resourceBundle;

   @Override
   protected Class<?> getComponentClass()
   {
      return Supplier.class;
   }

   @Override
   protected Class<? extends DomainComponentController> getControllerClass()
   {
      return SupplierController.class;
   }

   @Override
   protected String getComponentI18nName()
   {
      Description annotation = Supplier.class.getAnnotation(Description.class);
      if (annotation != null)
         return resourceBundle.getString(annotation.value() + ".title");
      return Supplier.class.getName();
   }
}
