package org.adorsys.adpharma.client.jpa.debtstatement;

import java.util.Locale;
import java.util.ResourceBundle;

import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import jfxtras.scene.control.CalendarTextField;

import org.adorsys.adpharma.client.jpa.articlelot.ArticleLot;
import org.adorsys.adpharma.client.jpa.articlelot.ArticleLotDetailsManager;
import org.adorsys.adpharma.client.jpa.customer.Customer;
import org.adorsys.adpharma.client.jpa.documentprocessingstate.DocumentProcessingState;
import org.adorsys.adpharma.client.jpa.login.Login;
import org.adorsys.adpharma.client.jpa.payment.Payment;
import org.adorsys.adpharma.client.jpa.paymentmode.PaymentMode;
import org.adorsys.adpharma.client.jpa.paymentmode.PaymentModeConverter;
import org.adorsys.adpharma.client.jpa.paymentmode.PaymentModeListCellFatory;
import org.adorsys.adpharma.client.jpa.salesorder.SalesOrder;
import org.adorsys.javaext.format.NumberType;
import org.adorsys.javafx.crud.extensions.ViewModel;
import org.adorsys.javafx.crud.extensions.ViewType;
import org.adorsys.javafx.crud.extensions.control.BigDecimalField;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.validation.TextInputControlValidator;
import org.adorsys.javafx.crud.extensions.validation.ToOneAggreggationFieldValidator;
import org.adorsys.javafx.crud.extensions.view.ApplicationModal;
import org.adorsys.javafx.crud.extensions.view.ComboBoxInitializer;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;
import org.adorsys.javafx.crud.extensions.view.ViewBuilder;
import org.adorsys.javafx.crud.extensions.view.ViewBuilderUtils;

import de.jensd.fx.fontawesome.AwesomeIcon;

public class ModalDebtStatementPayementCreateView extends ApplicationModal{
	private AnchorPane rootPane;

	private TextField statementNumber;

	private TextField insurrer ;

	private BigDecimalField initialAmount;

	private BigDecimalField advancePayment;

	private BigDecimalField restAmount;

	private BigDecimalField payAmount;

	private ComboBox<PaymentMode> paymentMode;

	@Inject
	@Bundle(PaymentMode.class)
	private ResourceBundle paymentModeBundle;

	@Inject
	private PaymentModeConverter paymentModeConverter;

	@Inject
	private PaymentModeListCellFatory paymentModeListCellFatory;

	private Button saveButton ;
	private Button cancelButton ;

	@Inject
	@Bundle({ CrudKeys.class, DebtStatement.class,Payment.class })
	private ResourceBundle resourceBundle;

	@Inject
	private Locale locale;

	@PostConstruct
	public void postConstruct(){
		LazyViewBuilder lvb = new LazyViewBuilder();
		LazyViewBuilder lvb2 = new LazyViewBuilder();
		statementNumber = lvb.addTextField("DebtStatement_statementNumber_description.title", "statementNumber", resourceBundle,ViewModel.READ_ONLY);
		insurrer = lvb.addTextField("DebtStatement_insurrance_description.title", "insurrer", resourceBundle,ViewModel.READ_ONLY);
		initialAmount = lvb.addBigDecimalField("DebtStatement_initialAmount_description.title", "initialAmount", resourceBundle, NumberType.CURRENCY, locale,ViewModel.READ_ONLY);
		advancePayment = lvb.addBigDecimalField("DebtStatement_advancePayment_description.title", "advancePayment", resourceBundle, NumberType.CURRENCY, locale,ViewModel.READ_ONLY);
		restAmount = lvb.addBigDecimalField("DebtStatement_restAmount_description.title", "restAmount", resourceBundle, NumberType.CURRENCY, locale,ViewModel.READ_ONLY);


		Font font = new Font("latin", 20D);
		paymentMode = lvb2.addComboBox("Payment_paymentMode_description.title", "paymentMode", resourceBundle, PaymentMode.valuesForDebtstatement(), false);
		payAmount = lvb2.addBigDecimalField("DebtStatement_payAmount_description.title", "payAmount", resourceBundle, NumberType.INTEGER, locale);
		//		payAmount.setFont(font);
		payAmount.getStyleClass().add("blue-text");

		ComboBoxInitializer.initialize(paymentMode, paymentModeConverter, paymentModeListCellFatory, paymentModeBundle);
		ViewBuilder viewBuilder = new ViewBuilder();
		viewBuilder.addRows(lvb.toRows(),ViewType.CREATE, false);
		viewBuilder.addSeparator();
		viewBuilder.addRows(lvb2.toRows(),ViewType.CREATE, false);
		viewBuilder.addSeparator();
		HBox buttonBar = viewBuilder.addButtonBar();
		saveButton = viewBuilder.addButton(buttonBar, "Entity_save.title", "saveButton", resourceBundle, AwesomeIcon.SAVE);
		cancelButton = viewBuilder.addButton(buttonBar, "Entity_cancel.title", "resetButton", resourceBundle, AwesomeIcon.REFRESH);
		rootPane = viewBuilder.toAnchorPane();
		rootPane.setPrefWidth(500d);

	}


	public void bind(DebtStatement model)
	{
		statementNumber.textProperty().bindBidirectional(model.statementNumberProperty());
		insurrer.textProperty().bindBidirectional(model.getInsurrance().fullNameProperty());
		initialAmount.numberProperty().bindBidirectional(model.initialAmountProperty());
		advancePayment.numberProperty().bindBidirectional(model.advancePaymentProperty());
		restAmount.numberProperty().bindBidirectional(model.restAmountProperty());
		payAmount.numberProperty().bindBidirectional(model.payAmountProperty());
		paymentMode.valueProperty().bindBidirectional(model.paymentModeProperty());
	}

	public void addValidators()
	{
		//		detailsQty.focusedProperty().addListener(new TextInputControlFoccusChangedListener<ArticleLotDetailsManager>(textInputControlValidator, detailsQty, ArticleLotDetailsManager.class, "detailsQty", resourceBundle));
		// no active validator
		// no active validator
	}


	public AnchorPane getRootPane() {
		return rootPane;
	}


	public Button getSaveButton() {
		return saveButton;
	}


	public Button getCancelButton() {
		return cancelButton;
	}


	public ResourceBundle getResourceBundle() {
		return resourceBundle;
	}


	public ComboBox<PaymentMode> getPaymentMode() {
		return paymentMode;
	}


	public void setPaymentMode(ComboBox<PaymentMode> paymentMode) {
		this.paymentMode = paymentMode;
	}


	public Locale getLocale() {
		return locale;
	}


	public TextField getStatementNumber() {
		return statementNumber;
	}


	public TextField getInsurrer() {
		return insurrer;
	}


	public BigDecimalField getInitialAmount() {
		return initialAmount;
	}


	public BigDecimalField getAdvancePayment() {
		return advancePayment;
	}


	public BigDecimalField getRestAmount() {
		return restAmount;
	}


	public BigDecimalField getPayAmount() {
		return payAmount;
	}


}
