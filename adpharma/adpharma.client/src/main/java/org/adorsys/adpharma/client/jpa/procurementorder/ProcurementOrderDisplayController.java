package org.adorsys.adpharma.client.jpa.procurementorder;

import java.math.BigDecimal;
import java.util.List;

import javafx.collections.ObservableList;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.adpharma.client.jpa.article.Article;
import org.adorsys.adpharma.client.jpa.article.ArticleSearchInput;
import org.adorsys.adpharma.client.jpa.procurementorderitem.ProcurementOrderItem;
import org.adorsys.adpharma.client.jpa.procurementorderitem.ProcurementOrderItemArticle;
import org.adorsys.adpharma.client.jpa.procurementorderitem.ProcurementOrderItemCreateService;
import org.adorsys.adpharma.client.jpa.procurementorderitem.ProcurementOrderItemEditService;
import org.adorsys.adpharma.client.jpa.procurementorderitem.ProcurementOrderItemProcurementOrder;
import org.adorsys.adpharma.client.jpa.procurementorderitem.ProcurementOrderItemRemoveService;
import org.adorsys.adpharma.client.jpa.procurementorderitem.ProcurementOrderItemSearchInput;
import org.adorsys.adpharma.client.jpa.procurementorderitem.ProcurementOrderItemSearchResult;
import org.adorsys.adpharma.client.jpa.procurementorderitem.ProcurementOrderItemSearchService;
import org.adorsys.adpharma.client.jpa.salesorderitem.SalesOrderItem;
import org.adorsys.javafx.crud.extensions.EntityController;
import org.adorsys.javafx.crud.extensions.ViewType;
import org.adorsys.javafx.crud.extensions.events.AssocSelectionEventData;
import org.adorsys.javafx.crud.extensions.events.AssocSelectionResponseEvent;
import org.adorsys.javafx.crud.extensions.events.ComponentSelectionRequestData;
import org.adorsys.javafx.crud.extensions.events.ComponentSelectionRequestEvent;
import org.adorsys.javafx.crud.extensions.events.EntityEditRequestedEvent;
import org.adorsys.javafx.crud.extensions.events.EntityRemoveRequestEvent;
import org.adorsys.javafx.crud.extensions.events.EntitySearchRequestedEvent;
import org.adorsys.javafx.crud.extensions.events.EntitySelectionEvent;
import org.adorsys.javafx.crud.extensions.events.ModalEntityCreateDoneEvent;
import org.adorsys.javafx.crud.extensions.events.ModalEntitySearchDoneEvent;
import org.adorsys.javafx.crud.extensions.events.ModalEntitySearchRequestedEvent;
import org.adorsys.javafx.crud.extensions.events.SelectedModelEvent;
import org.adorsys.javafx.crud.extensions.login.ErrorDisplay;
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
	@EntityRemoveRequestEvent
	private Event<ProcurementOrder> removeRequest;

	@Inject 
	@ModalEntitySearchRequestedEvent
	private Event<ArticleSearchInput> modalArticleSearchEvent;


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
	private ServiceCallFailedEventHandler serviceCallFailedEventHandler;

	@Inject
	private ProcurementOrder displayedEntity;

	@Inject
	private ProcurementOrderRegistration registration;

	@Inject
	private ProcurementOrderItem item ;

	@PostConstruct
	public void postConstruct()
	{
		displayView.bind(item);
		displayView.bind(displayedEntity);
		serviceCallFailedEventHandler.setErrorDisplay(new ErrorDisplay() {

			@Override
			protected void showError(Throwable exception) {
				Dialogs.create().showException(exception);

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

		/*
		 * listen to delete menu Item.
		 */

		displayView.getDeleteItemMenu().setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				ProcurementOrderItem selectedItem = displayView.getDataList().getSelectionModel().getSelectedItem();
				if(selectedItem!=null) {
					itemRemoveService.setEntity(selectedItem).start();
				}

			}
		});

		displayView.getOkButton().setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if(isValidItem())
					handleAddItem(item);
			}

		});

		//      /*
		//       * listen to search button and fire search requested event.
		//       */
		//      displayView.getSearchButton().setOnAction(new EventHandler<ActionEvent>()
		//      {
		//         @Override
		//         public void handle(ActionEvent e)
		//         {
		//            searchRequestedEvent.fire(displayedEntity);
		//         }
		//      });
		//
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
				displayView.getDataList().getItems().add(createdItem);
				PropertyReader.copy(new ProcurementOrderItem(), item);
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
				displayView.getDataList().getItems().add(editedItem);
				PropertyReader.copy(new ProcurementOrderItem(), item);

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
				PropertyReader.copy(new ProcurementOrderItem(), item);

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

	/**
	 * This is the only time where the bind method is called on this object.
	 * @param model
	 */
	public void handleNewModelEvent(@Observes @SelectedModelEvent ProcurementOrder model)
	{
		this.displayedEntity = model;
		displayView.bind(this.displayedEntity);
	}

}