package org.adorsys.adpharma.client.jpa.paymentitem;

import java.util.Locale;
import java.util.ResourceBundle;

import javafx.scene.control.TextField;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adorsys.adpharma.client.jpa.paymentmode.PaymentMode;
import org.adorsys.adpharma.client.jpa.paymentmode.PaymentModeConverter;
import org.adorsys.adpharma.client.jpa.paymentmode.PaymentModeListCellFatory;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.AbstractForm;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;

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
