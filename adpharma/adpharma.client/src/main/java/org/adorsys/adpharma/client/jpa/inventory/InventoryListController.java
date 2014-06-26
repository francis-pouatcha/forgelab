package org.adorsys.adpharma.client.jpa.inventory;

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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.adpharma.client.events.PrintRequestedEvent;
import org.adorsys.adpharma.client.jpa.documentprocessingstate.DocumentProcessingState;
import org.adorsys.adpharma.client.jpa.inventoryitem.InventoryItemInventory;
import org.adorsys.adpharma.client.jpa.inventoryitem.InventoryItemSearchInput;
import org.adorsys.adpharma.client.jpa.inventoryitem.InventoryItemSearchResult;
import org.adorsys.adpharma.client.jpa.inventoryitem.InventoryItemSearchService;
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
import org.adorsys.javafx.crud.extensions.login.ServiceCallFailedEventHandler;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;
import org.adorsys.javafx.crud.extensions.utils.PaginationUtils;
import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.Dialogs;

@Singleton
public class InventoryListController implements EntityController
{

	@Inject
	private InventoryListView listView;

	@Inject
	@EntitySelectionEvent
	private Event<Inventory> selectionEvent;

	@Inject
	@EntitySearchRequestedEvent
	private Event<Inventory> searchRequestedEvent;

	@Inject
	@EntityCreateRequestedEvent
	private Event<Inventory> createRequestedEvent;

	@Inject
	@EntityListPageIndexChangedEvent
	private Event<InventorySearchResult> entityListPageIndexChangedEvent;

	private InventorySearchResult searchResult;

	@Inject
	private InventoryRegistration registration;

	@Inject
	private InventorySearchService inventorySearchService ;

	@Inject
	private InventoryItemSearchService itemSearchService ;

	@Inject
	private InventoryRemoveService inventoryRemoveService;

	@Inject
	private ServiceCallFailedEventHandler callFailedEventHandler ;

	@Inject
	private InventorySearchInput searchInput ;
	@Inject
	@PrintRequestedEvent
	private Event<InventoryRepportData> printRequest;


	@PostConstruct
	public void postConstruct()
	{
		listView.getCreateButton().disableProperty().bind(registration.canCreateProperty().not());
		listView.getSearchButton().disableProperty().bind(inventorySearchService.runningProperty());
		listView.getSearchButton().disableProperty().bind(itemSearchService.runningProperty());

		listView.getDataList().getSelectionModel().selectedItemProperty()
		.addListener(new ChangeListener<Inventory>()
				{
			@Override
			public void changed(
					ObservableValue<? extends Inventory> property,
					Inventory oldValue, Inventory newValue)
			{
				if (newValue != null) {
					InventoryItemSearchInput itemSearchInput = new InventoryItemSearchInput();
					itemSearchInput.getEntity().setInventory(new InventoryItemInventory(newValue));
					itemSearchInput.getFieldNames().add("inventory");
					itemSearchInput.setMax(-1);
					itemSearchService.setSearchInputs(itemSearchInput).start();
				}
			}
				});
		listView.getEditButton().setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Inventory selectedItem = listView.getDataList().getSelectionModel().getSelectedItem();
				if(selectedItem!=null)
					selectionEvent.fire(selectedItem);

			}
		});
		listView.getPrintButton().setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Inventory inventory = listView.getDataList().getSelectionModel().getSelectedItem();
				if(inventory!=null){
					InventoryRepportData inventoryRepportData = new InventoryRepportData(inventory);
					printRequest.fire(inventoryRepportData);
				}

			}
		});
		listView.getPrintRepportButton().setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Inventory inventory = listView.getDataList().getSelectionModel().getSelectedItem();
				if(inventory!=null){
					InventoryRepportData inventoryRepportData = new InventoryRepportData(inventory);
					inventoryRepportData.setCountRepport(false);
					printRequest.fire(inventoryRepportData);
				}

			}
		});
		listView.getRemoveButton().setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Inventory selectedItem = listView.getDataList().getSelectionModel().getSelectedItem();
				Action showConfirm = Dialogs.create().message("etes vous sur de vouloir suprimer ?").showConfirm();
				if(Dialog.Actions.YES.equals(showConfirm))
					if(selectedItem!=null){
						if(!DocumentProcessingState.CLOSED.equals(selectedItem.getInventoryStatus())){
							inventoryRemoveService.setEntity(selectedItem).start();
						}else {
							Dialogs.create().message("Impossible de suprimer deja cloturer ?").showError();
						}
					}

			}
		});
		inventoryRemoveService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				InventoryRemoveService source = (InventoryRemoveService) event.getSource();
				Inventory result = source.getValue();
				source.reset();
				event.consume();
				listView.getDataListItem().getItems().clear();
				listView.getDataList().getItems().remove(result);


			}
		});
		inventoryRemoveService.setOnFailed(callFailedEventHandler);
		itemSearchService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				InventoryItemSearchService source = (InventoryItemSearchService) event.getSource();
				InventoryItemSearchResult result = source.getValue();
				source.reset();
				event.consume();
				listView.getDataListItem().getItems().setAll(result.getResultList());
			}
		});
		itemSearchService.setOnFailed(callFailedEventHandler);

		/*
		 * listen to search button and fire search activated event.
		 */
		listView.getSearchButton().setOnAction(new EventHandler<ActionEvent>()
				{
			@Override
			public void handle(ActionEvent e)
			{
				inventorySearchService.setSearchInputs(searchInput).start();
			}
				});

		inventorySearchService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				InventorySearchService source = (InventorySearchService) event.getSource();
				InventorySearchResult result = source.getValue();
				handleSearchResult(result);
				source.reset();
				event.consume();


			}
		});
		inventorySearchService.setOnFailed(callFailedEventHandler);

		listView.getCreateButton().setOnAction(new EventHandler<ActionEvent>()
				{
			@Override
			public void handle(ActionEvent e)
			{
				Inventory selectedItem = listView.getDataList().getSelectionModel().getSelectedItem();
				if (selectedItem == null)
					selectedItem = new Inventory();
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
					searchResult.setSearchInput(new InventorySearchInput());
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
		BorderPane rootPane = listView.getRootPane();
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
	 * in the main inventory controller.
	 * 
	 * @param entities
	 */
	public void handleSearchResult(@Observes @EntitySearchDoneEvent InventorySearchResult searchResult)
	{
		this.searchResult = searchResult;
		List<Inventory> entities = searchResult.getResultList();
		if (entities == null)
			entities = new ArrayList<Inventory>();
		listView.getDataList().getItems().clear();
		listView.getDataList().getItems().addAll(entities);
		int maxResult = searchResult.getSearchInput() != null ? searchResult.getSearchInput().getMax() : 5;
		int pageCount = PaginationUtils.computePageCount(searchResult.getCount(), maxResult);
		listView.getPagination().setPageCount(pageCount);
		int firstResult = searchResult.getSearchInput() != null ? searchResult.getSearchInput().getStart() : 0;
		int pageIndex = PaginationUtils.computePageIndex(firstResult, searchResult.getCount(), maxResult);
		listView.getPagination().setCurrentPageIndex(pageIndex);

	}

	public void handleCreatedEvent(@Observes @EntityCreateDoneEvent Inventory createdEntity)
	{
		listView.getDataList().getItems().add(0, createdEntity);
	}

	public void handleRemovedEvent(@Observes @EntityRemoveDoneEvent Inventory removedEntity)
	{
		listView.getDataList().getItems().remove(removedEntity);
	}

	public void handleEditDoneEvent(@Observes @EntityEditDoneEvent Inventory selectedEntity)
	{
		int selectedIndex = listView.getDataList().getItems().indexOf(selectedEntity);
		if (selectedIndex <= -1)
			return;
		Inventory entity = listView.getDataList().getItems().get(selectedIndex);
		PropertyReader.copy(selectedEntity, entity);

		ArrayList<Inventory> arrayList = new ArrayList<Inventory>(listView.getDataList().getItems());
		listView.getDataList().getItems().clear();
		listView.getDataList().getItems().addAll(arrayList);
		listView.getDataList().getSelectionModel().select(selectedEntity);
	}

	public void handleEditCanceledEvent(@Observes @EntityEditCanceledEvent Inventory selectedEntity)
	{
		int selectedIndex = listView.getDataList().getItems().indexOf(selectedEntity);
		if (selectedIndex <= -1)
			return;
		Inventory entity = listView.getDataList().getItems().get(selectedIndex);
		PropertyReader.copy(selectedEntity, entity);

		ArrayList<Inventory> arrayList = new ArrayList<Inventory>(listView.getDataList().getItems());
		listView.getDataList().getItems().clear();
		listView.getDataList().getItems().addAll(arrayList);
		listView.getDataList().getSelectionModel().select(selectedEntity);
	}

	public void reset() {
		listView.getDataList().getItems().clear();
	}
}
