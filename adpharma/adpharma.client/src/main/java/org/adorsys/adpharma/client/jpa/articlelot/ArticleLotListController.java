package org.adorsys.adpharma.client.jpa.articlelot;

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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.adpharma.client.jpa.delivery.Delivery;
import org.adorsys.adpharma.client.jpa.delivery.DeliverySupplier;
import org.adorsys.adpharma.client.jpa.documentprocessingstate.DocumentProcessingState;
import org.adorsys.adpharma.client.jpa.supplier.Supplier;
import org.adorsys.adpharma.client.jpa.supplier.SupplierSearchResult;
import org.adorsys.adpharma.client.jpa.supplier.SupplierSearchService;
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
	@EntitySearchRequestedEvent
	private Event<ArticleLot> searchRequestedEvent;

	@Inject
	@EntityCreateRequestedEvent
	private Event<ArticleLot> createRequestedEvent;

	@Inject
	@EntityListPageIndexChangedEvent
	private Event<ArticleLotSearchResult> entityListPageIndexChangedEvent;

	private ArticleLotSearchResult searchResult;

	@Inject
	private ArticleLotSearchInput searchInput;

	@Inject
	private ArticleLotSearchService searchService;

	@Inject
	private ArticleLotRegistration registration;

	@PostConstruct
	public void postConstruct()
	{
		//		listView.getCreateButton().disableProperty().bind(registration.canCreateProperty().not());
		listView.getDetailsButton().disableProperty().bind(registration.canCreateProperty().not());
		listView.getMoveButton().disableProperty().bind(registration.canEditProperty().not());
		listView.bind(searchInput);

		//		listView.getDataList().getSelectionModel().selectedItemProperty()
		//		.addListener(new ChangeListener<ArticleLot>()
		//				{
		//			@Override
		//			public void changed(
		//					ObservableValue<? extends ArticleLot> property,
		//					ArticleLot oldValue, ArticleLot newValue)
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
		searchService.setOnFailed(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				ArticleLotSearchService s = (ArticleLotSearchService) event.getSource();
				s.reset();				
			}
		});

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
				Dialogs.create().message("not yet implemented").showInformation();
			}
				});
		listView.getMoveButton().setOnAction(new EventHandler<ActionEvent>()
				{
			@Override
			public void handle(ActionEvent e)
			{
				Dialogs.create().message("not yet implemented").showInformation();
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

}