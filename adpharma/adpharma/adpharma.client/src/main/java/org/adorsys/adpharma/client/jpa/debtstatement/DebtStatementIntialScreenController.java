package org.adorsys.adpharma.client.jpa.debtstatement;

import org.adorsys.javafx.crud.extensions.InitialScreenController;

public class DebtStatementIntialScreenController extends InitialScreenController<DebtStatement>
{
   @Override
   public DebtStatement newEntity()
   {
      return new DebtStatement();
   }
}
