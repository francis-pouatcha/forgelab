package org.adorsys.adpharma.client.jpa.stockmovement;

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

import org.adorsys.adpharma.client.events.PeriodicalMovementSearchRequestEvent;
import org.adorsys.adpharma.client.jpa.salesorder.PeriodicalDataSearchInput;
import org.adorsys.adpharma.client.jpa.stockmovementterminal.StockMovementTerminal;
import org.adorsys.adpharma.client.jpa.stockmovementtype.StockMovementType;
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
public class StockMovementListController implements EntityController
{

	@Inject
	private StockMovementListView listView;

	@Inject
	@EntitySelectionEvent
	private Event<StockMovement> selectionEvent;

	@Inject
	@EntitySearchRequestedEvent
	private Event<StockMovement> searchRequestedEvent;
	
	@Inject
	@PeriodicalMovementSearchRequestEvent
	private Event<PeriodicalDataSearchInput> periodicalMovevementRequestEvent;

	@Inject
	@EntityCreateRequestedEvent
	private Event<StockMovement> createRequestedEvent;

	@Inject
	@EntityListPageIndexChangedEvent
	private Event<StockMovementSearchResult> entityListPageIndexChangedEvent;

	private StockMovementSearchResult searchResult;

	@Inject
	private StockMovementRegistration registration;

	@Inject
	private StockMovementSearchInput searchInput;

	@Inject
	private StockMovementSearchService searchService;

	@PostConstruct
	public void postConstruct()
	{

		//      listView.getCreateButton().disableProperty().bind(registration.canCreateProperty().not());

		listView.bind(searchInput);
		//		listView.getDataList().getSelectionModel().selectedItemProperty()
		//		.addListener(new ChangeListener<StockMovement>()
		//				{
		//			@Override
		//			public void changed(
		//					ObservableValue<? extends StockMovement> property,
		//					StockMovement oldValue, StockMovement newValue)
		//			{
		//				if (newValue != null)
		//					selectionEvent.fire(newValue);
		//			}
		//				});

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
		listView.getAdvenceSearchButton().setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				periodicalMovevementRequestEvent.fire(new PeriodicalDataSearchInput());
			}
		});
		searchService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				StockMovementSearchService s = (StockMovementSearchService) event.getSource();
				searchResult = s.getValue();
				event.consume();
				s.reset();
				handleSearchResult(searchResult);

			}
		});
		searchService.setOnFailed(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				StockMovementSearchService s = (StockMovementSearchService) event.getSource();
				s.reset();				
			}
		});

		//      listView.getCreateButton().setOnAction(new EventHandler<ActionEvent>()
		//      {
		//         @Override
		//         public void handle(ActionEvent e)
		//         {
		//            StockMovement selectedItem = listView.getDataList().getSelectionModel().getSelectedItem();
		//            if (selectedItem == null)
		//               selectedItem = new StockMovement();
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
					searchResult.setSearchInput(new StockMovementSearchInput());
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
	 * in the main stockMovement controller.
	 * 
	 * @param entities
	 */
	public void handleSearchResult(@Observes @EntitySearchDoneEvent StockMovementSearchResult searchResult)
	{
		this.searchResult = searchResult;
		List<StockMovement> entities = searchResult.getResultList();
		if (entities == null)
			entities = new ArrayList<StockMovement>();
		listView.getDataList().getItems().clear();
		listView.getDataList().getItems().addAll(entities);
		int maxResult = searchResult.getSearchInput() != null ? searchResult.getSearchInput().getMax() : 5;
		int pageCount = PaginationUtils.computePageCount(searchResult.getCount(), maxResult);
		listView.getPagination().setPageCount(pageCount);
		int firstResult = searchResult.getSearchInput() != null ? searchResult.getSearchInput().getStart() : 0;
		int pageIndex = PaginationUtils.computePageIndex(firstResult, searchResult.getCount(), maxResult);
		listView.getPagination().setCurrentPageIndex(pageIndex);

	}

	public void handleCreatedEvent(@Observes @EntityCreateDoneEvent StockMovement createdEntity)
	{
		listView.getDataList().getItems().add(0, createdEntity);
	}

	public void handleRemovedEvent(@Observes @EntityRemoveDoneEvent StockMovement removedEntity)
	{
		listView.getDataList().getItems().remove(removedEntity);
	}

	public void handleEditDoneEvent(@Observes @EntityEditDoneEvent StockMovement selectedEntity)
	{
		int selectedIndex = listView.getDataList().getItems().indexOf(selectedEntity);
		if (selectedIndex <= -1)
			return;
		StockMovement entity = listView.getDataList().getItems().get(selectedIndex);
		PropertyReader.copy(selectedEntity, entity);

		ArrayList<StockMovement> arrayList = new ArrayList<StockMovement>(listView.getDataList().getItems());
		listView.getDataList().getItems().clear();
		listView.getDataList().getItems().addAll(arrayList);
		listView.getDataList().getSelectionModel().select(selectedEntity);
	}



	public void handleEditCanceledEvent(@Observes @EntityEditCanceledEvent StockMovement selectedEntity)
	{
		int selectedIndex = listView.getDataList().getItems().indexOf(selectedEntity);
		if (selectedIndex <= -1)
			return;
		StockMovement entity = listView.getDataList().getItems().get(selectedIndex);
		PropertyReader.copy(selectedEntity, entity);

		ArrayList<StockMovement> arrayList = new ArrayList<StockMovement>(listView.getDataList().getItems());
		listView.getDataList().getItems().clear();
		listView.getDataList().getItems().addAll(arrayList);
		listView.getDataList().getSelectionModel().select(selectedEntity);
	}



	public List<String> readSearchAttributes(){
		ArrayList<String> seachAttributes = new ArrayList<String>() ;
				StockMovementTerminal movementOrigin = searchInput.getEntity().getMovementOrigin();
				StockMovementTerminal movementDestination = searchInput.getEntity().getMovementDestination();
				StockMovementType movementType = searchInput.getEntity().getMovementType();
				if(movementOrigin!=null)seachAttributes.add("movementOrigin");
				if(movementDestination!=null)seachAttributes.add("movementDestination");
				if(movementType!=null)seachAttributes.add("movementType");
				String internalPic = searchInput.getEntity().getInternalPic();
				if(StringUtils.isNotBlank(internalPic))seachAttributes.add("internalPic");
				String raison = searchInput.getEntity().getRaison();
				if(StringUtils.isNotBlank(raison))seachAttributes.add("raison");
				
		return seachAttributes;

	}

	public void reset() {
		   listView.getDataList().getItems().clear();
		}
}
