package org.adorsys.adpharma.client.jpa.supplierinvoiceitem;

import java.util.ResourceBundle;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javaext.description.Description;
import org.adorsys.javafx.crud.extensions.DomainComponentController;
import org.adorsys.javafx.crud.extensions.DomainComponentRegistration;
import org.adorsys.javafx.crud.extensions.cdiextention.Eager;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.adpharma.client.jpa.supplierinvoiceitem.SupplierInvoiceItem;

/**
 * Mark this component eager to enable registration of this component with the component list.
 * 
 * @author francis
 *
 */
//@Eager
@Singleton
public class SupplierInvoiceItemRegistration extends DomainComponentRegistration
{

   @Inject
   @Bundle(SupplierInvoiceItem.class)
   private ResourceBundle resourceBundle;

   @Override
   protected Class<?> getComponentClass()
   {
      return SupplierInvoiceItem.class;
   }

   @Override
   protected Class<? extends DomainComponentController> getControllerClass()
   {
      return SupplierInvoiceItemController.class;
   }

   @Override
   protected String getComponentI18nName()
   {
      Description annotation = SupplierInvoiceItem.class.getAnnotation(Description.class);
      if (annotation != null)
         return resourceBundle.getString(annotation.value() + ".title");
      return SupplierInvoiceItem.class.getName();
   }

   @Override
   public String getComponentPermission()
   {
      return "org.adorsys.adpharma.server.jpa.SupplierInvoiceItem";
   }

}
