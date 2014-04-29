package org.adorsys.adpharma.client.jpa.customerinvoice;

import java.util.Locale;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.util.converter.BooleanStringConverter;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adorsys.adpharma.client.jpa.agency.Agency;
import org.adorsys.adpharma.client.jpa.customer.Customer;
import org.adorsys.adpharma.client.jpa.invoicetype.InvoiceTypeConverter;
import org.adorsys.adpharma.client.jpa.login.Login;
import org.adorsys.adpharma.client.jpa.salesorder.SalesOrder;
import org.adorsys.javaext.format.NumberType;
import org.adorsys.javafx.crud.extensions.FXMLLoaderUtils;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.ViewBuilder;
import org.adorsys.javafx.crud.extensions.view.ViewBuilderUtils;

import de.jensd.fx.fontawesome.AwesomeIcon;

public class CustomerInvoiceListView
{


	@FXML
	BorderPane rootPane;

	@FXML
	private Button searchButton;

	@FXML
	private Button printButton;

	@FXML
	private Button showButton;

	@FXML
	HBox searchBar;

	private TextField invoiceNumber ;

	private CheckBox cashed ;

	@FXML
	private TableView<CustomerInvoice> dataList;

	@Inject
	private Locale locale;

	@FXML
	private Pagination pagination;

	@Inject
	@Bundle({ CrudKeys.class
		, CustomerInvoice.class
		, Customer.class
		, Login.class
		, Agency.class
		, SalesOrder.class
	})
	private ResourceBundle resourceBundle;

	@Inject
	private InvoiceTypeConverter invoiceTypeConverter;

	@Inject
	private FXMLLoader fxmlLoader;

	@PostConstruct
	public void postConstruct()
	{
		FXMLLoaderUtils.load(fxmlLoader, this, resourceBundle);
		ViewBuilder viewBuilder = new ViewBuilder();
		//      dataList = viewBuilder.addTable("dataList");
		viewBuilder.addStringColumn(dataList, "invoiceNumber", "CustomerInvoice_invoiceNumber_description.title", resourceBundle);
		viewBuilder.addEnumColumn(dataList, "invoiceType", "CustomerInvoice_invoiceType_description.title", resourceBundle, invoiceTypeConverter);
		viewBuilder.addStringColumn(dataList, "cashed", "SalesOrder_cashed_description.title", resourceBundle);
		viewBuilder.addDateColumn(dataList, "creationDate", "CustomerInvoice_creationDate_description.title", resourceBundle, "dd-MM-yyyy HH:mm", locale);
		viewBuilder.addStringColumn(dataList, "creatingUser", "CustomerInvoice_creatingUser_description.title", resourceBundle);
		//		viewBuilder.addStringColumn(dataList, "agency", "CustomerInvoice_agency_description.title", resourceBundle);
		viewBuilder.addStringColumn(dataList, "salesOrder", "CustomerInvoice_salesOrder_description.title", resourceBundle);
		// Field not displayed in table
		//		viewBuilder.addBigDecimalColumn(dataList, "amountBeforeTax", "CustomerInvoice_amountBeforeTax_description.title", resourceBundle, NumberType.INTEGER, locale);
		//		viewBuilder.addBigDecimalColumn(dataList, "taxAmount", "CustomerInvoice_taxAmount_description.title", resourceBundle, NumberType.CURRENCY, locale);
		//		viewBuilder.addBigDecimalColumn(dataList, "amountDiscount", "CustomerInvoice_amountDiscount_description.title", resourceBundle, NumberType.CURRENCY, locale);
		//		viewBuilder.addBigDecimalColumn(dataList, "amountAfterTax", "CustomerInvoice_amountAfterTax_description.title", resourceBundle, NumberType.CURRENCY, locale);
		viewBuilder.addBigDecimalColumn(dataList, "netToPay", "CustomerInvoice_netToPay_description.title", resourceBundle, NumberType.CURRENCY, locale);
		viewBuilder.addBigDecimalColumn(dataList, "customerRestTopay", "CustomerInvoice_customerRestTopay_description.title", resourceBundle, NumberType.CURRENCY, locale);
		viewBuilder.addBigDecimalColumn(dataList, "insurranceRestTopay", "CustomerInvoice_insurranceRestTopay_description.title", resourceBundle, NumberType.CURRENCY, locale);
		// Field not displayed in table
		viewBuilder.addBigDecimalColumn(dataList, "advancePayment", "CustomerInvoice_advancePayment_description.title", resourceBundle, NumberType.CURRENCY, locale);
		viewBuilder.addBigDecimalColumn(dataList, "totalRestToPay", "CustomerInvoice_totalRestToPay_description.title", resourceBundle, NumberType.CURRENCY, locale);

		//      pagination = viewBuilder.addPagination();
		//      viewBuilder.addSeparator();
		//
		//      HBox buttonBar = viewBuilder.addButtonBar();
		//      createButton = viewBuilder.addButton(buttonBar, "Entity_create.title", "createButton", resourceBundle, AwesomeIcon.SAVE);
		//      searchButton = viewBuilder.addButton(buttonBar, "Entity_search.title", "searchButton", resourceBundle, AwesomeIcon.SEARCH);
		//      rootPane = viewBuilder.toAnchorPane();
		buildsearchBar();
	}

	public void bind(CustomerInvoiceSearchInput searchInput)
	{

		invoiceNumber.textProperty().bindBidirectional(searchInput.getEntity().invoiceNumberProperty());
		cashed.textProperty().bindBidirectional(searchInput.getEntity().cashedProperty(), new BooleanStringConverter());
	}

	public void buildsearchBar(){
		invoiceNumber =ViewBuilderUtils.newTextField("customerName", false);
		invoiceNumber.setPromptText("customer Name");
		invoiceNumber.setPrefWidth(200d);
		invoiceNumber.setPrefHeight(40d);
		cashed = ViewBuilderUtils.newCheckBox(null, "cashed", resourceBundle, false);
		cashed.setText("Cashed");
		searchButton =ViewBuilderUtils.newButton("Entity_search.title", "searchButton", resourceBundle, AwesomeIcon.SEARCH);
		searchButton.setPrefHeight(40d);
		searchBar.getChildren().addAll(invoiceNumber,cashed,searchButton);
	}

	public Button getPrintButton()
	{
		return printButton;
	}

	public Button getSearchButton()
	{
		return searchButton;
	}

	public TableView<CustomerInvoice> getDataList()
	{
		return dataList;
	}

	public BorderPane getRootPane()
	{
		return rootPane;
	}

	public CheckBox getCashed()
	{
		return cashed;
	}

	public Pagination getPagination()
	{
		return pagination;
	}

	public Button getShowButton() {
		return showButton;
	}
	

}
