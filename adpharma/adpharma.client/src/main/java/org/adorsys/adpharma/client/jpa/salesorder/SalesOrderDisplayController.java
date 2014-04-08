package org.adorsys.adpharma.client.jpa.salesorder;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ListChangeListener;
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
import javax.validation.ConstraintViolation;

import org.adorsys.adpharma.client.SecurityUtil;
import org.adorsys.adpharma.client.jpa.articlelot.ArticleLot;
import org.adorsys.adpharma.client.jpa.articlelot.ArticleLotSearchInput;
import org.adorsys.adpharma.client.jpa.cashdrawer.CashDrawer;
import org.adorsys.adpharma.client.jpa.cashdrawer.CashDrawerAgency;
import org.adorsys.adpharma.client.jpa.cashdrawer.CashDrawerSearchInput;
import org.adorsys.adpharma.client.jpa.cashdrawer.CashDrawerSearchResult;
import org.adorsys.adpharma.client.jpa.cashdrawer.CashDrawerSearchService;
import org.adorsys.adpharma.client.jpa.customer.Customer;
import org.adorsys.adpharma.client.jpa.customer.CustomerSearchInput;
import org.adorsys.adpharma.client.jpa.deliveryitem.DeliveryItem;
import org.adorsys.adpharma.client.jpa.deliveryitem.DeliveryItemRemoveService;
import org.adorsys.adpharma.client.jpa.documentprocessingstate.DocumentProcessingState;
import org.adorsys.adpharma.client.jpa.insurrance.Insurrance;
import org.adorsys.adpharma.client.jpa.insurrance.InsurranceCustomer;
import org.adorsys.adpharma.client.jpa.insurrance.InsurranceSearchInput;
import org.adorsys.adpharma.client.jpa.insurrance.InsurranceSearchResult;
import org.adorsys.adpharma.client.jpa.insurrance.InsurranceSearchService;
import org.adorsys.adpharma.client.jpa.salesorderitem.SalesOrderItem;
import org.adorsys.adpharma.client.jpa.salesorderitem.SalesOrderItemArticle;
import org.adorsys.adpharma.client.jpa.salesorderitem.SalesOrderItemCreateService;
import org.adorsys.adpharma.client.jpa.salesorderitem.SalesOrderItemEditService;
import org.adorsys.adpharma.client.jpa.salesorderitem.SalesOrderItemRemoveService;
import org.adorsys.adpharma.client.jpa.salesorderitem.SalesOrderItemSalesOrder;
import org.adorsys.adpharma.client.jpa.vat.VAT;
import org.adorsys.adpharma.client.jpa.vat.VATSearchInput;
import org.adorsys.adpharma.client.jpa.vat.VATSearchResult;
import org.adorsys.adpharma.client.jpa.vat.VATSearchService;
import org.adorsys.javafx.crud.extensions.EntityController;
import org.adorsys.javafx.crud.extensions.ViewType;
import org.adorsys.javafx.crud.extensions.events.AssocSelectionEventData;
import org.adorsys.javafx.crud.extensions.events.AssocSelectionRequestEvent;
import org.adorsys.javafx.crud.extensions.events.AssocSelectionResponseEvent;
import org.adorsys.javafx.crud.extensions.events.ComponentSelectionRequestData;
import org.adorsys.javafx.crud.extensions.events.ComponentSelectionRequestEvent;
import org.adorsys.javafx.crud.extensions.events.EntityCreateRequestedEvent;
import org.adorsys.javafx.crud.extensions.events.EntityEditRequestedEvent;
import org.adorsys.javafx.crud.extensions.events.EntityRemoveRequestEvent;
import org.adorsys.javafx.crud.extensions.events.EntitySearchRequestedEvent;
import org.adorsys.javafx.crud.extensions.events.EntitySelectionEvent;
import org.adorsys.javafx.crud.extensions.events.ModalEntityCreateDoneEvent;
import org.adorsys.javafx.crud.extensions.events.ModalEntityCreateRequestedEvent;
import org.adorsys.javafx.crud.extensions.events.ModalEntitySearchDoneEvent;
import org.adorsys.javafx.crud.extensions.events.ModalEntitySearchRequestedEvent;
import org.adorsys.javafx.crud.extensions.events.SelectedModelEvent;
import org.adorsys.javafx.crud.extensions.login.ErrorDisplay;
import org.adorsys.javafx.crud.extensions.login.ServiceCallFailedEventHandler;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;
import org.apache.commons.lang3.StringUtils;
import org.controlsfx.control.action.Action;
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
	@EntitySearchRequestedEvent
	private Event<SalesOrder> searchRequestedEvent;

	@Inject
	@EntityEditRequestedEvent
	private Event<SalesOrder> editRequestEvent;

	@Inject
	@EntityRemoveRequestEvent
	private Event<SalesOrder> removeRequest;

	@Inject
	@AssocSelectionResponseEvent
	private Event<AssocSelectionEventData<SalesOrder>> selectionResponseEvent;

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

	@PostConstruct
	public void postConstruct()
	{
//		      displayView.getEditButton().disableProperty().bind(registration.canEditProperty().not());
		//		displayView.getRemoveButton().disableProperty().bind(registration.canEditProperty().not());

		//		bind models to the view
		displayView.bind(displayedEntity);
		displayView.bind(salesOrderItem);

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
					handleAddSalesOrderItem(salesOrderItem);
				}
			}

		});

		/*
		 * listen to delete menu Item.
		 */

		displayView.getDeleteSOIMenu().setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				SalesOrderItem selectedItem = displayView.getDataList().getSelectionModel().getSelectedItem();
				if(selectedItem!=null) {
					salesOrderItemRemoveService.setEntity(selectedItem).start();
				}

			}
		});

		/*
		 * listen to edit menu Item.
		 */
		displayView.getEditSOIMenu().setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				SalesOrderItem selectedItem = displayView.getDataList().getSelectionModel().getSelectedItem();
				if(selectedItem!=null) {
					PropertyReader.copy(selectedItem, salesOrderItem);
					displayView.getDataList().getItems().remove(selectedItem);
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
						Set<ConstraintViolation<SalesOrder>> violations = displayView.validate(displayedEntity);
			            if (violations.isEmpty())
			            {
			            	closeService.setSalesOrder(displayedEntity).start();
			            }
			            else
			            {
			               Dialogs.create().nativeTitleBar().title("Entity_create_error.title")
			               .message("Entity_click_to_see_error").showError();
			            }
					}
				});

		displayView.getOkButton().setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
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

		displayView.getClient().setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				CustomerSearchInput customerSearchInput = new CustomerSearchInput();
				customerSearchInput.setMax(50);
				modalCutomerSearchRequestEvent.fire(customerSearchInput);
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
				CashDrawerSearchInput cdsi = new CashDrawerSearchInput();
				cdsi.getEntity().setOpened(Boolean.TRUE);
				CashDrawerAgency cashDrawerAgency = new CashDrawerAgency();
				PropertyReader.copy(securityUtil.getAgency(), cashDrawerAgency);
				cdsi.getEntity().setAgency(cashDrawerAgency);
				cdsi.getFieldNames().addAll(Arrays.asList("opened","agency"));
				cashDrawerSearchService.setSearchInputs(cdsi).start();
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
					
					for (CashDrawer cashDrawer : resultList) {
						displayView.getCashDrawer().getItems().add(new SalesOrderCashDrawer(cashDrawer) );
						
					}
					displayedEntity.setCashDrawer(new SalesOrderCashDrawer(resultList.iterator().next()));
				}

			}
		});

		cashDrawerSearchService.setOnFailed(callFailedEventHandler);

		displayView.getTax().setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				vatSearchService.setSearchInputs(new VATSearchInput()).start();
			}


		});
		vatSearchService.setOnFailed(callFailedEventHandler);

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
				calculateProcessAmont();

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
				displayView.getDataList().getItems().add(editedItem);
				PropertyReader.copy(new SalesOrderItem(), salesOrderItem);
				calculateProcessAmont();

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
				PropertyReader.copy(new SalesOrderItem(), salesOrderItem);
				calculateProcessAmont();

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
				Action showConfirm = Dialogs.create().nativeTitleBar().message("would You Like to Print Invoice ?").showConfirm();
				PropertyReader.copy(entity, displayedEntity);
				salesOrderRequestEvent.fire(new SalesOrder());

			}
		});


		closeService.setOnFailed(callFailedEventHandler);
		vatSearchService.setOnFailed(callFailedEventHandler);

		vatSearchService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				VATSearchService s = (VATSearchService) event.getSource();
				VATSearchResult vsr = s.getValue();
				event.consume();
				s.reset();
				List<VAT> resultList = vsr.getResultList();
				resultList.add(0, null);
				VAT showChoices = Dialogs.create().title("List of Taxes ").showChoices(resultList);
				if(showChoices !=null)
					displayView.getTax().setValue(new SalesOrderVat(showChoices));

			}
		});
		displayView.getDataList().getItems().addListener(new ListChangeListener<SalesOrderItem>() {

			@Override
			public void onChanged(
					javafx.collections.ListChangeListener.Change<? extends SalesOrderItem> c) {
				c.next();
				if(c.getAddedSize()!=0){
					List<? extends SalesOrderItem> addedSubList = c.getAddedSubList();
					for (SalesOrderItem item : addedSubList) {
						item.setSalesOrder(new SalesOrderItemSalesOrder(displayedEntity));
						displayedEntity.setAmountBeforeTax(displayedEntity.getAmountBeforeTax().add(item.getTotalSalePrice()));
						displayedEntity.salesOrderItemsProperty().getValue().add(item);
					}
				}

				if(c.getRemovedSize()!=0){
					List<? extends SalesOrderItem> removed = c.getRemoved();
					for (SalesOrderItem item : removed) {
						displayedEntity.setAmountBeforeTax(displayedEntity.getAmountBeforeTax().subtract(item.getTotalSalePrice()));
						displayedEntity.salesOrderItemsProperty().getValue().remove(item);
					}
				}

				displayedEntity.calculateAmount();

			}
		});

		//		
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
					entity.setArticleName(internalPic);
					ArticleLotSearchInput asi = new ArticleLotSearchInput();
					asi.setEntity(entity);
					asi.getFieldNames().add("internalPic");
					modalArticleLotSearchEvent.fire(asi);
				}
			}
		});

		/*
		 * listen to search button and fire search requested event.
		 */
		displayView.getOrdonnancierButton().setOnAction(new EventHandler<ActionEvent>()
				{
			@Override
			public void handle(ActionEvent e)
			{
				searchRequestedEvent.fire(displayedEntity);
			}
				});


		//      displayView.getConfirmSelectionButton().setOnAction(new EventHandler<ActionEvent>()
		//      {
		//         @Override
		//         public void handle(ActionEvent e)
		//         {
		//            final AssocSelectionEventData<SalesOrder> pendingSelectionRequest = pendingSelectionRequestProperty.get();
		//            if (pendingSelectionRequest == null)
		//               return;
		//            pendingSelectionRequestProperty.set(null);
		//            pendingSelectionRequest.setTargetEntity(displayedEntity);
		//            selectionResponseEvent.fire(pendingSelectionRequest);
		//         				}
		//      });

		//      displayView.getConfirmSelectionButton().visibleProperty().bind(pendingSelectionRequestProperty.isNotNull());
		displayView.addValidators();
		displayView.getSaleOrderItemBar().visibleProperty().bind(displayedEntity.salesOrderStatusProperty().isNotEqualTo(DocumentProcessingState.CLOSED));

	}

	public void display(Pane parent)
	{
		BorderPane rootPane = displayView.getRootPane();
		ObservableList<Node> children = parent.getChildren();
		if (!children.contains(rootPane))
		{
			children.add(rootPane);
		}
	}

	@Override
	public ViewType getViewType()
	{
		return ViewType.DISPLAY;
	}

	private void handleAddSalesOrderItem(SalesOrderItem salesOrderItem) {
		salesOrderItem.calculateTotalAmout();
		if(salesOrderItem.getId()==null){
			salesOrderItemCreateService.setModel(salesOrderItem).start();
		}else {
			salesOrderItemEditService.setSalesOrderItem(salesOrderItem).start();
		}
	}


	/**
	 * Listens to list selection events and bind selected to pane
	 */
	public void handleSelectionEvent(@Observes @EntitySelectionEvent SalesOrder selectedEntity)
	{
		PropertyReader.copy(selectedEntity, displayedEntity);
		//      
		//      
	}

	public void handleAssocSelectionRequest(@Observes(notifyObserver = Reception.ALWAYS) @AssocSelectionRequestEvent AssocSelectionEventData<SalesOrder> eventData)
	{
		pendingSelectionRequestProperty.set(eventData);
		componentSelectionRequestEvent.fire(new ComponentSelectionRequestData(SalesOrder.class.getName()));
		searchRequestedEvent.fire(eventData.getTargetEntity() != null ? eventData.getTargetEntity() : new SalesOrder());
	}

	public void handleArticleLotSearchDone(@Observes @ModalEntitySearchDoneEvent ArticleLot model)
	{
		PropertyReader.copy(SalesOrderItemfromArticle(model), salesOrderItem);
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
			displayView.getClient().setValue(new SalesOrderCustomer(model));
			displayedEntity.setInsurance(new SalesOrderInsurance());
			displayView.getClientAdresse().setText(model.getEmail());
			displayView.getClientPhone().setText(model.getMobile()+"/"+model.getLandLinePhone());
			displayView.getClientcategorie().setText(model.getCustomerCategory().getName()+"-"+model.getCustomerCategory().getDiscountRate());

			Insurrance insurrance = new Insurrance();
			insurrance.setCustomer(new InsurranceCustomer(model));
			InsurranceSearchInput isi = new InsurranceSearchInput();
			isi.setEntity(insurrance);
			isi.setMax(100);
			isi.getFieldNames().add("customer");
			insurranceSearchService.setSearchInputs(isi).start();
		}
		
		
	}
	
	public void handleNewInsurance(Insurrance model){
		displayedEntity.setInsurance(new SalesOrderInsurance(model));
//		new SalesOrderInsurance(model);
//		Insurrance insurrance = new Insurrance();
//		insurrance.setCustomer(model.getCustomer());
//		InsurranceSearchInput isi = new InsurranceSearchInput();
//		isi.setEntity(insurrance);
//		isi.setMax(100);
//		isi.getFieldNames().add("customer");
//		insurranceSearchService.setSearchInputs(isi).start();
		
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

	public void calculateProcessAmont(){
		List<SalesOrderItem> salesOrderItems = displayedEntity.getSalesOrderItems();
		BigDecimal amountHT = BigDecimal.ZERO;
		BigDecimal vatAmount = BigDecimal.ZERO;
		BigDecimal discount = BigDecimal.ZERO;
		for (SalesOrderItem salesOrderItem : salesOrderItems) {
			amountHT=amountHT.add(salesOrderItem.getTotalSalePrice());
		}
		SalesOrderVat salesOrderVat = displayView.getTax().getValue();
		if(salesOrderVat!=null&&salesOrderVat.getId()!=null)
			vatAmount = (amountHT.multiply(salesOrderVat.getRate())).divide(BigDecimal.ZERO,2);
		displayedEntity.setAmountBeforeTax(amountHT);
		displayedEntity.setAmountVAT(vatAmount);
		displayedEntity.setAmountAfterTax(amountHT.add(vatAmount));
	}


	public SalesOrderItem SalesOrderItemfromArticle(ArticleLot al){
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
		soItem.setTotalSalePrice(al.getPurchasePricePU());
		soItem.setSalesOrder(new SalesOrderItemSalesOrder(displayedEntity));
		soItem.setInternalPic(al.getInternalPic());
		return soItem;
	}

}