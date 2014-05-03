package org.adorsys.adpharma.client.jpa.supplierinvoiceitem;

import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javax.inject.Inject;

import org.adorsys.adpharma.client.jpa.article.Article;
import org.adorsys.adpharma.client.jpa.article.ArticleSearchInput;
import org.adorsys.adpharma.client.jpa.article.ArticleSearchResult;
import org.adorsys.adpharma.client.jpa.article.ArticleSearchService;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.login.ErrorDisplay;
import org.adorsys.javafx.crud.extensions.login.ServiceCallFailedEventHandler;
import org.adorsys.javafx.crud.extensions.view.ErrorMessageDialog;
import org.apache.commons.lang3.StringUtils;

public abstract class SupplierInvoiceItemArticleController
{
   @Inject
   private ArticleSearchService searchService;
   @Inject
   private ServiceCallFailedEventHandler searchServiceCallFailedEventHandler;

   private ArticleSearchResult targetSearchResult;

   @Inject
   @Bundle({ CrudKeys.class, Article.class, SupplierInvoiceItem.class })
   private ResourceBundle resourceBundle;

   @Inject
   private ErrorMessageDialog errorMessageDialog;

   protected SupplierInvoiceItem sourceEntity;

   protected void disableButton(final SupplierInvoiceItemArticleSelection selection)
   {
      selection.getArticle().setDisable(true);
   }

   protected void activateButton(final SupplierInvoiceItemArticleSelection selection)
   {
   }

   protected void bind(final SupplierInvoiceItemArticleSelection selection, final SupplierInvoiceItemArticleForm form)
   {

      //	    selection.getArticle().valueProperty().bindBidirectional(sourceEntity.articleProperty());

      // send search result event.
      searchService.setOnSucceeded(new EventHandler<WorkerStateEvent>()
      {
         @Override
         public void handle(WorkerStateEvent event)
         {
            ArticleSearchService s = (ArticleSearchService) event
                  .getSource();
            targetSearchResult = s.getValue();
            event.consume();
            s.reset();
            List<Article> entities = targetSearchResult.getResultList();
            selection.getArticle().getItems().clear();
            selection.getArticle().getItems().add(new SupplierInvoiceItemArticle());
            for (Article entity : entities)
            {
               selection.getArticle().getItems().add(new SupplierInvoiceItemArticle(entity));
            }
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

      selection.getArticle().valueProperty().addListener(new ChangeListener<SupplierInvoiceItemArticle>()
      {
         @Override
         public void changed(ObservableValue<? extends SupplierInvoiceItemArticle> ov, SupplierInvoiceItemArticle oldValue,
               SupplierInvoiceItemArticle newValue)
         {
            if (sourceEntity != null)
               form.update(newValue);
            //                sourceEntity.setArticle(newValue);
         }
      });

      selection.getArticle().armedProperty().addListener(new ChangeListener<Boolean>()
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
      searchService.setSearchInputs(new ArticleSearchInput()).start();
   }

}
