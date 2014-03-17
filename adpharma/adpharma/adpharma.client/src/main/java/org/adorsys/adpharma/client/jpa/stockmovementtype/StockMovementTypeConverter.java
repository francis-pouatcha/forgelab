package org.adorsys.adpharma.client.jpa.stockmovementtype;

import java.util.ResourceBundle;

import javafx.util.StringConverter;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.locale.Bundle;

@Singleton
public class StockMovementTypeConverter extends StringConverter<StockMovementType>
{

   @Inject
   @Bundle({ StockMovementType.class })
   private ResourceBundle bundle;

   @Override
   public StockMovementType fromString(String name)
   {
      return StockMovementType.valueOf(name);
   }

   @Override
   public String toString(StockMovementType object)
   {
      return bundle.getString("StockMovementType_" + object.name()
            + "_description.title");
   }
}
