package org.adorsys.adpharma.client.jpa.cashdrawer;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

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

import org.adorsys.adpharma.client.jpa.agency.Agency;
import org.adorsys.adpharma.client.jpa.login.Login;
import org.adorsys.adpharma.client.jpa.login.LoginSearchInput;
import org.adorsys.adpharma.client.jpa.login.LoginSearchResult;
import org.adorsys.adpharma.client.jpa.login.LoginSearchService;
import org.adorsys.adpharma.client.jpa.payment.PaymentCashDrawer;
import org.adorsys.adpharma.client.jpa.payment.PaymentCashier;
import org.adorsys.adpharma.client.jpa.payment.PaymentSearchInput;
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
import org.adorsys.javafx.crud.extensions.events.ModalEntitySearchRequestedEvent;
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
	@ModalEntitySearchRequestedEvent
	private Event<PaymentSearchInput> paymentSearchInputRequestEvent;

	@Inject
	private CashDrawerSearchService  cashDrawerSearchService ;

	@Inject
	private CashDrawerRegistration registration;

	@Inject
	private LoginSearchService loginSearchService ;

	@Inject
	@Bundle({ CrudKeys.class
		, CashDrawer.class
		, Agency.class
	})
	private ResourceBundle resourceBundle;

	@Inject
	private CashDrawerCloseService cashDrawerCloseService;
	@Inject
	private ServiceCallFailedEventHandler cashDrawerCloseServiceFailedHandler;


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
				cashDrawerSearchService.setSearchInputs(searchInput).start();
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
					LoginSearchInput loginSearchInput = new LoginSearchInput();
					loginSearchInput.setMax(-1);
					loginSearchService.setSearchInputs(loginSearchInput).start();
				}

			}
		});
		listView.getPrintPaymentListButtonButton().setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) 
			{
				CashDrawer selectedItem = listView.getDataList().getSelectionModel().getSelectedItem();
				if(selectedItem!=null){
				PaymentSearchInput paymentSearchInput = new PaymentSearchInput();
				paymentSearchInput.getEntity().setCashDrawer(new PaymentCashDrawer(selectedItem));
				paymentSearchInput.setMax(-1);
				paymentSearchInput.getFieldNames().add("cashDrawer");
				paymentSearchInputRequestEvent.fire(paymentSearchInput);
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
				resultList.sort(new Comparator<Login>() {

					@Override
					public int compare(Login o1, Login o2) {
						// TODO Auto-generated method stub
						return o1.getLoginName().compareToIgnoreCase(o2.getLoginName());
					}
				});
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
		listView.getCloseButton().setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				CashDrawer selectedItem = listView.getDataList().getSelectionModel().getSelectedItem();
				if(selectedItem!=null&& selectedItem.getOpened()){
					Action showConfirm = Dialogs.create().message(resourceBundle.getString("CashDrawer_close_confirmation.title")).showConfirm();
					if(Dialog.Actions.YES.equals(showConfirm)){
						if(selectedItem.getId()!=null)
							cashDrawerCloseService.setCashDrawer(selectedItem).restart();
					}
				}
			}
		});

		cashDrawerCloseServiceFailedHandler.setErrorDisplay(new ErrorDisplay() {

			@Override
			protected void showError(Throwable exception) {
				Dialogs.create().showException(exception);

			}
		});

		cashDrawerCloseService.setOnFailed(cashDrawerCloseServiceFailedHandler);
		cashDrawerCloseService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				CashDrawerCloseService s = (CashDrawerCloseService) event.getSource();
				CashDrawer searchResult = s.getValue();
				event.consume();
				s.reset();

			}
		});

		listView.getOpenButton().setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				selectionEvent.fire(new CashDrawer());
			}
		});
		//		listView.getDataList().getSelectionModel().selectedItemProperty()
		//		.addListener(new ChangeListener<CashDrawer>()
		//				{
		//			@Override
		//			public void changed(
		//					ObservableValue<? extends CashDrawer> property,
		//					CashDrawer oldValue, CashDrawer newValue)
		//			{
		//				if (newValue != null)
		//					selectionEvent.fire(newValue);
		//			}
		//				});

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
		if(cashier!=null&&cashier.getId()!=null) seachAttributes.add("cashier");
		return seachAttributes;

	}

}


