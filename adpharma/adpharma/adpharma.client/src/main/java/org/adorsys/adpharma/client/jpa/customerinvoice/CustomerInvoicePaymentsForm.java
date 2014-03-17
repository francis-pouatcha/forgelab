package org.adorsys.adpharma.client.jpa.customerinvoice;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.beans.property.ObjectProperty;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adorsys.javafx.crud.extensions.view.AbstractToManyAssociation;
import org.adorsys.javaext.format.NumberType;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.AbstractForm;
import org.adorsys.javafx.crud.extensions.view.GridRow;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;
import org.adorsys.javafx.crud.extensions.view.ViewBuilder;

import org.adorsys.javafx.crud.extensions.view.ComboBoxInitializer;

import javafx.scene.control.TextField;
import java.util.Locale;
import jfxtras.scene.control.CalendarTextField;
import org.adorsys.javaext.format.NumberType;
import org.adorsys.javafx.crud.extensions.control.BigDecimalField;
import org.adorsys.adpharma.client.jpa.payment.PaymentAgencyForm;
import org.adorsys.adpharma.client.jpa.payment.PaymentAgencySelection;
import org.adorsys.javafx.crud.extensions.ViewModel;
import org.adorsys.javafx.crud.extensions.validation.ToOneAggreggationFieldValidator;
import org.adorsys.adpharma.client.jpa.payment.PaymentCashierForm;
import org.adorsys.adpharma.client.jpa.payment.PaymentCashierSelection;
import org.adorsys.adpharma.client.jpa.payment.PaymentCashDrawerForm;
import org.adorsys.adpharma.client.jpa.payment.PaymentCashDrawerSelection;
import org.adorsys.adpharma.client.jpa.payment.PaymentInvoicesForm;
import org.adorsys.adpharma.client.jpa.payment.PaymentInvoicesSelection;
import javafx.scene.control.ComboBox;
import javafx.scene.control.CheckBox;
import javafx.util.converter.BooleanStringConverter;
import org.adorsys.adpharma.client.jpa.payment.PaymentPaidByForm;
import org.adorsys.adpharma.client.jpa.payment.PaymentPaidBySelection;
import org.adorsys.adpharma.client.jpa.payment.PaymentAgency;
import org.adorsys.adpharma.client.jpa.payment.PaymentCashier;
import org.adorsys.adpharma.client.jpa.payment.PaymentCashDrawer;
import org.adorsys.adpharma.client.jpa.payment.PaymentPaidBy;

import org.adorsys.adpharma.client.jpa.payment.Payment;
import org.adorsys.adpharma.client.jpa.paymentmode.PaymentModeConverter;
import org.adorsys.adpharma.client.jpa.customerinvoice.CustomerInvoice;
import org.adorsys.adpharma.client.jpa.agency.Agency;
import org.adorsys.adpharma.client.jpa.login.Login;
import org.adorsys.adpharma.client.jpa.cashdrawer.CashDrawer;
import org.adorsys.adpharma.client.jpa.customer.Customer;

public class CustomerInvoicePaymentsForm extends AbstractToManyAssociation<CustomerInvoice, Payment>
{

   private TableView<Payment> dataList;
   private Pagination pagination;

   @Inject
   private PaymentModeConverter paymentModeConverter;

   @Inject
   @Bundle({ CrudKeys.class
         , Payment.class
         , CustomerInvoice.class
         , Agency.class
         , Login.class
         , CashDrawer.class
         , Customer.class
   })
   private ResourceBundle resourceBundle;

   @Inject
   private Locale locale;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();

      dataList = viewBuilder.addTable("dataList");
      viewBuilder.addStringColumn(dataList, "paymentNumber", "Payment_paymentNumber_description.title", resourceBundle);
      viewBuilder.addDateColumn(dataList, "paymentDate", "Payment_paymentDate_description.title", resourceBundle, "dd-MM-yyyy HH:mm", locale);
      viewBuilder.addDateColumn(dataList, "recordDate", "Payment_recordDate_description.title", resourceBundle, "dd-MM-yyyy HH:mm", locale);
      viewBuilder.addBigDecimalColumn(dataList, "amount", "Payment_amount_description.title", resourceBundle, NumberType.CURRENCY, locale);
      viewBuilder.addBigDecimalColumn(dataList, "receivedAmount", "Payment_receivedAmount_description.title", resourceBundle, NumberType.CURRENCY, locale);
      viewBuilder.addBigDecimalColumn(dataList, "difference", "Payment_difference_description.title", resourceBundle, NumberType.CURRENCY, locale);
      viewBuilder.addStringColumn(dataList, "name", "Agency_name_description.title", resourceBundle);
      viewBuilder.addStringColumn(dataList, "fullName", "Login_fullName_description.title", resourceBundle);
      viewBuilder.addStringColumn(dataList, "cashDrawerNumber", "CashDrawer_cashDrawerNumber_description.title", resourceBundle);
      viewBuilder.addEnumColumn(dataList, "paymentMode", "Payment_paymentMode_description.title", resourceBundle, paymentModeConverter);
      // Field not displayed in table
      viewBuilder.addStringColumn(dataList, "fullName", "Customer_fullName_description.title", resourceBundle);
      pagination = viewBuilder.addPagination();

      gridRows = viewBuilder.toRows();
   }

   public void bind(CustomerInvoice model)
   {
   }

   public TableView<Payment> getDataList()
   {
      return dataList;
   }

   public Pagination getPagination()
   {
      return pagination;
   }
}
