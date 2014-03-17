package org.adorsys.adpharma.client.jpa.inventoryitem;

import java.util.List;
import java.util.ResourceBundle;

import java.math.BigDecimal;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleLongProperty;
import org.adorsys.adpharma.client.jpa.login.Login;
import java.util.Calendar;
import javafx.beans.property.SimpleStringProperty;
import org.adorsys.adpharma.client.jpa.article.Article;
import org.adorsys.adpharma.client.jpa.inventory.Inventory;
import org.adorsys.javaext.format.NumberType;
import java.util.Locale;
import org.adorsys.javafx.crud.extensions.control.BigDecimalField;
import javafx.scene.control.TextField;
import java.text.NumberFormat;
import org.adorsys.javafx.crud.extensions.ViewModel;
import org.adorsys.javafx.crud.extensions.validation.ToOneAggreggationFieldValidator;
import jfxtras.scene.control.CalendarTextField;
import javafx.beans.property.ObjectProperty;
import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.AbstractForm;
import org.adorsys.javafx.crud.extensions.view.GridRow;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;
import org.adorsys.adpharma.client.jpa.inventoryitem.InventoryItem;

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
