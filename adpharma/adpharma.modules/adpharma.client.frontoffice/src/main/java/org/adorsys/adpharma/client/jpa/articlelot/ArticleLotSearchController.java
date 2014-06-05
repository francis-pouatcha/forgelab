package org.adorsys.adpharma.client.jpa.articlelot;

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
import org.adorsys.adpharma.client.jpa.articlelot.ArticleLot;

@Singleton
public class ArticleLotSearchController implements EntityController
{

   @Inject
   private ArticleLotSearchView searchView;

   @Inject
   private ArticleLotSearchService searchService;
   @Inject
   private ServiceCallFailedEventHandler searchServiceCallFailedEventHandler;

   @Inject
   @EntitySearchDoneEvent
   private Event<ArticleLotSearchResult> searchDoneEvent;

   private ArticleLot model;

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
            ArticleLotSearchService s = (ArticleLotSearchService) event.getSource();
            ArticleLotSearchResult searchResult = s.getValue();
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
            PropertyReader.copy(new ArticleLot(), model);
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
   public void handleSearchRequestedEvent(@Observes @EntitySearchRequestedEvent ArticleLot templateEntity)
   {
      PropertyReader.copy(templateEntity, model);
      model.setId(null);
      model.setVersion(0);
   }

   private ArticleLotSearchInput buildSearchInputs()
   {
      ArticleLotSearchInput result = new ArticleLotSearchInput();
      result.setEntity(model);
      List<String> readSearchFields = PropertyReader.readSearchFields(searchView, model);
      result.getFieldNames().clear();
      result.getFieldNames().addAll(readSearchFields);
      return result;
   }

   public void handleNewModelEvent(@Observes @SearchModelEvent ArticleLot model)
   {
      this.model = model;
      searchView.bind(this.model);
   }

   public void handleEntityListPageIndexChangedEvent(@Observes @EntityListPageIndexChangedEvent ArticleLotSearchResult searchResult)
   {
      searchService.setSearchInputs(searchResult.getSearchInput()).restart();
   }

	public void reset() {
	     PropertyReader.copy(new ArticleLot(), model);
	}
}