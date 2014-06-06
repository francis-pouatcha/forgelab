package org.adorsys.adpharma.client.jpa.stockmovementterminal;

import java.util.ResourceBundle;

import javafx.util.StringConverter;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.locale.Bundle;

@Singleton
public class StockMovementTerminalConverter extends StringConverter<StockMovementTerminal>
{

   @Inject
   @Bundle({ StockMovementTerminal.class })
   private ResourceBundle bundle;

   @Override
   public StockMovementTerminal fromString(String name)
   {
      return StockMovementTerminal.valueOf(name);
   }

   @Override
   public String toString(StockMovementTerminal object)
   {
      if (object == null)
         return null;
      return bundle.getString("StockMovementTerminal_" + object.name()
            + "_description.title");
   }
}
