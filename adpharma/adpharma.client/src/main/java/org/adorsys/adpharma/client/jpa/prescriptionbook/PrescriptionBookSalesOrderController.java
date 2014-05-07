package org.adorsys.adpharma.client.jpa.prescriptionbook;

import java.util.ResourceBundle;
import java.util.UUID;

import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.adorsys.adpharma.client.jpa.salesorder.SalesOrder;
import org.adorsys.adpharma.client.jpa.salesorder.SalesOrderLoadService;
import org.adorsys.javafx.crud.extensions.events.AssocSelectionRequestEvent;
import org.adorsys.javafx.crud.extensions.events.AssocSelectionResponseEvent;
import org.adorsys.javafx.crud.extensions.events.ComponentSelectionRequestData;
import org.adorsys.javafx.crud.extensions.events.ComponentSelectionRequestEvent;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.login.ErrorDisplay;
import org.adorsys.javafx.crud.extensions.login.ServiceCallFailedEventHandler;
import org.adorsys.javafx.crud.extensions.view.ErrorMessageDialog;
import org.apache.commons.lang3.StringUtils;

public abstract class PrescriptionBookSalesOrderController
{

   protected PrescriptionBook sourceEntity;

   @Inject
   private SalesOrderLoadService loadService;
   @Inject
   private ServiceCallFailedEventHandler loadServiceCallFailedEventHandler;

   @Inject
   private ErrorMessageDialog loadErrorMessageDialog;

   @Inject
   @AssocSelectionRequestEvent
   private Event<PrescriptionBookSalesOrderSelectionEventData> selectionRequestEvent;

   private PrescriptionBookSalesOrderSelectionEventData pendingSelectionRequest;

   @Inject
   @ComponentSelectionRequestEvent
   private Event<ComponentSelectionRequestData> componentSelectionRequestEvent;

   @Inject
   @Bundle(CrudKeys.class)
   private ResourceBundle resourceBundle;

   protected void disableButton(final PrescriptionBookSalesOrderSelection selection, final PrescriptionBookSalesOrderForm form)
   {
      selection.getSelectButton().setDisable(true);
   }

   protected void activateButton(final PrescriptionBookSalesOrderSelection selection, final PrescriptionBookSalesOrderForm form)
   {
   }

   protected void bind(final PrescriptionBookSalesOrderSelection selection, final PrescriptionBookSalesOrderForm form)
   {
      selection.getSelectButton().setOnAction(
            new EventHandler<ActionEvent>()
            {
               @Override
               public void handle(ActionEvent event)
               {
                  pendingSelectionRequest = new PrescriptionBookSalesOrderSelectionEventData(
                        UUID.randomUUID().toString(), sourceEntity, null);
                  selectionRequestEvent.fire(pendingSelectionRequest);
               }
            });

      // Handle edit canceld, reloading entity
      loadService.setOnSucceeded(new EventHandler<WorkerStateEvent>()
      {
         @Override
         public void handle(WorkerStateEvent event)
         {
            SalesOrderLoadService s = (SalesOrderLoadService) event.getSource();
            SalesOrder entity = s.getValue();
            event.consume();
            s.reset();
            sourceEntity.setSalesOrder(new PrescriptionBookSalesOrder(entity));
         }
      });
      loadServiceCallFailedEventHandler.setErrorDisplay(new ErrorDisplay()
      {
         @Override
         protected void showError(Throwable exception)
         {
            String message = exception.getMessage();
            loadErrorMessageDialog.getTitleText().setText(
                  resourceBundle.getString("Entity_load_error.title"));
            if (!StringUtils.isBlank(message))
               loadErrorMessageDialog.getDetailText().setText(message);
            loadErrorMessageDialog.display();
         }
      });
      loadService.setOnFailed(loadServiceCallFailedEventHandler);
      loadErrorMessageDialog.getOkButton().setOnAction(
            new EventHandler<ActionEvent>()
            {
               @Override
               public void handle(ActionEvent event)
               {
                  loadErrorMessageDialog.closeDialog();
               }
            });

   }

   public void handleAssocSelectionResponseEvent(@Observes @AssocSelectionResponseEvent PrescriptionBookSalesOrderSelectionEventData eventData)
   {
      if (eventData != null && pendingSelectionRequest != null && eventData.getId().equals(pendingSelectionRequest.getId())
            && eventData.getSourceEntity() == sourceEntity && eventData.getTargetEntity() != null)
      {
         if (sourceEntity != null)
            sourceEntity.setSalesOrder(new PrescriptionBookSalesOrder(eventData.getTargetEntity()));
      }
      componentSelectionRequestEvent.fire(new ComponentSelectionRequestData(PrescriptionBook.class.getName()));
   }

   /*
    * Only load if you need more fields than the one specified in the
    * association
    */
   protected void loadAssociation()
   {
   }

}
