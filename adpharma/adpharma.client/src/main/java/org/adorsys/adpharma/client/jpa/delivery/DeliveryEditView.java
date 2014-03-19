package org.adorsys.adpharma.client.jpa.delivery;

import java.util.Calendar;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
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
	
	@Inject
	private TextField deliveryNumber;
	
	@Inject
	private TextField deliverySlipNumber;
	
	@Inject
	private TextField recordingDate;
	
	@Inject
	private TextField deliveryDate;
	
	@Inject
	private TextField supplier;
	
	@Inject
	private TextField vat;
	
	@Inject
	private TextField agency;
	
	@Inject
	private TextField login;
	
	@Inject
	private TextField amountBeforeTax;
	
	@Inject
	private TextField taxAmount;
	
	@Inject
	private TextField amountDiscount;
	
	@Inject
	private TextField amountAfterTax;
	
	@Inject
	private TextField processAmont;
	

	@FXML
	private Button cancelButton;

	private TextField articleName;

	private TextField mainPic;

	private BigDecimalField stockQuantity;

	private BigDecimalField freeQuantity;

	private BigDecimalField purchasePricePU;

	private BigDecimalField salesPricePU;

	private TextField expirationDate;

	@FXML
	private HBox deliveryItemBar;

	@FXML

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
		ViewBuilder viewBuilder = new ViewBuilder();
		viewBuilder.addMainForm(view, ViewType.EDIT, false);
		//      viewBuilder.addSeparator();
		//      HBox buttonBar = viewBuilder.addButtonBar();
		//      saveButton = viewBuilder.addButton(buttonBar, "Entity_save.title", "saveButton", resourceBundle, AwesomeIcon.SAVE);
		//      cancelButton = viewBuilder.addButton(buttonBar, "Entity_cancel.title", "cancelButton", resourceBundle, AwesomeIcon.STOP);
		//      rootPane = viewBuilder.toAnchorPane();
		buildDeliveryItemBar();
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

	public void bind(Delivery model)
	{
		//		view.bind(model);
		deliveryNumber.textProperty().bindBidirectional(model.deliveryNumberProperty());
		deliverySlipNumber.textProperty().bindBidirectional(model.deliverySlipNumberProperty());
//		recordingDate.textProperty().bindBidirectional(model.recordingDateProperty(), new StringConverter<Calendar>() {
//		});
		
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

	public TextField getAmountBeforeTax() {
		return amountBeforeTax;
	}

	public TextField getTaxAmount() {
		return taxAmount;
	}

	public TextField getAmountDiscount() {
		return amountDiscount;
	}

	public TextField getAmountAfterTax() {
		return amountAfterTax;
	}

	public TextField getProcessAmont() {
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



}
