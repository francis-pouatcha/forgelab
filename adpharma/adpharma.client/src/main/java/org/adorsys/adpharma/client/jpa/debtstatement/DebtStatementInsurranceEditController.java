package org.adorsys.adpharma.client.jpa.debtstatement;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.events.SelectedModelEvent;

@Singleton
public class DebtStatementInsurranceEditController extends DebtStatementInsurranceController
{

   @Inject
   DebtStatementEditView editView;

   @PostConstruct
   public void postConstruct()
   {
   }

   public void handleNewModelEvent(@Observes @SelectedModelEvent DebtStatement model)
   {
      this.sourceEntity = model;
      activateButton(editView.getView().getDebtStatementInsurranceSelection());
      bind(editView.getView().getDebtStatementInsurranceSelection());
   }
}
