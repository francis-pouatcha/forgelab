package org.adorsys.adpharma.client.jpa.salesorder;

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
import org.adorsys.adpharma.client.jpa.salesordertype.SalesOrderType;
import org.adorsys.adpharma.client.jpa.salesordertype.SalesOrderTypeConverter;
import org.adorsys.adpharma.client.jpa.salesordertype.SalesOrderTypeListCellFatory;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.AbstractForm;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;

public class SalesOrderViewSearchFields extends AbstractForm<SalesOrder>
{

   private TextField soNumber;

   private CheckBox cashed;

   @Inject
   @Bundle({ CrudKeys.class, SalesOrder.class })
   private ResourceBundle resourceBundle;

   @Inject
   @Bundle(DocumentProcessingState.class)
   private ResourceBundle salesOrderStatusBundle;

   @Inject
   private DocumentProcessingStateConverter salesOrderStatusConverter;

   @Inject
   private DocumentProcessingStateListCellFatory salesOrderStatusListCellFatory;
   @Inject
   @Bundle(SalesOrderType.class)
   private ResourceBundle salesOrderTypeBundle;

   @Inject
   private SalesOrderTypeConverter salesOrderTypeConverter;

   @Inject
   private SalesOrderTypeListCellFatory salesOrderTypeListCellFatory;

   @Inject
   private Locale locale;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();
      soNumber = viewBuilder.addTextField("SalesOrder_soNumber_description.title", "soNumber", resourceBundle);
      cashed = viewBuilder.addCheckBox("SalesOrder_cashed_description.title", "cashed", resourceBundle);

      gridRows = viewBuilder.toRows();
   }

   public void bind(SalesOrder model)
   {
      soNumber.textProperty().bindBidirectional(model.soNumberProperty());
      cashed.textProperty().bindBidirectional(model.cashedProperty(), new BooleanStringConverter());

   }

   public TextField getSoNumber()
   {
      return soNumber;
   }

   public CheckBox getCashed()
   {
      return cashed;
   }
}
