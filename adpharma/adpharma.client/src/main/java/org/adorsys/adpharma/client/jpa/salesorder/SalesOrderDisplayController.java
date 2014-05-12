package org.adorsys.adpharma.client.jpa.salesorder;

import java.math.BigDecimal;
import java.util.Arrays;
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
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Separator;
import javafx.scene.control.TableColumn.CellEditEvent;
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
import javax.validation.ConstraintViolation;

import jfxtras.scene.layout.HBox;
import jfxtras.scene.layout.VBox;

import org.adorsys.adpharma.client.SecurityUtil;
import org.adorsys.adpharma.client.events.PrintCustomerInvoiceRequestedEvent;
import org.adorsys.adpharma.client.events.SalesOrderId;
import org.adorsys.adpharma.client.jpa.articlelot.ArticleLot;
import org.adorsys.adpharma.client.jpa.articlelot.ArticleLotSearchInput;
import org.adorsys.adpharma.client.jpa.articlelot.ArticleLotVat;
import org.adorsys.adpharma.client.jpa.cashdrawer.CashDrawer;
import org.adorsys.adpharma.client.jpa.cashdrawer.CashDrawerAgency;
import org.adorsys.adpharma.client.jpa.cashdrawer.CashDrawerSearchInput;
import org.adorsys.adpharma.client.jpa.cashdrawer.CashDrawerSearchResult;
import org.adorsys.adpharma.client.jpa.cashdrawer.CashDrawerSearchService;
import org.adorsys.adpharma.client.jpa.customer.Customer;
import org.adorsys.adpharma.client.jpa.customer.CustomerSearchInput;
import org.adorsys.adpharma.client.jpa.insurrance.Insurrance;
import org.adorsys.adpharma.client.jpa.insurrance.InsurranceCustomer;
import org.adorsys.adpharma.client.jpa.insurrance.InsurranceSearchInput;
import org.adorsys.adpharma.client.jpa.insurrance.InsurranceSearchResult;
import org.adorsys.adpharma.client.jpa.insurrance.InsurranceSearchService;
import org.adorsys.adpharma.client.jpa.login.Login;
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
	private ServiceCallFailedEventHandler callFailedEventHandler;

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

	@PostConstruct
	public void postConstruct()
	{

		//		      displayView.getEditButton().disableProperty().bind(registration.canEditProperty().not());
		//		displayView.getRemoveButton().disableProperty().bind(registration.canEditProperty().not());

		//		bind models to the view
		displayView.bind(displayedEntity);
		displayView.bind(salesOrderItem);


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
					selectedItem.setOrderedQty(newValue);
					selectedItem.updateTotalSalesPrice();
					// update article
					salesOrderItemEditService.setSalesOrderItem(selectedItem).start();
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
		 * listen to save button .
		 */
		displayView.getCloseButton().disableProperty().bind(closeService.runningProperty());
		displayView.getCloseButton().setOnAction(
				new EventHandler<ActionEvent>()
				{
					@Override
					public void handle(ActionEvent e)
					{
						if(isValideSale()){
							Set<ConstraintViolation<SalesOrder>> violations = displayView.validate(displayedEntity);
							if (violations.isEmpty())
							{
								String salesKey = salesKeyRecieverView.show();
								if(Dialog.Actions.OK.equals(salesKeyRecieverView.getUserAction())){
									displayedEntity.setSalesKey(salesKey);
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
				Insurrance insurrance = new Insurrance();
				InsurranceCustomer customer = new InsurranceCustomer();
				PropertyReader.copy(displayedEntity.getCustomer(), customer);
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


					orderReturnService.setEntity(displayedEntity).start();
				}else {
					Dialogs.create().message("Cette commande adeja fais l abjet dun retour !").showInformation();
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
				workingInfosEvent.fire("Article Returned   successfully !");

			}
		});

		orderReturnService.setOnFailed(callFailedEventHandler);

		displayView.getClient().setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				CustomerSearchInput customerSearchInput = new CustomerSearchInput();
				customerSearchInput.setMax(50);
				modalCutomerSearchRequestEvent.fire(customerSearchInput);
			}


		});

		displayView.getDiscountRate().numberProperty().addListener(new ChangeListener<BigDecimal>() {

			@Override
			public void changed(ObservableValue<? extends BigDecimal> observable,
					BigDecimal oldValue, BigDecimal newValue) {
				if(newValue!=null){
					BigDecimal amountAfterTax = displayedEntity.getAmountAfterTax()!=null?displayedEntity.getAmountAfterTax():BigDecimal.ZERO;
					BigDecimal discount = amountAfterTax.multiply(newValue);
					displayedEntity.setAmountDiscount(discount);

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
				resultList.add(0, new Insurrance());
				for (Insurrance insurrance : resultList) {
					displayView.getInsurrer().getItems().add(new SalesOrderInsurance(insurrance));
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
				displayView.getDataList().getItems().add(createdItem);
				PropertyReader.copy(new SalesOrderItem(), salesOrderItem);
				updateSalesOrder(createdItem);
				displayView.getInternalPic().requestFocus();

			}
		});
		salesOrderItemCreateService.setOnFailed(callFailedEventHandler);

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
				Action showConfirm = Dialogs.create().nativeTitleBar().message("Voulez vous imprimer la facture ?").showConfirm();
				if(Dialog.Actions.YES.equals(showConfirm)){
					printCustomerInvoiceRequestedEvent.fire(new SalesOrderId(entity.getId()));
				}
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
					ArticleLotSearchInput asi = new ArticleLotSearchInput();
					asi.setEntity(entity);
					asi.setEntity(entity);
					asi.setMax(30);
					asi.getFieldNames().add("articleName");
					modalArticleLotSearchEvent.fire(asi);
				}
			}
		});

		//		
		displayView.getInternalPic().setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				KeyCode code = event.getCode();
				if(code== KeyCode.ENTER){
					String internalPic = displayView.getInternalPic().getText();
					if(StringUtils.isBlank(internalPic)) return;
					ArticleLot entity = new ArticleLot();
					entity.setSecondaryPic(internalPic);
					ArticleLotSearchInput asi = new ArticleLotSearchInput();
					asi.setEntity(entity);
					asi.setMax(30);
					asi.getFieldNames().add("secondaryPic");
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
		getOpenCashDrawer();
	}

	@Override
	public ViewType getViewType()
	{
		return ViewType.DISPLAY;
	}

	private void handleAddSalesOrderItem(SalesOrderItem salesOrderItem) {
		//		salesOrderItem.calculateTotalAmout();
		if(salesOrderItem.getId()==null){
			if(salesOrderItem.getSalesOrder()==null) salesOrderItem.setSalesOrder(new SalesOrderItemSalesOrder(displayedEntity)); 
			salesOrderItemCreateService.setModel(salesOrderItem).start();
		}
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
		Customer customer = new Customer();
		PropertyReader.copy(displayedEntity.getCustomer(), customer);
		getCustomerInsurance(customer);
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

	public boolean isValideSale(){
		if(displayedEntity.getSalesOrderItems().isEmpty()){
			Dialogs.create().nativeTitleBar().message("la vente dois avoir au moins un produit ").showError();
			return false;
		}
		if(displayedEntity.getCustomer().getCustomerCategory()!=null){
			BigDecimal discountRate = displayedEntity.getCustomer().getCustomerCategory().getDiscountRate();
			if(discountRate!=null){
				BigDecimal amountDiscount = displayedEntity.getAmountDiscount()!=null?displayedEntity.getAmountDiscount():BigDecimal.ZERO;
				BigDecimal amountAfterTax = displayedEntity.getAmountAfterTax()!=null?displayedEntity.getAmountAfterTax():BigDecimal.ZERO;
				BigDecimal realDiscount = amountAfterTax.multiply(discountRate.divide(BigDecimal.valueOf(100)));
				if(realDiscount.compareTo(realDiscount)<0){
					Dialogs.create().message("la remise ne peux etre superieur a "+ realDiscount).showInformation();
					return false ;
				}
			}else {
				displayedEntity.setAmountDiscount(BigDecimal.ZERO);
			}
		}
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
		PropertyReader.copy(salesOrderItemfromArticle(model), salesOrderItem);
		displayView.getOrderedQty().requestFocus();
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
		handleNewCustomer(model);
	}

	public void handleNewCustomer(Customer model){
		if(model !=null){
			displayedEntity.setCustomer((new SalesOrderCustomer(model)));
			displayedEntity.setInsurance(new SalesOrderInsurance());
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
		displayedEntity.setInsurance(new SalesOrderInsurance(model));
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
		//		soItem.setTotalSalePrice(al.getPurchasePricePU());
		soItem.setSalesOrder(new SalesOrderItemSalesOrder(displayedEntity));
		soItem.setInternalPic(al.getInternalPic());
		SalesOrderItemVat soiVat = new SalesOrderItemVat();
		ArticleLotVat alVat = al.getVat();
		PropertyReader.copy(alVat, soiVat);
		soItem.setVat(soiVat);
		return soItem;
	}

	public BigDecimal getQtyToReturn() {
		String showTextInput = Dialogs.create().message("Qte retounee : ").showTextInput("1");
		BigDecimal qtyToReturn = BigDecimal.ONE;
		try {
			qtyToReturn = new BigDecimal(showTextInput);
		} catch (Exception e) {
			getQtyToReturn();
		}

		return qtyToReturn;
	}
	public void reset() {
		PropertyReader.copy(new SalesOrder(), displayedEntity);
	}

}
