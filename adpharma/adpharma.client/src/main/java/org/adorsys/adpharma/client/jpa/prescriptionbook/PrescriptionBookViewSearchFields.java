package org.adorsys.adpharma.client.jpa.prescriptionbook;

import java.util.Locale;
import java.util.ResourceBundle;

import javafx.scene.control.TextField;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.AbstractForm;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;

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
