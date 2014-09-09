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

import org.adorsys.adpharma.client.events.PrintRequestedEvent;
import org.adorsys.adpharma.client.events.ProcurementOrderId;
import org.adorsys.adpharma.client.jpa.delivery.DeliveryFromOrderServeice;
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
import org.adorsys.adpharma.client.utils.NetWorkChecker;
import org.adorsys.adpharma.client.utils.PhmlOrderReceiver;
import org.adorsys.adpharma.client.utils.PhmlOrderSender;
import org.adorsys.javafx.crud.extensions.EntityController;
import org.adorsys.javafx.crud.extensions.ViewType;
import org.adorsys.javafx.crud.extensions.events.EntityCreateDoneEvent;
import org.adorsys.javafx.crud.extensions.events.EntityCreateRequestedEvent;
import org.adorsys.javafx.crud.extensions.events.EntityEditCanceledEvent;
import org.adorsys.javafx.crud.extensions.events.EntityEditDoneEvent;
import org.adorsys.javafx.crud.extensions.events.EntityListPageIndexChangedEvent;
import org.adorsys.javafx.crud.extensions.events.EntityRemoveDoneEvent;
import org.adorsys.javafx.crud.extensions.events.EntityRemoveRequestEvent;
import org.adorsys.javafx.crud.extensions.events.EntitySearchDoneEvent;
import org.adorsys.javafx.crud.extensions.events.EntitySearchRequestedEvent;
import org.adorsys.javafx.crud.extensions.events.EntitySelectionEvent;
import org.adorsys.javafx.crud.extensions.events.HideProgressBarRequestEvent;
import org.adorsys.javafx.crud.extensions.events.ModalEntityCreateRequestedEvent;
import org.adorsys.javafx.crud.extensions.events.ShowProgressBarRequestEvent;
import org.adorsys.javafx.crud.extensions.login.ErrorDisplay;
import org.adorsys.javafx.crud.extensions.login.ServiceCallFailedEventHandler;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;
import org.adorsys.javafx.crud.extensions.utils.PaginationUtils;
import org.apache.commons.lang3.StringUtils;
import org.controlsfx.dialog.Dialogs;

@Singleton
public class ProcurementOrderListController implements EntityController
{

	@Inject
	private PhmlOrderSender phmlOrderBuilder ;

	@Inject
	private ProcurementOrderListView listView;

	@Inject
	@EntitySelectionEvent
	private Event<ProcurementOrder> selectionEvent;

	@Inject
	@EntitySearchRequestedEvent
	private Event<ProcurementOrder> searchRequestedEvent;

	@Inject
	@EntitySearchRequestedEvent
	private Event<ProcurementOrderPreparationData> advancedSearchRequestedEvent;

	@Inject
	@EntityRemoveRequestEvent
	private Event<ProcurementOrder> removeRequest;

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
	private ProcurementOrderPhmlSenderService phmlSenderService ;

	@Inject
	private ServiceCallFailedEventHandler callFailedEventHandler ;

	@Inject
	private PhmlSendAndReceiveService phmlSendAndReceiveService ;

	@Inject
	private ProcurementOrderSearchInput searchInput;

	@Inject
	private ProcurementOrderSearchResult searchResult;

	@Inject
	private ProcurementOrderRegistration registration;

	@Inject
	@PrintRequestedEvent
	private Event<ProcurementOrderId> poPrintRequestEvent ;

	@Inject
	DeliveryFromOrderServeice deliveryFromOrderServeice ;

	@Inject
	@ModalEntityCreateRequestedEvent
	private Event<ProcurementOrderPreparationData> orderPreparationEventData;


	@Inject
	private PhmlOrderReceiver orderBuilder ;

	@Inject
	@ShowProgressBarRequestEvent
	private Event<Object> showProgressBarRequestEvent ;

	@Inject
	@HideProgressBarRequestEvent
	private Event<Object> hideProgressBarRequestEvent ;

	@PostConstruct
	public void postConstruct()
	{
		listView.getRetreivedButton().disableProperty().bind(phmlSendAndReceiveService.runningProperty());
		listView.getSentButton().disableProperty().bind(phmlSendAndReceiveService.runningProperty());
		listView.getCreateButton().disableProperty().bind(registration.canCreateProperty().not());
		searchInput.setMax(30);
		listView.bind(searchInput);

		callFailedEventHandler.setErrorDisplay(new ErrorDisplay() {

			@Override
			protected void showError(Throwable exception) {
				Dialogs.create().showException(exception);
				hideProgressBarRequestEvent.fire(new Object());
			}
		});


		/**
		 * Handle Advanced search action
		 */
		listView.getAdvancedSearchButton().setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				ProcurementOrderPreparationData data = new ProcurementOrderPreparationData();
				advancedSearchRequestedEvent.fire(data); 
			}
		});


		/**
		 * handle print action
		 */
		listView.getPrintButton().setOnAction(new  EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				ProcurementOrder selectedItem = listView.getDataList().getSelectionModel().getSelectedItem();
				if(selectedItem!=null)
					poPrintRequestEvent.fire(new ProcurementOrderId(selectedItem.getId()));
				//				Iterator<ProcurementOrderItem> iterator = listView.getDataListItem().getItems().iterator();
				//				List<ProcurementOrderItem> items = Lists.newArrayList(iterator);
				//				selectedItem.setProcurementOrderItems(items);
				//				try {
				//					orderBuilder.build(selectedItem);
				//				} catch (IOException e) {
				//					// TODO Auto-generated catch block
				//					e.printStackTrace();
				//				}
			}
		});
		listView.getRuptureButton().setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				ProcurementOrder selectedItem = listView.getDataList().getSelectionModel().getSelectedItem();
				if(selectedItem!=null&& DocumentProcessingState.CLOSED.equals(selectedItem.getPoStatus())){
					ProcurementOrderId procurementOrderId = new ProcurementOrderId(selectedItem.getId());
					procurementOrderId.setOnlyRupture(true);
					poPrintRequestEvent.fire(procurementOrderId );
				}

			}
		});
		listView.getDataList().getSelectionModel().selectedItemProperty()
		.addListener(new ChangeListener<ProcurementOrder>()
				{
			@Override
			public void changed(
					ObservableValue<? extends ProcurementOrder> property,
					ProcurementOrder oldValue, ProcurementOrder newValue)
			{
				if (newValue != null){
					handleProcurementSeclection(newValue);

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
		itemSearchService.setOnFailed(callFailedEventHandler);

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

		listView.getPrintXlsButton().setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				ProcurementOrder selectedItem = listView.getDataList().getSelectionModel().getSelectedItem();
				if(selectedItem!=null){
					ProcurementOrderXlsExporter.exportProcurementToXls(listView.getDataListItem().getItems().iterator());
				}else {
					Dialogs.create().message("Selectionner une commande ").showInformation();
				}

			}
		});

		listView.getRetreivedButton().setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				ProcurementOrder selectedItem = listView.getDataList().getSelectionModel().getSelectedItem();
				if(selectedItem!=null && DocumentProcessingState.SENT.equals(selectedItem.getPoStatus())){
					showProgressBarRequestEvent.fire(new Object());
					phmlSendAndReceiveService.setProcurementOrder(selectedItem).setToBeSent(false).start();
				}else {
					Dialogs.create().message("La commande dois etre envoyer !").showInformation();
				}

			}
		});

		/*
		 * listen to remove  button and fire remove select event.
		 */
		listView.getSentButton().setOnAction(new EventHandler<ActionEvent>()
				{
			@Override
			public void handle(ActionEvent e)
			{
				if(NetWorkChecker.hasNetwork()){
					ProcurementOrder selectedItem = listView.getDataList().getSelectionModel().getSelectedItem();
					if(selectedItem!=null && DocumentProcessingState.ONGOING.equals(selectedItem.getPoStatus())){
						showProgressBarRequestEvent.fire(new Object());
						phmlSendAndReceiveService.setProcurementOrder(selectedItem).setToBeSent(true).start();
					}else {
						Dialogs.create().message("La commande dois etre encour !").showInformation();

					}
				}else {
					Dialogs.create().message("Impossible de joindre le serveur Phml Verifier votre connection Internet").showInformation();
				}

			}
				});

		phmlSendAndReceiveService.setOnFailed(callFailedEventHandler);
		phmlSendAndReceiveService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				PhmlSendAndReceiveService s = (PhmlSendAndReceiveService) event.getSource();
				ProcurementOrder value = s.getValue();
				handleEditDoneEvent(value);
				event.consume();
				s.reset();
				if(phmlSendAndReceiveService.isToBeSent()){
					Dialogs.create().message("Commande Envoyer Sur Phml Avec success !").showInformation();

				}else {
					Dialogs.create().message("Commande Receptionnee de Phml Avec success !").showInformation();
				}
				hideProgressBarRequestEvent.fire(new Object());
			}
		});
		/*
		 * listen to remove  button and fire remove select event.
		 */
		listView.getRemoveButton().setOnAction(new EventHandler<ActionEvent>()
				{
			@Override
			public void handle(ActionEvent e)
			{
				ProcurementOrder selectedItem = listView.getDataList().getSelectionModel().getSelectedItem();
				if(selectedItem!=null && !DocumentProcessingState.CLOSED.equals(selectedItem.getPoStatus()))
					removeRequest.fire(selectedItem);

			}
				});

		/*
		 * listen to edit button and fire search select event.
		 */
		listView.getEditButton().setOnAction(new EventHandler<ActionEvent>()
				{
			@Override
			public void handle(ActionEvent e)
			{
				ProcurementOrder selectedItem = listView.getDataList().getSelectionModel().getSelectedItem();
				if(selectedItem!=null)
					selectionEvent.fire(selectedItem);
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

		phmlSenderService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				ProcurementOrderPhmlSenderService s = (ProcurementOrderPhmlSenderService) event.getSource();
				ProcurementOrder value = s.getValue();
				event.consume();
				s.reset();
				//				handleRemovedEvent(value);
			}
		});

		phmlSenderService.setOnFailed(callFailedEventHandler);

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

		listView.getCreateButton().setOnAction(new EventHandler<ActionEvent>()
				{
			@Override
			public void handle(ActionEvent e)
			{
				orderPreparationEventData.fire(new ProcurementOrderPreparationData());
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

	private void handleProcurementSeclection(ProcurementOrder newValue){
		listView.getRemoveButton().disableProperty().unbind();
		listView.getSentButton().disableProperty().unbind();
		listView.getRetreivedButton().disableProperty().unbind();
		listView.getRemoveButton().disableProperty().bind(newValue.poStatusProperty().isEqualTo(DocumentProcessingState.CLOSED));
		listView.getSentButton().disableProperty().bind(newValue.poStatusProperty().isEqualTo(DocumentProcessingState.SENT));
		listView.getRetreivedButton().disableProperty().bind(newValue.poStatusProperty().isEqualTo(DocumentProcessingState.RETREIVED));
		//listView.getRetreivedButton().disableProperty().bind(newValue.poStatusProperty().isEqualTo(DocumentProcessingState.CLOSED));

		ProcurementOrderItemSearchInput poisi = new ProcurementOrderItemSearchInput();
		poisi.getEntity().setProcurementOrder(new ProcurementOrderItemProcurementOrder(newValue));
		poisi.getFieldNames().add("procurementOrder");
		poisi.setMax(-1);
		itemSearchService.setSearchInputs(poisi).start();
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
		listView.getDataList().getItems().setAll(entities);
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
		listView.getDataListItem().getItems().clear();
	}

	public void handleEditDoneEvent(@Observes @EntityEditDoneEvent ProcurementOrder selectedEntity)
	{
		int selectedIndex = listView.getDataList().getItems().indexOf(selectedEntity);
		if (selectedIndex <= -1)
			return;
		ProcurementOrder entity = listView.getDataList().getItems().get(selectedIndex);
		PropertyReader.copy(selectedEntity, entity);

		//		ArrayList<ProcurementOrder> arrayList = new ArrayList<ProcurementOrder>(listView.getDataList().getItems());
		//		listView.getDataList().getItems().clear();
		//		listView.getDataList().getItems().addAll(arrayList);
		//		listView.getDataList().getSelectionModel().select(selectedEntity);
		handleProcurementSeclection(entity);
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

	public void reset() {
		listView.getDataList().getItems().clear();
	}
}
