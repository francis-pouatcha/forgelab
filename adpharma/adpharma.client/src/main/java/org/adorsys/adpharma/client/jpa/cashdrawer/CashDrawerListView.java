package org.adorsys.adpharma.client.jpa.cashdrawer;

import java.util.Locale;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.util.converter.BooleanStringConverter;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adorsys.adpharma.client.jpa.agency.Agency;
import org.adorsys.adpharma.client.jpa.customerinvoice.CustomerInvoice;
import org.adorsys.adpharma.client.jpa.customerinvoice.CustomerInvoiceSearchInput;
import org.adorsys.adpharma.client.jpa.login.Login;
import org.adorsys.javaext.format.NumberType;
import org.adorsys.javafx.crud.extensions.FXMLLoaderUtils;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.ViewBuilder;
import org.adorsys.javafx.crud.extensions.view.ViewBuilderUtils;

import de.jensd.fx.fontawesome.AwesomeIcon;

public class CashDrawerListView
{
	@FXML
	BorderPane rootPane;

	@FXML
	private Button searchButton;

	@FXML
	private Button printPaymentListButton;

	@FXML
	private Button openButton;

	@FXML
	private Button closeButton;

	@FXML
	HBox searchBar;

	private TextField cashDrawerNumber ;
	
	private ComboBox<CashDrawerCashier> cashier ;

	private CheckBox opened ;

	@FXML
	private TableView<CashDrawer> dataList;

	@Inject
	private Locale locale;

	@FXML
	private Pagination pagination;


	@Inject
	private FXMLLoader fxmlLoader;


	@Inject
	@Bundle({ CrudKeys.class
		, CashDrawer.class
		, Agency.class
	})
	private ResourceBundle resourceBundle;

	@PostConstruct
	public void postConstruct()
	{
		FXMLLoaderUtils.load(fxmlLoader, this, resourceBundle);
		ViewBuilder viewBuilder = new ViewBuilder();
		//      dataList = viewBuilder.addTable("dataList");
		viewBuilder.addStringColumn(dataList, "cashDrawerNumber", "CashDrawer_cashDrawerNumber_description.title", resourceBundle);
		viewBuilder.addStringColumn(dataList, "cashier", "CashDrawer_cashier_description.title", resourceBundle);
		viewBuilder.addDateColumn(dataList, "openingDate", "CashDrawer_openingDate_description.title", resourceBundle, "dd-MM-yyyy HH:mm", locale);
		viewBuilder.addDateColumn(dataList, "closingDate", "CashDrawer_closingDate_description.title", resourceBundle, "dd-MM-yyyy HH:mm", locale);
		viewBuilder.addStringColumn(dataList, "opened", "CashDrawer_opened_description.title", resourceBundle);

		viewBuilder.addBigDecimalColumn(dataList, "initialAmount", "CashDrawer_initialAmount_description.title", resourceBundle, NumberType.CURRENCY, locale);
		viewBuilder.addBigDecimalColumn(dataList, "totalCashIn", "CashDrawer_totalCashIn_description.title", resourceBundle, NumberType.CURRENCY, locale);
		viewBuilder.addBigDecimalColumn(dataList, "totalCashOut", "CashDrawer_totalCashOut_description.title", resourceBundle, NumberType.CURRENCY, locale);
		viewBuilder.addBigDecimalColumn(dataList, "totalCash", "CashDrawer_totalCash_description.title", resourceBundle, NumberType.CURRENCY, locale);
		viewBuilder.addBigDecimalColumn(dataList, "totalCheck", "CashDrawer_totalCheck_description.title", resourceBundle, NumberType.CURRENCY, locale);
		viewBuilder.addBigDecimalColumn(dataList, "totalCreditCard", "CashDrawer_totalCreditCard_description.title", resourceBundle, NumberType.CURRENCY, locale);
		viewBuilder.addBigDecimalColumn(dataList, "totalCompanyVoucher", "CashDrawer_totalCompanyVoucher_description.title", resourceBundle, NumberType.CURRENCY, locale);
		viewBuilder.addBigDecimalColumn(dataList, "totalClientVoucher", "CashDrawer_totalClientVoucher_description.title", resourceBundle, NumberType.CURRENCY, locale);
//		// Field not displayed in table
//		pagination = viewBuilder.addPagination();
//		viewBuilder.addSeparator();
//
//		HBox buttonBar = viewBuilder.addButtonBar();
//		createButton = viewBuilder.addButton(buttonBar, "Entity_create.title", "createButton", resourceBundle, AwesomeIcon.SAVE);
//		searchButton = viewBuilder.addButton(buttonBar, "Entity_search.title", "searchButton", resourceBundle, AwesomeIcon.SEARCH);
//		rootPane = viewBuilder.toAnchorPane();
		buildsearchBar();
	}
	
	public void bind(CashDrawerSearchInput searchInput)
	{

		cashDrawerNumber.textProperty().bindBidirectional(searchInput.getEntity().cashDrawerNumberProperty());
		opened.selectedProperty().bindBidirectional(searchInput.getEntity().openedProperty());
		cashier.valueProperty().bindBidirectional(searchInput.getEntity().cashierProperty());
	}

	public void buildsearchBar(){
		cashDrawerNumber =ViewBuilderUtils.newTextField("cashDrawerNumber", false);
		cashDrawerNumber.setPromptText(resourceBundle.getString("CashDrawer_cashDrawerNumber_description.title"));
		cashDrawerNumber.setPrefWidth(200d);
		opened = ViewBuilderUtils.newCheckBox(null, "cashed", resourceBundle, false);
		opened.setText(resourceBundle.getString("CashDrawer_opened_description.title")+" ?");
		
		cashier = ViewBuilderUtils.newComboBox("cashier", "cashier", false) ;
		cashier.setPrefWidth(250d);
		cashier.setPromptText(resourceBundle.getString("CashDrawer_cashier_description.title"));
		searchButton =ViewBuilderUtils.newButton("Entity_search.title", "searchButton", resourceBundle, AwesomeIcon.SEARCH);
		searchBar.getChildren().addAll(cashDrawerNumber,cashier,opened,searchButton);
	}

	public Button getOpenButton()
	{
		return openButton;
	}
	
	public Button getCloseButton()
	{
		return closeButton;
	}

	public Button getSearchButton()
	{
		return searchButton;
	}

	public TableView<CashDrawer> getDataList()
	{
		return dataList;
	}

	public Pagination getPagination()
	{
		return pagination;
	}

	public BorderPane getRootPane() {
		return rootPane;
	}

	public Button getPrintPaymentListButtonButton() {
		return printPaymentListButton;
	}

	public HBox getSearchBar() {
		return searchBar;
	}

	public TextField getCashDrawerNumber() {
		return cashDrawerNumber;
	}

	public ComboBox<CashDrawerCashier> getCashier() {
		return cashier;
	}

	public CheckBox getOpened() {
		return opened;
	}

	public Locale getLocale() {
		return locale;
	}

	public FXMLLoader getFxmlLoader() {
		return fxmlLoader;
	}

	public ResourceBundle getResourceBundle() {
		return resourceBundle;
	}
	
	

}
