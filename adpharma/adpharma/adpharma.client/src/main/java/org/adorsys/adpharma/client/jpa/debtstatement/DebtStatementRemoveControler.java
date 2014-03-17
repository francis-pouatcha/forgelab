package org.adorsys.adpharma.client.jpa.debtstatement;

import java.util.ResourceBundle;

import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.adorsys.javafx.crud.extensions.events.EntityRemoveConfirmedEvent;
import org.adorsys.javafx.crud.extensions.events.EntityRemoveDoneEvent;
import org.adorsys.javafx.crud.extensions.events.EntityRemoveRequestEvent;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.login.ErrorDisplay;
import org.adorsys.javafx.crud.extensions.login.ServiceCallFailedEventHandler;
import org.adorsys.javafx.crud.extensions.view.ConfirmDialog;
import org.adorsys.javafx.crud.extensions.view.ErrorMessageDialog;
import org.apache.commons.lang3.StringUtils;
import org.adorsys.adpharma.client.jpa.debtstatement.DebtStatement;

public class DebtStatementRemoveControler
{

   @Inject
   @EntityRemoveDoneEvent
   private Event<DebtStatement> removedEvent;

   @Inject
   private DebtStatementRemoveService removeService;
   @Inject
   private ServiceCallFailedEventHandler removeServiceCallFailedEventHandler;

   @Inject
   @Bundle({ CrudKeys.class, DebtStatement.class })
   private ResourceBundle resourceBundle;

   @Inject
   private ConfirmDialog confirmDialog;

   private DebtStatement entity;

   @Inject
   private ErrorMessageDialog errorMessageDialog;

   public void handleRemoveRequest(
         @Observes @EntityRemoveRequestEvent DebtStatement entity)
   {
      this.entity = entity;
      confirmDialog.display();
   }

   public void handleRemoveOkEvent(
         @Observes @EntityRemoveConfirmedEvent DebtStatement entity)
   {
      removeService.setEntity(entity).start();
   }

   @PostConstruct
   public void postConstruct()
   {
      confirmDialog.setText(resourceBundle.getString("Entity_confirm_remove.title")
            + " "
            + resourceBundle.getString("DebtStatement_description.title"));
      confirmDialog.getOkButton().setOnAction(
            new EventHandler<ActionEvent>()
            {
               @Override
               public void handle(ActionEvent event)
               {
                  if (confirmDialog.hasDialog())
                  {
                     removeService.setEntity(entity).start();
                     confirmDialog.closeDialog();
                  }
               }
            });

      confirmDialog.getCancelButton().setOnAction(
            new EventHandler<ActionEvent>()
            {
               @Override
               public void handle(ActionEvent event)
               {
                  confirmDialog.closeDialog();
               }
            });

      removeService.setOnSucceeded(new EventHandler<WorkerStateEvent>()
      {
         @Override
         public void handle(WorkerStateEvent wse)
         {
            DebtStatementRemoveService service = (DebtStatementRemoveService) wse
                  .getSource();
            DebtStatement p = service.getValue();
            wse.consume();
            service.reset();
            removedEvent.fire(p);
         }
      });
      removeServiceCallFailedEventHandler.setErrorDisplay(new ErrorDisplay()
      {
         @Override
         protected void showError(Throwable exception)
         {
            String message = exception.getMessage();
            errorMessageDialog.getTitleText().setText(
                  resourceBundle.getString("Entity_remove_error.title"));
            if (!StringUtils.isBlank(message))
               errorMessageDialog.getDetailText().setText(message);
            errorMessageDialog.display();
         }
      });
      removeService.setOnFailed(removeServiceCallFailedEventHandler);
      errorMessageDialog.getOkButton().setOnAction(
            new EventHandler<ActionEvent>()
            {
               @Override
               public void handle(ActionEvent event)
               {
                  errorMessageDialog.closeDialog();
               }
            });
   }
}
