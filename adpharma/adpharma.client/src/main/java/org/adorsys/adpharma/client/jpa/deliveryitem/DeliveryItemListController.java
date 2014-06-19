package org.adorsys.adpharma.client.jpa.deliveryitem;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.concurrent.Worker;
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

import org.adorsys.adpharma.client.jpa.delivery.Delivery;
import org.adorsys.adpharma.client.jpa.delivery.DeliverySearchInput;
import org.adorsys.adpharma.client.jpa.delivery.DeliverySearchService;
import org.adorsys.adpharma.client.jpa.documentprocessingstate.DocumentProcessingState;
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
import org.adorsys.javafx.crud.extensions.login.ErrorDisplay;
import org.adorsys.javafx.crud.extensions.login.ServiceCallFailedEventHandler;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;
import org.adorsys.javafx.crud.extensions.utils.PaginationUtils;
import org.apache.commons.lang3.StringUtils;
import org.controlsfx.dialog.Dialogs;

@Singleton
public class DeliveryItemListController implements EntityController
{

   @Inject
   private DeliveryItemListView listView;

   @Inject
   @EntitySelectionEvent
   private Event<DeliveryItem> selectionEvent;

   @Inject
   @EntitySearchRequestedEvent
   private Event<DeliveryItem> searchRequestedEvent;

   @Inject
   @EntityCreateRequestedEvent
   private Event<DeliveryItem> createRequestedEvent;

   @Inject
   @EntityListPageIndexChangedEvent
   private Event<DeliveryItemSearchResult> entityListPageIndexChangedEvent;

   @Inject
   private DeliveryItemSearchResult searchResult;

   @Inject
   private DeliveryItemRegistration registration;
   
   @Inject
   private DeliverySearchService deliverySearchService;
   
   @Inject
   private DeliveryItemSearchService deliveryItemSearchService;
   
   @Inject
   private ServiceCallFailedEventHandler callFailedEventHandler ;
   

   @PostConstruct
   public void postConstruct()
   {
//      listView.getCreateButton().disableProperty().bind(registration.canCreateProperty().not());

	   callFailedEventHandler.setErrorDisplay(new ErrorDisplay() {
		
		@Override
		protected void showError(Throwable exception) {
			Dialogs.create().showException(exception);
			
		}
	});
	   
      listView.getDataList().getSelectionModel().selectedItemProperty()
            .addListener(new ChangeListener<DeliveryItem>()
            {
               @Override
               public void changed(
                     ObservableValue<? extends DeliveryItem> property,
                     DeliveryItem oldValue, DeliveryItem newValue)
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
           String deliverNumber = listView.getDeliveryNumber().getText();
           if(StringUtils.isNotBlank(deliverNumber)){
        	   DeliverySearchInput deliverySearchInput = new DeliverySearchInput();
        	   deliverySearchInput.getEntity().setDeliveryNumber(deliverNumber);
        	   deliverySearchInput.getEntity().setDeliveryProcessingState(DocumentProcessingState.CLOSED);
        	   deliverySearchInput.getFieldNames().add("deliveryNumber");
        	   deliverySearchInput.getFieldNames().add("deliveryProcessingState");
        	   deliverySearchService.setSearchInputs(deliverySearchInput).start();
           }
         }
      });
      deliverySearchService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
		
		@Override
		public void handle(WorkerStateEvent event) {
			DeliverySearchService source = (DeliverySearchService) event.getSource();
			List<Delivery> resultList = source.getValue().getResultList();
			event.consume();
			source.reset();
			if(!resultList.isEmpty()){
				DeliveryItemSearchInput deliveryItemSearchInput = new DeliveryItemSearchInput();
				Delivery next = resultList.iterator().next();
				deliveryItemSearchInput.getEntity().setDelivery(new DeliveryItemDelivery(next));
				deliveryItemSearchInput.getFieldNames().add("delivery");
				deliveryItemSearchInput.setMax(-1);
				deliveryItemSearchService.setSearchInputs(deliveryItemSearchInput).start();
			}
			
			
		}
	});
      deliverySearchService.setOnFailed(callFailedEventHandler);
      
      deliveryItemSearchService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
  		
  		@Override
  		public void handle(WorkerStateEvent event) {
  			DeliveryItemSearchService source = (DeliveryItemSearchService) event.getSource();
  			DeliveryItemSearchResult deliveryItemSearchResult = source.getValue();
  			event.consume();
  			source.reset();
  			handleSearchResult(deliveryItemSearchResult);
  		}
  	});
      deliveryItemSearchService.setOnFailed(callFailedEventHandler);

//      listView.getCreateButton().setOnAction(new EventHandler<ActionEvent>()
//      {
//         @Override
//         public void handle(ActionEvent e)
//         {
//            DeliveryItem selectedItem = listView.getDataList().getSelectionModel().getSelectedItem();
//            if (selectedItem == null)
//               selectedItem = new DeliveryItem();
//            createRequestedEvent.fire(selectedItem);
//         }
//      });

      listView.getPagination().currentPageIndexProperty().addListener(new ChangeListener<Number>()
      {
         @Override
         public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue)
         {
            if (searchResult == null)
               return;
            if (searchResult.getSearchInput() == null)
               searchResult.setSearchInput(new DeliveryItemSearchInput());
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
    * in the main deliveryItem controller.
    * 
    * @param entities
    */
   public void handleSearchResult(@Observes @EntitySearchDoneEvent DeliveryItemSearchResult searchResult)
   {
      this.searchResult = searchResult;
      List<DeliveryItem> entities = searchResult.getResultList();
      if (entities == null)
         entities = new ArrayList<DeliveryItem>();
      listView.getDataList().getItems().clear();
      listView.getDataList().getItems().addAll(entities);
      int maxResult = searchResult.getSearchInput() != null ? searchResult.getSearchInput().getMax() : 5;
      int pageCount = PaginationUtils.computePageCount(searchResult.getCount(), maxResult);
      listView.getPagination().setPageCount(pageCount);
      int firstResult = searchResult.getSearchInput() != null ? searchResult.getSearchInput().getStart() : 0;
      int pageIndex = PaginationUtils.computePageIndex(firstResult, searchResult.getCount(), maxResult);
      listView.getPagination().setCurrentPageIndex(pageIndex);

   }

   public void handleCreatedEvent(@Observes @EntityCreateDoneEvent DeliveryItem createdEntity)
   {
      listView.getDataList().getItems().add(0, createdEntity);
   }

   public void handleRemovedEvent(@Observes @EntityRemoveDoneEvent DeliveryItem removedEntity)
   {
      listView.getDataList().getItems().remove(removedEntity);
   }

   public void handleEditDoneEvent(@Observes @EntityEditDoneEvent DeliveryItem selectedEntity)
   {
      int selectedIndex = listView.getDataList().getItems().indexOf(selectedEntity);
      if (selectedIndex <= -1)
         return;
      DeliveryItem entity = listView.getDataList().getItems().get(selectedIndex);
      PropertyReader.copy(selectedEntity, entity);

      ArrayList<DeliveryItem> arrayList = new ArrayList<DeliveryItem>(listView.getDataList().getItems());
      listView.getDataList().getItems().clear();
      listView.getDataList().getItems().addAll(arrayList);
      listView.getDataList().getSelectionModel().select(selectedEntity);
   }

   public void handleEditCanceledEvent(@Observes @EntityEditCanceledEvent DeliveryItem selectedEntity)
   {
      int selectedIndex = listView.getDataList().getItems().indexOf(selectedEntity);
      if (selectedIndex <= -1)
         return;
      DeliveryItem entity = listView.getDataList().getItems().get(selectedIndex);
      PropertyReader.copy(selectedEntity, entity);

      ArrayList<DeliveryItem> arrayList = new ArrayList<DeliveryItem>(listView.getDataList().getItems());
      listView.getDataList().getItems().clear();
      listView.getDataList().getItems().addAll(arrayList);
      listView.getDataList().getSelectionModel().select(selectedEntity);
   }

	public void reset() {
		   listView.getDataList().getItems().clear();
		}
}
