package org.adorsys.adpharma.client.jpa.customervoucher;

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
import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.Dialogs;

@Singleton
public class CustomerVoucherListController implements EntityController
{

	@Inject
	private CustomerVoucherListView listView;

	@Inject
	@EntitySelectionEvent
	private Event<CustomerVoucher> selectionEvent;

	@Inject
	@EntitySearchRequestedEvent
	private Event<CustomerVoucher> searchRequestedEvent;

	@Inject
	@EntityCreateRequestedEvent
	private Event<CustomerVoucher> createRequestedEvent;

	@Inject
	@EntityListPageIndexChangedEvent
	private Event<CustomerVoucherSearchResult> entityListPageIndexChangedEvent;

	private CustomerVoucherSearchResult searchResult;

	@Inject
	private CustomerVoucherEditService customerVoucherEditService;

	@Inject
	private CustomerVoucherRegistration registration;

	@Inject
	private ServiceCallFailedEventHandler callFailedEventHandler ;
	
	@Inject
	private CustomerVoucher selected ;

	@PostConstruct
	public void postConstruct()
	{
		listView.getCreateButton().disableProperty().bind(registration.canCreateProperty().not());
        listView.getCancleButton().disableProperty().bind(selected.canceledProperty());
		callFailedEventHandler.setErrorDisplay(new ErrorDisplay() {

			@Override
			protected void showError(Throwable exception) {
				Dialogs.create().showException(exception);

			}
		});
		      listView.getDataList().getSelectionModel().selectedItemProperty()
		            .addListener(new ChangeListener<CustomerVoucher>()
		            {
		               @Override
		               public void changed(
		                     ObservableValue<? extends CustomerVoucher> property,
		                     CustomerVoucher oldValue, CustomerVoucher newValue)
		               {
		                  if (newValue != null){
		                	  PropertyReader.copy(newValue, selected);
		                     selectionEvent.fire(newValue);
		                  }
		               }
		            });

		listView.getCancleButton().setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				CustomerVoucher selectedItem = listView.getDataList().getSelectionModel().getSelectedItem();
				if(selectedItem!=null){
					Action showConfirm = Dialogs.create().message("Confirmer l'annulation").showConfirm();
					if(Dialog.Actions.YES.equals(showConfirm)){
						selectedItem.setCanceled(true);
						customerVoucherEditService.setCustomerVoucher(selectedItem).start();
					}

				}

			}
		});
		listView.getUnCancelButton().setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				CustomerVoucher selectedItem = listView.getDataList().getSelectionModel().getSelectedItem();
				if(selectedItem!=null){
					Action showConfirm = Dialogs.create().message("Confirmer le retablissement").showConfirm();
					if(Dialog.Actions.YES.equals(showConfirm)){
						selectedItem.setCanceled(false);
						customerVoucherEditService.setCustomerVoucher(selectedItem).start();
					}

				}
				
			}
		});
		customerVoucherEditService.setOnFailed(callFailedEventHandler);
		customerVoucherEditService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				CustomerVoucherEditService source = (CustomerVoucherEditService) event.getSource();
                CustomerVoucher customerVoucher = source.getValue();
                source.reset();
                event.consume();
                handleEditDoneEvent(customerVoucher);
                Dialogs.create().message("Operation effectuee avec success ").showInformation();
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
				CustomerVoucher selectedItem = listView.getDataList().getSelectionModel().getSelectedItem();
				if (selectedItem == null)
					selectedItem = new CustomerVoucher();
				searchRequestedEvent.fire(selectedItem);
			}
				});

		listView.getCreateButton().setOnAction(new EventHandler<ActionEvent>()
				{
			@Override
			public void handle(ActionEvent e)
			{
				CustomerVoucher selectedItem = listView.getDataList().getSelectionModel().getSelectedItem();
				if (selectedItem == null)
					selectedItem = new CustomerVoucher();
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
					searchResult.setSearchInput(new CustomerVoucherSearchInput());
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
	 * in the main customerVoucher controller.
	 * 
	 * @param entities
	 */
	public void handleSearchResult(@Observes @EntitySearchDoneEvent CustomerVoucherSearchResult searchResult)
	{
		this.searchResult = searchResult;
		List<CustomerVoucher> entities = searchResult.getResultList();
		if (entities == null)
			entities = new ArrayList<CustomerVoucher>();
		listView.getDataList().getItems().clear();
		listView.getDataList().getItems().addAll(entities);
		int maxResult = searchResult.getSearchInput() != null ? searchResult.getSearchInput().getMax() : 5;
		int pageCount = PaginationUtils.computePageCount(searchResult.getCount(), maxResult);
		listView.getPagination().setPageCount(pageCount);
		int firstResult = searchResult.getSearchInput() != null ? searchResult.getSearchInput().getStart() : 0;
		int pageIndex = PaginationUtils.computePageIndex(firstResult, searchResult.getCount(), maxResult);
		listView.getPagination().setCurrentPageIndex(pageIndex);

	}

	public void handleCreatedEvent(@Observes @EntityCreateDoneEvent CustomerVoucher createdEntity)
	{
		listView.getDataList().getItems().add(0, createdEntity);
	}

	public void handleRemovedEvent(@Observes @EntityRemoveDoneEvent CustomerVoucher removedEntity)
	{
		listView.getDataList().getItems().remove(removedEntity);
	}

	public void handleEditDoneEvent(@Observes @EntityEditDoneEvent CustomerVoucher selectedEntity)
	{
		int selectedIndex = listView.getDataList().getItems().indexOf(selectedEntity);
		if (selectedIndex <= -1)
			return;
		CustomerVoucher entity = listView.getDataList().getItems().get(selectedIndex);
		PropertyReader.copy(selectedEntity, entity);

		ArrayList<CustomerVoucher> arrayList = new ArrayList<CustomerVoucher>(listView.getDataList().getItems());
		listView.getDataList().getItems().clear();
		listView.getDataList().getItems().addAll(arrayList);
		listView.getDataList().getSelectionModel().select(selectedEntity);
	}

	public void handleEditCanceledEvent(@Observes @EntityEditCanceledEvent CustomerVoucher selectedEntity)
	{
		int selectedIndex = listView.getDataList().getItems().indexOf(selectedEntity);
		if (selectedIndex <= -1)
			return;
		CustomerVoucher entity = listView.getDataList().getItems().get(selectedIndex);
		PropertyReader.copy(selectedEntity, entity);

		ArrayList<CustomerVoucher> arrayList = new ArrayList<CustomerVoucher>(listView.getDataList().getItems());
		listView.getDataList().getItems().clear();
		listView.getDataList().getItems().addAll(arrayList);
		listView.getDataList().getSelectionModel().select(selectedEntity);
	}

	public void reset() {
		listView.getDataList().getItems().clear();
	}
}
