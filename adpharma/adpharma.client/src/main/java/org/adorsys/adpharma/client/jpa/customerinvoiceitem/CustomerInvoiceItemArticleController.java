package org.adorsys.adpharma.client.jpa.customerinvoiceitem;

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

import org.adorsys.adpharma.client.jpa.article.Article;
import org.adorsys.adpharma.client.jpa.article.ArticleLoadService;
import org.adorsys.adpharma.client.jpa.customerinvoiceitem.CustomerInvoiceItem;

public abstract class CustomerInvoiceItemArticleController
{

   protected CustomerInvoiceItem sourceEntity;

   @Inject
   private ArticleLoadService loadService;
   @Inject
   private ServiceCallFailedEventHandler loadServiceCallFailedEventHandler;

   @Inject
   private ErrorMessageDialog loadErrorMessageDialog;

   @Inject
   @AssocSelectionRequestEvent
   private Event<CustomerInvoiceItemArticleSelectionEventData> selectionRequestEvent;

   private CustomerInvoiceItemArticleSelectionEventData pendingSelectionRequest;

   @Inject
   @ComponentSelectionRequestEvent
   private Event<ComponentSelectionRequestData> componentSelectionRequestEvent;

   @Inject
   @Bundle(CrudKeys.class)
   private ResourceBundle resourceBundle;

   protected void disableButton(final CustomerInvoiceItemArticleSelection selection, final CustomerInvoiceItemArticleForm form)
   {
      selection.getSelectButton().setDisable(true);
   }

   protected void activateButton(final CustomerInvoiceItemArticleSelection selection, final CustomerInvoiceItemArticleForm form)
   {
   }

   protected void bind(final CustomerInvoiceItemArticleSelection selection, final CustomerInvoiceItemArticleForm form)
   {
      selection.getSelectButton().setOnAction(
            new EventHandler<ActionEvent>()
            {
               @Override
               public void handle(ActionEvent event)
               {
                  pendingSelectionRequest = new CustomerInvoiceItemArticleSelectionEventData(
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
            ArticleLoadService s = (ArticleLoadService) event.getSource();
            Article entity = s.getValue();
            event.consume();
            s.reset();
            sourceEntity.setArticle(new CustomerInvoiceItemArticle(entity));
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

   public void handleAssocSelectionResponseEvent(@Observes @AssocSelectionResponseEvent CustomerInvoiceItemArticleSelectionEventData eventData)
   {
      if (eventData != null && pendingSelectionRequest != null && eventData.getId().equals(pendingSelectionRequest.getId())
            && eventData.getSourceEntity() == sourceEntity && eventData.getTargetEntity() != null)
      {
         if (sourceEntity != null)
            sourceEntity.setArticle(new CustomerInvoiceItemArticle(eventData.getTargetEntity()));
      }
      componentSelectionRequestEvent.fire(new ComponentSelectionRequestData(CustomerInvoiceItem.class.getName()));
   }

   /*
    * Only load if you need more fields than the one specified in the
    * association
    */
   protected void loadAssociation()
   {
   }

}
