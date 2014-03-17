package org.adorsys.adpharma.client.jpa.supplier;

import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import org.adorsys.adpharma.client.jpa.salesmargin.SalesMargin;
import javafx.beans.property.SimpleObjectProperty;
import org.adorsys.adpharma.client.jpa.packagingmode.PackagingMode;
import org.adorsys.adpharma.client.jpa.agency.Agency;
import org.adorsys.adpharma.client.jpa.clearanceconfig.ClearanceConfig;
import javafx.scene.control.TextField;
import org.adorsys.javafx.crud.extensions.ViewModel;
import javafx.beans.property.ObjectProperty;
import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.AbstractForm;
import org.adorsys.javafx.crud.extensions.view.GridRow;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;
import org.adorsys.adpharma.client.jpa.supplier.Supplier;

public class SupplierViewSearchFields extends AbstractForm<Supplier>
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
