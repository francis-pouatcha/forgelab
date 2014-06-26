package org.adorsys.adpharma.client.jpa.articlelot;

import java.math.BigDecimal;
import java.util.Set;

import javafx.concurrent.Worker;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.validation.ConstraintViolation;

import org.adorsys.javafx.crud.extensions.events.EntityEditDoneEvent;
import org.adorsys.javafx.crud.extensions.login.ErrorDisplay;
import org.adorsys.javafx.crud.extensions.login.ServiceCallFailedEventHandler;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;
import org.controlsfx.dialog.Dialogs;

@Singleton
public class ModalArticleLotMovedToTrashController {

	@Inject
	private ModalArticleLotMovedToTrashView view ;

	@Inject
	private ArticleLotMovedToTrashData data ;

	@Inject
	private ArticlelotMoveToTrashService moveToTrashService ;
	
	@Inject
	private ServiceCallFailedEventHandler callFailedEventHandler ;
	
	@Inject
	@EntityEditDoneEvent
	private Event<ArticleLot> articleLotMovedDoneRequestEvent ;
	

	@PostConstruct
	public void  postonstruct(){
		view.bind(data);

		callFailedEventHandler.setErrorDisplay(new ErrorDisplay() {
			
			@Override
			protected void showError(Throwable exception) {
				Dialogs.create().showException(exception);
			}
		});
		view.getResetButton().setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				view.closeDialog();

			}
		});

		view.getSaveButton().setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Set<ConstraintViolation<ArticleLotMovedToTrashData>> validate = view.validate(data);
				if(validate.isEmpty()){
					BigDecimal qtyToMoved = data.getQtyToMoved();
					if(qtyToMoved.compareTo(BigDecimal.ONE)<0 || 
							data.getStockQuantity().compareTo(data.getQtyToMoved())<0){
						Dialogs.create().message("la qte a deplacer dois etre comprise entre  1 et : "+ data.getStockQuantity()).showError();
					 return;
					}
					moveToTrashService.setData(data).start();
				}
				view.closeDialog();

			}
		});
		
		moveToTrashService.setOnFailed(callFailedEventHandler);
		moveToTrashService.setOnSucceeded(new  EventHandler<WorkerStateEvent>() {
			
			@Override
			public void handle(WorkerStateEvent event) {
				ArticlelotMoveToTrashService source = (ArticlelotMoveToTrashService) event.getSource();
				ArticleLot articleLot = source.getValue();
				event.consume();
				source.reset();
				articleLotMovedDoneRequestEvent.fire(articleLot);
				
			}
		});
		view.addValidators();
	}

	public void handleAticleTarshRequestEvent(@Observes ArticleLotMovedToTrashData data){
		PropertyReader.copy(data, this.data);
		view.showDiaLog();
	}

}
