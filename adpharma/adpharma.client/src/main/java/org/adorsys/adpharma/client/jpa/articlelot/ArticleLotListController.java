package org.adorsys.adpharma.client.jpa.articlelot;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
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

import org.adorsys.adpharma.client.events.ArticlelotMovedDoneRequestEvent;
import org.adorsys.adpharma.client.jpa.warehousearticlelot.WareHouseArticleLot;
import org.adorsys.javafx.crud.extensions.EntityController;
import org.adorsys.javafx.crud.extensions.ViewType;
import org.adorsys.javafx.crud.extensions.events.EntityCreateDoneEvent;
import org.adorsys.javafx.crud.extensions.events.EntityEditCanceledEvent;
import org.adorsys.javafx.crud.extensions.events.EntityEditDoneEvent;
import org.adorsys.javafx.crud.extensions.events.EntityListPageIndexChangedEvent;
import org.adorsys.javafx.crud.extensions.events.EntityRemoveDoneEvent;
import org.adorsys.javafx.crud.extensions.events.EntitySearchDoneEvent;
import org.adorsys.javafx.crud.extensions.events.EntitySelectionEvent;
import org.adorsys.javafx.crud.extensions.events.ModalEntityCreateRequestedEvent;
import org.adorsys.javafx.crud.extensions.login.ErrorDisplay;
import org.adorsys.javafx.crud.extensions.login.ServiceCallFailedEventHandler;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;
import org.adorsys.javafx.crud.extensions.utils.PaginationUtils;
import org.apache.commons.lang3.StringUtils;
import org.controlsfx.dialog.Dialogs;

@Singleton
public class ArticleLotListController implements EntityController
{

	@Inject
	private ArticleLotListView listView;

	@Inject
	@EntitySelectionEvent
	private Event<ArticleLot> selectionEvent;

	@Inject
	@ModalEntityCreateRequestedEvent
	private Event<ArticleLotDetailsManager> detailscreateRequestedEvent;

	@Inject
	@ModalEntityCreateRequestedEvent
	private Event<ArticleLotTransferManager> transferCreateRequestedEvent;

	@Inject
	@EntityListPageIndexChangedEvent
	private Event<ArticleLotSearchResult> entityListPageIndexChangedEvent;

	@Inject
	private Event<ArticleLotMovedToTrashData> articleLotTrashRequestEvent;

	private ArticleLotSearchResult searchResult;

	@Inject
	private ArticleLotSearchInput searchInput;

	@Inject
	private ArticleLotSearchService searchService;

	@Inject 
	private ServiceCallFailedEventHandler callFailedEventHandler ;

	@Inject
	private ArticleLotRegistration registration;

	@PostConstruct
	public void postConstruct()
	{
		//		listView.getCreateButton().disableProperty().bind(registration.canCreateProperty().not());
		listView.getDetailsButton().disableProperty().bind(registration.canCreateProperty().not());
		listView.getMoveButton().disableProperty().bind(registration.canEditProperty().not());
		listView.getSearchButton().disableProperty().bind(searchService.runningProperty());
		listView.bind(searchInput);

		callFailedEventHandler.setErrorDisplay(new ErrorDisplay() {

			@Override
			protected void showError(Throwable exception) {
				Dialogs.create().showException(exception);

			}
		});

		listView.getUpdateLotButton().setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				ArticleLot selectedItem = listView.getDataList().getSelectionModel().getSelectedItem();
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

		searchService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				ArticleLotSearchService s = (ArticleLotSearchService) event.getSource();
				searchResult = s.getValue();
				event.consume();
				s.reset();
				handleSearchResult(searchResult);

			}
		});
		searchService.setOnFailed(callFailedEventHandler);

		//		listView.getCreateButton().setOnAction(new EventHandler<ActionEvent>()
		//				{
		//			@Override
		//			public void handle(ActionEvent e)
		//			{
		//				ArticleLot selectedItem = listView.getDataList().getSelectionModel().getSelectedItem();
		//				if (selectedItem == null)
		//					selectedItem = new ArticleLot();
		//				createRequestedEvent.fire(selectedItem);
		//			}
		//				});
		listView.getDetailsButton().setOnAction(new EventHandler<ActionEvent>()
				{
			@Override
			public void handle(ActionEvent e)
			{
				ArticleLot selectedItem = listView.getDataList().getSelectionModel().getSelectedItem();
				if(selectedItem!=null){
					ArticleLotDetailsManager lotDetailsManager = new ArticleLotDetailsManager();
					lotDetailsManager.setLotToDetails(selectedItem);
					lotDetailsManager.setLotQty(selectedItem.getStockQuantity());
					detailscreateRequestedEvent.fire(lotDetailsManager);

				}
			}
				});
		listView.getMoveToWareHouseButton().setOnAction(new EventHandler<ActionEvent>()
				{
			@Override
			public void handle(ActionEvent e)
			{
				ArticleLot selectedItem = listView.getDataList().getSelectionModel().getSelectedItem();
				if(selectedItem!=null){
					ArticleLotTransferManager lotTransferManager = new ArticleLotTransferManager();
					lotTransferManager.setLotToTransfer(selectedItem);
					lotTransferManager.setLotQty(selectedItem.getStockQuantity());
					transferCreateRequestedEvent.fire(lotTransferManager);
				}
			}
				});
		listView.getMoveButton().setOnAction(new EventHandler<ActionEvent>()
				{
			@Override
			public void handle(ActionEvent e)
			{
				ArticleLot selectedItem = listView.getDataList().getSelectionModel().getSelectedItem();
				if(selectedItem!=null){
					ArticleLotMovedToTrashData data = new ArticleLotMovedToTrashData();
					PropertyReader.copy(selectedItem, data);
					articleLotTrashRequestEvent.fire(data);
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
					searchResult.setSearchInput(new ArticleLotSearchInput());
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
	 * in the main articleLot controller.
	 * 
	 * @param entities
	 */
	public void handleSearchResult(@Observes @EntitySearchDoneEvent ArticleLotSearchResult searchResult)
	{
		this.searchResult = searchResult;
		List<ArticleLot> entities = searchResult.getResultList();
		entities.sort(new Comparator<ArticleLot>() {

			@Override
			public int compare(ArticleLot o1, ArticleLot o2) {
				return o1.getArticle().getArticleName().compareTo(o2.getArticle().getArticleName());
			}
		});
		if (entities == null)
			entities = new ArrayList<ArticleLot>();
		listView.getDataList().getItems().clear();
		listView.getDataList().getItems().addAll(entities);
		int maxResult = searchResult.getSearchInput() != null ? searchResult.getSearchInput().getMax() : 5;
		int pageCount = PaginationUtils.computePageCount(searchResult.getCount(), maxResult);
		listView.getPagination().setPageCount(pageCount);
		int firstResult = searchResult.getSearchInput() != null ? searchResult.getSearchInput().getStart() : 0;
		int pageIndex = PaginationUtils.computePageIndex(firstResult, searchResult.getCount(), maxResult);
		listView.getPagination().setCurrentPageIndex(pageIndex);

	}

	public void handleCreatedEvent(@Observes @EntityCreateDoneEvent ArticleLot createdEntity)
	{
		listView.getDataList().getItems().add(0, createdEntity);
	}

	public void handleCreatedEvent(@Observes @EntityCreateDoneEvent WareHouseArticleLot wareHouseArticleLot)
	{

		PropertyReader.copy(wareHouseArticleLot.getArticleLot(), listView.getDataList().getSelectionModel().getSelectedItem());
	}

	public void handleRemovedEvent(@Observes @EntityRemoveDoneEvent ArticleLot removedEntity)
	{
		listView.getDataList().getItems().remove(removedEntity);
	}

	public void handleEditDoneEvent(@Observes @EntityEditDoneEvent ArticleLot selectedEntity)
	{
		int selectedIndex = listView.getDataList().getItems().indexOf(selectedEntity);
		if (selectedIndex <= -1)
			return;
		ArticleLot entity = listView.getDataList().getItems().get(selectedIndex);
		PropertyReader.copy(selectedEntity, entity);

		ArrayList<ArticleLot> arrayList = new ArrayList<ArticleLot>(listView.getDataList().getItems());
		listView.getDataList().getItems().clear();
		listView.getDataList().getItems().addAll(arrayList);
		listView.getDataList().getSelectionModel().select(selectedEntity);
	}

	public void handleEditCanceledEvent(@Observes @EntityEditCanceledEvent ArticleLot selectedEntity)
	{
		int selectedIndex = listView.getDataList().getItems().indexOf(selectedEntity);
		if (selectedIndex <= -1)
			return;
		ArticleLot entity = listView.getDataList().getItems().get(selectedIndex);
		PropertyReader.copy(selectedEntity, entity);

		ArrayList<ArticleLot> arrayList = new ArrayList<ArticleLot>(listView.getDataList().getItems());
		listView.getDataList().getItems().clear();
		listView.getDataList().getItems().addAll(arrayList);
		listView.getDataList().getSelectionModel().select(selectedEntity);
	}

	public List<String> readSearchAttributes(){
		ArrayList<String> seachAttributes = new ArrayList<String>() ;
		String internalPic = searchInput.getEntity().getInternalPic();
		String articleName = searchInput.getEntity().getArticleName();
		if(StringUtils.isNotBlank(internalPic)) seachAttributes.add("internalPic");
		if(StringUtils.isNotBlank(articleName)) seachAttributes.add("articleName");
		return seachAttributes;

	}

	public void handleArticleLotMovetToTrashDone(@Observes  @ArticlelotMovedDoneRequestEvent ArticleLot articleLot){
		int indexOf = listView.getDataList().getItems().indexOf(articleLot);
		PropertyReader.copy(articleLot, listView.getDataList().getItems().get(indexOf));
		listView.getDataList().getItems().remove(articleLot);
	}

	public void reset() {
		listView.getDataList().getItems().clear();
	}
}