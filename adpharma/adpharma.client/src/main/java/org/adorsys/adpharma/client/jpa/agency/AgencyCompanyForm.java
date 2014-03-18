package org.adorsys.adpharma.client.jpa.agency;

import java.util.List;
import java.util.ResourceBundle;

import org.adorsys.javafx.crud.extensions.validation.TextInputControlValidator;
import org.adorsys.javafx.crud.extensions.validation.TextInputControlFoccusChangedListener;
import javafx.scene.control.TextField;
import java.util.Locale;
import jfxtras.scene.control.CalendarTextField;

import javafx.beans.property.ObjectProperty;
import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adorsys.javafx.crud.extensions.view.AbstractToOneAssociation;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.AbstractForm;
import org.adorsys.javafx.crud.extensions.view.GridRow;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;

import org.adorsys.adpharma.client.jpa.company.Company;

public class AgencyCompanyForm extends AbstractToOneAssociation<Agency, Company>
{

   private TextField displayName;

   private TextField phone;

   private TextField fax;

   private TextField siteManager;

   private TextField email;

   @Inject
   @Bundle({ CrudKeys.class, Company.class })
   private ResourceBundle resourceBundle;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();
      displayName = viewBuilder.addTextField("Company_displayName_description.title", "displayName", resourceBundle);
      phone = viewBuilder.addTextField("Company_phone_description.title", "phone", resourceBundle);
      fax = viewBuilder.addTextField("Company_fax_description.title", "fax", resourceBundle);
      siteManager = viewBuilder.addTextField("Company_siteManager_description.title", "siteManager", resourceBundle);
      email = viewBuilder.addTextField("Company_email_description.title", "email", resourceBundle);

      gridRows = viewBuilder.toRows();
   }

   public void bind(Agency model)
   {
      displayName.textProperty().bindBidirectional(model.getCompany().displayNameProperty());
      phone.textProperty().bindBidirectional(model.getCompany().phoneProperty());
      fax.textProperty().bindBidirectional(model.getCompany().faxProperty());
      siteManager.textProperty().bindBidirectional(model.getCompany().siteManagerProperty());
      email.textProperty().bindBidirectional(model.getCompany().emailProperty());
   }

   public TextField getDisplayName()
   {
      return displayName;
   }

   public TextField getPhone()
   {
      return phone;
   }

   public TextField getFax()
   {
      return fax;
   }

   public TextField getSiteManager()
   {
      return siteManager;
   }

   public TextField getEmail()
   {
      return email;
   }
}
