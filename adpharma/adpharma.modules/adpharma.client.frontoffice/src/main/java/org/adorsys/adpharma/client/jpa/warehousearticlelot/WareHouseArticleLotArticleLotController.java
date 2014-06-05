package org.adorsys.adpharma.client.jpa.warehousearticlelot;

import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javax.inject.Inject;

import org.adorsys.adpharma.client.jpa.articlelot.ArticleLot;
import org.adorsys.adpharma.client.jpa.articlelot.ArticleLotSearchInput;
import org.adorsys.adpharma.client.jpa.articlelot.ArticleLotSearchResult;
import org.adorsys.adpharma.client.jpa.articlelot.ArticleLotSearchService;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.login.ErrorDisplay;
import org.adorsys.javafx.crud.extensions.login.ServiceCallFailedEventHandler;
import org.adorsys.javafx.crud.extensions.view.ErrorMessageDialog;
import org.apache.commons.lang3.StringUtils;

public abstract class WareHouseArticleLotArticleLotController
{
   @Inject
   private ArticleLotSearchService searchService;
   @Inject
   private ServiceCallFailedEventHandler searchServiceCallFailedEventHandler;

   private ArticleLotSearchResult targetSearchResult;

   @Inject
   @Bundle({ CrudKeys.class, ArticleLot.class, WareHouseArticleLot.class })
   private ResourceBundle resourceBundle;

   @Inject
   private ErrorMessageDialog errorMessageDialog;

   protected WareHouseArticleLot sourceEntity;

   protected void disableButton(final WareHouseArticleLotArticleLotSelection selection)
   {
      selection.getArticleLot().setDisable(true);
   }

   protected void activateButton(final WareHouseArticleLotArticleLotSelection selection)
   {
   }

   protected void bind(final WareHouseArticleLotArticleLotSelection selection, final WareHouseArticleLotArticleLotForm form)
   {

      //	    selection.getArticleLot().valueProperty().bindBidirectional(sourceEntity.recordingUserProperty());

      // send search result event.
      searchService.setOnSucceeded(new EventHandler<WorkerStateEvent>()
      {
         @Override
         public void handle(WorkerStateEvent event)
         {
        	 ArticleLotSearchService s = (ArticleLotSearchService) event
                  .getSource();
            targetSearchResult = s.getValue();
            event.consume();
            s.reset();
            List<ArticleLot> entities = targetSearchResult.getResultList();
            selection.getArticleLot().getItems().clear();
            selection.getArticleLot().getItems().add(new WareHouseArticleLotArticleLot());
            for (ArticleLot entity : entities)
            {
               selection.getArticleLot().getItems().add(new WareHouseArticleLotArticleLot(entity));
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

      selection.getArticleLot().valueProperty().addListener(new ChangeListener<WareHouseArticleLotArticleLot>()
      {
         @Override
         public void changed(ObservableValue<? extends WareHouseArticleLotArticleLot> ov, WareHouseArticleLotArticleLot oldValue,
               WareHouseArticleLotArticleLot newValue)
         {
            if (sourceEntity != null)
               form.update(newValue);
            //                sourceEntity.setWareHouse(newValue);
         }
      });

      selection.getArticleLot().armedProperty().addListener(new ChangeListener<Boolean>()
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
      searchService.setSearchInputs(new ArticleLotSearchInput()).start();
   }

}
