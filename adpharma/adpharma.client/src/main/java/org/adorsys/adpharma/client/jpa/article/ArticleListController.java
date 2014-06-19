package org.adorsys.adpharma.client.jpa.article;

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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.adpharma.client.jpa.articlelot.ArticleLotSearchInput;
import org.adorsys.adpharma.client.jpa.articlelot.ArticleLotSearchService;
import org.adorsys.adpharma.client.jpa.section.Section;
import org.adorsys.adpharma.client.jpa.section.SectionSearchInput;
import org.adorsys.adpharma.client.jpa.section.SectionSearchResult;
import org.adorsys.adpharma.client.jpa.section.SectionSearchService;
import org.adorsys.javafx.crud.extensions.EntityController;
import org.adorsys.javafx.crud.extensions.ViewType;
import org.adorsys.javafx.crud.extensions.events.EntityCreateDoneEvent;
import org.adorsys.javafx.crud.extensions.events.EntityCreateRequestedEvent;
import org.adorsys.javafx.crud.extensions.events.EntityEditCanceledEvent;
import org.adorsys.javafx.crud.extensions.events.EntityEditDoneEvent;
import org.adorsys.javafx.crud.extensions.events.EntityEditRequestedEvent;
import org.adorsys.javafx.crud.extensions.events.EntityListPageIndexChangedEvent;
import org.adorsys.javafx.crud.extensions.events.EntityRemoveDoneEvent;
import org.adorsys.javafx.crud.extensions.events.EntitySearchDoneEvent;
import org.adorsys.javafx.crud.extensions.events.EntitySearchRequestedEvent;
import org.adorsys.javafx.crud.extensions.events.EntitySelectionEvent;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;
import org.adorsys.javafx.crud.extensions.utils.PaginationUtils;
import org.apache.commons.lang3.StringUtils;

@Singleton
public class ArticleListController implements EntityController
{

	@Inject
	private ArticleListView listView;

	@Inject
	@EntityEditRequestedEvent
	private Event<Article> selectionEvent;

	@Inject
	@EntitySearchRequestedEvent
	private Event<Article> searchRequestedEvent;

	@Inject
	@EntityCreateRequestedEvent
	private Event<Article> createRequestedEvent;

	@Inject
	@EntityListPageIndexChangedEvent
	private Event<ArticleSearchResult> entityListPageIndexChangedEvent;

	@Inject
	private ArticleSearchService searchService;

	@Inject
	private SectionSearchService sectionSearchService;

	private ArticleSearchResult searchResult;

	@Inject
	private ArticleSearchInput searchInput;


	@Inject
	private ArticleRegistration registration;

	@PostConstruct
	public void postConstruct()

	{
		listView.bind(searchInput);
		listView.getCreateButton().disableProperty().bind(registration.canCreateProperty().not());
		listView.getEditButton().disableProperty().bind(registration.canCreateProperty().not());
		//
		//		listView.getDataList().getSelectionModel().selectedItemProperty()
		//		.addListener(new ChangeListener<Article>()
		//				{
		//			@Override
		//			public void changed(
		//					ObservableValue<? extends Article> property,
		//					Article oldValue, Article newValue)
		//			{
		//				if (newValue != null)
		//					selectionEvent.fire(newValue);
		//			}
		//				});

		/*
		 * listen to search button and fire search activated event.
		 */
		listView.getEditButton().setOnAction(new EventHandler<ActionEvent>()
				{
			@Override
			public void handle(ActionEvent e)
			{
				Article selectedItem = listView.getDataList().getSelectionModel().getSelectedItem();
				if(selectedItem!=null)
					selectionEvent.fire(selectedItem);
			}
				});


		sectionSearchService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				SectionSearchService s = (SectionSearchService) event.getSource();
				SectionSearchResult result = s.getValue();
				event.consume();
				s.reset();
				List<Section> resultList = result.getResultList();
				listView.getSection().getItems().clear();
				ArrayList<ArticleSection> articleSections = new ArrayList<ArticleSection>();
				for (Section section : resultList) {
					articleSections.add(new ArticleSection(section));
				}
				articleSections.add(0, null);
				listView.getSection().getItems().setAll(articleSections);

			}
		});
		sectionSearchService.setOnFailed(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				SectionSearchService s = (SectionSearchService) event.getSource();
				s.reset();				
			}
		});

		listView.getMainPic().setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				if(KeyCode.ENTER.equals(event.getCode())){
					handleSearchAction();
				}

			}
		});

		listView.getArticleName().setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				if(KeyCode.ENTER.equals(event.getCode())){
					handleSearchAction();
				}

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
				handleSearchAction();
			}
				});


		searchService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				ArticleSearchService s = (ArticleSearchService) event.getSource();
				searchResult = s.getValue();
				event.consume();
				s.reset();
				handleSearchResult(searchResult);

			}
		});
		searchService.setOnFailed(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				ArticleSearchService s = (ArticleSearchService) event.getSource();
				s.reset();				
			}
		});

		listView.getCreateButton().setOnAction(new EventHandler<ActionEvent>()
				{
			@Override
			public void handle(ActionEvent e)
			{
				Article selectedItem = listView.getDataList().getSelectionModel().getSelectedItem();
				if (selectedItem == null)
					selectedItem = new Article();
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
					searchResult.setSearchInput(new ArticleSearchInput());
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
		SectionSearchInput sectionSearchInput = new SectionSearchInput();
		sectionSearchInput.setMax(-1);
		sectionSearchService.setSearchInputs(sectionSearchInput).start();
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

	private void handleSearchAction() {
		searchInput.setFieldNames(readSearchAttributes());
		searchInput.setMax(30);
		searchService.setSearchInputs(searchInput).start();

	}

	/**
	 * Handle search results. But the switch of displays is centralized
	 * in the main article controller.
	 * 
	 * @param entities
	 */
	public void handleSearchResult(@Observes @EntitySearchDoneEvent ArticleSearchResult searchResult)
	{
		this.searchResult = searchResult;
		List<Article> entities = searchResult.getResultList();
		entities.sort(new Comparator<Article>() {

			@Override
			public int compare(Article o1, Article o2) {
				return o1.getArticleName().compareTo(o2.getArticleName());
			}

		});
		if (entities == null)
			entities = new ArrayList<Article>();
		listView.getDataList().getItems().clear();
		listView.getDataList().getItems().addAll(entities);
		int maxResult = searchResult.getSearchInput() != null ? searchResult.getSearchInput().getMax() : 5;
		int pageCount = PaginationUtils.computePageCount(searchResult.getCount(), maxResult);
		listView.getPagination().setPageCount(pageCount);
		int firstResult = searchResult.getSearchInput() != null ? searchResult.getSearchInput().getStart() : 0;
		int pageIndex = PaginationUtils.computePageIndex(firstResult, searchResult.getCount(), maxResult);
		listView.getPagination().setCurrentPageIndex(pageIndex);

	}

	public void handleCreatedEvent(@Observes @EntityCreateDoneEvent Article createdEntity)
	{
		listView.getDataList().getItems().add(0, createdEntity);
	}

	public void handleRemovedEvent(@Observes @EntityRemoveDoneEvent Article removedEntity)
	{
		listView.getDataList().getItems().remove(removedEntity);
	}

	public void handleEditDoneEvent(@Observes @EntityEditDoneEvent Article selectedEntity)
	{
		int selectedIndex = listView.getDataList().getItems().indexOf(selectedEntity);
		if (selectedIndex <= -1)
			return;
		Article entity = listView.getDataList().getItems().get(selectedIndex);
		PropertyReader.copy(selectedEntity, entity);

		ArrayList<Article> arrayList = new ArrayList<Article>(listView.getDataList().getItems());
		listView.getDataList().getItems().clear();
		listView.getDataList().getItems().addAll(arrayList);
		listView.getDataList().getSelectionModel().select(selectedEntity);
	}

	public void handleEditCanceledEvent(@Observes @EntityEditCanceledEvent Article selectedEntity)
	{
		int selectedIndex = listView.getDataList().getItems().indexOf(selectedEntity);
		if (selectedIndex <= -1)
			return;
		Article entity = listView.getDataList().getItems().get(selectedIndex);
		PropertyReader.copy(selectedEntity, entity);

		ArrayList<Article> arrayList = new ArrayList<Article>(listView.getDataList().getItems());
		listView.getDataList().getItems().clear();
		listView.getDataList().getItems().addAll(arrayList);
		listView.getDataList().getSelectionModel().select(selectedEntity);
	}

	public List<String> readSearchAttributes(){
		ArrayList<String> seachAttributes = new ArrayList<String>() ;
		String pic = searchInput.getEntity().getPic();
		String articleName = searchInput.getEntity().getArticleName();
		ArticleSection section = searchInput.getEntity().getSection();
		if(StringUtils.isNotBlank(pic)) seachAttributes.add("pic");
		if(StringUtils.isNotBlank(articleName)) seachAttributes.add("articleName");
		if(section!=null && section.getId()!=null)seachAttributes.add("section");
		return seachAttributes;

	}


	public void reset() {
		listView.getDataList().getItems().clear();
	}
}