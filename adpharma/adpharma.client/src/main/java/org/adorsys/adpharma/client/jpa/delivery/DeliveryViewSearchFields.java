package org.adorsys.adpharma.client.jpa.delivery;

import java.util.Locale;
import java.util.ResourceBundle;

import javafx.scene.control.TextField;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adorsys.adpharma.client.jpa.documentprocessingstate.DocumentProcessingState;
import org.adorsys.adpharma.client.jpa.documentprocessingstate.DocumentProcessingStateConverter;
import org.adorsys.adpharma.client.jpa.documentprocessingstate.DocumentProcessingStateListCellFatory;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.AbstractForm;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;

public class DeliveryViewSearchFields extends AbstractForm<Delivery>
{

   private TextField deliveryNumber;

   private TextField deliverySlipNumber;

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

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();
      deliveryNumber = viewBuilder.addTextField("Delivery_deliveryNumber_description.title", "deliveryNumber", resourceBundle);
      deliverySlipNumber = viewBuilder.addTextField("Delivery_deliverySlipNumber_description.title", "deliverySlipNumber", resourceBundle);

      gridRows = viewBuilder.toRows();
   }

   public void bind(Delivery model)
   {
      deliveryNumber.textProperty().bindBidirectional(model.deliveryNumberProperty());
      deliverySlipNumber.textProperty().bindBidirectional(model.deliverySlipNumberProperty());

   }

   public TextField getDeliveryNumber()
   {
      return deliveryNumber;
   }

   public TextField getDeliverySlipNumber()
   {
      return deliverySlipNumber;
   }
}
