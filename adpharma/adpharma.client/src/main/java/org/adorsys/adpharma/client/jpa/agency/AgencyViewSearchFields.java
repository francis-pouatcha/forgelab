package org.adorsys.adpharma.client.jpa.agency;

import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleBooleanProperty;
import org.adorsys.adpharma.client.jpa.company.Company;
import javafx.beans.property.SimpleObjectProperty;
import java.util.Calendar;

import javafx.scene.control.TextField;
import org.adorsys.javafx.crud.extensions.validation.TextInputControlValidator;
import org.adorsys.javafx.crud.extensions.validation.TextInputControlFoccusChangedListener;
import javafx.scene.control.CheckBox;
import javafx.util.converter.BooleanStringConverter;
import org.adorsys.javafx.crud.extensions.ViewModel;
import javafx.scene.control.TextArea;
import java.util.Locale;
import jfxtras.scene.control.CalendarTextField;

import javafx.beans.property.ObjectProperty;
import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.AbstractForm;
import org.adorsys.javafx.crud.extensions.view.GridRow;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;
import org.adorsys.adpharma.client.jpa.agency.Agency;

public class AgencyViewSearchFields extends AbstractForm<Agency>
{

   private TextField agencyNumber;

   private TextField name;

//   private CheckBox active;
//
//   private TextField street;
//
//   private TextField zipCode;
//
//   private TextField city;
//
//   private TextField country;
//
//   private TextField phone;
//
//   private TextField fax;
//
//   private TextArea ticketMessage;
//
//   private TextArea invoiceMessage;

   @Inject
   @Bundle({ CrudKeys.class, Agency.class })
   private ResourceBundle resourceBundle;

   @Inject
   private Locale locale;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();
      agencyNumber = viewBuilder.addTextField("Agency_agencyNumber_description.title", "agencyNumber", resourceBundle);
      name = viewBuilder.addTextField("Agency_name_description.title", "name", resourceBundle);
//      active = viewBuilder.addCheckBox("Agency_active_description.title", "active", resourceBundle);
//      street = viewBuilder.addTextField("Agency_street_description.title", "street", resourceBundle);
//      zipCode = viewBuilder.addTextField("Agency_zipCode_description.title", "zipCode", resourceBundle);
//      city = viewBuilder.addTextField("Agency_city_description.title", "city", resourceBundle);
//      country = viewBuilder.addTextField("Agency_country_description.title", "country", resourceBundle);
//      phone = viewBuilder.addTextField("Agency_phone_description.title", "phone", resourceBundle);
//      fax = viewBuilder.addTextField("Agency_fax_description.title", "fax", resourceBundle);
//      ticketMessage = viewBuilder.addTextArea("Agency_ticketMessage_description.title", "ticketMessage", resourceBundle);
//      invoiceMessage = viewBuilder.addTextArea("Agency_invoiceMessage_description.title", "invoiceMessage", resourceBundle);

      gridRows = viewBuilder.toRows();
   }

   public void bind(Agency model)
   {
      agencyNumber.textProperty().bindBidirectional(model.agencyNumberProperty());
      name.textProperty().bindBidirectional(model.nameProperty());
//      active.textProperty().bindBidirectional(model.activeProperty(), new BooleanStringConverter());
//      street.textProperty().bindBidirectional(model.streetProperty());
//      zipCode.textProperty().bindBidirectional(model.zipCodeProperty());
//      city.textProperty().bindBidirectional(model.cityProperty());
//      country.textProperty().bindBidirectional(model.countryProperty());
//      phone.textProperty().bindBidirectional(model.phoneProperty());
//      fax.textProperty().bindBidirectional(model.faxProperty());
//      ticketMessage.textProperty().bindBidirectional(model.ticketMessageProperty());
//      invoiceMessage.textProperty().bindBidirectional(model.invoiceMessageProperty());

   }

   public TextField getAgencyNumber()
   {
      return agencyNumber;
   }

   public TextField getName()
   {
      return name;
   }

//   public CheckBox getActive()
//   {
//      return active;
//   }
//
//   public TextField getStreet()
//   {
//      return street;
//   }
//
//   public TextField getZipCode()
//   {
//      return zipCode;
//   }
//
//   public TextField getCity()
//   {
//      return city;
//   }
//
//   public TextField getCountry()
//   {
//      return country;
//   }
//
//   public TextField getPhone()
//   {
//      return phone;
//   }
//
//   public TextField getFax()
//   {
//      return fax;
//   }
//
//   public TextArea getTicketMessage()
//   {
//      return ticketMessage;
//   }
//
//   public TextArea getInvoiceMessage()
//   {
//      return invoiceMessage;
//   }
}
