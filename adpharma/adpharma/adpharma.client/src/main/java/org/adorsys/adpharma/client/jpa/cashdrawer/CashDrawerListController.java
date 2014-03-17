package org.adorsys.adpharma.client.jpa.cashdrawer;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
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
import org.adorsys.javafx.crud.extensions.events.EntityCreateDoneEvent;
import org.adorsys.javafx.crud.extensions.events.EntityCreateRequestedEvent;
import org.adorsys.javafx.crud.extensions.events.EntityEditCanceledEvent;
import org.adorsys.javafx.crud.extensions.events.EntityEditDoneEvent;
import org.adorsys.javafx.crud.extensions.events.EntityListPageIndexChangedEvent;
import org.adorsys.javafx.crud.extensions.events.EntityRemoveDoneEvent;
import org.adorsys.javafx.crud.extensions.events.EntitySearchDoneEvent;
import org.adorsys.javafx.crud.extensions.events.EntitySearchRequestedEvent;
import org.adorsys.javafx.crud.extensions.events.EntitySelectionEvent;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;
import org.adorsys.javafx.crud.extensions.utils.PaginationUtils;

@Singleton
public class CashDrawerListController implements EntityController
{

   @Inject
   private CashDrawerListView listView;

   @Inject
   @EntitySelectionEvent
   private Event<CashDrawer> selectionEvent;

   @Inject
   @EntitySearchRequestedEvent
   private Event<CashDrawer> searchRequestedEvent;

   @Inject
   @EntityCreateRequestedEvent
   private Event<CashDrawer> createRequestedEvent;

   @Inject
   @EntityListPageIndexChangedEvent
   private Event<CashDrawerSearchResult> entityListPageIndexChangedEvent;

   private CashDrawerSearchResult searchResult;

   @PostConstruct
   public void postConstruct()
   {
      listView.getDataList().getSelectionModel().selectedItemProperty()
            .addListener(new ChangeListener<CashDrawer>()
            {
               @Override
               public void changed(
                     ObservableValue<? extends CashDrawer> property,
                     CashDrawer oldValue, CashDrawer newValue)
               {
                  if (newValue != null)
                     selectionEvent.fire(newValue);
               }
            });

      /*
       * listen to search button and fire search activated event.
       */
      listView.getSearchButton().setOnAction(new EventHandler<ActionEvent>()
      {
         @Override
         public void handle(ActionEvent e)
         {
            CashDrawer selectedItem = listView.getDataList().getSelectionModel().getSelectedItem();
            if (selectedItem == null)
               selectedItem = new CashDrawer();
            searchRequestedEvent.fire(selectedItem);
         }
      });

      listView.getCreateButton().setOnAction(new EventHandler<ActionEvent>()
      {
         @Override
         public void handle(ActionEvent e)
         {
            CashDrawer selectedItem = listView.getDataList().getSelectionModel().getSelectedItem();
            if (selectedItem == null)
               selectedItem = new CashDrawer();
            createRequestedEvent.fire(selectedItem);
         }
      });

      listView.getPagination().currentPageIndexProperty().addListener(new ChangeListener<Number>()
      {
         @Override
         public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue)
         {
            if (searchResult == null)
               return;
            if (searchResult.getSearchInput() == null)
               searchResult.setSearchInput(new CashDrawerSearchInput());
            int start = 0;
            int max = searchResult.getSearchInput().getMax();
            if (newValue != null)
            {
               start = new BigDecimal(newValue.intValue()).multiply(new BigDecimal(max)).intValue();
            }
            searchResult.getSearchInput().setStart(start);
            entityListPageIndexChangedEvent.fire(searchResult);

         }
      });
   }

   @Override
   public void display(Pane parent)
   {
      AnchorPane rootPane = listView.getRootPane();
      ObservableList<Node> children = parent.getChildren();
      if (!children.contains(rootPane))
      {
         children.add(rootPane);
      }
   }

   @Override
   public ViewType getViewType()
   {
      return ViewType.LIST;
   }

   /**
    * Handle search results. But the switch of displays is centralized
    * in the main cashDrawer controller.
    * 
    * @param entities
    */
   public void handleSearchResult(@Observes @EntitySearchDoneEvent CashDrawerSearchResult searchResult)
   {
      this.searchResult = searchResult;
      List<CashDrawer> entities = searchResult.getResultList();
      if (entities == null)
         entities = new ArrayList<CashDrawer>();
      listView.getDataList().getItems().clear();
      listView.getDataList().getItems().addAll(entities);
      int maxResult = searchResult.getSearchInput() != null ? searchResult.getSearchInput().getMax() : 5;
      int pageCount = PaginationUtils.computePageCount(searchResult.getCount(), maxResult);
      listView.getPagination().setPageCount(pageCount);
      int firstResult = searchResult.getSearchInput() != null ? searchResult.getSearchInput().getStart() : 0;
      int pageIndex = PaginationUtils.computePageIndex(firstResult, searchResult.getCount(), maxResult);
      listView.getPagination().setCurrentPageIndex(pageIndex);

   }

   public void handleCreatedEvent(@Observes @EntityCreateDoneEvent CashDrawer createdEntity)
   {
      listView.getDataList().getItems().add(0, createdEntity);
   }

   public void handleRemovedEvent(@Observes @EntityRemoveDoneEvent CashDrawer removedEntity)
   {
      listView.getDataList().getItems().remove(removedEntity);
   }

   public void handleEditDoneEvent(@Observes @EntityEditDoneEvent CashDrawer selectedEntity)
   {
      int selectedIndex = listView.getDataList().getItems().indexOf(selectedEntity);
      if (selectedIndex <= -1)
         return;
      CashDrawer entity = listView.getDataList().getItems().get(selectedIndex);
      PropertyReader.copy(selectedEntity, entity);

      ArrayList<CashDrawer> arrayList = new ArrayList<CashDrawer>(listView.getDataList().getItems());
      listView.getDataList().getItems().clear();
      listView.getDataList().getItems().addAll(arrayList);
      listView.getDataList().getSelectionModel().select(selectedEntity);
   }

   public void handleEditCanceledEvent(@Observes @EntityEditCanceledEvent CashDrawer selectedEntity)
   {
      int selectedIndex = listView.getDataList().getItems().indexOf(selectedEntity);
      if (selectedIndex <= -1)
         return;
      CashDrawer entity = listView.getDataList().getItems().get(selectedIndex);
      PropertyReader.copy(selectedEntity, entity);

      ArrayList<CashDrawer> arrayList = new ArrayList<CashDrawer>(listView.getDataList().getItems());
      listView.getDataList().getItems().clear();
      listView.getDataList().getItems().addAll(arrayList);
      listView.getDataList().getSelectionModel().select(selectedEntity);
   }

}