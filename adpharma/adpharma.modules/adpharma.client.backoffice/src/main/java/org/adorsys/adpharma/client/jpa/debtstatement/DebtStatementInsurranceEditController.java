package org.adorsys.adpharma.client.jpa.debtstatement;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.events.EntityEditRequestedEvent;
import org.adorsys.javafx.crud.extensions.events.SelectedModelEvent;

@Singleton
public class DebtStatementInsurranceEditController extends DebtStatementInsurranceController
{

   @Inject
   DebtStatementEditView editView;

   public void handleNewModelEvent(@Observes @SelectedModelEvent DebtStatement model)
   {
      this.sourceEntity = model;
      activateButton(editView.getView().getDebtStatementInsurranceSelection(), editView.getView().getDebtStatementInsurranceForm());
      bind(editView.getView().getDebtStatementInsurranceSelection(), editView.getView().getDebtStatementInsurranceForm());
   }

   public void handleEditRequestEvent(
         @Observes @EntityEditRequestedEvent DebtStatement p)
   {
      loadAssociation();
   }

}
