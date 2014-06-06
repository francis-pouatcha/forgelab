package org.adorsys.adpharma.client.jpa.currency;

import org.adorsys.javafx.crud.extensions.InitialScreenController;

public class CurrencyIntialScreenController extends InitialScreenController<Currency>
{
   @Override
   public Currency newEntity()
   {
      return new Currency();
   }
}
