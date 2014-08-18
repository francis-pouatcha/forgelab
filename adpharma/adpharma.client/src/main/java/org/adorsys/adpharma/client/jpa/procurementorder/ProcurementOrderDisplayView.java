package org.adorsys.adpharma.client.jpa.procurementorder;

import java.math.BigDecimal;
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Singleton;

import jfxtras.scene.control.CalendarTextField;

import org.adorsys.adpharma.client.jpa.delivery.Delivery;
import org.adorsys.adpharma.client.jpa.documentprocessingstate.DocumentProcessingState;
import org.adorsys.adpharma.client.jpa.documentprocessingstate.DocumentProcessingStateConverter;
import org.adorsys.adpharma.client.jpa.documentprocessingstate.DocumentProcessingStateListCellFatory;
import org.adorsys.adpharma.client.jpa.procmtordertriggermode.ProcmtOrderTriggerMode;
import org.adorsys.adpharma.client.jpa.procmtordertriggermode.ProcmtOrderTriggerModeConverter;
import org.adorsys.adpharma.client.jpa.procmtordertriggermode.ProcmtOrderTriggerModeListCellFatory;
import org.adorsys.adpharma.client.jpa.procurementorderitem.ProcurementOrderItem;
import org.adorsys.adpharma.client.jpa.procurementordertype.ProcurementOrderType;
import org.adorsys.javaext.format.NumberType;
import org.adorsys.javafx.crud.extensions.FXMLLoaderUtils;
import org.adorsys.javafx.crud.extensions.control.BigDecimalField;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.ComboBoxInitializer;
import org.adorsys.javafx.crud.extensions.view.ViewBuilder;
import org.adorsys.javafx.crud.extensions.view.ViewBuilderUtils;

import de.jensd.fx.fontawesome.AwesomeIcon;

@Singleton
public class ProcurementOrderDisplayView
{
	@FXML
	private BorderPane rootPane;
	@FXML
	private HBox actionbar ;

	private Button saveButton;

	private Button editButton;

	private Button addButton;

	private Button printButton;
	
	private Button printXlsButton ;

	private Button cancelButton;

	@FXML
	TableView<ProcurementOrderItem> dataList;

	@FXML
	private GridPane itemBar;

	@Inject
	private ProcurementOrderView view;

	@Inject
	FXMLLoader fxmlLoader;

	@Inject
	Locale locale;

	@FXML
	private GridPane leftGrid;

	private TextField articleName;

	private TextField mainPic;

	private BigDecimalField qtyOrdered;

	private BigDecimalField purchasePricePU;

	private BigDecimalField salesPricePU;

	private BigDecimalField totalPurchasePrice;

	private BigDecimalField amountBeforeTax ;

	private BigDecimalField amountNetToPay ;

	private Button okButton ;

	private CalendarTextField recordingDate;

	@Inject
	@Bundle(ProcmtOrderTriggerMode.class)
	private ResourceBundle procmtOrderTriggerModeBundle;

	@Inject
	private ProcmtOrderTriggerModeConverter procmtOrderTriggerModeConverter;

	@Inject
	private ProcmtOrderTriggerModeListCellFatory procmtOrderTriggerModeListCellFatory;
	@Inject
	@Bundle(ProcurementOrderType.class)
	private ResourceBundle procurementOrderTypeBundle;

	@Inject
	@Bundle(DocumentProcessingState.class)
	private ResourceBundle poStatusBundle;

	@Inject
	private DocumentProcessingStateConverter poStatusConverter;

	@Inject
	private DocumentProcessingStateListCellFatory poStatusListCellFatory;


	@FXML
	private TextField procurementOrderNumber;

	@FXML
	private ComboBox<ProcurementOrderSupplier> supplier;

	@FXML
	private ComboBox<ProcmtOrderTriggerMode> procmtOrderTriggerMode;

	@FXML
	private ComboBox<ProcurementOrderCreatingUser> creatingUser;

	@FXML
	private ComboBox<DocumentProcessingState> poStatus;

	private TableColumn<ProcurementOrderItem, BigDecimal> orderQuantityColumn;

	private TableColumn<ProcurementOrderItem, BigDecimal> salesPricePUColumn;

	private TableColumn<ProcurementOrderItem, BigDecimal> purchasePricePUColumn;
	
	private TableColumn<ProcurementOrderItem, BigDecimal> availableQtyColumn;

	@Inject
	@Bundle({ CrudKeys.class, ProcurementOrder.class ,ProcurementOrderItem.class,Delivery.class})
	private ResourceBundle resourceBundle;

	@PostConstruct
	public void postConstruct()
	{
		FXMLLoaderUtils.load(fxmlLoader, this, resourceBundle);
		ViewBuilder viewBuilder = new ViewBuilder();

		viewBuilder.addStringColumn(dataList, "mainPic", "ProcurementOrderItem_mainPic_description.title", resourceBundle);
		viewBuilder.addStringColumn(dataList, "secondaryPic", "ProcurementOrderItem_secondaryPic_description.title", resourceBundle);
		ViewBuilderUtils.newStringColumn(dataList, "articleName", "ProcurementOrderItem_articleName_description.title", resourceBundle,330d);
		orderQuantityColumn = viewBuilder.addEditableBigDecimalColumn(dataList, "qtyOrdered", "ProcurementOrderItem_qtyOrdered_description.title", resourceBundle, NumberType.INTEGER, locale);
		availableQtyColumn = viewBuilder.addEditableBigDecimalColumn(dataList, "availableQty", "ProcurementOrderItem_availableQty_description.title", resourceBundle, NumberType.INTEGER, locale);
		viewBuilder.addBigDecimalColumn(dataList, "stockQuantity", "ProcurementOrderItem_stockQuantity_description.title", resourceBundle, NumberType.INTEGER, locale);
		salesPricePUColumn = viewBuilder.addEditableBigDecimalColumn(dataList, "salesPricePU", "ProcurementOrderItem_salesPricePU_description.title", resourceBundle, NumberType.INTEGER, locale);
		purchasePricePUColumn = viewBuilder.addEditableBigDecimalColumn(dataList, "purchasePricePU", "ProcurementOrderItem_purchasePricePU_description.title", resourceBundle, NumberType.INTEGER, locale);
		viewBuilder.addBigDecimalColumn(dataList, "totalPurchasePrice", "ProcurementOrderItem_totalPurchasePrice_description.title", resourceBundle, NumberType.CURRENCY, locale);

		//		viewBuilder.addMainForm(view, ViewType.DISPLAY, true);
		//		viewBuilder.addSeparator();
		//		List<HBox> doubleButtonBar = viewBuilder.addDoubleButtonBar();
		//		buttonBarLeft = doubleButtonBar.get(0);
		//		confirmSelectionButton = viewBuilder.addButton(buttonBarLeft, "Entity_select.title", "confirmSelectionButton", resourceBundle);
		//		HBox buttonBarRight = doubleButtonBar.get(1);
		//		editButton = viewBuilder.addButton(buttonBarRight, "Entity_edit.title", "editButton", resourceBundle, AwesomeIcon.EDIT);
		//		removeButton = viewBuilder.addButton(buttonBarRight, "Entity_remove.title", "removeButton", resourceBundle, AwesomeIcon.TRASH_ALT);
		//		searchButton = viewBuilder.addButton(buttonBarRight, "Entity_search.title", "searchButton", resourceBundle, AwesomeIcon.SEARCH);
		//		rootPane = viewBuilder.toAnchorPane();
		buildItemBar();
		buildActionBar();
		buildLeftGrid();

		ComboBoxInitializer.initialize(procmtOrderTriggerMode, procmtOrderTriggerModeConverter, procmtOrderTriggerModeListCellFatory, procmtOrderTriggerModeBundle);
		ComboBoxInitializer.initialize(poStatus, poStatusConverter, poStatusListCellFatory, poStatusBundle);

	}

	public void bind(ProcurementOrder model)
	{
		//		view.bind(model);
		procurementOrderNumber.textProperty().bindBidirectional(model.procurementOrderNumberProperty());
		poStatus.valueProperty().bindBidirectional(model.poStatusProperty());
		supplier.valueProperty().bindBidirectional(model.supplierProperty());
		procmtOrderTriggerMode.valueProperty().bindBidirectional(model.procmtOrderTriggerModeProperty());
		dataList.itemsProperty().bindBidirectional(model.procurementOrderItemsProperty());
		creatingUser.valueProperty().bindBidirectional(model.creatingUserProperty() );
		recordingDate.calendarProperty().bindBidirectional(model.createdDateProperty());
		amountNetToPay.numberProperty().bindBidirectional(model.netAmountToPayProperty());
		amountBeforeTax.numberProperty().bindBidirectional(model.amountBeforeTaxProperty());
		orderQuantityColumn.editableProperty().bind(model.poStatusProperty().isNotEqualTo(DocumentProcessingState.CLOSED));
		salesPricePUColumn.editableProperty().bind(model.poStatusProperty().isNotEqualTo(DocumentProcessingState.CLOSED));
		purchasePricePUColumn.editableProperty().bind(model.poStatusProperty().isNotEqualTo(DocumentProcessingState.CLOSED));
		availableQtyColumn.editableProperty().bind(model.poStatusProperty().isNotEqualTo(DocumentProcessingState.CLOSED));
		saveButton.disableProperty().bind(model.poStatusProperty().isEqualTo(DocumentProcessingState.CLOSED));
		itemBar.visibleProperty().bind(model.poStatusProperty().isNotEqualTo(DocumentProcessingState.CLOSED));

	}

	public void bind(ProcurementOrderItem model) {
		articleName.textProperty().bindBidirectional(model.articleNameProperty());
		mainPic.textProperty().bindBidirectional(model.mainPicProperty());
		qtyOrdered.numberProperty().bindBidirectional(model.qtyOrderedProperty());
		totalPurchasePrice.numberProperty().bindBidirectional(model.totalPurchasePriceProperty());
		salesPricePU.numberProperty().bindBidirectional(model.salesPricePUProperty());
		purchasePricePU.numberProperty().bindBidirectional(model.purchasePricePUProperty());
	}
	public void buildLeftGrid(){
		recordingDate =ViewBuilderUtils.newCalendarTextField("recordingDate", "dd-MM-yyyy HH:mm", locale, false);
		recordingDate.setPrefWidth(180d);
		recordingDate.setDisable(true);
		leftGrid.add(recordingDate, 1, 1);

		amountBeforeTax =ViewBuilderUtils.newBigDecimalField("amountBeforeTax", NumberType.CURRENCY, locale,false);
		amountBeforeTax.setPrefWidth(180d);
		amountBeforeTax.setEditable(false);
		leftGrid.add(amountBeforeTax, 1, 2);

		amountNetToPay =ViewBuilderUtils.newBigDecimalField("amountNetToPay", NumberType.CURRENCY, locale,false);
		amountNetToPay.setPrefWidth(180d);
		amountNetToPay.setEditable(false);
		leftGrid.add(amountNetToPay, 1, 3);


	}

	public void buildActionBar(){
		saveButton = ViewBuilderUtils.newButton("Entity_save.title", "saveButton", resourceBundle, AwesomeIcon.SAVE);
		saveButton.setAlignment(Pos.CENTER_LEFT);
		saveButton.setText("Livrer");

		cancelButton = ViewBuilderUtils.newButton( "Entity_cancel.title", "cancelButton", resourceBundle, AwesomeIcon.STOP);
		cancelButton.setAlignment(Pos.CENTER_LEFT);

		printButton = ViewBuilderUtils.newButton( "Delivery_print_description.title", "printButton", resourceBundle, AwesomeIcon.PRINT);
		printButton.setAlignment(Pos.CENTER_LEFT);
		printButton.setText("Exporter en Pdf");
		
		printXlsButton = ViewBuilderUtils.newButton( "Delivery_print_description.title", "printButton", resourceBundle, AwesomeIcon.PRINT);
		printXlsButton.setAlignment(Pos.CENTER_LEFT);
		printXlsButton.setText("Exporter en Xls");

		actionbar.getChildren().addAll(saveButton,cancelButton,printButton,printXlsButton);

	}

	public void buildItemBar(){
		mainPic = ViewBuilderUtils.newTextField( "mainPic", false);
		mainPic.setPromptText("cip");
		mainPic.setTooltip(new Tooltip("cip"));

		articleName = ViewBuilderUtils.newTextField("articleName", false);
		articleName.setPromptText("Designation");
		articleName.setPrefWidth(300d);
		articleName.setTooltip(new Tooltip("Designation article"));

		qtyOrdered = ViewBuilderUtils.newBigDecimalField( "freeQuantity", NumberType.INTEGER,locale,false);
		qtyOrdered.setTooltip(new Tooltip("Unite gratuite"));
		qtyOrdered.setPrefWidth(75d);

		salesPricePU = ViewBuilderUtils.newBigDecimalField("salesPricePU", NumberType.CURRENCY, locale,false);
		salesPricePU.setTooltip(new Tooltip("Prix de vente unitaire"));
		salesPricePU.setPrefWidth(100d);

		purchasePricePU = ViewBuilderUtils.newBigDecimalField( "purchasePricePU", NumberType.CURRENCY, locale,false);
		purchasePricePU.setTooltip(new Tooltip("Prix d achat unitaire"));
		purchasePricePU.setPrefWidth(100d);

		totalPurchasePrice = ViewBuilderUtils.newBigDecimalField("salesPricePU", NumberType.CURRENCY, locale,false);
		totalPurchasePrice.setTooltip(new Tooltip("expiration Date"));
		totalPurchasePrice.setPrefWidth(100d);
		totalPurchasePrice.setEditable(false);

		okButton = ViewBuilderUtils.newButton("Entity_ok.text", "ok", resourceBundle, AwesomeIcon.ARROW_DOWN);

		itemBar.addRow(0,new Label("CIP"),new Label("Designation"),new Label("Qte"),new Label("P.V"),new Label("P.A"),new Label("Prix .T"));
		itemBar.addRow(1,mainPic,articleName,qtyOrdered,salesPricePU,purchasePricePU,totalPurchasePrice,okButton);

	}


	public BorderPane getRootPane()
	{
		return rootPane;
	}

	public Button getSaveButton()
	{
		return saveButton;
	}

	public Button getCancelButton()
	{
		return cancelButton;
	}

	public Button getOkButton(){
		return okButton ;
	}

	public Button getPrintButton(){
		return printButton ;
	}
	
	public Button getPrintXlsButton(){
		return printXlsButton ;
	}

	public TextField getArticleName(){
		return articleName ;
	}

	public ProcurementOrderView getView()
	{
		return view;
	}
	public TableView<ProcurementOrderItem> getDataList(){
		return dataList ;
	}


	public TableColumn<ProcurementOrderItem, BigDecimal> getOrderQuantityColumn() {
		return orderQuantityColumn;
	}
	public TableColumn<ProcurementOrderItem, BigDecimal> getSalesPricePUColumn() {
		return salesPricePUColumn;
	}
	public TableColumn<ProcurementOrderItem, BigDecimal> getPurchasePricePUColumn() {
		return purchasePricePUColumn;
	}

public TableColumn<ProcurementOrderItem, BigDecimal> getAvailableQtyColumn() {
	return availableQtyColumn;
}
	//
	//	public Button getConfirmSelectionButton()
	//	{
	//		return confirmSelectionButton;
	//	}
}
