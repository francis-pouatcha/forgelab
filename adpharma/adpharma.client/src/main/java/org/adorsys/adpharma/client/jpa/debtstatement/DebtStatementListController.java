package org.adorsys.adpharma.client.jpa.debtstatement;

import java.awt.Desktop;
import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.adpharma.client.access.SecurityUtil;
import org.adorsys.adpharma.client.jpa.customer.Customer;
import org.adorsys.adpharma.client.jpa.customer.CustomerSearchInput;
import org.adorsys.adpharma.client.jpa.customer.CustomerSearchResult;
import org.adorsys.adpharma.client.jpa.customer.CustomerSearchService;
import org.adorsys.adpharma.client.jpa.customerinvoice.CustomerInvoice;
import org.adorsys.adpharma.client.jpa.debtstatementcustomerinvoiceassoc.DebtStatementCustomerInvoiceAssoc;
import org.adorsys.adpharma.client.jpa.debtstatementcustomerinvoiceassoc.DebtStatementCustomerInvoiceAssocSearchInput;
import org.adorsys.adpharma.client.jpa.debtstatementcustomerinvoiceassoc.DebtStatementCustomerInvoiceAssocSearchResult;
import org.adorsys.adpharma.client.jpa.debtstatementcustomerinvoiceassoc.DebtStatementCustomerInvoiceAssocSearchService;
import org.adorsys.adpharma.client.jpa.documentprocessingstate.DocumentProcessingState;
import org.adorsys.adpharma.client.jpa.login.Login;
import org.adorsys.adpharma.client.jpa.supplier.SupplierSearchService;
import org.adorsys.javafx.crud.extensions.EntityController;
import org.adorsys.javafx.crud.extensions.ViewType;
import org.adorsys.javafx.crud.extensions.events.EntityCreateDoneEvent;
import org.adorsys.javafx.crud.extensions.events.EntityCreateRequestedEvent;
import org.adorsys.javafx.crud.extensions.events.EntityEditCanceledEvent;
import org.adorsys.javafx.crud.extensions.events.EntityEditDoneEvent;
import org.adorsys.javafx.crud.extensions.events.EntityListPageIndexChangedEvent;
import org.adorsys.javafx.crud.extensions.events.EntityRemoveDoneEvent;
import org.adorsys.javafx.crud.extensions.events.EntityRemoveRequestEvent;
import org.adorsys.javafx.crud.extensions.events.EntitySearchDoneEvent;
import org.adorsys.javafx.crud.extensions.events.EntitySearchRequestedEvent;
import org.adorsys.javafx.crud.extensions.events.EntitySelectionEvent;
import org.adorsys.javafx.crud.extensions.login.ErrorDisplay;
import org.adorsys.javafx.crud.extensions.login.ServiceCallFailedEventHandler;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;
import org.adorsys.javafx.crud.extensions.utils.PaginationUtils;
import org.apache.commons.lang3.StringUtils;
import org.controlsfx.dialog.Dialogs;

import com.google.common.collect.Lists;

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
	@EntityRemoveRequestEvent
	private Event<DebtStatement> removeRequest;

	@Inject
	@EntityCreateRequestedEvent
	private Event<DebtStatement> createRequestedEvent;

	@Inject
	@EntityListPageIndexChangedEvent
	private Event<DebtStatementSearchResult> entityListPageIndexChangedEvent;

	@Inject
	private CustomerSearchService insurrerSearchService;

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
	private SecurityUtil securityUtil ;

	@Inject
	private DebtStatementCustomerInvoiceAssocSearchService statementCustomerInvoiceAssocSearchService ;
@Inject
private Locale locale ;

	@PostConstruct
	public void postConstruct()
	{
		listView.getCreateButton().disableProperty().bind(registration.canCreateProperty().not());
		searchInput.setMax(30);
		listView.bind(searchInput);

		callFailedEventHandler.setErrorDisplay(new ErrorDisplay() {

			@Override
			protected void showError(Throwable exception) {
				Dialogs.create().showException(exception);

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
					listView.getRemoveButton().disableProperty().bind(newValue.statementStatusProperty().isEqualTo(DocumentProcessingState.CLOSED));
					DebtStatementCustomerInvoiceAssocSearchInput dbtsci = new DebtStatementCustomerInvoiceAssocSearchInput();
					dbtsci.getEntity().setSource(newValue);
					dbtsci.getEntity().setSource(newValue);
					dbtsci.getFieldNames().add("source");
					dbtsci.setMax(-1);
					statementCustomerInvoiceAssocSearchService.setSearchInputs(dbtsci).start();
				}
			}
				});

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

		listView.getInsurrance().armedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable,
					Boolean oldValue, Boolean newValue) {
				if (newValue){
					CustomerSearchInput searchInput = new CustomerSearchInput();
					searchInput.setMax(-1);
					insurrerSearchService.setSearchInputs(searchInput).start();

				}


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
				if(selectedItem!=null && !DocumentProcessingState.CLOSED.equals(selectedItem.statementStatusProperty()))
					removeRequest.fire(selectedItem);
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
				searchInput.setFieldNames(readSearchAttributes());
				searchInput.setMax(30);
				searchService.setSearchInputs(searchInput).start();
			}
				});

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
		searchService.setOnFailed(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				DebtStatementSearchService s = (DebtStatementSearchService) event.getSource();
				s.reset();				
			}
		});

		insurrerSearchService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				CustomerSearchService s = (CustomerSearchService) event.getSource();
				CustomerSearchResult value = s.getValue();
				event.consume();
				s.reset();
				List<Customer> resultList = value.getResultList();
				ArrayList<DebtStatementInsurrance> arrayList = new ArrayList<>();
				for (Customer customer : resultList) {
					arrayList.add(new DebtStatementInsurrance(customer) );
				}
				arrayList.add(0,new DebtStatementInsurrance());
				listView.getInsurrance().getItems().setAll(arrayList);

			}
		});
		insurrerSearchService.setOnFailed(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				SupplierSearchService s = (SupplierSearchService) event.getSource();
				s.reset();				
			}
		});
		
		listView.getPrintButton().setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				DebtStatement selectedItem = listView.getDataList().getSelectionModel().getSelectedItem();
				Iterator<CustomerInvoice> iterator = listView.getDataListItem().getItems().iterator();
				ArrayList<CustomerInvoice> invoices = Lists.newArrayList(iterator);
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

		//		listView.getCreateButton().setOnAction(new EventHandler<ActionEvent>()
		//				{
		//			@Override
		//			public void handle(ActionEvent e)
		//			{
		//				orderPreparationEventData.fire(new DebtStatementPreparationData());
		//			}
		//				});

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
		listView.getDataList().getItems().clear();
		listView.getDataList().getItems().addAll(entities);
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
}
