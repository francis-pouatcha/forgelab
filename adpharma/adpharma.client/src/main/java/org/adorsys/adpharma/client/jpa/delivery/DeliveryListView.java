package org.adorsys.adpharma.client.jpa.delivery;

import java.util.Locale;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import jfxtras.scene.control.CalendarTextField;

import org.adorsys.adpharma.client.jpa.agency.Agency;
import org.adorsys.adpharma.client.jpa.deliveryitem.DeliveryItem;
import org.adorsys.adpharma.client.jpa.documentprocessingstate.DocumentProcessingState;
import org.adorsys.adpharma.client.jpa.documentprocessingstate.DocumentProcessingStateConverter;
import org.adorsys.adpharma.client.jpa.documentprocessingstate.DocumentProcessingStateListCellFatory;
import org.adorsys.adpharma.client.jpa.vat.VAT;
import org.adorsys.adpharma.client.utils.ChartData;
import org.adorsys.javaext.format.NumberType;
import org.adorsys.javafx.crud.extensions.FXMLLoaderUtils;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.ComboBoxInitializer;
import org.adorsys.javafx.crud.extensions.view.ViewBuilder;
import org.adorsys.javafx.crud.extensions.view.ViewBuilderUtils;

import de.jensd.fx.fontawesome.AwesomeIcon;

public class DeliveryListView
{
	@FXML
	BorderPane rootPane;

	@FXML
	HBox searchBar;

	private TextField deliveryNumber ;

	private CalendarTextField deliveryDateFrom;

	private ComboBox<DeliverySupplier> supplier;

	private ComboBox<DocumentProcessingState> deliveryProcessingState;

	private Button searchButton;

	@FXML
	private Button computeButton;

	@FXML
	private PieChart pieChart;

	@FXML
	private TableView<ChartData> pieChartData;

	@FXML 
	private ComboBox<DeliverySupplier> chartSupplierList;

	@FXML 
	private ComboBox<Integer> yearList;


	@FXML
	private Button createButton;

	@FXML
	private Button updateButton;

	@FXML
	private Button exportToXlsButton;

	@FXML
	private Button printButton;

	@FXML
	private Button removeButton;

	@FXML
	private Button processButton;

	@FXML
	private TableView<Delivery> dataList;

	@FXML
	private TableView<DeliveryItem> dataListItem;

	@FXML
	private Pagination pagination;

	@Inject
	@Bundle(DocumentProcessingState.class)
	private ResourceBundle deliveryProcessingStateBundle;

	@Inject
	private DocumentProcessingStateConverter deliveryProcessingStateConverter;

	@Inject
	private DocumentProcessingStateListCellFatory deliveryProcessingStateListCellFatory;

	@Inject
	private Locale locale;

	@Inject
	@Bundle({ CrudKeys.class,DeliveryItem.class,ChartData.class
		, Delivery.class
		, VAT.class
		, Agency.class
	})
	private ResourceBundle resourceBundle;

	@Inject
	FXMLLoader fxmlLoader ;

	@PostConstruct
	public void postConstruct()
	{
		FXMLLoaderUtils.load(fxmlLoader, this, resourceBundle);
		ViewBuilder viewBuilder = new ViewBuilder();
		//		dataList = viewBuilder.addTable("dataList");
		viewBuilder.addStringColumn(dataList, "deliveryNumber", "Delivery_deliveryNumber_description.title", resourceBundle);
		viewBuilder.addStringColumn(dataList, "procurementOrderNumber", "Delivery_procurementOrderNumber_description.title", resourceBundle);
		viewBuilder.addStringColumn(dataList, "deliverySlipNumber", "Delivery_deliverySlipNumber_description.title", resourceBundle);
		viewBuilder.addEnumColumn(dataList, "deliveryProcessingState", "Delivery_deliveryProcessingState_description.title", resourceBundle, deliveryProcessingStateConverter);
		viewBuilder.addStringColumn(dataList, "creatingUser", "Delivery_creatingUser_description.title", resourceBundle,150d);
		viewBuilder.addStringColumn(dataList, "supplier", "Delivery_supplier_description.title", resourceBundle,250d);
		viewBuilder.addDateColumn(dataList, "dateOnDeliverySlip", "Delivery_dateOnDeliverySlip_description.title", resourceBundle, "dd-MM-yyyy", locale);
		viewBuilder.addBigDecimalColumn(dataList, "amountBeforeTax", "Delivery_amountBeforeTax_description.title", resourceBundle, NumberType.INTEGER, locale);
		//		viewBuilder.addBigDecimalColumn(dataList, "amountAfterTax", "Delivery_amountAfterTax_description.title", resourceBundle, NumberType.CURRENCY, locale);
		//		viewBuilder.addBigDecimalColumn(dataList, "amountDiscount", "Delivery_amountDiscount_description.title", resourceBundle, NumberType.CURRENCY, locale);
		//		viewBuilder.addBigDecimalColumn(dataList, "netAmountToPay", "Delivery_netAmountToPay_description.title", resourceBundle, NumberType.CURRENCY, locale);
		//		viewBuilder.addStringColumn(dataList, "vat", "Delivery_vat_description.title", resourceBundle);
		//		viewBuilder.addStringColumn(dataList, "receivingAgency", "Delivery_receivingAgency_description.title", resourceBundle,250d);

		//				pagination = viewBuilder.addPagination();
		//				viewBuilder.addSeparator();

		//				HBox buttonBar = viewBuilder.addButtonBar();
		//				createButton = viewBuilder.addButton(buttonBar, "Entity_create.title", "createButton", resourceBundle, AwesomeIcon.SAVE);
		//				searchButton = viewBuilder.addButton(buttonBar, "Entity_search.title", "searchButton", resourceBundle, AwesomeIcon.SEARCH);
		//				rootPane = viewBuilder.toAnchorPane();
		buildsearchBar();
		ComboBoxInitializer.initialize(deliveryProcessingState, deliveryProcessingStateConverter, deliveryProcessingStateListCellFatory, deliveryProcessingStateBundle);


		//deliveryitem

		viewBuilder.addStringColumn(dataListItem, "internalPic", "DeliveryItem_internalPic_description.title", resourceBundle);
		//		viewBuilder.addStringColumn(dataListItem, "mainPic", "DeliveryItem_mainPic_description.title", resourceBundle);
		//		viewBuilder.addStringColumn(dataListItem, "secondaryPic", "DeliveryItem_secondaryPic_description.title", resourceBundle);
		ViewBuilderUtils.newStringColumn(dataListItem, "article", "DeliveryItem_article_description.title", resourceBundle,350d);
		viewBuilder.addDateColumn(dataListItem, "expirationDate", "DeliveryItem_expirationDate_description.title", resourceBundle, "dd-MM-yyyy", locale);
		viewBuilder.addBigDecimalColumn(dataListItem, "stockQuantity", "DeliveryItem_qtyOrdered_description.title", resourceBundle, NumberType.INTEGER, locale);
		viewBuilder.addBigDecimalColumn(dataListItem, "freeQuantity", "DeliveryItem_freeQuantity_description.title", resourceBundle, NumberType.INTEGER, locale);
		viewBuilder.addBigDecimalColumn(dataListItem, "article.qtyInStock", "DeliveryItem_stockQuantity_description.title", resourceBundle, NumberType.INTEGER, locale);
		viewBuilder.addBigDecimalColumn(dataListItem, "salesPricePU", "DeliveryItem_salesPricePU_description.title", resourceBundle, NumberType.CURRENCY, locale);
		viewBuilder.addBigDecimalColumn(dataListItem, "purchasePricePU", "DeliveryItem_purchasePricePU_description.title", resourceBundle, NumberType.CURRENCY, locale);
		viewBuilder.addBigDecimalColumn(dataListItem, "totalPurchasePrice", "DeliveryItem_totalPurchasePrice_description.title", resourceBundle, NumberType.CURRENCY, locale);

		// pie Chart table view
		viewBuilder.addStringColumn(pieChartData, "name", "ChartData_name_description.title", resourceBundle);
		viewBuilder.addBigDecimalColumn(pieChartData, "value", "ChartData_value_description.title", resourceBundle, NumberType.CURRENCY, locale);

	}

	public void bind(DeliverySearchInput searchInput)
	{

		deliveryNumber.textProperty().bindBidirectional(searchInput.getEntity().deliveryNumberProperty());
		//		deliveryDateFrom.calendarProperty().bindBidirectional(searchInput.getEntity().deliveryDateProperty());
		supplier.valueProperty().bindBidirectional(searchInput.getEntity().supplierProperty());
		deliveryProcessingState.valueProperty().bindBidirectional(searchInput.getEntity().deliveryProcessingStateProperty());
	}

	public void buildsearchBar(){
		deliveryNumber =ViewBuilderUtils.newTextField("deliveryNumber", false);
		deliveryNumber.setPromptText("delivery Number");
		deliveryNumber.setTooltip(new Tooltip("Num Livraison"));
		deliveryNumber.setPrefHeight(40d);

		//		deliveryDateFrom =ViewBuilderUtils.newCalendarTextField("deliveryDateFrom", "dd-MM-yyyy HH:mm", locale, false);
		//		deliveryDateFrom.setPromptText("date From");
		//		deliveryDateFrom.setPrefWidth(160d);
		//		HBox.setMargin(deliveryDateFrom, new Insets(15, 0, 0, 0));

		supplier =ViewBuilderUtils.newComboBox(null, "supplier", false);
		supplier.setPromptText("All Suppliers");
		supplier.setTooltip(new Tooltip("Fournisseurs"));
		supplier.setPrefWidth(300d);
		supplier.setPrefHeight(40d);


		deliveryProcessingState =ViewBuilderUtils.newComboBox(null, "deliveryProcessingState", resourceBundle, DocumentProcessingState.valuesWithNull(), false);
		deliveryProcessingState.setPromptText("state");
		deliveryProcessingState.setPrefHeight(40d);
		deliveryProcessingState.setTooltip(new Tooltip("Status"));

		searchButton =ViewBuilderUtils.newButton("Entity_search.title", "searchButton", resourceBundle, AwesomeIcon.SEARCH);
		searchButton.setPrefHeight(40d);
		searchBar.getChildren().addAll(deliveryNumber,supplier,deliveryProcessingState,searchButton);
	}

	public Button getCreateButton()
	{
		return createButton;
	}

	public Button getSearchButton()
	{
		return searchButton;
	}

	public TableView<Delivery> getDataList()
	{
		return dataList;
	}

	public TableView<DeliveryItem> getDataListItem()
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

	public TextField getDeliveryNumber() {
		return deliveryNumber;
	}

	//	public CalendarTextField getDeliveryDateFrom() {
	//		return deliveryDateFrom;
	//	}

	public ComboBox<DeliverySupplier> getSupplier() {
		return supplier;
	}

	public ComboBox<DocumentProcessingState> getDeliveryProcessingState() {
		return deliveryProcessingState;
	}


	public Button getProcessButton() {
		return processButton;
	}

	public HBox getSearchBar() {
		return searchBar;
	}

	public Button getUpdateButton() {
		return updateButton;
	}

	public Button getExportToXlsButton() {
		return exportToXlsButton;
	}

	public Button getRemoveButton() {
		return removeButton;
	}

	public CalendarTextField getDeliveryDateFrom() {
		return deliveryDateFrom;
	}

	public Button getComputeButton() {
		return computeButton;
	}

	public Button getPrintButton() {
		return printButton;
	}

	public PieChart getPieChart() {
		return pieChart;
	}

	public TableView<ChartData> getPieChartData() {
		return pieChartData;
	}

	public ComboBox<DeliverySupplier> getChartSupplierList() {
		return chartSupplierList;
	}

	public ComboBox<Integer> getYearList() {
		return yearList;
	}

	public ResourceBundle getDeliveryProcessingStateBundle() {
		return deliveryProcessingStateBundle;
	}

	public DocumentProcessingStateConverter getDeliveryProcessingStateConverter() {
		return deliveryProcessingStateConverter;
	}

	public DocumentProcessingStateListCellFatory getDeliveryProcessingStateListCellFatory() {
		return deliveryProcessingStateListCellFatory;
	}

	public Locale getLocale() {
		return locale;
	}

	public ResourceBundle getResourceBundle() {
		return resourceBundle;
	}

	public FXMLLoader getFxmlLoader() {
		return fxmlLoader;
	}

}
