package org.adorsys.adpharma.client.jpa.inventory;

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
import org.adorsys.adpharma.client.jpa.documentprocessingstate.DocumentProcessingStateConverter;
import org.adorsys.adpharma.client.jpa.inventoryitem.InventoryItem;
import org.adorsys.javaext.format.NumberType;
import org.adorsys.javafx.crud.extensions.FXMLLoaderUtils;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.ViewBuilder;
import org.adorsys.javafx.crud.extensions.view.ViewBuilderUtils;

import de.jensd.fx.fontawesome.AwesomeIcon;

public class InventoryListView
{

	@FXML
	BorderPane rootPane;

	@FXML
	private Button searchButton;

	@FXML
	private Button createButton;

	@FXML
	private Button printButton;
	
	@FXML
	private Button removeButton;
	
	@FXML
	private Button editButton;

	@FXML
	HBox searchBar;

	private TextField invoiceNumber ;

	private CheckBox cashed ;

	@FXML
	private TableView<Inventory> dataList;

	@FXML
	private TableView<InventoryItem> dataListItem;

	@Inject
	private Locale locale;

	@FXML
	private Pagination pagination;

	@Inject
	@Bundle({ CrudKeys.class
		, Inventory.class
		, Agency.class,InventoryItem.class
	})
	private ResourceBundle resourceBundle;

	@Inject
	private DocumentProcessingStateConverter documentProcessingStateConverter;

	@Inject
	private FXMLLoader fxmlLoader;

	@PostConstruct
	public void postConstruct()
	{
		FXMLLoaderUtils.load(fxmlLoader, this, resourceBundle);
		ViewBuilder viewBuilder = new ViewBuilder();
		//		dataList = viewBuilder.addTable("dataList");
		viewBuilder.addStringColumn(dataList, "inventoryNumber", "Inventory_inventoryNumber_description.title", resourceBundle);
		viewBuilder.addStringColumn(dataList, "agency", "Inventory_agency_description.title", resourceBundle);
		viewBuilder.addStringColumn(dataList, "section", "Inventory_section_description.title", resourceBundle,300d);
		viewBuilder.addStringColumn(dataList, "recordingUser", "Inventory_recordingUser_description.title", resourceBundle);
		viewBuilder.addDateColumn(dataList, "inventoryDate", "Inventory_inventoryDate_description.title", resourceBundle,"dd-MM-yyyy",locale);



		viewBuilder.addBigDecimalColumn(dataList, "gapSaleAmount", "Inventory_gapSaleAmount_description.title", resourceBundle, NumberType.CURRENCY, locale);
		viewBuilder.addBigDecimalColumn(dataList, "gapPurchaseAmount", "Inventory_gapPurchaseAmount_description.title", resourceBundle, NumberType.CURRENCY, locale);
		viewBuilder.addEnumColumn(dataList, "inventoryStatus", "Inventory_inventoryStatus_description.title", resourceBundle, documentProcessingStateConverter);
		//		pagination = viewBuilder.addPagination();
		//		viewBuilder.addSeparator();

		//		HBox buttonBar = viewBuilder.addButtonBar();
		//		createButton = viewBuilder.addButton(buttonBar, "Entity_create.title", "createButton", resourceBundle, AwesomeIcon.SAVE);
		//		searchButton = viewBuilder.addButton(buttonBar, "Entity_search.title", "searchButton", resourceBundle, AwesomeIcon.SEARCH);
		//		rootPane = viewBuilder.toAnchorPane();

		viewBuilder.addStringColumn(dataListItem, "internalPic", "InventoryItem_internalPic_description.title", resourceBundle);
		viewBuilder.addStringColumn(dataListItem, "article", "InventoryItem_article_description.title", resourceBundle,350d);
		viewBuilder.addBigDecimalColumn(dataListItem, "expectedQty", "InventoryItem_expectedQty_description.title", resourceBundle, NumberType.INTEGER, locale);
		viewBuilder.addBigDecimalColumn(dataListItem, "asseccedQty", "InventoryItem_asseccedQty_description.title", resourceBundle, NumberType.INTEGER, locale);
		viewBuilder.addSimpleNumberColumn(dataListItem, "gap", "InventoryItem_gap_description.title", resourceBundle);
		viewBuilder.addBigDecimalColumn(dataListItem, "gapSalesPricePU", "InventoryItem_gapSalesPricePU_description.title", resourceBundle, NumberType.CURRENCY, locale);
		viewBuilder.addBigDecimalColumn(dataListItem, "gapPurchasePricePU", "InventoryItem_gapPurchasePricePU_description.title", resourceBundle, NumberType.CURRENCY, locale);
		viewBuilder.addBigDecimalColumn(dataListItem, "gapTotalSalePrice", "InventoryItem_gapTotalSalePrice_description.title", resourceBundle, NumberType.CURRENCY, locale);
		viewBuilder.addBigDecimalColumn(dataListItem, "gapTotalPurchasePrice", "InventoryItem_gapTotalPurchasePrice_description.title", resourceBundle, NumberType.CURRENCY, locale);

		buildsearchBar();
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

		createButton =ViewBuilderUtils.newButton("Entity_create.title", "createButton", resourceBundle, AwesomeIcon.ADJUST);
		createButton.setPrefHeight(40d);
		

		editButton =ViewBuilderUtils.newButton("Entity_edit.title", "editButton", resourceBundle, AwesomeIcon.EDIT);
		editButton.setPrefHeight(40d);
		
		removeButton =ViewBuilderUtils.newButton("Entity_delete.title", "removeButton", resourceBundle, AwesomeIcon.TRASH_ALT);
		removeButton.setPrefHeight(40d);
		
		printButton =ViewBuilderUtils.newButton("Entity_print.title", "printButton", resourceBundle, AwesomeIcon.PRINT);
		printButton.setPrefHeight(40d);


		searchBar.getChildren().addAll(invoiceNumber,cashed,searchButton,createButton,editButton,removeButton,printButton);
	}
	public Button getCreateButton()
	{
		return createButton;
	}

	public Button getSearchButton()
	{
		return searchButton;
	}

	public TableView<Inventory> getDataList()
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


	public Button getPrintButton() {
		return printButton;
	}


	public HBox getSearchBar() {
		return searchBar;
	}


	public TextField getInvoiceNumber() {
		return invoiceNumber;
	}


	public CheckBox getCashed() {
		return cashed;
	}


	public TableView<InventoryItem> getDataListItem() {
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


	public Button getRemoveButton() {
		return removeButton;
	}


	public Button getEditButton() {
		return editButton;
	}
	

}
