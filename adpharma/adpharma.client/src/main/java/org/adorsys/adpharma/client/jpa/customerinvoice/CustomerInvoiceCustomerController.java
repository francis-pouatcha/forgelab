package org.adorsys.adpharma.client.jpa.customerinvoice;

import java.util.ResourceBundle;
import java.util.UUID;

import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.adorsys.javafx.crud.extensions.events.AssocSelectionEventData;
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

import org.adorsys.adpharma.client.jpa.customer.Customer;
import org.adorsys.adpharma.client.jpa.customer.CustomerLoadService;
import org.adorsys.adpharma.client.jpa.customerinvoice.CustomerInvoice;

public abstract class CustomerInvoiceCustomerController
{

   protected CustomerInvoice sourceEntity;

   @Inject
   private CustomerLoadService loadService;
   @Inject
   private ServiceCallFailedEventHandler loadServiceCallFailedEventHandler;

   @Inject
   private ErrorMessageDialog loadErrorMessageDialog;

   @Inject
   @AssocSelectionRequestEvent
   private Event<CustomerInvoiceCustomerSelectionEventData> selectionRequestEvent;

   private CustomerInvoiceCustomerSelectionEventData pendingSelectionRequest;

   @Inject
   @ComponentSelectionRequestEvent
   private Event<ComponentSelectionRequestData> componentSelectionRequestEvent;

   @Inject
   @Bundle(CrudKeys.class)
   private ResourceBundle resourceBundle;

   protected void disableButton(final CustomerInvoiceCustomerSelection selection, final CustomerInvoiceCustomerForm form)
   {
      selection.getSelectButton().setDisable(true);
   }

   protected void activateButton(final CustomerInvoiceCustomerSelection selection, final CustomerInvoiceCustomerForm form)
   {
   }

   protected void bind(final CustomerInvoiceCustomerSelection selection, final CustomerInvoiceCustomerForm form)
   {
      selection.getSelectButton().setOnAction(
            new EventHandler<ActionEvent>()
            {
               @Override
               public void handle(ActionEvent event)
               {
                  pendingSelectionRequest = new CustomerInvoiceCustomerSelectionEventData(
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
            CustomerLoadService s = (CustomerLoadService) event.getSource();
            Customer entity = s.getValue();
            event.consume();
            s.reset();
            sourceEntity.setCustomer(new CustomerInvoiceCustomer(entity));
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

   public void handleAssocSelectionResponseEvent(@Observes @AssocSelectionResponseEvent CustomerInvoiceCustomerSelectionEventData eventData)
   {
      if (eventData != null && pendingSelectionRequest != null && eventData.getId().equals(pendingSelectionRequest.getId())
            && eventData.getSourceEntity() == sourceEntity && eventData.getTargetEntity() != null)
      {
         if (sourceEntity != null)
            sourceEntity.setCustomer(new CustomerInvoiceCustomer(eventData.getTargetEntity()));
      }
      componentSelectionRequestEvent.fire(new ComponentSelectionRequestData(CustomerInvoice.class.getName()));
   }

   /*
    * Only load if you need more fields than the one specified in the
    * association
    */
   protected void loadAssociation()
   {
   }

}
