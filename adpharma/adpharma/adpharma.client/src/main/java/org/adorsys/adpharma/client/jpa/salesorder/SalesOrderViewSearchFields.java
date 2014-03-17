package org.adorsys.adpharma.client.jpa.salesorder;

import java.util.List;
import java.util.ResourceBundle;

import org.adorsys.adpharma.client.jpa.cashdrawer.CashDrawer;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import java.util.Calendar;
import org.adorsys.adpharma.client.jpa.customer.Customer;
import org.adorsys.adpharma.client.jpa.insurrance.Insurrance;
import org.adorsys.adpharma.client.jpa.vat.VAT;
import org.adorsys.adpharma.client.jpa.login.Login;
import org.adorsys.adpharma.client.jpa.agency.Agency;
import org.adorsys.adpharma.client.jpa.documentprocessingstate.DocumentProcessingState;
import javafx.beans.property.SimpleBooleanProperty;
import java.math.BigDecimal;
import org.adorsys.adpharma.client.jpa.salesordertype.SalesOrderType;
import org.adorsys.adpharma.client.jpa.salesorderitem.SalesOrderItem;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import java.util.ArrayList;
import java.util.List;

import org.adorsys.javafx.crud.extensions.view.ComboBoxInitializer;

import org.adorsys.javafx.crud.extensions.ViewModel;
import javafx.scene.control.TextField;
import java.util.Locale;
import jfxtras.scene.control.CalendarTextField;
import org.adorsys.javafx.crud.extensions.validation.ToOneAggreggationFieldValidator;
import javafx.scene.control.ComboBox;
import javafx.scene.control.CheckBox;
import javafx.util.converter.BooleanStringConverter;
import org.adorsys.javaext.format.NumberType;
import org.adorsys.javafx.crud.extensions.control.BigDecimalField;
import javafx.beans.property.ObjectProperty;
import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.AbstractForm;
import org.adorsys.javafx.crud.extensions.view.GridRow;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;
import org.adorsys.adpharma.client.jpa.salesorder.SalesOrder;
import org.adorsys.adpharma.client.jpa.documentprocessingstate.DocumentProcessingStateConverter;
import org.adorsys.adpharma.client.jpa.documentprocessingstate.DocumentProcessingStateListCellFatory;
import org.adorsys.adpharma.client.jpa.salesordertype.SalesOrderTypeConverter;
import org.adorsys.adpharma.client.jpa.salesordertype.SalesOrderTypeListCellFatory;

public class SalesOrderViewSearchFields extends AbstractForm<SalesOrder>
{

   private TextField soNumber;

   private CheckBox cashed;

   @Inject
   @Bundle({ CrudKeys.class, SalesOrder.class })
   private ResourceBundle resourceBundle;

   @Inject
   @Bundle(DocumentProcessingState.class)
   private ResourceBundle salesOrderStatusBundle;

   @Inject
   private DocumentProcessingStateConverter salesOrderStatusConverter;

   @Inject
   private DocumentProcessingStateListCellFatory salesOrderStatusListCellFatory;
   @Inject
   @Bundle(SalesOrderType.class)
   private ResourceBundle salesOrderTypeBundle;

   @Inject
   private SalesOrderTypeConverter salesOrderTypeConverter;

   @Inject
   private SalesOrderTypeListCellFatory salesOrderTypeListCellFatory;

   @Inject
   private Locale locale;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();
      soNumber = viewBuilder.addTextField("SalesOrder_soNumber_description.title", "soNumber", resourceBundle);
      cashed = viewBuilder.addCheckBox("SalesOrder_cashed_description.title", "cashed", resourceBundle);

      gridRows = viewBuilder.toRows();
   }

   public void bind(SalesOrder model)
   {
      soNumber.textProperty().bindBidirectional(model.soNumberProperty());
      cashed.textProperty().bindBidirectional(model.cashedProperty(), new BooleanStringConverter());

   }

   public TextField getSoNumber()
   {
      return soNumber;
   }

   public CheckBox getCashed()
   {
      return cashed;
   }
}
