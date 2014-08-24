package org.adorsys.adpharma.client.jpa.cashdrawer;

import java.util.Locale;
import java.util.ResourceBundle;

import javafx.beans.property.Property;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adorsys.adpharma.client.jpa.article.Article;
import org.adorsys.adpharma.client.jpa.customer.Customer;
import org.adorsys.adpharma.client.jpa.debtstatement.DebtStatement;
import org.adorsys.adpharma.client.jpa.payment.Payment;
import org.adorsys.adpharma.client.jpa.paymentitem.PaymentItem;
import org.adorsys.adpharma.client.jpa.paymentmode.PaymentMode;
import org.adorsys.adpharma.client.jpa.paymentmode.PaymentModeConverter;
import org.adorsys.adpharma.client.jpa.paymentmode.PaymentModeListCellFatory;
import org.adorsys.javaext.format.NumberType;
import org.adorsys.javafx.crud.extensions.FXMLLoaderUtils;
import org.adorsys.javafx.crud.extensions.control.BigDecimalField;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.ApplicationModal;
import org.adorsys.javafx.crud.extensions.view.ComboBoxInitializer;
import org.adorsys.javafx.crud.extensions.view.ViewBuilder;
import org.adorsys.javafx.crud.extensions.view.ViewBuilderUtils;

public class ModalCashView extends ApplicationModal{

	@FXML
	private VBox rootPane ;

	@FXML
	private TextField articleName;

	@FXML Button cashButton;

	@FXML Button returnButton;

	@FXML Button closeButton;

	@FXML
	private GridPane paymentGrid ;

	private ComboBox<PaymentMode> paymentMode;

	private BigDecimalField amount;

	private BigDecimalField receivedAmount;
	private BigDecimalField difference;

	@FXML
	TableView<DebtStatement> dataList;

	@Inject
	FXMLLoader fxmlLoader;

	@Inject
	@Bundle({ CrudKeys.class, CashDrawer.class ,Payment.class,DebtStatement.class})
	private ResourceBundle resourceBundle;

	@Inject
	private Locale locale;

	@Inject
	@Bundle(PaymentMode.class)
	private ResourceBundle paymentModeBundle;
	@Inject
	private PaymentModeConverter paymentModeConverter;

	@Inject
	private PaymentModeListCellFatory paymentModeListCellFatory;

	@Inject
	@Bundle({ Payment.class, PaymentItem.class, CrudKeys.class, Customer.class })
	private ResourceBundle paymentResourceBundle;

	@Override
	public VBox getRootPane() {
		return rootPane;
	}

	@PostConstruct
	public void onPostConstruct(){
		FXMLLoaderUtils.load(fxmlLoader, this,resourceBundle);
		ViewBuilder viewBuilder = new ViewBuilder();
		viewBuilder.addStringColumn(dataList, "statementNumber", "DebtStatement_statementNumber_description.title", resourceBundle);
		viewBuilder.addStringColumn(dataList, "insurrance", "DebtStatement_insurrance_description.title", resourceBundle,300d);
		viewBuilder.addBigDecimalColumn(dataList, "restAmount", "DebtStatement_restAmount_description.title", resourceBundle, NumberType.INTEGER, locale);
		BuildPaymentGrid();
	}


	public void BuildPaymentGrid(){
		//		paymentMode = ViewBuilderUtils.newComboBox(null, "paymentMode", resourceBundle, PaymentMode.values(), false);
		Font font = new Font("latin", 20D);
		paymentMode = ViewBuilderUtils.newComboBox("Payment_paymentMode_description.title", "paymentMode", paymentResourceBundle, PaymentMode.valuesForDebtstatement(), false);
		paymentMode.setPrefHeight(30d);
		paymentMode.setPrefWidth(230d);
		ComboBoxInitializer.initialize(paymentMode, paymentModeConverter, paymentModeListCellFatory, paymentModeBundle);
		paymentMode.setValue(PaymentMode.CASH);

		amount = ViewBuilderUtils.newBigDecimalField("amount", NumberType.INTEGER, locale, false);
		amount.setPrefHeight(30d);
		amount.setPrefWidth(200d);
		amount.setFont(font);
		amount.setEditable(false);
		amount.getStyleClass().add("green-text");

		receivedAmount = ViewBuilderUtils.newBigDecimalField("receivedAmount", NumberType.INTEGER, locale, false);
		receivedAmount.setPrefHeight(30d);
		receivedAmount.setFont(font);
		receivedAmount.setPrefWidth(200d);
		receivedAmount.getStyleClass().add("blue-text");

		difference = ViewBuilderUtils.newBigDecimalField("difference", NumberType.INTEGER, locale, false);
		difference.setPrefHeight(30d);
		difference.setPrefWidth(200d);
		difference.setEditable(false);
		difference.setFont(font);
		difference.getStyleClass().add("red-text");

		paymentGrid.addColumn(1, amount,paymentMode,receivedAmount,difference);
	}

	public void bind(Payment payment){
		paymentMode.valueProperty().bindBidirectional(payment.paymentModeProperty());
		receivedAmount.numberProperty().bindBidirectional(payment.receivedAmountProperty());
		difference.numberProperty().bindBidirectional(payment.differenceProperty());
	}
	
	public void bindDebt(DebtStatement debt){
		amount.numberProperty().bindBidirectional(debt.restAmountProperty());
	}

	public TextField getArticleName() {
		return articleName;
	}

	public Button getCashButton() {
		return cashButton;
	}

	public Button getReturnButton() {
		return returnButton;
	}

	public Button getCloseButton() {
		return closeButton;
	}

	public GridPane getPaymentGrid() {
		return paymentGrid;
	}

	public ComboBox<PaymentMode> getPaymentMode() {
		return paymentMode;
	}

	public BigDecimalField getAmount() {
		return amount;
	}

	public BigDecimalField getReceivedAmount() {
		return receivedAmount;
	}

	public BigDecimalField getDifference() {
		return difference;
	}

	public TableView<DebtStatement> getDataList() {
		return dataList;
	}

	public FXMLLoader getFxmlLoader() {
		return fxmlLoader;
	}

	public ResourceBundle getResourceBundle() {
		return resourceBundle;
	}

	public Locale getLocale() {
		return locale;
	}

	public ResourceBundle getPaymentModeBundle() {
		return paymentModeBundle;
	}

	public PaymentModeConverter getPaymentModeConverter() {
		return paymentModeConverter;
	}

	public PaymentModeListCellFatory getPaymentModeListCellFatory() {
		return paymentModeListCellFatory;
	}

	public ResourceBundle getPaymentResourceBundle() {
		return paymentResourceBundle;
	}


}
