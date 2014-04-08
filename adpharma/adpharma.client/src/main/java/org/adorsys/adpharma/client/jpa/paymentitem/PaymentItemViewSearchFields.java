package org.adorsys.adpharma.client.jpa.paymentitem;

import java.util.List;
import java.util.ResourceBundle;

import org.adorsys.adpharma.client.jpa.paymentmode.PaymentMode;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import java.math.BigDecimal;
import org.adorsys.adpharma.client.jpa.customer.Customer;
import org.adorsys.adpharma.client.jpa.payment.Payment;
import org.adorsys.javafx.crud.extensions.view.ComboBoxInitializer;

import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import org.adorsys.javaext.format.NumberType;
import java.util.Locale;
import org.adorsys.javafx.crud.extensions.control.BigDecimalField;
import org.adorsys.javafx.crud.extensions.ViewModel;
import javafx.beans.property.ObjectProperty;
import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.AbstractForm;
import org.adorsys.javafx.crud.extensions.view.GridRow;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;
import org.adorsys.adpharma.client.jpa.paymentitem.PaymentItem;
import org.adorsys.adpharma.client.jpa.paymentmode.PaymentModeConverter;
import org.adorsys.adpharma.client.jpa.paymentmode.PaymentModeListCellFatory;

public class PaymentItemViewSearchFields extends AbstractForm<PaymentItem>
{

   private TextField documentNumber;

   private TextField documentDetails;

   @Inject
   @Bundle({ CrudKeys.class, PaymentItem.class })
   private ResourceBundle resourceBundle;

   @Inject
   @Bundle(PaymentMode.class)
   private ResourceBundle paymentModeBundle;

   @Inject
   private PaymentModeConverter paymentModeConverter;

   @Inject
   private PaymentModeListCellFatory paymentModeListCellFatory;

   @Inject
   private Locale locale;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();
      documentNumber = viewBuilder.addTextField("PaymentItem_documentNumber_description.title", "documentNumber", resourceBundle);
      documentDetails = viewBuilder.addTextField("PaymentItem_documentDetails_description.title", "documentDetails", resourceBundle);

      gridRows = viewBuilder.toRows();
   }

   public void bind(PaymentItem model)
   {
      documentNumber.textProperty().bindBidirectional(model.documentNumberProperty());
      documentDetails.textProperty().bindBidirectional(model.documentDetailsProperty());

   }

   public TextField getDocumentNumber()
   {
      return documentNumber;
   }

   public TextField getDocumentDetails()
   {
      return documentDetails;
   }
}
