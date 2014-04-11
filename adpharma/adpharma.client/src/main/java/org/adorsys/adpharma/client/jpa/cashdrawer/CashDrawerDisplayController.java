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
import org.adorsys.adpharma.client.jpa.customervoucher.CustomerVoucher;
import org.adorsys.adpharma.client.jpa.customervoucher.CustomerVoucherCustomer;
import org.adorsys.adpharma.client.jpa.customervoucher.CustomerVoucherSearchInput;
import org.adorsys.adpharma.client.jpa.customervoucher.CustomerVoucherSearchResult;
import org.adorsys.adpharma.client.jpa.customervoucher.CustomerVoucherSearchService;
import org.adorsys.adpharma.client.jpa.payment.Payment;
import org.adorsys.adpharma.client.jpa.payment.PaymentCashDrawer;
import org.adorsys.adpharma.client.jpa.payment.PaymentCreateService;
import org.adorsys.adpharma.client.jpa.paymentcustomerinvoiceassoc.PaymentCustomerInvoiceAssoc;
import org.adorsys.adpharma.client.jpa.paymentitem.PaymentItem;
import org.adorsys.adpharma.client.jpa.paymentitem.PaymentItemPaidBy;
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
import org.apache.commons.lang3.StringUtils;
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
	private ServiceCallFailedEventHandler customerInvoiceSearchServiceFailedHandler;
	
	@Inject
	@PermissionsEvent
	private Event<DomainComponent> permissionEvent;

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
	private CustomerInvoiceItemSearchService customerInvoiceItemSearchService ;
	@Inject
	private ServiceCallFailedEventHandler customerInvoiceItemSearchServiceFailedHandler;

	@Inject 
	private PaymentCreateService paymentCreateService;
	@Inject
	private ServiceCallFailedEventHandler paymentCreateServiceFailedHandler;
	


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

	private final PaymentManager paymentManager = new PaymentManager();

	@Inject
	private CustomerInvoice proccessingInvoice;


	@Inject
	private CashDrawerRegistration registration;

   @Inject
   private CustomerVoucherSearchService customerVoucherSearchService;
   @Inject
   private ServiceCallFailedEventHandler customerVoucherSearchFailedEventHandler;
   @Inject
   private ErrorMessageDialog customerVoucherErrorMessageDialog;
   @Inject
   private ErrorMessageDialog errorMessageDialog;
	
	@PostConstruct
	public void postConstruct()
	{

		displayView.getOpenCashDrawerButton().disableProperty().bind(registration.canCreateProperty().not());
		displayView.getCloseCashDrawerButton().disableProperty().bind(registration.canEditProperty().not());
		displayView.bindInvoice(proccessingInvoice);
//		paymentManager.getPayment().paymentItemsProperty().bind(displayView.getPaymentItemDataList().itemsProperty());
		displayView.bindPayment(paymentManager.getPayment());

		processDocumentNumberChanged();
		handleCashDrawerCreateService();
		handleCashDrawerLoadService();
		handleReceivedAmountChanged();
		handlePaymentModeChanged();
		handlePaymentCreateService();

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

					CustomerInvoiceItemSearchInput ciisi = new CustomerInvoiceItemSearchInput();
					ciisi.getEntity().setInvoice(new CustomerInvoiceItemInvoice(selectedItem));
					ciisi.getFieldNames().add("invoice");
					ciisi.setMax(-1);
					customerInvoiceItemSearchService.setSearchInputs(ciisi).start();
					
					activate();
				}

			}
		});

		cashDrawerCloseService.setOnFailed(cashDrawerCloseServiceFailedHandler);

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
		customerInvoiceSearchService.setOnFailed(customerInvoiceSearchServiceFailedHandler);

		customerInvoiceItemSearchService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				CustomerInvoiceItemSearchService s = (CustomerInvoiceItemSearchService) event.getSource();
				CustomerInvoiceItemSearchResult searchResult = s.getValue();
				event.consume();
				s.reset();
				List<CustomerInvoiceItem> resultList = searchResult.getResultList();
				proccessingInvoice.setInvoiceItems(resultList);
				displayView.getAmount().setNumber(proccessingInvoice.getCustomerRestTopay());
			}
		});
		customerInvoiceItemSearchService.setOnFailed(customerInvoiceItemSearchServiceFailedHandler);

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

	public void handleCustomerInvoiceSearchEvent(){
		CustomerInvoiceSearchInput csi = new CustomerInvoiceSearchInput();
		csi.getEntity().setCashed(Boolean.FALSE);
		csi.getFieldNames().add("cashed");
		csi.setMax(-1);
		customerInvoiceSearchService.setSearchInputs(csi).start();
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
	
	private void processDocumentNumberChanged(){
		displayView.getDocNumber().textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> obs,
					String oldValue, String newValue) {
				// check is we have the old payment on file and update it
				ObservableList<PaymentItem> paymentItems = displayView.getPaymentItemDataList().getItems();				
				PaymentItem selectedItem = null;
				for (PaymentItem paymentItem : paymentItems) {
					if(paymentItem.getPaymentMode().equals(displayView.getPaymentMode())){
						paymentItem.setDocumentNumber(displayView.getDocNumber().getText());
						selectedItem = paymentItem;
					}
				}
				if(PaymentMode.VOUCHER.equals(selectedItem.getPaymentMode())){
					// load voucher from the database.
					CustomerVoucher customerVoucher = new CustomerVoucher();
					customerVoucher.setVoucherNumber(selectedItem.getDocumentNumber());
					CustomerVoucherSearchInput searchInputs = new CustomerVoucherSearchInput();
					searchInputs.setEntity(customerVoucher);
					searchInputs.getFieldNames().add("voucherNumber");
					customerVoucherSearchService.setSearchInputs(searchInputs).start();
				}
			}
		});

		customerVoucherSearchService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent event) {
				CustomerVoucherSearchService s = (CustomerVoucherSearchService) event.getSource();
				CustomerVoucherSearchResult result = s.getValue();
				event.consume();
				s.reset();
				if(result.getResultList().isEmpty()){
					// reset and show error.
					// Display error to user.
					customerVoucherErrorMessageDialog.getTitleText().setText(
		                       resourceBundle.getString("Entity_search_error.title"));
					customerVoucherErrorMessageDialog.getDetailText().setText("Customer voucher with Number: " + displayView.getDocNumber().getText() + " not found");
					customerVoucherErrorMessageDialog.display();
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
				}
			}
		});
		customerVoucherErrorMessageDialog.getOkButton().setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				customerVoucherErrorMessageDialog.closeDialog();
			}
		});
		customerVoucherSearchService.setOnFailed(customerVoucherSearchFailedEventHandler);
		customerVoucherSearchFailedEventHandler.setErrorDisplay(new ErrorDisplay()
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
	
	public void handleCashDrawerCreateService(){
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
				BigDecimal paidAmount = null;
				if(newValue.compareTo(amount)>=0){
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
					PropertyReader.copy(proccessingInvoice.getCustomer(), paidBy);
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
				
				// Whatever comes here, first compute the amount to pay.
				BigDecimal amount = displayView.getAmount().getNumber();
				BigDecimal paid = BigDecimal.ZERO;
				
				PaymentItem selectedItem = getSelectedPaymentItem();
				ObservableList<PaymentItem> paymentItems = displayView.getPaymentItemDataList().getItems();				
				for (PaymentItem paymentItem : paymentItems) {
					paid = paid.add(paymentItem.getReceivedAmount());
				}				
				if(selectedItem!=null){
					displayView.getAmount().setNumber(amount.subtract(paid).add(selectedItem.getReceivedAmount()));
					displayView.getReceivedAmount().setNumber(selectedItem.getReceivedAmount());
					displayView.getDocNumber().setText(selectedItem.getDocumentNumber());
					displayView.getDifference().setNumber(BigDecimal.ZERO);
				} else {
					displayView.getAmount().setNumber(amount.subtract(paid));
					displayView.getReceivedAmount().setNumber(BigDecimal.ZERO);
					displayView.getDocNumber().setText("");
					displayView.getDifference().setNumber(BigDecimal.ZERO);
				}
				if(newValue.equals(PaymentMode.VOUCHER)){
					displayView.getPaymentMode().setEditable(false);
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
				Payment payment = paymentManager.getPayment();
				payment.setCashDrawer(new PaymentCashDrawer(displayedEntity));
				PaymentCustomerInvoiceAssoc paymentCustomerInvoiceAssoc = new PaymentCustomerInvoiceAssoc();
				paymentCustomerInvoiceAssoc.setSource(payment);
				paymentCustomerInvoiceAssoc.setTarget(proccessingInvoice);
				payment.addToInvoices(paymentCustomerInvoiceAssoc);
				int size3 = payment.getInvoices().size();
				
				ObservableList<PaymentItem> list = displayView.getPaymentItemDataList().getItems();
				int size = list.size();
				ArrayList<PaymentItem> arrayList = new ArrayList<PaymentItem>(list);
				int size2 = arrayList.size();
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
					displayView.getPaymentItemDataList().getItems().add(paymentItem);
				}
				int size4 = payment.getInvoices().size();
				paymentCreateService.setModel(payment).start();

			}
		});
		
		paymentCreateService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent event) {
				PaymentCreateService s = (PaymentCreateService) event.getSource();
				Payment payment = s.getValue();
				event.consume();
				s.reset();
				
				PropertyReader.copy(new Payment(), payment);
				PropertyReader.copy(new CustomerInvoice(), proccessingInvoice);
				
				deactivate();
			}
		});
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
		// print receipt
		displayView.getCashButon().setDisable(true);
		displayView.getCashOutButton().setDisable(true);
	}
	
	private void activate(){
		displayView.getAmount().setDisable(false);
		displayView.getDifference().setDisable(false);
		displayView.getDocNumber().setDisable(false);
		displayView.getInvoiceItemDataList().setDisable(false);
		displayView.getPaymentMode().setDisable(false);
		displayView.getReceivedAmount().setDisable(false);
		// print receipt
		displayView.getCashButon().setDisable(false);
		displayView.getCashOutButton().setDisable(false);
	}
}