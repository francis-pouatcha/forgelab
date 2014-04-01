package org.adorsys.adpharma.client.jpa.section;

import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import org.adorsys.adpharma.client.jpa.agency.Agency;
import javafx.beans.property.SimpleObjectProperty;

import javafx.scene.control.TextField;
import org.adorsys.javafx.crud.extensions.validation.TextInputControlValidator;
import org.adorsys.javafx.crud.extensions.validation.TextInputControlFoccusChangedListener;
import org.adorsys.javafx.crud.extensions.ViewModel;
import org.adorsys.javafx.crud.extensions.validation.ToOneAggreggationFieldValidator;
import javafx.beans.property.ObjectProperty;
import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.AbstractForm;
import org.adorsys.javafx.crud.extensions.view.GridRow;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;
import org.adorsys.adpharma.client.jpa.section.Section;

public class SectionViewSearchFields extends AbstractForm<Section>
{

   private TextField sectionCode;

   private TextField name;

   private TextField position;

   private TextField geoCode;

   @Inject
   @Bundle({ CrudKeys.class, Section.class })
   private ResourceBundle resourceBundle;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();
      sectionCode = viewBuilder.addTextField("Section_sectionCode_description.title", "sectionCode", resourceBundle);
      name = viewBuilder.addTextField("Section_name_description.title", "name", resourceBundle);
      position = viewBuilder.addTextField("Section_position_description.title", "position", resourceBundle);
      geoCode = viewBuilder.addTextField("Section_geoCode_description.title", "geoCode", resourceBundle);

      gridRows = viewBuilder.toRows();
   }

   public void bind(Section model)
   {
      sectionCode.textProperty().bindBidirectional(model.sectionCodeProperty());
      name.textProperty().bindBidirectional(model.nameProperty());
      position.textProperty().bindBidirectional(model.positionProperty());
      geoCode.textProperty().bindBidirectional(model.geoCodeProperty());

   }

   public TextField getSectionCode()
   {
      return sectionCode;
   }

   public TextField getName()
   {
      return name;
   }

   public TextField getPosition()
   {
      return position;
   }

   public TextField getGeoCode()
   {
      return geoCode;
   }
}
