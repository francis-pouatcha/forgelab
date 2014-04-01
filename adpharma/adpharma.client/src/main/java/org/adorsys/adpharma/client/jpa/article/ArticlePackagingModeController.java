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

import org.adorsys.adpharma.client.jpa.packagingmode.PackagingMode;
import org.adorsys.adpharma.client.jpa.packagingmode.PackagingModeSearchInput;
import org.adorsys.adpharma.client.jpa.packagingmode.PackagingModeSearchResult;
import org.adorsys.adpharma.client.jpa.packagingmode.PackagingModeSearchService;
import org.adorsys.adpharma.client.jpa.article.Article;

public abstract class ArticlePackagingModeController
{
   @Inject
   private PackagingModeSearchService searchService;
   @Inject
   private ServiceCallFailedEventHandler searchServiceCallFailedEventHandler;

   private PackagingModeSearchResult targetSearchResult;

   @Inject
   @Bundle({ CrudKeys.class, PackagingMode.class, Article.class })
   private ResourceBundle resourceBundle;

   @Inject
   private ErrorMessageDialog errorMessageDialog;

   protected Article sourceEntity;

   protected void disableButton(final ArticlePackagingModeSelection selection)
   {
      selection.getPackagingMode().setDisable(true);
   }

   protected void activateButton(final ArticlePackagingModeSelection selection)
   {
   }

   protected void bind(final ArticlePackagingModeSelection selection, final ArticlePackagingModeForm form)
   {

      //	    selection.getPackagingMode().valueProperty().bindBidirectional(sourceEntity.packagingModeProperty());

      // send search result event.
      searchService.setOnSucceeded(new EventHandler<WorkerStateEvent>()
      {
         @Override
         public void handle(WorkerStateEvent event)
         {
            PackagingModeSearchService s = (PackagingModeSearchService) event
                  .getSource();
            targetSearchResult = s.getValue();
            event.consume();
            s.reset();
            List<PackagingMode> entities = targetSearchResult.getResultList();
            selection.getPackagingMode().getItems().clear();
            selection.getPackagingMode().getItems().add(new ArticlePackagingMode());
            for (PackagingMode entity : entities)
            {
               selection.getPackagingMode().getItems().add(new ArticlePackagingMode(entity));
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

      selection.getPackagingMode().valueProperty().addListener(new ChangeListener<ArticlePackagingMode>()
      {
         @Override
         public void changed(ObservableValue<? extends ArticlePackagingMode> ov, ArticlePackagingMode oldValue,
               ArticlePackagingMode newValue)
         {
            if (sourceEntity != null)
               form.update(newValue);
            //                sourceEntity.setPackagingMode(newValue);
         }
      });

      selection.getPackagingMode().armedProperty().addListener(new ChangeListener<Boolean>()
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
      searchService.setSearchInputs(new PackagingModeSearchInput()).start();
   }

}
