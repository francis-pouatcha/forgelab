package org.adorsys.adpharma.client.jpa.salesorder;

import java.util.Locale;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adorsys.adpharma.client.jpa.agency.Agency;
import org.adorsys.adpharma.client.jpa.cashdrawer.CashDrawer;
import org.adorsys.adpharma.client.jpa.customer.Customer;
import org.adorsys.adpharma.client.jpa.documentprocessingstate.DocumentProcessingStateConverter;
import org.adorsys.adpharma.client.jpa.login.Login;
import org.adorsys.adpharma.client.jpa.salesordertype.SalesOrderTypeConverter;
import org.adorsys.adpharma.client.jpa.vat.VAT;
import org.adorsys.javaext.format.NumberType;
import org.adorsys.javafx.crud.extensions.FXMLLoaderUtils;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.ViewBuilder;

import de.jensd.fx.fontawesome.AwesomeIcon;

public class SalesOrderListView
{

	@FXML
	BorderPane rootPane;

	@FXML
	private Button searchButton;

	@FXML
	private Button createButton;

	@FXML
	private TableView<SalesOrder> dataList;

	@Inject
	private Locale locale;

	@FXML
	private Pagination pagination;

	@Inject
	@Bundle({ CrudKeys.class
		, SalesOrder.class
		, CashDrawer.class
		, Customer.class
		, VAT.class
		, Login.class
		, Agency.class
	})
	private ResourceBundle resourceBundle;

	@Inject
	private DocumentProcessingStateConverter documentProcessingStateConverter;

	@Inject
	private SalesOrderTypeConverter salesOrderTypeConverter;
	
	@Inject
	private FXMLLoader fxmlLoader;

	@PostConstruct
	public void postConstruct()
	{
		FXMLLoaderUtils.load(fxmlLoader, this, resourceBundle);
		ViewBuilder viewBuilder = new ViewBuilder();
		//      dataList = viewBuilder.addTable("dataList");
		viewBuilder.addStringColumn(dataList, "cashDrawerNumber", "CashDrawer_cashDrawerNumber_description.title", resourceBundle);
		viewBuilder.addStringColumn(dataList, "soNumber", "SalesOrder_soNumber_description.title", resourceBundle); 
		viewBuilder.addStringColumn(dataList, "fullName", "Customer_fullName_description.title", resourceBundle);
		viewBuilder.addStringColumn(dataList, "fullName", "Customer_fullName_description.title", resourceBundle);
		viewBuilder.addStringColumn(dataList, "fullName", "Customer_fullName_description.title", resourceBundle);
		viewBuilder.addBigDecimalColumn(dataList, "rate", "VAT_rate_description.title", resourceBundle, NumberType.PERCENTAGE, locale);
		viewBuilder.addStringColumn(dataList, "fullName", "Login_fullName_description.title", resourceBundle);
		viewBuilder.addStringColumn(dataList, "name", "Agency_name_description.title", resourceBundle);
		viewBuilder.addEnumColumn(dataList, "salesOrderStatus", "SalesOrder_salesOrderStatus_description.title", resourceBundle, documentProcessingStateConverter);

		// Field not displayed in table
		viewBuilder.addBigDecimalColumn(dataList, "amountBeforeTax", "SalesOrder_amountBeforeTax_description.title", resourceBundle, NumberType.CURRENCY, locale);
		viewBuilder.addBigDecimalColumn(dataList, "amountVAT", "SalesOrder_amountVAT_description.title", resourceBundle, NumberType.CURRENCY, locale);
		viewBuilder.addBigDecimalColumn(dataList, "amountDiscount", "SalesOrder_amountDiscount_description.title", resourceBundle, NumberType.CURRENCY, locale);
		viewBuilder.addBigDecimalColumn(dataList, "totalReturnAmount", "SalesOrder_totalReturnAmount_description.title", resourceBundle, NumberType.CURRENCY, locale);
		viewBuilder.addBigDecimalColumn(dataList, "amountAfterTax", "SalesOrder_amountAfterTax_description.title", resourceBundle, NumberType.CURRENCY, locale);
		viewBuilder.addEnumColumn(dataList, "salesOrderType", "SalesOrder_salesOrderType_description.title", resourceBundle, salesOrderTypeConverter);

		//		pagination = viewBuilder.addPagination();
		//		viewBuilder.addSeparator();
		//
		//		HBox buttonBar = viewBuilder.addButtonBar();
		//		createButton = viewBuilder.addButton(buttonBar, "Entity_create.title", "createButton", resourceBundle, AwesomeIcon.SAVE);
		//		searchButton = viewBuilder.addButton(buttonBar, "Entity_search.title", "searchButton", resourceBundle, AwesomeIcon.SEARCH);
		//		rootPane = viewBuilder.toAnchorPane();

	}

	public Button getCreateButton()
	{
		return createButton;
	}

	public Button getSearchButton()
	{
		return searchButton;
	}

	public TableView<SalesOrder> getDataList()
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

}
