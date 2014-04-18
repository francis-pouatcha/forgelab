package org.adorsys.adpharma.client.jpa.articlelot;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.adpharma.client.jpa.salesorder.SalesOrder;
import org.adorsys.adpharma.client.jpa.salesorder.SalesOrderSearchResult;
import org.adorsys.javafx.crud.extensions.events.EntityListPageIndexChangedEvent;
import org.adorsys.javafx.crud.extensions.events.EntitySearchDoneEvent;
import org.adorsys.javafx.crud.extensions.events.ModalEntitySearchDoneEvent;
import org.adorsys.javafx.crud.extensions.events.ModalEntitySearchRequestedEvent;
import org.adorsys.javafx.crud.extensions.login.ServiceCallFailedEventHandler;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;
import org.adorsys.javafx.crud.extensions.utils.PaginationUtils;
import org.apache.commons.lang3.StringUtils;

public class ModalArticleLotSearchController  {

	@Inject
	ModalArticleLotSearchView view;

	@Inject 
	private ArticleLotSearchService articleSearchService;

	@Inject
	private ServiceCallFailedEventHandler articleSearchServiceCallFailedEventHandler;

	@Inject 
	@ModalEntitySearchRequestedEvent
	private Event<ArticleLotSearchInput> modalArticleLotSearchEvent;

	@Inject 
	@ModalEntitySearchDoneEvent
	private Event<ArticleLot> modalArticleLotSearchDoneEvent;

	@Inject
	ArticleLot articleLot;
	
	@Inject
	@EntityListPageIndexChangedEvent
	private Event<ArticleLotSearchResult> entityListPageIndexChangedEvent;

	private ArticleLotSearchResult searchResult;


	@PostConstruct
	public void postConstruct(){
		view.getCancelButton().setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				view.closeDialog();
			}
		});

		view.getDataList().getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ArticleLot>() {

			@Override
			public void changed(
					ObservableValue<? extends ArticleLot> observable,
					ArticleLot oldValue, ArticleLot newValue) {
				if(newValue!=null){
					modalArticleLotSearchDoneEvent.fire(newValue);
					view.closeDialog();

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
					ArticleLot entity = new ArticleLot();
					entity.setArticleName(articleName);
					ArticleLotSearchInput asi = new ArticleLotSearchInput();
					asi.setEntity(entity);
					asi.setMax(-1);
					asi.getFieldNames().add("articleName");
					articleSearchService.setSearchInputs(asi).start();
				}
			}
		});

		articleSearchService.setOnFailed(articleSearchServiceCallFailedEventHandler);
		articleSearchService.setOnSucceeded(new EventHandler<WorkerStateEvent>(){

			@Override
			public void handle(WorkerStateEvent event) {
				ArticleLotSearchService s = (ArticleLotSearchService) event.getSource();
				ArticleLotSearchResult articleSearchResult = s.getValue();
				event.consume();
				s.reset();
				handleArticleSearchResult(articleSearchResult);
			}



		});

		view.getPagination().currentPageIndexProperty().addListener(new ChangeListener<Number>()
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
		view.getDataList().getItems().clear();
		view.getDataList().getItems().addAll(entities);
		int maxResult = searchResult.getSearchInput() != null ? searchResult.getSearchInput().getMax() : 5;
		int pageCount = PaginationUtils.computePageCount(searchResult.getCount(), maxResult);
		view.getPagination().setPageCount(pageCount);
		int firstResult = searchResult.getSearchInput() != null ? searchResult.getSearchInput().getStart() : 0;
		int pageIndex = PaginationUtils.computePageIndex(firstResult, searchResult.getCount(), maxResult);
		view.getPagination().setCurrentPageIndex(pageIndex);

	}

	public void handleArticleSearchResult(
			ArticleLotSearchResult articleLotSearchResult) {
		if(articleLotSearchResult.getResultList().isEmpty()) return;
		if(articleLotSearchResult.getResultList().size()==1){
			ArticleLot articleLot2 = articleLotSearchResult.getResultList().iterator().next();
			PropertyReader.copy(new ArticleLot(), articleLot);
			view.closeDialog();
			modalArticleLotSearchDoneEvent.fire(articleLot2);
		}else {
			//			view.getDataList().getItems().setAll(articleLotSearchResult.getResultList());
			handleSearchResult(articleLotSearchResult);
			view.showDiaLog();
		}
	}


	public ModalArticleLotSearchView getView() {
		return view;
	}

	public void handleArticleLotSearchRequestEvent(@Observes @ModalEntitySearchRequestedEvent ArticleLotSearchInput lotSearchInput){
		articleSearchService.setSearchInputs(lotSearchInput).start();
	}


}
