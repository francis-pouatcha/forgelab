package org.adorsys.adpharma.client.jpa.procurementorder;

import java.util.Locale;
import java.util.ResourceBundle;

import javafx.scene.control.TextField;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adorsys.adpharma.client.jpa.documentprocessingstate.DocumentProcessingState;
import org.adorsys.adpharma.client.jpa.documentprocessingstate.DocumentProcessingStateConverter;
import org.adorsys.adpharma.client.jpa.documentprocessingstate.DocumentProcessingStateListCellFatory;
import org.adorsys.adpharma.client.jpa.procmtordertriggermode.ProcmtOrderTriggerMode;
import org.adorsys.adpharma.client.jpa.procmtordertriggermode.ProcmtOrderTriggerModeConverter;
import org.adorsys.adpharma.client.jpa.procmtordertriggermode.ProcmtOrderTriggerModeListCellFatory;
import org.adorsys.adpharma.client.jpa.procurementordertype.ProcurementOrderType;
import org.adorsys.adpharma.client.jpa.procurementordertype.ProcurementOrderTypeConverter;
import org.adorsys.adpharma.client.jpa.procurementordertype.ProcurementOrderTypeListCellFatory;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.AbstractForm;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;

public class ProcurementOrderViewSearchFields extends AbstractForm<ProcurementOrder>
{

   private TextField procurementOrderNumber;

   @Inject
   @Bundle({ CrudKeys.class, ProcurementOrder.class })
   private ResourceBundle resourceBundle;

   @Inject
   @Bundle(ProcmtOrderTriggerMode.class)
   private ResourceBundle procmtOrderTriggerModeBundle;

   @Inject
   private ProcmtOrderTriggerModeConverter procmtOrderTriggerModeConverter;

   @Inject
   private ProcmtOrderTriggerModeListCellFatory procmtOrderTriggerModeListCellFatory;
   @Inject
   @Bundle(ProcurementOrderType.class)
   private ResourceBundle procurementOrderTypeBundle;

   @Inject
   private ProcurementOrderTypeConverter procurementOrderTypeConverter;

   @Inject
   private ProcurementOrderTypeListCellFatory procurementOrderTypeListCellFatory;
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
      procurementOrderNumber = viewBuilder.addTextField("ProcurementOrder_procurementOrderNumber_description.title", "procurementOrderNumber", resourceBundle);

      gridRows = viewBuilder.toRows();
   }

   public void bind(ProcurementOrder model)
   {
      procurementOrderNumber.textProperty().bindBidirectional(model.procurementOrderNumberProperty());

   }

   public TextField getProcurementOrderNumber()
   {
      return procurementOrderNumber;
   }
}
