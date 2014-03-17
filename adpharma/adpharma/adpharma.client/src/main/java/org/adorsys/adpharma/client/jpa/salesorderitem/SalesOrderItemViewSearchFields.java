package org.adorsys.adpharma.client.jpa.salesorderitem;

import java.util.List;
import java.util.ResourceBundle;

import java.math.BigDecimal;
import javafx.beans.property.SimpleObjectProperty;
import java.util.Calendar;
import javafx.beans.property.SimpleStringProperty;
import org.adorsys.adpharma.client.jpa.article.Article;
import org.adorsys.adpharma.client.jpa.salesorder.SalesOrder;
import org.adorsys.javaext.format.NumberType;
import java.util.Locale;
import org.adorsys.javafx.crud.extensions.control.BigDecimalField;
import jfxtras.scene.control.CalendarTextField;
import org.adorsys.javafx.crud.extensions.validation.TextInputControlValidator;
import org.adorsys.javafx.crud.extensions.validation.TextInputControlFoccusChangedListener;
import javafx.scene.control.TextField;
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
import org.adorsys.adpharma.client.jpa.salesorderitem.SalesOrderItem;

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
