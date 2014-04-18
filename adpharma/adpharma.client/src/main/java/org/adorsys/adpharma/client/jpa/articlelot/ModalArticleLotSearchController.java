package org.adorsys.adpharma.client.jpa.articlelot;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.adorsys.javafx.crud.extensions.events.ModalEntitySearchDoneEvent;
import org.adorsys.javafx.crud.extensions.events.ModalEntitySearchRequestedEvent;
import org.adorsys.javafx.crud.extensions.login.ServiceCallFailedEventHandler;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;
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
					asi.getFieldNames().add("articleName");
					modalArticleLotSearchEvent.fire(asi);
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
			List<ArticleLot> resultList = articleLotSearchResult.getResultList();
			List<String> resultOrder = new ArrayList<String>();
			Map<String, Set<ArticleLot>> lotMap = new HashMap<String, Set<ArticleLot>>();
			// Group by secondary pic always taking the article
			for (ArticleLot articleLot : resultList) {
				String mainPic = articleLot.getMainPic();
				if(!resultOrder.contains(mainPic)) resultOrder.add(mainPic);
				// the main pic is unique per product.
				Set<ArticleLot> set = lotMap.get(mainPic);
				if(set==null){
					set = new TreeSet<ArticleLot>();
					lotMap.put(mainPic, set);
				}
				set.add(articleLot);
			}
			ArrayList<ArticleLot> lotList = new ArrayList<ArticleLot>(resultOrder.size());
			for (String mainPic : resultOrder) {
				Set<ArticleLot> set = lotMap.get(mainPic);
				ArticleLot articleLot = null;
				for (ArticleLot lot : set) {
					if(articleLot==null) {
						articleLot=lot;
					} else if(BigDecimal.ZERO.compareTo(articleLot.getStockQuantity())>=0){
						articleLot=lot;
					} else {
						break;
					}
				}
				if(articleLot!=null)lotList.add(articleLot);
			}
			
			view.getDataList().getItems().setAll(lotList);
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
