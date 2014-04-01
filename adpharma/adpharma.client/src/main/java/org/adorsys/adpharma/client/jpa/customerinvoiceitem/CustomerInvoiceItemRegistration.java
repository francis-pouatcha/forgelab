package org.adorsys.adpharma.client.jpa.customerinvoiceitem;

import java.util.ResourceBundle;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javaext.description.Description;
import org.adorsys.javafx.crud.extensions.DomainComponentController;
import org.adorsys.javafx.crud.extensions.DomainComponentRegistration;
import org.adorsys.javafx.crud.extensions.cdiextention.Eager;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.adpharma.client.jpa.customerinvoiceitem.CustomerInvoiceItem;

/**
 * Mark this component eager to enable registration of this component with the component list.
 * 
 * @author francis
 *
 */
@Eager
@Singleton
public class CustomerInvoiceItemRegistration extends DomainComponentRegistration
{

   @Inject
   @Bundle(CustomerInvoiceItem.class)
   private ResourceBundle resourceBundle;

   @Override
   protected Class<?> getComponentClass()
   {
      return CustomerInvoiceItem.class;
   }

   @Override
   protected Class<? extends DomainComponentController> getControllerClass()
   {
      return CustomerInvoiceItemController.class;
   }

   @Override
   protected String getComponentI18nName()
   {
      Description annotation = CustomerInvoiceItem.class.getAnnotation(Description.class);
      if (annotation != null)
         return resourceBundle.getString(annotation.value() + ".title");
      return CustomerInvoiceItem.class.getName();
   }

   @Override
   public String getComponentPermission()
   {
      return "org.adorsys.adpharma.server.jpa.CustomerInvoiceItem";
   }

}
