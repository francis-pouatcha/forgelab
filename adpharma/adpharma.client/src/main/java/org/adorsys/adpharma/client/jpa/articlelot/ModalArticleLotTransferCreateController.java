package org.adorsys.adpharma.client.jpa.articlelot;

import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;

import org.adorsys.adpharma.client.jpa.productdetailconfig.ProductDetailConfig;
import org.adorsys.adpharma.client.jpa.productdetailconfig.ProductDetailConfigSearchResult;
import org.adorsys.adpharma.client.jpa.productdetailconfig.ProductDetailConfigSearchService;
import org.adorsys.adpharma.client.jpa.productdetailconfig.ProductDetailConfigSource;
import org.adorsys.adpharma.client.jpa.warehouse.WareHouse;
import org.adorsys.adpharma.client.jpa.warehouse.WareHouseSearchInput;
import org.adorsys.adpharma.client.jpa.warehouse.WareHouseSearchResult;
import org.adorsys.adpharma.client.jpa.warehouse.WareHouseSearchService;
import org.adorsys.adpharma.client.jpa.warehousearticlelot.WareHouseArticleLot;
import org.adorsys.javafx.crud.extensions.events.EntityCreateDoneEvent;
import org.adorsys.javafx.crud.extensions.events.ModalEntityCreateRequestedEvent;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.login.ErrorDisplay;
import org.adorsys.javafx.crud.extensions.login.ServiceCallFailedEventHandler;
import org.adorsys.javafx.crud.extensions.login.WorkingInformationEvent;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;
import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.Dialogs;

public class ModalArticleLotTransferCreateController {

	@Inject
	private ModalArticleLotTransferCreateView lotTransferCreateView;

	@Inject
	ArticleLotTransferManager model ;

	@Inject
	private WareHouseSearchService wareHouseSearchService;

	@Inject
	private ServiceCallFailedEventHandler callFailedEventHandler;

	@Inject
	private WareHouseSearchInput searchInput;

	@Inject
	private ArticleLotDetailsService articleLotDetailsService;

	@Inject
	@EntityCreateDoneEvent
	private Event<WareHouseArticleLot> wareHouseArticleLotCreateDoneEvent;

	@Inject
	@WorkingInformationEvent
	private Event<String> workingEvent;

	@Inject
	@Bundle({ CrudKeys.class, ArticleLot.class })
	private ResourceBundle resourceBundle;

	@PostConstruct
	public void postConstruct(){
		lotTransferCreateView.bind(model);
		searchInput.setMax(-1);

		callFailedEventHandler.setErrorDisplay(new ErrorDisplay() {

			@Override
			protected void showError(Throwable exception) {
				Dialogs.create().nativeTitleBar().showException(exception);

			}
		});

		lotTransferCreateView.getCancelButton().setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				lotTransferCreateView.closeDialog();

			}
		});

		lotTransferCreateView.getSaveButton().setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Set<ConstraintViolation<ArticleLotTransferManager>> validate = lotTransferCreateView.validate(model);
				if(validate.isEmpty()){
					Action showConfirm = Dialogs.create().nativeTitleBar().message(resourceBundle.getString("ArticleLot_details_confirmation_description.title")).showConfirm();
					if(Dialog.Actions.YES.equals(showConfirm)){
//						articleLotDetailsService.setModel(model).start();
					}else {
						lotTransferCreateView.closeDialog();
					}
				}

			}
		});
		articleLotDetailsService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				ArticleLotDetailsService s = (ArticleLotDetailsService) event.getSource();
				ArticleLot ent = s.getValue();
				event.consume();
				s.reset();
//				articleLotCreateDoneEvent.fire(ent);
				workingEvent.fire("Article details successfuly !");
				lotTransferCreateView.closeDialog();

			}
		});
		articleLotDetailsService.setOnFailed(callFailedEventHandler);

		wareHouseSearchService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				WareHouseSearchService s = (WareHouseSearchService) event.getSource();
				WareHouseSearchResult searchResult = s.getValue();
				event.consume();
				s.reset();
				List<WareHouse> resultList = searchResult.getResultList();
				lotTransferCreateView.getWareHouse().getItems().setAll(resultList);

			}
		});
		wareHouseSearchService.setOnFailed(callFailedEventHandler);

		lotTransferCreateView.addValidators();
	}

	public void handleModalArticleCreateEvent(@Observes @ModalEntityCreateRequestedEvent ArticleLotTransferManager lotTransferManager){
		PropertyReader.copy(lotTransferManager, model);
		ArticleLotArticle articleLot = model.getLotToTransfer().getArticle();
		wareHouseSearchService.setSearchInputs(searchInput).start();
		lotTransferCreateView.showDiaLog();
	}

}
