package org.adorsys.adpharma.client.jpa.customervoucher;

import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import org.adorsys.adpharma.client.jpa.customerinvoice.CustomerInvoice;
import javafx.beans.property.SimpleObjectProperty;
import java.math.BigDecimal;
import org.adorsys.adpharma.client.jpa.customer.Customer;
import org.adorsys.adpharma.client.jpa.agency.Agency;
import javafx.beans.property.SimpleBooleanProperty;
import org.adorsys.adpharma.client.jpa.login.Login;
import java.util.Calendar;

import javafx.scene.control.TextField;
import org.adorsys.javafx.crud.extensions.ViewModel;
import org.adorsys.javaext.format.NumberType;
import java.util.Locale;
import org.adorsys.javafx.crud.extensions.control.BigDecimalField;
import org.adorsys.javafx.crud.extensions.validation.BigDecimalFieldValidator;
import org.adorsys.javafx.crud.extensions.validation.BigDecimalFieldFoccusChangedListener;
import javafx.scene.control.CheckBox;
import javafx.util.converter.BooleanStringConverter;
import jfxtras.scene.control.CalendarTextField;
import javafx.beans.property.ObjectProperty;
import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.AbstractForm;
import org.adorsys.javafx.crud.extensions.view.GridRow;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;
import org.adorsys.adpharma.client.jpa.customervoucher.CustomerVoucher;

public class CustomerVoucherViewSearchFields extends AbstractForm<CustomerVoucher>
{

   private TextField voucherNumber;

   private CheckBox canceled;

   private CheckBox settled;

   private CheckBox voucherPrinted;

   @Inject
   @Bundle({ CrudKeys.class, CustomerVoucher.class })
   private ResourceBundle resourceBundle;

   @Inject
   private Locale locale;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();
      voucherNumber = viewBuilder.addTextField("CustomerVoucher_voucherNumber_description.title", "voucherNumber", resourceBundle);
      canceled = viewBuilder.addCheckBox("CustomerVoucher_canceled_description.title", "canceled", resourceBundle);
      settled = viewBuilder.addCheckBox("CustomerVoucher_settled_description.title", "settled", resourceBundle);
      voucherPrinted = viewBuilder.addCheckBox("CustomerVoucher_voucherPrinted_description.title", "voucherPrinted", resourceBundle);

      gridRows = viewBuilder.toRows();
   }

   public void bind(CustomerVoucher model)
   {
      voucherNumber.textProperty().bindBidirectional(model.voucherNumberProperty());
      canceled.textProperty().bindBidirectional(model.canceledProperty(), new BooleanStringConverter());
      settled.textProperty().bindBidirectional(model.settledProperty(), new BooleanStringConverter());
      voucherPrinted.textProperty().bindBidirectional(model.voucherPrintedProperty(), new BooleanStringConverter());

   }

   public TextField getVoucherNumber()
   {
      return voucherNumber;
   }

   public CheckBox getCanceled()
   {
      return canceled;
   }

   public CheckBox getSettled()
   {
      return settled;
   }

   public CheckBox getVoucherPrinted()
   {
      return voucherPrinted;
   }
}
