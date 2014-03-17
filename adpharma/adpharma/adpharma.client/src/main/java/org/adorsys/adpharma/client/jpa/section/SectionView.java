package org.adorsys.adpharma.client.jpa.section;

import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.beans.property.SimpleStringProperty;
import org.adorsys.adpharma.client.jpa.agency.Agency;
import javafx.beans.property.SimpleObjectProperty;

import javafx.scene.control.TextField;
import org.adorsys.javafx.crud.extensions.validation.TextInputControlValidator;
import org.adorsys.javafx.crud.extensions.validation.TextInputControlFoccusChangedListener;
import javafx.scene.control.TextArea;
import org.adorsys.javafx.crud.extensions.ViewModel;
import org.adorsys.javafx.crud.extensions.validation.ToOneAggreggationFieldValidator;
import javafx.beans.property.ObjectProperty;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;

import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.AbstractForm;
import org.adorsys.javafx.crud.extensions.view.GridRow;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;
import org.adorsys.adpharma.client.jpa.section.Section;

public class SectionView extends AbstractForm<Section>
{

   private TextField sectionCode;

   private TextField name;

   private TextField displayName;

   private TextField position;

   private TextField geoCode;

   private TextArea description;

   @Inject
   private SectionAgencyForm sectionAgencyForm;
   @Inject
   private SectionAgencySelection sectionAgencySelection;

   @Inject
   @Bundle({ CrudKeys.class, Section.class })
   private ResourceBundle resourceBundle;

   @Inject
   private TextInputControlValidator textInputControlValidator;
   @Inject
   private ToOneAggreggationFieldValidator toOneAggreggationFieldValidator;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();
      sectionCode = viewBuilder.addTextField("Section_sectionCode_description.title", "sectionCode", resourceBundle);
      name = viewBuilder.addTextField("Section_name_description.title", "name", resourceBundle);
      displayName = viewBuilder.addTextField("Section_displayName_description.title", "displayName", resourceBundle);
      position = viewBuilder.addTextField("Section_position_description.title", "position", resourceBundle);
      geoCode = viewBuilder.addTextField("Section_geoCode_description.title", "geoCode", resourceBundle);
      description = viewBuilder.addTextArea("Section_description_description.title", "description", resourceBundle);
      viewBuilder.addTitlePane("Section_agency_description.title", resourceBundle);
      viewBuilder.addSubForm("Section_agency_description.title", "agency", resourceBundle, sectionAgencyForm, ViewModel.READ_ONLY);
      viewBuilder.addSubForm("Section_agency_description.title", "agency", resourceBundle, sectionAgencySelection, ViewModel.READ_WRITE);

      gridRows = viewBuilder.toRows();
   }

   public void addValidators()
   {
      name.focusedProperty().addListener(new TextInputControlFoccusChangedListener<Section>(textInputControlValidator, name, Section.class, "name", resourceBundle));
      description.focusedProperty().addListener(new TextInputControlFoccusChangedListener<Section>(textInputControlValidator, description, Section.class, "description", resourceBundle));
      // no active validator
   }

   public Set<ConstraintViolation<Section>> validate(Section model)
   {
      Set<ConstraintViolation<Section>> violations = new HashSet<ConstraintViolation<Section>>();
      violations.addAll(textInputControlValidator.validate(name, Section.class, "name", resourceBundle));
      violations.addAll(textInputControlValidator.validate(description, Section.class, "description", resourceBundle));
      violations.addAll(toOneAggreggationFieldValidator.validate(sectionAgencySelection.getAgency(), model.getAgency(), Section.class, "agency", resourceBundle));
      return violations;
   }

   public void bind(Section model)
   {
      sectionCode.textProperty().bindBidirectional(model.sectionCodeProperty());
      name.textProperty().bindBidirectional(model.nameProperty());
      displayName.textProperty().bindBidirectional(model.displayNameProperty());
      position.textProperty().bindBidirectional(model.positionProperty());
      geoCode.textProperty().bindBidirectional(model.geoCodeProperty());
      description.textProperty().bindBidirectional(model.descriptionProperty());
      sectionAgencyForm.bind(model);
      sectionAgencySelection.bind(model);
   }

   public TextField getSectionCode()
   {
      return sectionCode;
   }

   public TextField getName()
   {
      return name;
   }

   public TextField getDisplayName()
   {
      return displayName;
   }

   public TextField getPosition()
   {
      return position;
   }

   public TextField getGeoCode()
   {
      return geoCode;
   }

   public TextArea getDescription()
   {
      return description;
   }

   public SectionAgencyForm getSectionAgencyForm()
   {
      return sectionAgencyForm;
   }

   public SectionAgencySelection getSectionAgencySelection()
   {
      return sectionAgencySelection;
   }
}
