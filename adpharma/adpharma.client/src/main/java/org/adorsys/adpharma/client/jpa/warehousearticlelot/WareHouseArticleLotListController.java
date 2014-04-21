package org.adorsys.adpharma.client.jpa.warehousearticlelot;

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

import org.adorsys.adpharma.client.jpa.articlelot.ArticleLot;
import org.adorsys.adpharma.client.jpa.articlelot.ArticleLotTransferManager;
import org.adorsys.adpharma.client.jpa.warehouse.WareHouse;
import org.adorsys.adpharma.client.jpa.warehouse.WareHouseSearchInput;
import org.adorsys.adpharma.client.jpa.warehouse.WareHouseSearchResult;
import org.adorsys.adpharma.client.jpa.warehouse.WareHouseSearchService;
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
import org.adorsys.javafx.crud.extensions.login.WorkingInformationEvent;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;
import org.adorsys.javafx.crud.extensions.utils.PaginationUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.controlsfx.dialog.Dialogs;

@Singleton
public class WareHouseArticleLotListController implements EntityController
{

	@Inject
	private WareHouseArticleLotListView listView;

	@Inject
	@EntitySelectionEvent
	private Event<WareHouseArticleLot> selectionEvent;

	@Inject
	@EntitySearchRequestedEvent
	private Event<WareHouseArticleLot> searchRequestedEvent;

	@Inject
	@EntityCreateRequestedEvent
	private Event<WareHouseArticleLot> createRequestedEvent;

	@Inject
	@EntityListPageIndexChangedEvent
	private Event<WareHouseArticleLotSearchResult> entityListPageIndexChangedEvent;

	@Inject
	@WorkingInformationEvent
	private Event<String> workingInformationEvent ;

	private WareHouseArticleLotSearchResult searchResult;

	@Inject
	private WareHouseArticleLotSearchInput searchInput;

	@Inject
	private WareHouseArticleLotSearchService searchService;

	@Inject
	private WareHouseSearchService wareHousesearchService;

	@Inject
	private WareHouseArticleLotDestockingService articleLotDestockingService;

	@Inject
	private WareHouseArticleLotRegistration registration;

	@Inject
	private ServiceCallFailedEventHandler callFailedEventHandler ;
	
	@Inject
	private WareHouseArticleLot selectedItem ;

	@PostConstruct
	public void postConstruct()
	{
		listView.getTransferToSaleButton().disableProperty().bind(registration.canCreateProperty().not());
		listView.getSearchButton().disableProperty().bind(searchService.runningProperty());
		listView.getTransferToSaleButton().disableProperty().bind(articleLotDestockingService.runningProperty());
		listView.bind(searchInput);

		//      listView.getDataList().getSelectionModel().selectedItemProperty()
		//            .addListener(new ChangeListener<WareHouseArticleLot>()
		//            {
		//               @Override
		//               public void changed(
		//                     ObservableValue<? extends WareHouseArticleLot> property,
		//                     WareHouseArticleLot oldValue, WareHouseArticleLot newValue)
		//               {
		//                  if (newValue != null)
		//                     selectionEvent.fire(newValue);
		//               }
		//            });

		callFailedEventHandler.setErrorDisplay(new ErrorDisplay() {

			@Override
			protected void showError(Throwable exception) {
				Dialogs.create().nativeTitleBar().showException(exception);

			}
		});
		
		listView.getDataList().getSelectionModel().selectedItemProperty().addListener(new ChangeListener<WareHouseArticleLot>() {

			@Override
			public void changed(
					ObservableValue<? extends WareHouseArticleLot> observable,
					WareHouseArticleLot oldValue, WareHouseArticleLot newValue) {
				if(newValue!=null)
					PropertyReader.copy(newValue, selectedItem);
				
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
				WareHouseArticleLotSearchService s = (WareHouseArticleLotSearchService) event.getSource();
				searchResult = s.getValue();
				event.consume();
				s.reset();
				handleSearchResult(searchResult);

			}
		});
		searchService.setOnFailed(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				WareHouseArticleLotSearchService s = (WareHouseArticleLotSearchService) event.getSource();
				s.reset();				
			}
		});

		listView.getWareHouse().armedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable,
					Boolean oldValue, Boolean newValue) {
				if(newValue)
					wareHousesearchService.setSearchInputs(new WareHouseSearchInput()).start();

			}
		});

		wareHousesearchService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				WareHouseSearchService s = (WareHouseSearchService) event.getSource();
				WareHouseSearchResult result = s.getValue();
				event.consume();
				s.reset();
				ArrayList<WareHouseArticleLotWareHouse> arrayList = new ArrayList<WareHouseArticleLotWareHouse>();
				List<WareHouse> resultList = result.getResultList();
				for (WareHouse wareHouse : resultList) {
					arrayList.add(new WareHouseArticleLotWareHouse(wareHouse));
				}
				arrayList.add(new WareHouseArticleLotWareHouse());
				listView.getWareHouse().getItems().setAll(arrayList);


			}
		});
		wareHousesearchService.setOnFailed(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				WareHouseSearchService s = (WareHouseSearchService) event.getSource();
				s.reset();				
			}
		});



		listView.getTransferToSaleButton().setOnAction(new EventHandler<ActionEvent>()
				{
			@Override
			public void handle(ActionEvent e)
			{
//				WareHouseArticleLot selectedItem = listView.getDataList().getSelectionModel().getSelectedItem();
				if (selectedItem != null){
					String showTextInput = Dialogs.create().nativeTitleBar().message("Qty to Transfer :").showTextInput("1");
					BigDecimal transferdQty = BigDecimal.valueOf(NumberUtils.toDouble(showTextInput));
					BigDecimal stockQuantity = selectedItem.getStockQuantity();
					if(stockQuantity.compareTo(transferdQty)< 0) {
						Dialogs.create().message("You Could not transfer More than "+stockQuantity).showInformation();
					}else {
						ArticleLotTransferManager alm = new ArticleLotTransferManager();
						ArticleLot articleLot = new ArticleLot();
						WareHouse wareHouse = new WareHouse();
						PropertyReader.copy(selectedItem.getArticleLot(), articleLot);
						PropertyReader.copy(selectedItem.getWareHouse(), wareHouse);
						alm.setQtyToTransfer(transferdQty);
						alm.setLotToTransfer(articleLot);
						alm.setWareHouse(wareHouse);
						articleLotDestockingService.setModel(alm ).start();

					}
				}
			}
				});

		articleLotDestockingService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				WareHouseArticleLotDestockingService s = (WareHouseArticleLotDestockingService) event.getSource();
				WareHouseArticleLot result = s.getValue();
				event.consume();
				s.reset();
				if(selectedItem!=null&&selectedItem.getId()!=null){
					PropertyReader.copy(result, listView.getDataList().getSelectionModel().getSelectedItem());
				}
				workingInformationEvent.fire("destocking done successfuly !");

			}
		});
		articleLotDestockingService.setOnFailed(callFailedEventHandler) ;
		listView.getPagination().currentPageIndexProperty().addListener(new ChangeListener<Number>()
				{
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue)
			{
				if (searchResult == null)
					return;
				if (searchResult.getSearchInput() == null)
					searchResult.setSearchInput(new WareHouseArticleLotSearchInput());
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
	 * in the main WareHouse controller.
	 * 
	 * @param entities
	 */
	public void handleSearchResult(@Observes @EntitySearchDoneEvent WareHouseArticleLotSearchResult searchResult)
	{
		this.searchResult = searchResult;
		List<WareHouseArticleLot> entities = searchResult.getResultList();
		if (entities == null)
			entities = new ArrayList<WareHouseArticleLot>();
		listView.getDataList().getItems().clear();
		listView.getDataList().getItems().addAll(entities);
		int maxResult = searchResult.getSearchInput() != null ? searchResult.getSearchInput().getMax() : 5;
		int pageCount = PaginationUtils.computePageCount(searchResult.getCount(), maxResult);
		listView.getPagination().setPageCount(pageCount);
		int firstResult = searchResult.getSearchInput() != null ? searchResult.getSearchInput().getStart() : 0;
		int pageIndex = PaginationUtils.computePageIndex(firstResult, searchResult.getCount(), maxResult);
		listView.getPagination().setCurrentPageIndex(pageIndex);

	}

	public void handleCreatedEvent(@Observes @EntityCreateDoneEvent WareHouseArticleLot createdEntity)
	{
		listView.getDataList().getItems().add(0, createdEntity);
	}

	public void handleRemovedEvent(@Observes @EntityRemoveDoneEvent WareHouseArticleLot removedEntity)
	{
		listView.getDataList().getItems().remove(removedEntity);
	}

	public void handleEditDoneEvent(@Observes @EntityEditDoneEvent WareHouseArticleLot selectedEntity)
	{
		int selectedIndex = listView.getDataList().getItems().indexOf(selectedEntity);
		if (selectedIndex <= -1)
			return;
		WareHouseArticleLot entity = listView.getDataList().getItems().get(selectedIndex);
		PropertyReader.copy(selectedEntity, entity);

		ArrayList<WareHouseArticleLot> arrayList = new ArrayList<WareHouseArticleLot>(listView.getDataList().getItems());
		listView.getDataList().getItems().clear();
		listView.getDataList().getItems().addAll(arrayList);
		listView.getDataList().getSelectionModel().select(selectedEntity);
	}

	public void handleEditCanceledEvent(@Observes @EntityEditCanceledEvent WareHouseArticleLot selectedEntity)
	{
		int selectedIndex = listView.getDataList().getItems().indexOf(selectedEntity);
		if (selectedIndex <= -1)
			return;
		WareHouseArticleLot entity = listView.getDataList().getItems().get(selectedIndex);
		PropertyReader.copy(selectedEntity, entity);

		ArrayList<WareHouseArticleLot> arrayList = new ArrayList<WareHouseArticleLot>(listView.getDataList().getItems());
		listView.getDataList().getItems().clear();
		listView.getDataList().getItems().addAll(arrayList);
		listView.getDataList().getSelectionModel().select(selectedEntity);
	}

	public List<String> readSearchAttributes(){
		ArrayList<String> seachAttributes = new ArrayList<String>() ;
		String internalPic = searchInput.getEntity().getInternalCip();
		String articleName = searchInput.getEntity().getArticleName();
		WareHouseArticleLotWareHouse wareHouse = searchInput.getEntity().getWareHouse();
		if(StringUtils.isNotBlank(internalPic)) seachAttributes.add("internalCip");
		if(StringUtils.isNotBlank(articleName)) seachAttributes.add("articleName");
		if(wareHouse!=null && wareHouse.getId()!=null) seachAttributes.add("wareHouse");
		return seachAttributes;

	}

}