package org.adorsys.adpharma.client.jpa.salesorder;

import java.math.BigDecimal;
import java.util.List;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.inject.Inject;
import javax.inject.Singleton;

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
import org.adorsys.javafx.crud.extensions.events.SelectedModelEvent;
import org.adorsys.javafx.crud.extensions.login.ErrorDisplay;
import org.adorsys.javafx.crud.extensions.login.ServiceCallFailedEventHandler;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;
import org.adorsys.adpharma.client.jpa.articlelot.ModalArticleLotSearchController;
import org.adorsys.adpharma.client.jpa.articlelot.ModalArticleLotSearchView;
import org.adorsys.adpharma.client.jpa.cashdrawer.CashDrawer;
import org.adorsys.adpharma.client.jpa.cashdrawer.CashDrawerSearchInput;
import org.adorsys.adpharma.client.jpa.cashdrawer.CashDrawerSearchResult;
import org.adorsys.adpharma.client.jpa.cashdrawer.CashDrawerSearchService;
import org.adorsys.adpharma.client.jpa.customer.Customer;
import org.adorsys.adpharma.client.jpa.customer.CustomerSearchInput;
import org.adorsys.adpharma.client.jpa.customer.CustomerSearchResult;
import org.adorsys.adpharma.client.jpa.customer.CustomerSearchService;
import org.adorsys.adpharma.client.jpa.customer.ModalCustomerCreateView;
import org.adorsys.adpharma.client.jpa.delivery.Delivery;
import org.adorsys.adpharma.client.jpa.delivery.DeliveryCloseService;
import org.adorsys.adpharma.client.jpa.deliveryitem.DeliveryItem;
import org.adorsys.adpharma.client.jpa.deliveryitem.DeliveryItemDelivery;
import org.adorsys.adpharma.client.jpa.insurrance.Insurrance;
import org.adorsys.adpharma.client.jpa.insurrance.InsurranceSearchInput;
import org.adorsys.adpharma.client.jpa.insurrance.InsurranceSearchResult;
import org.adorsys.adpharma.client.jpa.insurrance.InsurranceSearchService;
import org.adorsys.adpharma.client.jpa.insurrance.ModalInsurranceCreateView;
import org.adorsys.adpharma.client.jpa.salesorder.SalesOrder;
import org.adorsys.adpharma.client.jpa.salesorderitem.SalesOrderItem;
import org.adorsys.adpharma.client.jpa.salesorderitem.SalesOrderItemSalesOrder;
import org.adorsys.adpharma.client.jpa.vat.VAT;
import org.adorsys.adpharma.client.jpa.vat.VATSearchInput;
import org.adorsys.adpharma.client.jpa.vat.VATSearchResult;
import org.adorsys.adpharma.client.jpa.vat.VATSearchService;
import org.controlsfx.dialog.Dialogs;
import org.omg.PortableInterceptor.INACTIVE;

@Singleton
public class SalesOrderDisplayController implements EntityController
{

	@Inject
	private SalesOrderDisplayView displayView;

	@Inject
	private ModalInsurranceCreateView moInsurranceCreateView;

	@Inject
	private ModalCustomerCreateView modalCustomerCreateView ;

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
	//  services
	@Inject
	private VATSearchService vatSearchService;

	@Inject
	CustomerSearchService customerSearchService;

	@Inject
	CashDrawerSearchService cashDrawerSearchService;

	@Inject
	InsurranceSearchService insurranceSearchService;

	@Inject
	private ServiceCallFailedEventHandler callFailedEventHandler;

	@Inject
	ModalArticleLotSearchController modalArticleLotSearchController;

	@Inject
	private SalesOrder displayedEntity;

	@Inject
	private SalesOrderItem salesOrderItem;


	@PostConstruct
	public void postConstruct()
	{
		//		bind models to the view
		displayView.bind(displayedEntity);
		displayView.bind(salesOrderItem);


		//		control insurreur create dialog
		displayView.getInsurreurButton().setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				moInsurranceCreateView.showDiaLog();

			}
		});

		//	control client create dialog
		displayView.getClientButton().setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				modalCustomerCreateView.showDiaLog();
			}
		});
		//  armed view dependencies combobox
		displayView.getClient().armedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable,
					Boolean oldValue, Boolean newValue) {
				if(newValue)
					customerSearchService.setSearchInputs(new CustomerSearchInput()).start();

			}
		});
		customerSearchService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				CustomerSearchService s = (CustomerSearchService) event.getSource();
				CustomerSearchResult cs = s.getValue();
				event.consume();
				s.reset();
				List<Customer> resultList = cs.getResultList();
				resultList.add(0, null);
				Customer showChoices = Dialogs.create().title("List of Customer ").showChoices(resultList);
				if(showChoices !=null){
					displayView.getClient().setValue(new SalesOrderCustomer(showChoices));
					displayView.getClientAdresse().setText(showChoices.getEmail());
					displayView.getClientPhone().setText(showChoices.getMobile()+"/"+showChoices.getLandLinePhone());
					displayView.getClientcategorie().setText(showChoices.getCustomerCategory().getName()+"-"+showChoices.getCustomerCategory().getDiscountRate());

				}


			}
		});
		customerSearchService.setOnFailed(callFailedEventHandler);
		displayView.getInsurrer().armedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable,
					Boolean oldValue, Boolean newValue) {
				if(newValue)
					insurranceSearchService.setSearchInputs(new InsurranceSearchInput()).start(); // TODO : provider et service which return insurreur for specifique clien

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
				resultList.add(0, null);
				Insurrance showChoices = Dialogs.create().title("List of Inssurer ").showChoices(resultList);
				if(showChoices !=null){
					displayView.getInsurrer().setValue(new SalesOrderInsurance(showChoices));
					displayView.getCoverageRate().setText(showChoices.getCoverageRate()+" %");

				}

			}
		});
		insurranceSearchService.setOnFailed(callFailedEventHandler);
		displayView.getCashDrawer().armedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable,
					Boolean oldValue, Boolean newValue) {
				if(newValue){
					cashDrawerSearchService.setSearchInputs(new CashDrawerSearchInput()).start();
				}

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
				resultList.add(0, null);
				CashDrawer showChoices = Dialogs.create().title("List of Cash Drawer ").showChoices(resultList);
				if(showChoices !=null)
					displayView.getCashDrawer().setValue(new SalesOrderCashDrawer(showChoices));

			}
		});
		cashDrawerSearchService.setOnFailed(callFailedEventHandler);
		displayView.getTax().armedProperty().addListener(new ChangeListener<Boolean>()
				{

			@Override
			public void changed(ObservableValue<? extends Boolean> observableValue,
					Boolean oldValue, Boolean newValue)
			{
				if (newValue){
					vatSearchService.setSearchInputs(new VATSearchInput()).start();
				}

			}

				});
		vatSearchService.setOnFailed(callFailedEventHandler);

		callFailedEventHandler.setErrorDisplay(new ErrorDisplay() {

			@Override
			protected void showError(Throwable exception) {
				Dialogs.create().nativeTitleBar().showException(exception);

			}
		});


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
					modalArticleLotSearchController.handleArticleSearchInput(articleName);
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

		displayView.getCloseButton().setOnAction(new EventHandler<ActionEvent>()
				{
			@Override
			public void handle(ActionEvent e)
			{
				editRequestEvent.fire(displayedEntity);
			}
				});

		displayView.getRemoveButton().setOnAction(new EventHandler<ActionEvent>()
				{
			@Override
			public void handle(ActionEvent e)
			{
				removeRequest.fire(displayedEntity);
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

	/**
	 * Listens to list selection events and bind selected to pane
	 */
	public void handleSelectionEvent(@Observes @EntitySelectionEvent SalesOrder selectedEntity)
	{
		PropertyReader.copy(selectedEntity, displayedEntity);
		//      displayView.getRemoveButton().setDisable(false);
		//      displayView.getEditButton().setDisable(false);
	}

	public void handleAssocSelectionRequest(@Observes(notifyObserver = Reception.ALWAYS) @AssocSelectionRequestEvent AssocSelectionEventData<SalesOrder> eventData)
	{
		pendingSelectionRequestProperty.set(eventData);
		componentSelectionRequestEvent.fire(new ComponentSelectionRequestData(SalesOrder.class.getName()));
		searchRequestedEvent.fire(eventData.getTargetEntity() != null ? eventData.getTargetEntity() : new SalesOrder());
	}

	/**
	 * This is the only time where the bind method is called on this object.
	 * @param model
	 */
	public void handleNewModelEvent(@Observes @SelectedModelEvent SalesOrder model)
	{
		this.displayedEntity = model;
		displayView.bind(this.displayedEntity);
	}

}