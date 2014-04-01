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

import org.adorsys.adpharma.client.jpa.productfamily.ProductFamily;
import org.adorsys.adpharma.client.jpa.productfamily.ProductFamilySearchInput;
import org.adorsys.adpharma.client.jpa.productfamily.ProductFamilySearchResult;
import org.adorsys.adpharma.client.jpa.productfamily.ProductFamilySearchService;
import org.adorsys.adpharma.client.jpa.article.Article;

public abstract class ArticleFamilyController
{
   @Inject
   private ProductFamilySearchService searchService;
   @Inject
   private ServiceCallFailedEventHandler searchServiceCallFailedEventHandler;

   private ProductFamilySearchResult targetSearchResult;

   @Inject
   @Bundle({ CrudKeys.class, ProductFamily.class, Article.class })
   private ResourceBundle resourceBundle;

   @Inject
   private ErrorMessageDialog errorMessageDialog;

   protected Article sourceEntity;

   protected void disableButton(final ArticleFamilySelection selection)
   {
      selection.getFamily().setDisable(true);
   }

   protected void activateButton(final ArticleFamilySelection selection)
   {
   }

   protected void bind(final ArticleFamilySelection selection, final ArticleFamilyForm form)
   {

      //	    selection.getFamily().valueProperty().bindBidirectional(sourceEntity.familyProperty());

      // send search result event.
      searchService.setOnSucceeded(new EventHandler<WorkerStateEvent>()
      {
         @Override
         public void handle(WorkerStateEvent event)
         {
            ProductFamilySearchService s = (ProductFamilySearchService) event
                  .getSource();
            targetSearchResult = s.getValue();
            event.consume();
            s.reset();
            List<ProductFamily> entities = targetSearchResult.getResultList();
            selection.getFamily().getItems().clear();
            selection.getFamily().getItems().add(new ArticleFamily());
            for (ProductFamily entity : entities)
            {
               selection.getFamily().getItems().add(new ArticleFamily(entity));
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

      selection.getFamily().valueProperty().addListener(new ChangeListener<ArticleFamily>()
      {
         @Override
         public void changed(ObservableValue<? extends ArticleFamily> ov, ArticleFamily oldValue,
               ArticleFamily newValue)
         {
            if (sourceEntity != null)
               form.update(newValue);
            //                sourceEntity.setFamily(newValue);
         }
      });

      selection.getFamily().armedProperty().addListener(new ChangeListener<Boolean>()
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
      searchService.setSearchInputs(new ProductFamilySearchInput()).start();
   }

}
