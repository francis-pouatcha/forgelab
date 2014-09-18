package org.adorsys.adpharma.client.jpa.salesorder;

import java.awt.Desktop;
import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.chart.XYChart.Series;
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
import org.adorsys.adpharma.client.events.PrintCustomerInvoiceRequestedEvent;
import org.adorsys.adpharma.client.events.PrintCustomerVoucherRequestEvent;
import org.adorsys.adpharma.client.events.PrintPaymentReceiptRequestedEvent;
import org.adorsys.adpharma.client.events.SalesOrderId;
import org.adorsys.adpharma.client.jpa.accessroleenum.AccessRoleEnum;
import org.adorsys.adpharma.client.jpa.article.Article;
import org.adorsys.adpharma.client.jpa.article.ArticleSearchInput;
import org.adorsys.adpharma.client.jpa.customer.Customer;
import org.adorsys.adpharma.client.jpa.customer.CustomerSearchInput;
import org.adorsys.adpharma.client.jpa.customer.CustomerSearchResult;
import org.adorsys.adpharma.client.jpa.customer.CustomerSearchService;
import org.adorsys.adpharma.client.jpa.customerinvoice.CustomerInvoice;
import org.adorsys.adpharma.client.jpa.customerinvoice.CustomerInvoiceChartDataService;
import org.adorsys.adpharma.client.jpa.customerinvoice.CustomerInvoiceSalesOrder;
import org.adorsys.adpharma.client.jpa.customerinvoice.CustomerInvoiceSearchInput;
import org.adorsys.adpharma.client.jpa.customerinvoice.CustomerInvoiceSearchResult;
import org.adorsys.adpharma.client.jpa.customerinvoice.CustomerInvoiceSearchService;
import org.adorsys.adpharma.client.jpa.customervoucher.CustomerVoucher;
import org.adorsys.adpharma.client.jpa.customervoucher.CustomerVoucherPrintTemplatePdf;
import org.adorsys.adpharma.client.jpa.customervoucher.CustomerVoucherSearchBySalesOrderService;
import org.adorsys.adpharma.client.jpa.documentprocessingstate.DocumentProcessingState;
import org.adorsys.adpharma.client.jpa.login.Login;
import org.adorsys.adpharma.client.jpa.payment.Payment;
import org.adorsys.adpharma.client.jpa.paymentcustomerinvoiceassoc.PaymentCustomerInvoiceAssoc;
import org.adorsys.adpharma.client.jpa.paymentcustomerinvoiceassoc.PaymentCustomerInvoiceAssocSearchInput;
import org.adorsys.adpharma.client.jpa.paymentcustomerinvoiceassoc.PaymentCustomerInvoiceAssocSearchResult;
import org.adorsys.adpharma.client.jpa.paymentcustomerinvoiceassoc.PaymentCustomerInvoiceAssocSearchService;
import org.adorsys.adpharma.client.jpa.salesorderitem.SalesOrderItem;
import org.adorsys.adpharma.client.jpa.salesorderitem.SalesOrderItemSalesOrder;
import org.adorsys.adpharma.client.jpa.salesorderitem.SalesOrderItemSearchInput;
import org.adorsys.adpharma.client.jpa.salesorderitem.SalesOrderItemSearchResult;
import org.adorsys.adpharma.client.jpa.salesorderitem.SalesOrderItemSearchService;
import org.adorsys.adpharma.client.utils.ChartData;
import org.adorsys.adpharma.client.utils.DateHelper;
import org.adorsys.javafx.crud.extensions.EntityController;
import org.adorsys.javafx.crud.extensions.ViewType;
import org.adorsys.javafx.crud.extensions.events.EntityCreateDoneEvent;
import org.adorsys.javafx.crud.extensions.events.EntityCreateRequestedEvent;
import org.adorsys.javafx.crud.extensions.events.EntityEditCanceledEvent;
import org.adorsys.javafx.crud.extensions.events.EntityEditDoneEvent;
import org.adorsys.javafx.crud.extensions.events.EntityListPageIndexChangedEvent;
import org.adorsys.javafx.crud.extensions.events.EntityRemoveDoneEvent;
import org.adorsys.javafx.crud.extensions.events.EntitySearchDoneEvent;
import org.adorsys.javafx.crud.extensions.events.EntitySearchRequestedEvent;
import org.adorsys.javafx.crud.extensions.events.EntitySelectionEvent;
import org.adorsys.javafx.crud.extensions.events.ModalEntitySearchDoneEvent;
import org.adorsys.javafx.crud.extensions.events.ModalEntitySearchRequestedEvent;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.login.ErrorDisplay;
import org.adorsys.javafx.crud.extensions.login.RolesEvent;
import org.adorsys.javafx.crud.extensions.login.ServiceCallFailedEventHandler;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;
import org.adorsys.javafx.crud.extensions.utils.PaginationUtils;
import org.apache.commons.lang3.StringUtils;
import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.Dialogs;

@Singleton
public class SalesOrderListController implements EntityController
{
	@Inject
	private SalesOrderListView listView;

	@Inject
	@EntitySelectionEvent
	private Event<SalesOrder> processSalesOrderRequestedEvent;

	@Inject
	private CustomerSearchService customerSearchService;
	@Inject
	private ServiceCallFailedEventHandler customerSearchServiceCallFailedEventHandler;

	@Inject
	@EntityCreateRequestedEvent
	private Event<SalesOrder> salesOrderCreateRequestEvent;

	@Inject
	private CustomerInvoiceSearchService customerInvoiceSearchService ;

	@Inject
	@ModalEntitySearchRequestedEvent
	private Event<ArticleSearchInput> articleSearchInput;

	@Inject
	private SalesOrderSearchService salesOrderSearchService;
	@Inject
	private ServiceCallFailedEventHandler salesOrderSearchServiceCallFailedEventHandler;

	@Inject
	private SalesOrderItemSearchService salesOrderItemSearchService;
	@Inject
	private ServiceCallFailedEventHandler serviceCallFailedEventHandler;

	@Inject
	private SalesOrderRemoveService salesOrderRemoveService ;

	@Inject
	private ServiceCallFailedEventHandler chartDataSearchServiceCallFailedEventHandler;

	@Inject
	private ServiceCallFailedEventHandler salesOrderRemoveServiceCallFailedEventHandler;

	@Inject
	@EntityListPageIndexChangedEvent
	private Event<SalesOrderSearchResult> entityListPageIndexChangedEvent;

	@Inject
	@EntitySearchRequestedEvent
	private Event<SalesOrderAdvenceSearchData> advenceSearchRequestEvent;

	private SalesOrderSearchResult searchResult;

	@Inject
	private PaymentCustomerInvoiceAssocSearchService paymentCustomerInvoiceAssocSearchService ;

	@Inject
	private PaymentCustomerInvoiceAssocSearchInput assocSearchInput ;

	@Inject 
	SalesOrderSearchInput searchInput;

	@Inject
	CustomerSearchInput customerSearchInput ;

	@Inject
	@PrintPaymentReceiptRequestedEvent
	private Event<PaymentId> printPaymentReceiptRequestedEvent;


	@Inject
	@Bundle({ CrudKeys.class,CustomerVoucher.class})
	private ResourceBundle resourceBundle;

	@Inject
	private SalesOrderRegistration registration;

	@Inject
	@PrintCustomerInvoiceRequestedEvent
	private Event<SalesOrderId> printCustomerInvoiceRequestedEvent;

	private SalesOrderId selectedSalesOrderId;

	@Inject
	private CustomerInvoiceChartDataService customerInvoiceChartDataService ;

	@Inject
	private CustomerVoucherSearchBySalesOrderService voucherSearchBySalesOrderService ;

	@Inject
	private SalesOrderLoadService salesOrderLoadService ;

	@Inject
	@PrintCustomerVoucherRequestEvent
	private Event<SalesOrder> salesOrderVoucherPrintRequestEvent ;

	@Inject
	private SalesOrderChangeCustomerService changeCustomerService ;

	@Inject
	private Locale locale;

	boolean printWhithDiscount = true;

	@Inject
	private SecurityUtil securityUtil;
	@PostConstruct
	public void postConstruct()
	{
		customerSearchInput.setMax(-1);
		listView.getCreateButton().disableProperty().bind(registration.canCreateProperty().not());
		listView.bind(searchInput);
		searchInput.setMax(100);

		listView.getYearList().getItems().setAll(DateHelper.getYears());
		listView.getAdvenceSearchButton().setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				SalesOrderAdvenceSearchData searchData = new SalesOrderAdvenceSearchData();
				advenceSearchRequestEvent.fire(searchData);

			}
		});

		listView.getEmptyArticle().selectedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable,
					Boolean oldValue, Boolean newValue) {
				if(newValue){
					listView.getChartArticleList().setValue(null);
				}

			}
		});

		listView.getDataList().getSelectionModel().selectedItemProperty().addListener(new ChangeListener<SalesOrder>() {
			@Override
			public void changed(ObservableValue<? extends SalesOrder> observable,
					SalesOrder oldValue, SalesOrder newValue) {
				if(newValue!=null){
					selectedSalesOrderId = new SalesOrderId(newValue.getId());
					listView.getRemoveButton().disableProperty().unbind();
					listView.getPrintInvoiceButtonn().disableProperty().unbind();
					listView.getPrintProformaButton().disableProperty().unbind();
					listView.getPrintTicketButton().disableProperty().unbind();
					listView.getPrintVoucherButton().disableProperty().unbind();
					listView.getPrintVoucherButton().disableProperty().bind(new SimpleBooleanProperty(!newValue.getAlreadyReturned()));
					listView.getRemoveButton().disableProperty().bind(newValue.salesOrderStatusProperty().isEqualTo(DocumentProcessingState.CLOSED));
					//					listView.getPrintInvoiceButtonn().disableProperty().bind(newValue.salesOrderStatusProperty().isNotEqualTo(DocumentProcessingState.CLOSED));
					listView.getPrintTicketButton().disableProperty().bind(new SimpleBooleanProperty(!newValue.getCashed()));
					listView.getPrintProformaButton().disableProperty().bind(new SimpleBooleanProperty(newValue.getCashed()));
					SalesOrderItemSearchInput sosi = new SalesOrderItemSearchInput();
					sosi.setMax(-1);
					sosi.getEntity().setSalesOrder(new SalesOrderItemSalesOrder(newValue));
					sosi.getFieldNames().add("salesOrder");
					salesOrderItemSearchService.setSearchInputs(sosi).start();
				}

			}
		});

		listView.getPrintVoucherButton().setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				SalesOrder selectedItem = listView.getDataList().getSelectionModel().getSelectedItem();
				if(selectedItem!=null){
					salesOrderVoucherPrintRequestEvent.fire(selectedItem);
				}
			}
		});

		voucherSearchBySalesOrderService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				CustomerVoucherSearchBySalesOrderService s = (CustomerVoucherSearchBySalesOrderService) event.getSource();
				CustomerVoucher result = s.getValue();
				event.consume();
				s.reset();
				Login login = securityUtil.getConnectedUser();
				SalesOrder selectedSalesOrder = listView.getDataList().getSelectionModel().getSelectedItem();
				if(result !=null && selectedSalesOrder!=null){
					try {
						CustomerVoucherPrintTemplatePdf pdfRepportTemplate = new CustomerVoucherPrintTemplatePdf(result, resourceBundle, locale, login,selectedSalesOrder.getSoNumber());
						pdfRepportTemplate.addItems();
						pdfRepportTemplate.closeDocument();
						Desktop.getDesktop().open(new File(pdfRepportTemplate.getFileName()));
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		voucherSearchBySalesOrderService.setOnFailed(serviceCallFailedEventHandler);


		salesOrderItemSearchService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				SalesOrderItemSearchService s = (SalesOrderItemSearchService) event.getSource();
				SalesOrderItemSearchResult result = s.getValue();
				event.consume();
				s.reset();
				List<SalesOrderItem> resultList = result.getResultList();
				listView.getDataListItem().getItems().setAll(resultList);
			}
		});
		salesOrderItemSearchService.setOnFailed(serviceCallFailedEventHandler);
		serviceCallFailedEventHandler.setErrorDisplay(new ErrorDisplay() {
			@Override
			protected void showError(Throwable exception) {
				Dialogs.create().nativeTitleBar().showException(exception);

			}
		});

		chartDataSearchServiceCallFailedEventHandler.setErrorDisplay(new ErrorDisplay() {

			@Override
			protected void showError(Throwable exception) {
				Dialogs.create().nativeTitleBar().showException(exception);

			}
		});


		listView.getComputeButton().setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Integer selectedYears = listView.getYearList().getSelectionModel().getSelectedItem();
				Customer selectedCustomer = listView.getChartClientList().getSelectionModel().getSelectedItem();
				Article article = listView.getChartArticleList().getSelectionModel().getSelectedItem();
				if(selectedYears!=null){
					SalesStattisticsDataSearchInput chartDataSearchInput = new SalesStattisticsDataSearchInput();
					chartDataSearchInput.setYears(selectedYears);
					if(selectedCustomer!=null&&selectedCustomer.getId()!=null)
						chartDataSearchInput.setCustomer(selectedCustomer);
					if(article!=null&&article.getId()!=null)
						chartDataSearchInput.setArticle(article);
					customerInvoiceChartDataService.setModel(chartDataSearchInput).start();
				}

			}
		});
		customerInvoiceChartDataService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				CustomerInvoiceChartDataService s = (CustomerInvoiceChartDataService) event.getSource();
				SalesStatisticsDataSearchResult result = s.getValue();
				event.consume();
				s.reset();
				Iterator<ChartData> iterator = result.getChartData().iterator();
				List<Series<String, BigDecimal>> pieChartData = ChartData.toBarChartData( result.getChartData());
				listView.getPieChart().getData().setAll(pieChartData);
			}
		});
		customerInvoiceChartDataService.setOnFailed(chartDataSearchServiceCallFailedEventHandler);

		/*
		 * listen to remove button .
		 */
		listView.getRemoveButton().setOnAction(new EventHandler<ActionEvent>()
				{
			@Override
			public void handle(ActionEvent e)
			{							
				SalesOrder selectedItem = listView.getDataList().getSelectionModel().getSelectedItem();
				if(selectedItem!=null){
					Action showConfirm = Dialogs.create().
							nativeTitleBar().
							message(resourceBundle.getString("Entity_confirm_remove.title")).showConfirm();
					if(showConfirm==Dialog.Actions.YES){
						salesOrderRemoveService.setEntity(selectedItem).start();
					}
				}

			}
				});


		listView.getCustomer().setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				customerSearchService.setSearchInputs(customerSearchInput).start();

			}
		});
		listView.getChartClientList().setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				customerSearchService.setSearchInputs(customerSearchInput).start();

			}
		});

		listView.getChartArticleList().setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				articleSearchInput.fire(new ArticleSearchInput());

			}
		});
		listView.getProcessButton().setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				SalesOrder selectedItem = listView.getDataList().getSelectionModel().getSelectedItem();
				if(selectedItem!= null) 
					salesOrderLoadService.setId(selectedItem.getId()).start();


			}
		});

		/*
		 * listen to search button and fire search activated event.
		 */
		listView.getSearchButton().setOnAction(new EventHandler<ActionEvent>()
				{
			@Override
			public void handle(ActionEvent e)
			{						
				searchInput.setFieldNames(readSearchAttributes());
				searchInput.setMax(30);
				salesOrderSearchService.setSearchInputs(searchInput).start();

			}

				});

		salesOrderLoadService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				SalesOrderLoadService s = (SalesOrderLoadService) event.getSource();
				SalesOrder result = s.getValue();
				event.consume();
				s.reset();
				processSalesOrderRequestedEvent.fire(result);

			}
		});
		salesOrderLoadService.setOnFailed(salesOrderRemoveServiceCallFailedEventHandler);
		salesOrderRemoveService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				SalesOrderRemoveService s = (SalesOrderRemoveService) event.getSource();
				SalesOrder result = s.getValue();
				event.consume();
				s.reset();
				listView.getDataList().getItems().remove(result);

			}
		});
		salesOrderRemoveService.setOnFailed(salesOrderRemoveServiceCallFailedEventHandler);
		salesOrderRemoveServiceCallFailedEventHandler.setErrorDisplay(new ErrorDisplay() {

			@Override
			protected void showError(Throwable exception) {
				Dialogs.create().nativeTitleBar().showException(exception);

			}
		});

		customerSearchService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				CustomerSearchService s = (CustomerSearchService) event.getSource();
				CustomerSearchResult result = s.getValue();
				event.consume();
				s.reset();
				ArrayList<SalesOrderCustomer> soc = new ArrayList<SalesOrderCustomer>();
				List<Customer> resultList = result.getResultList();
				Customer emptyCustomer = new Customer();
				emptyCustomer.setFullName("TOUS LES CLIENTS");
				resultList.add(0,emptyCustomer);
				for (Customer customer : resultList) {
					soc.add(new SalesOrderCustomer(customer));
				}
				listView.getCustomer().getItems().setAll(soc);
				listView.getCustomer().getSelectionModel().select(0);
				listView.getChartClientList().getItems().setAll(resultList);

			}
		});
		customerSearchService.setOnFailed(customerSearchServiceCallFailedEventHandler);
		customerSearchServiceCallFailedEventHandler.setErrorDisplay(new ErrorDisplay() {

			@Override
			protected void showError(Throwable exception) {
				Dialogs.create().nativeTitleBar().showException(exception);

			}
		});

		salesOrderSearchService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				SalesOrderSearchService s = (SalesOrderSearchService) event.getSource();
				searchResult = s.getValue();
				event.consume();
				s.reset();
				handleSearchResult(searchResult);

			}
		});
		salesOrderSearchService.setOnFailed(salesOrderSearchServiceCallFailedEventHandler);
		salesOrderSearchServiceCallFailedEventHandler.setErrorDisplay(new ErrorDisplay() {

			@Override
			protected void showError(Throwable exception) {
				Dialogs.create().nativeTitleBar().showException(exception);

			}
		});


		listView.getCreateButton().setOnAction(new EventHandler<ActionEvent>()
				{
			@Override
			public void handle(ActionEvent e)
			{
				salesOrderCreateRequestEvent.fire(new SalesOrder());
			}
				});

		//		paginate
		listView.getPagination().currentPageIndexProperty().addListener(new ChangeListener<Number>()
				{
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue)
			{
				if (searchResult == null)
					return;
				if (searchResult.getSearchInput() == null)
					searchResult.setSearchInput(new SalesOrderSearchInput());
				int start = 0;
				int max = searchResult.getSearchInput().getMax();
				if (newValue != null)
				{
					start = new BigDecimal(newValue.intValue()).multiply(new BigDecimal(max)).intValue();
				}
				searchResult.getSearchInput().setStart(start);
				entityListPageIndexChangedEvent.fire(searchResult);

			}
				});

		listView.getPrintInvoiceButtonn().setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if(selectedSalesOrderId==null || selectedSalesOrderId.getId()==null) return;
				SalesOrder selectedItem = listView.getDataList().getSelectionModel().getSelectedItem();
				String customerName = null;
//				if(selectedItem!=null && "000000001".equals(selectedItem.getCustomer().getSerialNumber()))
					customerName = Dialogs.create().message("Nom du client : ").showTextInput();
				selectedSalesOrderId.setCustomerName(customerName);
				selectedSalesOrderId.setProformat(false);
				printCustomerInvoiceRequestedEvent.fire(selectedSalesOrderId);	
			}
		});

		listView.getPrintProformaButton().setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if(selectedSalesOrderId==null || selectedSalesOrderId.getId()==null) return;
				SalesOrder selectedItem = listView.getDataList().getSelectionModel().getSelectedItem();
				String customerName = null;
//				if(selectedItem!=null && "000000001".equals(selectedItem.getCustomer().getSerialNumber()))
					customerName = Dialogs.create().message("Nom du client : ").showTextInput();
				selectedSalesOrderId.setCustomerName(customerName);
				selectedSalesOrderId.setProformat(true);
				printCustomerInvoiceRequestedEvent.fire(selectedSalesOrderId);	
			}
		});

		listView.getPrintTicketButton().setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				SalesOrder selectedItem = listView.getDataList().getSelectionModel().getSelectedItem();
				if(selectedItem!=null){
					CustomerInvoiceSearchInput invoiceSearchInput = new CustomerInvoiceSearchInput();
					invoiceSearchInput.getEntity().setSalesOrder(new CustomerInvoiceSalesOrder(selectedItem));
					invoiceSearchInput.getFieldNames().add("salesOrder");
					printWhithDiscount = true;
					customerInvoiceSearchService.setSearchInputs(invoiceSearchInput).start();

				}
			}
		});

		listView.getUnDiscountReceipt().setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				SalesOrder selectedItem = listView.getDataList().getSelectionModel().getSelectedItem();
				if(selectedItem!=null){
					printWhithDiscount = false;
					CustomerInvoiceSearchInput invoiceSearchInput = new CustomerInvoiceSearchInput();
					invoiceSearchInput.getEntity().setSalesOrder(new CustomerInvoiceSalesOrder(selectedItem));
					invoiceSearchInput.getFieldNames().add("salesOrder");
					customerInvoiceSearchService.setSearchInputs(invoiceSearchInput).start();

				}
			}
		});



		customerInvoiceSearchService.setOnFailed(serviceCallFailedEventHandler);
		customerInvoiceSearchService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				CustomerInvoiceSearchService s = (CustomerInvoiceSearchService) event.getSource();
				CustomerInvoiceSearchResult results = s.getValue();
				event.consume();
				s.reset();
				List<CustomerInvoice> resultList = results.getResultList();
				if(!resultList.isEmpty()){
					for (CustomerInvoice customerInvoice : resultList) {
						if(customerInvoice.getCashed()){
							assocSearchInput.getEntity().setTarget(customerInvoice);
							assocSearchInput.getFieldNames().add("target");
							paymentCustomerInvoiceAssocSearchService.setSearchInputs(assocSearchInput).start();
							break;
						}
					}
				}

			}
		});

		paymentCustomerInvoiceAssocSearchService.setOnFailed(serviceCallFailedEventHandler);
		paymentCustomerInvoiceAssocSearchService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				PaymentCustomerInvoiceAssocSearchService s = (PaymentCustomerInvoiceAssocSearchService) event.getSource();
				PaymentCustomerInvoiceAssocSearchResult results = s.getValue();
				event.consume();
				s.reset();
				List<PaymentCustomerInvoiceAssoc> resultList = results.getResultList();
				if(!resultList.isEmpty()){
					PaymentCustomerInvoiceAssoc paymentCustomerInvoiceAssoc = resultList.iterator().next();
					Payment payment = paymentCustomerInvoiceAssoc.getSource();
					PaymentId paymentId = new PaymentId(payment.getId());
					SalesOrder selectedItem = listView.getDataList().getSelectionModel().getSelectedItem();
					String customerName = null;
//					if(selectedItem!=null && "000000001".equals(selectedItem.getCustomer().getSerialNumber()))
						customerName = Dialogs.create().message("Nom du client : ").showTextInput();
					paymentId.setCustomerName(customerName);
					paymentId.setPrintWhithoutDiscount(printWhithDiscount);

					printPaymentReceiptRequestedEvent.fire(paymentId);

				}


			}
		});

		changeCustomerService.setOnFailed(serviceCallFailedEventHandler);
		changeCustomerService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				SalesOrderChangeCustomerService s = (SalesOrderChangeCustomerService) event.getSource();
				SalesOrder sales = s.getValue();
				event.consume();
				s.reset();
				printCustomerInvoiceRequestedEvent.fire(new SalesOrderId(sales.getId()));

			}
		});
	}

	@Override
	public void display(Pane parent)
	{
		BorderPane rootPane = listView.getRootPane();
		ObservableList<Node> children = parent.getChildren();
		if (!children.contains(rootPane))
		{
			children.add(rootPane);
		}
		listView.getSalesOrderStatus().setValue(DocumentProcessingState.CLOSED);
	}

	@Override
	public ViewType getViewType()
	{
		return ViewType.LIST;
	}

	/**
	 * Handle search results. But the switch of displays is centralized
	 * in the main salesOrder controller.
	 * 
	 * @param entities
	 */
	public void handleSearchResult(@Observes @EntitySearchDoneEvent SalesOrderSearchResult searchResult)
	{
		this.searchResult = searchResult;
		List<SalesOrder> entities = searchResult.getResultList();
		if (entities == null)
			entities = new ArrayList<SalesOrder>();
		listView.getDataList().getItems().setAll(entities);
		int maxResult = searchResult.getSearchInput() != null ? searchResult.getSearchInput().getMax() : 100;
		int pageCount = PaginationUtils.computePageCount(searchResult.getCount(), maxResult);
		listView.getPagination().setPageCount(pageCount);
		int firstResult = searchResult.getSearchInput() != null ? searchResult.getSearchInput().getStart() : 0;
		int pageIndex = PaginationUtils.computePageIndex(firstResult, searchResult.getCount(), maxResult);
		listView.getPagination().setCurrentPageIndex(pageIndex);

	}

	public void handleCreatedEvent(@Observes @EntityCreateDoneEvent SalesOrder createdEntity)
	{
		listView.getDataList().getItems().add(0, createdEntity);
	}

	public void handleRemovedEvent(@Observes @EntityRemoveDoneEvent SalesOrder removedEntity)
	{
		listView.getDataList().getItems().remove(removedEntity);
	}

	public void handleEditDoneEvent(@Observes @EntityEditDoneEvent SalesOrder selectedEntity)
	{
		int selectedIndex = listView.getDataList().getItems().indexOf(selectedEntity);
		if (selectedIndex <= -1)
			return;
		SalesOrder entity = listView.getDataList().getItems().get(selectedIndex);
		PropertyReader.copy(selectedEntity, entity);

		ArrayList<SalesOrder> arrayList = new ArrayList<SalesOrder>(listView.getDataList().getItems());
		listView.getDataList().getItems().clear();
		listView.getDataList().getItems().addAll(arrayList);
		listView.getDataList().getSelectionModel().select(selectedEntity);
	}

	public void handleEditCanceledEvent(@Observes @EntityEditCanceledEvent SalesOrder selectedEntity)
	{
		int selectedIndex = listView.getDataList().getItems().indexOf(selectedEntity);
		if (selectedIndex <= -1)
			return;
		SalesOrder entity = listView.getDataList().getItems().get(selectedIndex);
		PropertyReader.copy(selectedEntity, entity);

		ArrayList<SalesOrder> arrayList = new ArrayList<SalesOrder>(listView.getDataList().getItems());
		listView.getDataList().getItems().clear();
		listView.getDataList().getItems().addAll(arrayList);
		listView.getDataList().getSelectionModel().select(selectedEntity);
	}

	public List<String> readSearchAttributes(){
		ArrayList<String> seachAttributes = new ArrayList<String>() ;
		String soNumber = searchInput.getEntity().getSoNumber();
		SalesOrderCustomer customer = searchInput.getEntity().getCustomer();
		DocumentProcessingState state = searchInput.getEntity().getSalesOrderStatus();

		if(StringUtils.isNotBlank(soNumber)) seachAttributes.add("soNumber");
		if(customer!=null && customer.getId()!=null) seachAttributes.add("customer");
		if(state!=null) seachAttributes.add("salesOrderStatus") ;
		return seachAttributes;

	}
	public void reset() {
		listView.getDataList().getItems().clear();
		listView.getDataListItem().getItems().clear();
	}

	public void handleCustomerVoucherPrint(@Observes SalesOrder salesOrder){
		if(salesOrder.getId()!=null){
			voucherSearchBySalesOrderService.setSalesOrder(salesOrder).start();
		}
	}

	public void HandleArticleSearchDone(@Observes @ModalEntitySearchDoneEvent Article article){
		listView.getChartArticleList().setValue(article);
		listView.getEmptyArticle().setSelected(false);
	}

	public void handleRolesEvent(@Observes(notifyObserver=Reception.ALWAYS) @RolesEvent Set<String> roles){
		if(roles.contains(AccessRoleEnum.MANAGER.name())){
			listView.getTurnoverTab().setDisable(false);
		}else {
			listView.getTurnoverTab().setDisable(true);
		}

		if(roles.contains(AccessRoleEnum.RETURN_SALES_PERM.name())||roles.contains(AccessRoleEnum.MANAGER.name())){
			listView.getPrintVoucherButton().setVisible(true);
		}else {
			listView.getPrintVoucherButton().setVisible(false);
		}
	}


}
