package org.adorsys.adpharma.client.jpa.procurementorder;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.SplitPane.Divider;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.adpharma.client.events.PrintRequestedEvent;
import org.adorsys.adpharma.client.events.ProcurementOrderId;
import org.adorsys.adpharma.client.jpa.accessroleenum.AccessRoleEnum;
import org.adorsys.adpharma.client.jpa.article.Article;
import org.adorsys.adpharma.client.jpa.article.ArticleSearchInput;
import org.adorsys.adpharma.client.jpa.delivery.Delivery;
import org.adorsys.adpharma.client.jpa.delivery.DeliveryFromOrderServeice;
import org.adorsys.adpharma.client.jpa.documentprocessingstate.DocumentProcessingState;
import org.adorsys.adpharma.client.jpa.procurementorderitem.ProcurementOrderItem;
import org.adorsys.adpharma.client.jpa.procurementorderitem.ProcurementOrderItemArticle;
import org.adorsys.adpharma.client.jpa.procurementorderitem.ProcurementOrderItemCreateService;
import org.adorsys.adpharma.client.jpa.procurementorderitem.ProcurementOrderItemEditService;
import org.adorsys.adpharma.client.jpa.procurementorderitem.ProcurementOrderItemProcurementOrder;
import org.adorsys.adpharma.client.jpa.procurementorderitem.ProcurementOrderItemRemoveService;
import org.adorsys.adpharma.client.jpa.procurementorderitem.ProcurementOrderItemSearchInput;
import org.adorsys.adpharma.client.jpa.procurementorderitem.ProcurementOrderItemSearchResult;
import org.adorsys.adpharma.client.jpa.procurementorderitem.ProcurementOrderItemSearchService;
import org.adorsys.adpharma.client.utils.DeliveryFromOrderData;
import org.adorsys.javafx.crud.extensions.EntityController;
import org.adorsys.javafx.crud.extensions.ViewType;
import org.adorsys.javafx.crud.extensions.events.AssocSelectionEventData;
import org.adorsys.javafx.crud.extensions.events.AssocSelectionResponseEvent;
import org.adorsys.javafx.crud.extensions.events.ComponentSelectionRequestData;
import org.adorsys.javafx.crud.extensions.events.ComponentSelectionRequestEvent;
import org.adorsys.javafx.crud.extensions.events.EntityEditDoneEvent;
import org.adorsys.javafx.crud.extensions.events.EntityEditRequestedEvent;
import org.adorsys.javafx.crud.extensions.events.EntityRemoveRequestEvent;
import org.adorsys.javafx.crud.extensions.events.EntitySearchRequestedEvent;
import org.adorsys.javafx.crud.extensions.events.EntitySelectionEvent;
import org.adorsys.javafx.crud.extensions.events.HideProgressBarRequestEvent;
import org.adorsys.javafx.crud.extensions.events.ModalEntityCreateDoneEvent;
import org.adorsys.javafx.crud.extensions.events.ModalEntitySearchDoneEvent;
import org.adorsys.javafx.crud.extensions.events.ModalEntitySearchRequestedEvent;
import org.adorsys.javafx.crud.extensions.events.SelectedModelEvent;
import org.adorsys.javafx.crud.extensions.events.ShowProgressBarRequestEvent;
import org.adorsys.javafx.crud.extensions.login.ErrorDisplay;
import org.adorsys.javafx.crud.extensions.login.RolesEvent;
import org.adorsys.javafx.crud.extensions.login.ServiceCallFailedEventHandler;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;
import org.apache.commons.lang3.StringUtils;
import org.controlsfx.dialog.Dialogs;

@Singleton
public class ProcurementOrderDisplayController implements EntityController
{

	@Inject
	private ProcurementOrderDisplayView displayView;

	@Inject
	@EntitySearchRequestedEvent
	private Event<ProcurementOrder> searchRequestedEvent;

	@Inject
	@EntityEditRequestedEvent
	private Event<ProcurementOrder> editRequestEvent;

	@Inject
	@ComponentSelectionRequestEvent
	private Event<ComponentSelectionRequestData> componentSelectionRequestData;

	@Inject
	@EntityRemoveRequestEvent
	private Event<ProcurementOrder> removeRequest;

	@Inject 
	@ModalEntitySearchRequestedEvent
	private Event<ArticleSearchInput> modalArticleSearchEvent;

	@Inject 
	@EntitySelectionEvent
	private Event<Delivery> deliveryEditRequestEvent;
	
	@Inject 
	@EntitySelectionEvent
	private Event<ProcurementOrder> orderSelectedRequestEvent;

	@Inject 
	@EntityEditDoneEvent
	private Event<ProcurementOrder> procurementEditRequestEvent;


	@Inject
	@AssocSelectionResponseEvent
	private Event<AssocSelectionEventData<ProcurementOrder>> selectionResponseEvent;

	//	private ObjectProperty<AssocSelectionEventData<ProcurementOrder>> pendingSelectionRequestProperty = new SimpleObjectProperty<AssocSelectionEventData<ProcurementOrder>>();

	@Inject
	@ComponentSelectionRequestEvent
	private Event<ComponentSelectionRequestData> componentSelectionRequestEvent;

	@Inject
	private ProcurementOrderItemSearchService itemSearchService;

	@Inject
	private ProcurementOrderItemEditService itemEditService;

	@Inject
	private ProcurementOrderItemRemoveService itemRemoveService;

	@Inject
	private ProcurementOrderItemCreateService itemCreateService;

	@Inject
	private DeliveryFromOrderServeice deliveryFromOrderServeice ;
	
	@Inject
	private ProcurementOrderRetrievedService procurementOrderRetrievedService;


	@Inject
	private ServiceCallFailedEventHandler serviceCallFailedEventHandler;

	@Inject
	private ProcurementOrder displayedEntity;

	@Inject
	private ProcurementOrderRegistration registration;

	@Inject
	private ProcurementOrderItem item ;

	@Inject
	@PrintRequestedEvent
	private Event<ProcurementOrderId> printRequestedEvent;

	@Inject
	@ShowProgressBarRequestEvent
	private Event<Object> showProgressBarRequestEvent ;

	@Inject
	@HideProgressBarRequestEvent
	private Event<Object> hideProgressBarRequestEvent ;

	@PostConstruct
	public void postConstruct()
	{
		displayView.bind(item);
		displayView.bind(displayedEntity);
		displayView.getDeliverButton().disableProperty().bind(deliveryFromOrderServeice.runningProperty());
		displayView.getRetrievedButton().disableProperty().bind(procurementOrderRetrievedService.runningProperty());
		serviceCallFailedEventHandler.setErrorDisplay(new ErrorDisplay() {


			@Override
			protected void showError(Throwable exception) {
				Dialogs.create().showException(exception);
				hideProgressBarRequestEvent.fire(new Object());
			}
		});
		displayView.getOrderQuantityColumn().setOnEditCommit(new EventHandler<CellEditEvent<ProcurementOrderItem,BigDecimal>>() {
			@Override
			public void handle(CellEditEvent<ProcurementOrderItem, BigDecimal> orderedQtyCell) {
				ProcurementOrderItem selectedItem = orderedQtyCell.getRowValue();
				BigDecimal newValue = orderedQtyCell.getNewValue();
				if(newValue==null){
					// reset old value.
				} else if (newValue.compareTo(BigDecimal.ZERO)<=0){
					// delete article
					itemRemoveService.setEntity(selectedItem).start();
				} else {
					selectedItem.setQtyOrdered(newValue);
					selectedItem.calculateTotalAmout();
					// update article
					itemEditService.setProcurementOrderItem(selectedItem).start();
				}
			}
		});

		displayView.getAvailableQtyColumn().setOnEditCommit(new EventHandler<CellEditEvent<ProcurementOrderItem,BigDecimal>>() {
			@Override
			public void handle(CellEditEvent<ProcurementOrderItem, BigDecimal> availebleQtyCell) {
				ProcurementOrderItem selectedItem = availebleQtyCell.getRowValue();
				BigDecimal newValue = availebleQtyCell.getNewValue();
				if(newValue!=null && BigDecimal.ZERO.compareTo(newValue)<0){
					System.out.println(newValue);
					if(selectedItem.getQtyOrdered().compareTo(newValue)>=0){
						selectedItem.setAvailableQty(newValue);
						selectedItem.calculateTotalAmout();
						itemEditService.setProcurementOrderItem(selectedItem).start();
					}
				}
			}
		});

		displayView.getPurchasePricePUColumn().setOnEditCommit(new EventHandler<CellEditEvent<ProcurementOrderItem,BigDecimal>>() {
			@Override
			public void handle(CellEditEvent<ProcurementOrderItem, BigDecimal> puCell) {
				ProcurementOrderItem selectedItem = puCell.getRowValue();
				BigDecimal newValue = puCell.getNewValue();
				if(newValue==null){
					// reset old value.
				} else if (newValue.compareTo(BigDecimal.ZERO)<=0){

				} else {
					selectedItem.setPurchasePricePU(newValue);
					selectedItem.calculateTotalAmout();
					// update article
					itemEditService.setProcurementOrderItem(selectedItem).start();
				}
			}
		});

		displayView.getSalesPricePUColumn().setOnEditCommit(new EventHandler<CellEditEvent<ProcurementOrderItem,BigDecimal>>() {
			@Override
			public void handle(CellEditEvent<ProcurementOrderItem, BigDecimal> spCell) {
				ProcurementOrderItem selectedItem = spCell.getRowValue();
				BigDecimal newValue = spCell.getNewValue();
				if(newValue==null){
					// reset old value.
				} else if (newValue.compareTo(BigDecimal.ZERO)<=0){

				} else {
					selectedItem.setSalesPricePU(newValue);
					selectedItem.calculateTotalAmout();
					// update article
					itemEditService.setProcurementOrderItem(selectedItem).start();
				}
			}
		});
		itemSearchService.setOnFailed(serviceCallFailedEventHandler);

		itemSearchService.setOnSucceeded(new EventHandler<WorkerStateEvent>()
				{
			@Override
			public void handle(WorkerStateEvent event)
			{
				ProcurementOrderItemSearchService s = (ProcurementOrderItemSearchService) event.getSource();
				ProcurementOrderItemSearchResult searchResult = s.getValue();
				event.consume();
				s.reset();
				List<ProcurementOrderItem> resultList = searchResult.getResultList();
				displayedEntity.setProcurementOrderItems(resultList);
			}
				});
		//      displayView.getEditButton().disableProperty().bind(registration.canEditProperty().not());
		//      displayView.getRemoveButton().disableProperty().bind(registration.canEditProperty().not());
		//
		/*
		 * listen to article name textfied.
		 */
		displayView.getArticleName().setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				KeyCode code = event.getCode();
				if(code== KeyCode.ENTER){
					String articleName = displayView.getArticleName().getText();
					if(StringUtils.isBlank(articleName)) return;
					Article entity = new Article();
					entity.setArticleName(articleName);
					ArticleSearchInput asi = new ArticleSearchInput();
					asi.setEntity(entity);
					asi.getFieldNames().add("articleName");
					modalArticleSearchEvent.fire(asi);
				}
			}
		});

		/*
		 * listen to Print button.
		 */
		displayView.getPrintButton().setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if(displayedEntity==null || displayedEntity.getId()==null) return;
				printRequestedEvent.fire(new ProcurementOrderId(displayedEntity.getId()));
			}
		});

		/*
		 * listen to Ok button.
		 */
		displayView.getOkButton().disableProperty().bind(itemCreateService.runningProperty());
		displayView.getOkButton().disableProperty().bind(itemEditService.runningProperty());
		displayView.getOkButton().setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				KeyCode code = event.getCode();
				if(isValidItem())
					handleAddItem(item);
			}

		});


		displayView.getOkButton().setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if(isValidItem())
					handleAddItem(item);
			}

		});



		/*
		 * listen to printXls button and fire search requested event.
		 */
		displayView.getPrintXlsButton().setOnAction(new EventHandler<ActionEvent>()
				{
			@Override
			public void handle(ActionEvent e)
			{
				if(displayedEntity!=null&&displayView.getDataList().getItems().iterator().hasNext()){
					ProcurementOrderXlsExporter.exportProcurementToXls(displayView.getDataList().getItems().iterator());
				}else {
					Dialogs.create().message("La  commande dois avoir au moins une ligne  ").showInformation();
				}

			}
				});



		/*
		 * listen to save button and fire search requested event.
		 */
		displayView.getDeliverButton().setOnAction(new EventHandler<ActionEvent>()
				{
			@Override
			public void handle(ActionEvent e)
			{
				if(DocumentProcessingState.RETREIVED.equals(displayedEntity.getPoStatus())){
					showProgressBarRequestEvent.fire(new Object());
					deliveryFromOrderServeice.setModel(displayedEntity).start();
				}else {
					Dialogs.create().message("La commande dois etre dans l'etat recu !").showInformation();
				}
			}
				});

		deliveryFromOrderServeice.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {

				DeliveryFromOrderServeice s = (DeliveryFromOrderServeice) event.getSource();
				DeliveryFromOrderData data = s.getValue();
				event.consume();
				s.reset();
				procurementEditRequestEvent.fire(data.getOrder());
				//				handleSelectionEvent(data.getOrder());
				componentSelectionRequestEvent.fire(new ComponentSelectionRequestData(Delivery.class.getName()));
				deliveryEditRequestEvent.fire(data.getDelivery());
				hideProgressBarRequestEvent.fire(new Object());
			}
		});
		deliveryFromOrderServeice.setOnFailed(serviceCallFailedEventHandler);
		
		
		displayView.getRetrievedButton().setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				procurementOrderRetrievedService.setModel(displayedEntity).start();
			}
		});
		procurementOrderRetrievedService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent event) {
				ProcurementOrderRetrievedService s= (ProcurementOrderRetrievedService)event.getSource();
				ProcurementOrder order = s.getValue();
				event.consume();
				s.reset();
//					List<ProcurementOrderItem> procurementOrderItems = order.getProcurementOrderItems();
//					ObservableList<ProcurementOrderItem> observableList= FXCollections.observableArrayList();
//					observableList.setAll(procurementOrderItems);
//					displayView.getDataList().setItems(observableList);
				orderSelectedRequestEvent.fire(order);
			}
		});
		procurementOrderRetrievedService.setOnFailed(serviceCallFailedEventHandler);
		

		displayView.getCancelButton().setOnAction(new EventHandler<ActionEvent>()
				{
			@Override
			public void handle(ActionEvent e)
			{
				searchRequestedEvent.fire(displayedEntity);
			}
				});


		itemCreateService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				ProcurementOrderItemCreateService s = (ProcurementOrderItemCreateService) event.getSource();
				ProcurementOrderItem createdItem = s.getValue();
				event.consume();
				s.reset();
				int index = displayView.getDataList().getItems().indexOf(createdItem);
				if(index>-1){
					ProcurementOrderItem displayed = displayView.getDataList().getItems().get(index);
					PropertyReader.copy(createdItem, displayed);
				}else {
					displayView.getDataList().getItems().add(createdItem);
				}
				updateProcurementOrder(item);
			}
		});
		itemCreateService.setOnFailed(serviceCallFailedEventHandler);

		itemEditService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				ProcurementOrderItemEditService s = (ProcurementOrderItemEditService) event.getSource();
				ProcurementOrderItem editedItem = s.getValue();
				event.consume();
				s.reset();
				int index = displayView.getDataList().getItems().indexOf(editedItem);
				if(index>-1){
					ProcurementOrderItem displayed = displayView.getDataList().getItems().get(index);
					PropertyReader.copy(editedItem, displayed);
				}
				updateProcurementOrder(editedItem);

			}
		});
		itemEditService.setOnFailed(serviceCallFailedEventHandler);

		itemRemoveService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				ProcurementOrderItemRemoveService s = (ProcurementOrderItemRemoveService) event.getSource();
				ProcurementOrderItem removeddItem = s.getValue();
				event.consume();
				s.reset();
				displayView.getDataList().getItems().remove(removeddItem);
				updateProcurementOrder(removeddItem);

			}
		});
		itemRemoveService.setOnFailed(serviceCallFailedEventHandler);


		//
		//      displayView.getConfirmSelectionButton().setOnAction(new EventHandler<ActionEvent>()
		//      {
		//         @Override
		//         public void handle(ActionEvent e)
		//         {
		//            final AssocSelectionEventData<ProcurementOrder> pendingSelectionRequest = pendingSelectionRequestProperty.get();
		//            if (pendingSelectionRequest == null)
		//               return;
		//            pendingSelectionRequestProperty.set(null);
		//            pendingSelectionRequest.setTargetEntity(displayedEntity);
		//            selectionResponseEvent.fire(pendingSelectionRequest);
		//         }
		//      });
		//
		//      displayView.getConfirmSelectionButton().visibleProperty().bind(pendingSelectionRequestProperty.isNotNull());
	}

	//	private static final BigDecimal HUNDRED = new BigDecimal(100); 
	public void updateProcurementOrder(ProcurementOrderItem procurementOrderItem){
		ProcurementOrderItemProcurementOrder procurementOrder = procurementOrderItem.getProcurementOrder();
		PropertyReader.copy(procurementOrder, displayedEntity);
		PropertyReader.copy(new ProcurementOrderItem(),item);
	}

	public void display(Pane parent)
	{
		BorderPane rootPane = displayView.getRootPane();
		ObservableList<Node> children = parent.getChildren();
		if (!children.contains(rootPane))
		{
			children.add(rootPane);
		}
	}

	@Override
	public ViewType getViewType()
	{
		return ViewType.DISPLAY;
	}

	/**
	 * Listens to list selection events and bind selected to pane
	 */
	public void handleSelectionEvent(@Observes @EntitySelectionEvent ProcurementOrder selectedEntity)
	{
		PropertyReader.copy(selectedEntity, displayedEntity);
		itemSearchService.setSearchInputs(getSearchInput(displayedEntity)).start();
	}

	//   public void handleAssocSelectionRequest(@Observes(notifyObserver = Reception.ALWAYS) @AssocSelectionRequestEvent AssocSelectionEventData<ProcurementOrder> eventData)
	//   {
	//      pendingSelectionRequestProperty.set(eventData);
	//      componentSelectionRequestEvent.fire(new ComponentSelectionRequestData(ProcurementOrder.class.getName()));
	//      searchRequestedEvent.fire(eventData.getTargetEntity() != null ? eventData.getTargetEntity() : new ProcurementOrder());
	//   }

	public ProcurementOrderItemSearchInput getSearchInput(ProcurementOrder template){
		ProcurementOrderItemSearchInput searchInput = new ProcurementOrderItemSearchInput();
		searchInput.setMax(-1);
		searchInput.getEntity().setProcurementOrder(new ProcurementOrderItemProcurementOrder(template));
		searchInput.getFieldNames().add("procurementOrder");
		return searchInput;
	}

	public boolean isValidItem(){
		BigDecimal orderedQty = item.getQtyOrdered();
		ProcurementOrderItemArticle article = item.getArticle();
		if(article==null || article.getId()==null){
			Dialogs.create().nativeTitleBar().message("you need to specified article ").showError();
			return false;
		}
		if(orderedQty==null || orderedQty.compareTo(BigDecimal.ZERO)==0){
			Dialogs.create().nativeTitleBar().message("orderedQty is required ").showError();
			return false;
		}
		item.setTotalPurchasePrice(item.getQtyOrdered().multiply(item.getPurchasePricePU()));

		return true;

	}
	private void handleAddItem(ProcurementOrderItem item) {
		item.calculateTotalAmout();
		if(item.getId()==null){
			itemCreateService.setModel(item).start();
		}else {
			itemEditService.setProcurementOrderItem(item).start();
		}
	}
	private void handleSelectedArticle(Article article) {
		ProcurementOrderItem fromArticle = ProcurementOrderItem.fromArticle(article);
		fromArticle.setProcurementOrder(new ProcurementOrderItemProcurementOrder(displayedEntity));
		PropertyReader.copy(fromArticle, item);
	}

	private void handleModalArticleCreateDone(@Observes @ModalEntityCreateDoneEvent Article article) {
		handleSelectedArticle(article);

	} 

	public void handleArticleSearchDone(@Observes @ModalEntitySearchDoneEvent Article article)
	{
		handleSelectedArticle(article);
	}
	
	public void handleRolesEvent(@Observes(notifyObserver=Reception.ALWAYS) @RolesEvent Set<String> roles){
		if(roles.contains(AccessRoleEnum.MANAGER.name())){
			displayView.getRetrievedButton().setVisible(true);
		}else {
			displayView.getRetrievedButton().setVisible(false);
		}
	}

	/**
	 * This is the only time where the bind method is called on this object.
	 * @param model
	 */
	public void handleNewModelEvent(@Observes @SelectedModelEvent ProcurementOrder model)
	{
		this.displayedEntity = model;
		displayView.bind(this.displayedEntity);
	}

	public void reset() {
		PropertyReader.copy(new ProcurementOrder(), displayedEntity);
	}
}