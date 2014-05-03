package org.adorsys.adpharma.client.jpa.salesorderitem;

import java.util.Locale;
import java.util.ResourceBundle;

import javafx.scene.control.TextField;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.AbstractForm;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;

public class SalesOrderItemViewSearchFields extends AbstractForm<SalesOrderItem>
{

   private TextField internalPic;

   @Inject
   @Bundle({ CrudKeys.class, SalesOrderItem.class })
   private ResourceBundle resourceBundle;

   @Inject
   private Locale locale;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();
      internalPic = viewBuilder.addTextField("SalesOrderItem_internalPic_description.title", "internalPic", resourceBundle);

      gridRows = viewBuilder.toRows();
   }

   public void bind(SalesOrderItem model)
   {
      internalPic.textProperty().bindBidirectional(model.internalPicProperty());

   }

   public TextField getInternalPic()
   {
      return internalPic;
   }
}
