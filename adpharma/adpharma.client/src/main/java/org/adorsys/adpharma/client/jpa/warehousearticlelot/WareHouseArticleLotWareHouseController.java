package org.adorsys.adpharma.client.jpa.warehousearticlelot;

import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javax.inject.Inject;

import org.adorsys.adpharma.client.jpa.warehouse.WareHouse;
import org.adorsys.adpharma.client.jpa.warehouse.WareHouseSearchInput;
import org.adorsys.adpharma.client.jpa.warehouse.WareHouseSearchResult;
import org.adorsys.adpharma.client.jpa.warehouse.WareHouseSearchService;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.login.ErrorDisplay;
import org.adorsys.javafx.crud.extensions.login.ServiceCallFailedEventHandler;
import org.adorsys.javafx.crud.extensions.view.ErrorMessageDialog;
import org.apache.commons.lang3.StringUtils;

public abstract class WareHouseArticleLotWareHouseController
{
   @Inject
   private WareHouseSearchService searchService;
   @Inject
   private ServiceCallFailedEventHandler searchServiceCallFailedEventHandler;

   private WareHouseSearchResult targetSearchResult;

   @Inject
   @Bundle({ CrudKeys.class, WareHouse.class, WareHouseArticleLot.class })
   private ResourceBundle resourceBundle;

   @Inject
   private ErrorMessageDialog errorMessageDialog;

   protected WareHouseArticleLot sourceEntity;

   protected void disableButton(final WareHouseArticleLotWareHouseSelection selection)
   {
      selection.getWareHouse().setDisable(true);
   }

   protected void activateButton(final WareHouseArticleLotWareHouseSelection selection)
   {
   }

   protected void bind(final WareHouseArticleLotWareHouseSelection selection, final WareHouseArticleLotWareHouseForm form)
   {

      //	    selection.getWareHouse().valueProperty().bindBidirectional(sourceEntity.recordingUserProperty());

      // send search result event.
      searchService.setOnSucceeded(new EventHandler<WorkerStateEvent>()
      {
         @Override
         public void handle(WorkerStateEvent event)
         {
        	 WareHouseSearchService s = (WareHouseSearchService) event
                  .getSource();
            targetSearchResult = s.getValue();
            event.consume();
            s.reset();
            List<WareHouse> entities = targetSearchResult.getResultList();
            selection.getWareHouse().getItems().clear();
            selection.getWareHouse().getItems().add(new WareHouseArticleLotWareHouse());
            for (WareHouse entity : entities)
            {
               selection.getWareHouse().getItems().add(new WareHouseArticleLotWareHouse(entity));
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

      selection.getWareHouse().valueProperty().addListener(new ChangeListener<WareHouseArticleLotWareHouse>()
      {
         @Override
         public void changed(ObservableValue<? extends WareHouseArticleLotWareHouse> ov, WareHouseArticleLotWareHouse oldValue,
               WareHouseArticleLotWareHouse newValue)
         {
            if (sourceEntity != null)
               form.update(newValue);
            //                sourceEntity.setWareHouse(newValue);
         }
      });

      selection.getWareHouse().armedProperty().addListener(new ChangeListener<Boolean>()
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
      searchService.setSearchInputs(new WareHouseSearchInput()).start();
   }

}
