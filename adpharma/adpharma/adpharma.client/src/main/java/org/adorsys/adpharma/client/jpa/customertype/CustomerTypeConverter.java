package org.adorsys.adpharma.client.jpa.customertype;

import java.util.ResourceBundle;

import javafx.util.StringConverter;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.locale.Bundle;

@Singleton
public class CustomerTypeConverter extends StringConverter<CustomerType>
{

   @Inject
   @Bundle({ CustomerType.class })
   private ResourceBundle bundle;

   @Override
   public CustomerType fromString(String name)
   {
      return CustomerType.valueOf(name);
   }

   @Override
   public String toString(CustomerType object)
   {
      return bundle.getString("CustomerType_" + object.name()
            + "_description.title");
   }
}
