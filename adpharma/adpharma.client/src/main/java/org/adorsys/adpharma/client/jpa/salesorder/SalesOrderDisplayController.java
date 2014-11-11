package org.adorsys.adpharma.client.jpa.salesorder;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
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
import javax.validation.ConstraintViolation;

import org.adorsys.adpharma.client.KeyCodeGenerator;
import org.adorsys.adpharma.client.access.SecurityUtil;
import org.adorsys.adpharma.client.events.PrintCustomerInvoiceRequestedEvent;
import org.adorsys.adpharma.client.events.PrintCustomerVoucherRequestEvent;
import org.adorsys.adpharma.client.events.SalesOrderId;
import org.adorsys.adpharma.client.jpa.accessroleenum.AccessRoleEnum;
import org.adorsys.adpharma.client.jpa.article.ArticleSearchInput;
import org.adorsys.adpharma.client.jpa.articlelot.ArticleLot;
import org.adorsys.adpharma.client.jpa.articlelot.ArticleLotArticle;
import org.adorsys.adpharma.client.jpa.articlelot.ArticleLotSearchInput;
import org.adorsys.adpharma.client.jpa.articlelot.ArticleLotService;
import org.adorsys.adpharma.client.jpa.articlelot.ArticleLotVat;
import org.adorsys.adpharma.client.jpa.cashdrawer.CashDrawer;
import org.adorsys.adpharma.client.jpa.cashdrawer.CashDrawerAgency;
import org.adorsys.adpharma.client.jpa.cashdrawer.CashDrawerSearchInput;
import org.adorsys.adpharma.client.jpa.cashdrawer.CashDrawerSearchResult;
import org.adorsys.adpharma.client.jpa.cashdrawer.CashDrawerSearchService;
import org.adorsys.adpharma.client.jpa.clearanceconfig.ClearanceConfig;
import org.adorsys.adpharma.client.jpa.customer.Customer;
import org.adorsys.adpharma.client.jpa.customer.CustomerSearchInput;
import org.adorsys.adpharma.client.jpa.customerinvoice.ReceiptPrintProperties;
import org.adorsys.adpharma.client.jpa.insurrance.Insurrance;
import org.adorsys.adpharma.client.jpa.insurrance.InsurranceCustomer;
import org.adorsys.adpharma.client.jpa.insurrance.InsurranceSearchInput;
import org.adorsys.adpharma.client.jpa.insurrance.InsurranceSearchResult;
import org.adorsys.adpharma.client.jpa.insurrance.InsurranceSearchService;
import org.adorsys.adpharma.client.jpa.login.Login;
import org.adorsys.adpharma.client.jpa.login.LoginSearchInput;
import org.adorsys.adpharma.client.jpa.login.LoginSearchResult;
import org.adorsys.adpharma.client.jpa.login.LoginService;
import org.adorsys.adpharma.client.jpa.prescriptionbook.PrescriptionBook;
import org.adorsys.adpharma.client.jpa.prescriptionbook.PrescriptionBookAgency;
import org.adorsys.adpharma.client.jpa.prescriptionbook.PrescriptionBookRecordingAgent;
import org.adorsys.adpharma.client.jpa.prescriptionbook.PrescriptionBookSalesOrder;
import org.adorsys.adpharma.client.jpa.prescriptionbook.PrescriptionBookSearchInput;
import org.adorsys.adpharma.client.jpa.prescriptionbook.PrescriptionBookSearchResult;
import org.adorsys.adpharma.client.jpa.prescriptionbook.PrescriptionBookSearchService;
import org.adorsys.adpharma.client.jpa.salesorderitem.SalesOrderItem;
import org.adorsys.adpharma.client.jpa.salesorderitem.SalesOrderItemArticle;
import org.adorsys.adpharma.client.jpa.salesorderitem.SalesOrderItemCreateService;
import org.adorsys.adpharma.client.jpa.salesorderitem.SalesOrderItemEditService;
import org.adorsys.adpharma.client.jpa.salesorderitem.SalesOrderItemRemoveService;
import org.adorsys.adpharma.client.jpa.salesorderitem.SalesOrderItemSalesOrder;
import org.adorsys.adpharma.client.jpa.salesorderitem.SalesOrderItemVat;
import org.adorsys.adpharma.client.jpa.vat.VATSearchService;
import org.adorsys.adpharma.client.utils.SalesKeyReciever;
import org.adorsys.javafx.crud.extensions.EntityController;
import org.adorsys.javafx.crud.extensions.ViewType;
import org.adorsys.javafx.crud.extensions.events.AssocSelectionEventData;
import org.adorsys.javafx.crud.extensions.events.AssocSelectionRequestEvent;
import org.adorsys.javafx.crud.extensions.events.ComponentSelectionRequestData;
import org.adorsys.javafx.crud.extensions.events.ComponentSelectionRequestEvent;
import org.adorsys.javafx.crud.extensions.events.EntityCreateRequestedEvent;
import org.adorsys.javafx.crud.extensions.events.EntitySearchRequestedEvent;
import org.adorsys.javafx.crud.extensions.events.EntitySelectionEvent;
import org.adorsys.javafx.crud.extensions.events.ModalEntityCreateDoneEvent;
import org.adorsys.javafx.crud.extensions.events.ModalEntityCreateRequestedEvent;
import org.adorsys.javafx.crud.extensions.events.ModalEntitySearchDoneEvent;
import org.adorsys.javafx.crud.extensions.events.ModalEntitySearchRequestedEvent;
import org.adorsys.javafx.crud.extensions.events.SelectedModelEvent;
import org.adorsys.javafx.crud.extensions.login.ErrorDisplay;
import org.adorsys.javafx.crud.extensions.login.LoginSucceededEvent;
import org.adorsys.javafx.crud.extensions.login.LogoutSucceededEvent;
import org.adorsys.javafx.crud.extensions.login.RolesEvent;
import org.adorsys.javafx.crud.extensions.login.ServiceCallFailedEventHandler;
import org.adorsys.javafx.crud.extensions.login.WorkingInformationEvent;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;
import org.apache.commons.lang3.StringUtils;
import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.Dialogs;

@Singleton
public class SalesOrderDisplayController implements EntityController
{
	@Inject
	private SalesOrderDisplayView displayView;

	private boolean  isCustomerSearch = false ;


	@Inject 
	@ModalEntitySearchRequestedEvent
	private Event<ArticleSearchInput> modalArticleSearchEvent;


	@Inject
	@ModalEntityCreateRequestedEvent
	private Event<Customer> modalCustomerCreateRequestEvent ;

	@Inject
	@ModalEntityCreateRequestedEvent
	private Event<Insurrance> modalInsurerCreateRequestEvent ;

	@Inject
	@ModalEntitySearchRequestedEvent
	private Event<CustomerSearchInput> modalCutomerSearchRequestEvent ;

	@Inject
	@ModalEntityCreateRequestedEvent
	private Event<PrescriptionBook> modalPrescriptionBookCreateRequestEvent ;

	@Inject
	@EntitySearchRequestedEvent
	private Event<SalesOrder> searchRequestedEvent;

	private ObjectProperty<AssocSelectionEventData<SalesOrder>> pendingSelectionRequestProperty = new SimpleObjectProperty<AssocSelectionEventData<SalesOrder>>();

	@Inject
	@ComponentSelectionRequestEvent
	private Event<ComponentSelectionRequestData> componentSelectionRequestEvent;

	@Inject 
	@ModalEntitySearchRequestedEvent
	private Event<ArticleLotSearchInput> modalArticleLotSearchEvent;

	//  services
	@Inject
	private VATSearchService vatSearchService;

	@Inject
	private SalesOrderReturnService orderReturnService;

	@Inject
	private SalesOrderCancelService orderCancelService;

	@Inject
	CashDrawerSearchService cashDrawerSearchService;

	@Inject
	InsurranceSearchService insurranceSearchService;

	@Inject
	private SalesOrderManagedLotService salesOrderManagedLotService ;

	@Inject
	private ServiceCallFailedEventHandler callFailedEventHandler;

	@Inject
	private ServiceCallFailedEventHandler salesOrderItemCreateFailedEventHandler;

	@Inject
	private SalesOrder displayedEntity;

	@Inject
	private SalesOrderItem salesOrderItem;

	@Inject
	private SalesOrderItemCreateService salesOrderItemCreateService;

	@Inject
	private SalesOrderItemEditService salesOrderItemEditService;

	@Inject
	private PrescriptionBookSearchService prescriptionBookSearchService;

	@Inject
	private SalesOrderItemRemoveService salesOrderItemRemoveService;

	@Inject
	private SalesOrderCloseService closeService;

	@Inject
	@EntityCreateRequestedEvent
	private Event<SalesOrder> salesOrderRequestEvent;

	@Inject 
	private SecurityUtil securityUtil;

	@Inject
	private SalesOrderRegistration registration;

	@Inject
	private InsurranceSearchInput insurranceSearchInput;

	@Inject
	@WorkingInformationEvent
	private Event<String> workingInfosEvent;

	@Inject
	@PrintCustomerInvoiceRequestedEvent
	private Event<SalesOrderId> printCustomerInvoiceRequestedEvent;

	@Inject 
	private LoginService loginService ;

	@Inject
	private SalesKeyReciever salesKeyRecieverView;

	@Inject
	@PrintCustomerVoucherRequestEvent
	private Event<SalesOrder> salesOrderVoucherPrintRequestEvent ;

	@Inject
	private ArticleLotService articleLotService ;
	
   
	
	

	@PostConstruct
	public void postConstruct()
	{

		 
		//		      displayView.getEditButton().disableProperty().bind(registration.canEditProperty().not());
		//		displayView.getRemoveButton().disableProperty().bind(registration.canEditProperty().not());

		//		bind models to the view
		displayView.bind(displayedEntity);
		displayView.bind(salesOrderItem);
//		displayView.getCloseButton().disableProperty().bind(displayedEntity.salesOrderStatusProperty().isEqualTo(DocumentProcessingState.CLOSED));
//		displayView.getClientButton().disableProperty().bind(displayedEntity.salesOrderStatusProperty().isEqualTo(DocumentProcessingState.CLOSED));
//		displayView.getInsurreurButton().disableProperty().bind(displayedEntity.salesOrderStatusProperty().isEqualTo(DocumentProcessingState.CLOSED));
//		displayView.getOrderQuantityColumn().editableProperty().bind(displayedEntity.salesOrderStatusProperty().isNotEqualTo(DocumentProcessingState.CLOSED));
//		
		salesOrderItemCreateFailedEventHandler.setErrorDisplay(new ErrorDisplay() {

			@Override
			protected void showError(Throwable exception) {
				Dialogs.create().showException(exception);
			}
		});

		displayView.getFindArticleButton().setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				ArticleSearchInput articleSearchInput = new ArticleSearchInput();
				articleSearchInput.setMax(50);
				modalArticleSearchEvent.fire(articleSearchInput);

			}
		});

		displayView.getPrintProformaButton().setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Boolean cashed = displayedEntity.getCashed();
				if(!cashed){
					SalesOrderId selectedSalesOrderId = new SalesOrderId(displayedEntity.getId());
					if(selectedSalesOrderId==null || selectedSalesOrderId.getId()==null) return;
					String customerName = null;
					if(displayedEntity!=null && "000000001".equals(displayedEntity.getCustomer().getSerialNumber()))
						customerName = Dialogs.create().message("Nom du client : ").showTextInput();
					selectedSalesOrderId.setCustomerName(customerName);
					selectedSalesOrderId.setProformat(true);
					printCustomerInvoiceRequestedEvent.fire(selectedSalesOrderId);

				}else {
					Dialogs.create().message("Impossible d'editer une proforma facture deja encaissee").showInformation();
				}

			}
		});

		displayView.getOrderQuantityColumn().setOnEditCommit(new EventHandler<CellEditEvent<SalesOrderItem,BigDecimal>>() {
			@Override
			public void handle(CellEditEvent<SalesOrderItem, BigDecimal> orderedQtyCell) {
				SalesOrderItem selectedItem = orderedQtyCell.getRowValue();
				BigDecimal newValue = orderedQtyCell.getNewValue();
				if(newValue==null){
					// reset old value.
				} else if (newValue.compareTo(BigDecimal.ZERO)<=0){
					// delete article
					salesOrderItemRemoveService.setEntity(selectedItem).start();
				} else {
					if(displayView.getOrderedQty().isEditable()){
						selectedItem.setOrderedQty(newValue);
						selectedItem.updateTotalSalesPrice();
						// update article
						salesOrderItemEditService.setSalesOrderItem(selectedItem).start();
					}else {
						orderedQtyCell.getRowValue().setOrderedQty(orderedQtyCell.getOldValue());
					}
				}
			}
		});

		/*
		 * listen to Ok button.
		 */
		displayView.getOkButton().disableProperty().bind(salesOrderItemCreateService.runningProperty());
		displayView.getOkButton().disableProperty().bind(salesOrderItemEditService.runningProperty());
		displayView.getOkButton().setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				KeyCode code = event.getCode();
				if(code== KeyCode.ENTER){
					if(isValidSalesOrderItem())
						handleAddSalesOrderItem(salesOrderItem);
				}
			}

		});


		displayView.getOrderedQty().setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				KeyCode code = event.getCode();
				if(code== KeyCode.ENTER){
					if(isValidSalesOrderItem())
						handleAddSalesOrderItem(salesOrderItem);
				}
			}
		});


		/*
		 * listen to delete menu Item.
		 */
		//
		//		displayView.getDeleteSOIMenu().setOnAction(new EventHandler<ActionEvent>() {
		//
		//			@Override
		//			public void handle(ActionEvent event) {
		//				SalesOrderItem selectedItem = displayView.getDataList().getSelectionModel().getSelectedItem();
		//				if(selectedItem!=null) {
		//					salesOrderItemRemoveService.setEntity(selectedItem).start();
		//				}
		//
		//			}
		//		});



		/*
		 * listen to edit menu Item.
		 */
		displayView.getReturnSOIMenu().setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				SalesOrderItem selectedItem = displayView.getDataList().getSelectionModel().getSelectedItem();
				if(!displayedEntity.getAlreadyReturned()){
					if(selectedItem!= null){
						BigDecimal oderedQty = selectedItem.getOrderedQty();
						BigDecimal qtyToReturn = getQtyToReturn();
						if(qtyToReturn.compareTo(oderedQty)>0){
							Dialogs.create().nativeTitleBar().message("Vous ne pouvez retourner plus de : "+oderedQty +" pour cette ligne !").showInformation();
						}else {
							selectedItem.setReturnedQty(qtyToReturn);
							selectedItem.setDeliveredQty(selectedItem.getOrderedQty().subtract(qtyToReturn));
						}
					}

				}else {
					Dialogs.create().message("cette Commande a deja fais l objet d un retour !").showInformation();
				}
			}
		});


		/*
		 * listen to cancel button .
		 */
		displayView.getCancelButton().setOnAction(
				new EventHandler<ActionEvent>()
				{
					@Override
					public void handle(ActionEvent e)
					{
						PropertyReader.copy(new SalesOrder(), displayedEntity);
						PropertyReader.copy(new SalesOrderItem(), salesOrderItem);
						searchRequestedEvent.fire(displayedEntity);
					}
				});


		
		/*
		 */
		displayView.getCloseButton().disableProperty().bind(closeService.runningProperty());
		final KeyCombination closeKeyCodeCombination = new KeyCodeCombination(KeyCode.C, KeyCombination.SHORTCUT_DOWN);
		displayView.getRootPane().addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent e) {
				if(closeKeyCodeCombination.match(e)) {
					if(!displayView.getCloseButton().isDisabled()) {
						displayView.getCloseButton().fire();
					}
				}
			}});
		
		displayView.getCloseButton().setOnAction(
				new EventHandler<ActionEvent>()
				{
					@Override
					public void handle(ActionEvent e)
					{
						String saleKey = salesKeyRecieverView.show();
						LoginSearchInput loginSearchInput = new LoginSearchInput();
						loginSearchInput.getEntity().setSaleKey(saleKey);
						loginSearchInput.getFieldNames().add("saleKey");
						LoginSearchResult loginResult = loginService.findBy(loginSearchInput);
						if(loginResult.getResultList().isEmpty()){
							Dialogs.create().message("Cle de vente Incorrecte").showError();
							return ;
						}
						Login login = loginResult.getResultList().iterator().next();
						if(isValideSale(login)){
							Set<ConstraintViolation<SalesOrder>> violations = displayView.validate(displayedEntity);
							if (violations.isEmpty())
							{
								if(Dialog.Actions.OK.equals(salesKeyRecieverView.getUserAction())){
									displayedEntity.setSalesKey(saleKey);
									closeService.setSalesOrder(displayedEntity).start();
								}
							}
							else
							{
								Dialogs.create().nativeTitleBar().title("Entity_create_error.title")
								.message("Entity_click_to_see_error").showError();
							}
						}
					}

				});

		displayView.getOkButton().setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if(isValidSalesOrderItem())
					handleAddSalesOrderItem(salesOrderItem);
			}
		});

		//		control insurreur create dialog
		displayView.getInsurreurButton().setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				SalesOrderCustomer orderCustomer = displayedEntity.getCustomer();

				if("000000001".equals(orderCustomer.getSerialNumber()))
					return ;
				Insurrance insurrance = new Insurrance();
				InsurranceCustomer customer = new InsurranceCustomer();
				PropertyReader.copy(orderCustomer, customer);
				insurrance.setCustomer(customer);
				modalInsurerCreateRequestEvent.fire(insurrance);

			}
		});

		//	control client create dialog
		displayView.getClientButton().setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				modalCustomerCreateRequestEvent.fire(new Customer());
			}
		});

		displayView.getSaveReturnButton().setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if(!displayedEntity.getAlreadyReturned()){
					if(canProcessReturn()){
						orderReturnService.setEntity(displayedEntity).start();
					}else {
						Dialogs.create().message("La commande dois avoir au moins un article retourne").showInformation();
					}
				}else {
					Dialogs.create().message("Cette commande a deja fais l'objet dun retour !").showInformation();
				}
			}
		});

		orderReturnService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				SalesOrderReturnService s = (SalesOrderReturnService) event.getSource();
				SalesOrder so = s.getValue();
				event.consume();
				s.reset();
				displayedEntity.setAlreadyReturned(so.getAlreadyReturned());
				workingInfosEvent.fire("Article Returned   successfully !");
				Action showConfirm = Dialogs.create().message("voulez vs Imprimer l'Avoir ?").showConfirm();
				if(Dialog.Actions.YES.equals(showConfirm)){
					salesOrderVoucherPrintRequestEvent.fire(so);
				}

			}
		});

		orderReturnService.setOnFailed(callFailedEventHandler);

		displayView.getClient().setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				CustomerSearchInput customerSearchInput = new CustomerSearchInput();
				customerSearchInput.setMax(50);
				modalCutomerSearchRequestEvent.fire(customerSearchInput);
				isCustomerSearch = true ;
			}


		});

		displayView.getDiscountRate().numberProperty().addListener(new ChangeListener<BigDecimal>() {

			@Override
			public void changed(ObservableValue<? extends BigDecimal> observable,
					BigDecimal oldValue, BigDecimal newValue) {
				BigDecimal discount  = BigDecimal.ZERO;
				if(newValue!=null){
					BigDecimal amountBeforeTax = displayedEntity.getAmountBeforeTax()!=null?displayedEntity.getAmountBeforeTax():BigDecimal.ZERO;
					//					if(BigDecimal.ONE.compareTo(newValue)<0)
					//						newValue = ;
					discount = amountBeforeTax.multiply(newValue.divide(BigDecimal.valueOf(100), 8, RoundingMode.DOWN));
				}
				displayedEntity.setAmountDiscount(discount.setScale(0,RoundingMode.HALF_UP).setScale(2));
				resetNetTopay(displayedEntity);

			}
		});



		displayView.getAmountTTC().numberProperty().addListener(new ChangeListener<BigDecimal>() {

			@Override
			public void changed(ObservableValue<? extends BigDecimal> observable,
					BigDecimal oldValue, BigDecimal newValue) {
				BigDecimal discount  = BigDecimal.ZERO;
				if(newValue!=null){
					resetNetTopay(displayedEntity);
				}

			}
		});

		insurranceSearchService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				InsurranceSearchService s = (InsurranceSearchService) event.getSource();
				InsurranceSearchResult cs = s.getValue();
				event.consume();
				s.reset();
				List<Insurrance> resultList = cs.getResultList();
				displayView.getInsurrer().getItems().clear();
				ArrayList<SalesOrderInsurance> insurrances = new ArrayList<SalesOrderInsurance>();
				resultList.add( new Insurrance());
				for (Insurrance insurrance : resultList) {
					insurrances.add(new SalesOrderInsurance(insurrance));
				}

				displayView.getInsurrer().getItems().addAll(insurrances);
				if(!insurrances.isEmpty()){
					displayView.getInsurrer().setValue(insurrances.iterator().next());
					//					displayedEntity.setInsurance(insurrances.iterator().next());

				}else {
					displayedEntity.setInsurance(new SalesOrderInsurance());
				}

			}
		});
		insurranceSearchService.setOnFailed(callFailedEventHandler);
		displayView.getCashDrawer().setOnMouseClicked(new EventHandler<MouseEvent>()  {

			@Override
			public void handle(MouseEvent event) {
				getOpenCashDrawer();
			}

		});
		cashDrawerSearchService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				CashDrawerSearchService s = (CashDrawerSearchService) event.getSource();
				CashDrawerSearchResult cs = s.getValue();
				event.consume();
				s.reset();
				List<CashDrawer> resultList = cs.getResultList();
				if(!resultList.isEmpty()){
					displayView.getCashDrawer().getItems().clear();
					for (CashDrawer cashDrawer : resultList) {
						displayView.getCashDrawer().getItems().add(new SalesOrderCashDrawer(cashDrawer) );

					}
					displayView.getCashDrawer().getSelectionModel().select(0);
				}
			}
		});


		cashDrawerSearchService.setOnFailed(callFailedEventHandler);


		callFailedEventHandler.setErrorDisplay(new ErrorDisplay() {

			@Override
			protected void showError(Throwable exception) {
				Dialogs.create().nativeTitleBar().showException(exception);

			}
		});
		salesOrderItemCreateService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				SalesOrderItemCreateService s = (SalesOrderItemCreateService) event.getSource();
				SalesOrderItem createdItem = s.getValue();
				event.consume();
				s.reset();
				int indexOf = displayView.getDataList().getItems().indexOf(createdItem);
				if(indexOf>=0){
					PropertyReader.copy(createdItem, displayView.getDataList().getItems().get(indexOf));
				}else {
					displayView.getDataList().getItems().add(0,createdItem);
				}
				updateSalesOrder(createdItem);
				resetSearchBar();
				PropertyReader.copy(new SalesOrderItem(), salesOrderItem);
				displayView.getInternalPic().requestFocus();

			}
		});
		salesOrderItemCreateService.setOnFailed(salesOrderItemCreateFailedEventHandler);
		// handle managedlot information 
		salesOrderManagedLotService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				SalesOrderManagedLotService s = (SalesOrderManagedLotService) event.getSource();
				Boolean managedLot = s.getValue();
				event.consume();
				s.reset();
				displayView.getOrderedQty().setEditable(!managedLot);
				if(securityUtil.hasRole("SALES_PRICE_CHANGE"))
					displayView.getSalesPricePU().setEditable(true);

			}
		});
		salesOrderManagedLotService.setOnFailed(callFailedEventHandler);

		salesOrderItemEditService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				SalesOrderItemEditService s = (SalesOrderItemEditService) event.getSource();
				SalesOrderItem editedItem = s.getValue();
				event.consume();
				s.reset();
				int index = displayView.getDataList().getItems().indexOf(editedItem);
				if(index>-1){
					SalesOrderItem displayed = displayView.getDataList().getItems().get(index);
					PropertyReader.copy(editedItem, displayed);
				}
				updateSalesOrder(editedItem);

			}
		});
		salesOrderItemEditService.setOnFailed(callFailedEventHandler);
		salesOrderItemRemoveService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				SalesOrderItemRemoveService s = (SalesOrderItemRemoveService) event.getSource();
				SalesOrderItem removeddItem = s.getValue();
				event.consume();
				s.reset();
				displayView.getDataList().getItems().remove(removeddItem);
				//				PropertyReader.copy(new SalesOrderItem(), salesOrderItem);
				updateSalesOrder(removeddItem);
			}
		});
		salesOrderItemRemoveService.setOnFailed(callFailedEventHandler);

		closeService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				SalesOrderCloseService s = (SalesOrderCloseService) event.getSource();
				SalesOrder entity = s.getValue();
				event.consume();
				s.reset();
				//				Action showConfirm = Dialogs.create().nativeTitleBar().message("Voulez vous imprimer la facture ?").showConfirm();
				//				if(Dialog.Actions.YES.equals(showConfirm)){
				//					printCustomerInvoiceRequestedEvent.fire(new SalesOrderId(entity.getId()));
				//				}
				PropertyReader.copy(entity, displayedEntity);
				salesOrderRequestEvent.fire(new SalesOrder());
				workingInfosEvent.fire("Sales Number : "+entity.getSoNumber()+" Closed successfully!");

			}
		});


		closeService.setOnFailed(callFailedEventHandler);


		displayView.getArticleName().setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				KeyCode code = event.getCode();
				if(code== KeyCode.ENTER){
					String articleName = displayView.getArticleName().getText();
					if(StringUtils.isBlank(articleName)) return;
					ArticleLot entity = new ArticleLot();
					entity.setArticleName(articleName);
					entity.setStockQuantity(BigDecimal.ONE);
					ArticleLotSearchInput asi = new ArticleLotSearchInput();
					asi.setEntity(entity);
					asi.setMax(30);
					asi.getFieldNames().add("articleName");
					modalArticleLotSearchEvent.fire(asi);
				}

				if(code== KeyCode.DELETE){
					displayView.getArticleName().setText("");

				}
			}
		});

		displayView.getDiscount().numberProperty().addListener(new ChangeListener<BigDecimal>(
				) {

			@Override
			public void changed(
					ObservableValue<? extends BigDecimal> observable,
					BigDecimal oldValue, BigDecimal newValue) {
				BigDecimal ttc = displayView.getAmountTTC().getNumber();
				if(newValue!=null){
					if(ttc!=null&& ttc.compareTo(BigDecimal.ZERO)>0)
						ttc = ttc.subtract(newValue);
				}
				displayView.getNetClientText().setText(ttc.toBigInteger()+"");

			}
		});

		//		
		displayView.getInternalPic().setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				KeyCode code = event.getCode();
				if(code== KeyCode.ENTER||code== KeyCode.TAB){
					String internalPic = displayView.getInternalPic().getText();
					if(StringUtils.isBlank(internalPic)) return;
					ArticleLot entity = new ArticleLot();
					entity.setSecondaryPic(internalPic);
					entity.setInternalPic(internalPic);
					entity.setMainPic(internalPic);
					ArticleLotSearchInput asi = new ArticleLotSearchInput();
					asi.setEntity(entity);
					asi.setMax(30);
					asi.getFieldNames().add("secondaryPic");
					asi.getFieldNames().add("mainPic");
					asi.getFieldNames().add("internalPic");
					modalArticleLotSearchEvent.fire(asi);

				}
			}
		});

		/*
		 * listen to ordonnancier button and fire modal create  requested event.
		 */
		displayView.getOrdonnancierButton().setOnAction(new EventHandler<ActionEvent>()
				{
			@Override
			public void handle(ActionEvent e)
			{
				PrescriptionBookSearchInput searchInput = new PrescriptionBookSearchInput();
				searchInput.getEntity().setSalesOrder(new PrescriptionBookSalesOrder(displayedEntity));
				searchInput.getFieldNames().add("salesOrder");
				prescriptionBookSearchService.setSearchInputs(searchInput).start();
			}
				});

		prescriptionBookSearchService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				PrescriptionBookSearchService s = (PrescriptionBookSearchService) event.getSource();
				PrescriptionBookSearchResult searchResult = s.getValue();
				event.consume();
				s.reset();

				PrescriptionBook prescriptionBook ;
				if(searchResult.getResultList().isEmpty()){
					Login login = securityUtil.getConnectedUser();
					prescriptionBook = new PrescriptionBook();

					PrescriptionBookAgency agency = new PrescriptionBookAgency();
					PropertyReader.copy(login.getAgency(), agency);
					prescriptionBook.setAgency(agency);

					prescriptionBook.setSalesOrder(new PrescriptionBookSalesOrder(displayedEntity));

					PrescriptionBookRecordingAgent recordingAgent = new PrescriptionBookRecordingAgent();
					PropertyReader.copy(login, recordingAgent);
					prescriptionBook.setRecordingAgent(recordingAgent);
				}else {
					prescriptionBook = searchResult.getResultList().iterator().next();
				}

				modalPrescriptionBookCreateRequestEvent.fire(prescriptionBook);

			}
		});
		prescriptionBookSearchService.setOnFailed(callFailedEventHandler);

		displayView.addValidators();
	}

	public void display(Pane parent)
	{
		BorderPane rootPane = displayView.getRootPane();
		ObservableList<Node> children = parent.getChildren();
		if (!children.contains(rootPane))
		{
			children.add(rootPane);
		}
		displayView.getInternalPic().requestFocus();
		getOpenCashDrawer();

	}

	@Override
	public ViewType getViewType()
	{
		return ViewType.DISPLAY;
	}

	private void handleAddSalesOrderItem(SalesOrderItem salesOrderItem) {
		if(salesOrderItem.getId()==null){
			if(salesOrderItem.getSalesOrder()==null) salesOrderItem.setSalesOrder(new SalesOrderItemSalesOrder(displayedEntity)); 
			salesOrderItem.setDeliveredQty(salesOrderItem.getOrderedQty());
			salesOrderItemCreateService.setModel(salesOrderItem).start();
		}
	}
	public void resetSearchBar(){
		displayView.getInternalPic().setText("");
		displayView.getArticleName().setText("");
		displayView.getSalesPricePU().setText("");
		displayView.getTotalSalePrice().setText("");
	}

	public void getOpenCashDrawer(){
		CashDrawerSearchInput cdsi = new CashDrawerSearchInput();
		cdsi.getEntity().setOpened(Boolean.TRUE);
		CashDrawerAgency cashDrawerAgency = new CashDrawerAgency();
		PropertyReader.copy(securityUtil.getAgency(), cashDrawerAgency);
		cdsi.getEntity().setAgency(cashDrawerAgency);
		cdsi.getFieldNames().addAll(Arrays.asList("opened","agency"));
		cdsi.setMax(-1);
		cashDrawerSearchService.setSearchInputs(cdsi).start();
	}

	/**
	 * Listens to list selection events and bind selected to pane
	 */
	public void handleSelectionEvent(@Observes @EntitySelectionEvent SalesOrder selectedEntity)
	{
		PropertyReader.copy(selectedEntity, displayedEntity);
		displayView.getClient().setValue(selectedEntity.getCustomer());
		Customer customer = new Customer();
		PropertyReader.copy(displayedEntity.getCustomer(), customer);
		displayView.getAmountHT().setNumber(displayedEntity.getAmountAfterTax());
		getCustomerInsurance(customer);
		setDiscountt(selectedEntity);
		resetNetTopay(selectedEntity);
		displayView.getNumBon().setText(null);
	}

	public void setDiscountt(SalesOrder selectedEntity){
		displayView.getDiscountRate().setNumber(BigDecimal.ZERO);
		displayView.getDiscount().setNumber(BigDecimal.ZERO);

	}

	public void resetNetTopay(SalesOrder selectedEntity){
		BigDecimal discount = displayView.getDiscount().getNumber()!=null?displayView.getDiscount().getNumber():BigDecimal.ZERO;
		BigDecimal netToPay = displayView.getAmountTTC().getNumber()!=null?displayView.getAmountTTC().getNumber():BigDecimal.ZERO;
		if(discount!=null){
			netToPay = netToPay.subtract(discount);
		}
		if(selectedEntity.getInsurance()!=null&&selectedEntity.getInsurance().getId()!=null){
			BigDecimal coverageRate = selectedEntity.getInsurance().getCoverageRate();
			coverageRate = coverageRate.divide(BigDecimal.valueOf(100));
			netToPay = netToPay.subtract(netToPay.multiply(coverageRate));
		}
		displayView.getNetClientText().setText(netToPay.toBigInteger()+" CFA");
	}

	public boolean isValidSalesOrderItem(){
		BigDecimal orderedQty = salesOrderItem.getOrderedQty();
		SalesOrderItemArticle article = salesOrderItem.getArticle();

		if(article==null || article.getId()==null){
			Dialogs.create().nativeTitleBar().message("vous devez selection un article ").showError();
			return false;
		}

		if(orderedQty==null || orderedQty.compareTo(BigDecimal.ZERO)==0){
			Dialogs.create().nativeTitleBar().message("la Qte est Requis ").showError();
			displayView.getOrderedQty().requestFocus();
			return false;
		}

		return true;

	}

	public boolean isValideSale(Login login){
		BigDecimal amountDiscount = displayedEntity.getAmountDiscount()!=null?displayedEntity.getAmountDiscount():BigDecimal.ZERO;
		BigDecimal amountAfterTax = displayedEntity.getAmountAfterTax()!=null?displayedEntity.getAmountAfterTax():BigDecimal.ZERO;

		if(displayedEntity.getSalesOrderItems().isEmpty()){
			Dialogs.create().nativeTitleBar().message("la vente dois avoir au moins un produit ").showError();
			return false;
		}
		
		String discoutStrategy = ReceiptPrintProperties.loadPrintProperties().getDiscoutStrategy();
		BigDecimal userDiscounteRate = login.getDiscountRate()!=null?login.getDiscountRate():BigDecimal.ZERO;
		BigDecimal categoryDiscountRate = displayedEntity.getCustomer().getCustomerCategory().getDiscountRate()!=null?displayedEntity.getCustomer().getCustomerCategory().getDiscountRate():BigDecimal.ZERO;
		BigDecimal realdiscounteRate = BigDecimal.ZERO;
		switch (discoutStrategy) {
		case "MIN":
			    if(userDiscounteRate.compareTo(categoryDiscountRate)<=0){
			       realdiscounteRate = userDiscounteRate;
			    }else {
					realdiscounteRate = categoryDiscountRate;
				}
			break;
			
		case "MAX":
		    if(userDiscounteRate.compareTo(categoryDiscountRate)>=0){
		       realdiscounteRate = userDiscounteRate;
		    }else {
				realdiscounteRate = categoryDiscountRate;
			}
		    
		case "USER":
		    realdiscounteRate = userDiscounteRate ;
		break;
		
		case "CATEGORY":
		    realdiscounteRate = categoryDiscountRate ;
		break;

		default:
			realdiscounteRate = categoryDiscountRate;
			break;
		}
		BigDecimal realDiscount = amountAfterTax.multiply(realdiscounteRate.divide(BigDecimal.valueOf(100), 4, RoundingMode.DOWN));
		realDiscount = realDiscount.add(BigDecimal.valueOf(1)) ;
		if(realDiscount.compareTo(amountDiscount)<0){
			Dialogs.create().message("la remise ne peux etre superieur a "+ realDiscount).showInformation();
			return false ;
		}
//		
//		if(displayedEntity.getCustomer().getCustomerCategory().getDiscountRate()!=null){
//			BigDecimal discountRate = displayedEntity.getCustomer().getCustomerCategory().getDiscountRate();
//			if(discountRate!=null&&amountDiscount!=null){
//				BigDecimal realDiscount = amountAfterTax.multiply(discountRate.divide(BigDecimal.valueOf(100), 4, RoundingMode.HALF_EVEN)).add(BigDecimal.valueOf(5));
//				if(realDiscount.compareTo(amountDiscount)<0){
//					Dialogs.create().message("la remise ne peux etre superieur a "+ realDiscount).showInformation();
//					return false ;
//				}
//			}
//		}else  if(BigDecimal.ZERO.compareTo(amountDiscount)!=0){
//			Dialogs.create().message("ce client n est pas autorise a avoir une remise ").showInformation();
//			displayedEntity.setAmountDiscount(BigDecimal.ZERO);
//			displayView.getDiscountRate().setNumber(BigDecimal.ZERO);
//			displayView.getDiscountRate().requestFocus();
//			return false ;
//
//		}

		return true;
	}
	

	public void handleAssocSelectionRequest(@Observes(notifyObserver = Reception.ALWAYS) @AssocSelectionRequestEvent AssocSelectionEventData<SalesOrder> eventData)
	{
		pendingSelectionRequestProperty.set(eventData);
		componentSelectionRequestEvent.fire(new ComponentSelectionRequestData(SalesOrder.class.getName()));
		searchRequestedEvent.fire(eventData.getTargetEntity() != null ? eventData.getTargetEntity() : new SalesOrder());
	}

	public void handleArticleLotSearchDone(@Observes @ModalEntitySearchDoneEvent ArticleLot model)
	{
		if(!isValidateOrderedQty(model))
			return ;
		PropertyReader.copy(salesOrderItemfromArticle(model), salesOrderItem);
		if(!displayView.getArticleName().isEditable()){
			if(isValidSalesOrderItem())
				if(isOldStock(model)){
					handleAddSalesOrderItem(salesOrderItem);
				}else {
					Dialogs.create().message("Veuillez Saisir le plus ancien Merci pour votre comprehension ! ").showInformation();
					PropertyReader.copy(new SalesOrderItem(), salesOrderItem);
				}
			displayView.getInternalPic().requestFocus();
		}else {
			handleAddSalesOrderItem(salesOrderItem);
			displayView.getArticleName().requestFocus();
		}
	}

	public boolean isOldStock(ArticleLot model){
		boolean isOldStock = false ;
		ArticleLotSearchInput articleLotSearchInput = new ArticleLotSearchInput();
		articleLotSearchInput.setEntity(model);
		articleLotSearchInput.getFieldNames().add("article") ;
		List<ArticleLot> allLot = articleLotService.findArticleLotByArticleOrderByCreationDate(articleLotSearchInput).getResultList();
		ArrayList<ArticleLot> restLot = new ArrayList<ArticleLot>(allLot);
		Iterator<SalesOrderItem> salesOrderItems = displayView.getDataList().getItems().iterator();

		List<SalesOrderItem> orderItemWithSameArticle = extractSalesOrderItemWithSameArticle(model.getArticle(), salesOrderItems);
		if(orderItemWithSameArticle.isEmpty()){
			if(!allLot.isEmpty())
				return allLot.get(0).equals(model);

		}else {
			for (ArticleLot lot : allLot) {
				for (SalesOrderItem salesOrderItem : orderItemWithSameArticle) {
					if(lot.getInternalPic().equals(salesOrderItem.getInternalPic())){
						if(lot.getStockQuantity().compareTo(salesOrderItem.getOrderedQty())==0){
							restLot.remove(lot);
						}else {
							if(restLot.get(0).equals(model)&& model.getInternalPic().equals(salesOrderItem.getInternalPic()))
								return true ;
						}
					}
				}
			}
			if(!restLot.isEmpty())
				return restLot.get(0).equals(model);

		}


		return isOldStock ;
	}
	public boolean isValidateOrderedQty(ArticleLot model){
		Iterator<SalesOrderItem> iterator = displayView.getDataList().getItems().iterator();
		//		while (iterator.hasNext()) {
		//			SalesOrderItem next = iterator.next();
		//			if(next.getInternalPic().equals(model.getInternalPic())){
		//				BigDecimal salableStock = model.getStockQuantity();
		//				BigDecimal orderedStock = next.getOrderedQty().add(BigDecimal.ONE);
		//				if(salableStock.compareTo(orderedStock)< 0 ){
		//					Dialogs.create().message("La Quantite Commandee ne peux etre superieur a "+ salableStock).showError();
		//				displayView.getInternalPic().setText(null);
		//					return false ;
		//					
		//				}
		//			}
		//		}

		return true ;
	}


	public List<SalesOrderItem> extractSalesOrderItemWithSameArticle(ArticleLotArticle aticle, Iterator<SalesOrderItem> salesOrderItems){
		ArrayList<SalesOrderItem> salesOrderItemWithSameArticle = new ArrayList<SalesOrderItem>();
		while (salesOrderItems.hasNext()) {
			SalesOrderItem salesOrderItem = (SalesOrderItem) salesOrderItems.next();
			if(salesOrderItem.getArticle().getId().equals(aticle.getId()))
				salesOrderItemWithSameArticle.add(salesOrderItem);
		}

		return salesOrderItemWithSameArticle ;
	}

	public void handleCustomerCreateDoneEvent(@Observes @ModalEntityCreateDoneEvent Customer model)
	{
		handleNewCustomer(model);
	}

	public void handleInssuranceCreateDoneEvent(@Observes @ModalEntityCreateDoneEvent Insurrance model)
	{
		handleNewInsurance(model);
	}


	public void handleCustomerSearchDoneEvent(@Observes @ModalEntitySearchDoneEvent Customer model)
	{
		if(isCustomerSearch){
			handleNewCustomer(model);
			isCustomerSearch = false ;
		}
	}

	public void handleNewCustomer(Customer model){
		if(model !=null){
			displayView.getClient().setValue(new SalesOrderCustomer(model));
			displayView.getInsurrer().setValue(new SalesOrderInsurance());
			getCustomerInsurance(model);

		}


	}

	public void getCustomerInsurance(Customer model){
		insurranceSearchInput.getEntity().setCustomer(new InsurranceCustomer(model));
		insurranceSearchInput.setMax(-1);
		insurranceSearchInput.getFieldNames().clear();
		insurranceSearchInput.getFieldNames().add("customer");
		insurranceSearchService.setSearchInputs(insurranceSearchInput).start();
	}

	public void handleNewInsurance(Insurrance model){
		Customer customer = new Customer();
		PropertyReader.copy(model.getCustomer(),customer);
		getCustomerInsurance(customer);
	}


	//	public void handleInsurranceCreateDoneEvent(@Observes @ModalEntityCreateDoneEvent Insurrance model)
	//	{
	//		displayView.getInsurrer().getItems().add(0,new SalesOrderInsurance(model));
	//	}

	/**
	 * This is the only time where the bind method is called on this object.
	 * @param model
	 */

	public void handleNewModelEvent(@Observes @SelectedModelEvent SalesOrder model)
	{
		this.displayedEntity = model;
		displayView.bind(this.displayedEntity);
	}

	//	private static final BigDecimal HUNDRED = new BigDecimal(100); 
	public void updateSalesOrder(SalesOrderItem salesOrderItem){
		SalesOrderItemSalesOrder salesOrder = salesOrderItem.getSalesOrder();
		PropertyReader.copy(salesOrder, displayedEntity);
	}


	public SalesOrderItem salesOrderItemfromArticle(ArticleLot al){
		SalesOrderItem soItem = new SalesOrderItem();
		SalesOrderItemArticle soia = new SalesOrderItemArticle();
		soia.setId(al.getArticle().getId());
		soia.setArticleName(al.getArticle().getArticleName());
		soia.setVersion(al.getArticle().getVersion());
		soia.setPic(al.getArticle().getPic());

		soItem.setInternalPic((al.getInternalPic()));
		soItem.setArticle(soia);
		soItem.setOrderedQty(BigDecimal.ONE);
		soItem.setSalesPricePU((al.getSalesPricePU()));
		soItem.setPurchasePricePU(al.getPurchasePricePU());
		//		soItem.setTotalSalePrice(al.getPurchasePricePU());
		soItem.setSalesOrder(new SalesOrderItemSalesOrder(displayedEntity));
		soItem.setInternalPic(al.getInternalPic());
		SalesOrderItemVat soiVat = new SalesOrderItemVat();
		ArticleLotVat alVat = al.getVat();
		PropertyReader.copy(alVat, soiVat);
		soItem.setVat(soiVat);

		ClearanceConfig clearanceConfig = al.getArticle().getClearanceConfig();
		if(clearanceConfig!=null&&clearanceConfig.getDiscountRate()!=null){
			BigDecimal rate = clearanceConfig.getDiscountRate();
			Date time = clearanceConfig.getEndDate().getTime();
			BigDecimal discount = (al.getSalesPricePU().multiply(rate)).divide(BigDecimal.valueOf(100));
			if(clearanceConfig.getEndDate()!=null&&clearanceConfig.getEndDate().getTime().after(new Date()))
				soItem.setSalesPricePU((al.getSalesPricePU().subtract(discount)));
		}
		return soItem;
	}

	public BigDecimal getQtyToReturn() {
		String showTextInput = Dialogs.create().actions(Dialog.Actions.OK).message("Qte retounee : ").showTextInput("0");
		BigDecimal qtyToReturn = BigDecimal.ZERO;
		try {
			qtyToReturn = new BigDecimal(showTextInput);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return qtyToReturn;
	}
	public void reset() {
		PropertyReader.copy(new SalesOrder(), displayedEntity);
	}

	public void handleLoginSucceedEvent (@Observes(notifyObserver = Reception.ALWAYS) @LoginSucceededEvent String loginName) {
		salesOrderManagedLotService.start();
	}

	public void handleRolesRequestEvent(@Observes @RolesEvent Set<String> roles) {
		if(roles.contains("MANAGER")){
			//			displayView.getOrderQuantityColumn().setEditable(true);
		}
	}

	public boolean hasReturn(){
		Iterator<SalesOrderItem> items = displayView.getDataList().getItems().iterator();
		while (items.hasNext()) {
			SalesOrderItem orderItem = items.next();
			if(BigDecimal.ZERO.compareTo(orderItem.getReturnedQty())!=0)
				return true;
		}
		return false;
	}

	public void handleRolesEvent(@Observes(notifyObserver=Reception.ALWAYS) @RolesEvent Set<String> roles){
		if(roles.contains(AccessRoleEnum.SUPER_SALLER_PERM.name())||roles.contains(AccessRoleEnum.MANAGER.name())){
			displayView.getSalesPricePU().setEditable(true);
		}else {
			displayView.getSalesPricePU().setEditable(false);
		}

		//		if(roles.contains(AccessRoleEnum.SALLE_BY_ARTICLENAME_PERM.name())||roles.contains(AccessRoleEnum.MANAGER.name())){
		//			displayView.getArticleName().setEditable(true);
		//		}else {
		//			displayView.getArticleName().setEditable(false);
		//		}

		//		if(roles.contains(AccessRoleEnum.RETURN_SALES_PERM.name())||roles.contains(AccessRoleEnum.MANAGER.name())){
		//			displayView.getReturnSOIMenu().setVisible(true);
		//			displayView.getSaveReturnButton().setVisible(true);
		//		}else {
		//			displayView.getReturnSOIMenu().setVisible(false);
		//			displayView.getSaveReturnButton().setVisible(false);
		//		}
	}
	public boolean canProcessReturn(){
		boolean canProcessReturn = false ;
		Iterator<SalesOrderItem> items = displayView.getDataList().getItems().iterator();
		while (items.hasNext()) {
			SalesOrderItem salesOrderItem = (SalesOrderItem) items.next();
			if(BigDecimal.ZERO.compareTo(salesOrderItem.getReturnedQty())<0){
				canProcessReturn = true ;
				break ;
			}
		}
		return canProcessReturn  ;
	}
	public void handleLogoutSucceedEvent(@Observes(notifyObserver=Reception.ALWAYS) @LogoutSucceededEvent Object object){
		PropertyReader.copy(new SalesOrder(), displayedEntity);
		PropertyReader.copy(new SalesOrderItem(), salesOrderItem);
		searchRequestedEvent.fire(displayedEntity);
	}
}
