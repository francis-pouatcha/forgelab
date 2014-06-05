package org.adorsys.adpharma.client.jpa.salesordertype;

import java.util.ResourceBundle;

import javafx.util.StringConverter;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.locale.Bundle;

@Singleton
public class SalesOrderTypeConverter extends StringConverter<SalesOrderType>
{

   @Inject
   @Bundle({ SalesOrderType.class })
   private ResourceBundle bundle;

   @Override
   public SalesOrderType fromString(String name)
   {
      return SalesOrderType.valueOf(name);
   }

   @Override
   public String toString(SalesOrderType object)
   {
      if (object == null)
         return null;
      return bundle.getString("SalesOrderType_" + object.name()
            + "_description.title");
   }
}
