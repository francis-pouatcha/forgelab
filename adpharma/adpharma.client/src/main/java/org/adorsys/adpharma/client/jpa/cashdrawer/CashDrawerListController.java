package org.adorsys.adpharma.client.jpa.cashdrawer;

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

import org.adorsys.adpharma.client.jpa.login.Login;
import org.adorsys.adpharma.client.jpa.login.LoginSearchInput;
import org.adorsys.adpharma.client.jpa.login.LoginSearchResult;
import org.adorsys.adpharma.client.jpa.login.LoginSearchService;
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

	@Inject
	private CashDrawerSearchInput searchInput ;

	@Inject
	private CashDrawerSearchService  cashDrawerSearchService ;

	@Inject
	private CashDrawerRegistration registration;
	
	@Inject
	private LoginSearchService loginSearchService ;

	@PostConstruct
	public void postConstruct()
	{
		listView.getOpenButton().disableProperty().bind(registration.canCreateProperty().not());
		listView.getCloseButton().disableProperty().bind(registration.canEditProperty().not());
		listView.bind(searchInput);

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
				cashDrawerSearchService.setSearchInputs(new CashDrawerSearchInput()).start();
			}
				});
		cashDrawerSearchService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				CashDrawerSearchService s = (CashDrawerSearchService) event.getSource();
				searchResult = s.getValue();
				event.consume();
				s.reset();
				handleSearchResult(searchResult);

			}
		});

		cashDrawerSearchService.setOnFailed(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				CashDrawerSearchService s = (CashDrawerSearchService) event.getSource();
				s.reset();				
			}
		});
		listView.getCashier().armedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable,
					Boolean oldValue, Boolean newValue) {
				if(newValue){
					loginSearchService.setSearchInputs(new LoginSearchInput()).start();
				}
				
			}
		});
		
		loginSearchService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				LoginSearchService s = (LoginSearchService) event.getSource();
				LoginSearchResult result = s.getValue();
				event.consume();
				s.reset();
				List<Login> resultList = result.getResultList();
				for (Login login : resultList) {
					listView.getCashier().getItems().add(new CashDrawerCashier(login));
				}

			}
		});

		loginSearchService.setOnFailed(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				LoginSearchService s = (LoginSearchService) event.getSource();
				s.reset();				
			}
		});
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

		//      listView.getCreateButton().setOnAction(new EventHandler<ActionEvent>()
		//      {
		//         @Override
		//         public void handle(ActionEvent e)
		//         {
		//            CashDrawer selectedItem = listView.getDataList().getSelectionModel().getSelectedItem();
		//            if (selectedItem == null)
		//               selectedItem = new CashDrawer();
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


	public void reset() {
		listView.getDataList().getItems().clear();
	}


	public List<String> readSearchAttributes(){
		ArrayList<String> seachAttributes = new ArrayList<String>() ;
		String cashDrawerNumber = searchInput.getEntity().getCashDrawerNumber();
		Boolean opened = searchInput.getEntity().getOpened();
		CashDrawerCashier cashier = searchInput.getEntity().getCashier();
		if(StringUtils.isNotBlank(cashDrawerNumber)) seachAttributes.add("cashDrawerNumber");
		if(opened!=null) seachAttributes.add("opened");
		if(cashier!=null) seachAttributes.add("cashier");
		return seachAttributes;

	}

}


