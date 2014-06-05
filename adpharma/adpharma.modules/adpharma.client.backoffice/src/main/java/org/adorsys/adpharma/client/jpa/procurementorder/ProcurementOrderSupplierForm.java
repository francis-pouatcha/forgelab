package org.adorsys.adpharma.client.jpa.procurementorder;

import java.util.ResourceBundle;

import javafx.scene.control.TextField;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adorsys.adpharma.client.jpa.supplier.Supplier;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.AbstractToOneAssociation;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;

public class ProcurementOrderSupplierForm extends AbstractToOneAssociation<ProcurementOrder, Supplier>
{

   private TextField name;

   private TextField fax;

   private TextField email;

   @Inject
   @Bundle({ CrudKeys.class, Supplier.class })
   private ResourceBundle resourceBundle;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();
      name = viewBuilder.addTextField("Supplier_name_description.title", "name", resourceBundle);
      fax = viewBuilder.addTextField("Supplier_fax_description.title", "fax", resourceBundle);
      email = viewBuilder.addTextField("Supplier_email_description.title", "email", resourceBundle);

      gridRows = viewBuilder.toRows();
   }

   public void bind(ProcurementOrder model)
   {
      name.textProperty().bindBidirectional(model.getSupplier().nameProperty());
      fax.textProperty().bindBidirectional(model.getSupplier().faxProperty());
      email.textProperty().bindBidirectional(model.getSupplier().emailProperty());
   }

   public void update(ProcurementOrderSupplier data)
   {
      name.textProperty().set(data.nameProperty().get());
      fax.textProperty().set(data.faxProperty().get());
      email.textProperty().set(data.emailProperty().get());
   }

   public TextField getName()
   {
      return name;
   }

   public TextField getFax()
   {
      return fax;
   }

   public TextField getEmail()
   {
      return email;
   }
}
