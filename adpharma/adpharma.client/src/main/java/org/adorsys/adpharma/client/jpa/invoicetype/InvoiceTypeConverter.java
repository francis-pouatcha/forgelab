package org.adorsys.adpharma.client.jpa.invoicetype;

import java.util.ResourceBundle;

import javafx.util.StringConverter;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.locale.Bundle;

@Singleton
public class InvoiceTypeConverter extends StringConverter<InvoiceType>
{

   @Inject
   @Bundle({ InvoiceType.class })
   private ResourceBundle bundle;

   @Override
   public InvoiceType fromString(String name)
   {
      return InvoiceType.valueOf(name);
   }

   @Override
   public String toString(InvoiceType object)
   {
      if (object == null)
         return null;
      return bundle.getString("InvoiceType_" + object.name()
            + "_description.title");
   }
}
