package org.adorsys.adpharma.client.jpa.debtstatement;

import java.util.Locale;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adorsys.adpharma.client.jpa.agency.Agency;
import org.adorsys.adpharma.client.jpa.cashdrawer.CashDrawerReportPrintTemplate;
import org.adorsys.adpharma.client.jpa.customer.Customer;
import org.adorsys.adpharma.client.jpa.customerinvoice.CustomerInvoice;
import org.adorsys.adpharma.client.jpa.documentprocessingstate.DocumentProcessingState;
import org.adorsys.adpharma.client.jpa.documentprocessingstate.DocumentProcessingStateConverter;
import org.adorsys.adpharma.client.jpa.invoicetype.InvoiceTypeConverter;
import org.adorsys.javaext.format.NumberType;
import org.adorsys.javafx.crud.extensions.FXMLLoaderUtils;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.ViewBuilder;
import org.adorsys.javafx.crud.extensions.view.ViewBuilderUtils;

import de.jensd.fx.fontawesome.AwesomeIcon;

public class DebtStatementListView
{
	@FXML
	BorderPane rootPane;

	@FXML
	HBox searchBar;

	private TextField procurementOrderNumber ;

	private ComboBox<DebtStatementInsurrance> insurrance ;

	private ComboBox<DocumentProcessingState> state ;

	@FXML
	private Button searchButton;

	@FXML
	private Button createButton;

	@FXML
	private Button editButton ;
	
	@FXML
	private Button printButton ;

	@FXML
	private Button removeButton ;

	@FXML
	private TableView<DebtStatement> dataList;

	@FXML
	private TableView<CustomerInvoice> dataListItem;

	@Inject
	private Locale locale;

	@FXML
	private Pagination pagination;

	@Inject
	@Bundle({ CrudKeys.class
		, DebtStatement.class
		, Customer.class
		, Agency.class,CustomerInvoice.class ,CashDrawerReportPrintTemplate.class 
	})
	private ResourceBundle resourceBundle;

	@Inject
	private DocumentProcessingStateConverter documentProcessingStateConverter;

	@Inject
	private FXMLLoader fxmlLoader ;

	@Inject
	private InvoiceTypeConverter invoiceTypeConverter;

	@PostConstruct
	public void postConstruct()
	{
		FXMLLoaderUtils.load(fxmlLoader, this, resourceBundle);
		ViewBuilder viewBuilder = new ViewBuilder();
		//		dataList = viewBuilder.addTable("dataList");
		viewBuilder.addStringColumn(dataList, "statementNumber", "DebtStatement_statementNumber_description.title", resourceBundle);
		viewBuilder.addStringColumn(dataList, "insurrance", "DebtStatement_insurrance_description.title", resourceBundle,300d);
		viewBuilder.addStringColumn(dataList, "agency", "DebtStatement_agency_description.title", resourceBundle);
		viewBuilder.addDateColumn(dataList, "paymentDate", "DebtStatement_paymentDate_description.title", resourceBundle, "dd-MM-yyyy HH:mm", locale);
		viewBuilder.addBigDecimalColumn(dataList, "initialAmount", "DebtStatement_initialAmount_description.title", resourceBundle, NumberType.CURRENCY, locale);
		viewBuilder.addBigDecimalColumn(dataList, "advancePayment", "DebtStatement_advancePayment_description.title", resourceBundle, NumberType.CURRENCY, locale);
		viewBuilder.addBigDecimalColumn(dataList, "restAmount", "DebtStatement_restAmount_description.title", resourceBundle, NumberType.CURRENCY, locale);
		// Field not displayed in table
		viewBuilder.addBigDecimalColumn(dataList, "amountFromVouchers", "DebtStatement_amountFromVouchers_description.title", resourceBundle, NumberType.CURRENCY, locale);
		// Field not displayed in table
		// Field not displayed in table
		//		pagination = viewBuilder.addPagination();
		//		viewBuilder.addSeparator();
		//
		//		HBox buttonBar = viewBuilder.addButtonBar();
		//		createButton = viewBuilder.addButton(buttonBar, "Entity_create.title", "createButton", resourceBundle, AwesomeIcon.SAVE);
		//		searchButton = viewBuilder.addButton(buttonBar, "Entity_search.title", "searchButton", resourceBundle, AwesomeIcon.SEARCH);
		//		rootPane = viewBuilder.toAnchorPane();

		viewBuilder.addStringColumn(dataListItem, "id", "CustomerInvoice_invoiceNumber_description.title", resourceBundle);
		viewBuilder.addEnumColumn(dataListItem, "invoiceType", "CustomerInvoice_invoiceType_description.title", resourceBundle, invoiceTypeConverter);
		viewBuilder.addStringColumn(dataListItem, "cashed", "CustomerInvoice_cashed_description.title", resourceBundle);
		viewBuilder.addDateColumn(dataListItem, "creationDate", "CustomerInvoice_creationDate_description.title", resourceBundle, "dd-MM-yyyy HH:mm", locale);
		viewBuilder.addStringColumn(dataListItem, "creatingUser", "CustomerInvoice_creatingUser_description.title", resourceBundle);
		viewBuilder.addStringColumn(dataListItem, "salesOrder", "CustomerInvoice_salesOrder_description.title", resourceBundle);
		viewBuilder.addBigDecimalColumn(dataListItem, "netToPay", "CustomerInvoice_netToPay_description.title", resourceBundle, NumberType.CURRENCY, locale);
		viewBuilder.addBigDecimalColumn(dataListItem, "customerRestTopay", "CustomerInvoice_customerRestTopay_description.title", resourceBundle, NumberType.CURRENCY, locale);
		viewBuilder.addBigDecimalColumn(dataListItem, "insurranceRestTopay", "CustomerInvoice_insurranceRestTopay_description.title", resourceBundle, NumberType.CURRENCY, locale);
		viewBuilder.addBigDecimalColumn(dataListItem, "advancePayment", "CustomerInvoice_advancePayment_description.title", resourceBundle, NumberType.CURRENCY, locale);
		viewBuilder.addBigDecimalColumn(dataListItem, "totalRestToPay", "CustomerInvoice_totalRestToPay_description.title", resourceBundle, NumberType.CURRENCY, locale);
		buildsearchBar();
	}

	public void buildsearchBar(){
		procurementOrderNumber =ViewBuilderUtils.newTextField("procurementOrderNumber", false);
		procurementOrderNumber.setPromptText("code");
		procurementOrderNumber.setPrefHeight(40d);

		insurrance =ViewBuilderUtils.newComboBox(null, "insurrance", false);
		insurrance.setPromptText("Tous les Fournisseur");
		insurrance.setPrefHeight(40d);
		insurrance.setPrefWidth(300d);

		state = ViewBuilderUtils.newComboBox(null, "poStatus", resourceBundle, DocumentProcessingState.valuesWithNull(), false);
		state.setPrefHeight(40d);

		searchButton =ViewBuilderUtils.newButton("Entity_search.title", "searchButton", resourceBundle, AwesomeIcon.SEARCH);
		searchButton.setPrefHeight(40d);
		searchBar.getChildren().addAll(procurementOrderNumber,insurrance,state,searchButton);
	}

	public void bind(DebtStatementSearchInput searchInput)
	{
		procurementOrderNumber.textProperty().bindBidirectional(searchInput.getEntity().statementNumberProperty());
		insurrance.valueProperty().bindBidirectional(searchInput.getEntity().insurranceProperty());
		state.valueProperty().bindBidirectional(searchInput.getEntity().statementStatusProperty());
	}


	public Button getCreateButton()
	{
		return createButton;
	}

	public Button getSearchButton()
	{
		return searchButton;
	}

	public TableView<DebtStatement> getDataList()
	{
		return dataList;
	}

	public BorderPane getRootPane()
	{
		return rootPane;
	}

	public Pagination getPagination()
	{
		return pagination;
	}

	public HBox getSearchBar() {
		return searchBar;
	}

	public TextField getProcurementOrderNumber() {
		return procurementOrderNumber;
	}

	public ComboBox<DebtStatementInsurrance> getInsurrance() {
		return insurrance;
	}

	public ComboBox<DocumentProcessingState> getState() {
		return state;
	}

	public Button getEditButton() {
		return editButton;
	}
	
	public Button getPrintButton() {
		return printButton;
	}

	public Button getRemoveButton() {
		return removeButton;
	}

	public TableView<CustomerInvoice> getDataListItem() {
		return dataListItem;
	}

	public Locale getLocale() {
		return locale;
	}

	public ResourceBundle getResourceBundle() {
		return resourceBundle;
	}

	public DocumentProcessingStateConverter getDocumentProcessingStateConverter() {
		return documentProcessingStateConverter;
	}

	public FXMLLoader getFxmlLoader() {
		return fxmlLoader;
	}

	public InvoiceTypeConverter getInvoiceTypeConverter() {
		return invoiceTypeConverter;
	}

}
