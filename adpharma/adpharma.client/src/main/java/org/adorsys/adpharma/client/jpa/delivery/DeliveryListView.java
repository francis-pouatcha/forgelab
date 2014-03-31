package org.adorsys.adpharma.client.jpa.delivery;

import java.util.Locale;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adorsys.javaext.format.NumberType;
import org.adorsys.javafx.crud.extensions.FXMLLoaderUtils;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.ComboBoxInitializer;
import org.adorsys.javafx.crud.extensions.view.ViewBuilder;
import org.adorsys.javafx.crud.extensions.view.ViewBuilderUtils;

import de.jensd.fx.fontawesome.AwesomeIcon;
import javafx.beans.property.SimpleStringProperty;

import java.util.Calendar;

import javafx.beans.property.SimpleObjectProperty;

import org.adorsys.adpharma.client.jpa.login.Login;
import org.adorsys.adpharma.client.jpa.supplier.Supplier;

import java.math.BigDecimal;

import org.adorsys.adpharma.client.jpa.vat.VAT;
import org.adorsys.adpharma.client.jpa.currency.Currency;
import org.adorsys.adpharma.client.jpa.documentprocessingstate.DocumentProcessingState;
import org.adorsys.adpharma.client.jpa.documentprocessingstate.DocumentProcessingStateConverter;
import org.adorsys.adpharma.client.jpa.documentprocessingstate.DocumentProcessingStateListCellFatory;
import org.adorsys.adpharma.client.jpa.agency.Agency;
import org.adorsys.adpharma.client.jpa.deliveryitem.DeliveryItem;

import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

import java.util.ArrayList;
import java.util.List;

import jfxtras.scene.control.CalendarTextField;

import org.adorsys.adpharma.client.jpa.delivery.Delivery;

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
	private Button createButton;
	
	@FXML
	private Button updateButton;
	
	@FXML
	private Button processButton;

	@FXML
	private TableView<Delivery> dataList;

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
	@Bundle({ CrudKeys.class
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
		viewBuilder.addStringColumn(dataList, "deliverySlipNumber", "Delivery_deliverySlipNumber_description.title", resourceBundle);
		viewBuilder.addStringColumn(dataList, "supplier", "Delivery_supplier_description.title", resourceBundle);
		viewBuilder.addStringColumn(dataList, "creatingUser", "Delivery_creatingUser_description.title", resourceBundle);
		//		viewBuilder.addDateColumn(dataList, "dateOnDeliverySlip", "Delivery_dateOnDeliverySlip_description.title", resourceBundle, "dd-MM-yyyy", locale);
		viewBuilder.addStringColumn(dataList, "currency", "Delivery_currency_description.title", resourceBundle);
		viewBuilder.addBigDecimalColumn(dataList, "amountBeforeTax", "Delivery_amountBeforeTax_description.title", resourceBundle, NumberType.INTEGER, locale);
		viewBuilder.addBigDecimalColumn(dataList, "amountAfterTax", "Delivery_amountAfterTax_description.title", resourceBundle, NumberType.CURRENCY, locale);
		viewBuilder.addBigDecimalColumn(dataList, "amountDiscount", "Delivery_amountDiscount_description.title", resourceBundle, NumberType.CURRENCY, locale);
		viewBuilder.addBigDecimalColumn(dataList, "netAmountToPay", "Delivery_netAmountToPay_description.title", resourceBundle, NumberType.CURRENCY, locale);
		viewBuilder.addEnumColumn(dataList, "deliveryProcessingState", "Delivery_deliveryProcessingState_description.title", resourceBundle,deliveryProcessingStateConverter);
		//      pagination = viewBuilder.addPagination();
		//      viewBuilder.addSeparator();
		//      HBox buttonBar = viewBuilder.addButtonBar();
		//      createButton = viewBuilder.addButton(buttonBar, "Entity_create.title", "createButton", resourceBundle, AwesomeIcon.SAVE);
		//      searchButton = viewBuilder.addButton(buttonBar, "Entity_search.title", "searchButton", resourceBundle, AwesomeIcon.SEARCH);
		//      rootPane = viewBuilder.toAnchorPane();
		buildsearchBar();
		ComboBoxInitializer.initialize(deliveryProcessingState, deliveryProcessingStateConverter, deliveryProcessingStateListCellFatory, deliveryProcessingStateBundle);

	}

	public void bind(DeliverySearchInput searchInput)
	{
		deliveryNumber.textProperty().bindBidirectional(searchInput.getEntity().deliveryNumberProperty());
		deliveryDateFrom.calendarProperty().bindBidirectional(searchInput.getEntity().deliveryDateProperty());
		supplier.valueProperty().bindBidirectional(searchInput.getEntity().supplierProperty());
		deliveryProcessingState.valueProperty().bindBidirectional(searchInput.getEntity().deliveryProcessingStateProperty());
	}

	public void buildsearchBar(){
		deliveryNumber =ViewBuilderUtils.newTextField("deliveryNumber", false);
		deliveryNumber.setPromptText("delivery Number");

		deliveryDateFrom =ViewBuilderUtils.newCalendarTextField("deliveryDateFrom", "dd-MM-yyyy HH:mm", locale, false);
		deliveryDateFrom.setPromptText("date From");
		deliveryDateFrom.setPrefWidth(160d);
		HBox.setMargin(deliveryDateFrom, new Insets(15, 0, 0, 0));

		supplier =ViewBuilderUtils.newComboBox(null, "supplier", false);
		supplier.setPromptText("Supplier");
		supplier.setPrefWidth(200d);

		deliveryProcessingState =ViewBuilderUtils.newComboBox(null, "deliveryProcessingState", resourceBundle, DocumentProcessingState.values(), false);
		deliveryProcessingState.setPromptText("state");

		searchButton =ViewBuilderUtils.newButton("Entity_search.title", "searchButton", resourceBundle, AwesomeIcon.SEARCH);
		searchBar.getChildren().addAll(deliveryNumber,deliveryDateFrom,supplier,deliveryProcessingState,searchButton);
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

	public CalendarTextField getDeliveryDateFrom() {
		return deliveryDateFrom;
	}

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


}
