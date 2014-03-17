package org.adorsys.adpharma.client.jpa.supplierinvoiceitem;

import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import org.adorsys.adpharma.client.jpa.article.Article;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleLongProperty;
import java.math.BigDecimal;
import org.adorsys.adpharma.client.jpa.supplierinvoice.SupplierInvoice;
import javafx.scene.control.TextField;
import org.adorsys.javafx.crud.extensions.ViewModel;
import java.text.NumberFormat;
import java.util.Locale;
import org.adorsys.javaext.format.NumberType;
import org.adorsys.javafx.crud.extensions.control.BigDecimalField;
import javafx.beans.property.ObjectProperty;
import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.AbstractForm;
import org.adorsys.javafx.crud.extensions.view.GridRow;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;
import org.adorsys.adpharma.client.jpa.supplierinvoiceitem.SupplierInvoiceItem;

public class SupplierInvoiceItemViewSearchFields extends AbstractForm<SupplierInvoiceItem>
{

   private TextField internalPic;

   private TextField indexLine;

   @Inject
   @Bundle({ CrudKeys.class, SupplierInvoiceItem.class })
   private ResourceBundle resourceBundle;

   @Inject
   private Locale locale;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();
      internalPic = viewBuilder.addTextField("SupplierInvoiceItem_internalPic_description.title", "internalPic", resourceBundle);
      indexLine = viewBuilder.addTextField("SupplierInvoiceItem_indexLine_description.title", "indexLine", resourceBundle);

      gridRows = viewBuilder.toRows();
   }

   public void bind(SupplierInvoiceItem model)
   {
      internalPic.textProperty().bindBidirectional(model.internalPicProperty());
      indexLine.textProperty().bindBidirectional(model.indexLineProperty(), NumberFormat.getInstance(locale));

   }

   public TextField getInternalPic()
   {
      return internalPic;
   }

   public TextField getIndexLine()
   {
      return indexLine;
   }
}
