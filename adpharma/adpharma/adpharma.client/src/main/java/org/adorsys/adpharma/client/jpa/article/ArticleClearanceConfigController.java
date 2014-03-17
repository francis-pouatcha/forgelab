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

import org.adorsys.adpharma.client.jpa.clearanceconfig.ClearanceConfig;
import org.adorsys.adpharma.client.jpa.clearanceconfig.ClearanceConfigSearchInput;
import org.adorsys.adpharma.client.jpa.clearanceconfig.ClearanceConfigSearchResult;
import org.adorsys.adpharma.client.jpa.clearanceconfig.ClearanceConfigSearchService;
import org.adorsys.adpharma.client.jpa.article.Article;

public abstract class ArticleClearanceConfigController
{
   @Inject
   private ClearanceConfigSearchService searchService;
   @Inject
   private ServiceCallFailedEventHandler searchServiceCallFailedEventHandler;

   private ClearanceConfigSearchResult targetSearchResult;

   @Inject
   @Bundle({ CrudKeys.class, ClearanceConfig.class, Article.class })
   private ResourceBundle resourceBundle;

   @Inject
   private ErrorMessageDialog errorMessageDialog;

   protected Article sourceEntity;

   protected void disableButton(final ArticleClearanceConfigSelection selection)
   {
      selection.getClearanceConfig().setDisable(true);
   }

   protected void activateButton(final ArticleClearanceConfigSelection selection)
   {
   }

   protected void bind(final ArticleClearanceConfigSelection selection)
   {

      // send search result event.
      searchService.setOnSucceeded(new EventHandler<WorkerStateEvent>()
      {
         @Override
         public void handle(WorkerStateEvent event)
         {
            ClearanceConfigSearchService s = (ClearanceConfigSearchService) event
                  .getSource();
            targetSearchResult = s.getValue();
            event.consume();
            s.reset();
            List<ClearanceConfig> entities = targetSearchResult.getResultList();
            selection.getClearanceConfig().getItems().clear();
            selection.getClearanceConfig().getItems().addAll(entities);
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

      selection.getClearanceConfig().valueProperty().addListener(new ChangeListener<ClearanceConfig>()
      {
         @Override
         public void changed(ObservableValue<? extends ClearanceConfig> ov, ClearanceConfig oldValue,
               ClearanceConfig newValue)
         {
            if (sourceEntity != null)
               sourceEntity.setClearanceConfig(new ArticleClearanceConfig(newValue));
         }
      });

      selection.getClearanceConfig().armedProperty().addListener(new ChangeListener<Boolean>()
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
      searchService.setSearchInputs(new ClearanceConfigSearchInput()).start();
   }

}
