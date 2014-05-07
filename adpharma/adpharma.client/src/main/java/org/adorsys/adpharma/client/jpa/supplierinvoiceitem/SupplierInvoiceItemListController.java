package org.adorsys.adpharma.client.jpa.supplierinvoiceitem;

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
public class SupplierInvoiceItemListController implements EntityController
{

   @Inject
   private SupplierInvoiceItemListView listView;

   @Inject
   @EntitySelectionEvent
   private Event<SupplierInvoiceItem> selectionEvent;

   @Inject
   @EntitySearchRequestedEvent
   private Event<SupplierInvoiceItem> searchRequestedEvent;

   @Inject
   @EntityCreateRequestedEvent
   private Event<SupplierInvoiceItem> createRequestedEvent;

   @Inject
   @EntityListPageIndexChangedEvent
   private Event<SupplierInvoiceItemSearchResult> entityListPageIndexChangedEvent;

   private SupplierInvoiceItemSearchResult searchResult;

   @Inject
   private SupplierInvoiceItemRegistration registration;

   @PostConstruct
   public void postConstruct()
   {
      listView.getCreateButton().disableProperty().bind(registration.canCreateProperty().not());

      listView.getDataList().getSelectionModel().selectedItemProperty()
            .addListener(new ChangeListener<SupplierInvoiceItem>()
            {
               @Override
               public void changed(
                     ObservableValue<? extends SupplierInvoiceItem> property,
                     SupplierInvoiceItem oldValue, SupplierInvoiceItem newValue)
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
            SupplierInvoiceItem selectedItem = listView.getDataList().getSelectionModel().getSelectedItem();
            if (selectedItem == null)
               selectedItem = new SupplierInvoiceItem();
            searchRequestedEvent.fire(selectedItem);
         }
      });

      listView.getCreateButton().setOnAction(new EventHandler<ActionEvent>()
      {
         @Override
         public void handle(ActionEvent e)
         {
            SupplierInvoiceItem selectedItem = listView.getDataList().getSelectionModel().getSelectedItem();
            if (selectedItem == null)
               selectedItem = new SupplierInvoiceItem();
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
               searchResult.setSearchInput(new SupplierInvoiceItemSearchInput());
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
    * in the main supplierInvoiceItem controller.
    * 
    * @param entities
    */
   public void handleSearchResult(@Observes @EntitySearchDoneEvent SupplierInvoiceItemSearchResult searchResult)
   {
      this.searchResult = searchResult;
      List<SupplierInvoiceItem> entities = searchResult.getResultList();
      if (entities == null)
         entities = new ArrayList<SupplierInvoiceItem>();
      listView.getDataList().getItems().clear();
      listView.getDataList().getItems().addAll(entities);
      int maxResult = searchResult.getSearchInput() != null ? searchResult.getSearchInput().getMax() : 5;
      int pageCount = PaginationUtils.computePageCount(searchResult.getCount(), maxResult);
      listView.getPagination().setPageCount(pageCount);
      int firstResult = searchResult.getSearchInput() != null ? searchResult.getSearchInput().getStart() : 0;
      int pageIndex = PaginationUtils.computePageIndex(firstResult, searchResult.getCount(), maxResult);
      listView.getPagination().setCurrentPageIndex(pageIndex);

   }

   public void handleCreatedEvent(@Observes @EntityCreateDoneEvent SupplierInvoiceItem createdEntity)
   {
      listView.getDataList().getItems().add(0, createdEntity);
   }

   public void handleRemovedEvent(@Observes @EntityRemoveDoneEvent SupplierInvoiceItem removedEntity)
   {
      listView.getDataList().getItems().remove(removedEntity);
   }

   public void handleEditDoneEvent(@Observes @EntityEditDoneEvent SupplierInvoiceItem selectedEntity)
   {
      int selectedIndex = listView.getDataList().getItems().indexOf(selectedEntity);
      if (selectedIndex <= -1)
         return;
      SupplierInvoiceItem entity = listView.getDataList().getItems().get(selectedIndex);
      PropertyReader.copy(selectedEntity, entity);

      ArrayList<SupplierInvoiceItem> arrayList = new ArrayList<SupplierInvoiceItem>(listView.getDataList().getItems());
      listView.getDataList().getItems().clear();
      listView.getDataList().getItems().addAll(arrayList);
      listView.getDataList().getSelectionModel().select(selectedEntity);
   }

   public void handleEditCanceledEvent(@Observes @EntityEditCanceledEvent SupplierInvoiceItem selectedEntity)
   {
      int selectedIndex = listView.getDataList().getItems().indexOf(selectedEntity);
      if (selectedIndex <= -1)
         return;
      SupplierInvoiceItem entity = listView.getDataList().getItems().get(selectedIndex);
      PropertyReader.copy(selectedEntity, entity);

      ArrayList<SupplierInvoiceItem> arrayList = new ArrayList<SupplierInvoiceItem>(listView.getDataList().getItems());
      listView.getDataList().getItems().clear();
      listView.getDataList().getItems().addAll(arrayList);
      listView.getDataList().getSelectionModel().select(selectedEntity);
   }

	public void reset() {
		   listView.getDataList().getItems().clear();
		}
}
