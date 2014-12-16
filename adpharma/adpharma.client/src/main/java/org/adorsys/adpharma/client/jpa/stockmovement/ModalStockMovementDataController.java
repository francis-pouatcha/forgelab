package org.adorsys.adpharma.client.jpa.stockmovement;

import java.util.Set;

import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.validation.ConstraintViolation;

import org.adorsys.adpharma.client.access.SecurityUtil;
import org.adorsys.adpharma.client.events.PeriodicalMovementSearchRequestEvent;
import org.adorsys.adpharma.client.jpa.article.Article;
import org.adorsys.adpharma.client.jpa.article.ArticleSearchInput;
import org.adorsys.adpharma.client.jpa.salesorder.PeriodicalDataSearchInput;
import org.adorsys.javafx.crud.extensions.events.EntitySearchDoneEvent;
import org.adorsys.javafx.crud.extensions.events.ModalEntitySearchDoneEvent;
import org.adorsys.javafx.crud.extensions.events.ModalEntitySearchRequestedEvent;
import org.adorsys.javafx.crud.extensions.login.ErrorDisplay;
import org.adorsys.javafx.crud.extensions.login.ServiceCallFailedEventHandler;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;
import org.controlsfx.dialog.Dialogs;

@Singleton
public class ModalStockMovementDataController {

	@Inject
	private ModalStockMovementRepportDataView view ;

	@Inject
	private PeriodicalDataSearchInput model ;
	
	@Inject
	@ModalEntitySearchRequestedEvent
	private Event<ArticleSearchInput> articleSearchInput;

	@Inject
	@EntitySearchDoneEvent
	private Event<StockMovementSearchResult> periodicalMovementSearchDoneRequestEvent;

	@Inject
	private PeriodicalMovementService periodicalMovementService ;

	@Inject
	private ServiceCallFailedEventHandler callFailedEventHandler ;
	
	

	@PostConstruct
	public void postconstruct(){
		view.bind(model);
		view.getSaveButton().disableProperty().bind(periodicalMovementService.runningProperty());
		view.getResetButton().disableProperty().bind(periodicalMovementService.runningProperty());
		callFailedEventHandler.setErrorDisplay(new ErrorDisplay() {

			@Override
			protected void showError(Throwable exception) {
				Dialogs.create().showException(exception);

			}
		});

		periodicalMovementService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				PeriodicalMovementService source = (PeriodicalMovementService) event.getSource();
				StockMovementSearchResult result = source.getValue();
				event.consume();
				source.reset();
				result.getSearchInput().setMax(result.getResultList().size());
				result.setCount(Long.valueOf(result.getResultList().size()));
				
				periodicalMovementSearchDoneRequestEvent.fire(result);

			}
		});
		periodicalMovementService.setOnFailed(callFailedEventHandler);
		view.getResetButton().setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				view.closeDialog();

			}
		});
		
		view.getArticle().setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				articleSearchInput.fire(new ArticleSearchInput());
			}
		});
		
		view.getClearButton().setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				view.getArticle().setValue(null);
			}
		});

		view.getSaveButton().setOnAction(new EventHandler<ActionEvent>() {

			@Override 
			public void handle(ActionEvent event) {
				Set<ConstraintViolation<PeriodicalDataSearchInput>> validate = view.validate(model);
				if(validate.isEmpty()){
					periodicalMovementService.setData(model).start();
				}
			}
		});
		view.addValidators();
	}
	
	// Handle the rssult of the search of article
		public void handleArticleSearchDone(@Observes @ModalEntitySearchDoneEvent Article article){
				view.getArticle().setValue(article);
		}
	
	public void handleSearchDataRequestEvent(@Observes @PeriodicalMovementSearchRequestEvent PeriodicalDataSearchInput data){
		PropertyReader.copy(data, model);
		view.showDiaLog();
	}

}
