package org.adorsys.adpharma.client.jpa.article;

import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javax.inject.Inject;

import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.login.ErrorDisplay;
import org.adorsys.javafx.crud.extensions.login.ServiceCallFailedEventHandler;
import org.adorsys.javafx.crud.extensions.view.ErrorMessageDialog;
import org.apache.commons.lang3.StringUtils;

import org.adorsys.adpharma.client.jpa.salesmargin.SalesMargin;
import org.adorsys.adpharma.client.jpa.salesmargin.SalesMarginSearchInput;
import org.adorsys.adpharma.client.jpa.salesmargin.SalesMarginSearchResult;
import org.adorsys.adpharma.client.jpa.salesmargin.SalesMarginSearchService;
import org.adorsys.adpharma.client.jpa.article.Article;

public abstract class ArticleDefaultSalesMarginController
{
   @Inject
   private SalesMarginSearchService searchService;
   @Inject
   private ServiceCallFailedEventHandler searchServiceCallFailedEventHandler;

   private SalesMarginSearchResult targetSearchResult;

   @Inject
   @Bundle({ CrudKeys.class, SalesMargin.class, Article.class })
   private ResourceBundle resourceBundle;

   @Inject
   private ErrorMessageDialog errorMessageDialog;

   protected Article sourceEntity;

   protected void disableButton(final ArticleDefaultSalesMarginSelection selection)
   {
      selection.getDefaultSalesMargin().setDisable(true);
   }

   protected void activateButton(final ArticleDefaultSalesMarginSelection selection)
   {
   }

   protected void bind(final ArticleDefaultSalesMarginSelection selection)
   {

      // send search result event.
      searchService.setOnSucceeded(new EventHandler<WorkerStateEvent>()
      {
         @Override
         public void handle(WorkerStateEvent event)
         {
            SalesMarginSearchService s = (SalesMarginSearchService) event
                  .getSource();
            targetSearchResult = s.getValue();
            event.consume();
            s.reset();
            List<SalesMargin> entities = targetSearchResult.getResultList();
            selection.getDefaultSalesMargin().getItems().clear();
            selection.getDefaultSalesMargin().getItems().addAll(entities);
         }
      });
      searchServiceCallFailedEventHandler.setErrorDisplay(new ErrorDisplay()
      {
         @Override
         protected void showError(Throwable exception)
         {
            String message = exception.getMessage();
            errorMessageDialog.getTitleText().setText(
                  resourceBundle.getString("Entity_search_error.title"));
            if (!StringUtils.isBlank(message))
               errorMessageDialog.getDetailText().setText(message);
            errorMessageDialog.display();
         }
      });
      searchService.setOnFailed(searchServiceCallFailedEventHandler);

      errorMessageDialog.getOkButton().setOnAction(
            new EventHandler<ActionEvent>()
            {
               @Override
               public void handle(ActionEvent event)
               {
                  errorMessageDialog.closeDialog();
               }
            });

      selection.getDefaultSalesMargin().valueProperty().addListener(new ChangeListener<SalesMargin>()
      {
         @Override
         public void changed(ObservableValue<? extends SalesMargin> ov, SalesMargin oldValue,
               SalesMargin newValue)
         {
            if (sourceEntity != null)
               sourceEntity.setDefaultSalesMargin(new ArticleDefaultSalesMargin(newValue));
         }
      });

      selection.getDefaultSalesMargin().armedProperty().addListener(new ChangeListener<Boolean>()
      {

         @Override
         public void changed(ObservableValue<? extends Boolean> observableValue,
               Boolean oldValue, Boolean newValue)
         {
            if (newValue)
               load();
         }

      });
   }

   public void load()
   {
      searchService.setSearchInputs(new SalesMarginSearchInput()).start();
   }

}
