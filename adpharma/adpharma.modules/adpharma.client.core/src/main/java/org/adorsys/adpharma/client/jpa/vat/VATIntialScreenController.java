package org.adorsys.adpharma.client.jpa.vat;

import org.adorsys.javafx.crud.extensions.InitialScreenController;

public class VATIntialScreenController extends InitialScreenController<VAT>
{
   @Override
   public VAT newEntity()
   {
      return new VAT();
   }
}
