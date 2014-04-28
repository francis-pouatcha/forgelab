package org.adorsys.adpharma.client.jpa.salesorder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.adpharma.client.events.PrintCustomerInvoiceRequestedEvent;
import org.adorsys.adpharma.client.events.SalesOrderId;
import org.adorsys.adpharma.client.jpa.customer.Customer;
import org.adorsys.adpharma.client.jpa.customer.CustomerSearchInput;
import org.adorsys.adpharma.client.jpa.customer.CustomerSearchResult;
import org.adorsys.adpharma.client.jpa.customer.CustomerSearchService;
import org.adorsys.adpharma.client.jpa.documentprocessingstate.DocumentProcessingState;
import org.adorsys.adpharma.client.jpa.salesorderitem.SalesOrderItem;
import org.adorsys.adpharma.client.jpa.salesorderitem.SalesOrderItemSalesOrder;
import org.adorsys.adpharma.client.jpa.salesorderitem.SalesOrderItemSearchInput;
import org.adorsys.adpharma.client.jpa.salesorderitem.SalesOrderItemSearchResult;
import org.adorsys.adpharma.client.jpa.salesorderitem.SalesOrderItemSearchService;
import org.adorsys.adpharma.client.utils.ChartData;
import org.adorsys.javafx.crud.extensions.EntityController;
import org.adorsys.javafx.crud.extensions.ViewType;
import org.adorsys.javafx.crud.extensions.events.EntityCreateDoneEvent;
import org.adorsys.javafx.crud.extensions.events.EntityCreateRequestedEvent;
import org.adorsys.javafx.crud.extensions.events.EntityEditCanceledEvent;
import org.adorsys.javafx.crud.extensions.events.EntityEditDoneEvent;
import org.adorsys.javafx.crud.extensions.events.EntityListPageIndexChangedEvent;
import org.adorsys.javafx.crud.extensions.events.EntityRemoveDoneEvent;
import org.adorsys.javafx.crud.extensions.events.EntitySearchDoneEvent;
import org.adorsys.javafx.crud.extensions.events.EntitySelectionEvent;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.login.ErrorDisplay;
import org.adorsys.javafx.crud.extensions.login.ServiceCallFailedEventHandler;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;
import org.adorsys.javafx.crud.extensions.utils.PaginationUtils;
import org.apache.commons.lang3.StringUtils;
import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.Dialogs;

@Singleton
public class SalesOrderListController implements EntityController
{
	@Inject
	private SalesOrderListView listView;

	@Inject
	@EntitySelectionEvent
	private Event<SalesOrder> precessSalesOrderRequestedEvent;

	@Inject
	private CustomerSearchService customerSearchService;
	@Inject
	private ServiceCallFailedEventHandler customerSearchServiceCallFailedEventHandler;

	@Inject
	@EntityCreateRequestedEvent
	private Event<SalesOrder> salesOrderRequestEvent;

	@Inject
	private SalesOrderSearchService salesOrederSearchService;

	@Inject
	private SalesOrderItemSearchService salesOrederItemSearchService;

	@Inject
	private SalesOrderRemoveService salesOrderRemoveService ;

	@Inject
	private ServiceCallFailedEventHandler salesOrederSearchServiceCallFailedEventHandler;


	@Inject
	@EntityListPageIndexChangedEvent
	private Event<SalesOrderSearchResult> entityListPageIndexChangedEvent;

	private SalesOrderSearchResult searchResult;

	@Inject 
	SalesOrderSearchInput searchInput;
	
	@Inject
	CustomerSearchInput customerSearchInput ;
	

	@Inject
	@Bundle({ CrudKeys.class})
	private ResourceBundle resourceBundle;

	@Inject
	private SalesOrderRegistration registration;

	@Inject
	@PrintCustomerInvoiceRequestedEvent
	private Event<SalesOrderId> printCustomerInvoiceRequestedEvent;
	
	private SalesOrderId selectedSalesOrderId;
	
	@PostConstruct
	public void postConstruct()
	{
		customerSearchInput.setMax(-1);
		listView.getCreateButton().disableProperty().bind(registration.canCreateProperty().not());
		listView.bind(searchInput);
		searchInput.setMax(100);
		listView.getDataList().getSelectionModel().selectedItemProperty().addListener(new ChangeListener<SalesOrder>() {

			@Override
			public void changed(ObservableValue<? extends SalesOrder> observable,
					SalesOrder oldValue, SalesOrder newValue) {
				if(newValue!=null){
					selectedSalesOrderId = new SalesOrderId(newValue.getId());
					listView.getRemoveButton().disableProperty().unbind();
					listView.getPrintInvoiceButtonn().disableProperty().unbind();
					listView.getRemoveButton().disableProperty().bind(newValue.salesOrderStatusProperty().isEqualTo(DocumentProcessingState.CLOSED));
					listView.getPrintInvoiceButtonn().disableProperty().bind(newValue.salesOrderStatusProperty().isNotEqualTo(DocumentProcessingState.CLOSED));
					SalesOrderItemSearchInput sosi = new SalesOrderItemSearchInput();
					sosi.setMax(-1);
					sosi.getEntity().setSalesOrder(new SalesOrderItemSalesOrder(newValue));
					sosi.getFieldNames().add("salesOrder");
					salesOrederItemSearchService.setSearchInputs(sosi).start();
				}

			}
		});
		salesOrederItemSearchService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				SalesOrderItemSearchService s = (SalesOrderItemSearchService) event.getSource();
				SalesOrderItemSearchResult result = s.getValue();
				event.consume();
				s.reset();
				List<SalesOrderItem> resultList = result.getResultList();
				listView.getDataListItem().getItems().setAll(resultList);
			}
		});
		salesOrederItemSearchService.setOnFailed(customerSearchServiceCallFailedEventHandler);

		customerSearchServiceCallFailedEventHandler.setErrorDisplay(new ErrorDisplay() {

			@Override
			protected void showError(Throwable exception) {
				Dialogs.create().nativeTitleBar().showException(exception);

			}
		});
		salesOrederSearchServiceCallFailedEventHandler.setErrorDisplay(new ErrorDisplay() {

			@Override
			protected void showError(Throwable exception) {
				Dialogs.create().nativeTitleBar().showException(exception);

			}
		});
		
		listView.getComputeButton().setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
			List<ChartData> testData = ChartData.getTestData();
			listView.getPieChart().getData().setAll(ChartData.toPieChartData(testData));
			listView.getPieChartData().getItems().setAll(testData);
				
			}
		});

		/*
		 * listen to remove button .
		 */
		listView.getRemoveButton().setOnAction(new EventHandler<ActionEvent>()
				{
			@Override
			public void handle(ActionEvent e)
			{							
				SalesOrder selectedItem = listView.getDataList().getSelectionModel().getSelectedItem();
				if(selectedItem!=null){
					Action showConfirm = Dialogs.create().
							nativeTitleBar().
							message(resourceBundle.getString("Entity_confirm_remove.title")).showConfirm();
					if(showConfirm==Dialog.Actions.YES){
						salesOrderRemoveService.setEntity(selectedItem).start();
					}
				}

			}


				});

		
		listView.getCustomer().setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				customerSearchService.setSearchInputs(customerSearchInput).start();

			}
		});
		listView.getChartClientList().setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				customerSearchService.setSearchInputs(customerSearchInput).start();

			}
		});
		listView.getProcessButton().setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				SalesOrder selectedItem = listView.getDataList().getSelectionModel().getSelectedItem();
				if(selectedItem== null) return ;
				precessSalesOrderRequestedEvent.fire(selectedItem);
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
				searchInput.setMax(27);
				salesOrederSearchService.setSearchInputs(searchInput).start();

			}

				});

		salesOrderRemoveService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				SalesOrderRemoveService s = (SalesOrderRemoveService) event.getSource();
				SalesOrder result = s.getValue();
				event.consume();
				s.reset();
				listView.getDataList().getItems().remove(result);

			}
		});
		salesOrderRemoveService.setOnFailed(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				SalesOrderRemoveService s = (SalesOrderRemoveService) event.getSource();
				s.reset();				
			}
		});

		customerSearchService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				CustomerSearchService s = (CustomerSearchService) event.getSource();
				CustomerSearchResult result = s.getValue();
				event.consume();
				s.reset();
				ArrayList<SalesOrderCustomer> soc = new ArrayList<SalesOrderCustomer>();
				List<Customer> resultList = result.getResultList();
				for (Customer customer : resultList) {
					soc.add(new SalesOrderCustomer(customer));
				}
				soc.add(0, null);
				listView.getCustomer().getItems().setAll(soc);
				listView.getCustomer().getSelectionModel().select(0);
				listView.getChartClientList().getItems().setAll(resultList);

			}
		});
		customerSearchService.setOnFailed(customerSearchServiceCallFailedEventHandler);

		salesOrederSearchService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				SalesOrderSearchService s = (SalesOrderSearchService) event.getSource();
				searchResult = s.getValue();
				event.consume();
				s.reset();
				handleSearchResult(searchResult);

			}
		});

		salesOrederSearchService.setOnFailed(salesOrederSearchServiceCallFailedEventHandler);

		listView.getCreateButton().setOnAction(new EventHandler<ActionEvent>()
				{
			@Override
			public void handle(ActionEvent e)
			{
				salesOrderRequestEvent.fire(new SalesOrder());
			}
				});

		//		paginate
		listView.getPagination().currentPageIndexProperty().addListener(new ChangeListener<Number>()
				{
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue)
			{
				if (searchResult == null)
					return;
				if (searchResult.getSearchInput() == null)
					searchResult.setSearchInput(new SalesOrderSearchInput());
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
		
		listView.getPrintInvoiceButtonn().setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if(selectedSalesOrderId==null || selectedSalesOrderId.getId()==null) return;
				printCustomerInvoiceRequestedEvent.fire(selectedSalesOrderId);				
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
	 * in the main salesOrder controller.
	 * 
	 * @param entities
	 */
	public void handleSearchResult(@Observes @EntitySearchDoneEvent SalesOrderSearchResult searchResult)
	{
		this.searchResult = searchResult;
		List<SalesOrder> entities = searchResult.getResultList();
		if (entities == null)
			entities = new ArrayList<SalesOrder>();
		listView.getDataList().getItems().clear();
		listView.getDataList().getItems().addAll(entities);
		int maxResult = searchResult.getSearchInput() != null ? searchResult.getSearchInput().getMax() : 100;
		int pageCount = PaginationUtils.computePageCount(searchResult.getCount(), maxResult);
		listView.getPagination().setPageCount(pageCount);
		int firstResult = searchResult.getSearchInput() != null ? searchResult.getSearchInput().getStart() : 0;
		int pageIndex = PaginationUtils.computePageIndex(firstResult, searchResult.getCount(), maxResult);
		listView.getPagination().setCurrentPageIndex(pageIndex);

	}

	public void handleCreatedEvent(@Observes @EntityCreateDoneEvent SalesOrder createdEntity)
	{
		listView.getDataList().getItems().add(0, createdEntity);
	}

	public void handleRemovedEvent(@Observes @EntityRemoveDoneEvent SalesOrder removedEntity)
	{
		listView.getDataList().getItems().remove(removedEntity);
	}

	public void handleEditDoneEvent(@Observes @EntityEditDoneEvent SalesOrder selectedEntity)
	{
		int selectedIndex = listView.getDataList().getItems().indexOf(selectedEntity);
		if (selectedIndex <= -1)
			return;
		SalesOrder entity = listView.getDataList().getItems().get(selectedIndex);
		PropertyReader.copy(selectedEntity, entity);

		ArrayList<SalesOrder> arrayList = new ArrayList<SalesOrder>(listView.getDataList().getItems());
		listView.getDataList().getItems().clear();
		listView.getDataList().getItems().addAll(arrayList);
		listView.getDataList().getSelectionModel().select(selectedEntity);
	}

	public void handleEditCanceledEvent(@Observes @EntityEditCanceledEvent SalesOrder selectedEntity)
	{
		int selectedIndex = listView.getDataList().getItems().indexOf(selectedEntity);
		if (selectedIndex <= -1)
			return;
		SalesOrder entity = listView.getDataList().getItems().get(selectedIndex);
		PropertyReader.copy(selectedEntity, entity);

		ArrayList<SalesOrder> arrayList = new ArrayList<SalesOrder>(listView.getDataList().getItems());
		listView.getDataList().getItems().clear();
		listView.getDataList().getItems().addAll(arrayList);
		listView.getDataList().getSelectionModel().select(selectedEntity);
	}

	public List<String> readSearchAttributes(){
		ArrayList<String> seachAttributes = new ArrayList<String>() ;
		String soNumber = searchInput.getEntity().getSoNumber();
		SalesOrderCustomer customer = searchInput.getEntity().getCustomer();
		DocumentProcessingState state = searchInput.getEntity().getSalesOrderStatus();

		if(StringUtils.isNotBlank(soNumber)) seachAttributes.add("soNumber");
		if(customer!=null && customer.getId()!=null) seachAttributes.add("customer");
		if(state!=null) seachAttributes.add("salesOrderStatus") ;
		return seachAttributes;

	}
}