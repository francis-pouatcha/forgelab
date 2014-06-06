package org.adorsys.adpharma.client.jpa.debtstatement;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.events.CreateModelEvent;

@Singleton
public class DebtStatementInsurranceCreateController extends DebtStatementInsurranceController
{

   @Inject
   DebtStatementCreateView createView;

   @PostConstruct
   public void postConstruct()
   {
   }

   public void handleNewModelEvent(@Observes @CreateModelEvent DebtStatement model)
   {
      this.sourceEntity = model;
      activateButton(createView.getView().getDebtStatementInsurranceSelection(), createView.getView().getDebtStatementInsurranceForm());
      bind(createView.getView().getDebtStatementInsurranceSelection(), createView.getView().getDebtStatementInsurranceForm());
   }
}
