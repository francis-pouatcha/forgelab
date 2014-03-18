package org.adorsys.adpharma.client.jpa.prescriptionbook;

import java.util.List;
import java.util.ResourceBundle;

import org.adorsys.adpharma.client.jpa.prescriber.Prescriber;
import javafx.beans.property.SimpleObjectProperty;
import org.adorsys.adpharma.client.jpa.hospital.Hospital;
import org.adorsys.adpharma.client.jpa.agency.Agency;
import org.adorsys.adpharma.client.jpa.login.Login;
import javafx.beans.property.SimpleStringProperty;
import org.adorsys.adpharma.client.jpa.salesorder.SalesOrder;
import java.util.Calendar;

import org.adorsys.javafx.crud.extensions.ViewModel;
import org.adorsys.javafx.crud.extensions.validation.ToOneAggreggationFieldValidator;
import javafx.scene.control.TextField;
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
import org.adorsys.adpharma.client.jpa.prescriptionbook.PrescriptionBook;

public class PrescriptionBookViewSearchFields extends AbstractForm<PrescriptionBook>
{

   private TextField prescriptionNumber;

   @Inject
   @Bundle({ CrudKeys.class, PrescriptionBook.class })
   private ResourceBundle resourceBundle;

   @Inject
   private Locale locale;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();
      prescriptionNumber = viewBuilder.addTextField("PrescriptionBook_prescriptionNumber_description.title", "prescriptionNumber", resourceBundle);

      gridRows = viewBuilder.toRows();
   }

   public void bind(PrescriptionBook model)
   {
      prescriptionNumber.textProperty().bindBidirectional(model.prescriptionNumberProperty());

   }

   public TextField getPrescriptionNumber()
   {
      return prescriptionNumber;
   }
}
