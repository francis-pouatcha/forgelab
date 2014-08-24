package org.adorsys.adpharma.client.jpa.cashdrawer;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.adpharma.client.access.SecurityUtil;
import org.adorsys.adpharma.client.events.PaymentId;
import org.adorsys.adpharma.client.events.PrintPaymentReceiptRequestedEvent;
import org.adorsys.adpharma.client.events.ProcessDebtstatementPayment;
import org.adorsys.adpharma.client.jpa.customerinvoice.CustomerInvoice;
import org.adorsys.adpharma.client.jpa.customerinvoice.CustomerInvoiceSalesOrder;
import org.adorsys.adpharma.client.jpa.customerinvoice.CustomerInvoiceSearchInput;
import org.adorsys.adpharma.client.jpa.customerinvoice.CustomerInvoiceSearchResult;
import org.adorsys.adpharma.client.jpa.customerinvoice.CustomerInvoiceSearchService;
import org.adorsys.adpharma.client.jpa.customervoucher.CustomerVoucher;
import org.adorsys.adpharma.client.jpa.customervoucher.CustomerVoucherCheckingView;
import org.adorsys.adpharma.client.jpa.customervoucher.CustomerVoucherCustomer;
import org.adorsys.adpharma.client.jpa.customervoucher.CustomerVoucherSearchInput;
import org.adorsys.adpharma.client.jpa.customervoucher.CustomerVoucherSearchResult;
import org.adorsys.adpharma.client.jpa.customervoucher.CustomerVoucherSearchService;
import org.adorsys.adpharma.client.jpa.disbursement.Disbursement;
import org.adorsys.adpharma.client.jpa.disbursement.DisbursementAgency;
import org.adorsys.adpharma.client.jpa.disbursement.DisbursementCashDrawer;
import org.adorsys.adpharma.client.jpa.disbursement.DisbursementCashier;
import org.adorsys.adpharma.client.jpa.documentprocessingstate.DocumentProcessingState;
import org.adorsys.adpharma.client.jpa.login.Login;
import org.adorsys.adpharma.client.jpa.payment.Payment;
import org.adorsys.adpharma.client.jpa.payment.PaymentAgency;
import org.adorsys.adpharma.client.jpa.payment.PaymentCashDrawer;
import org.adorsys.adpharma.client.jpa.payment.PaymentCashier;
import org.adorsys.adpharma.client.jpa.payment.PaymentCreateService;
import org.adorsys.adpharma.client.jpa.payment.PaymentSearchInput;
import org.adorsys.adpharma.client.jpa.paymentcustomerinvoiceassoc.PaymentCustomerInvoiceAssoc;
import org.adorsys.adpharma.client.jpa.paymentitem.PaymentItem;
import org.adorsys.adpharma.client.jpa.paymentitem.PaymentItemPaidBy;
import org.adorsys.adpharma.client.jpa.paymentmode.PaymentMode;
import org.adorsys.adpharma.client.jpa.salesorder.SalesOrder;
import org.adorsys.adpharma.client.jpa.salesorder.SalesOrderCancelService;
import org.adorsys.adpharma.client.jpa.salesorder.SalesOrderRestToPay;
import org.adorsys.adpharma.client.jpa.salesorder.SalesOrderSearchInput;
import org.adorsys.adpharma.client.jpa.salesorder.SalesOrderSearchResult;
import org.adorsys.adpharma.client.jpa.salesorder.SalesOrderSearchService;
import org.adorsys.adpharma.client.jpa.salesorderitem.SalesOrderItem;
import org.adorsys.adpharma.client.jpa.salesorderitem.SalesOrderItemSalesOrder;
import org.adorsys.adpharma.client.jpa.salesorderitem.SalesOrderItemSearchInput;
import org.adorsys.adpharma.client.jpa.salesorderitem.SalesOrderItemSearchResult;
import org.adorsys.adpharma.client.jpa.salesorderitem.SalesOrderItemSearchService;
import org.adorsys.adpharma.client.jpa.salesordertype.SalesOrderType;
import org.adorsys.javafx.crud.extensions.EntityController;
import org.adorsys.javafx.crud.extensions.ViewType;
import org.adorsys.javafx.crud.extensions.events.AssocSelectionEventData;
import org.adorsys.javafx.crud.extensions.events.AssocSelectionRequestEvent;
import org.adorsys.javafx.crud.extensions.events.AssocSelectionResponseEvent;
import org.adorsys.javafx.crud.extensions.events.CloseOpenTabRequestEvent;
import org.adorsys.javafx.crud.extensions.events.ComponentSelectionRequestData;
import org.adorsys.javafx.crud.extensions.events.ComponentSelectionRequestEvent;
import org.adorsys.javafx.crud.extensions.events.EntityEditRequestedEvent;
import org.adorsys.javafx.crud.extensions.events.EntityRemoveRequestEvent;
import org.adorsys.javafx.crud.extensions.events.EntitySearchRequestedEvent;
import org.adorsys.javafx.crud.extensions.events.EntitySelectionEvent;
import org.adorsys.javafx.crud.extensions.events.ModalEntityCreateDoneEvent;
import org.adorsys.javafx.crud.extensions.events.ModalEntityCreateRequestedEvent;
import org.adorsys.javafx.crud.extensions.events.ModalEntitySearchRequestedEvent;
import org.adorsys.javafx.crud.extensions.events.SelectedModelEvent;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.login.ErrorDisplay;
import org.adorsys.javafx.crud.extensions.login.ServiceCallFailedEventHandler;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;
import org.adorsys.javafx.crud.extensions.view.ErrorMessageDialog;
import org.apache.commons.lang3.StringUtils;
import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.Dialog;
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
	private Event<Disbursement> modalCashOutCreateRequestedEvent;

	@Inject
	@EntityEditRequestedEvent
	private Event<CashDrawer> editRequestEvent;

	@Inject  
	@EntityRemoveRequestEvent
	private Event<CashDrawer> removeRequest;

	@Inject
	private SalesOrderSearchService salesOrderSearchService;
	@Inject
	private ServiceCallFailedEventHandler salesOrderSearchServiceFailedHandler;

	@Inject
	private CashDrawerCreateService cashDrawerCreateService ;
	@Inject
	private ServiceCallFailedEventHandler cashDrawerCreateServiceFailedHandler;

	@Inject
	private CashDrawerCloseService cashDrawerCloseService;
	@Inject
	private ServiceCallFailedEventHandler cashDrawerCloseServiceFailedHandler;

	@Inject
	private CashDrawerLoadOpenService cashDrawerLoadService ;
	@Inject
	private ServiceCallFailedEventHandler cashDrawerLoadServiceFailedHandler;
	@Inject
	private ErrorMessageDialog noCashDrawerErrorMessageDialog;

	@Inject
	private SalesOrderItemSearchService salesOrderItemSearchService ;
	@Inject
	private ServiceCallFailedEventHandler salesOrderItemSearchServiceFailedHandler;

	@Inject 
	private PaymentCreateService paymentCreateService;
	@Inject
	private ServiceCallFailedEventHandler paymentCreateServiceFailedHandler;

	@Inject
	private ServiceCallFailedEventHandler serviceFailedHandler;
	
	@Inject
	@ModalEntitySearchRequestedEvent
	private Event<PaymentSearchInput> paymentSearchInputRequestEvent;

	@Inject
	@AssocSelectionResponseEvent
	private Event<AssocSelectionEventData<CashDrawer>> selectionResponseEvent;

	private ObjectProperty<AssocSelectionEventData<CashDrawer>> pendingSelectionRequestProperty = new SimpleObjectProperty<AssocSelectionEventData<CashDrawer>>();

	@Inject
	@ComponentSelectionRequestEvent
	private Event<ComponentSelectionRequestData> componentSelectionRequestEvent;
	
	@Inject
	@ProcessDebtstatementPayment
	private Event<Payment> debtstatementPaymentRequestEvent;

	@Inject
	@Bundle({ CrudKeys.class, CashDrawer.class, CustomerInvoice.class })
	private ResourceBundle resourceBundle;

	@Inject
	private CashDrawer displayedEntity; 

	private final PaymentManager paymentManager = new PaymentManager();

	@Inject
	private SalesOrder proccessingOrder;

	@Inject
	private SalesOrderCancelService orderCancelService;

	@Inject
	private CashDrawerRegistration registration;

	@Inject
	private CustomerVoucherSearchService customerVoucherSearchService;
	@Inject
	private ServiceCallFailedEventHandler customerVoucherSearchFailedEventHandler;

	@Inject
	private ErrorMessageDialog errorMessageDialog;

	@Inject
	@PrintPaymentReceiptRequestedEvent
	private Event<PaymentId> printPaymentReceiptRequestedEvent;
	
	@Inject
	@EntitySearchRequestedEvent
	private Event<CashDrawer> cashDrawerSearchRequestEvent;
	
	@Inject
	private SalesOrderSearchService salesOrderPaymentChecker ;

	@Inject
	@CloseOpenTabRequestEvent
	private Event<Object> closeOpenTabRequestEvent ;

	@Inject
	private CustomerVoucherCheckingView voucherCheckingView ;
	
	@Inject
	private CustomerInvoiceSearchService customerInvoiceSearchService;
	@Inject
	private ServiceCallFailedEventHandler customerInvoiceServiceFailedHandler;
	
	@Inject
	private SecurityUtil securityUtil;


	@PostConstruct
	public void postConstruct()
	{

		//		displayView.getOpenCashDrawerButton().disableProperty().bind(registration.canCreateProperty().not());
		//		displayView.getCloseCashDrawerButton().disableProperty().bind(registration.canEditProperty().not());
		displayView.bindInvoice(proccessingOrder);
		//		paymentManager.getPayment().paymentItemsProperty().bind(displayView.getPaymentItemDataList().itemsProperty());
		displayView.bindPayment(paymentManager.getPayment());

		processVoucherPaiementChanged();
		handleCashDrawerCreateService();
		handleCashDrawerLoadService();
		handleReceivedAmountChanged();
		handlePaymentModeChanged();
		handlePaymentCreateService();

		displayView.getCancelButton().setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) 
			{
				SalesOrder selectedItem = displayView.getInvoicesDataList().getSelectionModel().getSelectedItem();
				if(selectedItem!=null){
					SalesOrder salesOrder = new SalesOrder() ;
					PropertyReader.copy(selectedItem, salesOrder);
					Action showConfirm = Dialogs.create().message("etes vous sure de vouloir annuler cette facture ? ").showConfirm();
					if(Dialog.Actions.YES.equals(showConfirm))
						orderCancelService.setSalesOrder(salesOrder).start();

				}

			}
		});
		
		displayView.getDebtStatementButton().setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				Login connectedUser = securityUtil.getConnectedUser();
				Payment payment = new Payment();
				payment.setCashDrawer(new PaymentCashDrawer(displayedEntity));
				payment.setCashier(new PaymentCashier(connectedUser));
				debtstatementPaymentRequestEvent.fire(payment);
			}
		});
		

		displayView.getSearchPayementButton().setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) 
			{
				PaymentSearchInput paymentSearchInput = new PaymentSearchInput();
				paymentSearchInput.getEntity().setCashDrawer(new PaymentCashDrawer(displayedEntity));
				paymentSearchInput.getEntity().setCashier(new PaymentCashier(securityUtil.getConnectedUser()));
				paymentSearchInput.setMax(30);
				paymentSearchInput.getFieldNames().add("cashDrawer");
				paymentSearchInput.getFieldNames().add("cashier");
				paymentSearchInputRequestEvent.fire(paymentSearchInput);

			}
		});

		orderCancelService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				SalesOrderCancelService s = (SalesOrderCancelService) event.getSource();
				SalesOrder so = s.getValue();
				event.consume();
				s.reset();
				SalesOrder selectedItem = displayView.getInvoicesDataList().getSelectionModel().getSelectedItem();
				displayView.getInvoicesDataList().getItems().remove(selectedItem);
				PropertyReader.copy(new SalesOrder(), proccessingOrder);
				// Reset invoices.
				
				proccessingOrder.clearInvoices();

				displayView.getPaymentItemDataList().getItems().clear();
				displayView.getAmount().setNumber(BigDecimal.ZERO);
			}
		});

		orderCancelService.setOnFailed(serviceFailedHandler);
		serviceFailedHandler.setErrorDisplay(new ErrorDisplay() 
		{
			@Override
			protected void showError(Throwable exception)
			{
				Dialogs.create().showException(exception);
			}
		});

		/*
		 * handle open cash drawer action
		 */
		displayView.getOpenCashDrawerButton().setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				cashDrawerSearchRequestEvent.fire(new CashDrawer());
			}
		});
		/*
		 * handle Remove Payment action
		 */
		displayView.getRemovePaymentMenuItem().setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				PaymentItem selectedItem = displayView.getPaymentItemDataList().getSelectionModel().getSelectedItem();
				if(selectedItem!=null){
					BigDecimal amount = displayView.getAmount().getNumber();
					//					displayView.getAmount().setNumber(amount.add(selectedItem.getAmount()));
					displayView.getPaymentItemDataList().getItems().remove(selectedItem);
					displayView.getReceivedAmount().setNumber(BigDecimal.ZERO);
					displayView.getDifference().setNumber(BigDecimal.ZERO);
				}
			}
		});

		/*
		 * handle open cash drawer action
		 */
		displayView.getCashOutButton().setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				DisbursementAgency cashOutAgency = new DisbursementAgency();
				DisbursementCashDrawer cashOutCashDrawer = new DisbursementCashDrawer();
				DisbursementCashier cashOutCashier = new DisbursementCashier();

				PropertyReader.copy(displayedEntity.getAgency(), cashOutAgency);
				PropertyReader.copy(displayedEntity.getCashier(), cashOutCashier);
				PropertyReader.copy(displayedEntity, cashOutCashDrawer);

				Disbursement disbursement = new Disbursement();
				disbursement.setAgency(cashOutAgency);
				disbursement.setCashDrawer(cashOutCashDrawer);
				disbursement.setCashier(cashOutCashier);

				modalCashOutCreateRequestedEvent.fire(disbursement);

			}
		});

		/*
		 * handle close cash drawer action
		 */
		displayView.getCloseCashDrawerButton().setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Action showConfirm = Dialogs.create().message(resourceBundle.getString("CashDrawer_close_confirmation.title")).showConfirm();
				if(Dialog.Actions.YES.equals(showConfirm)){
					if(displayedEntity.getId()!=null)
						cashDrawerCloseService.setCashDrawer(displayedEntity).restart();
				}
			}
		});


		/*
		 *listen to search button and fire search requested event
		 */
		displayView.getSearchButton().setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				handleSalesOrderSearchEvent();
			}
		});
		displayView.getInvoicesDataList().setOnMouseClicked(new  EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				SalesOrder selectedItem = displayView.getInvoicesDataList().getSelectionModel().getSelectedItem();
				if(selectedItem!=null){
					PropertyReader.copy(selectedItem, proccessingOrder);

					SalesOrderItemSearchInput ciisi = new SalesOrderItemSearchInput();
					ciisi.getEntity().setSalesOrder(new SalesOrderItemSalesOrder(selectedItem));
					ciisi.getFieldNames().add("salesOrder");
					ciisi.setMax(-1);
					activate();
					clearPaymentDataList();
					salesOrderItemSearchService.setSearchInputs(ciisi).start();
					
					CustomerInvoiceSearchInput searchInputs = new CustomerInvoiceSearchInput();
					searchInputs.getEntity().setSalesOrder(new CustomerInvoiceSalesOrder(selectedItem));
					searchInputs.getFieldNames().add("salesOrder");
					searchInputs.setMax(-1);
					customerInvoiceSearchService.setSearchInputs(searchInputs).start();
				}

			}
		});
		cashDrawerCloseServiceFailedHandler.setErrorDisplay(new ErrorDisplay() {

			@Override
			protected void showError(Throwable exception) {
				Dialogs.create().showException(exception);

			}
		});

		cashDrawerCloseService.setOnFailed(cashDrawerCloseServiceFailedHandler);
		cashDrawerCloseService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				CashDrawerCloseService s = (CashDrawerCloseService) event.getSource();
				CashDrawer searchResult = s.getValue();
				event.consume();
				s.reset();
				closeOpenTabRequestEvent.fire(new Object());

			}
		});


		salesOrderSearchService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				SalesOrderSearchService s = (SalesOrderSearchService) event.getSource();
				SalesOrderSearchResult searchResult = s.getValue();
				event.consume();
				s.reset();
				List<SalesOrder> resultList = searchResult.getResultList();
				resultList.sort(new Comparator<SalesOrder>() {

					@Override
					public int compare(SalesOrder o1, SalesOrder o2) {
						// TODO Auto-generated method stub
						return o1.getId().compareTo(o2.getId());
					}
				});
				displayView.getInvoicesDataList().getItems().setAll(resultList);
				proccessingOrder.clearInvoices();
				deactivate();

			}
		});
		salesOrderSearchService.setOnFailed(salesOrderSearchServiceFailedHandler);

		salesOrderItemSearchService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				SalesOrderItemSearchService s = (SalesOrderItemSearchService) event.getSource();
				SalesOrderItemSearchResult searchResult = s.getValue();
				event.consume();
				s.reset();
				List<SalesOrderItem> resultList = searchResult.getResultList();
				proccessingOrder.setSalesOrderItems(resultList);
				SalesOrderRestToPay restToPay = new SalesOrderRestToPay(proccessingOrder);
				displayView.getAmount().setNumber(restToPay.getCustomerRestToPay());
				displayView.getReceivedAmount().requestFocus();
			}
		});
		salesOrderItemSearchService.setOnFailed(salesOrderItemSearchServiceFailedHandler);

		customerInvoiceSearchService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				CustomerInvoiceSearchService s = (CustomerInvoiceSearchService) event.getSource();
				CustomerInvoiceSearchResult searchResult = s.getValue();
				event.consume();
				s.reset();
				List<CustomerInvoice> resultList2 = searchResult.getResultList();
				proccessingOrder.writeInvoices(resultList2);
			}
		});
		customerInvoiceSearchService.setOnFailed(customerInvoiceServiceFailedHandler);
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

	private BigDecimal getCashDrawerInitialAmount(){
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

	public void handleSalesOrderSearchEvent(){
		SalesOrderSearchInput csi = new SalesOrderSearchInput();
		csi.getEntity().setCashed(Boolean.FALSE);
		csi.getEntity().setSalesOrderType(SalesOrderType.CASH_SALE);
		csi.getEntity().setSalesOrderStatus(DocumentProcessingState.CLOSED);
		csi.getFieldNames().add("cashed");
		csi.getFieldNames().add("invoiceType");
		csi.getFieldNames().add("salesOrderStatus");
		csi.setMax(100);
		salesOrderSearchService.setSearchInputs(csi).start();
	}

	public void loadOpenCashDrawer(){
		cashDrawerLoadService.start();
	}

	static class PaymentManager {
		private final Payment payment = new Payment();

		public void newPayment(){
			PropertyReader.copy(new Payment(), payment);
		}

		public Payment getPayment(){
			return payment;
		}

	}

	private void processVoucherPaiementChanged(){

		voucherCheckingView.getSaveButton().setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				String voucherNumber = voucherCheckingView.getVoucherNumber().getText();
				BigDecimal restAmount = voucherCheckingView.getRestAmount().getNumber();

				CustomerVoucherSearchInput voucherSearchInput = new CustomerVoucherSearchInput();
				voucherSearchInput.getEntity().setVoucherNumber(voucherNumber);
				voucherSearchInput.getEntity().setRestAmount(restAmount);
				voucherSearchInput.getFieldNames().add("voucherNumber");
				voucherSearchInput.getFieldNames().add("restAmount");
				customerVoucherSearchService.setSearchInputs(voucherSearchInput).start();
				voucherCheckingView.close();

			}
		});
		voucherCheckingView.getCancelButton().setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				voucherCheckingView.close();
				resetPaymentModeTocash();
			}
		});

		displayView.getPaymentMode().getSelectionModel().selectedItemProperty().addListener(new ChangeListener<PaymentMode>() {

			@Override
			public void changed(
					ObservableValue<? extends PaymentMode> observable,
					PaymentMode oldValue, PaymentMode newValue) {
				if(newValue!=null){
					if(PaymentMode.VOUCHER.equals(newValue))
						voucherCheckingView.show();
				}

			}
		});

		//		displayView.getDocNumber().textProperty().addListener(new ChangeListener<String>() {
		//			@Override
		//			public void changed(ObservableValue<? extends String> obs,
		//					String oldValue, String newValue) {
		//				// check if we have the old payment on file and update it
		//				ObservableList<PaymentItem> paymentItems = displayView.getPaymentItemDataList().getItems();				
		//				PaymentItem selectedItem = null;
		//				for (PaymentItem paymentItem : paymentItems) {
		//					if(paymentItem.getPaymentMode().equals(displayView.getPaymentMode())){
		//						paymentItem.setDocumentNumber(displayView.getDocNumber().getText());
		//						selectedItem = paymentItem;
		//					}
		//				}
		//				if(selectedItem!=null){
		//					if(PaymentMode.VOUCHER.equals(selectedItem.getPaymentMode())){
		//						// load voucher from the database.
		//						CustomerVoucher customerVoucher = new CustomerVoucher();
		//						customerVoucher.setVoucherNumber(selectedItem.getDocumentNumber());
		//						CustomerVoucherSearchInput searchInputs = new CustomerVoucherSearchInput();
		//						searchInputs.setEntity(customerVoucher);
		//						searchInputs.getFieldNames().add("voucherNumber");
		//						customerVoucherSearchService.setSearchInputs(searchInputs).start();
		//					}
		//				}
		//			}
		//		});

		customerVoucherSearchService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent event) {
				CustomerVoucherSearchService s = (CustomerVoucherSearchService) event.getSource();
				CustomerVoucherSearchResult result = s.getValue();
				event.consume();
				s.reset();
				if(result.getResultList().isEmpty()){
					Dialogs.create().message("Les Informations Fournies Sont incorrecte").showError();
				} else {
					CustomerVoucher customerVoucher = result.getResultList().iterator().next();
					displayView.getReceivedAmount().setNumber(customerVoucher.getRestAmount());
					ObservableList<PaymentItem> paymentItems = displayView.getPaymentItemDataList().getItems();				
					PaymentItem expectedItem = null;
					for (PaymentItem paymentItem : paymentItems) {
						if(paymentItem.getPaymentMode().equals(PaymentMode.VOUCHER)){
							expectedItem = paymentItem;
							break;
						}
					}
					CustomerVoucherCustomer customer = customerVoucher.getCustomer();
					PaymentItemPaidBy payer = new PaymentItemPaidBy();
					PropertyReader.copy(customer, payer);
					expectedItem.setPaidBy(payer);
					expectedItem.setDocumentNumber(customerVoucher.getVoucherNumber());
				}
				resetPaymentModeTocash();
			}
		});
		customerVoucherSearchService.setOnFailed(serviceFailedHandler);
		
		displayView.getReceivedAmount().setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				KeyCode code = event.getCode();
				if(KeyCode.SPACE.equals(code))
					handlePayment();
				
			}
		});
	}

	public void handleCashDrawerCreateService(){
		cashDrawerCreateService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				CashDrawerCreateService s = (CashDrawerCreateService) event.getSource();
				CashDrawer cd = s.getValue();
				event.consume();
				s.reset();
				PropertyReader.copy(cd, displayedEntity);

			}
		});
		cashDrawerCreateService.setOnFailed(cashDrawerCreateServiceFailedHandler);
		cashDrawerCreateServiceFailedHandler.setErrorDisplay(new ErrorDisplay()
		{
			@Override
			protected void showError(Throwable exception)
			{
				String message = exception.getMessage();
				errorMessageDialog.getTitleText().setText(resourceBundle.getString("Entity_general_error.title"));
				if (!StringUtils.isBlank(message))
					errorMessageDialog.getDetailText().setText(message);
				errorMessageDialog.display();
			}
		});
	}

	public void handleCashDrawerLoadService(){

		cashDrawerLoadService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				CashDrawerLoadOpenService s = (CashDrawerLoadOpenService) event.getSource();
				CashDrawerSearchResult result = s.getValue();
				event.consume();
				s.reset();
				List<CashDrawer> resultList = result.getResultList();
				if(resultList==null || resultList.isEmpty()){
					//					// Display error to user.
					//					noCashDrawerErrorMessageDialog.getTitleText().setText(
					//							resourceBundle.getString("CashDrawer_no_opened_for_user.title"));
					//					noCashDrawerErrorMessageDialog.getDetailText().setText(resourceBundle.getString("CashDrawer_no_opened_for_user.text"));
					//					noCashDrawerErrorMessageDialog.display();
					Action showConfirm = Dialogs.create().title(resourceBundle.getString("CashDrawer_no_opened_for_user.title"))
							.message(resourceBundle.getString("CashDrawer_no_opened_for_user.text")).showConfirm();
					if(Dialog.Actions.YES.equals(showConfirm)){
						createCashDrawer();
					}else {
						closeOpenTabRequestEvent.fire(new Object());
					}


				} else {
					CashDrawer cashDrawer = resultList.iterator().next();
					PropertyReader.copy(cashDrawer, displayedEntity);
				}

			}
		});
		//		noCashDrawerErrorMessageDialog.getOkButton().setOnAction(
		//				new EventHandler<ActionEvent>()
		//				{
		//					@Override
		//					public void handle(ActionEvent event)
		//					{
		//						noCashDrawerErrorMessageDialog.closeDialog();
		//						createCashDrawer();
		//					}
		//				});

		cashDrawerLoadService.setOnFailed(cashDrawerLoadServiceFailedHandler);
		cashDrawerLoadServiceFailedHandler.setErrorDisplay(new ErrorDisplay()
		{
			@Override
			protected void showError(Throwable exception)
			{
				String message = exception.getMessage();
				errorMessageDialog.getTitleText().setText(resourceBundle.getString("Entity_general_error.title"));
				if (!StringUtils.isBlank(message))
					errorMessageDialog.getDetailText().setText(message);
				errorMessageDialog.display();
			}
		});
	}

	public void handleReceivedAmountChanged(){
		displayView.getReceivedAmount().numberProperty().addListener(new ChangeListener<BigDecimal>() {
			@Override
			public void changed(ObservableValue<? extends BigDecimal> obs,BigDecimal oldValue, BigDecimal newValue) {
				PaymentItem paymentItem = getSelectedPaymentItem();
				BigDecimal amount = displayView.getAmount().getNumber();
				amount = amount.subtract(getPayAmount());
				if(paymentItem!=null)
					amount = amount.add(paymentItem.getAmount());

				BigDecimal paidAmount = null;
			
				if(newValue.compareTo(amount)>=0) {
					displayView.getDifference().setNumber(newValue.subtract(amount));
					paidAmount = amount;
				} else {
					displayView.getDifference().setNumber(BigDecimal.ZERO);
					paidAmount =  newValue;
				}

				if(paymentItem!=null){
					paymentItem.setReceivedAmount(newValue);
					paymentItem.setAmount(paidAmount);
					if(BigDecimal.ZERO.compareTo(paidAmount)>0)
						displayView.getPaymentItemDataList().getItems().remove(paymentItem);
				} else {
					if(BigDecimal.ZERO.compareTo(paidAmount)>0) return;
					paymentItem = new PaymentItem();
					paymentItem.setAmount(paidAmount);
					paymentItem.setPaymentMode(displayView.getPaymentMode().getValue());
					paymentItem.setReceivedAmount(displayView.getReceivedAmount().getNumber());
					paymentItem.setDocumentNumber(displayView.getDocNumber().getText());
					PaymentItemPaidBy paidBy = new PaymentItemPaidBy();
					PropertyReader.copy(proccessingOrder.getCustomer(), paidBy);
					paymentItem.setPaidBy(paidBy);
					displayView.getPaymentItemDataList().getItems().add(paymentItem);
					int size = displayView.getPaymentItemDataList().getItems().size();
					boolean contains = displayView.getPaymentItemDataList().getItems().contains(paymentItem);
					int size2 = paymentManager.getPayment().getPaymentItems().size();
					if(size==size2){
						size++;
					}
				}
			}
		});

	}

	private PaymentItem getSelectedPaymentItem(){
		ObservableList<PaymentItem> paymentItems = displayView.getPaymentItemDataList().getItems();				
		for (PaymentItem paymentItem : paymentItems) {
			if(paymentItem.getPaymentMode().equals(displayView.getPaymentMode().getValue()))
				return paymentItem;
		}
		return null;
	}

	private void handlePaymentModeChanged() {

		displayView.getPaymentMode().valueProperty().addListener(new ChangeListener<PaymentMode>() {

			@Override
			public void changed(ObservableValue<? extends PaymentMode> obs,
					PaymentMode oldValue, PaymentMode newValue) {

				//				// Whatever comes here, first compute the amount to pay.
				//				BigDecimal amount = displayView.getAmount().getNumber();
				//				
				//				BigDecimal paid = getPayAmount();
				//
				//				PaymentItem selectedItem = getSelectedPaymentItem();
				////				ObservableList<PaymentItem> paymentItems = displayView.getPaymentItemDataList().getItems();				
				////				for (PaymentItem paymentItem : paymentItems) {
				////					paid = paid.add(paymentItem.getReceivedAmount());
				////				}
				//				if(amount.compareTo(paid)>=0){
				//					if(selectedItem!=null){
				//						displayView.getAmount().setNumber(amount.subtract(paid).add(selectedItem.getReceivedAmount()));
				//						displayView.getReceivedAmount().setNumber(selectedItem.getReceivedAmount());
				//						displayView.getDocNumber().setText(selectedItem.getDocumentNumber());
				//						displayView.getDifference().setNumber(BigDecimal.ZERO);
				//					} else {
				//						displayView.getAmount().setNumber(amount.subtract(paid));
				//						displayView.getReceivedAmount().setNumber(BigDecimal.ZERO);
				//						displayView.getDocNumber().setText("");
				//						displayView.getDifference().setNumber(BigDecimal.ZERO);
				//					}
				//				}else {
				//					Dialogs.create().message("impossible to add annother payement !").showInformation();
				//				}
				if(newValue.equals(PaymentMode.VOUCHER)){
					displayView.getPaymentMode().setEditable(false);
					displayView.getDocNumber().requestFocus();
				}
			}
		});
	}





	private void handlePaymentCreateService(){
		paymentCreateService.setOnFailed(paymentCreateServiceFailedHandler);
		paymentCreateServiceFailedHandler.setErrorDisplay(new ErrorDisplay()
		{
			@Override
			protected void showError(Throwable exception)
			{
				String message = exception.getMessage();
				errorMessageDialog.getTitleText().setText(resourceBundle.getString("Entity_general_error.title"));
				if (!StringUtils.isBlank(message))
					errorMessageDialog.getDetailText().setText(message);
				errorMessageDialog.display();
			}
		});
		/*
		 * handle  payement Action action
		 */
		displayView.getCashButon().setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				handlePayment();
			}
		});
		
		displayView.getCashButon().setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				KeyCode code = event.getCode();
				if(code== KeyCode.ENTER){
					handlePayment();
				}

			}
		});
		
		salesOrderPaymentChecker.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent event) {
				SalesOrderSearchService s = (SalesOrderSearchService) event.getSource();
				SalesOrderSearchResult result = s.getValue();
				event.consume();
				s.reset();
				if(!result.getResultList().isEmpty()){
					SalesOrder salesOrder = result.getResultList().iterator().next();
					if(!salesOrder.getCashed()){
						doPayement();
					}else {
						Dialogs.create().message("Cette Commande a deja ete encaissee par Une autre caisse ou par vous !").showError();
						handleSalesOrderSearchEvent();
					}
				}

			}
		});
		
		salesOrderPaymentChecker.setOnFailed(serviceFailedHandler);

		paymentCreateService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent event) {
				PaymentCreateService s = (PaymentCreateService) event.getSource();
				Payment payment = s.getValue();
				PaymentId paymentId = new PaymentId(payment.getId());
				event.consume();
				s.reset();
				
				displayView.getInvoicesDataList().getItems().remove(proccessingOrder);
				PropertyReader.copy(new Payment(), payment);
				PropertyReader.copy(new SalesOrder(), proccessingOrder);
				proccessingOrder.clearInvoices();
				deactivate();
				// Print receipt here.
				printPaymentReceiptRequestedEvent.fire(paymentId);

			}
		});
	}
	
	public void doPayement(){
		BigDecimal payAmount = getPayAmount();
		SalesOrderRestToPay restToPay = new SalesOrderRestToPay(proccessingOrder);
		BigDecimal customerRestTopay = restToPay.getCustomerRestToPay();
		if(customerRestTopay.compareTo(payAmount)<=0){
			processPayment();
		}else {
			Dialogs.create().message("le montant a payer Doit etre egal a "+ customerRestTopay).showInformation();
		}
	}
	
	public void handlePayment(){
		SalesOrderSearchInput salesOrderSearchInput = new SalesOrderSearchInput();
		salesOrderSearchInput.setEntity(proccessingOrder);
		salesOrderSearchInput.getFieldNames().add("id");
		salesOrderSearchInput.getFieldNames().add("soNumber");
		salesOrderPaymentChecker.setSearchInputs(salesOrderSearchInput).start();
	}
	public void processPayment(){
		Payment payment = paymentManager.getPayment();
		payment.setCashDrawer(new PaymentCashDrawer(displayedEntity));
		List<CustomerInvoice> invoices2 = proccessingOrder.readInvoices();
		for (CustomerInvoice customerInvoice : invoices2) {
			PaymentCustomerInvoiceAssoc paymentCustomerInvoiceAssoc = new PaymentCustomerInvoiceAssoc();
			paymentCustomerInvoiceAssoc.setSource(payment);
			paymentCustomerInvoiceAssoc.setTarget(customerInvoice);
			payment.addToInvoices(paymentCustomerInvoiceAssoc);
		}

		ObservableList<PaymentItem> list = displayView.getPaymentItemDataList().getItems();

		ArrayList<PaymentItem> arrayList = new ArrayList<PaymentItem>(list);

		BigDecimal receivedAmount2 = BigDecimal.ZERO;
		BigDecimal amount2 = BigDecimal.ZERO;
		for (PaymentItem paymentItem : arrayList) {
			if(paymentItem.getAmount().compareTo(BigDecimal.ZERO)<0) 
				displayView.getPaymentItemDataList().getItems().remove(paymentItem);
			receivedAmount2 = receivedAmount2.add(paymentItem.getReceivedAmount());
			amount2 = amount2.add(paymentItem.getAmount());
		}
		payment.setAmount(amount2);
		payment.setReceivedAmount(receivedAmount2);
		if(displayView.getPaymentItemDataList().getItems().isEmpty()){
			PaymentItem paymentItem = new PaymentItem();
			paymentItem.setAmount(BigDecimal.ZERO);
			paymentItem.setPaymentMode(PaymentMode.CASH);
			paymentItem.setReceivedAmount(BigDecimal.ZERO);
			paymentItem.setDocumentNumber("");
			PaymentItemPaidBy paymentItemPaidBy = new PaymentItemPaidBy();
			PropertyReader.copy(proccessingOrder.getCustomer(), paymentItemPaidBy);
			paymentItem.setPaidBy(paymentItemPaidBy);
			displayView.getPaymentItemDataList().getItems().add(paymentItem);
		}
		paymentCreateService.setModel(payment).start();

	}
	private void deactivate(){
		displayView.getAmount().setNumber(BigDecimal.ZERO);
		displayView.getAmount().setDisable(true);
		displayView.getDifference().setNumber(BigDecimal.ZERO);
		displayView.getDifference().setDisable(true);
		displayView.getDocNumber().setText("");
		displayView.getDocNumber().setDisable(true);
		displayView.getInvoiceItemDataList().getItems().clear();
		displayView.getInvoiceItemDataList().setDisable(true);
		displayView.getPaymentMode().setDisable(true);
		displayView.getReceivedAmount().setNumber(BigDecimal.ZERO);
		displayView.getReceivedAmount().setDisable(true);

		displayView.getPaymentItemDataList().getItems().clear();
		// print receipt???
		displayView.getCashButon().setDisable(true);
//		displayView.getCashOutButton().setDisable(true);
	}

	public void clearPaymentDataList(){
		displayView.getPaymentItemDataList().getItems().clear();
		displayView.getReceivedAmount().setNumber(BigDecimal.ZERO);
		displayView.getPaymentMode().setValue(PaymentMode.CASH);
		displayView.getDocNumber().setText(null);
		displayView.getDifference().setNumber(BigDecimal.ZERO);
		paymentManager.newPayment();
	}

	private void activate(){
		displayView.getAmount().setDisable(false);
		displayView.getDifference().setDisable(false);
		displayView.getDocNumber().setDisable(false);
		displayView.getInvoiceItemDataList().setDisable(false);
		displayView.getPaymentMode().setDisable(false);
		displayView.getReceivedAmount().setDisable(false);
		// print receipt???
		displayView.getCashButon().setDisable(false);
		displayView.getCashOutButton().setDisable(false);

	}                                                                                                                                 
	public void reset() {
		PropertyReader.copy(new CashDrawer(), displayedEntity);
	}

	public void resetPaymentModeTocash(){
		displayView.getPaymentMode().setValue(PaymentMode.CASH);
		displayView.getReceivedAmount().requestFocus();
		displayView.getReceivedAmount().setNumber(BigDecimal.ZERO);
	}


	public BigDecimal getPayAmount(){
		BigDecimal payAmount = BigDecimal.ZERO;
		Iterator<PaymentItem> iterator = displayView.getPaymentItemDataList().getItems().iterator();
		while (iterator.hasNext()) {
			PaymentItem paymentItem = (PaymentItem) iterator.next();
			BigDecimal amount = paymentItem.getAmount();
			if(amount!=null)
				payAmount = payAmount.add(amount);

		}
		return payAmount ;
	}


	public CashDrawerDisplayView getDisplayView() {
		return displayView;
	}
}