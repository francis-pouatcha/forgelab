package org.adorsys.adpharma.client.jpa.paymentitem;

import java.util.Locale;
import java.util.ResourceBundle;

import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.util.converter.BooleanStringConverter;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import jfxtras.scene.control.CalendarTextField;

import org.adorsys.adpharma.client.jpa.customer.Customer;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.AbstractToOneAssociation;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;

public class PaymentItemPaidByForm extends AbstractToOneAssociation<PaymentItem, Customer>
{

   private TextField fullName;

   private TextField landLinePhone;

   private TextField mobile;

   private TextField fax;

   private TextField email;

   private CheckBox creditAuthorized;

   private CheckBox discountAuthorized;

   private CalendarTextField birthDate;

   @Inject
   @Bundle({ CrudKeys.class, Customer.class })
   private ResourceBundle resourceBundle;

   @Inject
   private Locale locale;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();
      fullName = viewBuilder.addTextField("Customer_fullName_description.title", "fullName", resourceBundle);
      landLinePhone = viewBuilder.addTextField("Customer_landLinePhone_description.title", "landLinePhone", resourceBundle);
      mobile = viewBuilder.addTextField("Customer_mobile_description.title", "mobile", resourceBundle);
      fax = viewBuilder.addTextField("Customer_fax_description.title", "fax", resourceBundle);
      email = viewBuilder.addTextField("Customer_email_description.title", "email", resourceBundle);
      creditAuthorized = viewBuilder.addCheckBox("Customer_creditAuthorized_description.title", "creditAuthorized", resourceBundle);
      discountAuthorized = viewBuilder.addCheckBox("Customer_discountAuthorized_description.title", "discountAuthorized", resourceBundle);
      birthDate = viewBuilder.addCalendarTextField("Customer_birthDate_description.title", "birthDate", resourceBundle, "dd-MM-yyyy", locale);

      gridRows = viewBuilder.toRows();
   }

   public void bind(PaymentItem model)
   {
      fullName.textProperty().bindBidirectional(model.getPaidBy().fullNameProperty());
      landLinePhone.textProperty().bindBidirectional(model.getPaidBy().landLinePhoneProperty());
      mobile.textProperty().bindBidirectional(model.getPaidBy().mobileProperty());
      fax.textProperty().bindBidirectional(model.getPaidBy().faxProperty());
      email.textProperty().bindBidirectional(model.getPaidBy().emailProperty());
      creditAuthorized.textProperty().bindBidirectional(model.getPaidBy().creditAuthorizedProperty(), new BooleanStringConverter());
      discountAuthorized.textProperty().bindBidirectional(model.getPaidBy().discountAuthorizedProperty(), new BooleanStringConverter());
      birthDate.calendarProperty().bindBidirectional(model.getPaidBy().birthDateProperty());
   }

   public void update(PaymentItemPaidBy data)
   {
      fullName.textProperty().set(data.fullNameProperty().get());
      landLinePhone.textProperty().set(data.landLinePhoneProperty().get());
      mobile.textProperty().set(data.mobileProperty().get());
      fax.textProperty().set(data.faxProperty().get());
      email.textProperty().set(data.emailProperty().get());
      creditAuthorized.textProperty().set(new BooleanStringConverter().toString(data.creditAuthorizedProperty().get()));
      discountAuthorized.textProperty().set(new BooleanStringConverter().toString(data.discountAuthorizedProperty().get()));
      birthDate.calendarProperty().set(data.birthDateProperty().get());
   }

   public TextField getFullName()
   {
      return fullName;
   }

   public TextField getLandLinePhone()
   {
      return landLinePhone;
   }

   public TextField getMobile()
   {
      return mobile;
   }

   public TextField getFax()
   {
      return fax;
   }

   public TextField getEmail()
   {
      return email;
   }

   public CheckBox getCreditAuthorized()
   {
      return creditAuthorized;
   }

   public CheckBox getDiscountAuthorized()
   {
      return discountAuthorized;
   }

   public CalendarTextField getBirthDate()
   {
      return birthDate;
   }
}
