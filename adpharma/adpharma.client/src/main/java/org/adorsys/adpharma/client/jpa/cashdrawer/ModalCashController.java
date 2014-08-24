package org.adorsys.adpharma.client.jpa.cashdrawer;

import java.math.BigDecimal;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.adorsys.adpharma.client.events.PaymentId;
import org.adorsys.adpharma.client.events.PrintPaymentReceiptRequestedEvent;
import org.adorsys.adpharma.client.events.ProcessDebtstatementPayment;
import org.adorsys.adpharma.client.jpa.article.ArticleSearchResult;
import org.adorsys.adpharma.client.jpa.article.ArticleSearchService;
import org.adorsys.adpharma.client.jpa.customerinvoice.CustomerInvoiceSalesOrder;
import org.adorsys.adpharma.client.jpa.customerinvoice.CustomerInvoiceSearchInput;
import org.adorsys.adpharma.client.jpa.debtstatement.DebtStatement;
import org.adorsys.adpharma.client.jpa.debtstatement.DebtStatementEditService;
import org.adorsys.adpharma.client.jpa.debtstatement.DebtStatementSearchInput;
import org.adorsys.adpharma.client.jpa.debtstatement.DebtStatementSearchResult;
import org.adorsys.adpharma.client.jpa.debtstatement.DebtStatementSearchService;
import org.adorsys.adpharma.client.jpa.payment.Payment;
import org.adorsys.adpharma.client.jpa.payment.PaymentCreateService;
import org.adorsys.adpharma.client.jpa.salesorder.SalesOrder;
import org.adorsys.adpharma.client.jpa.salesorderitem.SalesOrderItemSalesOrder;
import org.adorsys.adpharma.client.jpa.salesorderitem.SalesOrderItemSearchInput;
import org.adorsys.javafx.crud.extensions.login.ErrorDisplay;
import org.adorsys.javafx.crud.extensions.login.ServiceCallFailedEventHandler;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;
import org.apache.commons.lang3.StringUtils;
import org.controlsfx.dialog.Dialogs;

public class ModalCashController {

	@Inject
	private ModalCashView cashView;

	@Inject
	private DebtStatementSearchService debtStatementSearchService ;

	@Inject
	private DebtStatementEditService debtStatementEditService ;

	@Inject private PaymentCreateService paymentCreateService ;

	@Inject
	private ServiceCallFailedEventHandler callFailedEventHandler ;

	@Inject
	private Payment model ;

	@Inject
	private DebtStatement processingDebt ;

	@Inject
	@PrintPaymentReceiptRequestedEvent
	private Event<PaymentId> printPaymentReceiptRequestedEvent;

	@PostConstruct
	public void postConstruct(){
		cashView.bind(model);
		cashView.bindDebt(processingDebt);       
		callFailedEventHandler.setErrorDisplay(new ErrorDisplay() {

			@Override
			protected void showError(Throwable exception) {
				Dialogs.create().showException(exception);
			}
		});
		paymentCreateService.setOnFailed(callFailedEventHandler);
		debtStatementSearchService.setOnFailed(callFailedEventHandler);
		debtStatementEditService.setOnFailed(callFailedEventHandler);

		paymentCreateService.setOnSucceeded(new EventHandler<WorkerStateEvent>(){

			@Override
			public void handle(WorkerStateEvent event) {
				PaymentCreateService s = (PaymentCreateService) event.getSource();
				Payment searchResult = s.getValue();
				event.consume();
				s.reset();
				printPaymentReceiptRequestedEvent.fire(new PaymentId(searchResult.getId()));
				paymentCreateService.setIsForDebtstatement(Boolean.FALSE) ;
				cashView.closeDialog();

			}



		});

		debtStatementSearchService.setOnSucceeded(new EventHandler<WorkerStateEvent>(){

			@Override
			public void handle(WorkerStateEvent event) {
				DebtStatementSearchService s = (DebtStatementSearchService) event.getSource();
				DebtStatementSearchResult searchResult = s.getValue();
				event.consume();
				s.reset();

				if(!searchResult.getResultList().isEmpty())	{
					cashView.getDataList().getItems().setAll(searchResult.getResultList());
					cashView.showDiaLog();
				}else {
					Dialogs.create().message("Aucun Etats en attente d'encaissement !").showInformation();
				}
			}



		});
		cashView.getCashButton().setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				DebtStatement selectedItem = cashView.getDataList().getSelectionModel().getSelectedItem();
				if(isValidePayement(model)){
					model.setStatementNumber(selectedItem.getStatementNumber());
					paymentCreateService.setModel(model).setIsForDebtstatement(Boolean.TRUE).start();
				}

			}
		});
		debtStatementEditService.setOnSucceeded(new EventHandler<WorkerStateEvent>(){

			@Override
			public void handle(WorkerStateEvent event) {
				DebtStatementEditService s = (DebtStatementEditService) event.getSource();
				DebtStatement result = s.getValue();
				event.consume();
				s.reset();
				cashView.getDataList().getItems().remove(result);
				PropertyReader.copy(new Payment(), model);
			}



		});

		cashView.getCloseButton().setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				cashView.closeDialog();

			}
		});

		cashView.getReceivedAmount().numberProperty().addListener(new ChangeListener<BigDecimal>() {

			@Override
			public void changed(
					ObservableValue<? extends BigDecimal> observable,
					BigDecimal oldValue, BigDecimal newValue) {
				if(newValue!=null){
					System.out.println(newValue);
					System.out.println(model.getAmount());
					model.setDifference(newValue.subtract(model.getAmount()));
				}

			}
		});

		cashView.getReturnButton().setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				DebtStatement selectedItem = cashView.getDataList().getSelectionModel().getSelectedItem();
				if(selectedItem!=null){
					selectedItem.setWaitingForCash(Boolean.FALSE);
					debtStatementEditService.setDebtStatement(selectedItem).start();
				}else {
					Dialogs.create().message("veullez selectionner un etat").showInformation();
				}

			}
		});

		cashView.getDataList().setOnMouseClicked(new  EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				DebtStatement selectedItem = cashView.getDataList().getSelectionModel().getSelectedItem();
				if(selectedItem!=null){
					if(!selectedItem.getSettled()){
						model.setAmount(selectedItem.getRestAmount());
						PropertyReader.copy(selectedItem, processingDebt);
					}else {
						Dialogs.create().message("Etat deja solde retourner !").showInformation();
					}
				}

				}
			});
		}

		public void handleDebtstaementPayement(@Observes @ProcessDebtstatementPayment Payment payment){
			PropertyReader.copy(payment, model);
			DebtStatementSearchInput debtStatementSearchInput = new DebtStatementSearchInput();
			debtStatementSearchInput.getEntity().setWaitingForCash(Boolean.TRUE);
			debtStatementSearchInput.getFieldNames().add("waitingForCash");
			debtStatementSearchService.setSearchInputs(debtStatementSearchInput).start();
		}

		public boolean isValidePayement(Payment payment){
			BigDecimal amount = payment.getAmount();
			BigDecimal receivedAmount = payment.getReceivedAmount();
			if(amount==null){
				Dialogs.create().message("Selectionner un etat").showInformation();
				return false ;
			}

			if(receivedAmount==null){
				Dialogs.create().message("Le montant ne peu etre null").showInformation();
				return false ;
			}

			if(receivedAmount.compareTo(amount)<=0){
				payment.setAmount(receivedAmount);
			}
			payment.setDifference(payment.getReceivedAmount().subtract(payment.getAmount()));
			return true ;
		}
	}
