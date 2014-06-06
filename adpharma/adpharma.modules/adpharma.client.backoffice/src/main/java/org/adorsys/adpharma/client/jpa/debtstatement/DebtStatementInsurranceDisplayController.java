package org.adorsys.adpharma.client.jpa.debtstatement;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.events.EntitySelectionEvent;
import org.adorsys.javafx.crud.extensions.events.SelectedModelEvent;

@Singleton
public class DebtStatementInsurranceDisplayController extends DebtStatementInsurranceController
{

   @Inject
   private DebtStatementDisplayView displayView;

   @PostConstruct
   public void postConstruct()
   {
   }

   /**
    * This is the only time where the bind method is called on this object.
    * @param model
    */
   public void handleNewModelEvent(@Observes @SelectedModelEvent DebtStatement model)
   {
      this.sourceEntity = model;
      disableButton(displayView.getView().getDebtStatementInsurranceSelection(), displayView.getView().getDebtStatementInsurranceForm());
      bind(displayView.getView().getDebtStatementInsurranceSelection(), displayView.getView().getDebtStatementInsurranceForm());
   }

   public void handleSelectionEvent(@Observes @EntitySelectionEvent DebtStatement selectedEntity)
   {
      loadAssociation();
   }
}
