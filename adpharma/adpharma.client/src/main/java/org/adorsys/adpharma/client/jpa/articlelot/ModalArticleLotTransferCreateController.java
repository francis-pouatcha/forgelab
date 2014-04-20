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

import org.adorsys.adpharma.client.jpa.warehouse.WareHouse;
import org.adorsys.adpharma.client.jpa.warehouse.WareHouseSearchInput;
import org.adorsys.adpharma.client.jpa.warehouse.WareHouseSearchResult;
import org.adorsys.adpharma.client.jpa.warehouse.WareHouseSearchService;
import org.adorsys.adpharma.client.jpa.warehousearticlelot.WareHouseArticleLot;
import org.adorsys.adpharma.client.jpa.warehousearticlelot.WareHouseArticleLotTransferService;
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
	private WareHouseArticleLotTransferService articleLotTransferService;

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
				WareHouse wareHouse = lotTransferCreateView.getWareHouse().getSelectionModel().getSelectedItem();
				if(validate.isEmpty()){
					Action showConfirm = Dialogs.create().nativeTitleBar().message(resourceBundle.getString("ArticleLot_details_confirmation_description.title")).showConfirm();
					if(Dialog.Actions.YES.equals(showConfirm)){
						articleLotTransferService.setModel(model).start();
					}else {
						lotTransferCreateView.closeDialog();
					}
				}

			}
		});

		articleLotTransferService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				WareHouseArticleLotTransferService s = (WareHouseArticleLotTransferService) event.getSource();
				WareHouseArticleLot ent = s.getValue();
				event.consume();
				s.reset();
				wareHouseArticleLotCreateDoneEvent.fire(ent);
				workingEvent.fire("Lot transfered successfuly !");
				lotTransferCreateView.closeDialog();

			}
		});
		articleLotTransferService.setOnFailed(callFailedEventHandler);

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
		lotTransferCreateView.bind(model);

		lotTransferCreateView.showDiaLog();
		wareHouseSearchService.setSearchInputs(searchInput).start();
	}

}
