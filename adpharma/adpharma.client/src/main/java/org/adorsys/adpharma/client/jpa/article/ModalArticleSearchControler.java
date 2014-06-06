package org.adorsys.adpharma.client.jpa.article;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.adorsys.javafx.crud.extensions.events.EntityListPageIndexChangedEvent;
import org.adorsys.javafx.crud.extensions.events.EntitySearchDoneEvent;
import org.adorsys.javafx.crud.extensions.events.ModalEntitySearchDoneEvent;
import org.adorsys.javafx.crud.extensions.events.ModalEntitySearchRequestedEvent;
import org.adorsys.javafx.crud.extensions.login.ErrorDisplay;
import org.adorsys.javafx.crud.extensions.login.ServiceCallFailedEventHandler;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;
import org.adorsys.javafx.crud.extensions.utils.PaginationUtils;
import org.apache.commons.lang3.StringUtils;
import org.controlsfx.dialog.Dialogs;

public class ModalArticleSearchControler {

	@Inject
	ModalArticleSearchView view;

	@Inject 
	private ArticleSearchService articleSearchService;

	@Inject
	private ServiceCallFailedEventHandler articleSearchServiceCallFailedEventHandler;

	@Inject 
	@ModalEntitySearchRequestedEvent
	private Event<ArticleSearchInput> modalArticleSearchEvent;

	@Inject 
	@ModalEntitySearchDoneEvent
	private Event<Article> modalArticleSearchDoneEvent;

	@Inject
	private Article article;
	
	private ArticleSearchResult searchResult;

	@PostConstruct
	public void postConstruct(){
		view.getPagination().currentPageIndexProperty().addListener(new ChangeListener<Number>()
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
				articleSearchService.setSearchInputs(searchResult.getSearchInput()).start();

			}
				});
		articleSearchServiceCallFailedEventHandler.setErrorDisplay(new ErrorDisplay() {
			
			@Override
			protected void showError(Throwable exception) {
				Dialogs.create().showException(exception);
				
			}
		});
		
		view.getCancelButton().setOnAction(new EventHandler<ActionEvent>() {

		@Override
		public void handle(ActionEvent event) {
			view.closeDialog();
		}
	});

	view.getDataList().getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Article>() {

		@Override
		public void changed(
				ObservableValue<? extends Article> observable,
				Article oldValue, Article newValue) {
			if(newValue!=null){
				view.closeDialog();
				modalArticleSearchDoneEvent.fire(newValue);

			}


		}
	});
	view.getArticleName().setOnKeyPressed(new EventHandler<KeyEvent>() {

		@Override
		public void handle(KeyEvent event) {
			KeyCode code = event.getCode();
			if(code== KeyCode.ENTER){
				String articleName = view.getArticleName().getText();
				if(StringUtils.isBlank(articleName)) return;
				Article entity = new Article();
				entity.setArticleName(articleName);
				ArticleSearchInput asi = new ArticleSearchInput();
				asi.setEntity(entity);
				asi.setMax(30);
				asi.getFieldNames().add("articleName");
				articleSearchService.setSearchInputs(asi).start();
			}
		}
	});

	articleSearchService.setOnFailed(articleSearchServiceCallFailedEventHandler);
	articleSearchService.setOnSucceeded(new EventHandler<WorkerStateEvent>(){

		@Override
		public void handle(WorkerStateEvent event) {
			ArticleSearchService s = (ArticleSearchService) event.getSource();
			ArticleSearchResult articleSearchResult = s.getValue();
			event.consume();
			s.reset();
			handleArticleSearchResult(articleSearchResult);
		}



	});
	}

	public ModalArticleSearchView getView() {
		return view;
	}

//	public void handleArticleSearchResult(
//			ArticleSearchResult articleSearchResult) {
//		
//		if(articleSearchResult.getResultList().isEmpty()) return;
//		if(articleSearchResult.getResultList().size()==1){
//			Article article2 = articleSearchResult.getResultList().iterator().next();
//			PropertyReader.copy(new Article(), article);
//			view.closeDialog();
//			modalArticleSearchDoneEvent.fire(article2);
//		}else {
//			List<Article> resultList = articleSearchResult.getResultList();
//			resultList.sort(new Comparator<Article>() {
//
//				@Override
//				public int compare(Article o1, Article o2) {
//					return o1.getArticleName().compareToIgnoreCase(o2.getArticleName());
//				}
//			});
//			view.getDataList().getItems().setAll(resultList);
//			if(!view.isDisplayed())
//				view.showDiaLog();
//		}
//	}
	
	/**
	 * Handle search results. But the switch of displays is centralized
	 * in the main article controller.
	 * 
	 * @param entities
	 */
	public void handleArticleSearchResult( ArticleSearchResult searchResult)
	{
		
		if(searchResult.getResultList().isEmpty()) return;
		if(searchResult.getResultList().size()==1){
			Article article2 = searchResult.getResultList().iterator().next();
			PropertyReader.copy(new Article(), article);
			view.closeDialog();
			modalArticleSearchDoneEvent.fire(article2);
		}else {
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
			view.getDataList().getItems().clear();
			view.getDataList().getItems().addAll(entities);
			int maxResult = searchResult.getSearchInput() != null ? searchResult.getSearchInput().getMax() : 5;
			int pageCount = PaginationUtils.computePageCount(searchResult.getCount(), maxResult);
			view.getPagination().setPageCount(pageCount);
			int firstResult = searchResult.getSearchInput() != null ? searchResult.getSearchInput().getStart() : 0;
			int pageIndex = PaginationUtils.computePageIndex(firstResult, searchResult.getCount(), maxResult);
			view.getPagination().setCurrentPageIndex(pageIndex);
			view.getDataList().getItems().setAll(entities);
			if(!view.isDisplayed())
				view.showDiaLog();
		}
	}

	public void handleArticleLotSearchRequestEvent(@Observes @ModalEntitySearchRequestedEvent ArticleSearchInput articleSearchInput){
		articleSearchService.setSearchInputs(articleSearchInput).start();
	}




}
