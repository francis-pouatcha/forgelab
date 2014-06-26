package org.adorsys.adpharma.client.jpa.customer;

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

import org.adorsys.adpharma.client.jpa.customercategory.CustomerCategory;
import org.adorsys.adpharma.client.jpa.customercategory.CustomerCategorySearchInput;
import org.adorsys.adpharma.client.jpa.customercategory.CustomerCategorySearchResult;
import org.adorsys.adpharma.client.jpa.customercategory.CustomerCategorySearchService;
import org.adorsys.adpharma.client.jpa.documentprocessingstate.DocumentProcessingState;
import org.adorsys.adpharma.client.jpa.salesorder.SalesOrder;
import org.adorsys.adpharma.client.jpa.salesorder.SalesOrderCustomer;
import org.adorsys.adpharma.client.jpa.salesorder.SalesOrderRemoveService;
import org.adorsys.adpharma.client.jpa.salesorder.SalesOrderSearchService;
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
import org.apache.commons.lang3.StringUtils;
import org.controlsfx.dialog.Dialogs;

@Singleton
public class CustomerListController implements EntityController
{

	@Inject
	private CustomerListView listView;

	@Inject
	@EntitySelectionEvent
	private Event<Customer> selectionEvent;

	@Inject
	@EntitySearchRequestedEvent
	private Event<Customer> searchRequestedEvent;

	@Inject
	@EntityCreateRequestedEvent
	private Event<Customer> createRequestedEvent;

	@Inject
	@EntityListPageIndexChangedEvent
	private Event<CustomerSearchResult> entityListPageIndexChangedEvent;

	@Inject
	@EntityEditRequestedEvent
	private Event<Customer> customerEditRequetedEvent ;

	private CustomerSearchResult searchResult;

	@Inject
	private CustomerRegistration registration;

	@Inject
	private CustomerCategorySearchService  customerCategorySearchService;

	@Inject
	private CustomerSearchService  customerSearchService;

	@Inject
	private CustomerSearchInput searchInput ;

	@PostConstruct
	public void postConstruct()
	{
		listView.getCreateButton().disableProperty().bind(registration.canCreateProperty().not());
		listView.getEditButton().disableProperty().bind(registration.canEditProperty().not());
		listView.bind(searchInput);

		//      listView.getDataList().getSelectionModel().selectedItemProperty()
		//            .addListener(new ChangeListener<Customer>()
		//            {
		//               @Override
		//               public void changed(
		//                     ObservableValue<? extends Customer> property,
		//                     Customer oldValue, Customer newValue)
		//               {
		//                  if (newValue != null)
		//                     selectionEvent.fire(newValue);
		//               }
		//            });
		listView.getCategory().armedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable,
					Boolean oldValue, Boolean newValue) {
				if(newValue){
					customerCategorySearchService.setSearchInputs(new CustomerCategorySearchInput()).start();
				}

			}
		});

		customerCategorySearchService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				CustomerCategorySearchService s = (CustomerCategorySearchService) event.getSource();
				CustomerCategorySearchResult searchResult = s.getValue();
				event.consume();
				s.reset();
				List<CustomerCategory> resultList = searchResult.getResultList() ;
				ArrayList<CustomerCustomerCategory> cc = new ArrayList<>();
				for (CustomerCategory categorie : resultList) {
					cc.add(new CustomerCustomerCategory(categorie));
				}
				listView.getDataList().getItems().remove(cc);

			}
		});
		customerCategorySearchService.setOnFailed(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				CustomerCategorySearchService s = (CustomerCategorySearchService) event.getSource();
				s.reset();				
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
				customerSearchService.setSearchInputs(searchInput).start();
			}
				});

		customerSearchService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				CustomerSearchService s = (CustomerSearchService) event.getSource();
				searchResult = s.getValue();
				event.consume();
				s.reset();
				handleSearchResult(searchResult);

			}
		});

		customerSearchService.setOnFailed(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				CustomerSearchService s = (CustomerSearchService) event.getSource();
				s.reset();				
			}
		});



		listView.getCreateButton().setOnAction(new EventHandler<ActionEvent>()
				{
			@Override
			public void handle(ActionEvent e)
			{
				createRequestedEvent.fire(new Customer());
			}
				});

		listView.getEditButton().setOnAction(new EventHandler<ActionEvent>()
				{
			@Override
			public void handle(ActionEvent e)
			{
				Customer selectedItem = listView.getDataList().getSelectionModel().getSelectedItem();
				if (selectedItem != null)
					if("000000001".equals(selectedItem.getSerialNumber())){
						Dialogs.create().masthead("Impossible de modifier ce client ").showError();
					}else {
						customerEditRequetedEvent.fire(selectedItem);
					}
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
					searchResult.setSearchInput(new CustomerSearchInput());
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
	 * in the main customer controller.
	 * 
	 * @param entities
	 */
	public void handleSearchResult(@Observes @EntitySearchDoneEvent CustomerSearchResult searchResult)
	{
		this.searchResult = searchResult;
		List<Customer> entities = searchResult.getResultList();
		if (entities == null)
			entities = new ArrayList<Customer>();
		listView.getDataList().getItems().clear();
		listView.getDataList().getItems().addAll(entities);
		int maxResult = searchResult.getSearchInput() != null ? searchResult.getSearchInput().getMax() : 5;
		int pageCount = PaginationUtils.computePageCount(searchResult.getCount(), maxResult);
		listView.getPagination().setPageCount(pageCount);
		int firstResult = searchResult.getSearchInput() != null ? searchResult.getSearchInput().getStart() : 0;
		int pageIndex = PaginationUtils.computePageIndex(firstResult, searchResult.getCount(), maxResult);
		listView.getPagination().setCurrentPageIndex(pageIndex);

	}

	public void handleCreatedEvent(@Observes @EntityCreateDoneEvent Customer createdEntity)
	{
		listView.getDataList().getItems().add(0, createdEntity);
	}

	public void handleRemovedEvent(@Observes @EntityRemoveDoneEvent Customer removedEntity)
	{
		listView.getDataList().getItems().remove(removedEntity);
	}

	public void handleEditDoneEvent(@Observes @EntityEditDoneEvent Customer selectedEntity)
	{
		int selectedIndex = listView.getDataList().getItems().indexOf(selectedEntity);
		if (selectedIndex <= -1)
			return;
		Customer entity = listView.getDataList().getItems().get(selectedIndex);
		PropertyReader.copy(selectedEntity, entity);

		ArrayList<Customer> arrayList = new ArrayList<Customer>(listView.getDataList().getItems());
		listView.getDataList().getItems().clear();
		listView.getDataList().getItems().addAll(arrayList);
		listView.getDataList().getSelectionModel().select(selectedEntity);
	}

	public void handleEditCanceledEvent(@Observes @EntityEditCanceledEvent Customer selectedEntity)
	{
		int selectedIndex = listView.getDataList().getItems().indexOf(selectedEntity);
		if (selectedIndex <= -1)
			return;
		Customer entity = listView.getDataList().getItems().get(selectedIndex);
		PropertyReader.copy(selectedEntity, entity);

		ArrayList<Customer> arrayList = new ArrayList<Customer>(listView.getDataList().getItems());
		listView.getDataList().getItems().clear();
		listView.getDataList().getItems().addAll(arrayList);
		listView.getDataList().getSelectionModel().select(selectedEntity);
	}

	public List<String> readSearchAttributes(){
		ArrayList<String> seachAttributes = new ArrayList<String>() ;
		String fullName = searchInput.getEntity().getFullName();
		CustomerCustomerCategory customerCategory = searchInput.getEntity().getCustomerCategory() ;

		if(StringUtils.isNotBlank(fullName)) seachAttributes.add("fullName");
		if(customerCategory!=null && customerCategory.getId()!=null) seachAttributes.add("customerCategory");
		return seachAttributes;

	}

	public void reset() {
		   listView.getDataList().getItems().clear();
		}
}
