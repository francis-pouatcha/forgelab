package org.adorsys.adpharma.client.jpa.debtstatement;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javafx.beans.property.SimpleBooleanProperty;
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
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.adpharma.client.access.SecurityUtil;
import org.adorsys.adpharma.client.events.PaymentRequestEvent;
import org.adorsys.adpharma.client.jpa.customer.Customer;
import org.adorsys.adpharma.client.jpa.customer.CustomerSearchInput;
import org.adorsys.adpharma.client.jpa.customerinvoice.CustomerInvoice;
import org.adorsys.adpharma.client.jpa.customerinvoice.CustomerInvoiceSearchResult;
import org.adorsys.adpharma.client.jpa.customerinvoice.CustomerInvoiceSearchService;
import org.adorsys.adpharma.client.jpa.debtstatementcustomerinvoiceassoc.DebtStatementCustomerInvoiceAssoc;
import org.adorsys.adpharma.client.jpa.debtstatementcustomerinvoiceassoc.DebtStatementCustomerInvoiceAssocRemoveService;
import org.adorsys.adpharma.client.jpa.debtstatementcustomerinvoiceassoc.DebtStatementCustomerInvoiceAssocSearchInput;
import org.adorsys.adpharma.client.jpa.debtstatementcustomerinvoiceassoc.DebtStatementCustomerInvoiceAssocSearchResult;
import org.adorsys.adpharma.client.jpa.debtstatementcustomerinvoiceassoc.DebtStatementCustomerInvoiceAssocSearchService;
import org.adorsys.adpharma.client.jpa.documentprocessingstate.DocumentProcessingState;
import org.adorsys.adpharma.client.jpa.login.Login;
import org.adorsys.adpharma.client.jpa.payment.PaymentSearchInput;
import org.adorsys.adpharma.client.jpa.payment.PaymentSearchResult;
import org.adorsys.adpharma.client.jpa.payment.PaymentSearchService;
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
import org.adorsys.javafx.crud.extensions.events.HideProgressBarRequestEvent;
import org.adorsys.javafx.crud.extensions.events.ModalEntitySearchDoneEvent;
import org.adorsys.javafx.crud.extensions.events.ModalEntitySearchRequestedEvent;
import org.adorsys.javafx.crud.extensions.events.ShowProgressBarRequestEvent;
import org.adorsys.javafx.crud.extensions.login.ErrorDisplay;
import org.adorsys.javafx.crud.extensions.login.ServiceCallFailedEventHandler;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;
import org.adorsys.javafx.crud.extensions.utils.PaginationUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.Dialogs;

import com.google.common.collect.Lists;
import com.lowagie.text.DocumentException;

@Singleton
public class DebtStatementListController implements EntityController
{

	@Inject
	private DebtStatementListView listView;

	@Inject
	@EntitySelectionEvent
	private Event<DebtStatement> selectionEvent;

	@Inject
	@EntitySearchRequestedEvent
	private Event<DebtStatement> searchRequestedEvent;

	@Inject
	private DebtStatementRemoveService removeService;

	@PaymentRequestEvent
	private Event<DebtStatement> debstatementPaymentCreateRequestEvent;


	@Inject
	@EntityCreateRequestedEvent
	private Event<DebtStatementProcessingData> createRequestedEvent;

	@Inject
	@EntityListPageIndexChangedEvent
	private Event<DebtStatementSearchResult> entityListPageIndexChangedEvent;

	@Inject
	@ModalEntitySearchRequestedEvent
	private Event<CustomerSearchInput>  insurrerSearchRequestEvent;

	@Inject
	private DebtStatementSearchService searchService ;

	@Inject
	private DebtStatementSearchInput searchInput;

	@Inject
	private DebtStatementSearchResult searchResult;

	@Inject
	private DebtStatementRegistration registration;

	@Inject
	private ServiceCallFailedEventHandler callFailedEventHandler ;

	@Inject
	private CustomerInvoiceSearchService customerInvoiceSearchService ;

	@Inject
	private SecurityUtil securityUtil ;

	@Inject
	@ShowProgressBarRequestEvent
	private Event<Object> showprogressEvent ;

	@Inject
	@HideProgressBarRequestEvent
	private Event<Object> hideProgressEvent ;

	@Inject
	private DebtStatementEditService debtStatementEditService;

	@Inject
	private DebtStatementCustomerInvoiceAssocSearchService statementCustomerInvoiceAssocSearchService ;

	@Inject
	private DebtStatementCustomerInvoiceAssocSearchService statementCustomerInvoiceAssocSearchForRemoveServiceService ;

	@Inject
	private DebtStatementCustomerInvoiceAssocRemoveService statementCustomerInvoiceAssocRemoveService ;

	@Inject
	private Locale locale ;
	
	@Inject
	private PaymentSearchService  paymentSearchService;

	@PostConstruct
	public void postConstruct()
	{
		listView.getCreateButton().disableProperty().bind(registration.canCreateProperty().not());
		listView.getRemoveButton().disableProperty().bind(removeService.runningProperty());
		listView.getPrintButton().disableProperty().bind(customerInvoiceSearchService.runningProperty());
		searchInput.setMax(30);
		listView.bind(searchInput);

		callFailedEventHandler.setErrorDisplay(new ErrorDisplay() {

			@Override
			protected void showError(Throwable exception) {
				Dialogs.create().showException(exception);

			}
		});

		listView.getCashButton().setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				DebtStatement selectedItem = listView.getDataList().getSelectionModel().getSelectedItem();
				if(selectedItem!=null){
					if(!selectedItem.getSettled()){
						Action showConfirm = Dialogs.create().message("Confirmer l'operation").showConfirm();
						if(Dialog.Actions.YES.equals(showConfirm)){
							selectedItem.setWaitingForCash(Boolean.TRUE);
							debtStatementEditService.setDebtStatement(selectedItem).start();
						}

					}else {
						Dialogs.create().message("Selectionner un etat !").showError();
					}
				}else {
					Dialogs.create().message("Etat Deja Solde").showInformation();
				}
			}
		});
		debtStatementEditService.setOnFailed(callFailedEventHandler);

		debtStatementEditService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				DebtStatementEditService s = (DebtStatementEditService) event.getSource();
				DebtStatement value = s.getValue();
				event.consume();
				s.reset();
				handleEditDoneEvent(value);
				Dialogs.create().message("Operation effectuee avec success !").showInformation();
			}
		});

		listView.getCloseButton().setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				DebtStatement selectedItem = listView.getDataList().getSelectionModel().getSelectedItem();
				if(selectedItem!=null&& !DocumentProcessingState.CLOSED.equals(selectedItem.getStatementStatus())){
					Action showConfirm = Dialogs.create().message("Confirmer la cloture l'operation inverse est impossible").showConfirm();
					if(Dialog.Actions.YES.equals(showConfirm)){
						selectedItem.setStatementStatus(DocumentProcessingState.CLOSED);
						debtStatementEditService.setDebtStatement(selectedItem).start();
					}
				}

			}
		});

		listView.getCashRepportButton().setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				DebtStatement selectedItem = listView.getDataList().getSelectionModel().getSelectedItem();
				if(selectedItem!=null){
                   PaymentSearchInput paymentSearchInput = new PaymentSearchInput();
                   paymentSearchInput.getEntity().setStatementNumber(selectedItem.getStatementNumber());
                   paymentSearchInput.getFieldNames().add("statementNumber");
                   paymentSearchInput.setMax(-1);
                   paymentSearchService.setSearchInputs(paymentSearchInput).start();
				}
			}
		});
		paymentSearchService.setOnFailed(callFailedEventHandler);
		paymentSearchService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				DebtStatement selectedItem = listView.getDataList().getSelectionModel().getSelectedItem();
				PaymentSearchService s = (PaymentSearchService) event.getSource();
				PaymentSearchResult value = s.getValue();
				event.consume();
				s.reset();
				Login login = securityUtil.getConnectedUser();
				try {
					DebtstatementCashReportPrintTemplatePDF cashReportPrintTemplatePDF = new DebtstatementCashReportPrintTemplatePDF(selectedItem, login);
				    cashReportPrintTemplatePDF.addItems(value.getResultList());
				    cashReportPrintTemplatePDF.closeDocument();
				    File file = new File(cashReportPrintTemplatePDF.getFileName()) ;
				    Desktop.getDesktop().open(file);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		listView.getRemoveInvoiceButton().setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				CustomerInvoice invoice = listView.getDataListItem().getSelectionModel().getSelectedItem();
				DebtStatement debtStatement = listView.getDataList().getSelectionModel().getSelectedItem();
				if(invoice!=null&&debtStatement!=null&& !DocumentProcessingState.CLOSED.equals(debtStatement.getStatementStatus())){
					Action showConfirm = Dialogs.create().message("Confirmer la Suppression de cette ligne ").showConfirm();
					if(Dialog.Actions.YES.equals(showConfirm)){
						DebtStatementCustomerInvoiceAssocSearchInput dbtsci = new DebtStatementCustomerInvoiceAssocSearchInput();
						dbtsci.getEntity().setSource(debtStatement);
						dbtsci.getEntity().setTarget(invoice);
						dbtsci.getFieldNames().add("source");
						dbtsci.getFieldNames().add("target");
						dbtsci.setMax(-1);
						statementCustomerInvoiceAssocSearchForRemoveServiceService.setSearchInputs(dbtsci).start();

					}
				}
			}
		});



		listView.getDataList().getSelectionModel().selectedItemProperty()
		.addListener(new ChangeListener<DebtStatement>()
				{
			@Override
			public void changed(
					ObservableValue<? extends DebtStatement> property,
					DebtStatement oldValue, DebtStatement newValue)
			{
				if (newValue != null){
					listView.getRemoveButton().disableProperty().unbind();
					listView.getCloseButton().disableProperty().unbind();
					listView.getCashButton().disableProperty().unbind();
					listView.getRemoveButton().disableProperty().bind(new SimpleBooleanProperty(!(BigDecimal.ZERO.compareTo(newValue.getAdvancePayment())==0)));
					listView.getCashButton().disableProperty().bind(newValue.statementStatusProperty().isNotEqualTo(DocumentProcessingState.CLOSED));
					listView.getCloseButton().disableProperty().bind(newValue.statementStatusProperty().isEqualTo(DocumentProcessingState.CLOSED));

					DebtStatementCustomerInvoiceAssocSearchInput dbtsci = new DebtStatementCustomerInvoiceAssocSearchInput();
					dbtsci.getEntity().setSource(newValue);
					dbtsci.getFieldNames().add("source");
					dbtsci.setMax(-1);
					statementCustomerInvoiceAssocSearchService.setSearchInputs(dbtsci).start();
				}
			}
				});
		statementCustomerInvoiceAssocSearchForRemoveServiceService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				DebtStatementCustomerInvoiceAssocSearchService s = (DebtStatementCustomerInvoiceAssocSearchService) event.getSource();
				DebtStatementCustomerInvoiceAssocSearchResult value = s.getValue();
				event.consume();
				s.reset();
				List<DebtStatementCustomerInvoiceAssoc> resultList = value.getResultList();
				if(!resultList.isEmpty()){
					statementCustomerInvoiceAssocRemoveService.setEntity(resultList.iterator().next()).start();
				}
			}
		});
		statementCustomerInvoiceAssocSearchForRemoveServiceService.setOnFailed(callFailedEventHandler);


		statementCustomerInvoiceAssocRemoveService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				DebtStatementCustomerInvoiceAssocRemoveService s = (DebtStatementCustomerInvoiceAssocRemoveService) event.getSource();
				DebtStatementCustomerInvoiceAssoc value = s.getValue();
				event.consume();
				s.reset();
				handleEditDoneEvent(value.getSource());
				listView.getDataListItem().getItems().remove(value.getTarget());
			}
		});

		statementCustomerInvoiceAssocRemoveService.setOnFailed(callFailedEventHandler);

		statementCustomerInvoiceAssocSearchService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				DebtStatementCustomerInvoiceAssocSearchService s = (DebtStatementCustomerInvoiceAssocSearchService) event.getSource();
				DebtStatementCustomerInvoiceAssocSearchResult value = s.getValue();
				event.consume();
				s.reset();
				ArrayList<CustomerInvoice> arrayList = new ArrayList<CustomerInvoice>();
				List<DebtStatementCustomerInvoiceAssoc> resultList = value.getResultList();
				for (DebtStatementCustomerInvoiceAssoc d : resultList) {
					arrayList.add(d.getTarget());
				}
				listView.getDataListItem().getItems().setAll(arrayList);
			}
		});
		statementCustomerInvoiceAssocSearchService.setOnFailed(callFailedEventHandler);
		listView.getInsurrance().setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				CustomerSearchInput searchInput = new CustomerSearchInput();
				searchInput.setMax(-1);
				insurrerSearchRequestEvent.fire(searchInput);
				listView.getEmptyInsurrance().setSelected(false);
			}
		});

		/*
		 * listen to remove  button and fire remove select event.
		 */
		listView.getRemoveButton().setOnAction(new EventHandler<ActionEvent>()
				{
			@Override
			public void handle(ActionEvent e)
			{
				DebtStatement selectedItem = listView.getDataList().getSelectionModel().getSelectedItem();
				Action message = Dialogs.create().message("Etes vous sure de vouloir suprimer ?").showConfirm();
				if(Dialog.Actions.YES.equals(message))
					if(selectedItem!=null && !DocumentProcessingState.CLOSED.equals(selectedItem.statementStatusProperty())){
						removeService.setEntity(selectedItem).start();
						showprogressEvent.fire(new Object());
						//						listView.getDataList().getItems().remove(selectedItem);
					}
			}
				});

		//		/*
		//		 * listen to edit button and fire search select event.
		//		 */
		//		listView.getEditButton().setOnAction(new EventHandler<ActionEvent>()
		//				{
		//			@Override
		//			public void handle(ActionEvent e)
		//			{
		//				DebtStatement selectedItem = listView.getDataList().getSelectionModel().getSelectedItem();
		//				if(selectedItem!=null)
		//					selectionEvent.fire(selectedItem);
		//			}
		//				});

		/*
		 * listen to search button and fire search activated event.
		 */
		listView.getSearchButton().setOnAction(new EventHandler<ActionEvent>()
				{
			@Override
			public void handle(ActionEvent e)
			{
				listView.getDataListItem().getItems().clear();
				searchInput.setFieldNames(readSearchAttributes());
				searchInput.setMax(30);
				searchService.setSearchInputs(searchInput).start();
			}
				});
		removeService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				DebtStatementRemoveService s = (DebtStatementRemoveService) event.getSource();
				DebtStatement value = s.getValue();
				event.consume();
				s.reset();
				handleRemovedEvent(value);
				hideProgressEvent.fire(new Object());
				listView.getDataListItem().getItems().clear();

			}
		});
		removeService.setOnFailed(callFailedEventHandler);

		searchService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				DebtStatementSearchService s = (DebtStatementSearchService) event.getSource();
				searchResult  = s.getValue();
				event.consume();
				s.reset();
				handleSearchResult(searchResult);
			}
		});
		searchService.setOnFailed(callFailedEventHandler);


		listView.getExportToXlsButton().setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				exportDeliveryToXls();

			}
		});


		listView.getPrintButton().setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				DebtStatement selectedItem = listView.getDataList().getSelectionModel().getSelectedItem();

				if(selectedItem!=null){
					customerInvoiceSearchService.setDebtStatement(selectedItem).start();
				}
			}
		});

		customerInvoiceSearchService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				CustomerInvoiceSearchService s = (CustomerInvoiceSearchService) event.getSource();
				CustomerInvoiceSearchResult value = s.getValue();
				event.consume();
				s.reset();
				DebtStatement selectedItem = listView.getDataList().getSelectionModel().getSelectedItem();
				List<CustomerInvoice> invoices = value.getResultList();
				Login login = securityUtil.getConnectedUser();
				if(selectedItem!=null){
					try {
						DebtStatementReportPrintTemplatePDF pdfRepportTemplate = new DebtStatementReportPrintTemplatePDF(selectedItem, listView.getResourceBundle(), locale, login);
						pdfRepportTemplate.addItems(invoices);
						pdfRepportTemplate.closeDocument();
						Desktop.getDesktop().open(new File(pdfRepportTemplate.getFileName()));
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}
		});
		customerInvoiceSearchService.setOnFailed(callFailedEventHandler);

		listView.getCreateButton().setOnAction(new EventHandler<ActionEvent>()
				{
			@Override
			public void handle(ActionEvent e)
			{
				createRequestedEvent.fire(new DebtStatementProcessingData());
			}
				});

		listView.getPagination().currentPageIndexProperty().addListener(new ChangeListener<Number>()
				{
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue)
			{
				if (searchResult == null)
					return;
				if (searchResult.getSearchInput() == null)
					searchResult.setSearchInput(new DebtStatementSearchInput());
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
	}

	@Override
	public ViewType getViewType()
	{
		return ViewType.LIST;
	}

	/**
	 * Handle search results. But the switch of displays is centralized
	 * in the main DebtStatement controller.
	 * 
	 * @param entities
	 */
	public void handleSearchResult(@Observes @EntitySearchDoneEvent DebtStatementSearchResult searchResult)
	{
		this.searchResult = searchResult;
		List<DebtStatement> entities = searchResult.getResultList();
		if (entities == null)
			entities = new ArrayList<DebtStatement>();
		listView.getDataList().getItems().setAll(entities);
		int maxResult = searchResult.getSearchInput() != null ? searchResult.getSearchInput().getMax() : 5;
		int pageCount = PaginationUtils.computePageCount(searchResult.getCount(), maxResult);
		listView.getPagination().setPageCount(pageCount);
		int firstResult = searchResult.getSearchInput() != null ? searchResult.getSearchInput().getStart() : 0;
		int pageIndex = PaginationUtils.computePageIndex(firstResult, searchResult.getCount(), maxResult);
		listView.getPagination().setCurrentPageIndex(pageIndex);

	}

	public void handleCreatedEvent(@Observes @EntityCreateDoneEvent DebtStatement createdEntity)
	{
		listView.getDataList().getItems().add(0, createdEntity);
	}

	public void handleModalCustomerSearchDoneEvent(@Observes @ModalEntitySearchDoneEvent Customer customer)
	{
		List<DebtStatementInsurrance> arrayList = new ArrayList<DebtStatementInsurrance>();
		listView.getInsurrance().setValue(new DebtStatementInsurrance(customer));
	}



	public void handleRemovedEvent(@Observes @EntityRemoveDoneEvent DebtStatement removedEntity)
	{
		listView.getDataList().getItems().remove(removedEntity);
	}

	public void handleEditDoneEvent(@Observes @EntityEditDoneEvent DebtStatement selectedEntity)
	{
		int selectedIndex = listView.getDataList().getItems().indexOf(selectedEntity);
		if (selectedIndex <= -1)
			return;
		DebtStatement entity = listView.getDataList().getItems().get(selectedIndex);
		PropertyReader.copy(selectedEntity, entity);

		ArrayList<DebtStatement> arrayList = new ArrayList<DebtStatement>(listView.getDataList().getItems());
		listView.getDataList().getItems().clear();
		listView.getDataList().getItems().addAll(arrayList);
		listView.getDataList().getSelectionModel().select(selectedEntity);
	}

	public void handleEditCanceledEvent(@Observes @EntityEditCanceledEvent DebtStatement selectedEntity)
	{
		int selectedIndex = listView.getDataList().getItems().indexOf(selectedEntity);
		if (selectedIndex <= -1)
			return;
		DebtStatement entity = listView.getDataList().getItems().get(selectedIndex);
		PropertyReader.copy(selectedEntity, entity);

		ArrayList<DebtStatement> arrayList = new ArrayList<DebtStatement>(listView.getDataList().getItems());
		listView.getDataList().getItems().clear();
		listView.getDataList().getItems().addAll(arrayList);
		listView.getDataList().getSelectionModel().select(selectedEntity);
	}

	public List<String> readSearchAttributes(){
		ArrayList<String> seachAttributes = new ArrayList<String>() ;
		String statementNumber = searchInput.getEntity().getStatementNumber();
		DebtStatementInsurrance insurrance = searchInput.getEntity().getInsurrance();
		DocumentProcessingState statementStatus = searchInput.getEntity().getStatementStatus();

		if(StringUtils.isNotBlank(statementNumber)) seachAttributes.add("statementNumber");
		if(insurrance!=null && insurrance.getId()!=null) seachAttributes.add("insurrance");
		if(statementStatus!=null) seachAttributes.add("statementStatus");
		return seachAttributes;

	}



	public void reset() {
		listView.getDataList().getItems().clear();
	}

	@SuppressWarnings("resource")
	public void exportDeliveryToXls(){
		DebtStatement selectedItem = listView.getDataList().getSelectionModel().getSelectedItem();
		String debtStmenetNumber = selectedItem.getStatementNumber(); 


		HSSFWorkbook deleveryXls = new HSSFWorkbook();
		int rownum = 0 ;
		int cellnum = 0 ;
		HSSFCell cell ;
		HSSFSheet sheet = deleveryXls.createSheet(debtStmenetNumber);
		HSSFRow head = sheet.createRow(rownum++);

		cell = head.createCell(cellnum++);
		cell.setCellValue(selectedItem.getInsurrance().getFullName());

		cellnum = 0 ;
		HSSFRow header = sheet.createRow(rownum++);

		cell = header.createCell(cellnum++);
		cell.setCellValue("Facture");

		cell = header.createCell(cellnum++);
		cell.setCellValue("matricule");

		cell = header.createCell(cellnum++);
		cell.setCellValue("client");

		//		cell = header.createCell(cellnum++);
		//		cell.setCellValue("employeur");

		cell = header.createCell(cellnum++);
		cell.setCellValue("date");

		cell = header.createCell(cellnum++);
		cell.setCellValue("total");

		cell = header.createCell(cellnum++);
		cell.setCellValue("montant");

		cell = header.createCell(cellnum++);
		cell.setCellValue("taux");

		if( selectedItem!=null&&sheet!=null){
			Iterator<CustomerInvoice> iterator = listView.getDataListItem().getItems().iterator();
			List<CustomerInvoice> items = Lists.newArrayList(iterator);
			for (CustomerInvoice item : items) {

				cellnum = 0 ;
				HSSFRow row = sheet.createRow(rownum++);
				cell = row.createCell(cellnum++);
				cell.setCellValue(item.getSalesOrder()+"");

				cell = row.createCell(cellnum++);
				cell.setCellValue(item.getInsurance().getCustomer().getSerialNumber());

				cell = row.createCell(cellnum++);
				cell.setCellValue(item.getCustomer().getFullName());

				if(item.getCreationDate()!=null){
					cell = row.createCell(cellnum++);
					cell.setCellValue(DateHelper.format(item.getCreationDate().getTime(),"dd-MM-yyyy"));
				}else {
					cell = row.createCell(cellnum++);
					cell.setCellValue("");
				}

				cell = row.createCell(cellnum++);
				cell.setCellValue(item.getNetToPay().toBigInteger()+"");

				cell = row.createCell(cellnum++);
				cell.setCellValue(item.getInsurranceRestTopay().toBigInteger()+"");

				cell = row.createCell(cellnum++);
				cell.setCellValue(item.getInsurance().getCoverageRate().toBigInteger()+"%");




			}
			try {
				File file = new File(debtStmenetNumber+".xls");
				FileOutputStream outputStream = new FileOutputStream(file);
				deleveryXls.write(outputStream);
				outputStream.close();
				Desktop.getDesktop().open(file);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
