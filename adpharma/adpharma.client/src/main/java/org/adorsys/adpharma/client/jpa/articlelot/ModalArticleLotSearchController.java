package org.adorsys.adpharma.client.jpa.articlelot;

import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adorsys.adpharma.client.jpa.article.Article;
import org.adorsys.adpharma.client.jpa.article.ArticleSearchInput;
import org.adorsys.adpharma.client.jpa.article.ArticleSearchResult;
import org.adorsys.adpharma.client.jpa.article.ArticleSearchService;
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
	ArticleLot articleLot;


	@PostConstruct
	public void postConstruct(){
		view.getCancelButton().setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				view.closeDialog();
			}
		});
		view.getArticleName().setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				KeyCode code = event.getCode();
				if(code== KeyCode.ENTER){
					String articleName = view.getArticleName().getText();
					handleArticleSearchInput(articleName);
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
		if(articleLotSearchResult.getResultList().size()==1){
			ArticleLot articleLot2 = articleLotSearchResult.getResultList().iterator().next();
			PropertyReader.copy(articleLot2, articleLot);
		}else {
			view.getDataList().getItems().setAll(articleLotSearchResult.getResultList());
			view.showDiaLog();
		}
	}

	public void handleArticleSearchInput(
			String articleName) {
		if(StringUtils.isBlank(articleName)) return;
		ArticleLot entity = new ArticleLot();
		entity.setArticleName(articleName);
		ArticleLotSearchInput asi = new ArticleLotSearchInput();
		asi.setEntity(entity);
		asi.getFieldNames().add("articleName");
		articleSearchService.setSearchInputs(asi).start();
	}

	public void handleArticleSearchBycip(
			String cip) {
		if(StringUtils.isBlank(cip)) return;
		ArticleLot entity = new ArticleLot();
		entity.setArticleName(cip);
		ArticleLotSearchInput asi = new ArticleLotSearchInput();
		asi.setEntity(entity);
		asi.getFieldNames().add("internalPic");
		articleSearchService.setSearchInputs(asi).start();
	}

	public ModalArticleLotSearchView getView() {
		return view;
	}



}
