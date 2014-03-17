package org.adorsys.adpharma.client.jpa.article;

import java.util.List;
import java.util.ResourceBundle;

import javafx.scene.control.TextField;
import org.adorsys.javafx.crud.extensions.validation.TextInputControlValidator;
import org.adorsys.javafx.crud.extensions.validation.TextInputControlFoccusChangedListener;
import javafx.scene.control.TextArea;
import org.adorsys.adpharma.client.jpa.section.SectionAgencyForm;
import org.adorsys.adpharma.client.jpa.section.SectionAgencySelection;
import org.adorsys.javafx.crud.extensions.ViewModel;
import org.adorsys.javafx.crud.extensions.validation.ToOneAggreggationFieldValidator;
import org.adorsys.adpharma.client.jpa.section.SectionAgency;

import javafx.beans.property.ObjectProperty;
import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adorsys.javafx.crud.extensions.view.AbstractToOneAssociation;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.AbstractForm;
import org.adorsys.javafx.crud.extensions.view.GridRow;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;

import org.adorsys.adpharma.client.jpa.section.Section;

public class ArticleSectionForm extends AbstractToOneAssociation<Article, Section>
{

   private TextField sectionCode;

   private TextField name;

   private TextField displayName;

   private TextField position;

   @Inject
   @Bundle({ CrudKeys.class, Section.class })
   private ResourceBundle resourceBundle;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();
      sectionCode = viewBuilder.addTextField("Section_sectionCode_description.title", "sectionCode", resourceBundle);
      name = viewBuilder.addTextField("Section_name_description.title", "name", resourceBundle);
      displayName = viewBuilder.addTextField("Section_displayName_description.title", "displayName", resourceBundle);
      position = viewBuilder.addTextField("Section_position_description.title", "position", resourceBundle);

      gridRows = viewBuilder.toRows();
   }

   public void bind(Article model)
   {
      sectionCode.textProperty().bindBidirectional(model.getSection().sectionCodeProperty());
      name.textProperty().bindBidirectional(model.getSection().nameProperty());
      displayName.textProperty().bindBidirectional(model.getSection().displayNameProperty());
      position.textProperty().bindBidirectional(model.getSection().positionProperty());
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
}
