package org.adorsys.adpharma.client.jpa.delivery;

import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.beans.property.SimpleStringProperty;
import java.util.Calendar;
import javafx.beans.property.SimpleObjectProperty;
import org.adorsys.adpharma.client.jpa.login.Login;
import org.adorsys.adpharma.client.jpa.supplier.Supplier;
import java.math.BigDecimal;
import org.adorsys.adpharma.client.jpa.vat.VAT;
import org.adorsys.adpharma.client.jpa.currency.Currency;
import org.adorsys.adpharma.client.jpa.documentprocessingstate.DocumentProcessingState;
import org.adorsys.adpharma.client.jpa.agency.Agency;
import org.adorsys.adpharma.client.jpa.deliveryitem.DeliveryItem;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import java.util.ArrayList;
import java.util.List;

import org.adorsys.javafx.crud.extensions.view.ComboBoxInitializer;

import org.adorsys.javafx.crud.extensions.validation.TextInputControlValidator;
import org.adorsys.javafx.crud.extensions.validation.TextInputControlFoccusChangedListener;
import javafx.scene.control.TextField;
import java.util.Locale;
import jfxtras.scene.control.CalendarTextField;
import org.adorsys.javafx.crud.extensions.ViewModel;
import org.adorsys.javafx.crud.extensions.validation.ToOneAggreggationFieldValidator;
import org.adorsys.javaext.format.NumberType;
import org.adorsys.javafx.crud.extensions.control.BigDecimalField;
import org.adorsys.javafx.crud.extensions.validation.BigDecimalFieldValidator;
import org.adorsys.javafx.crud.extensions.validation.BigDecimalFieldFoccusChangedListener;
import javafx.scene.control.ComboBox;
import javafx.beans.property.ObjectProperty;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;

import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.AbstractForm;
import org.adorsys.javafx.crud.extensions.view.GridRow;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;
import org.adorsys.adpharma.client.jpa.delivery.Delivery;
import org.adorsys.adpharma.client.jpa.documentprocessingstate.DocumentProcessingStateConverter;
import org.adorsys.adpharma.client.jpa.documentprocessingstate.DocumentProcessingStateListCellFatory;

public class DeliveryView extends AbstractForm<Delivery>
{

	private TextField deliveryNumber;

	private TextField deliverySlipNumber;

	private ComboBox<DocumentProcessingState> deliveryProcessingState;

	private BigDecimalField amountBeforeTax;

	private BigDecimalField amountAfterTax;

	private BigDecimalField amountDiscount;

	private BigDecimalField amountVat;

	private BigDecimalField netAmountToPay;

	private CalendarTextField dateOnDeliverySlip;

	private CalendarTextField orderDate;

	private CalendarTextField deliveryDate;

	private CalendarTextField recordingDate;

	@Inject
	private DeliveryDeliveryItemsForm deliveryDeliveryItemsForm;
	@Inject
	private DeliveryDeliveryItemsSelection deliveryDeliveryItemsSelection;

	@Inject
	private DeliveryCreatingUserForm deliveryCreatingUserForm;
	@Inject
	private DeliveryCreatingUserSelection deliveryCreatingUserSelection;

	@Inject
	private DeliverySupplierForm deliverySupplierForm;
	@Inject
	private DeliverySupplierSelection deliverySupplierSelection;

	@Inject
	private DeliveryVatForm deliveryVatForm;
	@Inject
	private DeliveryVatSelection deliveryVatSelection;

	@Inject
	private DeliveryCurrencyForm deliveryCurrencyForm;
	@Inject
	private DeliveryCurrencySelection deliveryCurrencySelection;

	@Inject
	private DeliveryReceivingAgencyForm deliveryReceivingAgencyForm;
	@Inject
	private DeliveryReceivingAgencySelection deliveryReceivingAgencySelection;

	@Inject
	@Bundle({ CrudKeys.class, Delivery.class })
	private ResourceBundle resourceBundle;

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
	private BigDecimalFieldValidator bigDecimalFieldValidator;
	@Inject
	private TextInputControlValidator textInputControlValidator;
	@Inject
	private ToOneAggreggationFieldValidator toOneAggreggationFieldValidator;

	@PostConstruct
	public void postConstruct()
	{
		LazyViewBuilder viewBuilder = new LazyViewBuilder();
		deliveryNumber = viewBuilder.addTextField("Delivery_deliveryNumber_description.title", "deliveryNumber", resourceBundle);
		deliverySlipNumber = viewBuilder.addTextField("Delivery_deliverySlipNumber_description.title", "deliverySlipNumber", resourceBundle);
		deliveryProcessingState = viewBuilder.addComboBox("Delivery_deliveryProcessingState_description.title", "deliveryProcessingState", resourceBundle, DocumentProcessingState.values());
		amountBeforeTax = viewBuilder.addBigDecimalField("Delivery_amountBeforeTax_description.title", "amountBeforeTax", resourceBundle, NumberType.CURRENCY, locale);
		amountVat = viewBuilder.addBigDecimalField("Delivery_amountVat_description.title", "amountVat", resourceBundle, NumberType.CURRENCY, locale);
		amountDiscount = viewBuilder.addBigDecimalField("Delivery_amountDiscount_description.title", "amountDiscount", resourceBundle, NumberType.CURRENCY, locale);
		amountAfterTax = viewBuilder.addBigDecimalField("Delivery_amountAfterTax_description.title", "amountAfterTax", resourceBundle, NumberType.CURRENCY, locale);
		netAmountToPay = viewBuilder.addBigDecimalField("Delivery_netAmountToPay_description.title", "netAmountToPay", resourceBundle, NumberType.CURRENCY, locale);
		dateOnDeliverySlip = viewBuilder.addCalendarTextField("Delivery_dateOnDeliverySlip_description.title", "dateOnDeliverySlip", resourceBundle, "dd-MM-yyyy", locale);
		orderDate = viewBuilder.addCalendarTextField("Delivery_orderDate_description.title", "orderDate", resourceBundle, "dd-MM-yyyy HH:mm", locale);
		deliveryDate = viewBuilder.addCalendarTextField("Delivery_deliveryDate_description.title", "deliveryDate", resourceBundle, "dd-MM-yyyy HH:mm", locale);
		//      recordingDate = viewBuilder.addCalendarTextField("Delivery_recordingDate_description.title", "recordingDate", resourceBundle, "dd-MM-yyyy HH:mm", locale);
		//      viewBuilder.addTitlePane("Delivery_deliveryItems_description.title", resourceBundle);
		//      viewBuilder.addSubForm("Delivery_deliveryItems_description.title", "deliveryItems", resourceBundle, deliveryDeliveryItemsForm, ViewModel.READ_WRITE);
		//      viewBuilder.addSubForm("Delivery_deliveryItems_description.title", "deliveryItems", resourceBundle, deliveryDeliveryItemsSelection, ViewModel.READ_WRITE);
		//      viewBuilder.addTitlePane("Delivery_creatingUser_description.title", resourceBundle);
		//      viewBuilder.addSubForm("Delivery_creatingUser_description.title", "creatingUser", resourceBundle, deliveryCreatingUserForm, ViewModel.READ_ONLY);
		//      viewBuilder.addSubForm("Delivery_creatingUser_description.title", "creatingUser", resourceBundle, deliveryCreatingUserSelection, ViewModel.READ_WRITE);
		//      viewBuilder.addTitlePane("Delivery_supplier_description.title", resourceBundle);
		//      viewBuilder.addSubForm("Delivery_supplier_description.title", "supplier", resourceBundle, deliverySupplierForm, ViewModel.READ_ONLY);
		viewBuilder.addSubForm("Delivery_supplier_description.title", "supplier", resourceBundle, deliverySupplierSelection, ViewModel.READ_WRITE);
		//      viewBuilder.addTitlePane("Delivery_vat_description.title", resourceBundle);
		//      viewBuilder.addSubForm("Delivery_vat_description.title", "vat", resourceBundle, deliveryVatForm, ViewModel.READ_ONLY);
		viewBuilder.addSubForm("Delivery_vat_description.title", "vat", resourceBundle, deliveryVatSelection, ViewModel.READ_WRITE);
		//      viewBuilder.addTitlePane("Delivery_currency_description.title", resourceBundle);
		//      viewBuilder.addSubForm("Delivery_currency_description.title", "currency", resourceBundle, deliveryCurrencyForm, ViewModel.READ_ONLY);
		viewBuilder.addSubForm("Delivery_currency_description.title", "currency", resourceBundle, deliveryCurrencySelection, ViewModel.READ_WRITE);
		//         viewBuilder.addSubForm("Delivery_receivingAgency_description.title", "receivingAgency", resourceBundle, deliveryReceivingAgencyForm, ViewModel.READ_ONLY);
		viewBuilder.addSubForm("Delivery_receivingAgency_description.title", "receivingAgency", resourceBundle, deliveryReceivingAgencySelection, ViewModel.READ_WRITE);

		//		ComboBoxInitializer.initialize(deliveryProcessingState, deliveryProcessingStateConverter, deliveryProcessingStateListCellFatory, deliveryProcessingStateBundle);

		gridRows = viewBuilder.toRows();
	}

	public void addValidators()
	{
		//		      deliveryNumber.focusedProperty().addListener(new TextInputControlFoccusChangedListener<Delivery>(textInputControlValidator, deliveryNumber, Delivery.class, "deliveryNumber", resourceBundle));
		deliverySlipNumber.focusedProperty().addListener(new TextInputControlFoccusChangedListener<Delivery>(textInputControlValidator, deliverySlipNumber, Delivery.class, "deliverySlipNumber", resourceBundle));
		amountBeforeTax.numberProperty().addListener(new BigDecimalFieldFoccusChangedListener<Delivery>(bigDecimalFieldValidator, amountBeforeTax, Delivery.class, "amountBeforeTax", resourceBundle));
		// no active validator
		// no active validator
		// no active validator
	}

	public Set<ConstraintViolation<Delivery>> validate(Delivery model)
	{
		Set<ConstraintViolation<Delivery>> violations = new HashSet<ConstraintViolation<Delivery>>();
		//      violations.addAll(textInputControlValidator.validate(deliveryNumber, Delivery.class, "deliveryNumber", resourceBundle));
		violations.addAll(textInputControlValidator.validate(deliverySlipNumber, Delivery.class, "deliverySlipNumber", resourceBundle));
		violations.addAll(bigDecimalFieldValidator.validate(amountBeforeTax, Delivery.class, "amountBeforeTax", resourceBundle));
		//		violations.addAll(toOneAggreggationFieldValidator.validate(deliveryCreatingUserSelection.getCreatingUser(), model.getCreatingUser(), Delivery.class, "creatingUser", resourceBundle));
		violations.addAll(toOneAggreggationFieldValidator.validate(deliverySupplierSelection.getSupplier(), model.getSupplier(), Delivery.class, "supplier", resourceBundle));
		violations.addAll(toOneAggreggationFieldValidator.validate(deliveryReceivingAgencySelection.getReceivingAgency(), model.getReceivingAgency(), Delivery.class, "receivingAgency", resourceBundle));
		return violations;
	}

	public void bind(Delivery model)
	{
		deliveryNumber.textProperty().bindBidirectional(model.deliveryNumberProperty());
		deliverySlipNumber.textProperty().bindBidirectional(model.deliverySlipNumberProperty());
		deliveryProcessingState.valueProperty().bindBidirectional(model.deliveryProcessingStateProperty());
		amountBeforeTax.numberProperty().bindBidirectional(model.amountBeforeTaxProperty());
		amountAfterTax.numberProperty().bindBidirectional(model.amountAfterTaxProperty());
		amountDiscount.numberProperty().bindBidirectional(model.amountDiscountProperty());
		amountVat.numberProperty().bindBidirectional(model.amountVatProperty());
		netAmountToPay.numberProperty().bindBidirectional(model.netAmountToPayProperty());
		dateOnDeliverySlip.calendarProperty().bindBidirectional(model.dateOnDeliverySlipProperty());
		orderDate.calendarProperty().bindBidirectional(model.orderDateProperty());
		deliveryDate.calendarProperty().bindBidirectional(model.deliveryDateProperty());
		//      recordingDate.calendarProperty().bindBidirectional(model.recordingDateProperty());
		//      deliveryDeliveryItemsForm.bind(model);
		//      deliveryDeliveryItemsSelection.bind(model);
		//      deliveryCreatingUserForm.bind(model);
		//      deliveryCreatingUserSelection.bind(model);
		//      deliverySupplierForm.bind(model);
		deliverySupplierSelection.bind(model);
		//      deliveryVatForm.bind(model);
		deliveryVatSelection.bind(model);
		//      deliveryCurrencyForm.bind(model);
		deliveryCurrencySelection.bind(model);
		//      deliveryReceivingAgencyForm.bind(model);
		deliveryReceivingAgencySelection.bind(model);
	}

	public TextField getDeliveryNumber()
	{
		return deliveryNumber;
	}

	public TextField getDeliverySlipNumber()
	{
		return deliverySlipNumber;
	}

	public ComboBox<DocumentProcessingState> getDeliveryProcessingState()
	{
		return deliveryProcessingState;
	}

	public BigDecimalField getAmountBeforeTax()
	{
		return amountBeforeTax;
	}

	public BigDecimalField getAmountAfterTax()
	{
		return amountAfterTax;
	}

	public BigDecimalField getAmountDiscount()
	{
		return amountDiscount;
	}

	public BigDecimalField getNetAmountToPay()
	{
		return netAmountToPay;
	}

	public CalendarTextField getDateOnDeliverySlip()
	{
		return dateOnDeliverySlip;
	}

	public CalendarTextField getOrderDate()
	{
		return orderDate;
	}

	public CalendarTextField getDeliveryDate()
	{
		return deliveryDate;
	}

	public CalendarTextField getRecordingDate()
	{
		return recordingDate;
	}

	public DeliveryDeliveryItemsForm getDeliveryDeliveryItemsForm()
	{
		return deliveryDeliveryItemsForm;
	}

	public DeliveryDeliveryItemsSelection getDeliveryDeliveryItemsSelection()
	{
		return deliveryDeliveryItemsSelection;
	}

	public DeliveryCreatingUserForm getDeliveryCreatingUserForm()
	{
		return deliveryCreatingUserForm;
	}

	public DeliveryCreatingUserSelection getDeliveryCreatingUserSelection()
	{
		return deliveryCreatingUserSelection;
	}

	public DeliverySupplierForm getDeliverySupplierForm()
	{
		return deliverySupplierForm;
	}

	public DeliverySupplierSelection getDeliverySupplierSelection()
	{
		return deliverySupplierSelection;
	}

	public DeliveryVatForm getDeliveryVatForm()
	{
		return deliveryVatForm;
	}

	public DeliveryVatSelection getDeliveryVatSelection()
	{
		return deliveryVatSelection;
	}

	public DeliveryCurrencyForm getDeliveryCurrencyForm()
	{
		return deliveryCurrencyForm;
	}

	public DeliveryCurrencySelection getDeliveryCurrencySelection()
	{
		return deliveryCurrencySelection;
	}

	public DeliveryReceivingAgencyForm getDeliveryReceivingAgencyForm()
	{
		return deliveryReceivingAgencyForm;
	}

	public DeliveryReceivingAgencySelection getDeliveryReceivingAgencySelection()
	{
		return deliveryReceivingAgencySelection;
	}
}
