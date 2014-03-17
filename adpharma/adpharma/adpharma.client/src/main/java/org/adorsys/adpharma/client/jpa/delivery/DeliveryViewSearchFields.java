package org.adorsys.adpharma.client.jpa.delivery;

import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import java.util.Calendar;
import javafx.beans.property.SimpleObjectProperty;
import org.adorsys.adpharma.client.jpa.login.Login;
import org.adorsys.adpharma.client.jpa.supplier.Supplier;
import org.adorsys.adpharma.client.jpa.agency.Agency;
import java.math.BigDecimal;
import org.adorsys.adpharma.client.jpa.vat.VAT;
import org.adorsys.adpharma.client.jpa.currency.Currency;
import org.adorsys.adpharma.client.jpa.documentprocessingstate.DocumentProcessingState;
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

import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.AbstractForm;
import org.adorsys.javafx.crud.extensions.view.GridRow;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;
import org.adorsys.adpharma.client.jpa.delivery.Delivery;
import org.adorsys.adpharma.client.jpa.documentprocessingstate.DocumentProcessingStateConverter;
import org.adorsys.adpharma.client.jpa.documentprocessingstate.DocumentProcessingStateListCellFatory;

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
