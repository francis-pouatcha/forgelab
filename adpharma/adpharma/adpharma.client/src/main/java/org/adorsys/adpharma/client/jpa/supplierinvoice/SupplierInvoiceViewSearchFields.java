package org.adorsys.adpharma.client.jpa.supplierinvoice;

import java.util.List;
import java.util.ResourceBundle;

import org.adorsys.adpharma.client.jpa.invoicetype.InvoiceType;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import java.util.Calendar;
import org.adorsys.adpharma.client.jpa.supplier.Supplier;
import org.adorsys.adpharma.client.jpa.login.Login;
import org.adorsys.adpharma.client.jpa.agency.Agency;
import org.adorsys.adpharma.client.jpa.delivery.Delivery;
import javafx.beans.property.SimpleBooleanProperty;
import java.math.BigDecimal;
import org.adorsys.adpharma.client.jpa.supplierinvoiceitem.SupplierInvoiceItem;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import java.util.ArrayList;
import java.util.List;

import org.adorsys.javafx.crud.extensions.view.ComboBoxInitializer;

import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import java.util.Locale;
import jfxtras.scene.control.CalendarTextField;
import org.adorsys.javafx.crud.extensions.ViewModel;
import org.adorsys.javafx.crud.extensions.validation.ToOneAggreggationFieldValidator;
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
import org.adorsys.adpharma.client.jpa.supplierinvoice.SupplierInvoice;
import org.adorsys.adpharma.client.jpa.invoicetype.InvoiceTypeConverter;
import org.adorsys.adpharma.client.jpa.invoicetype.InvoiceTypeListCellFatory;

public class SupplierInvoiceViewSearchFields extends AbstractForm<SupplierInvoice>
{

   private TextField invoiceNumber;

   private CheckBox settled;

   @Inject
   @Bundle({ CrudKeys.class, SupplierInvoice.class })
   private ResourceBundle resourceBundle;

   @Inject
   @Bundle(InvoiceType.class)
   private ResourceBundle invoiceTypeBundle;

   @Inject
   private InvoiceTypeConverter invoiceTypeConverter;

   @Inject
   private InvoiceTypeListCellFatory invoiceTypeListCellFatory;

   @Inject
   private Locale locale;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();
      invoiceNumber = viewBuilder.addTextField("SupplierInvoice_invoiceNumber_description.title", "invoiceNumber", resourceBundle);
      settled = viewBuilder.addCheckBox("SupplierInvoice_settled_description.title", "settled", resourceBundle);

      gridRows = viewBuilder.toRows();
   }

   public void bind(SupplierInvoice model)
   {
      invoiceNumber.textProperty().bindBidirectional(model.invoiceNumberProperty());
      settled.textProperty().bindBidirectional(model.settledProperty(), new BooleanStringConverter());

   }

   public TextField getInvoiceNumber()
   {
      return invoiceNumber;
   }

   public CheckBox getSettled()
   {
      return settled;
   }
}
