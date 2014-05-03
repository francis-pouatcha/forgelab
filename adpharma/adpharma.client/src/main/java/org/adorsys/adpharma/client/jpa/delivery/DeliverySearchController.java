package org.adorsys.adpharma.client.jpa.delivery;

import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.EntityController;
import org.adorsys.javafx.crud.extensions.ViewType;
import org.adorsys.javafx.crud.extensions.events.EntityListPageIndexChangedEvent;
import org.adorsys.javafx.crud.extensions.events.EntitySearchDoneEvent;
import org.adorsys.javafx.crud.extensions.events.EntitySearchRequestedEvent;
import org.adorsys.javafx.crud.extensions.events.SearchModelEvent;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.login.ErrorDisplay;
import org.adorsys.javafx.crud.extensions.login.ServiceCallFailedEventHandler;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;
import org.adorsys.javafx.crud.extensions.view.ErrorMessageDialog;
import org.apache.commons.lang3.StringUtils;

@Singleton
public class DeliverySearchController implements EntityController
{

   @Inject
   private DeliverySearchView searchView;

   @Inject
   private DeliverySearchService searchService;
   @Inject
   private ServiceCallFailedEventHandler searchServiceCallFailedEventHandler;

   @Inject
   @EntitySearchDoneEvent
   private Event<DeliverySearchResult> searchDoneEvent;

   private Delivery model;

   @Inject
   private ErrorMessageDialog errorMessageDialog;

   @Inject
   @Bundle(CrudKeys.class)
   private ResourceBundle resourceBundle;

   @PostConstruct
   public void postConstruct()
   {

      // Start search on action.
      searchView.getSearchButton().setOnAction(new EventHandler<ActionEvent>()
      {
         @Override
         public void handle(ActionEvent e)
         {
            searchService.setSearchInputs(buildSearchInputs()).start();
         }
      });

      // send search result event.
      searchService.setOnSucceeded(new EventHandler<WorkerStateEvent>()
      {
         @Override
         public void handle(WorkerStateEvent event)
         {
            DeliverySearchService s = (DeliverySearchService) event.getSource();
            DeliverySearchResult searchResult = s.getValue();
            event.consume();
            s.reset();
            searchDoneEvent.fire(searchResult);
         }
      });

      searchServiceCallFailedEventHandler.setErrorDisplay(new ErrorDisplay()
      {
         @Override
         protected void showError(Throwable exception)
         {
            String message = exception.getMessage();
            errorMessageDialog.getTitleText().setText(resourceBundle.getString("Entity_search_error.title"));
            if (!StringUtils.isBlank(message))
               errorMessageDialog.getDetailText().setText(message);
            errorMessageDialog.display();
         }
      });
      searchService.setOnFailed(searchServiceCallFailedEventHandler);
      errorMessageDialog.getOkButton().setOnAction(new EventHandler<ActionEvent>()
      {
         @Override
         public void handle(ActionEvent event)
         {
            errorMessageDialog.closeDialog();
         }
      });

      // Disable search button during search is running.
      searchView.getSearchButton().disableProperty().bind(searchService.runningProperty());

      // Reset search fields on action
      searchView.getResetButton().setOnAction(new EventHandler<ActionEvent>()
      {
         @Override
         public void handle(ActionEvent e)
         {
            PropertyReader.copy(new Delivery(), model);
         }
      });

      /*
       * Disable cancel button and enable only if search is running.
       */
      searchView.getCancelButton().setDisable(true);
      searchService.runningProperty().addListener(new ChangeListener<Boolean>()
      {
         @Override
         public void changed(ObservableValue<? extends Boolean> arg0,
               Boolean oldValue, Boolean newValue)
         {
            searchView.getCancelButton().setDisable(!newValue);
         }
      });
      searchView.getCancelButton().setOnAction(new EventHandler<ActionEvent>()
      {
         @Override
         public void handle(ActionEvent e)
         {
            if (searchService.isRunning())
            {
               searchService.cancel();
               searchService.reset();
            }

         }
      });
   }

   @Override
   public void display(Pane parent)
   {
      AnchorPane rootPane = searchView.getRootPane();
      ObservableList<Node> children = parent.getChildren();
      if (!children.contains(rootPane))
      {
         children.add(rootPane);
      }
   }

   @Override
   public ViewType getViewType()
   {
      return ViewType.SEARCH;
   }

   /**
    * use data of the selected to prefill search panel.
    * @param templateEntity
    */
   public void handleSearchRequestedEvent(@Observes @EntitySearchRequestedEvent Delivery templateEntity)
   {
      PropertyReader.copy(templateEntity, model);
      model.setId(null);
      model.setVersion(0);
   }

   private DeliverySearchInput buildSearchInputs()
   {
      DeliverySearchInput result = new DeliverySearchInput();
      result.setEntity(model);
      List<String> readSearchFields = PropertyReader.readSearchFields(searchView, model);
      result.getFieldNames().clear();
      result.getFieldNames().addAll(readSearchFields);
      return result;
   }

   public void handleNewModelEvent(@Observes @SearchModelEvent Delivery model)
   {
      this.model = model;
      searchView.bind(this.model);
   }

   public void handleEntityListPageIndexChangedEvent(@Observes @EntityListPageIndexChangedEvent DeliverySearchResult searchResult)
   {
      searchService.setSearchInputs(searchResult.getSearchInput()).start();
   }
   
	public void reset() {
	     PropertyReader.copy(new Delivery(), model);
	}

}