package org.adorsys.adpharma.client.jpa.delivery;

import java.util.Locale;
import java.util.ResourceBundle;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableBooleanValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Singleton;

import jfxtras.scene.control.CalendarTextField;

import org.adorsys.adpharma.client.jpa.deliveryitem.DeliveryItem;
import org.adorsys.adpharma.client.jpa.documentprocessingstate.DocumentProcessingState;
import org.adorsys.adpharma.client.jpa.vat.VAT;
import org.adorsys.javaext.format.NumberType;
import org.adorsys.javafx.crud.extensions.FXMLLoaderUtils;
import org.adorsys.javafx.crud.extensions.control.BigDecimalField;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.ViewBuilder;
import org.adorsys.javafx.crud.extensions.view.ViewBuilderUtils;

import de.jensd.fx.fontawesome.AwesomeIcon;

@Singleton
public class DeliveryDisplayView
{

	@FXML
	BorderPane rootPane;

	@FXML
	private VBox actionbar ;

	private Button saveButton;

	private Button deleteButton;

	private Button editButton;

	private Button addButton;

	private Button printButton;

	private Button cancelButton;

	private Button addArticleButton ;

	private Button okButton ;


	@FXML
	private TextField deliveryNumber;

	@FXML
	private TextField deliverySlipNumber;

	private CalendarTextField recordingDate;

	private CalendarTextField deliveryDate;

	@FXML
	private ComboBox<DeliverySupplier> supplier;


	@FXML
	private ComboBox<DeliveryReceivingAgency> agency;

	@FXML
	private ComboBox<DeliveryCreatingUser> login;


	@FXML
	private ComboBox<DocumentProcessingState> state;

	private BigDecimalField amountBeforeTax;

	private BigDecimalField taxAmount;

	private BigDecimalField amountDiscount;

	private BigDecimalField amountAfterTax;

	private BigDecimalField processAmont;

	@FXML
	private GridPane amountPane ;

	@FXML
	private ContextMenu datalistContextMenu;

	@FXML
	private MenuItem deleteDeliveryMenu;

	@FXML
	private MenuItem editDeliveryMenu;

	private TextField articleName;

	private TextField mainPic;

	private BigDecimalField stockQuantity;

	private BigDecimalField freeQuantity;

	private BigDecimalField purchasePricePU;

	private BigDecimalField salesPricePU;

	private TextField expirationDate;

	@FXML
	private ComboBox<VAT> tax;

	@FXML
	private GridPane leftGride;

	@FXML
	TableView<DeliveryItem> dataList;

	@FXML
	private GridPane deliveryItemBar;

	@Inject
	private DeliveryView view;

	@Inject
	FXMLLoader fxmlLoader;

	@Inject
	Locale locale;

	@Inject
	@Bundle({ CrudKeys.class, Delivery.class,DeliveryItem.class })
	private ResourceBundle resourceBundle;

	@PostConstruct
	public void postConstruct()
	{
		FXMLLoaderUtils.load(fxmlLoader, this, resourceBundle);
		//		ViewBuilder viewBuilder = new ViewBuilder();
		//		viewBuilder.addMainForm(view, ViewType.EDIT, false);
		//      viewBuilder.addSeparator();
		//      HBox buttonBar = viewBuilder.addButtonBar();
		//      saveButton = viewBuilder.addButton(buttonBar, "Entity_save.title", "saveButton", resourceBundle, AwesomeIcon.SAVE);
		//      cancelButton = viewBuilder.addButton(buttonBar, "Entity_cancel.title", "cancelButton", resourceBundle, AwesomeIcon.STOP);
		//      rootPane = viewBuilder.toAnchorPane();

		ViewBuilder viewBuilder = new ViewBuilder();
		viewBuilder.addStringColumn(dataList, "mainPic", "DeliveryItem_mainPic_description.title", resourceBundle);
		viewBuilder.addStringColumn(dataList, "articleName", "DeliveryItem_articleName_description.title", resourceBundle,300d);
		viewBuilder.addBigDecimalColumn(dataList, "stockQuantity", "DeliveryItem_stockQuantity_description.title", resourceBundle, NumberType.INTEGER, locale);
		viewBuilder.addBigDecimalColumn(dataList, "freeQuantity", "DeliveryItem_freeQuantity_description.title", resourceBundle, NumberType.INTEGER, locale);
		//		viewBuilder.addDateColumn(dataList, "expirationDate", "DeliveryItem_expirationDate_description.title", resourceBundle, "dd-MM-yyyy", locale);
		viewBuilder.addBigDecimalColumn(dataList, "salesPricePU", "DeliveryItem_salesPricePU_description.title", resourceBundle, NumberType.CURRENCY, locale);
		viewBuilder.addBigDecimalColumn(dataList, "purchasePricePU", "DeliveryItem_purchasePricePU_description.title", resourceBundle, NumberType.CURRENCY, locale);
		viewBuilder.addBigDecimalColumn(dataList, "totalPurchasePrice", "DeliveryItem_totalPurchasePrice_description.title", resourceBundle, NumberType.CURRENCY, locale);

		buildDeliveryItemBar();
		buildAmountPane();
		buildLeftGrid();
		buildActionBar();
	}
	public void buildLeftGrid(){
		recordingDate =ViewBuilderUtils.newCalendarTextField("recordingDate", "dd-MM-yyyy HH:mm", locale, false);
		recordingDate.setPrefWidth(180d);
		recordingDate.setDisable(true);
		leftGride.add(recordingDate, 1, 2);

		deliveryDate =ViewBuilderUtils.newCalendarTextField("deliveryDate", "dd-MM-yyyy HH:mm", locale, false);
		deliveryDate.setPrefWidth(180d);
		deliveryDate.setDisable(true);
		leftGride.add(deliveryDate, 1, 3);


	}

	public void buildActionBar(){
		saveButton = ViewBuilderUtils.newButton("Entity_save.title", "saveButton", resourceBundle, AwesomeIcon.SAVE);
		saveButton.setPrefWidth(150d);
		saveButton.setAlignment(Pos.CENTER_LEFT);

		cancelButton = ViewBuilderUtils.newButton( "Entity_cancel.title", "cancelButton", resourceBundle, AwesomeIcon.STOP);
		cancelButton.setPrefWidth(150d);
		cancelButton.setAlignment(Pos.CENTER_LEFT);

		addArticleButton = ViewBuilderUtils.newButton( "Delivery_Add_article.title", "addArticleButton", resourceBundle, AwesomeIcon.PLUS_CIRCLE);
		addArticleButton.setPrefWidth(150d);
		addArticleButton.setAlignment(Pos.CENTER_LEFT);

		deleteButton =ViewBuilderUtils.newButton( "Entity_remove.title", "deleteButton", resourceBundle, AwesomeIcon.TRASH_ALT);
		deleteButton.setPrefWidth(150d);
		deleteButton.setAlignment(Pos.CENTER_LEFT);

		editButton  =ViewBuilderUtils.newButton( "Delivery_Edit_Head.title", "editButton", resourceBundle, AwesomeIcon.EDIT);
		editButton.setPrefWidth(150d);
		editButton.setAlignment(Pos.CENTER_LEFT);

		addButton = ViewBuilderUtils.newButton( "Delivery_New.title", "addButton", resourceBundle, AwesomeIcon.PLUS_CIRCLE);
		addButton.setPrefWidth(150d);
		addButton.setAlignment(Pos.CENTER_LEFT);
		
		printButton = ViewBuilderUtils.newButton( "Delivery_print_description.title", "printButton", resourceBundle, AwesomeIcon.PRINT);
		printButton.setPrefWidth(150d);
		printButton.setAlignment(Pos.CENTER_LEFT);

		actionbar.getChildren().addAll(saveButton,editButton,deleteButton,addArticleButton,cancelButton,addButton,printButton);

	}
	public void buildDeliveryItemBar(){
		mainPic = ViewBuilderUtils.newTextField( "mainPic", false);
		mainPic.setPromptText("cip");
		mainPic.setTooltip(new Tooltip("cip"));

		articleName = ViewBuilderUtils.newTextField("articleName", false);
		articleName.setPromptText("Designation");
		articleName.setPrefWidth(350d);
		articleName.setTooltip(new Tooltip("Designation article"));

		freeQuantity = ViewBuilderUtils.newBigDecimalField( "freeQuantity", NumberType.INTEGER,locale,false);
		freeQuantity.setTooltip(new Tooltip("Unite gratuite"));
		freeQuantity.setPrefWidth(75d);

		stockQuantity = ViewBuilderUtils.newBigDecimalField( "stockQuantity", NumberType.INTEGER, locale,false);
		stockQuantity.setTooltip(new Tooltip("Quantite livree"));
		stockQuantity.setPrefWidth(75d);

		salesPricePU = ViewBuilderUtils.newBigDecimalField("salesPricePU", NumberType.CURRENCY, locale,false);
		salesPricePU.setTooltip(new Tooltip("Prix de vente unitaire"));
		salesPricePU.setPrefWidth(110d);

		purchasePricePU = ViewBuilderUtils.newBigDecimalField( "purchasePricePU", NumberType.CURRENCY, locale,false);
		purchasePricePU.setTooltip(new Tooltip("Prix d achat unitaire"));
		purchasePricePU.setPrefWidth(110d);

		expirationDate = ViewBuilderUtils.newTextField( "expirationDate", false);
		expirationDate.setPromptText("MMYY");
		expirationDate.setTooltip(new Tooltip("expiration Date"));
		expirationDate.setPrefWidth(60d);

		okButton = ViewBuilderUtils.newButton("Entity_ok.text", "ok", resourceBundle, AwesomeIcon.ARROW_DOWN);

		deliveryItemBar.addRow(0,new Label("CIP"),new Label("Designation"),new Label("Qte"),new Label("Qte UG"),new Label("Prix de Vente")
		,new Label("Prix d\'achat"),new Label("Exp Date"));
		deliveryItemBar.addRow(1,mainPic,articleName,stockQuantity,freeQuantity,salesPricePU,purchasePricePU,expirationDate,okButton);
		//		deliveryItemBar.getChildren().addAll(
		//				mainPic,articleName,stockQuantity,freeQuantity,salesPricePU,purchasePricePU,okButton);

	}

	public void buildAmountPane(){
		amountBeforeTax = ViewBuilderUtils.newBigDecimalField( "amountBeforeTax", NumberType.CURRENCY,locale,false);
		amountBeforeTax.setEditable(false);

		amountDiscount = ViewBuilderUtils.newBigDecimalField( "amountDiscount", NumberType.CURRENCY,locale,false);
		amountDiscount.setEditable(false);

		amountAfterTax = ViewBuilderUtils.newBigDecimalField( "amountAfterTax", NumberType.CURRENCY,locale,false);
		amountAfterTax.setEditable(false);

		taxAmount = ViewBuilderUtils.newBigDecimalField( "taxAmount", NumberType.CURRENCY,locale,false);
		taxAmount.setEditable(false);

		processAmont = ViewBuilderUtils.newBigDecimalField( "processAmont", NumberType.CURRENCY,locale,false);
		processAmont.setEditable(false);

		amountPane.add(amountBeforeTax, 1, 0);
		amountPane.add(taxAmount, 1, 1);
		amountPane.add(amountDiscount, 1, 2);
		amountPane.add(amountAfterTax, 1, 3);
		amountPane.add(processAmont, 1, 4);
	}

	public void bind(Delivery model)
	{
		//		view.bind(model);
		deliveryNumber.textProperty().bindBidirectional(model.deliveryNumberProperty());
		deliverySlipNumber.textProperty().bindBidirectional(model.deliverySlipNumberProperty());
		amountBeforeTax.numberProperty().bindBidirectional(model.amountBeforeTaxProperty());
		amountDiscount.numberProperty().bindBidirectional(model.amountDiscountProperty());
		amountAfterTax.numberProperty().bindBidirectional(model.amountAfterTaxProperty());
		supplier.valueProperty().bindBidirectional(model.supplierProperty());
		state.valueProperty().bindBidirectional(model.deliveryProcessingStateProperty());
		login.valueProperty().bindBidirectional(model.creatingUserProperty());
		agency.valueProperty().bindBidirectional(model.receivingAgencyProperty());
		recordingDate.calendarProperty().bindBidirectional(model.recordingDateProperty());
		deliveryDate.calendarProperty().bindBidirectional(model.deliveryDateProperty());
		taxAmount.numberProperty().bindBidirectional(model.amountVatProperty());
		dataList.itemsProperty().bindBidirectional(model.deliveryItemsProperty());

	}

	public void bind(DeliveryItem deliveryItem) {
		articleName.textProperty().bindBidirectional(deliveryItem.articleNameProperty());
		mainPic.textProperty().bindBidirectional(deliveryItem.mainPicProperty());
		freeQuantity.numberProperty().bindBidirectional(deliveryItem.freeQuantityProperty());
		stockQuantity.numberProperty().bindBidirectional(deliveryItem.stockQuantityProperty());
		salesPricePU.numberProperty().bindBidirectional(deliveryItem.salesPricePUProperty());
		purchasePricePU.numberProperty().bindBidirectional(deliveryItem.purchasePricePUProperty());
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

	public DeliveryView getView()
	{
		return view;
	}

	public TextField getArticleName() {
		return articleName;
	}

	public TextField getDeliveryNumber() {
		return deliveryNumber;
	}

	public TextField getDeliverySlipNumber() {
		return deliverySlipNumber;
	}

	public CalendarTextField getRecordingDate() {
		return recordingDate;
	}

	public CalendarTextField getDeliveryDate() {
		return deliveryDate;
	}

	public ComboBox<DeliverySupplier> getSupplier() {
		return supplier;
	}

	public ComboBox<DeliveryReceivingAgency> getAgency() {
		return agency;
	}

	public ComboBox<DeliveryCreatingUser> getLogin() {
		return login;
	}

	public BigDecimalField getAmountBeforeTax() {
		return amountBeforeTax;
	}

	public BigDecimalField getTaxAmount() {
		return taxAmount;
	}

	public BigDecimalField getAmountDiscount() {
		return amountDiscount;
	}

	public BigDecimalField getAmountAfterTax() {
		return amountAfterTax;
	}

	public BigDecimalField getProcessAmont() {
		return processAmont;
	}

	public TextField getMainPic() {
		return mainPic;
	}

	public BigDecimalField getStockQuantity() {
		return stockQuantity;
	}

	public BigDecimalField getFreeQuantity() {
		return freeQuantity;
	}

	public BigDecimalField getPurchasePricePU() {
		return purchasePricePU;
	}

	public BigDecimalField getSalesPricePU() {
		return salesPricePU;
	}

	public TextField getExpirationDate() {
		return expirationDate;
	}

	public GridPane getAmountPane() {
		return amountPane;
	}

	public TableView<DeliveryItem> getDataList() {
		return dataList;
	}

	public MenuItem getDeleteDeliveryMenu() {
		return deleteDeliveryMenu;
	}

	public MenuItem getEditDeliveryMenu() {
		return editDeliveryMenu;
	}

	public Button getAddArticleButton() {
		return addArticleButton;
	}

	public Button getOkButton() {
		return okButton;
	}

	public Button getPrintButton() {
		return printButton;
	}

	public Button getDeleteButton() {
		return deleteButton;
	}

	public Button getEditButton() {
		return editButton;
	}

	public ComboBox<VAT> getTax() {
		return tax;
	}
	public Button getAddButton() {
		return addButton;
	}
	public GridPane getDeliveryItemBar() {
		return deliveryItemBar;
	}

	public ContextMenu getDatalistContextMenu() {
		return datalistContextMenu;
	}



}
