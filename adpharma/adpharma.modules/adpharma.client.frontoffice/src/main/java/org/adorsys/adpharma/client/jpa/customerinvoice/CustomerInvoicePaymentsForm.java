package org.adorsys.adpharma.client.jpa.customerinvoice;

import java.util.Locale;
import java.util.ResourceBundle;

import javafx.scene.control.Pagination;
import javafx.scene.control.TableView;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adorsys.adpharma.client.jpa.agency.Agency;
import org.adorsys.adpharma.client.jpa.cashdrawer.CashDrawer;
import org.adorsys.adpharma.client.jpa.customer.Customer;
import org.adorsys.adpharma.client.jpa.login.Login;
import org.adorsys.adpharma.client.jpa.payment.Payment;
import org.adorsys.adpharma.client.jpa.paymentmode.PaymentModeConverter;
import org.adorsys.javaext.format.NumberType;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.AbstractToManyAssociation;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;

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
