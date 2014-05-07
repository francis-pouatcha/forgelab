package org.adorsys.adpharma.client.jpa.documentstore;

import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javax.inject.Inject;

import org.adorsys.adpharma.client.jpa.login.Login;
import org.adorsys.adpharma.client.jpa.login.LoginSearchInput;
import org.adorsys.adpharma.client.jpa.login.LoginSearchResult;
import org.adorsys.adpharma.client.jpa.login.LoginSearchService;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.login.ErrorDisplay;
import org.adorsys.javafx.crud.extensions.login.ServiceCallFailedEventHandler;
import org.adorsys.javafx.crud.extensions.view.ErrorMessageDialog;
import org.apache.commons.lang3.StringUtils;

public abstract class DocumentStoreRecordingUserController
{
   @Inject
   private LoginSearchService searchService;
   @Inject
   private ServiceCallFailedEventHandler searchServiceCallFailedEventHandler;

   private LoginSearchResult targetSearchResult;

   @Inject
   @Bundle({ CrudKeys.class, Login.class, DocumentStore.class })
   private ResourceBundle resourceBundle;

   @Inject
   private ErrorMessageDialog errorMessageDialog;

   protected DocumentStore sourceEntity;

   protected void disableButton(final DocumentStoreRecordingUserSelection selection)
   {
      selection.getRecordingUser().setDisable(true);
   }

   protected void activateButton(final DocumentStoreRecordingUserSelection selection)
   {
   }

   protected void bind(final DocumentStoreRecordingUserSelection selection, final DocumentStoreRecordingUserForm form)
   {

      //	    selection.getRecordingUser().valueProperty().bindBidirectional(sourceEntity.recordingUserProperty());

      // send search result event.
      searchService.setOnSucceeded(new EventHandler<WorkerStateEvent>()
      {
         @Override
         public void handle(WorkerStateEvent event)
         {
            LoginSearchService s = (LoginSearchService) event
                  .getSource();
            targetSearchResult = s.getValue();
            event.consume();
            s.reset();
            List<Login> entities = targetSearchResult.getResultList();
            selection.getRecordingUser().getItems().clear();
            selection.getRecordingUser().getItems().add(new DocumentStoreRecordingUser());
            for (Login entity : entities)
            {
               selection.getRecordingUser().getItems().add(new DocumentStoreRecordingUser(entity));
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

      selection.getRecordingUser().valueProperty().addListener(new ChangeListener<DocumentStoreRecordingUser>()
      {
         @Override
         public void changed(ObservableValue<? extends DocumentStoreRecordingUser> ov, DocumentStoreRecordingUser oldValue,
               DocumentStoreRecordingUser newValue)
         {
            if (sourceEntity != null)
               form.update(newValue);
            //                sourceEntity.setRecordingUser(newValue);
         }
      });

      selection.getRecordingUser().armedProperty().addListener(new ChangeListener<Boolean>()
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
      searchService.setSearchInputs(new LoginSearchInput()).start();
   }

}
