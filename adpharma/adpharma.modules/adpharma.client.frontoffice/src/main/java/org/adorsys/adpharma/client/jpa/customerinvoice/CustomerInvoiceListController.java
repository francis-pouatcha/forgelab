package org.adorsys.adpharma.client.jpa.customerinvoice;

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

import org.adorsys.adpharma.client.events.PrintCustomerInvoiceRequestedEvent;
import org.adorsys.adpharma.client.jpa.customerinvoiceitem.CustomerInvoiceItem;
import org.adorsys.adpharma.client.jpa.customerinvoiceitem.CustomerInvoiceItemInvoice;
import org.adorsys.adpharma.client.jpa.customerinvoiceitem.CustomerInvoiceItemSearchInput;
import org.adorsys.adpharma.client.jpa.customerinvoiceitem.CustomerInvoiceItemSearchResult;
import org.adorsys.adpharma.client.jpa.customerinvoiceitem.CustomerInvoiceItemSearchService;
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
public class CustomerInvoiceListController implements EntityController
{

	@Inject
	private CustomerInvoiceListView listView;

	@Inject
	@EntitySelectionEvent
	private Event<CustomerInvoice> selectionEvent;

	@Inject
	@EntitySearchRequestedEvent
	private Event<CustomerInvoice> searchRequestedEvent;

	@Inject
	@EntityCreateRequestedEvent
	private Event<CustomerInvoice> createRequestedEvent;

	@Inject
	@EntityListPageIndexChangedEvent
	private Event<CustomerInvoiceSearchResult> entityListPageIndexChangedEvent;

	private CustomerInvoiceSearchResult searchResult;

	@Inject
	private CustomerInvoiceRegistration registration;

	@Inject
	private CustomerInvoiceSearchInput searchInput ;

	@Inject
	private CustomerInvoiceSearchService customerInvoiceSearchService;

	@Inject
	@PrintCustomerInvoiceRequestedEvent
	private Event<CustomerInvoice> printCustomerInvoiceRequestedEvent;

	@Inject
	private CustomerInvoiceItemSearchService  invoiceItemSearchService ;

	@Inject
	private ServiceCallFailedEventHandler  callFailedEventHandler ;

	@PostConstruct
	public void postConstruct()
	{
		listView.getPrintButton().disableProperty().bind(registration.canCreateProperty().not());
		listView.bind(searchInput);
		callFailedEventHandler.setErrorDisplay(new ErrorDisplay() {

			@Override
			protected void showError(Throwable exception) {
				Dialogs.create().showException(exception);

			}
		});

		listView.getDataList().getSelectionModel().selectedItemProperty().addListener(new ChangeListener<CustomerInvoice>() {

			@Override
			public void changed(
					ObservableValue<? extends CustomerInvoice> observable,
					CustomerInvoice oldValue, CustomerInvoice newValue) {
				if(newValue!=null){
					CustomerInvoiceItemSearchInput  searchInput = new CustomerInvoiceItemSearchInput();
					searchInput.getEntity().setInvoice(new CustomerInvoiceItemInvoice(newValue));
					searchInput.getFieldNames().add("invoice");
					invoiceItemSearchService.setSearchInputs(searchInput).start();
				}

			}
		});
		invoiceItemSearchService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				CustomerInvoiceItemSearchService s = (CustomerInvoiceItemSearchService) event.getSource();
				CustomerInvoiceItemSearchResult result = s.getValue();
				event.consume();
				s.reset();
				List<CustomerInvoiceItem> resultList = result.getResultList();
				listView.getDataListItem().getItems().setAll(resultList);

			}
		});
		invoiceItemSearchService.setOnFailed(callFailedEventHandler);

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
				customerInvoiceSearchService.setSearchInputs(searchInput).start();
			}
				});
		customerInvoiceSearchService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				CustomerInvoiceSearchService s = (CustomerInvoiceSearchService) event.getSource();
				searchResult = s.getValue();
				event.consume();
				s.reset();
				handleSearchResult(searchResult);

			}
		});

		customerInvoiceSearchService.setOnFailed(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				CustomerInvoiceSearchService s = (CustomerInvoiceSearchService) event.getSource();
				s.reset();				
			}
		});

		listView.getPrintButton().setOnAction(new EventHandler<ActionEvent>()
				{
			@Override
			public void handle(ActionEvent e)
			{
				CustomerInvoice selectedItem = listView.getDataList().getSelectionModel().getSelectedItem();
				if(selectedItem!=null) printCustomerInvoiceRequestedEvent.fire(selectedItem);
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
					searchResult.setSearchInput(new CustomerInvoiceSearchInput());
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
	 * in the main customerInvoice controller.
	 * 
	 * @param entities
	 */
	public void handleSearchResult(@Observes @EntitySearchDoneEvent CustomerInvoiceSearchResult searchResult)
	{
		this.searchResult = searchResult;
		List<CustomerInvoice> entities = searchResult.getResultList();
		if (entities == null)
			entities = new ArrayList<CustomerInvoice>();
		listView.getDataList().getItems().clear();
		listView.getDataList().getItems().addAll(entities);
		int maxResult = searchResult.getSearchInput() != null ? searchResult.getSearchInput().getMax() : 5;
		int pageCount = PaginationUtils.computePageCount(searchResult.getCount(), maxResult);
		listView.getPagination().setPageCount(pageCount);
		int firstResult = searchResult.getSearchInput() != null ? searchResult.getSearchInput().getStart() : 0;
		int pageIndex = PaginationUtils.computePageIndex(firstResult, searchResult.getCount(), maxResult);
		listView.getPagination().setCurrentPageIndex(pageIndex);

	}

	public void handleCreatedEvent(@Observes @EntityCreateDoneEvent CustomerInvoice createdEntity)
	{
		listView.getDataList().getItems().add(0, createdEntity);
	}

	public void handleRemovedEvent(@Observes @EntityRemoveDoneEvent CustomerInvoice removedEntity)
	{
		listView.getDataList().getItems().remove(removedEntity);
	}

	public void handleEditDoneEvent(@Observes @EntityEditDoneEvent CustomerInvoice selectedEntity)
	{
		int selectedIndex = listView.getDataList().getItems().indexOf(selectedEntity);
		if (selectedIndex <= -1)
			return;
		CustomerInvoice entity = listView.getDataList().getItems().get(selectedIndex);
		PropertyReader.copy(selectedEntity, entity);

		ArrayList<CustomerInvoice> arrayList = new ArrayList<CustomerInvoice>(listView.getDataList().getItems());
		listView.getDataList().getItems().clear();
		listView.getDataList().getItems().addAll(arrayList);
		listView.getDataList().getSelectionModel().select(selectedEntity);
	}

	public void handleEditCanceledEvent(@Observes @EntityEditCanceledEvent CustomerInvoice selectedEntity)
	{
		int selectedIndex = listView.getDataList().getItems().indexOf(selectedEntity);
		if (selectedIndex <= -1)
			return;
		CustomerInvoice entity = listView.getDataList().getItems().get(selectedIndex);
		PropertyReader.copy(selectedEntity, entity);

		ArrayList<CustomerInvoice> arrayList = new ArrayList<CustomerInvoice>(listView.getDataList().getItems());
		listView.getDataList().getItems().clear();
		listView.getDataList().getItems().addAll(arrayList);
		listView.getDataList().getSelectionModel().select(selectedEntity);
	}


	public List<String> readSearchAttributes(){
		ArrayList<String> seachAttributes = new ArrayList<String>() ;
		String invoiceNumber = searchInput.getEntity().getInvoiceNumber();
		Boolean cashed = searchInput.getEntity().getCashed();
		if(StringUtils.isNotBlank(invoiceNumber)) seachAttributes.add("invoiceNumber");
		if(cashed) seachAttributes.add("cashed");
		return seachAttributes;

	}

	public void reset() {
		listView.getDataList().getItems().clear();
	}
}

