package org.adorsys.adpharma.client.jpa.disbursement;

import java.util.HashSet;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.validation.ConstraintViolation;

import jfxtras.scene.control.CalendarTextField;

import org.adorsys.adpharma.client.jpa.cashdrawer.CashDrawer;
import org.adorsys.adpharma.client.jpa.paymentmode.PaymentMode;
import org.adorsys.adpharma.client.jpa.paymentmode.PaymentModeConverter;
import org.adorsys.adpharma.client.jpa.paymentmode.PaymentModeListCellFatory;
import org.adorsys.javaext.format.NumberType;
import org.adorsys.javafx.crud.extensions.ViewModel;
import org.adorsys.javafx.crud.extensions.control.BigDecimalField;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.validation.ToOneAggreggationFieldValidator;
import org.adorsys.javafx.crud.extensions.view.AbstractForm;
import org.adorsys.javafx.crud.extensions.view.ComboBoxInitializer;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;
public class DisbursementView extends AbstractForm<Disbursement> {

	private TextField raison;

	private BigDecimalField amount;

	private BigDecimalField voucherAmount;

	private TextField voucherNumber;

	private CalendarTextField creatingDate;

	private ComboBox<PaymentMode> paymentMode;

	@Inject
	private DisbursementCashierForm cashOutCashierForm;
	@Inject
	private DisbursementCashierSelection cashOutCashierSelection;

	@Inject
	private DisbursementAgencyForm cashOutAgencyForm;
	@Inject
	private DisbursementAgencySelection cashOutAgencySelection;

	@Inject
	private DisbursementCashDrawerForm cashOutCashDrawerForm;
	@Inject
	private DisbursementCashDrawerSelection cashOutCashDrawerSelection;

	@Inject
	@Bundle(PaymentMode.class)
	private ResourceBundle paymentModeBundle;

	@Inject
	private PaymentModeConverter paymentModeConverter;

	@Inject
	private PaymentModeListCellFatory paymentModeListCellFatory;
	@Inject
	@Bundle({ CrudKeys.class, Disbursement.class })
	private ResourceBundle resourceBundle;

	@Inject
	private Locale locale;

	@Inject
	private ToOneAggreggationFieldValidator toOneAggreggationFieldValidator;

	@PostConstruct
	public void postConstruct()
	{
		LazyViewBuilder viewBuilder = new LazyViewBuilder();
		raison = viewBuilder.addTextField("Disbursement_raison_description.title", "raison", resourceBundle);
		amount = viewBuilder.addBigDecimalField("Disbursement_amount_description.title", "amount", resourceBundle, NumberType.CURRENCY, locale);
		paymentMode = viewBuilder.addComboBox("Disbursement_paymentMode_description.title", "paymentMode", resourceBundle, PaymentMode.valuesForDisbursement());
		paymentMode.setPrefWidth(350d);
		voucherNumber = viewBuilder.addTextField("Disbursement_voucherNumber_description.title", "raison", resourceBundle);
		voucherAmount = viewBuilder.addBigDecimalField("Disbursement_voucherAmount_description.title", "amount", resourceBundle, NumberType.CURRENCY, locale);

		//		creatingDate = viewBuilder.addCalendarTextField("Disbursement_creationDate_description.title", "creationDate", resourceBundle, "dd-MM-yyyy HH:mm", locale);
		//		viewBuilder.addTitlePane("Disbursement_cashier_description.title", resourceBundle);
		//		viewBuilder.addSubForm("Disbursement_cashier_description.title", "cashier", resourceBundle, cashOutCashierForm, ViewModel.READ_ONLY);
		//		viewBuilder.addSubForm("Disbursement_cashier_description.title", "cashier", resourceBundle, cashOutCashierSelection, ViewModel.READ_WRITE);
		//		viewBuilder.addTitlePane("Disbursement_cashDrawer_description.title", resourceBundle);
		//		viewBuilder.addSubForm("Disbursement_cashDrawer_description.title", "casDrawer", resourceBundle, cashOutCashDrawerForm, ViewModel.READ_ONLY);
		//		viewBuilder.addSubForm("Disbursement_cashDrawer_description.title", "casDrawer", resourceBundle, cashOutCashDrawerSelection, ViewModel.READ_WRITE);
		//		viewBuilder.addTitlePane("Disbursement_agency_description.title", resourceBundle);
		//		viewBuilder.addSubForm("Disbursement_agency_description.title", "agency", resourceBundle, cashOutAgencyForm, ViewModel.READ_ONLY);
		//		viewBuilder.addSubForm("Disbursement_agency_description.title", "agency", resourceBundle, cashOutAgencySelection, ViewModel.READ_WRITE);
		ComboBoxInitializer.initialize(paymentMode, paymentModeConverter, paymentModeListCellFatory, paymentModeBundle);
		gridRows = viewBuilder.toRows();
	}

	public void addValidators()
	{
		// no active validator
		// no active validator
	}

	public Set<ConstraintViolation<Disbursement>> validate(Disbursement model)
	{
		Set<ConstraintViolation<Disbursement>> violations = new HashSet<ConstraintViolation<Disbursement>>();
		violations.addAll(toOneAggreggationFieldValidator.validate(cashOutCashierSelection.getCashier(), model.getCashier(), Disbursement.class, "cashier", resourceBundle));
		violations.addAll(toOneAggreggationFieldValidator.validate(cashOutAgencySelection.getAgency(), model.getAgency(), Disbursement.class, "agency", resourceBundle));
		return violations;
	}

	public void bind(Disbursement model)
	{
		raison.textProperty().bindBidirectional(model.raisonProperty());
		amount.numberProperty().bindBidirectional(model.amountProperty());
		voucherNumber.textProperty().bindBidirectional(model.voucherNumberProperty());
		voucherAmount.numberProperty().bindBidirectional(model.voucherAmountProperty());
		paymentMode.valueProperty().bindBidirectional(model.paymentModeProperty());

	}

	public TextField getRaison() {
		return raison;
	}

	public BigDecimalField getAmount() {
		return amount;
	}

	public CalendarTextField getCreatingDate() {
		return creatingDate;
	}

	public DisbursementCashierForm getCashOutCashierForm() {
		return cashOutCashierForm;
	}

	public DisbursementCashierSelection getCashOutCashierSelection() {
		return cashOutCashierSelection;
	}

	public DisbursementAgencyForm getCashOutAgencyForm() {
		return cashOutAgencyForm;
	}

	public DisbursementAgencySelection getCashOutAgencySelection() {
		return cashOutAgencySelection;
	}

	public DisbursementCashDrawerForm getCashOutCashDrawerForm() {
		return cashOutCashDrawerForm;
	}

	public DisbursementCashDrawerSelection getCashOutCashDrawerSelection() {
		return cashOutCashDrawerSelection;
	}

	public ResourceBundle getResourceBundle() {
		return resourceBundle;
	}

	public Locale getLocale() {
		return locale;
	}

	public ToOneAggreggationFieldValidator getToOneAggreggationFieldValidator() {
		return toOneAggreggationFieldValidator;
	}

	public BigDecimalField getVoucherAmount() {
		return voucherAmount;
	}

	public TextField getVoucherNumber() {
		return voucherNumber;
	}

	public ComboBox<PaymentMode> getPaymentMode() {
		return paymentMode;
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


}
