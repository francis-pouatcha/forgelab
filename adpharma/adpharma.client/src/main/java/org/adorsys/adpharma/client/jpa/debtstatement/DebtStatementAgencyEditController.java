package org.adorsys.adpharma.client.jpa.debtstatement;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.events.SelectedModelEvent;

@Singleton
public class DebtStatementAgencyEditController extends DebtStatementAgencyController
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
      activateButton(editView.getView().getDebtStatementAgencySelection());
      bind(editView.getView().getDebtStatementAgencySelection(), editView.getView().getDebtStatementAgencyForm());
   }
}
