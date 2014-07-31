package org.adorsys.adpharma.client.jpa.salesorder;

import java.math.BigDecimal;
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.chart.BarChart;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Pagination;
import javafx.scene.control.Tab;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adorsys.adpharma.client.jpa.article.Article;
import org.adorsys.adpharma.client.jpa.customer.Customer;
import org.adorsys.adpharma.client.jpa.documentprocessingstate.DocumentProcessingState;
import org.adorsys.adpharma.client.jpa.documentprocessingstate.DocumentProcessingStateConverter;
import org.adorsys.adpharma.client.jpa.documentprocessingstate.DocumentProcessingStateListCellFatory;
import org.adorsys.adpharma.client.jpa.salesorderitem.SalesOrderItem;
import org.adorsys.adpharma.client.jpa.salesordertype.SalesOrderTypeConverter;
import org.adorsys.adpharma.client.utils.ChartData;
import org.adorsys.javaext.format.NumberType;
import org.adorsys.javafx.crud.extensions.FXMLLoaderUtils;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.ComboBoxInitializer;
import org.adorsys.javafx.crud.extensions.view.ViewBuilder;
import org.adorsys.javafx.crud.extensions.view.ViewBuilderUtils;

import de.jensd.fx.fontawesome.AwesomeIcon;

public class SalesOrderListView
{

	@FXML
	BorderPane rootPane;

	private Button searchButton;

	@FXML
	private Button createButton;
	
	@FXML
	private Button removeButton;

	@FXML
	private Button advenceSearchButton;
	
	@FXML
	private Button printInvoiceButton;
	
	@FXML
	private Button printTicketButton;
	
	@FXML
	private Button printVoucherButton;
	
	@FXML
	private CheckBox emptyArticle ;

	@FXML
	private Button computeButton;

	@FXML
	private BarChart<String, BigDecimal> barChart;


	@FXML 
	private ComboBox<Customer> chartClientList;
	
	@FXML 
	private ComboBox<Article> chartArticleList;

	@FXML 
	private ComboBox<Integer> yearList;

	@FXML
	private Button processButton;

	@FXML
	private TableView<SalesOrder> dataList;

	@FXML
	private TableView<SalesOrderItem> dataListItem;

	@Inject
	private Locale locale;

	@FXML
	private Pagination pagination;

	@FXML
	HBox searchBar;

	private TextField soNumber ;

	@FXML
	private Tab turnoverTab ;

	private ComboBox<SalesOrderCustomer> customer;

	private ComboBox<DocumentProcessingState> salesOrderStatus;

	@Inject
	@Bundle(DocumentProcessingState.class)
	private ResourceBundle salesOrderStatusBundle;

	@Inject
	private DocumentProcessingStateConverter salesOrderStatusConverter;

	@Inject
	private DocumentProcessingStateListCellFatory salesOrderStatusListCellFatory;

	@Inject
	@Bundle({ CrudKeys.class, SalesOrderItem.class,ChartData.class
		, SalesOrder.class
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
		viewBuilder.addStringColumn(dataList, "soNumber", "SalesOrder_soNumber_description.title", resourceBundle); 
		viewBuilder.addStringColumn(dataList, "customer", "SalesOrder_customer_description.title", resourceBundle,250d);
//		viewBuilder.addStringColumn(dataList, "cashDrawer", "SalesOrder_cashDrawer_description.title", resourceBundle);
		viewBuilder.addStringColumn(dataList, "salesAgent", "SalesOrder_salesAgent_description.title", resourceBundle);
		viewBuilder.addEnumColumn(dataList, "salesOrderStatus", "SalesOrder_salesOrderStatus_description.title", resourceBundle, documentProcessingStateConverter);

		// Field not displayed in table
		viewBuilder.addDateColumn(dataList, "creationDate", "SalesOrder_creationDate_description.title", resourceBundle, "dd-MM-yyyy HH:mm", locale);
		//		viewBuilder.addBigDecimalColumn(dataList, "amountVAT", "SalesOrder_amountVAT_description.title", resourceBundle, NumberType.CURRENCY, locale);
		viewBuilder.addBigDecimalColumn(dataList, "amountDiscount", "SalesOrder_amountDiscount_description.title", resourceBundle, NumberType.CURRENCY, locale);
		viewBuilder.addBigDecimalColumn(dataList, "amountAfterTax", "SalesOrder_amountAfterTax_description.title", resourceBundle, NumberType.CURRENCY, locale);
		//		viewBuilder.addEnumColumn(dataList, "salesOrderType", "SalesOrder_salesOrderType_description.title", resourceBundle, salesOrderTypeConverter);
		viewBuilder.addStringColumn(dataList, "cashed", "SalesOrder_cashed_description.title", resourceBundle);
		viewBuilder.addStringColumn(dataList, "alreadyReturned", "SalesOrder_alreadyReturned_description.title", resourceBundle);

		//		pagination = viewBuilder.addPagination();
		//		viewBuilder.addSeparator();
		//
		//		HBox buttonBar = viewBuilder.addButtonBar();
		//		createButton = viewBuilder.addButton(buttonBar, "Entity_create.title", "createButton", resourceBundle, AwesomeIcon.SAVE);
		//		searchButton = viewBuilder.addButton(buttonBar, "Entity_search.title", "searchButton", resourceBundle, AwesomeIcon.SEARCH);
		//		rootPane = viewBuilder.toAnchorPane();

		buildsearchBar();
		ComboBoxInitializer.initialize(salesOrderStatus, salesOrderStatusConverter, salesOrderStatusListCellFatory, salesOrderStatusBundle);

		// sales order item list view
		viewBuilder.addStringColumn(dataListItem, "internalPic", "SalesOrderItem_internalPic_description.title", resourceBundle);
		ViewBuilderUtils.newStringColumn(dataListItem, "article", "SalesOrderItem_article_description.title", resourceBundle,400d);
		viewBuilder.addBigDecimalColumn(dataListItem, "orderedQty", "SalesOrderItem_orderedQty_description.title", resourceBundle, NumberType.INTEGER, locale);
		viewBuilder.addBigDecimalColumn(dataListItem, "returnedQty", "SalesOrderItem_returnedQty_description.title", resourceBundle, NumberType.INTEGER, locale);
		viewBuilder.addBigDecimalColumn(dataListItem, "deliveredQty", "SalesOrderItem_deliveredQty_description.title", resourceBundle, NumberType.INTEGER, locale);
		viewBuilder.addBigDecimalColumn(dataListItem, "salesPricePU", "SalesOrderItem_salesPricePU_description.title", resourceBundle, NumberType.CURRENCY, locale);
		viewBuilder.addBigDecimalColumn(dataListItem, "totalSalePrice", "SalesOrderItem_totalSalePrice_description.title", resourceBundle, NumberType.CURRENCY, locale);
//		viewBuilder.addBigDecimalColumn(dataListItem, "vat", "SalesOrderItem_vat_description.title", resourceBundle, NumberType.PERCENTAGE, locale);


	}
	public void bind(SalesOrderSearchInput searchInput)
	{

		soNumber.textProperty().bindBidirectional(searchInput.getEntity().soNumberProperty());
		customer.valueProperty().bindBidirectional(searchInput.getEntity().customerProperty());
		salesOrderStatus.valueProperty().bindBidirectional(searchInput.getEntity().salesOrderStatusProperty());
	}

	public void buildsearchBar(){
		
		soNumber =ViewBuilderUtils.newTextField("soNumber", false);
		soNumber.setPromptText(resourceBundle.getString("SalesOrder_soNumber_description.title"));
		soNumber.setPrefHeight(30d);

		customer =ViewBuilderUtils.newComboBox(null, "customer", false);
		customer.setPromptText(resourceBundle.getString("SalesOrder_all_supplier_description.title"));
		customer.setPrefWidth(300d);
		customer.setPrefHeight(30d);

		salesOrderStatus =ViewBuilderUtils.newComboBox(null, "salesOrderStatus", resourceBundle, DocumentProcessingState.valuesWithNull(), false);
		salesOrderStatus.setPromptText(resourceBundle.getString("SalesOrder_salesOrderStatus_description.title"));
		salesOrderStatus.setPrefHeight(30d);

		searchButton =ViewBuilderUtils.newButton("Entity_search.title", "searchButton", resourceBundle, AwesomeIcon.SEARCH);
		searchButton.setPrefHeight(30d);
		searchBar.getChildren().addAll(soNumber,customer,salesOrderStatus,searchButton);
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
	public TableView<SalesOrderItem> getDataListItem()
	{
		return dataListItem;
	}

	public BorderPane getRootPane()
	{
		return rootPane;
	}

	public Pagination getPagination()
	{
		return pagination;
	}

	public Button getRemoveButton() {
		return removeButton;
	}
	

	public Button getAdvenceSearchButton() {
		return advenceSearchButton;
	}


	public Button getPrintInvoiceButtonn() {
		return printInvoiceButton;
	}
	
	public Button getPrintTicketButton() {
		return printTicketButton;
	}
	public Button getPrintVoucherButton() {
		return printVoucherButton;
	}
	public void setRemoveButton(Button removeButton) {
		this.removeButton = removeButton;
	}
	public Button getProcessButton() {
		return processButton;
	}
	public void setProcessButton(Button processButton) {
		this.processButton = processButton;
	}
	public void setSearchButton(Button searchButton) {
		this.searchButton = searchButton;
	}
	public void setCreateButton(Button createButton) {
		this.createButton = createButton;
	}

	public HBox getSearchBar() {
		return searchBar;
	}
	public TextField getSoNumber() {
		return soNumber;
	}
	public ComboBox<SalesOrderCustomer> getCustomer() {
		return customer;
	}
	public ComboBox<DocumentProcessingState> getSalesOrderStatus() {
		return salesOrderStatus;
	}

	public BarChart<String, BigDecimal> getPieChart(){
		return barChart;
	}
	public Button getComputeButton(){
		return computeButton;
	}

	public ComboBox<Customer> getChartClientList(){
		return chartClientList ;
	}
	public ComboBox<Article> getChartArticleList(){
		return chartArticleList ;
	}
	
	public ComboBox<Integer> getYearList(){
		return yearList ;
	}

	public Tab getTurnoverTab(){
		return turnoverTab ;
	}
     
	
	public CheckBox getEmptyArticle(){
		return emptyArticle ;
	}
}
