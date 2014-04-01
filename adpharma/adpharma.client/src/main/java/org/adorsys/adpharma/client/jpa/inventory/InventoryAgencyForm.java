package org.adorsys.adpharma.client.jpa.inventory;

import java.util.List;
import java.util.ResourceBundle;

import javafx.scene.control.TextField;
import org.adorsys.javafx.crud.extensions.validation.TextInputControlValidator;
import org.adorsys.javafx.crud.extensions.validation.TextInputControlFoccusChangedListener;
import javafx.scene.control.CheckBox;
import javafx.util.converter.BooleanStringConverter;
import org.adorsys.adpharma.client.jpa.agency.AgencyCompanyForm;
import org.adorsys.adpharma.client.jpa.agency.AgencyCompanySelection;
import org.adorsys.javafx.crud.extensions.ViewModel;
import javafx.scene.control.TextArea;
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

import org.adorsys.adpharma.client.jpa.agency.Agency;

public class InventoryAgencyForm extends AbstractToOneAssociation<Inventory, Agency>
{

   private TextField agencyNumber;

   private TextField name;

   private CheckBox active;

   private TextField phone;

   private TextField fax;

   @Inject
   @Bundle({ CrudKeys.class, Agency.class })
   private ResourceBundle resourceBundle;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();
      agencyNumber = viewBuilder.addTextField("Agency_agencyNumber_description.title", "agencyNumber", resourceBundle);
      name = viewBuilder.addTextField("Agency_name_description.title", "name", resourceBundle);
      active = viewBuilder.addCheckBox("Agency_active_description.title", "active", resourceBundle);
      phone = viewBuilder.addTextField("Agency_phone_description.title", "phone", resourceBundle);
      fax = viewBuilder.addTextField("Agency_fax_description.title", "fax", resourceBundle);

      gridRows = viewBuilder.toRows();
   }

   public void bind(Inventory model)
   {
      agencyNumber.textProperty().bindBidirectional(model.getAgency().agencyNumberProperty());
      name.textProperty().bindBidirectional(model.getAgency().nameProperty());
      active.textProperty().bindBidirectional(model.getAgency().activeProperty(), new BooleanStringConverter());
      phone.textProperty().bindBidirectional(model.getAgency().phoneProperty());
      fax.textProperty().bindBidirectional(model.getAgency().faxProperty());
   }

   public void update(InventoryAgency data)
   {
      agencyNumber.textProperty().set(data.agencyNumberProperty().get());
      name.textProperty().set(data.nameProperty().get());
      active.textProperty().set(new BooleanStringConverter().toString(data.activeProperty().get()));
      phone.textProperty().set(data.phoneProperty().get());
      fax.textProperty().set(data.faxProperty().get());
   }

   public TextField getAgencyNumber()
   {
      return agencyNumber;
   }

   public TextField getName()
   {
      return name;
   }

   public CheckBox getActive()
   {
      return active;
   }

   public TextField getPhone()
   {
      return phone;
   }

   public TextField getFax()
   {
      return fax;
   }
}
