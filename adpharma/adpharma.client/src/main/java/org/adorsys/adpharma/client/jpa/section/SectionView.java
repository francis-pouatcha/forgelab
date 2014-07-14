package org.adorsys.adpharma.client.jpa.section;

import java.awt.Checkbox;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;

import org.adorsys.javafx.crud.extensions.ViewModel;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.validation.TextInputControlFoccusChangedListener;
import org.adorsys.javafx.crud.extensions.validation.TextInputControlValidator;
import org.adorsys.javafx.crud.extensions.validation.ToOneAggreggationFieldValidator;
import org.adorsys.javafx.crud.extensions.view.AbstractForm;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;

public class SectionView extends AbstractForm<Section>
{

   private TextField sectionCode;

   private TextField name;

   private TextField position;

   private TextField geoCode;
   
   private CheckBox wareHouse ;

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
      position = viewBuilder.addTextField("Section_position_description.title", "position", resourceBundle);
      geoCode = viewBuilder.addTextField("Section_geoCode_description.title", "geoCode", resourceBundle);
      viewBuilder.addTitlePane("Section_agency_description.title", resourceBundle);
      viewBuilder.addSubForm("Section_agency_description.title", "agency", resourceBundle, sectionAgencyForm, ViewModel.READ_ONLY);
      viewBuilder.addSubForm("Section_agency_description.title", "agency", resourceBundle, sectionAgencySelection, ViewModel.READ_WRITE);
      wareHouse = viewBuilder.addCheckBox("Section_wareHouse_description.title", "wareHouse", resourceBundle);

      gridRows = viewBuilder.toRows();
   }

   public void addValidators()
   {
      name.focusedProperty().addListener(new TextInputControlFoccusChangedListener<Section>(textInputControlValidator, name, Section.class, "name", resourceBundle));
      // no active validator
   }

   public Set<ConstraintViolation<Section>> validate(Section model)
   {
      Set<ConstraintViolation<Section>> violations = new HashSet<ConstraintViolation<Section>>();
      violations.addAll(textInputControlValidator.validate(name, Section.class, "name", resourceBundle));
      violations.addAll(toOneAggreggationFieldValidator.validate(sectionAgencySelection.getAgency(), model.getAgency(), Section.class, "agency", resourceBundle));
      return violations;
   }

   public void bind(Section model)
   {
      sectionCode.textProperty().bindBidirectional(model.sectionCodeProperty());
      name.textProperty().bindBidirectional(model.nameProperty());
      position.textProperty().bindBidirectional(model.positionProperty());
      geoCode.textProperty().bindBidirectional(model.geoCodeProperty());
      wareHouse.selectedProperty().bindBidirectional(model.wareHouseProperty());
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

   public TextField getPosition()
   {
      return position;
   }

   public TextField getGeoCode()
   {
      return geoCode;
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
