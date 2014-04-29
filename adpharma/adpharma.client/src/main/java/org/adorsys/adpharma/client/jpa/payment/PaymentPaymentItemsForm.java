package org.adorsys.adpharma.client.jpa.payment;

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

import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import org.adorsys.javaext.format.NumberType;
import java.util.Locale;
import org.adorsys.javafx.crud.extensions.control.BigDecimalField;
import org.adorsys.adpharma.client.jpa.paymentitem.PaymentItemPaidByForm;
import org.adorsys.adpharma.client.jpa.paymentitem.PaymentItemPaidBySelection;
import org.adorsys.javafx.crud.extensions.ViewModel;
import org.adorsys.adpharma.client.jpa.paymentitem.PaymentItemPaymentForm;
import org.adorsys.adpharma.client.jpa.paymentitem.PaymentItemPaidBy;

import org.adorsys.adpharma.client.jpa.paymentitem.PaymentItem;
import org.adorsys.adpharma.client.jpa.paymentmode.PaymentModeConverter;
import org.adorsys.adpharma.client.jpa.payment.Payment;
import org.adorsys.adpharma.client.jpa.customer.Customer;

public class PaymentPaymentItemsForm extends AbstractToManyAssociation<Payment, PaymentItem>
{

   private TableView<PaymentItem> dataList;
   private Pagination pagination;

   @Inject
   private PaymentModeConverter paymentModeConverter;

   @Inject
   @Bundle({ CrudKeys.class
         , PaymentItem.class
         , Payment.class
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
      viewBuilder.addEnumColumn(dataList, "paymentMode", "PaymentItem_paymentMode_description.title", resourceBundle, paymentModeConverter);
      viewBuilder.addStringColumn(dataList, "documentNumber", "PaymentItem_documentNumber_description.title", resourceBundle);
      viewBuilder.addBigDecimalColumn(dataList, "amount", "PaymentItem_amount_description.title", resourceBundle, NumberType.CURRENCY, locale);
      viewBuilder.addBigDecimalColumn(dataList, "receivedAmount", "PaymentItem_receivedAmount_description.title", resourceBundle, NumberType.CURRENCY, locale);
      viewBuilder.addStringColumn(dataList, "fullName", "Customer_fullName_description.title", resourceBundle);
      pagination = viewBuilder.addPagination();

      gridRows = viewBuilder.toRows();
   }

   public void bind(Payment model)
   {
   }

   public TableView<PaymentItem> getDataList()
   {
      return dataList;
   }

   public Pagination getPagination()
   {
      return pagination;
   }
}
