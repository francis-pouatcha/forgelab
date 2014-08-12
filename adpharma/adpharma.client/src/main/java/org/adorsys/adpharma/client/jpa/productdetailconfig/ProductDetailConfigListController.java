package org.adorsys.adpharma.client.jpa.productdetailconfig;

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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.adpharma.client.jpa.article.Article;
import org.adorsys.adpharma.client.jpa.article.ArticleSearchInput;
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
import org.adorsys.javafx.crud.extensions.events.ModalEntitySearchDoneEvent;
import org.adorsys.javafx.crud.extensions.events.ModalEntitySearchRequestedEvent;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;
import org.adorsys.javafx.crud.extensions.utils.PaginationUtils;

@Singleton
public class ProductDetailConfigListController implements EntityController
{

	@Inject
	private ProductDetailConfigSearchService configSearchService;

	@Inject
	private ProductDetailConfigListView listView;

	private Boolean isOrigine = Boolean.FALSE;

	@Inject
	@ModalEntitySearchRequestedEvent
	private Event<ArticleSearchInput> articleSearchInput;

	@Inject
	@EntitySelectionEvent
	private Event<ProductDetailConfig> selectionEvent;

	@Inject
	@EntitySearchRequestedEvent
	private Event<ProductDetailConfig> searchRequestedEvent;

	@Inject
	@EntityCreateRequestedEvent
	private Event<ProductDetailConfig> createRequestedEvent;

	@Inject
	@EntityListPageIndexChangedEvent
	private Event<ProductDetailConfigSearchResult> entityListPageIndexChangedEvent;

	private ProductDetailConfigSearchResult searchResult;

	@Inject
	private ProductDetailConfigRegistration registration;

	@PostConstruct
	public void postConstruct()
	{
		listView.getCreateButton().disableProperty().bind(registration.canCreateProperty().not());

		//		listView.getDataList().getSelectionModel().selectedItemProperty()
		//		.addListener(new ChangeListener<ProductDetailConfig>()
		//				{
		//			@Override
		//			public void changed(
		//					ObservableValue<? extends ProductDetailConfig> property,
		//					ProductDetailConfig oldValue, ProductDetailConfig newValue)
		//			{
		//				if (newValue != null)
		//					selectionEvent.fire(newValue);
		//			}
		//				});

		/*
		 * listen to search button and fire search activated event.
		 */

		listView.getArticleOriginName().setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				isOrigine = Boolean.TRUE;
				articleSearchInput.fire(new ArticleSearchInput());

			}
		});

		listView.getArticleTargetName().setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				isOrigine = Boolean.FALSE ;
				articleSearchInput.fire(new ArticleSearchInput());
			}
		});
		listView.getSearchButton().setOnAction(new EventHandler<ActionEvent>()
				{
			@Override
			public void handle(ActionEvent e)
			{
				ProductDetailConfigSource source = listView.getArticleOriginName().getValue();
				ProductDetailConfigTarget target = listView.getArticleTargetName().getValue();
				ProductDetailConfigSearchInput searchInput = new ProductDetailConfigSearchInput();
				if(source!=null){
					searchInput.getEntity().setSource(source);
					searchInput.getFieldNames().add("source") ;
				}
				if(target!=null){
					searchInput.getEntity().setTarget(target);
					searchInput.getFieldNames().add("target") ;
				}
				searchInput.setMax(30);
				configSearchService.setSearchInputs(searchInput).start();


			}
				});
		configSearchService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				ProductDetailConfigSearchService s = (ProductDetailConfigSearchService) event.getSource();
				ProductDetailConfigSearchResult result = s.getValue();
				event.consume();
				s.reset();
				handleSearchResult(result);

			}
		});
		configSearchService.setOnFailed(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				ProductDetailConfigSearchService s = (ProductDetailConfigSearchService) event.getSource();
				s.reset();				
			}
		});

		listView.getCreateButton().setOnAction(new EventHandler<ActionEvent>()
				{
			@Override
			public void handle(ActionEvent e)
			{
				createRequestedEvent.fire(new ProductDetailConfig());
			}
				});

		listView.getEditButton().setOnAction(new EventHandler<ActionEvent>()
				{
			@Override
			public void handle(ActionEvent e)
			{
				ProductDetailConfig selectedItem = listView.getDataList().getSelectionModel().getSelectedItem();
				if (selectedItem != null)
					selectionEvent.fire(selectedItem);
			}
				});
		listView.getResetButton().setOnAction(new EventHandler<ActionEvent>()
				{
			@Override
			public void handle(ActionEvent e)
			{
				listView.getArticleOriginName().setValue(null);
				listView.getArticleTargetName().setValue(null);
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
					searchResult.setSearchInput(new ProductDetailConfigSearchInput());
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
	 * in the main productDetailConfig controller.
	 * 
	 * @param entities
	 */
	public void handleSearchResult(@Observes @EntitySearchDoneEvent ProductDetailConfigSearchResult searchResult)
	{
		this.searchResult = searchResult;
		List<ProductDetailConfig> entities = searchResult.getResultList();
		if (entities == null)
			entities = new ArrayList<ProductDetailConfig>();
		listView.getDataList().getItems().clear();
		listView.getDataList().getItems().addAll(entities);
		int maxResult = searchResult.getSearchInput() != null ? searchResult.getSearchInput().getMax() : 5;
		int pageCount = PaginationUtils.computePageCount(searchResult.getCount(), maxResult);
		listView.getPagination().setPageCount(pageCount);
		int firstResult = searchResult.getSearchInput() != null ? searchResult.getSearchInput().getStart() : 0;
		int pageIndex = PaginationUtils.computePageIndex(firstResult, searchResult.getCount(), maxResult);
		listView.getPagination().setCurrentPageIndex(pageIndex);

	}

	public void handleCreatedEvent(@Observes @EntityCreateDoneEvent ProductDetailConfig createdEntity)
	{
		listView.getDataList().getItems().add(0, createdEntity);
	}

	public void handleRemovedEvent(@Observes @EntityRemoveDoneEvent ProductDetailConfig removedEntity)
	{
		listView.getDataList().getItems().remove(removedEntity);
	}

	public void handleEditDoneEvent(@Observes @EntityEditDoneEvent ProductDetailConfig selectedEntity)
	{
		int selectedIndex = listView.getDataList().getItems().indexOf(selectedEntity);
		if (selectedIndex <= -1)
			return;
		ProductDetailConfig entity = listView.getDataList().getItems().get(selectedIndex);
		PropertyReader.copy(selectedEntity, entity);

		ArrayList<ProductDetailConfig> arrayList = new ArrayList<ProductDetailConfig>(listView.getDataList().getItems());
		listView.getDataList().getItems().clear();
		listView.getDataList().getItems().addAll(arrayList);
		listView.getDataList().getSelectionModel().select(selectedEntity);
	}

	public void handleEditCanceledEvent(@Observes @EntityEditCanceledEvent ProductDetailConfig selectedEntity)
	{
		int selectedIndex = listView.getDataList().getItems().indexOf(selectedEntity);
		if (selectedIndex <= -1)
			return;
		ProductDetailConfig entity = listView.getDataList().getItems().get(selectedIndex);
		PropertyReader.copy(selectedEntity, entity);

		ArrayList<ProductDetailConfig> arrayList = new ArrayList<ProductDetailConfig>(listView.getDataList().getItems());
		listView.getDataList().getItems().clear();
		listView.getDataList().getItems().addAll(arrayList);
		listView.getDataList().getSelectionModel().select(selectedEntity);
	}

	public void reset() {
		listView.getDataList().getItems().clear();
	}

	public void handleSourceSearchDone(@Observes @ModalEntitySearchDoneEvent Article article){
		if(isOrigine){
			listView.getArticleOriginName().setValue(new ProductDetailConfigSource(article));
		}else {
			listView.getArticleTargetName().setValue(new ProductDetailConfigTarget(article));
		}
	}
}
