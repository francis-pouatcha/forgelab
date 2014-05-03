package org.adorsys.adpharma.client.jpa.supplier;

import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.scene.control.TextField;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;

import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.validation.TextInputControlFoccusChangedListener;
import org.adorsys.javafx.crud.extensions.validation.TextInputControlValidator;
import org.adorsys.javafx.crud.extensions.view.AbstractForm;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;

public class SupplierView extends AbstractForm<Supplier>
{

   private TextField name;

   private TextField mobile;

   private TextField fax;

   private TextField email;

   private TextField zipCode;

   private TextField country;

   private TextField internetSite;

   private TextField responsable;

   private TextField revenue;

   private TextField taxIdNumber;

   @Inject
   @Bundle({ CrudKeys.class, Supplier.class })
   private ResourceBundle resourceBundle;

   @Inject
   private TextInputControlValidator textInputControlValidator;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();
      name = viewBuilder.addTextField("Supplier_name_description.title", "name", resourceBundle);
      mobile = viewBuilder.addTextField("Supplier_mobile_description.title", "mobile", resourceBundle);
      fax = viewBuilder.addTextField("Supplier_fax_description.title", "fax", resourceBundle);
      email = viewBuilder.addTextField("Supplier_email_description.title", "email", resourceBundle);
      zipCode = viewBuilder.addTextField("Supplier_zipCode_description.title", "zipCode", resourceBundle);
      country = viewBuilder.addTextField("Supplier_country_description.title", "country", resourceBundle);
      internetSite = viewBuilder.addTextField("Supplier_internetSite_description.title", "internetSite", resourceBundle);
      responsable = viewBuilder.addTextField("Supplier_responsable_description.title", "responsable", resourceBundle);
      revenue = viewBuilder.addTextField("Supplier_revenue_description.title", "revenue", resourceBundle);
      taxIdNumber = viewBuilder.addTextField("Supplier_taxIdNumber_description.title", "taxIdNumber", resourceBundle);

      gridRows = viewBuilder.toRows();
   }

   public void addValidators()
   {
      name.focusedProperty().addListener(new TextInputControlFoccusChangedListener<Supplier>(textInputControlValidator, name, Supplier.class, "name", resourceBundle));
   }

   public Set<ConstraintViolation<Supplier>> validate(Supplier model)
   {
      Set<ConstraintViolation<Supplier>> violations = new HashSet<ConstraintViolation<Supplier>>();
      violations.addAll(textInputControlValidator.validate(name, Supplier.class, "name", resourceBundle));
      return violations;
   }

   public void bind(Supplier model)
   {
      name.textProperty().bindBidirectional(model.nameProperty());
      mobile.textProperty().bindBidirectional(model.mobileProperty());
      fax.textProperty().bindBidirectional(model.faxProperty());
      email.textProperty().bindBidirectional(model.emailProperty());
      zipCode.textProperty().bindBidirectional(model.zipCodeProperty());
      country.textProperty().bindBidirectional(model.countryProperty());
      internetSite.textProperty().bindBidirectional(model.internetSiteProperty());
      responsable.textProperty().bindBidirectional(model.responsableProperty());
      revenue.textProperty().bindBidirectional(model.revenueProperty());
      taxIdNumber.textProperty().bindBidirectional(model.taxIdNumberProperty());
   }

   public TextField getName()
   {
      return name;
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

   public TextField getZipCode()
   {
      return zipCode;
   }

   public TextField getCountry()
   {
      return country;
   }

   public TextField getInternetSite()
   {
      return internetSite;
   }

   public TextField getResponsable()
   {
      return responsable;
   }

   public TextField getRevenue()
   {
      return revenue;
   }

   public TextField getTaxIdNumber()
   {
      return taxIdNumber;
   }
}
