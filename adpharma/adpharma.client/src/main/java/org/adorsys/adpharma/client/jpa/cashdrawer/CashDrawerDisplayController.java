package org.adorsys.adpharma.client.jpa.cashdrawer;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
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
import org.adorsys.adpharma.client.jpa.login.Login;
import org.adorsys.adpharma.client.jpa.payment.Payment;
import org.adorsys.adpharma.client.jpa.payment.PaymentCreateService;
import org.adorsys.adpharma.client.jpa.payment.PaymentCustomerService;
import org.adorsys.adpharma.client.jpa.payment.PaymentPaidBy;
import org.adorsys.adpharma.client.jpa.paymentcustomerinvoiceassoc.PaymentCustomerInvoiceAssocService;
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

	@Inject
	private PaymentCustomerService paymentCustomerService;
	
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
	private Payment payment ;

	@Inject
	private CustomerInvoice proccessingInvoice;


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
		 */
		displayView.getReceivedAmount().setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				calculateDifference();
			}
		});



		/*
		 * handle open cash drawer action
		 */
		displayView.getOpenCashDrawerButton().setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				BigDecimal cashDrawerInitialAmount = getCashDrawerInitialAmount();
				CashDrawer cashDrawer = new CashDrawer();
				cashDrawer.setInitialAmount(cashDrawerInitialAmount);
				cashDrawerCreateService.setModel(cashDrawer).start();

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
				CashDrawer cd = s.getValue();
				event.consume();
				s.reset();
				if(cd !=null)PropertyReader.copy(cd, displayedEntity);

			}
		});
		cashDrawerLoadOpenService.setOnFailed(serviceCallFailedEventHandler);

		paymentCustomerService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				PaymentCustomerService s = (PaymentCustomerService) event.getSource();
				Payment pay = s.getValue();
				event.consume();
				s.reset();
				PropertyReader.copy(new Payment(), payment);
				PropertyReader.copy(new CustomerInvoice(), proccessingInvoice);
				handleCustomerInvoiceSearchEvent();
			}
		});
		paymentCustomerService.setOnFailed(serviceCallFailedEventHandler);
		
		paymentCreateService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				PaymentCreateService s = (PaymentCreateService) event.getSource();
				Payment pay = s.getValue();
				event.consume();
				s.reset();
				PropertyReader.copy(pay, payment);
				ArrayList<CustomerInvoice> invoices = new ArrayList<CustomerInvoice>();
				invoices.add(proccessingInvoice);
				paymentCustomerService.setEntityId(pay.getId()).setInvoices(invoices).start();
				
			}
		});
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



		//		      displayView.getEditButton().disableProperty().bind(registration.canEditProperty().not());
		//		      displayView.getRemoveButton().disableProperty().bind(registration.canEditProperty().not());

		

		//		      displayView.getEditButton().setOnAction(new EventHandler<ActionEvent>()
		//		      {
		//		         @Override
		//		         public void handle(ActionEvent e)
		//		         {
		//		            editRequestEvent.fire(displayedEntity);
		//		         }
		//		      });
		//		
		//		      displayView.getRemoveButton().setOnAction(new EventHandler<ActionEvent>()
		//		      {
		//		         @Override
		//		         public void handle(ActionEvent e)
		//		         {
		//		            removeRequest.fire(displayedEntity);
		//		         }
		//		      });
		//		
		//		      displayView.getConfirmSelectionButton().setOnAction(new EventHandler<ActionEvent>()
		//		      {
		//		         @Override
		//		         public void handle(ActionEvent e)
		//		         {
		//		            final AssocSelectionEventData<CashDrawer> pendingSelectionRequest = pendingSelectionRequestProperty.get();
		//		            if (pendingSelectionRequest == null)
		//		               return;
		//		            pendingSelectionRequestProperty.set(null);
		//		            pendingSelectionRequest.setTargetEntity(displayedEntity);
		//		            selectionResponseEvent.fire(pendingSelectionRequest);
		//		         }
		//		      });

		//		      displayView.getConfirmSelectionButton().visibleProperty().bind(pendingSelectionRequestProperty.isNotNull());
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

	private void calculateDifference()
	{
		BigDecimal amount = payment.getAmount();
		BigDecimal diff = BigDecimal.ZERO;
		BigDecimal receive = BigDecimal.ZERO;
		String stringValue = displayView.getReceivedAmount().getText();
		try {
			BigDecimal number = new BigDecimal(stringValue);
			receive = number;
			diff = receive.subtract(amount);
			
		} catch (Exception e) {
			
		} 
		payment.setDifference(diff);
	}

	public void handleCustomerInvoiceSearchEvent(){
		CustomerInvoiceSearchInput csi = new CustomerInvoiceSearchInput();
		csi.getEntity().setCashed(Boolean.FALSE);
		csi.getFieldNames().add("cashed");
		csi.setMax(-1);
		customerInvoiceSearchService.setSearchInputs(csi).start();
	}

	private boolean isValidCustomerPayement(){
		BigDecimal receivedAmount2 = payment.getReceivedAmount();
		BigDecimal amount2 = payment.getAmount();
		return (receivedAmount2.compareTo(amount2)==1 || receivedAmount2.compareTo(amount2)==0);
	}

	public void loadOpenCashDrawer(){
		cashDrawerLoadOpenService.start();
	}

}