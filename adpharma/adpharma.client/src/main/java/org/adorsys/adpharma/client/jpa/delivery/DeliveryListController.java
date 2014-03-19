
package org.adorsys.adpharma.client.jpa.delivery;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.adpharma.client.jpa.article.ArticleSearchResult;
import org.adorsys.adpharma.client.jpa.article.ArticleSearchService;
import org.adorsys.adpharma.client.jpa.supplier.Supplier;
import org.adorsys.adpharma.client.jpa.supplier.SupplierLoadService;
import org.adorsys.adpharma.client.jpa.supplier.SupplierSearchInput;
import org.adorsys.adpharma.client.jpa.supplier.SupplierSearchResult;
import org.adorsys.adpharma.client.jpa.supplier.SupplierSearchService;
import org.adorsys.javafx.crud.extensions.EntityController;
import org.adorsys.javafx.crud.extensions.ViewType;
import org.adorsys.javafx.crud.extensions.events.EntityCreateDoneEvent;
import org.adorsys.javafx.crud.extensions.events.EntityCreateRequestedEvent;
import org.adorsys.javafx.crud.extensions.events.EntityEditCanceledEvent;
import org.adorsys.javafx.crud.extensions.events.EntityEditDoneEvent;
import org.adorsys.javafx.crud.extensions.events.EntityEditRequestedEvent;
import org.adorsys.javafx.crud.extensions.events.EntityListPageIndexChangedEvent;
import org.adorsys.javafx.crud.extensions.events.EntityRemoveDoneEvent;
import org.adorsys.javafx.crud.extensions.events.EntitySearchDoneEvent;
import org.adorsys.javafx.crud.extensions.events.EntitySearchRequestedEvent;
import org.adorsys.javafx.crud.extensions.events.EntitySelectionEvent;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;
import org.adorsys.javafx.crud.extensions.utils.PaginationUtils;
import org.controlsfx.dialog.Dialogs;

@Singleton
public class DeliveryListController implements EntityController
{

	@Inject
	private DeliveryListView listView;

	@Inject
	@EntitySelectionEvent
	private Event<Delivery> selectionEvent;

	@Inject
	@EntitySearchRequestedEvent
	private Event<Delivery> searchRequestedEvent;

	@Inject
	@EntityCreateRequestedEvent
	private Event<Delivery> createRequestedEvent;

	@Inject
	SupplierSearchService supplierSearchService;

	@Inject
	DeliverySearchService searchService;



	@Inject
	@EntityListPageIndexChangedEvent
	private Event<DeliverySearchResult> entityListPageIndexChangedEvent;

	@Inject
	@EntityEditRequestedEvent
	private Event<Delivery> deliveryEditEvent;

	private DeliverySearchResult searchResult;

	@Inject 
	DeliverySearchInput searchInput;

	@PostConstruct
	public void postConstruct()
	{
		listView.bind(searchInput);

		listView.getDataList().getSelectionModel().selectedItemProperty()
		.addListener(new ChangeListener<Delivery>()
				{
			@Override
			public void changed(
					ObservableValue<? extends Delivery> property,
					Delivery oldValue, Delivery newValue)
			{
				if (newValue != null){}
				//	selectionEvent.fire(newValue);
			}
				});
		listView.getProcessButton().setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Delivery selectedItem = listView.getDataList().getSelectionModel().getSelectedItem();
				if(selectedItem== null) return ;
                 deliveryEditEvent.fire(selectedItem);
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
				searchService.setSearchInputs(searchInput).start();

			}


				});
		supplierSearchService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				SupplierSearchService s = (SupplierSearchService) event.getSource();
				SupplierSearchResult result = s.getValue();
				event.consume();
				s.reset();
				List<Supplier> resultList = result.getResultList();
				listView.getSupplier().getItems().setAll(resultList);
				listView.getSupplier().getSelectionModel().clearSelection();

			}
		});
		supplierSearchService.setOnFailed(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				SupplierSearchService s = (SupplierSearchService) event.getSource();
				s.reset();				
			}
		});
		searchService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				DeliverySearchService s = (DeliverySearchService) event.getSource();
				DeliverySearchResult result = s.getValue();
				event.consume();
				s.reset();
				List<Delivery> resultList = result.getResultList();
				listView.getDataList().getItems().setAll(resultList);

			}
		});

		searchService.setOnFailed(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				DeliverySearchService s = (DeliverySearchService) event.getSource();
				s.reset();				
			}
		});

		listView.getCreateButton().setOnAction(new EventHandler<ActionEvent>()
				{
			@Override
			public void handle(ActionEvent e)
			{
				Delivery selectedItem = listView.getDataList().getSelectionModel().getSelectedItem();
				if (selectedItem == null)
					selectedItem = new Delivery();
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
					searchResult.setSearchInput(new DeliverySearchInput());
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

	public void onDisplayed(){
		supplierSearchService.setSearchInputs(new SupplierSearchInput()).start();

	}
	@Override
	public void display(Pane parent)
	{
		onDisplayed();
		BorderPane rootPane = listView.getRootPane();
		ObservableList<Node> children = parent.getChildren();
		if (!children.contains(rootPane))
		{
			children.add(rootPane);
		}
	}

	public void beforeShowing(){

	}

	@Override
	public ViewType getViewType()
	{
		return ViewType.LIST;
	}

	/**
	 * Handle search results. But the switch of displays is centralized
	 * in the main delivery controller.
	 * 
	 * @param entities
	 */
	public void handleSearchResult(@Observes @EntitySearchDoneEvent DeliverySearchResult searchResult)
	{
		this.searchResult = searchResult;
		List<Delivery> entities = searchResult.getResultList();
		if (entities == null)
			entities = new ArrayList<Delivery>();
		listView.getDataList().getItems().clear();
		listView.getDataList().getItems().addAll(entities);
		int maxResult = searchResult.getSearchInput() != null ? searchResult.getSearchInput().getMax() : 5;
		int pageCount = PaginationUtils.computePageCount(searchResult.getCount(), maxResult);
		listView.getPagination().setPageCount(pageCount);
		int firstResult = searchResult.getSearchInput() != null ? searchResult.getSearchInput().getStart() : 0;
		int pageIndex = PaginationUtils.computePageIndex(firstResult, searchResult.getCount(), maxResult);
		listView.getPagination().setCurrentPageIndex(pageIndex);

	}

	public void handleCreatedEvent(@Observes @EntityCreateDoneEvent Delivery createdEntity)
	{
		listView.getDataList().getItems().add(0, createdEntity);
	}

	public void handleRemovedEvent(@Observes @EntityRemoveDoneEvent Delivery removedEntity)
	{
		listView.getDataList().getItems().remove(removedEntity);
	}

	public void handleEditDoneEvent(@Observes @EntityEditDoneEvent Delivery selectedEntity)
	{
		int selectedIndex = listView.getDataList().getItems().indexOf(selectedEntity);
		if (selectedIndex <= -1)
			return;
		Delivery entity = listView.getDataList().getItems().get(selectedIndex);
		PropertyReader.copy(selectedEntity, entity);

		ArrayList<Delivery> arrayList = new ArrayList<Delivery>(listView.getDataList().getItems());
		listView.getDataList().getItems().clear();
		listView.getDataList().getItems().addAll(arrayList);
		listView.getDataList().getSelectionModel().select(selectedEntity);
	}

	public void handleEditCanceledEvent(@Observes @EntityEditCanceledEvent Delivery selectedEntity)
	{
		int selectedIndex = listView.getDataList().getItems().indexOf(selectedEntity);
		if (selectedIndex <= -1)
			return;
		Delivery entity = listView.getDataList().getItems().get(selectedIndex);
		PropertyReader.copy(selectedEntity, entity);

		ArrayList<Delivery> arrayList = new ArrayList<Delivery>(listView.getDataList().getItems());
		listView.getDataList().getItems().clear();
		listView.getDataList().getItems().addAll(arrayList);
		listView.getDataList().getSelectionModel().select(selectedEntity);
	}

}