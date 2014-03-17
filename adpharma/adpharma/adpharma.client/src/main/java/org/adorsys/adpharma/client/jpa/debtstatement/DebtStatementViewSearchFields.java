package org.adorsys.adpharma.client.jpa.debtstatement;

import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import org.adorsys.adpharma.client.jpa.customer.Customer;
import javafx.beans.property.SimpleObjectProperty;
import org.adorsys.adpharma.client.jpa.agency.Agency;
import java.util.Calendar;
import java.math.BigDecimal;
import javafx.beans.property.SimpleBooleanProperty;
import org.adorsys.adpharma.client.jpa.customerinvoice.CustomerInvoice;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.TextField;
import org.adorsys.javafx.crud.extensions.ViewModel;
import org.adorsys.javafx.crud.extensions.validation.ToOneAggreggationFieldValidator;
import java.util.Locale;
import jfxtras.scene.control.CalendarTextField;
import org.adorsys.javaext.format.NumberType;
import org.adorsys.javafx.crud.extensions.control.BigDecimalField;
import javafx.scene.control.CheckBox;
import javafx.util.converter.BooleanStringConverter;
import javafx.beans.property.ObjectProperty;
import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.AbstractForm;
import org.adorsys.javafx.crud.extensions.view.GridRow;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;
import org.adorsys.adpharma.client.jpa.debtstatement.DebtStatement;

public class DebtStatementViewSearchFields extends AbstractForm<DebtStatement>
{

   private TextField statementNumber;

   private CheckBox settled;

   private CheckBox canceled;

   private CheckBox useVoucher;

   @Inject
   @Bundle({ CrudKeys.class, DebtStatement.class })
   private ResourceBundle resourceBundle;

   @Inject
   private Locale locale;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();
      statementNumber = viewBuilder.addTextField("DebtStatement_statementNumber_description.title", "statementNumber", resourceBundle);
      settled = viewBuilder.addCheckBox("DebtStatement_settled_description.title", "settled", resourceBundle);
      canceled = viewBuilder.addCheckBox("DebtStatement_canceled_description.title", "canceled", resourceBundle);
      useVoucher = viewBuilder.addCheckBox("DebtStatement_useVoucher_description.title", "useVoucher", resourceBundle);

      gridRows = viewBuilder.toRows();
   }

   public void bind(DebtStatement model)
   {
      statementNumber.textProperty().bindBidirectional(model.statementNumberProperty());
      settled.textProperty().bindBidirectional(model.settledProperty(), new BooleanStringConverter());
      canceled.textProperty().bindBidirectional(model.canceledProperty(), new BooleanStringConverter());
      useVoucher.textProperty().bindBidirectional(model.useVoucherProperty(), new BooleanStringConverter());

   }

   public TextField getStatementNumber()
   {
      return statementNumber;
   }

   public CheckBox getSettled()
   {
      return settled;
   }

   public CheckBox getCanceled()
   {
      return canceled;
   }

   public CheckBox getUseVoucher()
   {
      return useVoucher;
   }
}
