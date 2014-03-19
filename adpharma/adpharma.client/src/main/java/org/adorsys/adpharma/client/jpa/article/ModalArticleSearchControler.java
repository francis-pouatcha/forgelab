package org.adorsys.adpharma.client.jpa.article;

import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adorsys.javafx.crud.extensions.login.ServiceCallFailedEventHandler;
import org.apache.commons.lang3.StringUtils;

public class ModalArticleSearchControler {

	@Inject
	ModalArticleSearchView view;

	@Inject 
	private ArticleSearchService articleSearchService;

	@Inject
	private ServiceCallFailedEventHandler articleSearchServiceCallFailedEventHandler;


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
				ArticleSearchService s = (ArticleSearchService) event.getSource();
				ArticleSearchResult articleSearchResult = s.getValue();
				event.consume();
				s.reset();
				handleArticleSearchResult(articleSearchResult);
			}



		});
	}

	public void handleArticleSearchResult(
			ArticleSearchResult articleSearchResult) {
		//		if(articleSearchResult.getCount() <= 0) return ;
		view.getDataList().getItems().setAll(articleSearchResult.getResultList());
		view.showDiaLog();
	}

	public void handleArticleSearchInput(
			String articleName) {
		if(StringUtils.isBlank(articleName)) return;
		Article entity = new Article();
		entity.setArticleName(articleName);
		ArticleSearchInput asi = new ArticleSearchInput();
		asi.setEntity(entity);
		asi.getFieldNames().add("articleName");
		articleSearchService.setSearchInputs(asi).start();
	}

	public ModalArticleSearchView getView() {
		return view;
	}


}
