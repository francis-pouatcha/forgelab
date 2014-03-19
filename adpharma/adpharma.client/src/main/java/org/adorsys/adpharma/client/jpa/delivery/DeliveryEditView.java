package org.adorsys.adpharma.client.jpa.delivery;

import java.util.Calendar;
import java.util.ResourceBundle;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import org.adorsys.javafx.crud.extensions.view.ComboBoxInitializer;
import org.adorsys.javafx.crud.extensions.view.ViewBuilderUtils;
import org.adorsys.javafx.crud.extensions.validation.TextInputControlValidator;
import org.adorsys.javafx.crud.extensions.validation.TextInputControlFoccusChangedListener;

import javafx.scene.control.TextField;

import java.util.Locale;

import jfxtras.scene.control.CalendarTextField;

import org.adorsys.javafx.crud.extensions.FXMLLoaderUtils;
import org.adorsys.javafx.crud.extensions.ViewModel;
import org.adorsys.javafx.crud.extensions.validation.ToOneAggreggationFieldValidator;
import org.adorsys.javaext.format.NumberType;
import org.adorsys.javafx.crud.extensions.control.BigDecimalField;
import org.adorsys.javafx.crud.extensions.validation.BigDecimalFieldValidator;
import org.adorsys.javafx.crud.extensions.validation.BigDecimalFieldFoccusChangedListener;

import javafx.scene.control.ComboBox;
import javafx.util.StringConverter;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.ViewBuilder;
import org.adorsys.javafx.crud.extensions.ViewType;

import de.jensd.fx.fontawesome.AwesomeIcon;

import javax.inject.Singleton;

import org.adorsys.adpharma.client.jpa.delivery.Delivery;
import org.adorsys.adpharma.client.jpa.deliveryitem.DeliveryItem;

@Singleton
public class DeliveryEditView
{

	@FXML
	BorderPane rootPane;

	@FXML
	private Button saveButton;

	@FXML
	private TextField deliveryNumber;

	@FXML
	private TextField deliverySlipNumber;

	private TextField recordingDate;

	private TextField deliveryDate;

	private TextField supplier;

	private TextField vat;

	private TextField agency;

	private TextField login;

	private BigDecimalField amountBeforeTax;

	private BigDecimalField taxAmount;

	private BigDecimalField amountDiscount;

	private BigDecimalField amountAfterTax;

	private BigDecimalField processAmont;

	@FXML
	private GridPane amountPane ;
	
	@FXML
	private MenuItem deleteDeliveryMenu;
	
	@FXML
	private MenuItem editDeliveryMenu;
	


	@FXML
	private Button cancelButton;
	
	@FXML
	private Button addArticleButton ;

	private TextField articleName;

	private TextField mainPic;

	private BigDecimalField stockQuantity;

	private BigDecimalField freeQuantity;

	private BigDecimalField purchasePricePU;

	private BigDecimalField salesPricePU;

	private TextField expirationDate;

	@FXML
	TableView<DeliveryItem> dataList;

	@FXML
	private HBox deliveryItemBar;

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
	}

	public void buildDeliveryItemBar(){
		mainPic = ViewBuilderUtils.newTextField( "mainPic", false);
		mainPic.setPromptText("cip");

		articleName = ViewBuilderUtils.newTextField("articleName", false);
		articleName.setPromptText("Designation");
		articleName.setPrefWidth(350d);

		freeQuantity = ViewBuilderUtils.newBigDecimalField( "freeQuantity", NumberType.INTEGER,locale,false);
		freeQuantity.setTooltip(new Tooltip("UG"));
		freeQuantity.setPrefWidth(75d);

		stockQuantity = ViewBuilderUtils.newBigDecimalField( "stockQuantity", NumberType.INTEGER, locale,false);
		stockQuantity.setTooltip(new Tooltip("Qte"));
		stockQuantity.setPrefWidth(75d);

		salesPricePU = ViewBuilderUtils.newBigDecimalField("salesPricePU", NumberType.CURRENCY, locale,false);
		salesPricePU.setTooltip(new Tooltip("PV"));
		salesPricePU.setPrefWidth(130d);

		purchasePricePU = ViewBuilderUtils.newBigDecimalField( "purchasePricePU", NumberType.CURRENCY, locale,false);
		purchasePricePU.setTooltip(new Tooltip("PA"));
		purchasePricePU.setPrefWidth(130d);

		deliveryItemBar.getChildren().addAll(
				mainPic,articleName,stockQuantity,freeQuantity,salesPricePU,purchasePricePU);

	}

	public void buildAmountPane(){
		amountBeforeTax = ViewBuilderUtils.newBigDecimalField( "amountBeforeTax", NumberType.CURRENCY,locale,true);
		amountDiscount = ViewBuilderUtils.newBigDecimalField( "amountDiscount", NumberType.CURRENCY,locale,false);
		amountAfterTax = ViewBuilderUtils.newBigDecimalField( "amountAfterTax", NumberType.CURRENCY,locale,false);
		taxAmount = ViewBuilderUtils.newBigDecimalField( "taxAmount", NumberType.CURRENCY,locale,false);
		processAmont = ViewBuilderUtils.newBigDecimalField( "processAmont", NumberType.CURRENCY,locale,false);

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

	public TextField getRecordingDate() {
		return recordingDate;
	}

	public TextField getDeliveryDate() {
		return deliveryDate;
	}

	public TextField getSupplier() {
		return supplier;
	}

	public TextField getVat() {
		return vat;
	}

	public TextField getAgency() {
		return agency;
	}

	public TextField getLogin() {
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

}
