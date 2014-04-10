package org.adorsys.adpharma.client.jpa.cashdrawer;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.adpharma.client.jpa.cashout.CashOut;
import org.adorsys.adpharma.client.jpa.cashout.CashOutAgency;
import org.adorsys.adpharma.client.jpa.cashout.CashOutCashDrawer;
import org.adorsys.adpharma.client.jpa.cashout.CashOutCashier;
import org.adorsys.adpharma.client.jpa.customerinvoice.CustomerInvoice;
import org.adorsys.adpharma.client.jpa.customerinvoice.CustomerInvoiceSearchInput;
import org.adorsys.adpharma.client.jpa.customerinvoice.CustomerInvoiceSearchResult;
import org.adorsys.adpharma.client.jpa.customerinvoice.CustomerInvoiceSearchService;
import org.adorsys.adpharma.client.jpa.customerinvoiceitem.CustomerInvoiceItem;
import org.adorsys.adpharma.client.jpa.customerinvoiceitem.CustomerInvoiceItemInvoice;
import org.adorsys.adpharma.client.jpa.customerinvoiceitem.CustomerInvoiceItemSearchInput;
import org.adorsys.adpharma.client.jpa.customerinvoiceitem.CustomerInvoiceItemSearchResult;
import org.adorsys.adpharma.client.jpa.customerinvoiceitem.CustomerInvoiceItemSearchService;
import org.adorsys.adpharma.client.jpa.payment.Payment;
import org.adorsys.adpharma.client.jpa.payment.PaymentAgency;
import org.adorsys.adpharma.client.jpa.payment.PaymentCashDrawer;
import org.adorsys.adpharma.client.jpa.payment.PaymentCashier;
import org.adorsys.adpharma.client.jpa.payment.PaymentCreateService;
import org.adorsys.adpharma.client.jpa.payment.PaymentCustomerService;
import org.adorsys.adpharma.client.jpa.payment.PaymentPaidBy;
import org.adorsys.adpharma.client.jpa.paymentitem.PaymentItem;
import org.adorsys.adpharma.client.jpa.paymentitem.PaymentItemPaidBy;
import org.adorsys.adpharma.client.jpa.paymentitem.PaymentItemPayment;
import org.adorsys.adpharma.client.jpa.paymentmode.PaymentMode;
import org.adorsys.javafx.crud.extensions.DomainComponent;
import org.adorsys.javafx.crud.extensions.EntityController;
import org.adorsys.javafx.crud.extensions.ViewType;
import org.adorsys.javafx.crud.extensions.events.AssocSelectionEventData;
import org.adorsys.javafx.crud.extensions.events.AssocSelectionRequestEvent;
import org.adorsys.javafx.crud.extensions.events.AssocSelectionResponseEvent;
import org.adorsys.javafx.crud.extensions.events.ComponentSelectionRequestData;
import org.adorsys.javafx.crud.extensions.events.ComponentSelectionRequestEvent;
import org.adorsys.javafx.crud.extensions.events.EntityEditRequestedEvent;
import org.adorsys.javafx.crud.extensions.events.EntityRemoveRequestEvent;
import org.adorsys.javafx.crud.extensions.events.EntitySearchRequestedEvent;
import org.adorsys.javafx.crud.extensions.events.EntitySelectionEvent;
import org.adorsys.javafx.crud.extensions.events.ModalEntityCreateDoneEvent;
import org.adorsys.javafx.crud.extensions.events.ModalEntityCreateRequestedEvent;
import org.adorsys.javafx.crud.extensions.events.SelectedModelEvent;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.login.ErrorDisplay;
import org.adorsys.javafx.crud.extensions.login.PermissionsEvent;
import org.adorsys.javafx.crud.extensions.login.ServiceCallFailedEventHandler;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;
import org.adorsys.javafx.crud.extensions.view.ErrorMessageDialog;
import org.controlsfx.dialog.Dialogs;

@Singleton
public class CashDrawerDisplayController implements EntityController
{

	@Inject
	private CashDrawerDisplayView displayView;

	@Inject
	@EntitySearchRequestedEvent
	private Event<CashDrawer> searchRequestedEvent;

	@Inject
	@ModalEntityCreateRequestedEvent
	private Event<CashOut> modalCashOutCreateRequestedEvent;

	@Inject
	@EntityEditRequestedEvent
	private Event<CashDrawer> editRequestEvent;

	@Inject
	@EntityRemoveRequestEvent
	private Event<CashDrawer> removeRequest;

	@Inject
	private CustomerInvoiceSearchService customerInvoiceSearchService;
	
	@Inject
	@PermissionsEvent
	private Event<DomainComponent> permissionEvent;

	@Inject
	private CashDrawerCreateService cashDrawerCreateService ;

	@Inject
	private CashDrawerCloseService cashDrawerCloseService ;

	@Inject
	private CashDrawerLoadOpenService cashDrawerLoadOpenService ;

	@Inject
	private CustomerInvoiceItemSearchService customerInvoiceItemSearchService ;

//	@Inject
//	private PaymentCustomerService paymentCustomerService;
	
	@Inject 
	private PaymentCreateService paymentCreateService;
	

	@Inject
	private ServiceCallFailedEventHandler serviceCallFailedEventHandler;

	@Inject
	@AssocSelectionResponseEvent
	private Event<AssocSelectionEventData<CashDrawer>> selectionResponseEvent;

	private ObjectProperty<AssocSelectionEventData<CashDrawer>> pendingSelectionRequestProperty = new SimpleObjectProperty<AssocSelectionEventData<CashDrawer>>();

	@Inject
	@ComponentSelectionRequestEvent
	private Event<ComponentSelectionRequestData> componentSelectionRequestEvent;

	@Inject
	@Bundle({ CrudKeys.class, CashDrawer.class, CustomerInvoice.class })
	private ResourceBundle resourceBundle;

	@Inject
	private CashDrawer displayedEntity; 

	@Inject 
	private Payment payment;

	@Inject
	private CustomerInvoice proccessingInvoice;

   @Inject
   private ErrorMessageDialog noCashDrawerErrorMessageDialog;

	@Inject
	private CashDrawerRegistration registration;

	@PostConstruct
	public void postConstruct()
	{

		displayView.getOpenCashDrawerButton().disableProperty().bind(registration.canCreateProperty().not());
		displayView.getCloseCashDrawerButton().disableProperty().bind(registration.canEditProperty().not());
		displayView.bindInvoice(proccessingInvoice);
		displayView.bindPayment(payment);

		serviceCallFailedEventHandler.setErrorDisplay(new ErrorDisplay() {

			@Override
			protected void showError(Throwable exception) {
				Dialogs.create().nativeTitleBar().showException(exception);
			}
		});


		/*
		 * handle open cash drawer action
		displayView.getReceivedAmount().setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				calculateDifference();
			}
		});
		 */
		displayView.getDocNumber().textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> obs,
					String oldValue, String newValue) {
				// check is we have the old payment on file and update it
				List<PaymentItem> paymentItems = payment.getPaymentItems();
				for (PaymentItem paymentItem : paymentItems) {
					if(paymentItem.getPaymentMode().equals(payment.getPaymentMode())){
						paymentItem.setDocumentNumber(payment.getPaymentNumber());
					}
					if(PaymentMode.VOUCHER.equals(paymentItem.getPaymentMode())){
						// load voucher from the database.
					}
				}
			}
			
			
		});
		displayView.getReceivedAmount().numberProperty().addListener(new ChangeListener<BigDecimal>() {
			@Override
			public void changed(ObservableValue<? extends BigDecimal> obs,
					BigDecimal oldValue, BigDecimal newValue) {
				
				BigDecimal paidAmount = calculateDifference(newValue);
				// check is we have the old payment on file and update it
//				List<PaymentItem> paymentItems = payment.getPaymentItems();
				ObservableList<PaymentItem> paymentItems = displayView.getPaymentItemDataList().itemsProperty().getValue();				
				PaymentItem expectedItem = null;
				for (PaymentItem paymentItem : paymentItems) {
					if(paymentItem.getPaymentMode().equals(payment.getPaymentMode())){
						expectedItem = paymentItem;
						expectedItem.setReceivedAmount(newValue);
						expectedItem.setAmount(paidAmount);
						break;
					}
				}
				if(BigDecimal.ZERO.compareTo(paidAmount)>=0){
					displayView.getPaymentItemDataList().itemsProperty().getValue().remove(expectedItem);
				} else {
					if (expectedItem==null){
						expectedItem = new PaymentItem();
						expectedItem.setAmount(paidAmount);
						expectedItem.setPaymentMode(payment.getPaymentMode());
						expectedItem.setReceivedAmount(payment.getReceivedAmount());
						expectedItem.setPayment(new PaymentItemPayment(payment));
						expectedItem.setDocumentNumber(payment.getPaymentNumber());
						PaymentItemPaidBy paymentItemPaidBy = new PaymentItemPaidBy();
						paymentItemPaidBy.setId(payment.getPaidBy().getId());
						expectedItem.setPaidBy(paymentItemPaidBy);
	//					payment.getPaymentItems().add(expectedItem);
						displayView.getPaymentItemDataList().itemsProperty().getValue().add(expectedItem);
					} 
				}
			}
		});

		displayView.getPaymentMode().valueProperty().addListener(new ChangeListener<PaymentMode>() {

			@Override
			public void changed(ObservableValue<? extends PaymentMode> obs,
					PaymentMode oldValue, PaymentMode newValue) {
				// Whatever comes here, first compute the amount to pay.
				BigDecimal amount = payment.getAmount();
				BigDecimal paid = BigDecimal.ZERO;
				
				PaymentItem selectedItem = null;
//				List<PaymentItem> paymentItems = payment.getPaymentItems();
				ObservableList<PaymentItem> paymentItems = displayView.getPaymentItemDataList().itemsProperty().getValue();				
				for (PaymentItem paymentItem : paymentItems) {
					paid = paid.add(paymentItem.getReceivedAmount());
					if(paymentItem.getPaymentMode().equals(newValue))
						selectedItem = paymentItem;
				}				
				if(selectedItem!=null){
					payment.setAmount(amount.subtract(paid).add(selectedItem.getReceivedAmount()));
					payment.setReceivedAmount(selectedItem.getReceivedAmount());
					payment.setPaymentNumber(selectedItem.getDocumentNumber());
					payment.setDifference(BigDecimal.ZERO);
				} else {
					payment.setAmount(amount.subtract(paid));
					payment.setReceivedAmount(BigDecimal.ZERO);
					payment.setPaymentNumber("");
					payment.setDifference(BigDecimal.ZERO);
				}
			}
		});

		/*
		 * handle open cash drawer action
		 */
		displayView.getOpenCashDrawerButton().setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				createCashDrawer();
			}
		});

		/*
		 * handle open cash drawer action
		 */
		displayView.getCashOutButton().setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				CashOutAgency cashOutAgency = new CashOutAgency();
				CashOutCashDrawer cashOutCashDrawer = new CashOutCashDrawer();
				CashOutCashier cashOutCashier = new CashOutCashier();

				PropertyReader.copy(displayedEntity.getAgency(), cashOutAgency);
				PropertyReader.copy(displayedEntity.getCashier(), cashOutCashier);
				PropertyReader.copy(displayedEntity, cashOutCashDrawer);

				CashOut cashOut = new CashOut();
				cashOut.setAgency(cashOutAgency);
				cashOut.setCasDrawer(cashOutCashDrawer);
				cashOut.setCashier(cashOutCashier);

				modalCashOutCreateRequestedEvent.fire(cashOut);

			}
		});

		cashDrawerCreateService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				CashDrawerCreateService s = (CashDrawerCreateService) event.getSource();
				CashDrawer cd = s.getValue();
				event.consume();
				s.reset();
				PropertyReader.copy(cd, displayedEntity);
				System.out.println(cd.getCashDrawerNumber());

			}
		});
		cashDrawerCreateService.setOnFailed(serviceCallFailedEventHandler);
		cashDrawerLoadOpenService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				CashDrawerLoadOpenService s = (CashDrawerLoadOpenService) event.getSource();
				CashDrawerSearchResult result = s.getValue();
				event.consume();
				s.reset();
				List<CashDrawer> resultList = result.getResultList();
				if(resultList==null || resultList.isEmpty()){
					// Display error to user.
	               noCashDrawerErrorMessageDialog.getTitleText().setText(
	                       resourceBundle.getString("CashDrawer_no_opened_for_user.title"));
	                 noCashDrawerErrorMessageDialog.getDetailText().setText(resourceBundle.getString("CashDrawer_no_opened_for_user.text"));
	                 noCashDrawerErrorMessageDialog.display();
				} else {
					CashDrawer cashDrawer = resultList.iterator().next();
					PropertyReader.copy(cashDrawer, displayedEntity);
				}

			}
		});
	      noCashDrawerErrorMessageDialog.getOkButton().setOnAction(
	              new EventHandler<ActionEvent>()
	              {
	                 @Override
	                 public void handle(ActionEvent event)
	                 {
	                    noCashDrawerErrorMessageDialog.closeDialog();
	                    createCashDrawer();
	                 }
	              });

		cashDrawerLoadOpenService.setOnFailed(serviceCallFailedEventHandler);

//		paymentCustomerService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
//
//			@Override
//			public void handle(WorkerStateEvent event) {
//				PaymentCustomerService s = (PaymentCustomerService) event.getSource();
//				Payment pay = s.getValue();
//				event.consume();
//				s.reset();
//				PropertyReader.copy(new Payment(), payment);
//				PropertyReader.copy(new CustomerInvoice(), proccessingInvoice);
//				handleCustomerInvoiceSearchEvent();
//			}
//		});
//		paymentCustomerService.setOnFailed(serviceCallFailedEventHandler);
//		
//		paymentCreateService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
//
//			@Override
//			public void handle(WorkerStateEvent event) {
//				PaymentCreateService s = (PaymentCreateService) event.getSource();
//				Payment pay = s.getValue();
//				event.consume();
//				s.reset();
//				PropertyReader.copy(pay, payment);
//				ArrayList<CustomerInvoice> invoices = new ArrayList<CustomerInvoice>();
//				invoices.add(proccessingInvoice);
//				paymentCustomerService.setEntityId(pay.getId()).setInvoices(invoices).start();
//				
//			}
//		});
		paymentCreateService.setOnFailed(serviceCallFailedEventHandler);

		/*
		 * handle close cash drawer action
		 */
		displayView.getCloseCashDrawerButton().setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if(displayedEntity.getId()!=null)
					cashDrawerCloseService.setCashDrawer(displayedEntity).start();

			}
		});

		/*
		 * handle  payement Action action
		 */
		displayView.getCashButon().setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				payment.setCashDrawer(new PaymentCashDrawer(displayedEntity));				
				if(isValidCustomerPayement()){
					paymentCreateService.setModel(payment).start();
				}else {
					Dialogs.create().nativeTitleBar().message("Receive Amount Should be upper than Amount").showInformation();
				}

			}
		});

		/*
		 *listen to search button and fire search requested event
		 */
		displayView.getSearchButton().setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				handleCustomerInvoiceSearchEvent();
			}
		});
		displayView.getInvoicesDataList().setOnMouseClicked(new  EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				CustomerInvoice selectedItem = displayView.getInvoicesDataList().getSelectionModel().getSelectedItem();
				if(selectedItem!=null){
					PropertyReader.copy(selectedItem, proccessingInvoice);

					PaymentPaidBy paymentPaidBy = new PaymentPaidBy();
					PropertyReader.copy(proccessingInvoice.getCustomer(), paymentPaidBy);
					payment.setPaidBy(paymentPaidBy);

					payment.setAmount(proccessingInvoice.getCustomerRestTopay());

					CustomerInvoiceItemSearchInput ciisi = new CustomerInvoiceItemSearchInput();
					ciisi.getEntity().setInvoice(new CustomerInvoiceItemInvoice(selectedItem));
					ciisi.getFieldNames().add("invoice");
					ciisi.setMax(-1);
					customerInvoiceItemSearchService.setSearchInputs(ciisi).start();

				}

			}
		});

		//		cashDrawerCloseService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
		//
		//			@Override
		//			public void handle(WorkerStateEvent event) {
		//				CashDrawerCloseService s = (CashDrawerCloseService) event.getSource();
		//				CashDrawer cd = s.getValue();
		//				event.consume();
		//				s.reset();
		//				PropertyReader.copy(cd, displayedEntity);
		//				System.out.println(displayedEntity +":"+displayedEntity.getClosingDate());
		//				permissionEvent.fire(new DomainComponent(null, null));
		//
		//			}
		//		});
		cashDrawerCloseService.setOnFailed(serviceCallFailedEventHandler);

		customerInvoiceSearchService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				CustomerInvoiceSearchService s = (CustomerInvoiceSearchService) event.getSource();
				CustomerInvoiceSearchResult searchResult = s.getValue();
				event.consume();
				s.reset();
				List<CustomerInvoice> resultList = searchResult.getResultList();
				displayView.getInvoicesDataList().getItems().setAll(resultList);

			}
		});
		customerInvoiceSearchService.setOnFailed(serviceCallFailedEventHandler);

		customerInvoiceItemSearchService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				CustomerInvoiceItemSearchService s = (CustomerInvoiceItemSearchService) event.getSource();
				CustomerInvoiceItemSearchResult searchResult = s.getValue();
				event.consume();
				s.reset();
				List<CustomerInvoiceItem> resultList = searchResult.getResultList();
				proccessingInvoice.setInvoiceItems(resultList);

			}
		});
		customerInvoiceItemSearchService.setOnFailed(serviceCallFailedEventHandler);

	}

	protected void createCashDrawer() {
		BigDecimal cashDrawerInitialAmount = getCashDrawerInitialAmount();
		CashDrawer cashDrawer = new CashDrawer();
		cashDrawer.setInitialAmount(cashDrawerInitialAmount);
		cashDrawerCreateService.setModel(cashDrawer).start();
	}

	public void display(Pane parent)
	{
		BorderPane rootPane = displayView.getRootPane();
		ObservableList<Node> children = parent.getChildren();
		if (!children.contains(rootPane))
		{
			children.add(rootPane);
		}
		loadOpenCashDrawer();
	}

	@Override
	public ViewType getViewType()
	{
		return ViewType.DISPLAY;
	}

	public BigDecimal getCashDrawerInitialAmount(){
		String showTextInput = Dialogs.create().message(resourceBundle.getString("CashDrawer_initialAmount_description.title")).showTextInput("0");
		BigDecimal initialAmount = BigDecimal.ZERO ;
		try {
			initialAmount = new BigDecimal(showTextInput);
		} catch (Exception e) {
			getCashDrawerInitialAmount();
		}

		return initialAmount;
	}

	/**
	 * Listens to list selection events and bind selected to pane
	 */
	public void handleSelectionEvent(@Observes @EntitySelectionEvent CashDrawer selectedEntity)
	{
		PropertyReader.copy(selectedEntity, displayedEntity);


	}
	public void handleCashDrawerCreateDoneEvent(@Observes @ModalEntityCreateDoneEvent CashDrawer cashDrawer){
		PropertyReader.copy(cashDrawer, displayedEntity);
	}
	public void handleAssocSelectionRequest(@Observes(notifyObserver = Reception.ALWAYS) @AssocSelectionRequestEvent AssocSelectionEventData<CashDrawer> eventData)
	{
		pendingSelectionRequestProperty.set(eventData);
		componentSelectionRequestEvent.fire(new ComponentSelectionRequestData(CashDrawer.class.getName()));
		searchRequestedEvent.fire(eventData.getTargetEntity() != null ? eventData.getTargetEntity() : new CashDrawer());
	}

	/**
	 * This is the only time where the bind method is called on this object.
	 * @param model
	 */
	public void handleNewModelEvent(@Observes @SelectedModelEvent CashDrawer model)
	{
		this.displayedEntity = model;
		displayView.bind(this.displayedEntity);
	}

	private BigDecimal calculateDifference(BigDecimal newValue)
	{
		BigDecimal amount = displayView.getAmount().getNumber();
		if(newValue.compareTo(amount)>=0){
			displayView.getDifference().setNumber(newValue.subtract(amount));
			return amount;
		} else {
			displayView.getDifference().setNumber(BigDecimal.ZERO);
			return newValue;
		}
	}

	public void handleCustomerInvoiceSearchEvent(){
		CustomerInvoiceSearchInput csi = new CustomerInvoiceSearchInput();
		csi.getEntity().setCashed(Boolean.FALSE);
		csi.getFieldNames().add("cashed");
		csi.setMax(-1);
		customerInvoiceSearchService.setSearchInputs(csi).start();
	}

	private boolean isValidCustomerPayement(){
		ObservableList<PaymentItem> list = displayView.getPaymentItemDataList().itemsProperty().getValue();
		ArrayList<PaymentItem> arrayList = new ArrayList<PaymentItem>(list);
		payment.getPaymentItems().clear();
		payment.getPaymentItems().addAll(arrayList);
		BigDecimal receivedAmount2 = BigDecimal.ZERO;
		BigDecimal amount2 = BigDecimal.ZERO;
		for (PaymentItem paymentItem : arrayList) {
			if(paymentItem.getReceivedAmount().compareTo(BigDecimal.ZERO)<=0) payment.getPaymentItems().remove(paymentItem);
			receivedAmount2 = receivedAmount2.add(paymentItem.getAmount());
			amount2 = amount2.add(paymentItem.getReceivedAmount());
		}
		return receivedAmount2.compareTo(BigDecimal.ZERO)>=0 && amount2.compareTo(BigDecimal.ZERO)>=0;
	}

	public void loadOpenCashDrawer(){
		cashDrawerLoadOpenService.start();
	}

}