package org.adorsys.adpharma.client.jpa.procurementorderitem;

import java.util.ResourceBundle;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javaext.description.Description;
import org.adorsys.javafx.crud.extensions.DomainComponentController;
import org.adorsys.javafx.crud.extensions.DomainComponentRegistration;
import org.adorsys.javafx.crud.extensions.locale.Bundle;

/**
 * Mark this component eager to enable registration of this component with the component list.
 * 
 * @author francis
 *
 */
//@Eager
@Singleton
public class ProcurementOrderItemRegistration extends DomainComponentRegistration
{

   @Inject
   @Bundle(ProcurementOrderItem.class)
   private ResourceBundle resourceBundle;

   @Override
   protected Class<?> getComponentClass()
   {
      return ProcurementOrderItem.class;
   }

   @Override
   protected Class<? extends DomainComponentController> getControllerClass()
   {
      return ProcurementOrderItemController.class;
   }

   @Override
   protected String getComponentI18nName()
   {
      Description annotation = ProcurementOrderItem.class.getAnnotation(Description.class);
      if (annotation != null)
         return resourceBundle.getString(annotation.value() + ".title");
      return ProcurementOrderItem.class.getName();
   }

   @Override
   public String getComponentPermission()
   {
      return "org.adorsys.adpharma.server.jpa.ProcurementOrderItem";
   }

}
