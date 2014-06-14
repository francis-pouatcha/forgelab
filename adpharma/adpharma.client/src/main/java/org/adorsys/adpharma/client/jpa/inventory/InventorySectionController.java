package org.adorsys.adpharma.client.jpa.inventory;

import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javax.inject.Inject;

import org.adorsys.adpharma.client.jpa.section.Section;
import org.adorsys.adpharma.client.jpa.section.SectionSearchInput;
import org.adorsys.adpharma.client.jpa.section.SectionSearchResult;
import org.adorsys.adpharma.client.jpa.section.SectionSearchService;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.login.ErrorDisplay;
import org.adorsys.javafx.crud.extensions.login.ServiceCallFailedEventHandler;
import org.adorsys.javafx.crud.extensions.view.ErrorMessageDialog;
import org.apache.commons.lang3.StringUtils;

public abstract class InventorySectionController
{
   @Inject
   private SectionSearchService searchService;
   @Inject
   private ServiceCallFailedEventHandler searchServiceCallFailedEventHandler;

   private SectionSearchResult targetSearchResult;

   @Inject
   @Bundle({ CrudKeys.class, Section.class, Inventory.class })
   private ResourceBundle resourceBundle;

   @Inject
   private ErrorMessageDialog errorMessageDialog;

   protected Inventory sourceEntity;

   protected void disableButton(final InventorySectionSelection selection)
   {
      selection.getSection().setDisable(true);
   }

   protected void activateButton(final InventorySectionSelection selection)
   {
   }

   protected void bind(final InventorySectionSelection selection, final InventorySectionForm form)
   {

      //	    selection.getAgency().valueProperty().bindBidirectional(sourceEntity.agencyProperty());

      // send search result event.
      searchService.setOnSucceeded(new EventHandler<WorkerStateEvent>()
      {
         @Override
         public void handle(WorkerStateEvent event)
         {
            SectionSearchService s = (SectionSearchService) event
                  .getSource();
            targetSearchResult = s.getValue();
            event.consume();
            s.reset();
            List<Section> entities = targetSearchResult.getResultList();
            selection.getSection().getItems().clear();
            selection.getSection().getItems().add(new InventorySection());
            for (Section entity : entities)
            {
               selection.getSection().getItems().add(new InventorySection(entity));
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

      selection.getSection().valueProperty().addListener(new ChangeListener<InventorySection>()
      {
         @Override
         public void changed(ObservableValue<? extends InventorySection> ov, InventorySection oldValue,
        		 InventorySection newValue)
         {
            if (sourceEntity != null)
               form.update(newValue);
            //                sourceEntity.setAgency(newValue);
         }
      });

      selection.getSection().armedProperty().addListener(new ChangeListener<Boolean>()
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
      searchService.setSearchInputs(new SectionSearchInput()).start();
   }

}
