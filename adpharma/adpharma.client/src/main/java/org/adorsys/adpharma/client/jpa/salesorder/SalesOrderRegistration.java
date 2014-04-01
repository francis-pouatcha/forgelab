package org.adorsys.adpharma.client.jpa.salesorder;

import java.util.ResourceBundle;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javaext.description.Description;
import org.adorsys.javafx.crud.extensions.DomainComponentController;
import org.adorsys.javafx.crud.extensions.DomainComponentRegistration;
import org.adorsys.javafx.crud.extensions.cdiextention.Eager;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.adpharma.client.jpa.salesorder.SalesOrder;

/**
 * Mark this component eager to enable registration of this component with the component list.
 * 
 * @author francis
 *
 */
@Eager
@Singleton
public class SalesOrderRegistration extends DomainComponentRegistration
{

   @Inject
   @Bundle(SalesOrder.class)
   private ResourceBundle resourceBundle;

   @Override
   protected Class<?> getComponentClass()
   {
      return SalesOrder.class;
   }

   @Override
   protected Class<? extends DomainComponentController> getControllerClass()
   {
      return SalesOrderController.class;
   }

   @Override
   protected String getComponentI18nName()
   {
      Description annotation = SalesOrder.class.getAnnotation(Description.class);
      if (annotation != null)
         return resourceBundle.getString(annotation.value() + ".title");
      return SalesOrder.class.getName();
   }

   @Override
   public String getComponentPermission()
   {
      return "org.adorsys.adpharma.server.jpa.SalesOrder";
   }

}
