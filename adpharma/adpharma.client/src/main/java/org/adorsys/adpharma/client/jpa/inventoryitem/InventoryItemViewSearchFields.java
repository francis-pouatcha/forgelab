package org.adorsys.adpharma.client.jpa.inventoryitem;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.scene.control.TextField;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.AbstractForm;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;

public class InventoryItemViewSearchFields extends AbstractForm<InventoryItem>
{

   private TextField gap;

   private TextField internalPic;

   @Inject
   @Bundle({ CrudKeys.class, InventoryItem.class })
   private ResourceBundle resourceBundle;

   @Inject
   private Locale locale;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();
      gap = viewBuilder.addTextField("InventoryItem_gap_description.title", "gap", resourceBundle);
      internalPic = viewBuilder.addTextField("InventoryItem_internalPic_description.title", "internalPic", resourceBundle);

      gridRows = viewBuilder.toRows();
   }

   public void bind(InventoryItem model)
   {
      gap.textProperty().bindBidirectional(model.gapProperty(), NumberFormat.getInstance(locale));
      internalPic.textProperty().bindBidirectional(model.internalPicProperty());

   }

   public TextField getGap()
   {
      return gap;
   }

   public TextField getInternalPic()
   {
      return internalPic;
   }
}
