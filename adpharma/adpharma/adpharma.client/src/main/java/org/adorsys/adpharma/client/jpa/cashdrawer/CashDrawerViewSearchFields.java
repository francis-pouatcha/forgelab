package org.adorsys.adpharma.client.jpa.cashdrawer;

import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import org.adorsys.adpharma.client.jpa.login.Login;
import javafx.beans.property.SimpleObjectProperty;
import org.adorsys.adpharma.client.jpa.agency.Agency;
import java.util.Calendar;
import java.math.BigDecimal;
import javafx.beans.property.SimpleBooleanProperty;

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
import org.adorsys.adpharma.client.jpa.cashdrawer.CashDrawer;

public class CashDrawerViewSearchFields extends AbstractForm<CashDrawer>
{

   private TextField cashDrawerNumber;

   private CheckBox opened;

   @Inject
   @Bundle({ CrudKeys.class, CashDrawer.class })
   private ResourceBundle resourceBundle;

   @Inject
   private Locale locale;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();
      cashDrawerNumber = viewBuilder.addTextField("CashDrawer_cashDrawerNumber_description.title", "cashDrawerNumber", resourceBundle);
      opened = viewBuilder.addCheckBox("CashDrawer_opened_description.title", "opened", resourceBundle);

      gridRows = viewBuilder.toRows();
   }

   public void bind(CashDrawer model)
   {
      cashDrawerNumber.textProperty().bindBidirectional(model.cashDrawerNumberProperty());
      opened.textProperty().bindBidirectional(model.openedProperty(), new BooleanStringConverter());

   }

   public TextField getCashDrawerNumber()
   {
      return cashDrawerNumber;
   }

   public CheckBox getOpened()
   {
      return opened;
   }
}
