package org.adorsys.adpharma.client.jpa.procurementorder;

import org.adorsys.javafx.crud.extensions.InitialScreenController;

public class ProcurementOrderIntialScreenController extends InitialScreenController<ProcurementOrder>
{
   @Override
   public ProcurementOrder newEntity()
   {
      return new ProcurementOrder();
   }
}
