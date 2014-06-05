package org.adorsys.adpharma.client.jpa.procurementorderitem;

import java.util.Locale;
import java.util.ResourceBundle;

import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.util.converter.BooleanStringConverter;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adorsys.adpharma.client.jpa.documentprocessingstate.DocumentProcessingState;
import org.adorsys.adpharma.client.jpa.documentprocessingstate.DocumentProcessingStateConverter;
import org.adorsys.adpharma.client.jpa.documentprocessingstate.DocumentProcessingStateListCellFatory;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.AbstractForm;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;

public class ProcurementOrderItemViewSearchFields extends AbstractForm<ProcurementOrderItem>
{

   private TextField mainPic;

   private TextField secondaryPic;

   private TextField articleName;

   private CheckBox valid;

   @Inject
   @Bundle({ CrudKeys.class, ProcurementOrderItem.class })
   private ResourceBundle resourceBundle;

   @Inject
   @Bundle(DocumentProcessingState.class)
   private ResourceBundle poStatusBundle;

   @Inject
   private DocumentProcessingStateConverter poStatusConverter;

   @Inject
   private DocumentProcessingStateListCellFatory poStatusListCellFatory;

   @Inject
   private Locale locale;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();
      mainPic = viewBuilder.addTextField("ProcurementOrderItem_mainPic_description.title", "mainPic", resourceBundle);
      secondaryPic = viewBuilder.addTextField("ProcurementOrderItem_secondaryPic_description.title", "secondaryPic", resourceBundle);
      articleName = viewBuilder.addTextField("ProcurementOrderItem_articleName_description.title", "articleName", resourceBundle);
      valid = viewBuilder.addCheckBox("ProcurementOrderItem_valid_description.title", "valid", resourceBundle);

      gridRows = viewBuilder.toRows();
   }

   public void bind(ProcurementOrderItem model)
   {
      mainPic.textProperty().bindBidirectional(model.mainPicProperty());
      secondaryPic.textProperty().bindBidirectional(model.secondaryPicProperty());
      articleName.textProperty().bindBidirectional(model.articleNameProperty());
      valid.textProperty().bindBidirectional(model.validProperty(), new BooleanStringConverter());

   }

   public TextField getMainPic()
   {
      return mainPic;
   }

   public TextField getSecondaryPic()
   {
      return secondaryPic;
   }

   public TextField getArticleName()
   {
      return articleName;
   }

   public CheckBox getValid()
   {
      return valid;
   }
}
