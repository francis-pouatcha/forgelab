package org.adorsys.adpharma.client.jpa.payment;

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
public class PaymentListController implements EntityController
{

   @Inject
   private PaymentListView listView;

   @Inject
   @EntitySelectionEvent
   private Event<Payment> selectionEvent;

   @Inject
   @EntitySearchRequestedEvent
   private Event<Payment> searchRequestedEvent;

   @Inject
   @EntityCreateRequestedEvent
   private Event<Payment> createRequestedEvent;

   @Inject
   @EntityListPageIndexChangedEvent
   private Event<PaymentSearchResult> entityListPageIndexChangedEvent;

   private PaymentSearchResult searchResult;

   @PostConstruct
   public void postConstruct()
   {
      listView.getDataList().getSelectionModel().selectedItemProperty()
            .addListener(new ChangeListener<Payment>()
            {
               @Override
               public void changed(
                     ObservableValue<? extends Payment> property,
                     Payment oldValue, Payment newValue)
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
            Payment selectedItem = listView.getDataList().getSelectionModel().getSelectedItem();
            if (selectedItem == null)
               selectedItem = new Payment();
            searchRequestedEvent.fire(selectedItem);
         }
      });

      listView.getCreateButton().setOnAction(new EventHandler<ActionEvent>()
      {
         @Override
         public void handle(ActionEvent e)
         {
            Payment selectedItem = listView.getDataList().getSelectionModel().getSelectedItem();
            if (selectedItem == null)
               selectedItem = new Payment();
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
               searchResult.setSearchInput(new PaymentSearchInput());
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
    * in the main payment controller.
    * 
    * @param entities
    */
   public void handleSearchResult(@Observes @EntitySearchDoneEvent PaymentSearchResult searchResult)
   {
      this.searchResult = searchResult;
      List<Payment> entities = searchResult.getResultList();
      if (entities == null)
         entities = new ArrayList<Payment>();
      listView.getDataList().getItems().clear();
      listView.getDataList().getItems().addAll(entities);
      int maxResult = searchResult.getSearchInput() != null ? searchResult.getSearchInput().getMax() : 5;
      int pageCount = PaginationUtils.computePageCount(searchResult.getCount(), maxResult);
      listView.getPagination().setPageCount(pageCount);
      int firstResult = searchResult.getSearchInput() != null ? searchResult.getSearchInput().getStart() : 0;
      int pageIndex = PaginationUtils.computePageIndex(firstResult, searchResult.getCount(), maxResult);
      listView.getPagination().setCurrentPageIndex(pageIndex);

   }

   public void handleCreatedEvent(@Observes @EntityCreateDoneEvent Payment createdEntity)
   {
      listView.getDataList().getItems().add(0, createdEntity);
   }

   public void handleRemovedEvent(@Observes @EntityRemoveDoneEvent Payment removedEntity)
   {
      listView.getDataList().getItems().remove(removedEntity);
   }

   public void handleEditDoneEvent(@Observes @EntityEditDoneEvent Payment selectedEntity)
   {
      int selectedIndex = listView.getDataList().getItems().indexOf(selectedEntity);
      if (selectedIndex <= -1)
         return;
      Payment entity = listView.getDataList().getItems().get(selectedIndex);
      PropertyReader.copy(selectedEntity, entity);

      ArrayList<Payment> arrayList = new ArrayList<Payment>(listView.getDataList().getItems());
      listView.getDataList().getItems().clear();
      listView.getDataList().getItems().addAll(arrayList);
      listView.getDataList().getSelectionModel().select(selectedEntity);
   }

   public void handleEditCanceledEvent(@Observes @EntityEditCanceledEvent Payment selectedEntity)
   {
      int selectedIndex = listView.getDataList().getItems().indexOf(selectedEntity);
      if (selectedIndex <= -1)
         return;
      Payment entity = listView.getDataList().getItems().get(selectedIndex);
      PropertyReader.copy(selectedEntity, entity);

      ArrayList<Payment> arrayList = new ArrayList<Payment>(listView.getDataList().getItems());
      listView.getDataList().getItems().clear();
      listView.getDataList().getItems().addAll(arrayList);
      listView.getDataList().getSelectionModel().select(selectedEntity);
   }

}