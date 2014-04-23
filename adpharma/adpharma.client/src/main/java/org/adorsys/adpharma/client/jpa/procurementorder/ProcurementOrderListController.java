package org.adorsys.adpharma.client.jpa.procurementorder;

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

import org.adorsys.adpharma.client.jpa.documentprocessingstate.DocumentProcessingState;
import org.adorsys.adpharma.client.jpa.procurementorderitem.ProcurementOrderItem;
import org.adorsys.adpharma.client.jpa.procurementorderitem.ProcurementOrderItemProcurementOrder;
import org.adorsys.adpharma.client.jpa.procurementorderitem.ProcurementOrderItemSearchInput;
import org.adorsys.adpharma.client.jpa.procurementorderitem.ProcurementOrderItemSearchResult;
import org.adorsys.adpharma.client.jpa.procurementorderitem.ProcurementOrderItemSearchService;
import org.adorsys.adpharma.client.jpa.supplier.Supplier;
import org.adorsys.adpharma.client.jpa.supplier.SupplierSearchInput;
import org.adorsys.adpharma.client.jpa.supplier.SupplierSearchResult;
import org.adorsys.adpharma.client.jpa.supplier.SupplierSearchService;
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
import org.apache.commons.lang3.StringUtils;

@Singleton
public class ProcurementOrderListController implements EntityController
{

	@Inject
	private ProcurementOrderListView listView;

	@Inject
	@EntitySelectionEvent
	private Event<ProcurementOrder> selectionEvent;

	@Inject
	@EntitySearchRequestedEvent
	private Event<ProcurementOrder> searchRequestedEvent;

	@Inject
	@EntityCreateRequestedEvent
	private Event<ProcurementOrder> createRequestedEvent;

	@Inject
	@EntityListPageIndexChangedEvent
	private Event<ProcurementOrderSearchResult> entityListPageIndexChangedEvent;

	@Inject
	private SupplierSearchService supplierSearchService;

	@Inject
	private ProcurementOrderSearchService searchService ;

	@Inject
	private ProcurementOrderItemSearchService itemSearchService ;

	@Inject
	private ProcurementOrderSearchInput searchInput;

	@Inject
	private ProcurementOrderSearchResult searchResult;

	@Inject
	private ProcurementOrderRegistration registration;

	@PostConstruct
	public void postConstruct()
	{
		//      listView.getCreateButton().disableProperty().bind(registration.canCreateProperty().not());
		searchInput.setMax(30);
		listView.bind(searchInput);

		listView.getDataList().getSelectionModel().selectedItemProperty()
		.addListener(new ChangeListener<ProcurementOrder>()
				{
			@Override
			public void changed(
					ObservableValue<? extends ProcurementOrder> property,
					ProcurementOrder oldValue, ProcurementOrder newValue)
			{
				if (newValue != null){
					ProcurementOrderItemSearchInput poi = new ProcurementOrderItemSearchInput();
					poi.getEntity().setProcurementOrder(new ProcurementOrderItemProcurementOrder(newValue));
					poi.setMax(-1);
					itemSearchService.setSearchInputs(poi).start();
				}
			}
				});

		itemSearchService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				ProcurementOrderItemSearchService s = (ProcurementOrderItemSearchService) event.getSource();
				ProcurementOrderItemSearchResult value = s.getValue();
				event.consume();
				s.reset();
				List<ProcurementOrderItem> resultList = value.getResultList();
				listView.getDataListItem().getItems().setAll(resultList);
			}
		});
		itemSearchService.setOnFailed(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				ProcurementOrderItemSearchService s = (ProcurementOrderItemSearchService) event.getSource();
				s.reset();				
			}
		});

		listView.getSupplier().armedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable,
					Boolean oldValue, Boolean newValue) {
				if (newValue){
					SupplierSearchInput supplierSearchInput = new SupplierSearchInput();
					supplierSearchInput.setMax(-1);
					supplierSearchService.setSearchInputs(supplierSearchInput).start();

				}


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
				searchInput.setFieldNames(readSearchAttributes());
				searchInput.setMax(30);
				searchService.setSearchInputs(searchInput).start();
			}
				});

		searchService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				ProcurementOrderSearchService s = (ProcurementOrderSearchService) event.getSource();
				searchResult  = s.getValue();
				event.consume();
				s.reset();
				handleSearchResult(searchResult);
			}
		});
		searchService.setOnFailed(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				ProcurementOrderSearchService s = (ProcurementOrderSearchService) event.getSource();
				s.reset();				
			}
		});

		supplierSearchService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				SupplierSearchService s = (SupplierSearchService) event.getSource();
				SupplierSearchResult value = s.getValue();
				event.consume();
				s.reset();
				List<Supplier> resultList = value.getResultList();
				ArrayList<ProcurementOrderSupplier> arrayList = new ArrayList<>();
				for (Supplier supplier : resultList) {
					arrayList.add(new ProcurementOrderSupplier(supplier) );
				}
				arrayList.add(0,new ProcurementOrderSupplier());
				listView.getSupplier().getItems().setAll(arrayList);

			}
		});
		supplierSearchService.setOnFailed(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				SupplierSearchService s = (SupplierSearchService) event.getSource();
				s.reset();				
			}
		});

		//      listView.getCreateButton().setOnAction(new EventHandler<ActionEvent>()
		//      {
		//         @Override
		//         public void handle(ActionEvent e)
		//         {
		//            ProcurementOrder selectedItem = listView.getDataList().getSelectionModel().getSelectedItem();
		//            if (selectedItem == null)
		//               selectedItem = new ProcurementOrder();
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
					searchResult.setSearchInput(new ProcurementOrderSearchInput());
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
	 * in the main procurementOrder controller.
	 * 
	 * @param entities
	 */
	public void handleSearchResult(@Observes @EntitySearchDoneEvent ProcurementOrderSearchResult searchResult)
	{
		this.searchResult = searchResult;
		List<ProcurementOrder> entities = searchResult.getResultList();
		if (entities == null)
			entities = new ArrayList<ProcurementOrder>();
		listView.getDataList().getItems().clear();
		listView.getDataList().getItems().addAll(entities);
		int maxResult = searchResult.getSearchInput() != null ? searchResult.getSearchInput().getMax() : 5;
		int pageCount = PaginationUtils.computePageCount(searchResult.getCount(), maxResult);
		listView.getPagination().setPageCount(pageCount);
		int firstResult = searchResult.getSearchInput() != null ? searchResult.getSearchInput().getStart() : 0;
		int pageIndex = PaginationUtils.computePageIndex(firstResult, searchResult.getCount(), maxResult);
		listView.getPagination().setCurrentPageIndex(pageIndex);

	}

	public void handleCreatedEvent(@Observes @EntityCreateDoneEvent ProcurementOrder createdEntity)
	{
		listView.getDataList().getItems().add(0, createdEntity);
	}

	public void handleRemovedEvent(@Observes @EntityRemoveDoneEvent ProcurementOrder removedEntity)
	{
		listView.getDataList().getItems().remove(removedEntity);
	}

	public void handleEditDoneEvent(@Observes @EntityEditDoneEvent ProcurementOrder selectedEntity)
	{
		int selectedIndex = listView.getDataList().getItems().indexOf(selectedEntity);
		if (selectedIndex <= -1)
			return;
		ProcurementOrder entity = listView.getDataList().getItems().get(selectedIndex);
		PropertyReader.copy(selectedEntity, entity);

		ArrayList<ProcurementOrder> arrayList = new ArrayList<ProcurementOrder>(listView.getDataList().getItems());
		listView.getDataList().getItems().clear();
		listView.getDataList().getItems().addAll(arrayList);
		listView.getDataList().getSelectionModel().select(selectedEntity);
	}

	public void handleEditCanceledEvent(@Observes @EntityEditCanceledEvent ProcurementOrder selectedEntity)
	{
		int selectedIndex = listView.getDataList().getItems().indexOf(selectedEntity);
		if (selectedIndex <= -1)
			return;
		ProcurementOrder entity = listView.getDataList().getItems().get(selectedIndex);
		PropertyReader.copy(selectedEntity, entity);

		ArrayList<ProcurementOrder> arrayList = new ArrayList<ProcurementOrder>(listView.getDataList().getItems());
		listView.getDataList().getItems().clear();
		listView.getDataList().getItems().addAll(arrayList);
		listView.getDataList().getSelectionModel().select(selectedEntity);
	}

	public List<String> readSearchAttributes(){
		ArrayList<String> seachAttributes = new ArrayList<String>() ;
		String procurementOrderNumber = searchInput.getEntity().getProcurementOrderNumber();
		ProcurementOrderSupplier supplier = searchInput.getEntity().getSupplier();
		DocumentProcessingState poStatus = searchInput.getEntity().getPoStatus();

		if(StringUtils.isNotBlank(procurementOrderNumber)) seachAttributes.add("procurementOrderNumber");
		if(supplier!=null && supplier.getId()!=null) seachAttributes.add("supplier");
		if(poStatus!=null) seachAttributes.add("poStatus");
		return seachAttributes;

	}

}