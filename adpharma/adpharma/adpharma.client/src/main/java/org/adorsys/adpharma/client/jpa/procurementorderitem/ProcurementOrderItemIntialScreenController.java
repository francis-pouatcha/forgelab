package org.adorsys.adpharma.client.jpa.procurementorderitem;

import org.adorsys.javafx.crud.extensions.InitialScreenController;

public class ProcurementOrderItemIntialScreenController extends InitialScreenController<ProcurementOrderItem>
{
   @Override
   public ProcurementOrderItem newEntity()
   {
      return new ProcurementOrderItem();
   }
}
