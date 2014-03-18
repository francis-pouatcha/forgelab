package org.adorsys.adpharma.client.jpa.paymentmode;

import java.util.ResourceBundle;

import javafx.util.StringConverter;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.locale.Bundle;

@Singleton
public class PaymentModeConverter extends StringConverter<PaymentMode>
{

   @Inject
   @Bundle({ PaymentMode.class })
   private ResourceBundle bundle;

   @Override
   public PaymentMode fromString(String name)
   {
      return PaymentMode.valueOf(name);
   }

   @Override
   public String toString(PaymentMode object)
   {
      return bundle.getString("PaymentMode_" + object.name()
            + "_description.title");
   }
}
