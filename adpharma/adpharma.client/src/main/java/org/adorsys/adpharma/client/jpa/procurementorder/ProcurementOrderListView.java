package org.adorsys.adpharma.client.jpa.procurementorder;

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
import org.adorsys.adpharma.client.jpa.documentprocessingstate.DocumentProcessingState;
import org.adorsys.adpharma.client.jpa.documentprocessingstate.DocumentProcessingStateConverter;
import org.adorsys.adpharma.client.jpa.documentprocessingstate.DocumentProcessingStateListCellFatory;
import org.adorsys.adpharma.client.jpa.procurementorderitem.ProcurementOrderItem;
import org.adorsys.adpharma.client.jpa.vat.VAT;
import org.adorsys.javaext.format.NumberType;
import org.adorsys.javafx.crud.extensions.FXMLLoaderUtils;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.ComboBoxInitializer;
import org.adorsys.javafx.crud.extensions.view.ViewBuilder;
import org.adorsys.javafx.crud.extensions.view.ViewBuilderUtils;

import de.jensd.fx.fontawesome.AwesomeIcon;

public class ProcurementOrderListView
{


	@FXML
	BorderPane rootPane;

	@FXML
	HBox searchBar;

	private TextField procurementOrderNumber ;

	private ComboBox<ProcurementOrderSupplier> supplier ;

	private ComboBox<DocumentProcessingState> state ;

	@FXML
	private Button searchButton;
	
	@FXML
	private Button advancedSearchButton;

	@FXML
	private Button createButton;

	@FXML
	private Button editButton ;
	
	@FXML
	private Button sentButton ;
	
	@FXML
	private Button retreivedButton ;

	@FXML
	private Button removeButton ;

	@FXML
	private Button printButton ;

	@FXML
	private TableView<ProcurementOrder> dataList;

	@FXML
	private TableView<ProcurementOrderItem> dataListItem;

	@Inject
	private Locale locale;

	@FXML
	private Pagination pagination;

	@Inject
	@Bundle({ CrudKeys.class
		, ProcurementOrder.class,ProcurementOrderItem.class
		, Agency.class
		, VAT.class
	})
	private ResourceBundle resourceBundle;

	@Inject
	private DocumentProcessingStateConverter documentProcessingStateConverter;

	@Inject
	private FXMLLoader fxmlLoader ;
	
	@Inject
	private DocumentProcessingStateListCellFatory procurementOrderStatusListCellFatory;


	@Inject
	@Bundle(DocumentProcessingState.class)
	private ResourceBundle procurementOrderStatusBundle;



	@PostConstruct
	public void postConstruct()
	{
		FXMLLoaderUtils.load(fxmlLoader, this, resourceBundle);
		ViewBuilder viewBuilder = new ViewBuilder();
		//      dataList = viewBuilder.addTable("dataList");
		viewBuilder.addStringColumn(dataList, "procurementOrderNumber", "ProcurementOrder_procurementOrderNumber_description.title", resourceBundle);
		viewBuilder.addEnumColumn(dataList, "poStatus", "ProcurementOrder_poStatus_description.title", resourceBundle, documentProcessingStateConverter);
		ViewBuilderUtils.newStringColumn(dataList, "supplier", "ProcurementOrder_supplier_description.title", resourceBundle,250d);
		ViewBuilderUtils.newDateColumn(dataList, "createdDate", "ProcurementOrder_createdDate_description.title", resourceBundle,"dd-MM-yyyy HH:mm",locale);
		viewBuilder.addBigDecimalColumn(dataList, "amountBeforeTax", "ProcurementOrder_amountBeforeTax_description.title", resourceBundle, NumberType.INTEGER, locale);
		//		viewBuilder.addBigDecimalColumn(dataList, "amountAfterTax", "ProcurementOrder_amountAfterTax_description.title", resourceBundle, NumberType.CURRENCY, locale);
		//		viewBuilder.addBigDecimalColumn(dataList, "amountDiscount", "ProcurementOrder_amountDiscount_description.title", resourceBundle, NumberType.CURRENCY, locale);
		viewBuilder.addBigDecimalColumn(dataList, "taxAmount", "ProcurementOrder_taxAmount_description.title", resourceBundle, NumberType.CURRENCY, locale);
		viewBuilder.addBigDecimalColumn(dataList, "netAmountToPay", "ProcurementOrder_netAmountToPay_description.title", resourceBundle, NumberType.CURRENCY, locale);
		//		viewBuilder.addBigDecimalColumn(dataList, "rate", "VAT_rate_description.title", resourceBundle, NumberType.PERCENTAGE, locale);
		//		datalistitem columns
		viewBuilder.addStringColumn(dataListItem, "mainPic", "ProcurementOrderItem_mainPic_description.title", resourceBundle);
		viewBuilder.addStringColumn(dataListItem, "secondaryPic", "ProcurementOrderItem_secondaryPic_description.title", resourceBundle);
		ViewBuilderUtils.newStringColumn(dataListItem, "articleName", "ProcurementOrderItem_articleName_description.title", resourceBundle,300d);
		viewBuilder.addDateColumn(dataListItem, "expirationDate", "ProcurementOrderItem_expirationDate_description.title", resourceBundle, "dd-MM-yyyy", locale);
		viewBuilder.addBigDecimalColumn(dataListItem, "qtyOrdered", "ProcurementOrderItem_qtyOrdered_description.title", resourceBundle, NumberType.INTEGER, locale);
		viewBuilder.addBigDecimalColumn(dataListItem, "availableQty", "ProcurementOrderItem_availableQty_description.title", resourceBundle, NumberType.INTEGER, locale);
		viewBuilder.addBigDecimalColumn(dataListItem, "article.qtyInStock", "ProcurementOrderItem_stockQuantity_description.title", resourceBundle, NumberType.INTEGER, locale);
//		viewBuilder.addBigDecimalColumn(dataListItem, "salesPricePU", "ProcurementOrderItem_salesPricePU_description.title", resourceBundle, NumberType.CURRENCY, locale);
		viewBuilder.addBigDecimalColumn(dataListItem, "purchasePricePU", "ProcurementOrderItem_purchasePricePU_description.title", resourceBundle, NumberType.CURRENCY, locale);


		//      pagination = viewBuilder.addPagination();
		//      viewBuilder.addSeparator();
		//      HBox buttonBar = viewBuilder.addButtonBar();
		//      createButton = viewBuilder.addButton(buttonBar, "Entity_create.title", "createButton", resourceBundle, AwesomeIcon.SAVE);
		//      searchButton = viewBuilder.addButton(buttonBar, "Entity_search.title", "searchButton", resourceBundle, AwesomeIcon.SEARCH);
		//      rootPane = viewBuilder.toAnchorPane();

		buildsearchBar();
		ComboBoxInitializer.initialize(state, documentProcessingStateConverter, procurementOrderStatusListCellFatory, procurementOrderStatusBundle);
	}

	public void buildsearchBar(){
		procurementOrderNumber =ViewBuilderUtils.newTextField("procurementOrderNumber", false);
		procurementOrderNumber.setPromptText(resourceBundle.getString("ProcurementOrder_procurementOrderNumber_description.title"));
		procurementOrderNumber.setPrefHeight(40d);

		supplier =ViewBuilderUtils.newComboBox(null, "supplier", false);
		supplier.setPromptText(resourceBundle.getString("ProcurementOrder_supplier_description.title"));
		supplier.setPrefHeight(40d);
		supplier.setPrefWidth(300d);

		state = ViewBuilderUtils.newComboBox(null, "poStatus", resourceBundle, DocumentProcessingState.valuesWithNull(), false);
		state.setPrefHeight(40d);

		searchButton =ViewBuilderUtils.newButton("Entity_search.title", "searchButton", resourceBundle, AwesomeIcon.SEARCH);
		searchButton.setPrefHeight(40d);
		searchBar.getChildren().addAll(procurementOrderNumber,supplier,state,searchButton);
	}

	public void bind(ProcurementOrderSearchInput searchInput)
	{
		procurementOrderNumber.textProperty().bindBidirectional(searchInput.getEntity().procurementOrderNumberProperty());
		supplier.valueProperty().bindBidirectional(searchInput.getEntity().supplierProperty());
		state.valueProperty().bindBidirectional(searchInput.getEntity().poStatusProperty());
	}

	public Button getCreateButton()
	{
		return createButton;
	}

	public Button getEditButton()
	{
		return editButton;
	}

	public Button getRemoveButton()
	{
		return removeButton;
	}

	public Button getSearchButton()
	{
		return searchButton;
	}

	public Button getPrintButton()
	{
		return printButton;
	}
	
	public Button getAdvancedSearchButton() {
		return advancedSearchButton;
	}
	
	public Button getSentButton()
	{
		return sentButton;
	}
	
	public Button getRetreivedButton()
	{
		return retreivedButton;
	}

	public TableView<ProcurementOrderItem> getDataListItem()
	{
		return dataListItem;
	}

	public TableView<ProcurementOrder> getDataList()
	{
		return dataList;
	}

	public BorderPane getRootPane()
	{
		return rootPane;
	}

	public ComboBox<ProcurementOrderSupplier> getSupplier()
	{
		return supplier;
	}

	public ComboBox<DocumentProcessingState> getState()
	{
		return state;
	}

	public TextField getProcurementOrderNumber()
	{
		return procurementOrderNumber;
	}

	public Pagination getPagination()
	{
		return pagination;
	}

}
