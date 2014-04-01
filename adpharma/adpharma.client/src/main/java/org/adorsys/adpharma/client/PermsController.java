package org.adorsys.adpharma.client;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.inject.Inject;

import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.login.LogoutSucceededEvent;
import org.adorsys.javafx.crud.extensions.login.PermissionsEvent;
import org.adorsys.javafx.crud.extensions.login.ErrorDisplay;
import org.adorsys.javafx.crud.extensions.login.LoginSucceededEvent;
import org.adorsys.javafx.crud.extensions.login.ServiceCallFailedEventHandler;
import org.adorsys.javafx.crud.extensions.view.ErrorMessageDialog;
import org.apache.commons.lang3.StringUtils;

public class PermsController
{

   @Inject
   private PermsTask dcTask;
   @Inject
   private ServiceCallFailedEventHandler searchServiceCallFailedEventHandler;

   @Inject
   @PermissionsEvent
   private Event<List<String>> permissionsEvent;

   @Inject
   private ErrorMessageDialog errorMessageDialog;

   @Inject
   @Bundle(CrudKeys.class)
   private ResourceBundle resourceBundle;

   public void handleLoginSucceededEvent(
         @Observes(notifyObserver = Reception.ALWAYS) @LoginSucceededEvent String loginName)
   {
      dcTask.setLoginName(loginName).start();
   }

   public void handleLogoutSucceededEvent(
         @Observes(notifyObserver = Reception.ALWAYS) @LogoutSucceededEvent Object obj)
   {
      List<String> permList = Collections.emptyList();
      permissionsEvent.fire(permList);
   }

   @PostConstruct
   public void postConstruct()
   {

      // send search result event.
      dcTask.setOnSucceeded(new EventHandler<WorkerStateEvent>()
      {
         @Override
         public void handle(WorkerStateEvent event)
         {
            PermsTask s = (PermsTask) event.getSource();
            String perms = s.getValue();
            event.consume();
            s.reset();
            String[] split = perms.split(":");
            List<String> permList = new ArrayList<String>();
            for (String string : split)
            {
               if (StringUtils.isNotBlank(string))
                  permList.add(string);
            }
            permissionsEvent.fire(permList);
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
      dcTask.setOnFailed(searchServiceCallFailedEventHandler);
      errorMessageDialog.getOkButton().setOnAction(
            new EventHandler<ActionEvent>()
            {
               @Override
               public void handle(ActionEvent event)
               {
                  errorMessageDialog.closeDialog();
               }
            });
   }
}
