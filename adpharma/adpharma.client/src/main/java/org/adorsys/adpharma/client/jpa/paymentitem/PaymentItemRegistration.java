package org.adorsys.adpharma.client.jpa.paymentitem;

import java.util.ResourceBundle;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javaext.description.Description;
import org.adorsys.javafx.crud.extensions.DomainComponentController;
import org.adorsys.javafx.crud.extensions.DomainComponentRegistration;
import org.adorsys.javafx.crud.extensions.cdiextention.Eager;
import org.adorsys.javafx.crud.extensions.locale.Bundle;

/**
 * Mark this component eager to enable registration of this component with the component list.
 * 
 * @author francis
 *
 */
@Eager
@Singleton
public class PaymentItemRegistration extends DomainComponentRegistration
{

   @Inject
   @Bundle(PaymentItem.class)
   private ResourceBundle resourceBundle;

   @Override
   protected Class<?> getComponentClass()
   {
      return PaymentItem.class;
   }

   @Override
   protected Class<? extends DomainComponentController> getControllerClass()
   {
      return PaymentItemController.class;
   }

   @Override
   protected String getComponentI18nName()
   {
      Description annotation = PaymentItem.class.getAnnotation(Description.class);
      if (annotation != null)
         return resourceBundle.getString(annotation.value() + ".title");
      return PaymentItem.class.getName();
   }

   @Override
   public String getComponentPermission()
   {
      return "org.adorsys.adpharma.server.jpa.PaymentItem";
   }

}
