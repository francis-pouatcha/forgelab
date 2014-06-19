package org.adorsys.adpharma.client.jpa.payment;

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

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adorsys.adpharma.client.jpa.agency.Agency;
import org.adorsys.adpharma.client.jpa.customer.Customer;
import org.adorsys.adpharma.client.jpa.customerinvoice.CustomerInvoice;
import org.adorsys.adpharma.client.jpa.invoicetype.InvoiceTypeConverter;
import org.adorsys.adpharma.client.jpa.login.Login;
import org.adorsys.adpharma.client.jpa.paymentmode.PaymentMode;
import org.adorsys.adpharma.client.jpa.paymentmode.PaymentModeConverter;
import org.adorsys.javaext.format.NumberType;
import org.adorsys.javafx.crud.extensions.FXMLLoaderUtils;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.ViewBuilder;
import org.adorsys.javafx.crud.extensions.view.ViewBuilderUtils;

import de.jensd.fx.fontawesome.AwesomeIcon;

public class PaymentListView
{

	@FXML
	BorderPane rootPane;

	@FXML
	private Button searchButton;

	private Button printButton;

	@FXML
	HBox searchBar;

	private TextField paymentNumber ;

	private CheckBox cashed ;

	@FXML
	private TableView<Payment> dataList;

	@Inject
	private Locale locale;

	@FXML
	private Pagination pagination;

	@Inject
	@Bundle({ CrudKeys.class
		, Payment.class
	})
	private ResourceBundle resourceBundle;

	@Inject
	private PaymentModeConverter paymentModeConverter;

	@Inject
	private FXMLLoader fxmlLoader;

	@PostConstruct
	public void postConstruct()
	{
		FXMLLoaderUtils.load(fxmlLoader, this, resourceBundle);
		ViewBuilder viewBuilder = new ViewBuilder();
		//      dataList = viewBuilder.addTable("dataList");
		viewBuilder.addStringColumn(dataList, "paymentNumber", "Payment_paymentNumber_description.title", resourceBundle);
		viewBuilder.addStringColumn(dataList, "cashDrawer", "Payment_cashDrawer_description.title", resourceBundle);
		viewBuilder.addStringColumn(dataList, "cashier", "Payment_cashier_description.title", resourceBundle,200d);
		viewBuilder.addDateColumn(dataList, "recordDate", "Payment_recordDate_description.title", resourceBundle, "dd-MM-yyyy HH:mm", locale);
		viewBuilder.addEnumColumn(dataList, "paymentMode", "Payment_paymentMode_description.title", resourceBundle, paymentModeConverter);
		viewBuilder.addBigDecimalColumn(dataList, "amount", "Payment_amount_description.title", resourceBundle, NumberType.CURRENCY, locale);
		viewBuilder.addBigDecimalColumn(dataList, "receivedAmount", "Payment_receivedAmount_description.title", resourceBundle, NumberType.CURRENCY, locale);
		viewBuilder.addBigDecimalColumn(dataList, "difference", "Payment_difference_description.title", resourceBundle, NumberType.CURRENCY, locale);
		//		viewBuilder.addStringColumn(dataList, "agency", "CustomerInvoice_agency_description.title", resourceBundle);
		// Field not displayed in table
		//		viewBuilder.addBigDecimalColumn(dataList, "amountBeforeTax", "CustomerInvoice_amountBeforeTax_description.title", resourceBundle, NumberType.INTEGER, locale);
		//		viewBuilder.addBigDecimalColumn(dataList, "taxAmount", "CustomerInvoice_taxAmount_description.title", resourceBundle, NumberType.CURRENCY, locale);
		//		viewBuilder.addBigDecimalColumn(dataList, "amountDiscount", "CustomerInvoice_amountDiscount_description.title", resourceBundle, NumberType.CURRENCY, locale);
		//		viewBuilder.addBigDecimalColumn(dataList, "amountAfterTax", "CustomerInvoice_amountAfterTax_description.title", resourceBundle, NumberType.CURRENCY, locale);
		// Field not displayed in table

		//      pagination = viewBuilder.addPagination();
		//      viewBuilder.addSeparator();
		//
		//      HBox buttonBar = viewBuilder.addButtonBar();
		//      createButton = viewBuilder.addButton(buttonBar, "Entity_create.title", "createButton", resourceBundle, AwesomeIcon.SAVE);
		//      searchButton = viewBuilder.addButton(buttonBar, "Entity_search.title", "searchButton", resourceBundle, AwesomeIcon.SEARCH);
		//      rootPane = viewBuilder.toAnchorPane();
		buildsearchBar();
	}

	public void bind(PaymentSearchInput searchInput)
	{

		paymentNumber.textProperty().bindBidirectional(searchInput.getEntity().paymentNumberProperty());
	}

	public void buildsearchBar(){
		paymentNumber =ViewBuilderUtils.newTextField("paymentNumber", false);
		paymentNumber.setPromptText("Numero du payement");
		paymentNumber.setPrefWidth(200d);
		paymentNumber.setPrefHeight(40d);
		searchButton =ViewBuilderUtils.newButton("Entity_search.title", "searchButton", resourceBundle, AwesomeIcon.SEARCH);
		searchButton.setPrefHeight(40d);
		
		printButton =ViewBuilderUtils.newButton("Entity_print.title", "printButton", resourceBundle, AwesomeIcon.SEARCH);
		printButton.setPrefHeight(40d);

		
		searchBar.getChildren().addAll(paymentNumber,searchButton,printButton);
	}

	public Button getPrintButton()
	{
		return printButton;
	}

	public Button getSearchButton()
	{
		return searchButton;
	}

	public TableView<Payment> getDataList()
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

}
